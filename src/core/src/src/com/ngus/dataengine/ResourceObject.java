/*
 * Created on 2005-4-10
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.ngus.dataengine;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

import com.ngus.resengine.IResEngine;
import com.ngus.resengine.ResourceId;
import com.ngus.um.IUser;
import com.ngus.um.http.UserManager;
import com.ns.dataobject.Attribute;
import com.ns.dataobject.DataObject;
import com.ns.dataobject.DataObjectList;
import com.ns.log.Log;
import com.ns.util.IO;
import com.ns.util.XmlUtil;

/**
 * @author I027910
 * 
 * One ResourceObject can contain differet models. Each model reprents one set
 * of attribute which explained by one dictionary. So one resource can be
 * described in several ways by serveral models
 */
public class ResourceObject extends DataObject {

	/**
	 * OBJ name
	 */
	final static String RES_OBJ_NAME = "ro"; // name of all resource data

	// object

	// created by engine
	private String res_id; // id represents one instance of this data object

	// for example http://ngus/jcr/user/a/b#12345
	private long index_id;

	private Timestamp createTime;

	private Timestamp updateTime;

	private int appRefCount; // the number of references from app

	// if it is 0, the resource will be delete.

	// private Object value;

	// set by app
	// private InputStream value;

	private int shareLevel = -1;

	private String user; //owner

	private String title;

	private int storageType = 1; // db, jcr

	private String StoragePath; // /<workspace>/path

	private int type = -1; // 1: text, 2: binary, default value is binary

	private List<String> tags;

	// resource type ( by its usage)
	private List<String> resourceType;

	private String desc;

	// supported resource type: picture, vedio, web page, doc...
	static public String RESTYPE_PIC = "pic"; // picutre, photo

	static public String RESTYPE_VEDIO = "video"; // vedio, movie

	static public String RESTYPE_AUDIO = "audio"; // audio

	static public String RESTYPE_WEBPAGE = "webpage"; // web page

	static public String RESTYPE_DOC = "doc"; // word, excel, pdf...

	static public String RESTYPE_RSS = "rss"; // RSS feed

	// final static String MODULENAME_ATTR_NAME = "AppName"; // name of data
	// object acting as a module

	public String getStoragePath() {
		return StoragePath;
	}

	public void setStoragePath(String storagePath) {
		this.StoragePath = storagePath;
	}

	public int getStorageType() {
		return storageType;
	}

	public void setStorageType(int storageType) {
		this.storageType = storageType;
	}

	public void setStorageType(String p) {
		if (p.equals(IResEngine.STORAGETYPE_JCR))
			this.storageType = IResEngine.STORAGEINTTYPE_JCR;
		else if (p.equals(IResEngine.STORAGETYPE_DB))
			this.storageType = IResEngine.STORAGEINTTYPE_DB;
	}

	public void setValue(InputStream value) throws Exception {
		setValue(IO.readInputStream(value));
	}

	public String getStringValue() {
		return super.getStringValue();
	}
	/**
	 * interface
	 * 
	 */
	public ResourceObject() {
		super(RES_OBJ_NAME);
		IUser user = UserManager.getCurrentUser();//.getSUserId();
		if (user != null)
			this.setUser(user.getSUserId());

	}

	public ResourceObject(String userId) {
		super(RES_OBJ_NAME);
		this.setUser(userId);
	}
	
	public ResourceObject(Element ele) throws Exception{
		super(ele);
	}

	public ResourceObject(ResourceObject ro) throws Exception {
		super(RES_OBJ_NAME);
		this.setCreateTime(ro.getCreateTime());
		this.setAppRefCount(ro.getAppRefCount());
		this.setDefinition(ro.getDefinition());
		this.setDesc(ro.getDesc());
		this.setDisplayName(ro.getDisplayName());
		this.setParent(ro.getParent());
		this.setResId(ro.getResId());
		this.setResourceType(ro.getResourceType());
		this.setShareLevel(ro.getShareLevel());
		this.setTags(ro.getTags());
		this.setTitle(ro.getTitle());
		this.setType(ro.getType());
		this.setUpdateTime(ro.getUpdateTime());
		this.setUser(ro.getUser());
		this.setValue(ro.getValue());
	}

	/**
	 * return all resource descriotion
	 * 
	 * @return
	 */
	public DataObjectList getModels() {
		
		return this.getChildren("rdo");
//		List<DataObjectList> list = getChildren();
//		for (int i = 0; i < list.size(); i++) {
//			DataObjectList dol = list.get(i);
//			if (dol.size() == 0)
//				continue;
//			if (!dol.get(0).getName().equalsIgnoreCase(
//					ResourceObject.RES_OBJ_NAME))
//				ret.add(dol);
//		}
		
	}

