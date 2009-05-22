package com.ngus.myweb.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ngus.myweb.rss.RSSFeedGenerator;
import com.ns.log.Log;

/**
 * Servlet implementation class for Servlet: RSSServlet
 *
 */
 public class RSSServlet extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public RSSServlet() {
		super();
	}   	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Log.trace("enter");
		String userId = request.getParameter("userId");
		String recent = request.getParameter("recent");
		Log.trace("userId="+userId);
		Log.trace("recent="+recent);
		try{
			String xml = RSSFeedGenerator.getUserAllResUpdate(Integer.parseInt(userId), Integer.parseInt(recent));
			response.getWriter().write(xml);
		}catch(Exception e){
			Log.error(e);
			response.getWriter().write(Log.printExpStack(e));
		}
		Log.trace("leave");
		
	}  	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}   	  	    
}