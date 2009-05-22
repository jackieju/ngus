package com.ngus.resengine;

import com.ngus.dataengine.ResourceObject;

public interface IResEngine {
	
	public static final String STORAGETYPE_DB = "db";
	public static final String STORAGETYPE_JCR = "jcr";
	public static final String STORAGETYPE_WEBDAV = "webDav";
	public static final String STORAGETYPE_FS = "fs";
	
	public static final int STORAGEINTTYPE_DB = 0;
	public static final int STORAGEINTTYPE_JCR = 1;
	public static final int STORAGEINTTYPE_WEBDAV = 2;
	public static final int STORAGEINTTYPE_FS = 3;
	
//	public InputStream get(String id) throws Exception;
	public ResourceObject getResource(String id, boolean bValue) throws Exception;
//	public ResourceObject getJCRResource(ResourceId id) throws Exception ;
	//public void set(String id, InputStream res, boolean bSync) throws Exception;
	public void setResource(ResourceObject ro, boolean bSync) throws Exception;
	public void delete(String id) throws Exception;
	
}
