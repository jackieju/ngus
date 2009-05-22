package com.ngus.um;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Properties;
import java.util.Random;

import javax.sql.DataSource;

import com.danga.MemCached.SockIOPool;
import com.ngus.um.dbobject.Session;
import com.ngus.um.dbobject.UserInfo;
import com.ns.db.DB;
import com.ns.exception.NSException;
import com.ns.log.Log;
import com.ns.util.Base64;
import com.ns.util.Security;

public class UserManagementEngine {
	static private UserManagementEngine _instance = null;

	private UserManagementEngine() {

	}

	//
	// private byte[] encyptPwd(String pwd){
	// return Security.MD5_Digest(pwd);
	// }
	//	
	public static void UMInit(DataSource ds, Properties prop) {
		DB.initialize(ds);

		try {
			String[] serverlist = {}; // = new String[20];// {
			// /*"192.168.1.162:11211"*/ };
			String[] serverlist1 = {};
			int n = 0;
			String servers = prop.getProperty("ngus.core.memCached.server");
			Log.trace("umj", "servers=" + servers);
			if (servers != null) {
				serverlist = servers.split("(;)");				
				for (int i = 0; i < serverlist.length; i++) {					
					if (serverlist[i] == null || serverlist[i].length() == 0)						
						continue;
					String server = serverlist[i].trim();
					if (server.length() == 0)
						continue;
					n++;
				}
				serverlist1 = new String[n];
				n=0;
				for (int i = 0; i < serverlist.length; i++) {					
					if (serverlist[i] == null || serverlist[i].length() == 0)
						continue;
					String server = serverlist[i].trim();
					if (server.length() == 0)
						continue;
					serverlist1[n] = serverlist[i];
					Log.trace("umj", "server[" + n + "]: " + serverlist1[n]);
					n++;
				}
				// Integer[] weights = { new Integer(5), new Integer(2) };
				// int initialConnections = 10;
				// int minSpareConnections = 5;
				// int maxSpareConnections = 50;
				// long maxIdleTime = 1000 * 60 * 30; // 30 minutes
				// long maxBusyTime = 1000 * 60 * 5; // 5 minutes
				// long maintThreadSleep = 1000 * 5; // 5 seconds
				// int socketTimeOut = 1000 * 3; // 3 seconds to block on reads
				// int socketConnectTO = 1000 * 3; // 3 seconds to block on
				// initial connections. If 0, then will use blocking connect
				// (default)
				// boolean failover = false; // turn off auto-failover in event
				// of server down
				// boolean nagleAlg = false; // turn off Nagle's algorithm on
				// all sockets in pool

				SockIOPool pool = SockIOPool.getInstance(CachedSession.MEMCACHED_POOL_NAME);
				pool.setServers(serverlist1);
				// pool.setWeights( weights );
				// pool.setInitConn( initialConnections );
				// pool.setMinConn( minSpareConnections );
				// pool.setMaxConn( maxSpareConnections );
				// pool.setMaxIdle( maxIdleTime );
				// pool.setMaxBusyTime( maxBusyTime );
				// pool.setMaintSleep( maintThreadSleep );
				// pool.setSocketTO( socketTimeOut );
				// pool.setSocketConnectTO( socketConnectTO );
				// pool.setNagle( nagleAlg );
				// pool.setHashingAlg( SockIOPool.NEW_COMPAT_HASH );
				pool.initialize();
			}
			Log.trace("umj", "SockIOPool Initialized with server: "
					+ serverlist.toString());

			UserManagementEngine.instance();
		} catch (Exception e) {
			Log.error("umj", e);
		}

	}

	/**
	 * Get instance
	 * 
	 * @return
	 * @throws NSException
	 */
	synchronized static public UserManagementEngine instance()
			throws NSException {
		if (_instance == null) {

			_instance = new UserManagementEngine();

			_instance.init();

		}

		return _instance;
	}

	/**
	 * destroy
	 * 
	 * @throws NSException
	 */
	synchronized static public void destroy() throws Throwable {
		if (_instance != null) {
			_instance = null;
		}
	}

	public void init() throws NSException {
		Log.trace("umj", "Init...");

		// check um table
		try {
			Session.getTableDef().checkTable(true);
			UserInfo.getTableDef().checkTable(true);
		} catch (Exception e) {
			throw new NSException(IUMClient.ERR_INIT_ERROR, e);
		}

		
		Log.trace("umj", "Init UM ok");
	}

