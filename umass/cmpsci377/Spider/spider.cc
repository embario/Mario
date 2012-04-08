/*
 * Spider.cc
 *
 *  Created on: Oct 17, 2009
 *      Author: mbarrenecheajr
 */

#include "url.h"
#include "simplesocket.h"
#include <iostream>
#include <stdio.h>
#include <set>
#include <queue>
#include <pthread.h>

using namespace std;

/****************   GLOBAL VARIABLES   *****************************************/

//This is our meaning 
//for the word "Buffer"
struct buffer_t{

  char * buffer;
  int workerId;
  url_t URL;
  int bufferSize;
  int depth;
}; 

/********************** Functions *************************/

//Principal Functions 
void * workerThread (void * i);
void * parserThread (void * i);

//Function that reads from URL and returns the buffer
char * readFromURL(buffer_t & buffer);

//Update the Total URL set and return a queue of those that haven't been visited
queue <url_t> checkURLs(set <url_t, lturl>  parsedURLs);

//Safe Poppers from Containers
buffer_t getBufferFromBufferQueue();
buffer_t getURLFromWorkerQueue();

//Safe Printers for Threads
void printWorkerThreadURL(url_t URL, int thread_id);
void printParserThreadURL(url_t URL, int thread_id);

//Helper Function for Creating the Root URL Struct
url_t createURLStruct(string url);

//Main Function to check Command Args
bool checkArgs(int argc, char * argv []);

/************************ Variables ****************************/

//Use this to count URLs left in the Worker Queue.
int g_URL_WORKER_COUNTER = 0;

//Finished Variable
bool g_FINISHED = false;

//Command Line Arguments
string g_rootURL;
int g_depth = 0;
int g_numberOfWorkerThreads = 0;

//Control Containers
queue <buffer_t> * g_workerQueue;
queue <buffer_t> * g_bufferQueue;
set <url_t, lturl> * g_URLs;

//Mutual Exclusion and Ordering Mechanisms

//Locks
pthread_mutex_t g_finished;
pthread_mutex_t g_printLock;
pthread_mutex_t g_workerQueueLock;
pthread_mutex_t g_bufferQueueLock;
pthread_mutex_t g_urlCounter;
pthread_mutex_t g_readFromURL;
pthread_mutex_t g_checkURL;
pthread_mutex_t g_parseURL;

//Condition Variables
pthread_cond_t g_hasMoreBuffers;
pthread_cond_t * g_workerThreadDone;
pthread_cond_t g_workerQueueEmpty;
pthread_cond_t g_bufferQueueEmpty;

//Thread Attribute
pthread_attr_t g_threadAttr;

/*************************************************************************/
/****************************  Main Method  ******************************/
/*************************************************************************/

