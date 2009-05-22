<%@ page language="java"%>
<%@ page contentType="text/html; charset=gb2312" %>
<%@ page import="com.stockstar.buos.um.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.lang.*" %>
<%
int lRet = -1;
String RetMsg = "UMClient.UM_UF_ResetPasswd:";

if("UM_UF_ResetPasswd".equals(request.getParameter("action"))){
	StringObj strNewPasswd = new StringObj();
	String strSecurityAnswer = request.getParameter("strSecurityAnswer");
	String strUserName = request.getParameter("strUserName");
	String strUserType = request.getParameter("strUserType");
	byte btResetType = new Integer(request.getParameter("btResetType")).byteValue();
	//byte btResetType = 2;
	lRet = UMClient.UF_ResetPasswd(strUserName, strUserType, strSecurityAnswer, btResetType , strNewPasswd);
	if (lRet == 0 ){
		RetMsg = RetMsg + "strNewPasswd=" + strNewPasswd.strString;
	}else{
		RetMsg = "Reset failed:" + lRet;
	}
}
else{
	RetMsg = RetMsg + "parameter null";
}
out.println(RetMsg);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<HTML>
<HEAD>
<META name="GENERATOR" content="IBM WebSphere Studio">
</HEAD>
</HTML>