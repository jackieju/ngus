package com.ns.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import com.ns.log.Log;

public class DB {
	static private DataSource ds = null;
	public static void initialize(DataSource s){
		Log.event("db initialized with data source: "+ s);
		ds = s;
	}
	public static Connection getConnection() throws Exception{
		return ds.getConnection();
	}
	public static DataSource getDataSource(){
		return ds;
	}
	
	static public void checkTable(Connection c, String tableName, String[] create){
//		Connection c = null;
		Statement st = null;
		Statement st2 = null;
		try {
			Log.log("checking table " + tableName + "...");

//			c = DBC.getConnection();
			st = c.createStatement();
			st2 = c.createStatement();
			
			// check table 
			String sql = "select * from " + tableName + " limit 1";
			boolean bExist = true;
			try {
				ResultSet rs = st.executeQuery(sql);
				bExist = true;
				Log.log("table " + tableName + " exists.");
			} catch (Exception e) {
				bExist = false;
				Log.log("table " + tableName + " dosn't exist.");
			}
			
			// if (rs == null || !rs.next()){
			if (!bExist) {

				// create table
				Log.log("create table "+tableName+"...");
				for (int i=0; i< create.length; i++){
					sql = create[i];
					Log.trace(sql);
					st2.executeUpdate(sql);
				}
		}
			
			
		} catch (Exception ee) {
			Log.error("", ee);
		} finally {
			if (st != null) {
				try {
					st.close();
				} catch (SQLException e) {
					Log.error("", e);
				}

				st = null;
			}
			if (st2 != null) {
				try {
					st2.close();
				} catch (SQLException e) {
					Log.error("daemon.webpage", "", e);
				}

				st2 = null;
			}

			try {
				if (c != null)
					c.close();
			} catch (SQLException e) {
				Log.error("daemon.webpage", "", e);
			}
		}
	}

}
