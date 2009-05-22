package com.ngus.dataengine;


public class ROWriter extends Thread{
	String id;
	ResourceObject ro;
	public ROWriter(String id, ResourceObject ro){
		this.id = id;
		this.ro = ro;
	}
	@Override
	public void run() {
		CacheWriter.putROToCache(id, ro);
		
		super.run();
	}
	
	public static void putROToCache(String id, ResourceObject ro){
		new ROWriter(id, ro).start();
	}
}
