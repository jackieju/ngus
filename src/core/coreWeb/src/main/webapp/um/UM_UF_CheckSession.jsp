<%@ page language="java"%>
<%@ page contentType="text/html; charset=gb2312" %>
<%@ page import="com.stockstar.buos.um.*" %>
<%@ include file="CheckSession.jsp"%>
<%
//setHeader sample
response.setHeader("Cache-Control","no-cache");
response.setHeader("Pragma","no-cache");

/*
int lRet = -1;
String SSSESSIONID = "";
String ClientId = request.getRemoteAddr();		
UserPubInfo userPubInfo = new UserPubInfo();

Cookie[] cookies = request.getCookies();
Cookie cookie;
for(int i=0; i<cookies.length; i++) {
cookie = cookies[i];
if (cookie.getName().compareTo("SSSESSIONID")==0){
	SSSESSIONID = cookie.getValue();
	}
}

//out.println(SSSESSIONID);
if (SSSESSIONID.length()!=0){
	//out.println("Begin to checksession.");
	//nAppType - 应用服务的类型,推荐缺省设置0, strAppLog - 应用服务的记录信息，可以不填
	
	lCheckRet = UMClient.UF_CheckSession(SSSESSIONID,ClientId,0,"AppLog","USER.CHECKSESSION",userPubInfo);
	
}else{
	out.println("<font color=red>NO SESSIONID FOUND, CHECK SESSION CANCELLED.</font>");
}*/
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
<h3>UMClient.UF_CheckSession</h3>
<h4>Returns:</h4>
<%
if (lCheckRet == 0){
	out.println("================" + SSSESSIONID + "=====================<br>");
	out.println("===============" + ClientId + "==================<br>");
	
	out.println("Check Session Successfully.");
	out.println("<li>Penname:" + ckUserPubInfo.sPenName);
	out.println("<li>UserId:" + ckUserPubInfo.sUserId);
	out.println("<li>UserType:" + ckUserPubInfo.sUserType);
	out.println("<li>Level:" + ckUserPubInfo.btLevel);
	out.println("<li>Sex:" + ckUserPubInfo.btSex);
	out.println("<li>Personal Service IP:" + ckUserPubInfo.sPersonalServiceIP);
}else{
	out.println("Check Session Failed." + lCheckRet);
}
%>
</BODY>
</HTML>
