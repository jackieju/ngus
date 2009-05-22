/*
 * Created on Dec 4, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ns.dataobject;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import com.ns.log.Log;

/**
 * @author Jackie Ju One attribute includes id, value, can be get by its unique
 *         id
 */
public class Attribute implements Serializable{
	// private int id; // the unique id of the attribute
	private String name; // the unique name of the attribute

	private Object value; // attribute value

	private int valueType; // data type of value, maybe redundant

	private int status; // attribute status

	private DataObject parent; // parent node (DataObject)

	private int iKey = 0; // 0: not key, >0: the index in the key combination

	// status constant
	final static public int ATTR_STATUS_MOD = 1; // modified

	final static public int ATTR_STATUS_INT = 0;

	// just initialized (normally after costruction)
	final static public int ATTR_STATUS_RST = 2;

	// reseted (normally after modified)

	// data type constant
	final static public int ATTR_DT_UNK = 0; // unkonwn, for extension, you
												// can put any object as value

	final static public int ATTR_DT_INT = 1; // integer

	final static public int ATTR_DT_STR = 2; // string

	final static public int ATTR_DT_FLT = 3; // float

	final static public int ATTR_DT_BIN = 6; // binary
	
	final static public int ATTR_DT_LIST = 7; // List

	final static public int ATTR_DT_TMS = 5; // timestamp

	final static public int ATTR_DT_SUBOBJ = 4; // sub object

	private AttributeDesc definition = null;

	public void setDefinition(AttributeDesc d) {
		definition = d;

	}

	public AttributeDesc getDefinition() {
		return definition;
	}

	public Attribute(String sName, int type) {
		name = sName;
		valueType = type;
	}

	public Attribute(String sName, int type, Object value) throws DOException {
		name = sName;
		valueType = type;
		setValue(value);

	}

	public Attribute(String sName, Object value) throws DOException {
		name = sName;
		if (value instanceof Integer)
			valueType = this.ATTR_DT_INT;
		else if (value instanceof String)
			valueType = this.ATTR_DT_STR;
		else if (value instanceof Float)
			valueType = this.ATTR_DT_FLT;
		else if (value instanceof byte[])
			valueType = this.ATTR_DT_BIN;
		else
			throw new DOException("unsupported type");
		setValue(value);

	}

	public Attribute(String sName, int type, String value) throws DOException {
		name = sName;
		valueType = type;
		// try{

		setValueByString(value);
		// }
		// catch (DOException e){
		// Log.error(e);
		// }
	}

	/**
	 * Is the attribute value just updated?
	 */
	public boolean isModified() {
		return status == ATTR_STATUS_MOD;
	}

	/**
	 * set the attribute value
	 */
	public void setValue(Object v) throws DOException {

		if (v == null) {
			// String msg = "null point parameter";
			// Log.warning(msg);
			return;
		}
		try {
			// check value
			switch (valueType) {
			case Attribute.ATTR_DT_INT:
				if (!(v instanceof Integer))
					throw new DOException(
							"value dose not match its type, type is "
									+ v.getClass() + ", value is " + v
									+ ", expected type is Integer");
				break;

			case Attribute.ATTR_DT_STR:
				if (!(v instanceof String))
					throw new DOException(
							"value dose not match its type, type is "
									+ v.getClass() + ", value is " + v
									+ ", expected type is String");
				break;

			case Attribute.ATTR_DT_FLT:
				if (!(v instanceof Double))
					throw new DOException(
							"value dose not match its type, type is "
									+ v.getClass() + ", value is " + v
									+ ", expected type is DOUBLE");

				break;

			case Attribute.ATTR_DT_TMS:
				if (!(v instanceof Timestamp))
					throw new DOException(
							"value dose not match its type, type is "
									+ v.getClass() + ", value is " + v
									+ ", expected type is Timestamp");
				break;

			case Attribute.ATTR_DT_SUBOBJ:
				if (!(v instanceof DataObjectList))
					throw new DOException(
							"value dose not match its type,  type is "
									+ v.getClass() + ", value is " + v
									+ ", expected type is DataObjectList");
				break;
				
			case Attribute.ATTR_DT_LIST:
				if (!(v instanceof List))
					throw new DOException(
							"value dose not match its type,  type is "
									+ v.getClass() + ", value is " + v
									+ ", expected type is List");
				break;

			case Attribute.ATTR_DT_UNK:
			
				break;
				
			default:
				// System.out.println("invalid data type");
				throw new DOException("invalid data type " + v.getClass());
			// return ;
			}
		} catch (DOException e) {

			Log.error(e);
			String msg = "set value failed, value type = " + valueType
					+ ", value=" + v;
			value = null;
			throw new DOException(msg);
		}

		value = v;
		status = ATTR_STATUS_MOD;
	}

	public void setValueByString(String v) throws DOException {
		// if (v == null){
		// String msg = "null point parameter";
		// Log.error(msg);
		// throw new DOException(msg);
		// }
		if (v == null) {
			String msg = "null point parameter";
			Log.warning(msg);
			return;
		}
		try {

			switch (valueType) {
			case Attribute.ATTR_DT_INT:
				value = new Integer(v);
				break;

			case Attribute.ATTR_DT_STR:
				value = new String(v);
				break;

			case Attribute.ATTR_DT_FLT:
				value = new Double(v);
				break;

			case Attribute.ATTR_DT_TMS:
				value = Timestamp.valueOf(v);

			case Attribute.ATTR_DT_LIST:
					throw new DOException("can not use this mothod to set a list");
			default:
				// System.out.println("invalid data type");
				throw new DOException("invalid data type");
			// return ;
			}
		} catch (DOException e) {
			value = null;
			Log.error(e);
			String msg = "set value failed, value type = " + valueType
					+ ", value=" + value;
			throw new DOException(msg);
		}
	}

