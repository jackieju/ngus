<%@ page language="java"%>
<%@ page contentType="text/html; charset=gb2312" %>
<%@ page import="com.stockstar.buos.um.*" %>
<%@ include file="CheckSession.jsp"%>
<%
//setHeader sample
response.setHeader("Cache-Control","no-cache");
response.setHeader("Pragma","no-cache");

int lRet = -1;

String AuthorityObjectId  = request.getParameter("AuthorityObjectId");

AuthorityObjectList authorityObjectList = new AuthorityObjectList();
lRet = UMClient.AF_GetAllAuthorityObject(SSSESSIONID,ClientId,authorityObjectList);

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
<h3>UMClient.AF_ModAuthorityObject</h3>
<h4>Returns:</h4>
<%
if (lRet == 0){
	AuthorityObject authorityObject = new AuthorityObject();

	out.println("AF_ModAuthorityObject Successfully.");
	out.println("AuthorityObject Number:" + authorityObjectList.Size());

	for (int i=0; i<authorityObjectList.Size(); i++){
		authorityObjectList.Get(authorityObject,i);

		if ((AuthorityObjectId.trim()).compareTo(authorityObject.sObjectId.trim()) == 0){
		%>
			<form name=f1 method=post action="UM_AF_ModAuthorityObject.jsp">
			<input type=hidden name="AuthorityObjectId" value="<%=authorityObject.sObjectId%>">
			È¨ÏÞÃû³Æ£º<input type="text" name="AuthorityObjectName" value="<%=authorityObject.sObjectName%>"><br>
			È¨ÏÞÃèÊö£º<input type="text" name="AuthorityObjectDescription" value="<%=authorityObject.sObjectDescription%>"><br>
			<input type="hidden" name="action" value="modify">
			<input type=submit value="Modify AuthorityObject">
			</form>
		<%
		}
	}
}else{
	out.println("AF_GetAllAuthorityObject Failed." + lRet);
}
%>

</BODY>
</HTML>
