<%@ page language="java"%>
<%@ page contentType="text/html; charset=gb2312" %>
<%@ page import="com.stockstar.buos.um.*" %>
<%@ include file="CheckSession.jsp"%>
<%
//setHeader sample
response.setHeader("Cache-Control","no-cache");
response.setHeader("Pragma","no-cache");

int R,lRet = -1;
UserInfoEx userInfo = new UserInfoEx();

LogonTypeInfoList logonTypeList = new LogonTypeInfoList();

String PenName = request.getParameter("PenName");
out.println(PenName);
UserPubInfo userPubInfo = new UserPubInfo();
R = UMClient.UF_GetPubInfoByPenName(PenName,userPubInfo);
if (R == 0){
	out.println("UF_GetPubInfoByPenName Successfully.");
	String UserId = userPubInfo.sUserId;
	lRet = UMClient.AF_GetInfo(SSSESSIONID,ClientId,UserId,userInfo,logonTypeList);
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
<h3>UMClient.AF_GetInfo</h3>
<h4>Returns:</h4>
<%
if (lRet == 0){

	out.println("AF_GetInfo Successfully.");
	out.println("<li>Penname:" + userInfo.sPenName);
	out.println("<li>Usertype:" + userInfo.sUserType);
	out.println("<li>Level:" + userInfo.btLevel);
	out.println("<li>Sex:" + userInfo.btSex);
	out.println("<li>Security Question ID:" + userInfo.lSecurityQuestionId);
	out.println("<li>Security Answer:" + userInfo.sSecurityAnswer);
	out.println("<li>Password Hint:" + userInfo.sPwdHint);
	out.println("<li>Personal Service IP:" + userInfo.sPersonalServiceIP);

	out.println("<b><li>Valid:" + userInfo.btValid + "</b>");
	out.println("<b><li>UserName:" + userInfo.strUserName + "</b>");


	out.println("<h4>LogonType Info List</h4>");
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
	out.println("AF_GetInfo Failed." + lRet);
}
%>

</BODY>
</HTML>
