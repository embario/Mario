package hw3;

import java.util.ArrayList;
import java.util.Arrays;

// Here's the start of 
public class Entry {

	private int entryNum = -1;
	private String sig = null;
	private long imageOffset = -1;
	private int entrySize = -1;
	private long fixupOffset = -1;
	private long fixupEntryCount = -1;
	private byte[] fixupArray = null;
	private String slack = null;
	private long logSequenceNumber = -1;
	private long sequenceValue = -1;
	private long linkCount = -1;
	private long offsetToFirstAttr = -1;
	private long inUseFlag = -1;
	private long entryUsedSize = -1;
	private long entryAllocSize = -1;
	ArrayList<Attribute> attributes = null;

	//Setters
	private void setEntryNum(int entryNum){ this.entryNum = entryNum;}
	protected void setEntrySize(int size) {this.entrySize = size;}
	protected void setImageOffset(long off) {this.imageOffset = off;}

	// Getters
	protected long getEntryNum(){ return this.entryNum;}
	protected long getImageOffset() {return this.imageOffset;}
	protected String getSignature() {return this.sig;}
	protected int getEntrySize() {return this.entrySize;}
	protected long getFixupOffset() {return this.fixupOffset;}
	protected long getFixupEntryCount() {return this.fixupEntryCount;}
	protected String getSlack() {return this.slack;}
	protected byte[] getFixupArray() {return this.fixupArray;}
	protected long getLogSequenceNumber() {return this.logSequenceNumber;}
	protected long getSequenceValue() {return this.sequenceValue;}
	protected long getLinkCount() {return this.linkCount;}
	protected long getOffsetToFirstAttribute() {return this.offsetToFirstAttr;}
	protected long getInUseFlag() {return this.inUseFlag;}
	protected long getEntryUsedSize() {return this.entryUsedSize;}
	protected long getEntryAllocatedSize() {return this.entryAllocSize;}

