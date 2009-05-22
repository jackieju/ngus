package com.ngus.um.dbobject;

import com.ns.db.stat.TableDef;



public class SessionTable extends TableDef{
	public SessionTable(){
		super("umj_session", "session");
		this.setCHECKTABLE_SQL("select * from " +  TableDef.var_tablename);

		this.setCREATE_SQL("create table IF NOT EXISTS " +  TableDef.var_tablename 
			+ " (id char(100)primary key, userid bigint unsigned, data varbinary(1000), createtime timestamp NOT NULL )");
		
		this.setCREATEINDEX_SQL(new String[]{"create unique index ix_umjsession1 on " +  TableDef.var_tablename  +"(userid)"});
	}
}