int main(int argc, char * argv[]){

  if(checkArgs(argc, argv) == false)
    return 1;

  //These args check out!
  g_rootURL = string(argv[1]);
  g_depth = atoi(argv[2]);
  g_numberOfWorkerThreads = atoi(argv[3]);


  //If there are no threads, end the program.
  if(g_numberOfWorkerThreads == 0)
    return 0;

  /********************* Pre-Loop Business **********************/

  //Worker Threads
  pthread_t * worker_tids  = new pthread_t [g_numberOfWorkerThreads];
  //Parser Thread
  pthread_t parser_tid;

  /** Mutual Exclusion and Ordering Mechanisms **/

  //Locks
  pthread_mutex_init(&g_finished, NULL);
  pthread_mutex_init(&g_printLock, NULL);
  pthread_mutex_init(&g_workerQueueLock, NULL);
  pthread_mutex_init(&g_bufferQueueLock, NULL);
  pthread_mutex_init(&g_urlCounter, NULL);
  pthread_mutex_init(&g_readFromURL, NULL);
  pthread_mutex_init(&g_checkURL, NULL);
  pthread_mutex_init(&g_parseURL, NULL);
 
  //Condition Variables
  pthread_cond_init(&g_bufferQueueEmpty, NULL);
  pthread_cond_init(&g_workerQueueEmpty, NULL);
  pthread_cond_init(&g_hasMoreBuffers, NULL);

  //Individual Thread Locks and Condition Variables
  g_workerThreadDone = new pthread_cond_t [g_numberOfWorkerThreads];

  for(int i = 0; i < g_numberOfWorkerThreads; i++)
    pthread_cond_init(&g_workerThreadDone[i], NULL);
  
  //Instantiate Control Containers
  g_workerQueue = new queue <buffer_t>;
  g_bufferQueue = new queue <buffer_t>;
  g_URLs = new set <url_t, lturl>;

  //Create Thread Attribute (Stack Size)
  int status = pthread_attr_init(&g_threadAttr);
  if (status) {
    cout << "pthread_attr_init returned " << status << endl;
    exit(1);
  }
  status = pthread_attr_setstacksize(&g_threadAttr, 5*1024*1024);
  if (status) {
    cout << "pthread_attr_setstacksize returned " << status << endl;
    exit(1);
  }

  //First, Get the Root URL Struct and send it to the Worker Queue
  buffer_t rootURL;
  rootURL.URL = createURLStruct(g_rootURL);
  rootURL.depth = 0; //Have the depth initially be zero!
  g_workerQueue->push(rootURL);
  g_URL_WORKER_COUNTER++; //We Just pushed the Root URL
  g_URLs->insert(rootURL.URL);

  //If we want threads to run the program...
  if(g_numberOfWorkerThreads > 0){

    //Create the Worker Threads
    for (int i = 0; i < g_numberOfWorkerThreads; i++){
      int * id = new int;
      *id = i;
      pthread_create(&worker_tids[i], &g_threadAttr, workerThread, (void *)id);
    }

    //Create the Parser Thread
    pthread_create(&parser_tid, &g_threadAttr, parserThread, NULL);
    
    //Join the Threads
    for (int i = 0; i < g_numberOfWorkerThreads; i++)
      pthread_join(worker_tids[i], NULL);
    pthread_join(parser_tid, NULL);
    
  }

  //De-allocation
  delete g_workerQueue;
  delete g_bufferQueue;
  delete g_URLs;
  delete worker_tids;
  delete g_workerThreadDone;
  
  return 0;

}//end main();

void * workerThread(void * i){

  int  id = *((int*) i);

  while(true){

  //Grab the URL from the Worker Queue Safely
  buffer_t buff = getURLFromWorkerQueue();
  buff.workerId = id;

  if(buff.depth == -1)
    return NULL;
  
  //Read from the URL, Modify the Buffer, and return its Size too
  buff.buffer = readFromURL(buff);
  
  /******** Pushing the Buffer to the Buffer Queue **********/
  if(buff.bufferSize > 0){

    //Push the Buffer onto the Buffer Queue for Parsing
    pthread_mutex_lock(&g_bufferQueueLock);
  
    //Print off the URL and Thread Info Safely
    printWorkerThreadURL(buff.URL, id);

    g_bufferQueue->push(buff);

    //Signal the Parser Thread that there are more Buffers
    pthread_cond_signal(&g_hasMoreBuffers);
  
    //Have the Working thread go to sleep until the parser does its job and signals it!
    pthread_cond_wait(&g_workerThreadDone[id], &g_bufferQueueLock);
  
    pthread_mutex_unlock(&g_bufferQueueLock);
  }

  pthread_mutex_lock(&g_finished);
  if(g_FINISHED == true){
    pthread_mutex_unlock(&g_finished);
    return NULL;
  }
  pthread_mutex_unlock(&g_finished);
  
  }//End Loop

  return NULL;
}//end workerThread

