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
public class journal {
	static public final String OBJECT_NAME = "journal";

	DataObject node;
	public journal(DataObject src) throws DOException {
		//node.addAttrs(src);
		if (src == null)
			throw new IllegalArgumentException();
		//	node = new DataObject (src);
		node = src;
	}

	
	public journal() {
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
	public Integer getdesc() {
		Attribute attr = node.getAttr("desc");
		if (attr == null) return null;
			return (Integer)attr.getValue();
	}
	
	/**
	 * @param Integer
	 */
	public void setdesc(Integer p) throws DOException {
		node.addAttr(
			new Attribute("desc", p));
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
	public Integer getaccountId() {
		Attribute attr = node.getAttr("accountId");
		if (attr == null) return null;
			return (Integer)attr.getValue();
	}
	
	/**
	 * @param Integer
	 */
	public void setaccountId(Integer p) throws DOException {
		node.addAttr(
			new Attribute("accountId", p));
	}

	
	/**
	 * @return Integer
	 */
	public Integer getaccountJournalId() {
		Attribute attr = node.getAttr("accountJournalId");
		if (attr == null) return null;
			return (Integer)attr.getValue();
	}
	
	/**
	 * @param Integer
	 */
	public void setaccountJournalId(Integer p) throws DOException {
		node.addAttr(
			new Attribute("accountJournalId", p));
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
