import java.util.ArrayList;
import java.util.Scanner;


public class AlgDriver {

    private static Scanner inline = null;	
    public static void main (String args []){
	    
		inline = new Scanner (System.in);
		while (true){

		    int inputSize;
		    String sortType = "";
		    
		    System.out.println("---------------------------[ Algorithms ]-----------------------------");
		    System.out.println("What kind of algorithms do you want to interact with?");
		    System.out.println(" (1) Sorting ");
		    input  = inline.next();
		    int sortNum = Integer.parseInt(input);

		    //Choose the Algorithm Type
		    case (sortNum){

		    case 1: AlgSort.begin();
			break;
		    default;
		    }
		}
    }//end main
	
}
