<%@ page language="java"%>
<%@ page contentType="text/html; charset=gb2312" %>
<%@ page import="com.stockstar.buos.um.*" %>
<%@ include file="CheckSession.jsp"%>
<%
//setHeader sample
response.setHeader("Cache-Control","no-cache");
response.setHeader("Pragma","no-cache");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META name="GENERATOR" content="IBM WebSphere Studio">
<TITLE>Usermanager Client on JSP Sample</TITLE>
<style>
	body,table,p,td,input,select{font-family:verdana;font-size:9pt;}
</style>
</HEAD>
<BODY>
<h3>UMClient Admin Functions-¹ÜÀíÔ±½Ó¿Ú</h3>
<%


out.println("<li>Penname:" + ckPenName);
out.println("<li>UserId:" + ckUserId);
out.println("<li>UserType:" + ckUserType);
out.println("<li>Level:" + ckLevel);
//out.println("<li>Sex:" + ckSex);
//out.println("<li>Personal Service IP:" + ckPSIP);


int lRet = -1;
LogonTypeList logonTypeList = new LogonTypeList();
lRet = UMClient.AF_GetAllLogonTypeInfo(SSSESSIONID,ClientId,logonTypeList);
if (lRet!=0) out.println("Can not get LogonTypeList:" + lRet);
%>

<h4>»ñµÃÓÃ»§ÐÅÏ¢</h4>
<li><a href="UM_AF_UserInit_FE.jsp">AF_UserInit</a> ¹ÜÀíÔ±×¢²áÒ»¸öÐÂÓÃ»§

<li>AF_ModInfo ¹ÜÀíÔ±ÐÞ¸ÄÈÎÒâÒ»¸öÓÃ»§µÄÐÅÏ¢£¨Ö»ÓÐ±ÊÃû¿ÉÒÔÐÞ¸Ä£©
<blockquote>
	<form name=f1 method=post action="UM_AF_ModInfo.jsp">
		Penname: <input type="text" name="PenName" value="" size=10>
		NewPenName: <input type="text" name="PenNameNew" value="" size=10>
		<input type="submit" value="ÐÞ¸Ä±ÊÃû">
	</form>
</blockquote>

<li>AF_ModUserLevel ¹ÜÀíÔ±ÐÞ¸ÄÓÃ»§µÄ¼¶±ð
<blockquote>
	<form name=f1 method=post action="UM_AF_ModUserLevel.jsp">
		Penname: <input type="text" name="PenName" value="" size=10>
		UserLevel: <input type="text" size=1 name="UserLevel" value=""> (1-100)
		<input type="submit" value="ÐÞ¸ÄLevel">
	</form>
</blockquote>

<li>AF_ResetPasswd ¹ÜÀíÔ±ÖØÖÃÓÃ»§µÄÃÜÂë
<blockquote>
	<form name=f1 method=post action="UM_AF_ResetPasswd.jsp">
		Penname: <input type="text" name="PenName" value="" size=10>
		ResetType: <input type="radio" name="ResetType" value="1">×Ö·ûÃÜÂë <input type="radio" name="ResetType" value="2">Êý×ÖÃÜÂë
		<input type="submit" value="ÖØÖÃÃÜÂë">
	</form>
</blockquote>

<li>AF_SetUserValid ¹ÜÀíÔ±ÉèÖÃÓÃ»§µÄÓÐÐ§ÐÔ
<blockquote>
	<form name=f1 method=post action="UM_AF_SetUserValid.jsp">
		Penname: <input type="text" name="PenName" value="" size=10>
		Valid: <input type="radio" name="Valid" value="1">ÓÐÐ§ <input type="radio" name="Valid" value="0">ÎÞÐ§
		<input type="submit" value="ÉèÖÃÓÐÐ§ÐÔ">
	</form>
</blockquote>


<li>AF_GetUserSessionStatus  ¹ÜÀíÔ±µÃµ½ÓÃ»§µÄ»á»°×´Ì¬
<blockquote>
	<form name=f1 method=post action="UM_AF_GetUserSessionStatus.jsp">
		Penname: <input type="text" name="PenName" value="" size=10>
		<input type="submit" value="²éÑ¯»á»°×´Ì¬">
	</form>
</blockquote>

