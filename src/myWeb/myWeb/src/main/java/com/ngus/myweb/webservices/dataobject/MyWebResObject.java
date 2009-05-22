package com.ngus.myweb.webservices.dataobject;

public class MyWebResObject {
	private String id;
	private String name;
	private String parentId;
	private String resType;
	private String rtype;
	private String ptype;
	private String URL;
	private String description;
	private String[] tags;	
	public MyWebResObject(){
		
	}
	public MyWebResObject(String id, String name, String parentId, String resType, String description, String[] tags){
		this.id = id;
		this.name = name;
		this.parentId = parentId;
		this.resType = resType;
		this.description = description;
		this.tags = tags;
	}
	public MyWebResObject(String id, String name, String parentId, String resType, String rType, String pType, String URL, String description, String[] tags){
		this(id, name, parentId, resType, description, tags);
		this.rtype = rType;
		this.ptype =pType; 
		this.URL = URL;
	}
	public String getId(){
		return this.id;
	}
	public String getName(){
		return this.name;
	}
	public String getParentId(){
		return this.parentId;
	}
	public String getResType(){
		return this.resType;
	}
	public String getRtype(){
		return this.rtype;
	}
	public String getPtype(){
		return this.ptype;
	}
	public String getURL(){
		return this.URL;
	}
	
	public void setRtype(String rType){
		this.rtype = rType;
	}
	public void setPtype(String pType){
		this.ptype = pType;
	}
	public void setURL(String URL){
		this.URL = URL;
	}
	
	public void setId(String id){
		this.id = id;
	}
	public void setName(String name){
		this.name = name;
	} 
	public void setParentId(String parentId){
		this.parentId = parentId;
	}
	public void setResType(String resType){
		this.resType = resType;
	}
	public void setTags(String tags[]){
		this.tags = tags;
	}
	public String[] getTags(){
		return this.tags;
	}
	public void setDescription(String desc){
		this.description = desc;		
	}
	public String getDescription(){
		return this.description;
	}
}
