package com.ngus.myweb.servlet;

import java.io.*;
import java.sql.Blob;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.AffineTransformOp;
import java.awt.geom.AffineTransform;



import com.ngus.myweb.userextension.UserExtensionService;
import com.ngus.um.*;
import com.ns.log.Log;

public class GetUserPictureServlet extends javax.servlet.http.HttpServlet implements
javax.servlet.Servlet {

	private UMSession ums = null;
	private String sessionId = null;
	private String userName= null;
	
	private  byte[] getDelineation(byte[] pic){
		
		 try{
		      
	            AffineTransform transform = new AffineTransform();
	            BufferedImage bis = ImageIO.read(new ByteArrayInputStream(pic));

	            int w = bis.getWidth();
	            int h = bis.getHeight();

	            int nw = 60;
	            int nh = (nw * h) / w;
	            if(nh>60) {
	                nh = 60;
	                nw = (nh * w) / h;
	            }
	            
	            double sx = (double)nw / w;
	            double sy = (double)nh / h;

	            transform.setToScale(sx,sy);

	            AffineTransformOp ato = new AffineTransformOp(transform, null);
	            BufferedImage bid = new BufferedImage(nw, nh, BufferedImage.TYPE_3BYTE_BGR);
	            ato.filter(bis,bid);
	            
	            ByteArrayOutputStream byteArrayOutputStream= new ByteArrayOutputStream();
	            ImageIO.write(bid, "jpeg", byteArrayOutputStream);
	            
	            return byteArrayOutputStream.toByteArray();
	            //out.write(new ByteArrayInputStream(bid));
	        } catch(Exception e) {
	            e.printStackTrace();
	            return null;
	        }
	}
	
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ServletOutputStream out = response.getOutputStream();
		checkSession(request, response);
		response.setContentType("image/jpeg");

		byte[] userPic = getBlob(userName);
		if (userPic != null && userPic.length != 0) {
			userPic= getDelineation(userPic);
			out.write(userPic);
		} else {

			String imagePath = "/img/user.jpg";

			imagePath = getServletContext().getRealPath(imagePath);
			FileInputStream hFile = new FileInputStream(imagePath);
			int i = hFile.available(); // 
			byte data[] = new byte[i];		
			hFile.read(data); //
			hFile.close();
			
			data= getDelineation(data);
			out.write(data);

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
		doGet(request, response);
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
			Log.error(e);
		}
		return bytes;
	}
	
	private void checkSession(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	

		Cookie[] cookies = request.getCookies();
		if(cookies!=null){
			Cookie cookie;
			for(int i=0; i<cookies.length; i++) {
				cookie = cookies[i];
				if (cookie.getName().compareTo("sessionId")==0){
					sessionId = cookie.getValue();
					
				}
			}
			
			if (sessionId!=null){
				try{
					ums = new UMClient().checkSession(sessionId);
					userName = ums.getUser().getUserName();

				}catch(Exception e){
					response.sendRedirect("webos_signin.html");
				}
			}else{
				response.sendRedirect("webos_signin.html");
			}
			
		}
		else{
			response.sendRedirect("webos_signin.html");
		}	
	}
}
