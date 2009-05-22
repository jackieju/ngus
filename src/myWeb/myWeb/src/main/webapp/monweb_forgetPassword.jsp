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
		<title>monweb.com-取回新密码</title>
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
							<h2>取回密码</h2><br />
							<span class="font01">"*"为必填项</span>
						</div>
						<%
							String strUserName = request.getParameter("strUserName");
							if(strUserName==null || strUserName.length() == 0){
								
							
						%>
						<html:form action="/forgetPassword" method="POST">
							<div><span class="font01">*</span>用户名:<br />
								<html:text property="userName" />
							</div>
							<div>
								<span class="font01">*</span>验证码:<br />
								<html:text property="validCode" size="4"/><img width=60  height=20 src="ValidateCode" />
							</div>
				
							
							<br />
							<div>
								<html:submit>发送新密码</html:submit>
							</div>
						
						</html:form>
						<div class="separateline"></div>
						<div style="color:#FF6600;">
							<strong>注：新的密码将会通过邮件发至您的邮箱，在登录后请及时修改密码！</strong>
						</div>
						<%}
							else
							{
								int lRet = -1;							
								try{
									String strNewPasswd = new UMClient().resetPassword(strUserName);
									
									MailSettings mail = new MailSettings();	
									mail.ccAddresses = null;
									mail.fromAddress = "someone@xx.com";
									mail.fromName = "myWeb";
									
									mail.toAddress =  new UMClient().getUserInfoByUserName(strUserName).getEmail();
									mail.messageBody = "Dear user, your new password is "+ strNewPasswd;
									mail.messageSubject = "Your password is reset";
									MailSender.sendMailThreaded(mail);
									out.println("Your new password has been sent to you by email(" + mail.toAddress + ") , please check it<p>");
								}catch(Exception e){
									Log.error(e);
									throw e;
								}
							}
						
						%>
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

