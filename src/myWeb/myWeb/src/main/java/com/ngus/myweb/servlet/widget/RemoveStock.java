package com.ngus.myweb.servlet.widget;

import java.io.PrintWriter;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ngus.dataengine.DataEngine;
import com.ngus.myweb.util.ParamUtils;
import com.ngus.um.UMClient;
import com.ns.log.Log;


public class RemoveStock extends javax.servlet.http.HttpServlet implements
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
		if (resId == null) // should go to error page
		{
			out.print('0');
			//response.sendRedirect("/error.jsp?msg=resid is null");
			return;
		}
		Log.trace("resId="+resId);
		resId = resId.trim();

		try {
			DataEngine.instance().deleteResource(resId, true);
		} catch (Exception e) {
			Log.error(e);
			out.print('0');
			return;
		}
		
		out.print('1');
	}
	
}
