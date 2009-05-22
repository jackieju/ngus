package com.ngus.dataengine;

import java.util.HashMap;
import java.util.Iterator;

import com.ns.log.Log;

public class ThreadManager {
	/**
	 * singleton logic
	 */
	static private ThreadManager _instance = null;

	private HashMap<Long, NGUSThread> map= new HashMap<Long, NGUSThread>(); 
	/**
	 * Get instance of DataMgr
	 * 
	 * @return the single instance of DataMgr, using the default config file
	 *         "ACCConfig.xml"
	 * @throws Exception
	 */
	synchronized static public ThreadManager instance() throws Exception {
		if (_instance == null) {

			_instance = new ThreadManager();

			_instance.init();

		}

		return _instance;
	}

	/**
	 * destroy
	 * 
	 * @throws Exception
	 */
	static synchronized public void destroy() throws Throwable {
		// ngusModel.close();
		
		if (_instance != null) {
			
		
			_instance = null;
		}
	}
	
	public void init() throws Exception {
		Log.trace("Init...");
	
		Log.trace("Init ok.");
	}
	
	public void startThread(HashMap params){
		NGUSThread t = new NGUSThread(params);
		t.start();
		this.map.put(new Long(t.getId()), t);	
		Log.trace("new thread started(id="+t.getId()+")");
	}
	
	public void startThread(NGUSThread t){		
		t.start();
		this.map.put(new Long(t.getId()), t);	
		Log.trace("new thread started(id="+t.getId()+")");
	}
	
	public void stopAll(){
		Iterator it = map.values().iterator();
		while(it.hasNext()){
			((NGUSThread)it.next()).stopit();
		}
	}
	
}
