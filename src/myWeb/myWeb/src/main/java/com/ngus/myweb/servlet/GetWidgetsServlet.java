package com.ngus.myweb.servlet;

import java.io.*;
import java.sql.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.ngus.um.UMClient;
import com.ngus.um.UMSession;
import com.ns.db.*;
import com.ngus.myweb.userextension.UserExtensionService;


public class GetWidgetsServlet extends javax.servlet.http.HttpServlet implements
javax.servlet.Servlet {

	private UMSession ums = null;
	private String sessionId = null;
	private String userName= null;
	
	public void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		checkSession(request, response);
//		Connection con= null;
//		Statement state= null;
		
		PrintWriter out= response.getWriter();
	
		out.print(UserExtensionService.getWidgets(userName));
//		try{
//			con= DB.getConnection();
//			state= con.createStatement();
//			String sql="select widgets from umj_userinfo where username=\""+userName+"\"";
					
//			ResultSet rs= state.executeQuery(sql);
//			if(rs.next()){
//				out.print(rs.getString("widgets"));
//			}
//			String widgets= rs.getString("widgets");
//			else{
//				out.print("[]");
//			}
//		}catch(Exception e){
//			//Log.error(e);
//			e.printStackTrace();
//			out.println("[]");
//		}
//		finally{
//			DBC.closeStatement(state);
//			DBC.closeConnection(con);
//		}
				
	}
	
	public void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}
	
	private void checkSession(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	

		Cookie[] cookies = request.getCookies();
		if(cookies!=null){
			Cookie cookie;
			for(int i=0; i<cookies.length; i++) {
				cookie = cookies[i];
				if (cookie.getName().compareTo("sessionId")==0){
					sessionId = cookie.getValue();
					
				}
			}
			
			if (sessionId!=null){
				try{
					ums = new UMClient().checkSession(sessionId);
					userName = ums.getUser().getUserName();

				}catch(Exception e){
					response.sendRedirect("webos_singin.html");
				}
			}else{
				response.sendRedirect("webos_singin.html");
			}
			
		}
		else{
			response.sendRedirect("webos_singin.html");
		}	
	}
}
