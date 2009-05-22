<%@ page import="com.stockstar.buos.um.*" %>

<%
int lCheckRet = -1;

String SSSESSIONID = "";
String ckPenName = "";
String ckUserId = "";
String ckUserType = "";
byte ckLevel = 0;
byte ckSex = 0;
String ckPSIP = "";

String ClientId = request.getRemoteAddr();
UserPubInfo ckUserPubInfo = new UserPubInfo();

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

	lCheckRet = UMClient.UF_CheckSession(SSSESSIONID,ClientId,0,"AppLog","USER.CHECKSESSION",ckUserPubInfo);

}else{
	//out.println("<font color=red>NO SESSIONID FOUND, CHECK SESSION CANCELLED.</font>");
}


if (lCheckRet == 0){
	//out.println("Check Session Successfully.");
	ckPenName = ckUserPubInfo.sPenName;
	ckUserId = ckUserPubInfo.sUserId;
	ckUserType = ckUserPubInfo.sUserType;
	ckLevel = ckUserPubInfo.btLevel;
	ckSex = ckUserPubInfo.btSex;
	ckPSIP = ckUserPubInfo.sPersonalServiceIP;
}else{
	out.println("Check Session Failed." + lCheckRet);
}

%>