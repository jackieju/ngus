package com.ngus.myweb.servlet;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ngus.myweb.services.MyWebResService;
import com.ngus.myweb.util.ParamUtils;
import com.ngus.um.UMClient;
import com.ns.dataobject.Attribute;
import com.ns.log.Log;
import com.ngus.myweb.searchkey.*;

/**
 * Servlet implementation class for Servlet: Search
 * 
 */
public class Search extends javax.servlet.http.HttpServlet implements
		javax.servlet.Servlet {

	public Search() {
		super();
	}

	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request,
	 *      HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request,
	 *      HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		//request.setCharacterEncoding("utf-8");
		boolean byUser = ParamUtils.getBooleanParameter(request, "byUser");		
		String byUserId = ParamUtils.getParameter(request, "byUserId", false);
		if (!byUser)
			byUserId = null;
		String type = ParamUtils.getParameter(request, "type", false);
		if (type == null) {
			type = "fulltext";
		}
		int number = ParamUtils.getIntParameter(request, "number", 10);
		String key = ParamUtils.getParameter(request, "key");
		key = (key == null) ? "" : key;
		int page = ParamUtils.getIntParameter(request, "page", 1);
		SearchKeyService.addKey(key);
		int total_pages = 1;
		Attribute count = null;
		if (type.equalsIgnoreCase("user")) {
			UMClient um = new UMClient();
			try {
				count = new Attribute("name", new Integer(0));
				Iterator iter =	um.searchUser(key, ((page - 1) * number), number, count);
				request.setAttribute("userList", iter);
			} catch (Exception e) {
				Log.error(e);
			}
		}

		else {
			try {
				List ot = null;
				count = new Attribute("name", new Integer(0));
				if (type.equalsIgnoreCase("fulltext")) {
					ot = MyWebResService.instance().search(key.trim(),
							(page - 1) * number, number, count, byUserId);
				} else {
					ot = MyWebResService.instance().listByTag(key.trim(),
							(page - 1) * number, number, count, byUserId);
				}
				request.setAttribute("bookmarklist", ot);
			} catch (Exception e) {
				Log.error(e);
			}
		}
		total_pages = (int) Math.ceil(Double
				.parseDouble(count.getStringValue())
				/ number);
		request.setAttribute("page", page);
		request.setAttribute("key", key);
		request.setAttribute("type", type);
		//request.setAttribute("radSearchType", type);
		request.setAttribute("total_pages", total_pages);
		request.setAttribute("count", count.getValue());
		RequestDispatcher rd = null;
		if(!type.equalsIgnoreCase("user")){
			rd = request.getRequestDispatcher("search.jsp");
		}
		else{
			rd = request.getRequestDispatcher("searchUser.jsp");
		}		
		rd.forward(request, response);
	}
}