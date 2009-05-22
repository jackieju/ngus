/*
 * Created on 2005-4-5
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.ngus.dataengine;


import java.io.InputStream;
import java.util.List;

import com.ns.dataobject.Attribute;
import com.ns.dataobject.DataObjectList;

/**
 * @author I027910
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface IDataEngine {
	
		/**
		 * constants
		 */
		// internal models
		public final static String MODEL_PS = "ps";  // personal information
		public final static String MODEL_DEFAULT = "ngus";
		public final static String MODEL_USERAPP = "userapp";
		
		/**
		 * attribute or property name for ngus
		 */
		// for model "ngus"
		static final String ATTR_CREATETIME = "create_time";
		static final String ATTR_UPDATETIME = "update_time";
		static final String ATTR_SHARE_LEVEL = "share_level";
		static final String ATTR_TYPE = "type";
		static final String ATTR_TITLE = "title";
		static final String ATTR_APPS = "apps";
		static final String ATTR_RESTYPE = "resType"; 
		static final String ATTR_USER = "user"; 
		static final String ATTR_TAGS = "tags"; 
		
		// for model user-app
		static final String ATTR_USERAPP_RESID = "resid";
		
		// for app model
		//static final String ATTR_APP_USER = "user";
		
		/**
		 * resource type definition
		 */
		static final int RES_TYPE_TEXT = 1;

		static final int RES_TYPE_BIN = 2;
		
		/**
		 * protocl (depends on dat type)
		 */
		static final String RES_PROTOCOL_TYPE_TEXT = "text";
		
		static final String RES_PROTOCOL_TYPE_BIN = "bin";

		/**
		 * share level definition
		 */
		static final int SHARE_LVL_PRIVATE = 0;

		static final int SHARE_LVL_USER = 1;

		static final int SHARE_LVL_PUBLIC = 2;		
		
		/**
		 * storage type
		 */
//		public final static int STORAGETYPE_DB = 1;
//		public final static int STORAGETYPE_JCR = 2;
		
		
		/**
		 * Interfaces
		 */
		
		//-------------------------------
		// Insert 
		//-------------------------------
		
		/**
		 * put an object with a set of properties (which encapsulte in class DataObject)
		 * @param ob
		 * @return
		 * @throws Exception
		 */	
		public String post(ResourceObject ob) throws Exception;
		
		/**
		 * put an object with a set of properties (which encapsulte in class DataObject)
		 * @param ob
		 * @return
		 * @throws Exception
		 */
		public String post(AbstractAppObject ob) throws Exception;
		
		/**
		 * add one resource under one tree node
		 * @param ob
		 * @param resId
		 * @throws Exception
		 */
		public void addResourceToTree(ResourceObject ob, String resId) throws Exception;

		

		//-------------------------------
		// Update 
		//-------------------------------

		
		/**
		 * only updat the value of resource
		 * @param Id
		 * @param v
		 * @throws Exception
		 */
		//public void updateResourceValue(String Id, InputStream v) throws Exception ;
		

		/**
		 * update share level of resource 
		 * @param Id
		 * @param shareLevel
		 * @throws Exception
		 */
		public void updateResourceShareLevel(String Id, int shareLevel, boolean recursive) throws Exception ;
		

		/**
		 * update one model of resource
		 * @param Id
		 * @param ob
		 * @throws Exception
		 */
		public void updateResourceDes(ResDesObject ob)throws Exception;
		
		
		//-------------------------------
		// read 
		//-------------------------------
		
		
//		public AbstractAppObject getAppObject() throws Exception;
		/**
		 * get resource descriptor by id and model name
		 * @param Id
		 * @param modelName
		 * @return
		 * @throws Exception
		 */
		public ResDesObject getResourceDesObj(String Id, String modelName) throws Exception;
		
		/**
		 * 
		 * Get the property value of resource
		 * @param Id
		 * @param modelName
		 * @param property
		 * @return
		 * @throws Exception
		 */
		public String getResourceProperty(String Id, String modelName, String property) throws Exception;
		

		/**
		 * get resource by resource id
		 * @param Id
		 * @param getValue
		 * @return
		 * @throws Exception
		 */
		public ResourceObject getResourceObjById(String Id, boolean getValue)  throws Exception;		
	
		/**
		 * get all resource des for one user in specified model
		 * @param username
		 * @param model
		 * @return
		 * @throws Exception
		 */
		public List listAllResOfUser(String username, String model) throws Exception;
		

		/**
		 * get resource which was stored as a tree
		 * @param resId
		 * @return
		 * @throws Exception
		 */
		public ResourceObject getResourceTree(String resId, int depth) throws Exception;
		
		

	
		/**
		 * get the trees for app and current user
		 * @param app		app name
		 * @param recursive	recursively load all sub tree or not
		 * @returna list of trees on top level in app tree for the current user
		 */
		public List<ResourceObject> getAppTrees(String app, int depth, String userName)  throws Exception;
		
		
		
		//-------------------------------
		// delete 
		//-------------------------------


		/**
		 * delete Resource and all its children and all resource des
		 * @param Id
		 * @param delRes	delete the resource from storage if true
		 * @throws Exception
		 */
		public void deleteResource(String Id, boolean delRes)  throws Exception;
		
		/**
		 * delete Resource from a model, if no model has reference to the resource, the resource will be deleted
		 * @param Id
		 * @param modelName
		 * @throws Exception
		 */
		public void deleteResourceDes(String Id, String modelName)  throws Exception;
			
		

		//-------------------------------
		// others 
		//-------------------------------

		/**
		 * get an object by properties
		 * @param modelName
		 * @return
		 * @throws DEException
		 */
		public DataObjectList getAllResourceOfModel(String modelName) throws Exception;
	
		
		/**
		 * search an object by properties with conditional expression
		 * @param modelName
		 * @param queryString
		 * @return
		 * @throws DEException
		 */
		//public DataObjectList selectByRDQL(String modelName, String queryString) throws DEException;
		
		public List<ResourceObject> search(String modelName, String statement, int start, int number, Attribute total, String userId) throws Exception;
		
		

		
}
