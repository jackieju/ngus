package com.ngus.myweb.servlet;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.*;
import javax.servlet.http.*;

import com.ngus.um.UMClient;
import com.ngus.um.UMSession;
import com.ns.exception.NSException;

public class WebOSLoginServlet extends javax.servlet.http.HttpServlet implements
javax.servlet.Servlet {
	
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out= response.getWriter();
		
		InputStream in= request.getInputStream();
		BufferedReader reader= new BufferedReader(new InputStreamReader(in));
		
		String line= reader.readLine();
		
		String template=".+:\"(.+)\",.+:\"(.+)\",.+:\"(.+)\"}";
		Pattern pattern= Pattern.compile(template);
		Matcher matcher= pattern.matcher(line);
		
		if(!matcher.matches()){
			out.print("0");
		}
		else{
			
		
		String userName= matcher.group(1);//request.getParameter("userName");
		String password= matcher.group(2);//request.getParameter("password");
		String validateCode= matcher.group(3);//request.getParameter("");
		String validateCode2= (String)request.getSession().getAttribute("validateCode");
		//out.print(userName+" "+ password+" "+ validateCode2); 
		//validateCode= "2";
		try{
			if(userName ==null || userName.length()== 0){
				out.print("0");
				//test the login with out javascript code
				//the site redirected is just for testing, it's not serious 
				//response.sendRedirect("webos_reg_failed.jsp");
				return;
			}
			else if(!validateCode.equalsIgnoreCase(validateCode2)){
				out.print("2");
				//test the login with out javascript code
				//the site redirected is just for testing, it's not serious 
				//response.sendRedirect("webos_reg_failed.jsp");
				return ;
			}
			else if(new UMClient().checkDuplicate(userName, null, null)!=-3){
				out.print("0");
				//test the login with out javascript code
				//the site redirected is just for testing, it's not serious 
				//response.sendRedirect("webos_reg_failed.jsp");
				return ;
			}
			else{
				UMSession ums = new UMClient().logon(userName, password, "");
				Cookie userCookie = new Cookie("sessionId", ums.getSessionId());
				request.getSession().setAttribute("userName", userName);	
				userCookie.setMaxAge(60*60*24*365);
				userCookie.setPath("/");
				response.addCookie(userCookie);
				request.setAttribute("userName",userName);
				out.print("1");
				//test the login with out javascript code
				//the site redirected is just for testing, it's not serious 
				//response.sendRedirect("webos_desktop.jsp");
				return ;
			}
			
		}catch(NSException e){
			e.printStackTrace();
			out.print("0");
		}
		}
	}
}
