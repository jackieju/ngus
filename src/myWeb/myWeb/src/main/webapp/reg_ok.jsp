<!-- User Register step1 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<%
	
//setHeader sample
	response.setHeader("Cache-Control","no-cache");
	response.setHeader("Pragma","no-cache");


	String userName = request.getParameter("userName");
	String penName = request.getParameter("penName");
	
%>

<html>
<center>
恭喜您已经注册成功， 您的用户名是<%=userName%>， 您的笔名是<%=penName%><p></p>
<a href="main.jsp">点击这里登录</a>
</center>
</html>