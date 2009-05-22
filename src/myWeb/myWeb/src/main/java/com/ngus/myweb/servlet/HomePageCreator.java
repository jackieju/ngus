package com.ngus.myweb.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;

import com.ngus.comment.CommentEngine;
import com.ngus.config.SystemProperty;
import com.ngus.myweb.dataobject.BookMark;
import com.ngus.myweb.dataobject.MyWebRes;
import com.ngus.myweb.searchkey.SearchKeyService;
import com.ngus.myweb.services.MyWebResService;
import com.ngus.myweb.util.DbUtils;
import com.ngus.um.IUser;
import com.ngus.um.UMClient;
import com.ns.dataobject.Attribute;
import com.ns.db.DB;
import com.ns.log.Log;

import freemarker.template.Configuration;
import freemarker.template.ObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class HomePageCreator implements ServletContextListener {

	private Configuration cfg;

	public void contextDestroyed(ServletContextEvent sce) {
		// Log4jConfigurer.shutdownLogging(envProperties);
	}

	public void contextInitialized(ServletContextEvent sce) {
		Log.trace("servlet context = " + sce.getServletContext().getServletContextName());
		// initialize configuration
		cfg = new Configuration();
		 cfg.setObjectWrapper(ObjectWrapper.BEANS_WRAPPER); 
		// - Templates are stoted in the WEB-INF/templates directory of the Web
		// app.
		cfg.setServletContextForTemplateLoading(sce.getServletContext(),
				"WEB-INF/templates");
		// final ServletContext sc = sce.getServletContext();
		final File file = new File(sce.getServletContext().getRealPath(
				"index.htm"));
		final File rss = new File(sce.getServletContext()
				.getRealPath("rss.htm"));
		final File video = new File(sce.getServletContext().getRealPath(
				"video.htm"));
		final File pic = new File(sce.getServletContext()
				.getRealPath("pic.htm"));
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			public void run() {
				try {
					Map<String, Object> root = new HashMap<String, Object>();
					Map<String, Object> root_rss = new HashMap<String, Object>();
					Map<String, Object> root_video = new HashMap<String, Object>();
					Map<String, Object> root_pic = new HashMap<String, Object>();
					bindData(root, "webpage");
					bindData(root_rss, "rss");
					bindData(root_video, "video");
					bindData(root_pic, "pic");
					Template t = cfg.getTemplate("index.ftl", "utf-8");
					OutputStreamWriter fw = new OutputStreamWriter(
							new FileOutputStream(file), "utf-8");
					OutputStreamWriter fw_rss = new OutputStreamWriter(
							new FileOutputStream(rss), "utf-8");
					OutputStreamWriter fw_video = new OutputStreamWriter(
							new FileOutputStream(video), "utf-8");
					OutputStreamWriter fw_pic = new OutputStreamWriter(
							new FileOutputStream(pic), "utf-8");
					try {
						t.process(root, fw);
						t.process(root_rss, fw_rss);
						t.process(root_video, fw_video);
						t.process(root_pic, fw_pic);
					} catch (TemplateException e) {
						throw new ServletException(
								"Error while processing FreeMarker template", e);
					}
					fw.flush();
					fw.close();
				} catch (Exception e) {
					Log.error(e);
				}
			}
		}, 0, Integer.parseInt(SystemProperty
				.getProperty("ngus.myweb.homepage.refreshInterval")));
	}

	private void bindData(Map<String, Object> root, String type) {
		long startTime = System.currentTimeMillis();
		List<String> pop_tag = getMostPopularTag();
		List<IUser> user = getHotUser();
		root.put("hot_tags", pop_tag);
		root.put("hot_list",
				getMostPopularResByTypeWithCount(type, 0, 10, null));
		root.put("hot_user_list", user);
		root.put("type", type);
		long endTime = System.currentTimeMillis();
		root.put("pageCreateTime", new Timestamp(startTime));
		root.put("pageCostTime", endTime - startTime);
	}

	private List<String> getMostPopularTag() {
		List<String> al = MyWebResService.instance().mostPopularTag(20);
		return al;
	}

	private List<String> getHotkey() {
		List<String> al = SearchKeyService.getPopKey(10);
		return al;
	}

	private List<MyWebRes> getMostPopularResByType(String type, int num) {
		List<MyWebRes> al = MyWebResService.instance().listMostPopularRes(num,
				type);
		// Iterator<MyWebRes> iter = mp.keySet().iterator();
		/*
		 * while(iter.hasNext()){ Wrapper tmp = new Wrapper(); MyWebRes res =
		 * iter.next(); tmp.setTime(mp.get(res)); tmp.setRes(res); al.add(tmp); }
		 */
		return al;
	}

	public static List<Wrapper> getMostPopularResByTypeWithCount(String type,
			int start, int size, Attribute count) {
		
		List<Wrapper> al = new ArrayList<Wrapper>();
		int checked = 1;
		if (type.equalsIgnoreCase("pic"))
			checked =2;
		List<MyWebRes> mp = MyWebResService.instance()
				.listMostPopularResWithCount(start, size, type, checked, count);


		Connection con = null;
		try {
			con = DB.getConnection();
			for (int i = 0; i< mp.size(); i++) {
				Wrapper tmp = new Wrapper();
				BookMark res = (BookMark)mp.get(i);
				tmp.setTime(res.getAddCount());
				String snapshot = null;
				ResultSet rs = null;
				if (type.equalsIgnoreCase("webpage")) {
					try {
						PreparedStatement psmt = con
								.prepareStatement("SELECT jpgpath FROM myweb_pagejpg where url=?");
						psmt.setString(1, res.getURL());
						rs = psmt.executeQuery();
						if (rs.next()) {
							snapshot = rs.getString(1);
							int pos_off = snapshot.lastIndexOf("/");
							snapshot = "pagess/" + snapshot.substring(pos_off);
						}
					} catch (Exception e) {
						Log.error(e);
					} finally {
						DbUtils.closeResultSet(rs);
					}
				} else if (type.equalsIgnoreCase("video")) {
					try {
						PreparedStatement psmt = con
								.prepareStatement("SELECT jpgpath FROM myweb_videojpg where url=?");
						psmt.setString(1, res.getURL());
						rs = psmt.executeQuery();
						if (rs.next()) {
							snapshot = rs.getString(1);
							int pos_off = snapshot.lastIndexOf("/");
							snapshot = "pagess/" + snapshot.substring(pos_off);
						}
					} catch (Exception e) {
						Log.error(e);
					} finally {
						DbUtils.closeResultSet(rs);
					}
				} else if (type.equalsIgnoreCase("pic")) {
					try {
						String id = res.getID();
						id = id.substring(id.lastIndexOf("myweb"));
						id = id.replaceAll("\\\\", "/");
						id = id.replaceAll("#", "/");
						String t = id.substring(id.lastIndexOf("/"));
						snapshot = "images/" + id + t + "300_300.jpg";
					} catch (Exception e) {
						Log.error(e);
					}
				}
				try {
					tmp.setName(res.getName());
					tmp.setUrl(res.getURL());
					tmp.setTags(res.getTags());
					tmp.setDescription(res.getDescription());
					String snap_url = null;
					if (snapshot == null) {
						snap_url = "images/bookmark_sample.gif";
					} else {
						snap_url = SystemProperty
								.getProperty("ngus.myweb.fileserver")
								+ snapshot;
					}
					tmp.setSnapshot(snap_url);

					// set owner
					String usrId = ((MyWebRes) res).getRO().getUser();
					tmp.setUserName(new UMClient().getUserByUserId(
							Integer.parseInt(usrId)).getUserName());

					// set create time
					try {
						Timestamp tm = ((MyWebRes) res).getRO().getCreateTime();
						if (tm == null)
							tmp.setCreateTime(new Date());
						else {
							Log.trace("create time " + tm.toString());
							// tmp.setCreateTime(new Date(tm.getTime()));
							tmp.setCreateTime(new Date(tm.getTime()));
							//Log.trace("set create time ok");
						}
					} catch (Exception e) {
						Log.error("error to set create time");
						Log.error(e);
					}

				} catch (Exception e) {
					Log.error(e);
				}

				al.add(tmp);
				Log.trace("add new res to list " + tmp.getName() + ", " + tmp.getUrl());
			}

		} catch (Exception e) {
			Log.error(e);
		} finally {
			DbUtils.closeConnection(con);
		}
//		unstable sort...
//		Collections.sort(al, new Comparator<Wrapper>() {
//			public int compare(Wrapper wrap1, Wrapper wrap2) {
//				return wrap2.getTime() - wrap1.getTime();
//			}
//		});
		return al;
	}

	private List<MyWebRes> getMostRecentRes() {
		List<MyWebRes> al = MyWebResService.instance().listMostRecentRes(10);
		return al;
	}

	private List<IUser> getHotUser() {
		List<IUser> arr = UMClient.getHotUser(8);
		return arr;
	}

	private List<IUser> getNewUser() {
		List<IUser> arr = UMClient.getNewUser(10);
		return arr;
	}

	private List getNewComment() {
		try {
			List arr = CommentEngine.instance().getNewComment(3);
			return arr;
		} catch (Exception e) {
			Log.error(e);
		}
		return null;
	}
}
