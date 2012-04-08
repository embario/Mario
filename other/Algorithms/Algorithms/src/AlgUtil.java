/**
   Algorithms: Implementation of Sorting, Searching, and Selection Algorithms
   Sorting Algorithms: (Insertion, Selection, Merge, Heap, and Radix/Counting Sort)
**/

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class AlgUtil {

    private static Random rand = new Random(System.currentTimeMillis());
    private static Scanner inline = new Scanner (System.in);

    /**
       generateRandomArray():

       This function accepts an integer representing the requested size of a random array and creates
       one using the Random library. It returns the newly created random
       array to the main function after printing out the elements in order.

    **/
 
    /*@ public normal_behavior
      @ requires size >= 0 && upperbound >= 0; 
      @ ensures (* \forall i in result; i < 0 *); @*/
    protected static ArrayList <Integer> generateRandomArray (int size, int upperbound){

	if(size <= 0 || upperbound <= 0)
	    return null;
	      
	ArrayList <Integer> result = new ArrayList <Integer> ();
	for(int i = 0; i < size; i++)		    
	    result.add(rand.nextInt(upperbound)); //Generate Random seed for Array
		
	System.out.println("GENERATED RANDOM ARRAY:");
	AlgUtil.printArray(result);
	return result;
    }

    /**
       sortArray():

       This function accepts a string representing the sorting type and an integer representing the
       size of the array (created in generateRandomArray()) and determines which sorting algorithm to
       initiate. Note that the array is officially created as a function parameter passed to the proper 
       sorting algorithm once it is determined through the sortType string. This function returns the 
       return type from the proper sorting algorithm once it is done sorting the array that it passed to it.
    **/
    protected static ArrayList <Integer> sortArray (int sortType, int inputSize){

	//Determine sorting algorithm to initiate based on sortType.
	switch (sortType){

	case 1: return AlgSort.insertionSort(generateRandomArray(inputSize, 100));

	case 2: return AlgSort.selectionSort(generateRandomArray(inputSize, 100));

	case 3: return AlgSort.mergeSort(generateRandomArray(inputSize, 100));

	case 4: return AlgSort.heapSort(generateRandomArray(inputSize, 100));

	case 5: int range = 0;
	    System.out.print("(Counting Sort): What is the range of these integers?");
	    range = inline.nextInt();
	    return AlgSort.countingSort(generateRandomArray(inputSize, range), range);

	}

	System.out.println("Invalid Sort Choice");
	return null;
    }

    /*@ public normal_behavior
      @ requires sortArray != null;
      @ signals (NullPointerException e) sortArray == null; @*/
    protected /*@ pure @*/ static void printArray (ArrayList <Integer> sortArray){

	/*** Printing an array ****/
	System.out.print("Array is {");
	for (int i = 0; i < sortArray.size(); i++){

	    if(i == sortArray.size() - 1)
		System.out.print(sortArray.get(i) + "");
	    else
		System.out.print(sortArray.get(i) + ", ");

	}
	System.out.println("}\n");	
    }



    /*@ public normal_behavior
      @ requires array.size() >= 2;
      @ 
      @*/
    protected static ArrayList <ArrayList <Integer>> splitArray (ArrayList <Integer> array){

	if(array.size() < 2)
	    return null;

	ArrayList <ArrayList <Integer>> result = new ArrayList <ArrayList <Integer>>();

	//Initialize splitArray result
	for (int i = 0; i < 2; i++)
	    result.add(new ArrayList<Integer>());

	//For the Left Side...
	for (int i = 0; i < array.size()/2; i++)
	    result.get(0).add(array.get(i));

	//For the Right Side...
	for (int i = array.size()/2; i < array.size(); i++)
	    result.get(1).add(array.get(i));

	return result;
    }

} //end class Algorithms
