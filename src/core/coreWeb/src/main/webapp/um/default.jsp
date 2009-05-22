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
	//nAppType - Ӧ�÷��������,�Ƽ�ȱʡ����0, strAppLog - Ӧ�÷���ļ�¼��Ϣ�����Բ���

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
<TITLE> ��¼ </TITLE>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel="stylesheet" href="lib/newstyle.css" type="text/css">
</HEAD>
<BODY BGCOLOR=#FFFFFF LINK=#336666 TEXT=#000000 ALINK=#CC0000 VLINK=#666666>
<CENTER>
��¼�ɹ�
<table>
<tr><td><A STYLE="text-decoration: underline" href="UM_UF_CheckSession.jsp">check session</a> </td></tr>
<tr><td><A STYLE="text-decoration: underline" href="UM_UF_GenNewSession.jsp">generate new session id</a></td></tr>
<tr><td><A STYLE="text-decoration: underline" href="ResetPwd.htm">��������</a></td></tr>
<tr><td><A STYLE="text-decoration: underline" href="change_pwd.jsp">�޸�����</a></td></tr>
<tr><td><A STYLE="text-decoration: underline" href="GetInfo.jsp">�û���Ϣ(���������ѿ�ͨ��¼����)</a></td></tr>
<tr><td><A STYLE="text-decoration: underline" href="ModInfo.jsp">�޸���Ϣ</a></td></tr>
<tr><td><A STYLE="text-decoration: underline" href="UM_AF.jsp">����Աҳ��</a></td></tr>
<tr><td><A STYLE="text-decoration: underline" href="LogOff.jsp">�˳���¼</a></td></tr>
</center>
</body>
</html> 