	/**
	 * Get the attribute value
	 */
	public Object getValue() {
		return value;
	}

	public int getInt() throws DOException{
		if (value == null)
			throw new DOException("value is null");
		if (this.valueType != Attribute.ATTR_DT_INT)
			throw new DOException("attribute type is not integer, but " + this.valueType());
		return ((Integer)value).intValue();
	}
	
	
	public float getFloat() throws DOException{
		if (value == null)
			throw new DOException("value is null");
		if (this.valueType != Attribute.ATTR_DT_FLT)
			throw new DOException("attribute type is not float, but " + this.valueType());
		return ((Float)value).floatValue();
	}
	
	public Timestamp getTimestamp() throws DOException{
		if (value == null)
			throw new DOException("value is null");
		if (this.valueType != Attribute.ATTR_DT_TMS)
			throw new DOException("attribute type is not timestamp, but " + this.valueType());
		return ((Timestamp)value);
	}
	
	public List getList() throws DOException{
		if (value == null)
			throw new DOException("value is null");
		if (this.valueType != Attribute.ATTR_DT_LIST)
			throw new DOException("attribute type is not list, but " + this.valueType());
		return ((List)value);
	}
	
	
	public byte[] getBinary() throws DOException{
		if (value == null)
			throw new DOException("value is null");
		if (this.valueType != Attribute.ATTR_DT_BIN)
			throw new DOException("attribute type is not binary, but " + this.valueType());
		return ((byte[])value);
	}
	
	
	public String getStringValue() {

		String ret;
		if (value == null) {
			return null;
		}

		try {

			switch (valueType) {
			case Attribute.ATTR_DT_INT:
				ret = ((Integer) value).toString();
				break;

			case Attribute.ATTR_DT_STR:
				ret = (String) value;
				break;

			case Attribute.ATTR_DT_FLT:
				ret = ((Double) value).toString();
				break;

			case Attribute.ATTR_DT_TMS:
				ret = ((Timestamp) value).toString();
				break;

			case ATTR_DT_SUBOBJ: {
				ret = "";
				DataObjectList list;
				try {

					list = (DataObjectList) value;
				} catch (Exception e) {
					Log.error(e);
					Log.error(this + "::getStringValue got exception, value= "
							+ value);
					return null;
				}
				for (int i = 0; i < list.size(); i++)
					ret += list.get(i).printXML();
			}
				break;
			case ATTR_DT_LIST: {
				ret="list{";
				List list = (List)value;
				for (int i = 0; i < list.size(); i++)
					ret += list.get(i).toString();
				ret+="}";
			};;
				break;

			default:
				// System.out.println("invalid data type");
				// throw new DOException("invalid data type");
				return null;
			}
		} catch (Exception e) {
			Log.error(e);
			Log.error("getStringValue failed, value = " + this.value
					+ ", name=" + this.name + ", type=" + this.valueType);
			return null;
		}
		return ret;
	}

	public String printHtml() {

		String ret = "<font color=\"#992222\">" + getName() + "</font>=";

		if (valueType == ATTR_DT_SUBOBJ) {

			DataObjectList list;
			try {

				list = (DataObjectList) value;
			} catch (Exception e) {
				Log.error(e);
				Log.error(this + "::getStringValue got exception, value= "
						+ value);
				return null;
			}
			for (int i = 0; i < list.size(); i++)
				ret += list.get(i).printHtml();
		} else
			ret += "<b>" + getStringValue() + "</b>";
		return ret;
	}

	/**
	 * Get attribute name
	 * 
	 * @return
	 */

	public String getName() {
		return name;
	}

	/**
	 * return data type of this attribute
	 * 
	 * @return
	 */
	public int valueType() {
		return valueType;
	}

	/**
	 * get parent object
	 * 
	 * @return
	 */
	public DataObject parent() {
		return parent;
	}

	/**
	 * set if the attribute is a key
	 * 
	 * @param nKeyIndex
	 *            0: not key, >0: the index in the key combination
	 */
	public void setKey(int nKeyIndex) {
		iKey = nKeyIndex;

	}

	public int compareTo(Attribute key) throws DOException {
		if (key == null) {
			throw new DOException(this + "::compareTo(): invalid parameter");
		}

		String s = key.getStringValue();
		return s.compareTo(getStringValue());
	}

	public void setInt(int p) throws DOException{
//		if (value == null)
//			throw new DOException("value is null");
		if (this.valueType != Attribute.ATTR_DT_INT)
			throw new DOException("attribute type is not integer, but " + this.valueType());
		value = p;
	}
	
	public void setFloat(float p) throws DOException{
//		if (value == null)
//			throw new DOException("value is null");
		if (this.valueType != Attribute.ATTR_DT_FLT)
			throw new DOException("attribute type is not FLOAT, but " + this.valueType());
		value = p;
	}
	
	public void setTimestamp(Timestamp p) throws DOException{
//		if (value == null)
//			throw new DOException("value is null");
		if (this.valueType != Attribute.ATTR_DT_TMS)
			throw new DOException("attribute type is not timestamp, but " + this.valueType());
		value = p;
	}
	
	public void setList(List p) throws DOException{
//		if (value == null)
//			throw new DOException("value is null");
		if (this.valueType != Attribute.ATTR_DT_LIST)
			throw new DOException("attribute type is not list, but " + this.valueType());
		value = p;
	}
	
	public void setBinary(byte[] p) throws DOException{
//		if (value == null)
//			throw new DOException("value is null");
		if (this.valueType != Attribute.ATTR_DT_BIN)			
			throw new DOException("attribute type is not binary, but " + this.valueType());
		value = p;
	}
	// /**
	// * Get Attribute id
	// * @return
	// */
	// public String getId(){
	// return id;
	// }

}