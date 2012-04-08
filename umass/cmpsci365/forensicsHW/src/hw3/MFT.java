package hw3;


import hw3.Attribute.AttributeTypeIdentifier;
import hw3.Attribute.Runlist;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;


public class MFT{

	static RandomAccessFile  image = null; // the disk image we'll be reading in and processing
	static BootSector boot = null; // the processed boot sector
	static Entry mft = null; // the main mft entry; in other words entry 0.
	static long imageOffset = -1; //the offset to the main mft entry (entry 0).
	
	public MFT (RandomAccessFile  inputFile) throws IOException{

		assert(inputFile != null);
		image = inputFile; 
		byte[] sector = new byte[512];
		
		// get the boot sector: 512 bytes at the start of the image
		image.seek(0);
		int bytesRead = image.read(sector, 0, 512);
		if (512 != bytesRead)
		    System.out.println("Couldn't read in 512 bytes of bootsector from image.");
		
		boot = new BootSector(sector);
		//boot.dump();
		
		// get the first MFT entry so that we can read in any other MFT entry
		imageOffset = boot.getMftStart() * boot.getSectorsPerCluster() * boot.getBytesPerSector();
		image.seek(imageOffset);
		byte[] entry = new byte[(int) boot.getEntrySize()];
		bytesRead = image.read(entry, 0, (int)boot.getEntrySize());
		mft = new Entry(entry, imageOffset, 0);
	}
	
	public void getEntry(int entryNum) throws IOException {
		
		System.out.println("MFT Entry " + entryNum);
		byte [] entry = new byte[(int)boot.getEntrySize()];
		
		//First get the $DATA Attribute
		Attribute dataAttr = null;
		for(Attribute a : mft.attributes)
			if(a.getAttributeTypeIdentifier() == AttributeTypeIdentifier.$DATA)
				dataAttr = a;
		
		ArrayList <Runlist> runlists = dataAttr.getRunlists();
		//Keep Iterating until we find the right cluster!
		for(Runlist r : runlists){
			
			System.out.println("MFT.getEntry(): Attempting to find Runlist to find Cluster...");
			System.out.println("Runlist: [" + r.getClusterStart() + ", " + r.getNumClusters() + "]");
		
			//Total Bytes offset to Entry (relative to 0)
			long entryOffset = (entryNum) * boot.getEntrySize();
			//Total Sectors Offset to Entry (relative to 0)
			long sectorOffset = entryOffset/boot.getBytesPerSector();
			//Total Clusters Offset to Entry (relative to 0)
			long clusterOffset = sectorOffset/boot.getSectorsPerCluster();
			//Total Number of Sectors after dividing by clusters
			long sectorRemainder = sectorOffset % boot.getSectorsPerCluster();
			//Cluster # computed by cluster offset and the starting cluster from Runlist
			long clusterNumber = r.getClusterStart() + clusterOffset;
			
			//Total Number of Bytes Per Cluster
			long bytesPerCluster = boot.getBytesPerSector() * boot.getSectorsPerCluster();
			
			System.out.println("Sector Offset: " + sectorOffset);
			System.out.println("Cluster Offset: " + clusterOffset);
			System.out.println("Cluster Number: " + clusterNumber);
			
			if(clusterOffset < r.getNumClusters() && (clusterNumber < (r.getClusterStart() + r.getNumClusters()))){
			
				imageOffset = (clusterNumber * bytesPerCluster) + (sectorRemainder * boot.getBytesPerSector());
				image.seek(imageOffset);
				int bytesRead = image.read(entry, 0, (int)boot.getEntrySize());
				Entry e = new Entry(entry, imageOffset, entryNum);	
				break;
			}	
		}//end For Loop
	}//end getEntry()
	
	
}
