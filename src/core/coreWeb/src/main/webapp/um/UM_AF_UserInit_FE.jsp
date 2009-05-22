<%@ page language="java"%>
<%@ page contentType="text/html; charset=gb2312" %>
<%@ page import="com.stockstar.buos.um.*" %>

<%
long lRet;
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=GB2312">
<META name="GENERATOR" content="IBM WebSphere Studio">
<TITLE>Usermanager Client on JSP Sample</TITLE>
<style>
	body,table,p,td,input,select,option{font-family:verdana;font-size:9pt;}
</style>
</HEAD>
<BODY>
<h2>Usermanager Client on JSP Sample</h2>
<li>AF_UserInit
<blockquote>
<form name=f1 method=post action="UM_AF_UserInit.jsp">
	Username*: <input name="UserName" value="" onchange="f1.PenName.value=f1.UserName.value"><br>
	Penname*:<input name="PenName" value=""><br>
	Password1*: <input name="Pwd1" value=""> 用户基本密码,主要用于网站登录<br>
	Password2*: <input name="Pwd2" value=""> 用户数字密码,用于其他类型的登录<br>

	在设置登录类型时可以决定采用何种密码<br>
	Usertype: <input name="UserType" value="none"><br>
	Sex: <select name="Sex"><option value="1">Male</option><option value="0">Female</option></select>
	<br>
	Security Question ID:
	<select name="SecurityQuestionId">
	<%
	lRet = -1;
	SecurityQuestionList questionList = new SecurityQuestionList();
	lRet = UMClient.UF_GetSecurityQuestionList(questionList);
	if (lRet == 0){
	SecurityQuestion securityQuestion = new SecurityQuestion();

	out.println("UF_GetSecurityQuestionList Successfully.");
	out.print("Question Number:" + questionList.Size());
	for (int i=0; i<questionList.Size(); i++){
		questionList.Get(securityQuestion,i);
		out.println("<option value=" + securityQuestion.iQuestionId + ">");
		out.println(securityQuestion.strQuestion + "</option>");
	}

	}else{
		out.println("UF_GetSecurityQuestionList Failed." + lRet);
	}

	%>
	</select>
	<br>


	SecurityAnswer:<input type="text" name="SecurityAnswer" value=""><br>
	Password Hint:<input type="text" name="PwdHint" value=""><br>
	<input type="submit" value="Register">
</form>
</blockquote>
</BODY>
</HTML>
