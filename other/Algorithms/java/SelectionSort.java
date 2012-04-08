import java.util.*;


public class SelectionSort{

    public static void main (String args []){

	if(args.length < 1)
	    System.exit(1);

	//Number of Elements in the Random array
	int numsForArray = Integer.parseInt(args[0]);
	Random rand = new Random();

	//Our array to sort
	int [] array = new int [numsForArray];

	for(int i = 0; i < numsForArray; i++){
	    array[i] = rand.nextInt(100);
	    System.out.print(array[i] + " ");
	}
	System.out.println();

	//Execute Selection Sort
	doSelectionSort(array);

	//Print the Resulting Sorted Array
	printArray(array);

    }//end main


    /* doSelectionSort():
       <p>
       This method will accept an array of integer values
       and run the Selection Sort algorithm to correctly sort
       them in ascending order.

     */
    public static void doSelectionSort(int array []){

	for (int i = 0; i < array.length - 1; i++){
	    for(int j = i; j < array.length; j++){

		if(array[j] < array[i]){
		    int temp = array[i];
		    array[i] = array[j];
		    array[j] = temp;
		}

	    }//end inner loop

	}//end outer loop
    }

    public static void printArray (int array []){

	for (int i = 0; i < array.length; i++)
	    System.out.print(array[i] + " ");
	System.out.println();
    }

}//end class