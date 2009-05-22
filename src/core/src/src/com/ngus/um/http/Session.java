package com.ngus.um.http;

import com.ngus.um.IUser;

public abstract class Session {

	static public Session getCurrentSession(){
		return SessionManager.getSession();
	}
	
	abstract public IUser getUser();
	
}
