package com.ngus.myweb.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;


public class LoginForm extends ActionForm{
	private String userName = null;
	private String password = null;
	private String vaildCode = null;
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getVaildCode() {
		return vaildCode;
	}
	public void setVaildCode(String vaildCode) {
		this.vaildCode = vaildCode;
	}

	
	
	
	
	
	
	
}