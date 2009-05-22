package com.ngus.myweb.action;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.ngus.dataengine.DBConnection;
import com.ngus.myweb.form.ChangePasswordForm;
import com.ngus.myweb.form.UserRegForm;
import com.ngus.um.*;
import com.ngus.um.dbobject.*;

public final class ChangePasswordAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		ChangePasswordForm changeForm = (ChangePasswordForm)form;
		int log_error_level ;
		
		String password = changeForm.getPassword();
		String validCode = changeForm.getValidCode();
		HttpSession session = request.getSession();
		String validSession = (String)session.getAttribute("validateCode");
		System.out.println(validCode+"======="+validSession);
		
		if (!validCode.equals(validSession)){
			log_error_level =2 ;
			ActionForward fail = mapping.findForward("fail") ;
			fail = new ActionForward(fail.getPath()+"?log_error_level="+log_error_level);
			return fail;
		}
		
		
		String SSSESSIONID = "";

		String ClientId = request.getRemoteAddr();

		Cookie[] cookies = request.getCookies();
		Cookie cookie;
		for (int i = 0; i < cookies.length; i++) {
			cookie = cookies[i];
			if (cookie.getName().compareTo("sessionId") == 0) {
				SSSESSIONID = cookie.getValue();
			}
		}
		
		new UMClient().changePasword(SSSESSIONID, password);
		
	
		
		
		return (mapping.findForward("success"));
		
	}	
}
