/*
 * Created on 2005-4-5
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.ngus.dataengine;

import java.io.StringWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Session;
import javax.jcr.query.QueryResult;

import com.hp.hpl.jena.db.DBConnection;
import com.hp.hpl.jena.db.ModelRDB;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.ResIterator;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.vocabulary.VCARD;
import com.ngus.resengine.IResEngine;
import com.ngus.resengine.JCRContentRepository;
import com.ngus.resengine.ResourceEngine;
import com.ns.dataobject.Attribute;
import com.ns.dataobject.DataObject;
import com.ns.dataobject.DataObjectList;
import com.ns.db.DB;
import com.ns.log.Log;
import com.ns.util.DateTime;

// import com.ns.util.Singleton;

/**
 * @author I027910
 * 
 * 
 * 
 */
/*
 * db scripts: create table resourceid( id bigint unsigned auto_increment
 * primary key, uuid char(36), resid varchar(255) );
 * 
 * 
 * create unique index iresid on resourceid ( id ASC );
 * 
 * create unique index iresid2 on resourceid ( resid ASC ); create table
 * ngus_resbytype( id bigint unsigned primary key, username char(30), type
 * char(20) );
 * 
 * create unique index iresbytype on ngus_resbytype ( id ASC ); create unique
 * index iresbytype2 on ngus_resbytype ( username ASC ); create unique index
 * iresbytype3 on ngus_resbytype ( type ASC );
 * 
 */
public class DataEngine implements IDataEngine {

	static {
		java.sql.Statement st = null;
		java.sql.Statement st2 = null;
		java.sql.Connection c = null;
		java.sql.ResultSet rs = null;
		String sql = null;
		try {
			Log.log("checking system table.");
			Log.log("check table resourceid.");
			c = com.ngus.dataengine.DBConnection.getConnection();
			Log.trace("get connectyion:"+c);
			st = c.createStatement();
			Log.trace("create statement:"+st);
			st2 = c.createStatement();
			Log.trace("create statement:"+st2);
			// check table resource id existing

			sql = "select * from resourceid where  id = 1 ";
			boolean bExist = true;
			try {
				rs = st.executeQuery(sql);
				bExist = true;
			} catch (Exception e) {
				bExist = false;
			}
			// if (rs == null || !rs.next()){
			if (!bExist) {
				try {
					// create table
					Log.log("create table resourceid");
					sql = "create table IF NOT EXISTS resourceid( id bigint unsigned auto_increment  primary key, uuid char(36), resid varchar(255) )";
					st2.executeUpdate(sql);
					Log.log("create index for table resourceid");
					sql = "create unique IF NOT EXISTS index iresid on resourceid(  id ASC)";
					st2.executeUpdate(sql);
					sql = "create unique IF NOT EXISTS index iresid2 on resourceid(  resid ASC)";
					st2.executeUpdate(sql);
					Log.log("Insert first record for table resourceid");
					sql = " insert into resourceid values(1, 'test', 'test')";
					st2.executeUpdate(sql);
				} catch (Exception e) {
					Log.error(e);
				}
				// }
				// else
				// rs();
			}
			// check table resByType existing

			Log.log("core", "check table ngus_resbytype");
			sql = "select * from ngus_resbytype where id = 1 ";
			try {
				rs = st.executeQuery(sql);
				bExist = true;
			} catch (Exception e) {
				bExist = false;
			}
			if (!bExist) {
				// if (rs == null || !rs.next()){
				try {
					Log.log("create table ngus_resbytype");
					// create table
					sql = "create table IF NOT EXISTS ngus_resbytype( resid  varchar(255) primary key, username char(30), usertype char(20), realtype char(20), checked int(1))";
					st2.executeUpdate(sql);
					Log.log("create index for table ngus_resbytype");
					sql = "create  index iresbytype on ngus_resbytype(  resid ASC)";
					st2.executeUpdate(sql);
					sql = "create  index iresbytype2 on ngus_resbytype(  username ASC)";
					st2.executeUpdate(sql);
					sql = "create  index iresbytype3 on ngus_resbytype(  usertype ASC)";
					st2.executeUpdate(sql);
					sql = "create  index  iresbytype4 on ngus_resbytype(  realtype ASC)";
					st2.executeUpdate(sql);
					// sql = "create index IF NOT EXISTS iresbytype5 on
					// ngus_resbytype( realtype ASC)";
					// st2.executeUpdate(sql);
					Log.log("Insert first record for table resByType");
					sql = " insert into resByType values(1, 'test', 'test')";
					st2.executeUpdate(sql);
				} catch (Exception e) {
					Log.error(e);
				}
				// }else
				// rs.close();
			}

			Log.log("check table ngus_tags");
			sql = "select * from ngus_tags where resId = '1' ";
			try {
				rs = st.executeQuery(sql);
				bExist = true;
			} catch (Exception e) {
				bExist = false;
			}
			if (!bExist) {
				// if (rs == null || !rs.next()){
				try {

					Log.log("create table ngus_tags");
					// create table
					sql = "CREATE TABLE IF NOT EXISTS `ngus_tags` (`resId` varchar(255) ,`tag` varchar(50) default NULL,`userid` varchar(255) default NULL, unique key ix_ngus_key(resid, tag), KEY ix_ngus_tags1 (tag), KEY ix_ngus_tag2s (userid))";
					st2.executeUpdate(sql);
					Log.log("create index for table ngus_tags");

					Log.log("Insert first record for table ngus_tags");
					sql = " insert into ngus_tags values('1', 'test', 'test')";
					st2.executeUpdate(sql);
					// }else
					// rs.close();
				} catch (Exception e) {
					Log.error(e);
				}
			}
			
			Log.log("check table ngus_tags");
			sql = "select * from ngus_tags where resId = '1' ";
			try {
				rs = st.executeQuery(sql);
				bExist = true;
			} catch (Exception e) {
				bExist = false;
			}
			if (!bExist) {
				// if (rs == null || !rs.next()){
				try {

					Log.log("create table ngus_tags");
					// create table
					sql = "CREATE TABLE IF NOT EXISTS `ngus_tags` (`resId` varchar(255) ,`tag` varchar(50) default NULL,`userid` varchar(255) default NULL, unique key ix_ngus_key(resid, tag), KEY ix_ngus_tags1 (tag), KEY ix_ngus_tag2s (userid))";
					st2.executeUpdate(sql);
					Log.log("create index for table ngus_tags");

					Log.log("Insert first record for table ngus_tags");
					sql = " insert into ngus_tags values('1', 'test', 'test')";
					st2.executeUpdate(sql);
					// }else
					// rs.close();
				} catch (Exception e) {
					Log.error(e);
				}
			}
			

			DB.checkTable(com.ngus.dataengine.DBConnection.getConnection(), "ngus_resmodeluser", new String[]{
				"CREATE TABLE IF NOT EXISTS `ngus_resmodeluser` (`userid` bigint(20) unsigned NOT NULL default 0, `app` varchar(10) NOT NULL default '*', `resId` varchar(255), PRIMARY KEY  (`userid`,`app`, resid)) ENGINE=InnoDB DEFAULT CHARSET=utf8",
				"create  index irmu1 on ngus_resmodeluser(userid ASC, app ASC)",
				"insert into ngus_resmodeluser values(0, 'test', 'test')"
			});
			
			DB.checkTable(com.ngus.dataengine.DBConnection.getConnection(), "ngus_deleted", new String[]{
				"CREATE TABLE IF NOT EXISTS `ngus_deleted` (" +
				"`userid` bigint(20) unsigned NOT NULL default '0'," +
				"`app` varchar(10) NOT NULL default '*'," +
				"`resId` varchar(255) default NULL," +
				"`deletetime` bigint(20) unsigned default '0'," +
				"PRIMARY KEY  (userid, app, resId)," +
				"KEY `ideleted1` (`deletetime`)" +
				") ENGINE=InnoDB DEFAULT CHARSET=utf8;",	
				"create  index idel2 on ngus_deleted(userid ASC)",
				"insert into ngus_deleted values(0, 'test', 'test', null)"
			});
			
			
			
		} catch (Exception e) {
			Log.error(e);
		} finally {
			if (st != null) {
				try {
					st.close();
				} catch (Exception e) {
					Log.error(e);
				}

				st = null;
			}
			if (st2 != null) {
				try {
					st2.close();
				} catch (Exception e) {
					Log.error(e);
				}

				st2 = null;
			}

			try {
				if (c != null)
					c.close();
			} catch (Exception e) {
				Log.error(e);
			}
		}
		Log.log("check system table finished.");
	}

	// int count = 0;

	// static String modelName = "clipboard";
	// static String uri = "http://www.w3.org/2001/vcard-rdf/3.0#";

	/**
	 * create resource in resource table resource id = <protocol type>://ngus/<storage
	 * path>#int id
	 * 
	 * @param ob
	 * @return
	 */
	public String createResource(ResourceObject ob) throws Exception {
		Log.trace("enter");
		if (ob == null)
			throw new IllegalArgumentException();
		StringBuffer ret = new StringBuffer();

		// write protocol type string
		String protocol_type = IDataEngine.RES_PROTOCOL_TYPE_BIN;
		if (ob.getType() == IDataEngine.RES_TYPE_TEXT)
			protocol_type = IDataEngine.RES_PROTOCOL_TYPE_TEXT;
		else if (ob.getType() == IDataEngine.RES_TYPE_BIN)
			protocol_type = IDataEngine.RES_PROTOCOL_TYPE_BIN;

		// append pre-fix
		ret.append(protocol_type + "://ngus/");

		// append storage path
		if (ob.getStorageType() == IResEngine.STORAGEINTTYPE_JCR) {
			ret.append("jcr");
			String path = ob.getStoragePath();
			// int index = path.indexOf((int)'/', 1);
			// String ws = path.substring(1, index);
			ret.append(path);

			// get int id
			long index_id = RDFEngine.instance().genResourceId(ret);

			// append int id
			// ret.append("#" + index_id);
			// ob.setIndexId(index_id);
		}else if (ob.getStorageType() == IResEngine.STORAGEINTTYPE_DB){
			Log.error("not supported storage type "+ob.getStorageType());
		}else if (ob.getStorageType() == IResEngine.STORAGEINTTYPE_FS){
			Log.error("not supported storage type "+ob.getStorageType());
		}

		// ob.setResId(ret.toString());

		Log.log("resource id = " + ret.toString());

		return ret.toString();
	}

	public String post(AbstractAppObject ob) throws Exception {
		return post(ob.render());
	}

	private void updateResTypeDBAsync(ResourceObject ob, boolean update)
			throws Exception {
		Log.trace("updating type db");
		final HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("ro", ob);
		map.put("update", new Boolean(update));
		NGUSThread t = new NGUSThread(map) {
			public void myRun() {
				Log.trace("enter");
				try {
					// IUser u = UserManager.getCurrentUser();
					// String userId = ob.
					// if (u != null)
					// userId = "" + u.getSUserId();

					updateResTypeTable((ResourceObject) map.get("ro"),
							((Boolean) map.get("update")).booleanValue());
				} catch (Exception e) {
					Log.error(e);
					Log.error("update res-type db failed");
				}

				Log.trace("leave");
			}
		};
		ThreadManager.instance().startThread(t);
		Log.trace("update type db OK");
	}

