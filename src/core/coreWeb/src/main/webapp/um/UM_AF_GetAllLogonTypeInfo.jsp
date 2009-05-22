<%@ page language="java"%>
<%@ page contentType="text/html; charset=gb2312" %>
<%@ page import="com.stockstar.buos.um.*" %>
<%@ include file="CheckSession.jsp"%>
<%
//setHeader sample
response.setHeader("Cache-Control","no-cache");
response.setHeader("Pragma","no-cache");

int lRet = -1;
LogonTypeList logonTypeList = new LogonTypeList();
lRet = UMClient.AF_GetAllLogonTypeInfo(SSSESSIONID,ClientId,logonTypeList);

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
<h3>UMClient.AF_GetAllLogonTypeInfo</h3>
<h4>Returns:</h4>
<%
if (lRet == 0){
	LogonType logonType = new LogonType();

	out.println("AF_GetAllLogonTypeInfo Successfully.");
	out.println("LogonType Number:" + logonTypeList.Size());
	out.println("<form name=f1 method=post action=\"UM_AF_DelLogonType.jsp\">");
	out.println("<table border=0 bgcolor=silver>");
	out.println("<tr><th>��¼����ID</th><th>��¼����˵��</th><th>LogonTypeName</th><th>��������</th></tr>");
	for (int i=0; i<logonTypeList.Size(); i++){
		logonTypeList.Get(logonType,i);
		out.println("<tr bgcolor=white><td><input type=radio name=LogonTypeId value=\"" + logonType.nLogonType + "\">" + logonType.nLogonType + "</td><td><a href=\"UM_AF_ModLogonType_FE.jsp?ID=" + logonType.nLogonType + "\">" + logonType.sLogonTypeDescription + "</a></td><td>" + logonType.sLogonTypeName + "</td><td>" + logonType.btWhichPasswd + "</td></tr>");
	}
	out.println("</table>");
	out.println("<input type=submit value=\"Delete Selected LogonType\">");
	out.println("</form>");

}else{
	out.println("AF_GetAllLogonTypeInfo Failed." + lRet);
}
%>

<li>AF_AddLogonType ����һ�ֵ�¼����</li>
<form name=f2 method=post action="UM_AF_AddLogonType.jsp">
	��¼�������ƣ�<input type="text" name="LogonTypeName"><br>
	��¼����˵����<input type="text" name="LogonTypeDescription"><br>
	��¼�������ͣ�<select name="WhichPasswd">
			<option value="0">0-����Ҫ����</option>
			<option value="1">1-�ַ�����</option>
			<option value="2">2-��������</option>
		</select>
	<input type="submit" value="Add LogonType">
</form>
</BODY>
</HTML>
