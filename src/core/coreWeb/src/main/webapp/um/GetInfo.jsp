<%@ page language="java"%>
<%@ page contentType="text/html; charset=gb2312" %>

<%@ page import="com.stockstar.buos.um.*" %>
<%
//setHeader sample
response.setHeader("Cache-Control","no-cache");
response.setHeader("Pragma","no-cache");

int lRet = -1;
String SSSESSIONID = "";
String ClientId = request.getRemoteAddr();
UserInfo userInfo = new UserInfo();
LogonTypeInfoList logonTypeList = new LogonTypeInfoList();

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

	lRet = UMClient.UF_GetInfo(SSSESSIONID,ClientId,"USER.GETINFO",userInfo,logonTypeList);
	
}else{
	out.println("<font color=red>NO SESSIONID FOUND, GetInfo CANCELLED.</font>");
}
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=GB2312">
<META name="GENERATOR" content="IBM WebSphere Studio">
<TITLE>Usermanager Client on JSP Sample</TITLE>
<style>
	body,table,p,td{font-family:verdana;font-size:9pt;}
</style>
</HEAD>
<BODY>
<h3>UMClient.UF_GetInfo</h3>
<h4>Returns:</h4>
<%
if (lRet == 0){
	out.println("GetInfo Successfully.");
	out.println("<li>Penname:" + userInfo.sPenName);
	out.println("<li>Usertype:" + userInfo.sUserType);
	out.println("<li>Level:" + userInfo.btLevel);
	out.println("<li>Sex:" + userInfo.btSex);
	out.println("<li>Security Question ID:" + userInfo.lSecurityQuestionId);
	out.println("<li>Security Answer:" + userInfo.sSecurityAnswer);
	out.println("<li>Password Hint:" + userInfo.sPwdHint);
	out.println("<li>Personal Service IP:" + userInfo.sPersonalServiceIP);
	
	out.println("<p>LogonType List</p>");
	LogonTypeInfo s = new LogonTypeInfo();
	out.print ("LogonType Size:" + logonTypeList.Size() + "<p>");
	
	for (int i=0; i<logonTypeList.Size(); i++){
		
		boolean bRet = logonTypeList.Get(s,i);
		if (bRet){
			out.println("<li>LogonType:" + s.nLogonType);
			out.println(" LogonID:" + s.sId);
		}
		
	}
	
}else{
	out.println("GetInfo Failed." + lRet);
}
%>
</BODY>
</HTML>
