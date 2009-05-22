<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ page import="com.ngus.myweb.util.*"%>
<%@ page import="com.ngus.myweb.dataobject.*"%>
<%@ page import="com.ngus.myweb.friend.*"%>
<%@ page import="com.ngus.um.http.*"%>
<%@ page import="com.ngus.myweb.util.*"%>
<%@ page import="java.util.*"%>
<%@ page import="com.ns.dataobject.Attribute"%>
<%@ page import="java.net.*"%>
<%
	int number = ParamUtils.getIntParameter(request, "number", 10);
	int pg = ParamUtils.getIntParameter(request, "page", 1);
	Attribute count = new Attribute("name", new Integer(0));
	int total_pages = 0;
	Iterator iter = FriendService.listFriend(UserManager.getCurrentUser(), (pg-1)*number, number, count);
	total_pages = (int) Math.ceil(Double
			.parseDouble(count.getStringValue())
			/ number);
	request.setAttribute("userList", iter);
	request.setAttribute("page", page);
	request.setAttribute("total_pages", total_pages);
	request.setAttribute("count", count.getValue());
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" title="default"
	href="css/lite_blue.css" media="screen" />
<script type="text/javascript" src="javascript/page.js"></script>
<script type="text/javascript" src="javascript/prototype.js"></script>
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
								<div class="header-search-field"><input type="text" name="key" class="header-search-text" value="<bean:write name="key" />"/><input type="submit" value=" 搜 索 " class="botton" /></div>
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
					<div class="panel_content">
						<div class="inner-content">
							<div class="menu">
								<div class="menu-tab-unselected"><a href="main.jsp">首 页</a></div>
								<div class="menu-tab-selected">链 接</div>
								<div class="menu-tab-unselected"><a href="#">其 他</a></div>
								<div class="menu-tab-unselected"><a href="#">帮 助</a></div>
							</div>
							<div class="page">
								<div class="page-previous" onmouseover="changeClass(this, 'page-previous-hover');" onmouseout="changeClass(this, 'page-previous');">
								<% 
									int current_page = ParamUtils.getIntAttribute(request, "page", 0);
									total_pages = ParamUtils.getIntAttribute(request, "total_pages", 0);
									String key = ParamUtils.getAttribute(request, "key");
									if(current_page==1){
										out.print("上一页");
									}
									else{
										out.print("<a href=\"search?key="+key+"&page="+(current_page-1)+"\">上一页</a>");
									}
								%>
								</div>
								<%
									
									for(int i=1; i<6&&i<=total_pages; i++){
										out.print("<div class=\"page-selected\" onmouseover=\"changeClass(this, 'page-selected-hover');\" onmouseout=\"changeClass(this, 'page-selected');\">");
										if(i%5==current_page%5){
											out.print(i);
										}
										else{
											out.print("<a href=\"search?key="+key+"&page="+i+"\">"+i+"</a>");
										}
										out.print("</div>");
									}									
								%>							
								<div class="page-next" onmouseover="changeClass(this, 'page-next-hover');" onmouseout="changeClass(this, 'page-next');">
								<%
									if(current_page==total_pages){
										out.print("下一页");
									}
									else{
										out.print("<a href=\"search?key="+key+"&page="+(current_page+1)+"\">下一页</a>");
									}
								%>								
								</div>
							</div>
							<!--xxx panel start####################################################-->
							<div class="content-panel">
								<div class="content-panel-title">“ <span class="red"><bean:write name="key" /></span> ”搜索结果 （共找到<span class="red"><bean:write name="count"/></span>个结果）</div>
								<div class="content-panel-content">
									<ul>
										<logic:iterate id="user" name="userList">
										<li>
											<div class="search-result">
												<div class="search-result-title"><bean:write name="user" property="userName" /></div>
												<div class="search-result-url"><bean:write name="user" property="email" /></div>
												<div class="search-result-description"><bean:write name="user" property="lastLogonTime" /></div>	
											</div>
										</li>
										</logic:iterate>							
									</ul>
								</div>
							</div>
							<!--xxx panel start####################################################-->
							<div class="page">
								<div class="page-previous" onmouseover="changeClass(this, 'page-previous-hover');" onmouseout="changeClass(this, 'page-previous');">上一页</div>
								<%
									//int current_page = ParamUtils.getIntAttribute(request, "page", 0);
									for(int i=1; i<6&&i<=total_pages; i++){
										out.print("<div class=\"page-selected\" onmouseover=\"changeClass(this, 'page-selected-hover');\" onmouseout=\"changeClass(this, 'page-selected');\">");
										if(i%5==current_page%5){
											out.print(i);
										}
										else{
											out.print("<a href=\"search?key="+key+"&page="+i+"\">"+i+"</a>");
										}
										out.print("</div>");
									}
								%>							
								<div class="page-next" onmouseover="changeClass(this, 'page-next-hover');" onmouseout="changeClass(this, 'page-next');"><a href="#">下一页</a></div>								
							</div>
						</div>
					</div>
					<!--content end####################################################-->
					<!--sidebar start####################################################-->
					<div class="sidebar">
						<div class="inner-sidebar">
							<!--user panel start####################################################-->
							<div class="sidebar-panel">
								<div class="sidebar-panel-title">登录</div>
								<div class="sidebar-panel-content">
									<div class="user-login">
										<div class="user-login-input"><div>用户名</div> <input type="text" /></div>
										<div class="user-login-input"><div>密码</div> <input type="password" /> <a href="#">忘记密码</a></div>
										<div class="user-login-submit"><a href="#">立即注册！</a>&nbsp;&nbsp;&nbsp;<input type="submit" value=" 登 录 " />
										</div>
									</div>
								</div>
							</div>
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
