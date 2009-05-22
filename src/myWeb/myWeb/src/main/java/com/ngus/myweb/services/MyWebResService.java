package com.ngus.myweb.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.StringTokenizer;
import java.util.TreeMap;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Property;
import javax.jcr.Session;
import javax.jcr.Workspace;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;

import com.ngus.comment.CommentEngine;
import com.ngus.dataengine.DataEngine;
import com.ngus.dataengine.ResDesObject;
import com.ngus.dataengine.ResourceObject;
import com.ngus.myweb.Constant;
import com.ngus.myweb.dataobject.BookMark;
import com.ngus.myweb.dataobject.Folder;
import com.ngus.myweb.dataobject.MyWebRes;
import com.ngus.myweb.dataobject.ObjectTree;
import com.ngus.myweb.util.DbUtils;
import com.ngus.myweb.webservices.dataobject.MyWebResObject;
import com.ngus.resengine.JCRContentRepository;
import com.ngus.um.IUser;
import com.ngus.um.UMSession;
import com.ngus.um.User;
import com.ngus.um.http.SessionManager;
import com.ngus.um.http.UserManager;
import com.ngus.um.http.WebServiceSession;
import com.ns.dataobject.Attribute;
import com.ns.dataobject.DataObjectList;
import com.ns.log.Log;
import com.ns.util.DateTime;

/**
 * 
 * @author Administrator
 * 
 */
public class MyWebResService implements IMyWebResService {
	private static MyWebResService instance;
	
	static{
		if (instance == null) {
			instance = new MyWebResService();
		}		
	}
	/**
	 * QUERY SQL
	 */
	private final String LIST_MOST_RECENT_RESOURCE = "SELECT * FROM myweb_res where url != '' and sharelevel < 1 order by createTime desc limit ?";

	private final String LIST_MOST_POPULAR_RESOURCE = "SELECT url from myweb_res where url != '' and sharelevel < 1 group by url order by count(*) desc limit 0, ?";

	private final String LIST_MOST_POPULAR_RESOURCE_BY_TYPE		= "SELECT url, count(*) from myweb_res, ngus_resbytype where myweb_res.url != '' and myweb_res.sharelevel < 1 and ngus_resbytype.realtype = ? and ngus_resbytype.checked >? and myweb_res.resid = ngus_resbytype.resid group by url order by count(*) desc";
	private final String LIST_MOST_POPULAR_RESOURCE_BY_TYPE2	= "SELECT url, count(*) from myweb_res, ngus_resbytype where myweb_res.url != '' and myweb_res.sharelevel < 1 and ngus_resbytype.realtype = ? and ngus_resbytype.checked >2 and myweb_res.resid = ngus_resbytype.resid group by url order by count(*) desc";
	
	private final String GET_RESOURCE_COUNT_BY_TYPE = "SELECT count(*) from myweb_res, ngus_resbytype where myweb_res.url != '' and myweb_res.sharelevel < 1 and ngus_resbytype.realtype = ? and ngus_resbytype.checked >1 and myweb_res.resid = ngus_resbytype.resid group by url order by count(*)";
	
	private final String LIST_PAGE_RESOURCE_BY_TYPE = "SELECT url, count(*) from myweb_res, ngus_resbytype where myweb_res.url != '' and myweb_res.sharelevel < 1 and ngus_resbytype.realtype = ? and ngus_resbytype.checked >1 and myweb_res.resid = ngus_resbytype.resid group by url order by count(*) desc limit ? , ?";
	
	private final String LIST_TOTAL_RESOURCE_BY_TYPE = "SELECT url, count(*) from myweb_res, ngus_resbytype where myweb_res.url != '' and myweb_res.sharelevel < 1 and ngus_resbytype.realtype = ? and ngus_resbytype.checked >1 and myweb_res.resid = ngus_resbytype.resid group by url order by count(*) desc";
	
	private final String GET_RES_BY_URL = "SELECT resid from myweb_res where url = ? and sharelevel < 1";

	private final String LIST_ALL_TAG = "select distinct tag from ngus_tags";

	private final String LIST_USER_TAG = "select distinct tag from ngus_tags where userid = ?";

	private final String List_BY_TYPE_TAG_USER = "select myweb_url.url , ngus_resbytype.checked from myweb_url, ngus_resbytype ,ngus_tags where ngus_resbytype.resid=myweb_url.resid and ngus_resbytype.resid=ngus_tags.resid and (ngus_resbytype.usertype = ? or ngus_resbytype.realtype=? ) and ngus_resbytype.checked >= 2 and ngus_tags.tag = ? and ngus_tags.userid = ? group by ngus_tags.resid order by myweb_url.resid DESC limit ?, ?";
	
