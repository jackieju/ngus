package com.ngus.myweb.searchkey;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.ngus.myweb.friend.FriendService;
import com.ngus.myweb.util.DbUtils;
import com.ns.db.DB;
import com.ns.log.Log;

public class SearchKeyService {
	private static final String CREATE_TABLE = "CREATE TABLE `search_key` (`searchKey` varchar(255) default NULL,`createTime` timestamp NULL default NULL) ENGINE=MyISAM DEFAULT CHARSET=utf8;";
	private static final String LIST_POP_KEY = "SELECT searchKey from search_key where searchKey != '' group by searchKey order by count(*) desc limit 0, ? ";
	private static final String ADD_KEY ="INSERT INTO search_key  values (?, now())";
	
	private static SearchKeyService instance = new SearchKeyService();
	
	static {
		Connection con = null;
		Boolean bExists = true;
		try{
			con = DB.getConnection();
			
			//check whether the table exists
			try{
				PreparedStatement pst = con.prepareStatement("SELECT * FROM search_key");
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
	
	private SearchKeyService() {
	}
	
	public static SearchKeyService getInstance() {
		return instance;
	}
	
	private static List<String> getStringList(String searchText){
		String newSearch = searchText.trim();
		String[] stringArray = newSearch.split(" ");
		int t = stringArray.length;
		List<String> stringList = new ArrayList<String>();
		for(int i =0; i< t ; i++){
			
			stringList.add(stringArray[i]);
			stringList.remove("");
		}
		
		return stringList;
		
	}
	
	/**
	 * List search key words.
	 * 
	 * @param number 
	 *            the count of key words
	 * @return list of key words
	 */
	public static List<String> getPopKey(int number){
		Connection con = null;
		PreparedStatement psmt = null;
		List<String> keylist = new ArrayList<String>();
		try{
			con = DB.getConnection();
			psmt = con.prepareStatement(LIST_POP_KEY);
			psmt.setInt(1, number);
			ResultSet rs = psmt.executeQuery();
			while(rs.next()){
				String key = rs.getString(1);
				keylist.add(key);
			}
		}catch(Exception e){
			Log.error(e);
		}finally{
			DbUtils.closeConnection(con);
			DbUtils.closePreparedStatement(psmt);
		}
		return keylist;
	}
	
	/**
	 * Add a search key 
	 * 
	 * @param key
	 *            the key word which user searched
	 */
	public static void addKey(String key){
		Connection con = null;
		PreparedStatement psmt = null;
		try{
			con = DB.getConnection();			
			psmt = con.prepareStatement(ADD_KEY);
			List<String> keylist = getStringList(key);
			int len = keylist.size();
			for(int i =0;i<len;i++){
				psmt.setString(1,keylist.get(i));
				psmt.executeUpdate();
			}
		}catch(Exception e){
			Log.error(e);
		}finally{
			DbUtils.closeConnection(con);
			DbUtils.closePreparedStatement(psmt);
		}
	}
	
}
