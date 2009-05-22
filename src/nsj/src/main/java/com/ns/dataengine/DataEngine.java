/*
 * Created on Aug 10, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.ns.dataengine;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DataEngine {

	/*
	 * singleton implemenation
	 */
	static DataEngine _inst = null;
	static DataEngine instance(){
		if (_inst == null){
			_inst = new DataEngine();
		}
		return _inst;
	}
	static void destroy(){
	}
	protected DataEngine(){
		
	}
	
	
	static String configFile = "dataengine.xml";
	static void setConfigFile(String config_file){
		configFile = config_file; 
	}
	
	
	void init(String configFile){
		// read config File 
		
	}
	
	public static void main(String[] args) {
	}
}
