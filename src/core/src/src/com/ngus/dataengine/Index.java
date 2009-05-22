package com.ngus.dataengine;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ngus.config.SystemProperty;
import com.ngus.resengine.ResourceEngine;
import com.ngus.resengine.ResourceId;
import com.ns.dataobject.Attribute;
import com.ns.db.auto.FieldDef;
import com.ns.log.Log;
import com.ns.util.IO;

public class Index {
	String name;

	List<FieldDef> fields = new ArrayList<FieldDef>();

	HashMap<String, FieldDef> map = new HashMap<String, FieldDef>(); // map

	// from
	// name

	// to field

	HashMap<String, FieldDef> map2 = new HashMap<String, FieldDef>(); // map

	// from

	// mapped name
	// to field

	String mappedName[] = null;

	boolean syncUpdate = true;

	public String[] getMappedName() {
		return mappedName;
	}

	public void setMappedName(String[] mappedName) {
		this.mappedName = mappedName;
	}

	public Index(String name, String[] mappedName, boolean syncUpdate) {
		this.name = name.toLowerCase();
		this.mappedName = mappedName;
		this.syncUpdate = syncUpdate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name.toLowerCase();
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

	public String genSQL(String select, String where, String sortBy,
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
				/*+ SystemProperty.getProperty("ngus.jdbc.schema") + "." */+ name
				+ " " + where + sortBy + " " + asc;

		return ret;
	}

	public String genCreateSQL() throws Exception {
		StringBuffer ret = new StringBuffer();
		ret.append("create table "
			/*	+ SystemProperty.getProperty("ngus.jdbc.schema") + "."*/
				+ this.name + "(");
		for (int i = 0; i < this.fields.size(); i++) {
			if (i != 0)
				ret.append(", ");
			ret.append(fields.get(i).genCreateSQLString());
		}
		ret.append(")");

		return ret.toString();
	}

	public List<String> genCreateSQLForIndex() throws Exception {
		ArrayList<String> list = new ArrayList<String>();
		for (int i = 0; i < this.fields.size(); i++) {
		StringBuffer ret = new StringBuffer();
		ret.append("create index  " + "ix_" + this.name + i + " on "
				/*+ SystemProperty.getProperty("ngus.jdbc.schema") + "."*/
				+ this.name + " (" );
		
//			if (i != 1)
//				ret.append(", ");
			ret.append(fields.get(i).getName());
			ret.append(")");
			list.add(new String(ret));
		}
		

		return list;
	}

	// public List<String> genInsertSQL(ResDesObject rdo, Timestamp time,
	// String userId) throws Exception {
	// Log.trace("enter genInsertSQL");
	// List<String> list = new ArrayList<String>();
	//
	// // if (!isMatchIndex(rdo))
	// // return list;
	//
	// // calculate row number ( if there is multi-value attribute)
	// int count = 1;
	// for (int j = 0; j < this.fields.size(); j++) {
	// String mn = fields.get(j).getMappedName();
	// if (mn.startsWith("$")) {
	// continue;
	// } else {
	// Attribute attr = rdo.getAttr(mn);
	// if (attr == null) {
	// // throw new Exception("update index failed, column "
	// // + fields.get(j).getName()
	// // + " can not find its mapped attribute " + mn);
	//
	// Log.warning("update index failed, column "
	// + fields.get(j).getName()
	// + " can not find its mapped attribute " + mn);
	// continue;
	// }
	// if (attr.valueType() == Attribute.ATTR_DT_LIST) {
	// if (count > 1)
	// throw new Exception(
	// "one index table can not have two fields with list type");
	// count = ((List) attr.getValue()).size();
	// continue;
	// }
	// }
	// }
	//
	// // generate sqls
	// for (int n = 0; n < count; n++) {
	// int number = 0;
	// StringBuffer ret = new StringBuffer();
	// ret.append("insert into "
	// + SystemProperty.getProperty("ngus.jdbc.schema") + "."
	// + this.name + " values(");
	//
	// // values clause
	// for (int i = 0; i < this.fields.size(); i++) { // for each fields
	// String v = null;
	// FieldDef field = fields.get(i);
	// String mn = field.getMappedName();
	// if (mn.startsWith("$")) { // predined variable
	// if (mn.equalsIgnoreCase("$resId"))
	// v = rdo.getResId();
	// else if (mn.equalsIgnoreCase("$value")) {
	// v = new String(IO.readInputStream(ResourceEngine.instance()
	// .getJCRResource(new ResourceId(rdo.getResId())).getValue()), "UTF-8");
	// } else if (mn.equalsIgnoreCase("$createTime")) {
	// // v = new
	// // DataEngine().getResourceProperty(old.getResId(),
	// // IDataEngine.MODEL_DEFAULT,
	// // IDataEngine.ATTR_CREATETIME);
	// v = time.toString();
	// } else if (mn.equalsIgnoreCase("$userId")) {
	// v = userId;
	// } else if (mn.equalsIgnoreCase("$title")) {
	// v = ResourceEngine.instance().getResource(rdo.getResId()).getTitle();
	// } else
	// throw new Exception("attribute " + mn + "not exist");
	// } else { // attribute
	// Attribute attr = rdo.getAttr(mn);
	// if (attr == null) {
	// Log.warning("insert to index failed, column "
	// + fields.get(i).getName()
	// + " can not find its mapped attribute " + mn);
	// v = null;
	// // } else if (attr.valueType() !=
	// // Attribute.ATTR_DT_LIST) {
	// // v = attr.getStringValue();
	// // } else {
	// // List l = (List) attr.getValue();
	// // v = "[";
	// // for (int n = 0; n < l.size(); n++) {
	// // if (n != 0)
	// // v += ",";
	// // v += l.get(n);
	// // }
	// // v += "]";
	// // }
	// } else if (attr.valueType() == Attribute.ATTR_DT_LIST) {
	// v = ((List<String>) attr.getValue()).get(n);
	// } else
	// v = attr.getStringValue();
	// }
	// if (number != 0)
	// ret.append(", ");
	// if (v == null) // null value does not need single quote
	// ret.append(v + " ");
	// else if (field.getType().equalsIgnoreCase("string")
	// || field.getType().equalsIgnoreCase("time"))
	// ret.append("'" + v + "'");
	// else
	// // number value does not need single quote
	// ret.append(v + " ");
	// number++;
	// }
	// ret.append(")");
	// Log.trace(ret);
	// list.add(ret.toString());
	// }
	// Log.trace("leave genInsertSQL");
	// return list;
	//
	// }

	public List<String> genInsertSQL(ResourceObject ob, String modelName, Timestamp time)
			throws Exception {
		Log.trace("enter genInsertSQL");
		List<String> list = new ArrayList<String>();
		//for (int k = 0; k < this.getMappedName().length; k++) {
		//	String modelName = this.getMappedName()[k];
			ResDesObject rdo = null;
			if (modelName.startsWith("$"))
			 // common index, indenpendant to app
			{
				if (!isMatchROIndex())
				{
					Log.trace("Index " + this.getName() + " is independant index, but no field needs to be updated from ResourceObject");
//					continue;
					return null;
				}		
			}
			else {
				rdo = ob.getResDesObject(modelName);
				if (rdo == null)
//					continue;
					return null;
//				if (!isMatchIndex(rdo))
//				{
//					Log.trace("index " + this.getName() + "doesn't match rdo "+ rdo.getModelName());
////					continue;
//					return null;
//				}
			}
			int count = 1;
			for (int j = 0; j < this.fields.size(); j++) {
				String mn = fields.get(j).getMappedName();
				if (mn.startsWith("$")) {
					continue;
				} else if (rdo != null) {
					Attribute attr = rdo.getAttr(mn);
					if (attr == null) {
						// throw new Exception("update index failed, column "
						// + fields.get(j).getName()
						// + " can not find its mapped attribute " + mn);

						Log.warning("update index failed, column "
								+ fields.get(j).getName()
								+ " can not find its mapped attribute " + mn);
						continue;
					}
					if (attr.valueType() == Attribute.ATTR_DT_LIST) {
						if (count > 1)
							throw new Exception(
									"one index table can not have two fields with list type");
						count = ((List) attr.getValue()).size();
						continue;
					}
				}
			}

			// generate sqls
			for (int n = 0; n < count; n++) {
				int number = 0;
				StringBuffer ret = new StringBuffer();
				ret.append("insert into "
						/*+ SystemProperty.getProperty("ngus.jdbc.schema") + "."*/
						+ this.name + " values(");

				// values clause
				for (int i = 0; i < this.fields.size(); i++) { // for each
					// fields
					String v = null;
					FieldDef field = fields.get(i);
					String mn = field.getMappedName();
					if (mn.startsWith("$")) { // predined variable
						if (mn.equalsIgnoreCase("$resId"))
							v = ob.getResId();
						else if (mn.equalsIgnoreCase("$value")) {
							v = new String(IO.readInputStream(ob.getValue()),
									"UTF-8");
						} else if (mn.equalsIgnoreCase("$createTime")) {
							// v = new
							// DataEngine().getResourceProperty(old.getResId(),
							// IDataEngine.MODEL_DEFAULT,
							// IDataEngine.ATTR_CREATETIME);
							v = time.toString();
						} else if (mn.equalsIgnoreCase("$userId")) {
							v = ob.getUser();
						} else if (mn.equalsIgnoreCase("$title")) {
							v = ob.getTitle();
						}  else if (mn.equalsIgnoreCase("$shareLevel")){
							v = ob.getShareLevel()+"";						
						}else
							throw new Exception("attribute " + mn + "not exist");
					} else if (rdo != null) { // attribute
						Attribute attr = rdo.getAttr(mn);
						if (attr == null) {
							Log.warning("insert to index failed, column "
									+ fields.get(i).getName()
									+ " can not find its mapped attribute "
									+ mn);
							v = null;
							// } else if (attr.valueType() !=
							// Attribute.ATTR_DT_LIST) {
							// v = attr.getStringValue();
							// } else {
							// List l = (List) attr.getValue();
							// v = "[";
							// for (int n = 0; n < l.size(); n++) {
							// if (n != 0)
							// v += ",";
							// v += l.get(n);
							// }
							// v += "]";
							// }
						} else if (attr.valueType() == Attribute.ATTR_DT_LIST) {
							v = ((List<String>) attr.getValue()).get(n);
						} else
							v = attr.getStringValue();
					}
					if (number != 0)
						ret.append(", ");
					if (v == null) // null value does not need single quote
						ret.append(v + " ");
					else if (field.getType().equalsIgnoreCase("string")
							|| field.getType().equalsIgnoreCase("time"))
						{
							v = v.replaceAll("'", "\'");
							ret.append("'" + v + "'");
						}
					else
						// number value does not need single quote
						ret.append(v + " ");
					number++;
				}
				ret.append(")");
				Log.trace(ret);
				list.add(ret.toString());
			}

		//}
		Log.trace("leave genInsertSQL");
		return list;
	}


	public boolean isUpdateIndex(ResDesObject rdo) {
		// for (int i = 0; i < rdo.getAttrNum(); i++) { // for each fields
		// Attribute attr = rdo.getAttr(i);
		// String mn = attr.getName();
		// FieldDef field = this.map2.get(attr.getName());
		// if (field == null) {
		// Log.trace("cannot find field for attribute " + mn + " in index "
		// + this.getName());
		// return false;
		// }
		// }
		// return true;
		boolean hasOwnAttr = false;
		for (int i = 0; i < this.fields.size(); i++) {
			String mn = fields.get(i).getMappedName();
			if (mn.startsWith("$"))
				continue;
			String fn = fields.get(i).getName();

			Attribute attr = rdo.getAttr(mn);
			if (attr == null) {
				Log.trace("cannot find attribute " + mn + " for field " + fn
						+ " in index " + this.getName() + " object:" + rdo.printXML());
				return false;
			}else
				hasOwnAttr = true;
		}
		
		return hasOwnAttr;
	}

	public boolean isMatchROIndex() {
		for (int i = 0; i < this.fields.size(); i++) {
			String mn = fields.get(i).getMappedName();
			if (mn.startsWith("$") && !mn.equalsIgnoreCase("$resId"))
				return true;
		}
		return false;
	}

	/**
	 * update index according to old value
	 * 
	 * @param rdo
	 * @param old
	 * @return
	 * @throws Exception
	 */
	// public List<String> genUpdateSQL(ResDesObject rdo, ResDesObject old,
	// Timestamp time, String userId) throws Exception {
	// List<String> list = new ArrayList<String>();
	// /*
	// * list.add("delete from table "+
	// * SystemProperty.getProperty("ngus.jdbc.schema") + "." + this.name +"
	// * where resid='"+rdo.getResId()+"'"); List<String> list_insert =
	// * this.genInsertSQL(rdo); for (int i = 0; i<list_insert.size(); i++){
	// * list.add(list_insert.get(i)); }
	// */
	//
	// if (!isMatchIndex(rdo))
	// return list;
	//
	// int count = 1;
	// for (int j = 0; j < this.fields.size(); j++) {
	// String mn = fields.get(j).getMappedName();
	// if (mn.startsWith("$")) {
	// continue;
	// } else {
	// Attribute attr = old.getAttr(mn);
	// if (attr == null)
	// throw new Exception("update index failed, column "
	// + fields.get(j).getName()
	// + " can not find its mapped attribute " + mn);
	// if (attr.valueType() == Attribute.ATTR_DT_LIST) {
	// if (count > 1)
	// throw new Exception(
	// "one index table can not have two fields with list type");
	// count = ((List) attr.getValue()).size();
	// continue;
	// }
	// }
	// }
	//
	// // TODO thinking of multi-value field like tags, one ways is to package
	// // it as string like list{"fd", "dfd), the
	// // other way is to delete all related first and then insert new records.
	// // but anway, now only tags field is multi-value, move it ResourceObject
	// // and hard-code and create special table for it
	//
	// // generate sqls
	// for (int n = 0; n < count; n++) {
	// StringBuffer stmt = new StringBuffer();
	// stmt.append("update "
	// + SystemProperty.getProperty("ngus.jdbc.schema") + "."
	// + this.name + " set ");
	//
	// // String oldV=null;
	//
	// // set clause
	// int number = 0;
	// Log.trace("field number is" + fields.size());
	// for (int i = 0; i < fields.size(); i++) { // for each fields
	// String v = null;
	// String oldV = null;
	// FieldDef field = fields.get(i);
	// String mn = field.getMappedName();
	// Log.trace("mapped name = " + mn);
	// if (mn.startsWith("$")) { // predefined variable
	// if (mn.equalsIgnoreCase("$value")) {
	// v = new String(IO.readInputStream(ResourceEngine.instance()
	// .getJCRResource(new ResourceId(rdo.getResId())).getValue()), "UTF-8");
	// // oldV = new String(IO.readInputStream(DataEngine
	// // .getResourceValue(old.getResId())), "UTF-8");
	//
	// } else if (mn.equalsIgnoreCase("$resId")) {
	// v = rdo.getResId();
	// oldV = old.getResId();
	// } else if (mn.equalsIgnoreCase("$createTime")) {
	// continue;
	// } else if (mn.equalsIgnoreCase("$userId")) {
	// v = userId;
	// oldV = new ResourceId(old.getResId()).getUser();
	// } else if (mn.equalsIgnoreCase("$title")) {
	// // v = ResourceEngine.instance().getResource(rdo.getResId()).getTitle();
	// continue;
	// }else
	// throw new Exception("field " + mn + " not supported");
	//
	// /*
	// * else if (mn.equalsIgnoreCase("$createTime")){ //v = new
	// * DataEngine().getResourceProperty(old.getResId(),
	// * IDataEngine.MODEL_DEFAULT, IDataEngine.ATTR_CREATETIME);
	// * v = time.toString(); } else if
	// * (mn.equalsIgnoreCase("$userId")){ v = userId; }
	// */
	// } else { // normal field
	// // Attribute attr = rdo.getAttr(mn);
	// // if (attr == null)
	// // throw new Exception("update index failed, column "
	// // + fields.get(i).getName()
	// // + " can not find its mapped attribute " + mn);
	// Attribute attr = rdo.getAttr(mn);
	// Attribute old_attr = old.getAttr(mn);
	//
	// if (attr == null) {
	// Log.trace("attribute " + mn + " was not found");
	// continue;
	// }
	// if (attr.valueType() == Attribute.ATTR_DT_SUBOBJ)
	// continue;
	//
	// if (attr.valueType() == Attribute.ATTR_DT_LIST) { // list
	// // type
	// v = ((List<String>) attr.getValue()).get(n);
	// oldV = ((List<String>) old_attr.getValue()).get(n);
	// } else {
	// v = attr.getStringValue();
	// oldV = old_attr.getStringValue();
	// }
	//
	// }
	// Log.trace("v=" + v);
	// if (v.equals(oldV)) {
	// Log.trace("value for field " + mn + " not changed: " + v);
	// continue;
	// }
	// if (number != 0)
	// stmt.append(", ");
	//
	// if (v == null) // null value does not need single quote
	// stmt.append(field.getName() + "= " + v + "");
	// else if (field.getType().equalsIgnoreCase("string")
	// || field.getType().equalsIgnoreCase("time"))
	// stmt.append(field.getName() + "= '" + v + "'");
	// else
	// // number value does not need single quote
	// stmt.append(field.getName() + "= " + v + "");
	// number++;
	//
	// }
	// if (number == 0) // no value to be updated
	// {
	// Log.trace("no field for index " + this.getName() + " rdo "
	// + rdo.getName() + "(" + rdo.getResId()
	// + ") should be updated");
	// continue;
	// }
	//
	// // where clause
	// number = 0;
	// for (int i = 0; i < this.fields.size(); i++) { // for each fields
	// // Attribute attr = old.getAttr(i);
	// // if (attr.valueType() == Attribute.ATTR_DT_SUBOBJ)
	// // continue;
	//
	// String v = null;
	// // get field
	// FieldDef field = this.fields.get(i);
	// String mn = field.getMappedName();
	//
	// if (field == null) {
	// continue;
	// }
	//
	// // get field value
	// // if (mn.startsWith("$")) { // predefined variable
	// // if (mn.equalsIgnoreCase("$value")) {
	// // v = new String(IO.readInputStream(DataEngine
	// // .getResourceValue(rdo.getResId())), "UTF-8");
	// // }
	// // }else{
	//
	// if (mn.startsWith("$")) { // predefined variable
	// if (mn.equalsIgnoreCase("$value")) {
	// // v = new String(IO.readInputStream(DataEngine
	// // .getResourceValue(old.getResId())), "UTF-8");
	// continue;
	// } else if (mn.equalsIgnoreCase("$resid")) {
	// v = old.getResId();
	// } else if (mn.equalsIgnoreCase("$userid")) {
	// v = new ResourceId(old.getResId()).getUser();
	// } else if (mn.equalsIgnoreCase("$createTime"))
	// continue;
	// else if (mn.equalsIgnoreCase("$title")) {
	// v = ResourceEngine.instance().getResource(rdo.getResId()).getTitle();
	// }else
	// throw new Exception("attribute " + mn + "not exist");
	// /*
	// * else if (mn.equalsIgnoreCase("$createTime")){ //v = new
	// * DataEngine().getResourceProperty(old.getResId(),
	// * IDataEngine.MODEL_DEFAULT, IDataEngine.ATTR_CREATETIME);
	// * v = time.toString(); } else if
	// * (mn.equalsIgnoreCase("$userId")){ v = userId; }
	// */
	// } else { // normal field
	// Attribute attr = old.getAttr(field.getMappedName());
	// if (attr == null) {
	// throw new Exception("cannot find field "
	// + field.getMappedName() + " for index "
	// + this.getName());
	// }
	// if (attr.valueType() == Attribute.ATTR_DT_SUBOBJ)
	// continue;
	// if (attr.valueType() == Attribute.ATTR_DT_LIST) { // list
	// // type
	// v = ((List<String>) attr.getValue()).get(n);
	// } else
	// v = attr.getStringValue();
	//
	// }
	//
	// // fill where clause
	// if (number != 0)
	// stmt.append(" and ");
	// else
	// stmt.append(" where ");
	//
	// // create string pattern
	// if (v == null) // null value does not need single quote
	// stmt.append(field.getName() + "= " + v + "");
	// else if (field.getType().equalsIgnoreCase("string")
	// || field.getType().equalsIgnoreCase("time"))
	// stmt.append(field.getName() + "= '" + v + "'");
	// else
	// // number value does not need single quote
	// stmt.append(field.getName() + "= " + v + "");
	//
	// number++;
	//
	// }
	//
	// // add to list
	// list.add(stmt.toString());
	// Log.trace(stmt);
	// }
	//
	// return list;
	// }
	public List<String> genUpdateSQL(ResourceObject ob, ResourceObject oldRO, String modelName,
			Timestamp time) throws Exception {
		Log.trace("enter");
		List<String> list = new ArrayList<String>();

//		for (int k = 0; k < this.getMappedName().length; k++) {
//			String modelName = getMappedName()[k];
			ResDesObject rdo = null;
			ResDesObject old = null;
			if (modelName.startsWith("$")) {
				if (!isMatchROIndex())
				{
					Log.trace("Index " + this.getName() + " is independant index, but no field needs to be updated from ResourceObject");
//					continue;
					return null;
				}				
			} else {
				rdo = ob.getResDesObject(modelName);
				old = oldRO.getResDesObject(modelName);
				if (rdo == null || old == null)
//					continue;
					return null;
//				if (!isUpdateIndex(rdo))
//				{
//					Log.trace("index " + this.getName() + "doesn't match rdo "+ rdo.getModelName());
////					continue;
//					return null;
//				}
			}			

			int count = 1;
			for (int j = 0; j < this.fields.size(); j++) {
				String mn = fields.get(j).getMappedName();
				if (mn.startsWith("$")) {
					continue;
				} else if (rdo != null && old != null) {
					Attribute attr = old.getAttr(mn);
					if (attr == null)
						throw new Exception("update index failed, column "
								+ fields.get(j).getName()
								+ " can not find its mapped attribute " + mn+ ", object:"+old.printXML());
					if (attr.valueType() == Attribute.ATTR_DT_LIST) {
						if (count > 1)
							throw new Exception(
									"one index table can not have two fields with list type");
						count = ((List) attr.getValue()).size();
						continue;
					}
				}
			}

			// TODO thinking of multi-value field like tags, one ways is to
			// package
			// it as string like list{"fd", "dfd), the
			// other way is to delete all related first and then insert new
			// records.
			// but anway, now only tags field is multi-value, move it
			// ResourceObject
			// and hard-code and create special table for it

			// generate sqls
			for (int n = 0; n < count; n++) {
				StringBuffer stmt = new StringBuffer();
				stmt.append("update "
					/*	+ SystemProperty.getProperty("ngus.jdbc.schema") + "."*/
						+ this.name + " set ");

				// String oldV=null;

				// set clause
				int number = 0;
				Log.trace("field number is" + fields.size());
				for (int i = 0; i < fields.size(); i++) { // for each fields
					String v = null;
					String oldV = null;
					FieldDef field = fields.get(i);
					String mn = field.getMappedName();
					Log.trace("mapped name = " + mn);
					if (mn.startsWith("$")) { // predefined variable
						if (mn.equalsIgnoreCase("$value")) {
							v = new String(IO.readInputStream(ob.getValue()),
									"UTF-8");
							oldV = new String(IO.readInputStream(oldRO
									.getValue()), "UTF-8");
							// oldV = new String(IO.readInputStream(DataEngine
							// .getResourceValue(old.getResId())), "UTF-8");

						} else if (mn.equalsIgnoreCase("$resId")) {
							continue;
						} else if (mn.equalsIgnoreCase("$createTime")) {
							continue;
						} else if (mn.equalsIgnoreCase("$userId")) {
							continue;
						} else if (mn.equalsIgnoreCase("$title")) {
							v = ob.getTitle();
							oldV = oldRO.getTitle();
						} else if (mn.equalsIgnoreCase("$shareLevel")){
							v = ob.getShareLevel()+"";
							oldV = oldRO.getShareLevel()+"";
						}else						
							throw new Exception("field " + mn
									+ " not supported");

						/*
						 * else if (mn.equalsIgnoreCase("$createTime")){ //v =
						 * new DataEngine().getResourceProperty(old.getResId(),
						 * IDataEngine.MODEL_DEFAULT,
						 * IDataEngine.ATTR_CREATETIME); v = time.toString(); }
						 * else if (mn.equalsIgnoreCase("$userId")){ v = userId; }
						 */
					} else if (rdo != null && old != null) { // normal field
						// Attribute attr = rdo.getAttr(mn);
						// if (attr == null)
						// throw new Exception("update index failed, column "
						// + fields.get(i).getName()
						// + " can not find its mapped attribute " + mn);
						Attribute attr = rdo.getAttr(mn);
						Attribute old_attr = old.getAttr(mn);

						if (attr == null) {
							Log.trace("attribute " + mn + " was not found");
							continue;
						}
						if (attr.valueType() == Attribute.ATTR_DT_SUBOBJ)
							continue;

						if (attr.valueType() == Attribute.ATTR_DT_LIST) { // list
							// type
							v = ((List<String>) attr.getValue()).get(n);
							oldV = ((List<String>) old_attr.getValue()).get(n);
						} else {
							v = attr.getStringValue();
							oldV = old_attr.getStringValue();
						}

					}
					Log.trace("v=" + v);
					if (v.equals(oldV)) {
						Log.trace("value for field " + mn + " not changed: "
								+ v);
						continue;
					}
					if (number != 0)
						stmt.append(", ");

					if (v == null) // null value does not need single quote
						stmt.append(field.getName() + "= " + v + "");
					else if (field.getType().equalsIgnoreCase("string")
							|| field.getType().equalsIgnoreCase("time"))
						stmt.append(field.getName() + "= '" + v.replaceAll("'", "\'") + "'");
					else
						// number value does not need single quote
						stmt.append(field.getName() + "= " + v + "");
					number++;

				}
				if (number == 0) // no value to be updated
				{
					Log.trace("no field for index " + this.getName() + " rdo "
							+ modelName + "(" + ob.getResId()
							+ ") should be updated");
					continue;
				}

				// where clause
				number = 0;
				for (int i = 0; i < this.fields.size(); i++) { // for each
																// fields
					// Attribute attr = old.getAttr(i);
					// if (attr.valueType() == Attribute.ATTR_DT_SUBOBJ)
					// continue;

					String v = null;
					// get field
					FieldDef field = this.fields.get(i);
					String mn = field.getMappedName();

					if (field == null) {
						continue;
					}

					// get field value
					// if (mn.startsWith("$")) { // predefined variable
					// if (mn.equalsIgnoreCase("$value")) {
					// v = new String(IO.readInputStream(DataEngine
					// .getResourceValue(rdo.getResId())), "UTF-8");
					// }
					// }else{

					if (mn.startsWith("$")) { // predefined variable
						if (mn.equalsIgnoreCase("$value")) {
							// v = new String(IO.readInputStream(DataEngine
							// .getResourceValue(old.getResId())), "UTF-8");
							continue;
						} else if (mn.equalsIgnoreCase("$resid")) {
							v = oldRO.getResId();
						} else if (mn.equalsIgnoreCase("$userid")) {
							v = oldRO.getUser();
						} else if (mn.equalsIgnoreCase("$createTime"))
							continue;
						else if (mn.equalsIgnoreCase("$title")) {
							v = oldRO.getTitle();
						}
						else if (mn.equalsIgnoreCase("$shareLevel")){
							v = oldRO.getShareLevel()+"";							
						}
						 else							
							throw new Exception("attribute " + mn + "not exist");
						/*
						 * else if (mn.equalsIgnoreCase("$createTime")){ //v =
						 * new DataEngine().getResourceProperty(old.getResId(),
						 * IDataEngine.MODEL_DEFAULT,
						 * IDataEngine.ATTR_CREATETIME); v = time.toString(); }
						 * else if (mn.equalsIgnoreCase("$userId")){ v = userId; }
						 */
					} else if (rdo != null && old != null) { // normal field
						Attribute attr = old.getAttr(field.getMappedName());
						if (attr == null) {
							throw new Exception("cannot find field "
									+ field.getMappedName() + " for index "
									+ this.getName());
						}
						if (attr.valueType() == Attribute.ATTR_DT_SUBOBJ)
							continue;
						if (attr.valueType() == Attribute.ATTR_DT_LIST) { // list
							// type
							v = ((List<String>) attr.getValue()).get(n);
						} else
							v = attr.getStringValue();

					}

					// fill where clause
					if (number != 0)
						stmt.append(" and ");
					else
						stmt.append(" where ");

					// create string pattern
					if (v == null) // null value does not need single quote
						stmt.append(field.getName() + "= " + v + "");
					else if (field.getType().equalsIgnoreCase("string")
							|| field.getType().equalsIgnoreCase("time"))
						stmt.append(field.getName() + "= '" + v.replaceAll("'", "\'") + "'");
					else
						// number value does not need single quote
						stmt.append(field.getName() + "= " + v + "");

					number++;

				}

				// add to list
				list.add(stmt.toString());
				Log.trace(stmt);
			}
//		}
		Log.trace("leave");
		return list;
	}

	public List<String> genDeleteSQL(ResourceObject ro, String modelName) throws Exception {
		List<String> list = new ArrayList<String>();
		Log.trace("enter");
		
//		for (int k = 0; k < this.getMappedName().length; k++) {
//			String modelName = this.getMappedName()[k];
			ResDesObject rdo = null;
			if (modelName.startsWith("$")) { // independant to app

			} else {
				rdo = ro.getResDesObject(modelName);
				if (rdo == null)
				{
					Log.trace("no rdo for model "+ modelName + " found, ro=" + ro.printXML());
//					continue;
					return null;
				}
//				if (!isMatchIndex(rdo))
//				{
//					Log.trace("index " + this.getName() + "doesn't match rdo "+ rdo.getModelName());
////					continue;
//					return null;
//				}
			}

			int count = 1;
			for (int j = 0; j < this.fields.size(); j++) {

				String mn = fields.get(j).getMappedName();
				if (mn.startsWith("$")) {
					continue;
				} else if (rdo != null) {
					Attribute attr = rdo.getAttr(mn);
					if (attr == null)
						// throw new Exception("delete index failed, column "
						// + fields.get(j).getName()
						// + " can not find its mapped attribute " + mn);
						continue;
					if (attr.valueType() == Attribute.ATTR_DT_LIST) {
						if (count > 1)
							throw new Exception(
									"one index table can not have two fields with list type");
						count = ((List) attr.getValue()).size();
						continue;
					}
				}
			}

			for (int n = 0; n < count; n++) {
				StringBuffer stmt = new StringBuffer();
				stmt.append("delete from "
					/*	+ SystemProperty.getProperty("ngus.jdbc.schema") + "."*/
						+ this.name + " where ");
				String v;
				v = null;
				int number = 0;
				for (int i = 0; i < this.fields.size(); i++) { // for each
					// fields
					FieldDef field = fields.get(i);
					String mn = field.getMappedName();
					if (mn.startsWith("$")) { // predefined variable
						if (mn.equalsIgnoreCase("$value")) {
							v = new String(IO.readInputStream(ro.getValue()),
									"UTF-8");
						} else if (mn.equalsIgnoreCase("$resid")) {
							v = ro.getResId();
						} else if (mn.equalsIgnoreCase("$userid"))
							v = ro.getUser();
						else if (mn.equalsIgnoreCase("$createTime"))
							continue;
						else if (mn.equalsIgnoreCase("$title")) {
							v = ro.getTitle();
						} else if (mn.equalsIgnoreCase("$shareLevel")){
							v = ro.getShareLevel()+"";						
						} else
							throw new Exception("attribute " + mn + "not exist");
					} else if (rdo != null) { // normal field
						Attribute attr = rdo.getAttr(mn);

						if (attr == null) {
							Log.warning("delete from index failed, column "
									+ fields.get(i).getName()
									+ " can not find its mapped attribute "
									+ mn);
							v = null;
						} else if (attr.valueType() == Attribute.ATTR_DT_LIST) { // list
							// type
							v = ((List<String>) attr.getValue()).get(n);
						} else
							v = attr.getStringValue();
					}
					if (number != 0)
						stmt.append(" and ");
					if (v == null) // null value does not need single quote
						stmt.append(fields.get(i).getName() + "=" + v);
					else if (field.getType().equalsIgnoreCase("string")
							|| field.getType().equalsIgnoreCase("time"))
						stmt.append(fields.get(i).getName() + "='" + v.replaceAll("'", "\'") + "'");
					else
						// number value does not need single quote
						stmt.append(fields.get(i).getName() + "=" + v);
					// stmt.append(fields.get(i).getName() + "= '" + v + "'");
					number++;

				}
				list.add(stmt.toString());
				Log.trace(stmt);
			}
//		}
		Log.trace("leave");
		return list;
	}

	public boolean isSyncUpdate() {
		return syncUpdate;
	}

	public void setSyncUpdate(boolean syncUpdate) {
		this.syncUpdate = syncUpdate;
	}

}
