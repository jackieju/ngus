package com.ngus.resengine;

import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Property;
import javax.jcr.Session;
import javax.jcr.lock.Lock;
import javax.jcr.lock.LockException;

import org.apache.commons.lang.StringUtils;
import org.apache.jackrabbit.value.ValueFactoryImpl;

import com.ngus.dataengine.DEException;
import com.ngus.dataengine.DataEngine;
import com.ngus.dataengine.IDataEngine;
import com.ngus.dataengine.ResourceObject;
import com.ns.exception.NSException;
import com.ns.log.Log;
import com.ns.util.DateTime;
import com.ns.util.IO;

/**
 * @author jackie
 * 
 */
public class ResourceEngine implements IResEngine {

	// DBConnection dbc = null;

	// static final String DBCONNECT_STR =

	/**
	 * singleton logic
	 */
	static private ResourceEngine _instance = null;

	/**
	 * Get instance of DataMgr
	 * 
	 * @return the single instance of DataMgr, using the default config file
	 *         "ACCConfig.xml"
	 * @throws Exception
	 */
	synchronized static public ResourceEngine instance() throws NSException {
		if (_instance == null) {

			_instance = new ResourceEngine();

			_instance.init();

		}

		return _instance;
	}

	/**
	 * destroy
	 * 
	 * @throws Exception
	 */
	synchronized static public void destroy() throws Throwable {
		if (_instance != null) {
			_instance = null;
		}
	}

	/**
	 * interface
	 */

	/**
	 * Get resource by resource id. The id is in following format:
	 * <protocol>://huamu/<storage String>#ID storage can be: db, jcr, fs,
	 * webDav if storage is jcr, <storage String> = jcr/<workspace>/<path> ID
	 * is a number(long)
	 */
	// public InputStream get(String id) throws Exception {
	// ResourceId rid = new ResourceId(id);
	// if (rid.storageType.equalsIgnoreCase(IResEngine.STORAGETYPE_JCR))
	// return getJCRResource(rid);
	// return null;
	// }
	// public void set(String id, InputStream res, boolean Sync) throws
	// Exception {
	// ResourceId rid = new ResourceId(id);
	// Log.trace("resource id:" + rid.print());
	// if (rid.storageType.equalsIgnoreCase(IResEngine.STORAGETYPE_JCR))
	// setJCRResource(rid, res); // now always synchronized
	// }
	public void setResource(ResourceObject ro, boolean sync) throws Exception {
		ResourceId rid = new ResourceId(ro.getResId());
		Log.trace("resource id:" + rid.print());
		if (rid.storageType.equalsIgnoreCase(IResEngine.STORAGETYPE_JCR))
			setJCRResource(ro); // now always synchronized
	}

	public void updateResource(ResourceObject ro, boolean sync)
			throws Exception {
		ResourceId rid = new ResourceId(ro.getResId());
		Log.trace("resource id:" + rid.print());
		if (rid.storageType.equalsIgnoreCase(IResEngine.STORAGETYPE_JCR))
			updateJCRResource(ro); // now always synchronized
	}

	public void delete(String id) throws Exception {
		ResourceId rid = new ResourceId(id);
		if (rid.storageType.equalsIgnoreCase(IResEngine.STORAGETYPE_JCR))
			removeJCRResource(rid); // now always synchronized
	}

	private void removeJCRResource(Node node, boolean deleteRDF)
			throws Exception {
		String id = node.getProperty(
				JCRContentRepository.NAMESPACE_PREFIX + ":resid").getString();
		if (id == null)
			return;

		// delete children nodes
		NodeIterator it = node.getNodes();
		while (it.hasNext()) {
			Node child = it.nextNode();
			// delete it
			removeJCRResource(child, true);
		}

		// delete it from RDF
		if (deleteRDF)
			DataEngine.instance().deleteResource(id, false);

		Node p = node.getParent();

		// lock node
		boolean locked = false;
		while (true) {
			try {
				Log.trace("Lock node " + p.getPath() + "to remove "
						+ node.getPath());
				p.lock(false, false);
				Log.trace("Lock node " + p.getPath() + " successfully.");
				break;
			} catch (LockException e) {
				Log.error(e);
				Lock l = p.getLock();
				if (l != null) {
					Log.error("lock node failed, node locked by "
							+ l.getLockOwner());
					Log.error("lock token: " + l.getLockToken());
				}
				// break;
				Log.trace("wait one millisecond and retry...");

				Thread.sleep(1);
			}
		}
		locked = true;

		try {
			// delete itself
			node.remove();
			p.getSession().save();
		} catch (Exception e) {
			Log.error(e);
		}

		if (locked)
			p.unlock();

		Log.trace("resource " + id + " removed.");
	}

