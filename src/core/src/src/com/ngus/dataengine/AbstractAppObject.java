package com.ngus.dataengine;

import java.io.InputStream;
import java.util.List;

import com.ns.dataobject.Attribute;
import com.ns.log.Log;

public abstract class AbstractAppObject {
	ResourceObject ob = new ResourceObject();
	String logicPath = "";
	// get app name (model name in RDF)
	public abstract String getAppName();
	public abstract String getStorageType();
	
	/* the storage path for one specified APP is devided into two parts:
	Base path: it's same for each resource in one app, for example /<workspace>/<appName>/<userId>
	logic path: it's differnt for every resource in one app and specify the logic location of resource, e.g. /earth/asia/china/shanghai/peopleSquare	
	*/
	public abstract String getBaseStoragePath(); 
//	public abstract String getLogicPath();
	public abstract int getType();
	
	public ResourceObject render(){
		ob.setType(getType());
		ob.setStorageType(getStorageType());
		ob.setStoragePath(getBaseStoragePath()+getLogicPath());
		return ob;
	}
	
	public AbstractAppObject(ResourceObject ob) throws Exception{
		this.ob = ob;
		setContent(ob.getValue());
		String storagePath  = ob.getStoragePath();
		Log.trace("base path = " + getBaseStoragePath());
		Log.trace("storagePath = " + storagePath);
		if (!storagePath.trim().toLowerCase().startsWith(getBaseStoragePath().trim().toLowerCase()))
			throw new Exception("the storage path does not start with base path, so this resouce dose not fit path spec of current app.");
		logicPath = storagePath.substring(getBaseStoragePath().length());
	}
	
	public AbstractAppObject(){
		this.ob.addResDesObject(new ResDesObject(getAppName().toLowerCase()));
	}
	
	public InputStream getContent() {
		return ob.getValue();
	}

	public void setContent(InputStream content) throws Exception{
		ob.setValue(content);
	}
	
	public void setShareLevel(int v){
		ob.setShareLevel(v);
	}
	
	public void setTitle(String title){
		ob.setTitle(title);
	}
	public String getTitle(){
		return ob.getTitle();
	}

	public ResourceObject getRO(){
		return ob;
	}
	
	
	public void setAppAttribute(String name, String value) throws Exception{
		ResDesObject rdo = ob.getResDesObject(getAppName().toLowerCase());
		if (rdo == null)
		{
			rdo = new ResDesObject(getAppName().toLowerCase());
			ob.addResDesObject(rdo);
		}
		
		rdo.addAttr(new Attribute(name, Attribute.ATTR_DT_STR, value));
		
	}
	
	public void setAttribute(Attribute attr) throws Exception{
		ResDesObject rdo = ob.getResDesObject(getAppName().toLowerCase());
		if (rdo == null)
		{
			rdo = new ResDesObject(getAppName().toLowerCase());
			ob.addResDesObject(rdo);
		}
		
		rdo.addAttr(attr);
	}
	
	public String getSAttribute(String name) throws Exception{
		return ob.getResDesObjects(getAppName().toLowerCase()).get(0).getAttr(name).getStringValue();
	}
	
	public Attribute getAttribute(String name) throws Exception{
		return ob.getResDesObjects(getAppName().toLowerCase()).get(0).getAttr(name);
	}
	
	public void addChildAppObject(AbstractAppObject aao){
		ob.addChildResource(aao.getRO());
	}
	
	public ResourceObject getChildAppObjectByAttr(Attribute attr) throws Exception{
		return (ResourceObject)ob.getChildByAttr(ResourceObject.RES_OBJ_NAME, attr).get(0);
	}
	
	public ResourceObject getChildAppObjectByAttr(String name, String value) throws Exception{
		return (ResourceObject)ob.getChildByAttr(ResourceObject.RES_OBJ_NAME, new Attribute(name, Attribute.ATTR_DT_STR, value)).get(0);
	}
	
	
	/** 
	 * Interfaces for tag
	 * 
	 */
	public List<String> getTags() {
//		List<ResDesObject> rdos = getRO().getResDesObjects(this.getAppName().toLowerCase());
//		ResDesObject rdo = (ResDesObject)rdos.get(0);
//		if (rdo == null)
//			return null;
//		Attribute attr = rdo.getAttr("tag");
//		if (attr == null)
//			return null;
//		return (List<String>) (attr.getValue());
		return ob.getTags();
	}

	public void setTags(List<String> tag) throws Exception {
//		Attribute attr = new Attribute("tag", Attribute.ATTR_DT_LIST, tag );
//		ResDesObject rdo = ob.getResDesObject(getAppName().toLowerCase());
//		if (rdo == null)
//		{
//			rdo = new ResDesObject(getAppName().toLowerCase());
//			ob.addResDesObject(rdo);
//		}
//		rdo.addAttr(attr);
//		this.tag = ob.getResDesObjects(Clipboard.APP_NAME).get(0).getAttr("tag").getValue();;
		ob.setTags(tag);
	}
// tags functionality was moved ResourceObject
	public String getLogicPath() {
		return logicPath;
	}
	public void setLogicPath(String logicPath) {
		this.logicPath = logicPath;
	}
	
}
