package com.ngus.dataengine;

import java.io.File;
import java.io.FileWriter;

import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;
import com.ngus.config.SystemProperty;
import com.ns.log.Log;

public class test {
	static public final String MEMCACHED_POOL_NAME = "ngus-core";
	static {
		Log.setCache(false, 0,0);
		try {
			String[] serverlist = new String[] { "192.168.3.162:11211",
					"192.168.3.163:11211",";" };
			SockIOPool pool = SockIOPool.getInstance(MEMCACHED_POOL_NAME);
			pool.setServers(serverlist);
			pool.initialize();

			Log.trace("SockIOPool Initialized with server: "
					+ serverlist.toString());
		} catch (Exception e) {
			Log.error(e);
		}
	}

	String id;

	ResourceObject ro;

	static public void putROToCache(String poolName, String id, Object ro) {
		Log.trace("enter");
		MemCachedClient mc = new MemCachedClient();
	//	mc.setPoolName(poolName);
		Log.trace("set object to cache");
		if (!mc.set(id, ro))
			Log.error("put resource object " + id + "(" + ro + ")"
					+ " to cache failed");
		Log.trace("leave");
	}

	static public Object getROFromCache(String id) {
		MemCachedClient mc = new MemCachedClient();
		//	mc.setPoolName(MEMCACHED_POOL_NAME);
		// compression is enabled by default
		mc.setCompressEnable(true);

		// set compression threshhold to 4 KB (default: 15 KB)
		mc.setCompressThreshold(4096);

		// turn on storing primitive types as a string representation
		// Should not do this in most cases.
		// mc.setPrimitiveAsString(true);

		Object ro = mc.get(id);

		return ro;

	}

	static void testMC() {
		for (int i = 0; i< 20; i++){
		test.putROToCache(MEMCACHED_POOL_NAME, "test", ""+i);
		System.out.println("return " + test.getROFromCache("test"));
		}

	}

	void testEncoding() throws Exception {
		String s = "<table>用户名大幅度撒谎佛安哦打法反对法<table>";
		s = new String(s.getBytes("UTF-8"), "UTF-8");
		System.out.print(s);
		FileWriter fw = new FileWriter(new File(".\\index.htm"));
		fw.write(s);
		fw.close();
	}

	public static void main(String arg[]) throws Exception {
		Log.setCache(false, 0,0);		
		test.testMC();
	}
}
