package com.ngus.myweb.daemon;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ngus.config.Path;
import com.ngus.config.SystemProperty;
import com.ngus.myweb.spider.PageSnapshot;
import com.ngus.myweb.spider.ResDaemon;
import com.ngus.myweb.spider.Resource;
import com.ngus.resengine.ResourceId;
import com.ns.log.Log;
import com.ns.util.Base64;
import com.ns.util.FileUtil;
import com.ns.util.Image;
import com.ns.util.Security;

public class WebPageDaemon extends ResDaemon{
	String file_server_pagesnapshot_root_path;
	int width=100;
	int height=100;
	String DISPLAY;

	public WebPageDaemon(HashMap arg0) {
		super(arg0);

		// get system property
//		 get file server root path
//		String root = SystemProperty.getProperty("ngus.myweb.fs.pagesnapshot.root");
//		while (root.endsWith("/") || root.endsWith("\\")) {
//			root = root.substring(0, root.length() - 1);
//		}

//		width = Integer.parseInt(SystemProperty
//				.getProperty("ngus.myweb.spider.pagesnapshot.width"));
//		height = Integer.parseInt(SystemProperty
//				.getProperty("ngus.myweb.spider.pagesnapshot.height"));
//
//		file_server_pagesnapshot_root_path = Path.getAbsoluteFileSystemPath(root);
		
		width = Integer.parseInt((String)arg0.get("width"));
		height = Integer.parseInt((String)arg0.get("height"));
		file_server_pagesnapshot_root_path = (String)arg0.get("root");
		file_server_pagesnapshot_root_path = Path.getAbsoluteFileSystemPath(file_server_pagesnapshot_root_path);
		DISPLAY = (String)arg0.get("display");
		String working_dir = (String)arg0.get("workingDir");
		String command = (String)arg0.get("command");
		PageSnapshot.setWorkingDir(working_dir);
		PageSnapshot.setCommand(command);

	}

	static public class _URL{
		String url;
		int count; // count being added to favorites
		public int getCount() {
			return count;
		}
		public void setCount(int count) {
			this.count = count;
		}
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public _URL(String url, int count) {
			super();
			// TODO Auto-generated constructor stub
			this.url = url;
			this.count = count;
		}
		
	}
	public List<_URL> getUrls() {
		ArrayList<_URL> list = new ArrayList<_URL>();
		// select resId, url, title from myweb_url

		String sql = 
			"SELECT url, count(*) from myweb_res, ngus_resbytype " +
			"where myweb_res.url != '' and myweb_res.sharelevel < 1 and ngus_resbytype.realtype = 'webpage' and ngus_resbytype.checked >1 and myweb_res.resid = ngus_resbytype.resid group by url order by count(*) desc limit 0, 100"
			;
			
		Connection c = null;
		Statement st = null;
		try {

			try {
				c = getConnection();
				st = c.createStatement();
				ResultSet rs = st.executeQuery(sql);
				while (rs != null && rs.next()) {
					_URL page = new _URL(rs.getString(1), rs.getInt(2));
					list.add(page);
				}

			} catch (Exception e) {
				Log.error("daemon.webpage", "", e);
			} finally {

				if (st != null) {
					try {
						st.close();
					} catch (SQLException e) {
						Log.error("daemon.webpage", "", e);
					}

					st = null;
				}

			}

		} finally {
			try {
				if (c != null)
					c.close();
			} catch (SQLException e) {
				Log.error("daemon.webpage", "", e);
			}
		}
		return list;

	}

	public String genFileName(String url, int width, int height) throws Exception{
		String fileName = Base64.encode(Security.MD5_Digest(url));
		fileName = fileName.replaceAll("\\\\", "");
		fileName = fileName.replaceAll("/", "");
		return fileName;
	}
	
	public String genFilePath(String url, int width, int height) throws Exception {		
		return file_server_pagesnapshot_root_path + "/" + genFileName(url, width, height) +"-" + width+"_"+height+".jpg";
	}

