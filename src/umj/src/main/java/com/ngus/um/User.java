package com.ngus.um;

import java.sql.Timestamp;

import com.ngus.um.dbobject.UserInfo;

public class User implements com.ngus.um.IUser{
	
	int userId = -1;
	String userName = null;
	String nickName = null;
	int sex = -1;
	String email = null;
	String mobile = null;
	Timestamp lastLogonTime = null;
	
	public Timestamp getLastLogonTime() {
		return lastLogonTime;
	}

	public void setLastLogonTime(Timestamp lastLogonTime) {
		this.lastLogonTime = lastLogonTime;
	}

	public User(){
		
	}
	
	public String getSUserId() {
		return String.format("%018d", new Object[]{userId});
	}

	public  User(UserInfo ubi){
		this.setNickName(ubi.getNickName());
		this.setSex(ubi.getSex());
		this.setUserName(ubi.getUserName());
		this.setUserId(ubi.getId());
		this.setMobile(ubi.getMobile());
		this.setLastLogonTime(ubi.getLastLogonTime());
		this.setEmail(ubi.getEmail());
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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
