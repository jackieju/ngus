/*
 * Created on 2005-5-8
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ns.db.auto;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ns.db.DBC;
import com.ns.db.stat.TableDef;
import com.ns.log.Log;
//import com.sun.rsasign.r;

/**
 * Table defitinion for dynamic table object
 * @author I027910
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Table extends TableDef {
	List<FieldDef> fields = new ArrayList<FieldDef>();

	//	map from name to field name
	HashMap<String, FieldDef> map = new HashMap<String, FieldDef>(); 

	// map from mapped name to field name
	HashMap<String, FieldDef> map2 = new HashMap<String, FieldDef>(); 

	public Table(String table, String mappedName){
		super(table, mappedName);
	}
	
	synchronized public void addField(String name, String mappedName,
			String type) {
		FieldDef field = new FieldDef(name, mappedName, type);
		fields.add(field);
		map.put(name, field);
		map2.put(mappedName, field);
	}

	synchronized public FieldDef getField(String name) {
		return map.get(name);
	}

	synchronized public FieldDef getFieldByMappedName(String name) {
		return map2.get(name);
	}

	public String genSQL(String schema, String select, String where, String sortBy,
			boolean bAsc) {

		if (select == null || select.length() == 0)
			select = "*";

		if (where == null)
			where = "";
		else
			where = where.trim();
		if (where.length() > 0 && !where.toLowerCase().startsWith("where")) {
			where = " where " + where;
		}

		if (sortBy == null)
			sortBy = "";
		else
			sortBy = sortBy.trim();
		if (sortBy.length() > 0 && !sortBy.toLowerCase().startsWith("order by")) {
			sortBy = " order by " + sortBy;
		}

		String asc = "";
		if (sortBy != null && sortBy.length() > 0) {
			if (bAsc)
				asc = "ASC";
			else
				asc = "DESC";
		}

		String ret = "select " + select + " from "
				+ TABLENAME
				+ " " + where + sortBy + " " + asc;

		return ret;
	}

	public String genCreateSQL() throws Exception {
		String s = super.genCreateSQL();
		if ( s!= null)
			return s;
		StringBuffer ret = new StringBuffer();
		ret.append("create table " + this.TABLENAME + "(");
		for (int i = 0; i < this.fields.size(); i++) {
			if (i != 0)
				ret.append(", ");
			ret.append(fields.get(i).genCreateSQLString());
		}
		ret.append(")");

		return ret.toString();
	}

	/**
	 * create index for every column
	 * @param schema
	 * @return
	 * @throws Exception
	 */
	public String genCreateSQLForIndex() throws Exception {
		StringBuffer ret = new StringBuffer();
		ret.append("create index  " + "ix_" + this.TABLENAME + " on "				
				+ this.TABLENAME + " (");
		for (int i = 1; i < this.fields.size(); i++) {
			if (i != 1)
				ret.append(", ");
			ret.append(fields.get(i).getName());
		}
		ret.append(")");

		return ret.toString();
	}
	
	public void createTable() throws Exception
	{

		Connection c = null;
		Statement st = null;
		try {
			c = getDBC();
			st = c.createStatement();
			// create table
			String s = genCreateSQL();
			Log.trace("sql: " + s);
			st.execute(s);
			Log.trace("create table" + getTABLENAME());

			// create index
			for (int i = 0; i < CREATEINDEX_SQL.length; i++){
				s = CREATEINDEX_SQL[i];
				Log.trace("sql: " + s);
				st.execute(s);
			}
			
		} finally {
			DBC.closeStatement(st);
			DBC.closeConnection(c);
		}

		// if (i <= 0)
		// throw new Exception("create index " + index.getName() + " failed");
		Log.trace("create table " + getTABLENAME() + " OK.");
	}
}
