<%@ page language="java"%>
<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="java.util.*" %>
<%@ page import="com.ngus.um.dbobject.*,com.ngus.um.*" %>
<%@ page import="com.ns.mail.*, com.ns.log.*" %>
<%
if(request.getParameter("strUserName")==null){
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=gb2312">
<META name="GENERATOR" content="IBM WebSphere Studio">
<TITLE>UMLogon JSP Sample</TITLE>
<style>
	body,table,p,td{font-family:verdana;font-size:9pt;}
</style>
</HEAD>
<BODY>
<p>
</p>
<h3>用户重置密码</h3>
<form name=f1 method=post action="forgetPassword.jsp">
	用户名:<input type="text" name="strUserName"><br>
	

	<input type="submit" name="action">
</form>
</BODY>
</HTML>
<%}else{%>
<%
int lRet = -1;



	
	
try{
	String strUserName = request.getParameter("strUserName");

	String strNewPasswd = new UMClient().resetPassword(strUserName);
	
	MailSettings mail = new MailSettings();	
	mail.ccAddresses = null;
	mail.fromAddress = "someone@xx.com";
	mail.fromName = "myWeb";
	mail.toAddress = new UMClient().getUserInfoByUserName(strUserName).getEmail();
	mail.messageBody = "Dear user, your new password is "+ strNewPasswd;
	mail.messageSubject = "Your password is reset";
	MailSender.sendMailThreaded(mail);
	out.println("Your new password has been sent to you by email, please check it<p>");
}catch(Exception e){
	Log.error(e);
	throw e;
}

out.println(" <a href=\"index.htm\">return!</a>");
%>

<%}%>