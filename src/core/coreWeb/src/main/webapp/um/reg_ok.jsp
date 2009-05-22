<!-- User Register step1 -->
<%@ page language="java"%>
<%@ page contentType="text/html; charset=gb2312" %>

<%@ page import="com.stockstar.buos.um.*" %>

<%
//setHeader sample
response.setHeader("Cache-Control","no-cache");
response.setHeader("Pragma","no-cache");



//Get submited elements

int lRet;

String UserName = request.getParameter("UserName");
String PenName = request.getParameter("PenName");

%>

<html>
<center>
恭喜您已经注册成功， 您的用户名是<%=UserName%>， 您的笔名是<%=PenName%><p></p>
<a href="log_in.jsp">点击这里登录</a>
</center>
</html>