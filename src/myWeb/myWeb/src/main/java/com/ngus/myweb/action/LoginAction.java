package com.ngus.myweb.action;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ngus.myweb.form.LoginForm;
import com.ngus.um.UMClient;
import com.ngus.um.UMSession;
import com.ns.exception.NSException;
import com.ns.log.Log;

public final class LoginAction extends Action {
	
	
	/**
	 * @param args
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception, NSException {

				int log_error_level;
				LoginForm loginform = (LoginForm)form;
				String userName= loginform.getUserName();
				String password= loginform.getPassword();
				String validateCode = loginform.getVaildCode();
				String validateCode2 = (String) request.getSession().getAttribute("validateCode");
				//用户名为空
				if (userName == null || userName.length()==0){
					log_error_level=0;
					ActionForward fail = mapping.findForward("fail") ;
					fail = new ActionForward(fail.getPath()+"?log_error_level="+log_error_level);
					return fail;
				}
				//验证码不匹配
				else if(!validateCode.equalsIgnoreCase(validateCode2)){
					log_error_level =2 ;
					ActionForward fail = mapping.findForward("fail") ;
					fail = new ActionForward(fail.getPath()+"?log_error_level="+log_error_level);
					return fail;
				}
				else if(new UMClient().checkDuplicate(userName, null, null)!=-3){
					log_error_level =3 ;
					ActionForward fail = mapping.findForward("fail") ;
					fail = new ActionForward(fail.getPath()+"?log_error_level="+log_error_level);
					return fail;
					
				}
				//密码输入错误
				else {
						try{
								UMSession ums = new UMClient().logon(userName, password, "");
								Cookie userCookie = new Cookie("sessionId", ums.getSessionId());
								request.getSession().setAttribute("userName", userName);	
								userCookie.setMaxAge(60*60*24*365);
								userCookie.setPath("/");
								response.addCookie(userCookie);
								request.setAttribute("userName",userName);
								return (mapping.findForward("success"));
							
						}catch(Exception e){
							log_error_level =1;
							ActionForward fail = mapping.findForward("fail") ;
							fail = new ActionForward(fail.getPath()+"?log_error_level="+log_error_level);
							return fail;	
						}
						
					
				
					
					
				}
				
				
		
	}
	

}
