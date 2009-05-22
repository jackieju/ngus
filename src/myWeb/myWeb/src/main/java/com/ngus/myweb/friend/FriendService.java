package com.ngus.myweb.friend;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;

import com.ngus.myweb.util.DbUtils;
import com.ngus.um.IUser;
import com.ngus.um.UMClient;
import com.ngus.um.User;
import com.ns.dataobject.Attribute;
import com.ns.db.DB;
import com.ns.log.Log;

/**
 * 
 * @author Administrator
 * 
 */
public class FriendService {
	private static final String CREATE_TABLE = "CREATE TABLE friend (userId1 INTEGER UNSIGNED NOT NULL DEFAULT 0,  userId2 INTEGER UNSIGNED NOT NULL DEFAULT 0,  status SMALLINT UNSIGNED NOT NULL DEFAULT 0,  PRIMARY KEY(userId1, userId2))ENGINE = InnoDB;";
	private static final String ADD_FRIEND = "INSERT INTO friend  values (?, ?, 0)";
	private static final String CONFIRM_FRIEND = "UPDATE friend SET status = 1 where userId1 = ? and userId2 = ?";
	private static final String REJECT_FRIEND = "UPDATE friend SET status = 50 where userId1 = ? and userId2 = ?";
	private static final String DELETE_FRIEND = "DELETE FROM friend where userId1 = ? and userId2 = ?";
	private static final String LIST_FRIEND = "SELECT userId2 FROM friend where userId1 = ?";
	private static final String CHECK_FRIEND = "SELECT userId2 FROM friend where userId1 = ? and userId2 = ? and status = 1";
	
	/**
	 * Singleton
	 */
	private static FriendService instance = new FriendService();

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
				PreparedStatement pst = con.prepareStatement("SELECT * FROM friends");
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
		// create the table
	}

	/**
	 * default constructor
	 */
	private FriendService() {
	}

	/**
	 * Singleton pattern, return the single instance of this class
	 * 
	 * @return single instance of this class
	 */
	public static FriendService getInstance() {
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
	public static boolean addFriend(User user, User friend) {
		Connection con = null;
		PreparedStatement psmt = null;
		try{
			con = DB.getConnection();			
			psmt = con.prepareStatement(ADD_FRIEND);
			psmt.setInt(1, user.getUserId());
			psmt.setInt(2, friend.getUserId());
			psmt.execute();
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
	 * Confirm friend adding information
	 * 
	 * @param userId
	 * @param friendId
	 * @return true if success
	 */
	public static boolean confirmFriend(User user, User friend) {
		Connection con = null;
		PreparedStatement psmt = null;
		try{
			con = DB.getConnection();			
			psmt = con.prepareStatement(CONFIRM_FRIEND);
			psmt.setInt(1, user.getUserId());
			psmt.setInt(2, friend.getUserId());
			psmt.execute();
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
	 * Delete a friend from list
	 * @param user		
	 * @param friend
	 * @return
	 */
	public static boolean deleteFriend(User user, User friend) {
		Connection con = null;
		PreparedStatement psmt = null;
		try{
			con = DB.getConnection();			
			psmt = con.prepareStatement(DELETE_FRIEND);
			psmt.setInt(1, user.getUserId());
			psmt.setInt(2, friend.getUserId());
			psmt.execute();
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
	 * Block a friend
	 * 
	 * @param userId
	 * @param friendId
	 */
	public static boolean blockFriend(int userId, int friendId) {
		return true;
	}

	/**
	 * 
	 * @param user
	 * @param friend
	 * @return
	 */
	public static boolean rejectFriend(User user, User friend) {
		Connection con = null;
		PreparedStatement psmt = null;
		try{
			con = DB.getConnection();			
			psmt = con.prepareStatement(REJECT_FRIEND);
			psmt.setInt(1, user.getUserId());
			psmt.setInt(2, friend.getUserId());
			psmt.execute();
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
	
	public static Iterator<IUser> listFriend(IUser user){
		return listFriend(user, 0, 10, null);
	}
	/**
	 * 
	 * @param user
	 * @return
	 */
	public static Iterator<IUser> listFriend(IUser user, int start, int number, Attribute count){
		UMClient um = new UMClient();
		Connection con = null;
		PreparedStatement psmt = null;
		Iterator<IUser> iter = null;		
		try{
			ArrayList<IUser> userList = new ArrayList<IUser>();
			con = DB.getConnection();
			psmt = con.prepareStatement(LIST_FRIEND);
			psmt.setInt(1, user.getUserId());
			ResultSet rs = psmt.executeQuery();
			if(rs!=null){
				while(start-->0&&rs.next()){
					
				}
				while(rs.next()&&number-->0){
					int i = rs.getInt(1);
					userList.add(um.getUserByUserId(i));
				}
			}
			rs.last();
			int i = rs.getRow();
			iter = userList.iterator();
			if(count != null){
				count.setValue(i);
			}
		}
		catch(Exception e){
			Log.error(e);
		}
		finally{
			DbUtils.closeConnection(con);
			DbUtils.closePreparedStatement(psmt);
		}	
		return iter;
	}
	
	/**
	 * Check whether the user has added user2 as friend or not
	 * @param user		
	 * @param friend
	 * @return		
	 */
	public static boolean checkFriend(User user, User friend){
		Connection con = null;
		PreparedStatement psmt = null;
		try{
			con = DB.getConnection();			
			psmt = con.prepareStatement(CHECK_FRIEND);
			psmt.setInt(1, user.getUserId());
			psmt.setInt(2, friend.getUserId());
			ResultSet rs = psmt.executeQuery();
			if(rs!=null){
				return rs.next();
			}
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
}
