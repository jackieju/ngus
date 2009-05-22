<%@ page language="java"%>
<%@ page contentType="text/html; charset=gb2312" %>
<%@ page import="com.stockstar.buos.um.*" %>
<%@ include file="CheckSession.jsp"%>
<%
//setHeader sample
response.setHeader("Cache-Control","no-cache");
response.setHeader("Pragma","no-cache");

int lRet = -1;

Integer GroupId = new Integer(request.getParameter("GroupId"));
String GroupName  = request.getParameter("GroupName").trim();
String GroupDescription  = request.getParameter("GroupDescription").trim();
Integer IsDefault = new Integer(request.getParameter("IsDefault"));

GroupInfo groupInfo = new GroupInfo();

groupInfo.lGroupId = GroupId.intValue();
groupInfo.sGroupName = GroupName;
groupInfo.sGroupDescription = GroupDescription;
groupInfo.nIsDefault = IsDefault.intValue();

lRet = UMClient.AF_ModGroup(SSSESSIONID,ClientId,groupInfo);

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
<h3>UMClient.AF_ModGroup</h3>
<h4>Returns:</h4>
<%
if (lRet == 0){
	out.println("AF_ModGroup Successfully.");
}else{
	out.println("AF_ModGroup Failed." + lRet);
}
%>

</BODY>
</HTML>
