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

					<%
					
					if ( request.getAttribute("username") != null ){ 
						String userid=(String)request.getAttribute("userid");
					List<String> al = MyWebResService.instance().listUserTag(userid);
						request.setAttribute("keyList",al);
						
						%>
					<div class="sidebar-panel">
						<div class="sidebar-panel-title">标签&nbsp;|&nbsp;Your tags </div>
						<div class="sidebar-panel-content">
							<div class="show_tags"><!--程序统计访问数, 改变style中的font-weight 和font-size的值-->

								<logic:iterate id="tag" name="keyList">
								<a href="search?byUser=true&byUserId=<%=userid%>&type=tag&key=<bean:write name="tag"/>"  style=""><bean:write name="tag"/></a>&nbsp; 
								</logic:iterate>

							</div>
						</div>
					</div>
					<%}else { 
						List<String> al = MyWebResService.instance().mostPopularTag(14);
						request.setAttribute("keyList",al);
					%>
					<div class="sidebar-panel">
						<div class="sidebar-panel-title">热门标签&nbsp;|&nbsp;hot tags </div>
						<div class="sidebar-panel-content">
							<div class="show_tags"><!--程序统计访问数, 改变style中的font-weight 和font-size的值-->
								<logic:iterate id="element" name="keyList">
									<a href="search?type=tag&key=<bean:write name='element' />" style=""><bean:write name="element" /></a>
								</logic:iterate>
								
								
								&nbsp;
							</div>
						</div>
					</div>
					
										<%} %>

