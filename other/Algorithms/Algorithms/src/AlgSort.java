import java.util.ArrayList;

/**
   Algorithms: Implementation of Sorting Algorithms
   Sorting Algorithms: (Insertion, Selection, Merge, Heap, and Radix/Counting Sort)

**/
public class AlgSort {

    protected static void begin(){
	
	System.out.println("------------- [Sorting Algorithms] ---------------------");
	System.out.println("Please determine the Sort Type for the array to be sorted: \n");
	System.out.println(" (1) Insertion\n (2) Selection\n (3) Merge\n (4) Heap\n (5) Counting\n");
	System.out.println("Or Simply Type 'Quit' or 'Exit' to Finish the Program");
	System.out.print(":> ");

	String input = inline.next();
	int sortType = Integer.parseInt(input);

	if(sortType.equalsIgnoreCase("quit") == true || sortType.equalsIgnoreCase("exit") == true){
	    System.out.println("\nGoodbye!");
	    System.exit(0);
	}
		  
	System.out.print ("Please determine the size of the array to be sorted: ");
	inputSize = inline.nextInt();

	ArrayList <Integer> sortedArray = AlgUtil.sortArray (Integer.parseInt(sortType), inputSize);
	if (sortedArray != null)
	    AlgUtil.printArray (sortedArray);



    }

    /* The insertion sort algorithm has a running time complexity of O(n^2). It is particularly useful
       for smaller input sizes. */
    static protected ArrayList <Integer> insertionSort (ArrayList <Integer> unsortedArray){
		
	//Designated sorted array to return
	ArrayList <Integer> sortedArray = new ArrayList <Integer>();
		
	//For each key in the unsortedArray...
	for (int i = 0; i < unsortedArray.size(); i++){
			
	    int key = unsortedArray.get(i);
			
	    //Flag to determine a successful comparison
	    boolean found = false;
			
	    //For each element in the sorted list...
	    for(int j = 0; j < sortedArray.size(); j++){
				
		int integer = sortedArray.get(j);
		if(key <= integer){
					
		    found = true;
		    //Inserts the key element right in between the elements
		    //that neighbor the current element pointed by the iterator
		    sortedArray.add(j, key);
		    break;
		}
	    }//end inner loop
			
	    //Push the key to the back if it is the largest...
	    if(found == false)
		sortedArray.add(key);
		    
	}//end outer loop
	return sortedArray;
    }
	

    /* Merge Sort is a Divide and Conquer recursive algorithm that has a running time
       of O(nlogn) and beats the quadratic running time of insertion sort(O(n^2)) */
    static protected ArrayList <Integer> mergeSort (ArrayList <Integer> sortArray){
		
	if (sortArray.size() > 1){
		
	    //Split the array in half.
	    ArrayList <ArrayList <Integer>> arrays = AlgUtil.splitArray(sortArray);
	    ArrayList <Integer> left = arrays.get(0);
	    ArrayList <Integer> right = arrays.get(1);
			
	    //Perform mergeSort recursively for both halves.
	    left = mergeSort(left);
	    right = mergeSort(right);
			
	    return merge (left, right, sortArray.size());
	}
		
	return sortArray;
    }

    /**

       merge():

       This function takes a subarray "left" and a subarray "right" and
       combines them to form a third subarray "result" that has all of its
       members in sorted order.

    **/
    static ArrayList <Integer> merge (ArrayList <Integer> left, ArrayList <Integer> right, int size){
		
	//Result Array
	ArrayList <Integer> result = new ArrayList <Integer>();
		
	//Comparison Loop: Iterate through both arrays with pair indices to 
	//maintain sort order when pushing to result array.
	int j = 0;
	int k = 0;
	for (int i = 0; i < size; i++){
			
	    //If both j and k are smaller than the size of
	    //left and right...
	    if (j < left.size() && k < right.size()){
			
		//Push Left Array Element to Result Array
		if (left.get(j) <= right.get(k)){
		    result.add(left.get(j));
		    j++;
		}
				
		//Push Right Array element to Result Array
		else if (right.get(k) <= left.get(j)){
		    result.add(right.get(k));
		    k++;
		}
	    }//end if conditional
			
				
	    else if (j < left.size()){
		result.add(left.get(j));
		j++;
	    }
				
	    else if (k < right.size()){
		result.add(right.get(k));
		k++;
	    }	
			
	}//end loop
		
	return result;
    }


    /* This algorithm has a running time of O(n^2). Against Insertion Sort,
       Selection Sort is asymptotically worse because of extra constants. */
    protected static ArrayList <Integer> selectionSort (ArrayList <Integer> sortArray){
		
	for (int i = 0; i < sortArray.size() - 1; i++){
	    for (int j = i; j < sortArray.size(); j++){
				
		if (sortArray.get(j) < sortArray.get(i)){
		    int temp = sortArray.get(i);
		    sortArray.set(i, sortArray.get(j));
		    sortArray.set(j, temp);
		}
	    }//end inner loop
	}//end outer loop
		
	return sortArray;
    }


    /* Counting Sort performs sorting because we know the range of the integers ahead of time.
       This algorithms beats any comparison-based sorting algorithm asymptotically because of its
       linear time (O(n)) running time. */
    protected static ArrayList <Integer> countingSort (ArrayList <Integer> unsortedArray, int range){
		
	unsortedArray.add(-1);
	ArrayList <Integer> result = new ArrayList <Integer> (unsortedArray.size());
	ArrayList <Integer> counts = new ArrayList <Integer> (range);
		
	//Zero out these arrays
	for (int i = 0; i < range; i++)
	    counts.add(0);
	for (int i = 0; i < unsortedArray.size(); i++)
	    result.add(0);
	
	//Capture the count for each element in the proper position in the counts array.
	for (int i = 0; i < unsortedArray.size() - 1; i++)
	    counts.set(unsortedArray.get(i), counts.get(unsortedArray.get(i)) + 1);
		
	//Get a cascading index map that determines element position when sorting.
	for (int i = 1; i < range; i++)
	    counts.set(i, (counts.get(i) + counts.get(i-1)));
		
	//Finally, swap in elements to the result array at the indices given as values in the count array.
	for (int i = unsortedArray.size() - 2; i >= 0; i--){
	    result.set((counts.get(unsortedArray.get(i))), unsortedArray.get(i));
	    counts.set(unsortedArray.get(i), counts.get(unsortedArray.get(i)) - 1);
	}
		
	return result;
    }
	
	
    /* Heap Sort has two extra subroutines: buildHeap() and heapify(). Together,
       this algorithm competes against Merge Sort(O(nlogn)) with an upper bound of O(nlogn)
       and is considered to be asymptotically better. */
    protected static ArrayList <Integer> heapSort (ArrayList <Integer> unsortedArray){
		
	//TODO: Implement this when you get a chance!
	System.out.println("NOT IMPLEMENTED YET");
	return null;
    }

}//end class AlgSort
