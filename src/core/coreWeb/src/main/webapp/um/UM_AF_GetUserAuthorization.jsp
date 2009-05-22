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
StringObj strRootObjIdOut = new StringObj();
AuthorizationDataList authorizationDataList = new AuthorizationDataList();

String PenName = request.getParameter("PenName");
out.println(PenName);
UserPubInfo userPubInfo = new UserPubInfo();
R = UMClient.UF_GetPubInfoByPenName(PenName,userPubInfo);
if (R == 0){
	out.println("UF_GetPubInfoByPenName Successfully.");
	String UserId = userPubInfo.sUserId;
	lRet = UMClient.AF_GetUserAuthorization(SSSESSIONID,ClientId,UserId,strRootObjIdOut,authorizationDataList);
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
<h3>UMClient.AF_GetUserAuthorization</h3>
<h4>Returns:</h4>
<%
if (lRet == 0){
	out.println("AF_GetUserAuthorization Successfully.");
	out.println("Total Size:" + authorizationDataList.Size());
	out.println("<table><tr bgcolor=silver>");
	out.println("<td>ObjectId</td>");
	out.println("<td>SubObjNum</td>");
	out.println("<td>TotalObjNum</td>");
	out.println("<td>Authorization</td>");
	out.println("</tr>");

	String Auth = "";
	int ilen=0;
	AuthorizationData s = new AuthorizationData();
	for (int i=0;i<authorizationDataList.Size();i++){
		if (authorizationDataList.Get(s,i)){
			Auth = (new Integer(s.nAuthorization)).toBinaryString(s.nAuthorization);
			ilen = 32-Auth.length();
			for(int n=0;n<ilen;n++){
				Auth = "0" + Auth;
				}

			out.println("<tr><td>" + s.nObjectId + "</td><td>" +  s.nSubObjNum  + "</td><td>" + s.nTotalObjNum + "</td><td>" + Auth + "(" + s.nAuthorization + ")</td></tr>");
		}
	}
	out.println("</table>");
}else{
	out.println("AF_GetUserAuthorization Failed." + lRet);
}
%>


<p style="background-color:lightyellow">
授权信息列表 组织示意图
<pre>
        m: 根节点的父节点的权限对象ID字符串（例如："2.1.3"）
        n: 根节点的权限对象ID（整数）
       ObjectId   Authorization   SubObjNum   TotalObjNum
      +---------+---------------+-----------+--------------+
  0   |   n     |     auth      |   2       |       14     | 根权限对象 m.n
      +---------+---------------+-----------+--------------+
  1   |   1     |     auth      |   3       |        8     |   权限对象 m.n.1
      +---------+---------------+-----------+--------------+
  2   |   1     |     auth      |   2       |        3     |   权限对象 m.n.1.1
      +---------+---------------+-----------+--------------+
  3   |   1     |     auth      |   0       |        1     |   权限对象 m.n.1.1.1
      +---------+---------------+-----------+--------------+
  4   |   2     |     auth      |   0       |        1     |   权限对象 m.n.1.1.2
      +---------+---------------+-----------+--------------+
  5   |   2     |     auth      |   0       |        1     |   权限对象 m.n.1.2
      +---------+---------------+-----------+--------------+
  6   |   3     |     auth      |   2       |        3     |   权限对象 m.n.1.3
      +---------+---------------+-----------+--------------+
  7   |   1     |     auth      |   0       |        1     |   权限对象 m.n.1.3.1
      +---------+---------------+-----------+--------------+
  8   |   2     |     auth      |   0       |        1     |   权限对象 m.n.1.3.2
      +---------+---------------+-----------+--------------+
  9   |   2     |     auth      |   2       |        5     |   权限对象 m.n.2
      +---------+---------------+-----------+--------------+
  10  |   1     |     auth      |   0       |        1     |   权限对象 m.n.2.1
      +---------+---------------+-----------+--------------+
  11  |   2     |     auth      |   2       |        3     |   权限对象 m.n.2.2
      +---------+---------------+-----------+--------------+
  12  |   1     |     auth      |   0       |        1     |   权限对象 m.n.2.2.1
      +---------+---------------+-----------+--------------+
  13  |   2     |     auth      |   0       |        1     |   权限对象 m.n.2.2.2
      +---------+---------------+-----------+--------------+
 </pre>
</p>

</BODY>
</HTML>