	// public DataObjectList getModuleByName(String name){
	// return getChildrenById(MODULE_OBJ_NAME);
	// }

	/**
	 * add one kind of resource description
	 * 
	 * @param ob
	 */
	public void addResDesObject(ResDesObject ob) {
		//		DataObjectList list =  this.getChildren(ob.getModelName());
		//		if (list == null)
		//			addChild(ob);
		//		else
		//		{
		//			list.removeAll();
		//			list.add(ob);
		//		}
		this.removeRDO(ob.getModelName()); // make it unique
		this.addChild(ob);

	}

	public void addChildResource(ResourceObject ob) {
		this.addChild(ob);
	}

	public DataObjectList getChildResourceObjects() {
		return this.getChildren(RES_OBJ_NAME);
	}

	public List<ResDesObject> getResDesObjects(String appName) {
		DataObjectList list = this.getModels();
		Log.trace("model list size="+ list.size());
		List<ResDesObject> ret = new ArrayList<ResDesObject>(list.size());
		for (int i = 0; i < list.size(); i++)
		{
			ResDesObject rdo = (ResDesObject) list.get(i);
			Log.trace("rdo="+rdo.printXML());
			if (rdo.getModelName().equalsIgnoreCase(appName))
				ret.add(rdo);
		}
		return ret;
	}

	public ResDesObject getResDesObject(String appName) {
//		DataObjectList list = this.getChildren(appName);
//		if (list == null || list.size() == 0)
//			return null;
//		return (ResDesObject) list.get(0);
		List<ResDesObject> list = getResDesObjects(appName);
		if (list == null || list.size()<=0)
			return null;
		return list.get(0);
	}

	public String printHtml() {
		Iterator it = attributes.values().iterator();
		Attribute attr;
		String ret = "\r\n<ul><font color=\"#222299\">" + this.getName()
				+ "</font>\r\n<br>";
		ret += "resource id:\t" + this.getResId() + "<br>";
		ret += "index  id:\t" + this.getIndexId() + "<br>";
		ret += "resource type:\t" + this.getType() + "<br>";
		ret += "title:\t" + this.getTitle() + "<br>";
		ret += "share level:\t" + this.getShareLevel() + "<br>";
		ret += "storage type:\t" + this.getStorageType() + "<br>";
		ret += "storage path:\t" + this.getStoragePath() + "<br>";
		ret += "create time:\t" + this.getCreateTime() + "<br>";
		ret += "update time:\t" + this.getUpdateTime() + "<br>";
		try {
			//			ByteArrayInputStream bais = (ByteArrayInputStream) this.getValue();
			byte[] bs = IO.readInputStream(getValue());
			String v = new String(bs, "UTF-8");
			ret += "value:\t" + v + "<br>";
		} catch (Exception e) {
			Log.error(e);
			ret += e.toString();
		}

		while (it.hasNext()) {
			// System.out.println("iterator point " + it.next() + "****");
			// attr = (Attribute)(((Map.Entry)it.next()).getValue());
			attr = (Attribute) (it.next());
			// System.out.println("//// ");
			ret += "<li> " + attr.printHtml() + "</li>\r\n";
		}
		// ret += ">\r\n";
		ret += "</ul>\r\n";
		return ret;
	}
	public void parseXml(Element ele) throws Exception{
		//super.parseXml(ele);
		//String doName = ele.getTagName();
		//ResourceObject ret = new ResourceObject();
		//setValue(ele.getFirstChild().getNodeValue());
		
		NamedNodeMap attrs = ele.getAttributes();
		for (int j=0; j < attrs.getLength(); j++){
			org.w3c.dom.Attr attr = (org.w3c.dom.Attr)attrs.item(j);
			String name = attr.getName();
			String v = attr.getTextContent();
			//Log.trace("parse xml:" + name+"="+v);
			if (name.equalsIgnoreCase("createTime"))
				createTime = Timestamp.valueOf(v);
			else if (name.equalsIgnoreCase("updateTime"))
				updateTime = Timestamp.valueOf(v);
			else if (name.equalsIgnoreCase("shareLevel"))
				shareLevel = Integer.parseInt(v);
			else if (name.equalsIgnoreCase("user"))
				user = v;
			else if (name.equalsIgnoreCase("title"))
				title = v;
			else if (name.equalsIgnoreCase("type"))
				type = Integer.parseInt(v);
			else if (name.equalsIgnoreCase("desc"))
				desc = v;
			else if (name.equalsIgnoreCase("resourceType")){
					String vv = v.substring(1, v.length() - 1);
					String[] s = v.split(",");
					List<String> ls = new ArrayList<String>();
					for (int i = 0; i < s.length; i++) {
						ls.add(s[i].trim());
					}
					resourceType = ls;
				}
			else if (name.equalsIgnoreCase("tags")){						
						String[] s = v.split(",");
						List<String> ls = new ArrayList<String>();
						for (int i = 0; i < s.length; i++) {
							ls.add(s[i].trim());
						}
						tags = ls;
					}
			else if (name.equalsIgnoreCase("resourceTypes")){
				String vv = v.substring(1, v.length() - 1);
				String[] s = v.split(",");
				List<String> ls = new ArrayList<String>();
				for (int i = 0; i < s.length; i++) {
					ls.add(s[i].trim());
				}
				resourceType = ls;
			}
			else if (name.equalsIgnoreCase("res_id"))
							this.setResId(v);
			else if (name.equalsIgnoreCase("storagePath"))
					StoragePath = v;
			else if (name.equalsIgnoreCase("storageType"))
				storageType = Integer.parseInt(v);
			else {								
				Attribute at = new Attribute(name, Attribute.ATTR_DT_STR, attr.getTextContent());
				addAttr(at);
			}
		}// process children ro
		NodeList list = ele.getElementsByTagName("list");
		for (int i = 0; i< list.getLength(); i++){
			Element child = (Element)list.item(i);
			String doName = child.getAttribute("name");
			if (doName.equalsIgnoreCase("rdo"))
			{
				NodeList rdoList = child.getElementsByTagName("rdo");
				for (int n = 0; n < rdoList.getLength(); n++){
					Element rdoNode = (Element)rdoList.item(i);
					ResDesObject c = ResDesObject.fromXML(rdoNode);
					this.addResDesObject(c);
				}
				
			}else if (doName.equalsIgnoreCase("ro")){
				DataObjectList c = new DataObjectList();
				NodeList listRo = child.getElementsByTagName("ro");
				for (int k = 0; k< listRo.getLength(); k++){
					Element cc = (Element)listRo.item(k);
					ResourceObject r  = ResourceObject.fromXML(cc);
					c.add(r);
				}
				addChildren(c);			
			}
			
		}
		
//		if (list.getLength() == 0){
		String value = ele.getTextContent();
		if (value != null)
			setValue(value.getBytes());
//		}
	}
	static public ResourceObject fromXML(Element ele) throws Exception{
		
		//String doName = ele.getTagName();
		ResourceObject ret = new ResourceObject(ele);
		
		ret.parseXml(ele);
		
		return ret;
	}	

