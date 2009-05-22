package com.ngus.dataengine;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.sql.DataSource;

import com.ngus.config.SystemProperty;
import com.ns.log.Log;

public class DBConnection {
	private static DataSource ds;
	static public final String DS_NAME="java:comp/env/jdbc/ngus";
	
	synchronized static public Connection getConnection () throws Exception{	
	
	if (ds == null)
		ds = com.ns.db.DBC.getDataSource(DS_NAME);
	Log.trace("data source:"+ds);
	
	Connection c = ds.getConnection();
	Log.trace("Connection:"+c);
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
	
	synchronized static public DataSource getDataSource() throws Exception{
		if (ds == null)
			ds = com.ns.db.DBC.getDataSource(DS_NAME);
		return ds;
		
	}
}