<li>AF_GetInfo ¹ÜÀíÔ±µÃµ½ÓÃ»§µÄËùÓÐÐÅÏ¢
<blockquote>
	<form name=f1 method=post action="UM_AF_GetInfo.jsp">
		Penname: <input type="text" name="PenName" value="" size=10>
		<input type="submit" value="»ñµÃÓÃ»§ÐÅÏ¢">
	</form>
</blockquote>
<li>AF_GetPubInfo ¹ÜÀíÔ±¸ù¾ÝÓÃ»§µÄµÇÂ¼ÐÅÏ¢µÃµ½ÓÃ»§¹«¿ªÐÅÏ¢
<blockquote>
	<form name=f1 method=post action="UM_AF_GetPubInfo.jsp">

		LogonType(µÇÂ¼ÀàÐÍ):
		<select name="LogonType">
			<%
			if (lRet == 0){
				LogonType logonType = new LogonType();
				for (int i=0; i<logonTypeList.Size(); i++){
					logonTypeList.Get(logonType,i);
					out.println("<option value=\"" + logonType.nLogonType + "\">" + logonType.nLogonType + "-" + logonType.sLogonTypeName + "</option>");
				}
			}
			%>
		</select>
		<br>
		LogonID(¸ÃÀàÐÍµÄµÇÂ¼±êÊ¶): <input type="text" name="LogonId" value=""> (µ±Ñ¡ÔñµÚÒ»ÖÖµÇÂ¼ÀàÐÍÊ±£¬µÇÂ¼ÃûÐèÒªÔö¼Ó¡°µÇÂ¼ÀàÐÍ¡±);
		<input type="submit" value="»ñµÃÓÃ»§¹«¿ªÐÅÏ¢">
	</form>
</blockquote>
<li>AF_GetSecurityQuestion ¹ÜÀíÔ±¸ù¾ÝÓÃ»§µÄµÇÂ¼ÐÅÏ¢µÃµ½ÓÃ»§µÄ°²È«ÎÊÌâ
<blockquote>
	<form name=f1 method=post action="UM_AF_GetSecurityQuestion.jsp">
		UserName: <input type="text" name="UserName" value="" size=10>
		UserType: <input type="text" name="UserType" value="none" size=6>
		<input type="submit" value="»ñµÃ°²È«ÎÊÌâ">
	</form>
</blockquote>
<li>UM_AF_GetUserAuthorization  ¹ÜÀíÔ±È¡µÃÓÃ»§µÄÒ»×éÈ¨ÏÞÊý¾Ý
<blockquote>
	<form name=f1 method=post action="UM_AF_GetUserAuthorization.jsp">
		Penname: <input type="text" name="PenName" value="" size=10>
		<input type="submit" value="È¡µÃÓÃ»§È¨ÏÞ">
	</form>
</blockquote>


<li><a href="UM_AF_GetOnlineNumber.jsp">AF_GetOnlineNumber</a> ¹ÜÀíÔ±µÃµ½ÔÚÏßÓÃ»§Êý

<h4>¹ÜÀíÔ±¹ÜÀí</h4>
<li><a href="UM_AF_GetBasicRight.jsp">AF_GetBasicRight</a> ¹ÜÀíÔ±È¡µÃ×Ô¼ºÔÚÈ¨ÏÞ¶ÔÏóÉÏµÄÈ¨Á¦×´Ì¬
<li><a href="UM_AF_GetBasicOperator.jsp">AF_GetBasicOperator</a> ¹ÜÀíÔ±È¡µÃ¿ÉÒÔ²Ù×÷»ò¹ÜÀíÈ¨ÏÞ¶ÔÏóµÄ¹ÜÀíÔ±ÁÐ±í(²»°üÀ¨×î¸ß¹ÜÀíÔ±)


<h4>ÓÃ»§µÇÂ¼ÀàÐÍ¹Ø±ÕºÍ¿ªÍ¨</h4>

<li><a href="UM_AF_GetAllLogonTypeInfo.jsp">AF_GetAllLogonTypeInfo</a> ¹ÜÀíÔ±»ñµÃËùÓÐµÄµÇÂ¼ÀàÐÍÁÐ±í</li>
<li><a href="UM_AF_GetAllLogonTypeInfo.jsp">AF_DelLogonType</a> ¹ÜÀíÔ±É¾³ýµÇÂ¼ÀàÐÍ</li>
<li><a href="UM_AF_GetAllLogonTypeInfo.jsp">AF_AddLogonType</a> ¹ÜÀíÔ±Ôö¼ÓµÇÂ¼ÀàÐÍ</li>

