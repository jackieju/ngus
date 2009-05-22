package com.ngus.um.dbobject;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import com.ngus.um.IUMClient;
import com.ns.db.stat.DBObject;
import com.ns.db.stat.TableDef;
import com.ns.exception.NSException;
import com.ns.log.Log;
import com.ns.util.DateTime;
import com.ns.util.IO;

public class Session extends DBObject implements Serializable{
//	{
//		super.TABLENAME = "umj_session";
//	}

//	public final String CREATE_SQL = "create table $TABLENAME"
//			+ "(id char(100)primary key, data varbinary(1000), createtime timestamp NOT NULL )";

//	public final String CHECKTABLE_SQL = "select * from $TABLENAME";

	static SessionTable tableDef = new SessionTable();
	
	public static TableDef getTableDef(){
		return tableDef;
	}
	
	public String[] CREATEINDEX_SQL = null;

	private String sid = null;

	private int userId = -1;

	private String userName = null;

	private String nickName = null;

	private int sex = -1;
	
	String email = null;
	
	String mobile = null;
	
	private Timestamp createTime = null;	// session create time

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Session(){
		
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Session(String sid, UserInfo userInfo) {
		this.sid = sid;
		this.setUserId(userInfo.getId());
		this.setNickName(userInfo.getNickName());
		this.setSex(userInfo.getSex());
		this.setUserName(userInfo.getUserName());
		this.setEmail(userInfo.getEmail());
		this.setMobile(userInfo.getMobile());

	}

	

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

//	static public String getTABLENAME() {
//		return TABLENAME;
//	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	static public void deleteSessionBySID(String sid ) throws NSException{
		Log.trace("umj", "delete session " + sid + "...");
		Connection c = null;
		PreparedStatement st = null;
		String sql = null;

		try {
			c = tableDef.getDBC();

			sql = "delete from " + tableDef.getTABLENAME() + " where  id=? ";			
			Log.trace("umj", sql);
			st = c.prepareStatement(sql);
			st.setString(1, sid);
			int result = st.executeUpdate();
			if (result == 1) {
				Log.trace("umj", "execute delete " + result + " row successfully.");
			}
		} catch(Exception e){
			throw new NSException(IUMClient.ERR_DB_ERR, e);
		}finally {
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
		
		Log.trace("umj", "delete session " + sid + " successfully.");
	}
	
	
	static public Session selectBySID(String sid) throws NSException {
		Connection c = null;
		Statement st = null;
		String sql = null;

		try {
			c = tableDef.getDBC();
			st = c.createStatement();
			sql = "select data, createtime from " + tableDef.getTABLENAME()
					+ " where id='" + sid + "'";
			Log.trace("umj", sql);
			ResultSet result = st.executeQuery(sql);
			if (result != null) {
				while (result.next()) {					
					
					byte[] data = result.getBytes(1);
					Timestamp createTime = result.getTimestamp(2);
					
					Object o = IO.byteArrayToObject(data);
					
					((Session)o).setCreateTime(createTime);
					return (Session)o;
				}
			}
		} catch(Exception e){
			throw new NSException(IUMClient.ERR_DB_ERR, e);
		}finally {
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

	static public Session selectByUserId(int uid) throws NSException {
		Connection c = null;
		Statement st = null;
		String sql = null;

		try {
			c = tableDef.getDBC();
			st = c.createStatement();
			sql = "select id, data, createtime from " + tableDef.getTABLENAME()
					+ " where userid=" + uid + "";
			Log.trace("umj", sql);
			ResultSet result = st.executeQuery(sql);
			if (result != null) {
				while (result.next()) {					
					
					byte[] data = result.getBytes(2);
					Timestamp createTime = result.getTimestamp(3);
					
					Object o = IO.byteArrayToObject(data);
					
					((Session)o).setCreateTime(createTime);
					return (Session)o;
				}
			}
		} catch(Exception e){
			throw new NSException(IUMClient.ERR_DB_ERR, e);
		}finally {
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

	static public void persist(Session s, boolean update) throws NSException {
		Connection c = null;
		PreparedStatement st = null;
		String sql = null;

		try {
			c = tableDef.getDBC();
			byte[] val = IO.objectToByteArray(s);
			if (update)
			{				//throw new Exception("table " + tableDef.getTABLENAME()						+ "should not be updated");
				sql = "update "+tableDef.getTABLENAME()+" set data=? where id='" + s.getSid() + "'";
				st = c.prepareStatement(sql);
				st.setBytes(1, val);				
			}
			else
			{
				sql = "insert into " + tableDef.getTABLENAME()
			
						+ "(id, userid, data, createtime) values ('" + s.getSid() + "', "+s.getUserId()+", ?, ?)";
				st = c.prepareStatement(sql);
				st.setBytes(1, val);
				st.setTimestamp(2, DateTime.currentUTCTimeStamp());
			}
			Log.trace("umj", "sql: "+ sql);
			
			int result = st.executeUpdate();
			if (result == 1) {
				Log.trace("umj", "execute update " + result + " row successfully.");
			}
		} catch(Exception e){
			throw new NSException(IUMClient.ERR_DB_ERR, e);
		}finally {
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

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
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

	public String  printXML(){
		StringBuffer sb = new StringBuffer();
		sb.append("<user sid="+this.getSid() + " userid="+this.getUserId()+" username="+this.getUserName()+" sex="+this.getSex() + " createtime="+this.getCreateTime().toString() + " nickname="+this.getNickName() + " />");
		return sb.toString();
	}
}
