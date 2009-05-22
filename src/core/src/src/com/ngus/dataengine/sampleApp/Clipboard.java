package com.ngus.dataengine.sampleApp;

import com.ngus.dataengine.AbstractJCRAppObject;
import com.ngus.dataengine.IDataEngine;
import com.ngus.dataengine.ResourceObject;
import com.ns.log.Log;
import com.ns.util.IO;

public class Clipboard extends AbstractJCRAppObject{
	
	/**
	 * constant
	 */
	static final String APP_NAME = "clipboard";
	
	/**
	 * Attributes
	 */
	
//	InputStream Content;	
//	List<String> tag;
//	String category;
//	Timestamp	createTime;
	
	////////////////////////////////
	// override abstract method
	////////////////////////////////
	@Override
	public int getType() {
		return IDataEngine.RES_TYPE_TEXT;
	}

	@Override
	public String getAppName() {
		return APP_NAME;
	}

	

	@Override
	public String getLogicPath() {		
		return "";
	}


	
	////////////////////////////////
	// app specific interface
	////////////////////////////////
	public String getCategory() throws Exception{
		return this.getAttribute("category").getStringValue();
	}

	public void setCategory(String category) throws Exception {
		this.setAppAttribute("category", category);
	}

		
	////////////////////////////////
	// app specific constructor
	////////////////////////////////
	
	// construct from input
	// you'd better put all parameter in constructor to reduce the code line to create an app object.
	public Clipboard(String content, String category) throws Exception{
		super();
		this.setContent(IO.getInputStream(content, "UTF-8"));
		this.setCategory(category);
	}	
	
	
	// construct from resource object
	public Clipboard(ResourceObject ro) throws Exception{
		super(ro);
	}	
	
	
	// just for demo the tree precessing
	public void addChildClipboard(Clipboard c){
		super.addChildAppObject(c);
	}

//	public Timestamp getCreateTime() throws Exception{
//		return (Timestamp)this.getAttribute("createTime").getValue();
//	}
//
//	public void setCreateTime(Timestamp createTime) throws Exception{
//		this.setAttribute(new Attribute("createTime", Attribute.ATTR_DT_TMS , createTime));
//	}

	static public void main(String arg[]) throws Exception{
		Clipboard c = new Clipboard("haha", "gossip");
		c.setAppAttribute("subject", "haha");
		Log.trace(c.getRO().printXML());
		//Log.trace(c.getRO().printHtml());
	}
	
	

	                  
	
	
	
	

}
