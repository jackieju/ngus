<%@ page language="java"%>
<%@ page contentType="text/html; charset=gb2312" %>
<%@ page import="com.stockstar.buos.um.*" %>
<%@ include file="CheckSession.jsp"%>
<%
//setHeader sample
response.setHeader("Cache-Control","no-cache");
response.setHeader("Pragma","no-cache");

//Get submited elements

int lRet;

String UserName = request.getParameter("UserName");
String Pwd1 = request.getParameter("Pwd1");
String Pwd2 = request.getParameter("Pwd2");

UserInfo userinfo = new UserInfo();
StringObj userid = new StringObj();

Byte sex = new Byte(request.getParameter("Sex"));
Integer SecurityQuestionId = new Integer(request.getParameter("SecurityQuestionId"));

userinfo.btSex = sex.byteValue();
out.println(request.getParameter("PenName"));
userinfo.sPenName = request.getParameter("PenName");
userinfo.lSecurityQuestionId = SecurityQuestionId.intValue();
userinfo.sSecurityAnswer = request.getParameter("SecurityAnswer");
out.println("userType:" + request.getParameter("UserType"));
userinfo.sUserType = request.getParameter("UserType");
userinfo.sPwdHint = request.getParameter("PwdHint");

lRet = UMClient.AF_UserInit(SSSESSIONID,ClientId,(byte)1,UserName,Pwd1,Pwd2,userinfo,userid);

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
<h3>UMClient.AF_UserInit</h3>
<h4>Returns:</h4>
<%
if (lRet == 0){
	out.println("User Register Successfully.");
	out.println("UserId:" + userid.strString);
}else{
	out.println("User Register Failed." + lRet);
}
%>
</BODY>
</HTML>
