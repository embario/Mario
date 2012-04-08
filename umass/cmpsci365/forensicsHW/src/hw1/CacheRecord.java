package hw1;

import java.util.Date;

/**
 * Object Placeholder for Important Cache Record information including (but not limited to):
 * 
 * 1) Data Location, Size, and File
 * 2) Metadata Location, Size, and File
 * 3) Metadata slack and hex dump
 * 
 * @author mbarrenecheajr
 *
 */
public class CacheRecord {
	
	
	/*** Members ***/
	private int cacheRecordId_ = 0;
	private boolean dataEntryParsed_ = false;
	private boolean metadataEntryParsed_ = false;
	
	//Data
	private CacheFile dataCacheFile_ = null;
	private String dataHexString_ = null;
	private String dataBinaryString_ = null;
	private Integer dataByteInteger_ = null;
	private int dataBlockNumber_ = 0;
	private int dataBlockCount_ = 0;
	private int metadataBlockCount_ = 0;
	private long dataFileOffset_ = 0;
	private int dataObjectSize_ = 0;
	private String data_ = null;
	
	//Metadata
	private CacheFile metadataCacheFile_ = null;
	private String metadataHexString_ = null;
	private String metadataBinaryString_ = null;
	private Integer metadataByteInteger_ = null;
	private int metadataBlockNumber_ = 0;
	private int metadataFileOffset_ = 0;
	private int metadataFetchCount_ = 0;
	private Date metadataLastFetched_ = null;
	private Date metadataLastModified_ = null;
	private Date metadataExpirationTime_ = null;
	private int metadataSize_ = 0;
	private int metadataUrlSize_ = 0;
	private String metadataUrl_ = null;
	private String metadata_ = null;
	private String metadataSlack_ = null;
	private boolean metadataParsed_ = false;
	
	/*** Getters ***/
	protected int getCacheRecordId(){ return this.cacheRecordId_;}
	protected boolean getDataEntryParsed(){ return this.dataEntryParsed_;}
	protected boolean getMetadataEntryParsed(){ return this.metadataEntryParsed_;}
	protected boolean getMetadataParsed(){ return this.metadataParsed_;}
	
	//Data
	protected CacheFile getDataCacheFile(){ return this.dataCacheFile_;}
	protected String getDataHexString() {return dataHexString_;}
	protected String getDataBinaryString() {return dataBinaryString_;}
	protected int getDataBlockNumber() {return dataBlockNumber_;}
	protected int getDataBlockCount() {return dataBlockCount_;}
	protected long getDataFileOffset() {return dataFileOffset_;}
	protected Integer getDataByteInteger(){ return dataByteInteger_;}
	protected int getDataObjectSize(){ return this.dataObjectSize_;}
	protected String getData(){ return this.data_;}
	
	//Metadata
	protected CacheFile getMetadataCacheFile(){ return this.metadataCacheFile_;}
	protected String getMetadataHexString() {return metadataHexString_;}
	protected String getMetadataBinaryString() {return metadataBinaryString_;}
	protected int getMetadataBlockNumber() {return metadataBlockNumber_;}
	protected int getMetadataBlockCount() {return metadataBlockCount_;}	
	protected int getMetadataFileOffset() {return metadataFileOffset_;}
	protected Integer getMetadataByteInteger(){ return metadataByteInteger_;}
	protected int getMetadataFetchCount(){ return this.metadataFetchCount_;}
	protected Date getMetadataLastFetched(){ return this.metadataLastFetched_;}
	protected Date getMetadataLastModified(){ return this.metadataLastModified_;}
	protected Date getMetadataExpirationTime(){ return this.metadataExpirationTime_;}
	protected int getMetadataSize(){ return this.metadataSize_; }
	protected int getMetadataUrlSize(){ return this.metadataUrlSize_;}
	protected String getMetadataUrl(){ return this.metadataUrl_;}
	protected String getMetadata(){ return this.metadata_;}
	protected String getMetadataSlack(){ return this.metadataSlack_;}
	
	/*** Setters ****/
	protected void setDataEntryParsed(boolean b){ this.dataEntryParsed_ = b;}
	protected void setMetadataEntryParsed(boolean b){ this.metadataEntryParsed_ = b;}
	protected void setMetadataParsed(boolean b){ this.metadataParsed_ = b;}
	
	//Data
	protected void setDataCacheFile(CacheFile f){ this.dataCacheFile_ = f;}
	protected void setDataHexString(String dataHexString) {this.dataHexString_ = dataHexString;}
	protected void setDataBinaryString(String dataBinaryString) {this.dataBinaryString_ = dataBinaryString;}
	protected void setDataBlockNumber(int dataBlockNumber_) {this.dataBlockNumber_ = dataBlockNumber_;}
	protected void setDataBlockCount(int dataBlockCount_) {this.dataBlockCount_ = dataBlockCount_;}
	protected void setDataFileOffset(long offset){ this.dataFileOffset_ = offset;}
	protected void setDataByteInteger(Integer byteint){ this.dataByteInteger_ = byteint;}
	protected void setDataObjectSize(int i){ this.dataObjectSize_ = i;}
	protected void setData(String s){ this.data_ = s;}
	
	//Metadata
	protected void setMetadataCacheFile(CacheFile f){ this.metadataCacheFile_ = f;}
	protected void setMetadataBinaryString(String metadataBinaryString) {this.metadataBinaryString_ = metadataBinaryString;}
	protected void setMetadataHexString(String metadataHexString) {this.metadataHexString_ = metadataHexString;}
	protected void setMetadataBlockNumber(int metadataBlockNumber_) {this.metadataBlockNumber_ = metadataBlockNumber_;}
	protected void setMetadataBlockCount(int metadataBlockCount_) {this.metadataBlockCount_ = metadataBlockCount_;}
	protected void setMetadataFileOffset(int metadataOffset){ this.metadataFileOffset_ = metadataOffset;}
	protected void setMetadataByteInteger(Integer byteint){ this.metadataByteInteger_ = byteint;}
	protected void setMetadataFetchCount(int i){ this.metadataFetchCount_ = i;}
	protected void setMetadataLastFetched(Date d){ this.metadataLastFetched_ = d;}
	protected void setMetadataLastModified(Date d){ this.metadataLastModified_ = d;}
	protected void setMetadataExpirationTime(Date d){ this.metadataExpirationTime_ = d;}
	protected void setMetadataSize(int i){ this.metadataSize_ = i;}
	protected void setMetadataUrlSize(int i){ this.metadataUrlSize_ = i;}
	protected void setMetadataUrl(String s){ this.metadataUrl_ = s;}
	protected void setMetadata(String s){ this.metadata_ = s;}
	protected void setMetadataSlack (String s){ this.metadataSlack_ = s;}
	
	
	
	//Constructor
	private CacheRecord(int id){
		this.cacheRecordId_ = id;
	}
	
	/**
	 * Factory Method for creating new CacheRecords.
	 * @return
	 */
	public static CacheRecord createCacheRecord(int id){return new CacheRecord(id);}
	
}
