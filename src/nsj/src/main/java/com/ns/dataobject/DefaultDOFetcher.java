/*
 * Created on Aug 10, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.ns.dataobject;

import com.ns.log.Log;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DefaultDOFetcher implements DOFetcher {

	DataObjectFactory dof = null;
	String DOName = "";
	
	public DefaultDOFetcher(String DOName, DataObjectFactory dof){
		this.dof = dof;
	}
	
	/* (non-Javadoc)
	 * @see com.ns.dataobject.DOFetcher#fetch(com.ns.dataobject.Attribute)
	 */
	public DataObject fetch(Attributes Keys) {
		try{
		return dof.queryObjectByKeys(null, DOName, Keys).get(0);
		}catch(Exception e){
			Log.error(e);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.ns.dataobject.DOFetcher#fetchAll()
	 */
	public DataObjectList fetchAll() {
		try{
		return dof.queryObject(null, DOName, null, null);		
		}catch (Exception e){
			Log.error(e);
		}
		return null;
		
	}

	/* (non-Javadoc)
	 * @see com.ns.dataobject.DOFetcher#fetchKeyList()
	 */
	public DataObjectList fetchKeyList() {
		
		return fetchAll();
	}

	/* (non-Javadoc)
	 * @see com.ns.dataobject.DOFetcher#refresh(com.ns.dataobject.Attribute)
	 */
	public DataObject refresh(Attributes Keys) {
		// TODO Auto-generated method stub
		return fetch(Keys);
	}

	public static void main(String[] args) {
	}
}
