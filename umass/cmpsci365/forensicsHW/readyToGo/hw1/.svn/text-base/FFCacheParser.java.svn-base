import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Date;

/**
 * 
 * This class is responsible for parsing the Firefox cache by reading the cachemap, determining the size,
 * location, and metadata for each record in the same directory, and finally printing out the results in
 * a readable fashion.
 * 
 * @author mbarrenecheajr
 *
 */

public class FFCacheParser {
	
	/******** FFCacheParser class definition *******/
	
	private int recordCount_ = 0;
	private File outDirectory_ = null;
	
	/** Cache Files **/
	private CacheFile cachemap_ = null;
	private CacheFile cache001_ = null;
	private CacheFile cache002_ = null;
	private CacheFile cache003_ = null;

	/** Cache File Setters **/
	private void setCacheMap(CacheFile f){ this.cachemap_ = f;}
	private void setCache001(CacheFile f){ this.cache001_ = f;}
	private void setCache002(CacheFile f){ this.cache002_ = f;}
	private void setCache003(CacheFile f){ this.cache003_ = f;}
	
	/** Cache File Getters **/
	private CacheFile getCacheMap(){ return this.cachemap_;}
	private CacheFile getCache001(){ return this.cache001_;}
	private CacheFile getCache002(){ return this.cache002_;}
	private CacheFile getCache003(){ return this.cache003_;}
	
	//Other
	private File getOutDirectory(){ return this.outDirectory_;}

	private FFCacheParser(){
		
		this.outDirectory_ = new File("out");
		this.outDirectory_.mkdir();
	}
	
	public static void main (String args []){
		
		FFCacheParser parser = new FFCacheParser();
		
		//First, check the Program Argument and setup the Parser
		if(parser.checkArgs(args) == false)
			System.exit(1);
		else
			parser.doParse();
	}
	
	
	/**doParse():
	 * 
	 * This function is the high-level construct for doing several things in order as follows:
	 * <p>
	 * 1) Read in one record at a time, parsing both data and metadata entries and storing relevant information as appropriate.
	 * <p>
	 * 2) Parse the Cache Files for both data and metadata from a record, the first 8 bytes from the data entry, and the Metadata dump from the Metadata entry.
	 * <p>
	 * 3) Report and Write to files in a subdirectory called "out"
	 */
	private void doParse() {
		
		try{
			
			FileInputStream fs = new FileInputStream(this.getCacheMap().getCacheFileObject());
			
			//Skip the first 276 bytes first
			fs.skip(276);
			
			//While there are more bits and bytes to read in (CacheMap)
			while(fs.available() > 0){
				
				CacheRecord record = CacheRecord.createCacheRecord(this.recordCount_);
				
				//Create a byte array of 128 bits so we can read in relevant information
				byte nextBytes [] = new byte [(128/Byte.SIZE)];
				fs.read(nextBytes);
				
				/** Parse the Data and the Metadata Entries to obtain important information 
				about the Web objects. Also, check if these entries were parsed correctly **/
				record.setDataEntryParsed(parseDataEntry(record, nextBytes, 8));
				record.setMetadataEntryParsed(parseMetadataEntry(record, nextBytes, 12));
				
				if(record.getDataEntryParsed() == false && record.getMetadataEntryParsed() == false){
					
					this.recordCount_++;
					continue;
				}
				
				//Parse the first 8 bytes out of the cache file
				if((record.getDataCacheFile() != null) && (record.getDataCacheFile() != CacheFile.EXTERNAL) && (record.getDataEntryParsed() == true))
					record.setData(this.parseDataFromCacheFile(record));
				
				//Parse the Metadata Header
				if((record.getMetadataCacheFile() != null) && (record.getMetadataCacheFile() != CacheFile.EXTERNAL) && (record.getMetadataEntryParsed() == true))
					record.setMetadataParsed(this.parseMetadataFromCacheFile(record));
				
				//Finally, write everything to the record file under the subdirectory "out"
				this.writeDataToFile(record);
				this.recordCount_++;
			}
			
			fs.close();
		} catch(IOException io){io.printStackTrace();}
		
	}//end doParse();
	
