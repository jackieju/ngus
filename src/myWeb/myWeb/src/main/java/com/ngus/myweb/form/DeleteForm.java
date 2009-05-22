package com.ngus.myweb.form;

import org.apache.struts.action.ActionForm;

public class DeleteForm extends ActionForm{
	private String id = null;
	
	public String getId(){
		return this.id;
	}
	public void setId(String id){
		this.id = id;
	}
}
