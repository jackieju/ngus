<%@ page language="java"%>
<%@ page contentType="text/html; charset=gb2312" %>
<%@ page import="com.stockstar.buos.um.*" %>
<%@ include file="CheckSession.jsp"%>
<%
//setHeader sample
response.setHeader("Cache-Control","no-cache");
response.setHeader("Pragma","no-cache");

int lRet = -1;

LogonTypeInfo logonTypeInfo = new LogonTypeInfo();

String LogonId = request.getParameter("LogonId");
Integer LogonType = new Integer(request.getParameter("LogonType"));
logonTypeInfo.nLogonType=LogonType.intValue();
logonTypeInfo.sId = LogonId;

UserPubInfo userPubInfo = new UserPubInfo();

lRet = UMClient.AF_GetPubInfo(SSSESSIONID,ClientId,logonTypeInfo,userPubInfo);
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
<h3>UMClient.AF_GetPubInfo</h3>
<h4>Returns:</h4>
<%
if (lRet == 0){

	out.println("AF_GetPubInfo Successfully.");
	out.println("<li>Penname:" + userPubInfo.sPenName);
	out.println("<li>UserId:" + userPubInfo.sUserId);
	out.println("<li>UserType:" + userPubInfo.sUserType);
	out.println("<li>Level:" + userPubInfo.btLevel);
	out.println("<li>Sex:" + userPubInfo.btSex);
	out.println("<li>Personal Service IP:" + userPubInfo.sPersonalServiceIP);

}else{
	out.println("AF_GetPubInfo Failed." + lRet);
}
%>

</BODY>
</HTML>
