<%@ page language="java"%>
<%@ page contentType="text/html; charset=gb2312" %>
<%@ page import="com.stockstar.buos.um.*" %>
<%@ include file="CheckSession.jsp"%>
<%
//setHeader sample
response.setHeader("Cache-Control","no-cache");
response.setHeader("Pragma","no-cache");

int lRet = -1;

IntObj intObj = new IntObj();
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
<h3>UMClient.AF_GetBasicRight</h3>
<h4>Returns:</h4>
È¨Á¦×´Ì¬£¬0:ÎÞÈ¨£¬1:²Ù×÷£¬2:¹ÜÀí
<p>
<%
lRet = UMClient.AF_GetBasicRight(SSSESSIONID,ClientId,intObj);
if (lRet == 0){
	out.println("AF_GetBasicRight Successfully.");
	out.println("Right:" + intObj.i);

}else{
	out.println("AF_GetBasicRight Failed." + lRet);
}


%>

</BODY>
</HTML>
