package com.ngus.dataengine;

public class RDOWriter extends Thread{
	String id;
	ResDesObject rdo;
	public RDOWriter(String id, ResDesObject rdo){
		this.id = id;
		this.rdo = rdo;
	}
	@Override
	public void run() {
		CacheWriter.putRDOToCache(id, rdo);
		super.run();
	}
	
	public static void putRDOToCache(String id, ResDesObject rdo){
		new RDOWriter(id, rdo).start();
	}
	
}