	private boolean parseMetadataFromCacheFile(CacheRecord record) throws IOException{
		
		Hexdump.dumpHex(record.getMetadataCacheFile().getCacheFileObject().getAbsolutePath(), 8, 8, (long)record.getMetadataFileOffset());
		
		FileInputStream fs = new FileInputStream(record.getMetadataCacheFile().getCacheFileObject());
		fs.skip(record.getMetadataFileOffset());
		
		//Calculate the Limit to what we are reading from the metadata entry in this cache file
		int limit = (record.getMetadataBlockCount() * record.getMetadataCacheFile().getCacheBlockSize());
		
		//Counts the Number of Bytes up to important points in parsing
		int byteCount = 0;
		byte nextBytes [] = new byte [4];
		
		/**Reading in Constant (00 01 00 0C) **/
		fs.read(nextBytes);
		byteCount += nextBytes.length;
		
		//Variables
		Integer byteInt = ByteBuffer.wrap(nextBytes).getInt();
		String binaryString = Integer.toBinaryString(byteInt);
		
		//Verifying Constant
		if(byteInt != 0x0001000C){
			//System.out.println("ERROR: Header Constant is inconsistent. Skipping...");
			return false;
		}
		
		/** Reading in location **/
		fs.read(nextBytes);
		byteCount += nextBytes.length;
		
		/** Reading in Fetch Count **/
		fs.read(nextBytes);
		byteCount += nextBytes.length;
		byteInt = ByteBuffer.wrap(nextBytes).getInt();
		binaryString = Integer.toBinaryString(byteInt);
		record.setMetadataFetchCount(Integer.parseInt(binaryString, 2));
		
		/** Reading in Last Fetched Date **/
		fs.read(nextBytes);
		byteCount += nextBytes.length;
		long byteLong = ByteBuffer.wrap(nextBytes).getInt();
		Date lastFetched = new Date(byteLong);
		record.setMetadataLastFetched(lastFetched);
		
		/** Reading in Last Modified Date **/
		fs.read(nextBytes);
		byteCount += nextBytes.length;
		byteLong = ByteBuffer.wrap(nextBytes).getInt();
		Date lastModified = new Date(byteLong);
		record.setMetadataLastModified(lastModified);
		
		/** Reading in Expiration Time **/
		fs.read(nextBytes);
		byteCount += nextBytes.length;
		byteLong = ByteBuffer.wrap(nextBytes).getInt();
		Date expiration = new Date(byteLong);
		record.setMetadataExpirationTime(expiration);
		
		/** Reading in Data Object Size **/
		fs.read(nextBytes);
		byteCount += nextBytes.length;
		byteInt = ByteBuffer.wrap(nextBytes).getInt();
		binaryString = Integer.toBinaryString(byteInt);
		int dataObjectSize = Integer.parseInt(binaryString, 2);
		record.setDataObjectSize(dataObjectSize);
		
		/** Reading in Metadata URL Size **/
		fs.read(nextBytes);
		byteCount += nextBytes.length;
		byteInt = ByteBuffer.wrap(nextBytes).getInt();
		binaryString = Integer.toBinaryString(byteInt);
		int metadataUrlSize = Integer.parseInt(binaryString, 2);
		record.setMetadataUrlSize(metadataUrlSize);
		
		/** Reading in Metadata Size **/
		fs.read(nextBytes);
		byteCount += nextBytes.length;
		byteInt = ByteBuffer.wrap(nextBytes).getInt();
		binaryString = Integer.toBinaryString(byteInt);
		int metadataSize = Integer.parseInt(binaryString, 2);
		record.setMetadataSize(metadataSize);
		
		/** Now, let's read in the Metadata URL **/
		nextBytes = new byte [metadataUrlSize];
		fs.read(nextBytes);
		byteCount += nextBytes.length;
		String metadataUrl = new String(nextBytes);
		record.setMetadataUrl(metadataUrl);
		
		/** Grab the Metadata itself **/
		String metadata = Hexdump.dumpStrings(record.getMetadataCacheFile().getCacheFileObject().getAbsolutePath(), 
				metadataSize, 64, (record.getMetadataFileOffset() + byteCount));
		record.setMetadata(metadata);
		byteCount += metadataSize;
		
		//This should be a reasonable number (i.e. non-negative)
		int slackSize = limit - byteCount;
		assert(slackSize > 0 == true);
		
		/** Finally, print out the slack data as a hex dump **/
		String metadataSlack = Hexdump.dumpHex(record.getMetadataCacheFile().getCacheFileObject().getAbsolutePath(),
				slackSize, 8, (record.getMetadataFileOffset() + byteCount));
		
		record.setMetadataSlack(metadataSlack);
		
		fs.close();
		return true;
		
	}

