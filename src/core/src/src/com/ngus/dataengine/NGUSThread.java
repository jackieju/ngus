package com.ngus.dataengine;

import java.util.HashMap;

import com.ns.log.Log;

public class NGUSThread extends Thread{
	boolean toStop = false;
	public HashMap params = new HashMap();
	
	public NGUSThread(HashMap params){
		this.params = params;
	}
	
	public boolean toStop(){
		return toStop;
	}

	//	 please overide myRun(), but not run()
	public void run() {
		try{
			myRun();
		}catch(Exception e){
			Log.error(e);
		}
		super.run();
	}
	
	// please overide myRun(), but not run()
	public void myRun() throws Exception{
		
	}
	
	public void stopit(){
		toStop=true;
	}
}
