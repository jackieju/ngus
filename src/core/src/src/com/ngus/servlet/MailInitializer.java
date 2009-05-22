package com.ngus.servlet;

import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.ngus.dataengine.DBConnection;

import com.ns.log.Log;
import com.ns.mail.MailSender;

public class MailInitializer  implements ServletContextListener {
	public void contextDestroyed(ServletContextEvent arg0) {
		Log.trace("enter Mail Destroy");
		try {
		//	UserManagementEngine.destroy();
			MailSender.destroy();
		} catch (Throwable e) {
			Log.error(e);
		}
		Log.trace("leave Mail Destroy");
	}

	public void contextInitialized(ServletContextEvent arg0) {
		Log.trace("enter MailInitializer");
		final ServletContext context = arg0.getServletContext();
		// CommuParam cp = new CommuParam();
		// cp.bPortLoadBalance = true;
		// cp.dwGetTime = 10000;
		// cp.dwMaxProcesserNum = 1;
		// cp.dwMaxSessionNum = 10000;
		// cp.dwMaxSocketNum = 5;
		// cp.dwProcessThreshold = 10;
		// cp.dwPutTime = 10000;
		// cp.dwRcvThreshold = 10;
		// cp.dwSndThreshold = 10;
		 String server = context.getInitParameter("mail.smtp.host");
		 String port = context.getInitParameter("mail.smtp.port");
		 String username = context.getInitParameter("mail.smtp.username");
		 String password = context.getInitParameter("mail.smtp.password");
		 Log.trace("smtp server="+server);
		 Log.trace("port="+port);
		 Log.trace("username="+username);
		 Log.trace("password="+password);
		 
		// Log.trace("server="+server);
		// cp.sUserMgrSvrIP = server;
		// String port = context.getInitParameter("ngus.um.port");
		// Log.trace("port="+port);
		// cp.wUserMgrSvrPort = (short)Integer.parseInt(port);
		//		
		// int ret = UMClient.UMLIBInit(cp, 60000*2, true,
		// "umclientjni%04d%02d%02d.log");
//		Properties p = new Properties();
//		p.put("ngus.core.memCached.server", context
//				.getInitParameter("ngus.core.memCached.server"));
		try {
			MailSender.initialize(server, port, username, password);
		} catch (Exception e) {
			Log.error(e);
		}
		Log.trace("leave MailInitializer.");
	}
}
