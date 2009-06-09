package com.ngus.dataengine;

//import java.sql.Connection;
import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;

import com.hp.hpl.jena.db.DBConnection;
import com.hp.hpl.jena.db.ModelRDB;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.ngus.config.SystemProperty;
import com.ns.log.Log;

public class RDFEngine {
	// DBConnection dbc = null;

	Model ngusModel;

	Model psModel;

	Model userAppModel;

	final static int PROPERTY_TYPE_LIST = 1;

	final static int PROPERTY_TYPE_STRING = 0;

	private HashMap<String, Model> modelPool = new HashMap<String, Model>();

	public DBConnection getDbc() throws Exception {
		// return dbc;
		DBConnection dbc = null;
		Connection c = null;

		try {

//			Log.trace("get db connection from data source java:comp/env/jdbc/rdf");
//			Connection c = com.ns.db.DBC.getDataSource(
//			"java:comp/env/jdbc/rdf").getConnection();
			
//			Log.trace("c:"+c);
//			dbc = new DBConnection(c, SystemProperty  
//					.getProperty("ngus.jdbc.dbType"));			

			if (dbc == null) {
				Log
						.warning("cannot get connection from data source java:comp/env/jdbc/ngus, trying to get jdbc connection");
				dbc = new DBConnection(SystemProperty
						.getProperty("ngus.jdbc.connectString"), SystemProperty
						.getProperty("ngus.jdbc.user"), SystemProperty
						.getProperty("ngus.jdbc.pwd"), SystemProperty
						.getProperty("ngus.jdbc.dbType"));
				c = dbc.getConnection();
			}
			Log.trace("dbconnection=" + dbc + ", connection="+c +", "+dbc.getConnection());			
			
			java.sql.Statement st = c.createStatement();
			int b = st.executeUpdate("SET AUTOCOMMIT=0;");
			Log.trace("set autocommit=0 return " + b);	
			c.setAutoCommit(false);
			st.close();
			Log.trace("autocommit="+dbc.getConnection().getAutoCommit());
		} catch (Exception e) {
			throw new DEException("create db connection failed", e);
		}
		return dbc;
	}

	public Model getNgusModel() {
		return ngusModel;
	}

	public Model getPsModel() {
		return psModel;
	}

	public Model getUserAppModel() {
		return userAppModel;
	}

	/**
	 * generate resource id
	 */
	synchronized public long genResourceId(StringBuffer resid) throws Exception {
		Log.trace("enter");
		// now it's a very triggy method
		// return "http://gnus/" + "jackie" + "#" + count++; // for test

		// create uuid
		String uuid = UUID.randomUUID().toString();
		long id = 0;
		java.sql.Connection c = null;
		java.sql.Statement st = null;
		try {
			// a auto-creamental id;
			//c = com.ns.db.DBC.getConnection("java:comp/env/jdbc/ngus");
			c = com.ngus.dataengine.DBConnection.getConnection();
			c.setAutoCommit(true);
			st = c.createStatement();

			String sql_getId = "select max(id) from resourceid";
			java.sql.ResultSet result = st.executeQuery(sql_getId);
			if (result != null && result.next())
				id = result.getLong(1) + 1;
			else
				throw new Exception("db failure, get resource id failed");

			resid.append("#" + id); // append to complete resid
			String sql_insert = "insert into resourceid values(null, '" + uuid
					+ "', '" + resid + "')";
			st.executeUpdate(sql_insert);

			//c.commit();
		} catch (Exception e) {
			Log.error("create resource id failed" + e);
			throw new Exception("create resource id failed", e);
		} finally {
			if (st != null) {
				try {
					st.close();
				} catch (SQLException e) {
					Log.error(e);
				}

				st = null;
			}
			if (c != null) {
				try {
				//	c.rollback();
					c.close();
				} catch (SQLException e) {
					Log.error(e);
				}
			}
			c = null;

		}

		Log.log("resource id = " + id);
		return id;

	}

	public void init() throws Exception {
		Log.trace("Init...");
		try {
			Class.forName(SystemProperty.getProperty("ngus.jdbc.driver"));
			// Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception e) {
			throw new DEException("create db connection failed", e);
		}
		// try {
		// dbc = new DBConnection(SystemProperty
		// .getProperty("ngus.jdbc.connectString"), SystemProperty
		// .getProperty("ngus.jdbc.user"), SystemProperty
		// .getProperty("ngus.jdbc.pwd"), SystemProperty
		// .getProperty("ngus.jdbc.dbType"));
		// } catch (Exception e) {
		// throw new DEException("create db connection failed", e);
		// }

		// create default model
		ngusModel = this.openModule(IDataEngine.MODEL_DEFAULT, true); // default

		// model
		psModel = this.openModule(IDataEngine.MODEL_PS, true);
		userAppModel = this.openModule(IDataEngine.MODEL_USERAPP, true);

		Log.trace("Init ok.");
	}

