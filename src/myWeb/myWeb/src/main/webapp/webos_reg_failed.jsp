<!-- User Register step1 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<%
	
//setHeader sample
	response.setHeader("Cache-Control","no-cache");
	response.setHeader("Pragma","no-cache");
    String lag= request.getParameter("err");
%>

<html>
<center>
抱歉，您注册失败！<p></p>
<a href="webos_signin.html">点击这重新注册</a><br />
lag= <%=lag %>
</center>
</html>