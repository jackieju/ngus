/*
 * Created on 2005-4-10
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.ngus.dataengine;

import java.sql.Timestamp;
import java.util.Iterator;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.ns.dataobject.Attribute;
import com.ns.dataobject.DataObject;
import com.ns.log.Log;
import com.ns.util.XmlUtil;

/**
 * @author I027910
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ResDesObject extends DataObject{
	String user;	// owner
//	final static String MODULE_OBJ_NAME = "model";	// name of data object acting as a module
	//String modelName;
//	public ResDesObject(String modelName){
//		//super(MODULE_OBJ_NAME);
//		super(modelName.toLowerCase());
//		
//		
//		//this.modelName =  modelName;
//	}
	public ResDesObject(Element ele) throws Exception{
		super(ele);		
		if (getModelName().length() == 0)
			throw new Exception("didn't find modelName attribute");
	}
	public ResDesObject(String modelName){
		super("rdo");
		try{
			setModelName(modelName);
		}catch(Exception e){
			Log.error(e);
		}
		
		
		//this.modelName =  modelName;
	}
	String resId;
	
	static public ResDesObject fromXML(String xml)throws Exception{
		Document doc = XmlUtil.loadXML(xml);
		Element ele = doc.getDocumentElement();
		
		return fromXML(ele);
	}
	static public ResDesObject fromXML(Element ele)throws Exception{
		ResDesObject rdo = new ResDesObject("");
		rdo.parseXml(ele);
		if (rdo.getModelName().length() == 0)
			throw new Exception("didn't find modelName attribute");
		return rdo;
	}
	public void parseXml(Element ele) throws Exception{
		super.parseXml(ele);	
		setModelName(this.getAttr("modelName").getStringValue());
		removeAttr("modelName");
	}
	
	
	public String getResId() {
		if (resId == null)
			return ((ResourceObject)this.getParent()).getResId();
		return resId;
	}

	public void setResId(String resId) {		
		this.resId = resId;
	}

	String modelName;
	public String getModelName(){
		return modelName;
//		return this.getAttr("modelName").getStringValue();
	}
	public void setModelName(String s) throws Exception{
		//this.addAttr(new Attribute("modelName", Attribute.ATTR_DT_STR , s));
		modelName=s;
	}
	
	public String getUser() {
		if (user != null)			
			return user;
		else
			if (this.getParent() != null)
			{
				String u = ((ResourceObject)getParent()).getUser();
				user = u;
			}
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public static void main(String[] args) {
	}
	
	public String printHtml() {
		Iterator<Attribute> it = attributes.values().iterator();
		Attribute attr;
		String ret = "\r\n<ul><font color=\"#222299\">" + this.getName() + "</font>\r\n";
		ret += "<li> resid = " + this.getResId() + "</li>\r\n";
		while (it.hasNext()) {
			//	System.out.println("iterator point " + it.next() + "****");
			//	attr = (Attribute)(((Map.Entry)it.next()).getValue());
			attr = (Attribute) (it.next());
			//	System.out.println("//// ");
			ret += "<li> " + attr.printHtml() + "</li>\r\n";
		}
		//ret += ">\r\n";
		ret += "</ul>\r\n";
		return ret;
	}
	public Timestamp getCreateTime() throws Exception{
		return (Timestamp)this.getAttr("createTime").getValue();
	}

	public void setCreateTime(Timestamp createTime) throws Exception{
		this.addAttr(new Attribute("createTime", Attribute.ATTR_DT_TMS , createTime));
	}
	
	public Timestamp getUpdateTime() throws Exception{
		return (Timestamp)this.getAttr("updateTime").getValue();
	}

	public void setUpdateTime(Timestamp createTime) throws Exception{
		this.addAttr(new Attribute("updateTime", Attribute.ATTR_DT_TMS , createTime));
	}
};
