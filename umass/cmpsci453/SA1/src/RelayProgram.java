import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.ServerSocket;
import java.net.UnknownHostException;


public class RelayProgram {

    //Port Numbers between Sender and Receiever
    private static final int SENDER_PORT_NUMBER = 6565;
    private static final int RECEIVER_PORT_NUMBER = 6566;

    //Sockets
    private static ServerSocket mySender = null;
    private static Socket connectionSocket = null;
    private static Socket myReceiver = null;

    //Streams
    private static BufferedReader inFromSender = null;
    private static DataOutputStream outToReceiver = null;
    
    public static void main (String args []){

	try {

	    //Setup connection with Sender
	    mySender = new ServerSocket (SENDER_PORT_NUMBER);

	    //Set up Connection to Receiver...
	    System.out.print("Setting up socket connection to Receiver...");
	    myReceiver = new Socket ("localhost", RECEIVER_PORT_NUMBER);
	    System.out.println("OK");

	    //We use a boolean to determine when to end the Relay Program
	    boolean relayDone = false;

	    while (relayDone == false){

		//Wait for Sender Connection...
		System.out.println("-----------------------------------\n");
		System.out.print("Waiting for a sender socket connection...");
		connectionSocket = mySender.accept();
		System.out.println("OK");

		//Set up the appropriate streams.
		System.out.print ("Setting up appropriate streams...");
		inFromSender = new BufferedReader (new InputStreamReader (connectionSocket.getInputStream()));
		outToReceiver = new DataOutputStream (myReceiver.getOutputStream());
		System.out.println("OK");

		//Boolean to evaluate when the sender is finished.
		boolean senderDone = false;

		while (senderDone == false){

		    //Send Message from Sender to Receiver
		    System.out.print("Waiting for Message from Sender to Relay to Receiver...");
		    String message = inFromSender.readLine();
		    outToReceiver.writeBytes(message + '\n');
		    System.out.println("OK");

		    //If it was a CLOSE Message...
		    if(message.equalsIgnoreCase("Close")){
			senderDone = true;
			relayDone = true;
		    }
		}//end inner while loop

		System.out.print("Closing Streams and Sockets for this Connection...");
		inFromSender.close();
		outToReceiver.close();
		connectionSocket.close();
		System.out.println("OK");

	    }//end outer while loop

	    //Closing Streams and Sockets..
	    System.out.print("Closing all Streams and Sockets...");	
	    mySender.close();
	    myReceiver.close();
	    System.out.println("OK");

	} catch (Exception e){ System.err.println(e); }
		
    }

}
