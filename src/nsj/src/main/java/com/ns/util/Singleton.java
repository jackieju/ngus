/*
 * Created on 2005-4-5
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.ns.util;

import com.ns.exception.NSException;


/**
 * @author I027910
 * You can only reuse the code by copy, but not extends it. Because the implemation is based on static member.
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Singleton {
	
	static private Singleton _instance = null;
		
	
	/**
	 * Get instance of DataMgr
	 * @return	the single instance of DataMgr, using the default config file "ACCConfig.xml"
	 * @throws Exception
	 */
	synchronized static public Singleton instance() throws NSException {
		if (_instance == null) {

			_instance = new Singleton();

			_instance.init();
			

		}

		return _instance;
	}
	
	/**
	 * destroy
	 * @throws Exception
	 */
	synchronized static public void destroy() throws Throwable{
		if (_instance != null) {
		_instance = null;
		}
	}
	public static void init(){
		
	}
	public static void main(String[] args) {
	}
}
