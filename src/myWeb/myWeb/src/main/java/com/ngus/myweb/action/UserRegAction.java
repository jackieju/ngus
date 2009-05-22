package com.ngus.myweb.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.upload.FormFile;

import com.ngus.dataengine.DBConnection;
import com.ngus.myweb.form.UserRegForm;
import com.ngus.myweb.userextension.UserExtensionService;
import com.ngus.um.*;
import com.ngus.um.dbobject.*;
import com.ns.exception.NSException;
import com.ns.util.Image;

public final class UserRegAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception, NSException {	
		UserRegForm regForm = (UserRegForm) form;
		String userName = regForm.getUserName();
		String penName = regForm.getPenName();
		String password = regForm.getPassword();
		String email = regForm.getEmail();
		String mobile = regForm.getMobile();
		FormFile userPic = regForm.getUserPic();
		int sex = regForm.getSex();
		String vaildCode = regForm.getValidCode();
		String validCodeConfirm = (String) request.getSession().getAttribute("validateCode");
		String confirmPassword = regForm.getConfirmPassword();
		
		
		int checkName = new UMClient().checkDuplicate(userName,null,null);
		if(checkName == -3){
			int log_error_level =0;
			ActionForward fail = mapping.findForward("fail") ;
			fail = new ActionForward(fail.getPath()+"?log_error_level="+log_error_level);
			return fail;
		}
		
		int checkPenName = new UMClient().checkDuplicate(null,penName,null);
		if(checkPenName== -4){
			int log_error_level =1;
			ActionForward fail = mapping.findForward("fail") ;
			fail = new ActionForward(fail.getPath()+"?log_error_level="+log_error_level);
			return fail;
		}
		
		if(!password.equals(confirmPassword)){
			int log_error_level =2;
			ActionForward fail = mapping.findForward("fail") ;
			fail = new ActionForward(fail.getPath()+"?log_error_level="+log_error_level);
			return fail;
		}
		
		
		if(!vaildCode.equals(validCodeConfirm)){
			int log_error_level =3;
			ActionForward fail = mapping.findForward("fail") ;
			fail = new ActionForward(fail.getPath()+"?log_error_level="+log_error_level);
			return fail;	
		
		}
		
	
		
		Pattern mp = Pattern.compile("(^[0-9]{3,4}/-[0-9]{3,8}$)|(^[0-9]{3,8}$)|(^/([0-9]{3,4}/)[0-9]{3,8}$)|(^0{0,1}13[0-9]{9}$)");
		Matcher mm = mp.matcher(mobile);
		
		Pattern ep = Pattern.compile("\\w+(\\.\\w+)*@\\w+(\\.\\w+)+");
		Matcher em = ep.matcher(email);
		
		if(!mm.matches()&&mobile.length()!=0){
			int log_error_level =4;
			ActionForward fail = mapping.findForward("fail") ;
			fail = new ActionForward(fail.getPath()+"?log_error_level="+log_error_level);
			return fail;	
		}
		if(!em.matches()){
			int log_error_level =5;
			ActionForward fail = mapping.findForward("fail") ;
			fail = new ActionForward(fail.getPath()+"?log_error_level="+log_error_level);
			return fail;	
		}
		
		UserInfo userinfo = new UserInfo();
		userinfo.setUserName(userName);
		userinfo.setNickName(penName);
		userinfo.setPwd(password);
		userinfo.setEmail(email);
		if(mobile!=null||!mobile.equals(""))
			userinfo.setMobile(mobile);
		if(sex!=-1){
			userinfo.setSex(sex);
		}
		new UMClient().register(userinfo);

		if(userPic != null && userPic.getFileSize() > 0){
			InputStream fileInput = userPic.getInputStream();
			int fileSize = userPic.getFileSize();
			if(fileSize>64*1024){
				int log_error_level =6;
				ActionForward fail = mapping.findForward("fail") ;
				fail = new ActionForward(fail.getPath()+"?log_error_level="+log_error_level);
				return fail;	
			}
			else{
				ByteArrayOutputStream pic = Image.resize(fileInput, 100, 100, "jpeg");
				byte b[] = pic.toByteArray();
				int size = b.length;
				ByteArrayInputStream bis = new ByteArrayInputStream(b);
				UserExtensionService.getInstance().addUserExtension(userName, bis, size);
			}
		}
		else{
			UserExtensionService.addUserExtension(userName, new ByteArrayInputStream(new byte[0]), 0);
		}
			
		ActionForward success = mapping.findForward("success") ;
		success = new ActionForward(success.getPath()+"?userName="+userName+"&penName="+penName);
		return success;
	}	
}
