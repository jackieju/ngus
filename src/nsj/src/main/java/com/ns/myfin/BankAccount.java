package com.ns.myfin;

/*
 * Created on 2005-4-18
 * template for nsj dev
 */


import com.ns.dataobject.Attribute;
import com.ns.dataobject.DOException;
import com.ns.dataobject.DataObject;
import com.ns.dataobject.DataObjectList;


/**
 * @author I027910
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class BankAccount {
	static public final String OBJECT_NAME = "BankAccount";

	DataObject node;
	public BankAccount(DataObject src) throws DOException {
		//node.addAttrs(src);
		if (src == null)
			throw new IllegalArgumentException();
		//	node = new DataObject (src);
		node = src;
	}

	
	public BankAccount() {
		node = new DataObject(OBJECT_NAME);
	}
	
/***************************************************/	
	
	/**
	 * @return String
	 */
	public String getname() {
		Attribute attr = node.getAttr("name");
		if (attr == null) return null;
			return (String)attr.getValue();
	}
	
	/**
	 * @param String
	 */
	public void setname(String p) throws DOException {
		node.addAttr(
			new Attribute("name", p));
	}

	
	/**
	 * @return Integer
	 */
	public Integer getcreationAddr() {
		Attribute attr = node.getAttr("creationAddr");
		if (attr == null) return null;
			return (Integer)attr.getValue();
	}
	
	/**
	 * @param Integer
	 */
	public void setcreationAddr(Integer p) throws DOException {
		node.addAttr(
			new Attribute("creationAddr", p));
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
	 * @return String
	 */
	public String getcurrency() {
		Attribute attr = node.getAttr("currency");
		if (attr == null) return null;
			return (String)attr.getValue();
	}
	
	/**
	 * @param String
	 */
	public void setcurrency(String p) throws DOException {
		node.addAttr(
			new Attribute("currency", p));
	}

	
	/**
	 * @return Float
	 */
	public Float getbalance() {
		Attribute attr = node.getAttr("balance");
		if (attr == null) return null;
			return (Float)attr.getValue();
	}
	
	/**
	 * @param Float
	 */
	public void setbalance(Float p) throws DOException {
		node.addAttr(
			new Attribute("balance", p));
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
	public Integer getunknown() {
		Attribute attr = node.getAttr("unknown");
		if (attr == null) return null;
			return (Integer)attr.getValue();
	}
	
	/**
	 * @param Integer
	 */
	public void setunknown(Integer p) throws DOException {
		node.addAttr(
			new Attribute("unknown", p));
	}

	
	/**
	 * @return Integer
	 */
	public Integer getlastChangeItem() {
		Attribute attr = node.getAttr("lastChangeItem");
		if (attr == null) return null;
			return (Integer)attr.getValue();
	}
	
	/**
	 * @param Integer
	 */
	public void setlastChangeItem(Integer p) throws DOException {
		node.addAttr(
			new Attribute("lastChangeItem", p));
	}

	


	/**
	 * @return
	 */
	public DataObjectList getaccount() {
		return this.getNode().getChildren(account.getOBJECT_NAME());
	}
	
	/**
	 * @return
	 */
	public void addaccount(account child) {
		this.getNode().addChild(child.getNode());
	}

	/**
	 * @return
	 */
	public DataObjectList getjournal() {
		return this.getNode().getChildren(journal.getOBJECT_NAME());
	}
	
	/**
	 * @return
	 */
	public void addjournal(journal child) {
		this.getNode().addChild(child.getNode());
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
