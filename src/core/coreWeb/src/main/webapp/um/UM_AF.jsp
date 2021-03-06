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
<h3>UMClient Admin Functions-管理员接口</h3>
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

<h4>获得用户信息</h4>
<li><a href="UM_AF_UserInit_FE.jsp">AF_UserInit</a> 管理员注册一个新用户

<li>AF_ModInfo 管理员修改任意一个用户的信息（只有笔名可以修改）
<blockquote>
	<form name=f1 method=post action="UM_AF_ModInfo.jsp">
		Penname: <input type="text" name="PenName" value="" size=10>
		NewPenName: <input type="text" name="PenNameNew" value="" size=10>
		<input type="submit" value="修改笔名">
	</form>
</blockquote>

<li>AF_ModUserLevel 管理员修改用户的级别
<blockquote>
	<form name=f1 method=post action="UM_AF_ModUserLevel.jsp">
		Penname: <input type="text" name="PenName" value="" size=10>
		UserLevel: <input type="text" size=1 name="UserLevel" value=""> (1-100)
		<input type="submit" value="修改Level">
	</form>
</blockquote>

<li>AF_ResetPasswd 管理员重置用户的密码
<blockquote>
	<form name=f1 method=post action="UM_AF_ResetPasswd.jsp">
		Penname: <input type="text" name="PenName" value="" size=10>
		ResetType: <input type="radio" name="ResetType" value="1">字符密码 <input type="radio" name="ResetType" value="2">数字密码
		<input type="submit" value="重置密码">
	</form>
</blockquote>

<li>AF_SetUserValid 管理员设置用户的有效性
<blockquote>
	<form name=f1 method=post action="UM_AF_SetUserValid.jsp">
		Penname: <input type="text" name="PenName" value="" size=10>
		Valid: <input type="radio" name="Valid" value="1">有效 <input type="radio" name="Valid" value="0">无效
		<input type="submit" value="设置有效性">
	</form>
</blockquote>


<li>AF_GetUserSessionStatus  管理员得到用户的会话状态
<blockquote>
	<form name=f1 method=post action="UM_AF_GetUserSessionStatus.jsp">
		Penname: <input type="text" name="PenName" value="" size=10>
		<input type="submit" value="查询会话状态">
	</form>
</blockquote>

<li>AF_GetInfo 管理员得到用户的所有信息
<blockquote>
	<form name=f1 method=post action="UM_AF_GetInfo.jsp">
		Penname: <input type="text" name="PenName" value="" size=10>
		<input type="submit" value="获得用户信息">
	</form>
</blockquote>
<li>AF_GetPubInfo 管理员根据用户的登录信息得到用户公开信息
<blockquote>
	<form name=f1 method=post action="UM_AF_GetPubInfo.jsp">

		LogonType(登录类型):
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
		LogonID(该类型的登录标识): <input type="text" name="LogonId" value=""> (当选择第一种登录类型时，登录名需要增加“登录类型”);
		<input type="submit" value="获得用户公开信息">
	</form>
</blockquote>
<li>AF_GetSecurityQuestion 管理员根据用户的登录信息得到用户的安全问题
<blockquote>
	<form name=f1 method=post action="UM_AF_GetSecurityQuestion.jsp">
		UserName: <input type="text" name="UserName" value="" size=10>
		UserType: <input type="text" name="UserType" value="none" size=6>
		<input type="submit" value="获得安全问题">
	</form>
</blockquote>
<li>UM_AF_GetUserAuthorization  管理员取得用户的一组权限数据
<blockquote>
	<form name=f1 method=post action="UM_AF_GetUserAuthorization.jsp">
		Penname: <input type="text" name="PenName" value="" size=10>
		<input type="submit" value="取得用户权限">
	</form>
</blockquote>


<li><a href="UM_AF_GetOnlineNumber.jsp">AF_GetOnlineNumber</a> 管理员得到在线用户数

<h4>管理员管理</h4>
<li><a href="UM_AF_GetBasicRight.jsp">AF_GetBasicRight</a> 管理员取得自己在权限对象上的权力状态
<li><a href="UM_AF_GetBasicOperator.jsp">AF_GetBasicOperator</a> 管理员取得可以操作或管理权限对象的管理员列表(不包括最高管理员)


<h4>用户登录类型关闭和开通</h4>

<li><a href="UM_AF_GetAllLogonTypeInfo.jsp">AF_GetAllLogonTypeInfo</a> 管理员获得所有的登录类型列表</li>
<li><a href="UM_AF_GetAllLogonTypeInfo.jsp">AF_DelLogonType</a> 管理员删除登录类型</li>
<li><a href="UM_AF_GetAllLogonTypeInfo.jsp">AF_AddLogonType</a> 管理员增加登录类型</li>

<li>AF_OpenLogonType 开通某个用户的登录类型
<blockquote>
	<form name=f1 method=post action="UM_AF_OpenLogonType.jsp">
		Penname: <input type="text" name="PenName" value="">
		<!--UserId(18为编号): <input type="text" name="UserId" value="">-->
		<br>
		LogonType(登录类型的编号):
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
		LogonTypeName(该登录类型的登录名): <input type="text" name="LogonID" value=""><br>
		<input type="submit" value="OpenLogonType">
	</form>
</blockquote>
<li>AF_CloseLogonType 关闭某个用户的登录类型
<blockquote>
	<form name=f1 method=post action="UM_AF_CloseLogonType.jsp">
		Penname: <input type="text" name="PenName" value="">
		<!--UserId(18为编号): <input type="text" name="UserId" value="">-->
		<br>
		LogonType(登录类型的编号):
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
		LogonTypeName(该登录类型的登录名): <input type="text" name="LogonID" value=""><br>
		<input type="submit" value="CloseLogonType">
	</form>
</blockquote>

<h4>权限，角色和组管理</h4>
<li><a href="UM_AF_GetAllAuthorityObject.jsp">AF_GetAllAuthorityObject</a> 管理员取得所有的权限列表</li>
<li><a href="UM_AF_GetAllAuthorityObject.jsp">AF_DelAuthorityObject</a> 管理员删除权限对象</li>
<li><a href="UM_AF_GetAllAuthorityObject.jsp">AF_AddAuthorityObject</a> 管理员添加权限对象</li>

<br><br>
<li><a href="UM_AF_GetAllRole.jsp">AF_GetAllRole</a> 管理员取得所有的角色列表</li>
<li><a href="UM_AF_GetAllRole.jsp">AF_DelRole</a> 管理员删除角色对象</li>
<li><a href="UM_AF_GetAllRole.jsp">AF_AddRole</a> 管理员添加角色对象</li>

<br><br>
<li><a href="UM_AF_GetAllGroup.jsp">AF_GetAllGroup</a> 管理员取得所有的角色列表</li>
<li><a href="UM_AF_GetAllGroup.jsp">AF_DelGroup</a> 管理员删除角色对象</li>
<li><a href="UM_AF_GetAllGroup.jsp">AF_AddGroup</a> 管理员添加角色对象</li>

<h4><a href="default.jsp">返回普通接口页面</a></h4>
</BODY>
</HTML>