	/**
	 * insert resource, return the ID of resource
	 * 
	 * @param ob
	 * @return
	 */
	public String post(final ResourceObject ob) throws Exception {
		if (ob == null || ob.getUser() == null)
			throw new IllegalArgumentException();
		Log.trace("post...");
		Log.trace(ob.printXML());
		Timestamp c_tm = DateTime.currentUTCTimeStamp();
		boolean update = true;
		// get object id
		String resourceId = ob.getResId();

//		Log.trace(ob.printXML());
		if (resourceId == null || resourceId.length()==0) { // new resource
			update = false;
			// create resource and get reource id
			resourceId = createResource(ob);
			ob.setResId(resourceId);
			Timestamp utm = DateTime.currentUTCTimeStamp();
			ob.setCreateTime(utm);
			ob.setUpdateTime(utm);
			// store resource in storage
			ResourceEngine.instance().setResource(ob, false);
			this.updateIndex("$$", ob, null, c_tm);
		} else { // update old resource
			ResourceObject old = this.getResourceObjById(resourceId, true);
			updateResourceObject(ob);
			this.updateIndex("$$", ob, old, c_tm);
			this.updateIndex("$update", ob, old, c_tm);
		}
//		Log.trace(ob.printXML());
		// launch thread to update res-type db
		updateResTypeDBAsync(ob, update);

		//Log.trace(ob.printXML());
		Log.trace("update default model(ngus)");
		// update default model
		// StringBuffer apps = new StringBuffer("");
		Resource res = null;

		// update default model "ngus"
		// boolean bExist = true;
		Model ngusModel = RDFEngine.instance().getNgusModel();
		try {
			((ModelRDB)ngusModel).getConnection().getConnection().setAutoCommit(false);
			ngusModel.begin();

			// get or generate resouce
			res = ngusModel.getResource(resourceId);
			if (res == null) {
				// bExist = false;
				res = ngusModel.createResource(resourceId);
				// ob.setAppRefCount(0);
			}
			/*
			 * // create time // if (!bExist) {
			 * 
			 * Statement stmt =
			 * res.getProperty(ngusModel.createProperty(RDFEngine
			 * .instance().getModelDesc(""), IDataEngine.ATTR_CREATETIME)); if
			 * (stmt != null) stmt.changeObject(c_tm); else
			 * res.addProperty(ngusModel.createProperty(RDFEngine.instance()
			 * .getModelDesc(""), IDataEngine.ATTR_CREATETIME), c_tm); // } //
			 * update time Timestamp u_tm = new
			 * Timestamp(System.currentTimeMillis()); stmt =
			 * res.getProperty(ngusModel.createProperty(RDFEngine
			 * .instance().getModelDesc(""), IDataEngine.ATTR_UPDATETIME)); if
			 * (stmt != null) stmt.changeObject(u_tm); else
			 * res.addProperty(ngusModel.createProperty(RDFEngine.instance()
			 * .getModelDesc(""), IDataEngine.ATTR_UPDATETIME), u_tm); // share
			 * level long lvl = (long) ob.getShareLevel(); stmt = res
			 * .getProperty(ngusModel.createProperty(RDFEngine.instance()
			 * .getModelDesc(""), IDataEngine.ATTR_SHARE_LEVEL)); if (stmt !=
			 * null) stmt.changeObject(lvl); else
			 * res.addProperty(ngusModel.createProperty(RDFEngine.instance()
			 * .getModelDesc(""), IDataEngine.ATTR_SHARE_LEVEL), lvl); // type
			 * long type = (long) ob.getType(); stmt =
			 * res.getProperty(ngusModel.createProperty(RDFEngine
			 * .instance().getModelDesc(""), IDataEngine.ATTR_TYPE)); if (stmt !=
			 * null) stmt.changeObject(type); else
			 * res.addProperty(ngusModel.createProperty(RDFEngine.instance()
			 * .getModelDesc(""), IDataEngine.ATTR_TYPE), type); // // title //
			 * String title = ob.getTitle(); // stmt =
			 * res.getProperty(ngusModel.createProperty(RDFEngine //
			 * .instance().getModelDesc(""), IDataEngine.ATTR_TITLE)); // if
			 * (stmt != null) // stmt.changeObject(title); // else //
			 * res.addProperty(ngusModel.createProperty(RDFEngine.instance() //
			 * .getModelDesc(""), IDataEngine.ATTR_TITLE), title); // owner
			 * String owner = ob.getUser(); stmt =
			 * res.getProperty(ngusModel.createProperty(RDFEngine
			 * .instance().getModelDesc(""), IDataEngine.ATTR_USER)); if (stmt !=
			 * null) stmt.changeObject(owner); else
			 * res.addProperty(ngusModel.createProperty(RDFEngine.instance()
			 * .getModelDesc(""), IDataEngine.ATTR_USER), owner); // // resource
			 * type // List<String> resType = ob.getResourceType(); // stmt =
			 * res.getProperty(ngusModel.createProperty(RDFEngine //
			 * .instance().getModelDesc(""), IDataEngine.ATTR_RESTYPE)); // if
			 * (stmt != null) // stmt.changeObject(resType); // else //
			 * res.addProperty(ngusModel.createProperty(RDFEngine.instance() //
			 * .getModelDesc(""), IDataEngine.ATTR_RESTYPE), resType); // //
			 * tags // List<String> tags = ob.getTags(); // stmt =
			 * res.getProperty(ngusModel.createProperty(RDFEngine //
			 * .instance().getModelDesc(""), IDataEngine.ATTR_TAGS)); // if
			 * (stmt != null) // stmt.changeObject(tags); // else //
			 * res.addProperty(ngusModel.createProperty(RDFEngine.instance() //
			 * .getModelDesc(""), IDataEngine.ATTR_TAGS), tags);
			 */
		
		
					
		} catch (Exception e) {
			Log.error(e);
			Log.trace("ngusModle autoCommit = " +((ModelRDB)ngusModel).getConnection().getConnection().getAutoCommit());
			ngusModel.abort();
			return null;
		}

		Log.trace("update tags");
		this.updateTagsTable(ob, ob.getUser(), update);
		//Log.trace(ob.printXML());
		// update cache
		Log.trace("put to cache...");
		CacheWriter.putROToCache(resourceId, ob);
		Log.trace("put to cache OK.");
		//Log.trace(ob.printXML());
		Log.trace("update app models");
		// update app models
		DataObjectList models = ob.getModels();
		if (models != null) {

			for (int i = 0; i < models.size(); i++) {
				try {
//					DataObjectList dol = (DataObjectList) models.get(i);
//					if (dol.size() != 0)
						setResourceDes(ob, (ResDesObject) (models.get(i)));
					// ob.setAppRefCount(ob.getAppRefCount() + 1);
					// if (i != 0)
					// apps.append(";");
					// apps.append(((ResDesObject)
					// (dol).get(0)).getModelName());

					Log.trace("update apps");
					// update apps record
					String appName = ((ResDesObject) models.get(i)).getModelName();
					updateApps(appName,	res);
					
					// update user updatetime table
					updateTimeTable(Long.parseLong(ob.getUser()), appName);

				} catch (Exception e) {
					Log.error("update model failed" + ", Exception:"
							+ Log.printExpStack(e));
					Log.trace("ngusModle autoCommit = " +((ModelRDB)ngusModel).getConnection().getConnection().getAutoCommit());
					ngusModel.abort();
					return null;
				}
			}
		}

		ngusModel.commit();

		Log.trace("insert children ro");
		DataObjectList children = ob.getChildResourceObjects();
		for (int n = 0; n < children.size(); n++) {
			post((ResourceObject) children.get(n));
		}
		
		

		Log.trace("leave post.");

		if (update)
			return this.getResourceObjById(resourceId, true).printXML();
		else
		return resourceId;

	}
	
	// update user updatetime table
	private void updateTimeTable(long user, String app){
		Log.trace("enter");
	
		java.sql.Statement st = null;
		java.sql.Connection c = null;
		try {
			c = com.ngus.dataengine.DBConnection.getConnection();
			String sql = "select * from updatetime where userid="+user+" and app='" + app + "'";
			Log.trace(sql);
			st = c.createStatement();
			java.sql.ResultSet rs = st.executeQuery(sql);
			long curTime = DateTime.currentUTCTimeStamp().getTime();
			if (rs != null && rs.next()){ // update
				sql = "update updatetime set updatetime="+curTime+" where userid="+user+" and app='" + app + "'";
				Log.trace(sql);
				st.executeUpdate(sql);
			}
			else { // insert
				sql = "insert into updatetime values("+user+", '"+app+"', "+ curTime +")";
				Log.trace(sql);
				st.executeUpdate(sql);
			}
		} catch(Exception e){
			Log.error(e);
		}
		finally {
			try {
				if (st != null)
					st.close();
				if (c != null)
					c.close();
			} catch (SQLException e) {
				Log.error(e);
			}
		}

		
		
		
	}

	// public void updateDefaultModel(ResourceObject rdo){
	//		
	//		
	//		
	// }
	//	

	// class IndexUpdator extends Thread {
	// // String method;
	// Index index;
	//
	// ResDesObject old;
	//
	// ResDesObject rdo;
	//
	// Timestamp time;
	//
	// String userId;
	//
	// public Index getIndex() {
	// return index;
	// }
	//
	// public void setIndex(Index index) {
	// this.index = index;
	// }
	//
	// public IndexUpdator(Index index, ResDesObject rdo, ResDesObject old,
	// Timestamp time, String userId) {
	// // this.method = method;
	// this.index = index;
	// this.rdo = rdo;
	// this.old = old;
	// this.time = time;
	// this.userId = userId;
	// }
	//
	// public void run() {
	// try {
	// // int count = IndexManager.instance().selectCountById(index,
	// // rdo.getResId());
	// // if (count == 0)
	// if (old == null)
	// IndexManager.instance().insertToIndex(index, rdo, time,
	// userId);
	// else
	// IndexManager.instance().updateIndex(index, rdo, old, time,
	// userId);
	// } catch (Exception e) {
	// Log.trace("error when update index " + index.getName()
	// + ", Exception:" + Log.printExpStack(e));
	// }
	//
	// }
	// }
	//
	//	

	class ROIndexUpdator extends Thread {
		// String method;
		Index index;

		Timestamp time;

		ResourceObject ro;

		ResourceObject old;

		String app;

		public Index getIndex() {
			return index;
		}

		public void setIndex(Index index) {
			this.index = index;
		}

		public ROIndexUpdator(Index index, ResourceObject ro,
				ResourceObject old, String app, Timestamp time) {
			// this.method = method;
			this.index = index;
			this.ro = ro;
			this.old = old;
			this.time = time;
			this.app = app;
		}

		public void run() {
			try {
				// int count = IndexManager.instance().selectCountById(index,
				// rdo.getResId());
				// if (count == 0)
				if (old == null)
					IndexManager.instance().insertToIndex(index, ro, app, time);
				else
					IndexManager.instance().updateIndex(index, ro, old, app,
							time);
			} catch (Exception e) {
				Log.trace("error when update index " + index.getName()
						+ ", Exception:" + Log.printExpStack(e));
			}

		}
	}

	// class IndexDeletor extends Thread {
	// // String method;
	// Index index;
	//
	// ResDesObject rdo;
	//
	// public Index getIndex() {
	// return index;
	// }
	//
	// public void setIndex(Index index) {
	// this.index = index;
	// }
	//
	// public IndexDeletor(Index index, ResDesObject rdo) {
	// // this.method = method;
	// this.index = index;
	// this.rdo = rdo;
	//
	// }
	//
	// public void run() {
	// try {
	// IndexManager.instance().deleteFromIndex(index, rdo);
	// // Log.trace("delete "+ ret + " record from index
	// // "+index.getName());
	// } catch (Exception e) {
	//
	// Log.trace("error when delete index" + index.getName()
	// + ". Exception" + Log.printExpStack(e));
	// }
	//
	// }
	// }

	class ROIndexDeletor extends Thread {
		// String method;
		Index index;

		ResourceObject ro;

		String app;

		public Index getIndex() {
			return index;
		}

		public void setIndex(Index index) {
			this.index = index;
		}

		public ROIndexDeletor(Index index, ResourceObject ro, String app) {
			// this.method = method;
			this.index = index;
			this.ro = ro;
			this.app = app;

		}

		public void run() {
			try {
				IndexManager.instance().deleteFromIndex(index, app, ro);
				// Log.trace("delete "+ ret + " record from index
				// "+index.getName());
			} catch (Exception e) {
				Log.error("error when delete index " + index.getName()
						+ ". Exception" + Log.printExpStack(e));
			}

		}
	}

	// public void updateIndex(String modelName, String resId, ResDesObject rdo,
	// ResDesObject old, Timestamp updateTime, String userId)
	// throws Exception {
	// if (modelName == null || resId == null)
	// return;
	// Log.trace("userId = " + userId);
	// updateIndex(modelName, rdo, old, updateTime, userId);
	// }

	/*
	 * public void updateAllIndexForRO(ResourceObject ro, ResourceObject old,
	 * Timestamp updateTime) throws Exception { // get index which is related
	 * with this model List<Index> indexes =
	 * IndexManager.instance().getAllIndex(); List<ROIndexUpdator> updators =
	 * new ArrayList<ROIndexUpdator>(); if (indexes != null) for (int i = 0; i <
	 * indexes.size(); i++) { Index index = indexes.get(i); if
	 * (!index.isMatchIndex(ro)) continue; ROIndexUpdator iu = new
	 * ROIndexUpdator(index, ro, old, updateTime); // start // threads // to
	 * update // all // indexes updators.add(iu); iu.start(); } // for (int i =
	 * 0; i < updators.size(); i++) { // IndexUpdator iu = updators.get(i); //
	 * if (iu.getIndex().isSyncUpdate()) // if sync mode, join it // iu.join(); // } }
	 */
	private void deleteFromIndex(String modelName, ResourceObject ro)
			throws Exception {
		if (modelName == null || ro == null)
			return;
		// get index which is related with this model// get index which is
		// related with this model
		List<Index> indexes = IndexManager.instance().getIndexByMappedName(
				modelName);

		List<ROIndexDeletor> updators = new ArrayList<ROIndexDeletor>();
		if (indexes != null)
			for (int i = 0; i < indexes.size(); i++) {
				Index index = indexes.get(i);
				ROIndexDeletor iu = new ROIndexDeletor(index, ro, modelName);
				updators.add(iu);
				iu.start();
				Log.trace("start to delete from index " + index.getName()
						+ "..." + iu.getName() + "(" + iu.getId() + ")");
			}

		for (int i = 0; i < updators.size(); i++) {
			ROIndexDeletor iu = updators.get(i);
			if (iu.getIndex().isSyncUpdate()) // if sync mode, join it
				iu.join();
		}
	}

