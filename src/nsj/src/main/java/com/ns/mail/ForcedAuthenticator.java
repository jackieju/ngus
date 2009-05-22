package com.ns.mail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

class ForcedAuthenticator extends Authenticator {
	//MailSettings mailSettings;
	String user;
	String pwd;
	public ForcedAuthenticator(String u, String p){
	//	this.mailSettings = mailSettings;
		user = u;
		pwd = p;
	}
	public PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(user, pwd);
	}
}
