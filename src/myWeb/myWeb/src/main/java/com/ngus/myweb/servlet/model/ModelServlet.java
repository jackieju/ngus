package com.ngus.myweb.servlet.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ngus.dataengine.DataEngine;
import com.ngus.dataengine.ResDesObject;
import com.ngus.dataengine.ResourceObject;
import com.ngus.myweb.services.MyWebResService;
import com.ngus.myweb.servlet.ROWrapper;
import com.ngus.myweb.util.ParamUtils;
import com.ngus.um.UMClient;
import com.ns.dataobject.Attribute;
import com.ns.log.Log;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * Servlet implementation class for Servlet: ModelServlet
 * 
 */
public class ModelServlet extends javax.servlet.http.HttpServlet implements
		javax.servlet.Servlet {
	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public ModelServlet() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Servlet#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
		super.destroy();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doDelete(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doDelete(request, response);
	}

	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request,
	 *      HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		handler(request, response);
	}

	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request,
	 *      HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		handler(request, response);
	}

	protected void handler(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		Log.trace("enter");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");

		String req = ParamUtils.getParameter(request, "req");
		if (req == null) // should go to error page
		{
			response.sendRedirect("/error.jsp?msg=req is null");
			return;
		}

		// check session
		if (!new UMClient().checkHttpSession(request)) {
			response.sendRedirect("/error.jsp?msg=check session failed");
			return;
		}

		// get parameter
		String uid = (String) request.getSession().getAttribute("userid");
		if (uid == null) // should go to error page
		{
			response.sendRedirect("/error.jsp?msg=uid is null");
			return;
		}

		String output = (String) ParamUtils.getParameter(request, "output");
		if (output == null) // should go to error page
		{
			// response.sendRedirect("/error.jsp?msg=output is null");
			output = "webpage";
		}

		if (req.equalsIgnoreCase("listModels")) {
			listModels(uid, output, request, response);
			return;
		} else if (req.equalsIgnoreCase("tagModel")) {
			tagModel(uid, output, request, response);
			return;
		} else if (req.equalsIgnoreCase("addRes")) {
			addRes(uid, output, request, response);
			return;
		} else if (req.equalsIgnoreCase("updateRes")) {
			addRes(uid, output, request, response);
			return;
		} else if (req.equalsIgnoreCase("delRes")) {
			delRes(uid, output, request, response);
			return;
		} else if (req.equalsIgnoreCase("listTags")) {
			listTags(uid, output, request, response);
			return;
		}

	}

	public void listModels(String uid, String output,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String ret;
		ret = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" + "<models>"
				+ "<modle name=\"scratchPad\" />" + "<modle name=\"memo\" />"
				+ "<modle name=\"todo\" />" + "<modle name=\"notes\" />"
				+ "</models>";
		response.setContentType("text/xml");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(ret);
	}

	public void listTags(String uid, String output, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Log.trace("enter");
		List<String> list = MyWebResService.instance().listUserTag(uid);
		StringBuffer sb = new StringBuffer(
				"<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" + "<tags>");
		for (int i = 0; i < list.size(); i++)
			sb.append("<tag name=\"" + list.get(i) + "\" />");
		sb.append("</tags>");
		response.setContentType("text/xml");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(sb.toString());
		Log.trace("leave");
	}

	/**
	 * delete resource
	 * 
	 * @param uid
	 * @param output
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void delRes(String uid, String output, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String resId = ParamUtils.getParameter(request, "resId");
		if (resId == null) // should go to error page
		{
			response.sendRedirect("/error.jsp?msg=resid is null");
			return;
		}
		Log.trace("resId=" + resId);
		resId = resId.trim();

		try {
			DataEngine.instance().deleteResource(resId, true);
		} catch (Exception e) {
			Log.error(e);
			response
					.sendRedirect("/error.jsp?msg=delete resource object failed, exp:"
							+ Log.printExpStack(e));
			return;
		}

		response.getWriter().print(
				"<?xml version=\"1.0\" encoding=\"utf-8\"?><return>" + "ok"
						+ "</return>");

	}

	/**
	 * select resource by tag, and display by model
	 * 
	 * @param uid
	 * @param output
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void tagModel(String uid, String output, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// list res by tag and model
		List<ResourceObject> list = new ArrayList<ResourceObject>();
		Attribute attr = new Attribute("u", Attribute.ATTR_DT_INT);
		List<String> res = null;
		try {
			String tag = ParamUtils.getParameter(request, "tag");
			// if (tag == null) // should go to error page
			// {
			// response.sendRedirect("/error.jsp?msg=tag is null");
			// return;
			// }
			String model = ParamUtils.getParameter(request, "model");
			if (model == null) // should go to error page
			{
				response.sendRedirect("/error.jsp?msg=model is null");
				return;
			}

			if (tag == null) {
				list = DataEngine.instance().listROByUserModel(uid, model, 0,
						-1, true,
						new Attribute("total", Attribute.ATTR_DT_INT, 1));

			} else {
				res = DataEngine.instance().listResByTag(tag, 0,
						Integer.MAX_VALUE, attr, true, uid);
				for (int i = 0; i < res.size(); i++) {
					ResourceObject obj = DataEngine.instance()
							.getResourceObjById(res.get(i), true);
					try {
						ResDesObject desobj = DataEngine.instance()
								.getResourceDesObj(res.get(i), model);
						Log.trace("desobj=" + desobj + ",id=" + res.get(i)
								+ " model=" + model);
						if (desobj == null)
							continue;
						obj.addResDesObject(desobj);
					} catch (Exception e) {
						Log.error(e);
					}
					list.add(obj);
				}
			}
			Log.trace("list size = " + list.size());

			if (output.equalsIgnoreCase("xml")) {
				StringBuffer sb = new StringBuffer();
				sb
						.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?><response>");
				response.setContentType("text/xml");
				for (ResourceObject ro : list) {
					sb.append(ro.printXML());
				}
				sb.append("</response>");
				response.getWriter().print(sb);
				return;
			}
			// load template
			Configuration cfg = new Configuration();
			// - Templates are stoted in the WEB-INF/templates directory of the
			// Web app.
			cfg.setServletContextForTemplateLoading(this.getServletContext(),
					"WEB-INF/templates");
			Template t = null;
			t = cfg.getTemplate(model + ".ftl", "utf-8");

			// bind data for template
			Map<String, Object> root = new HashMap<String, Object>();
			ArrayList<ROWrapper> l = new ArrayList<ROWrapper>();
			for (int i = 0; i < list.size(); i++)
				l.add(new ROWrapper(list.get(i)));
			root.put("res_list", l);

			root.put("ret_type", output);

			// response to client
			t.process(root, response.getWriter());

		} catch (Exception e) {
			Log.error(e);
			response.sendRedirect("/error.jsp?msg=internal error");
		}
	}

	public void addRes(String uid, String output, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Log.trace("enter");
		// String tag = ParamUtils.getParameter(request, "tag");
		// if (tag == null) // should go to error page
		// {
		// response.sendRedirect("/error.jsp?msg=tag is null");
		// return;
		// }
		// String model = ParamUtils.getParameter(request, "model");
		// if (model == null) // should go to error page
		// {
		// response.sendRedirect("/error.jsp?msg=model is null");
		// return;
		// }
		String xml = ParamUtils.getParameter(request, "resobj");
		if (xml == null) // should go to error page
		{
			response.sendRedirect("/error.jsp?msg=resobj is null");
			return;
		}
		xml = xml.trim();
		Log.trace("xml=" + xml);

		ResourceObject obj = null;
		try {
			obj = ResourceObject.fromXML(xml);
		} catch (Exception e) {
			Log.error(e);
			response
					.sendRedirect("/error.jsp?msg=load resource object from xml failed, exp:"
							+ Log.printExpStack(e));
			return;
		}
		obj.setUser(uid);
		String resid = null;
		// Log.trace("id="+obj.getResId());
		// Log.trace(obj.printXML());

		try {
			resid = DataEngine.instance().post(obj);
		} catch (Exception e) {
			Log.error(e);
			response
					.sendRedirect("/error.jsp?msg=post resource object failed, exp:"
							+ Log.printExpStack(e));
			return;
		}
		
		response.setContentType("text/xml");
		response.setCharacterEncoding("UTF-8");
		try{
		response.getWriter().print("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>"+
				DataEngine.instance().getResourceObjById(resid, true).printXML());
		}catch(Exception e){
			response.getWriter().print(e.getMessage()+":"+Log.printExpStack(e));
		}

	}

	public void updateRes(String uid, String output,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// String resid = ParamUtils.getParameter(request, "resid");
		// if (resid == null) // should go to error page
		// {
		// response.sendRedirect("/error.jsp?msg=resid is null");
		// return;
		// }
		//		
		// String tag = ParamUtils.getParameter(request, "tag");
		// if (tag == null) // should go to error page
		// {
		// response.sendRedirect("/error.jsp?msg=tag is null");
		// return;
		// }
		// String model = ParamUtils.getParameter(request, "model");
		// if (model == null) // should go to error page
		// {
		// response.sendRedirect("/error.jsp?msg=model is null");
		// return;
		// }

		addRes(uid, output, request, response);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.GenericServlet#init()
	 */
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
	}
}