<li>AF_OpenLogonType ¿ªÍ¨Ä³¸öÓÃ»§µÄµÇÂ¼ÀàÐÍ
<blockquote>
	<form name=f1 method=post action="UM_AF_OpenLogonType.jsp">
		Penname: <input type="text" name="PenName" value="">
		<!--UserId(18Îª±àºÅ): <input type="text" name="UserId" value="">-->
		<br>
		LogonType(µÇÂ¼ÀàÐÍµÄ±àºÅ):
		<select name="LogonType">
			<%
			if (lRet == 0){
				LogonType logonType = new LogonType();
				for (int i=0; i<logonTypeList.Size(); i++){
					logonTypeList.Get(logonType,i);
					out.println("<option value=\"" + logonType.nLogonType + "\">" + logonType.sLogonTypeName + "</option>");
				}
			}
			%>
		</select>
		<br>
		LogonTypeName(¸ÃµÇÂ¼ÀàÐÍµÄµÇÂ¼Ãû): <input type="text" name="LogonID" value=""><br>
		<input type="submit" value="OpenLogonType">
	</form>
</blockquote>
<li>AF_CloseLogonType ¹Ø±ÕÄ³¸öÓÃ»§µÄµÇÂ¼ÀàÐÍ
<blockquote>
	<form name=f1 method=post action="UM_AF_CloseLogonType.jsp">
		Penname: <input type="text" name="PenName" value="">
		<!--UserId(18Îª±àºÅ): <input type="text" name="UserId" value="">-->
		<br>
		LogonType(µÇÂ¼ÀàÐÍµÄ±àºÅ):
		<select name="LogonType">
			<%
			if (lRet == 0){
				LogonType logonType = new LogonType();
				for (int i=0; i<logonTypeList.Size(); i++){
					logonTypeList.Get(logonType,i);
					out.println("<option value=\"" + logonType.nLogonType + "\">" + logonType.nLogonType + "-" + logonType.sLogonTypeName + "</option>");
				}
			}
			%>
		</select>

		<br>
		LogonTypeName(¸ÃµÇÂ¼ÀàÐÍµÄµÇÂ¼Ãû): <input type="text" name="LogonID" value=""><br>
		<input type="submit" value="CloseLogonType">
	</form>
</blockquote>

<h4>È¨ÏÞ£¬½ÇÉ«ºÍ×é¹ÜÀí</h4>
<li><a href="UM_AF_GetAllAuthorityObject.jsp">AF_GetAllAuthorityObject</a> ¹ÜÀíÔ±È¡µÃËùÓÐµÄÈ¨ÏÞÁÐ±í</li>
<li><a href="UM_AF_GetAllAuthorityObject.jsp">AF_DelAuthorityObject</a> ¹ÜÀíÔ±É¾³ýÈ¨ÏÞ¶ÔÏó</li>
<li><a href="UM_AF_GetAllAuthorityObject.jsp">AF_AddAuthorityObject</a> ¹ÜÀíÔ±Ìí¼ÓÈ¨ÏÞ¶ÔÏó</li>

<br><br>
<li><a href="UM_AF_GetAllRole.jsp">AF_GetAllRole</a> ¹ÜÀíÔ±È¡µÃËùÓÐµÄ½ÇÉ«ÁÐ±í</li>
<li><a href="UM_AF_GetAllRole.jsp">AF_DelRole</a> ¹ÜÀíÔ±É¾³ý½ÇÉ«¶ÔÏó</li>
<li><a href="UM_AF_GetAllRole.jsp">AF_AddRole</a> ¹ÜÀíÔ±Ìí¼Ó½ÇÉ«¶ÔÏó</li>

<br><br>
<li><a href="UM_AF_GetAllGroup.jsp">AF_GetAllGroup</a> ¹ÜÀíÔ±È¡µÃËùÓÐµÄ½ÇÉ«ÁÐ±í</li>
<li><a href="UM_AF_GetAllGroup.jsp">AF_DelGroup</a> ¹ÜÀíÔ±É¾³ý½ÇÉ«¶ÔÏó</li>
<li><a href="UM_AF_GetAllGroup.jsp">AF_AddGroup</a> ¹ÜÀíÔ±Ìí¼Ó½ÇÉ«¶ÔÏó</li>

<h4><a href="default.jsp">·µ»ØÆÕÍ¨½Ó¿ÚÒ³Ãæ</a></h4>
</BODY>
</HTML>
