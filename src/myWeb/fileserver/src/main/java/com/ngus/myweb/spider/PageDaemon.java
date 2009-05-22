package com.ngus.myweb.spider;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ngus.config.SystemProperty;
import com.ngus.dataengine.DBConnection;
import com.ngus.dataengine.NGUSThread;
import com.ns.db.DBC;
import com.ns.log.Log;

public class PageDaemon extends ResDaemon {

	public PageDaemon(HashMap arg0) {
		super(arg0);

		// TODO Auto-generated constructor stub
	}

	public List getUrls() {
		ArrayList<Resource> list = new ArrayList<Resource>();
		// select resId, url, title from myweb_url

		String sql = "select resId, url from myweb_url where title is null";
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
				Log.error("spider", e);
			} finally {

				if (st != null) {
					try {
						st.close();
					} catch (SQLException e) {
						Log.error("spider", e);
					}

					st = null;
				}

			}

		} finally {
			try {
				if (c != null)
					c.close();
			} catch (SQLException e) {
				Log.error("spider", e);
			}
		}
		return list;

	}

	public void updateDB(String resid, String title) {
		ArrayList<Resource> list = new ArrayList<Resource>();
		// select resId, url, title from myweb_url
		String sql = "update myweb_url set title='" + title + "' where resid='"
				+ resid + "'";
		Statement st = null;
		Connection c = null;
		try {
			try {
				c = getConnection();
				st = c.createStatement();
				int n = st.executeUpdate(sql);
			} catch (Exception e) {
				Log.error("spider", e);
			} finally {

				if (st != null) {
					try {
						st.close();
					} catch (SQLException e) {
						Log.error("spider", e);
					}

					st = null;
				}

			}

		} finally {
			try {
				if (c != null)
					c.close();
			} catch (SQLException e) {
				Log.error("spider", e);
			}
		}

		Log.trace("spider", "update db return n");

	}

	@Override
	public void daemonRun() throws Exception {
		List<Resource> list = getUrls();
		Log.trace("spider", "getUrls return " + list.size() + "urls");
		// int success = 0;
		for (int i = 0; i < list.size(); i++) {
			Resource page = list.get(i);

			if (!lockRow(page.getResId()))
				continue;
			String url = list.get(i).getUrl();
			String title = HtmlTitleCatcher.getPageTitle(url);
			if (title == null)
				title = "";
			// if (title != null && title.length() > 0)
			updateDB(page.getResId(), title);
			unlockRow(page.getResId());
			Log.trace("spider", "title of page " + url + ": " + title);

		}

	}

}
