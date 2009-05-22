package com.ngus.myweb.spider;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;

import com.ngus.dataengine.DBConnection;
import com.ngus.dataengine.NGUSThread;
import com.ns.log.Log;

public class ResDaemon extends NGUSThread{
//	static boolean toStop = false;
	int interval = 1000;
	public Connection getConnection() throws Exception {
		return DBConnection.getConnection();
	}

	static HashMap<String, Boolean> lock = new HashMap<String, Boolean>();

	public ResDaemon(HashMap arg0) {
		super(arg0);
		interval = ((Integer)arg0.get("interval")).intValue();
		// TODO Auto-generated constructor stub
	}

//	public static void stopit(){
//		toStop = true;
//	}
	synchronized public boolean lockRow(String resId) {

		Boolean b = lock.get(resId);
		if (b == null || !b.booleanValue()) {
			lock.put(resId, true);
			return true;
		} else
			return false;

	}

	synchronized public void unlockRow(String resId) {
		lock.remove(resId);
	}
	
	@Override
	public void myRun() throws Exception {

		Log.trace("spider", "spider loop started");
		while (!toStop()) {
			daemonRun();			
			Thread.sleep(interval);
		}

		super.myRun();
		Log.trace("spider", "spider loop ended.");
	}

	public void daemonRun()throws Exception {
		
	}
}
