package com.ngus.comment;



public class CommentObject {
	private long commentId;

	private String resourceId;

	private String user;

	private String content;
	
	private String createTime;

	public CommentObject()
	{
		this.commentId = 0;
		this.resourceId = null;
		this.user = null;
		this.content = null;
		this.createTime = null;
	}
	public long getCommentId() {
		return commentId;
	}

	public void setCommentId(long commentId) {
		this.commentId = commentId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
	
	public String getCreateTime() {
		return createTime;
	}
	
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
}
