import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

/** Mario Barrenechea <p>
 * 	10/13/2010 <p>
 * 	CMPSCI453: SA #1 <p>
 * 
 * 	ClientProgram.java:
 * 
 * 	This code is based on the Client code presented
 * 	in class by Prof. Jim Kurose. It has been modified 
 * 	to complete the socket programming assignment.
 * 
 * @author mbarrene
 *
 */
public class SenderProgram {
	
    public static final int PORT_NUMBER = 6565;
    public static Socket mySender = null;
    public static BufferedReader inFromSystem = null;
    public static DataOutputStream outToRelay = null;
    
    public static void main (String args []){
		
	try {
	    
	    //Set up the Client Socket. "Localhost" means the underlying machine.
	    mySender = new Socket ("localhost", PORT_NUMBER);
	    
	    //Set up I/O Streams
	    inFromSystem = new BufferedReader(new InputStreamReader(System.in));
	    outToRelay = new DataOutputStream(mySender.getOutputStream());
	    
	    boolean done = false;
	    while (done == false){
		
		System.out.println("-----------------------------------\n");
		System.out.println("SENDER: Send a Message to the Receiver via a Relay Program. Type 'Close' to terminate the Sender .");
		System.out.print(">: ");
		String message = inFromSystem.readLine();

		System.out.print ("Sending Message to Server at Port " + PORT_NUMBER + "...");
		outToRelay.writeBytes(message + '\n');
		System.out.println("OK");

		//If it was a CLOSE Message...
		if (message.equalsIgnoreCase("close"))
		    done = true;
	    }
			
	    //Important to Close all streams and sockets.
	    System.out.print ("Closing all streams and sockets...");
	    inFromSystem.close();
	    outToRelay.close();
	    mySender.close();
	    System.out.println("OK");
	}
	catch (UnknownHostException u){ System.err.println(u);}
	catch (IOException e) {System.err.println(e);}
	
    }//end main
}
