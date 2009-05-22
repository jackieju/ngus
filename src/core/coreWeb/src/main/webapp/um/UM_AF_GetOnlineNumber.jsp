<%@ page language="java"%>
<%@ page contentType="text/html; charset=gb2312" %>
<%@ page import="com.stockstar.buos.um.*" %>
<%@ include file="CheckSession.jsp"%>
<%
//setHeader sample
response.setHeader("Cache-Control","no-cache");
response.setHeader("Pragma","no-cache");

int lRet = -1;

OnlineNumberInfo onlineNumberInfo = new OnlineNumberInfo();
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
<h3>UMClient.AF_GetOnlineNumber</h3>
<h4>Returns:</h4>

<%
lRet = UMClient.AF_GetOnlineNumber(SSSESSIONID,ClientId,(byte)0,onlineNumberInfo);
if (lRet == 0){
	out.println("AF_GetOnlineNumber Successfully.");
	out.println("Total OnlineNum :" + onlineNumberInfo.nOnlineNum);

}else{
	out.println("AF_GetOnlineNumber Failed." + lRet);
}


LogonTypeList logonTypeList = new LogonTypeList();
lRet = UMClient.AF_GetAllLogonTypeInfo(SSSESSIONID,ClientId,logonTypeList);
if (lRet!=0){
	 out.println("Can not get LogonTypeList:" + lRet);
}else{
	out.println("<table><tr bgcolor=whitesmoke><td>µÇÂ¼ÀàÐÍ±àºÅ</td><td>µÇÂ¼ÀàÐÍ</td><td>ÔÚÏßÈËÊý</td></tr>");
	LogonType logonType = new LogonType();
	for (int i=0; i<logonTypeList.Size(); i++){
		logonTypeList.Get(logonType,i);
		out.println("<tr><td>" + logonType.nLogonType + "</td><td>" + logonType.sLogonTypeName + "</td>");
		lRet = UMClient.AF_GetOnlineNumber(SSSESSIONID,ClientId,logonType.nLogonType,onlineNumberInfo);
		if (lRet == 0){
			//out.println("AF_GetOnlineNumber Successfully.");
			out.println("<td>" + onlineNumberInfo.nOnlineNum + "</td></tr>");

		}else{
			out.println("<td>AF_GetOnlineNumber Failed." + lRet + "</td></tr>");
		}

	}
	out.println("</table>");

}
%>

</BODY>
</HTML>
