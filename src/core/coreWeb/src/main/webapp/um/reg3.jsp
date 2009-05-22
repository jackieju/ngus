<!-- User Register step 3 -->
<%@ page language="java"%>
<%@ page contentType="text/html; charset=gb2312" %>

<%@ page import="com.stockstar.buos.um.*" %>
<%

//setHeader sample
response.setHeader("Cache-Control","no-cache");
response.setHeader("Pragma","no-cache");

//Get submited elements

int lRet;

String UserName = request.getParameter("UserName");
String PenName = request.getParameter("PenName");
String Pwd1 = request.getParameter("Password");
String Pwd2 = request.getParameter("RePassword");
String UserType = request.getParameter("UserType");
String sSex = request.getParameter("Sex");
String sPwdHint = request.getParameter("PasswordHint");
if (sPwdHint == null)
	sPwdHint = new String("");
String sSecurityAnswer = request.getParameter("QuestionAnswer");
Integer SecurityQuestionId = new Integer(request.getParameter("QuestionId"));

out.println(UserName + ", " + PenName + ", " + Pwd1 + ", " + Pwd2 + ", " + UserType + ", " + 
sSex + ", " + sSex + ", " + sSecurityAnswer + ", " + SecurityQuestionId + ", " + sPwdHint);

// check pasword
if (Pwd1.compareTo(Pwd2) != 0)
{
	
}

UserInfo userinfo = new UserInfo();
StringObj userid = new StringObj();
Byte sex = new Byte(sSex);

userinfo.btSex = sex.byteValue();
//out.println(request.getParameter("PenName"));
userinfo.sPenName = PenName;
userinfo.lSecurityQuestionId = SecurityQuestionId.intValue();
userinfo.sSecurityAnswer = sSecurityAnswer;
//out.println("userType:" + request.getParameter("UserType"));
userinfo.sUserType = UserType;
userinfo.sPwdHint = sPwdHint;


lRet = UMClient.UF_UserInit(UserName,Pwd1,Pwd2,userinfo,userid);
out.println("UF_UserInit return " + lRet);
/*
if (lRet == 0)
{
	response.sendRedirect("reg_ok.jsp?UserName="+UserName + "&PenName=" + PenName);
}
else
{
	response.sendRedirect("reg_err.jsp?ErrCode="+lRet + "&UserName="+UserName + "&PenName=" + PenName);
}
	

*/

%>