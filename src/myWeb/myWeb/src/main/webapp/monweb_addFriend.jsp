<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ page import="com.ngus.myweb.services.MyWebResService"%>
<%@ page import="com.ngus.myweb.friend.*"%>
<%@ page import="com.ngus.um.http.*" %>
<%@ page import="com.ngus.um.*" %>
<%@ page import="com.ngus.myweb.dataobject.*"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="com.ngus.myweb.util.ParamUtils" %>
<%@ page import="com.ngus.um.dbobject.*,com.ngus.um.*" %>
<%@ include file="checkSession.jsp" %>

<% 
	response.setHeader("Cache-Control","no-cache");
	response.setHeader("Pragma","no-cache");
	int error_level = ParamUtils.getIntParameter(request,"log_error_level",0);
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<link rel="stylesheet" type="text/css" title="default" href="css/styles.css">
		<link rel="stylesheet" type="text/css" title="default" href="css/styles01.css">
		<link href="css/css.css" rel="stylesheet" type="text/css">
		<title>添加好友</title>
	</head>

	<body>
		
		<div class="wrapper">
			<div class="inner-wrapper">
				<!--header start####################################################-->
				<div id="header">
					<div class="head_entrance">
						<%@ include file="monweb_head.jsp" %>
					</div>
				
					<a href="#"><img src="images/logo_monweb.gif" alt="monweb" border="0" /></a>
				
				<form class="search_form" action="search" method="post">
					<div class="head_searchbox">
						<div class="searchbox_inner">
							<div>
								<span class="patchbox">&nbsp;</span>
								<input type="text" class="text" name="key"/>
								<select name="type">
									<option value="fulltext">全文搜索</option>
									<option value="tag">搜标签</option>
									<option value="user">用户</option>
								</select>
								<input type="image" class="image" src="images/search_butn.gif" />
								&nbsp; &nbsp;|
								<img src="images/rss_list.gif" alt="rss资源" />
								<img src="images/music.gif" alt="音乐资源" />
								<img src="images/HV_list.gif" alt="视频资源" />
								<img src="images/user.gif" alt="用户" />
							</div>
						</div>
					</div>
				</form>
					
					
				</div>
				<!--header end####################################################-->
				
				<!--main-wrapper start####################################################-->
				<div class="main-wrapper">
					<!--content start####################################################-->
					<div class="panel_content">
						<div class="inner-content">
							<div class="menu">
								<div class="menu-tab-start"></div>
								<div class="menu-tab-unselected"><span><a href="main.jsp">我的资源</a></span></div>
				
								<div class="menu-tab-unselected"><span><a href="monweb_pic.jsp">图 片</a></span></div>
								<div class="menu-tab-unselected"><span><a href="viewrss.jsp">RSS</a></span></div>
								
								<div class="menu-tab-end"></div>
							</div>							
							<!--xxx panel start####################################################-->
							<div class="content-panel">
								<div class="content-panel-content">
									<div style="height: 310px;">
									<div style="float: left;">
										
									</div>
									
									<div id="bk_display" style="float: left; border: 1px solid gray; width: 720px; height: 370px; overflow:auto">
										<br/><br/><br/><br/><br/>
										<div align="center">
											<form action="addFriend.do" method = "post">
												<%
												String userName= (String)session.getAttribute("username");
												if(userName!=null) {%>
												<input name="userId" type="hidden" value="<%=UserManager.getCurrentUser().getUserId()%>"/>											
												用户名: <input name="friendName" type="text" />	<%if(error_level==3){ %>	<span style="color:red">对不起，该用户不存在 </span>	<%} %>						
												<input type="submit" value="提交"/>
												<%} %>
											</form>	
											
										</div>
										
										
									</div>
									</div>
																		
								</div>
							</div>
							<!--xxx panel end####################################################-->
						</div> <!-- inner content -->
					</div><!-- panel content -->
					<!--content end####################################################-->
					
					<!--sidebar start####################################################-->
					<div class="sidebar">
						<div class="inner-sidebar">
							<!--user panel start####################################################-->
							
								<!-- -->
							<%@ include file="login.jsp" %>
							
							<!--user panel start####################################################-->
						
							<!--xxx panel start####################################################-->
								<div class="sidebar-panel">
									<div class="sidebar-panel-title"><div>用户好友</div></div>
									<div class="sidebar-panel-content">
										<div class="">
											<div><a href="monweb_addFriend.jsp">添加</a>&nbsp;|&nbsp;<a href="monweb_friend.jsp">更多</a></div>
										</div>
									</div>
									<div class="sidebar_pannel_foot"><div class="sidebar_pannel_1foot"><div></div></div></div>
								</div>
							<!--xxx panel end####################################################-->
						</div>
					</div>
					<!--sidebar end####################################################-->
					<div class="clear"></div>
				</div>
				<!--main-wrapper end####################################################-->
				
				<!--footer start####################################################-->
				<div class="footer">
					<div class="inner-footer">
						<div class="footer-help">
						© 2006 <a href="#">MyWeb</a> 版权所有 保留所有权利 | <a href="http://www.miibeian.gov.cn/">沪ICP备06020881号</a><br>
						<a href="#">使用指南</a> | <a href="#">隐私条款</a> | <a href="#">关于我们</a> | <a href="#">法律条款</a> | <a href="#">联系我们</a> | <a href="#">常见问题</a></div>
					</div>
				</div>
				<!--footer end####################################################-->
			</div>
		</div>
	
	</body>

</html>