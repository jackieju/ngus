<%@ page language="java"%>
<%@ page contentType="text/html; charset=gb2312" %>
<%@ page import="java.util.*" %>
<%@ page import="com.stockstar.buos.um.*" %>
<%@ include file="CheckSession.jsp"%>
<%
//setHeader sample
response.setHeader("Cache-Control","no-cache");
response.setHeader("Pragma","no-cache");

int R,lRet = -1;
SessionStatusList sessionStatusList = new SessionStatusList();

String PenName = request.getParameter("PenName");
out.println(PenName);
UserPubInfo userPubInfo = new UserPubInfo();
R = UMClient.UF_GetPubInfoByPenName(PenName,userPubInfo);
if (R == 0){
	out.println("UF_GetPubInfoByPenName Successfully.");
	String UserId = userPubInfo.sUserId;
	lRet = UMClient.AF_GetUserSessionStatus(SSSESSIONID,ClientId,UserId,sessionStatusList);
}else{
	out.println("UF_GetPubInfoByPenName Failed." + R);
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
<h3>UMClient.AF_GetUserSessionStatus</h3>
<h4>Returns:</h4>
<p style="background-color:lightyellow">
ËµÃ÷£º
SessionType = LogonType*2(+1)
+1±íÊ¾±£³ÖµÇÂ¼
</p>
<%
if (lRet == 0){
	out.println("AF_GetUserSessionStatus Successfully.");
	SessionStatus sessionStatus = new SessionStatus();
	for (int i=0;i<sessionStatusList.Size();i++){
		if (sessionStatusList.Get(sessionStatus,i)){

			Date dateLogon = new Date((long)sessionStatus.nLogonTime*1000);
			Date dateAccess = new Date((long)sessionStatus.nAccessTime*1000);
			out.println("<li>SessionType:" + sessionStatus.btSessionType);
			out.println("<br>LogonTime:" + dateLogon.toString());
			out.println("<br>AccessTime:" + dateAccess.toString());
		}
	}

}else{
	out.println("AF_GetUserSessionStatus Failed." + lRet);
}
%>

</BODY>
</HTML>
