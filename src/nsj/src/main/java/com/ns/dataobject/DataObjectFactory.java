/*
 * Created on Dec 8, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ns.dataobject;
import java.io.File;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.ns.log.Log;

/**
 * @author Jackie Ju
 * The factory for creating attribute using AttributeDesc
 */
public class DataObjectFactory {

	//	static private AttributeFactory _instance = null;

	//	synchronized static public AttributeFactory instance() throws Exception{	
	//		
	//	if (_instance == null)			
	//		_instance = new AttributeFactory();
	//	
	//	return _instance;
	//	}

	//	private HashMap attributeDescList = new HashMap();
	// the mapping from attribute name to attribute description
	private HashMap objectDescList = new HashMap();

	//private HashMap DataMinerList;	// will not used temperary

	// the mapping from the attribute data source to DataMiner
	//	private dbProxy db;

	//	protected AttributeFactory() throws Exception {
	//		Init(); // load all description for attributes from database
	//	}
	static {
		try {

			Log.set(
				"/usr/tmp/acc/lpm.log",
				true,
				true,
				true,
				true,
				1000,
				1000,
				true,
				10000);
		} catch (Exception e) {

		}
	}
	
	DataSource ds;
	String user;
	/**
	 * @return Returns the ds.
	 */
	public DataSource getDs() {
		return ds;
	}
	/**
	 * @param ds The ds to set.
	 */
	public void setDs(DataSource ds) {
		this.ds = ds;
	}
	/**
	 * @return Returns the objectDescList.
	 */
	public HashMap getObjectDescList() {
		return objectDescList;
	}
	/**
	 * @param objectDescList The objectDescList to set.
	 */
	public void setObjectDescList(HashMap objectDescList) {
		this.objectDescList = objectDescList;
	}
	/**
	 * @return Returns the pwd.
	 */
	public String getPwd() {
		return pwd;
	}
	/**
	 * @param pwd The pwd to set.
	 */
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	/**
	 * @return Returns the url.
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * @param url The url to set.
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * @return Returns the user.
	 */
	public String getUser() {
		return user;
	}
	/**
	 * @param user The user to set.
	 */
	public void setUser(String user) {
		this.user = user;
	}
	String pwd;
	String url;

	public DataObjectFactory(String dataSource) throws Exception {
		try {
			Context initCtx = new InitialContext();
			this.ds = (DataSource) initCtx.lookup(dataSource);
		} catch (NamingException namex) {
			namex.printStackTrace();
			Log.error(namex);
			throw new Exception("Unable to create Connection Pool");
		}
	}
	
	public DataObjectFactory(String jdbcDriver, String url, String user, String pwd) throws Exception {
		try {
			//DriverManager.registerDriver(new com.microsoft.jdbc.sqlserver.SQLServerDriver());

			Class.forName(jdbcDriver);
			this.user = user;
			this.pwd = pwd;
			this.url = url;
		} catch (Exception e) {
			
			Log.error(e);
			throw new Exception("Unable to load jdbc driver");
		}
	}
	
	private Connection getConnection( )throws Exception  {
		if (ds != null)
			return ds.getConnection();
		else
		{
			return java.sql.DriverManager.getConnection(url, user, pwd);
		}
		
	}
//	public listAllSchema(){
//		
//	}

	/*
	 * Load attribute descriptions from XML
	 */
	public void loadFromXML(String fileName) throws Exception {

		//get a DocumentBuilderFactory from the underlying implementation
		DocumentBuilderFactory m_factory = null;
		DocumentBuilder m_builder = null;
		Document doc = null;
		Node root = null;
		//		try {
		m_factory = DocumentBuilderFactory.newInstance();

		//factory.setValidating(true);

		//get a DocumentBuilder from the factory
		m_builder = m_factory.newDocumentBuilder();

		doc = m_builder.parse(new File(fileName));

		//		} catch (Exception e) {
		//			Log.error(e);
		//			return;
		//		}

		if (doc == null) {
			Log.error("parse failed");
			throw new Exception("parse failed");
		}

		// get root
		root = doc.getDocumentElement();
		if (root == null) {
			Log.error("get root element failed");
			throw new Exception("get root element failed");
		}

		Log.trace(root.getNodeName());

		Node node = null;
		NodeList childs = root.getChildNodes();
		for (int i = 0; i < childs.getLength(); i++) {
			Node child = childs.item(i);
			if (child.getNodeType() != Node.ELEMENT_NODE)
				continue;
			String nodeName = child.getNodeName();
			if (!nodeName.equals("class"))
				continue;
			try {
				loadClass(null, (Element) child);
			} catch (Exception e) {
				Log.error(e);
				throw new Exception("load " + i + "th class failed: " + e);
			}
		}

	}