	/** Get the First 8 Bytes from the Hex Dump of the Data Cache File **/
	private String parseDataFromCacheFile(CacheRecord record) throws IOException{
		
		return Hexdump.dumpHex(record.getDataCacheFile().getCacheFileObject().getAbsolutePath(), 8, 8, (long)record.getDataFileOffset());
	}

	/**
	 * This function will modify the current CacheRecord for MetaData information.
	 * @param record
	 */
	private boolean parseMetadataEntry(CacheRecord record, byte bytes [], int index){
		
		//Grab the 32 bits that represents the metadata or data entry for this 128-bit record.
		Integer byteInt = ByteBuffer.wrap(bytes, index, 4).getInt();
		String hexString = Integer.toHexString(byteInt);
		String binaryString = Integer.toBinaryString(byteInt);
		
		record.setMetadataByteInteger(byteInt);
		record.setMetadataHexString(hexString);
		record.setMetadataBinaryString(binaryString);
		
		//Error Checking
		if(binaryString.length() != 32 || hexString.length() != 8){
			//System.out.println("Record #" + this.recordCount_ + " is inconsistent with Hex and Binary data (Metadata Entry). Skipping...");
			return false;
		}
		
		//Trim the Binary String and determine the cache file pertaining to this 128-bit record.
		String temp = binaryString.substring(2,4);
		int cacheFile = Integer.parseInt(temp, 2);
		
		CacheFile cf = null;
		switch(cacheFile){
		
		case 0: cf = CacheFile.EXTERNAL; break;
		case 1: cf = CacheFile.CACHE_001; break;
		case 2: cf = CacheFile.CACHE_002; break;
		case 3: cf = CacheFile.CACHE_003; break;
		default: System.out.println("ERROR: Unrecognized Cache File Number: FFCacheParser.doParse().parseMetadataEntry()");
		
		}
		
		record.setMetadataCacheFile(cf);
		
		//Then, trim the Binary String and determine the number of cache blocks that this 128-bit record occupies.
		temp = binaryString.substring(6,8);
		int numCacheBlocks = 1 + Integer.parseInt(temp, 2);
		record.setMetadataBlockCount(numCacheBlocks);

		//If not an external file, trim the Binary String and determine the cache block number of this 128-bit record.
		int cacheBlockNum = 0;
		if(cf != CacheFile.EXTERNAL){
			
			temp = binaryString.substring(8, 32);
			cacheBlockNum = Integer.parseInt(temp, 2);
			record.setMetadataBlockNumber(cacheBlockNum);
			

			
			//Finally, determine the offset in the cache file wherein the data and metadata for this 128-bit record resides.
			int offset = cf.getCacheBlockSize() * cacheBlockNum + 4096; 
			record.setMetadataFileOffset(offset);
			
			if(offset > 0 && offset < cf.getCacheFileSize())
				return true;
			else{
				//System.out.println("ERROR: Offset is greater than the size of the Cache File (Data Entry). Skipping...");
				return false;
			}
			
		}
		
		//External Files: No more work to be done; finish parsing.
		else
			return true;
	}

