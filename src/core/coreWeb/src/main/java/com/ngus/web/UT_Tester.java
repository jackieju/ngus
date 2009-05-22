package com.ngus.web;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import com.ns.log.Log;

public class UT_Tester extends Thread {

	Object instance = null;

	static String result = "";

	static int successNum = 0;

	static int lostNum = 0;

	static Long usedTime = new Long(0);

	static boolean bStop = false;

	static HashMap<Long, Thread> threads = new HashMap<Long, Thread>();

	static public void testMethod(Object instance, Method m, Object inst,
			HttpServletRequest request, int number, long time) throws Exception {
		reset();
		Log.trace("Launching threads...");
		for (int i = 0; i < number; i++) {
			UT_Tester utt = new UT_Tester(instance, m, request);
			utt.start();
			synchronized (threads) {
				threads.put(new Long(utt.getId()), utt);
			}
			
		}
		Log.trace("Threads Launched");
		long start_time = System.currentTimeMillis();

		while (true) {
			if (System.currentTimeMillis() - start_time > time)
				break;
		}

		stopAll();
	}

	static public void reset() {
		result = "";
		successNum = 0;
		usedTime = new Long(0);
		lostNum = 0;
		bStop = false;
		threads.clear();
	}

	Method method = null;

	HttpServletRequest req = null;

	public UT_Tester(Object instance, Method m, HttpServletRequest request) {
		method = m;
		req = request;
		this.instance = instance;
	}

	static synchronized String report() {
		if (successNum > 0)
			return "result:<br>" + result + "<br>===========<br>" + "<table>"
					+ "<tr><td>" + "Thread Number: " + "</td><td>"
					+ threads.size() + "</td></tr>" + "<tr><td>"
					+ "success number: " + "</td><td>" + successNum
					+ "</td></tr>" + "<tr><td>" + "lost number: " + "</td><td>"
					+ lostNum + "</td></tr>" + "<tr><td>" + "usedTime: "
					+ "</td><td>" + usedTime + "ms</td></tr>" + "<tr><td>"
					+ "Average response time: " + "</td><td>" + usedTime
					/ successNum + "ms</td></tr>";
		else
			return "result:<br>" + result + "<br>===========<br>" + "<table>"
					+ "<tr><td>" + "Thread Number: " + "</td><td>"
					+ threads.size() + "</td></tr>" + "<tr><td>"
					+ "success number: " + "</td><td>" + successNum
					+ "</td></tr>" + "<tr><td>" + "lost number: " + "</td><td>"
					+ lostNum + "</td></tr>" + "<tr><td>" + "usedTime: "
					+ "</td><td>" + usedTime + "ms</td></tr>";

	}

	public static  void stopAll() throws Exception {
		Log.trace("Stopping threads...");
		bStop = true;
		synchronized (threads){
		Iterator<Long> it = threads.keySet().iterator();
		while (it.hasNext()) {
			threads.get(it.next()).join();
		}
		threads.clear();
		}
		Log.trace("All threads stopped.");
		bStop = false;
	}

	public void run() {

		try {
			Log.trace("thread " + this.getId() + " started");
			

			while (!bStop) {
				yield();
				StringBuffer ret = new StringBuffer(0);

				synchronized (result) {
					lostNum++;
				}

				long start = System.currentTimeMillis();
				method.invoke(this.instance, new Object[] { req, ret });
				long end = System.currentTimeMillis();

				synchronized (result) {
					lostNum--;
					successNum++;
					result += "====thread " + this.getId() + "========<br>";
					result += ret + "<br>";
					result += "cost time: " +  (end - start);
					result += "<br>============================<br>";
				}

				synchronized (usedTime) {
					usedTime += end - start;
				}
			}
		} catch (Exception e) {
			Log.error(e);
		}
		Log.trace("thread " + this.getId() + " is exiting");
//		synchronized (threads) {
//			this.threads.remove(new Long(this.getId()));
//		}
		super.run();
		Log.trace("thread " + this.getId() + " ended");

	}
}