	private final String List_ALL_TYPE_TAG_USER = "select myweb_url.url , ngus_resbytype.checked from myweb_url, ngus_resbytype ,ngus_tags where ngus_resbytype.resid=myweb_url.resid and ngus_resbytype.resid=ngus_tags.resid and (ngus_resbytype.usertype = ? or ngus_resbytype.realtype=? )  and ngus_resbytype.checked >= 2 and ngus_tags.tag = ? and ngus_tags.userid = ? group by ngus_tags.resid order by myweb_url.resid DESC";
	
	private final String List_BY_TYPE_USER =	"select myweb_url.url , ngus_resbytype.checked from myweb_url, ngus_resbytype where ngus_resbytype.resid=myweb_url.resid and (ngus_resbytype.usertype = ? or ngus_resbytype.realtype=? ) and ngus_resbytype.checked >= 2 and ngus_resbytype.username = ? order by myweb_url.resid DESC limit ?, ?";
	
	private final String List_ALL_TYPE_USER =	"select myweb_url.url , ngus_resbytype.checked from myweb_url, ngus_resbytype where ngus_resbytype.resid=myweb_url.resid and (ngus_resbytype.usertype = ? or ngus_resbytype.realtype=? ) and ngus_resbytype.checked >= 2 and ngus_resbytype.username = ? order by myweb_url.resid DESC ";
	
	private final String LIST_DELETE_RES = "select resId from deleted where userid = ? and deletetime > ?";
	
	/**
	 * private Constructor
	 */
	protected MyWebResService() {
	}

	/**
	 * Singleton Pattern, This class has only one instance
	 * 
	 * @return the single instance of this class
	 */
	public static synchronized MyWebResService instance() {
		return instance;
	}

	/**
	 * FullTextSearch, temporarily this method searches the jcr(url) only
	 * 
	 * @param key
	 *            key to be searched
	 * @param start
	 *            start rows
	 * @param number
	 *            the number of the resul
	 * @param count
	 *            total result
	 * @return search result
	 */
	public List search(String key, int start, int number, Attribute count, String userId) {
		List<MyWebRes> result = new ArrayList<MyWebRes>();
		try {
			// invoke the method in the DataEngine
			List<ResourceObject> tmp = DataEngine.instance().search(
					Constant.APP_NAME, key, start, number, count, userId);

			// convert the result to Object
			for (int i = 0; i < tmp.size(); i++) {
				try{
					MyWebRes res = this.getInstanceByID(tmp.get(i).getResId());
					if(res instanceof BookMark)
					result.add(res);
				}
				catch(Exception e){
					Log.error(e);
				}
			}
		} catch (Exception e) {
			Log.error(e);
		}
		return result;
	}

	/**
	 * Copy from an
	 * 
	 * @param from
	 * @param to
	 * @return success or not
	 */
	/*public boolean copy(String from, String to) {
		try {
			MyWebRes res = this.getInstanceByID(to);
			String path = res.getRO().getStoragePath();
			if (res instanceof BookMark) {
				to = res.getParentID();
			}
			ObjectTree ot = this.getTree(from, -1);
			ot.getNode().setParentID(to);
			ot.getNode().setLogicPath(
					path.substring(ot.getNode().getBaseStoragePath().length())
							+ "/" + res.getRO().getIndexId());
			postTree(ot);
			return true;
		} catch (Exception e) {
			Log.error(e);
		}
		return false;
	}*/

	/**
	 * Make a tree persistent
	 * 
	 * @param ot
	 *            the tree to be persistented
	 * @return true if success
	 */
	public boolean postTree(ObjectTree ot, String sessionID) {
		try {
			// TODO low efficiency
			String id = ot.getNode().getID();
			ot.getNode().setID(null);
			// System.err.println(ot.getNode().getParentID());
			String test = this.makePersistent(ot.getNode(), sessionID);
			this.getInstanceByID(test).setID(id);

			String path = ot.getNode().getRO().getStoragePath();
			// for all child
			for (int i = 0; i < ot.getChildren().size(); i++) {
				((ObjectTree) ot.getChildren().get(i)).getNode().setParentID(
						ot.getNode().getID());
				((ObjectTree) ot.getChildren().get(i)).getNode().setLogicPath(
						path.substring(ot.getNode().getBaseStoragePath()
								.length())
								+ "/" + ot.getNode().getRO().getIndexId());
				postTree((ObjectTree) ot.getChildren().get(i), sessionID);
			}
		} catch (Exception e) {
			Log.error(e);
			return false;
		}
		return true;
	}

