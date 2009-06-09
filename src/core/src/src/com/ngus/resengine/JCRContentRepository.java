package com.ngus.resengine;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import javax.jcr.NamespaceException;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import javax.jcr.Workspace;
import javax.jcr.lock.Lock;
import javax.jcr.lock.LockException;
import javax.jcr.nodetype.NoSuchNodeTypeException;
import javax.jcr.nodetype.NodeTypeManager;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.jackrabbit.core.nodetype.InvalidNodeTypeDefException;
import org.apache.jackrabbit.core.nodetype.NodeTypeDef;
import org.apache.jackrabbit.core.nodetype.NodeTypeManagerImpl;
import org.apache.jackrabbit.core.nodetype.NodeTypeRegistry;
import org.apache.jackrabbit.core.nodetype.xml.NodeTypeReader;

import com.ngus.config.Path;
import com.ngus.config.SystemProperty;
import com.ns.log.Log;

public class JCRContentRepository {
	/**
	 * singleton logic
	 */
	static private JCRContentRepository _instance = null;

	public static final String WORKSPACE_TREE = "tree";

	public static final String PROPERTY_RESID = "resid";

	public static final String PROPERTY_TITLE = "title";

	public static final String PROPERTY_CONTENT = "content";

	public static final String PROPERTY_RESTYPE = "resType";

	public static final String PROPERTY_CATEGORY = "category";

	public class SessionListEle {
		private boolean used = false;

		private Session session = null;

		public SessionListEle(Session s) {
			this.session = s;
		}

		boolean isUsed() {
			return used;
		}

		Session getSession() {
			used = true;
			return session;
		}

		void releaseSession() {
			used = false;
		}

	}

	public static final int MAX_SESSIONPOOLSIZE = 20;

	private static final HashMap<String, Vector<SessionListEle>> sessions = new HashMap<String, Vector<SessionListEle>>();

	public Session getSession(String workspace) {
		String keyName = workspace;
		if (keyName == null)
			keyName = "[default]"; // Log.trace("get session for workspace " +
		// workspace);

		boolean bPoolFull = false;
		do {
			//Log.trace("get session...");
			synchronized (sessions) {
				Log.trace("after synchronizaed");
				// get session list for workspace
				Vector<SessionListEle> sessionList = sessions.get(keyName);
				if (sessionList == null) {
					Log.trace("no session list for workspace " + workspace);
					sessionList = new Vector<SessionListEle>();
					sessions.put(keyName, sessionList);
				}

				SessionListEle sle = null;
				if (sessionList.size() != 0)
					sle = sessionList.get(0); // get from head
				if (sle == null || sle.isUsed()) { // no session availble
					// create new session
					if (sessionList.size() < MAX_SESSIONPOOLSIZE) {
						Session session = null;
						try {
							SimpleCredentials cred = new SimpleCredentials(
									"userid", "".toCharArray());
							session = repo.login(cred, workspace);
							Log.trace("new session is " + session
									+ " for workspace " + workspace);
						} catch (Exception e) {
							Log.error(e);
							return null;
						}
						sle = new SessionListEle(session);
						sessionList.insertElementAt(sle, 0);
					} else { // reach max pool size
						Log.trace("session list if full for workspace "
								+ workspace);
						bPoolFull = true;
					}
				}
				if (sle != null) {
					sessionList.remove(0);
					sessionList.insertElementAt(sle, sessionList.size()); // put
					// to
					// tail
					Session ret = sle.getSession();
					Log.trace("session: " + ret);
					return ret;
				}

			}
			if (bPoolFull) // wait
			{
				try {
					Thread.sleep(1);
				} catch (Exception e) {
					Log.error(e);
				}
			}
		} while (bPoolFull);

		return null;

	}

