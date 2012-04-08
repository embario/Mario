#include <iostream>
#include <map>
#include <string.h>
#include <set>
#include <list>
#include <queue>
#include <pthread.h>
#include <fstream>

using namespace std;

/**

   The Keyboard Map:

   This program utilizes multithreading to 
   populate a map that correctly represents a
   QWERTY keyboard. Each vertex represents a key
   on the keyboard (Input), and the keyboard map holds that
   vertex as a key to a set of values which represent 
   adjacent keys. This program outputs the values to
   a set of input keys from the user.


1) Parse Input: ex. {'a'} OR {'a','b','c'}
2) Put Threads to work: ONE THREAD FOR EACH INPUT
3) Print Output: ex. {'q','w','s','z'} OR {'q','w','s','z','v','f','g', ... }

**/


//Arguments
int g_numThreads;
string * g_keysequence;

//Data Structures
map <char, set<char> > * g_keyMap; //Our Map to hold <key, value> pairs.
queue <char> * g_keyQueue; //Key Worker Queue for Concurrent Work.

//Constant Thread Variable(s)
static const int MAX_THREADS = 50;
int g_num_barrier_threads = 0;

//The Threads
pthread_t * g_tids;

//Mutual Exclusion
pthread_mutex_t g_queueLock;
pthread_mutex_t g_printLock;
pthread_mutex_t g_barrier;

//Ordering
pthread_cond_t g_barrierFull;

//Functions
void * workerThread(void * id);
void loadMap();
void loadKeySet (string keyline);
void printMap (map <char, set<char> > keymap);
void printQueue (queue <char> keyqueue);
bool checkArgs (int argc, char * argv[]);
set <char> findAdjacentKeys (char key);

int main(int argc, char * argv[]){

  if(checkArgs(argc, argv) == false){
    cout << "Invalid Argument(s)." << endl;
    return 1;
  }

  g_tids = new pthread_t [g_numThreads];

  //Initialize Lock(s)
  pthread_mutex_init(&g_queueLock, NULL);
  pthread_mutex_init(&g_printLock, NULL);
  pthread_mutex_init(&g_barrier, NULL);

  //Initialize Condition Variable(s)
  pthread_cond_init(&g_barrierFull, NULL);

  //Initialize the Keyboard Map and load it up!
  g_keyMap = new map <char, set<char> >;
  loadMap();

  for(int i = 0; i < g_numThreads; i++){

    int * id = new int;
    *id = i;
    pthread_create(&g_tids[i], NULL, workerThread, (void *) id);
  }

  //Join the Threads after their work is done.
  for(int i = 0; i < g_numThreads; i++)
    pthread_join(g_tids[i], NULL);

  return 0;
}

void * workerThread(void * id){

  int * threadid = (int *)id;

  while (true){

    pthread_mutex_lock(&g_queueLock);

    //Terminating Case: Queue is Empty!
    if(g_keyQueue->empty() == true){

      //Give up the lock
      pthread_mutex_unlock(&g_queueLock);

      //Broadcast to any waiting threads
      pthread_cond_broadcast(&g_barrierFull);
      return NULL;
    }

    //Grab some work from the KeyQueue
    char key = g_keyQueue->front();
    g_keyQueue->pop();
    
    pthread_mutex_unlock(&g_queueLock);
  
    pthread_mutex_lock(&g_printLock);

    //find the letter in the map and obtain the set of adjacent letters.
    set <char> adjacentKeys = findAdjacentKeys(key);
    set <char> :: iterator itr;
  
    //Finally, print off the results!
    cout << " Thread [" << *threadid << "] - Key: [" << key << "]  " << "Adjacent Keys: {";
    
    for(itr = adjacentKeys.begin(); itr != adjacentKeys.end(); itr++)
      cout << *itr << " ";
    
    cout << "}" << endl;
    
    pthread_mutex_unlock(&g_printLock);
    


    /** TODO: Barriers are tricky. The Race Condition here is: If Numthreads = x and the Input Key Sequence has a length of y, how do you determine if the thread should wait in the barrier or quit work? **/

    /** SOLUTION:

	we have Numthreads % Length (IKS) = x % y = z, where z = number of remaining work to be done. 

	We evaluate x % y iff 

     **/

    //Barrier implemented here to encourage use of all threads!
    pthread_mutex_lock(&g_barrier);
    g_num_barrier_threads++;

    //If the Barrier is Full, Wake up threads to do more work!
    if(g_num_barrier_threads == g_numThreads){
      pthread_cond_broadcast(&g_barrierFull);

      //Reset this Barrier Counter
      g_num_barrier_threads = 0;
    }

    //But check if the Queue is empty before waiting!
    else if (g_keyQueue->empty() == true){
      pthread_mutex_unlock(&g_barrier);
      return NULL;
    }

    //Wait for the last thread to broadcast.
    else
      pthread_cond_wait(&g_barrierFull, &g_barrier);

    pthread_mutex_unlock(&g_barrier);

  }//end while loop

  return NULL;
}


