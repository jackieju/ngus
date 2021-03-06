package com.ngus.myweb.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.ngus.myweb.dataobject.BookMark;
import com.ngus.myweb.dataobject.MyWebRes;
import com.ngus.myweb.services.MyWebResService;
import com.ns.log.Log;

public class AddBookMarkForm extends ActionForm{
	private String name = null;
	private String url = null;
	private String type = null;
	private String parentID = null;
	private String description = null;
	private String tags = null;
	private int shareLevel = 1;
	public String getName(){
		return (this.name);
	}
	public void setName(String name){
		this.name = name;
	}
	
	public String getUrl(){
		return this.url;
	}	
	public void setUrl(String url){
		this.url = url;
	}
	
	public String getType(){
		return this.type;
	}
	public void setType(String type){
		this.type = type;
	}
	
	public String getParentID(){
		return this.parentID;
	}
	public void setParentID(String parentID) throws Exception{
		MyWebRes res = null;
		try{
			res = MyWebResService.instance().getInstanceByID(parentID);
		}
		catch(Exception e){			
			Log.error(e);
		}
		if(res instanceof BookMark){
			parentID = res.getParentID();
		}
		this.parentID = parentID;		
	}
	public void setTags(String tags){
		this.tags = tags;
	}
	public String getTags(){
		return this.tags;
	}
	public String getDescription(){
		return this.description;
	}
	public void setDescription(String description){
		this.description = description;
	}
	public int getShareLevel(){
		return this.shareLevel;
	}
	public void setShareLevel(int shareLevel){
		this.shareLevel = shareLevel;
	}
	
	public void reset(ActionMapping mapping, HttpServletRequest request){
		this.name = null;
		this.type= null;
		this.url = null;
		this.parentID = null;
		try{
			request.setCharacterEncoding("UTF-8");
		}
		catch(Exception e){
			System.out.println("request not setCharacterEncoding UTF-8 !");
		}
	}
	
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request){
		ActionErrors errors = new ActionErrors();		
		return errors;
	}
}
