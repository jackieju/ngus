package com.ngus.myweb.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.ngus.um.UMClient;
import com.ngus.um.UMSession;
import com.ngus.um.User;
import com.ngus.um.http.UserManager;


public class ChangeInfoForm extends ActionForm{
	UMClient tempclient = new UMClient();
	int userId ;
//	
//	User tep =null;
//	
//	userId =UserManager.getCurrentUser().getUserId();
//
//	tep = tempclient.getUserByUserId(userId);
	
	private String email;
	private String mobile;
	private FormFile userPic;
	private int id;
	private String userName;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public FormFile getUserPic() {
		return userPic;
	}
	public void setUserPic(FormFile userPic) {
		this.userPic = userPic;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	
	
}