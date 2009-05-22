package com.ngus.myweb.dataobject;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.ngus.dataengine.ResourceObject;
import com.ns.util.IO;


public class BookMark extends MyWebRes{

	public final static String PTYPE_ATTR = "ptype";

	public final static String URL_ATTR = "url";

	public final static String RTYPE_ATTR = "rtype";
	
	public BookMark(String name, String url, String rtype, String parentID, String description, List<String> tags) throws Exception{
		this(name,url,rtype,parentID,description, tags, 0);
	}
	public BookMark(String name, String url, String rtype, String parentID, String description, List<String> tags, int shareLevel) throws Exception{
		super(name, MyWebRes.BOOKMARK_TYPE,parentID, description, tags);		
		this.setURL(url);
		this.setRtype(rtype);
		this.setShareLevel(shareLevel);
	}
	public BookMark(String name, String url, String rtype, String parentID, String description, List<String> tags, int shareLevel, String userID) throws Exception{
		super(name, MyWebRes.BOOKMARK_TYPE,parentID, description, tags, userID);		
		this.setURL(url);
		this.setRtype(rtype);
		this.setShareLevel(shareLevel);
	}
	public BookMark(ResourceObject ro) throws Exception{
		// TODO
		super(ro);
	}

	private void setPtype(String pType) throws Exception{
		this.setAppAttribute(BookMark.PTYPE_ATTR,pType);
	}
	public String getPtype() throws Exception{
		return this.getAttribute(BookMark.PTYPE_ATTR).getStringValue();
	}

	public void setRtype(String rType) throws Exception{
		if(rType==null){
			throw new Exception("rType can not be null");
		}
		ArrayList<String> al = new ArrayList<String>();
		al.add(rType);
		this.getRO().setResourceType(al);
		//this.setAppAttribute(BookMark.RTYPE_ATTR,rType);
	}
	public String getRtype() throws Exception{
		return this.getRO().getResourceType().get(0);
		//return this.getAttribute(BookMark.RTYPE_ATTR).getStringValue();
	}
	
	public void setURL(String url) throws Exception{			
		if(url.indexOf(':')!=-1){
			setPtype(url.substring(0, url.indexOf(':')));
		}
		else{
			url="http://"+url;
			setPtype("http");			
		}
		InputStream is = new ByteArrayInputStream(url.getBytes("UTF-8"));
		this.getRO().setValue(is);	
		
	}
	public String getURL() throws Exception{
		InputStream is = this.getRO().getValue();
		byte[] bs = IO.readInputStream(is);
		String v = new String(bs, "UTF-8");
		return v;
	}
	
	public void setShareLevel(int shareLevel){
		this.getRO().setShareLevel(shareLevel);
	}
	public int getShareLevel(){
		return this.getRO().getShareLevel();
	}
}
