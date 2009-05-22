package com.ngus.myweb.form;

import org.apache.struts.action.ActionForm;

public class SearchTextForm extends ActionForm{
	private String text = null;
	private String postId = null;

	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
}