void * parserThread(void * i){

  while(true){

    //Grab the next buffer safely
    buffer_t buff = getBufferFromBufferQueue();

    //If the Buffer is NULL, then quit.
    if(buff.depth == -1)
      return NULL;

    set <url_t, lturl> parsed_urls;
    parse_URLs(buff.buffer, buff.bufferSize, parsed_urls);
    
    printParserThreadURL(buff.URL, buff.workerId);

    //Signal the Worker (who pushed the buffer into the Buffer Queue)
    //To Wake up and look for more work!
    pthread_cond_signal(&g_workerThreadDone[buff.workerId]);

    //Check for Recurring URLs and acquire the ones that are only New
    queue <url_t> newURLs = checkURLs(parsed_urls);

    //Lock the Worker Queue
    pthread_mutex_lock(&g_workerQueueLock);

    //If the Buffer's depth is less than the command line Depth...
    if(buff.depth < g_depth){

      int depth = buff.depth + 1;
      buffer_t temp;
      while (newURLs.empty() == false){
	temp.URL = newURLs.front();
	temp.depth = depth;
	g_workerQueue->push(temp);
	newURLs.pop();
	g_URL_WORKER_COUNTER++;
      }

    }//end if conditional

    //Signal all the threads that are waiting for more work!
    pthread_cond_broadcast(&g_workerQueueEmpty);

    //Unlock the Worker Queue
    pthread_mutex_unlock(&g_workerQueueLock);

  }//end while loop
  return NULL;
}

//Grab a Buffer from the Buffer Queue
buffer_t getBufferFromBufferQueue(){

  //Grab the Lock to pop a Buffer from the Buffer Queue
  pthread_mutex_lock(&g_bufferQueueLock);
    
  //Wait until there's something to grab!
  while(g_bufferQueue->empty() == true){

    //Terminating Condition (To Stop the Program!)
    if(g_URL_WORKER_COUNTER <= 0){

      pthread_mutex_lock(&g_finished);
      g_FINISHED = true;
      pthread_mutex_unlock(&g_finished);

      for(int i = 0; i < g_numberOfWorkerThreads; i++){
	pthread_cond_signal(&g_workerThreadDone[i]);
	pthread_cond_broadcast(&g_workerQueueEmpty);
      }

      buffer_t quit;
      quit.depth = -1;
      pthread_mutex_unlock(&g_bufferQueueLock);
      return quit;
    }

    pthread_cond_wait(&g_hasMoreBuffers, &g_bufferQueueLock);

  }//end while loop
 
  buffer_t buff = g_bufferQueue->front();
  g_bufferQueue->pop();
  g_URL_WORKER_COUNTER--;

  pthread_mutex_unlock(&g_bufferQueueLock);
  return buff;

}

//Grab a URL from the Worker Queue
buffer_t getURLFromWorkerQueue(){

    pthread_mutex_lock(&g_workerQueueLock);

    //If the queue is empty, wait for more
    while(g_workerQueue->empty() == true){

      pthread_mutex_lock(&g_finished);
      if(g_FINISHED == true){
	pthread_mutex_unlock(&g_finished);
	buffer_t quit;
	quit.depth = -1;
	pthread_mutex_unlock(&g_workerQueueLock);
	return quit;
      }
      pthread_mutex_unlock(&g_finished);

      pthread_cond_wait(&g_workerQueueEmpty, &g_workerQueueLock);
    }

    pthread_mutex_lock(&g_finished);
    if(g_FINISHED == true){
      pthread_mutex_unlock(&g_finished);
      buffer_t quit;
      quit.depth = -1;
      pthread_mutex_unlock(&g_workerQueueLock);
      return quit;
    }
    pthread_mutex_unlock(&g_finished);
    
    buffer_t URL = g_workerQueue->front();
    g_workerQueue->pop();

    //Update the URL Counter
    //pthread_mutex_lock(&g_urlCounter);
    // g_URL_WORKER_COUNTER--;
    // pthread_mutex_unlock(&g_urlCounter);

    pthread_mutex_unlock(&g_workerQueueLock);

    return URL;
}

//Worker Threads need this to print off the URL right BEFORE 
//reading it and adding the buffer to the Buffer Queue.
 void printWorkerThreadURL(url_t URL, int thread_id){

   pthread_mutex_lock(&g_printLock);
   cout << "requester " << thread_id << " url " << URL.host << "/" << URL.file << endl;
   pthread_mutex_unlock(&g_printLock);
 }

//Parser Thread needs this to print off the URL right AFTER
//parsing the page and BEFORE sending all the URLS to the work queue
 void printParserThreadURL(url_t URL, int thread_id){

    pthread_mutex_lock(&g_printLock);
    cout << "service requester " << thread_id << " url " << URL.host << "/" << URL.file << endl;
    pthread_mutex_unlock(&g_printLock);
  }

