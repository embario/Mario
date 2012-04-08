package hw3;

import java.util.ArrayList;

/**
 * 
 * Attribute.java
 * 
 * Encapsulates all of the details for maintaining information about an MFT Attribute.
 * 
 * @author mbarrenecheajr
 *
 */
public class Attribute {
	
	/**
	 * Describes a type identifier for Attributes.
	 * @author mbarrenecheajr
	 */
	enum AttributeTypeIdentifier {
		
		$STANDARD_INFORMATION,
		$ATTRIBUTE_LIST,
		$FILE_NAME,
		$VOLUME_VERSION,
		$SECURITY_DESCRIPTOR,
		$VOLUME_NAME,
		$VOLUME_INFORMATION,
		$DATA,
		$INDEX_ROOT,
		OTHER
	}
	
	/**
	 * Easy Class to use to keep details of a runlist.
	 * 
	 * @author mbarrenecheajr
	 *
	 */
	public class Runlist{
		
		private long clusterStart = -1;
		private long numClusters = -1;
		
		private Runlist(long clustS, long numClust){
			this.clusterStart = clustS;
			this.numClusters = numClust;
		}
		
		protected long getClusterStart(){ return this.clusterStart;}
		protected long getNumClusters(){ return this.numClusters;}
		
		public String toString(){ return "[" + this.clusterStart + ", " + this.numClusters + "]";}
	}
	
	/** Attribute Header Information **/
	private AttributeTypeIdentifier attrTypeId = null;
	private long attrId = -1;
	private long attrLength = -1;
	private long residentFlag = -1;
	private long attrNameLength = -1;
	private long attrNameOffset = -1;
	private long flags = -1;
	
	/** Attribute Content Information **/
	private long attrContentSize = -1;
	private long attrContentOffset = -1;
	private ArrayList<Runlist> runlists = null;
	
	
	//Setters
	protected void setAttributeIdentifier(long id){ this.attrId = id;}
	protected void setAttributeTypeId(AttributeTypeIdentifier ati){ this.attrTypeId = ati;}
	protected void setAttributeLength(long length){ this.attrLength = length;}
	protected void setResidentFlag(long flag){ this.residentFlag = flag;}
	protected void setAttributeNameLength(long length){ this.attrNameLength = length;}
	protected void setAttributeNameOffset(long offset){ this.attrNameOffset = offset;}
	protected void setFlags(long f){ this.flags = f;}
	protected void setAttributeContentSize (long number){ this.attrContentSize = number;}
	protected void setAttributeContentOffset (long number){ this.attrContentOffset = number;}
	
	//Getters
	protected long getAttributeIdentifier(){ return this.attrId;}
	protected AttributeTypeIdentifier getAttributeTypeIdentifier(){ return this.attrTypeId;}
	protected long getAttrLength(){ return this.attrLength;}
	protected long getResidentFlag(){ return this.residentFlag;}
	protected long getAttributeNameLength(){ return this.attrNameLength;}
	protected long getAttributeNameOffset(){ return this.attrNameOffset;}
	protected long getFlags(){ return this.flags;}
	protected long getAttributeContentSize (){ return this.attrContentSize;}
	protected long getAttributeContentOffset(){ return this.attrContentOffset;}
	protected ArrayList<Runlist> getRunlists(){ return this.runlists;}
	
	//Simple Constructor for our purposes.
	public Attribute(long attrTypeId){
		
		switch((int)attrTypeId){
		
		case 16: 
			this.attrTypeId = AttributeTypeIdentifier.$STANDARD_INFORMATION; 
			this.attrId = 16;
			break;
		
		case 32:
			this.attrTypeId = AttributeTypeIdentifier.$ATTRIBUTE_LIST;
			this.attrId = 32;
			break;
		case 48:
			this.attrTypeId = AttributeTypeIdentifier.$FILE_NAME; 
			this.attrId = 48;
			break;
		case 64:
			this.attrTypeId = AttributeTypeIdentifier.$VOLUME_VERSION;
			this.attrId = 64;
			break;
		case 80:
			this.attrTypeId = AttributeTypeIdentifier.$SECURITY_DESCRIPTOR;
			this.attrId = 80;
			break;
		case 96:
			this.attrTypeId = AttributeTypeIdentifier.$VOLUME_NAME;
			this.attrId = 96;
			break;
		case 112:
			this.attrTypeId = AttributeTypeIdentifier.$VOLUME_INFORMATION;
			this.attrId = 112;
			break;
		case 128:
			this.attrTypeId = AttributeTypeIdentifier.$DATA; 
			this.attrId = 128;
			break;
		case 144:
			this.attrTypeId = AttributeTypeIdentifier.$INDEX_ROOT;
			this.attrId = 144;
			break;
			
		default: this.attrTypeId = AttributeTypeIdentifier.OTHER;
			break;
		}
		
		this.runlists = new ArrayList <Runlist> ();
	}
	
	/**
	 * Factory Method for creating Runlist objects. Also adds to this Attribute's runlist List.
	 * 
	 * @param clusterStart
	 * @param numCluster
	 * @return
	 */
	public Runlist createRunlist(long clusterStart, long numCluster){
		Runlist newRunlist = new Runlist(clusterStart, numCluster);
		this.runlists.add(newRunlist);
		return newRunlist;
	}
	
	public String toString(){ return "" + this.attrTypeId;}

}
