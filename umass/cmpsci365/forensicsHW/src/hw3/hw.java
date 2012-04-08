package hw3;



import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;


public class hw {
	
	static RandomAccessFile  inputFile = null; //file handle to the NTFS image.
	static int entry =-1;  				   // the MFT entry that we'll parse
	
	public static void usage(String s){
		System.out.println(s);
		System.out.printf("Expecting two arguments:\njava hw3 [filename] [entryNumber].\n" );
		System.out.println("where\tfilename: an NTFS image to open\n\tnumber:   the mft entry to parse.");
		System.exit(0);
	}

	public static void processArgs(String[] args){
		if (args.length !=2){
			usage("Too few arguments given.");
		}
		try {
			inputFile = new RandomAccessFile (args[0],"r");
		} catch (FileNotFoundException e) {
			usage("File not found.");
		}
		
		entry = Integer.valueOf(args[1]);
	}
	public static void main(String[] args) {

		processArgs(args); 
		assert(entry>=0); // make sure we got a valid entry on the command line.
		
		//process the MFT on this image
		MFT mft = null;
		try {
			
		    mft= new MFT(inputFile);
			// then grab the second argument and parse that entry
			// doing something like this:
			mft.getEntry(entry);
			
		} catch (IOException e) {
		    // TODO Auto-generated catch block
		    usage("IO exception when reading image file.");
		}



	}


}
