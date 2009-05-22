<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%> 
<%@ page import="java.util.List"%>
<%@ page import="com.ngus.myweb.dataobject.MyWebRes"%>
<%@ page import="com.ngus.myweb.services.MyWebResService"%>
<%@ page import="com.ngus.um.dbobject.*,com.ngus.um.*" %>
<%@ page import="java.util.*" %>
<%@ page import="com.ns.mail.*, com.ns.log.*" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

	<head>
		<meta http-equiv="content-type" content="text/html;charset=utf-8" />
		<meta name="generator" content="Adobe GoLive" />
		<title>monweb.cn - error</title>
		<link href="css/style.css" rel="stylesheet" type="text/css" media="all" />
	</head>

	<body>
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
						<div style="color:#999;">
							<h2>Error</h2><br />
							
						</div>
						
						
							<div><span class="font01">*</span>message:<br />
							<%=request.getParameter("msg") %>
							</div>
						
						
					</div>
					<!--end of the main of left area-->
					</div>
				</div>
			
				<div id="right_side">
					<!--start of the main content of the right side-->
					
					<!--block no.1|starts|-->
					<div class="sidebar-panel">
						<div class="sidebar-panel-title"><img src="images/star.gif" />&nbsp;收集精彩...</div>
						<div>
							<div class="sider_ads">
								<img src="images/collect.gif" alt="搜集精彩,分享快乐" /><br />
								丰富个人收藏
							</div>
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

