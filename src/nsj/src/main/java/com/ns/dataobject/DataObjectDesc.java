/*
 * Created on Dec 16, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ns.dataobject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * @author i027910
 *
 */
public class DataObjectDesc {
	private HashMap attrs = new HashMap();
	private HashMap children = new HashMap();
	private String ObjectName;
	private String TableName;
	private String desc;
	private ArrayList keys;	// keys, element is String
	private boolean hasValue = false; // does this object can have value or only has children
	private long valueType;
	// data type constant
	final static public int ATTR_DT_UNK = 0; // unkonwn
	final static public int ATTR_DT_INT = 1; // integer
	final static public int ATTR_DT_STR = 2; // string
	final static public int ATTR_DT_FLT = 3; // float
	final static public int ATTR_DT_BIN = 6; // binary
	final static public int ATTR_DT_TMS = 5; // timestamp

	
	public DataObjectDesc(String object_name, String description){
		ObjectName = object_name;
		desc = description;
		//TableName = table_name;
	}
	
	public DataObjectDesc(String object_name, String table_name, String description){
		ObjectName = object_name;
		desc = description;
		TableName = table_name;
	}
	
	public DataObjectDesc(String object_name, boolean hasValue, long valueType){
		ObjectName = object_name;
		this.hasValue = hasValue;
		this.valueType = valueType;
	}
	
//	public 	HashMap attrs(){
//		return attrs;
//	}
	 
	
	public void addAttrDesc(AttributeDesc attr){
		attrs.put(attr.attrName(), attr);
		if (attr.isKey())
			keys.add(attr.attrName());
	}
	
	public ArrayList getKeyAttrs(){
		return keys;
	}
	/**
	 * get number of attributes which are not a child Object
	 * @return
	 */
	public int getAttrNum() {
		Iterator it = attrs.values().iterator();
		int ret = 0;
		while (it.hasNext()) {
				it.next();
				ret++;
				
		}
		return ret;
	}
	/**
	 * get attribute by index
	 * @param i
	 * @return
	 */
	public AttributeDesc getAttr(int i) {
		int l = 0;
		Iterator it = attrs.values().iterator();
		while (it.hasNext()) {
			AttributeDesc attrDesc = (AttributeDesc) it.next();
			if (l == i)
				return attrDesc;
			l++;
		}

		return null;
	}

	public HashMap children(){
		return children;
	}
	
	public void addChild(DataObjectDesc dod){
		children.put(dod.ObjectName, dod);
	}
	
	public String name(){
		return ObjectName;
	}
	public String desc(){
		return desc;
	}
	public String tableName(){
		return TableName;
	}
	
	public String print(){
		
		Iterator it = attrs.values().iterator();
		
		AttributeDesc attr;
		
		String ret = "\r\n<DataObjecDesc ObjectName=\""+ ObjectName  + "\" TableName=\"" +TableName+"\" desc=\""+desc+"\" >\r\n";
		
		while (it.hasNext())
		{
		//	System.out.println("iterator point " + it.next() + "****");
		//	attr = (Attribute)(((Map.Entry)it.next()).getValue());
			attr = (AttributeDesc)(it.next());
		//	System.out.println("//// ");
			ret += attr.print() + "\r\n";
		}
		//ret += ">\r\n";
	
		
		DataObjectDesc child;
		it = children.values().iterator();
		while (it.hasNext())
		{
			child = (DataObjectDesc)(it.next());
			ret += child.print();
		}
		
		ret += "</DataObjecDesc>\r\n";
		return ret;
	}
	public String printHtml(){
		
			Iterator it = attrs.values().iterator();
		
			AttributeDesc attr;
		
		//	String ret = "\r\n[DataObjecDesc ObjectName=\""+ ObjectName  + "\" TableName=\"" +TableName+"\" desc=\""+desc+"\" ]\r\n";
		String ret = "\r\n<ul><font color=\"#222299\">[DataObjecDesc ObjectName=\"" + ObjectName + "\" TableName=\"" +TableName+"\" desc=\""+desc+"\" ]\r\n" + "</font>\r\n";
			while (it.hasNext())
			{
			//	System.out.println("iterator point " + it.next() + "****");
			//	attr = (Attribute)(((Map.Entry)it.next()).getValue());
				attr = (AttributeDesc)(it.next());
			//	System.out.println("//// ");
		//		ret += attr.printHtml() + "\r\n";
				ret += "<li> " + attr.printHtml() + "</li>\r\n";
			}
			//ret += ">\r\n";
	
		
			DataObjectDesc child;
			it = children.values().iterator();
			while (it.hasNext())
			{
				child = (DataObjectDesc)(it.next());
				ret += child.printHtml();
			}
		
	//		ret += "[/DataObjecDesc]\r\n";
	ret += "</ul>\r\n";
			return ret;
		}
	
	public boolean hasValue(){
		return hasValue;
	}
	
}
