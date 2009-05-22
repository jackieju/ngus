<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ page import="com.ngus.myweb.util.*"%>
<%@ page import="com.ngus.myweb.dataobject.*"%>
<%@ page import="java.net.*"%>
<%@ include file="checkSessionNoLink.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

	<head>
		<meta http-equiv="content-type" content="text/html;charset=utf-8" />
		<meta name="generator" content="Adobe GoLive" />
		<title>monweb.com-search result</title>
		<link href="css/style.css" rel="stylesheet" type="text/css" media="all" />
	</head>


	<%
		int current_page = ParamUtils.getIntAttribute(request, "currPage", 0);
		int total_pages = ParamUtils.getIntAttribute(request, "totalPage", 0);
	%>
	<body>
		
		<div id="mainwrap">
			<div>
			<!--start of the body-->
			<div id="header">
				<!--start of the head area-->
				<div class="logo"><div><a href="#" class="linklogo"></a></div></div>
				<div class="head-register"><a href="index.htm">首页</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href="monweb_QA.jsp">帮助</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href="monweb_reg.jsp">注册</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href="monweb_login.jsp">登录 </a></div>	
				<!--end of the head area-->
			</div>
			
			
			<br />
			<div id="content">
				<!--start of the main content-->
				<div id="main">
					<div id="inner_l_main">
					<!--start of the main of left area-->
					<div>
						<ul class="nav">
							<li class="initial_tab_on"><img src="images/icon_bookmark.gif" />&nbsp;书签</li>
							<li class="tab_off"><a href="ListPopByType?type=rss"><img src="images/icon_rss.gif" />&nbsp;RSS</a></li>
							<li class="tab_off"><a href="ListPopByType?type=video"><img src="images/icon_video.gif" />&nbsp;视频</a></li>
							<li class="tab_off"><a href="ListPopByType?type=pic"><img src="images/icon_pic.gif" />&nbsp;图片</a></li>
							<li class="tab_off"><a href="download.jsp"><img src="images/icon_download.gif" />&nbsp;下载</a></li>
						</ul>
					</div>
					<div style="clear:both;"></div>
					<div style="border-top:1px solid #66CCFF;"></div>
					
					<form class="search_form" method="post" action="search">
									<input type="text" class="text" name="key" />
									<select type="name">
										<option value="fulltext">全文搜索</option>
										<option value="tag">搜标签</option>
										<option value="user">搜用户</option>
									</select>
									<input type="image" class="image" src="images/butn_search.gif" />
					</form>
							
					<div class="SR-title"></div>
					<!-- body of one piece of results||starts||-->
					<div class="content-panel">
						<div class="page_list">
							共<%=total_pages %>页
							<%								
								if(current_page<=5){
									out.println("<img src=\"images/pre_arrow_grey.gif\" align=\"absmiddle\" />&nbsp;");
								}
								else{
									out.println("<a href=\"ListPopByType?type=webpage&currPage="+(current_page/5*5+1)+"\"><img src=\"images/pre_arrow.gif\" align=\"absmiddle\" /></a>");
								}
							%>							
							<%				
							int start1 = (current_page-1)/5*5+1;
							int end1 = start1 + 4;
							for(int i=start1; i<=end1; i++){
									if(i>0&&i<=total_pages){
										if(i!=current_page){
											out.println("<a href=\"ListPopByType?type=webpage&currPage="+(i)+"\">"+i+"</a>");
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
									out.println("<a href=\"ListPopByType?type=webpage&currPage="+(current_page/5*5+6)+"\"><img src=\"images/next_arrow.gif\" align=\"absmiddle\" /></a>");	
								}
							%>							
							&nbsp;到<form action="ListPopByType" style="display:inline" ><input type="hidden" name="type" value="webpage" /><input type="text" name="currPage" class="text" style="width:15px"/>页&nbsp;<input type="submit" value="GO" class="page_hop" /></form>
						</div>
						
						<logic:iterate id="bookmark" name="list">
							<div class="result_display">
								<div class="pic_display">
									<a href="<bean:write name="bookmark" property="url" />"><img src="<bean:write name="bookmark" property="snapshot" />" width="130" /></a><!--程序控制，图片宽度不超过130px;-->
								</div>
								<div class="content_display">
									<span class="content_title"><a href="<bean:write name="bookmark" property="url" />"><bean:write name="bookmark" property="name" /></a></span>
									<br />
									by&nbsp;<span class="user"><a href="#">test</a></span>&nbsp;添加于<bean:write name="bookmark" property="createTime" /><br />
									<div class="content_description">
										<bean:write name="bookmark" property="description" />
									</div>
									<span class="tag"><img src="images/tag.gif" />&nbsp;tags</span>:
									<span>
										<logic:iterate id="tags" name="bookmark" property="tags">
										<a href="search?type=tag&key=<bean:write name="tags"/>"><bean:write name="tags"/></a> |
										</logic:iterate>
									</span>
									<div class="content_reviews"><img src="images/add.gif" />搜藏次数：<bean:write name="bookmark" property="time" />次</div>
								</div>
								<div class="clear"></div>
								<div class="patchbox"></div>
							</div>
						</logic:iterate>
						<div class="page_list">
						共<%=total_pages %>页
						<%								
								if(current_page<=5){
									out.println("<img src=\"images/pre_arrow_grey.gif\" align=\"absmiddle\" />&nbsp;");
								}
								else{
									out.println("<a href=\"ListPopByType?type=webpage&currPage="+(current_page/5*5+1)+"\"><img src=\"images/pre_arrow.gif\" align=\"absmiddle\" /></a>");
								}
							%>							
							<%	
								int start2 = (current_page-1)/5*5+1;
								int end2 = start2 + 4;
								for(int i=start2; i<= end2; i++){
									if(i>0&&i<=total_pages){
										if(i!=current_page){
											out.println("<a href=\"ListPopByType?type=webpage&currPage="+(i)+"\">"+i+"</a>");
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
									out.println("<a href=\"ListPopByType?type=webpage&currPage="+(current_page/5*5+6)+"\"><img src=\"images/next_arrow.gif\" align=\"absmiddle\" /></a>");	
								}
							%>							
							&nbsp;到<form action="ListPopByType" style="display:inline" ><input type="hidden" name="type" value="webpage" /><input type="text" name="currPage" class="text" style="width:15px"/>页&nbsp;<input type="submit" value="GO" class="page_hop" /></form>
							</div>																						
					</div>
					<!--end of the main of left area-->
					</div>
				</div>
			
				<%@ include file="login.jsp" %>
				<!--end of the main content-->
			</div>
			
			<div class="clear">&nbsp;</div>
			<%@ include file="footer.jsp" %>
			<!--end of the body-->
			</div>
		</div>
	</body>

</html>