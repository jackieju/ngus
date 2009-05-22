package com.ngus.myweb.form;

import org.apache.struts.action.ActionForm;

public class VoteForm extends ActionForm{

	private String url = null;
	public void setUrl(String url){
		this.url = url;
	}
	public String getUrl(){
		return url;
	}
}
