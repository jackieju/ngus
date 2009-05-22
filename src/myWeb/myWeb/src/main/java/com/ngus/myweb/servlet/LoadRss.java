package com.ngus.myweb.servlet;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.gnu.stealthp.rsslib.RSSChannel;
import org.gnu.stealthp.rsslib.RSSHandler;
import org.gnu.stealthp.rsslib.RSSItem;
import org.gnu.stealthp.rsslib.RSSParser;

/**
 * Servlet implementation class for Servlet: LoadRss
 *
 */
 public class LoadRss extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public LoadRss() {
		super();
	}   	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		StringBuffer sb = new StringBuffer();		
		RSSHandler hand = new RSSHandler();
		try{			
			//a bug when accessing http://www.eclipseworld.org/bbs/rss.php
			URL url = new URL(request.getParameter("url"));
			RSSParser.parseXmlFile(url, hand, false);
			RSSChannel cha = hand.getRSSChannel();
			LinkedList items = cha.getItems();			
			for(int i=0; i<items.size(); i++){		
				sb.append("<div class='search-result'>");
				sb.append("<div class='search-result-title'>");
				RSSItem item = (RSSItem)items.get(i);
				sb.append("<a href='");
				sb.append(item.getLink());
				sb.append("'>");
				sb.append(item.getTitle());
				sb.append("</a></div>");
				sb.append("<div class='search-result-description'>");
				sb.append(item.getDescription());
				sb.append("</div>");				
				sb.append("<div class='search-result-date'>");
				sb.append(item.getDate());
				sb.append("</div>");
				sb.append("</div>");
			}
		}				
		catch(Exception e){
			e.printStackTrace();
		}		
		response.getWriter().print(sb.toString());		
	}  	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}   	  	    
}