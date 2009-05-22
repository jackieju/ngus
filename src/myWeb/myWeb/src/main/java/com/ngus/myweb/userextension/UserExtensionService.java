package com.ngus.myweb.userextension;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.ngus.myweb.util.DbUtils;
import com.ns.db.DB;
import com.ns.log.Log;

public class UserExtensionService {
	private static final String CREATE_TABLE = "CREATE TABLE  `userextension` (" +
			"`userName` varchar(10) NOT NULL default '0'," +
			" `score` int(10) unsigned default '0'," +
			" `userPic` blob," +
			" `widgets` mediumtext," +
			"  PRIMARY KEY  (`userName`)" +
			"  ) ENGINE=InnoDB DEFAULT CHARSET=utf8";
	//private static final String CREATE_TABLE = "CREATE TABLE userextension (userName VARCHAR(255) NOT NULL,  score INTEGER UNSIGNED NOT NULL DEFAULT 0,  userPic blob,  PRIMARY KEY(userName))ENGINE = InnoDB DEFAULT CHARSET=utf8;";
	private static final String ADD_USEREXTENSION = "INSERT INTO userextension  values (?, 0, ?, '[]')";
	private static final String SELECT_USERPIC = "SELECT userPic from userextension WHERE userName = ?;";
	private static final String UPDATE_USERPIC = "UPDATE userextension set userPic = ? WHERE userName = ?;";
	private static final String UPDATE_WIDGETS = "UPDATE userextension SET widgets = ? WHERE userName = ?;";
	private static final String SELECT_WIDGETS = "SELECT widgets from userextension WHERE userName = ?;";
//	private static final String DELETE_FRIEND = "DELETE FROM friend where userId1 = ? and userId2 = ?";
//	private static final String LIST_FRIEND = "SELECT userId2 FROM friend where userId1 = ? and status = 1";
//	private static final String CHECK_FRIEND = "SELECT userId2 FROM friend where userId1 = ? and userId2 = ? and status = 1";	
	
	private static UserExtensionService instance = new UserExtensionService();
	
	static{
		Connection con = null;
		Boolean bExists = true;
		try{
			con = DB.getConnection();
			
			//check whether the table exists
			try{
				PreparedStatement pst = con.prepareStatement("SELECT * FROM userextension");
				pst.execute();
			}
			catch(Exception e){
				bExists = false;
			}
			if(bExists == false){
				
				//create table
				try{
					PreparedStatement pst = con.prepareStatement(CREATE_TABLE);
					pst.execute();
				}
				catch(Exception e){
					Log.error(e);
				}
			}
		}
		catch(Exception e){
			Log.error(e);
		}
		finally{
			DbUtils.closeConnection(con);
		}
	}
	
		
	private UserExtensionService() {
	}
	
	public static UserExtensionService getInstance() {
		return instance;
	}
	
	public static String getWidgets(String userName){
		String widgets= "[]";
		Connection con = null;
		PreparedStatement psmt = null;
		try{
			con = DB.getConnection();			
			psmt = con.prepareStatement(SELECT_WIDGETS);
			psmt.setString(1, userName);
			ResultSet rs = psmt.executeQuery();
			if(rs!=null){
				while(rs.next()){
					widgets= rs.getString("widgets");
					if(widgets == null || widgets == "")
						widgets= "[]";
				}
			}
		}
		catch(Exception e){
			Log.error(e);
		}
		finally{
			DbUtils.closeConnection(con);
			DbUtils.closePreparedStatement(psmt);
		}
		
		return widgets;
	}
	
	public static void setWidgets(String userName, String widgets){
		Connection con= null;
		PreparedStatement psmt = null;
		if(widgets == null || widgets== "")
			widgets= "[]";
		try{
			con = DB.getConnection();			
			psmt = con.prepareStatement(UPDATE_WIDGETS);
			psmt.setString(1, widgets);
			psmt.setString(2, userName);
			psmt.execute();
			
		}
		catch(Exception e){
			Log.error(e);
		}
		finally{
			DbUtils.closeConnection(con);
			DbUtils.closePreparedStatement(psmt);
		}				
	}
	
	public static void addUserExtension(String userName, InputStream picFile , int fileSize) {
		Connection con = null;
		PreparedStatement psmt = null;
		try{
			con = DB.getConnection();			
			psmt = con.prepareStatement(ADD_USEREXTENSION);
			psmt.setString(1, userName);
			psmt.setBinaryStream(2, picFile , fileSize);
			psmt.execute();
			
		}
		catch(Exception e){
			Log.error(e);
		}
		finally{
			DbUtils.closeConnection(con);
			DbUtils.closePreparedStatement(psmt);
		}
	}
	
	public static Blob selectImg(String userName) {
		Connection con = null;
		PreparedStatement psmt = null;
		Blob img = null;
		try{
			con = DB.getConnection();			
			psmt = con.prepareStatement(SELECT_USERPIC);
			psmt.setString(1, userName);
			ResultSet rs = psmt.executeQuery();
			if(rs!=null){
				while(rs.next()){
					img = rs.getBlob(1);					
				}
			}
		}
		catch(Exception e){
			Log.error(e);
		}
		finally{
			DbUtils.closeConnection(con);
			DbUtils.closePreparedStatement(psmt);
		}
		
		return img;
	}
	
	
	public void updateImg(String userName, InputStream picFile , int fileSize){
		Connection con = null;
		PreparedStatement psmt = null;
		try{
			con = DB.getConnection();			
			psmt = con.prepareStatement(UPDATE_USERPIC);
			psmt.setBinaryStream(1, picFile , fileSize);
			psmt.setString(2, userName);
			
			psmt.execute();
			
		}
		catch(Exception e){
			Log.error(e);
		}
		finally{
			DbUtils.closeConnection(con);
			DbUtils.closePreparedStatement(psmt);
		}
	}
	
	
}
