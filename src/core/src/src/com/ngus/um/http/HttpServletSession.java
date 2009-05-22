package com.ngus.um.http;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.ngus.um.IUser;
import com.ngus.um.UMClient;
import com.ngus.um.UMSession;
import com.ngus.um.User;
import com.ns.log.Log;

public class HttpServletSession extends Session{
	ServletRequest request;
	UMSession ums;

	@Override
	public IUser getUser() {
		if(ums==null){
			return null;
		}
		return ums.getUser();
	}
	public HttpServletSession(ServletRequest r)  throws Exception{
		request = r;
//		try{
			ums = HttpServletSession.getSessinFromHttpReq(r);
//		}catch(Exception e){			
//			Log.error(e);
//		}
		Log.trace("ums="+ums);
		//Log.trace("userid = " + ums.getUser().getSUserId());
	}
	public ServletRequest getRequest() {
		return request;
	}

	

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	} 
	
	static public UMSession getSessinFromHttpReq(ServletRequest request) throws Exception{
//		int lCheckRet = -1;

		String SSSESSIONID = "";
//		String ckPenName = "";
//		String ckUserId = "";
//		String ckUserType = "";
//		byte ckLevel = 0;
//		byte ckSex = 0;
//		String ckPSIP = "";

//		String ClientId = request.getRemoteAddr();
//		UserPubInfo ckUserPubInfo = new UserPubInfo();

		Cookie[] cookies = ((HttpServletRequest) request).getCookies();
		Cookie cookie;
		if (cookies == null) {
			Log.trace("Check Session Failed, no cookie found");
			return null;
		}
		// Log.trace("cookie length:" + cookies.length);
		for (int i=0; i<cookies.length; i++) {
		cookie = cookies[i];
	//	Log.trace(cookie.getName());
		if (cookie.getName().equalsIgnoreCase("sessionId")){
			SSSESSIONID = cookie.getValue();
			}
		}
		Log.trace("session id =" +SSSESSIONID);	
		
//		out.println(SSSESSIONID);		
		if (SSSESSIONID.startsWith("\""))
			SSSESSIONID = SSSESSIONID.substring(1);
		
		if (SSSESSIONID.endsWith("\""))
			SSSESSIONID = SSSESSIONID.substring(0, SSSESSIONID.length()-1);
		
		
		int sl = SSSESSIONID.length();
		Log.trace("session id length = " + sl+", value="+SSSESSIONID);		
//		Log.trace("===============" + ClientId + "==================");
		UMSession session = null;
		if (sl!=0){
			//out.println("Begin to checksession.");
			//nAppType - ????��??????��??,?????��???��??0, strAppLog - ????��???????????????????????
	
			session = new UMClient().checkSession(SSSESSIONID);

		}else{
			
			//out.println("<font color=red>NO SESSIONID FOUND, CHECK SESSION CANCELLED.</font>");
		}


		if (session == null){
			Log.trace("Check Session Failed.");
			//return null;
//			throw new Exception("Check Session Failed");
			return null;
		}
		
		return session;
	}
	
}