	/**
	 * This function will modify the current CacheRecord for Data Information.
	 * @param record
	 */
	private boolean parseDataEntry(CacheRecord record, byte bytes [], int index){

		//Grab the 32 bits that represent the metadata or data entry for this 128-bit record.
		Integer byteInt = ByteBuffer.wrap(bytes, index, 4).getInt();
		String hexString = Integer.toHexString(byteInt);
		String binaryString = Integer.toBinaryString(byteInt);
		
		record.setDataByteInteger(byteInt);
		record.setDataHexString(hexString);
		record.setDataBinaryString(binaryString);
		
		//Error Checking
		if(binaryString.length() != 32 || hexString.length() != 8){
			//System.out.println("Record #" + this.recordCount_ + " is inconsistent with Hex and Binary data (Data Entry). Skipping...");
			return false;
		}
		
		//Trim the Binary String and determine the cache file pertaining to this 128-bit record.
		String temp = binaryString.substring(2,4);
		int cacheFile = Integer.parseInt(temp, 2);
		
		CacheFile cf = null;
		switch(cacheFile){
		
		case 0: cf = CacheFile.EXTERNAL; break;
		case 1: cf = CacheFile.CACHE_001; break;
		case 2: cf = CacheFile.CACHE_002; break;
		case 3: cf = CacheFile.CACHE_003; break;
		default: System.out.println("ERROR: Unrecognized Cache File Number: FFCacheParser.doParse().parseDataEntry()");
		return false;
		}
		
		record.setDataCacheFile(cf);
		
		//Then, trim the Binary String and determine the number of cache blocks that this 128-bit record occupies.
		temp = binaryString.substring(6,8);
		int numCacheBlocks = 1 + Integer.parseInt(temp, 2);
		record.setDataBlockCount(numCacheBlocks);

		//If not an external file, trim the Binary String and determine the cache block number of this 128-bit record.
		int cacheBlockNum = 0;
		if(cf != CacheFile.EXTERNAL){
			
			temp = binaryString.substring(8, 32);
			cacheBlockNum = Integer.parseInt(temp, 2);
			record.setDataBlockNumber(cacheBlockNum);
			
			//Finally, determine the offset in the cache file wherein the data and metadata for this 128-bit record resides.
			long offset = cf.getCacheBlockSize() * cacheBlockNum + 4096; 
			record.setDataFileOffset(offset);
			
			if(offset > 0 && offset < cf.getCacheFileSize())
				return true;
			else{
				//System.out.println("ERROR: Offset is greater than the size of the Cache File (Data Entry). Skipping...");
				return false;
			}
			
		}
		
		//External Files: No more work to be done; finish parsing.
		else			
			return true;
	}
	
	
	protected boolean writeDataToFile(CacheRecord record) throws IOException{
		
		String filename = String.format("%03d", record.getCacheRecordId());
		File file = new File(filename);
		file.createNewFile();
		
		if(file.exists() == false){
			System.err.println("File Failed to Create.");
			return false;
		}
		
		FileWriter fw = new FileWriter(file);
		CacheFile datafile = record.getDataCacheFile();
		CacheFile metadatafile = record.getMetadataCacheFile();
		
		/*********************** Writing to this File ****************************/
		
		/* ******************** Data and Metadata Entry Parsing ******************/
		
		fw.write("---------------------------------------- \n");
		fw.write("Record #: " + record.getCacheRecordId() + "\n");
	
		fw.write("Data:\n");
		fw.write("\t Hex String: " + record.getDataHexString() + "\n");
		fw.write("\t Binary String: " + record.getDataBinaryString() + "\n");
		
		if(datafile != null && record.getDataEntryParsed() == true){
			
			fw.write("\t Cache File: " + datafile.getCacheFileName() + "\n");
		
			if(datafile != CacheFile.EXTERNAL){
				
				fw.write("\t Number of Cache Blocks: " + record.getDataBlockCount() + " " + 
						datafile.getCacheBlockSize() + "-byte Block(s)\n");
				fw.write("\t Offset Cachce Block #: " + record.getDataBlockNumber() + "\n");
				fw.write("\t Offset in Bytes: " + record.getDataFileOffset() + " bytes\n");
			}
		}
			
		fw.write("Metadata:\n");
		fw.write("\t Hex String: " + record.getMetadataHexString() + "\n");
		fw.write("\t Binary String: " + record.getMetadataBinaryString() + "\n");
		
		if(metadatafile != null && record.getMetadataEntryParsed() == true){
			
			fw.write("\t Cache File: " + metadatafile.getCacheFileName() + "\n");
			
			if(metadatafile != CacheFile.EXTERNAL){
				
				fw.write("\t Number of Cache Blocks: " + record.getMetadataBlockCount() + " " +
						metadatafile.getCacheBlockSize() + "-byte Block(s)\n");
				
				fw.write("\t Offset Cache Block #: " + record.getMetadataBlockNumber() + "\n");
				fw.write("\t Offset in Bytes: " + record.getMetadataFileOffset() + " bytes\n\n");
			}
		}
			
		
		/****************** Data and Metadata Header Parsing **************************/
		
		if(metadatafile != null && metadatafile != CacheFile.EXTERNAL && record.getMetadataParsed() == true){
			
			fw.write("Stored Data Header: \n");
			fw.write(record.getData() + "\n");
			fw.write("\nMetadata:\n");
			fw.write("\t Fetch Count: " + record.getMetadataFetchCount() + "\n");
			fw.write("\t Last Fetch: " + record.getMetadataLastFetched() + "\n");
			fw.write("\t Last Modified: " + record.getMetadataLastModified() + "\n");
			fw.write("\t Expiration Time: " + record.getMetadataExpirationTime() + "\n");
			fw.write("\t Data Size: " + record.getDataObjectSize() + " Byte(s)\n");
			fw.write("\t URL Size: " + record.getMetadataUrlSize() + " Byte(s)\n");
			fw.write("\t Metadata Size: " + record.getMetadataSize() + " Byte(s)\n");
			fw.write("\t URL: " + record.getMetadataUrl() + "\n\n");
			fw.write("Metadata (From Server): \n");
			fw.write(record.getMetadata() + "\n");
			
			fw.write("Remaining Slack Data: \n");
			fw.write(record.getMetadataSlack() + "\n");
		}
		else
			fw.write("\n\n\t Record # " + record.getCacheRecordId() + " has inconsistent data and could not be fully parsed.\n");

		fw.close();		
		file.renameTo(new File(this.getOutDirectory(), file.getName()));
		return true;
	}


