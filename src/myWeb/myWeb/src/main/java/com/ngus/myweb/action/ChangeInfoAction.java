package com.ngus.myweb.action;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.regex.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.ngus.myweb.form.ChangeInfoForm;
import com.ngus.myweb.userextension.UserExtensionService;
import com.ngus.um.UMClient;
import com.ngus.um.dbobject.UserInfo;
import com.ns.exception.NSException;
import com.ns.util.Image;

public final class ChangeInfoAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception, NSException {	
		ChangeInfoForm changeForm = (ChangeInfoForm) form;
		
		int error =0;
		String email = request.getParameter("email");
		String mobile = request.getParameter("mobile");
		int id = Integer.parseInt(request.getParameter("id"));
		String userName = changeForm.getUserName();
		FormFile userPic = changeForm.getUserPic();
		if(userPic.getFileSize()!=0){
			InputStream fileInput = userPic.getInputStream();
			int fileSize = userPic.getFileSize();
			if(fileSize>64*1024){
				error++;
				request.setAttribute("perror",1);
			}
			else{
				ByteArrayOutputStream pic = Image.resize(fileInput, 100, 100, "jpeg");
				byte b[] = pic.toByteArray();
				int fsize = b.length;
				ByteArrayInputStream bis = new ByteArrayInputStream(b);
				UserExtensionService.getInstance().updateImg(userName,bis,fsize);
			}
			request.setAttribute("pic",1);
		}
		
		
		UserInfo userInfo = new UserInfo();
		userInfo.setId(id);
		
		Pattern mp = Pattern.compile("(^[0-9]{3,4}/-[0-9]{3,8}$)|(^[0-9]{3,8}$)|(^/([0-9]{3,4}/)[0-9]{3,8}$)|(^0{0,1}13[0-9]{9}$)");
		Matcher mm = mp.matcher(mobile);
		
		
		Pattern ep = Pattern.compile("\\w+(\\.\\w+)*@\\w+(\\.\\w+)+");
		Matcher em = ep.matcher(email);
		
		if(mobile.length()!=0){
			
			if(!mm.matches()){
				error++;
				request.setAttribute("merror",1);
			}
			else{
				userInfo.setMobile(mobile);
			}
			request.setAttribute("mobile",mobile);
		}
		
		if(email.length()!=0){
			
			if(!em.matches()){
				error++;
				request.setAttribute("eerror",1);
			}
			else{
				userInfo.setEmail(email);
			}
			request.setAttribute("email",email);
		}
		
		try{
			new UMClient().updateUserInfo(userInfo);
		
		}catch(Exception e){
			;
		}
		
		
		
		request.setAttribute("error",error);
		return (mapping.findForward("success"));
	}
	
}

