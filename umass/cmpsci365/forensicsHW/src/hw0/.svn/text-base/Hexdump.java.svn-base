package hw0;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Mario Barrenechea CMPSCI 365: Digital Forensics
 * 
 * Hexdump:
 * 
 * This program mirrors the UNIX command "hexdump" which takes in a file and
 * dumps its hex representation on the screen. Additionally, the --string
 * argument can be added to provide similar output as the UNIX command
 * "strings".
 * 
 * First, a HexDump Object is created to maintain the command line arguments, and
 * then the proper function is called by determining whether the arguments call
 * for a string or a hex dump.
 * 
 * @author mbarrenecheajr
 */

public class Hexdump {

	// Members
	private String m_filename = null;
	private boolean m_stringOutput = false;

	// Getters
	public String getFilename() {return this.m_filename;}
	public boolean getStringOutput() {return this.m_stringOutput;}

	// Setters
	public void setFilename(String fn) {this.m_filename = fn;}
	public void setStringOutput(boolean so) {this.m_stringOutput = so;}

	
	/*** MAIN METHOD ****/
	public static void main(String args[]) {

		Hexdump hd = new Hexdump();

		// Determine correctness of program argument(s).
		if (hd.checkArgs(args) == true) {

			//Either a HexDump or a StringDump
			if (hd.getStringOutput() == true)
				Hexdump.dumpStrings(hd.getFilename());
			else
				Hexdump.dumpHex(hd.getFilename());
		}
	}

	/**
	 * dump()
	 * <p>
	 * This function parses the file with the filename parameter and correctly
	 * prints out three columns: 1) The number of bytes starting from the
	 * beginning of the file in hex, 2) the byte-for-byte hex representation in
	 * the file, and 3) the actual ASCII representation of each character (or
	 * '.' if NA).
	 */
	private static void dumpHex(String filename){
		
		File file = new File(filename);
		try{
			
		 	FileInputStream fs = new FileInputStream(file);
		 	int count = 0;
		 	
		 	while(fs.available() > 0){
		 		
		 		byte nextBytes [] = new byte [16];
		 		fs.read(nextBytes, 0, 16);
		 		
		 		//Get the length of the bytes array (tells us where we currently are in the file)
		 		count += nextBytes.length;
		 		
		 		//Column #1: Count of Bytes in Hex
		 		System.out.printf("%04X:  ", count);
		 		
		 		//Column #2: Print Hex Values of 16 Bytes Each
		 		for(int i = 0; i < nextBytes.length; i++){
		 			
		 			if(i == 7)
		 				System.out.printf("%02x  ", nextBytes[i]);
		 			else
		 				System.out.printf("%02x ", nextBytes[i]);
		 		}
		 			
		 		//Column #3: Print ASCII Characters
		 		System.out.print("  |");
		 		for(byte b : nextBytes){
		 			
		 			if(b >= 32 && b < 127)
		 				System.out.printf("%c",(char)b);
		 			else
		 				System.out.printf("%c", '.');
		 		}
		 		System.out.println("|");
		 		
		 	}//end loop
		 	
		 	fs.close();
		 	
		}catch (FileNotFoundException e){
			e.printStackTrace();
			
		}catch (IOException e){
			e.printStackTrace();
		}
	}

	/**dumpStrings();
	 * <p>
	 * This function iterates through the hex dump, finds big-enough strings 
	 * of characters that exist, and then prints them.
	 */
	private static void dumpStrings(String filename){
		
		File file = new File(filename);
		try{
			
		 	FileInputStream fs = new FileInputStream(file);
		 	
		 	//Keep Track of Bytes pieced together to form strings.
		 	String byteString = "";
		 	
		 	while(fs.available() > 0){
		 		
		 		byte nextBytes [] = new byte [64];
		 		fs.read(nextBytes, 0, 64);
		 		
		 		/*** Iterate through the bytes and determine unicode characters
		 		and consecutive characters that will make up a proper string ***/
		 		
		 		int unicodeBytePair = 0;
		 		for(int i = 0; i < nextBytes.length; i++){
		 			
		 			byte b = nextBytes[i];
		 			
		 			if(b >= 32 && b < 127){
		 				
		 				unicodeBytePair = 1;
		 				byteString += (char)b;
		 				
		 			}else if(b == 00 || b == 0){
		 				
	 					//Reset the Counter to Ignore the 
	 					//second Byte in a Unicode Character
		 				if(unicodeBytePair == 1)
		 					unicodeBytePair = 0;
		 				else
		 					byteString = "";
		 				
		 			}else if (byteString.length() >= 4){ 
		 				
		 				System.out.println(byteString);
		 				byteString = "";
		 				unicodeBytePair = 0;
		 			}
		 			
		 		}//end for loop
		 	}//end loop
		 	
		}catch (FileNotFoundException e){
			e.printStackTrace();
			
		}catch (IOException e){
			e.printStackTrace();
		}
	}
	

	/**
	 * checkArgs()
	 * <p>
	 * This method is for parsing command line arguments and properly feeding
	 * them to the Hexdump object so that the hex representation is sent as
	 * output (and maybe the strings output).
	 * 
	 * @param args
	 * @return
	 */
	private boolean checkArgs(String args[]) {

		boolean done = false;
		String filename;

		// For each argument coming in...
		for (int i = 0; i < args.length; i++) {

			// If this String is the file flag and there is still
			// an argument to parse (hopefully the filename)...
			if (args[i].equals("--file") && i < args.length - 1) {

				filename = args[i + 1];

				File file = new File(filename);
				if (file.exists() == false)
					return false;

				this.setFilename(filename);
				done = true;
			}

			else if (args[i].equals("--strings"))
				this.setStringOutput(true);

		}// end loop

		if (done == true)
			return true;
		else
			return false;
	}

}// end HexDump class
