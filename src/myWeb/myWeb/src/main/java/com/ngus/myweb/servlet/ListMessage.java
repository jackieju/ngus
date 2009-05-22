package com.ngus.myweb.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ngus.message.MessageEngine;
import com.ngus.message.MessageObject;
import com.ngus.myweb.util.ParamUtils;
import com.ns.exception.NSException;

/**
 * Servlet implementation class for Servlet: ListMessage
 *
 */
 public class ListMessage extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public ListMessage() {
		super();
	}   	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}  	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		
		int currPage = ParamUtils.getIntParameter(request,"currPage",1);
		
		int pageSize = 5;
		
		int totalNum = 0;
		
		int totalPage = 1;
		
		List<MessageObject> messageList = new ArrayList<MessageObject>();
		
		String userName = ParamUtils.getParameter(request,"userName");
		
		String type = ParamUtils.getParameter(request,"type");
		
		RequestDispatcher rd = null;
		
		if(type.equals("receive")){
			
			
			
			try {
				totalNum = MessageEngine.instance().getReceiveMsgCount(userName);
				totalPage = (int)Math.ceil((totalNum + pageSize-1) / pageSize); 
				messageList = MessageEngine.instance().listReceiveMsg(userName,(currPage-1)*pageSize,pageSize);
			} catch (NSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			rd = request.getRequestDispatcher("listReceiveMsg.jsp");
		}
		
		if(type.equals("post")){
			try {
				totalNum = MessageEngine.instance().getPostMsgCount(userName);
				totalPage = (int)Math.ceil((totalNum + pageSize-1) / pageSize); 
				messageList = MessageEngine.instance().listPostMsg(userName,(currPage-1)*pageSize,pageSize);
			} catch (NSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			rd = request.getRequestDispatcher("listPostMsg.jsp");
		}
		
		request.setAttribute("currPage",currPage);
		request.setAttribute("totalPage",totalPage);
		request.setAttribute("totalNum",totalNum);
		request.setAttribute("msgList",messageList);
		
		rd.forward(request, response);
		
	}   	  	    
}