	private void removeJCRResource(ResourceId id) throws Exception {
		// get root
		Session session = JCRContentRepository.instance().getSession(
				id.getJcrWorkspace());
		Node root = session.getRootNode();

		// reach the node
		// Node node = createPath(root, id.getJcrPath());
		Node node = null;
		String path = id.getJcrPath().substring(1);
		if (root.hasNode(path)) {
			if (path.equalsIgnoreCase("/")) {
				Log.error("cannot remove root node");
				return;
			}
			node = root.getNode(path);

			// remove it
			removeJCRResource(node, false);

			// save
			session.save();
		} else
			Log.error("node " + path + " not exist in workspace "
					+ id.getJcrWorkspace());

		JCRContentRepository.instance().releaseSession(session,
				id.getJcrWorkspace());

		return;
	}

	public void init() throws DEException {
		Log.trace("Init resource engine...");
		// try {
		// Class.forName("com.mysql.jdbc.Driver");
		// } catch (Exception e) {
		// throw new DEException("create db connection failed", e);
		// }
		// try {
		// dbc = new DBConnection("jdbc:mysql://localhost/test", "root",
		// "111111", "mysql");
		// } catch (Exception e) {
		// throw new DEException("create db connection failed", e);
		// }
		Log.trace("Init resource engine finished.");
	}

	public ResourceObject getResource(String id, boolean value)
			throws Exception {
		ResourceId rid = new ResourceId(id);
		if (rid.storageType.equalsIgnoreCase(IResEngine.STORAGETYPE_JCR))
			return getJCRResource(rid, value);
		return null;
	}

	public ResourceObject getJCRResource(Node node, boolean bValue) throws Exception{
		// get data
		Property p = node.getProperty(JCRContentRepository.NAMESPACE_PREFIX
				+ ":content");
		// Value v = p.getValue();

		// Log.trace("pp="+p.getStream());
		// Log.trace("type="+p.getType());

		// LazyFileInputStream lfis = p.getStream();
		// byte[] data = IO.readInputStream(lfis);
		//		
		
		String resId = node.getProperty(JCRContentRepository.NAMESPACE_PREFIX + ":resid").getString();
		ResourceId id = new ResourceId(resId);
		InputStream value = null;
		if (bValue) {
			if (id.getProtocol().equalsIgnoreCase(
					IDataEngine.RES_PROTOCOL_TYPE_TEXT)) { // save as text
				String v = p.getString();
				value = IO.getInputStream(v, "UTF-8");

			} else
				value = p.getStream();
		}

		// title
		p = node.getProperty(JCRContentRepository.NAMESPACE_PREFIX + ":title");
		String title = p.getString();
		Log.trace("title=" + title);

		// desc
		String desc ="";
		try{
			p = node.getProperty(JCRContentRepository.NAMESPACE_PREFIX + ":desc");
			desc = p.getString();
			Log.trace("desc=" + desc);
		}catch(Exception e){
			Log.error(e);
		}

		// tags
		p = node.getProperty(JCRContentRepository.NAMESPACE_PREFIX + ":tags");
		String tags = p.getString();
		tags = tags.substring(1, tags.length() - 1);
		String[] s = tags.split(",");
		List<String> ls = new ArrayList<String>();
		for (int i = 0; i < s.length; i++) {
			ls.add(s[i].trim());
		}
		Log.trace("tags=" + tags);

		// resource type
		p = node
				.getProperty(JCRContentRepository.NAMESPACE_PREFIX + ":resType");
		String types = p.getString();
		types = types.substring(1, types.length() - 1);
		s = types.split(",");
		List<String> ls_type = new ArrayList<String>();
		for (int i = 0; i < s.length; i++) {
			ls_type.add(s[i].trim());
		}
		Log.trace("restypes=" + types);


		// user
		p = node.getProperty(JCRContentRepository.NAMESPACE_PREFIX + ":user");
		String user = p.getString();
		Log.trace("user=" + user);
		
		// share level
		p = node
				.getProperty(JCRContentRepository.NAMESPACE_PREFIX + ":shareLevel");
		int sl = (int)p.getValue().getLong();		
		Log.trace("shareLevel=" + sl);
		
		// type
		p = node
				.getProperty(JCRContentRepository.NAMESPACE_PREFIX + ":type");
		int type = Integer.parseInt(p.getString());
		Log.trace("type=" + type);
		
		// creat time
		p = node
				.getProperty(JCRContentRepository.NAMESPACE_PREFIX + ":createTime");
		
		Timestamp c_tm = new Timestamp(DateTime.fromUTCTime(p.getString()).getTime());		
		Log.trace("c_tm=" + c_tm.toString());
		
		// update time
		// creat time
		p = node
				.getProperty(JCRContentRepository.NAMESPACE_PREFIX + ":updateTime");
		Timestamp u_tm = new Timestamp(DateTime.fromUTCTime(p.getString()).getTime());		
		Log.trace("u_tm=" + u_tm.toString());

		
		
		
		ResourceObject ro = new ResourceObject(user);
		ro.setResId(id.getResId());
		if (value != null)
			ro.setValue(value);
		ro.setTags(ls);
		ro.setDesc(desc);
		ro.setResourceType(ls_type);
		ro.setType(type);
		ro.setCreateTime(c_tm);
		ro.setUpdateTime(u_tm);
		ro.setUser(user);
		ro.setShareLevel(sl);
		ro.setTitle(title);
		

		return ro;
	}
	
