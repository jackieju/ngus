package com.ngus.myweb.form;

import org.apache.struts.action.ActionForm;

public class AddFriendForm extends ActionForm{

	private int userId = 0;
	private int friendId = 0;
	private String friendName = null;
	public void setUserId(int userId){
		this.userId = userId;
	}
	public int getUserId(){
		return userId;
	}
	
	public void setFriendId(int friendId){
		this.friendId = friendId;
	}
	public int getFriendId(){
		return friendId;
	}

	public void setFriendName(String friendName){
		this.friendName = friendName;
	}
	public String getFriendName(){
		return friendName;
	}
}
