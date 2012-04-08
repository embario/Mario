#ifndef ALG_SORT
#define ALG_SORT

#include <iostream>
#include <list>
#include <vector>
#include <time.h>

using namespace std;
class Algorithms;

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

/* The insertion sort algorithm has a running time complexity of O(n^2). It is particularly useful
   for smaller input sizes. */
static vector <int> * insertionSort (vector <int> * unsortedArray){

  //Designated sorted array to return
  vector <int> * sortedArray = new vector <int>;

  //For each key in the unsortedArray...
  for (int i = 0; i < unsortedArray->size(); i++){
    
    int key = unsortedArray->at(i);
		
    //Flag to determine a successful comparison
    bool found = false; 
   
    //For each element in the sorted list...
    vector <int> ::iterator itr;
    for(itr = sortedArray->begin(); itr != sortedArray->end(); itr++){
		  
      if(key <= *itr){

	found = true;
				
	//Inserts the key element right in between the elements
	//that neighbor the current element pointed by the iterator
	sortedArray->insert(itr, key);
	break;
      }
    }
    
    //Push the key to the back if it is the largest...
    if(found == false)
      sortedArray->push_back(key);
  }

  return sortedArray;
}


/* Merge Sort is a Divide and Conquer recursive algorithm that has a running time
 of O(nlogn) and beats the quadratic running time of insertion sort(O(n^2)) */
static vector <int> * mergeSort (vector <int> * sortArray){

  if(sortArray->size() > 1){

    vector <vector <int> * > * arrays = Algorithms::splitArray(sortArray);
    vector <int> * left = arrays->at(0);
    vector <int> * right = arrays->at(1);

    left = mergeSort(left);
    right = mergeSort(right);

    return merge(left, right, sortArray->size());
  }
  return sortArray;	
}

/**

merge():

This function takes a subarray "left" and a subarray "right" and
combines them to form a third subarray "result" that has all of its
members in sorted order.

 **/
static vector <int> * merge (vector <int> * left, vector <int> * right, int size){

  //Result Array
  vector <int> * result = new vector <int>;

  //Comparison Loop: Iterate through both arrays with pair indices to 
  //maintain sort order when pushing to result array.
  int j = 0;
  int k = 0;
  for(int i = 0; i < size; i++){

    //If both j and k are smaller than the size of
    //left and right...
    if(j < left->size() && k < right->size()){

      //Push Left Array element to Result Array
      if(left->at(j) <= right->at(k)){
	result->push_back(left->at(j));
	j++;
      }

      //Push Right Array element to Result Array
      else if (right->at(k) <= left->at(j)){
	result->push_back(right->at(k));
	k++;
      }
    }

    //Do this for bookkeeping bigger elements (Left)
    else if (j < left->size()){
      result->push_back(left->at(j));
      j++;
    }

    //Do this for bookkeeping bigger elements (Right)
    else if (k < right->size()){
      result->push_back(right->at(k));
      k++;
    }

  }//end loop

  return result;
}

/* This algorithm has a running time of O(n^2). Against Insertion Sort,
 Selection Sort is asymptotically worse because of extra constants. */
static vector <int> * selectionSort(vector <int> * sortArray){

  for (int i = 0; i < sortArray->size() - 1; i++){
    for(int j = i; j < sortArray->size(); j++){

      if(sortArray->at(j) < sortArray->at(i)){
	int temp = sortArray->at(i);
	sortArray->at(i) = sortArray->at(j);
	sortArray->at(j) = temp;
      }
      
    }//end inner loop
    
  }//end outer loop
  
  return sortArray;
}

/* Heap Sort has two extra subroutines: buildHeap() and heapify(). Together,
this algorithm competes against Merge Sort(O(nlogn)) with an upper bound of O(nlogn)
and is considered to be asymptotically better. */
static vector <int> * heapSort (vector <int> * unsortedArray){

  //TODO:
  return NULL;

}

/* Counting Sort performs sorting because we know the range of the integers ahead of time.
This algorithms beats any comparison-based sorting algorithm asymptotically because of its
linear time (O(n)) running time. */
static vector <int> * countingSort(vector <int> * unsortedArray, int range){

  unsortedArray->push_back(-1);
  vector <int> * result = new vector <int> (unsortedArray->size());
  vector <int> * counts = new vector <int> (range);

  //Zero out this array.
  for(int i = 0; i < range; i++)
    counts->at(i) = 0;

  //Capture the count for each element in the proper position in the counts array.
  for(int j = 0; j < unsortedArray->size() - 1; j++)
    counts->at(unsortedArray->at(j)) = counts->at(unsortedArray->at(j)) + 1;

  //Get a cascading index map that determines element position when sorting.
  for(int i = 1; i < range; i++)
    counts->at(i) = counts->at(i) + counts->at(i-1);

  //Finally, swap in elements to the result array at the indices given as values in the count array.
  for(int j = unsortedArray->size() - 2; j >= 0; j--){
    result->at(counts->at(unsortedArray->at(j))) = unsortedArray->at(j);
    counts->at(unsortedArray->at(j)) = counts->at(unsortedArray->at(j)) - 1;
  }

  printArray(*counts);
  cout << "OK" << endl;

  delete unsortedArray;
  delete counts;
  return new vector <int> (result->begin() + 1, result->end());

}

#endif /* ALG_SORT */
