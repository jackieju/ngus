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
public class UserInfo {
	static public final String OBJECT_NAME = "UserInfo";

	DataObject node;
	public UserInfo(DataObject src) throws DOException {
		//node.addAttrs(src);
		if (src == null)
			throw new IllegalArgumentException();
		//	node = new DataObject (src);
		node = src;
	}

	
	public UserInfo() {
		node = new DataObject(OBJECT_NAME);
	}
	
/***************************************************/	
	
	/**
	 * @return String
	 */
	public String getuserName() {
		Attribute attr = node.getAttr("userName");
		if (attr == null) return null;
			return (String)attr.getValue();
	}
	
	/**
	 * @param String
	 */
	public void setuserName(String p) throws DOException {
		node.addAttr(
			new Attribute("userName", p));
	}

	
	/**
	 * @return String
	 */
	public String getcreateTime() {
		Attribute attr = node.getAttr("createTime");
		if (attr == null) return null;
			return (String)attr.getValue();
	}
	
	/**
	 * @param String
	 */
	public void setcreateTime(String p) throws DOException {
		node.addAttr(
			new Attribute("createTime", p));
	}

	
	/**
	 * @return String
	 */
	public String getaddress() {
		Attribute attr = node.getAttr("address");
		if (attr == null) return null;
			return (String)attr.getValue();
	}
	
	/**
	 * @param String
	 */
	public void setaddress(String p) throws DOException {
		node.addAttr(
			new Attribute("address", p));
	}

	
	/**
	 * @return String
	 */
	public String getemail() {
		Attribute attr = node.getAttr("email");
		if (attr == null) return null;
			return (String)attr.getValue();
	}
	
	/**
	 * @param String
	 */
	public void setemail(String p) throws DOException {
		node.addAttr(
			new Attribute("email", p));
	}

	
	/**
	 * @return Integer
	 */
	public Integer getphone() {
		Attribute attr = node.getAttr("phone");
		if (attr == null) return null;
			return (Integer)attr.getValue();
	}
	
	/**
	 * @param Integer
	 */
	public void setphone(Integer p) throws DOException {
		node.addAttr(
			new Attribute("phone", p));
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
	 * @return Integer
	 */
	public Integer getuserid() {
		Attribute attr = node.getAttr("userid");
		if (attr == null) return null;
			return (Integer)attr.getValue();
	}
	
	/**
	 * @param Integer
	 */
	public void setuserid(Integer p) throws DOException {
		node.addAttr(
			new Attribute("userid", p));
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
	public DataObjectList getBankAccount() {
		return this.getNode().getChildren(BankAccount.getOBJECT_NAME());
	}
	
	/**
	 * @return
	 */
	public void addBankAccount(BankAccount child) {
		 this.getNode().addChild(child.getNode());
	}

	/**
	 * @return
	 */
	public DataObjectList getInventoryjournal() {
		return this.getNode().getChildren(Inventoryjournal.getOBJECT_NAME());
	}
	
	/**
	 * @return
	 */
	public void addInventoryjournal(Inventoryjournal child) {
		 this.getNode().addChild(child.getNode());
	}

	/**
	 * @return
	 */
	public DataObjectList getItem() {
		return this.getNode().getChildren(Item.getOBJECT_NAME());
	}
	
	/**
	 * @return
	 */
	public void addItem(Item child) {
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

//	public static List query(){
//		
//	}

	

	
	
	

}
