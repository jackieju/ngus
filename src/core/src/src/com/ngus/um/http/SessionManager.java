package com.ngus.um.http;

import java.util.HashMap;

import com.ns.log.Log;

public class SessionManager {
	static HashMap<Long, Session> map = new HashMap<Long, Session>() ;
	
	public static void putSession(Session s){
		
//		Log.trace("thread id = " + Thread.currentThread().getId());
//		Log.trace("user id = " + s.getUser().getUserId());
////		s.getUser().getNickName();
		synchronized (map){
			map.put(new Long(Thread.currentThread().getId()), s);
		}
	}
	public static Session getSession(){
		Log.trace("thread id = " + Thread.currentThread().getId());
		Long l = new Long(Thread.currentThread().getId());
		Session s = map.get(l);
//		IUser u = s.getUser();
		Log.trace("leave");

		return s;
	}
	
	public static Session removeSession(){
		return map.remove(new Long(Thread.currentThread().getId()));
	}
}
