package com.ngus.um;

import java.util.Iterator;
import java.util.Map;

import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;

import com.ngus.um.dbobject.Session;
import com.ns.log.Log;

public class CachedSession extends Thread {
//	public class MCClientPoolEle {
//		MemCachedClient mc = new MemCachedClient();
//		boolean used = false;
//		public MCClientPoolEle(){
//			  // compression is enabled by default    
//	        mc.setCompressEnable(true);
//
//	        // set compression threshhold to 4 KB (default: 15 KB)  
//	        mc.setCompressThreshold(4096);
//		}
//	}
	
//	final static int  MAX_POOLSIZE = 50;
//	final static int INIT_POOLSIZE = 10;
//	static private ArrayList<MCClientPoolEle> clientPool = new ArrayList<MCClientPoolEle>();
//	
	static {
		
    }
	
	static public final String MEMCACHED_POOL_NAME = "umj";
	String sid;
	Session session;
	static public void putSessionToCache(String sid, Session s){
		Log.trace("umj", "enter");
		 MemCachedClient mc = new MemCachedClient(MEMCACHED_POOL_NAME);   
		// mc.setPoolName(MEMCACHED_POOL_NAME);
		 
//		 if (!mc.set("111111111111111111111111111111111111111111111111111111111111111111", "haha"))
//	    	 Log.error("put test object  to cache failed"); 
//		 else 
//			 Log.error("put test object  to cache OK");
//		 
//	     Log.error("get test object:" + mc.get("111111111111111111111111111111111111111111111111111111111111111111")); 
		 Log.trace("umj", "set session to cache");
	     if (!mc.set(sid, s))
	    	 Log.error("put resource object " + sid + "("+ s +")" + " to cache failed"); 
	}
	
	public CachedSession(String sid, Session s) {
		this.sid = sid;
		this.session = s;
	}

	public void run() {
		try {
			putSessionToCache(sid, session);

		} catch (Exception e) {
			Log.trace("umj", e);
		}

	}
	
	

	static public void deleteSession(String id){
		MemCachedClient mc = new MemCachedClient(MEMCACHED_POOL_NAME);
		// mc.setPoolName(MEMCACHED_POOL_NAME);
        // compression is enabled by default    
        mc.setCompressEnable(true);

        // set compression threshhold to 4 KB (default: 15 KB)  
        mc.setCompressThreshold(4096);

        // turn on storing primitive types as a string representation
        // Should not do this in most cases.    
        // mc.setPrimitiveAsString(true);
        
        
        boolean ret = mc.delete(id);
        Log.trace("umj", "delete return " + ret);
        
        return ;
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	static public Session getSessionFromCache(String id){
		MemCachedClient mc = new MemCachedClient(MEMCACHED_POOL_NAME);
		// mc.setPoolName(MEMCACHED_POOL_NAME);
        // compression is enabled by default    
        mc.setCompressEnable(true);

        // set compression threshhold to 4 KB (default: 15 KB)  
        mc.setCompressThreshold(4096);

        // turn on storing primitive types as a string representation
        // Should not do this in most cases.    
        // mc.setPrimitiveAsString(true);
        
        
        Session ro = (Session)mc.get(id);
        
        return ro;

	}
	
	
	static public String printStatus(){
		String ret = "";
		MemCachedClient mc = new MemCachedClient(MEMCACHED_POOL_NAME);
		// mc.setPoolName(MEMCACHED_POOL_NAME);
        Map stats = mc.stats();
        if (stats == null)
        	return "Get status failed";
        
        Iterator it = stats.keySet().iterator();
        while (it.hasNext()){
        	String name = (String)it.next();
        	String value = stats.get(name).toString();
        	ret += "name="+name+" value="+value+"<br>";
        }
        return ret;

	}
	
}