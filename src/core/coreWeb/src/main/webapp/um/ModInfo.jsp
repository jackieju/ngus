<%@ page language="java"%>
<%@ page contentType="text/html; charset=gb2312" %>
<%@ page import="com.stockstar.buos.um.*" %>
<%
//setHeader sample
response.setHeader("Cache-Control","no-cache");
response.setHeader("Pragma","no-cache");

int lRet = -1;
int lRet2 = -1;
String SSSESSIONID = "";
String ClientId = request.getRemoteAddr();
UserInfo userInfo = new UserInfo();
LogonTypeInfoList logonTypeList = new LogonTypeInfoList();

Cookie[] cookies = request.getCookies();
Cookie cookie;
for(int i=0; i<cookies.length; i++) {
cookie = cookies[i];
if (cookie.getName().compareTo("SSSESSIONID")==0){
	SSSESSIONID = cookie.getValue();
	}
}

//out.println(SSSESSIONID);
if (SSSESSIONID.length()!=0){

	if (request.getParameter("action")!=null){

		UserInfo userinfo = new UserInfo();

		Byte sex = new Byte(request.getParameter("Sex"));
		Integer SecurityQuestionId = new Integer(request.getParameter("SecurityQuestionId"));

		userinfo.btSex = sex.byteValue();
		userinfo.sPenName = request.getParameter("PenName");
		userinfo.lSecurityQuestionId = SecurityQuestionId.intValue();
		userinfo.sSecurityAnswer = request.getParameter("SecurityAnswer");
		userinfo.sUserType = request.getParameter("UserType");
		userinfo.sPwdHint = request.getParameter("PwdHint");

		lRet2 = UMClient.UF_ModInfo(SSSESSIONID,ClientId,userinfo);
	}

	lRet = UMClient.UF_GetInfo(SSSESSIONID,ClientId,"USER.GETINFO",userInfo,logonTypeList);

}else{
	out.println("<font color=red>NO SESSIONID FOUND, GetInfo CANCELLED.</font>");
}
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
<h3>UMClient.UF_GetInfo</h3>
<h4>Returns:</h4>
<%
if (lRet2 == 0){
	out.println("ModInfo Successfully.");
}else{
	out.println("<font color=red>ModInfo Failed." + lRet + "</font> Possible Reason: PenName duplicated.");
}
if (lRet == 0){

	out.println("<p>GetInfo Successfully.");
	out.println("<form name=f1 method=post action=ModInfo.jsp>");
	out.println("<li>Penname:<input type=text name=PenName value=\"" + userInfo.sPenName + "\">");
	out.println("<li>Usertype:<input type=text name=UserType value=\"" + userInfo.sUserType + "\">");
	out.println("<li>Level:" + userInfo.btLevel);
	out.println("<li>Sex:");
	out.println("<select name=\"Sex\"><option ");
	if (userInfo.btSex == 1) out.println("selected");
	out.println (" value=\"1\">Male</option><option ");
	if (userInfo.btSex == 0) out.println("selected");
	out.println(" value=\"0\">Female</option></select>");

	out.println("<li>Security Question ID:<input type=text name=\"SecurityQuestionId\" value=\"" + userInfo.lSecurityQuestionId + "\">");
	out.println("<li>Security Answer:<input type=text name=SecurityAnswer value=\"" + userInfo.sSecurityAnswer + "\">");
	out.println("<li>Password Hint:<input type=text name=PwdHint value=\"" + userInfo.sPwdHint + "\">");
	out.println("<li>Personal Service IP:" + userInfo.sPersonalServiceIP);
	out.println("<input type=hidden name=action value=save><input type=submit value=save>");
	out.println("</form>");

}else{
	out.println("GetInfo Failed." + lRet);
}
%>
</BODY>
</HTML>
