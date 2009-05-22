<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.ngus.myweb.userextension.UserExtensionService"%>
<%@ page import="java.util.List"%>
<%@ page import="com.ngus.um.http.*" %>
<%@ page import="com.ngus.um.*" %>
<%@ page import="com.ns.dataobject.Attribute"%>
<%@ page import="java.util.*"%>
<%@ page import="com.ngus.myweb.services.MyWebResService" %>
<%@ page import="com.ngus.myweb.friend.*"%>
	
					
					<!--block no.1|starts|-->
					<div class="sidebar-panel">
						<div class="sidebar-panel-title"><img src="images/star.gif" />&nbsp;最近活动用户</div>
						<div class="sidebar-panel-content">
							<%
							List<IUser> newuser = UMClient.getNewUser(10);
								for(int i=0; i<newuser.size(); i++){									
									out.println("<div class='user'>\n"+
										"<div class='user_pic'><a href='#'><img src='userPic?userName="+((User)newuser.get(i)).getUserName()+"' width=50 height=50/></a></div>\n"+
										"<div class='user_name'><a href='#'>"+((User)newuser.get(i)).getUserName()+"</a></div>\n"+
										"</div>\n");
									if(i%4==3||i==newuser.size()-1){
										out.println("<div style='clear:left;'></div>\n");
									}
								}
							%>
							
						</div>						
					</div>
					<!--block no.1|ends|-->
					
				
				
					
					
					
