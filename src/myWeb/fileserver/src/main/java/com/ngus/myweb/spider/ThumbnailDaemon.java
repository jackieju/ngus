package com.ngus.myweb.spider;


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
import com.ngus.resengine.ResourceId;
import com.ns.log.Log;
import com.ns.util.FileUtil;
import com.ns.util.Image;


public class ThumbnailDaemon extends ResDaemon {

	String file_server_img_root_path = "";

	int width = 100;

	int height = 100;

	String format = "jpeg";

	public ThumbnailDaemon(HashMap arg0) {
		super(arg0);


		
		
		// get file server root path
		String root = SystemProperty.getProperty("ngus.myweb.fs.imageroot");
		while (root.endsWith("/") || root.endsWith("\\")) {
			root = root.substring(0, root.length() - 1);
		}
		
		width = Integer.parseInt((String)arg0.get("width"));
		height = Integer.parseInt((String)arg0.get("height"));
		
//		width = Integer.parseInt(SystemProperty
//				.getProperty("ngus.myweb.spider.thumbnail.width"));
//		height = Integer.parseInt(SystemProperty
//				.getProperty("ngus.myweb.spider.thumbnail.height"));

		file_server_img_root_path = Path.getAbsoluteFileSystemPath(root);

	}

	public List getUrls() {
		ArrayList<Resource> list = new ArrayList<Resource>();
		// select resId, url, title from myweb_url

		String sql = "select ngus_resbytype.resid, url from myweb_url, ngus_resbytype where ngus_resbytype.resid=myweb_url.resid and ngus_resbytype.checked=2 and ngus_resbytype.realtype='pic'";
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
				Log.error("spider.thumbnail", "", e);
			} finally {

				if (st != null) {
					try {
						st.close();
					} catch (SQLException e) {
						Log.error("spider.thumbnail", "", e);
					}

					st = null;
				}

			}

		} finally {
			try {
				if (c != null)
					c.close();
			} catch (SQLException e) {
				Log.error("spider.thumbnail", "", e);
			}
		}
		return list;

	}

	public String genFilePath(String resId, int width, int height) throws Exception {

		ResourceId rid = new ResourceId(resId);

		return file_server_img_root_path + "/" + rid.getJcrPath() + "/"
				+ rid.getId() + width+"_"+height+".jpg";
	}

	public boolean updateFS(String resid, String url) throws Exception {
		String path = genFilePath(resid, width, height);		
		File fo = new File(path);
		FileUtil.createPath(fo.getParentFile().getAbsolutePath());
		try{
			Image.genThumbnail(new URL(url), fo, width, height, format);
		}catch(javax.imageio.IIOException e){
			Log.error("spider.thumbnail", "generate thumbnail for url "+ url + " failed because of IOException");
			return false;
		}
		Log.trace("spider.thumbnail", "update fs " + fo.getPath() + " ok");
		return true;
	}

	public void updateDB(String resid) {
		// select resId, url, title from myweb_url
		String sql = null;
		sql = "update ngus_resbytype set checked=3 where resid='" + resid + "'";

		// Log.trace("spider.thumbnail", sql);
		Statement st = null;
		Connection c = null;
		int n = 0;
		try {
			try {
				c = getConnection();
				st = c.createStatement();
				n = st.executeUpdate(sql);
			} catch (Exception e) {
				Log.error("spider.thumbnail", "", e);
			} finally {

				if (st != null) {
					try {
						st.close();
					} catch (SQLException e) {
						Log.error("spider.thumbnail", "", e);
					}

					st = null;
				}

			}

		} finally {
			try {
				if (c != null)
					c.close();
			} catch (SQLException e) {
				Log.error("spider.thumbnail", "", e);
			}
		}

		Log.trace("spider.thumbnail", "update db return " + n);

	}

	@Override
	public void daemonRun() throws Exception {

		List<Resource> list = getUrls();
		Log.trace("spider.thumbnail", "get Urls return " + list.size() + " urls");
		// int success = 0;
		for (int i = 0; i < list.size(); i++) {
			Resource page = list.get(i);

			if (!lockRow(page.getResId()))
				continue;
			String url = list.get(i).getUrl();

			Log.trace("spider.thumbnail", url);
			try {
				if (updateFS(page.getResId(), url)) {
					updateDB(page.getResId());
					Log.trace("spider.thumbnail", "generate thumbnail for " + url + " ok.");
				}				
				else{
					Log.error("spider.thumbnail", "update file system for url "+ url + "failed");
				}
				
			} catch (Exception e) {
				Log.error("spider.thumbnail", "", e);
			}
			
			
			unlockRow(page.getResId());
			Log.trace("spider.thumbnail", "generate thumbnail for " + url + " ok");

		}
	}

}