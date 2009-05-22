package com.ngus.myweb.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.ngus.message.*;
import com.ns.exception.NSException;

/**
 * Servlet implementation class for Servlet: DeleteMessage
 *
 */
 public class DeleteMessage extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public DeleteMessage() {
		super();
	}   	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int messageId=Integer.parseInt(request.getParameter("messageId"));
		String userShow=request.getParameter("userShow");
		try {
			MessageEngine.instance().updateShowStatus(messageId,userShow);
			response.sendRedirect("monweb_message.jsp");
		} catch (NSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}  	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}   	  	    
}