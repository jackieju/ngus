<%@ page language="java"%>
<%@ page contentType="text/html; charset=gb2312" %>
<%@ page import="com.stockstar.buos.um.*" %>
<%@ include file="CheckSession.jsp"%>
<%
//setHeader sample
response.setHeader("Cache-Control","no-cache");
response.setHeader("Pragma","no-cache");

int lRet = -1;
GroupInfoList groupInfoList = new GroupInfoList();
lRet = UMClient.AF_GetAllGroup(SSSESSIONID,ClientId,groupInfoList);
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
<h3>UMClient.AF_GetAllGroup</h3>
<h4>Returns:</h4>
<%
if (lRet == 0){
	GroupInfo groupInfo = new GroupInfo();

	out.println("AF_GetAllGroup Successfully.");
	out.println("Group Number:" + groupInfoList.Size());
	out.println("<form name=f1 method=post action=\"UM_AF_DelGroup.jsp\">");
	out.println("<table border=0 bgcolor=silver>");
	out.println("<tr><th>用户组ID</th><th>用户组名称</th><th>用户组说明</th><th>是否默认</th></tr>");
	for (int i=0; i<groupInfoList.Size(); i++){
		groupInfoList.Get(groupInfo,i);
		out.println("<tr bgcolor=white><td><input type=radio name=GroupId value=\"" + groupInfo.lGroupId + "\">" + groupInfo.lGroupId + "</td><td><a href=\"UM_AF_ModGroup_FE.jsp?GroupId=" + groupInfo.lGroupId + "\">" + groupInfo.sGroupName + "</a></td><td>" + groupInfo.sGroupDescription + "</td><td>" + groupInfo.nIsDefault + "</td></tr>");
	}
	out.println("</table>");
	out.println("<input type=submit value=\"Delete Selected Group\">");
	out.println("</form>");

}else{
	out.println("AF_GetAllGroup Failed." + lRet);
}
%>

<li>AF_AddGroup 增加一种用户组</li>
<form name=f2 method=post action="UM_AF_AddGroup.jsp">
<!--	用户组ID：<input type="text" name="GroupId"><br>-->
	用户组名称：<input type="text" name="GroupName"><br>
	用户组说明：<input type="text" name="GroupDescription"><br>
	是否默认: <select name="IsDefault">
		  	<option value="1">是</option>
		  	<option value="0">否</option>
		  </select><br>
	<input type="submit" value="Add Group">
</form>
</BODY>
</HTML>
