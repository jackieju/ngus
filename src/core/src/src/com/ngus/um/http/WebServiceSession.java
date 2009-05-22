package com.ngus.um.http;

import com.ngus.um.IUser;
import com.ngus.um.UMClient;
import com.ngus.um.UMSession;
import com.ns.log.Log;

public class WebServiceSession extends Session{
	UMSession ums;
	public WebServiceSession(String sessionId){
		UMSession session = null;
		try{
			session = new UMClient().checkSession(sessionId);				
		}
		catch(Exception e){
			Log.error(e);
		}
		this.ums = session;
	}
	
	@Override
	public IUser getUser() {
		return ums.getUser();
	}

}
