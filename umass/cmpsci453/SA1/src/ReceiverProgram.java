import java.net.ServerSocket;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/** Mario Barrenechea <p>
 * 	10/13/2010 <p>
 * 	CMPSCI453: SA #1 <p>
 * 
 * 	ServerProgram.java:
 * 
 * 	This code is based on the Server code presented
 * 	in class by Prof. Jim Kurose. It has been modified 
 * 	to complete the socket programming assignment.
 * 
 * @author mbarrene
 *
 */
public class ReceiverProgram {
	

    private static final int PORT_NUMBER = 6566;
    private static ServerSocket myReceiver = null;
    private static Socket serviceSocket = null;
    private static BufferedReader inFromRelay = null;
	
    public static void main (String args []){
		
	try {
		    	
	    //Set up the Server Socket listening on specified Port Number.
	    myReceiver = new ServerSocket (PORT_NUMBER);
	    
	    //We use a boolean to evaluate when to end the Server program.
	    boolean receiverDone = false;

	    //We iterate until the Server process is killed.
	    while (receiverDone == false){
	       
		System.out.print("Waiting for a Relay socket connection...");
		//Now wait for a client socket connection...
		serviceSocket = myReceiver.accept();
		System.out.println ("OK");
		
		//Set up the appropriate streams.
		System.out.print ("Setting up appropriate streams...");
		inFromRelay = new BufferedReader (new InputStreamReader (serviceSocket.getInputStream()));
		System.out.println("OK");
		
		//Boolean to evaluate when Relay is finished sending messages.
		boolean relayDone = false;
		
		while (relayDone == false){
		     	      
		    System.out.println("-----------------------------------\n");
		    System.out.print("Reading in Message from Relay Socket...");
		    String message = inFromRelay.readLine();
		    System.out.println("OK");

		    System.out.println ("Message is: " + message);


		    //If it was a CLOSE Message...
		    if (message.equalsIgnoreCase("Close")){
			relayDone = true;
			receiverDone = true;
		    }

		}//end inner while loop

		System.out.print("Closing Streams and Sockets for this Connection...");			
		inFromRelay.close();
		serviceSocket.close();
		System.out.println("OK");

	    }//end outer while loop
	       
	    System.out.print("Closing All Streams and Sockets...");
	    myReceiver.close();
	    System.out.println("OK");
			
	} catch (IOException e){ System.err.println(e); }
		
    }//end main

}
