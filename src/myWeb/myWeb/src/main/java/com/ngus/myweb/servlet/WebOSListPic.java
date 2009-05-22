package com.ngus.myweb.servlet;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ngus.message.MessageEngine;
import com.ngus.message.MessageObject;
import com.ngus.myweb.util.ParamUtils;
import com.ns.dataobject.Attribute;
import com.ns.dataobject.DOException;
import com.ns.exception.NSException;
import com.ngus.myweb.dataobject.BookMark;
import com.ngus.myweb.dataobject.MyWebRes;
import com.ngus.myweb.services.*;
import com.ngus.myweb.webservices.*;
import com.ngus.um.http.UserManager;

/**
 * Servlet implementation class for Servlet: ListMessage
 *
 */
 public class WebOSListPic extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public WebOSListPic() {
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
		
		int pageSize = 36;
		
		int totalNum = 0;
		
		int totalPage = 1;
		
		Attribute count = null;
		
		
		
		String userId = ParamUtils.getParameter(request,"userId");
		
		String tag = ParamUtils.getParameter(request,"tag");
		
	
		String type = "pic";
		
		RequestDispatcher rd = null;
		String newTag= null;
		

		/*if(tag == null){
			try {
				count = new Attribute("name", new Integer(0));
			} catch (DOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			picList = MyWebResService.instance().getByType("pic",(currPage-1)*pageSize,pageSize);
			
			rd = request.getRequestDispatcher("monweb_pic.jsp");
		}
		
		else{*/
			try {
				
				count = new Attribute("name", new Integer(0));
			} catch (DOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			List<PicChecked> al = new ArrayList<PicChecked>();
			List<MyWebRes> picList = null;
			if(tag!=null){
				tag= URLDecoder.decode(tag, "utf-8");
				newTag = new String(tag.getBytes("ISO8859_1"));
				picList = MyWebResService.instance().listByTypeTag("pic",tag,(currPage-1)*pageSize,pageSize,userId,count);
			}else{
				picList = MyWebResService.instance().listByTypeTag("pic",tag,(currPage-1)*pageSize,pageSize,userId,count);
			}
		
		for (int i = 0; i< picList.size(); i++){
				PicChecked tmp = new PicChecked();
				MyWebRes res = (MyWebRes)picList.get(i);
				
				tmp.setRes(res);
				tmp.setChecked(res.getChecked());
				tmp.getPicUrl();
				tmp.setCreateTime(new Date());
				al.add(tmp);
			}
			
			rd = request.getRequestDispatcher("webos_picByTag.jsp");
		//}
		totalPage = (int) Math.ceil(Double
				.parseDouble(count.getStringValue())
				/ pageSize);
		
		request.setAttribute("currPage",currPage);
		request.setAttribute("totalPage",totalPage);
		request.setAttribute("count",count.getValue());
		request.setAttribute("list",al);

		rd.forward(request, response);
		
	}   	  	    
}