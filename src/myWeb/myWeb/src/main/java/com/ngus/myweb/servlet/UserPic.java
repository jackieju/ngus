package com.ngus.myweb.servlet;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ngus.myweb.userextension.UserExtensionService;
import com.ns.log.Log;

/**
 * Servlet implementation class for Servlet: UserPic
 * 
 */
public class UserPic extends javax.servlet.http.HttpServlet implements
		javax.servlet.Servlet {
	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	byte data[] = null;
	


	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		
		try{
			String imagePath = "/img/user.jpg";

			imagePath = getServletContext().getRealPath(imagePath);
			FileInputStream hFile = new FileInputStream(imagePath);
			int i = hFile.available(); // 得到文件大小
			data = new byte[i];
			hFile.read(data); // 读数据
			hFile.close();

			// OutputStream toClient=response.getOutputStream();
			// //得到向客户端输出二进制数据的对象
			// toClient.write(data); //输出数据
			// toClient.close();
			}catch(Exception e){
				Log.error(e);
			}
	}


	public UserPic() {
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
		// TODO Auto-generated method stub
		ServletOutputStream out = response.getOutputStream();
		String userName = request.getParameter("userName");

		response.setContentType("image/jpeg");

		byte[] userPic = getBlob(userName);
		if (userPic != null && userPic.length != 0) {
			out.write(userPic);
		} else {
			out.write(data); // default image
		}

		out.flush();
		out.close();
	}

	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request,
	 *      HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	public byte[] getBlob(String userName) {

		// log.info(sql);
		Blob blob = null;
		byte[] bytes = null;
		// String description="";

		blob = UserExtensionService.selectImg(userName);

		try {
			bytes = blob.getBytes(1, (int) (blob.length()));
		} catch (Exception e) {
			if(blob == null)
				Log.trace("blob is null!!");
			Log.trace("UserPic.java user name is:"+ userName);
			Log.error(e);
		}
		return bytes;
	}

}