<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ page import="com.ngus.myweb.services.MyWebResService"%>
<%@ page import="com.ngus.myweb.friend.*"%>
<%@ page import="com.ngus.um.http.*" %>
<%@ page import="com.ngus.um.*" %>
<%@ page import="com.ngus.myweb.dataobject.*"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="com.ngus.um.dbobject.*,com.ngus.um.*" %>
<%@ include file="checkSession.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<link rel="stylesheet" type="text/css" title="default" href="css/lite_blue.css" />
		<link href="css/css.css" rel="stylesheet" type="text/css"/>
		<title>MyWeb - 首页</title>
	</head>
	<body>
		<div class="wrapper">
			<div class="inner-wrapper">
				<!--header start####################################################-->
				<div class="header">
					<div class="inner-header">
						<div class="header-top">
							<div class="header-top-description"></div>
							<div class="header-top-link"><a href="#">设为首页</a> | <a href="#">下载</a></div>
						</div>
						<div class="header-main">
							<div class="header-logo"><a href="#"><img src="img/logo.gif" alt="MyWeb Logo" /></a></div>
							<div class="header-search">
								<form method="post" accept-charset="utf-8" action="search">
								<div class="header-search-type"><input type="radio" name="radSearchType" checked="checked" class="radio" value="fulltext"/>全文  <input type="radio" name="radSearchType" class="radio" value="tag"/>Tag  <input type="radio" name="radSearchType" class="radio" value="user"/>好友</div>								
								<div class="header-search-field"><input type="text" name="key" class="header-search-text"/><input type="submit" value=" 搜 索 " class="botton" /></div>
								</form>
								<div class="header-search-hint">
								热门搜索 
								<a href=# >股票</a>
								<a href=# >NBA</a>
								<a href=# >美女</a>
								<a href=# >电影</a>
								<a href=# >视频</a>
								<a href=# >奥运</a>
								<a href=# >情人节</a>	
								</div>
							</div>
						</div>
					</div>
				</div>
				<!--header end####################################################-->
				<!--main-wrapper start####################################################-->
				<div class="main-wrapper">
					<!--content start####################################################-->
					<div class="menu">
								<div class="menu-tab-unselected"><a href="#">首 页</a></div>
								<div class="menu-tab-selected">我的资源</div>
								<div class="menu-tab-unselected"><a href="#">下 载</a></div>
								<div class="menu-tab-unselected"><a href="#">帮 助</a></div>
					</div>
					<div class="panel_content">
						<div class="inner-content" >							
							<!--xxx panel start####################################################-->
							<div class="content-panel">
								<div class="content-panel-title">我的资源</div>
								<div class="content-panel-content">
									<div style="height: 310px">
										<form action="addFriend.do" method = "post">
											<input name="userId" type="hidden" value="<%=UserManager.getCurrentUser().getUserId()%>"/>											
											用户名: <input name="friendName" type="text" />											
											<input type="submit" value="提交"/>
										</form>									
									</div>									
								</div>
							</div>
							<!--xxx panel start####################################################-->
						</div> <!-- inner content -->
					</div><!-- panel content -->
					<!--content end####################################################-->
					<!--sidebar start####################################################-->
					<div class="sidebar">
						<div class="inner-sidebar">
							<!--user panel start####################################################-->
							<%-- <div class="sidebar-panel">
								<div class="sidebar-panel-title">登录</div>
								<div class="sidebar-panel-content">
									<div class="user-login">
										<div class="user-login-input"><div>用户名</div> <input type="text" /></div>
										<div class="user-login-input"><div>密码</div> <input type="password" /> <a href="#">忘记密码</a></div>
										<div class="user-login-submit"><a href="reg.jsp">立即注册！</a>&nbsp;&nbsp;&nbsp;<input type="submit" value=" 登 录 " />
										</div>
									</div>
								</div>
							</div>--%>
							
							<%@ include file="login.jsp" %>
							
							<!--user panel start####################################################-->
							<!--xxx panel start####################################################-->
							<div class="sidebar-panel">
								<div class="sidebar-panel-title">某个操作面板</div>
								<div class="sidebar-panel-content">
									<ul>
										<logic:present name="friendList">
											<logic:iterate id="user" name="friendList">
												<li><img src="img/user.jpg" /><bean:write name="user" property="userName"/></li>
											</logic:iterate>	
										</logic:present>										
									</ul>
									<div><a href="#">添加</a><a href="friend.jsp">更多</a></div>
								</div>
							</div>
							<!--xxx panel start####################################################-->
						</div>
					</div>
					<!--sidebar start####################################################-->
					<div class="clear"></div>
				</div>
				<!--main-wrapper end####################################################-->
				<!--footer start####################################################-->
				<div class="footer">
					<div class="inner-footer">
						<div class="footer-help"><a href="http://validator.w3.org/check/referer"><img src="img/w3c_xhtml.png" alt="通过W3C XHTML1.1校验" /></a> <a href="http://jigsaw.w3.org/css-validator/check/referer"><img src="img/w3c_css.png" alt="通过W3C CSS校验" /></a> <a href="http://www.w3.org/WAI/WCAG1AAA-Conformance"><img src="img/w3c_access.png" alt="符合W3C可用性AAA级标准" /></a><br />
						&copy; 2006 <a href="#">MyWeb</a> 版权所有 保留所有权利 | <a href="http://www.miibeian.gov.cn">沪ICP备06020881号</a><br />
						<a href="#">使用指南</a> | <a href="#">隐私条款</a> | <a href="#">关于我们</a> | <a href="#">法律条款</a> | <a href="#">联系我们</a> | <a href="#">常见问题</a></div>
					</div>
				</div>
				<!--footer end####################################################-->
			</div>
		</div>
	</body>
</html>
