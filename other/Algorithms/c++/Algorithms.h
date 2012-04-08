#ifndef ALG_MAIN
#define ALG_MAIN

#include <list>
#include <vector>
#include <time.h>
#include <iostream>

using namespace std;

/**

Algorithms: Implementation of Sorting, Searching, and Selection Algorithms

Sorting Algorithms: (Insertion, Selection, Merge, Heap, and Radix/Counting Sort)

**/

static vector <vector <int> * > * splitArray (vector <int> * array);
static void printArray (vector <int> sortArray);
static vector <int> * generateRandomArray(int size, int upperbound);
static vector <int> * sortArray (int sortType, int inputSize);

#endif /* ALG_MAIN */
