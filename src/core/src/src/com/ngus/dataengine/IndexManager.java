package com.ngus.dataengine;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.sql.DataSource;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.ngus.config.Path;
import com.ngus.config.SystemProperty;
import com.ns.dataobject.Attribute;
import com.ns.log.Log;
import com.ns.util.XmlUtil;

public class IndexManager {

	// DBConnection dbc = null;

	HashMap<String, Index> map = new HashMap<String, Index>();

	HashMap<String, List<Index>> map_mappedName = new HashMap<String, List<Index>>();

	DataSource ds = null;

	public List<Index> getAllIndex() {
		ArrayList<Index> list = new ArrayList<Index>();
		Collection<Index> c = map.values();
		list.addAll(c);
		return list;
	}

	/**
	 * singleton logic
	 */
	static private IndexManager _instance = null;

	/**
	 * Get instance of DataMgr
	 * 
	 * @return the single instance of DataMgr, using the default config file
	 *         "ACCConfig.xml"
	 * @throws Exception
	 */
	synchronized static public IndexManager instance() throws Exception {
		if (_instance == null) {

			_instance = new IndexManager();

			_instance.init();

		}

		return _instance;
	}

	private Connection getConnection() throws Exception {
		if (ds == null)
			ds = com.ns.db.DBC.getDataSource("java:comp/env/jdbc/ngus");
		Connection c = ds.getConnection();
		if (c == null) {
			Log
					.warning("cannot get connection from data source java:comp/env/jdbc/ngus, trying to get jdbc connection");
			c = DriverManager.getConnection(SystemProperty
					.getProperty("ngus.jdbc.connectString"), SystemProperty
					.getProperty("ngus.jdbc.user"), SystemProperty
					.getProperty("ngus.jdbc.pwd"));
		}
		Log.trace("connection=" + c);
		return c;
	}

	private synchronized void addIndex(Index p) {
		map.put(p.getName().toLowerCase(), p);
		for (int k = 0; k < p.getMappedName().length; k++) {
			String name = p.getMappedName()[k].trim().toLowerCase();
			List<Index> indexes = map_mappedName.get(name);
			
			if (indexes == null) {
				indexes = new ArrayList<Index>();
				map_mappedName.put(name, indexes);
			}
			
			if (!indexes.contains(p))
				indexes.add(p);
		}
		Log.trace("add index " + p.getName());
	}

	public List<Index> getIndexByMappedName(String mappedName) {
		return this.map_mappedName.get(mappedName.toLowerCase());
	}

