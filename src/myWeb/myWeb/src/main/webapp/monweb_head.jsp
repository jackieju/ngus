<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.ngus.myweb.userextension.UserExtensionService"%>
<% 
	String name1 = (String) request.getAttribute("username"); 
	if(name1 == null){ %>
		<a href="/myWeb">首页</a>&nbsp;|&nbsp;
		<a href="main.jsp">我的资源</a>&nbsp;|&nbsp; 
		<a href="download.jsp">下载</a>&nbsp;|&nbsp;
		<a href="monweb_login.jsp">登录</a>&nbsp;|&nbsp;
		<a href="monweb_reg.jsp">注册</a>&nbsp;&nbsp;
<%} else{%>		
		<a href="/myWeb">首页</a>&nbsp;|&nbsp;
		<a href="main.jsp">我的资源</a>&nbsp;|&nbsp; 
		<a href="download.jsp">下载</a>&nbsp;|&nbsp;
		<a href="logout.jsp">退出</a>&nbsp;|&nbsp;				
<% 	} %>