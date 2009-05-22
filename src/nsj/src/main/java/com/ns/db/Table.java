/*
 * Created on 2005-5-8
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ns.db;

//import com.sun.rsasign.r;

/**
 * @author I027910
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Table {
	public String[] cols; // column name
	public String table_name; // name of table

	public static void main(String[] args) {
	}
	/**
	 * @return
	 */
	public String[] getCols() {
		return cols;
	}

	/**
	 * @return
	 */
	public String getTable_name() {
		return table_name;
	}

	/**
	 * @param strings
	 */
	public void setCols(String[] strings) {
		cols = strings;
	}

	/**
	 * @param string
	 */
	public void setTable_name(String string) {
		table_name = string;
	}

	public String genCols(String[] cols) {
		String s = "";
		for (int i = 0; i < cols.length; i++) {
			if (i != 0)
				s += ", ";
			s += cols[i];
		}
		return s;
	}

	

}
