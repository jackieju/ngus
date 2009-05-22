/*
 * Created on Dec 4, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ns.dataobject;


/**
 * @author Jackie Ju
 * The Attribute Description class
 */
public class AttributeDesc {
		
	private String attrName;	// logical name for this attribute
	private String dbColName;	// column name in db table
	private int dataType;		// data type of value in ACC system but not in external system.
	private boolean isKey;
	private int length;
	public AttributeDesc(String name, int type, String colName, boolean key, int length){
		attrName = name;
		dbColName = colName;
		dataType = type;
	}

	
	public boolean isKey(){
		return isKey;
	}
	public int dataType(){
		return dataType;
	}
	

	
	public String attrName(){
		return attrName;
	}
	

	
	public String dbColumnName(){
			return dbColName;
	}
	
	public void setDBColumnName(String name){
		dbColName = name;
	}
	
	public String print(){
		String s = "";
		s = "<AttrDesc name= \""+attrName+"\" dbColName=\""+dbColName+"\" dataType=\""+dataType+"\" />";
		return s;
	}
	public String printHtml(){
		
		String ret = "<font color=\"#992222\">" + "[AttrDesc name= \""+attrName+"\" dbColName="+dbColName+"\" dataType="+dataType+"\" /]" + "</font>";
		
		return ret;
	}
}