	/**
	 * checkArgs():
	 * 
	 * This function accepts the input program argument(s) and determines the
	 * directory path where the Firefox cache files live. If successful, the
	 * program will be ready for parsing these cache files.
	 * 
	 * @param args
	 * @return
	 */
	public boolean checkArgs(String args[]) {
		
	    if(args.length < 1)
		return false;

		try{
			
			// Directory we're looking for
			File directory = new File(args[0]);
			
			if (directory.isDirectory() == true) {

				System.out.println("Found the directory path: " + directory.getAbsolutePath());
				
				File cachefile = new File(directory.getAbsolutePath() + "/" + CacheFile.CACHE_MAP.getCacheFileName());
				this.setCacheMap(CacheFile.CACHE_MAP);
				this.getCacheMap().setCacheFileObject(cachefile);
				this.getCacheMap().setCacheFileSize(cachefile.length());
				
				cachefile = new File(directory.getAbsolutePath() + "/"+ CacheFile.CACHE_001.getCacheFileName());
				this.setCache001(CacheFile.CACHE_001);
				this.getCache001().setCacheFileObject(cachefile);
				this.getCache001().setCacheFileSize(cachefile.length());
				
				cachefile = new File(directory.getAbsolutePath() + "/"+ CacheFile.CACHE_002.getCacheFileName());
				this.setCache002(CacheFile.CACHE_002);
				this.getCache002().setCacheFileObject(cachefile);
				this.getCache002().setCacheFileSize(cachefile.length());
				
				cachefile = new File(directory.getAbsolutePath() + "/"+ CacheFile.CACHE_003.getCacheFileName());
				this.setCache003(CacheFile.CACHE_003);
				this.getCache003().setCacheFileObject(cachefile);
				this.getCache003().setCacheFileSize(cachefile.length());

				if (this.getCache001().getCacheFileObject().isFile() && this.getCache002().getCacheFileObject().isFile() && this.getCache003().getCacheFileObject().isFile())
					System.out.println("Discovered Important Cache Files in Directory Path...Ready for Parsing.");
				else
					throw new FileNotFoundException("ERROR: Unable to find important Firefox Cache Files in Directory Path.");
			}
			else
				throw new FileNotFoundException("ERROR: Argument is not a Directory.");
			
		} catch (FileNotFoundException ex){
			ex.printStackTrace();
			return false;
		}

		return true;
	}
	



}