	/**
	 * 
	 */
	/*public boolean move(String from, String to, String sessionID) {
		boolean success = false;
		try {
			MyWebRes res = this.getInstanceByID(to);
			String path = res.getRO().getStoragePath();
			if (res instanceof BookMark) {
				to = res.getParentID();
			}
			ObjectTree ot = this.getTree(from, -1);
			ot.getNode().setParentID(to);
			ot.getNode().setLogicPath(
					path.substring(ot.getNode().getBaseStoragePath().length())
							+ "/" + res.getRO().getIndexId());
			postTree(ot);
			this.deleteByID(from);
			success = true;
		} catch (Exception e) {
			Log.error(e);
		}
		return success;
	}*/

	/**
	 * 
	 */
	public MyWebRes getInstanceByID(String id) throws Exception {
		MyWebRes res;
		
		ResourceObject ro = DataEngine.instance().getResourceObjById(id, true);
		
		ResDesObject rdo = DataEngine.instance().getResourceDesObj(id,
				Constant.APP_NAME);
		if (rdo == null){
			Log.error("get rdo " + Constant.APP_NAME + " of ro " + id + " failed");
			return null;
		}
		
		Log.trace("rdo="+rdo.printXML());
		// ResDesObject rdo =
		// (ResDesObject)ro.getResDesObjects(Constant.APP_NAME.toLowerCase()).get(0);
		ro.addResDesObject(rdo);
		if (rdo.getAttr(MyWebRes.TYPE_ATTR).getStringValue().equalsIgnoreCase(
				MyWebRes.FOLDER_TYPE)) {
			
			res = new Folder(ro);
			
		} else {
			
			res = new BookMark(ro);
			
		
		}
		return res;
	}

	/**
	 * 
	 */
	public boolean deleteByID(String id) {
		try {
			if (id == null) {
				return false;
			}
			DataEngine.instance().deleteResource(id, true);
			return true;
		} catch (Exception e) {
			Log.error(e);
		}
		return false;
	}

	public String makePersistent(MyWebRes res) {
		return makePersistent(res, UserManager.getCurrentUser().getSUserId());
	}
	
	public String makePersistent(MyWebRes res, String userID) {
		String ret = null;
		try {
			res.getRO().setUser(userID);
			ret = DataEngine.instance().post(res);
			res.setID(ret);
		} catch (Exception e) {
			Log.error(e);
		}
		return ret;
	}
	public boolean update(MyWebRes res){
		return update(res, UserManager.getCurrentUser().getSUserId());
	}
	public boolean update(MyWebRes res, String userID) {
		try {
			res.getRO().setUser(userID);
			DataEngine.instance().post(res);
			return true;
		} catch (Exception e) {
			Log.error(e);
		}
		return false;
	}

	public List<ObjectTree> listAll(int depth) {
		return listAll(depth, UserManager.getCurrentUser().getSUserId());
	}
	
	public List<ObjectTree> listAll(int depth, String sUserId) {
		ArrayList<ObjectTree> al = new ArrayList<ObjectTree>();
		try {
			List<ResourceObject> rol = DataEngine.instance().getAppTrees(
					Constant.APP_NAME, depth,
					sUserId);
			for (int i = 0; i < rol.size(); i++) {
				try {
					ObjectTree tmp = convert(rol.get(i));
					if (tmp != null) {
						al.add(tmp);
					}
				} catch (Exception e) {
					Log.error(e);
				}
			}
		} catch (Exception e) {
			Log.error(e);
		}
		return al;
	}
	
	public List<MyWebRes> getByType(String type, int start, int number) {
		return getByType(type, start, number, UserManager.getCurrentUser().getSUserId());
	}
	
	
	