/* readFromURL();
This function accepts a url_t struct consisting of a hostname and filename (as well as a flag to output buffer)
and modifies the buffer that a particular thread has read in from the given URL. Finally, the
Buffer Queue pushes the buffer into it so that the parser thread can grab a buffer later. */
char * readFromURL(buffer_t & buffer){

  //First, connect to the URL
  string host = buffer.URL.host;
  string file = buffer.URL.file;

  clientsocket sock (host.c_str(), 80, 0, false);

  //if the Connection succeeds...
  if(sock.connect() == true){

    pthread_mutex_lock(&g_readFromURL);

    char buf [MAX_READ];
    //If we're successful, perform the read.
    sprintf(buf, "GET /%s HTTP/1.0\r\nHost: %s\r\n\r\n", file.c_str(), host.c_str());
    //cout << "Written to socket : " << buf << endl;
    fflush(stdout);
    sock.write(buf, strlen(buf));

     pthread_mutex_unlock(&g_readFromURL);
    
    //Read from the Socket
    int ret;
    int size = 0;
    
    //Set Timeout Value in Seconds
    sock.setTimeout(5);
    while ((ret = sock.read(buf + size, MAX_READ - size)) > 0 )
      size += ret;
    
    //Close the Socket
    sock.close();
    //Return the Buffer
    buffer.bufferSize = size;
    char * result = buf;
    return result;

  }
  else{

    pthread_mutex_lock(&g_readFromURL);
    cout <<"Socket Connection failed to :" << host.c_str() << "/" << file.c_str() << endl;
    fflush(stdout);
    pthread_mutex_unlock(&g_readFromURL);
    return NULL;
  }

}//end readFromURL();



/* checkURLS();

This function accepts the parsedURL set that has been returned by parsing a Buffer from the
Buffer Queue (parser thread job). It performs a Set Union on this set and the set of Total URLs
owned by the Spider object to obtain a newly modified URL set that will continuously be used
to have all of the URLs visited.

Secondly, this function performs a Set Difference to obtain the set of URLs that HAVE NOT
been visited yet (i.e. {parsedURLs} - {TotalURLs} = {New URLs}). It then returns the resulting
queue (to be merged with the Worker Queue).

Both of these set operations are done in a For loop that iterates through the parsedURL set
in search for elements that do not yet exist in the TotalURL set. If an element X does not,
it is both added to the TotalURL set and to a result queue that will be returned.  */

queue <url_t> checkURLs(set <url_t, lturl> parsedURLs){

  set <url_t, lturl>:: iterator itr;
  queue <url_t> result;
  pair <set <url_t, lturl>:: iterator, bool> returnPair;

  pthread_mutex_lock(&g_checkURL);
  for(itr = parsedURLs.begin(); itr != parsedURLs.end(); itr++){

    returnPair = g_URLs->insert(*itr);

    if(returnPair.second == true){
      url_t url = *itr;
      result.push(url);
    }
  }
  pthread_mutex_unlock(&g_checkURL);

  return result;
}

//Helper Function for creating the Root URL struct
url_t createURLStruct(string URL){

  url_t result;
  string host, file;
  parse_single_URL(URL, host, file);
  result.host = host;
  result.file = file;
  return result;
}

/* checkArgs()
 * This function checks the program input arguments for legimitacy but does
 * not check for validity (i.e. data types vs. data boundaries). For example,
 * this function might pass (4, {"Spider.cc","hello", 2, 2}), but obviously
 * "hello" is not a proper hostname! */
bool checkArgs(int argc, char * argv[]){
  
  if(argc < 4 || argc > 4){
    printf("Invalid Number of Arguments.\n");
    return false;
  }
  
  //If neither depth or numWorkerThreads are digits, then get out.
  if(isdigit(*(argv[2])) == false || isdigit(*(argv[3])) == false)
    return false;
  if(atoi(argv[2]) < 0)
    return false;
  if(atoi(argv[3]) < 0 || atoi(argv[3]) > 50)
    return false;
  
  //These arguments check out:
  // Args = {[Spider.cc][string hostname][int depth][int numOfWorkerThreads]}
  return true;
  
}