	public int selectCountById(Index index, String resId) throws Exception {
		String sql = index.genSQL("count(*)", "resid='" + resId + "'", null,
				true);
		Log.trace("sql: " + sql);
		Connection c = getConnection();
		Statement st = c.createStatement();
		ResultSet rs = null;
		try {
			rs = st.executeQuery(sql);
			if (rs != null && rs.next()) {
				return rs.getInt(1);
			}
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					;
				}
				rs = null;
			}
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
					c.close();
				} catch (SQLException e) {
					Log.error(e);
				}
			}
			c = null;
		}
		return 0;
	}

	// public void insertToIndex(Index index, ResDesObject rdo, Timestamp time,
	// String userId) throws Exception {
	// List<String> sqls = index.genInsertSQL(rdo, time, userId);
	// // Log.trace("sql: "+ sql);
	// int success = executeUpdate(sqls);
	// if (success < sqls.size())
	// throw new Exception("insert into index " + index.getName()
	// + " failed, " + success + " of " + sqls.size()
	// + " succeeded");
	//
	// }

	public void insertToIndex(Index index, ResourceObject ro, String app, Timestamp time)
			throws Exception {
		List<String> sqls = index.genInsertSQL(ro, app, time);
		if (sqls == null)
		{
			Log.trace("no sqls");
			return ;
		}
		// Log.trace("sql: "+ sql);
		int success = executeUpdate(sqls);
		if (success < sqls.size())
			throw new Exception("insert into index " + index.getName()
					+ " failed, " + success + " of " + sqls.size()
					+ " succeeded");

	}

	//
	// public void updateIndex(Index index, ResDesObject rdo, ResDesObject old,
	// Timestamp time, String userId) throws Exception {
	// List<String> sqls = index.genUpdateSQL(rdo, old, time, userId);
	// // Log.trace("sql: "+ sql);
	// int success = executeUpdate(sqls);
	// if (success < sqls.size())
	// throw new Exception("update index " + index.getName() + "failed");
	//
	// }

	public void updateIndex(Index index, ResourceObject rdo,
			ResourceObject old, String modelName, Timestamp time) throws Exception {
		List<String> sqls = index.genUpdateSQL(rdo, old, modelName, time);
		if (sqls == null)
		{
			Log.trace("no sqls");
			return ;
		}
		// Log.trace("sql: "+ sql);
		int success = executeUpdate(sqls);
		if (success < sqls.size())
			throw new Exception("update index " + index.getName() + "failed");

	}

	public int executeUpdate(List<String> sqls) throws Exception {
		if (sqls == null || sqls.size()==0)
		{
			Log.trace("no sql statement");
			return 0;
		}
		Connection c = getConnection();
		Statement st = null;
		int success = 0;
		int n = 0;
		try {
			for (n = 0; n < sqls.size(); n++) {
				try {
					st = c.createStatement();
					int r = st.executeUpdate(sqls.get(n));
					Log.trace("execute '" + sqls.get(n) + "'" + " return " + r);

					// Log.trace("update "+ i + " records");
					if (r < 1) {
						// throw new Exception("update index " + index.getName()
						// + "
						// failed");
						Log.error("update failed, sql=" + sqls.get(n));
						continue;
					}
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
						} catch (SQLException e) {
							Log.error(e);
						}

						st = null;
					}
					// if (c != null) {
					// try {
					// c.close();
					// } catch (SQLException e) {
					// Log.error(e);
					// }
					// }
					// c = getConnection();
				}

				success++;
			}
		} finally {
			try {
				if (c != null)
					c.close();
			} catch (SQLException e) {
				Log.error(e);
			}
		}

		Log.trace(sqls.size() + " sql stmt, " + success + " succeeded.");

		return success;
	}

	// public void deleteFromIndex(Index index, ResDesObject rdo) throws
	// Exception {
	// List<String> sqls = index.genDeleteSQL(rdo);
	// // Log.trace("sql: "+ sql);
	// int success = executeUpdate(sqls);
	// if (success < sqls.size())
	// throw new Exception("update index " + index.getName() + "failed");
	// Log.trace("delete from index " + index.getName() + ", " + success
	// + " records was deleted");
	//
	// }

	public void deleteFromIndex(Index index, String app, ResourceObject ro)
			throws Exception {
		List<String> sqls = index.genDeleteSQL(ro, app);
		if (sqls == null)
		{
			Log.trace("no sqls");
			return ;
		}
		// Log.trace("sql: "+ sql);
		int success = executeUpdate(sqls);
		if (success < sqls.size())
			throw new Exception("update index " + index.getName() + "failed");
		Log.trace("delete from index " + index.getName() + ", " + success
				+ " records was deleted");
	}

	private synchronized Index getIndex(String name) {
		return map.get(name.toLowerCase());
	}

	private boolean checkIndex(Index index) {
		Connection c = null;
		Statement st = null;

		try {
			c = getConnection();
			st = c.createStatement();
			String s = index.genSQL(null, null, null, true);
			Log.trace("sql: " + s);
			ResultSet rs = st.executeQuery(s);
			if (rs == null)
				return false;
		} catch (Exception e) {
			Log.error(e);
			return false;
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
					c.close();
				} catch (SQLException e) {
					Log.error(e);
				}
			}
			c = null;
		}

		return true;

	}

	public void createIndex(Index index) throws Exception {

		Connection c = null;
		Statement st = null;
		try {
			c = getConnection();
			st = c.createStatement();
			String s = index.genCreateSQL();
			Log.trace("sql: " + s);
			st.execute(s);
			Log.trace("create table" + index.getName());

			List<String> list = index.genCreateSQLForIndex();
			if (list != null)
			for (int i = 0; i< list.size(); i++){
				Log.trace("sql: " + list.get(i));
				st.execute(list.get(i));
			}
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
					c.close();
				} catch (SQLException e) {
					Log.error(e);
				}
			}
			c = null;
		}

		// if (i <= 0)
		// throw new Exception("create index " + index.getName() + " failed");
		Log.trace("create index for table" + index.getName());
	}

	private void buildIndex(Element ele) throws Exception {
		String indexName = ele.getAttribute("name").toLowerCase().trim();
		String mappedName = ele.getAttribute("mappedName").toLowerCase().trim();
		String mn[] = mappedName.split(",");
		String syncUpdate = ele.getAttribute("syncUpdate");
		boolean bSyncUpdate = syncUpdate.equalsIgnoreCase("true");
		Index index = new Index(indexName, mn, bSyncUpdate);

		NodeList list = ele.getElementsByTagName("field");
		for (int i = 0; i < list.getLength(); i++) {
			Element element = (Element) list.item(i);
			String fName = element.getAttribute("name").toLowerCase();
			Log.trace("field name = " + fName);
			String mapped_Name = element.getAttribute("mappedName").toLowerCase();
			Log.trace("mapped name = " + mapped_Name);
			if (mapped_Name==null || mapped_Name.trim().length()==0)
				mapped_Name="#field"+i;
			String type = element.getAttribute("type").toLowerCase();
			Log.trace("type = " + type);
			index.addField(fName, mapped_Name, type);
		}

		if (!checkIndex(index))
			createIndex(index);

		addIndex(index);

	}

	private void load(String file) throws Exception {
		Document doc = XmlUtil.loadDom(file);
		Element root = doc.getDocumentElement();
		NodeList list = root.getElementsByTagName("index");
		for (int i = 0; i < list.getLength(); i++) {
			Element ele = (Element) list.item(i);
			buildIndex(ele);
		}
	}

	private void init() throws Exception {
		Log.trace("Init index manager...");
		// try {
		// Class.forName(SystemProperty.getProperty("ngus.jdbc.driver"));
		// } catch (Exception e) {
		// throw new Exception("create db connection failed", e);
		// }
		// try {
		// dbc = new DBConnection(SystemProperty
		// .getProperty("dbc:mysql://localhost/test"), SystemProperty
		// .getProperty("ngus.jdbc.user"), SystemProperty
		// .getProperty("ngus.jdbc.pwd"), SystemProperty
		// .getProperty("ngus.jdbc.schema"));
		// } catch (Exception e) {
		// throw new Exception("create db connection failed", e);
		// }

		// load config
		load(Path.getAbsoluteFileSystemPath(SystemProperty
				.getProperty("ngus.indexManager.configFile")));

		ds = com.ns.db.DBC.getDataSource("java:comp/env/jdbc/ngus");

		Log.trace("Init ok.");
	}

	/**
	 * Query by index
	 * @param name
	 * @param where
	 * @param sortBy
	 * @param bAsc
	 * @param start		start from 0, canno be negative
	 * @param number
	 * @param total
	 * @return
	 * @throws Exception
	 */
	public List<String> QueryIndex(String name, String where, String sortBy,
			boolean bAsc, int start, int number, Attribute total)
			throws Exception {
		int count = 0;
		if (number < 0)
			number = Integer.MAX_VALUE;
		Index index = this.getIndex(name);
		if (index == null)
			throw new Exception("Index " + name + " does not exist");
		List<String> ret = new ArrayList<String>();

		Connection c = getConnection();
		Statement st = c.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);

		ResultSet rs = null;
		String s = index.genSQL(null, where, sortBy, bAsc);
		Log.trace("sql: " + s);
		try {
			rs = st.executeQuery(s);
			if (rs == null)
				return ret;

			Log.trace("number:" + number);
			while (rs.next() && number > 0) {
				Log.trace("start=" + start);
				start--;
				if (start >= 0)
					continue;
				String resId = rs.getString("resid");
				Log.trace("res id = " + resId);
				ret.add(resId);
				number--;
			}

			// get count
			if (total != null) {
				rs.last();
				count = rs.getRow();
				Log.trace("total is " + count);
				total.setValue(count);
				// rs.beforeFirst();
			}

		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					;
				}
				rs = null;
			}
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
					c.close();
				} catch (SQLException e) {
					Log.error(e);
				}
			}
			c = null;
		}

		return ret;

	}

}
