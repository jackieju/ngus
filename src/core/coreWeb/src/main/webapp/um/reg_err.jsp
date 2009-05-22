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
String ErrCode = request.getParameter("ErrCode");

%>

<html>
<center>
зЂВсЪЇАмЃЌДэЮѓДњТы<%=ErrCode%>
</center>
</html>