	public ResourceObject getJCRResource(ResourceId id, boolean bValue)
			throws Exception {
		Session session = JCRContentRepository.instance().getSession(
				id.getJcrWorkspace());
		Node root = session.getRootNode();

		// reach the parent
		// Node node = createPath(root, id.getJcrPath());
		Node node = root.getNode(id.getJcrPath().substring(1)); // skip "/"
		// JCRContentRepository.instance().getWorkspace().
		// Session session = node.getSession();

		ResourceObject ro = this.getJCRResource(node, bValue);
		
		JCRContentRepository.instance().releaseSession(session,
				id.getJcrWorkspace());

		return ro;

	}

	/*
	 * private InputStream getJCRResource(ResourceId id) throws Exception {
	 * Session session = JCRContentRepository.instance().getSession(
	 * id.getJcrWorkspace()); Node root = session.getRootNode(); // reach the
	 * parent // Node node = createPath(root, id.getJcrPath()); Node node =
	 * root.getNode(id.getJcrPath().substring(1)); // skip "/" //
	 * JCRContentRepository.instance().getWorkspace(). // Session session =
	 * node.getSession(); // get data Property p =
	 * node.getProperty(JCRContentRepository.NAMESPACE_PREFIX + ":content"); //
	 * Value v = p.getValue(); // Log.trace("pp="+p.getStream()); //
	 * Log.trace("type="+p.getType()); // Log.trace("title = " + //
	 * node.getProperty(JCRContentRepository.NAMESPACE_PREFIX // +
	 * ":title").getLong()); // LazyFileInputStream lfis = p.getStream(); //
	 * byte[] data = IO.readInputStream(lfis); //
	 * 
	 * InputStream ret; if (id.getProtocol().equalsIgnoreCase(
	 * IDataEngine.RES_PROTOCOL_TYPE_TEXT)) { // save as text String v =
	 * p.getString(); ret = IO.getInputStream(v, "UTF-8"); } else ret =
	 * p.getStream(); JCRContentRepository.instance().releaseSession(session,
	 * id.getJcrWorkspace()); return ret; }
	 * 
	 * private void setJCRResource(ResourceId id, InputStream res) throws
	 * Exception { Log.trace("session for " + id.getJcrPath() + " start."); //
	 * test Session session = null; // try { // SimpleCredentials cred = new
	 * SimpleCredentials(UserManager // .getCurrentUser().getUserId(),
	 * "".toCharArray()); // // session =
	 * JCRContentRepository.instance().getRepository().login( // cred,
	 * id.getJcrWorkspace()); // // Log.trace("new session is " + session + "
	 * for workspace " + // // workspace); // } catch (Exception e) { //
	 * Log.error(e); // } session = JCRContentRepository.instance().getSession(
	 * id.getJcrWorkspace()); Log.trace("sesson = " + session); Node root =
	 * session.getRootNode(); // Node root =
	 * JCRContentRepository.instance().getWorkspaceRoot( //
	 * id.getJcrWorkspace());
	 * 
	 * Log.trace("create parent node"); // reach the parent Node node =
	 * reachPath(root, id.getJcrPath()); // String path =
	 * id.getJcrPath().substring(1); // Node node = null; // if
	 * (root.hasNode(path)) // node = root.getNode(path); // else // node =
	 * root.addNode(path, "res"); // node.getSession().save(); // Session
	 * session = node.getSession(); // set data // ValueFactory vf =
	 * session.getValueFactory(); // Value v = vf.createValue(res);
	 * node.setProperty(JCRContentRepository.NAMESPACE_PREFIX + ":title", new
	 * Long(id.getId()).toString()); //
	 * node.setProperty(JCRContentRepository.NAMESPACE_PREFIX + ":category", //
	 * new Value[] {}); // node.setProperty("category", // / new Value[] {});
	 * node.setProperty(JCRContentRepository.NAMESPACE_PREFIX + ":resType", id
	 * .getProtocol()); Property p = null; if
	 * (id.getProtocol().equalsIgnoreCase( IDataEngine.RES_PROTOCOL_TYPE_TEXT)) { //
	 * save as text try { // ByteArrayInputStream bais = (ByteArrayInputStream)
	 * byte[] bs = IO.readInputStream(res); String v = new String(bs, "UTF-8");
	 * p = node.setProperty(JCRContentRepository.NAMESPACE_PREFIX + ":content",
	 * v); } catch (Exception e) { Log.error(e);
	 * JCRContentRepository.instance().releaseSession(session,
	 * id.getJcrWorkspace()); return; }
	 * 
	 * Log.trace("p=" + p.getString()); }
	 * 
	 * else if (id.getProtocol().equalsIgnoreCase(
	 * IDataEngine.RES_PROTOCOL_TYPE_BIN)) { // save as binary p =
	 * node.setProperty(JCRContentRepository.NAMESPACE_PREFIX + ":content",
	 * res); Log.trace("p=" + p.getStream()); } else { // default is save as
	 * binary p = node.setProperty(JCRContentRepository.NAMESPACE_PREFIX +
	 * ":content", res); Log.trace("p=" + p.getStream()); }
	 * 
	 * node.setProperty(JCRContentRepository.NAMESPACE_PREFIX + ":resid", id
	 * .getResId());
	 * 
	 * session.save(); // node.getSession().logout();
	 * JCRContentRepository.instance().releaseSession(session,
	 * id.getJcrWorkspace()); Log.trace("session for " + id.getJcrPath() + "
	 * end."); return; }
	 */

