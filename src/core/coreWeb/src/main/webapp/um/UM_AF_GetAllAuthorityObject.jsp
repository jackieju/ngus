<%@ page language="java"%>
<%@ page contentType="text/html; charset=gb2312" %>
<%@ page import="com.stockstar.buos.um.*" %>
<%@ include file="CheckSession.jsp"%>
<%
//setHeader sample
response.setHeader("Cache-Control","no-cache");
response.setHeader("Pragma","no-cache");

int lRet = -1;
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
<h3>UMClient.AF_GetAllAuthorityObject</h3>
<h4>Returns:</h4>
<%
if (lRet == 0){
	AuthorityObject authorityObject = new AuthorityObject();

	out.println("AF_GetAllAuthorityObject Successfully.");
	out.println("AuthorityObject Number:" + authorityObjectList.Size());
	out.println("<form name=f1 method=post action=\"UM_AF_DelAuthorityObject.jsp\">");
	out.println("<table border=0 bgcolor=silver>");
	out.println("<tr><th>È¨ÏÞID</th><th>È¨ÏÞÃû³Æ</th><th>È¨ÏÞËµÃ÷</th></tr>");
	for (int i=0; i<authorityObjectList.Size(); i++){
		authorityObjectList.Get(authorityObject,i);
		out.println("<tr bgcolor=white><td><input type=radio name=AuthorityObjectId value=\"" + authorityObject.sObjectId + "\">" + authorityObject.sObjectId + "</td><td><a href=\"UM_AF_ModAuthorityObject_FE.jsp?AuthorityObjectId=" + authorityObject.sObjectId + "\">" + authorityObject.sObjectName + "</a></td><td>" + authorityObject.sObjectDescription + "</td></tr>");
	}
	out.println("</table>");
	out.println("<input type=submit value=\"Delete Selected AuthorityObject\">");
	out.println("</form>");

}else{
	out.println("AF_GetAllAuthorityObject Failed." + lRet);
}
%>

<li>AF_AddAuthorityObject Ôö¼ÓÒ»ÖÖÈ¨ÏÞ</li>
<form name=f2 method=post action="UM_AF_AddAuthorityObject.jsp">
	È¨ÏÞ¸¸ID£º<input type="text" name="AuthorityObjectId"><br>
	È¨ÏÞÃû³Æ£º<input type="text" name="AuthorityObjectName"><br>
	È¨ÏÞËµÃ÷£º<input type="text" name="AuthorityObjectDescription"><br>
	<input type="submit" value="Add AuthorityObject">
</form>
</BODY>
</HTML>
