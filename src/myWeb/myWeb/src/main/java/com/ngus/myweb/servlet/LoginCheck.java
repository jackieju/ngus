package com.ngus.myweb.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import com.ns.log.Log;
import com.ngus.dataengine.DBConnection;
import com.ngus.um.UMClient;
import com.ngus.um.UMSession;
import com.ns.log.Log;


import java.sql.Connection;
import java.sql.Statement;

/**
 * Servlet implementation class for Servlet: LoginCheck
 *
 */
 public class LoginCheck extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public LoginCheck() {
		super();
	}   	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int logRet=0;
		byte logt = 0, livet = 0;
		int log_erro_level = 0;
		boolean bLogon = false;
		String logonStatus = "";
		String userName = request.getParameter("userName");
		int length = userName.length();
//		String UserType = request.getParameter("UserType");
		String password = request.getParameter("password");	
		String validateCode = request.getParameter("validateCode");
		String validateCode2 = (String) request.getSession().getAttribute("validateCode");
		request.getSession().removeAttribute("validateCode");//ȥ��session�е�validateCode,���Զ��ʹ��ͬһ����֤��ͼƬ
		if (userName == null || userName.length()==0){//userName == null || userName.length()==0
			response.sendRedirect("log_erro.jsp?log_erro_level="+log_erro_level);//�û�����Ϊ��
		}
		else{
			if(!validateCode.equalsIgnoreCase(validateCode2)){
				log_erro_level = 2;//��֤���������
				response.sendRedirect("log_erro.jsp?log_erro_level="+log_erro_level);
			}else{
				String clientId = request.getRemoteAddr();	
				if (userName == null) userName = "";
				if (password == null) password = "";
				String userType = "none";
				String id =  userName + userType;
				
				logt = 1;	//Logon Type
				livet = 1;
//				UserLogonOutput output = new UserLogonOutput(); 
//				logRet = UMClient.UF_Logon(logt, livet, id, password, clientId, output);
//				if (logRet != 0){//logRet != 0
//					log_erro_level = 1;//�û�����������
//					bLogon = false;
//					logonStatus = logonStatus + "UserLogOn error with code:" + logRet;
//					response.sendRedirect("log_erro.jsp?log_erro_level="+log_erro_level);
//				}else{
//					Connection con;
//					Statement st;
//					try {
//						//通过checksession得到UserPubInfo对象，然后得到penName,最后得到最近登陆时间
//						UserPubInfo userPubInfo = new UserPubInfo();
//						UMClient.UF_CheckSession(output.sEncryptedSessionId,clientId,0,"AppLog","USER.CHECKSESSION",userPubInfo);
//						String penName = userPubInfo.getSPenName();
//						penName =  penName.substring(0, penName.length()-1);
//						con = DBConnection.getConnection();
//						st = con.createStatement();
//						st.executeUpdate("update userextension set newLogonTime=now() where penName='"+penName+"'");
//					} catch (Exception e) {
//						// TODO Auto-generated catch block
//						Log.error(e);
//					}
//					
//					bLogon = true;
//	//				Log.trace("session id length="+ output.sEncryptedSessionId.length());
//					//add Cookie
//					Cookie userCookie = new Cookie("sessionId", output.sEncryptedSessionId);
//					request.getSession().putValue("userName", userName);	
//					if (livet == 2){
//						userCookie.setMaxAge(60*60*24*365); // 1 year
//					}
//					//userCookie.setDomain(".stockstar.com");	//һ����Ҫȷ��һ������������в��ԣ�������ȡ���������ơ�
//					userCookie.setPath("/");
//					response.addCookie(userCookie);
//					logonStatus = "Logon Succesfully, Cookie Wrote to client. cookie=" + userCookie.getValue();				
//					response.sendRedirect("main.jsp");	
//				}
				try{
					UMSession ums = new UMClient().logon(userName, password, "");
					if (ums != null){
						//				Log.trace("session id length="+ output.sEncryptedSessionId.length());
						//add Cookie
						Cookie userCookie = new Cookie("sessionId", ums.getSessionId());
						request.getSession().putValue("userName", userName);	
//						if (livet == 2){
							userCookie.setMaxAge(60*60*24*365); // 1 year
//						}
						//userCookie.setDomain(".stockstar.com");	//һ����Ҫȷ��һ������������в��ԣ�������ȡ���������ơ�
						userCookie.setPath("/");
						response.addCookie(userCookie);
						logonStatus = "Logon Succesfully, Cookie Wrote to client. cookie=" + userCookie.getValue();				
						response.sendRedirect("main.jsp");	
					}
				}catch(Exception e){
					response.getOutputStream().println(Log.printExpStack(e));
				}
				
			}				
		}	
	}

	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}   	  	    
}