	private void setJCRResource(ResourceObject ro) throws Exception {
		ResourceId id = new ResourceId(ro.getResId());
		Log.trace("session for " + id.getJcrPath() + " start.");
		// test
		Session session = null;
		// try {
		// SimpleCredentials cred = new SimpleCredentials(UserManager
		// .getCurrentUser().getUserId(), "".toCharArray());
		//
		// session = JCRContentRepository.instance().getRepository().login(
		// cred, id.getJcrWorkspace());
		// // Log.trace("new session is " + session + " for workspace " +
		// // workspace);
		// } catch (Exception e) {
		// Log.error(e);
		// }
		session = JCRContentRepository.instance().getSession(
				id.getJcrWorkspace());
		Log.trace("session = " + session);
		Node root = session.getRootNode();
		// Node root = JCRContentRepository.instance().getWorkspaceRoot(
		// id.getJcrWorkspace());

		Log.trace("create parent node");

		// reach the parent
		Node node = JCRContentRepository.instance().reachPath(root, id.getJcrPath(), true);

		// String path = id.getJcrPath().substring(1);
		// Node node = null;
		// if (root.hasNode(path))
		// node = root.getNode(path);
		// else
		// node = root.addNode(path, "res");
		// node.getSession().save();

		// Session session = node.getSession();

		// set data
		// ValueFactory vf = session.getValueFactory();
		// Value v = vf.createValue(res);
		// node.setProperty(JCRContentRepository.NAMESPACE_PREFIX + ":title",
		// new Long(id.getId()).toString());
		//Log.trace("checkpoint 2 start");
		String title = ro.getTitle();
		if (title == null)
			title = "";
		//Log.trace("checkpoint 2 1");
		node.setProperty(JCRContentRepository.NAMESPACE_PREFIX + ":title", title);
		// node.setProperty(JCRContentRepository.NAMESPACE_PREFIX + ":category",
		// new Value[] {});
		// node.setProperty("category",
		// / new Value[] {});
		//Log.trace("checkpoint 2 2");
		List<String> types = ro.getResourceType();
		if (types == null)
			throw new Exception("no resource type ");
		//Log.trace("checkpoint 2 3");
		node.setProperty(JCRContentRepository.NAMESPACE_PREFIX + ":resType", ro
				.getResourceType().toString());
		//Log.trace("checkpoint 2 4");
		Property p = null;
		InputStream value = ro.getValue();
		if (value == null)
			throw new Exception("no resource value");
		//Log.trace("checkpoint 2 5");
		if (id.getProtocol().equalsIgnoreCase(
				
				IDataEngine.RES_PROTOCOL_TYPE_TEXT)) { // save as text
			try {
				// ByteArrayInputStream bais = (ByteArrayInputStream)
				byte[] bs = IO.readInputStream(value);
				String v = new String(bs, "UTF-8");
				p = node.setProperty(JCRContentRepository.NAMESPACE_PREFIX
						+ ":content", v);
			} catch (Exception e) {
				Log.error(e);
				JCRContentRepository.instance().releaseSession(session,
						id.getJcrWorkspace());
				return;
			}

			Log.trace("p=" + p.getString());
		}
		else if (id.getProtocol().equalsIgnoreCase(
				IDataEngine.RES_PROTOCOL_TYPE_BIN)) { // save as binary
			p = node.setProperty(JCRContentRepository.NAMESPACE_PREFIX
					+ ":content", value);
			Log.trace("p=" + p.getStream());
		} else { // default is save as binary
			p = node.setProperty(JCRContentRepository.NAMESPACE_PREFIX
					+ ":content", value);
			Log.trace("p=" + p.getStream());
		}
		//Log.trace("checkpoint 2 6");
		String resId = id.getResId();
		if (resId == null )
			throw new Exception("no resource id");
		node.setProperty(JCRContentRepository.NAMESPACE_PREFIX + ":resid", resId);
		//Log.trace("checkpoint 2 7");
		List<String> tags = ro.getTags();
		if (tags == null)
			tags = new ArrayList<String>();
		node.setProperty(JCRContentRepository.NAMESPACE_PREFIX + ":tags", tags.toString());
		//Log.trace("checkpoint 2 8");
		String desc = ro.getDesc();
		if (desc != null && desc.length()>0) // jcr won't create property if it is empty string, so we don't call it if value is null or empty string
			node.setProperty(JCRContentRepository.NAMESPACE_PREFIX + ":desc", ro
				.getDesc());
		
		//Log.trace("checkpoint 2 9");
		String user = ro.getUser();
		if (user != null && user.length()>0)
			node.setProperty(JCRContentRepository.NAMESPACE_PREFIX + ":user", user);
		//Log.trace("checkpoint 2 10");
		
		int type = ro.getType();
		if (type < 0)
			type = 2;
		node.setProperty(JCRContentRepository.NAMESPACE_PREFIX + ":type", type);
		//Log.trace("checkpoint 2 11");
		// share level
		int shareLevel = ro.getShareLevel();
		if (shareLevel < 0)
			shareLevel = 0;			
		node.setProperty(JCRContentRepository.NAMESPACE_PREFIX + ":shareLevel",  ValueFactoryImpl.getInstance().createValue(shareLevel));
		//Log.trace("checkpoint 2 12");
		Timestamp c_tm = ro.getCreateTime();
		if (c_tm == null)
			c_tm = new Timestamp(DateTime.currentTime().getTime());
		node.setProperty(JCRContentRepository.NAMESPACE_PREFIX + ":createTime",  DateTime.toUTCTime(c_tm.getTime()));
		//Log.trace("checkpoint 2 13");
		Timestamp u_tm = ro.getUpdateTime();
		if (u_tm == null)
			u_tm = c_tm = new Timestamp(DateTime.currentTime().getTime());
		node.setProperty(JCRContentRepository.NAMESPACE_PREFIX + ":updateTime",  DateTime.toUTCTime(u_tm.getTime()));
		//Log.trace("checkpoint 2 end");
		Log.trace("save session");
		session.save();

		
		// node.getSession().logout();
		JCRContentRepository.instance().releaseSession(session,
				id.getJcrWorkspace());

		Log.trace("session for " + id.getJcrPath() + " end.");
		return;
	}

