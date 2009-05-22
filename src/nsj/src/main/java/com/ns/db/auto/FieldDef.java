package com.ns.db.auto;

/**
 * use this class to force not using default value when inserting
 * @author jackie
 *
 */
public class FieldDef {
	String name;
	String type;
	String mappedName;
	
	public String getMappedName() {
		return mappedName;
	}

	public void setMappedName(String mappedName) {
		this.mappedName = mappedName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public FieldDef(String name, String mappedName, String type){
		this.name = name;
		this.mappedName = mappedName;
		this.type = type;
	}
	
	public String convertType(String type) throws Exception{
		if (type.equalsIgnoreCase("String"))
			return "varchar(255)";
		else if (type.equalsIgnoreCase("number"))
			return "integer";
		else if (type.equalsIgnoreCase("time"))
			return "datetime";
		throw new Exception ("unknown field type" + type);
	}
	
	public String genCreateSQLString() throws Exception{
		return name + " " + convertType(type);
	}
}
