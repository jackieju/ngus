package com.ngus.myweb.dataobject;

import java.util.List;

import com.ngus.dataengine.AbstractJCRAppObject;
import com.ngus.dataengine.DataEngine;
import com.ngus.dataengine.IDataEngine;
import com.ngus.dataengine.ResourceObject;
import com.ngus.myweb.Constant;
import com.ngus.um.http.UserManager;
import com.ns.dataobject.DataObjectList;


public abstract class MyWebRes extends AbstractJCRAppObject{

	public final static String ID_ATTR = "id";
	
	public final static String NAME_ATTR = "name";

	public final static String PARENTID_ATTR = "parentid";

	public final static String TYPE_ATTR = "type";
	
	public final static String RTYPE_ATTR = "rtype";

	public final static String PTYPE_ATTR = "ptype";

	public final static String DESCRIPTION_ATTR = "discription";
	
	public final static String FOLDER_TYPE = "folder";

	public final static String BOOKMARK_TYPE = "bookmark";

	public final static String APP_NAME= Constant.APP_NAME;
	
	private int addCount = 0; // count to be added to myWeb
	
	private int checked = 0; // check status
	
	
	public int getChecked() {
		return checked;
	}

	public void setChecked(int checked) {
		this.checked = checked;
	}

	public int getAddCount() {
		return addCount;
	}

	public void setAddCount(int addCount) {
		this.addCount = addCount;
	}

	public MyWebRes(ResourceObject ro) throws Exception{
		super(ro);
	}
	
	public MyWebRes(){
		
	}
	
	public MyWebRes(String name, String resType, String parentID, String description, List<String> tags) throws Exception{
		this(name, resType, parentID, description, tags, UserManager.getCurrentUser().getSUserId());
	}
	public MyWebRes(String name, String resType, String parentID, String description, List<String> tags, String userID) throws Exception{
		super();		
		this.setName(name);
		this.setResType(resType);
		this.setParentID(parentID);
		this.setTags(tags);
		this.setDescription(description);
		//String logicPath = UserManager.getCurrentUser().getUserId();
		if(!"".equalsIgnoreCase(parentID) && parentID != null){			
			ResourceObject ro = DataEngine.instance().getResourceObjById(parentID,false);
			if(ro==null){
				throw new Exception("The parentID:"+ parentID+" doesn't exist");
			}
			String logicPathParent = ro.getStoragePath();			
			this.setLogicPath(logicPathParent.substring(this.getBaseStoragePath().length())+"/"+ro.getIndexId());
		}
		else{
			this.setLogicPath("/" +  userID);
		}
	}
	
	@Override
	public int getType() {		
		return IDataEngine.RES_TYPE_TEXT;
	}

	@Override
	public String getAppName() {		
		return APP_NAME;
	}

	
	public void setID(String ID) throws Exception{		
		this.getRO().setResId(ID);
	}

	public String getID() throws Exception{
		return this.getRO().getResId();
	}	
	public void setName(String name) throws Exception{
		if(name==null){
			throw new Exception("name can not be null");
		}
		this.setTitle(name);
		this.setAppAttribute(MyWebRes.NAME_ATTR,name);
	}
	public String getName() throws Exception{
		//return this.getTitle();
		return this.getAttribute(MyWebRes.NAME_ATTR).getStringValue();
	}

	public void setParentID(String parentID) throws Exception {
		if(parentID==null){
			throw new Exception("parentID can not be null");
		}
		this.setAppAttribute(MyWebRes.PARENTID_ATTR, parentID);
	}

	public String getParentID() throws Exception{
		return this.getAttribute(MyWebRes.PARENTID_ATTR).getStringValue();
	}

	public void setResType(String type) throws Exception{
		if(type==null){
			throw new Exception("type can not be null");
		}
		this.setAppAttribute(MyWebRes.TYPE_ATTR, type);
	}

	public String getResType() throws Exception{
		return this.getAttribute(MyWebRes.TYPE_ATTR).getStringValue();
	}
	
	public DataObjectList getChildren(){
		return this.getRO().getChildResourceObjects();
	}
	public void setDescription(String desc) throws Exception{
		if(desc==null){
			desc = "no description";
		}
		this.setAppAttribute(MyWebRes.DESCRIPTION_ATTR,desc);
		this.getRO().setDesc(desc);
	}
	
	public String getDescription() throws Exception{
		return this.getAttribute(MyWebRes.DESCRIPTION_ATTR).getStringValue();
		//return this.getRO().getDesc();
	}
	
	//public abstract void setShareLevel(int shareLevel) throws Exception;
}
