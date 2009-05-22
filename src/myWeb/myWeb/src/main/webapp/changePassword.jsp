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



%>

<script language="javascript" type="text/javascript">

	var http= getHTTPObject();
	var validCorrect=1;   //1表示不正确

	
	function handleHttpResponse2(){
		if(http.readyState ==4 ){
		
			if(http.status == 200){
				
				validCorrect = http.responseText;
				
				if(validCorrect == 1){
					
					document.getElementById("validCodeError").style.display="";
					document.getElementById("validCode").style.background="#FF0000";
					document.getElementById("validCodeError").innerText = "对不起，请输入正确验证马";
				}else{
					document.getElementById("validCode").style.background="#FFFFFF";
					document.getElementById("validCodeError").style.display="none";
					
				}
			}
			else{
			alert("你所请求的页面发生异常，可能会影响你的浏览");
			alert(http.status);
			}
		}
		
			
	}
	
	
	function checkVaild(){
		var url="checkValidCode.jsp";
		var name= document.getElementById("validCode").value;
		url += "?validCode="+name;
		http.open("GET",url,true);
		http.onreadystatechange=handleHttpResponse2;
		http.send(null);
		
		return;
	}
	

	function getHTTPObject() 
	{
		if (window.ActiveXObject) 
			return  new ActiveXObject("Microsoft.XMLHTTP");
	  	else if (window.XMLHttpRequest) 
			return  new XMLHttpRequest();                

	}
		
	
	
	function chkpassword(){
		var m =document.changePassword;
		if(m.password.value.length.length>20||m.password.value.length<5){
			document.getElementById("passwordError").style.display = "";
			document.getElementById("password").style.background= "#FF0000";
			document.getElementById("passwordError").innerText = "对不起,密码必须为英文字母、数字或下划线，长度为5~20!";
		}
		else{
			document.getElementById("password").style.background= "#FFFFFF";
			document.getElementById("passwordError").style.display = "none";
		}
	}
	
	function chkconfirmPassword(){
		var m =document.changePassword;
		if(m.password.value != m.confirmPassword.value){
			document.getElementById("confirmPasswordStr").style.display = "";
			document.getElementById("confirmPassword").style.background= "#FF0000";
			document.getElementById("confirmPasswordStr").innerText = "对不起,密码与重复密码不一致!";
		}
		else{
			document.getElementById("confirmPassword").style.background= "#FFFFFF";
			document.getElementById("confirmPasswordStr").style.display = "none";
		}
	}


	
	
	function checkfield(){
	{
 		var m=document.changePassword; 
 		
		if(m.password.value.length==0)		
		{		
		　 alert("对不起,密码必须为英文字母、数字或下划线，长度为5~20。");		
		　 m.password.focus();		
		　 return false;	
		}

		 if (m.password.value != m.confirmPassword.value)
		 {
		　 alert("对不起,密码与重复密码不一致!");
		　 m.confirmPassword.focus();
		　 return false;
		 } 
		 
		 if(validCorrect==1)
		 {
		　 alert("对不起,验证码不对！！");
		　 m.validCode.focus();
		　 return false; 
		 }

 		

	}
	
</script>

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
										
											<html:form action="/change" method="GET">
									
												<table width="100%">
									
													<tr>
														<td align="center">
															<H1>
																用户修改密码
															</H1>
														</td>
													</tr>
									
													<tr>
														<td align="center">
															------ sean
														</td>
													</tr>
									
												</table>
									
									
									
												<HR>
									
												<table width="400" border="0" cellpadding="1" cellspacing="1" align="center">
									
													<tr>
									
														<td>
															<font color="red">*</font>
														</td>
									
														<td>
															你的新密码:
														</td>
									
														<td>
															<input type="password" name="password" id="password" maxlength="20" style="background:#FFFFFF" onBlur="chkpassword()" />
									
															
															<div id="passwordError" style="background-color:#FF9900;display:none"></div>
									
														</td>
									
													</tr>
									
													<tr>
									
														<td>
															<font color="red">*</font>
														</td>
									
														<td>
															再输入一次:
														</td>
									
														<td>
															<input type="password" name="confirmPassword" id="confirmPassword" maxlength="20" style="background:#FFFFFF" onBlur="chkconfirmPassword()" />
															<div id="confirmPasswordStr" style="background-color:#FF9900;display:none"></div>
									
														</td>
								
												</tr>
												
												<tr>
								
													<td>
														<font color="red">*</font>
													</td>
								
													<td>
														验证码:
													</td>
								
													<td>
														<html:text property="validCode" maxlength="20" style="background:#FFFFFF" onblur="checkVaild()"/>
														<img width=60  height=20 src="validateCode.jsp" >
														<div id="validCodeError" style="background-color:#FF9900;display:none"></div>
													</td>
								
												</tr>
												
											</table>
		
				</div>
		
					<div align="center">
		
					
						
		
						<html:submit onclick="return checkfield()">确定</html:submit>
					<html:reset > 取消</html:reset>
	
				</div>
	
	
	
			</html:form>
											
										
										
										
									
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
	<ul id="contextMenu" style="display:none">
	<li onmouseover="javascript: this.className='menuHighLighted';" onmouseout="javascript: this.className=null" onclick="javascript: addTest();"><span class="imgbox"><img src="img/contextmenu/add_bookmark.gif"/></span><span class="text">添加书签</span></li>
	<li onmouseover="javascript: this.className='menuHighLighted';" onmouseout="javascript: this.className=null" onclick="javascript: addFolderTest();"><span class="imgbox"><img src="img/contextmenu/add_folder.gif"/></span><span class="text">添加目录</span></li>
	<li onmouseover="javascript: this.className='menuHighLighted';" onmouseout="javascript: this.className=null"><span class="text">删除</span></li>
	<li onmouseover="javascript: this.className='menuHighLighted';" onmouseout="javascript: this.className=null"><span class="text">刷新</span></li>
	</ul>
	</body>
</html>



