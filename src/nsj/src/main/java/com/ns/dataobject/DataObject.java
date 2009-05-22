/*
 * Created on Dec 4, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ns.dataobject;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

import com.ns.log.Log;
import com.ns.util.IO;
import com.ns.util.XmlUtil;


/**
 * @author Jackie Ju
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class DataObject implements Serializable{
	// should be private, but DAL is not willing to use addChild but access the attributes directly
	public HashMap<String, Attribute> attributes = new HashMap(); // list of attributes 
	String name;							// name represents one class of this data object
	String displayName;
	
	//	String desc;
	String keyAttributesName[] = null;	// names of key attributes
	DataObject parent = null;
	DataObjectDesc definition = null;
	private byte[] value = null;		// value of this object
	private int valueType = Attribute.ATTR_DT_UNK;
	//private Timestamp tm_create = new Timestamp(System.currentTimeMillis());
	private Timestamp tm_setValue = null; 
	
	/**
	 * ctor
	 */
	//	public DataObject(String n, String d) 
	//	{
	//		name = n;
	//		desc = d;
	//	}

	public void setDefinition(DataObjectDesc d){
		definition = d;
		
	}
	public DataObjectDesc getDefinition(){
		return definition;
	}
	
	public DataObject(String n) {
		name = n;
	}
	public void setParent(DataObject p)
	{
		parent = p;
	}
	public DataObject getParent(){
		return parent;
	}
	
	
	public void parseXml(Element ele) throws Exception{
		String doName = ele.getTagName();
		
		NamedNodeMap attrs = ele.getAttributes();
		for (int j=0; j < attrs.getLength(); j++){
			org.w3c.dom.Attr attr = (org.w3c.dom.Attr)attrs.item(j);
			Attribute at = new Attribute(attr.getName(), Attribute.ATTR_DT_STR, attr.getTextContent());
			this.addAttr(at);
		}
	
		NodeList list = ele.getElementsByTagName("list");
		for (int i = 0; i< list.getLength(); i++){
			Element child = (Element)list.item(i);
			DataObjectList c = DataObjectList.fromXML(child);
			this.addChildren(c);
		}
		
		if (list.getLength() == 0){
		String value = ele.getTextContent();
		if (value != null)
			this.setValue(value.getBytes());
		}		
	}
	
	public DataObject(Element ele) throws Exception{
		String doName = ele.getTagName();
		this.name = doName;
		Log.trace("data object name: "+ doName);
		this.parseXml(ele);
		
	}

	
	
	static public DataObject fromXML(Element ele) throws Exception{
	
		String doName = ele.getTagName();
		DataObject ret = new DataObject(ele);
		
		ret.parseXml(ele);
		return ret;
	}
	static public DataObject fromXML(String xml) throws Exception{
		Document doc = XmlUtil.loadXML(xml);
		Element ele = doc.getDocumentElement();
		return fromXML(ele);
	}

	/**
	 * Add one attribute to attribute list
	 * @param attr
	 * @return	return old object with the same attribute name
	 */
	public Attribute addAttr(Attribute attr) throws DOException {
		//Log.trace(attr.toString());
		//Log.trace(attr.getName());
		//Log.trace(attributes + "");
		if (attr.valueType() == Attribute.ATTR_DT_SUBOBJ)
			throw new DOException("can not add a child node,you must use addChild to append child node");		// you must use addChild to append child node
		return (Attribute) attributes.put(attr.getName(), attr);
	}

	/**
	 * Get attribute by attribute name
	 * @param AttrName	Attribute name
	 * @return
	 */
	public Attribute getAttr(String AttrName) {
		return (Attribute) attributes.get((Object) AttrName);
	}
	
	public Attributes getAttr(String[] attrNames) {
		Attributes attrs = new Attributes();
		for (int i = 0; i < attrNames.length; i++){
			attrs.addAttr(getAttr(attrNames[i]));
		}
		return attrs;
	}
	
	public HashMap attrs() {
		return attributes;
	}
	/**
	 * Remove one attribute object
	 * @param AttrName	Attribute name
	 */
	public void removeAttr(String AttrName) {
		attributes.remove((Object) AttrName);
	}

	/**
	 * Return attribute number
	 * @return	the size of attribute list
	 */
	public int size() {
		return attributes.size();
	}

	/**
	 * remove all attribute from attributes
	 */
	public void clear() {
		attributes.clear();
	}

	/**
	 * print content of this dataObject to string
	 * @return
	 */
	public String printXML() {
		Iterator<Attribute> it = attributes.values().iterator();
		Attribute attr;
		//String ret = "\r\n<" + name + " id = \"" + id + "\">\r\n";
		String nodeName = "";
		if (displayName == null || displayName.length() == 0)
			nodeName = name;
		else
			nodeName = displayName;
		String ret = "\r\n<" +  nodeName  + " ";;
		
		// attribute
		ArrayList<DataObjectList> children = new ArrayList<DataObjectList> ();
		while (it.hasNext()) {
			//	System.out.println("iterator point " + it.next() + "****");
			//	attr = (Attribute)(((Map.Entry)it.next()).getValue());
			attr = (Attribute) (it.next());
			//	System.out.println("//// ");
			if (attr.valueType() != Attribute.ATTR_DT_SUBOBJ)
			ret += " " 
				+ attr.getName()
				+ "=\""
				+ attr.getStringValue()
				+ "\" ";
			else
				children.add((DataObjectList)attr.getValue());
			//ret += ((DataObject)attr.getValue()).printXML();
			
		}
		ret += ">";
		// children
		for (int i = 0; i< children.size(); i++){
			DataObjectList dol =  children.get(i);
			ret+="\r\n<list name=\""+dol.objectName() + "\" >\r\n";
			for (int j= 0; j < dol.size(); j++)
				ret += dol.get(j).printXML();
			ret+="</list>\r\n";
		}		
		//ret += ">\r\n";
		
		// value
		if (value != null)
			ret += this.getStringValue();
		ret += "</" + nodeName + ">\r\n";
		return ret;
	}

	/**
	 * print content of this dataObject to string
	 * @return
	 */
	public String printHtml() {
		Iterator<Attribute> it = attributes.values().iterator();
		Attribute attr;
		String ret = "\r\n<ul><font color=\"#222299\">" + name + "</font>\r\n";
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

	/**
	 * return object name
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * add child Data Object
	 * @param ob
	 */
	public boolean addChild(DataObject ob) {
		if (ob == null) {
			String msg = "addChild: input parameter is null";
			Log.error(msg);
			//throw new DOException(msg);			
			return false;
		}
		// get object name
		String sObjName = ob.getName();

		// get attribute by name
		Attribute attr = getAttr(sObjName);

		DataObjectList list;
		if (attr == null) { // create new attribute
			list = new DataObjectList();
			list.setObjectName(sObjName);
			try {

				attr = new Attribute(sObjName, Attribute.ATTR_DT_SUBOBJ, list);
			} catch (DOException e) {
				Log.error(e);
				return false;
			}
			//addAttr(attr);
			attributes.put(attr.getName(), attr);
		} else // get list
			{
			list = (DataObjectList) attr.getValue();
		}

		// add node
		list.add(ob);
		ob.setParent(this);
		return true;
	}

	/**
	 * get one child node by child object and attribute
	 * @param sChildObjName name of the target object
	 * @param key			the attribute used to select Object. only if the object contain a attribute with the same value, it will be returned.
	 * @return
	 */
	public DataObjectList getChildByAttr(String sChildObjName, Attribute key) {

		DataObjectList list = getChildren(sChildObjName);
		if (list == null)
			return null;

		DataObjectList ret = new DataObjectList();

		for (int i = 0; i < list.size(); i++) {
			DataObject obj = list.get(i);
			Attribute a = obj.getAttr(key.getName());
			if (a == null) {
				Log.warning(
					this
						+ "::Attribute '"
						+ key.getName()
						+ "' dose not exist in Object:"
						+ obj.printXML());
				continue;
			}
			try {

				if (key.compareTo(a) == 0) {
					ret.add(obj);
				}
			} catch (DOException e) {
				Log.error(e);
			}
		}

		return ret;
	}

	/**
	 * get all childs (not attribute)
	 * @return a list of data object list
	 */
	public ArrayList<DataObjectList> getChildren(){
		ArrayList ret = new ArrayList();
		Iterator<Attribute> it = attributes.values().iterator();
		Attribute attr;
	
		while (it.hasNext()) {
			attr = (Attribute) (it.next());
			if (attr.valueType() == Attribute.ATTR_DT_SUBOBJ){
				ret.add(attr.getValue());
			}			
		}
		return ret;
	}
	
	public DataObjectList getChildren(String sChildObjName) {
		DataObjectList ret = new DataObjectList();
		// check parameter
		if (sChildObjName == null) {
			Log.error(this +"::getChildByAttr: invalid parameter");
			return ret;
		}

		// get attribute by name
		Attribute attr = getAttr(sChildObjName);
		if (attr == null) {
			Log.error(
				this
					+ "::getChildByAttr(): get attriubte by name '"
					+ sChildObjName
					+ "' failed");
			return ret;
		}

		// check attribute type
		if (attr.valueType() != Attribute.ATTR_DT_SUBOBJ) {
			Log.error(
				this
					+ "::getChildByAttr(): attribute '"
					+ sChildObjName
					+ "' is not a child object");
			return ret;
		}

		ret = (DataObjectList) attr.getValue();

		return ret;
	}

	public DataObject getChild(String sChildObjName, int index) {
		DataObject ret = null;
		DataObjectList list = getChildren(sChildObjName);
		if (list == null) {
			return null;
		}
		ret = list.get(index);
		return ret;

	}
	
	/**
	 * get number of attributes which are not a child Object
	 * @return
	 */
	public int getAttrNum() {
		Iterator<Attribute> it = attributes.values().iterator();
		int ret = 0;
		while (it.hasNext()) {
			Attribute attr = (Attribute) it.next();
			if (attr.valueType() != Attribute.ATTR_DT_SUBOBJ)
				ret++;
		}
		return ret;
	}

	/**
	 * get attribute by index
	 * @param i
	 * @return
	 */
	public Attribute getAttr(int i) {
		int l = 0;
		Iterator<Attribute> it = attributes.values().iterator();
		while (it.hasNext()) {
			Attribute attr = (Attribute) it.next();
			if (l == i)
				return attr;
			l++;
		}

		return null;
	}

	public Attribute[] attrList() {
		int size = size();
		Attribute[] ret = new Attribute[size];

		int i = 0;
		Iterator<Attribute> it = attributes.values().iterator();
		while (it.hasNext()) {
			Attribute attr = (Attribute) it.next();
			ret[i] = attr;
			i++;
		}
		return ret;
	}
	
	/**
	 * user ob to update data. The ob only include node which needs to be updated
	 * @param ob
	 */
	public void update(DataObject ob) throws DOException {
		if (ob == null) {
			Log.error("input parameter is null");
			return;
		}

		
		Iterator<Attribute> it = ob.attrs().values().iterator();

		while (it.hasNext()) {
			Attribute attr = (Attribute) it.next();
			if (attr.valueType() == Attribute.ATTR_DT_SUBOBJ) {
				// update children
				// because the DAL can not provide Object which key, 
				// so assume that
				// 1. the children with same name will be updated at a same time
				// 2. the update children has the complete sub tree data
				
				DataObjectList list = (DataObjectList) attr.getValue();
				Attribute at = this.getAttr(attr.getName());
				if (at != null)
					at.setValue(list);
				else
					attributes.put(attr.getName(), attr);
//				if (list != null){
//					if (l != null){		// has this kind of children
//						
//						
//					}else	// no this kind of children
//					{
//						for (int i = 0; i < list.size(); i++){
//							addChild(list.get(i));
//						}
//						
//					}
//				}
				
			} else {
				// update attribute
				addAttr(attr);
			}
		}	

	}
	
	/**
	 * add children nodes
	 * @param childs
	 * @throws DOException
	 */
	public void addChildren(DataObjectList childs) throws DOException{
		Log.trace("enter");
		
		if (childs == null){
			throw new DOException("invalid parameter(null)");
		}
		for (int i = 0; i < childs.size(); i++){
			DataObject ob = childs.get(i);
			addChild(ob);
			Log.trace("add child(name=" + ob.getName() + ")");
		}
		
		Log.trace("leave");
	}
	
	public void removeChild(String name, DataObject node){
		if (name == null || node == null)
			return;
		DataObjectList list = (DataObjectList)this.getAttr(name).getValue();
		for (int i = 0; i< list.size(); i++){
			DataObject o = list.get(i);
			if (o == node)
			{
				list.remove(i);
				return;
			}
		}		
	}
	
	public void setValue(byte[] object){
		if (definition != null){
			if (definition.hasValue())
				value = object;
			
		}else
			value = object;
		tm_setValue = new Timestamp(System.currentTimeMillis());
		return;
	}
	
	public void setValue(String object){
		setValue(object.getBytes());
	}
	
	public void setValue(int valueType, byte[] object){
		
		if (definition != null){
			if (definition.hasValue())
				value = object;
			
		}else{
			this.valueType = valueType; 
			value = object;
		}
		tm_setValue = new Timestamp(System.currentTimeMillis());
		return;
	}
	
	public String getStringValue(){
		String v = "";
		try {
			byte[] bs = IO.readInputStream(getValue());
			 v = new String(bs, "UTF-8");
			
		} catch (Exception e) {
			Log.error(e);
			v = e.getMessage();
		}
		return v;
	}
	public InputStream getValue() {
		if (value == null)
			return null;
		return new ByteArrayInputStream(value);
	}
	public Timestamp getSetValueTime(){
		if (value == null)
			return null;
		return tm_setValue;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	public void removeAllChildren(String childName){
		this.removeAttr(childName);
	}
	
	public static void main(String args[]) throws Exception{
		DataObject do1 = new DataObject("HAHA");
		do1.addAttr(new Attribute("att1", Attribute.ATTR_DT_STR, "pp"));
		do1.addChild(new DataObject("hehe"));
		DataObject do2 = new DataObject("heffhe");
		do2.setValue("String");
		do1.addChild(do2);
		do1.addChild(new DataObject("hehe"));
		String x = do1.printXML();
		System.out.print(x);
		System.out.print("----\r\n");
		DataObject ret = fromXML(x);
		System.out.print(ret.printXML());
		System.out.print("----\r\n");
	}
	
}