	static public ResourceObject fromXML(String xml) throws Exception {
		Document doc = XmlUtil.loadXML(xml);
		Element ele = doc.getDocumentElement();
		return ResourceObject.fromXML(ele);
	}

	public String printXML() {

		Iterator<Attribute> it = attributes.values().iterator();
		Attribute attr;
		//String ret = "\r\n<" + name + " id = \"" + id + "\">\r\n";
		String nodeName = "";
		if (this.getDisplayName() == null || getDisplayName().length() == 0)
			nodeName = this.getName();
		else
			nodeName = getDisplayName();
		String ret = "\r\n<" + nodeName + " res_id=\"" + res_id + "\" "
				+ "createTime=\"" + createTime + "\" " + "updateTime=\""
				+ updateTime + "\" " + "shareLevel=\"" + shareLevel + "\" "
				+ "user=\"" + user + "\" " + "title=\"" + title + "\" "
				+ "type=\"" + type + "\" " + "desc=\"" + desc + "\" "
				+ "resourceType=\"" + resourceType + "\" " + "tags=\"" + tags
				+ "\" ";
		
		
		// attribute
		ArrayList<DataObjectList> children = new ArrayList<DataObjectList>();
		while (it.hasNext()) {
			//	System.out.println("iterator point " + it.next() + "****");
			//	attr = (Attribute)(((Map.Entry)it.next()).getValue());
			attr = (Attribute) (it.next());
			//	System.out.println("//// ");
			if (attr.valueType() != Attribute.ATTR_DT_SUBOBJ)
				ret += " " + attr.getName() + "=\"" + attr.getStringValue()
						+ "\" ";
			else
				children.add((DataObjectList) attr.getValue());
			//ret += ((DataObject)attr.getValue()).printXML();

		}
		ret += ">";
		
//		 value
		if (getValue() != null)
			ret += this.getStringValue();
		
		// children
		for (int i = 0; i < children.size(); i++) {
			DataObjectList dol = children.get(i);
			ret += "\r\n<list name=\"" + dol.objectName() + "\" >\r\n";
			for (int j = 0; j < dol.size(); j++)
				ret += dol.get(j).printXML();
			ret += "</list>\r\n";
		}
		//ret += ">\r\n";


		ret += "</" + nodeName + ">\r\n";
		return ret;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getResId() {
		return res_id;
	}

	/**
	 * if id not null, set the resource id and storage path, storage type, index id according to res id.
	 * if id null, just set the resource id to null
	 * @param id
	 * @throws Exception
	 */
	public void setResId(String id) throws Exception {
		if (id == null || id.length() == 0 || id.trim().length() == 0) {
			this.res_id = null;
			return;
		}
		this.res_id = id;
		
		ResourceId rid = new ResourceId(id);
		this.setStoragePath(rid.getStoragePath());
		this.setStorageType(rid.getStorageType());
		this.setIndexId(rid.getId());
		if (rid.getProtocol().equalsIgnoreCase("text"))
			this.setType(1);
		else if (rid.getProtocol().equalsIgnoreCase("bin"))
			this.setType(2);

		// set id for all rdo
		DataObjectList list = this.getModels();
		if (list != null)
			for (int i = 0; i < list.size(); i++) {
				ResDesObject  rdo = (ResDesObject)list.get(i);
				if (rdo == null)
					continue;
					rdo.setResId(id);
			}
	}

	public long getIndexId() {
		return index_id;
	}

	public void setIndexId(long index_id) {
		this.index_id = index_id;
	}

	public int getShareLevel() {
		return shareLevel;
	}

	public void setShareLevel(int shareLevel) {
		this.shareLevel = shareLevel;
	}

	public void setShareLevel(String l) {
		this.shareLevel = new Integer(l).intValue();
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getAppRefCount() {
		return appRefCount;
	}

	public void setAppRefCount(int appRefCount) {
		this.appRefCount = appRefCount;
	}

	public void removeRDO(String modelName) {
		ResDesObject rdo = this.getResDesObject(modelName);
		if ( rdo != null)
			this.removeChild("rdo", rdo);
	}

	static public void main(String arg[]) throws Exception {
//		ResourceObject ro = new ResourceObject();
//		//ro.setValue("dafda");
//		ResDesObject rdo = new ResDesObject("model1");
//		rdo.addAttr(new Attribute("attr1", Attribute.ATTR_DT_STR, "value1"));
//		ro.addResDesObject(rdo);
		
//		class a{
//			public  void test(){
//				Log.trace("a.test");
//			}
//		}
//		class b extends a{
//			public void test(){
//				Log.trace("b.test");
//			}
//		}
//		b c = new b();
//		c.test();
		/*
		String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?><ro shareLevel=\"0\" title=\"todo\" " +
				"tags=\"1,2,3\" type=\"1\" storageType=\"1\" storagePath=\"/tree/todo\" resourceType=\"doc\">abc<list name=\"rdo\"><rdo modelName=\"todo\" done=\"true\" /></list></ro>";
		Document doc = XmlUtil.loadXML(xml);
		Element ele = doc.getDocumentElement();
		ResourceObject ro = new ResourceObject();
		ro.parseXml(ele);
		Log.trace(ro.printXML());*/
		
		ResourceObject d = new ResourceObject();
		String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
		"<ro shareLevel=\"0\" title=\"todo\" tags=\"1,2,3\" type=\"1\" storageType=\"1\" storagePath=\"/tree/todo\" resourceType=\"doc\">" +
		"周会<list name=\"rdo\" ><rdo modelName=\"todo\" done=\"true\" />" +
		"</list><list name=\"ro\" ><ro aa=\"true\" /></list></ro>";
		xml = new String(xml.getBytes("UTF-8"));
		ResourceObject.fromXML(xml);
		d.setCreateTime(new Timestamp(System.currentTimeMillis()));
		System.out.println(d.getCreateTime());
	   ObjectOutputStream out = new ObjectOutputStream(
			   new BufferedOutputStream(
					   new FileOutputStream("c:\\test.dat",true)));
	   out.writeObject(d);
	   out.close();
	   ObjectInputStream in = new ObjectInputStream(

				new BufferedInputStream(

					 new FileInputStream("c:\\test.dat")));

		  

		   d =(ResourceObject)in.readObject();//读保存过的对象 转型回Date型

		  System.out.println(d.getCreateTime());

		  in.close();	
	}

	public List<String> getResourceType() {
		return resourceType;
	}

	public void setResourceType(List<String> resourceType) {
		this.resourceType = resourceType;
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
		DataObjectList dl =this.getChildResourceObjects();
		for (int i = 0; i< dl.size(); i++){
			((ResourceObject)dl.get(i)).setUser(user);
		}
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
