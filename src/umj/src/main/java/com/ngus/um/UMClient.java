package com.ngus.um;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.ngus.um.dbobject.Session;
import com.ngus.um.dbobject.UserInfo;
import com.ns.dataobject.Attribute;
import com.ns.exception.NSException;
import com.ns.log.Log;
import com.ns.util.Security;

public class UMClient implements IUMClient {

	public void changePasword(String sid, String newPwd) throws NSException {
		// com.ngus.um.dbobject.Session s =
		// UserManagementEngine.instance().getSessionBySID(sid);
		com.ngus.um.dbobject.Session s = Session.selectBySID(sid);
		if (s == null) {
			Log.error("umj", "get session by session id " + sid + " failed");
			return;
		}

		int userId = Session.selectBySID(sid).getUserId();
		Log.trace("umj", "user id = " + userId);
		Log.trace("umj", "password = " + newPwd);
		try {
			UserInfo.updatePwdByUserId(s.getUserId(), Security
					.MD5_Digest(newPwd));
		} catch (Exception e) {
			Log.error("umj", e);
			throw new NSException(e);
		}
	}

	public int checkDuplicate(String userName, String nickName, String email)
			throws NSException {
		return UserInfo.checkDuplicate(userName, nickName, email);

	}

	public void logoff(String sid) throws NSException {
		// delete from DB
		Session.deleteSessionBySID(sid);

		// delete from cache
		CachedSession.deleteSession(sid);
	}

	public void register(UserInfo userInfo) throws NSException {
		String userName = userInfo.getUserName();
		userName = userName.toLowerCase();
		userInfo.setUserName(userName);
		UserInfo.persist(userInfo, false);
	}

	public String resetPassword(String userName) throws NSException {
		String pwd = UserManagementEngine.instance().genRandomPWD(6);
		try {
			UserInfo.updatePwdByUserName(userName, Security.MD5_Digest(pwd));
		} catch (Exception e) {
			throw new NSException(e);
		}
		return pwd;
	}

	public void updateUserInfo(UserInfo userInfo) throws NSException {
		if (userInfo == null) {
			throw new NSException("userInfo is null");
		}

		if (userInfo.getId() < 0) {
			throw new NSException("invalid user id " + userInfo.getId());
		}
		UserInfo.persist(userInfo, true);

		// update session data in cache and db
		UserManagementEngine.instance().updateSessionDataByUserId(
				userInfo.getId());

	}

	public UMSession logon(String username, String pwd, String app)
			throws NSException {
		return UserManagementEngine.instance().logon(username, pwd, app);
	}

	public UMSession checkSession(String sid) throws NSException {
		com.ngus.um.dbobject.Session s = UserManagementEngine.instance()
				.getSessionBySID(sid);
		// com.ngus.um.dbobject.Session s = Session.selectBySID(sid);
		if (s == null) {
			throw new NSException("session is null");
			//return null;
		}
		User user = new User();
		user.setNickName(s.getNickName());
		user.setUserName(s.getUserName());
		user.setSex(s.getSex());
		user.setUserId(s.getUserId());
		user.setLastLogonTime(s.getCreateTime());
		user.setEmail(s.getEmail());
		user.setMobile(s.getMobile());

		return new UMSession(sid, user);
	}

	public boolean checkHttpSession(HttpServletRequest request) {
		UMSession ums = null;
		String sessionId = null;
		String userName = null;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			Cookie cookie;
			for (int i = 0; i < cookies.length; i++) {
				cookie = cookies[i];
				if (cookie.getName().compareTo("sessionId") == 0) {
					sessionId = cookie.getValue();

				}
			}

			if (sessionId != null) {
				try {
					ums = new UMClient().checkSession(sessionId);
					userName = ums.getUser().getUserName();
					String userId = ums.getUser().getSUserId();
					request.setAttribute("username", userName);
					request.setAttribute("userid", userId);
					request.getSession().setAttribute("username", userName);
					request.getSession().setAttribute("sessionid", sessionId);
					request.getSession().setAttribute("ums", ums);
					request.getSession().setAttribute("userid", userId);
				} catch (Exception e) {
					Log.error("umj", e);
					return false;
				}
			} else {
				Log.error("umj", "sessionid is null");
				return false;
			}

		} else {
			Log.error("umj", "cookies are null");
			return false;
		}
		return true;
	}

	public UserInfo getUserInfoByUserName(String userName) throws Exception {
		return UserInfo.selectByUserName(userName);
	}

	public UserInfo getUserInfoByUserId(int userId) throws NSException {
		return UserInfo.selectByUserId(userId);
	}

	public User getUserByUserName(String userName) throws Exception {
		return new User(UserInfo.selectByUserName(userName));
	}

	public User getUserByUserId(int userId) throws NSException {
		return new User(UserInfo.selectByUserId(userId));
	}

	public Iterator<IUser> searchUser(String key, int start, int number,
			Attribute count) {
		ArrayList<IUser> users = new ArrayList<IUser>();
		try {
			Connection con = com.ns.db.DB.getConnection();
			PreparedStatement psmt = con
					.prepareStatement("select * from umj_userinfo where username like ?");
			psmt.setString(1, "%" + key + "%");
			ResultSet rs = psmt.executeQuery();
			if (rs != null) {
				while (start-- > 0 && rs.next()) {
				}
				while (number-- > 0 && rs.next()) {
					users.add(this.getUserByUserId(rs.getInt(1)));
				}
				rs.last();
				count.setValue(rs.getRow());
			}
		} catch (Exception e) {
			Log.error("umj", e);
		}
		return users.iterator();
	}

	public static List<IUser> getHotUser(int number) {
		Iterator<UserInfo> ui = UserInfo.getHotUser(number).iterator();
		ArrayList<IUser> arr = new ArrayList<IUser>();
		while (ui.hasNext()) {
			arr.add(new User(ui.next()));
		}
		return arr;
	}

	public static List<IUser> getNewUser(int number) {
		Iterator<UserInfo> ui = UserInfo.getNewUser(number).iterator();
		ArrayList<IUser> arr = new ArrayList<IUser>();
		while (ui.hasNext()) {
			arr.add(new User(ui.next()));
		}
		return arr;
	}
}