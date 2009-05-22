<%@ page language="java"%>
<%@ page contentType="text/html; charset=gb2312" %>
<%@ page import="com.stockstar.buos.um.*" %>
<%@ include file="CheckSession.jsp"%>
<%
//setHeader sample
response.setHeader("Cache-Control","no-cache");
response.setHeader("Pragma","no-cache");

int R,lRet = -1;
String UserId;

LogonTypeInfo logonTypeOpen = new LogonTypeInfo();
Integer LogonType = new Integer(request.getParameter("LogonType"));
logonTypeOpen.nLogonType=LogonType.intValue();
logonTypeOpen.sId=request.getParameter("LogonID");

//Get target UserID from penname
String PenName = request.getParameter("PenName");
out.println(PenName);
UserPubInfo userPubInfo = new UserPubInfo();
R = UMClient.UF_GetPubInfoByPenName(PenName,userPubInfo);
if (R == 0){
	out.println("UF_GetPubInfoByPenName Successfully.");
	UserId = userPubInfo.sUserId;


	if (lCheckRet==0){
		lRet = UMClient.AF_CloseLogonType(SSSESSIONID,ClientId,UserId,logonTypeOpen);
	}else{
		out.println("<font color=red>CheckSession Failed, OPERATION CANCELLED.</font>");
	}

}else{
	out.println("UF_GetPubInfoByPenName Failed." + R);
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
<h3>UMClient.AF_CloseLogonType</h3>
<h4>Returns:</h4>
<%
if (lRet == 0){
	out.println("AF_CloseLogonType Successfully.");

}else{
	out.println("AF_CloseLogonType Failed." + lRet);
}
%>
</BODY>
</HTML>
