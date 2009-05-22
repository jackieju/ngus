/*
 * Created on 2005-4-5
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.ngus.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ngus.apps.clipboard.Clipboard;
import com.ngus.comment.CommentEngine;
import com.ngus.dataengine.DataEngine;
import com.ngus.dataengine.ResourceObject;
import com.ngus.um.http.UserManager;
import com.ns.exception.NSException;
import com.ns.log.Log;

/**
 * @author I027910
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ClipboardServlet extends HttpServlet {
	final public static String modelName = "clipborad";

	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		Log.trace("servle init");
		// Log.setFile("c:\\gnus.log");
		// Log.setCache(false, 0, 1000);
	}

	public java.lang.String getServletInfo() {
		// TODO Method stub generated by Lomboz
		return super.getServletInfo();
	}

	public void service(ServletRequest request, ServletResponse response)
			throws ServletException, IOException {
		super.service(request, response);
	}

	public void destroy() {
		super.destroy();
		// TODO Method stub generated by Lomboz
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Method stub generated by Lomboz
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// SessionManager.putSession(new HttpServletSession(request));
		Log.trace("enter");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		// String userName = request.getRemoteUser();
		String userName = "tester";
		String requestType = request.getParameter("request");

		String multiThread = request.getParameter("multiThread");
//		response.getWriter().println("multiThread="+multiThread);
		int threadNum = 1;
		int time = 0;

		if (multiThread!=null && multiThread.equalsIgnoreCase("on")) {
			String sThreadNum = request.getParameter("threadNumber");
			threadNum = Integer.parseInt(sThreadNum);
			time = Integer.parseInt(request.getParameter("time"));

			try {
				UT_Tester.testMethod(this, this.getClass().getMethod(requestType,
						HttpServletRequest.class, StringBuffer.class), this,
						request, threadNum, time);
			} catch (Exception e) {
				Log.error(e);
				response.getWriter().println(e+"<br>");
				response.getWriter().println(Log.printExpStack(e));
			}
			response.getWriter().println(UT_Tester.report());
			return;
		}

		if (requestType.equalsIgnoreCase("post")) {
			String ret = "error";

			try {
				ret = post(request, new StringBuffer());
			} catch (Exception e) {
				Log.error(e);
			}

			response
					.getWriter()
					.println(
							"<?xml version=\"1.0\" encoding=\"UTF-8\"?><GNUS xmlns:gnus = \"http://\"> <gnus:resourceId>"
									+ ret + "</gnus:resourceId>\n</GNUS>");

		} else if (requestType.equalsIgnoreCase("listAll")) {
			response.getWriter().println(listAll(request, new StringBuffer()));
		}
		else if(requestType.equalsIgnoreCase("addComment")){
			String ret = "error";

			try {
				ret = addComment(request, new StringBuffer());
			} catch (Exception e) {
				Log.error(e);
			}

			response
					.getWriter()
					.println(""
							);

		}
	}

	protected void doDelete(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		super.doDelete(request, response);
	}

	protected void doHead(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		super.doHead(request, response);
	}

	protected void doOptions(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		super.doOptions(request, response);
	}

	protected void doTrace(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		super.doTrace(request, response);
	}

	public String listAll(HttpServletRequest request, StringBuffer ret) {
		
		try {
			List list = DataEngine.instance().listAllResOfUser(
					UserManager.getCurrentUser().getSUserId(), "Clipboard");
			ret.append("size=" + list.size() + "<br>");
			for (int i = 0; i < list.size(); i++) {
				String res_id = (String) list.get(i);
				try {
					ret.append(DataEngine.instance().getResourceObjById(res_id,
							true).printHtml());
				} catch (Exception e) {
					Log.error(e);
				}

			}

			ret.append(list.toString());
		} catch (Exception e) {
			Log.error(e);
			// throw new ServletException(e.toString());
			return "error";

		}
		return ret.toString();
	}

	// public ret = 0;

	public String post(HttpServletRequest request, StringBuffer ret) throws Exception {

		String content = request.getParameter("content");
		String category = request.getParameter("category");

		Clipboard c = null;

		try {
			c = new Clipboard(content, category);
		} catch (Exception e) {
			Log.error(e);
			throw new ServletException(e);
		}
		// prepare attributes
		String[] properties = request.getParameterValues("property");
		String[] values = request.getParameterValues("value");
		String[] tags = request.getParameterValues("tag");
		
		Log.trace("property number: " + properties.length);
		try {
			for (int i = 0; i < properties.length; i++) {
				if (properties[i] == null || properties[i].length() == 0)
					continue;
				if (values[i] == null || values[i].length() == 0)
					continue;

				c.setAppAttribute(properties[i].toLowerCase(), values[i]);
				Log.trace(c.render().printXML());
			}

			List<String> tagList = new ArrayList<String>();
			for (int i = 0; i < tags.length; i++) {
				if (tags[i] == null || tags[i].length() == 0)
					continue;
				tagList.add(tags[i]);
			}
			c.setTags(tagList);
			ArrayList<String> types = new ArrayList<String>();
			types.add(ResourceObject.RESTYPE_DOC);
			c.getRO().setResourceType(types);
			Log.trace(c.render().printXML());

			// update
			ret.append(DataEngine.instance().post(c));
			return ret.toString();
		} catch (Exception e) {
			Log.error(e);
			// throw new ServletException(new String(e.toString()
			// ));
			return "error";

		}

	}
	
	
	public String addComment(HttpServletRequest request, StringBuffer ret) throws Exception {
		String content = request.getParameter("content");
		String resId = request.getParameter("resId");
		
		try {
			CommentEngine.instance().addComment(content,resId,"sean");
		} catch (NSException e) {
			// TODO Auto-generated catch block
			//response.getWriter().println("error");
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//response.getWriter().println("error");
			e.printStackTrace();
		}
		return "seccess";
		
	}
}