	private void updateJCRResource(ResourceObject ro) throws Exception {
		ResourceId id = new ResourceId(ro.getResId());
		Log.trace("session for " + id.getJcrPath() + " start.");
		// test
		Session session = null;

		session = JCRContentRepository.instance().getSession(
				id.getJcrWorkspace());
		Log.trace("sesson = " + session);
		Node root = session.getRootNode();

		Log.trace("create parent node");

		// reach the parent
		Node node = JCRContentRepository.instance().reachPath(root, id.getJcrPath(), false);
		if (node == null)
			throw new NSException("jcr resource " + id + " does not exist");

		if (ro.getTitle() != null)
		node.setProperty(JCRContentRepository.NAMESPACE_PREFIX + ":title", ro
				.getTitle());
		// node.setProperty(JCRContentRepository.NAMESPACE_PREFIX + ":category",
		// new Value[] {});
		// node.setProperty("category",
		// / new Value[] {});
		if (ro.getResourceType() != null)
		node.setProperty(JCRContentRepository.NAMESPACE_PREFIX + ":resType", ro
				.getResourceType().toString());

		
		Property p = null;
		if (ro.getValue() != null){
		if (id.getProtocol().equalsIgnoreCase(
				IDataEngine.RES_PROTOCOL_TYPE_TEXT)) { // save as text
			try {
				// ByteArrayInputStream bais = (ByteArrayInputStream)
				byte[] bs = IO.readInputStream(ro.getValue());
				String v = new String(bs, "UTF-8");
				Log.trace("v="+v);
				
				p = node.setProperty(JCRContentRepository.NAMESPACE_PREFIX
						+ ":content", v);
				Log.trace("p=" + p.getString());
			} catch (Exception e) {
				Log.error(e);
				JCRContentRepository.instance().releaseSession(session,
						id.getJcrWorkspace());
				return;
			}

			
		}

		else if (id.getProtocol().equalsIgnoreCase(
				IDataEngine.RES_PROTOCOL_TYPE_BIN)) { // save as binary
			p = node.setProperty(JCRContentRepository.NAMESPACE_PREFIX
					+ ":content", ro.getValue());
			Log.trace("p=" + p.getStream());
		} else { // default is save as binary
			p = node.setProperty(JCRContentRepository.NAMESPACE_PREFIX
					+ ":content", ro.getValue());
			Log.trace("p=" + p.getStream());
		}
		}

		if (ro.getTags() != null)
		node.setProperty(JCRContentRepository.NAMESPACE_PREFIX + ":tags", ro
				.getTags().toString());

		if (ro.getDesc()!=null)
		node.setProperty(JCRContentRepository.NAMESPACE_PREFIX + ":desc", ro
				.getDesc());
		int type = ro.getType();
		if (type >= 0)			
			node.setProperty(JCRContentRepository.NAMESPACE_PREFIX + ":desc", ro
				.getDesc());
		
		int shareLevel = ro.getShareLevel();
			if (shareLevel >= 0)					
			node.setProperty(JCRContentRepository.NAMESPACE_PREFIX + ":shareLevel",  ValueFactoryImpl.getInstance().createValue(shareLevel));

		Timestamp u_tm = ro.getUpdateTime();
		if (u_tm != null)
			node.setProperty(JCRContentRepository.NAMESPACE_PREFIX + ":updateTime",  DateTime.toUTCTime(u_tm.getTime()));

		session.save();

		// node.getSession().logout();
		JCRContentRepository.instance().releaseSession(session,
				id.getJcrWorkspace());

		Log.trace("session for " + id.getJcrPath() + " end.");
		return;
	}



