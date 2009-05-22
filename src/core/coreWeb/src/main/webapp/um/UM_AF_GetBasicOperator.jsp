<%@ page language="java"%>
<%@ page contentType="text/html; charset=gb2312" %>
<%@ page import="com.stockstar.buos.um.*" %>
<%@ include file="CheckSession.jsp"%>
<%
//setHeader sample
response.setHeader("Cache-Control","no-cache");
response.setHeader("Pragma","no-cache");

int lRet = -1;

AdminRightInfoList adminRightInfoList = new AdminRightInfoList();
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
<h3>UMClient.AF_GetBasicOperator</h3>
<h4>Returns:</h4>
<%
lRet = UMClient.AF_GetBasicOperator(SSSESSIONID,ClientId,adminRightInfoList);
if (lRet == 0){
	AdminRightInfo adminRightInfo = new AdminRightInfo();
	out.println("AF_GetBasicOperator Successfully.");
	out.println("AdminRightInfoList Size:" + adminRightInfoList.Size());
	out.println("<table border=0>");
	for (int i=0;i<adminRightInfoList.Size();i++){
		if (adminRightInfoList.Get(adminRightInfo,i)){
			out.println("<tr><td>" + adminRightInfo.lRight + "</td><td>" + adminRightInfo.sAdminName + "</td><td>" + adminRightInfo.sAdminUid + "</td><td>" + adminRightInfo.sAdminUserType + "</td></tr>");
		}
	}
	out.println("</table>");
}else{
	out.println("AF_GetBasicOperator Failed." + lRet);
}


%>

</BODY>
</HTML>
