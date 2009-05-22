package com.ngus.myweb.servlet;

public class Comment {
	String userName;
	String comment;
	String submitTime;
	
	public Comment(String userName, String comment, String submitTime){
		this.setComment(comment);
		this.setUserName(userName);
		this.setSubmitTime(submitTime);
	}
	
	public void setUserName(String name){
		this.userName= name;
	}
	public String getUserName(){
		return this.userName;
	}
	public void setComment(String comment){
		this.comment= comment;
	}
	public String comment(){
		return this.comment;
	}
	public void setSubmitTime(String time){
		this.submitTime= time;
	}
}