	static public Model openModule(String modelName, boolean create)
			throws Exception {

		Model model = null;

		// get from pool
		if (modelName != null)
			model = RDFEngine.instance().modelPool.get(modelName);
		else
			model = RDFEngine.instance().modelPool.get("[default]");
		if (model != null)
			return model;

		// model not in pool, open it from db
		Log.trace("model not in pool, open it from db");
		DBConnection dbc = RDFEngine.instance().getDbc();
		Log.trace("dbc:" +dbc);
		try {

			if (modelName != null) {
				model = ModelRDB.open(dbc, modelName);
				RDFEngine.instance().modelPool.put(modelName, model);
			} else {
				model = ModelRDB.open(dbc);
				RDFEngine.instance().modelPool.put("[default]", model);
			}

		} catch (com.hp.hpl.jena.shared.DoesNotExistException e) {

			if (create) {
				Log.trace("model " + modelName
						+ " does not exist, create model.");
				if (modelName != null)
					model = ModelRDB.createModel(dbc, modelName);
				else
					model = ModelRDB.createModel(dbc);				
			} else
				throw new DEException("modle " + modelName + " dose not exist",
						e);
		} catch (Exception e) {
			Log.error(e);
			dbc.close();
			return null;
		}
		
		// it seems that the model.open will reset autocommit to true... 
		((ModelRDB)model).getConnection().getConnection().setAutoCommit(false);
		Log.trace("model " +  modelName + " autoCommit = " +((ModelRDB)model).getConnection().getConnection().getAutoCommit());
		return model;
	}

	static public void closeModel(Model model)
			throws Exception {
		
	}

	/**
	 * singleton logic
	 */
	static private RDFEngine _instance = null;

	/**
	 * Get instance of DataMgr
	 * 
	 * @return the single instance of DataMgr, using the default config file
	 *         "ACCConfig.xml"
	 * @throws Exception
	 */
	synchronized static public RDFEngine instance() throws Exception {
		if (_instance == null) {

			_instance = new RDFEngine();

			_instance.init();

		}

		return _instance;
	}

	/**
	 * destroy
	 * 
	 * @throws Exception
	 */
	static synchronized public void destroy() throws Throwable {
		// ngusModel.close();
		Log.log("Destroying RDFEngine...");
		if (_instance != null) {
			if (_instance.ngusModel instanceof ModelRDB) {
				((ModelRDB) _instance.ngusModel).getConnection().close();
			}
			_instance.ngusModel.close();
			if (_instance.psModel instanceof ModelRDB) {
				((ModelRDB) _instance.psModel).getConnection().close();
			}
			_instance.psModel.close();
			if (_instance.userAppModel instanceof ModelRDB) {
				((ModelRDB) _instance.userAppModel).getConnection().close();
			}
			_instance.userAppModel.close();
			_instance = null;

		}

		Iterator<String> it = instance().modelPool.keySet().iterator();
		while (it.hasNext()) {
			String s = it.next();
			instance().modelPool.get(s).close();
			instance().modelPool.remove(s);
		}

		Log.log("Destroying RDFEngine successfully.");
	}

	public int getPropertyType(String model, String propertyName) {
		if (propertyName.equalsIgnoreCase("tag")) {
			return PROPERTY_TYPE_LIST;
		}
		return 0;
	}

	public String getXMLOfModel(String modelName) throws Exception {
		Model model = RDFEngine.openModule(modelName, false);
		// StringWriter sw = new StringWriter();
		StringBuffer str = new StringBuffer();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Log.trace("getXMLOfModel");
		model.write(baos);
		model.write(System.out);
//		model.close();
		RDFEngine.closeModel(model);
		return baos.toString("UTF-8");
	}

	public void createModel(String modelName) throws Exception {
		modelName = modelName.toLowerCase();
//		RDFEngine.openModule(modelName, true).close();
		Model model = RDFEngine.openModule(modelName, true);
		RDFEngine.closeModel(model);
	}

	/*
	 * public Resource getResource(String Id) throws Exception { Model model =
	 * RDFEngine.openModule(IDataEngine.MODEL_DEFAULT, false); if (model ==
	 * null) { Log.error("can not open model " + IDataEngine.MODEL_DEFAULT);
	 * return null; } Resource res = model.getResource(Id); //model.close();
	 * return res; }
	 * 
	 * public Resource getResource(String Id, String modelName) throws Exception {
	 * Model model = RDFEngine.openModule(modelName, false); if (model == null) {
	 * Log.error("can not open model " + modelName); return null; } Resource res =
	 * model.getResource(Id); //model.close(); return res; }
	 * 
	 */

	public void updateResDesUpdateTime(Resource res, Model model)
			throws Exception {
		Model ngusModel = RDFEngine.instance().getNgusModel();
		Timestamp tm = new Timestamp(System.currentTimeMillis());
		Statement stmt = res.getProperty(model.createProperty(this
				.getModelDesc(""), IDataEngine.ATTR_UPDATETIME));
		if (stmt != null)
			stmt.changeObject(tm);
		else
			res.addProperty(ngusModel.createProperty(this.getModelDesc(""),
					IDataEngine.ATTR_UPDATETIME), tm);
	}

	/**
	 * get model dictionary
	 * 
	 * @param modelName
	 * @return
	 */
	public String getModelDesc(String modelName) {
		modelName = modelName.toLowerCase();
		// return "http://www.w3.org/2001/vcard-rdf/3.0#"; // for test
		return "http://nugs/" + modelName + "#";
	}
	
	public static void main(String v[]) throws Exception{
		Class.forName("org.gjt.mm.mysql.Driver").newInstance(); 
		String url ="jdbc:mysql://211.144.92.96/test?user=root&password=ft05ab"; 
		Connection conn= DriverManager.getConnection(url);
		conn.setAutoCommit(false);
		System.out.println(conn.getAutoCommit());
	}
}
