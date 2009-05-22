<%@ page language="java"%>
<%@ page contentType="text/html; charset=gb2312" %>
<%@ page import="com.stockstar.buos.um.*" %>
<%@ include file="CheckSession.jsp"%>
<%
//setHeader sample
response.setHeader("Cache-Control","no-cache");
response.setHeader("Pragma","no-cache");

int lRet = -1;
RoleInfoList roleInfoList = new RoleInfoList();
lRet = UMClient.AF_GetAllRole(SSSESSIONID,ClientId,roleInfoList);

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
<h3>UMClient.AF_GetAllRole</h3>
<h4>Returns:</h4>
<%
if (lRet == 0){
	RoleInfo roleInfo = new RoleInfo();

	out.println("AF_GetAllRole Successfully.");
	out.println("Role Number:" + roleInfoList.Size());
	out.println("<form name=f1 method=post action=\"UM_AF_DelRole.jsp\">");
	out.println("<table border=0 bgcolor=silver>");
	out.println("<tr><th>½ÇÉ«ID</th><th>½ÇÉ«Ãû³Æ</th><th>½ÇÉ«ËµÃ÷</th></tr>");
	for (int i=0; i<roleInfoList.Size(); i++){
		roleInfoList.Get(roleInfo,i);
		out.println("<tr bgcolor=white><td><input type=radio name=RoleId value=\"" + roleInfo.lRoleId + "\">" + roleInfo.lRoleId + "</td><td><a href=\"UM_AF_ModRole_FE.jsp?RoleId=" + roleInfo.lRoleId + "\">" + roleInfo.sRoleName + "</a></td><td>" + roleInfo.sRoleDescription + "</td></tr>");
	}
	out.println("</table>");
	out.println("<input type=submit value=\"Delete Selected Role\">");
	out.println("</form>");

}else{
	out.println("AF_GetAllRole Failed." + lRet);
}
%>

<li>AF_AddRole Ôö¼ÓÒ»ÖÖ½ÇÉ«</li>
<form name=f2 method=post action="UM_AF_AddRole.jsp">
<!--	½ÇÉ«ID£º<input type="text" name="RoleId"><br>-->
	½ÇÉ«Ãû³Æ£º<input type="text" name="RoleName"><br>
	½ÇÉ«ËµÃ÷£º<input type="text" name="RoleDescription"><br>
	<input type="submit" value="Add Role">
</form>
</BODY>
</HTML>
