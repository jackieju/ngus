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
<h3>UMClient.AF_ModLogonType</h3>
<h4>Returns:</h4>
<%
if (lRet == 0){
	LogonType logonType = new LogonType();

	out.println("AF_GetAllLogonTypeInfo Successfully.");
	out.println("LogonType Number:" + logonTypeList.Size());


	for (int i=0; i<logonTypeList.Size(); i++){
		logonTypeList.Get(logonType,i);

		if (logonType.nLogonType==(new Byte(request.getParameter("ID"))).byteValue()){
		%>
			<form name=f1 method=post action="UM_AF_ModLogonType.jsp">
			<input type=hidden name="LogonTypeId" value="<%=logonType.nLogonType%>">
			µÇÂ¼ÀàÐÍÃû³Æ£º<input type="text" name="LogonTypeName" value="<%=logonType.sLogonTypeName%>"><br>
			µÇÂ¼ÀàÐÍËµÃ÷£º<input type="text" name="LogonTypeDescription" value="<%=logonType.sLogonTypeDescription%>"><br>
			µÇÂ¼ÃÜÂëÀàÐÍ£º<select name="WhichPasswd">
			<option value="0" <%if (logonType.btWhichPasswd==0) out.print("selected");%>>0-²»ÐèÒªÃÜÂë</option>
			<option value="1" <%if (logonType.btWhichPasswd==1) out.print("selected");%>>1-×Ö·ûÃÜÂë</option>
			<option value="2" <%if (logonType.btWhichPasswd==2) out.print("selected");%>>2-Êý×ÖÃÜÂë</option>
			</select>
			<input type="hidden" name="action" value="modify">
			<input type=submit value="Modify LogonType">
			</form>
		<%
		}
	}
}else{
	out.println("AF_GetAllLogonTypeInfo Failed." + lRet);
}
%>

</BODY>
</HTML>
