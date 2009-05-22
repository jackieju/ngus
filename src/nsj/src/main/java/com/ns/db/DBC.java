package com.ns.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.ns.log.Log;

public class DBC {

	static public Connection getConnection(String lookupString)
			throws Exception {
		/*
		 * Context ctx = new InitialContext(); if(ctx == null ) throw new
		 * Exception("Boom - No Context");
		 * 	
		 * //DataSource ds =
		 * (DataSource)ctx.lookup("java:comp/env/jdbc/TestDB"); DataSource ds =
		 * (DataSource)ctx.lookup(lookupString);
		 */
		DataSource ds = getDataSource(lookupString);
		if (ds == null)
			throw new Exception("can not found data source " + lookupString);
		else {
			Connection conn = ds.getConnection();
			Log.trace("connection=" + conn);
			return conn;
		}
	}

	static public DataSource getDataSource(String lookupString)
			throws Exception {
		Context ctx = new InitialContext();
		if (ctx == null)
			throw new Exception("Boom - No Context");

		// DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/TestDB");
		DataSource ds = (DataSource) ctx.lookup(lookupString);
		return ds;
	}

	/**
	 * close database connection
	 * 
	 * @param con
	 */
	public static void closeConnection(Connection con) {
		if (con != null) {
			try {
				con.close();
			} catch (Exception e) {
				Log.error(e);
			}
		}
	}

	/**
	 * 
	 * @param rs
	 */
	public static void closeResultSet(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (Exception e) {
				Log.error(e);
			}
		}
	}

	/**
	 * 
	 * @param st
	 */
	public static void closeStatement(Statement st) {
		if (st != null) {
			try {
				st.close();
			} catch (Exception e) {
				Log.error(e);
			}
		}
	}

	public static void closePreparedStatement(PreparedStatement st) {
		if (st != null) {
			try {
				st.close();
			} catch (Exception e) {
				Log.error(e);
			}
		}
	}
}
