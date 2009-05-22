package com.ngus.um.dbobject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.ngus.um.IUMClient;
import com.ns.db.DBC;
import com.ns.db.stat.DBObject;
import com.ns.db.stat.TableDef;
import com.ns.exception.NSException;
import com.ns.log.Log;
import com.ns.util.DateTime;
import com.ns.util.Security;

public class UserInfo extends DBObject {
	{
		// TABLENAME = "umj_userinfo";
	}

	// public final String CREATE_SQL = "create table $TABLENAME (id bigint
	// unsigned auto_increment primary key, username char(30), password
	// VARBINARY(100), sex int(1), nickname char(30), email char(50),mobile
	// char(20), status int(1), pwd varbinary(30), createtime timestamp NOT
	// NULL, updatetime timestamp , lastLogonTime timestamp)";
	//
	// public final String CHECKTABLE_SQL = "select * from $TABLENAME";
	//
	// public String[] CREATEINDEX_SQL = null;

	static TableDef tableDef = new UserInfoTable();

	public static TableDef getTableDef() {
		return tableDef;
	}

	// data member
	private int id = -1;

	private String userName = null;

	private int sex = -1;

	private String nickName = null;

	private String email = null;

	private String mobile = null;

	private int status = -1;

	private byte[] shadowedPwd = null; // shadowed password

	private Timestamp insertTime = null;

	private Timestamp updateTime = null;

	private Timestamp lastLogonTime = null;

	// constructor
	public UserInfo() {

	};

	public UserInfo(int id, String userName, int sex, String nickName,
			String email, String mobile, int status, Timestamp creatTime,
			Timestamp updateTime, Timestamp lastLogonTime) {
		this.id = id;
		this.userName = userName;
		this.sex = sex;
		this.nickName = nickName;
		this.mobile = mobile;
		this.status = status;
		this.email = email;
		this.insertTime = creatTime;
		this.lastLogonTime = lastLogonTime;
		this.updateTime = lastLogonTime;
	}

	// getter and setter

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	//
	// public static String getCREATE_SQL() {
	// return CREATE_SQL;
	// }
	//
	// public static String getTABLENAME() {
	// return TABLENAME;
	// }

