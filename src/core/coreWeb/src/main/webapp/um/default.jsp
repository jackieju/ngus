<%@ page language="java"%>
<%@ page contentType="text/html; charset=gb2312" %>

<%@ page import="com.stockstar.buos.um.*,com.ns.log.*" %>

<%
//setHeader sample
try{
response.setHeader("Cache-Control","no-cache");
response.setHeader("Pragma","no-cache");

int lRet=0;

boolean bCheckSessionOK=false;

String ClientId = request.getRemoteAddr();
	
// check session

String SSSESSIONID = "";
	
UserPubInfo userPubInfo = new UserPubInfo();


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
	//out.println("Begin to checksession.");
	//nAppType - 应用服务的类型,推荐缺省设置0, strAppLog - 应用服务的记录信息，可以不填

	lRet = UMClient.UF_CheckSession(SSSESSIONID,ClientId,0,"AppLog","USER.CHECKSESSION",userPubInfo);
	bCheckSessionOK = (lRet == 0);
	if (lRet != 0 )
	{
		bCheckSessionOK = false;
		out.println("CheckSession failed, return code " + lRet);
	}
	else
	bCheckSessionOK = true;
}else{
	out.println("<font color=red>NO SESSIONID FOUND, CHECK SESSION CANCELLED.</font>");
	bCheckSessionOK = false;
}
if (!bCheckSessionOK)
	response.sendRedirect("log_in.jsp");
}catch(Exception e){
	Log.trace(e);
}

%>
<HTML>
<HEAD>
<TITLE> 登录 </TITLE>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel="stylesheet" href="lib/newstyle.css" type="text/css">
</HEAD>
<BODY BGCOLOR=#FFFFFF LINK=#336666 TEXT=#000000 ALINK=#CC0000 VLINK=#666666>
<CENTER>
登录成功
<table>
<tr><td><A STYLE="text-decoration: underline" href="UM_UF_CheckSession.jsp">check session</a> </td></tr>
<tr><td><A STYLE="text-decoration: underline" href="UM_UF_GenNewSession.jsp">generate new session id</a></td></tr>
<tr><td><A STYLE="text-decoration: underline" href="ResetPwd.htm">重置密码</a></td></tr>
<tr><td><A STYLE="text-decoration: underline" href="change_pwd.jsp">修改密码</a></td></tr>
<tr><td><A STYLE="text-decoration: underline" href="GetInfo.jsp">用户信息(包括所有已开通登录类型)</a></td></tr>
<tr><td><A STYLE="text-decoration: underline" href="ModInfo.jsp">修改信息</a></td></tr>
<tr><td><A STYLE="text-decoration: underline" href="UM_AF.jsp">管理员页面</a></td></tr>
<tr><td><A STYLE="text-decoration: underline" href="LogOff.jsp">退出登录</a></td></tr>
</center>
</body>
</html> 