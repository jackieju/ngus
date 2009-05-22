package com.ngus.myweb.rss;

import java.sql.Timestamp;
import java.util.List;

import com.ngus.config.SystemProperty;
import com.ngus.dataengine.DataEngine;
import com.ngus.dataengine.IndexManager;
import com.ngus.dataengine.ResourceObject;
import com.ns.dataobject.Attribute;
import com.ns.log.Log;
import com.ns.util.IO;
import com.rsslibj.elements.Channel;
import com.rsslibj.elements.Item;

public class RSSFeedGenerator {

	public static void main(String s[]) throws Exception {
		System.out.println(getUserAllResUpdate(0, 0));
	}

	/**
	 * get recent n days' update of user
	 * 
	 * @param recent
	 * @return rss
	 */
	public static String getUserAllResUpdate(int userId, int recent)
			throws Exception {
		String ret = "";

		long current = System.currentTimeMillis();
		long target = current - recent * 24 * 3600000;			
		Timestamp target_day = new Timestamp(target);
		Timestamp tt = new Timestamp(current);
		String t = String.format("%04d-%02d-%02d %02d:%02d:%02d", tt.getYear()+1900, tt.getMonth()+1, tt.getDate(), tt.getHours(), tt.getMinutes(), tt.getSeconds());
		Log.trace(t);
		String formated = String.format("%04d-%02d-%02d %02d:%02d:%02d", target_day.getYear()+1900, target_day.getMonth()+1, target_day.getDate(), target_day.getHours(), target_day.getMinutes(), target_day.getSeconds());
		List<String> list = IndexManager.instance().QueryIndex(
				"myWeb_addTime",
				"userid=" + userId + " and createTime>'"
						+formated + "'", "createTime", true,
				0, -1, new Attribute("total", Attribute.ATTR_DT_INT));

		Log.trace("result length = " + list.size());
		Channel channel = new Channel();
		channel.setDescription("myWeb - user updated resource in past "
				+ recent + "days");
		channel.setLink("http://localhost/");
		channel.setTitle("myWeb - user updated resource in past " + recent
				+ "days");
		channel.setImage("my", "The Channel Image", "http://localhost/foo.jpg");
		channel.setTextInput("http://localhost/search",
				"Search The Channel Image", "The Channel Image", "s");
		for (int i = 0; i < list.size(); i++) {
			DataEngine de = new DataEngine();
			String resId = list.get(i);
			ResourceObject ro = de.getResourceObjById(resId, true);
			StringBuffer sb = new StringBuffer();
			List<String> tags = ro.getTags();			
			for (int j = 0; j < tags.size(); j++)
					sb.append(tags.get(j) + " ");
			
//			TODO link to view bookmark
			String link = "http://" + SystemProperty.getProperty("ngus.myweb.domain") + "/";		
			
			
			Item item = channel.addItem(link, sb.toString(), ro
					.getTitle());
			if (ro.getResourceType().contains(ResourceObject.RESTYPE_PIC))
				sb.append("<img src=\"" + IO.InputStreamToUTF8Str(ro.getValue()) + "\" />");

		}
		ret = channel.getFeed("2.0");

		return ret;

	}

	/**
	 * get most recent n day's update of resource
	 * 
	 * @param resType
	 * @param recent
	 * @return rss
	 */
	public static String getNewResource(String resType, int recent) {
		return "";
	}
}
