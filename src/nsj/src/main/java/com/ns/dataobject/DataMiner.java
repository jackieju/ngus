/*
 * Created on Dec 8, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ns.dataobject;

/**
 * @author I027910
 *
 * This interface is used by AttributeFactory and DataObjectFactory to get data
 * from physical data source.
 */
public interface DataMiner {
	public Object getAttr(String name);	// the interface for get data for one Attribute
	
	public Object getDataObject(String name);	// the interface for get data for one DataObject 
												// at one time.
}
