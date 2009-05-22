<%@ page language="java"%>
<%@ page contentType="text/html; charset=gb2312" %>
<%@ page import="com.stockstar.buos.um.*" %>
<%@ include file="CheckSession.jsp"%>
<%
//setHeader sample
response.setHeader("Cache-Control","no-cache");
response.setHeader("Pragma","no-cache");

int R,lRet = -1;

String PenName = request.getParameter("PenName");
byte ResetType = (new Byte(request.getParameter("ResetType"))).byteValue();
StringObj strNewPwd = new StringObj();

out.println(PenName);
UserPubInfo userPubInfo = new UserPubInfo();
R = UMClient.UF_GetPubInfoByPenName(PenName,userPubInfo);
if (R == 0){
	out.println("UF_GetPubInfoByPenName Successfully.");
	String UserId = userPubInfo.sUserId;
	out.println("UserId:" + UserId);
	lRet = UMClient.AF_ResetPasswd(SSSESSIONID,ClientId,UserId,ResetType,strNewPwd);
}else{
	out.println("UF_GetPubInfoByPenName Failed." + R);
}

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
<h3>UMClient.AF_ModUserLevel</h3>
<h4>Returns:</h4>
<%
if (lRet == 0){
	out.println("AF_ResetPasswd Successfully.");
	out.println("New Password:" + strNewPwd.strString);
}else{
	out.println("AF_ResetPasswd Failed." + lRet);
}
%>

</BODY>
</HTML>