	public Entry(byte[] data, long imageOff, int entryNum) {

		this.setImageOffset(imageOff);
		this.setEntryNum(entryNum);
		this.setEntrySize(data.length);
		//System.out.println("IMAGE OFFSET: " + imageOff);
		
		sig = new String(Arrays.copyOfRange(data, 0, 4));
		if (!sig.contentEquals("FILE")) {
			System.out.printf("Bad Entry Signature: {%s} - At Entry.java (line 55)\n\n", sig);
			System.exit(-1);
		}

		System.out.println( "\n" + Utils.hexdumpBlock(data, 0));
		
		// Capture Important Header Information
		this.attributes = new ArrayList<Attribute>();
		this.fixupOffset = Utils.getUnsigned(data, 4, 6);
		this.fixupEntryCount = Utils.getUnsigned(data, 6, 8);
		this.logSequenceNumber = Utils.getUnsigned(data, 8, 16);
		this.sequenceValue = Utils.getUnsigned(data, 16, 18);
		this.linkCount = Utils.getUnsigned(data, 18, 20);
		this.offsetToFirstAttr = Utils.getUnsigned(data, 20, 22);
		this.inUseFlag = Utils.getUnsigned(data, 22, 24);
		this.entryUsedSize = Utils.getUnsigned(data, 24, 28);
		this.entryAllocSize = Utils.getUnsigned(data, 28, 32);
		
		//Need this variable to loop over attributes that exist within the MFT Entry.
		int nextAttributeID = (int)Utils.getUnsigned(data, 40, 42);
		int attrCount = 0;

		// Process the Fixup Array
		this.fixupArray = new byte[(int) this.getFixupEntryCount() * 2];
		for (int i = 0; i < this.fixupArray.length; i += 2) {
			this.fixupArray[i] = data[(int) this.getFixupOffset() + i];
			this.fixupArray[i + 1] = data[(int) this.getFixupOffset() + i + 1];
		}

		// Must replace fixup values at the end of both sectors (510-511 & 1022-1023)
		data[510] = (byte) this.fixupArray[2];
		data[511] = (byte) this.fixupArray[3];
		data[1022] = (byte) this.fixupArray[4];
		data[1023] = (byte) this.fixupArray[5];
		
		
		/** Print General MFT Entry Header Info **/
		System.out.println("\nEntry: " + this.getEntryNum() + "\n----------");
		System.out.printf("Signature: \t\t\t%s\n", sig);
		System.out.printf("Entry Number: \t\t\t%d\n", this.getEntryNum());
		System.out.printf("Sequence Number: \t\t%s\n", this.getSequenceValue());
		System.out.printf("$Logfile Sequence Number: \t%s\n", this.getLogSequenceNumber());
		System.out.printf("Link Count: \t\t\t%s\n", this.getLinkCount());
		System.out.printf("Entry Total Size: \t\t%d\n", this.getEntrySize());
		System.out.printf("Entry Used Size: \t\t%d\n", this.getEntryUsedSize());
		
		
		int inUseFlag = (int)this.getInUseFlag();
		if ((inUseFlag & 0x01) == 0x01)
			System.out.println("Allocated File");
		if ((inUseFlag & 0x02) == 0x02)
			System.out.println("Directory File (Un-Allocated");
		if ((inUseFlag & 0x03) == 0x03)
			System.out.println("Directory File (Allocated)");
		
		// Now Concentrate on Parsing Attributes in Entry (STANDARD_INFORMATION,
		// FILE_NAME, DATA) and dumping them.
		long attrOffset = this.getOffsetToFirstAttribute();
		
		while ((attrOffset + 16 < this.getEntryUsedSize()) && (attrCount < nextAttributeID)){
		//while (this.doneParsingAttributes() == false){
			if (Utils.getUnsigned(data, (int) attrOffset, (int) attrOffset + 4) == 0xffffffff)
				break;

			long attributeType = Utils.getUnsigned(data, (int) attrOffset,(int) attrOffset + 4);
			Attribute attr = new Attribute(attributeType);
			attr.setAttributeLength(Utils.getUnsigned(data,(int) attrOffset + 4, (int) attrOffset + 8));
			attr.setResidentFlag(Utils.getUnsigned(data, (int) attrOffset + 8,(int) attrOffset + 10));
			attr.setAttributeNameLength(Utils.getUnsigned(data,(int) attrOffset + 9, (int) attrOffset + 10));
			attr.setAttributeNameOffset(Utils.getUnsigned(data,(int) attrOffset + 10, (int) attrOffset + 12));
			attr.setFlags(Utils.getUnsigned(data, (int) attrOffset + 12,(int) attrOffset + 14));
			attr.setAttributeIdentifier(Utils.getUnsigned(data,(int) attrOffset + 14, (int) attrOffset + 16));
			
			System.out.println("\nAttribute: " + attr);
			System.out.println("Attribute ID: " + attr.getAttributeIdentifier());
			System.out.println("Length: " + attr.getAttrLength());
			
			if(attr.getResidentFlag() == 0x0)
				System.out.println("Resident (0x0)");
			else
				System.out.println("Non-Resident (0x1)");
			
			switch((int)attr.getFlags()){
			
				case 0x0001: System.out.println("Compressed (0x0001)");
				case 0x0004: System.out.println("Encrypted  (0x0004)");
				case 0x0008: System.out.println("Sparse     (0x0008)");
			}

			/** Parse the Attribute Content here (SPECIFIC to Attribute!) **/
			switch (attr.getAttributeTypeIdentifier()) {

			case $STANDARD_INFORMATION:

				// System.out.println("CONTENT_OFFSET in STNDRD_INFO Parsing" +
				// contentOffset);
				assert (attr.getResidentFlag() == 0x0);

				// Parse More Attribute Header Information
				attr.setAttributeContentSize(Utils.getUnsigned(data,(int) attrOffset + 16, (int) attrOffset + 20));
				attr.setAttributeContentOffset(Utils.getUnsigned(data,(int) attrOffset + 20, (int) attrOffset + 22));

				// Now the ContentOffset variable is our 0 position to the
				// actual attribute content.
				int contentOffset = (int) (attrOffset + attr.getAttributeContentOffset());

				// Parse for Extra Information
				System.out.println("Creation Time: " + Utils.filetimeToDate(Utils.getUnsigned(data, contentOffset, contentOffset + 8)));
				System.out.println("File Altered Time: " + Utils.filetimeToDate(Utils.getUnsigned(data, contentOffset + 8, contentOffset + 16)));
				System.out.println("MFT Altered Time: " + Utils.filetimeToDate(Utils.getUnsigned(data, contentOffset + 16, contentOffset + 24)));
				System.out.println("File Accessed Time: " + Utils.filetimeToDate(Utils.getUnsigned(data, contentOffset + 24, contentOffset + 32)));
				
				long stdinfoFlags = Utils.getUnsigned(data, contentOffset + 32, contentOffset + 36);
				this.printAttributeFlags(stdinfoFlags);
				
				System.out.println("Maximum Number of Versions: " + Utils.getUnsigned(data, contentOffset + 36, contentOffset + 40));
				System.out.println("Version Number: " + Utils.getUnsigned(data, contentOffset + 40, contentOffset + 44));
				System.out.println("Class ID: " + Utils.getUnsigned(data, contentOffset + 44, contentOffset + 48));
				System.out.println("Owner ID: " + Utils.getUnsigned(data, contentOffset + 48, contentOffset + 52));
				System.out.println("Security ID: " + Utils.getUnsigned(data, contentOffset + 52, contentOffset + 56));
				System.out.println("Quota Charged: " + Utils.getUnsigned(data, contentOffset + 56, contentOffset + 64));
				System.out.println("Updated Sequence Number (USN): " + Utils.getUnsigned(data, contentOffset + 64, contentOffset + 72));
				break;

			case $FILE_NAME:

				assert (attr.getResidentFlag() == 0x0);

				// Parse More Attribute Header Information
				attr.setAttributeContentSize(Utils.getUnsigned(data,(int) attrOffset + 16, (int) attrOffset + 20));
				attr.setAttributeContentOffset(Utils.getUnsigned(data,(int) attrOffset + 20, (int) attrOffset + 22));
				
				System.out.println("Attribute Content Size: " + attr.getAttributeContentSize());
				System.out.println("Attribute Content Offset: " + attr.getAttributeContentOffset());
				
				// Now the ContentOffset variable is our 0 position to the
				// actual attribute content.
				contentOffset = (int) (attrOffset + attr.getAttributeContentOffset());

				// Parse for Extra Information
				System.out.println("File Reference of Parent Directory: " + Utils.getUnsigned(data, contentOffset, contentOffset + 8));
				System.out.println("File Creation Time: " + Utils.filetimeToDate(Utils.getUnsigned(data, contentOffset + 8, contentOffset + 16)));
				System.out.println("File Modification Time: " + Utils.filetimeToDate(Utils.getUnsigned(data, contentOffset + 16, contentOffset + 24)));
				System.out.println("MFT Modification Time: " + Utils.filetimeToDate(Utils.getUnsigned(data, contentOffset + 24, contentOffset + 32)));
				System.out.println("File Access Time: " + Utils.filetimeToDate(Utils.getUnsigned(data, contentOffset + 32, contentOffset + 40)));
				System.out.println("Allocated Size of File: " + Utils.getUnsigned(data, contentOffset + 40, contentOffset + 48));
				System.out.println("Real Size of File: " + Utils.getUnsigned(data, contentOffset + 48, contentOffset + 56));
				
				//Print Flags
				long fileNameFlags = Utils.getUnsigned(data, contentOffset + 56, contentOffset + 60);
				printAttributeFlags(fileNameFlags);
				
				System.out.println("Reparse Value: " + Utils.getUnsigned(data, contentOffset + 60, contentOffset + 64));
				long nameLength = Utils.getUnsigned(data, contentOffset + 64, contentOffset + 65);
				System.out.println("Length of Name: " + nameLength);
				String name = new String(Arrays.copyOfRange(data, contentOffset + 66, contentOffset + 66 + ((int)nameLength *2) + 1));
				System.out.println("Name: " + name);
				break;

			case $DATA:

				// If Non-Resident
				if (attr.getResidentFlag() == 0x01) {

					System.out.println("Starting VCN Of Runlist: " + Utils.getUnsigned(data, (int)attrOffset + 16, (int)attrOffset + 24));
					System.out.println("Ending VCN Of Runlist: " + Utils.getUnsigned(data, (int)attrOffset + 24, (int)attrOffset + 32));
					long offsetToRunList = Utils.getUnsigned(data, (int)attrOffset + 32, (int)attrOffset + 34);
					System.out.println("Offset to Runlist: " + offsetToRunList);
					System.out.println("Compression Unit Size: " + Utils.getUnsigned(data, (int)attrOffset + 34, (int)attrOffset + 36));
					System.out.println("Unused: " + Utils.getUnsigned(data, (int)attrOffset + 36, (int)attrOffset + 40));
					System.out.println("Allocated Size of Attribute Content: " + Utils.getUnsigned(data, (int)attrOffset + 40, (int)attrOffset + 48));
					System.out.println("Actual Size of Attribute Content: " + Utils.getUnsigned(data, (int)attrOffset + 48, (int)attrOffset + 56));

					long runlistOffset = attrOffset + offsetToRunList;
					long lengthByte = Utils.getUnsigned(data, (int) runlistOffset, (int) runlistOffset + 1);
					long offsetField = 0;
					
					System.out.println("Parsing the Runlist:");
					System.out.println("--------------------");
					System.out.println();
					
					for(byte b: Arrays.copyOfRange(data, (int)runlistOffset, (int)runlistOffset + 17))
						System.out.printf("%02X ", b);
					
					/** Parsing the Runlist ***/
					// If we run into 0x00, we stop parsing for runlists.
					while (lengthByte != 0x00) {

						int lowNibble = (int) lengthByte % 16;
						int highNibble = (int) lengthByte / 16;
						long runlistField = Utils.getSigned(data,(int) runlistOffset + 1, (int) (runlistOffset + 1) + lowNibble);
						long thisOffsetField = Utils.getSigned(data,(int) (runlistOffset + 1) + lowNibble,(int) (runlistOffset + 1) + lowNibble + highNibble);
						
						//Keep Adding to the real offsetField.
						offsetField += thisOffsetField;
						
						System.out.println("\nLength of Offset Field: " + highNibble);
						System.out.println("Length of Runlist Field: " + lowNibble);
						System.out.println("Runlist Field: " + runlistField);
						System.out.println("Offset Field: " + thisOffsetField);
						System.out.println("Adding to the Previous Offset Field yields: " + offsetField);
						System.out.println("Clusters [" + offsetField + " - " + (offsetField + runlistField - 1) + "]");
						
						//Keep this runlist and save it for later.
						attr.createRunlist(offsetField, runlistField);
						
						// Move the Offset pointer after every iteration.
						runlistOffset = runlistOffset + 1 + lowNibble + highNibble;
						lengthByte = Utils.getSigned(data, (int) runlistOffset,(int) runlistOffset + 1);
					}

				}// end "Non-Resident"
				
				//If Resident
				else if (attr.getResidentFlag() == 0x0){
					
					//Parse More Attribute Header Information
					attr.setAttributeContentSize(Utils.getUnsigned(data,(int) attrOffset + 16, (int) attrOffset + 20));
					attr.setAttributeContentOffset(Utils.getUnsigned(data,(int) attrOffset + 20, (int) attrOffset + 22));
					System.out.println("Attribute Content Size: " + attr.getAttributeContentSize());
					System.out.println("Attribute Content Offset: " + attr.getAttributeContentOffset());
				}

				break;
			}

			// We need to maintain the growing offset to the Next Attribute to Parse.
			attrOffset = attrOffset + attr.getAttrLength();
			attrCount++;
			this.attributes.add(attr);
			System.out.println();

		}//end While Loop
		
		this.slack = new String(Arrays.copyOfRange(data, (int)this.getEntryUsedSize(), this.getEntrySize() - 1));
		System.out.println("Slack Space:");
		System.out.println(this.getSlack() + "\n\n");
	}

