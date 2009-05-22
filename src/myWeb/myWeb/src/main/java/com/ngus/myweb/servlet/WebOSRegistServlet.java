package com.ngus.myweb.servlet;

import java.io.*;
import java.sql.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.struts.action.ActionForward;

import com.ngus.myweb.userextension.UserExtensionService;
import com.ngus.um.UMClient;
import com.ngus.um.UMSession;
import com.ngus.um.dbobject.UserInfo;
import com.ns.db.*;

public class WebOSRegistServlet extends javax.servlet.http.HttpServlet implements
javax.servlet.Servlet {
	
	public void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		PrintWriter out= response.getWriter();
		
		try{
			InputStream in= request.getInputStream();
			BufferedReader reader= new BufferedReader(new InputStreamReader(in));
			
			String line= reader.readLine();
			String template=".+:\"(.+)\",.+:\"(.+)\",.+:\"(.+)\",.+:\"(.+)\"}";
			Pattern pattern= Pattern.compile(template);
			Matcher matcher= pattern.matcher(line);

			if(matcher.matches()){
				String userName= matcher.group(1);
				String password= matcher.group(2);
				String confirmPassword= matcher.group(2);
				String email= matcher.group(4);
				String validCode= matcher.group(3);
				String validCodeConfirm = (String) request.getSession().getAttribute("securityCode");
				
				if(userName == null || password == null || confirmPassword== null 
						|| email== null || validCode ==null || validCode == null 
						|| validCodeConfirm ==null){
					//response.sendRedirect("webos_reg_failed.jsp");
					out.print("7");
					return;
				}		
							
				int checkName = new UMClient().checkDuplicate(userName,null,null);
				if(checkName == -3){
					//response.sendRedirect("webos_reg_failed.jsp");
					out.print("3");
					return;
				}
				
				int checkPenName = new UMClient().checkDuplicate(null,userName,null);
				if(checkPenName== -4){
					out.print("3");
					//response.sendRedirect("webos_reg_failed.jsp");
					return;
				}
				
				if(!password.equals(confirmPassword)){
					out.print("5");
					//response.sendRedirect("webos_reg_failed.jsp");
					return;
				}
				
				if(!validCode.equals(validCodeConfirm)){
					out.print("2");
					//response.sendRedirect("webos_reg_failed.jsp");
					return;
				
				}				
				
				Pattern ep = Pattern.compile("\\w+(\\.\\w+)*@\\w+(\\.\\w+)+");
				Matcher em = ep.matcher(email);	
				if(!em.matches()){
					out.print("4");
					//response.sendRedirect("webos_reg_failed.jsp");
					return;
				}
	
				UserInfo userinfo = new UserInfo();
				userinfo.setUserName(userName);
				userinfo.setNickName(userName);
				userinfo.setPwd(password);
				userinfo.setEmail(email);
				userinfo.setMobile("");
				new UMClient().register(userinfo);
				//response.sendRedirect("webos_reg_ok.jsp?userName="+userName+"&penName="+userName);
				UserExtensionService.addUserExtension(userName, new ByteArrayInputStream(new byte[0]), 0);
				out.print("1");
			}
		}catch(Exception e){
			//Log.error(e);
			e.printStackTrace();
			out.print("0");
			//response.sendRedirect("webos_reg_failed.jsp");
		}
		finally{

		}
				
	}
	
	public void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}
	
}