	public static void main(String[] v) throws Exception {
		String[] strings = new String[3];
		strings[0] = "http://huamu/db#32432";
		strings[1] = "http://huamu/jcr/haha/hoho#32432";
		// strings[1] = "http://huamu/jcr/haha/hoho#32432";
		for (int i = 0; i < strings.length; i++)
			System.out.println("convert id " + strings[i] + " = "
					+ new ResourceId(strings[i]).print());
	}

	/**
	 * 
	 * @param jcrPath
	 *            valid JCRPath
	 * @param recursive
	 *            recursively or not
	 * @return
	 * @throws Exception
	 */
	public ResourceObject getResourceByPath(String jcrPath, int depth)
			throws Exception {
		Session session = JCRContentRepository.instance().getSession(
				JCRContentRepository.WORKSPACE_TREE);
		Node root = session.getRootNode();

		// reach the parent
		Node node = root.getNode(jcrPath);
		ResourceObject ret = loadResource(node, depth);
		JCRContentRepository.instance().releaseSession(session,
				JCRContentRepository.WORKSPACE_TREE);
		return ret;
	}

	public void updateShareLevel(Node  node, int sl, boolean recursive ) throws Exception{

		node.setProperty(JCRContentRepository.NAMESPACE_PREFIX + ":shareLevel",  ValueFactoryImpl.getInstance().createValue(sl));

		if (recursive)
		{
			NodeIterator it = node.getNodes(); // get children
			while (it.hasNext()) {
				/*
				 * String res_id = it.nextNode().getProperty(
				 * JCRContentRepository.NAMESPACE_PREFIX + ":resid") .getString();
				 */
				updateShareLevel(it.nextNode(), sl, recursive);
			}

		}		
		
	}
	