	private void loadClass(DataObjectDesc parent, Element ele)
		throws Exception {

		String object_name = "";
		String table_name = "";
		String description = "";
		if (ele == null) {
			return;
		}

		object_name = ele.getAttribute("name");
		table_name = ele.getAttribute("table");

		DataObjectDesc pNew =
			new DataObjectDesc(object_name, table_name, description);

		// load attribute
		NodeList childs = ele.getChildNodes();
		if (childs != null) {
			for (int i = 0; i < childs.getLength(); i++) {
				Node child = childs.item(i);
				if (child.getNodeType() != Node.ELEMENT_NODE)
					continue;
				Element c = (Element)child;
				String name = c.getNodeName();
				if (name.equalsIgnoreCase("member"))
					loadAttribute(pNew, c);
				else 				if (name.equalsIgnoreCase("class"))
				loadClass(pNew, c);

			}
		}

	

		if (parent != null) {
			parent.addChild(pNew);
		} else {
			// add to root list
			addDODesc(pNew);
		}

	}

	private void loadAttribute(DataObjectDesc parent, Element ele)
		throws Exception {
		if (ele == null || parent == null) {
			Log.error("invalid parameter");
			return;
		}

		String name = "";
		int type = 0;
		String colName = "";
		String length = "";
		String key = "";

		name = ele.getAttribute("name");
		colName = ele.getAttribute("column");
		key = ele.getAttribute("key");
		String typeStr = ele.getAttribute("type");
		length = ele.getAttribute("length");
		if (typeStr == null) {
			throw new Exception("Attribute '" + name + "' has no type");
		}
		if (typeStr.equalsIgnoreCase("long")) {
			type = Attribute.ATTR_DT_INT;
		} else if (
			typeStr.equalsIgnoreCase("String")
				|| typeStr.equalsIgnoreCase("String")) {
			type = Attribute.ATTR_DT_STR;
		} else if (typeStr.equalsIgnoreCase("float")) {
			type = Attribute.ATTR_DT_FLT;
		} else if (typeStr.equalsIgnoreCase("time")) {
			type = Attribute.ATTR_DT_TMS;
		}

		AttributeDesc attr =
			new AttributeDesc(
				name,
				type,
				colName,
				key.equalsIgnoreCase("yes"),
				Integer.parseInt(length));

		parent.addAttrDesc(attr);

	}
	//	public void addAttrDesc(AttributeDesc desc) {
	//		Log.trace(this+"::addAttrDesc(AttributeDesc): add attribute desc " + desc.attrName());
	//		attributeDescList.put(desc.attrName(), desc);
	//	}

	private void addDODesc(DataObjectDesc desc) {
		Log.trace(
			this
				+ "::addDODesc(DataObjectDesc, boolean): add do desc "
				+ desc.name());
		objectDescList.put(desc.name(), desc);
	}

	//	public Attribute create(String name, String value) {
	//
	//		// find description for this attribute by name
	//		AttributeDesc ad = (AttributeDesc) attributeDescList.get((Object) name);
	//		if (ad == null)
	//			return null;
	//
	//		// create attribute object
	//		Attribute attr = new Attribute(ad.attrName(), ad.dataType());
	//	
	//		try{
	//		
	//		// set value;
	//		switch (ad.dataType()) {
	//			case Attribute.ATTR_DT_INT :
	//				attr.setValue(new Integer(value));
	//				break;
	//
	//			case Attribute.ATTR_DT_STR :
	//				attr.setValue(value);
	//				break;
	//			case Attribute.ATTR_DT_FLT :
	//				attr.setValue(new Double(value));
	//				break;
	//			default :
	//				//System.out.println("invalid data type");
	//				Log.error("invalid data type");
	//				return null;
	//		}
	//		}catch (Exception e)
	//		{
	//			Log.error(e);
	//			return null;
	//		}
	//		return attr;
	//	}

	//	/**
	//	 * get one attribute description by name
	//	 * @param name
	//	 * @return
	//	 */
	//	public AttributeDesc getAttrDesc(String name) {
	//		return (AttributeDesc) attributeDescList.get(name);
	//	}

	/**
	 * Get data object description by objecte name
	 * @param name
	 * @return
	 */
	public DataObjectDesc getDODesc(String name) {
		/*
		if (name.equalsIgnoreCase("serviceStatic"))
		{
			try{
				throw new Exception("normal");
			}
			catch(Exception e)
			{
				Log.error(e);
			}
		}
		*/
		DataObjectDesc ret = (DataObjectDesc) objectDescList.get(name);
		Log.trace(this +"::getDODesc(" + name + "): return " + ret);
		return ret;
	}

