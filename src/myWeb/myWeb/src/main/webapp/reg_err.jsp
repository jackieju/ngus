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
��֤�����벻��ȷ<a href="reg2.jsp">return</a>
<%}else if(errCode.equalsIgnoreCase("2")){ %>
����userextension���ɹ�<a href="index.htm">return</a>
<%}else if(errCode.equalsIgnoreCase("3")){ %>
�����������벻��<a href="reg2.jsp">return</a>
<%}else %>
  ע��ʧ�ܣ�um���Ӳ��ɹ���errocode=<%=errCode%>
</center>
</html>