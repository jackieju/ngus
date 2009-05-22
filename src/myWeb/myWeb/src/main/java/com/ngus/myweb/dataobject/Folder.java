package com.ngus.myweb.dataobject;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.ngus.dataengine.DataEngine;
import com.ngus.dataengine.ResourceObject;
import com.ns.log.Log;

public class Folder extends MyWebRes{

	public Folder(String name, String parentID, String description,List<String> tags) throws Exception{
		this(name,parentID,description, tags, 0);
	}	
	public Folder(String name, String parentID, String description,List<String> tags, int shareLevel) throws Exception{
		
		super(name, MyWebRes.FOLDER_TYPE, parentID, description, tags);		
		InputStream is = new ByteArrayInputStream("".getBytes("UTF-8"));
		this.getRO().setValue(is);
		ArrayList<String> list = new ArrayList<String>(1);
		list.add("folder");
		this.getRO().setResourceType(list);
		this.setShareLevel(shareLevel);
		
	}
	public Folder(String name, String parentID, String description,List<String> tags, int shareLevel, String userID) throws Exception{
		
		super(name, MyWebRes.FOLDER_TYPE, parentID, description, tags, userID);
		InputStream is = new ByteArrayInputStream("".getBytes("UTF-8"));
		this.getRO().setValue(is);
		ArrayList<String> list = new ArrayList<String>(1);
		list.add("folder");
		this.getRO().setResourceType(list);
		this.setShareLevel(shareLevel);
		
	}	
	public Folder(ResourceObject ro) throws Exception{
		super(ro);
	}
	public void addChildRes(MyWebRes res){
		super.addChildAppObject(res);
	}
	
	public void setShareLevel(int shareLevel){
		this.getRO().setShareLevel(shareLevel);
//		try{
//			DataEngine.instance().updateResourceShareLevel(this.getID(), shareLevel, true);
//		}
//		catch(Exception e){
//			Log.error(e);
//		}
	}	
}
