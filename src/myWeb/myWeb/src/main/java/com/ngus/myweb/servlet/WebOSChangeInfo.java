package com.ngus.myweb.servlet;

import java.io.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Iterator;

import javax.servlet.*;
import javax.servlet.http.*;

import com.ngus.um.UMClient;
import com.ngus.um.UMSession;
import com.ngus.um.dbobject.UserInfo;
import com.ns.db.*;

public class WebOSChangeInfo extends javax.servlet.http.HttpServlet implements
javax.servlet.Servlet {
	
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		int error= 0;
		try{
		
			String email= request.getParameter("email");
			String mobile= request.getParameter("mobile");
			if(email == null)
			throw new IOException();
			int id= Integer.parseInt(request.getParameter("id"));
			int sex= Integer.parseInt(request.getParameter("sex"));

			UserInfo userInfo= new UserInfo();
			userInfo.setId(id);
			userInfo.setSex(sex);
			
			Pattern mp = Pattern.compile("(^[0-9]{3,4}/-[0-9]{3,8}$)|(^[0-9]{3,8}$)|(^/([0-9]{3,4}/)[0-9]{3,8}$)|(^0{0,1}13[0-9]{9}$)");
			Matcher mm = mp.matcher(mobile);
			
			Pattern ep = Pattern.compile("\\w+(\\.\\w+)*@\\w+(\\.\\w+)+");
			Matcher em = ep.matcher(email);
			
			if(mobile.length()!=0){
				
				if(!mm.matches()){
					error++;
					request.setAttribute("merror",1);
				}
				else{
					userInfo.setMobile(mobile);
				}
				request.setAttribute("mobile",mobile);
			}
			
			if(email.length()!=0){
				
				if(!em.matches()){
					error++;
					request.setAttribute("eerror",1);
				}
				else{
					userInfo.setEmail(email);
				}
				request.setAttribute("email",email);
			}
		
		
			new UMClient().updateUserInfo(userInfo);

		}catch(Exception e){
			e.printStackTrace();
		}
		
		response.sendRedirect("webos_userInfo.jsp");
	}
	
}
