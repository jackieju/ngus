package com.ngus.myweb.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ngus.config.SystemProperty;
import com.ngus.myweb.form.ForgetPasswordForm;
import com.ngus.um.UMClient;
import com.ns.exception.NSException;
import com.ns.log.Log;
import com.ns.mail.MailSender;
import com.ns.mail.MailSettings;

public final class ForgetPasswordAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception, NSException {
			ForgetPasswordForm forgetform = (ForgetPasswordForm) form;
			int log_error_level ;
			try{
				String userName = forgetform.getUserName();
				String validCode = forgetform.getValidCode();
				HttpSession session = request.getSession();
				String validSession = (String)session.getAttribute("validateCode");
				if (!validCode.equals(validSession)){
					log_error_level =2 ;
					ActionForward fail = mapping.findForward("fail") ;
					fail = new ActionForward(fail.getPath()+"?log_error_level="+log_error_level);
					return fail;
				}

				String strNewPasswd = new UMClient().resetPassword(userName);
				
				MailSettings mail = new MailSettings();	
//				mail.smtpHost = SystemProperty.getProperty("mail.smtp.host");
//				mail.smtpPort = SystemProperty.getProperty("mail.smtp.port");
				mail.ccAddresses = null;
				mail.fromAddress = "admin@monweb.cn";
				mail.fromName = "monWeb";
				mail.toAddress = new UMClient().getUserInfoByUserName(userName).getEmail();
				mail.messageBody = "Dear user " + userName + ", your new password is "+ strNewPasswd;
				mail.messageSubject = "Your password is reset";
				MailSender.sendMailThreaded(mail);
				return (new ActionForward(mapping.findForward("success").getPath()+"?mail="+mail.toAddress));
				
			}catch(Exception e){
				Log.error(e);
				log_error_level =2 ;
				ActionForward fail = mapping.findForward("fail") ;
				fail = new ActionForward(fail.getPath()+"?log_error_level="+log_error_level);
				return fail;
			}
					
		
	}

}