set <char> findAdjacentKeys (char letter){

  map <char, set<char> > :: iterator itr;
  itr =  g_keyMap->find(letter);

  //Maybe say NULL...
  if(itr != g_keyMap->end()){
    return (*itr).second;
  }
}


void loadMap(){

  ifstream stream ("KeyMap.txt");
  if(stream.good() == true && stream.is_open() == true){

    string bufferStr, result;
    while(stream.eof() == false){

      getline(stream, bufferStr);

      if(bufferStr == "")
	break;

      //Insert the key and its keySet into the Map
      loadKeySet(bufferStr);
    }
    
    stream.close();	  
  }
  else
    cout << "Filestream was invalid. Map was not loaded." << endl;
}


void loadKeySet (string keyline){

  char * line = (char *) keyline.c_str();
  char delimiters [] = " 1234567890!@#$%^&*()[]{};:'\\|,<.>/?`~";

  //The First token is the character (KEY)
  char * key = strtok(line, delimiters);

  //The Second (and last) token is the set of characters (VALUES)
  char * values = strtok(NULL, delimiters);

  set <char> keys;
  for (int i = 0; i < strlen(values); i++)
    keys.insert(values[i]);

  //Finally, insert the pair into the key Map so we can refer to the right
  //adjacent keys later.
  g_keyMap->insert(pair<char, set<char> >(*key, keys));
}

void printQueue(queue <char> keyqueue){

  cout << "Queue: {";
  while(keyqueue.empty() == false){
    cout << keyqueue.front() << " ";
    keyqueue.pop();
  }
  cout << "}" << endl;
}

void printMap (map <char, set <char> > keymap){

    map <char, set<char> >:: iterator itr;
    for(itr = keymap.begin(); itr != keymap.end(); itr++){

      set <char> values = (*itr).second;
      cout << (*itr).first << " {";
      set <char> :: iterator setitr;

      //For each key, print its set of key values
      for(setitr = values.begin(); setitr != values.end(); setitr++)
	cout << *setitr << " ";

      cout << "} " << endl;
  }

    cout << endl;
}

bool checkArgs (int argc, char * argv[]){

  //Check Arguments
  g_numThreads = 0;
  g_keysequence = new string;

  if (argc != 3)
    return false;

  //Convert the first argument to the number of threads
  if(isdigit(*argv[1]) == true && atoi(argv[1]) < MAX_THREADS)
    g_numThreads = atoi(argv[1]);
  else{
    cout << "Too many threads declared." << endl;
    return false;
  }

  cout << "Key Sequence Input (Before Filtering) is: " << argv[2] << endl;

  //Convert the second argument to the proper key sequence
  char delimiters [] = " 1234567890!@#$%^&*()_-+={}[]\\|';:/?.>,<`~";
  char * seq = argv [2];
  char * token = strtok(seq, delimiters);

  while (token != NULL){

    g_keysequence->append(token);
    token = strtok(NULL, delimiters);
  }

  cout << "Key Sequence Input (After Filtering) is: " << *g_keysequence << endl;

  //Arguments are OK. Start loading up the work queue!
  g_keyQueue = new queue <char>;
  for(int i = 0; i < g_keysequence->length(); i++){
    char letter = (*g_keysequence) [i];
    g_keyQueue->push(letter);
  }

  return true;

}
