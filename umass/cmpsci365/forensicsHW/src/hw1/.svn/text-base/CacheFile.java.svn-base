package hw1;

import java.io.File;

/**
 * CacheFile Enum improves clarity and efficiency when determining cache file
 * sizes and filenames.
 * 
 * @author mbarrenecheajr
 */
public enum CacheFile {

	CACHE_MAP(128, "_CACHE_MAP_"),
	CACHE_001(256, "_CACHE_001_"), 
	CACHE_002(1024, "_CACHE_002_"), 
	CACHE_003(4096, "_CACHE_003_"), 
	EXTERNAL(-1, "EXTERNAL"); //Entirely Mutable

	//Members
	
	/** Member for holding cache block size. **/
	private int cacheBlockSize_;
	/** Member for holding cache file size (as specified by the File object) **/
	private long cacheFileSize_;
	/** Member for holding the cache file name. **/
	private String cacheFileName_;
	/** Member for holding the cache File Java object. **/
	private File cacheFileObject_;
	

	// Getters
	protected int getCacheBlockSize() {return this.cacheBlockSize_;}
	protected long getCacheFileSize() {return this.cacheFileSize_;}
	protected String getCacheFileName() {return this.cacheFileName_;}
	protected File getCacheFileObject(){ return this.cacheFileObject_;}
	

	// Setters
	protected void setCacheFileObject(File f){this.cacheFileObject_ = f;}
	protected void setCacheFileSize(long l){this.cacheFileSize_ = l;}
	protected boolean setExternalFileSize(int size) {
		if (this == CacheFile.EXTERNAL) {
			this.cacheFileSize_ = size;
			return true;
		}
		return false;
	}
	protected boolean setExternalFileName(String name){
		if (this == CacheFile.EXTERNAL){
			this.cacheFileName_ = name;
			return true;
		}
		return false;
	}
	
	/**
	 * This function determines if the underlying
	 * CacheFile is CACHE 001, 002, or 003.
	 * @return
	 */
	protected boolean isADefinedCacheFile(){ 
		return (this == CACHE_001 || this == CACHE_002 || this == CACHE_003);}

	// Cannot be instantiated!
	private CacheFile(int blocksize, String filename) {
		this.cacheBlockSize_ = blocksize;
		this.cacheFileName_ = filename;
	}
}