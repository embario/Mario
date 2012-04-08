#ifndef ALG_MAIN
#define ALG_MAIN

#include <list>
#include <vector>
#include <time.h>
#include <iostream>

using namespace std;
class AlgSort;


/**
Algorithms: Implementation of Sorting, Searching, and Selection Algorithms
Sorting Algorithms: (Insertion, Selection, Merge, Heap, and Radix/Counting Sort)
**/

static vector <vector <int> * > * splitArray (vector <int> * array);
static void printArray (vector <int> sortArray);
static vector <int> * generateRandomArray(int size, int upperbound);
static vector <int> * sortArray (int sortType, int inputSize);

int main (int argc, char * argv []){

  //Initialize the Random Seed Generator
  srand((unsigned)(time(NULL)));	
	
  //Keep looping until done.
  while(true){
    
    //Some Input required.
    int inputSize;
    char * sortType = new char;
    
    cout << "---------------------------[ Algorithms ]-----------------------------" << endl;
    cout << "Please determine the Sort Type for the array to be sorted: " << endl << endl;
    cout << "\t (1) Insertion" << endl << "\t (2) Selection" << endl << "\t (3) Merge" << endl << "\t (4) Heap" << endl;
    cout << "\t (5) Counting" << endl << "\t (6) Radix" << endl << endl;
    cout << "Or simply type 'quit' or 'exit' to finish the program." << endl;
    cout << ":> ";
    scanf("%s", sortType);
    
    if(strcmp(sortType, "quit") == 0 || strcmp(sortType, "exit")== 0){
      cout << "Goodbye!" << endl;
      return 0;
    }
    if(isdigit(*sortType) == false){
      cout << "Invalid Input" << endl;
      continue;
    }
    
    cout << "Please determine the size of the array to be sorted: ";
    cin >> inputSize;
    
    //Sorts
    vector <int> * sortedArray = sortArray(atoi(sortType), inputSize);
    if(sortedArray != NULL)
      printArray(*sortedArray);

  }

  return 0;
}



/**
generateRandomArray():

This function accepts an integer representing the requested size of a random array and creates
one using the srand() and rand() functions (stdlib.h). It returns the newly created random
array to the main function after printing out the elements in order.

**/

static vector <int> * generateRandomArray (int size, int upperbound){

  //int * result = new int [size];
  vector <int> * result = new vector <int>;
  cout << "The Array Generated is {";

  for(int i = 0; i < size; i++){
    
    //Generate Random seed for Array
    result->push_back(rand() % upperbound);
    
    if(i == (size - 1))
      cout << result->at(i) << "";
    
    else
      cout << result->at(i) << ", ";
  }
  
  cout << "}" << endl << endl;
  return result;
}



/**
sortArray():

This function accepts a string representing the sorting type and an integer representing the
size of the array (created in generateRandomArray()) and determines which sorting algorithm to
initiate. Note that the array is officially created as a function parameter passed to the proper sorting algorithm once it is determined through the sortType string. This function returns the return type from the proper sorting algorithm once it is done sorting the array that it passed to it.

**/

static vector <int> * sortArray (int sortType, int inputSize){

  //Determine sorting algorithm to initiate based on sortType.
  switch (sortType){

  case 1: return AlgSort::insertionSort(generateRandomArray(inputSize, 100));

  case 2: return AlgSort::selectionSort(generateRandomArray(inputSize, 100));

  case 3: return AlgSort::mergeSort(generateRandomArray(inputSize, 100));

  case 4: return AlgSort::heapSort(generateRandomArray(inputSize, 100));

  case 5: int range = 0;
    cout << "(Counting Sort): What is the range of these integers?";
    cin >> range;
    return AlgSort::countingSort(generateRandomArray(inputSize, range), range);

  }

  cout << "Invalid Sort Choice" << endl;
  return NULL;
}

static void printArray (vector <int> sortArray){

  /*** Printing the Newly sorted array ****/
  cout << "Array is {";
  for (int i = 0; i < sortArray.size(); i++){
 
    if(i == sortArray.size() - 1)
      cout << sortArray.at(i) << "";
    else
      cout << sortArray.at(i) << ", ";
  }

  cout << "}" << endl;
}

static vector <vector <int> * > * splitArray (vector <int> * array){

  if(array->size() < 2)
    return NULL;

  //Initialize splitArray result
  vector <vector <int> * > * result = new vector <vector <int> *>;
  for(int i = 0; i < 2; i++)
    result->push_back(new vector <int>);

  //For the Left Side...
  for(int i = 0; i < array->size()/2; i++)
    result->at(0)->push_back(array->at(i));

  // For the Right Side... 
    for(int i = array->size()/2; i < array->size(); i++)
      result->at(1)->push_back(array->at(i));
  
  return result;
}

#endif /* ALG_MAIN */
