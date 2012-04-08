#include <iostream>
#include <time.h>

using namespace std;

struct node_t{

  char data;
  node_t * next;

};

//Utilities
int checkArgs (int argc, char * argv[]);
int * generateRandomArray (int size);
void printArray (node_t * nodes [], string identifier, int size);
bool chooseQuestion(int questionNumber);
void printArray (int array [], string identifier, int size);

//(1)
void deleteLastNode (node_t * nodes [], int indices [], int size);

//(2)
node_t * swapNodes (node_t * head);

//(3)

//(4)

//(5)

/**

The Microsoft Interview Experience: Technical Questions

3) Write a function that accepts a pointer to a node_t and an integer representing
data and inserts a new node_t struct (with the input data) following a node with
the same input data.

4) Implement a stack using a linked list (node_t) and its pop() and push() 
functions.

5) Write a recursive function that accepts a node_t pointer and prints out
a binary tree in inorder notation.

6) (Ball-breaker): Write an iterative function that prints out a binary tree in
inorder notation using a stack.


 **/
int main(int argc, char * argv []){

  //Check Arguments
  int questionNumber = checkArgs (argc, argv);

  //Initiate Specific problem with randomly generated data
  if(chooseQuestion(questionNumber) == false)
    cout << "Invalid Argument(s). " << endl;

  return 0;
}

bool chooseQuestion (int questionNumber){

  if(questionNumber == -1)
    return false;

  //Initialize the Random Seed Generator
  srand((unsigned)(time(NULL)));

  switch (questionNumber){

  case 1:
 
    node_t * nodes [6];
    for (int i = 0; i < 6; i++)
      nodes[i] = new node_t;

    nodes [0]->data = 'b';
    nodes [1]->data = 'c';
    nodes [2]->data = 'f';
    nodes [3]->data = 'a';
    nodes [4]->data = 'd';
    nodes [5]->data = 'e';
    int indices [] = {5, 3, 2, 0, 1, 4};
    deleteLastNode(nodes, indices, 6);
    return true;

  }

  return false;
}

int checkArgs (int argc, char * argv []){

  if (argc != 2)
    return -1;

  char * arg = argv [1];
  for(int i = 0; i < strlen(argv[1]); i++){

    if(isdigit(arg[i]) == false){
      cout << "Invalid argument." << endl;
      return -1;
    }
  }

  return atoi(argv[1]); 
}

/** printArray: prints each element from the input array properly. **/
void printArray (node_t * nodes [], string identifier, int size){

  cout << identifier << ": {";
  for(int i = 0; i < size; i++){

    cout << nodes[i]->data;
    if(i == size - 1)
      cout << "}";
    else
      cout << ", ";
  }

  cout << endl;
}

/** printArray: prints each element from the input array properly. **/
void printArray (int indices [], string identifier, int size){

  cout << identifier << ": {";
  for(int i = 0; i < size; i++){

    cout << indices[i];
    if(i == size - 1)
      cout << "}";
    else
      cout << ", ";
  }

  cout << endl;
}

/**
generateRandomArray():

This function accepts an integer representing the requested size of a random array and creates

one using the srand() and rand() functions (stdlib.h). It returns the newly created random

array to the main function after printing out the elements in order.
**/

int * generateRandomArray (int size){

  int * result = new int [size];
  cout << "The array generated is {";

  for(int i = 0; i < size; i++){

    //Generate Random seed for Array
    result [i] = rand();

    if(i == (size -1))
      cout << result [i] << "";
    else
      cout << result [i] << ", ";
  }

  cout << "}" << endl << endl;
  return result;

}



/******************* MIE Problems ********************/


/** 1) Suppose there is an array of nodes that are mapped by
a secondary array of integers representing indices. Therefore,
both arrays are necessary to correctly determine which index
(key) points to which node (value).

For example, 

Node array: {'a', 'b', 'c', 'd', 'e', 'f', 'g'}
Index array: {5, 2, 3, 0, 4, 1, 6}

Accessing a particular node's data uses both arrays:
        
char data = nodes[index[3]]->data

Write a function that accepts both arrays and the equivalent size and deletes
the node whose index is the last element in the index array. **/
void deleteLastNode (node_t * nodes [], int indices [], int size){

  printArray (nodes, "BEFORE: Nodes", size);
  printArray (indices, "BEFORE: Indices", size);

  /** Delete the element in the node array
  that is mapped by the last element in
  the index array. **/

  //This variable gives us the last index
  int deleteIndex = indices[size - 1];

  delete nodes[deleteIndex];

  //Shift Nodes left one because of the deleted Node...
  for(int i = deleteIndex; i < size - 1; i++)
    nodes [i] = nodes [i+1];

  for(int i = 0; i < size - 1; i++){

    if(indices [i] > deleteIndex)
      indices[i]--;

  }

  printArray(nodes, "AFTER: Nodes", size);
  printArray(indices, "AFTER: Indices", size);
}



/**
2)  Suppose a linked list is implemented using the node_t struct as follows:

 -> [1] -> [2] -> [3] -> [4] -> [5] -> / 

where '/' is the NULL character, the '->' symbols are pointers to the nodes
represented as [#].

Write a function that accepts a pointer to check and swap successive pairs of nodes
in the list. For example, the correct solution of the list above after the function
is complete is:

 -> [2] -> [1] -> [4] -> [3] => [5] -> /

Note that if there is only a single node left after swapping previous pairs, leave
it untouched. **/
node_t swapNodes (node_t * head){

  node_t * currentNode = head;
  while(currentNode != NULL){

    node_t * nextNode = node->next;
    if(nextNode != NULL){

      node_t * swap = currentNode;
      swap = nextNode;
      swap->next = swap;
      swap->next = nextNode->next;

      //This is what should happen after swapping!
      currentNode = nextNode->next;
    }

    else{

    }


  }//end while loop


}