	private void updateIndex(String modelName, ResourceObject ro,
			ResourceObject old, Timestamp updateTime) throws Exception {
		if (modelName == null || ro == null)
			return;
		// get index which is related with this model
		List<Index> indexes = IndexManager.instance().getIndexByMappedName(
				modelName);
		List<ROIndexUpdator> updators = new ArrayList<ROIndexUpdator>();
		if (indexes != null)
			for (int i = 0; i < indexes.size(); i++) {
				Index index = indexes.get(i);
				ROIndexUpdator iu = new ROIndexUpdator(index, ro, old,
						modelName, updateTime); // start
				// threads
				// to update
				// all
				// indexes
				updators.add(iu);
				iu.start();
				Log.trace("start update index " + index.getName()
						+ ", thread id = " + iu.getName() + "(" + iu.getId()
						+ ")");
			}

		for (int i = 0; i < updators.size(); i++) {
			ROIndexUpdator iu = updators.get(i);
			if (iu.getIndex().isSyncUpdate()) // if sync mode, join it
				iu.join();
		}
	}

	private void updateApps(String app, Resource res) throws Exception {
		Log.trace("enter, app="+app);
// int a = 0;
// if (a == 0)
// return;
		Model ngusModel = RDFEngine.instance().getNgusModel();
		app = app.toLowerCase();
		StmtIterator it = res.listProperties(ngusModel.createProperty(RDFEngine
				.instance().getModelDesc(""), IDataEngine.ATTR_APPS));
		if (it != null )
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			String appName = stmt.getString();
			Log.trace("appname="+appName);
			if (appName.equalsIgnoreCase(app))
				return;
		}

