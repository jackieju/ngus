<%@ page language="java"%>
<%@ page contentType="text/html; charset=gb2312" %>
<%@ page import="com.stockstar.buos.um.*" %>
<%@ include file="CheckSession.jsp"%>
<%
//setHeader sample
response.setHeader("Cache-Control","no-cache");
response.setHeader("Pragma","no-cache");

int lRet = -1;

byte LogonTypeId = (new Byte(request.getParameter("LogonTypeId"))).byteValue();

lRet = UMClient.AF_DelLogonType(SSSESSIONID,ClientId,LogonTypeId);

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
<h3>UMClient.AF_DelLogonType</h3>
<h4>Returns:</h4>
<%
if (lRet == 0){
	out.println("AF_DelLogonType Successfully." + LogonTypeId);
}else{
	out.println("AF_DelLogonType Failed." + lRet);
}
%>

</BODY>
</HTML>
