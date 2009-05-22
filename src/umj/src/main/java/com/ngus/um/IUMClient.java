package com.ngus.um;

import com.ngus.um.dbobject.UserInfo;
import com.ns.exception.NSException;

public interface IUMClient {
	
	public int ERR_NO_ERROR = 0;
	public int ERR_INIT_ERROR = -1;
	public int ERR_NO_DBC = -2;
	public int ERR_DUP_USERNAME = -3;
	public int ERR_DUP_NICKNAME = -4;
	public int ERR_DUP_EMAIL = -5;
	public int ERR_DB_ERR = -6;
	
	
	
	public UMSession checkSession(String sid) throws NSException;
	
	public UMSession logon(String username, String pwd, String app) throws NSException;
	
	public void logoff(String sid) throws NSException;
	
	public void register(UserInfo usrInfo) throws NSException;

	public int checkDuplicate(String userName, String nickName, String email)  throws NSException;
	
	/**
	 * reset password, return new password
	 * @param userName
	 */
	public String resetPassword(String userName) throws NSException; 
	
	public void changePasword(String sid, String newPwd) throws NSException;
}
