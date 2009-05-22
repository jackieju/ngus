<%@ page language="java"%>
<%@ page contentType="text/html; charset=gb2312" %>
<%@ page import="com.stockstar.buos.um.*" %>
<%@ include file="CheckSession.jsp"%>
<%
//setHeader sample
response.setHeader("Cache-Control","no-cache");
response.setHeader("Pragma","no-cache");

int lRet = -1;

//Integer RoleId = new Integer(request.getParameter("RoleId"));
String RoleName  = request.getParameter("RoleName").trim();
String RoleDescription  = request.getParameter("RoleDescription").trim();

RoleInfo roleInfo = new RoleInfo();

//roleInfo.lRoleId = RoleId.intValue();
roleInfo.sRoleName = RoleName;
roleInfo.sRoleDescription = RoleDescription;

lRet = UMClient.AF_AddRole(SSSESSIONID,ClientId,roleInfo);
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
<h3>UMClient.AF_AddRole</h3>
<h4>Returns:</h4>
<%
if (lRet == 0){
	out.println("AF_AddRole Successfully.");
}else{
	out.println("AF_AddRole Failed." + lRet);
}
%>
</BODY>
</HTML>