		res.addProperty(ngusModel.createProperty(RDFEngine.instance()
				.getModelDesc(""), IDataEngine.ATTR_APPS), app);
		Log.trace("leave");	
	}

	String genUserAppId(String userId, String app) {
		return "userapp://ngus/" + userId + "/" + app;
	}

	List getResOfUserApp(String userId, String app) throws Exception {
		List ret = new ArrayList();

		// resource id of model userapp
		String resource_id = genUserAppId(userId, app);
		Model userAppModel = RDFEngine.instance().getUserAppModel();
		// get or generate resouce
		Resource res = userAppModel.getResource(resource_id);
		if (res == null) {
			Log.trace("didn't find resource " + resource_id
					+ "in model userapp");
			return null;
		}

		Property p = userAppModel.createProperty(RDFEngine.instance()
				.getModelDesc(""), IDataEngine.ATTR_USERAPP_RESID);
		StmtIterator it = res.listProperties();

		while (it.hasNext()) {
			String rid = it.nextStatement().getString();
			ret.add(rid);
		}

		return ret;
	}

	/**
	 * update user-app relationship
	 * 
	 * @param userId
	 * @param app
	 */
	void updateUserApp(String userId, String app, String resId)
			throws Exception {

		int a = 0;
		if (a == 0)
			return;

		app = app.toLowerCase();
		Model userAppModel = RDFEngine.instance().getUserAppModel();
		Log.trace("enter updateUserApp");
		// resource id of model userapp
		String resource_id = "userapp://ngus/" + userId + "/" + app;

		// get or generate resouce
		Resource res = userAppModel.getResource(resource_id);
		if (res == null) {
			// create new resource
			Log.trace("Resource not found, create new resource for id "
					+ resource_id);
			res = userAppModel.createResource(resource_id);
		}

		Property p = userAppModel.createProperty(RDFEngine.instance()
				.getModelDesc(""), "resid");

		StmtIterator it = res.listProperties();

		while (it.hasNext()) {
			String rid = it.nextStatement().getString();
			Log.trace("rid" + rid);
			if (rid.equals(resId))
				return;
		}

		res.addProperty(p, resId);
		Log.trace("leave updateUserApp");
	}

	private void addAttributeAsPropertyToRes(String resId, Model model,
			String modelName, Resource res, Attribute attr) throws Exception {
		Log.trace("enter");
		String uri = RDFEngine.instance().getModelDesc(modelName);

		Log.trace("attribute type=" + attr.valueType());
		if (attr.valueType() == Attribute.ATTR_DT_STR) {
			Statement st = res.getProperty(model.createProperty(uri, attr
					.getName()));
			if (st == null)
				res.addProperty(model.createProperty(uri, attr.getName()), attr
						.getStringValue());
			else
				st.changeObject(attr.getStringValue());

		} else if (attr.valueType() == Attribute.ATTR_DT_LIST) {
			List values = (List) attr.getValue();
			Statement st = res.getProperty(model.createProperty(uri, attr
					.getName()));
			if (st == null)
				res.addProperty(model.createProperty(uri, attr.getName()),
						values);
			else
				st.changeObject(values);
			// for (int j = 0; j < values.size(); j++)
			// res.addProperty(model.createProperty(uri, attr
			// .getName()), values.get(j).toString());
		} else if (attr.valueType() == Attribute.ATTR_DT_SUBOBJ) {
			DataObjectList dol = (DataObjectList) attr.getValue();
			String new_id = resId + "@" + attr.getName();
			Resource res_attr = model.getResource(new_id);
			res
					.addProperty(model.createProperty(uri, attr.getName()),
							res_attr);
			for (int a = 0; a < 0; a++) {
				DataObject dot = dol.get(a);
				for (int i = 0; i < dot.size(); i++) {
					Attribute attr1 = dot.getAttr(i);
					addAttributeAsPropertyToRes(new_id, model, modelName, res,
							attr1);
				}
			}
		}
		Log.trace("leave");
	}

	public List<ResourceObject> listROByUserModel(String userid, String modelName, int start, int number, boolean bRDO, Attribute total) throws Exception{
		Log.trace("enter");
		if (start < 0)
			start =0;
		if (number <0)
			number = Integer.MAX_VALUE;
		List<ResourceObject>  ret = new ArrayList<ResourceObject>();
		java.sql.Statement st = null;
		java.sql.Connection c = null;
		
			c = com.ngus.dataengine.DBConnection.getConnection();
			
				String sql = "select resid from ngus_resmodeluser where userid="+Integer.parseInt(userid)+" and app='" + modelName +"'";
						
				Log.trace(sql);
				try{
					st = c.createStatement();
					java.sql.ResultSet rs = st.executeQuery(sql);
					Log.trace("execute '" + sql + "'" + " return " + rs);
					if (rs != null) {
						
						while(start>0){
							start --;
							rs.next();
						}
						while (rs.next()) {	
							String resid = rs.getString(1);
							ResourceObject ro = this.getResourceObjById(resid, true);
							if (ro != null)	
							{
								if (bRDO)
								{
								ResDesObject rdo = getResourceDesObj(resid, modelName);
								if (rdo != null)
									ro.addResDesObject(rdo);
								else
									Log.warning("cannot get rdo resource "+resid+" with model "+ modelName);
								}
								ret.add(ro);
							}
							else
								ro = new ResourceObject(); // empty object
							number --;
							if (number <= 0)
								break;
						}
						rs.last();
						if (total != null) {
							total.setValue(rs.getRow());
						}
					}
				} catch (Exception e) {
					Log.error(e);
					
				} finally {
					// Always make sure result sets and statements are closed,
					// and the connection is returned to the pool
					// if (rs != null) {
					// try { rs.close(); } catch (SQLException e) { ; }
					// rs = null;
					// }
					if (st != null) {
						try {
							st.close();
						} catch (Exception e) {
							Log.error(e);
						}

						st = null;
					}

				}
				
		return ret;
	}
	
	private void deleteFromRMU(String resid, String modelName) throws Exception{
		Log.trace("enter");
		
		java.sql.Statement st = null;
		java.sql.Connection c = null;
		
			c = com.ngus.dataengine.DBConnection.getConnection();
			
				String sql = "delete from ngus_resmodeluser where resid='"+ resid+"' and app='" + modelName +"'";
						
				Log.trace(sql);
				try {
					st = c.createStatement();
					int r = st.executeUpdate(sql);
					Log.trace("execute '" + sql + "'" + " return " + r);

					// Log.trace("update "+ i + " records");
					if (r < 1) {
						// throw new Exception("update index " + index.getName()
						// + "
						// failed");
						Log.error("delete failed, sql=" + sql);
						;
					}
				} catch (Exception e) {
					Log.error(e);
					;
				} finally {
					// Always make sure result sets and statements are closed,
					// and the connection is returned to the pool
					// if (rs != null) {
					// try { rs.close(); } catch (SQLException e) { ; }
					// rs = null;
					// }
					if (st != null) {
						try {
							st.close();
						} catch (Exception e) {
							Log.error(e);
						}

						st = null;
					}

				}
				
		return;

	}

	private void updateResModelUserTable(ResourceObject ro, String modelName) throws Exception{
		Log.trace("enter");
		
		java.sql.Statement st = null;
		java.sql.Connection c = null;
		
			c = com.ngus.dataengine.DBConnection.getConnection();
			
				String sql = "insert into ngus_resmodeluser values("+Integer.parseInt(ro.getUser())+", '" + modelName +"', '"+ ro.getResId() +"')";
						
				Log.trace(sql);
				try {
					st = c.createStatement();
					int r = st.executeUpdate(sql);
					Log.trace("execute '" + sql + "'" + " return " + r);

					// Log.trace("update "+ i + " records");
					if (r < 1) {
						// throw new Exception("update index " + index.getName()
						// + "
						// failed");
						Log.error("delete failed, sql=" + sql);
						;
					}
				} catch (Exception e) {
					Log.error(e);
					;
				} finally {
					// Always make sure result sets and statements are closed,
					// and the connection is returned to the pool
					// if (rs != null) {
					// try { rs.close(); } catch (SQLException e) { ; }
					// rs = null;
					// }
					if (st != null) {
						try {
							st.close();
						} catch (Exception e) {
							Log.error(e);
						}

						st = null;
					}

				}
				
		return;

	}
	
	public void addResourceDes(ResourceObject ro, ResDesObject ob, Resource res)
			throws Exception {
		if (ob == null || res == null)
			throw new IllegalArgumentException();
		Log.trace("enter addResourceDes.");
		Log.trace(ob.printXML());
		Model model = res.getModel();
		// // get module name
		String modelName = ob.getModelName();
		// Log.trace("model name = " + modelName);
		// get model uri (model description)
		// String uri = RDFEngine.instance().getModelDesc(modelName);

		// create time
		Timestamp tm = new Timestamp(System.currentTimeMillis());
		Statement stmt = res.getProperty(model.createProperty(RDFEngine
				.instance().getModelDesc(""), IDataEngine.ATTR_CREATETIME));
		if (stmt == null)
			res.addProperty(model.createProperty(RDFEngine.instance()
					.getModelDesc(""), IDataEngine.ATTR_CREATETIME), tm);

//		String userid = ro.getUser();
//		stmt = res.getProperty(model.createProperty(RDFEngine
//				.instance().getModelDesc(""), IDataEngine.ATTR_USER));
//		if (stmt == null)
//			res.addProperty(model.createProperty(RDFEngine.instance()
//					.getModelDesc(""), IDataEngine.ATTR_USER), userid);
//		
		// update time
		RDFEngine.instance().updateResDesUpdateTime(res, model);

		// set owner
		// res.addProperty(model.createProperty(uri, IDataEngine.ATTR_APP_USER),
		// UserManager.getCurrentUser());

		// Log.trace(uri+objectId);
		for (int i = 0; i < ob.size(); i++) {
			Attribute attr = ob.getAttr(i);
			if (attr.getName().equalsIgnoreCase("createTime")
					|| attr.getName().equalsIgnoreCase("updateTime"))
				continue;
			Log.trace(attr.printHtml());
			// res.addProperty(model.createProperty(uri, "FN"),
			// attr.getStringValue());

			try {
				addAttributeAsPropertyToRes(ob.getResId(), model, modelName,
						res, attr);
			} catch (Exception e) {
				Log.trace("model "+modelName+" autoCommit = " +((ModelRDB)model).getConnection().getConnection().getAutoCommit());
				model.abort();
				// if (model instanceof ModelRDB) {
				// ((ModelRDB) model).getConnection().close();
				// }
				// model.close();
				RDFEngine.closeModel(model);
				throw new DEException("can not add property "
						+ attr.printHtml(), e);
			}
		}

		// update ngus_resmodel
		updateResModelUserTable(ro, modelName);
		
		Log.trace("update model " + modelName + " for resource "
				+ ob.getResId() + " ok");

		// model.write(System.out); // this operation cost must time

		// model.commit();
		// if (model instanceof ModelRDB) {
		// ((ModelRDB) model).getConnection().close();
		// }
		// model.close();

		Log.trace("starting update index");

		updateIndex(modelName, ro, null, tm);

		Log.trace("updating userapp model");
		// update user-app relationship
		this.updateUserApp(ob.getUser(), modelName, ob.getResId());
		Log.trace("update model ok");
		Log.trace("leave addResourceDes.");
	}

	private void setResourceDes(ResourceObject ro, ResDesObject ob)
			throws Exception {
		if (ob == null)
			throw new IllegalArgumentException();
		Log.trace("enter setResourceDes.");
		Log.trace(ob.printXML());

		Model model = null;
		// get module name
		String modelName = ob.getModelName();
		Log.trace("model name = " + modelName);

		// open model
		model = RDFEngine.openModule(modelName, true);		
		((ModelRDB)model).getConnection().getConnection().setAutoCommit(false);
		model.begin();

		try {
			Log.trace("check whether resource exists");
			// get or generate resouce
			if (hasResource(model, ob.getResId())) {
				Log.trace("resource " + ob.getResId() + " already exists.");
				Resource res = model.getResource(ob.getResId());
				ResourceObject old = this.getResourceObjById(ob.getResId(),
						true);
				old.addResDesObject(convertResourceToObj(res, ob
						.getModelName()));
				this.updateResourceDes(ro, old, ob, res);
				model.commit();
				// RDFEngine.closeModel(model);
				return;
			} else {
				Log.trace("create resource " + ob.getResId());
				Resource res = model.createResource(ob.getResId());
				addResourceDes(ro, ob, res);
				model.commit();
				return;
				// RDFEngine.closeModel(model);
			}
		}catch( Exception e){
			Log.error(e);
			Log.trace("model "+modelName + " autoCommit = " +((ModelRDB)model).getConnection().getConnection().getAutoCommit());
			model.abort();
		}
		finally {
			RDFEngine.closeModel(model);
		}

	}

	public void updateResourceDes(ResDesObject ob) throws Exception {
		// open model
		Model model = null;
		try {
			ResourceObject ro = this.getResourceObjById(ob.getResId(), true);
			ro.addResDesObject(ob);
			ResourceObject old = new ResourceObject(ro);
			old.addResDesObject(this.getResourceDesObj(ob.getResId(), ob
					.getModelName()));
			model = RDFEngine.openModule(ob.getModelName(), true);
			((ModelRDB)model).getConnection().getConnection().setAutoCommit(false);
			model.begin();
			try{
				updateResourceDes(ro, old, ob, model.getResource(ob.getResId()));
			}catch(Exception e){
				Log.error(e);
				Log.trace("model "+ob.getModelName()+" autoCommit = " +((ModelRDB)model).getConnection().getConnection().getAutoCommit());
				model.abort();
				throw e;
			}
			model.commit();			
		} finally {
			RDFEngine.closeModel(model);
		}
		
		updateTimeTable(Long.parseLong(ob.getUser()), ob.getModelName());

	}

	/**
	 * update resource description
	 * 
	 * @param id
	 *            uuid of resource
	 * @param ob
	 */
	public void updateResourceDes(ResourceObject ro, ResourceObject old,
			ResDesObject ob, Resource res) throws Exception {
		if (ob == null || res == null)
			throw new IllegalArgumentException();
		Log.trace("enter updateResourceDes.");
		Log.trace(ob.printXML());

		Model model = res.getModel();
		// get module name
		String modelName = ob.getModelName();
		Log.trace("model name = " + modelName);

		// open model
		// model = RDFEngine.openModule(modelName, true);
		// model.begin();

		// get model uri (model description)
		// String uri = RDFEngine.instance().getModelDesc(modelName);
		// ResDesObject old = null;

		// get or generate resouce
		// Resource res = model.getResource(id);
		// if (res == null) {
		// // create new resource
		// Log.trace("Resource not found, create new resource for id " + id);
		// res = model.createResource(id);
		// } else
		// old = convertResourceToObj(res, modelName);
		// old = convertResourceToObj(res, modelName);

		// create time
		Timestamp tm = new Timestamp(System.currentTimeMillis());
		Statement stmt = res.getProperty(model.createProperty(RDFEngine
				.instance().getModelDesc(""), IDataEngine.ATTR_CREATETIME));
		if (stmt == null)
			res.addProperty(model.createProperty(RDFEngine.instance()
					.getModelDesc(""), IDataEngine.ATTR_CREATETIME), tm);

		// update time
		RDFEngine.instance().updateResDesUpdateTime(res, model);

		// set owner
		// res.addProperty(model.createProperty(uri, IDataEngine.ATTR_APP_USER),
		// UserManager.getCurrentUser());

		// Log.trace(uri+objectId);
		for (int i = 0; i < ob.size(); i++) {
			Attribute attr = ob.getAttr(i);
			if (attr.getName().equalsIgnoreCase("createTime")
					|| attr.getName().equalsIgnoreCase("updateTime"))
				continue;
			Log.trace(attr.printHtml());
			// res.addProperty(model.createProperty(uri, "FN"),
			// attr.getStringValue());

			try {
				addAttributeAsPropertyToRes(ob.getResId(), model, modelName,
						res, attr);
			} catch (Exception e) {
				Log.trace("model "+ modelName +" autoCommit = " +((ModelRDB)model).getConnection().getConnection().getAutoCommit());
				model.abort();
				// if (model instanceof ModelRDB) {
				// ((ModelRDB) model).getConnection().close();
				// }
				// model.close();
				RDFEngine.closeModel(model);
				throw new DEException("can not add property "
						+ attr.printHtml(), e);
			}
		}

		Log.trace("update model " + modelName + " for resource "
				+ ob.getResId() + " ok");

		// model.write(System.out);

		// model.commit();
		// // if (model instanceof ModelRDB) {
		// // ((ModelRDB) model).getConnection().close();
		// // }
		// // model.close();
		// RDFEngine.closeModel(model);

		updateIndex(modelName, ro, old, tm);

		// update user-app relationship
		this.updateUserApp(ob.getUser(), modelName, ob.getResId() + "");
		
		updateTimeTable(Long.parseLong(ro.getUser()), ob.getModelName());
		
		// update cache
		Log.trace("put to cache...");
		CacheWriter.putRDOToCache(ob.getResId(), ob);
		Log.trace("put to cache OK.");

		Log.trace("leave updateResourceDes.");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gnus.dataengine.IDataEngine#post(java.lang.String,
	 *      com.ns.dataobject.DataObject)
	 *//*
		 * public String post(String userName, String modelName, DataObject ob) {
		 * if (ob == null) throw new IllegalArgumentException();
		 * Log.trace("post..."); Log.trace(ob.print());
		 * 
		 * Model model = null; // open model try { model = ModelRDB.open(dbc,
		 * modelName); } catch (com.hp.hpl.jena.shared.DoesNotExistException e) {
		 * model = ModelRDB.createModel(dbc, modelName); } Log.trace("get model
		 * ok"); // generate object id String objectId; if (ob.getId() == null) { //
		 * create new id objectId = "http://gnus/" + userName + "#" + count++;
		 * ob.setId(objectId); } else objectId = ob.getId();
		 * 
		 * Log.trace(objectId); // generate resouce Resource res =
		 * model.createResource(objectId); //Log.trace(uri+objectId);
		 * 
		 * for (int i = 0; i < ob.size(); i++) { Attribute attr = ob.getAttr(i);
		 * Log.trace(attr.printHtml());
		 * //res.addProperty(model.createProperty(uri, "FN"),
		 * attr.getStringValue()); res.addProperty( model.createProperty(uri,
		 * attr.getName()), attr.getStringValue()); }
		 * 
		 * Log.trace("create reaousce ok");
		 * 
		 * model.write(System.out);
		 * 
		 * model.close(); Log.trace("post ok");
		 * 
		 * return objectId; }
		 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gnus.dataengine.IDataEngine#get(java.lang.String,
	 *      com.ns.dataobject.DataObject)
	 */
	/*
	 * public DataObjectList get(String userName, DataObject ob) throws
	 * DEException{ Log.trace("get..."); Log.trace(dbc); Model model = null; //
	 * open model try{ model = ModelRDB.open(dbc, modelName); }catch
	 * (com.hp.hpl.jena.shared.DoesNotExistException e){ throw new
	 * DEException("modle dose not exist", e); } DataObjectList ret = new
	 * DataObjectList();
	 * 
	 * //DataObject ret_ob = new DataObject(""); String objectId; // get object
	 * id objectId = ob.getId(); // if (objectId != null) // { //
	 * ob.setId(objectId); // }
	 * 
	 * 
	 * 
	 * 
	 * SimpleSelector ss = new SimpleSelector(model.createResource(objectId),
	 * (Property)null, (String)null); // select resouce StmtIterator iter =
	 * model.listStatements(ss); // add the Smith's to the bag while
	 * (iter.hasNext()) { Statement stmt = iter.nextStatement();
	 * ret_ob.setId(stmt.getSubject().getURI()); try { ret.addAttr(new Attribute
	 * (stmt.getPredicate().getLocalName(), Attribute.ATTR_DT_STR,
	 * stmt.getString())); }catch (Exception e){ Log.error(e); continue; } }
	 * 
	 * 
	 * model.write(System.out);
	 * 
	 * model.close(); Log.trace("get ok");
	 * 
	 * return ob; }
	 */
	/**
	 * select resource descrioption using RDQL return a list of ResourcObject
	 */
	public List<ResDesObject> selectByRDQL(String modelName, String queryString)
			throws Exception {
		if (modelName == null || modelName.length() == 0)
			modelName = "[default]";
		modelName = modelName.toLowerCase();
		List<ResDesObject> ret = null;
		if (queryString == null || queryString.length() == 0)
			throw new IllegalArgumentException();
		// Query query = new Query(queryString);
		Query query = QueryFactory.create(queryString);

		// DataObjectList ret = new DataObjectList();

		Model model = null;

		// open model
		// if (modelName != null && modelName.length() != 0) {

		// try {
		model = RDFEngine.openModule(modelName, true);
		// } catch (com.hp.hpl.jena.shared.DoesNotExistException e) {
		// model = ModelRDB.createModel(RDFEngine.instance().getDbc(),
		// modelName);
		// }
		// model.write(System.out);
		// }
		// Need to set the source if the query does not.
		// This provided this override any query named source.
		// query.setSource(model);
		// QueryExecution qe = new QueryEngine(query);

		ret = selectByRDQL(model, modelName, queryString);
		/*
		 * QueryResultsFormatter fmt = new QueryResultsFormatter(results) ;
		 * PrintWriter pw = new PrintWriter(System.out) ; fmt.printAll(pw, " | ") ;
		 * pw.flush() ; fmt.close() ;
		 */
		// results.close();
		// if (model instanceof ModelRDB) {
		// ((ModelRDB) model).getConnection().close();
		// }
		// model.close();
		RDFEngine.closeModel(model);
		return ret;
	}

	public List<ResDesObject> selectByRDQL(Model model, String modelName,
			String queryString) {
		List<ResDesObject> ret = new ArrayList<ResDesObject>();
		QueryExecution qe = null;
		if (model == null)
			qe = QueryExecutionFactory.create(queryString);
		else
			qe = QueryExecutionFactory.create(queryString, model);

		ResultSet results = qe.execSelect();
		while (results.hasNext()) {
			QuerySolution qs = results.nextSolution();
			// Return from get is null if not found
			Resource p = qs.getResource("x");

			// Resource v = qs.getResource("v");
			// Log.trace("p=" + p.getClass());
			// Log.trace("v=" + v.getClass());
			ResDesObject rdo = this.convertResourceToObj(p, modelName);
			// Set r = qs.get();
			// Iterator it = r.iterator();
			// while (it.hasNext()) {
			// Statement st = (Statement) it.next();
			// System.out.println("statment:" + st);
			// ResourceObject o = new ResourceObject();
			// o.setResId(st.getSubject().getURI());
			// ResDesObject rdo = new ResDesObject(modelName);
			// o.addModule(rdo);
			//
			// try {
			// rdo.addAttr(new Attribute(st.getPredicate().getURI(),
			// Attribute.ATTR_DT_STR, st.getString()));
			// } catch (Exception e) {
			// Log.error(e);
			// throw new DEException(
			// "add attribute to data object failed: " + e);
			// }
			ret.add(rdo);
			// }

			// obj will be a Jena object: resource, property or RDFNode.

		}
		qe.close();
		return ret;
	}

	public int selectCountByRDQL(Model model, String queryString) {

		QueryExecution qe = null;
		if (model == null)
			qe = QueryExecutionFactory.create(queryString);
		else
			qe = QueryExecutionFactory.create(queryString, model);

		ResultSet results = qe.execSelect();
		int ret = 0;
		while (results.hasNext()) {
			results.next();
			ret++;
		}
		qe.close();
		return ret;
	}

	// public void delete() throws Exception {
	// Model model = null;
	// // open model
	// try {
	// model = RDFEngine.openModule("clipboard", true);
	// } catch (com.hp.hpl.jena.shared.DoesNotExistException e) {
	// throw new DEException("modle dose not exist", e);
	// }
	// model.remove(model);
	// model.close();
	// }

	public boolean hasResource(Model model, String resId) {
		QueryExecution qe = null;
		String ql = "select ?v  where { <" + resId + "> ?x ?y.}";
		if (model == null)
			qe = QueryExecutionFactory.create(ql);
		else
			qe = QueryExecutionFactory.create(ql, model);
		try {
			ResultSet results = qe.execSelect();
			if (results == null)
				return false;
			while (results.hasNext()) {
				return true;

			}
		} finally {
			try{
				qe.close();
			}catch(Exception e){
				Log.warning(e);
			}
		}
		return false;

	}

	public void deleteAll(String modelName) throws Exception {
		modelName = modelName.toLowerCase();
		Model model = null;
		// open model
		try {
			model = RDFEngine.openModule(modelName, false);
		} catch (com.hp.hpl.jena.shared.DoesNotExistException e) {
			throw new DEException("modle dose not exist", e);
		}
		model.removeAll();
		// model.write(System.out);
		// if (model instanceof ModelRDB) {
		// ((ModelRDB) model).getConnection().close();
		// }
		// model.close();
		RDFEngine.closeModel(model);
	}

	static Attribute convertStmtToAttr(Statement st, String modelName) {
		if (st == null)
			throw new IllegalArgumentException();

		// check if it has id
		String attrName = st.getPredicate().getLocalName();
		if (attrName == null)
			return null;
		// st.getObject()

		Object value = st.getObject();
		/*
		 * Log.trace("---------------------"); Log.trace("statement:" + st);
		 * Log.trace("statement.getSubject():" + st.getSubject());
		 * Log.trace("statement.getObject():" + st.getObject()); Log.trace(
		 * "statement.getObject().getClass():" + st.getObject().getClass());
		 * Log.trace("statement.getPredicate():" + st.getPredicate()); if (value
		 * instanceof Resource) Log.trace("statement.getResource():" +
		 * st.getResource()); //Log.trace("statement.getStatementProperty():" +
		 * st.getStatementProperty()); else Log.trace("statement.getString():" +
		 * st.getString()); Log.trace( "statement.getPredicate().getURI():" +
		 * st.getPredicate().getURI()); Log.trace(
		 * "statement.getPredicate().getLocalName():" +
		 * st.getPredicate().getLocalName());
		 * Log.trace("//-------------------");
		 */
		try {
			int type = RDFEngine.instance()
					.getPropertyType(modelName, attrName);
			Log.trace("value is " + value + " " + value.getClass());
			Log.trace("type is " + type);
			// if (value instanceof Resource) { // it is a internal middle node
			// Resource res = st.getResource();
			// // StmtIterator it = res.listProperties();
			// // while (it.hasNext()) {
			// // rdo
			// // .addChild(convertStmtToDO(modelName, it
			// // .nextStatement()));
			// // }
			// return new Attribute(attrName, Attribute.ATTR_DT_SUBOBJ,
			// convertResourceToObj(res, modelName));
			if (type == RDFEngine.PROPERTY_TYPE_LIST) {
				Attribute ret = new Attribute(attrName, Attribute.ATTR_DT_LIST);
				String v = st.getString();
				v = v.substring(1, v.length() - 1);
				String[] s = v.split(",");
				List<String> ls = new ArrayList<String>();
				for (int i = 0; i < s.length; i++) {
					ls.add(s[i].trim());
				}
				ret.setValue(ls);
				return ret;
			} else {
				// if (value instanceof List)
				// return new Attribute(attrName, Attribute.ATTR_DT_LIST,
				// value);
				return new Attribute(attrName, Attribute.ATTR_DT_STR, st
						.getString());
				// a simple node
				// rdo.addAttr(new Attribute(attrName, Attribute.ATTR_DT_STR, st
				// .getString()));
			}
		} catch (Exception e) {
			return null;
		}

	}

	static ResDesObject convertResourceToObj(Resource res, String modelName) {

		// String modelURI = res.getModel().getNsPrefixURI("vcard:");
		// String modelName = "unknown";
		// if (modelURI != null)
		// modelName = modelURI.substring(12, modelURI.length()-1);
		// Log.trace(modelURI);
		// Log.trace(res.getModel().getNsURIPrefix());
		ResDesObject ret = new ResDesObject(modelName);
		ret.setResId(res.getURI());
		StmtIterator it = res.listProperties();
		int i = 0;
		while (it.hasNext()) {
			i++;
			Statement st = it.nextStatement();
			Log.trace("stmt=" + st);
			Attribute st_attr = convertStmtToAttr(st, modelName);
			if (st_attr != null)
				try {
					ret.addAttr(st_attr);
				} catch (Exception e) {
					Log.error(e);
				}
		}
		if( i== 0)
			return null;
		return ret;
	}

	/**
	 * get all resource description in this model
	 * 
	 * @param modelName
	 * @return data object list of ResDesObject
	 * @throws DEException
	 */
	public DataObjectList getAllResourceOfModel(String modelName)
			throws Exception {
		Log.trace("enter");
		modelName = modelName.toLowerCase();
		DataObjectList ret = new DataObjectList();
		Model model = null;

		// open model
		// try {
		// if (modelName != null)
		// model = ModelRDB.open(dbc, modelName);
		// else
		// model = ModelRDB.open(dbc);
		// } catch (com.hp.hpl.jena.shared.DoesNotExistException e) {
		// throw new DEException("modle " + modelName + " dose not exist", e);
		// }
		model = RDFEngine.openModule(modelName, true);
		// model.write(System.out);

		if (model == null) {
			Log.log("can not get model " + modelName);
			Log.trace("leave");
			return ret;
		}

		// convert module content to dataobject list
		ResIterator it = model.listSubjects();
		int i = 0;
		while (it.hasNext()) {
			i++;
			Resource res = it.nextResource();
			Log.trace("resource = " + res);

			ResDesObject res_ob = convertResourceToObj(res, modelName);
			if (res_ob != null)
				ret.add(res_ob);
		}
		Log.trace("found " + i + " modules for " + modelName);

		// model.write(System.out);
		// if (model instanceof ModelRDB) {
		// ((ModelRDB) model).getConnection().close();
		// }
		// model.close();
		RDFEngine.closeModel(model);
		Log.trace("leave");

		return ret;

	}

	/**
	 * gena wrapper function
	 */
	public String printAllModel() throws Exception {
		Model model = null;
		// open model
		// try {
		// model = ModelRDB.open(dbc);
		// } catch (com.hp.hpl.jena.shared.DoesNotExistException e) {
		// throw new DEException("modle dose not exist", e);
		// }
		model = RDFEngine.openModule(null, true);

		StringWriter ret = new StringWriter();
		// model.write(ret);
		System.out.println("string");
		System.out.println(ret.getBuffer());
		// if (model instanceof ModelRDB) {
		// ((ModelRDB) model).getConnection().close();
		// }
		// model.close();
		RDFEngine.closeModel(model);
		return new String(ret.getBuffer());

	}

	/**
	 * for test
	 * 
	 * @param args
	 */
	public static void testPost() throws Exception {
		// DataObject o = new DataObject("");
		// ob.addAttr(new Attribute("title", Attribute.ATTR_DT_STR, "java"));

		// DataEngine.instance().post("jackie_juju", "clipboard", ob);

		ResourceObject ob = new ResourceObject();
		ResDesObject rdo = new ResDesObject("clipboard1");
		rdo.addAttr(new Attribute("title", Attribute.ATTR_DT_STR, "java"));
		ob.addResDesObject(rdo);
		new DataEngine().post(ob);
	}

	public static void testGet() throws Exception {
		// DataObject ob = new DataObject("");
		// ob.setId("jackie1");

		Log.trace(new DataEngine().getAllResourceOfModel(null).toXML());

	}

	/**
	 * get rdo without cache
	 * 
	 * @param Id
	 * @return
	 */
	static public ResDesObject getRDO(String Id, String modelName)
			throws Exception {

		Model model = RDFEngine.openModule(modelName, false);
		if (model == null) {
			Log.error("can not open model " + modelName);
			return null;
		}
		Resource res = model.getResource(Id);

		if (res == null) {
			Log.error("can not get resource " + Id + "in model " + modelName);
			// model.close();
			RDFEngine.closeModel(model);
			return null;
		}
		ResDesObject ret = convertResourceToObj(res, modelName);
		// if (model instanceof ModelRDB) {
		// ((ModelRDB) model).getConnection().close();
		// }
		// model.close();
		RDFEngine.closeModel(model);
		return ret;
	}

	/**
	 * get resource descriptor by id and model name from cache first, db then
	 */
	public ResDesObject getResourceDesObj(String Id, String modelName)
			throws Exception {
		ResDesObject ret = null;
		ret = CacheWriter.getRDOFromCache(Id, modelName);
		Log.trace("getRDOFromCache return " + ret + ", model="+modelName);
		if (ret != null)
			return ret;
		ret = getRDO(Id, modelName);
		Log.trace("getRDO return " + ret + ", model="+modelName);
		if (ret != null)
			RDOWriter.putRDOToCache(Id, ret);
		return ret;
	}

	/**
	 * get ro without cache
	 * 
	 * @param Id
	 * @param getValue
	 * @return
	 * @throws Exception
	 */
	public static ResourceObject getROById(String Id, boolean getValue)
			throws Exception {
		ResourceObject ret = null;

		ret = getResourceObject(Id, getValue);

		/*
		 * Model modelDefault = RDFEngine.openModule(IDataEngine.MODEL_DEFAULT,
		 * false); if (modelDefault == null) { Log.error("can not open model " +
		 * IDataEngine.MODEL_DEFAULT); return null; } Resource res =
		 * modelDefault.getResource(Id); if (res == null) { Log.error("can not
		 * get resource " + Id + "in model " + IDataEngine.MODEL_DEFAULT);
		 * return null; } // Model model =
		 * RDFEngine.openModule(IDataEngine.MODEL_DEFAULT, false); // if (model ==
		 * null) { // Log.error("can not open model " +
		 * IDataEngine.MODEL_DEFAULT); // return null; // }
		 * 
		 * 
		 * Statement st = null; // create time st =
		 * res.getProperty(modelDefault.createProperty(RDFEngine.instance()
		 * .getModelDesc(""), IDataEngine.ATTR_CREATETIME)); if (st != null)
		 * ret.setCreateTime(Timestamp.valueOf(st.getString())); else
		 * Log.error("cannot get creat time for resource " + Id); // update time
		 * st = res.getProperty(modelDefault.createProperty(RDFEngine.instance()
		 * .getModelDesc(""), IDataEngine.ATTR_UPDATETIME)); if (st != null)
		 * ret.setUpdateTime(Timestamp.valueOf(st.getString())); else
		 * Log.error("cannot get update time for resource " + Id); // // title //
		 * st = res.getProperty(modelDefault.createProperty(RDFEngine.instance() //
		 * .getModelDesc(""), IDataEngine.ATTR_TITLE)); // if (st != null) //
		 * ret.setTitle(st.getString()); // else // Log.error("cannot get title
		 * for resource " + Id); // // resource type // st =
		 * res.getProperty(modelDefault.createProperty(RDFEngine.instance() //
		 * .getModelDesc(""), IDataEngine.ATTR_RESTYPE)); // if (st != null) { // //
		 * ret.setResourceType((List<String>)st.getObject()); // String v =
		 * st.getString(); // v = v.substring(1, v.length() - 1); // String[] s =
		 * v.split(","); // List<String> ls = new ArrayList<String>(); // for
		 * (int i = 0; i < s.length; i++) { // ls.add(s[i].trim()); // } //
		 * ret.setResourceType(ls); // } else // Log.error("cannot get title for
		 * resource " + Id); // // tags // st =
		 * res.getProperty(modelDefault.createProperty(RDFEngine.instance() //
		 * .getModelDesc(""), IDataEngine.ATTR_TAGS)); // if (st != null) { // //
		 * ret.setResourceType((List<String>)st.getObject()); // String v =
		 * st.getString(); // v = v.substring(1, v.length() - 1); // String[] s =
		 * v.split(","); // List<String> ls = new ArrayList<String>(); // for
		 * (int i = 0; i < s.length; i++) { // ls.add(s[i].trim()); // } //
		 * ret.setTags(ls); // } else // Log.error("cannot get tags for resource " +
		 * Id); // type st =
		 * res.getProperty(modelDefault.createProperty(RDFEngine.instance()
		 * .getModelDesc(""), IDataEngine.ATTR_TYPE)); if (st != null)
		 * ret.setType(st.getInt()); else Log.error("cannot get type for
		 * resource " + Id); // share level st =
		 * res.getProperty(modelDefault.createProperty(RDFEngine.instance()
		 * .getModelDesc(""), IDataEngine.ATTR_SHARE_LEVEL)); if (st != null)
		 * ret.setShareLevel(st.getInt()); else Log.error("cannot get share
		 * level for resource " + Id);
		 *  // if (modelDefault instanceof ModelRDB) { // ((ModelRDB)
		 * modelDefault).getConnection().close(); // } // modelDefault.close();
		 * RDFEngine.closeModel(modelDefault);]
		 */
		return ret;
	}

	/**
	 * get resource by resource id from cache first, then db
	 */
	public ResourceObject getResourceObjById(String Id, boolean getValue)
			throws Exception {
		Log.trace("enter");
		// Log.trace(Log.getStack());
		ResourceObject ret = CacheWriter.getROFromCache(Id);
		Log.trace("get ro (" + Id + ") from cache:" + ret);
		if (ret != null) 
			Log.trace("ret:"+ret.printXML());
		// Log.trace(Log.getStack());
		if (ret != null)
			return ret;
		ret = getROById(Id, getValue);
		if (ret != null) 
			Log.trace("ret:"+ret.printXML());
		if (ret != null) 
			ROWriter.putROToCache(Id, ret);
		Log.trace("leave");
		return ret;

	}

	static private ResourceObject getResourceObject(String id, boolean value)
			throws Exception {
		return ResourceEngine.instance().getResource(id, value);
	}

	static public void testJena() throws Exception {
		// some definitions
		String personURI = "http://somewhere1/tester";
		String givenName = "John";
		String familyName = "Smith";
		String fullName = givenName + " " + familyName;

		// create an empty Model
		// Model model = ModelFactory.createDefaultModel();
		Class.forName("com.mysql.jdbc.Driver");
		DBConnection dbc = new DBConnection("jdbc:mysql://localhost/test",
				"root", "", "mysql");
		// GraphRDB g = new GraphRDB(dbc, "test", null,
		// GraphRDB.OPTIMIZE_ALL_REIFICATIONS_AND_HIDE_NOTHING , true);
		Model model;
		try {
			model = RDFEngine.openModule("clipboard", true);
		} catch (com.hp.hpl.jena.shared.DoesNotExistException e) {
			model = ModelRDB.createModel(dbc, "clipboard");
			// create the resource
			// and add the properties cascading style

			Resource johnSmith = model.createResource(personURI);
			johnSmith.addProperty(VCARD.FN, fullName).addProperty(
					VCARD.N,
					model.createResource().addProperty(VCARD.Given, givenName)
							.addProperty(VCARD.Family, familyName));
		}

		Resource johnSmith = model.createResource(personURI).addProperty(
				VCARD.FN, fullName + 2).addProperty(
				VCARD.N,
				model.createResource().addProperty(VCARD.Given, givenName)
						.addProperty(VCARD.Family, familyName));

		// now write the model in XML form to a file
		// model.write(System.out);
		// model.commit();
		// model.add(johnSmith, VCARD.FN, "dfsdfs");
		// model.close();
		RDFEngine.closeModel(model);

	}

	public static void main(String[] args) throws Exception {

		int a = Integer.MAX_VALUE;
		Log.trace("a="+a);
		try {
			//Log.setCache(false, 0, 1000);
			// DataEngine.instance().deleteAll();
			// testJena();

			// DataEngine.instance().printAllModel();
			// DataEngine.instance().delete();
			// testPost();
			// testPost();testPost();testPost();testPost();testPost();testPost();testPost();testPost();testPost();testPost();testPost();testPost();testPost();testPost();testPost();testPost();testPost();testPost();testPost();
			// testPost();testPost();testPost();testPost();testPost();testPost();testPost();testPost();testPost();testPost();testPost();testPost();testPost();testPost();testPost();testPost();testPost();testPost();testPost();
			// testPost();
			// testGet();
			// DataEngine.instance().getAll();
			// DataEngine.instance().printAllModel();
			// DataEngine.instance().selectByRDQL("clipboard", "SELECT ?a WHERE
			// (<http://somewhere/JohnSmith>, <vcard:FN>, ?a)");
			/*
			 * DataObjectList l = DataEngine.instance().selectByRDQL(
			 * "clipboard", "SELECT ?x WHERE (<http://gnus/jackie_juju#23>,
			 * <http://www.w3.org/2001/vcard-rdf/3.0#title>, ?x)");
			 * Log.trace(l.toXML());
			 */
			// /for (int i = 0; i< l.size(); i++){
			// System.out.println(l.get(i).print());
			// }
		//	testPost();
		//	testGet();
		} catch (Exception e) {
			Log.error(e);
		}
	}

	public Statement getProperty(Model model, Resource res, String propertyName)
			throws Exception {
		return res.getProperty(model.createProperty(RDFEngine.instance()
				.getModelDesc(""), propertyName));
	}

	public StmtIterator getProperties(Model model, Resource res,
			String propertyName) throws Exception {
		return res.listProperties(model.createProperty(RDFEngine.instance()
				.getModelDesc(""), propertyName));
	}

	/**
	 * delete resource itself and all app reference it.
	 * 
	 * @param Id
	 * @param delRes
	 *            delete the resource from storage if true
	 * @throws Exception
	 */
	public void deleteResource(String Id, boolean delRes) throws Exception {
		Log.trace("enter deleteResource");
		ResourceObject ro = this.getResourceObjById(Id, true);

		Model model = RDFEngine.openModule(IDataEngine.MODEL_DEFAULT, false);
		if (model == null) {
			Log.error("can not open model " + IDataEngine.MODEL_DEFAULT);
			return;
		}
		Resource res = model.getResource(Id);
// StmtIterator it = getProperties(RDFEngine.instance().getNgusModel(),
// res, IDataEngine.ATTR_APPS);
		StmtIterator it = res.listProperties(RDFEngine.instance().getNgusModel().createProperty(
				RDFEngine.instance().getModelDesc(""), IDataEngine.ATTR_APPS));
		while (it.hasNext()) {
			Statement st = it.nextStatement();
			String app = st.getString();
			Log.trace("app name is " + app);
			// remove it from app model
			ro.addResDesObject(this.getResourceDesObj(Id, app));
			deleteResourceDes(ro, app);		
		}
		res.removeProperties();

		// delete from restype and tags
		removeResFromResTypeTable(Id);
		removeResFromTagsTable(Id);
		// remove resource from resource storage
		if (delRes) {
			removeResource(Id);
			this.deleteFromIndex("$$", ro);
			// this.deleteFromIndex("$update", ro);
		}
		// if (model instanceof ModelRDB) {
		// ((ModelRDB) model).getConnection().close();
		// }
		// model.close();
		RDFEngine.closeModel(model);
		Log.trace("leave deleteResource");
	}

	/**
	 * delete resource from resource strage directly.
	 * 
	 * @param id
	 * @throws Exception
	 */
	private void removeResource(String id) throws Exception {
		ResourceEngine.instance().delete(id);
	}

	
	// remove resource from one RDF model
	private void removeResFromModel(ResourceObject ro, String modelName)
			throws Exception {
		String id = ro.getResId();
		Log.trace("remove resouce description in model " + modelName
				+ ", id = " + id);
		Model model = RDFEngine.openModule(modelName, false);
		if (model == null) {
			Log.error("can not open model " + modelName);
			return;
		}

		Resource res = model.getResource(id);
		if (res == null) {
			Log.error("can not get resource " + id + "in model " + modelName);
			return;
		}
		// ResDesObject rdo = convertResourceToObj(res, modelName);
		// ro.addResDesObject(rdo);
		res.removeProperties();

		deleteFromIndex(modelName, ro);
		// if (model instanceof ModelRDB) {
		// ((ModelRDB) model).getConnection().close();
		// }
		// model.close();
		RDFEngine.closeModel(model);
	}

	private void removeResourceDesFromUserApp(String id, String app, String user)
			throws Exception {
		String appUserId = genUserAppId(user, app);
		Resource appUser_res = RDFEngine.instance().getUserAppModel()
				.getResource(appUserId);
		Statement stmt = appUser_res.getProperty(RDFEngine.instance()
				.getUserAppModel().createProperty(
						RDFEngine.instance().getModelDesc(""),
						IDataEngine.ATTR_USERAPP_RESID));
		if (stmt != null)
			stmt.remove();
	}

	/**
	 * delete resource des, remove the resource if no app reference the
	 * resource,
	 */
	public void deleteResourceDes(String Id, String modelName) throws Exception {
		modelName = modelName.toLowerCase();
		if (modelName.equalsIgnoreCase(IDataEngine.MODEL_DEFAULT))
			throw new Exception(
					"can not delete resource description in default model");

		// remove it
		ResourceObject ro = getResourceObjById(Id, true);
		deleteResourceDes(ro, modelName);
	}

	public void deleteResourceDes(ResourceObject ro, String modelName) throws Exception {
	
		if (modelName.equalsIgnoreCase(IDataEngine.MODEL_DEFAULT))
			throw new Exception(
					"can not delete resource description in default model");

		// remove it
		ro.addResDesObject(this.getResourceDesObj(ro.getResId(), modelName));
		removeResFromModel(ro, modelName); // remove propertise from model
		// delete record in ngus_resmodeluser
		deleteFromRMU(ro.getResId(), modelName);

		int count = 0;
		// remove it from resource app references
		Model ngusModel = RDFEngine.instance().getNgusModel();
		Resource ngus_res = ngusModel.getResource(ro.getResId());
		StmtIterator it = ngus_res.listProperties(ngusModel.createProperty(
				RDFEngine.instance().getModelDesc(""), IDataEngine.ATTR_APPS));
		while (it.hasNext()) {
			count++;
			Statement stmt = it.nextStatement();
			String app = stmt.getString();

			if (app.equalsIgnoreCase(modelName)) {
				stmt.remove();
				count--;
				// break;
			}
		}

		// remove it from user-app
		removeResourceDesFromUserApp(ro.getResId(), modelName, ro.getUser());

		// check if no app reference the resource, then delete it
		if (count == 0)
			removeResource(ro.getResId());
		
		updateDeletedTable(Long.parseLong(ro.getUser()), modelName, ro.getResId());
		updateTimeTable(Long.parseLong(ro.getUser()), modelName);
	}
	
	// only update the value of resource
	public void updateResourceObject(ResourceObject ro) throws Exception {
		ResourceEngine.instance().updateResource(ro, true);
		if (ro.getResourceType() != null)
			updateResTypeDBAsync(ro, true);

		Log.trace("update tags");
		if (ro.getTags() != null)
			this.updateTagsTable(ro, ro.getUser(), true);

		// get resource
		Model model = RDFEngine.openModule(IDataEngine.MODEL_DEFAULT, false);
		if (model == null) {
			Log.error("can not open model " + IDataEngine.MODEL_DEFAULT);
			return;
		}
		Resource res = model.getResource(ro.getResId());

		RDFEngine.instance().updateResDesUpdateTime(res,
				RDFEngine.instance().getNgusModel());

		// update cache
		Log.trace("put to cache...");
		CacheWriter.updateROInCache(ro.getResId());
		Log.trace("put to cache OK.");
		// if (model instanceof ModelRDB) {
		// ((ModelRDB) model).getConnection().close();
		// }
		// model.close();
		RDFEngine.closeModel(model);
	}

	// // update one model of resource
	// public void updateResourceDes(String Id, ResDesObject ob){
	//		
	// }

	// public ResourceObject getResourceByAttrs(ResDesObject res) throws
	// Exception{
	// String modelName = res.getModelName();
	// Model model = this.openModule(modelName, false);
	// if (model == null)
	// {
	// Log.error("can not get model " + modelName);
	// return null;
	// }
	//		
	// model.get
	// ResourceObject ret;
	// }
	public String getResourceProperty(String Id, String modelName,
			String propertyName) throws Exception {
		Model model = RDFEngine.openModule(modelName, false);
		if (model == null) {
			Log.error("can not open model " + modelName);
			return null;
		}
		Resource res = model.getResource(Id);
		if (res == null) {
			// model.close();
			RDFEngine.closeModel(model);
			throw new DEException("can not find resource with id " + Id
					+ " in model " + modelName);
		}

		Property p = res.getModel().createProperty(
				RDFEngine.instance().getModelDesc(""), propertyName);
		Statement st = res.getProperty(p);
		if (st == null) {
			// if (model instanceof ModelRDB) {
			// ((ModelRDB) model).getConnection().close();
			// }
			// model.close();
			RDFEngine.closeModel(model);
			throw new DEException("can not find property " + propertyName
					+ " of resource with id " + Id + " in model " + modelName);
		}
		String ret = st.getString();
		// if (model instanceof ModelRDB) {
		// ((ModelRDB) model).getConnection().close();
		// }
		// model.close();
		RDFEngine.closeModel(model);
		return ret;

	}

	public List listAllResOfUser(String username, String model)
			throws Exception {
		// DataObjectList ret = selectByRDQL(model, "SELECT ?a WHERE ?a,
		// <"+RDFEngine.instance().getModelDesc("")+":userId>, " + username +
		// ")");
		List ret = this.getResOfUserApp(username, model);
		return ret;
	}

	public ResourceObject getResourceTree(String resId, int depth)
			throws Exception {
		return ResourceEngine.instance().getResourceTree(resId, depth);

	}

	public void addResourceToTree(ResourceObject ob, String resId)
			throws Exception {
	}

	public void updateResourceShareLevel(String Id, int shareLevel,
			boolean recursive) throws Exception {
		ResourceObject old_ro = getResourceObjById(Id, true);

		ResourceEngine.instance().updateShareLevel(Id, shareLevel, recursive);
		// update cache
		Log.trace("put to cache...");
		CacheWriter.updateROInCache(Id);
		Log.trace("put to cache OK.");

		ResourceObject ro = this.getResourceObjById(Id, true);

		updateIndex("$$", ro, old_ro, DateTime.currentUTCTimeStamp());

	}

	/*
	 * public void updateResourceShareLevel(String Id, int shareLevel, boolean
	 * recursive) throws Exception { Model model =
	 * RDFEngine.openModule(IDataEngine.MODEL_DEFAULT, false); if (model ==
	 * null) { Log.error("can not open model " + IDataEngine.MODEL_DEFAULT);
	 * return; } Resource res = model.getResource(Id);
	 * 
	 * if (res == null) throw new Exception("Resource " + Id + " does not
	 * exist.");
	 * 
	 * Statement stmt = res.getProperty(RDFEngine.instance().getNgusModel()
	 * .createProperty(RDFEngine.instance().getModelDesc(""),
	 * IDataEngine.ATTR_SHARE_LEVEL)); if (stmt != null)
	 * stmt.changeObject(shareLevel); else
	 * res.addProperty(RDFEngine.instance().getNgusModel().createProperty(
	 * RDFEngine.instance().getModelDesc(""), IDataEngine.ATTR_SHARE_LEVEL),
	 * shareLevel); RDFEngine.instance().updateResDesUpdateTime(res,
	 * RDFEngine.instance().getNgusModel()); // if (model instanceof ModelRDB) { //
	 * ((ModelRDB) model).getConnection().close(); // } // model.close();
	 * RDFEngine.closeModel(model); // update cache Log.trace("put to
	 * cache..."); CacheWriter.updateROInCache(Id); Log.trace("put to cache
	 * OK."); }
	 */
	public List<ResourceObject> search(String indexName, String whereStatment,
			String sortBy, boolean bAsc, int start, int number, Attribute total)
			throws Exception {
		List<ResourceObject> ret = new ArrayList<ResourceObject>();
		List<String> list = IndexManager.instance().QueryIndex(indexName,
				whereStatment, sortBy, bAsc, start, number, total);
		for (int i = 0; i < list.size(); i++) {
			ret.add(this.getResourceObjById(list.get(i), false));
		}

		return ret;
	}

	public List<ResourceObject> search(String appName, String statement,
			int start, int number, Attribute total, String userId) throws Exception {
		appName = appName.toLowerCase();
		List<ResourceObject> ret = searchJCR(appName, statement, start, number,
				total, userId);
		/*
		 * String queryString = "SELECT ?x ?p ?v WHERE {?x ?p ?v FILTER (
		 * regex(?v, \"" + statement + "\", \"i\"))}"; Model model = null; //
		 * open model if (appName != null && appName.length() != 0) { try {
		 * model = RDFEngine.openModule(appName, true); } catch
		 * (com.hp.hpl.jena.shared.DoesNotExistException e) { model =
		 * ModelRDB.createModel(RDFEngine.instance().getDbc(), appName); }
		 * model.write(System.out); } // Need to set the source if the query
		 * does not. // This provided this override any query named source. //
		 * query.setSource(model); // QueryExecution qe = new
		 * QueryEngine(query);
		 * 
		 * QueryExecution qe = null; if (model == null)
		 * 
		 * qe = QueryExecutionFactory.create(queryString); else qe =
		 * QueryExecutionFactory.create(queryString, model);
		 * 
		 * ResultSet results = qe.execSelect(); while
		 * (results.hasNext()&&ret.size()<number) { QuerySolution qs =
		 * results.nextSolution(); // Return from get is null if not found
		 * Resource p = qs.getResource("x"); for(int i=0; i<ret.size(); i++){
		 * if(!p.getURI().equalsIgnoreCase(ret.get(i).getResId())){
		 * ret.add(DataEngine.getROById(p.getURI(), true)); } } //ret.add(rdo); }
		 * qe.close();
		 * 
		 * RDFEngine.closeModel(model);
		 */

		return ret;
	}
	/*
	public List<ResourceObject> listROByUserApp(String userid, String modelName) throws Exception{
		List<ResourceObject> ret = new ArrayList<ResourceObject>();
		String queryString = "SELECT ?x WHERE  {?x, \"user\", \"" + userid + "\"}";
	Log.trace("query string is " + queryString);
	List<ResDesObject> list = selectByRDQL(modelName, queryString);
	
	for (int i = 0; i < list.size(); i++) {
		String resid = list.get(i).getResId();
		boolean found = false;
		int j = 0;
		for (; j < ret.size(); j++) {
			if (ret.get(j).getResId().equalsIgnoreCase(resid)) {
				found = true;
				break;
			}
		}
		if (!found) // create new resource
		{
			ResourceObject ob = this.getResourceObjById(resid, false);
			ob.addResDesObject(list.get(i));
			ret.add(ob);
		} else // add res des to res obj
		{
			ret.get(j).addResDesObject(list.get(j));
		}
	}
	return ret;
	}
*/
	public List<ResourceObject> searchRDF(String modelName, String statement)
			throws Exception {
		modelName = modelName.toLowerCase();
		statement = statement.toLowerCase();
		// String queryString = "SELECT ?x ?p ?v WHERE {?x ?p ?v FILTER (
		// regex(?v, \"" + statement + "\", \"i\") || regex(?p, \"" + statement
		// + "\", \"i\"))}";
		String queryString = "SELECT ?x ?p ?v WHERE  {?x ?p ?v FILTER ( regex(?v, \""
				+ statement + "\", \"i\"))}";
		Log.trace("query string is " + queryString);
		List<ResDesObject> list = selectByRDQL(modelName, queryString);
		List<ResourceObject> ret = new ArrayList<ResourceObject>();
		for (int i = 0; i < list.size(); i++) {
			String resid = list.get(i).getResId();
			boolean found = false;
			int j = 0;
			for (; j < ret.size(); j++) {
				if (ret.get(j).getResId().equalsIgnoreCase(resid)) {
					found = true;
					break;
				}
			}
			if (!found) // create new resource
			{
				ResourceObject ob = this.getResourceObjById(resid, false);
				ob.addResDesObject(list.get(i));
				ret.add(ob);
			} else // add res des to res obj
			{
				ret.get(j).addResDesObject(list.get(j));
			}
		}
		return ret;

	}

	public List<ResourceObject> searchJCR(String modelName, String keys,
			int start, int number, Attribute total, String userId) throws Exception {
		modelName = modelName.toLowerCase();
		Log.trace("enter, start=" + start + ", number=" + number);

		ArrayList<ResourceObject> ret = new ArrayList<ResourceObject>();

		// String[] ls =
		// JCRContentRepository.instance().getWorkspace(JCRContentRepository.WORKSPACE_TREE).getQueryManager().getSupportedQueryLanguages();
		// for (int i = 0; i < ls.length; i++){
		// Log.trace("supported language: " + ls[i]);
		// }

		if (modelName == null)
			modelName = "";
		String queryString = "//" + modelName + "//*[";// jcr:like(@ngus:content,'%"+
		// statement + "%')]";
		StringTokenizer st = new StringTokenizer(keys, " ");
		String nt = st.nextToken().toLowerCase();
		queryString += "(jcr:like(fn:lower-case(@ngus:content), '%" + nt + "%')"
				+ " or jcr:like(fn:lower-case(@ngus:title), '%" + nt + "%')"
				+ " or jcr:like(fn:lower-case(@ngus:desc), '%" + nt + "%')"
				+ " or jcr:like(fn:lower-case(@ngus:tags), '%" + nt + "%')"
		;
		
		while (st.hasMoreTokens()) {
			nt = st.nextToken();
			queryString += " or jcr:like(fn:lower-case(@ngus:content), '%" + nt + "%')"
					+ " or jcr:like(fn:lower-case(@ngus:title), '%" + nt + "%')"
					+ " or jcr:like(fn:lower-case(@ngus:desc), '%" + nt + "%')"
					+ " or jcr:like(fn:lower-case(@ngus:tags), '%" + nt + "%')";

		}

		queryString += " ) ";

		queryString += " and @ngus:shareLevel < 1";
		
		if (userId != null && userId.length() > 0)
			queryString += " and @ngus:user = '"+ userId + "'";
		
		queryString += "]";
		
		// String queryString = "//*[@ngus:title=\"104\"]";
		Log.trace("query string = " + queryString);
		Session session = JCRContentRepository.instance().getSession(
				JCRContentRepository.WORKSPACE_TREE);
		javax.jcr.query.Query q = session.getWorkspace().getQueryManager()
				.createQuery(queryString, "xpath");
		QueryResult result = q.execute();
		NodeIterator it = result.getNodes();
		if (it == null || !it.hasNext())
			return ret;
		it.skip(start);
		int count = 0;
		while (it.hasNext() && number > 0) {
			count++;
			Node node = it.nextNode();
			/*
			 * if (start > 0) { start--; continue; }
			 */
			String name = node.getName();
			Log.trace("node name=" + name);
			String id = JCRContentRepository.getNodeId(node);
			ret.add(this.getResourceObjById(id, true));
			number--;
		}
		if (total != null)
			total.setValue((int) it.getSize());
		JCRContentRepository.instance().releaseSession(session,
				JCRContentRepository.WORKSPACE_TREE);
		// Log.trace("Found " + count + " records.");
		return ret;
	}

	public List<String> listResByTag(String tag,
			int start, int number, Attribute total, boolean and, String userId){
		if (start < 0 )
			start = 0;
		if (number < 0)
			number = Integer.MAX_VALUE;
		List<String> ret = new ArrayList<String>();
		try {
			StringTokenizer st = new StringTokenizer(tag, " ");		
			String condition = "";
			if (userId != null && userId.length()>0)
			{
				condition += "userid='" + userId + "' ";
				if (st.hasMoreTokens())
					condition += "and (";					
			}
			
				
			if (st.hasMoreTokens()) {
				condition += "tag = '" + st.nextToken() + "'";
			}
			String logicOp = "or";
			if (and) logicOp = "and";
			while (st.hasMoreTokens()) {
				condition += logicOp + " tag = '" + st.nextToken() + "'";
			}
			if (userId != null && userId.length()>0)
				condition += ")";		
			String sql = "select resId from ngus_tags where " + condition;
			Log.trace(sql);
			Connection con = com.ngus.dataengine.DBConnection.getConnection();
			java.sql.Statement statement = con.createStatement();
			java.sql.ResultSet rs = statement.executeQuery(sql);
			if (rs != null) {
				
				while(start>0){
					start --;
					rs.next();
				}
				while (rs.next()) {				
					ret.add(rs.getString(1));
					number --;
					if (number <= 0)
						break;
				}
				rs.last();
				if (total != null) {
					total.setValue(rs.getRow());
				}
			}
		} catch (Exception e) {
			Log.error(e);
		}
		return ret;
	}
		
	
	public List<ResourceObject> searchByTag(String tag,
			int start, int number, Attribute total, boolean and, String userId){		
		List<ResourceObject> ret = new ArrayList<ResourceObject>();
		List<String> list = listResByTag(tag, start, number, total, and, userId);
		for (int i = 0; i< list.size(); i++){
			try{
			ResourceObject ro = this.getResourceObjById(list.get(i), true);
			ret.add(ro);
			}catch(Exception e){
				Log.error(e);
			}
			
		}	
		return ret;
	}
	// public AbstractAppObject getAppObject(String appName, String resId,
	// boolean getValue) throws Exception{
	// ResourceObject ro = this.getResourceObjById(resId, getValue);
	// AbstractAppObject
	//		
	// }

	/**
	 * singleton logic
	 */
	static private DataEngine _instance = null;

	/**
	 * Get instance of DataMgr
	 * 
	 * @return the single instance of DataMgr, using the default config file
	 *         "ACCConfig.xml"
	 * @throws Exception
	 */
	static public DataEngine instance() throws Exception {
		if (_instance == null) {

			_instance = new DataEngine();

			// _instance.init();

		}

		return _instance;
	}

	/**
	 * destroy
	 * 
	 * @throws Exception
	 */
	static public void destroy() throws Throwable {
		// ngusModel.close();

		if (_instance != null) {
			_instance = null;
		}
	}

	public List<ResourceObject> getAppTrees(String app, int depth, String userId)
			throws Exception {
		return getJCRNodesUnderPath(app + "/" + userId, depth);
	}

	public List<ResourceObject> getJCRNodesUnderPath(String jcrPath, int depth)
			throws Exception {
		Log.trace("enter");
		List<ResourceObject> ret = new ArrayList<ResourceObject>();
		Session session = JCRContentRepository.instance().getSession(
				JCRContentRepository.WORKSPACE_TREE);
		Log.trace("session=" + session);
		Node root = session.getRootNode();
		// reach the target node
		if (jcrPath.startsWith("/")) {// skip "/"
			jcrPath = jcrPath.substring(1);
		}
		Log.trace("jcr path=" + jcrPath);
		Node node = root.getNode(jcrPath);
		NodeIterator ni = node.getNodes();
		while (ni.hasNext()) {
			Node n = ni.nextNode();
			try {
				ResourceObject ro = ResourceEngine.instance().loadResource(n,
						depth);
				ret.add(ro);
			} catch (Exception e) {
				String t = node.getName();
				String k = node.getPath();
				Log.warning("can not load resource form node" + node.getName(),
						e);
				continue;
			}
		}

		JCRContentRepository.instance().releaseSession(session,
				JCRContentRepository.WORKSPACE_TREE);
		Log.trace("leave");
		return ret;
	}

	/**
	 * if userId == null or "", will return resource of or users
	 * 
	 * @param type
	 * @param userId
	 * @return
	 */
	public List<ResourceObject> getROByType(String type, String userId,
			int start, int number) {
		List<ResourceObject> ret = new ArrayList<ResourceObject>(1);

		Log.trace("enter");
		java.sql.Statement st = null;
		java.sql.Connection c = null;
		String sql = null;
		start = start > 0 ? start : 0;
		number = number > 0 ? number : 0;
		// if (userId == null || userId.length() == 0)
		sql = "select resid from ngus_resbytype where usertype='" + type
				+ "' and username='" + userId + "' limit " + start + ", "
				+ number;
		// else
		// sql = "select id from ngus_resbytype where type='" + type
		// + "' and username='" + userId + "' limit "+start+", "+number;
		Log.trace(sql);
		try {
			c = com.ngus.dataengine.DBConnection.getConnection();
			st = c.createStatement();
			java.sql.ResultSet rs = st.executeQuery(sql);
			Log.trace("result set = " + rs);
			if (rs != null) {
				while (rs.next()) {
					String id = rs.getString(1);
					Log.trace("id=" + id);
					ResourceObject ob = getResourceObjById(id, true);

					ret.add(ob);
					Log.trace("add ob");
				}
			}
		} catch (Exception e) {
			Log.error(e);
		} finally {
			if (st != null) {
				try {
					st.close();
				} catch (Exception e) {
					Log.error(e);
				}

				st = null;
			}

			try {
				if (c != null)
					c.close();
			} catch (Exception e) {
				Log.error(e);
			}
		}
		Log.trace("leave");
		return ret;

	}

	/**
	 * get resource id by index id
	 * 
	 * @param id
	 * @return
	 */
	public String getResIdByIndexId(int id) {
		java.sql.Statement st = null;
		java.sql.Connection c = null;
		String sql = null;
		String resid = "";
		sql = "select resid from resourceid where id=" + id;

		try {
			c = com.ngus.dataengine.DBConnection.getConnection();
			st = c.createStatement();
			java.sql.ResultSet rs = st.executeQuery(sql);
			if (rs != null) {
				while (rs.next()) {
					resid = rs.getString(1);
				}
			}
		} catch (Exception e) {
			Log.error(e);
		} finally {
			if (st != null) {
				try {
					st.close();
				} catch (SQLException e) {
					Log.error(e);
				}

				st = null;
			}

			try {
				if (c != null)
					c.close();
			} catch (SQLException e) {
				Log.error(e);
			}
		}

		return resid;

	}

	private void updateResTypeTable(ResourceObject ob, boolean update)
			throws Exception {
		Log.trace("enter");
		List<String> types = ob.getResourceType();
		if (types == null || types.size() == 0)
			return;

		java.sql.Statement st = null;
		java.sql.Connection c = null;
		try {
			c = com.ngus.dataengine.DBConnection.getConnection();
			if (update) { // delete all first
				String sql = "delete from ngus_resbytype where resid='"
						+ ob.getResId() + "'";
				Log.trace(sql);
				try {
					st = c.createStatement();
					int r = st.executeUpdate(sql);
					Log.trace("execute '" + sql + "'" + " return " + r);

					// Log.trace("update "+ i + " records");
					if (r < 1) {
						// throw new Exception("update index " + index.getName()
						// + "
						// failed");
						Log.error("delete failed, sql=" + sql);
						;
					}
				} catch (Exception e) {
					Log.error(e);
					;
				} finally {
					// Always make sure result sets and statements are closed,
					// and the connection is returned to the pool
					// if (rs != null) {
					// try { rs.close(); } catch (SQLException e) { ; }
					// rs = null;
					// }
					if (st != null) {
						try {
							st.close();
						} catch (Exception e) {
							Log.error(e);
						}

						st = null;
					}

				}

			}
			// insert records
			for (int i = 0; i < types.size(); i++) {
				String type = types.get(i);
				if (type == null || type.length() == 0)
					continue;
				String sql = null;
				sql = "insert into ngus_resbytype values('" + ob.getResId()
						+ "', '" + ob.getUser() + "', '" + type
						+ "', null, -1)";
				Log.trace(sql);
				try {
					st = c.createStatement();
					int r = st.executeUpdate(sql);
					Log.trace("execute '" + sql + "'" + " return " + r);

					// Log.trace("update "+ i + " records");
					if (r < 1) {
						// throw new Exception("update index " + index.getName()
						// + "
						// failed");
						Log.error("insert failed, sql=" + sql);
						continue;
					}
				} catch (Exception e) {
					Log.error(e);
					continue;
				} finally {
					// Always make sure result sets and statements are closed,
					// and the connection is returned to the pool
					// if (rs != null) {
					// try { rs.close(); } catch (SQLException e) { ; }
					// rs = null;
					// }
					if (st != null) {
						try {
							st.close();
						} catch (Exception e) {
							Log.error(e);
						}

						st = null;
					}

				}
				Log.trace("insert for type " + type + "ok");
			}
		} finally {
			try {
				if (c != null)
					c.close();
			} catch (SQLException e) {
				Log.error(e);
			}
		}

	}

	private void removeResFromResTypeTable(ResourceObject ob) throws Exception {
		Log.trace("enter");
		List<String> types = ob.getResourceType();
		if (types == null || types.size() == 0)
			return;

		removeResFromResTypeTable(ob.getResId());
	}

	private void removeResFromResTypeTable(String id) throws Exception {
		Log.trace("enter");

		java.sql.Statement st = null;
		java.sql.Connection c = null;
		try {
			c = com.ngus.dataengine.DBConnection.getConnection();
			// delete all
			String sql = "delete from ngus_resbytype where resid='" + id + "'";
			Log.trace(sql);
			try {
				st = c.createStatement();
				int r = st.executeUpdate(sql);
				Log.trace("execute '" + sql + "'" + " return " + r);

				// Log.trace("update "+ i + " records");
				if (r < 1) {
					// throw new Exception("update index " + index.getName()
					// + "
					// failed");
					Log.error("delete failed, sql=" + sql);
					;
				}
			} catch (Exception e) {
				Log.error(e);
				;
			} finally {
				// Always make sure result sets and statements are closed,
				// and the connection is returned to the pool
				// if (rs != null) {
				// try { rs.close(); } catch (SQLException e) { ; }
				// rs = null;
				// }
				if (st != null) {
					try {
						st.close();
					} catch (Exception e) {
						Log.error(e);
					}

					st = null;
				}

			}

		} finally {
			try {
				if (c != null)
					c.close();
			} catch (SQLException e) {
				Log.error(e);
			}
		}

	}

	private void updateTagsTable(ResourceObject ob, String userId,
			boolean update) throws Exception {
		Log.trace("enter");
		List<String> tags = ob.getTags();
		if (tags == null)
			return;
		Log.trace("tags="+tags.toString());

		java.sql.Statement st = null;
		java.sql.Connection c = null;
		try {
			c = com.ngus.dataengine.DBConnection.getConnection();

			if (update) { // delete all first
				String sql = "delete from ngus_tags where resid='"
						+ ob.getResId() + "'";
				Log.trace(sql);
				try {
					st = c.createStatement();
					int r = st.executeUpdate(sql);
					Log.trace("execute '" + sql + "'" + " return " + r);

					// Log.trace("update "+ i + " records");
					if (r < 1) {
						// throw new Exception("update index " + index.getName()
						// + "
						// failed");
						Log.error("delete failed, sql=" + sql);
						;
					}
				} catch (Exception e) {
					Log.error(e);
					;
				} finally {
					// Always make sure result sets and statements are closed,
					// and the connection is returned to the pool
					// if (rs != null) {
					// try { rs.close(); } catch (SQLException e) { ; }
					// rs = null;
					// }
					if (st != null) {
						try {
							st.close();
						} catch (Exception e) {
							Log.error(e);
						}

						st = null;
					}

				}

			}
			// insert all tags
			for (int i = 0; i < tags.size(); i++) {
				String tag = tags.get(i);
				if (tag == null || tag.length() == 0)
					continue;
				String sql = null;
				sql = "insert into ngus_tags values('" + ob.getResId() + "', '"
						+ tag + "', '" + userId + "')";
				Log.trace(sql);
				try {
					st = c.createStatement();
					int r = st.executeUpdate(sql);
					Log.trace("execute '" + sql + "'" + " return " + r);

					// Log.trace("update "+ i + " records");
					if (r < 1) {
						// throw new Exception("update index " + index.getName()
						// + "
						// failed");
						Log.error("insert failed, sql=" + sql);
						continue;
					}
				} catch (Exception e) {
					Log.error(e);
					continue;
				} finally {
					// Always make sure result sets and statements are closed,
					// and the connection is returned to the pool
					// if (rs != null) {
					// try { rs.close(); } catch (SQLException e) { ; }
					// rs = null;
					// }
					if (st != null) {
						try {
							st.close();
						} catch (Exception e) {
							Log.error(e);
						}

						st = null;
					}

				}
				Log.trace("insert for tag " + tag + "ok");
			}
		} finally {
			try {
				if (c != null)
					c.close();
			} catch (SQLException e) {
				Log.error(e);
			}
		}

	}

	private void removeResFromTagsTable(ResourceObject ob) throws Exception {
		Log.trace("enter");
		List<String> tags = ob.getTags();
		if (tags == null || tags.size() == 0)
			return;

		removeResFromTagsTable(ob.getResId());

	}


	private void removeResFromTagsTable(String id) throws Exception {
		Log.trace("enter");

		java.sql.Statement st = null;
		java.sql.Connection c = null;
		try {
			c = com.ngus.dataengine.DBConnection.getConnection();
			// delete all first
			String sql = "delete from ngus_tags where resid='" + id + "'";
			Log.trace(sql);
			try {
				st = c.createStatement();
				int r = st.executeUpdate(sql);
				Log.trace("execute '" + sql + "'" + " return " + r);

				// Log.trace("update "+ i + " records");
				if (r < 1) {
					// throw new Exception("update index " + index.getName()
					// + "
					// failed");
					Log.error("delete failed, sql=" + sql);
					;
				}
			} catch (Exception e) {
				Log.error(e);
				;
			} finally {
				// Always make sure result sets and statements are closed,
				// and the connection is returned to the pool
				// if (rs != null) {
				// try { rs.close(); } catch (SQLException e) { ; }
				// rs = null;
				// }
				if (st != null) {
					try {
						st.close();
					} catch (Exception e) {
						Log.error(e);
					}

					st = null;
				}

			}

		} finally {
			try {
				if (c != null)
					c.close();
			} catch (SQLException e) {
				Log.error(e);
			}
		}

	}
	
	
	private void updateDeletedTable(long user, String app, String resid) throws Exception {
		Log.trace("enter");

		java.sql.Statement st = null;
		java.sql.Connection c = null;
		try {
			c = com.ngus.dataengine.DBConnection.getConnection();
			// delete all first
			long time = DateTime.currentUTCTimeStamp().getTime();
			String sql = "insert into ngus_deleted values(" + user + ", '" + app + "', '" + resid + "', " + time + ")";			
			Log.trace(sql);
			try {
				st = c.createStatement();
				int r = st.executeUpdate(sql);
				Log.trace("execute '" + sql + "'" + " return " + r);

				// Log.trace("update "+ i + " records");
				if (r < 1) {
					// throw new Exception("update index " + index.getName()
					// + "
					// failed");
					Log.error("insert failed, sql=" + sql);
					;
				}
			} catch (Exception e) {
				Log.error(e);
				;
			} finally {
				// Always make sure result sets and statements are closed,
				// and the connection is returned to the pool
				// if (rs != null) {
				// try { rs.close(); } catch (SQLException e) { ; }
				// rs = null;
				// }
				if (st != null) {
					try {
						st.close();
					} catch (Exception e) {
						Log.error(e);
					}

					st = null;
				}

			}

		} finally {
			try {
				if (c != null)
					c.close();
			} catch (SQLException e) {
				Log.error(e);
			}
		}

	}
	// ///////////////////////////////////// // cached operation
	// /////////////////////////////////////

}