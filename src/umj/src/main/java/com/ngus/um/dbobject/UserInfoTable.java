package com.ngus.um.dbobject;

import com.ns.db.stat.TableDef;

public class UserInfoTable extends TableDef {
	public UserInfoTable(){
		super("umj_userinfo", "userinfo");
		this.setCHECKTABLE_SQL("select * from "+ TableDef.var_tablename);

		this.setCREATE_SQL("create table IF NOT EXISTS " + TableDef.var_tablename +" (id bigint unsigned auto_increment primary key, username char(30), password VARBINARY(50), sex int(1), nickname char(30), email char(50),mobile char(20), status int(1), createtime timestamp NOT NULL,	updatetime timestamp , lastLogonTime timestamp)");
		
		this.setCREATEINDEX_SQL(new String[]{
				"create unique index ix_userinfo1 on "+ TableDef.var_tablename + " (username)",
				"create unique index ix_userinfo2 on "+ TableDef.var_tablename + " (nickname)"});
	}
}
