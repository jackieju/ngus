package com.ngus.core.webservice;

import com.ns.log.Log;

public class Test3 {
	
	public int BB(){
		Log.trace("enter test3:BB");
		return 99;
	}
	
	public Book CC(){
		Log.trace("enter test3:CC");
		return new Book();
	}
}
