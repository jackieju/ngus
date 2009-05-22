<%@ page language="java"%>
<%@ page contentType="text/html; charset=gb2312" %>
<%@ page import="com.stockstar.buos.um.*" %>
<%@ include file="CheckSession.jsp"%>
<%
//setHeader sample
response.setHeader("Cache-Control","no-cache");
response.setHeader("Pragma","no-cache");

int lRet = -1;

String AuthorityObjectId  = request.getParameter("AuthorityObjectId");
lRet = UMClient.AF_DelAuthorityObject(SSSESSIONID,ClientId,AuthorityObjectId);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META name="GENERATOR" content="IBM WebSphere Studio">
<TITLE>Usermanager Client on JSP Sample</TITLE>
<style>
	body,table,p,td{font-family:verdana;font-size:9pt;}
</style>
</HEAD>
<BODY>
<h3>UMClient.AuthorityObjectId</h3>
<h4>Returns:</h4>
<%
if (lRet == 0){
	out.println("AF_DelAuthorityObject Successfully." + AuthorityObjectId);
}else{
	out.println("AF_DelAuthorityObject Failed." + lRet);
}
%>

</BODY>
</HTML>
