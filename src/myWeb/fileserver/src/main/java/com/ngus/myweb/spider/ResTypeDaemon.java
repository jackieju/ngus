package com.ngus.myweb.spider;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.ngus.config.Path;
import com.ngus.config.SystemProperty;
import com.ns.log.Log;
import com.ns.util.XmlUtil;

public class ResTypeDaemon extends ResDaemon {
	static public class ResType {

		String name;

		ArrayList<Pattern> patternList = new ArrayList<Pattern>();

		public ResType(String name) {
			super();
			// TODO Auto-generated constructor stub
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public ArrayList<Pattern> getPatternList() {
			return patternList;
		}

		// public void setPatternList(ArrayList<Pattern> patternList) {
		// this.patternList = patternList;
		// }
		public void addPattern(Pattern p) {
			patternList.add(p);
		}
	}

	static public class Pattern {
		String matchType;

		String pattern;

		ArrayList<String> types = new ArrayList<String>();

		public Pattern(String matchType, String pattern) {
			super();
			// TODO Auto-generated constructor stub
			this.matchType = matchType;
			this.pattern = pattern;
		}

		public String getMatchType() {
			return matchType;
		}

		public void setMatchType(String matchType) {
			this.matchType = matchType;
		}

		public String getPattern() {
			return pattern;
		}

		public void setPattern(String pattern) {
			this.pattern = pattern;
		}

		public ArrayList<String> getType() {
			return types;
		}

		public void addType(String t) {
			if (t != null)
				types.add(t);
		}

	}

	public static ArrayList<ResType> resTypeList = new ArrayList<ResType>();

	static {
		try {

			// load pattern list from file
			String file = Path.getAbsoluteFileSystemPath(SystemProperty
					.getProperty("ngus.myweb.restype.urlpattern.patternfile"));
			Log.trace("spider", "load pattern list from file " + file + "...");
			Document doc = XmlUtil.loadDom(file);
			Element root = doc.getDocumentElement();
			NodeList list = root.getElementsByTagName("resType");
			for (int i = 0; i < list.getLength(); i++) {
				Element ele = (Element) list.item(i);
				String typeName = ele.getAttribute("name");
				ResType rt = new ResType(typeName);
				if (typeName == null || typeName.length() == 0)
					continue;
				resTypeList.add(rt);
				Log.trace("spider", "type name: " + typeName);
				NodeList l = ele.getElementsByTagName("pattern");
				Log.trace("spider", l.getLength() + " patter elements");
				for (int j = 0; j < l.getLength(); j++) {
					Element pn = (Element) l.item(j);
					String type = pn.getAttribute("type");
					String value = pn.getTextContent();
					Log.trace("spider", "type=" + type + ", value=" + value);
					if (type == null || value == null || type.length() == 0
							|| value.length() == 0)
						continue;
					Pattern p = new Pattern(type, value);
					p.addType(typeName);
					rt.addPattern(p);
					ResTypeSpider.patterns.add(p);
					Log.trace("spider", "match type: " + type + ", pattern: "
							+ p);
				}

			}
			Log.trace("spider", "load pattern list ok");
		} catch (Exception e) {
			Log.error("spider", "", e);
		}
	}

	public ResTypeDaemon(HashMap arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public List getUrls() {
		ArrayList<Resource> list = new ArrayList<Resource>();
		// select resId, url, title from myweb_url

		String sql = "select ngus_resbytype.resid, url from myweb_url, ngus_resbytype where ngus_resbytype.resid=myweb_url.resid and ngus_resbytype.checked=-1";
		Connection c = null;
		Statement st = null;
		try {

			try {
				c = getConnection();
				st = c.createStatement();
				ResultSet rs = st.executeQuery(sql);
				while (rs != null && rs.next()) {
					Resource page = new Resource(rs.getString(1), rs
							.getString(2));
					list.add(page);
				}

			} catch (Exception e) {
				Log.error("spider", "", e);
			} finally {

				if (st != null) {
					try {
						st.close();
					} catch (SQLException e) {
						Log.error("spider", "", e);
					}

					st = null;
				}

			}

		} finally {
			try {
				if (c != null)
					c.close();
			} catch (SQLException e) {
				Log.error("spider", "", e);
			}
		}
		return list;

	}

	public void updateDB(String resid, String type, int checked) {
		ArrayList<Resource> list = new ArrayList<Resource>();
		// select resId, url, title from myweb_url
		String sql = null;
		if (checked > 1) // get type succeeded
			sql = "update ngus_resbytype set realtype='" + type + "', checked="
					+ checked + " where resid='" + resid + "'";
		else
			sql = "update ngus_resbytype set checked=" + checked
					+ " where resid='" + resid + "'";

		// Log.trace("spider", sql);
		Statement st = null;
		Connection c = null;
		try {
			try {
				c = getConnection();
				st = c.createStatement();
				int n = st.executeUpdate(sql);
			} catch (Exception e) {
				Log.error("spider", "", e);
			} finally {

				if (st != null) {
					try {
						st.close();
					} catch (SQLException e) {
						Log.error("spider", "", e);
					}

					st = null;
				}

			}

		} finally {
			try {
				if (c != null)
					c.close();
			} catch (SQLException e) {
				Log.error("spider", "", e);
			}
		}

		Log.trace("spider", "update db return n");

	}

	@Override
	public void daemonRun() throws Exception {

		List<Resource> list = getUrls();
		Log.trace("spider", "get Urls return " + list.size() + " urls");
		// int success = 0;
		for (int i = 0; i < list.size(); i++) {
			Resource page = list.get(i);

			if (!lockRow(page.getResId()))
				continue;
			String url = list.get(i).getUrl();
			int checked = -1;
			String type = null;
			try {
				type = ResTypeSpider.getResType(url);
				if (type != null && type.length() > 0)
					checked = 2; // succeeded
				else
					checked = 1; // unknown type
			} catch (Exception e) {
				checked = 0; // resource not exist
			}
			updateDB(page.getResId(), type, checked);
			unlockRow(page.getResId());
			Log.trace("spider", "type of resource " + url + ": " + type);

		}

	}

}
