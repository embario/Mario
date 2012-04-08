#ifndef ALG_SORT
#define ALG_SORT

#include <iostream>
#include <list>
#include <vector>
#include <time.h>

using namespace std;

/**

Algorithms: Implementation of Sorting Algorithms

Sorting Algorithms: (Insertion, Selection, Merge, Heap, and Radix/Counting Sort)

**/

static vector <int> * insertionSort (vector <int> * unsortedArray);
static vector <int> * selectionSort(vector <int> * sortArray);
static vector <int> * mergeSort (vector <int> * unsortedArray);
static vector <int> * merge (vector <int> * left, vector <int> * right, int size);
static vector <int> * heapSort (vector <int> * unsortedArray);
static vector <int> * countingSort (vector <int> * unsortedArray, int range);

#endif /* ALG_SORT */
