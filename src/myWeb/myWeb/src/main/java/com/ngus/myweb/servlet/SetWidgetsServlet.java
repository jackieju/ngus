package com.ngus.myweb.servlet;

import java.io.*;
import java.sql.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.ngus.um.UMClient;
import com.ngus.um.UMSession;
import com.ns.db.*;
import com.ngus.myweb.userextension.UserExtensionService;


public class SetWidgetsServlet extends javax.servlet.http.HttpServlet implements
javax.servlet.Servlet {

	private UMSession ums = null;
	private String sessionId = null;
	private String userName= null;
	
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
//		Connection con= null;
//		Statement state= null;
	
		PrintWriter out= response.getWriter();	

		checkSession(request, response);
		
		try{
//			con= DB.getConnection();
//			state= con.createStatement();
			
			String widgets= "[]";
			if(request.getParameter("widgets") != null)
				widgets= request.getParameter("widgets");

			UserExtensionService.setWidgets(userName, widgets);
			out.println(1);
//			String sql=null;
//			sql= "update umj_userinfo set widgets=\""+ widgets+ "\" where username=\""+userName+"\"";						

//			state.execute(sql);
			
			//out.println("id= "+ userID+"<br />widgets<hr /><br />"+ widgets);
			//out.println("id= "+userName+ ", widgets= widgets<br />");
		}catch(Exception e){
			//Log.error(e);
			e.printStackTrace();
		}
		finally{
//			DBC.closeStatement(state);
//			DBC.closeConnection(con);
			//out.println("</body></html>");
		}
		

		
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
