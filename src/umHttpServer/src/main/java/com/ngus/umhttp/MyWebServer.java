package com.ngus.umhttp;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ngus.um.UMClient;
import com.ngus.um.UMSession;
import com.ngus.um.dbobject.UserInfo;
import com.ns.log.Log;
import com.ns.util.Base64;
import com.ns.util.Security;
;

/**
 * Servlet implementation class for Servlet: myWebServer
 *
 */
 public class MyWebServer extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String errostr = null;
	public String clientId;
	private String re;
	static {
		Log.setFile("ngus.log");
	}
	public MyWebServer() {
		super();
	}   	

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String methodName = request.getParameter("Submit");
		Log.trace("umHttpServer", "methodName=" + methodName);
		try{
		clientId = request.getRemoteAddr();
		if(methodName.equalsIgnoreCase("logOn")){
			String userName = request.getParameter("userName");
			String password = request.getParameter("password");
			try{
			re = logon(userName, password);
			}catch(Exception e){
				Log.error(e);
				response.getWriter().write(Log.printExpStack(e));
				return;				
			}
			//String penName = checkSession(re);
			//int retCode = logOff(re);
			//response.getWriter().write(re);			
		}
		else if(methodName.equalsIgnoreCase("checkSession")){
			String sessionId = request.getParameter("sessionId");
			try{
		//	re = checkSession(Base64.decodeToString(sessionId));
				re = checkSession(sessionId);
			}catch(Exception e){
				Log.error(e);
				response.getWriter().write(Log.printExpStack(e));
				return;
			}
			
		}else if(methodName.equalsIgnoreCase("logOff")){// methodName="logOff"
			Log.trace("umHttpServer", "logoff");
			String sessionId = (String) request.getParameter("sessionId");
			try{
				logOff(sessionId);
				Log.trace("umHttpServer", "logoff succeeded");
			}catch(Exception e){
				Log.error(e);
				response.getWriter().write(Log.printExpStack(e));
				return;
			}
			re = "ok";
		}
		else if (methodName.equalsIgnoreCase("register")){
			
			String userName = request.getParameter("userName");
			String nickName = request.getParameter("nickName");
			String pwd =  request.getParameter("password");
			int sex = Integer.parseInt(request.getParameter("sex"));
			String email = request.getParameter("email");
			String mobile = request.getParameter("mobile");
			UserInfo userInfo = new UserInfo();
			userInfo.setEmail(email);
			userInfo.setMobile(mobile);
			userInfo.setUserName(userName);
			userInfo.setSex(sex);
			userInfo.setNickName(nickName);
			
			userInfo.setPwd(pwd);
			
			new UMClient().register(userInfo);
			
			re = "ok";
			errostr = "no error";
		}
	}catch(Exception e){
		Log.error(e);
		response.getWriter().write(Log.printExpStack(e));
		return;
	}
		response.getWriter().write("<?xml version=\"1.0\" encoding=\"UTF-8\"?><return><value>"+ re + "</value><errostr>"+errostr+"</errostr></return>");
	}  	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	} 
	
	/**
	 * 
	 * @param userName
	 * @param password
	 * @return sessionId
	 */
	public String logon(String userName, String password) throws Exception{
		String sessionId = null;
		int logRet=0;
		byte logt = 0, livet = 0;
		if (userName == null) userName = "";
		if (password == null) password = "";
		String userType = "none";
		String id =  userName + userType;
		logt = 1;	//Logon Type
		livet = 1;
//		UserLogonOutput output = new UserLogonOutput(); 
		UMSession s = new UMClient().logon(userName, password, userType);
		if (s == null){
			errostr = "logon failed";
		return null;
		}
		else{
			errostr = "success";
			return s.getSessionId();
		}
//		if (logRet != 0){
//			sessionId = null;
//			if(logRet==-30009)
//				errostr = "userName doesn't exit�?";
//			else if(logRet==-30010 )
//				errostr = "password error�?";
//			else
//				errostr = "server error�?";
//		}
//		else{
//			
//			sessionId = Base64.encode(output.sEncryptedSessionId);		
//			errostr = "success";
//		}
//		return sessionId;
	}
	
	/**
	 * 
	 * @param sessionId
	 * @return penName
	 */
	public String checkSession(String sessionId) throws Exception{
			return new UMClient().checkSession(sessionId).getUser().getNickName();
	}
	
	/**
	 * 
	 * @param sessionId
	 */
	public void logOff(String sessionId) throws Exception{
		new UMClient().logoff(sessionId);		
	}
}