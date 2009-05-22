<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ page import="java.sql.Statement"%>
<%@ page import="com.ngus.dataengine.DBConnection"%>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.ResultSet" %>
<%@ include file="checkSession.jsp" %>
<%
	response.setHeader("Cache-Control","no-cache");
	response.setHeader("Pragma","no-cache");

	String userName= (String)session.getAttribute("username");
	UMSession ums= (UMSession)session.getAttribute("ums");
%>	

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
							<div class="header-top-description">必要的介绍。。。。。。。。。。。。。。。。。。。。。</div>
							<div class="header-top-link"><a href="#">设为首页</a> | <a href="#">下载XXXX插件</a></div>
						</div>
						<div class="header-main">
							<div class="header-logo"><a href="#"><img src="img/logo.gif" alt="MyWeb Logo" /></a></div>
							<div class="header-search">
								<form method="post" action="search">
								<div class="header-search-type"><input type="radio" name="radSearchType" checked="checked" class="radio" value="fulltext"/>全文 | <input type="radio" name="radSearchType" class="radio" value="tag"/>链接 | <input type="radio" name="radSearchType" class="radio" value="user"/>好友</div>								
								<div class="header-search-field"><input type="text" name="key" class="header-search-text"/><input type="submit" value=" 搜 索 " class="botton" /></div>
								</form>
								<div class="header-search-hint">请输入关键字，例如“Adways”，“XX”，“XX”，“XX”等。</div>
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
								<div class="menu-tab-selected">链 接</div>
								<div class="menu-tab-unselected"><a href="#">其 他</a></div>
								<div class="menu-tab-unselected"><a href="#">帮 助</a></div>
							</div>
							<div class="panel_content">
							<div class="inner-content">
							<!--xxx panel start####################################################-->
							<div class="content-panel">
								<div class="content-panel-title">某个操作面板</div>
								<div class="content-panel-content">
									<div style="height: 370px">
									<div style="float: left">
										
									</div>
									
									<div id="bk_display" style="float: left; border: 1px solid gray; width: 720px; height: 370px; overflow:auto">
										<br/><br/><br/><br/><br/>
										<div align="center">
											<div>用户名：<%=userName %></div>
											<div>笔名：<%=ums.getUser().getNickName() %></div>
											<div>性别：<%if(ums.getUser().getSex()==0){ %>男<%}else{ %> 女<%} %></div>
											<div>电子邮箱：<%=ums.getUser().getEmail() %></div>
											<div>手机号码：<%=ums.getUser().getMobile() %></div>
											<div>最近登入时间：<%=ums.getUser().getLastLogonTime() %></div>
											
										</div>
										
										
									</div>
									</div>
																		
								</div>
							</div>
							<!--xxx panel start####################################################-->
						</div>
					</div>
					<!--content end####################################################-->
					<!--sidebar start####################################################-->
					<div class="sidebar">
						<div class="inner-sidebar">
							<!--user panel start####################################################-->
							
							<%@ include file="login.jsp" %>
							<!--user panel start####################################################-->
							<!--xxx panel start####################################################-->
							<div class="sidebar-panel">
								<div class="sidebar-panel-title">某个操作面板</div>
								<div class="sidebar-panel-content">
									<ul>
										<li>sdfrfergtrhtyfhf</li>
										<li>sdfrfergtrhtyfhf</li>
										<li>sdfrfergtrhtyfhf</li>
										<li>sdfrfergtrhtyfhf</li>
										<li>sdfrfergtrhtyfhf</li>
										<li>sdfrfergtrhtyfhf</li>
										<li>sdfrfergtrhtyfhf</li>
										<li>sdfrfergtrhtyfhf</li>
										<li>sdfrfergtrhtyfhf</li>
										<li>sdfrfergtrhtyfhf</li>
									</ul>
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



