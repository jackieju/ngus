package com.ns.myfin;

/*
 * Created on 2005-4-18
 * template for nsj dev
 */


import com.ns.dataobject.Attribute;
import com.ns.dataobject.DOException;
import com.ns.dataobject.DataObject;


/**
 * @author I027910
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Item {
	static public final String OBJECT_NAME = "Item";

	DataObject node;
	public Item(DataObject src) throws DOException {
		//node.addAttrs(src);
		if (src == null)
			throw new IllegalArgumentException();
		//	node = new DataObject (src);
		node = src;
	}

	
	public Item() {
		node = new DataObject(OBJECT_NAME);
	}
	
/***************************************************/	
	
	/**
	 * @return Integer
	 */
	public Integer getId() {
		Attribute attr = node.getAttr("Id");
		if (attr == null) return null;
			return (Integer)attr.getValue();
	}
	
	/**
	 * @param Integer
	 */
	public void setId(Integer p) throws DOException {
		node.addAttr(
			new Attribute("Id", p));
	}

	
	/**
	 * @return Integer
	 */
	public Integer getFullName() {
		Attribute attr = node.getAttr("FullName");
		if (attr == null) return null;
			return (Integer)attr.getValue();
	}
	
	/**
	 * @param Integer
	 */
	public void setFullName(Integer p) throws DOException {
		node.addAttr(
			new Attribute("FullName", p));
	}

	
	/**
	 * @return Integer
	 */
	public Integer getType() {
		Attribute attr = node.getAttr("Type");
		if (attr == null) return null;
			return (Integer)attr.getValue();
	}
	
	/**
	 * @param Integer
	 */
	public void setType(Integer p) throws DOException {
		node.addAttr(
			new Attribute("Type", p));
	}

	
	/**
	 * @return Integer
	 */
	public Integer getquantity() {
		Attribute attr = node.getAttr("quantity");
		if (attr == null) return null;
			return (Integer)attr.getValue();
	}
	
	/**
	 * @param Integer
	 */
	public void setquantity(Integer p) throws DOException {
		node.addAttr(
			new Attribute("quantity", p));
	}

	
	/**
	 * @return Integer
	 */
	public Integer getprice() {
		Attribute attr = node.getAttr("price");
		if (attr == null) return null;
			return (Integer)attr.getValue();
	}
	
	/**
	 * @param Integer
	 */
	public void setprice(Integer p) throws DOException {
		node.addAttr(
			new Attribute("price", p));
	}

	
	/**
	 * @return Integer
	 */
	public Integer getLastChangeItem() {
		Attribute attr = node.getAttr("LastChangeItem");
		if (attr == null) return null;
			return (Integer)attr.getValue();
	}
	
	/**
	 * @param Integer
	 */
	public void setLastChangeItem(Integer p) throws DOException {
		node.addAttr(
			new Attribute("LastChangeItem", p));
	}

	
	/**
	 * @return String
	 */
	public String getdesc() {
		Attribute attr = node.getAttr("desc");
		if (attr == null) return null;
			return (String)attr.getValue();
	}
	
	/**
	 * @param String
	 */
	public void setdesc(String p) throws DOException {
		node.addAttr(
			new Attribute("desc", p));
	}

	
	/**
	 * @return String
	 */
	public String getstatus() {
		Attribute attr = node.getAttr("status");
		if (attr == null) return null;
			return (String)attr.getValue();
	}
	
	/**
	 * @param String
	 */
	public void setstatus(String p) throws DOException {
		node.addAttr(
			new Attribute("status", p));
	}

	



/***************************************************/
	
	

	/**
	 * @return
	 */
	public static String getOBJECT_NAME() {
		return OBJECT_NAME;
	}

	

	/**
	 * @return
	 */
	public DataObject getNode() {
		return node;
	}

	/**
	 * @param object
	 */
	public void setNode(DataObject object) {
		node = object;
	}

	

	

	
	
	

}