	public List<MyWebRes> getByType(String type, int start, int number, String userID) {
		ArrayList<MyWebRes> al = new ArrayList<MyWebRes>();
		try {
			List<ResourceObject> rol = DataEngine.instance().getROByType(type,
					userID, start, number);
			for (int i = 0; i < rol.size(); i++) {
				MyWebRes res;
				ResDesObject rdo = DataEngine.instance().getResourceDesObj(
						rol.get(i).getResId(), Constant.APP_NAME);
				rol.get(i).addResDesObject(rdo);
				if (rdo.getAttr(MyWebRes.TYPE_ATTR).getStringValue()
						.equalsIgnoreCase(MyWebRes.FOLDER_TYPE)) {
					res = new Folder(rol.get(i));
				} else {
					res = new BookMark(rol.get(i));
				}
				al.add(res);
			}
		} catch (Exception e) {
			Log.log(e.toString());
		}
		return al;
	}
	/**
	 * 
	 */
	public ObjectTree getTree(String id, int depth) {
		ObjectTree ot = null;
		try {
			// MyWebRes res;
			ResourceObject ro = DataEngine.instance()
					.getResourceTree(id, depth);
			ot = convert(ro);
		} catch (Exception e) {
			Log.error(e);
		}
		return ot;
	}

	private ObjectTree convert(ResourceObject ro) throws Exception {
		ObjectTree obt = null;
		MyWebRes res;
		ResDesObject rdo = DataEngine.instance().getResourceDesObj(
				ro.getResId(), Constant.APP_NAME);
		// added by jackie
		if (rdo == null) {
			Log.error("cannot get rdo , id=" + ro.getResId() + ", app="
					+ Constant.APP_NAME);
			return null;
		}
		// ===
		// long end = System.nanoTime();
		// System.err.println("Wrapper: "+(end-start)/1000000);
		ro.addResDesObject(rdo);
		Attribute attr = rdo.getAttr(MyWebRes.TYPE_ATTR);
		// added by jackie
		if (attr == null) {
			Log.error("cannot get attribute " + MyWebRes.TYPE_ATTR
					+ " from rdo , id=" + ro.getResId() + ", app="
					+ Constant.APP_NAME);
			return null;
		}
		// ===
		if (attr.getStringValue().equalsIgnoreCase(MyWebRes.FOLDER_TYPE)) {
			res = new Folder(ro);
		} else {
			res = new BookMark(ro);
		}
		obt = new ObjectTree(res);
		DataObjectList children = res.getChildren();
		if (children != null) {
			for (int i = 0; i < children.size(); i++) {
				ObjectTree t = convert((ResourceObject) children.get(i));
				if (t != null) {
					obt.insertChild(t);
				}
			}
		}
		return obt;
	}

	public List listByTag(String tag, int start, int number, Attribute count, String userId) {
		List<MyWebRes> result = new ArrayList<MyWebRes>();
//		try {
//			StringTokenizer st = new StringTokenizer(tag, " ");
//			String condition = "";
//			if (st.hasMoreTokens()) {
//				condition += "tag = '" + st.nextToken() + "'";
//			}
//			while (st.hasMoreTokens()) {
//				condition += "or tag = '" + st.nextToken() + "'";
//			}
//			String sql = "select resId from ngus_tags where " + condition;
//			Connection con = com.ns.db.DBC.getDataSource(
//					"java:comp/env/jdbc/ngus").getConnection();
//			Statement statement = con.createStatement();
//			ResultSet rs = statement.executeQuery(sql);
//			if (rs != null) {
//				while (rs.next()) {
//					try {
//						MyWebRes res = this.getInstanceByID(rs.getString(1));
//						result.add(res);
//					} catch (Exception e) {
//						Log.error(e);
//					}
//				}
//				rs.last();
//				if (count != null) {
//					count.setValue(rs.getRow());
//				}
//			}
//		} catch (Exception e) {
//			Log.error(e);
//		}
		List<String> list = null;
		try{
		list = DataEngine.instance().listResByTag(tag, start, number, count, false, userId);
		}catch (Exception e){
			Log.error(e);
			return result;
		}
		
		for (int i = 0; i< list.size(); i++){
			try{
			MyWebRes res = this.getInstanceByID(list.get(i));
			result.add(res);
			}catch(Exception e){
				Log.error(e);
				continue;
			}
		}	
		return result;
	}

	public List<String> mostPopularTag(int count) {
		ArrayList<String> al = new ArrayList<String>();
		Connection con = null;
		ResultSet rs = null;
		try {
			con = com.ns.db.DBC.getDataSource("java:comp/env/jdbc/ngus")
					.getConnection();
			Statement st = con.createStatement();
			String sql = "select tag from ngus_tags group by tag order by count(*) desc limit "+count;
			rs = st.executeQuery(sql);			
			if(rs!=null){
				while(rs.next())
				{
					al.add(rs.getString(1));
				}
			}
		} catch (Exception e) {
			Log.error(e);
		} finally {
			DbUtils.closeResultSet(rs);
			DbUtils.closeConnection(con);
		}
		return al;
	}

