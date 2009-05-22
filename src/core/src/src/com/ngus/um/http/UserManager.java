package com.ngus.um.http;

import com.ngus.um.User;
import com.ns.log.Log;
//import com.stockstar.buos.um.UserPubInfo;

public class UserManager {

	
	static public com.ngus.um.IUser getCurrentUser(){
		//UserPubInfo upi = new UserPubInfo();
		//upi.sUserId = "dummyUser";
		//return new User(upi);
		Session s = Session.getCurrentSession();
		if (s == null)
		{
//			UserPubInfo upi = new UserPubInfo();
////			upi.sUserId = "guest";// + Thread.currentThread().getId();
//			User user = new User();
//			user.setUserName("guest");
//			return user;
//			
			Log.trace("get current session failed");
			return null;
		}
		Log.trace("leave");
		return s.getUser();
	}
}
