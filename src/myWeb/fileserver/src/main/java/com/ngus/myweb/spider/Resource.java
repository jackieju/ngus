package com.ngus.myweb.spider;

public class Resource {
	String resId;

	String url;

	String title;
	
	String type;

	public String getResId() {
		return resId;
	}

	public void setResId(String resId) {
		this.resId = resId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Resource(String resId, String url) {
		super();
		// TODO Auto-generated constructor stub
		this.resId = resId;
		this.url = url;
	}
}
