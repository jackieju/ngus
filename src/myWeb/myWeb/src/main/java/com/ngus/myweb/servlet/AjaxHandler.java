package com.ngus.myweb.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.ngus.dataengine.DataEngine;
import com.ngus.dataengine.ResourceObject;
import com.ns.dataobject.DataObjectList;
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
		// for testing, only use 'post' in production server
		doPost(request, response);
	}

	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request,
	 *      HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Log.trace("enter");
		String req = request.getParameter("request");
		if (req == null)
			throw new ServletException("can not find parameter 'request'");
		response.setContentType("text/xml");
		response.setHeader("Cache-Control", "no-cache");
		if (req.equalsIgnoreCase("listRes")) {
			String ret = _listRes(request);
			response.getWriter().print(ret);
		} else
			throw new ServletException("unknown request '" + req + "'");
		Log.trace("leave");
	}

	public String _listRes1(HttpServletRequest req) {
		String resId = req.getParameter("resId");
		int menuId = Integer.parseInt(req.getParameter("menuId"));
		int nodeId = Integer.parseInt(req.getParameter("nodeId"));

		Log.trace("resId=" + resId);
		Log.trace("menuId=" + menuId);
		Log.trace("nodeId=" + nodeId);

		int newMenuId = menuId++;

		String template = "stm_bpx(\"p" + newMenuId + "\",\"p" + menuId
				+ "\",[1,2,0,0,2,3,25,7,100,\"stEffect(\\\"slip\\\")\"]);\r\n";
		DataEngine de = new DataEngine();
		ResourceObject root = null;
		try {
			root = de.getResourceTree(resId, 2);
		} catch (Exception e) {
			Log.error(e);
			return "error:" + Log.printExpStack(e);
		}
		DataObjectList dol = root.getChildResourceObjects();
		for (int i = 0; i < dol.size(); i++) {
			ResourceObject ro = (ResourceObject) dol.get(i);
			String line = "";
			if (ro.getResourceType().contains("folder")) // is folder
				line = "stm_aix(\"p"
						+ newMenuId
						+ "i"
						+ i
						+ "\",\"p"
						+ menuId
						+ "i"
						+ nodeId
						+ "\",[0,\"+  ro.getStringValue() + \",\"\",\"\",-1,-1,0,\"\",\"_self\",\"\",\"+  ro.getStringValue() + \"]);";
			else
				line = "stm_aix(\"p"
						+ newMenuId
						+ "i"
						+ i
						+ "\",\"p"
						+ menuId
						+ "i"
						+ nodeId
						+ "\",[0,\""
						+ ro.getTitle()
						+ "\",\"\",\"\",-1,-1,0,\""
						+ ro.getStringValue()
						+ "\",\"_self\",\"\",\""
						+ ro.getTitle()
						+ "\",\"icon_02b.gif\",\"icon_02b.gif\",25,20,0,\"\",\"\",0,0]);\r\n";
			template += line;
		}
		template += "stm_ep();\r\n";

		return template;
	}

	public String _listRes(HttpServletRequest req) {
		String resId = req.getParameter("resId");
		int menuId = Integer.parseInt(req.getParameter("menuId"));
		int nodeId = Integer.parseInt(req.getParameter("nodeId"));
		int deep = Integer.parseInt(req.getParameter("deep"));

		Log.trace("resId=" + resId);
		Log.trace("menuId=" + menuId);
		Log.trace("nodeId=" + nodeId);
		Log.trace("deep=" + deep);

		int newMenuId = menuId++;

		DataEngine de = new DataEngine();
		ResourceObject root = null;
		try {
			root = de.getResourceTree(resId, deep);
		} catch (Exception e) {
			Log.error(e);
			return "error:" + Log.printExpStack(e);
		}
		
	
		Document doc = XmlUtil.createDocument();
		Element rootEle = doc.createElement("ngusResponse");
		rootEle = (Element) (doc.appendChild(rootEle));
		addRO(root, rootEle, doc);		

		return XmlUtil.DOMToString(doc);
	}
	
	protected void addRO(ResourceObject ro, Element parent, Document doc){
		if (ro.getResourceType().contains("folder")) { // is folder
			Element folder = (Element) parent.appendChild(doc
					.createElement("folder"));
			folder.setAttribute("title", ro.getTitle());
			folder.setAttribute("resid", ro.getResId());
			
			DataObjectList dol = ro.getChildResourceObjects();
			for (int i = 0; i < dol.size(); i++) {
				ResourceObject ro1 = (ResourceObject) dol.get(i);
				addRO(ro1, folder, doc);
			}
		} else {
			Element res = (Element) parent.appendChild(doc
					.createElement("res"));
			res.setAttribute("title", ro.getTitle());
			res.setAttribute("value", ro.getStringValue());
			res.setAttribute("resid", ro.getResId());
		}
	}

}