package com.ngus.myweb.spider;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import com.ngus.dataengine.IDataEngine;
import com.ngus.dataengine.ResourceObject;
import com.ngus.myweb.spider.ResTypeDaemon.Pattern;
import com.ngus.resengine.IResEngine;
import com.ns.log.Log;

public class ResTypeSpider {
	public static ArrayList<Pattern> patterns = new ArrayList<Pattern>();

	static public void main(String v[]) throws Exception {
		System.out.println("test");
		System.out
				.println("type is "
						+ getResType("http://movieserver.china.com/game/061220/WOWSPBUGNAXX061220.wmv"));
	}

	static public String getResType(String url) throws Exception {
		Log.trace("spider", "enter");
		try {
			URL urlE = new URL(url);
			try {
				URLConnection con = urlE.openConnection();

				InputStream is = con.getInputStream();

				String contentType = con.getContentType();

				Log.trace("spider", "content type=" + contentType);
				if (contentType != null) {
					if (contentType.toLowerCase().contains("image"))
						return ResourceObject.RESTYPE_PIC;
					if (contentType.toLowerCase().contains("text/html"))
						return ResourceObject.RESTYPE_WEBPAGE;
					if (contentType.toLowerCase().contains("text/xml")
							|| contentType.toLowerCase().contains(
									"application/xml"))
						return ResourceObject.RESTYPE_RSS;
					if (contentType.toLowerCase().contains("realmedia")) // rm
						return ResourceObject.RESTYPE_VEDIO;
					else if (contentType.toLowerCase().contains("video")) // mpeg
						return ResourceObject.RESTYPE_VEDIO;
					else if (contentType.toLowerCase().contains("audio")) // mpeg
						return ResourceObject.RESTYPE_AUDIO;
				} else { // wmv, flv, ...

				}
			} catch (Exception e) {
				Log.error("spider", "", e);
				//return null;
			}
			
			// by file extension name
			if (url.lastIndexOf(".") >= 0){
			Log.trace("spider", "url end with" + url.substring(url.lastIndexOf("."), url.length()));
			if (url.endsWith(".wmv") || url.endsWith(".avi")
					|| url.endsWith(".flv") || url.endsWith(".asf")
					|| url.endsWith(".mpeg") || url.endsWith(".mpg")
					|| url.endsWith("rm") || url.endsWith("mp4")) {
				return ResourceObject.RESTYPE_VEDIO;
			} else if (url.endsWith(".mp3") || url.endsWith(".wma"))
				return ResourceObject.RESTYPE_AUDIO;
			else if (url.endsWith(".jpg") || url.endsWith(".jpeg")
					|| url.endsWith(".gif") || url.endsWith(".png")
					|| url.endsWith(".bmp") || url.endsWith(".cr2")
					|| url.endsWith(".") || url.endsWith(".tif")
					|| url.endsWith(".tiff") || url.endsWith(".psd"))
				return ResourceObject.RESTYPE_PIC;
			else if (url.endsWith(".xml"))
				return ResourceObject.RESTYPE_RSS;
			}
			
			// by url pattern
			Log.trace("spider", "pattern list size " + patterns.size());
			for (int i = 0; i < patterns.size(); i++) {
				ResTypeDaemon.Pattern p = patterns.get(i);
				Log.trace("spider", "match type=" + p.getMatchType()
						+ ", pattern=" + p.getPattern());
				if (p.getMatchType().equalsIgnoreCase("contain")
						&& url.contains(p.getPattern()))
					return p.getType().get(0);
				else if (p.getMatchType().equalsIgnoreCase("startWith")
						&& url.startsWith(p.getPattern()))
					return p.getType().get(0);
				else if (p.getMatchType().equalsIgnoreCase("endWith")
						&& url.endsWith(p.getPattern()))
					return p.getType().get(0);
				else if (p.getMatchType().equalsIgnoreCase("regExp")
						&& url.matches(p.getPattern()))
					return p.getType().get(0);
			}

			// else if ()

			// try{
			// is.reset();
			// }catch(Exception e){
			// is = urlE.openConnection().getInputStream();
			// }
			//			
			// BufferedReader r = null;
			// if (encoding == null)
			// r = new BufferedReader(new InputStreamReader(is));
			// else
			// r = new BufferedReader(new InputStreamReader(is, encoding));
			//			
			// String line;
			//		
			//			
			// while ((line = r.readLine()) != null ) {
			//		
			// }
		} catch (Exception e) {
			Log.error("spider", "", e);
		}
		return "";

	}
}
