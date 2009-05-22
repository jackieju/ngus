package com.ns.db.stat;

import java.sql.Connection;
import java.sql.Statement;

import com.ns.db.DB;
import com.ns.db.DBC;
import com.ns.log.Log;

/**
 * table definition for static table object
 * 
 * @author jackie
 * 
 */
public class TableDef {
	static final public String var_tablename = "#TABLENAME";

	public String TABLENAME = "_TABLENAME"; // this table name must be set in
											// subclass's ctor

	public String CREATE_SQL = "";

	public String CHECKTABLE_SQL = "";

	public String[] CREATEINDEX_SQL = null;

	String mappedName;

	public String getMappedName() {
		return mappedName;
	}

	public void setMappedName(String mappedName) {
		this.mappedName = mappedName;
	}

	public TableDef(String name, String mappedName) {
		this.TABLENAME = name;
		this.mappedName = mappedName;
	}

	public String genCreateSQL() throws Exception {
		if (CREATE_SQL != null && CREATE_SQL.length() > 0)
			return CREATE_SQL.replaceAll(var_tablename, getTABLENAME());
		else
			return null;
	}

	// public String getCHECKTABLE_SQL() {
	// return CHECKTABLE_SQL;
	// }

	public void setCHECKTABLE_SQL(String checktable_sql) {
		CHECKTABLE_SQL = checktable_sql;
	}

	// public String getCREATE_SQL() {
	// return CREATE_SQL;
	// }

	public void setCREATE_SQL(String create_sql) {
		CREATE_SQL = create_sql;
	}

	public String[] getCREATEINDEX_SQL() {
		return CREATEINDEX_SQL;
	}

	public void setCREATEINDEX_SQL(String[] createindex_sql) {
		CREATEINDEX_SQL = createindex_sql;
	}

	public String getTABLENAME() {
		return TABLENAME;
	}

	public void setTABLENAME(String tablename) {
		TABLENAME = tablename;
	}

	public String genCheckSQL() {
		return CHECKTABLE_SQL.replaceAll("#TABLENAME", getTABLENAME());

	}

	static public Connection getDBC() throws Exception {
		return DB.getConnection();
	}

	public void createTable() throws Exception {

		Connection c = null;
		Statement st = null;
		try {
			c = getDBC();
			st = c.createStatement();
			// create table
			String s = genCreateSQL();
			Log.trace("sql: " + s);
			st.execute(s);
			Log.trace("create table " + getTABLENAME() + " successfully.");

			// create index
			
			for (int i = 0; i < CREATEINDEX_SQL.length; i++) {
				s = CREATEINDEX_SQL[i]
						.replaceAll(var_tablename, getTABLENAME());
				Log.trace("sql: " + s);
				try{
				if (st.execute(s))
					Log.trace("create index for table " + getTABLENAME()
							+ " successfully.");
				}catch (Exception e){
					Log.error(e);
				}
			}
			

		} finally {
			DBC.closeStatement(st);
			DBC.closeConnection(c);
		}

		// if (i <= 0)
		// throw new Exception("create index " + index.getName() + " failed");
		Log.trace("create table " + getTABLENAME() + " OK.");
	}

	/**
	 * check whether table exists, create the table if crate==true;
	 * 
	 * @param create
	 */
	public void checkTable(boolean create) throws Exception {
		Statement st = null;
		Connection dbc = null;
		try {
			dbc = getDBC();
			st = dbc.createStatement();
			// String sql = genCheckSQL();
			//		
			// Log.trace(sql);
			// boolean exist = true;
			// try{
			// ResultSet rs = st.executeQuery(sql);
			// Log.trace("Table " + getTABLENAME() + " exists");
			// }catch(Exception e)
			// {
			// Log.error(e);
			// exist = false;
			// }
			//		
			// if (!exist){
			// Log.trace("Table " + getTABLENAME() + " not exists,
			// creating...");
			createTable();
			Log.trace("Crate table " + getTABLENAME() + "OK.");
			// }
		} finally {
			DBC.closeStatement(st);
			DBC.closeConnection(dbc);
		}

	}

}
