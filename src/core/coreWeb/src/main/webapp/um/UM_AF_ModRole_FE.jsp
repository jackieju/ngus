<%@ page language="java"%>
<%@ page contentType="text/html; charset=gb2312" %>
<%@ page import="com.stockstar.buos.um.*" %>
<%@ include file="CheckSession.jsp"%>
<%
//setHeader sample
response.setHeader("Cache-Control","no-cache");
response.setHeader("Pragma","no-cache");

int lRet = -1;

Integer RoleId = new Integer(request.getParameter("RoleId"));

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
<h3>UMClient.AF_ModRole</h3>
<h4>Returns:</h4>
<%
if (lRet == 0){
	RoleInfo roleInfo = new RoleInfo();

	out.println("AF_ModRole Successfully.");
	out.println("Role Number:" + roleInfoList.Size());

	for (int i=0; i<roleInfoList.Size(); i++){
		roleInfoList.Get(roleInfo,i);

		if (RoleId.intValue() == roleInfo.lRoleId){
		%>
			<form name=f1 method=post action="UM_AF_ModRole.jsp">
			<input type=hidden name="RoleId" value="<%=roleInfo.lRoleId%>">
			½ÇÉ«Ãû³Æ£º<input type="text" name="RoleName" value="<%=roleInfo.sRoleName%>"><br>
			½ÇÉ«ÃèÊö£º<input type="text" name="RoleDescription" value="<%=roleInfo.sRoleDescription%>"><br>
			<input type="hidden" name="action" value="modify">
			<input type=submit value="Modify Role">
			</form>
		<%
		}
	}
}else{
	out.println("AF_GetAllRole Failed." + lRet);
}
%>

</BODY>
</HTML>
