<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%> 
<%@ page import="java.util.List"%>
<%@ page import="com.ngus.myweb.util.ParamUtils" %>
<%@ page import="com.ngus.myweb.dataobject.MyWebRes"%>
<%@ page import="com.ngus.myweb.services.MyWebResService"%>
<%@ page import="com.ngus.um.dbobject.*,com.ngus.um.*" %>
<%
	int error_level = ParamUtils.getIntParameter(request,"log_error_level",-1);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">


<html xmlns="http://www.w3.org/1999/xhtml">

	<head>
		<meta http-equiv="content-type" content="text/html;charset=utf-8" />
		<meta name="generator" content="Adobe GoLive" />
		<title>monweb.com-用户登陆</title>
		<link href="css/style.css" rel="stylesheet" type="text/css" media="all" />
	</head>

	<body>
	<%
		List<IUser> arr = UMClient.getNewUser(8);
		List<MyWebRes> al = MyWebResService.instance().listMostRecentRes(9);
		request.setAttribute("newUser",arr);
		request.setAttribute("newRes",al);
	%>
		<div id="mainwrap">
			<div>
			<!--start of the body-->
			<%@ include file="header.jsp" %>
			
			
			<br />
			<div id="content">
				<!--start of the main content-->
				<div id="main">
					<div id="inner_l_main">
					<!--start of the main of left area-->
					<div>
						<table cellspacing="2px;">
							<tr>
								<td class="introduction">
									<div class="intro_title"><img src="images/glass.gif" />&nbsp;发现</div>
									<div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;什么当前最流行,最有意思的事是什么? 什么”菜”最合你胃口,快搜索一下吧!</div>
								</td>
								<td class="introduction">
									<div class="intro_title"><img src="images/hand.gif" />&nbsp;收集</div>
									<div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;汇集了各种有趣的，吸引人的各种信息和资源，随心所欲的搜藏,不论何时何地。</div>
								</td>
								<td class="introduction">
									<div class="intro_title"><img src="images/share.gif" />&nbsp;分享</div>
									<div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;一个快捷途径去组织与发布您的搜藏,让您的家人,您的朋友,乃至全球的互联网用户都能分享您的快乐。</div>
								</td>
							</tr>
						</table>
					</div>
					<br />
					<div class="help_instruction">
						<a href="#">如何在线分享我喜欢的网络收藏？</a><br />
						<a href="#">如何搜索其他人的收藏?</a><br />
						<a href="#">如何使用monweb的客户端插件?</a><br />
						<div><a href="#">更多>></a></div>
					</div>
					<!--end of the main of left area-->
					</div>
				</div>
			
				<div id="right_side">
					<!--start of the main content of the right side-->
					
					<!--block no.1|starts|-->
					<div class="sidebar-panel">
						<div class="sidebar-panel-title"><img src="images/star.gif" />&nbsp;用户登陆</div>
						<div class="register_box">
							<html:form action="/login_in" method="POST">
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
									<html:submit>登入</html:submit>&nbsp;&nbsp;<a href="monweb_forgetPassword.jsp"><html:button onclick="window.location='monweb_forgetPassword.jsp'" property="buttonName" >忘记密码</html:button></a>
								</div>
							</html:form>
							<hr />
							<div align="center"><a href="monweb_reg.jsp">注册monweb新用户</a></div>
						</div>						
					</div>
					<!--block no.1|ends|-->
			
				<!--end of the main content of the right side-->
				</div>
				<!--end of the main content-->
			</div>
			
			<div class="clear">&nbsp;</div>
<%@ include file="footer.jsp" %>
			<!--end of the body-->
			</div>
		</div>
	</body>

</html>