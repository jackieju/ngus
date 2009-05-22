<%@ page language="java"%>
<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%> 
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
		<h3>用户重置密码</h3>
		
		<html:form action="/forgetPassword" method="POST">
			用户名:<html:text property="userName" />
			<br>
			输入验证码:<html:text property="validCode" /><img width=60  height=20 src="validateCode.jsp" >
			<br>
			<html:submit>发送新密码</html:submit>
		</html:form>
	</BODY>
</HTML>
