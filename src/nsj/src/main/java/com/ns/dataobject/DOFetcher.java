/*
 * Created on Dec 10, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ns.dataobject;



/**
 * @author i027910
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public interface DOFetcher {
	
	// fetch one object by a list of attribute
	public DataObject fetch(Attributes Keys);
	
	// fetch all objects
	public DataObjectList fetchAll();
	
	// fetch all objects but only only contain key attributes
	public DataObjectList fetchKeyList();
	
	// refresh all objects
	public DataObject refresh(Attributes Keys);

}