	/**
	 * Get all Object description
	 * @return
	 */
	public Collection getAllObjDesc() {
		return objectDescList.values();
	}

	private String getColumns(DataObjectDesc desc) throws Exception {
		if (desc == null)
			throw new Exception("null parameter");

		String ret = "";
		for (int i = 0; i < desc.getAttrNum(); i++) {
			AttributeDesc attrDesc = desc.getAttr(i);
			if (i > 0)
				ret += ", ";
			ret += attrDesc.dbColumnName();
		}
		ret += "";
		return ret;
	}
	/**
	 * query object 
	 * @param parent Parent data Object, chould be null if it is root
	 * @param name the name of data object
	 * @param where where clause
	 * @param conditions
	 * @return
	 */
	public DataObjectList queryObject(
		DataObject parent,
		String name,
		String where,
		Vector conditions)
		throws Exception {
			
		Log.trace("enter");
		
		DataObjectList ret = new DataObjectList();

		// create select statment from ancestors
		Vector condition = new Vector();
		DataObject p = parent;
		while (p != null) {
			Vector v = genWhereByKey(p);
			for (int i = 0; i < v.size(); i++)
				condition.add(v.get(i));
			p = p.getParent();
		}

		DataObjectDesc desc = null;

		if (parent != null) {
			DataObjectDesc descParent = null;
			// get DataObject desc
			descParent = getDataObjectDesc(parent);
			// desc of parent
			if (descParent == null) {
				throw new Exception(
					"can not get desc for DataObject " + parent.printXML());
			}

			desc = (DataObjectDesc) descParent.children().get(name);
			// desc of target object
			if (desc == null) {
				throw new Exception(
					"can not get child desc named "
						+ name
						+ " for DataObject "
						+ parent.printXML());
			}
		} else {

			desc = getDODesc(name);

			if (desc == null)
				throw new Exception(
					"can not get data object desc named "
						+ name
						+ " from root ");
		}
		//		// generate sql statement
		//		stmt += " and " + genWhereClause(desc, where);
		
		String header =
			"select " + getColumns(desc) + " from " + desc.tableName();

		Connection con = null;
		try {
			Log.trace("get connection...");
			con = getConnection();
			Log.trace("get connection ok");
			ResultSet r = select(con, header, conditions, where);
			if (r != null)
				while (r.next()) {
					DataObject newObj = createObject(desc, r);
					ret.add(newObj);
				}
		} finally {
			con.close();
		}
		Log.trace("leave");
		return ret;
	}

	public DataObjectList queryObjectByKeys(DataObject parent, String name, Attributes attsList) throws Exception{
		return this.queryObject(parent, name, genWhereClauseByAttrs(attsList), null);
		
	}
	private DataObject createObject(DataObjectDesc desc, ResultSet r)
		throws Exception {
		if (desc == null || r == null)
			throw new Exception("null parameter");

		DataObject ret = new DataObject(desc.name());
		ret.setDefinition(desc);

		for (int i = 0; i < desc.getAttrNum(); i++) {
			AttributeDesc attrDesc = desc.getAttr(i);
			Attribute attr =
				new Attribute(
					attrDesc.attrName(),
					attrDesc.dataType(),
					r.getObject(i + 1));
			ret.addAttr(attr);
		}

		return ret;

	}
//	private Connection getConnection() throws Exception {
//		return ds.getConnection();
//	}

	/**
	 * generate sql statement by key members
	 * @param parent
	 * @return
	 */
	private String genWhereClauseByKey(DataObject parent) throws Exception {
		String ret = "";
		DataObjectDesc desc = getDataObjectDesc(parent);
		if (desc == null) {
			throw new Exception(
				"can not get definiton for Object: " + parent.printXML());
		}
		ArrayList keys = desc.getKeyAttrs();
		for (int i = 0; i < keys.size(); i++) {
			AttributeDesc attrDesc = (AttributeDesc) keys.get(i);
			Attribute attr = parent.getAttr(attrDesc.attrName());
			if (attr == null)
				throw new Exception(
					"get key attribute "
						+ attrDesc.print()
						+ " in object "
						+ parent.printXML()
						+ " failed.");
			ret += attrDesc.attrName() + "=" + attr.getStringValue();
		}
		return ret;
	}
	
