package com.ngus.myweb.form;




import org.apache.struts.action.ActionForm;

import org.apache.struts.upload.FormFile;


public class UserRegForm extends ActionForm{
	private String userName = null;
	private String penName = null;
	private String password = null;
	private String email = null;
	private String mobile= null;
	private FormFile userPic = null;
	private int sex  =0;
	private String validCode = null;
	private String confirmPassword=null; 
	

	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	public String getValidCode() {
		return validCode;
	}
	public void setValidCode(String validCode) {
		this.validCode = validCode;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPenName() {
		return penName;
	}
	public void setPenName(String penName) {
		this.penName = penName;
	}
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
	
}