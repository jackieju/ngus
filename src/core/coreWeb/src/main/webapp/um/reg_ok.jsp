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
��ϲ���Ѿ�ע��ɹ��� �����û�����<%=UserName%>�� ���ı�����<%=PenName%><p></p>
<a href="log_in.jsp">��������¼</a>
</center>
</html>