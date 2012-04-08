
/**
Algorithms: Implementation of Sorting, Searching, and Selection Algorithms
Sorting Algorithms: (Insertion, Selection, Merge, Heap, and Radix/Counting Sort)
**/

import java.util.ArrayList;
import java.util.Scanner;

public class Algorithms {

    private Random rand;

    public static void main (String args []){

	Scanner inline = new Scanner (System.in);
	while (true){

	    int inputSize;
	    String sortType = "";
	    
	    System.out.println("---------------------------[ Algorithms ]-----------------------------");
	    System.out.println("Please determine the Sort Type for the array to be sorted: \n");
	    System.out.println(" (1) Insertion\n (2) Selection\n (3) Merge\n (4) Heap\n (5) Counting\n (6) Radix\n");
	    System.out.println("Or Simply Type 'Quit' or 'Exit' to Finish the Program");
	    System.out.print(":> ");
	    sortType = inline.next();

	    if(sortType.equalsIgnoreCase("quit") == true || sortType.equalsIgnoreCase("exit") == true){
		System.out.println("Goodbye!");
		System.exit(0);
	    }

	    System.out.print ("Please determine the size of the array to be sorted: ");
	    inputSize = inline.nextInt();

	    ArrayList <int> sortedArray = sortArray (Integer.parseInt(sortType), inputSize);
	    if (sortedArray != null)
		printArray (sortedArray);

	}
    }//end main


/**
generateRandomArray():

This function accepts an integer representing the requested size of a random array and creates
one using the srand() and rand() functions (stdlib.h). It returns the newly created random
array to the main function after printing out the elements in order.

**/

    protected static ArrayList <Integer> generateRandomArray (int size, int upperbound){

	ArrayList <Integer> result = new ArrayList <Integer> ();
	System.out.print ("The Array Generated is {");

	for(int i = 0; i < size; i++){
    
	    //Generate Random seed for Array
	    result->push_back(rand() % upperbound);

	    result.add(
    
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





} //end class Algorithms