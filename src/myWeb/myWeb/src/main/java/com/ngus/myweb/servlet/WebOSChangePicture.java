package com.ngus.myweb.servlet;

import java.io.*;
import java.util.List;
import java.util.Iterator;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.commons.fileupload.*;
import com.ngus.myweb.userextension.UserExtensionService;
import com.ns.log.Log;

public class WebOSChangePicture extends javax.servlet.http.HttpServlet implements
javax.servlet.Servlet {

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		//ServletOutputStream out= response.getOutputStream();
		String userName= (String)request.getSession().getAttribute("username");
		
		try{
			DiskFileUpload   fu   =   new   DiskFileUpload();   
		    //   设置允许用户上传文件大小,单位:字节，这里设为2m   
		    fu.setSizeMax(64*1024);   
		    //   设置最多只允许在内存中存储的数据,单位:字节   
		    fu.setSizeThreshold(64*1024);   
		    //   设置一旦文件大小超过getsizethreshold()的值时数据存放在硬盘的目录   
		    fu.setRepositoryPath("c:\\");   
		    //开始读取上传信息   
		    List   fileitems   =   fu.parseRequest(request);
		    //   依次处理每个上传的文件
		    Iterator iter= fileitems.iterator();
		    String fileName= null;
		    FileItem item= null;
		    while(iter.hasNext()){
		    	item= (FileItem)iter.next();
		    	fileName= item.getName();
		    	
		    	if(fileName != null && (fileName.endsWith(".jpg") || fileName.endsWith(".JPG"))){
					int fsize = (int)item.getSize();
					byte[] pic= new byte[fsize];
		    		FilterInputStream in= new DataInputStream(item.getInputStream());
		    		in.read(pic);
		    		
		    		ByteArrayInputStream bis = new ByteArrayInputStream(pic);
					UserExtensionService.getInstance().updateImg(userName,bis,pic.length);
		    		//response.setContentType("image/jpeg");
		    		//out.print("item size= "+ item.getSize()+ "\n byte[] size= "+ pic.length);
		    		response.sendRedirect("webos_userInfo.jsp?");
		    		return;
					//out.print(userName);
		    	}			  
		    }
		}
		catch(Exception e){
			e.printStackTrace();
			response.sendRedirect("webos_userInfo.jsp");
			Log.trace("webos change user picture error!");
			Log.error(e);
			return;
		}
		
		response.sendRedirect("webos_userInfo.jsp");
	}
	
}
