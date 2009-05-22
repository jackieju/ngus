package com.ngus.myweb.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.mysql.jdbc.Statement;
import com.ns.log.Log;

public class DbUtils {

	/**
	 * 
	 * @param con
	 */
	public static void closeConnection(Connection con){
		if(con!=null){
			try{
				con.close();
			}
			catch(Exception e){
				Log.error(e);
			}
		}
	}
	
	/**
	 * 
	 * @param rs
	 */
	public static void closeResultSet(ResultSet rs){
		if(rs!=null){
			try{
				rs.close();
			}
			catch(Exception e){
				Log.error(e);
			}
		}
	}
	
	/**
	 * 
	 * @param st
	 */
	public static void closeStatement(Statement st){
		if(st!=null){
			try{
				st.close();
			}
			catch(Exception e){
				Log.error(e);
			}
		}
	}
	public static void closePreparedStatement(PreparedStatement st){
		if(st!=null){
			try{
				st.close();
			}
			catch(Exception e){
				Log.error(e);
			}
		}
	}
}
