package com.ngus.myweb.vote;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.ngus.myweb.util.DbUtils;
import com.ns.db.DB;
import com.ns.log.Log;

/**
 * 
 * @author Administrator
 * 
 */
public class VoteService {
	private static final String CREATE_TABLE = "CREATE TABLE  myweb_vote (url varchar(255) NOT NULL default '',  vote bigint(20) default NULL,  PRIMARY KEY  (url)) ENGINE=MyISAM DEFAULT CHARSET=utf8";
	private static final String ADD_VOTE = "INSERT INTO myweb_vote  values (?, 1)";
	private static final String VOTE = "UPDATE myweb_vote SET vote = vote+1 where url=?";
	private static final String VIEW_VOTE = "SELECT vote from myweb_vote where url = ?";
	
	/**
	 * Singleton
	 */
	private static VoteService instance = new VoteService();

	/**
	 * create the table if not exists
	 */
	static {
		Connection con = null;
		Boolean bExists = true;
		try{
			con = DB.getConnection();
			
			//check whether the table exists
			try{
				PreparedStatement pst = con.prepareStatement("SELECT * FROM myweb_vote");
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

	/**
	 * default constructor
	 */
	private VoteService() {
	}

	/**
	 * Singleton pattern, return the single instance of this class
	 * 
	 * @return single instance of this class
	 */
	public static VoteService getInstance() {
		return instance;
	}

	/**
	 * Add an friend of a user, if friend already exists
	 * 
	 * @param userId
	 *            user who want to add a friend
	 * @param friendId
	 *            user who is added as a friend
	 * @return true if success
	 */
	public static boolean vote(String url) {
		Connection con = null;
		PreparedStatement psmt = null;
		try{
			con = DB.getConnection();
			if(viewVote(url)==0){				
				psmt = con.prepareStatement(ADD_VOTE);
				psmt.setString(1, url);	
				psmt.execute();
			}
			else{
				psmt = con.prepareStatement(VOTE);
				psmt.setString(1, url);	
				psmt.execute();
			}
			return true;
		}
		catch(Exception e){
			Log.error(e);
		}
		finally{
			DbUtils.closeConnection(con);
			DbUtils.closePreparedStatement(psmt);
		}
		
		return false;
	}

	/**
	 * 
	 * @param url
	 * @return
	 */
	public static int viewVote(String url){
		Connection con = null;
		PreparedStatement psmt = null;
		try{
			con = DB.getConnection();
			psmt = con.prepareStatement(VIEW_VOTE);
			psmt.setString(1, url);
			ResultSet rs = psmt.executeQuery();
			if(rs!=null){
				if(rs.next()){
					return rs.getInt(1);
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
		return 0;
	}
}
