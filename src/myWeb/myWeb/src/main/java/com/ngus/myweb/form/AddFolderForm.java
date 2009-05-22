package com.ngus.myweb.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class AddFolderForm extends ActionForm{
	private String name = null;
	private String parentID = null;
	private String description = null;
	private int shareLevel = 0;
	public String getName(){
		return (this.name);
	}
	public void setName(String name){
		this.name = name;
	}
	
	public String getParentID(){
		return this.parentID;
	}
	public void setParentID(String parentID){
		this.parentID = parentID;
	}
	
	public String getDescription(){
		return this.description;
	}
	public void setDescription(String description){
		this.description = description;
	}
	
	public void reset(ActionMapping mapping, HttpServletRequest request){
		this.name = null;
		this.parentID = null;
		try{
			request.setCharacterEncoding("UTF-8");
		}
		catch(Exception e){
			System.out.println("request not setCharacterEncoding UTF-8 !");
		}
	}
	public int getShareLevel(){
		return this.shareLevel;
	}
	public void setShareLevel(int shareLevel){
		this.shareLevel = shareLevel;
	}
	
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request){
		ActionErrors errors = new ActionErrors();
		
		return errors;
	}
}
