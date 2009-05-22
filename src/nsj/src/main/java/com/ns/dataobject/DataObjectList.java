/*
 * Created on Dec 16, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ns.dataobject;

import java.io.Serializable;
import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

import com.ns.util.XmlUtil;

/**
 * @author i027910
 *
 * list or array of DataObject
 */
public class DataObjectList implements Serializable{
	private ArrayList m_list = new ArrayList();
	private String objectName;
	private String tableName;
	private String desc;
	
	public String objectName(){
		return objectName;
	}
	public String tableName(){
		return tableName;
	}
	public String desc(){
		return desc;
	}
	static public DataObjectList fromXML(String xml) throws Exception{
		Document doc = XmlUtil.loadXML(xml);
		Element ele = doc.getDocumentElement();
		
		return fromXML(ele);
	}
	
	static public DataObjectList fromXML(Element ele) throws Exception{
		
		String doName = ele.getTagName();
		DataObjectList ret = new DataObjectList();
		ret.setObjectName(doName);
		
		NodeList list = ele.getElementsByTagName("*");
		for (int i = 0; i< list.getLength(); i++){
			Element child = (Element)list.item(i);
			DataObject c = DataObject.fromXML(child);
			ret.add(c);
		}
		return ret;
	}
	
	public void setObjectName(String name)
	{
		objectName = name;
	}
	
	public void setTableName(String name){
		tableName = name;
	}
	
	public void setDesc(String d){
		desc = d;
	}
	
	public void add(DataObject obj){
		m_list.add(obj);
	}
	
	public DataObject get(int i){
		return(DataObject)m_list.get(i);		
	}
	
	public int size(){
		return m_list.size();
	}
	
	public Object remove(int index) {
		return m_list.remove(index);
	}
	
	public void removeAll(){
		m_list = new ArrayList();
	}
	
	public String toXML(){
		String s = ""; 
		for (int i = 0; i < m_list.size(); i++){
			DataObject ob = (DataObject)m_list.get(i);
			s += ob.printXML();
		}
		return s;
	}

	public String toHtml(){
		String s = "ObjectList(size=" + size()+")<br>"; 
		for (int i = 0; i < m_list.size(); i++){
			DataObject ob = (DataObject)m_list.get(i);
			s += ob.printHtml();
		}
		return s;
	}
}