	/**
	 * Get DB Connection
	 * 
	 * @return
	 * @throws NSException
	 */
	private Connection getConnection() throws NSException {
		try {
			return DB.getConnection();
		} catch (Exception e) {
			throw new NSException(UMClient.ERR_NO_DBC, e);
		}
	}

	static public String createSessionId(UserInfo userInfo) throws NSException {
		// TODO simple algorythm to generate sessionid
		try {
			String sid = Base64.encode(Security.MD5_Digest(""
					+ System.currentTimeMillis() + "" + userInfo.getId()));
			return sid;
		} catch (Exception e) {
			throw new NSException(e);
		}

	}

	// public UserInfo getUserInfoByUserName(String userName) throws
	// NSException{
	// return UserInfo.selectByUserName(userName);
	// }

	public Session getSessionBySID(String sid) throws NSException {
		Log.trace("umj", "enter getSessionBySID, sessionId="+sid);
		Session s = CachedSession.getSessionFromCache(sid);
		Log.trace("umj", "session="+s);
		if (s == null) {
			Log.trace("umj", "session not in cached, try to get from db. sid=" + sid);
			s = Session.selectBySID(sid);
		}
		Log.trace("umj", "sid=" + sid + ", session="+ (s!=null?s.printXML():null));
		return s;
	}

	// public void updatePwd(int userId, String pwd)throws NSException{
	// UserInfo.updatePwd(userId, pwd);
	// }
	//	
	public String genRandomPWD(int pwd_len) {
//		35是因为数组是从0开始的，26个字母+10个数字
		  final int  maxNum = 36;
		  int i;  //生成的随机数
		  int count = 0; //生成的密码的长度
		  char[] str = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
		    'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
		    'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
		  
		  StringBuffer pwd = new StringBuffer("");
		  Random r = new Random();
		  while(count < pwd_len){
		   //生成随机数，取绝对值，防止生成负数，
		   
		   i = Math.abs(r.nextInt(maxNum));  //生成的数最大为36-1
		   
		   if (i >= 0 && i < str.length) {
		    pwd.append(str[i]);
		    count ++;
		   }
		  }
		  
		  return pwd.toString();
	}

	private boolean checkUserPassword(byte[] shadowed1, byte[] shadowed2) {
		// Log.trace("umj", new String(shadowed1));
		// Log.trace("umj", new String(shadowed2));
		if (shadowed1.length != shadowed2.length)
			return false;
		for (int i = 0; i < shadowed2.length; i++) {
			if (shadowed1[i] != shadowed2[i])
				return false;
		}
		return true;
	}

	public UMSession logon(String username, String pwd, String app)
			throws NSException {
		UserInfo userInfo = UserInfo.selectByUserName(username);
		if (userInfo == null)
			throw new UserNotExistException();
		try {
			if (!checkUserPassword(userInfo.getShadowedPwd(), Security
					.MD5_Digest(pwd))) {
				throw new PasswordErrException();
			}
		} catch (Exception e) {
			throw new NSException(e);

		}

		String sid = UserManagementEngine.createSessionId(userInfo);
		Log.trace("umj", "sid=" + sid);

		// check if user sesion already exist
		Session old_session = Session.selectByUserId(userInfo.getId());
		if (old_session != null)
		{
			Log.trace("umj", "session for user " + username);
			Session.deleteSessionBySID(old_session.getSid());
		}
		
		// put session to db
		Session s = new Session(sid, userInfo);
		s.setCreateTime(new Timestamp(System.currentTimeMillis()));
		Session.persist(s, false);
		Log.trace("umj", "save session to db ok, sid=" + sid);
		
		// update last logon time
		UserInfo.updateLogonTimeByUserId(userInfo.getId(), new Timestamp(System.currentTimeMillis()));

		// put session to cache
		new CachedSession(sid, s).start();
		Log.trace("umj", "save session to cache ok, sid=" + sid);

		UMSession ret = new UMSession(sid, new User(userInfo));

		return ret;
	}
	
	public void updateSessionDataByUserId(int userId) throws NSException{
		Session s = Session.selectByUserId(userId);
		String sid = s.getSid();
		UserInfo u = UserInfo.selectByUserId(userId);
		Session new_s = new Session(sid, u);
		Session.persist(new_s, true);
		CachedSession.putSessionToCache(sid, s);
	}

}