	/**
	 * Function invoked to print off attribute flags (STANDARD_INFO, FILE_NAME)
	 * @param flags
	 */
	public void printAttributeFlags(long flags){
	
		System.out.println("Flags:");
		if((flags & 0x0001) == 0x0001)
			System.out.println("\tRead Only (0x0001) ");
		if((flags & 0x0002) == 0x0002)
			System.out.println("\tHidden (0x0002)");
		if((flags & 0x0004) == 0x0004)
			System.out.println("\tSystem (0x0004)");
		if((flags & 0x0020) == 0x0020)
			System.out.println("\tArchive (0x0020)");
		if((flags & 0x0040) == 0x0040)
			System.out.println("\tDevice (0x0040)");
		if((flags & 0x0080) == 0x0080)
			System.out.println("\tNormal (0x0080)");
		if((flags & 0x0100) == 0x0100)
			System.out.println("\tTemporary (0x0100)");
		if((flags & 0x0200) == 0x0200)
			System.out.println("\tSparse File (0x0200)");
		if((flags & 0x0400) == 0x0400)
			System.out.println("\tReparse Point (0x0400)");
		if((flags & 0x0800) == 0x0800)
			System.out.println("\tCompressed (0x0800)");
		if((flags & 0x1000) == 0x1000)
			System.out.println("\tOffline (0x1000)");
		if((flags & 0x2000) == 0x2000)
			System.out.println("\tContent is not being Indexed for Faster Searches (0x2000)");
		if((flags & 0x4000) == 0x4000)
			System.out.println("\tEncrypted (0x4000)");
		System.out.println();
	}
}