	public void updateShareLevel(String resourceId, int sl, boolean recursive ) throws Exception{
		ResourceId id = new ResourceId(resourceId);
		Session session = JCRContentRepository.instance().getSession(
				id.getJcrWorkspace());
		Node root = session.getRootNode();

		// reach the parent
		// Node node = createPath(root, id.getJcrPath());
		Node node = root.getNode(id.getJcrPath().substring(1)); // skip "/"
		// JCRContentRepository.instance().getWorkspace().
		// Session session = node.getSession();

		updateShareLevel(node, sl, recursive);
		
		JCRContentRepository.instance().releaseSession(session,
				id.getJcrWorkspace());
		
	}
	
	public ResourceObject loadResource(Node node, int depth) throws Exception {
		Log.trace("enter, depth="+depth);
		String resId = JCRContentRepository.getNodeId(node);
		Log.trace("node id = " + resId);
		ResourceObject ret = DataEngine.instance().getResourceObjById(resId,
				true);
		Log.trace("ro = " + ret);
		
		
		// get data
//		Property p = node.getProperty(JCRContentRepository.NAMESPACE_PREFIX
//				+ ":content");
//
//		InputStream v = p.getStream();
//
//		ret.setValue(v);
		

		if (depth == 1) // only current layer
			return ret;

		NodeIterator it = node.getNodes(); // get children
		while (it.hasNext()) {
			/*
			 * String res_id = it.nextNode().getProperty(
			 * JCRContentRepository.NAMESPACE_PREFIX + ":resid") .getString();
			 */
			ResourceObject ro = loadResource(it.nextNode(), depth - 1);
			if (ro != null)
				ret.addChildResource(ro);
		}
		Log.trace("leave");
		return ret;

	}

	public ResourceObject getResourceTree(String resId, int depth)
			throws Exception {
		/*
		 * ResourceObject ret = DataEngine.instance().getResourceObjById(resId,
		 * false);
		 */

		Log.trace("id=" + resId);
		ResourceId id = new ResourceId(resId);
		Log.trace("id=" + id.print());

		Session session = JCRContentRepository.instance().getSession(
				id.getJcrWorkspace());
		Node root = session.getRootNode();

		// reach the target node
		Node node = root.getNode(id.getJcrPath().substring(1)); // skip "/"

		ResourceObject ret = loadResource(node, depth);
		JCRContentRepository.instance().releaseSession(session,
				id.getJcrWorkspace());
		return ret;

	}

}
