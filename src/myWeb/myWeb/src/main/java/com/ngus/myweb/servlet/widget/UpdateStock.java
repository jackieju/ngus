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


public class UpdateStock extends javax.servlet.http.HttpServlet implements
javax.servlet.Servlet {
	public void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out= response.getWriter();
		
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
		
		String resId = ParamUtils.getParameter(request, "resId");
		String code= ParamUtils.getParameter(request, "code");
		String isDone= ParamUtils.getParameter(request, "isChoose");
		String xml= null;
		
		if(code == null){
			code = "";
		
			code= code.replace('\n', ' ');
			code= code.trim();
		}		
		if (resId != null){
			resId= resId.trim();
		
			xml="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
				"<ro resId=\""+resId +"\" shareLevel=\"0\" title=\"stock\" tags=\"1,2,3\" type=\"1\" storageType=\"1\" storagePath=\"/tree/stock\" resourceType=\"doc\">"+ 
				code+ 
				"<list name=\"rdo\">"+
				"<rdo modelName=\"stock\" done=\""+ isDone +"\" />" +
				"</list>"+
				"</ro>";
		}
		else
			xml="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
				"<ro shareLevel=\"0\" title=\"stock\" tags=\"1,2,3\" type=\"1\" storageType=\"1\" storagePath=\"/tree/stock\" resourceType=\"doc\">"+ 
				code+ 
				"<list name=\"rdo\">"+					
				"<rdo modelName=\"stock\" done=\""+ isDone+"\" />" +
				"</list>"+
				"</ro>";
		
		ResourceObject obj = null;
		try {
			obj = ResourceObject.fromXML(xml);
		} catch (Exception e) {
			Log.error(e);
			out.print("0");
			return;
		}
		obj.setUser(uid);

		try {
			resId = DataEngine.instance().post(obj);
//			resId= "error!";
		} catch (Exception e) {
			Log.error(e);
			out.print("0");
			return;
		}
		
		out.print(resId);
	}
	
}
