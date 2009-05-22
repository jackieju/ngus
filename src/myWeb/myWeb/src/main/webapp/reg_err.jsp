<!-- User Register step1 -->
<%@ page language="java"%>
<%@ page contentType="text/html; charset=gb2312" %>

<%
//setHeader sample
response.setHeader("Cache-Control","no-cache");
response.setHeader("Pragma","no-cache");



//Get submited elements

//int lRet;

//String userName = request.getParameter("UserName");
//String penName = request.getParameter("PenName");
String errCode = request.getParameter("ErrCode");
String userName = request.getParameter("userName");
String penName = request.getParameter("penName");
String userType = request.getParameter("userType");
%>

<html>
<center>
<%if(errCode.equalsIgnoreCase("1")){ %>
验证码输入不正确<a href="reg2.jsp">return</a>
<%}else if(errCode.equalsIgnoreCase("2")){ %>
插入userextension表不成功<a href="index.htm">return</a>
<%}else if(errCode.equalsIgnoreCase("3")){ %>
两次密码输入不符<a href="reg2.jsp">return</a>
<%}else %>
  注册失败，um连接不成功。errocode=<%=errCode%>
</center>
</html>