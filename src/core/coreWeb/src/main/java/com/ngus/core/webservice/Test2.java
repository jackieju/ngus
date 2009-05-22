package com.ngus.core.webservice;

import com.ns.log.Log;

public class Test2 {
	public ComplexObject test(){
		Log.trace("enter tester");
		return new ComplexObject();
	}
	public ComplexObject test2(int p){
		Log.trace("enter tester2, p = " + p);
		return new ComplexObject();
	}
	public ComplexObject test_rcp_com(int p){
		Log.trace("enter tester2, p = " + p);
		return new ComplexObject();
	}
	public ComplexObject AA(){
		Log.trace("enter tester");
		return new ComplexObject();
	}
	public int BB(){
		Log.trace("enter BB");
		return 99;
	}
	
	public Book CC(){
		Log.trace("enter CC");
		return new Book();
	}
}
