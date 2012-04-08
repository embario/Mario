package hw3;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.Date;

public class Utils {

	static public Date filetimeToDate(long filetime){
		long diff=116444736000000000L; //the difference between 1601 and 1970
	    //we divide to convert to milliseconds
	    long epoch= (filetime-diff)/10000;
	    //System.out.println(epoch);
		Date result = new Date(epoch);
		return result;
		
	}

	static public short getSignedShort(byte[] s,int start,int end){
		assert((end-start ) == 2):end-start + " bytes passed to getShort()";
		return ByteBuffer.wrap(Arrays.copyOfRange(s, start,end)).order(ByteOrder.LITTLE_ENDIAN).getShort();	
	}
	static public int getSignedInt(byte[] s,int start,int end){
		assert((end-start ) == 4):end-start + " bytes passed to getInt()";
		return ByteBuffer.wrap(Arrays.copyOfRange(s, start,end)).order(ByteOrder.LITTLE_ENDIAN).getInt();	
	}

	static public long getSignedLong(byte[] s,int start,int end){
		assert((end-start ) == 8):end-start + " bytes passed to getShort()";
		return ByteBuffer.wrap(Arrays.copyOfRange(s, start,end)).order(ByteOrder.LITTLE_ENDIAN).getLong();	
	}

	static public long getSigned(byte[] s,int start,int end){
		assert(end-start <= 8): end-start+ " bytes passed to getSigned(); too many";
		assert( end> start): "end is not strictly larger than start same value.";

		byte [] padded = {0,0,0,0,0,0,0,0};
		byte [] negative = {(byte)0xFF, (byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF};
		if ((s[end-1] & 0x80 ) >> 7 ==1){
			padded = negative;
		}
		System.arraycopy(s, start, padded, 0, end-start);
		return ByteBuffer.wrap(padded).order(ByteOrder.LITTLE_ENDIAN).getLong();	
	}
	
	static public long getUnsigned(byte[] s,int start,int end){
		assert((end-start ) <= 8):end-start + " bytes passed to getShort()";
		//assume little endian!!
		long result=0;	
		for (int i =end-1 ; i >=start; i--){
			//System.out.printf("%02X ", s [i]);
			result = result << 8;
			result +=(0x000000FF & (byte)s[i]);
		}
		//System.out.println();
		return  result;
	}

	/*
	 * Return the hexdump of a byte block as a string
	 * Separated out so that it can be used in later assignments
	 * 
	 * @param offset: number of bytes to start counting from
	 */
	static public String hexdumpBlock (byte data[], int offset){
		String output = ""; // stores the returned output
		//process one line at a time.
		for (int i=0;i<data.length;i+=16){ 
			output += String.format("%05x: ",offset+i); // offset since start of block
			String column = "";  // temp value of the column output
			int length = Math.min(data.length-i,16); // length of the current line, which might be short

			// dump one byte at a time, skipping space in the middle of the column
			for (int j=i; j<(i+length); j+=1){
				column += String.format("%02X ",data[j]); 
				if (8 == j-i) {
					column += "  ";
				}
			}
			// ensure we are at least 52 characters wide, left justified
			// 52 = 3* LINE_LENGTH  (no way to make that pretty in java without obfuscating intention; leaving it)
			output += String.format("%-52s |",column);

			// now print the ascii values when possible
			for (int j=i; j<(i+length); j+=1){
				if ((data[j]>31) & (data[j]<127)) {
					output+=String.format("%c",data[j]);
				} else {
					output +=String.format(".");
				}
			}
			output += String.format("|\n");
		}
		return output;
	}


}

