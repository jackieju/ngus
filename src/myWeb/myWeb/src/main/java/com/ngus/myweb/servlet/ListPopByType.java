package com.ngus.myweb.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ngus.myweb.dataobject.MyWebRes;
import com.ngus.myweb.services.MyWebResService;
import com.ngus.myweb.util.ParamUtils;
import com.ngus.um.UMClient;
import com.ns.dataobject.Attribute;
import com.ns.dataobject.DOException;
import com.ns.log.Log;

/**
 * Servlet implementation class for Servlet: Search
 * 
 */
public class ListPopByType extends javax.servlet.http.HttpServlet implements
		javax.servlet.Servlet {

	public ListPopByType() {
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
		
		//
		request.setCharacterEncoding("utf-8");
		
		int currPage = ParamUtils.getIntParameter(request,"currPage",1);
		
		String type = ParamUtils.getParameter(request,"type");
		
		int pageSize = 10;
		
		int totalNum = 0;
		
		int totalPage = 1;
		
		Attribute count = null;
		
		List<Wrapper> al = new ArrayList<Wrapper>();
	
		RequestDispatcher rd = null;
		
//		try {
			count = new Attribute("count", Attribute.ATTR_DT_INT);
//		} catch (Exception e) {
//			Log.error(e);
//		}
			
		al = HomePageCreator.getMostPopularResByTypeWithCount(type, (currPage-1)*pageSize, pageSize, count);
			
		
		
//		totalPage = (int) Math.ceil(Double
//				.parseDouble(count.getStringValue())
//				/ pageSize);
		try{
			Log.trace("count=="+count.getInt());
			Log.trace("totalPage=="+count.getInt());
			totalPage = (count.getInt()-1)/10+1;
		}catch(Exception e){
			Log.error(e);
		}
		request.setAttribute("totalPage",totalPage);
		request.setAttribute("count",count.getValue());
		request.setAttribute("currPage",currPage);
		request.setAttribute("list",al);
		
		if(type.equals("rss")){
			rd = request.getRequestDispatcher("monweb_popRss.jsp");
		}
		else if(type.equals("video")){
			rd = request.getRequestDispatcher("monweb_popVideo.jsp");
		}
		else if(type.equals("pic")){
			rd = request.getRequestDispatcher("monweb_popPic.jsp");
		}
		else if(type.equals("webpage")){
			rd = request.getRequestDispatcher("monweb_popWebPage.jsp");
		}	
		
		rd.forward(request, response);
	
	}
}