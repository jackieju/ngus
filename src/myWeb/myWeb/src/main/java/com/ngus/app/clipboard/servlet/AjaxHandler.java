package com.ngus.app.clipboard.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.ngus.dataengine.DataEngine;
import com.ngus.dataengine.ResourceObject;
import com.ns.log.Log;
import com.ns.util.XmlUtil;

/**
 * Servlet implementation class for Servlet: AjaxHandler
 * 
 */
public class AjaxHandler extends javax.servlet.http.HttpServlet implements
		javax.servlet.Servlet {
	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public AjaxHandler() {
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

	}

	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request,
	 *      HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String method = request.getParameter("method");
		String postData = request.getParameter("data");

		try {
			if (method.equalsIgnoreCase("post")) {
				ResourceObject ro = ResourceObject.fromXML(postData);
				String res_id = new DataEngine().post(ro);
				
				Document doc = XmlUtil.createDocument();
				Element rootEle = doc.createElement("ngusResponse");
				rootEle = (Element) (doc.appendChild(rootEle));
				rootEle.setAttribute("res_id", res_id);				

				response.getWriter().print(XmlUtil.DOMToString(doc));
			}
		} catch (Exception e) {
			Log.error(e);
		}
	}
}