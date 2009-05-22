package com.ngus.dataengine;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;
import com.ngus.config.SystemProperty;
import com.ns.log.Log;

public class CacheWriter extends Thread {
	// public class MCClientPoolEle {
	// MemCachedClient mc = new MemCachedClient();
	// boolean used = false;
	// public MCClientPoolEle(){
	// // compression is enabled by default
	// mc.setCompressEnable(true);
	//
	// // set compression threshhold to 4 KB (default: 15 KB)
	// mc.setCompressThreshold(4096);
	// }
	// }

	// final static int MAX_POOLSIZE = 50;
	// final static int INIT_POOLSIZE = 10;
	// static private ArrayList<MCClientPoolEle> clientPool = new
	// ArrayList<MCClientPoolEle>();
	//	
	static public final String MEMCACHED_POOL_NAME = "ngus-core";

	
	static {
		try {
			String[] serverlist = {}; // = new String[20];// {
										// /*"192.168.1.162:11211"*/ };
			String servers = SystemProperty
					.getProperty("ngus.core.memCached.server");

			if (servers != null) {
				serverlist = servers.split("(;)");
				for (int i = 0; i < serverlist.length; i++) {
					// if (serverlist[i] == null || serverlist.length==0){
					//    			
					// }
					Log.trace("server[" + i + "]: " + serverlist[i]);
				}
				// Integer[] weights = { new Integer(5), new Integer(2) };
				// int initialConnections = 10;
				// int minSpareConnections = 5;
				// int maxSpareConnections = 50;
				// long maxIdleTime = 1000 * 60 * 30; // 30 minutes
				// long maxBusyTime = 1000 * 60 * 5; // 5 minutes
				// long maintThreadSleep = 1000 * 5; // 5 seconds
				// int socketTimeOut = 1000 * 3; // 3 seconds to block on reads
				// int socketConnectTO = 1000 * 3; // 3 seconds to block on
				// initial connections. If 0, then will use blocking connect
				// (default)
				// boolean failover = false; // turn off auto-failover in event
				// of server down
				// boolean nagleAlg = false; // turn off Nagle's algorithm on
				// all sockets in pool

				SockIOPool pool = SockIOPool.getInstance(MEMCACHED_POOL_NAME);
				pool.setServers(serverlist);

				// pool.setWeights( weights );
				// pool.setInitConn( initialConnections );
				// pool.setMinConn( minSpareConnections );
				// pool.setMaxConn( maxSpareConnections );
				// pool.setMaxIdle( maxIdleTime );
				// pool.setMaxBusyTime( maxBusyTime );
				// pool.setMaintSleep( maintThreadSleep );
				// pool.setSocketTO( socketTimeOut );
				// pool.setSocketConnectTO( socketConnectTO );
				// pool.setNagle( nagleAlg );
				// pool.setHashingAlg( SockIOPool.NEW_COMPAT_HASH );
			
				pool.initialize();
				Log.trace("SockIOPool Initialized with server: "
						+ serverlist.toString());
			}else
				Log.error("cannot init memcached for pool "+ MEMCACHED_POOL_NAME + ", because cannot get server list.");
			
		} catch (Exception e) {
			Log.error(e);
		}
	}

	String id;

	ResourceObject ro;

	static public void putROToCache(String id, ResourceObject ro) {
		Log.trace("enter");
		MemCachedClient mc = new MemCachedClient(MEMCACHED_POOL_NAME);
		//mc.setPoolName(MEMCACHED_POOL_NAME);
		// if
		// (!mc.set("111111111111111111111111111111111111111111111111111111111111111111",
		// "haha"))
		// Log.error("put test object to cache failed");
		// else
		// Log.error("put test object to cache OK");
		//		 
		// Log.error("get test object:" +
		// mc.get("111111111111111111111111111111111111111111111111111111111111111111"));
		Log.trace("set object to cache");
		if (!mc.set(id, ro))
			Log.error("put resource object " + id + "(" + ro + ")"
					+ " to cache failed");
	}

	public CacheWriter(String id, ResourceObject ro) {
		this.id = id;
		this.ro = ro;
	}

	public void run() {
		try {
			putROToCache(id, ro);

		} catch (Exception e) {
			Log.trace(e);
		}

	}

	public static void putRDOToCache(String id, ResDesObject rdo) {
		// find ro in cache
		ResourceObject ro = getROFromCache(id);
		try {
			if (ro == null) // not found, get from db
				ro = DataEngine.getROById(id, true);
		} catch (Exception e) {
			Log.error("get ro failed, id=" + id);
			return;
		}
		if (ro == null) // ro no exist any more
		{
			Log.error("get ro failed, id=" + id);
			return;
		}

		// add rdo
		ro.addResDesObject(rdo);

		// update cache
		MemCachedClient mc = new MemCachedClient(MEMCACHED_POOL_NAME);
		//mc.setPoolName(MEMCACHED_POOL_NAME);
		if (!mc.set(id, ro))
			Log.error("put resource object " + id + " to caceh failed");

	}

	static public void updateROInCache(String id) {
		try {
			putROToCache(id, DataEngine.getROById(id, true));
		} catch (Exception e) {
			Log.error(e);
			return;
		}
	}

	static public void updateROInCache(ResourceObject ro) {
		try {
			putROToCache(ro.getResId(), ro);
		} catch (Exception e) {
			Log.error(e);
			return;
		}
	}

	static public void updateRDOInCache(String id, String modelName) {
		try {
			// get resource object
			ResourceObject ro = getROFromCache(id);
			if (ro == null) {
				ro = DataEngine.getROById(id, true);
			}

			// remove rdo
			ro.removeRDO(modelName);

			// get rdo
			ResDesObject rdo = DataEngine.getRDO(id, modelName);
			ro.addResDesObject(rdo);

			putROToCache(id, ro);
		} catch (Exception e) {
			Log.error(e);
			return;
		}
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	static public ResourceObject getROFromCache(String id) {
		MemCachedClient mc = new MemCachedClient(MEMCACHED_POOL_NAME);
		//mc.setPoolName(MEMCACHED_POOL_NAME);
		// compression is enabled by default
		mc.setCompressEnable(true);

		// set compression threshhold to 4 KB (default: 15 KB)
		mc.setCompressThreshold(4096);

		// turn on storing primitive types as a string representation
		// Should not do this in most cases.
		// mc.setPrimitiveAsString(true);

		ResourceObject ro = (ResourceObject) mc.get(id);

		return ro;

	}

	static public ResDesObject getRDOFromCache(String id, String model) {
		ResourceObject ro = getROFromCache(id);
		if (ro == null)
			return null;
		List<ResDesObject> list = ro.getResDesObjects(model);
		if (list == null || list.size() == 0)
			return null;
		return list.get(0);
	}

	static public String printStatus() {
		String ret = "";
		MemCachedClient mc = new MemCachedClient(MEMCACHED_POOL_NAME);
		//mc.setPoolName(MEMCACHED_POOL_NAME);
		Map stats = mc.stats();
		if (stats == null)
			return "Get status failed";

		Iterator it = stats.keySet().iterator();
		while (it.hasNext()) {
			String name = (String) it.next();
			String value = stats.get(name).toString();
			ret += "name=" + name + " value=" + value + "<br>";
		}
		return ret;

	}

}