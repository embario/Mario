package hw3;
import java.util.Arrays;


public class BootSector {
    
    private static String OEM = null;
    private static long BytesPerSector = -1;
    private static long SectorsPerCluster = -1;
    private static long MediaDescriptor = -1;
    private static long TotalSectors = -1;
    private static long MftStart = -1;
    private static long MftMirrorStart = -1;
    private static long EntrySize = -1;
    private static long IndexSize = -1;
    private static long SerialNumber;
    private static boolean Signature = false;

    //Methods
    long getBytesPerSector(){ return BytesPerSector;}
    long getSectorsPerCluster(){ return SectorsPerCluster;}
    long getMftStart(){return MftStart;}
    long getEntrySize(){return EntrySize;}
   
    public BootSector(byte[] sector){

	OEM  = new String(Arrays.copyOfRange(sector, 3,11));
	BytesPerSector  = Utils.getUnsigned(sector,11,13);		
	SectorsPerCluster  = Utils.getUnsigned(sector,13,14);	
	MediaDescriptor = Utils.getUnsigned(sector,21,22);
	TotalSectors = Utils.getUnsigned(sector, 40,48);	
	MftStart= Utils.getSignedLong(sector,48,56);
	MftMirrorStart = Utils.getSignedLong(sector,56,64);

	if (sector[64] < 0)
	    EntrySize = (long) Math.pow(2.00,(Math.abs(sector[64])));
	else
	    EntrySize = sector[64] * BytesPerSector * SectorsPerCluster;

	if (sector[68] < 0)
	    IndexSize = (long) Math.pow(2.00,(Math.abs(sector[68])));
	else
	    IndexSize = sector[68]*BytesPerSector * SectorsPerCluster;

	//getUnsigned() returns same value because largest java native type is long (no room for the sign)
	SerialNumber = Utils.getSignedLong(sector,72,80); 
	Signature = ((byte)0x55==sector[510]) && ((byte) 0xAA == sector[511]);
	
	if (Signature == false){
	    System.out.println("ERROR: Signature failure on boot sector.\n");
	    dump();
	    System.exit(-1);
	}
    }
    
    public void dump(){
	System.out.printf("BootSector dump\n");
	System.out.printf("OEM:\t\t\t%s\n",OEM);
	System.out.printf("Bytes per sector:\t%s\n",BytesPerSector);
	System.out.printf("Sectors per cluster:\t%s\n",SectorsPerCluster);
	System.out.printf("Media descriptor:\t%X\n",MediaDescriptor);
	System.out.printf("Total sectors:\t\t%s\n",TotalSectors);
	System.out.printf("MFT start:\t\tcluster %s\n",MftStart);
	System.out.printf("Mirror start:\t\tcluster %s\n",MftMirrorStart);
	System.out.printf("Entry size:\t\t%s bytes\n",EntrySize);
	System.out.printf("Index size:\t\t%s\n",IndexSize);
	System.out.printf("Serial number:\t\t%s\n",Long.toHexString(SerialNumber).toUpperCase());
	System.out.printf("Signature match:\t%S\n",Signature);
    }
}