	private String genWhereClauseByAttrs(Attributes attrList) throws Exception{
		String ret = "";
		for (int i = 0; i < attrList.size(); i++) {
			//AttributeDesc attrDesc = (AttributeDesc) keys.get(i);
			Attribute attr = (Attribute)attrList.get(i);
			if (attr == null)
				throw new Exception(
					"get key attribute failed.");
			ret += attr.getName() + "=" + attr.getStringValue();
		}
		return ret;
	}

	private Vector genWhereByKey(DataObject parent) throws Exception {
		Vector ret = new Vector();
		DataObjectDesc desc = getDataObjectDesc(parent);
		if (desc == null) {
			throw new Exception(
				"can not get definiton for Object: " + parent.printXML());
		}
		ArrayList keys = desc.getKeyAttrs();
		for (int i = 0; i < keys.size(); i++) {
			AttributeDesc attrDesc = (AttributeDesc) keys.get(i);
			Attribute attr = parent.getAttr(attrDesc.attrName());
			if (attr == null)
				throw new Exception(
					"get key attribute "
						+ attrDesc.print()
						+ " in object "
						+ parent.printXML()
						+ " failed.");
			Vector v = new Vector();
			v.add(attr.getName());
			v.add(attr.getValue());
			ret.add(v);
		}
		return ret;
	}

	private ResultSet select(
		Connection con,
		String header,
		Vector condition,
		String where)
		throws Exception {
			Log.trace("enter");
		if (con == null)
			throw new Exception("null parameeter");
		PreparedStatement stmt = null;

		// create select statement
		String sql = header;
		if (condition != null || (where != null && where.length() > 0))
			sql += " where ";

		if (where != null)
			sql += where;

		if (condition != null) {
			// add condition
			for (int i = 0; i < condition.size(); i++) {
				Vector r = (Vector) condition.get(i);
				if (i > 0)
					sql += " and ";
				sql += (String) r.get(0) + "=?";
			}
		}
		Log.trace("query: " + sql );
		stmt = con.prepareStatement(sql);

		if (condition != null) {
			for (int i = 0; i < condition.size(); i++) {
				Vector r = (Vector) condition.get(i);
				Object ele = r.get(1);

				if (ele == null) {
					//stmt.getPreparedStmt().setNull(i+1, java.sql.Types.TIMESTAMP);
					stmt.setString(i + 1, " ");
				} else if (ele instanceof Integer)
					stmt.setInt(i + 1, ((Integer) ele).intValue());
				else if (ele instanceof String) {
					String s = (String) ele;
					if (((String) ele).length() < 1) {
						//					Log.warning("String literal can not be empty, now one space added automatically");
						//					s = (String)ele + " ";  ]
						//stmt.getPreparedStmt().setNull(i+1, java.sql.Types.VARCHAR);
						//stmt.getPreparedStmt().setString(i+1, " ");
						stmt.setString(i + 1, " ");
					} else
						stmt.setString(i + 1, (String) ele);
				} else if (ele instanceof Timestamp)
					stmt.setTimestamp(i + 1, (Timestamp) ele);
				else if (ele instanceof Float)
					stmt.setFloat(i + 1, ((Float) ele).floatValue());
				else if (ele instanceof Double)
					stmt.setDouble(i + 1, ((Double) ele).doubleValue());
				else if (ele instanceof Date)
					stmt.setDate(i + 1, (Date) ele);
				else if (ele instanceof Time)
					stmt.setTime(i + 1, (Time) ele);
				else {
					Log.error("object " + ele + " is not supported");
				}
				Log.trace("value(" + i + "): " + ele);
			}
		}

		// select
		ResultSet r = stmt.executeQuery();
		Log.trace("leave");
		return r;

	}

	public DataObjectDesc getDataObjectDesc(DataObject parent) {
		if (parent == null) {
			return null;
		}
		if (parent.getDefinition() != null)
			return parent.getDefinition();

		DataObjectDesc ret;
		Vector names = new Vector();
		DataObject p = parent;
		while (p.getParent() != null) {
			names.add(p.getName());
			p = p.getParent();
		}
		ret = getDODesc(p.getName());
		for (int i = names.size() - 1; i <= 0; i--) {
			ret = (DataObjectDesc) ret.children().get(names.get(i));
		}

		parent.setDefinition(ret);
		return ret;
	}
	//
	//	private String genWhereClause(desc, where) {
	//
	//	}
	public String printXml() {

		String ret = "<dataStruct>";
		
		Iterator it;
		for (it = objectDescList.values().iterator(); it.hasNext();) {
			DataObjectDesc desc = (DataObjectDesc) it.next();
			ret += desc.print();

		}
		
		ret += "</dataStruct>";
		return ret;
	}
}
