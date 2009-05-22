	package com.ngus.myweb.servlet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.ngus.config.SystemProperty;
import com.ngus.dataengine.DBConnection;
import com.ngus.servlet.Initializer;
import com.ns.db.DB;
import com.ns.log.Log;
import com.ns.mail.MailSender;

public class MyWebInitializer implements ServletContextListener {
	Initializer core = new Initializer();

	HomePageCreator hpc = new HomePageCreator();

	public void contextDestroyed(ServletContextEvent arg0) {
		hpc.contextDestroyed(arg0);
		
		core.contextDestroyed(arg0);
	}

	public void contextInitialized(ServletContextEvent arg0) {
		core.contextInitialized(arg0);
		// check table myweb_pagejpeg
		try{
		DB.checkTable(DBConnection.getConnection(), "myweb_pagejpg", new String[]{
		"create table IF NOT EXISTS myweb_pagejpg(url varchar(255), count int(11), jpgpath varchar (255) )",
		"create unique index ix_url on myweb_pagejpg(url ASC)",
		"insert into myweb_pagejpg values('dummy', 'dummy')"});
	
		DB.checkTable(DBConnection.getConnection(), "myweb_videojpg", new String[]{
			"create table IF NOT EXISTS myweb_videojpg(url varchar(255), count int(11), jpgpath varchar (255) )",
			"create unique index ix_vurl on myweb_videojpg(url ASC)",
		"insert into myweb_videojpg values('dummy', 'dummy')"});
		Log.log("check table comment");
		DB.checkTable(DBConnection.getConnection(), "comment", new String[]{
			"CREATE TABLE IF NOT EXISTS `comment` (`id` int(11) unsigned NOT NULL auto_increment,`resId` varchar(50) default NULL,`user` varchar(50) default NULL, `createTime` timestamp NULL default NULL, PRIMARY KEY  (`id`))ENGINE=InnoDB DEFAULT CHARSET=utf8"
			//, "create index for table comment"
		});
		DB.checkTable(DBConnection.getConnection(), "message", new String[]{
			"CREATE TABLE IF NOT EXISTS `message` (`messageId` int(11) unsigned NOT NULL auto_increment,`postUserId` varchar(50) default NULL,`receiveUserId` varchar(50) default NULL,`createTime` timestamp NULL default NULL,`isReaded` bit(1) default NULL,`postShow` bit(1) default NULL,`receiveShow` bit(1) default NULL,PRIMARY KEY  (`messageId`)) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8",
		});
		
		DB.checkTable(DBConnection.getConnection(), "userextension", new String[]{
			"CREATE TABLE IF NOT EXISTS `userextension` (`userName` varchar(10) NOT NULL default '0',`score` int(10) unsigned default '0',`userPic` blob,PRIMARY KEY  (`userName`)) ENGINE=InnoDB DEFAULT CHARSET=utf8",
		});
		DB.checkTable(DBConnection.getConnection(), "updatetime", new String[]{
			"CREATE TABLE IF NOT EXISTS `updatetime` (`userid` bigint(20) unsigned NOT NULL default 0, `app` varchar(10) NOT NULL default '*', `updatetime` bigint(20) unsigned default 0, PRIMARY KEY  (`userid`,`app`)) ENGINE=InnoDB DEFAULT CHARSET=utf8",
			"insert into updatetime values(0, 'test', 0)"
		});
		
		DB.checkTable(DBConnection.getConnection(), "deleted", new String[]{
			"CREATE TABLE IF NOT EXISTS `deleted` (`userid` bigint(20) unsigned NOT NULL default 0, `app` varchar(10) NOT NULL default '*', `resId` varchar(255), `deletetime` bigint(20) unsigned default 0, PRIMARY KEY  (`userid`,`app`)) ENGINE=InnoDB DEFAULT CHARSET=utf8",
			"create  index ideleted1 on deleted( deletetime ASC)",
			"insert into deleted values(0, 'test', 'test', 0)"
		});
		
		}catch(Exception e){
			Log.error(e);
		}
		

		
		
		hpc.contextInitialized(arg0);
		
	}

	

}
