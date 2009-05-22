<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ page import="com.ngus.myweb.userextension.UserExtensionService"%>
<%@ page import="java.util.List"%>
<%@ page import="com.ngus.um.http.*" %>
<%@ page import="com.ngus.um.*" %>
<%@ page import="com.ns.dataobject.Attribute"%>
<%@ page import="java.util.*"%>
<%@ page import="com.ngus.myweb.services.MyWebResService" %>
<%@ page import="com.ngus.myweb.friend.*"%>
<%@ page import="com.ngus.myweb.util.ParamUtils" %>
<%
	int error_level = ParamUtils.getIntParameter(request,"log_error_level",-1);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">


<html xmlns="http://www.w3.org/1999/xhtml">

	<head>
		<meta http-equiv="content-type" content="text/html;charset=utf-8" />
		<meta name="generator" content="Adobe GoLive" />
		<title>WebOS-用户登陆</title>
		<link href="css/style.css" rel="stylesheet" type="text/css" media="all" />
	</head>

	<body>
		<% 
			String name = (String) request.getAttribute("username"); 
			
			if(name == null){
				List<IUser> newuser = UMClient.getNewUser(10);
		%>
			<div id="right_side">
					<!--start of the main content of the right side-->
					
					<!--block no.1|starts|-->
					<div class="sidebar-panel">
						<div class="sidebar-panel-title"><img src="images/star.gif" />&nbsp;用户登陆</div>
						<div class="register_box">
							<html:form action="/webos_login.do" method="POST">
								<table>
									<tbody>
										<tr>
											<td class="register_td_name"> 用户名:</td>
											<td>
												<html:text property="userName" size="18"/>
												<% if(error_level==0){ %><div><span class="font01">用户名不能为空！</span></div><%} %><% if(error_level==3){ %><div><span class="font01">用户不存在!</span></div><%} %>
									
											</td>
										</tr>
										<tr>
				
											<td class="register_td_name">密码:</td>
											<td>
												<html:password property="password" size="20"/><% if(error_level==1){ %><div><span class="font01">用户名密码错误！</span></div><%} %>
									
											</td>
										</tr>
										<tr>
											<td class="register_td_name">验证码:</td>
											<td>
												<html:text property="vaildCode" size="4"/><img width=60  height=20 src="ValidateCode" /><% if(error_level==2){ %><div><span class="font01">验证码不匹配！</span></div><%} %>
									
											</td>
										</tr>
									</tbody>
								</table>
								<div align="center" >
									<html:submit>登入</html:submit>&nbsp;&nbsp;<a href="monweb_forgetPassword.jsp"><html:button property="buttonName" >忘记密码</html:button></a>
								</div>
							</html:form>
							<hr />
							<div align="center"><a href="monweb_reg.jsp">注册monweb新用户</a></div>
						</div>						
					</div>
					<!--block no.1|ends|-->
					<!--end of the main content of the right side-->
				</div>
				<%} else{
					Attribute count1 = new Attribute("name", new Integer(0));
								
					Iterator iter1 = FriendService.listFriend(UserManager.getCurrentUser(), 0, 8, count1);
								
					List<String> al = MyWebResService.instance().mostPopularTag(14);
								
					request.setAttribute("userList", iter1);
								
					request.setAttribute("keyList",al);
				%>
						<div id="right_side">
					<!--start of the main content of the right side-->
				
					<!--user information starts-->
					<div class="sidebar-panel">
						<div class="sidebar-panel-title"></div>
						<div class="sidebar-panel-content">
							<div>
								<div class="user_id_pic"><img width=100  height=100 src="userPic?userName=<%=name%>" /></div>
								<div class="user_id_content">
									<div><strong><%=name %></strong></div>
									<a href="monweb_personalResource.jsp">查看修改个人信息</a><br />
									<a href="monweb_changePassword.jsp">修改密码</a><br />
									<a href="monweb_message.jsp">我的信箱</a><br />
									<a href="logout.jsp">[退出]</a>
								</div>
								<div class="clear"></div>
							</div>	
						</div>						
					</div>
					<!--user information ends-->
					<!--end of the main content of the right side-->
				</div>
<% } %>
    </body>

</html>