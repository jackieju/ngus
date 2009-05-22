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
public class account {
	static public final String OBJECT_NAME = "account";

	DataObject node;
	public account(DataObject src) throws DOException {
		//node.addAttrs(src);
		if (src == null)
			throw new IllegalArgumentException();
		//	node = new DataObject (src);
		node = src;
	}

	
	public account() {
		node = new DataObject(OBJECT_NAME);
	}
	
/***************************************************/	
	
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
