package com.ngus.app.clipboard;

import com.ngus.dataengine.AbstractJCRAppObject;
import com.ngus.dataengine.IDataEngine;

public class Clipbard extends AbstractJCRAppObject{

	public static String APP_NAME= "clipboard";
	@Override
	public String getAppName() {
		// TODO Auto-generated method stub
		return APP_NAME;
	}

	@Override
	public int getType() {		
		return IDataEngine.RES_TYPE_BIN;
	}

}