	/**
	 * List all tags of a user
	 * 
	 * @param username
	 *            username
	 * @return	all tags of a user
	 */
	public List<String> listUserTag(String username) {
		ArrayList<String> al = new ArrayList<String>();
		Connection con = null;
		ResultSet rs = null;
		try {
			con = com.ns.db.DBC.getDataSource("java:comp/env/jdbc/ngus")
					.getConnection();
			PreparedStatement st = con.prepareStatement(this.LIST_USER_TAG);
			st.setString(1, username);
			rs = st.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					al.add(rs.getString(1));
				}
			}
		} catch (Exception e) {
			Log.error(e);
		} finally {
			DbUtils.closeResultSet(rs);
			DbUtils.closeConnection(con);
		}
		return al;
	}

	/**
	 * 
	 * @return
	 */
	public List<String> listAllTag() {
		ArrayList<String> al = new ArrayList<String>();
		Connection con = null;
		ResultSet rs = null;
		try {
			con = com.ns.db.DBC.getDataSource("java:comp/env/jdbc/ngus")
					.getConnection();
			PreparedStatement st = con.prepareStatement(this.LIST_ALL_TAG);
			rs = st.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					al.add(rs.getString(1));
				}
			}
		} catch (Exception e) {
			Log.error(e);
		} finally {
			DbUtils.closeResultSet(rs);
			DbUtils.closeConnection(con);
		}
		return al;
	}

	/**
	 * 
	 * @param number
	 * @return
	 */
	public List<MyWebRes> listMostPopularRes(int number) {
		ArrayList<MyWebRes> al = new ArrayList<MyWebRes>();
		Connection con = null;
		ResultSet rs = null;
		try {
			con = com.ns.db.DBC.getConnection("java:comp/env/jdbc/ngus");
			PreparedStatement st = con
					.prepareStatement(this.LIST_MOST_POPULAR_RESOURCE);
			st.setInt(1, number);
			rs = st.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					String url = rs.getString(1);
					MyWebRes res = this.getResByURL(url);
					if(res!=null){
						al.add(res);
					}
				}
			}
		} catch (Exception e) {
			Log.error(e);
		} finally {
			DbUtils.closeResultSet(rs);
			DbUtils.closeConnection(con);
		}
		return al;
	}
	
	//TODO just temperary usage
	private MyWebRes getResByURL(String url){
		MyWebRes res = null;
		String id = null;
		Connection con = null;
		ResultSet rs = null;
		try {
			con = com.ns.db.DBC.getConnection("java:comp/env/jdbc/ngus");
			PreparedStatement st = con
					.prepareStatement(GET_RES_BY_URL);
			st.setString(1, url);
			rs = st.executeQuery();
			if (rs != null) {
			
				if (rs.next()) {
					id = rs.getString(1);	
					
				}
			}
		} catch (Exception e) {
			Log.error(e);
		} finally {
			DbUtils.closeResultSet(rs);
			DbUtils.closeConnection(con);
		}
		
		
		try{
			
			res = getInstanceByID(id);
		}
		catch(Exception e){
			
			Log.error(e);
			Log.error("get resource by id "+id + " failed");
		}
		
		return res;
	}
	
	
	/**
	 * 
	 * @param number
	 * @param type
	 * @return
	 */
	public List<MyWebRes> listMostPopularResWithCount(int start, int size, String type, int checked, Attribute count) {
		List<MyWebRes> al = new ArrayList<MyWebRes>();
		Connection con = null;
		ResultSet rs = null;		
		try {
			con = com.ns.db.DBC.getConnection("java:comp/env/jdbc/ngus");
			PreparedStatement st = con
					.prepareStatement(this.LIST_MOST_POPULAR_RESOURCE_BY_TYPE);
			st.setString(1, type);
			st.setInt(2, checked);
			//st.setInt(2, start);
			//st.setInt(3, size);
			rs = st.executeQuery();
			if (rs != null) {
				if (start > 0)
					rs.absolute(start+1);
				//while(start-->0&&rs.next()){}
				while (size-->0&&rs.next()) {
					String url = rs.getString(1);
					int addCount = rs.getInt(2);
					MyWebRes res = this.getResByURL(url);
					if (res == null){
						Log.error("get resource by url "+ url + "failed");
						continue;
					}
					res.setAddCount(addCount);
					if(res!=null){
						al.add(res);
						Log.trace("add one res " + res.getID());
					}else
						Log.error("cannot get res by url "+ url);
				}
				rs.last();
				if (count != null) {
					count.setValue(rs.getRow());
				}
				Log.trace("count="+count);
			}
		} catch (Exception e) {
			Log.error(e);
		} finally {
			DbUtils.closeResultSet(rs);
			DbUtils.closeConnection(con);
		}
		return al;
	}
	/**
	 * 
	 * @param type
	 * @return
	 */
	public int getResCountByType(String type) {
		//List<MyWebRes> al = new ArrayList<MyWebRes>();
		
		Connection con = null;
		ResultSet rs = null;
		int count = 0;
		try {
			con = com.ns.db.DBC.getConnection("java:comp/env/jdbc/ngus");
			PreparedStatement st = con
					.prepareStatement(GET_RESOURCE_COUNT_BY_TYPE);
			st.setString(1, type);			
			rs = st.executeQuery();
			if (rs != null) {
				if (rs.next()) {
					count = rs.getInt(1); 
				}
			}
		} catch (Exception e) {
			Log.error(e);
		} finally {
			DbUtils.closeResultSet(rs);
			DbUtils.closeConnection(con);
		}
		return count;
	}
	/**
	 * 
	 * @param number
	 * @param type
	 * @return
	 */
	public List<MyWebRes> listMostPopularRes(int number, String type) {
		List<MyWebRes> al = new ArrayList<MyWebRes>();
		
		Connection con = null;
		ResultSet rs = null;
		try {
			con = com.ns.db.DBC.getConnection("java:comp/env/jdbc/ngus");
			PreparedStatement st = null;
			if (type.equalsIgnoreCase("pic") || type.equalsIgnoreCase("webpage"))
				st = con
					.prepareStatement(this.LIST_MOST_POPULAR_RESOURCE_BY_TYPE2);
			else
				st = con
				.prepareStatement(this.LIST_MOST_POPULAR_RESOURCE_BY_TYPE);
			st.setString(1, type);
			st.setInt(2,0);
			st.setInt(3, number);
			rs = st.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					String url = rs.getString(1);
					int time = rs.getInt(2);
					MyWebRes res = this.getResByURL(url);
					if(res!=null){
						al.add(res);
					}
				}
			}
		} catch (Exception e) {
			Log.error(e);
		} finally {
			DbUtils.closeResultSet(rs);
			DbUtils.closeConnection(con);
		}
		return al;
	}
	/**
	 * List the lastest n resource added, n is specified by the user
	 * 
	 * @param number
	 *            the number of the result to be listed
	 * @return resource added most recently
	 */
	public List<MyWebRes> listMostRecentRes(int number) {
		ArrayList<MyWebRes> al = new ArrayList<MyWebRes>();
		Connection con = null;
		ResultSet rs = null;
		try {
			con = com.ns.db.DBC.getConnection("java:comp/env/jdbc/ngus");
			PreparedStatement st = con
					.prepareStatement(LIST_MOST_RECENT_RESOURCE);
			st.setInt(1, number);
			rs = st.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					String id = rs.getString(1);
					MyWebRes res = null;
					try{
						res = getInstanceByID(id);
					}
					catch(Exception e){
						Log.error(e);
					}
					if(res!=null){
						al.add(res);
					}
				}
			}
		} catch (Exception e) {
			Log.error(e);
		} finally {
			DbUtils.closeResultSet(rs);
			DbUtils.closeConnection(con);
		}
		return al;
	}
	public int getCount(String id){
		int count = 0;
		Connection con = null;
		ResultSet rs = null;
		try {
			con = com.ns.db.DBC.getConnection("java:comp/env/jdbc/ngus");
			PreparedStatement st = con
					.prepareStatement(this.LIST_PAGE_RESOURCE_BY_TYPE);
			st.setString(1, id);			
			rs = st.executeQuery();
			if (rs != null) {
				if(rs.next()){
					rs.getInt(1);
				}
			}			
		} catch (Exception e) {
			Log.error(e);
		} finally {
			DbUtils.closeResultSet(rs);
			DbUtils.closeConnection(con);
		}
		return count;
	}
	public List<MyWebRes> listPagePopularRes(int start , int number, String type, Attribute count) {
		List<MyWebRes> al = new ArrayList<MyWebRes>();
		Connection con = null;
		ResultSet rs = null;
		try {
			con = com.ns.db.DBC.getConnection("java:comp/env/jdbc/ngus");
			PreparedStatement st = con
					.prepareStatement(this.LIST_PAGE_RESOURCE_BY_TYPE);
			st.setString(1, type);
			st.setInt(2, start);
			st.setInt(3, number);
			rs = st.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					String url = rs.getString(1);
					
					
					
					MyWebRes res = this.getResByURL(url);
					if(res!=null){
						
						al.add(res);
					}
				}
			}
			st = con
			.prepareStatement(this.LIST_TOTAL_RESOURCE_BY_TYPE);
			st.setString(1, type);
			rs = st.executeQuery();
			if (rs != null) {
				rs.last();
				if (count != null) {
					count.setValue(rs.getRow());
				}
			}
		} catch (Exception e) {
			Log.error(e);
		} finally {
			DbUtils.closeResultSet(rs);
			DbUtils.closeConnection(con);
		}
		return al;
	}
	
	public List<MyWebRes> listByTypeTag(String type, String tag , int start, int number, String userID , Attribute count) {
		List<MyWebRes> al = new ArrayList<MyWebRes>();
		Connection con = null;
		ResultSet rs = null;
		if(tag!=null){ // type with tag
			try {
				con = com.ns.db.DBC.getConnection("java:comp/env/jdbc/ngus");
				PreparedStatement st = con
						.prepareStatement(List_BY_TYPE_TAG_USER);
				st.setString(1,type);
				st.setString(2,type);
				st.setString(3,tag);
				st.setString(4,userID);
				st.setInt(5, start);
				st.setInt(6, number);
				rs = st.executeQuery();
				if (rs != null) {
					while (rs.next()) {
						String url = rs.getString(1);						
						int checked = rs.getInt(2);
						MyWebRes res = this.getResByURL(url);		
						res.setChecked(checked);
						al.add(res);						
					}
				}
				
				// get count 
				st = con
				.prepareStatement(this.List_ALL_TYPE_TAG_USER);
				st.setString(1,type);
				st.setString(2,type);
				st.setString(3,tag);
				st.setString(4,userID);
				rs = st.executeQuery();
				if (rs != null) {
					rs.last();
					if (count != null) {
						count.setValue(rs.getRow());
					}
				}
			} catch (Exception e) {
				Log.error(e);
			} finally {
				DbUtils.closeResultSet(rs);
				DbUtils.closeConnection(con);
			}
		}
		if(tag == null||tag.equals("")){
			try {
				con = com.ns.db.DBC.getConnection("java:comp/env/jdbc/ngus");
				PreparedStatement st = con
						.prepareStatement(List_BY_TYPE_USER);
				st.setString(1,type);
				st.setString(2,type);
				st.setString(3,userID);
				st.setInt(4, start);
				st.setInt(5, number);
				rs = st.executeQuery();
				if (rs != null) {
					while (rs.next()) {
						String url = rs.getString(1);
						
						int checked = rs.getInt(2);
						MyWebRes res = this.getResByURL(url);
						res.setChecked(checked);
						al.add(res);
						
					}
				}
				st = con
				.prepareStatement(this.List_ALL_TYPE_USER);
				st.setString(1,type);
				st.setString(2,type);
				st.setString(3,userID);
				
				rs = st.executeQuery();
				if (rs != null) {
					rs.last();
					if (count != null) {
						count.setValue(rs.getRow());
					}
				}
			} catch (Exception e) {
				Log.error(e);
			} finally {
				DbUtils.closeResultSet(rs);
				DbUtils.closeConnection(con);
			}
		}
		
		return al;
	}
	
	public String[] getBeforeTimeList(long time, String sessionId) throws Exception{
		
		Session session = null;
		String[] list = null;
		try{
			//String newTime = parseTime(time);
			String newTime = DateTime.toUTCTime(time);
			String userId = parseSessionId(sessionId);
			session = JCRContentRepository.instance().getSession("tree");
			Workspace space = session.getWorkspace();
			QueryManager qm = space.getQueryManager();
			String sql = "select * from nt:base where jcr:path like '/myweb/"+userId+"[%]/%' and ngus:updateTime>'"+newTime+"'";
			Log.trace("sql="+sql);
			Query q = qm.createQuery(sql,Query.SQL);

			QueryResult qs = q.execute();
			NodeIterator ni = qs.getNodes();
			long num = ni.getSize();
			Log.trace("number="+num);
			list = new String[(int) num];
			int i =0;
			while(ni.hasNext()){
				Node n = ni.nextNode();
				Property pResid = n.getProperty(JCRContentRepository.NAMESPACE_PREFIX
						+ ":resid");
				String resid= pResid.getString();
				pResid = n.getProperty(JCRContentRepository.NAMESPACE_PREFIX
						+ ":updateTime");
				String ut = pResid.getString();
				list[i++] = resid;
				Log.trace("resid="+resid+", updatetime="+ut);
				
			
			}
		}catch(Exception e){
			Log.error(e);
		}
		finally{
			JCRContentRepository.instance().releaseSession(session,"tree");
		}	
		return list;
	}
	
	private String parseSessionId (String sessionId){
		WebServiceSession us = putSession(sessionId);
		if(us ==null){
			return null;
		}else{
			return us.getUser().getSUserId();
		}
	}
	
	private String parseTime (String time){
		StringBuffer buffer = new StringBuffer(21);
		String tmp = time.substring(0,8);
		String hour = time.substring(8,10);
		String minute = time.substring(10,12);
		String second = time.substring(12,14);
		buffer.append(tmp);
		buffer.append(" ");
		buffer.append(hour);
		buffer.append(":");
		buffer.append(minute);
		buffer.append(":");
		buffer.append(second);

		
		buffer.append(" 000");
		return buffer.toString();
	}
	
	public String[] getDeletedRes(long time,String sessionId){
		String[] list = null;
		String userId = parseSessionId(sessionId);
		Log.trace("userId="+userId + ", time="+time);
		Connection con = null;
		ResultSet rs = null;
		try{
			con = com.ns.db.DBC.getConnection("java:comp/env/jdbc/ngus");
			PreparedStatement st = con
					.prepareStatement(this.LIST_DELETE_RES);
			
			st.setInt(1, Integer.parseInt(userId));
			st.setLong(2, time);
			rs = st.executeQuery();
			
			int i=0;
			if (rs != null) {
				// get size
				rs.last();
				int size = rs.getRow();
				rs.beforeFirst();
				list = new String[size];
			
				Log.trace("size="+size);
				// fill array
				while(rs.next()){
					list[i++] = rs.getString(1);
				}
			}
		} catch (Exception e) {
			Log.error(e);
		} finally {
			DbUtils.closeResultSet(rs);
			DbUtils.closeConnection(con);
		}
		return list;
		
	}
	
	public String test() throws Exception{
		Session session = null;
		String[] str;
		String s = null;
		try{
			String newTime = this.parseTime("20070727121212");
			String sessionid = this.parseSessionId("LGW7IvFGoey617jXg+BbFQ==");
			session = JCRContentRepository.instance().getSession("tree");
			Workspace space = session.getWorkspace();
			QueryManager qm = space.getQueryManager();
			s += "select * from nt:base where jcr:path like '/myweb/"+sessionid+"[%]/%' and ngus:updateTime <"+newTime;
			Query q = qm.createQuery("select * from nt:base where jcr:path like '/myweb/"+sessionid+"[%]/%' and ngus:updateTime>'20070727 12:12:12 000'",Query.SQL);
			
			QueryResult qs = q.execute();
			NodeIterator ni = qs.getNodes();
			long num = ni.getSize();
			s += String.valueOf(num);
			str = new String[(int) num];
			int i =0;
			while(ni.hasNext()){
				Node n = ni.nextNode();
				Property pResid = n.getProperty(JCRContentRepository.NAMESPACE_PREFIX
						+ ":resid");
				
				
				str[i++]=pResid.getString();
				s+=str[i-1];
				
			}
		}catch(Exception e){
			s+=e.toString();
		}
		finally{
			JCRContentRepository.instance().releaseSession(session,"tree");
		}	
		return s;
	}
	
	//bDGjF3DWkh9w4pKpGAqplA==D:\ngus\trunk\myWeb\myWeb\target\myWeb-web-1.0-SNAPSHOT\WEB-INF\classes\com\ngus\myweb\services
	
	private WebServiceSession putSession(String sessionId){
		WebServiceSession session = null;
		session = new WebServiceSession(sessionId);		
		SessionManager.putSession(session);
		return session;
	}
	

}
