package com.ngus.myweb.servlet.widget;

import java.io.PrintWriter;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ngus.dataengine.DataEngine;
import com.ngus.dataengine.ResourceObject;
import com.ngus.myweb.util.ParamUtils;
import com.ngus.um.UMClient;
import com.ns.log.Log;
import org.json.JSONObject;

public class AddTodo extends javax.servlet.http.HttpServlet implements
javax.servlet.Servlet {
	public void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out= response.getWriter();
		String resId="";
		// check session
		if (!new UMClient().checkHttpSession(request)) {
			//response.sendRedirect("/error.jsp?msg=check session failed");
			out.print('0');
			return;
		}

		// get parameter
		String uid = (String) request.getSession().getAttribute("userid");
		if (uid == null) // should go to error page
		{
			out.print('0');
			//response.sendRedirect("/error.jsp?msg=uid is null");
			return;
		}
		
		String content = ParamUtils.getParameter(request, "content");
		String isDone = ParamUtils.getParameter(request, "isDone");
		
		try{
			
			String xml="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
				"<ro shareLevel=\"0\" title=\"todo\" tags=\"1,2,3\" type=\"1\" storageType=\"1\" storagePath=\"/tree/todo\" resourceType=\"doc\">"+ 
				content + 
				"<list name=\"rdo\">"+					
				"<rdo modelName=\"todo\" done=\""+ isDone +"\" />" +
				"</list>"+
				"</ro>";
			
			
			ResourceObject ro= ResourceObject.fromXML(xml);
			ro.setUser(uid);
			resId = DataEngine.instance().post(ro);
		} catch (Exception e) {
			Log.error(e);
			out.print("0");
			return;
		}
				
		out.print(resId);
	}
	
}
