package com.ngus.message;


public class MessageObject {
	private int messageId = 0;
	private String postUserId = null;
	private String receiveUserId = null;
	private String title = null;
	private String content = null;
	private boolean isReaded = false;
	private String createTime = null;
	private boolean postShow = true;
	private boolean receiveShow = true;
	

	public MessageObject(){
		this.postUserId = null;
		this.receiveUserId = null;
		this.title = null;
		this.content = null;
		this.isReaded = false;
		this.createTime = null;
		this.postShow = true;
		this.receiveShow = true;
		
		
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getPostUserId() {
		return postUserId;
	}
	public void setPostUserId(String postUserId) {
		this.postUserId = postUserId;
	}
	public String getReceiveUserId() {
		return receiveUserId;
	}
	public void setReceiveUserId(String receiveUserId) {
		this.receiveUserId = receiveUserId;
	}
	public int getMessageId() {
		return messageId;
	}
	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public boolean isReaded() {
		return isReaded;
	}
	public void setReaded(boolean isReaded) {
		this.isReaded = isReaded;
	}
	public boolean isPostShow() {
		return postShow;
	}
	public void setPostShow(boolean postShow) {
		this.postShow = postShow;
	}
	public boolean isReceiveShow() {
		return receiveShow;
	}
	public void setReceiveShow(boolean receiveShow) {
		this.receiveShow = receiveShow;
	}
	
	
}