	public String updateFS(String url) throws Exception {
		// create thumbnail
		String path = genFilePath(url, width, height);		
		File fo = new File(path);
		FileUtil.createPath(fo.getParentFile().getAbsolutePath());
		PageSnapshot.genImage(url, width, height, path, DISPLAY);
		
		// create big image
		path = genFilePath(url, 500, 500);		
		fo = new File(path);
		FileUtil.createPath(fo.getParentFile().getAbsolutePath());
		PageSnapshot.genImage(url, 500, 500, path, DISPLAY);
		Log.trace("daemon.webpage", "update fs " + fo.getPath() + " ok");
		return path;
	}

	public void updateDB(_URL url, String path) {
		// select resId, url, title from myweb_url
		String sql1 = null;
		String sql3 = null;
		sql1 = "update myweb_pagejpg set count="+url.getCount()+", jpgpath='"+path+"' where url='" + url.getUrl() + "'";
		sql3 = "insert into myweb_pagejpg values('"+url.getUrl() + "', "+url.getCount()+", '"+path+"')";

		String sql2 = "select * from myweb_pagejpg where url='"+url.getUrl()+"'";
		// Log.trace("daemon.webpage", sql);
		Statement st = null;
		Connection c = null;
		boolean existing = false;
		try {
			c = getConnection();
			
			// select
			try {				
				st = c.createStatement();
				ResultSet rs = st.executeQuery(sql2);
				if (rs !=null && rs.next()){
					existing = true;				
				}				
			} catch (Exception e) {
				Log.error("daemon.webpage", "", e);
			} finally {

				if (st != null) {
					try {
						st.close();
					} catch (SQLException e) {
						Log.error("daemon.webpage", "", e);
					}

					st = null;
				}

			}
			if (existing){
				try {
				
					st = c.createStatement();
					Log.trace("daemon.webpage", "sql:"+sql1);
					int n = st.executeUpdate(sql1);		
					Log.trace("daemon.webpage", "exexute sql:'"+ sql1+"' return "+n);
				} catch (Exception e) {
					Log.error("daemon.webpage", "", e);
				} finally {

					if (st != null) {
						try {
							st.close();
						} catch (SQLException e) {
							Log.error("daemon.webpage", "", e);
						}

						st = null;
					}

				}
			}
			else{
				try {
				
					st = c.createStatement();
					Log.trace("daemon.webpage", "sql:"+sql3);
					int n = st.executeUpdate(sql3);
					Log.trace("daemon.webpage", "exexute sql:'"+ sql3+"' return "+n);			
				} catch (Exception e) {
					Log.error("daemon.webpage", "", e);
				} finally {

					if (st != null) {
						try {
							st.close();
						} catch (SQLException e) {
							Log.error("daemon.webpage", "", e);
						}

						st = null;
					}

				}
			}

		} catch(Exception ee){
			Log.error("daemon.webpage", "", ee);
		}	finally {
		
			try {
				if (c != null)
					c.close();
			} catch (SQLException e) {
				Log.error("daemon.webpage", "", e);
			}
		}

		

	}

	@Override
	public void daemonRun() throws Exception {

		List<_URL> list = getUrls();
		Log.trace("daemon.webpage", "get Urls return " + list.size() + " urls");
		// int success = 0;
		for (int i = 0; i < list.size(); i++) {
			_URL page = list.get(i);		
			String url = page.getUrl();
			Log.trace("daemon.webpage", url);
			try {
				String path = "";
				try{
				 path = updateFS(url);
				}catch(Exception e){
					Log.error("daemon.webpage", "", e);
				}
				updateDB(page, genFilePath(url, width, height));
				Log.trace("daemon.webpage", "generate page snapshot for " + url + " ok.");
			} catch (Exception e) {
				Log.error("daemon.webpage", "", e);
			}
			
			Log.trace("daemon.webpage", "generate thumbnail for " + url + " ok");

		}
	}

}
