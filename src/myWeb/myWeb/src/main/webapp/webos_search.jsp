<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ page import="com.ngus.myweb.util.*"%>
<%@ page import="com.ngus.myweb.dataobject.*"%>
<%@ page import="java.net.*"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="com.ngus.myweb.services.MyWebResService"%>
<%@ page import="com.ngus.myweb.friend.FriendService" %>
<%@ page import="java.net.*" %>
<%@ page import="java.util.*"%>
<%@ page import="com.ngus.um.dbobject.*,com.ngus.um.*" %>
<%@ include file="checkSessionNoLink.jsp" %>
<% 
	int current_page = ParamUtils.getIntAttribute(request, "page", 0);
	int total_pages = ParamUtils.getIntAttribute(request, "total_pages", 0);
	List<String> poptags = MyWebResService.instance().mostPopularTag(20);
	List<IUser> newUser = UMClient.getNewUser(10);
	String key = ParamUtils.getAttribute(request, "key");
	String type = ParamUtils.getAttribute(request, "type");
	if(!poptags.isEmpty())
		request.setAttribute("poptags",poptags);
	if(!newUser.isEmpty())
		request.setAttribute("newUser",newUser);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

	<head>
		<meta http-equiv="content-type" content="text/html;charset=utf-8" />
		<meta name="generator" content="Adobe GoLive" />
		<title>monweb.com-search result
</title>
		<link href="css/style.css" rel="stylesheet" type="text/css" media="all" />
	</head>

	<body>
		<div id="webos_mainwrap">
			<div>
			<!--start of the body-->
			<div id="webos_content">
				<!--start of the main content-->
				<div id="main">
					<div id="inner_l_main">
					<div style="border-top:1px solid #66CCFF;"></div>
					<!--start of the main of left area-->
					<form class="search_form" method="post" action="webossearch">
									<input type="text" class="text" name="key" />
									<select name="type">
										<option value="fulltext">全文搜索</option>
										<option value="tag">搜标签</option>
										<option value="user">搜用户</option>
									</select>
									<input type="image" class="image" src="images/butn_search.gif" />
					</form>
					<div class="SR-title"><div>“ <span class="font02"><bean:write name="key"/></span> ”搜索结果 （共找到<span class="font03"><bean:write name="count"/></span>个结果）</div></div>
					<!-- body of one piece of results||starts||-->
					<div class="content-panel">
						<div class="page_list">
							<%								
								if(current_page<=5){
									out.println("<img src=\"images/pre_arrow_grey.gif\" align=\"absmiddle\" />&nbsp;");
								}
								else{
									out.println("<a href=\"webossearch?type="+type+"&key="+key+"&page="+(current_page/5*5+1)+"\"><img src=\"images/pre_arrow.gif\" align=\"absmiddle\" /></a>");
								}
							%>							
							<%	
								for(int i=(current_page-1)/5*5+1; i<=5; i++){
									if(i>0&&i<=total_pages){
										if(i!=current_page){
											out.println("<a href=\"webossearch?type="+type+"&key="+key+"&page="+(i)+"\">"+i+"</a>");
										}
										else{
											out.println(i);
										}
									}
								}								
							%>	
							<%
								if((current_page-1)/5==(total_pages-1)/5){
									out.println("<img src=\"images/next_arrow_grey.gif\" align=\"absmiddle\" />&nbsp;");
								}
								else{
									out.println("<a href=\"webossearch?type="+type+"&key="+key+"&page="+(current_page/5*5+6)+"\"><img src=\"images/next_arrow.gif\" align=\"absmiddle\" /></a>");	
								}
							%>							
							&nbsp;到<form action="webossearch" style="display:inline" ><input type="hidden" name="type" value="<%=type%>" /><input type="hidden" name="key" value="<%=key%>"/><input type="text" name="page" class="text" style="width:15px"/>页&nbsp;<input type="submit" value="GO" class="page_hop" /></form>
						</div>
						<div class="content-panel-content">

							<div id="bk_display" class="result_list">
									<ul>
										<logic:iterate id="bookmark" name="bookmarklist">
										<li>
											<div class="search-result">
												<% 
													String id = URLEncoder.encode(((BookMark)bookmark).getID(),"UTF-8");
												%>
												<div class="search-result-title"><a href="webos_bookmark.jsp?id=<%=id%>"><bean:write name="bookmark" property="title"/></a></div>
												<div class="search-result-url"><a href="<bean:write name="bookmark" property="URL"/>" target='_blank'><bean:write name="bookmark" property="URL"/></a></div>
												<div class="search-result-description"><bean:write name="bookmark" property="description"/></div>
												<div class="search-result-tag">
													<logic:iterate id="tag" name="bookmark" property="tags">
														<a href="webossearch?type=tag&key=<bean:write name="tag"/>"><bean:write name="tag"/></a>
													</logic:iterate>
												</div>
											</div>
										</li>											
										</logic:iterate>								
									</ul>
								</div>
							</div>
							<div class="page_list">
							<%								
								if(current_page<=5){
									out.println("<img src=\"images/pre_arrow_grey.gif\" align=\"absmiddle\" />&nbsp;");
								}
								else{
									out.println("<a href=\"webossearch?type="+type+"&key="+key+"&page="+(current_page/5*5+1)+"\"><img src=\"images/pre_arrow.gif\" align=\"absmiddle\" /></a>");
								}
							%>							
							<%	
								for(int i=(current_page-1)/5*5+1; i<=5; i++){
									if(i>0&&i<=total_pages){
										if(i!=current_page){
											out.println("<a href=\"webossearch?type="+type+"&key="+key+"&page="+(i)+"\">"+i+"</a>");
										}
										else{
											out.println(i);
										}
									}
								}								
							%>	
							<%
								if((current_page-1)/5==(total_pages-1)/5){
									out.println("<img src=\"images/next_arrow_grey.gif\" align=\"absmiddle\" />&nbsp;");
								}
								else{
									out.println("<a href=\"webossearch?type="+type+"&key="+key+"&page="+(current_page/5*5+6)+"\"><img src=\"images/next_arrow.gif\" align=\"absmiddle\" /></a>");	
								}
							%>								
							&nbsp;到<form action="webossearch" style="display:inline"><input type="hidden" name="type" value="<%=type %>" /><input type="hidden" name="key" value="<%=key%>"/><input type="text"  name="page" class="text" style="width:15px"/>页&nbsp;<input type="submit" value="GO" class="page_hop" /></form>
							</div>										
						</div>
					</div>
					<!--end of the main of left area-->
					</div>
				</div>
			
				<!--end of the main content-->
			</div>
			<!--end of the body-->
			</div>
	</body>

</html>