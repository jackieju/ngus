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
public class Inventoryjournal {
	static public final String OBJECT_NAME = "Inventoryjournal";

	DataObject node;
	public Inventoryjournal(DataObject src) throws DOException {
		//node.addAttrs(src);
		if (src == null)
			throw new IllegalArgumentException();
		//	node = new DataObject (src);
		node = src;
	}

	
	public Inventoryjournal() {
		node = new DataObject(OBJECT_NAME);
	}
	
/***************************************************/	
	
	/**
	 * @return Integer
	 */
	public Integer getid() {
		Attribute attr = node.getAttr("id");
		if (attr == null) return null;
			return (Integer)attr.getValue();
	}
	
	/**
	 * @param Integer
	 */
	public void setid(Integer p) throws DOException {
		node.addAttr(
			new Attribute("id", p));
	}

	
	/**
	 * @return Integer
	 */
	public Integer getdatetime() {
		Attribute attr = node.getAttr("datetime");
		if (attr == null) return null;
			return (Integer)attr.getValue();
	}
	
	/**
	 * @param Integer
	 */
	public void setdatetime(Integer p) throws DOException {
		node.addAttr(
			new Attribute("datetime", p));
	}

	
	/**
	 * @return Integer
	 */
	public Integer getitemId() {
		Attribute attr = node.getAttr("itemId");
		if (attr == null) return null;
			return (Integer)attr.getValue();
	}
	
	/**
	 * @param Integer
	 */
	public void setitemId(Integer p) throws DOException {
		node.addAttr(
			new Attribute("itemId", p));
	}

	
	/**
	 * @return Integer
	 */
	public Integer getdelta() {
		Attribute attr = node.getAttr("delta");
		if (attr == null) return null;
			return (Integer)attr.getValue();
	}
	
	/**
	 * @param Integer
	 */
	public void setdelta(Integer p) throws DOException {
		node.addAttr(
			new Attribute("delta", p));
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
