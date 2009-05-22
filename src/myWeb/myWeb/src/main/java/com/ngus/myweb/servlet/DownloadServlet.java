package com.ngus.myweb.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ns.log.Log;

/**
 * Servlet implementation class for Servlet: download
 *
 */
 public class DownloadServlet extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public DownloadServlet() {
		super();
	}   	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doSendFile(request, response);
	}  	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doSendFile(request, response);
	}
	
	public void doSendFile(HttpServletRequest request, HttpServletResponse response){
		String client = request.getParameter("client");
		String version = request.getParameter("version");
		if (version == null || version.length() == 0)
			version = getLatestVersion(client);
		try{
		response.sendRedirect("download/"+client.toLowerCase()+"-"+version+ getFileExt(client));
		}catch(Exception e){
			// to err page
			Log.error(e);
		}
	}
	
	public String getLatestVersion(String client){
		if (client.equalsIgnoreCase("IESidebar"))
			return "1.0.0";
		return "";
	}
	
	public String getFileExt(String client){
		if (client.equalsIgnoreCase("IESidebar"))
			return ".exe";
		else return "";
	}
}