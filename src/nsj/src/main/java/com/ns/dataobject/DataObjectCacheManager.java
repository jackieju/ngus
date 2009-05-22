/*
 * Created on Aug 10, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.ns.dataobject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DataObjectCacheManager {
	/*
	 * singleton implemenation
	 */
	static DataObjectCacheManager _inst = null;
	static DataObjectCacheManager instance(){
		if (_inst == null){
			_inst = new DataObjectCacheManager();
		}
		return _inst;
	}
	
	static void destroy(){
	}
	
	protected DataObjectCacheManager(){
		
	}
	
	
	String configFile = "dataengine.xml";
	 String jdbcDriver = "";
	 String url = "";
	 String user = "";
	 String pwd = "";

	
	private HashMap dbs = new HashMap();
	private HashMap caches = new HashMap();
	
	void setConfigFile(String config_file){
		configFile = config_file; 
	}
	
	void setDBParam(String jdbcDriver, String url, String user, String pwd){
		jdbcDriver = jdbcDriver;
		url = url;
		user = user;
		pwd = pwd;
	}
	
	void init(String configFile) throws Exception{
		// read config File
		
		// create factory		
		DataObjectFactory factory = new DataObjectFactory(jdbcDriver, url, user, pwd);
		
		// load app data model definition
		factory.loadFromXML(configFile);
		
		// create data object cache
		Collection list = factory.getAllObjDesc();
		Iterator it = list.iterator();
		while (it.hasNext()){
			DataObjectDesc dod = (DataObjectDesc)it.next();
			// get key
			ArrayList keys = dod.getKeyAttrs();
			String[] key = new String[keys.size()];
			for (int i = 0;i < keys.size(); i++){
				key[i] = (String)keys.get(i);
			}			
			DataObjectCache doc = new DataObjectCache(dod.name(), 10, key, new DefaultDOFetcher(dod.name(), factory), 10000);
		}
	}
	
	public static void main(String[] args) {
	}
}