	public void releaseSession(Session session, String workspace) {
		if (session == null)
			return;
		Log.trace("session = " + session);
		try {
			if (session.hasPendingChanges()) {
				Log.error("session " + session + " has pending changes!!!");
			}
		} catch (Exception e) {
			Log.error(e);
		}
		String keyName = workspace;
		if (keyName == null)
			keyName = "[default]"; // Log.trace("get session for workspace " +
		// workspace);

		synchronized (sessions) {
			// get session list for workspace
			Vector<SessionListEle> sessionList = sessions.get(keyName);
			if (sessionList == null) {
				Log
						.error("Cannot release session, because not session list for workspace"
								+ workspace);
				return;
			}

			// lookup session
			Iterator<SessionListEle> it = sessionList.iterator();
			while (it.hasNext()) {
				SessionListEle sle = it.next();
				if (sle.getSession() == session) {
					sle.releaseSession();

					// move to head
					sessionList.removeElement(sle);
					sessionList.insertElementAt(sle, 0);
					return;
				}
			}
			Log
					.error("Cannot release session, because session not found in pool.");

		}

	}

	private Repository repo;

	/**
	 * Get instance of DataMgr
	 * 
	 * @return the single instance of DataMgr, using the default config file
	 *         "ACCConfig.xml"
	 * @throws Exception
	 */
	synchronized static public JCRContentRepository instance() throws Exception {
		if (_instance == null) {

			_instance = new JCRContentRepository();

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

	public void init() throws Exception {
		Log.trace("Init...");
		/*
		 * String configFile = Path.getRepositoriesConfigFilePath(); String
		 * repHomeDir = SystemProperty.getProperty("ngus.jcr.rephome");
		 * Log.log("repository config file: " + configFile); Log.log("repository
		 * home dir: " + repHomeDir);
		 * 
		 * Hashtable env = new Hashtable();
		 * env.put(Context.INITIAL_CONTEXT_FACTORY,
		 * "org.apache.jackrabbit.core.jndi" +
		 * ".provider.DummyInitialContextFactory");
		 * 
		 * env.put(Context.PROVIDER_URL, "localhost");
		 * 
		 * InitialContext ctx = new InitialContext(env);
		 * 
		 * RegistryHelper.registerRepository(ctx, "repo", configFile,
		 * repHomeDir, true);
		 * 
		 * repo = (Repository) ctx.lookup("repo");
		 */
		Log.log("load repository home dir: ");
		try {
			Log.trace("init context");
			InitialContext initCtx = new InitialContext();
			Log.trace("lookup java:comp/env");
			Context ctx = (Context) initCtx.lookup("java:comp/env");
			Log.trace("lookup jcr/repository");
			repo = (Repository) ctx.lookup("jcr/repository");
			if (repo == null)
				throw new Exception("can not lookup repository");

			Log.trace("get session");
			Session session = getSession(null);
			Log.trace("get workspace");
			Workspace ws = session.getWorkspace();
			registerNamespace(NAMESPACE_PREFIX, NAMESPACE_URI, ws);
			Log.trace("release session");
			this.releaseSession(session, null);
		} catch (Exception e) {
			Log.error(e);
			throw e;
		}
		// this.registerNodeTypes();
		Log.trace("Init JCRContentRepository ok.");
	}

	/**
	 * @see info.magnolia.repository.Provider#registerNamespace(java.lang.String,
	 *      java.lang.String, javax.jcr.Workspace)
	 */
	static public void registerNamespace(String namespacePrefix, String uri,
			Workspace workspace) throws RepositoryException {
		try {
			workspace.getNamespaceRegistry().getURI(namespacePrefix);
		} catch (NamespaceException e) {
			Log.log(e.getMessage());
			Log
					.log("registering prefix [{}] with uri {}" + namespacePrefix + uri); //$NON-NLS-1$
			workspace.getNamespaceRegistry().registerNamespace(namespacePrefix,
					uri);
		}
	}

	public static final String NAMESPACE_PREFIX = "ngus";

	public static final String NAMESPACE_URI = "http://www.ngus.info/jcr/ngus"; //$NON-NLS-1$

	public Repository getRepository() {
		return repo;
	}

	// public Workspace getWorkspace(String workspace) throws Exception {
	//
	// Session session = getSession(workspace);
	// Workspace ws = session.getWorkspace();
	// // Node rn = session.getRootNode();
	// return ws;
	// }

	// public Session getSession(String workspace) {
	// Log.trace("enter");
	// Session session = null;
	//
	// /*
	// * // test try { SimpleCredentials cred = new
	// * SimpleCredentials("userid", "" .toCharArray()); session =
	// * repo.login(cred, workspace); // Log.trace("new session is " + session +
	// "
	// * for workspace " + // workspace); } catch (Exception e) {
	// * Log.error(e); return null; } return session;
	// */
	//
	// String keyName = workspace;
	// if (keyName == null)
	// keyName = "[default]"; // Log.trace("get session for workspace
	// // " + workspace);
	//
	// synchronized (sessions) {
	// session = sessions.get(keyName);
	// if (session == null) {
	// Log.trace("session is null for workspace " + workspace);
	// try {
	// SimpleCredentials cred = new SimpleCredentials("userid", ""
	// .toCharArray());
	// session = repo.login(cred, workspace);
	// Log.trace("new session is " + session + " for workspace "
	// + workspace);
	// } catch (Exception e) {
	// Log.error(e);
	// return null;
	// }
	// sessions.put(keyName, session);
	// }
	//
	// }
	//
	// Log.trace("leave");
	// return session;
	//
	// }

	// public Node getWorkspaceRoot(String workspace) throws Exception {
	// Session session = getSession(workspace);
	// Node rn = session.getRootNode();
	// return rn;
	// }

	public String printWorkspace(String wsName, String path) throws Exception {
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		Session session = this.getSession(wsName);
		session.exportSystemView(path, out, false, false);
		// String ret = out.toString("UTF-8");
		releaseSession(session, wsName);
		return out.toString();
	}

	static public String getNodeId(Node node) throws Exception {
		return node.getProperty(
				JCRContentRepository.NAMESPACE_PREFIX + ":"
						+ JCRContentRepository.PROPERTY_RESID).getString();
	}

	static public String getNodeResType(Node node) throws Exception {
		return node.getProperty(
				JCRContentRepository.NAMESPACE_PREFIX + ":"
						+ JCRContentRepository.PROPERTY_RESTYPE).getString();
	}

	static public String getNodeTitle(Node node) throws Exception {
		return node.getProperty(
				JCRContentRepository.NAMESPACE_PREFIX + ":"
						+ JCRContentRepository.PROPERTY_TITLE).getString();
	}

	static public String getNodeCategory(Node node) throws Exception {
		return node.getProperty(
				JCRContentRepository.NAMESPACE_PREFIX + ":"
						+ JCRContentRepository.PROPERTY_CATEGORY).getString();
	}

	public Node reachPath(Node node, String path, boolean bCreate)
			throws Exception {

		// remove leading '/'
		Log.trace("path=" + path);

		path = StringUtils.removeStart(path, "/");

		String[] names = path.split("/");
		for (int i = 0; i < names.length; i++) {
			String name = names[i];
			if (name == null || name.length() == 0)
				continue;
			boolean locked = false;
			Node p = node;

		//	Log.trace("checkpoint 1 start");
			
			boolean toAddNode = !node.hasNode(name);

			if (toAddNode && !bCreate)
				return null;

			if (toAddNode && !node.getPath().equalsIgnoreCase("/")) {
				//				
				// while (node.isLocked())
				// {
				// Thread.sleep(1);
				// }
		//		Log.trace("checkpoint 1 end2");
				Log
						.trace("Lock node " + node.getPath() + "to add child"
								+ name);
				locked = lockNode(node, true, false, false);
				Log.trace("Lock node " + node.getPath() + "return " + locked);

			}
			if (node.hasNode(name)) {// check it again, becuase node can be
				//Log.trace("checkpoint 1 end1");
				// added after last checking and before
				// locking
				node = node.getNode(name);
			} else {
			//	Log.trace("checkpoint 1 end3");
				node = node.addNode(name, "res");
				try {
					Log.trace("save session...");
					p.getSession().save();
					Log.trace("session saved.");
				} catch (Exception e) {
					Log.error(e);
				}
			}
			if (locked) {
				//Log.trace("checkpoint 1 end4");
				Log.trace("unlock node...");
				p.unlock();
				Log.trace("Unlock node " + p.getPath() + " successfully.");
			}
		}
		return node;
	}

	/**
	 * @param configuration
	 * @return InputStream of node type definition file
	 */
	private InputStream getNodeTypeDefinition(String configuration) {

		InputStream xml;

		if (StringUtils.isNotEmpty(configuration)) {

			// 1: try to load the configured file from the classpath
			xml = getClass().getResourceAsStream(configuration);
			if (xml != null) {
				Log.log("Custom node types registered using {}", configuration);
				return xml;
			}

			// 2: try to load it from the file system
			File nodeTypeDefinition = new File(Path
					.getAbsoluteFileSystemPath(configuration));
			if (nodeTypeDefinition.exists()) {
				try {
					return new FileInputStream(nodeTypeDefinition);
				} catch (FileNotFoundException e) {
					// should never happen
					Log.error("File not found: {}", xml);
				}
			}

			// 3: defaults to standard nodetypes
			Log.error("Unable to find node type definition " + configuration);
		}

		// initialize default magnolia nodetypes
		xml = getClass().getResourceAsStream("config/nodeTypes.xml");

		return xml;
	}

	public void registerNodeTypes() throws RepositoryException {

		// check if workspace already exists
		SimpleCredentials credentials = new SimpleCredentials("admin", "admin"
				.toCharArray());
		Session jcrSession = this.repo.login(credentials);
		Workspace workspace = jcrSession.getWorkspace();

		InputStream xml = getNodeTypeDefinition(SystemProperty
				.getProperty("ngus.jcr.nodeTypeConfigFile"));

		// should never happen
		if (xml == null) {
			Log.error("can not find nodeTypes.xml");
			return;
		}

		NodeTypeDef[] types;
		try {
			types = NodeTypeReader.read(xml);
		} catch (InvalidNodeTypeDefException e) {
			throw new RepositoryException(e.getMessage(), e);
		} catch (IOException e) {
			throw new RepositoryException(e.getMessage(), e);
		} finally {
			IOUtils.closeQuietly(xml);
		}

		NodeTypeManager ntMgr = (NodeTypeManager) workspace
				.getNodeTypeManager();

		// Log.trace("NodeTypeManager = " + ntMgr);

		NodeTypeRegistry ntReg = ((NodeTypeManagerImpl) ntMgr)
				.getNodeTypeRegistry();

		for (int j = 0; j < types.length; j++) {
			NodeTypeDef def = types[j];

			try {
				ntReg.getNodeTypeDef(def.getName());
			} catch (NoSuchNodeTypeException nsne) {
				Log.log("registering nodetype {}", def.getName()); //$NON-NLS-1$
				try {
					ntReg.registerNodeType(def);
				} catch (InvalidNodeTypeDefException e) {
					throw new RepositoryException(e.getMessage(), e);
				} catch (RepositoryException e) {
					throw new RepositoryException(e.getMessage(), e);
				}
			}

		}
	}

	public boolean lockNode(Node node, boolean force, boolean b1, boolean b2)
			throws Exception {
		String path = node.getPath();
		// lock it
		while (true) {
			try {
				Log.trace("Lock node " + node.getPath());
				node.lock(b1, b2);
				Log.trace("Lock node " + node.getPath() + "successfully.");
				return true;
			} catch (LockException e) {
				Log.error(e);
				try {
					Lock l = node.getLock();
					if (l != null) {
						Log.error("lock node failed, node locked by "
								+ l.getLockOwner());
					}
				} catch (Exception ee) {
					Log.error("get lock owner failed, ", ee);
				}
				if (!force)
					break;
				Log.trace("wait one millisecond and retry...");
				Thread.sleep(1);
			}
		}
		return false;
	}

	public void lockNode(String workspace, String path, boolean force,
			boolean b1, boolean b2) throws Exception {
		// get root
		Session session = getSession(workspace);
		Node root = session.getRootNode();

		// reach the node
		// Node node = createPath(root, id.getJcrPath());
		Node node = null;

		if (root.hasNode(path)) {
			node = root.getNode(path);
			lockNode(node, force, b1, b2);
		} else
			Log.error("path " + path + " not exist");

		JCRContentRepository.instance().releaseSession(session, workspace);

		Log.trace("lock node " + path + " successfully.");
		return;
	}

	public void unlockNode(Node node) throws Exception {
		node.unlock();
	}

	public void unlockNode(String workspace, String path) throws Exception {
		// get root
		Session session = getSession(workspace);
		Node root = session.getRootNode();

		// reach the node
		// Node node = createPath(root, id.getJcrPath());
		Node node = null;

		if (root.hasNode(path)) {

			node = root.getNode(path);

			unlockNode(node);
		}

		JCRContentRepository.instance().releaseSession(session, workspace);

		Log.trace("unlock node " + path + " successfully.");

		return;
	}

	public void saveSession(String workspace) throws Exception {
		Session session = getSession(workspace);
		session.save();
		JCRContentRepository.instance().releaseSession(session, workspace);
		Log.trace("save session for workspace " + workspace + " successfully.");

	}

	public void cleanup(String workspace) throws Exception {
		Session session = getSession(workspace);
		Node node = session.getRootNode();
//		lockNode(node, true, false, false);
		NodeIterator ni = node.getNodes();
		while(ni.hasNext()){
			ni.nextNode().remove();
		}
		
		node.remove();
	//	unlockNode(node);		
		session.save();
		
		releaseSession(session, workspace);
		Log.trace("save session for workspace " + workspace + " successfully.");
	} /*
		 * public void search() throws Exception { // QueryImpl query = new
		 * QueryImpl(this.getWorkspaceRoot(WORKSPACE_TREE // ).getSession(), //
		 * this.getWorkspace(WORKSPACE_TREE).getQueryManager())); }
		 */
	// private static void loadHierarchyManager(Repository repository, String
	// wspID)
	// {
	// try {
	// SimpleCredentials sc = new SimpleCredentials(UserManager.SYSTEM_USER,
	// UserManager.SYSTEM_PSWD.toCharArray());
	// Session session = repository.login(sc, wspID);
	// registerNamespace(NAMESPACE_PREFIX, NAMESPACE_URI,
	// session.getWorkspace());
	// registerNodeTypes(session.getWorkspace());
	// AccessManagerImpl accessManager = getAccessManager();
	// HierarchyManager hierarchyManager = new
	// HierarchyManager(UserManager.SYSTEM_USER);
	// hierarchyManager.init(session.getRootNode());
	// hierarchyManager.setAccessManager(accessManager);
	// ContentRepository.hierarchyManagers.put(map.getName() + "_" + wspID,
	// hierarchyManager); //$NON-NLS-1$
	//
	// try {
	// QueryManager queryManager =
	// SearchFactory.getAccessControllableQueryManager(hierarchyManager
	// .getWorkspace()
	// .getQueryManager(), accessManager);
	// hierarchyManager.setQueryManager(queryManager);
	// }
	// catch (RepositoryException e) {
	// // probably no search manager is configured for this workspace
	// log.info("QueryManager not initialized for repository " + map.getName() +
	// ": " + e.getMessage()); //$NON-NLS-1$ //$NON-NLS-2$
	// }
	// }
	// catch (RepositoryException re) {
	// log.error("System : Failed to initialize hierarchy manager for JCR - " +
	// map.getName()); //$NON-NLS-1$
	// log.error(re.getMessage(), re);
	// }
	// }
}
