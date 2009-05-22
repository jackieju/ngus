package com.ngus.servlet;

import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.ngus.config.SystemProperty;
import com.ngus.dataengine.DBConnection;
import com.ngus.um.UserManagementEngine;
import com.ns.log.Log;

public class UMInitializer implements ServletContextListener {

	public void contextDestroyed(ServletContextEvent arg0) {
		Log.trace("enter UM Destroy");
		try {
			UserManagementEngine.destroy();
		} catch (Throwable e) {
			Log.error(e);
		}
		Log.trace("leave UM Destroy");
	}

	public void contextInitialized(ServletContextEvent arg0) {
		Log.trace("enter UM Init");
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
		// String server = context.getInitParameter("ngus.um.server");
		// Log.trace("server="+server);
		// cp.sUserMgrSvrIP = server;
		// String port = context.getInitParameter("ngus.um.port");
		// Log.trace("port="+port);
		// cp.wUserMgrSvrPort = (short)Integer.parseInt(port);
		//		
		// int ret = UMClient.UMLIBInit(cp, 60000*2, true,
		// "umclientjni%04d%02d%02d.log");
		Properties p = new Properties();
//		p.put("ngus.core.memCached.server", context
//				.getInitParameter("ngus.core.memCached.server"));
		p.put("ngus.core.memCached.server", SystemProperty
				.getProperty("ngus.core.memCached.server"));
		try {
			UserManagementEngine.UMInit(DBConnection.getDataSource(), p);
		} catch (Exception e) {
			Log.error(e);
		}
		Log.trace("leave UM Init.");
	}

}