	public Timestamp getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Timestamp insertTime) {
		this.insertTime = insertTime;
	}

	public Timestamp getLastLogonTime() {
		return lastLogonTime;
	}

	public void setLastLogonTime(Timestamp lastLogonTime) {
		this.lastLogonTime = lastLogonTime;
	}

	public byte[] getShadowedPwd() {
		return shadowedPwd;
	}

	public void setShadowedPwd(byte[] shadowedPwd) {
		this.shadowedPwd = shadowedPwd;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	
	public void setPwd(String pwd) throws NSException{
		try{
		this.shadowedPwd = Security.MD5_Digest(pwd);
		}catch(Exception e){
			throw new NSException(e);
		}
	}
	
	static public UserInfo selectByUserName(String userName) throws NSException {
		Connection c = null;
		Statement st = null;
		String sql = null;

		try {
			c = tableDef.getDBC();
			st = c.createStatement();
			sql = "select id, sex, nickname, email, mobile, status, createtime, updatetime, lastlogontime, password from "
					+ tableDef.getTABLENAME()
					+ " where username='"
					+ userName
					+ "'";
			Log.trace("umj", sql);
			ResultSet result = st.executeQuery(sql);
			if (result != null) {
				while (result.next()) {
					int id = result.getInt(1);
					int sex = result.getInt(2);
					String nickname = result.getString(3);
					String email = result.getString(4);
					String mobile = result.getString(5);
					int status = result.getInt(6);
					Timestamp createTime = result.getTimestamp(7);
					Timestamp updateTime = result.getTimestamp(8);
					Timestamp logonTime = result.getTimestamp(9);
					byte[] pwd = result.getBytes(10);
					UserInfo userInfo = new UserInfo(id, userName, sex,
							nickname, email, mobile, status, createTime,
							updateTime, logonTime);
					userInfo.setShadowedPwd(pwd);
					return userInfo;
				}
			}
		} catch (Exception e) {
			throw new NSException(IUMClient.ERR_DB_ERR, e);
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
		return null;
	}

	
	static public UserInfo selectByUserId(int userId) throws NSException {
		Connection c = null;
		Statement st = null;
		String sql = null;

		try {
			c = tableDef.getDBC();
			st = c.createStatement();
			sql = "select username, sex, nickname, email, mobile, status, createtime, updatetime, lastlogontime, password from "
					+ tableDef.getTABLENAME()
					+ " where id="
					+ userId
					+ "";
			Log.trace("umj", sql);
			ResultSet result = st.executeQuery(sql);
			if (result != null) {
				while (result.next()) {
					String userName = result.getString(1);
					int sex = result.getInt(2);
					
					String nickname = result.getString(3);
					String email = result.getString(4);
					String mobile = result.getString(5);
					int status = result.getInt(6);
					Timestamp createTime = result.getTimestamp(7);
					Timestamp updateTime = result.getTimestamp(8);
					Timestamp logonTime = result.getTimestamp(9);
					byte[] pwd = result.getBytes(10);
					UserInfo userInfo = new UserInfo(userId, userName, sex,
							nickname, email, mobile, status, createTime,
							updateTime, logonTime);
					userInfo.setShadowedPwd(pwd);
					return userInfo;
				}
			}
		} catch (Exception e) {
			throw new NSException(IUMClient.ERR_DB_ERR, e);
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
		return null;
	}

	static public void updateLogonTimeByUserId(int userId, Timestamp tm)
			throws NSException {
		Connection c = null;
		PreparedStatement st = null;
		String sql = null;

		try {
			c = tableDef.getDBC();
			
			sql = "update " + tableDef.getTABLENAME() + " set lastLogonTime=? where id=" + userId + "";
			st = c.prepareStatement(sql);
			st.setTimestamp(1, tm);
			int result = st.executeUpdate();
			if (result == 1) {
				Log.trace("umj", "update password successfully");
			}
		} catch (Exception e) {
			throw new NSException(IUMClient.ERR_DB_ERR, e);
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
		return;
	}

	static public void updatePwdByUserId(int userId, byte[] shadowPwd)
			throws NSException {
		Connection c = null;
		PreparedStatement st = null;
		String sql = null;

		try {
			c = tableDef.getDBC();			
			sql = "update  " + tableDef.getTABLENAME() + " set password=?, updatetime=? where id=" + userId + "";
			st = c.prepareStatement(sql);			
			st.setBytes(1, shadowPwd);
			st.setTimestamp(2, DateTime.currentUTCTimeStamp());
			int result = st.executeUpdate();
			if (result == 1) {
				Log.trace("umj", "update password successfully");
			}
		} catch (Exception e) {
			throw new NSException(IUMClient.ERR_DB_ERR, e);
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
		return;
	}

	static public void updatePwdByUserName(String userName, byte[] shadowedPwd)
			throws NSException {
		Connection c = null;
		PreparedStatement st = null;
		String sql = null;

		try {
			c = tableDef.getDBC();
			sql = "update " + tableDef.getTABLENAME()
					+ " set password=?, updatetime=? where username='" + userName + "'";
			st = c.prepareStatement(sql);
			st.setBytes(1, shadowedPwd);
			st.setTimestamp(2, DateTime.currentUTCTimeStamp());
			Log.trace("umj", sql);
			int result = st.executeUpdate();
			if (result == 1) {
				Log.trace("umj", "update password successfully");
			}
		} catch (Exception e) {
			throw new NSException(IUMClient.ERR_DB_ERR, e);
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
		return;
	}

	/**
	 * 
	 * @param userName
	 * @param nickName
	 * @param email
	 * @return 0: no duplicate ERR_DUP_USERNAME: userName exist
	 *         ERR_DUP_NICKNAME: nickname exist ERR_DUP_EMAIL: email exist
	 */
	static public int checkDuplicate(String userName, String nickName,
			String email) throws NSException {
		Connection c = null;
		Statement st = null;
		String sql = null;
		
		if (userName == null && nickName== null && email == null )
			return 0;

		try {
			c = tableDef.getDBC();
			st = c.createStatement();
			sql = "select username, nickname, email from "
					+ tableDef.getTABLENAME()+ " where ";
			boolean OR = false;
			if (userName != null && userName.length() > 0)
			{
				sql += " username='" + userName + "'"; 
				OR = true;
			}
			if (nickName != null && nickName.length() > 0)
			{
				if (OR)
					sql += " or ";
				sql += " nickname='" + nickName + "'"; 
				OR = true;
			}
			if (nickName != null && nickName.length() > 0)
			{
				if (OR)
					sql += " or ";
				sql += " email='" + email + "'"; 
				OR = true;
			}
				
			Log.trace("umj", sql);
			ResultSet result = st.executeQuery(sql);
			if (result != null) {
				while (result.next()) {
					String u = result.getString(1);
					String n = result.getString(2);
					String e = result.getString(3);

					if (userName != null && userName.length() > 0 && userName.equalsIgnoreCase(u))
						return IUMClient.ERR_DUP_USERNAME;
					if (nickName != null && nickName.length() > 0 && nickName.equalsIgnoreCase(n))
						return IUMClient.ERR_DUP_NICKNAME;
					if (email != null && email.length() > 0 && email.equalsIgnoreCase(e))
						return IUMClient.ERR_DUP_EMAIL;

				}
			}
			return 0;
		} catch (Exception e) {
			Log.error(e);
			throw new NSException(e);
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

	}

	static public void persist(UserInfo s, boolean update) throws NSException {
		Connection c = null;
		PreparedStatement st = null;
		String sql = null;

		try {
			c = tableDef.getDBC();

			if (update) {
				sql = "update  " + tableDef.getTABLENAME()
						+ " set  updatetime=? ";
				String v = s.getEmail();
				if (v != null && v.length() > 0)
					sql += ", email='" + v + "'";

				v = s.getMobile();
				if (v != null && v.length() > 0)
					sql += ", mobile='" + v + "'";
				
				if(s.getSex() != -1){
					sql+= ", sex="+ s.getSex();
				}
	
				
				sql += " where id=" + s.getId();
				st = c.prepareStatement(sql);
				st.setTimestamp(1, DateTime.currentUTCTimeStamp());
			} else {
				if (s.getUserName() == null || s.getUserName().length() == 0 || s.getNickName() == null || s.getNickName().length() == 0)
					throw new NSException("username or nickname cannot be empty");
				sql = "insert into "
						+ tableDef.getTABLENAME()
						+ "(username, password, nickname, sex, email, mobile, status, createTime, updatetime, lastlogontime) values ('"
						+ s.getUserName() + "', ?, '" + s.getNickName() + "', "
						+ s.getSex() + ", '" + s.getEmail() + "', '"
						+ s.getMobile() + "', " + s.getStatus()
						+ ", ?, null, null)";
				st = c.prepareStatement(sql);
				st.setBytes(1, s.getShadowedPwd());
				st.setTimestamp(2, DateTime.currentUTCTimeStamp());
			}

			Log.trace("umj", "sql=" + sql);

			int result = st.executeUpdate();
			if (result == 1) {
				Log.trace("umj", "execute update " + result + " row successfully.");
			}
		} catch (Exception e) {
			throw new NSException(IUMClient.ERR_DB_ERR, e);
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
	}
	
	public static List<UserInfo> getNewUser(int number){
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<UserInfo> arr = new ArrayList<UserInfo>();
		try{
			con = TableDef.getDBC();
			ps = con.prepareStatement("select id, username, sex, nickname, email, mobile, status, createtime, updatetime, lastlogontime, password from "
					+ tableDef.getTABLENAME()
					+ " order by createtime limit 0, ?");
			ps.setInt(1, number);			
			rs = ps.executeQuery();
			if(rs!=null){
				while(rs.next()){
					int id = rs.getInt(1);
					String userName = rs.getString(2);
					int sex = rs.getInt(3);					
					String nickname = rs.getString(4);
					String email = rs.getString(5);
					String mobile = rs.getString(6);
					int status = rs.getInt(7);
					Timestamp createTime = rs.getTimestamp(8);
					Timestamp updateTime = rs.getTimestamp(9);
					Timestamp logonTime = rs.getTimestamp(10);
					//byte[] pwd = rs.getBytes(11);
					UserInfo userInfo = new UserInfo(id, userName, sex,
							nickname, email, mobile, status, createTime,
							updateTime, logonTime);
					//userInfo.setShadowedPwd(pwd);
					arr.add(userInfo);					
				}
			}
		}
		catch(Exception e){
			Log.error(e);
		}		
		finally{
			DBC.closeConnection(con);
			DBC.closePreparedStatement(ps);
			DBC.closeResultSet(rs);
		}
		return arr;
	}
	
	public static List<UserInfo> getHotUser(int number){
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<UserInfo> arr = new ArrayList<UserInfo>();
		try{
			con = TableDef.getDBC();
			ps = con.prepareStatement("select id, username, sex, nickname, email, mobile, status, createtime, updatetime, lastlogontime, password from "
					+ tableDef.getTABLENAME()+" order by lastlogontime desc limit 0, ?");
			ps.setInt(1, number);			
			rs = ps.executeQuery();
			if(rs!=null){
				while(rs.next()){
					int id = rs.getInt(1);
					String userName = rs.getString(2);
					int sex = rs.getInt(3);					
					String nickname = rs.getString(4);
					String email = rs.getString(5);
					String mobile = rs.getString(6);
					int status = rs.getInt(7);
					Timestamp createTime = rs.getTimestamp(8);
					Timestamp updateTime = rs.getTimestamp(9);
					Timestamp logonTime = rs.getTimestamp(10);
					//byte[] pwd = rs.getBytes(11);
					UserInfo userInfo = new UserInfo(id, userName, sex,
							nickname, email, mobile, status, createTime,
							updateTime, logonTime);
					//userInfo.setShadowedPwd(pwd);
					arr.add(userInfo);					
				}
			}
		}
		catch(Exception e){
			Log.error(e);
		}		
		finally{
			DBC.closeConnection(con);
			DBC.closePreparedStatement(ps);
			DBC.closeResultSet(rs);
		}
		return arr;
	}
}
