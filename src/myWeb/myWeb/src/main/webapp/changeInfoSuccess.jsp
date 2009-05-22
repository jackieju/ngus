<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ page import="com.ngus.myweb.services.MyWebResService"%>
<%@ page import="com.ngus.myweb.dataobject.*"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<% 
	List<BookMark> bk = new ArrayList<BookMark>();
	List<Folder> fd = new ArrayList<Folder>();
	try{
		List<ObjectTree> ob = MyWebResService.instance().listAll(1);
		bk = new ArrayList<BookMark>();
		fd = new ArrayList<Folder>();
		for(ObjectTree tmp : ob){
			if(tmp.getNode() instanceof BookMark){
				bk.add((BookMark)tmp.getNode());
			}
			else{
				fd.add((Folder)tmp.getNode());
			}
		}		
	}
	catch(Exception e){
		e.printStackTrace();
	}

	String userName = (String)session.getAttribute("userName");
	request.setAttribute("fd",fd);
	request.setAttribute("bk",bk);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<link rel="stylesheet" type="text/css" title="default" href="css/lite_blue.css" />
		<link href="css/css.css" rel="stylesheet" type="text/css"/>
		<title>MyWeb - 首页</title>
		<script type="text/javascript" src="javascript/prototype.js"></script>
		<script type="text/javascript" src="javascript/jsTree.js"></script>
		<script type="text/javascript" src="javascript/main.js"></script>
	</head>
	<body>
		<script type="text/javascript">
			document.body.onclick = function(ev){
				$("contextMenu").style.display = "none";
			}
		</script>
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
					<div class="panel_content">
						<div class="inner-content">
							<div class="menu">
								<div class="menu-tab-unselected"><a href="#">首 页</a></div>
								<div class="menu-tab-selected">链 接</div>
								<div class="menu-tab-unselected"><a href="#">其 他</a></div>
								<div class="menu-tab-unselected"><a href="#">帮 助</a></div>
							</div>
							<!--xxx panel start####################################################-->
							<div class="content-panel">
								<div class="content-panel-title">某个操作面板</div>
								<div class="content-panel-content">
									<div style="height: 370px">
									<div style="float: left">
										
									</div>
									
									<div id="bk_display" style="float: left; border: 1px solid gray; width: 730px; height: 370px; overflow:auto">
										<br/><br/><br/><br/><br/>
										<div align="center">
										你的个人信息修改成功。<p>
										<a href="main.jsp">返回首页面</a>
										</p>
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
							
							<% if(userName ==null){ %>
								<jsp:include page="mainTest.jsp" />
							<%} else{%>
							
							
								<div class="sidebar-panel">
								<div class="sidebar-panel-title">用户信息</div>
								<div class="sidebar-panel-content">
									<div class="user-login">
										<div class="user-login-input">用户名: <%=(String)session.getAttribute("userName")%></div>
										<div class="user-login-input"><a href="changeInfo2.jsp">修改个人信息</a>&nbsp;&nbsp;<a href="viewUserInfo.jsp">查看个人信息</a></div>
										<div class="user-login-input"><a href="changePassword.jsp">修改密码</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="logout.jsp">[退出]</a></div>
										</div>
									</div>
								</div>
							
							
							<% } %>
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
	<ul id="contextMenu" style="display:none">
	<li onmouseover="javascript: this.className='menuHighLighted';" onmouseout="javascript: this.className=null" onclick="javascript: addTest();"><span class="imgbox"><img src="img/contextmenu/add_bookmark.gif"/></span><span class="text">添加书签</span></li>
	<li onmouseover="javascript: this.className='menuHighLighted';" onmouseout="javascript: this.className=null" onclick="javascript: addFolderTest();"><span class="imgbox"><img src="img/contextmenu/add_folder.gif"/></span><span class="text">添加目录</span></li>
	<li onmouseover="javascript: this.className='menuHighLighted';" onmouseout="javascript: this.className=null"><span class="text">删除</span></li>
	<li onmouseover="javascript: this.className='menuHighLighted';" onmouseout="javascript: this.className=null"><span class="text">刷新</span></li>
	</ul>
	</body>
</html>
