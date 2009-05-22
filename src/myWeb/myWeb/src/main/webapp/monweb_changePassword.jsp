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

<% 
	response.setHeader("Cache-Control","no-cache");
	response.setHeader("Pragma","no-cache");
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

	<head>
		<meta http-equiv="content-type" content="text/html;charset=utf-8" />
		<meta name="generator" content="Adobe GoLive" />
		<title>monweb.com-我的消息</title>
		
		<link href="css/style.css" rel="stylesheet" type="text/css" media="all" />		
		<link href="css/css.css" rel="stylesheet" type="text/css">
	</head>

	<body>
		
		<script type="text/javascript">
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
		 		var m=document.getElementById('changePassword'); 
		 		
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
				 m.submit();
			}
		</script>
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
						<ul class="nav">
							<li class="tab_off"><a href="main.jsp"><img src="images/icon_bookmark.gif" />&nbsp;书签</a></li>
							<li class="tab_off"><a href="viewrss.jsp"><img src="images/icon_rss.gif" />&nbsp;RSS</a></li>
							<li class="tab_off"><a href="#"><img src="images/icon_video.gif" />&nbsp;视频</a></li>
							<li class="tab_off"><a href="monweb_pic.jsp"><img src="images/icon_pic.gif" />&nbsp;图片</a></li>
							<li class="tab_off"><a href="download.jsp"><img src="images/icon_download.gif" />&nbsp;下载</a></li>
						</ul>
					</div>
					<div style="clear:both;"></div>
					<div style="border-top:1px solid #66CCFF;"></div>
					
					<form class="search_form" method="post" action="search">
									<input type="text" class="text" name="key" />
									<select>
										<option value="fulltext">全文搜索</option>
										<option value="tag">搜标签</option>
										<option vlaue="user">搜用户</option>
									</select>
									<input type="image" class="image" src="images/butn_search.gif" />
					</form>
								
							<!--xxx panel start####################################################-->
						<div class="content-panel">
							<div class="content-panel-content">
								<div style="height: 600px;">
									
									
									
									<div style="border: 1px solid #CECECE; overflow: auto; float: left; width: 660px; height: 600px;">
										
											<html:form action="/change" method="Post">
											<table cellpadding="3" cellspacing="1" align="center" >
												<tr>
													<td class="register_table">
														<span class="font01">*</span>你的新密码:</td>
													<td>
														<input type="password" name="password" id="password" maxlength="20" style="background:#FFFFFF" onblur="chkpassword()" />
														<div id="passwordError" style="background-color:#FF9900;display:none;float:left;"></div>
													</td>
												</tr>
												
												<tr>
													<td class="register_table">
														<span class="font01">*</span>再输入一次:</td>
													<td>
														<input type="password" name="confirmPassword" id="confirmPassword" maxlength="20" style="background:#FFFFFF" onblur="chkconfirmPassword()" />
														<div id="confirmPasswordStr" style="background-color:#FF9900;display:none;float:left;"></div>
													</td>
												</tr>
												
												<tr>
													<td class="register_table">
														<span class="font01">*</span>验证码:</td>
													<td>
														<html:text property="validCode" size="18" style="background:#FFFFFF" onblur="checkVaild()"/>
														<img width=60  height=20 src="ValidateCode" />
														<div id="validCodeError" style="background-color:#FF9900;display:none;float:left;"></div>
													</td>
												</tr>	
												</table>		
												<div align="center">
													<a href="#" onclick="checkfield()">确定</a>
													<a href="#" onclick="document.getElementById('changePassword').reset()">取消</a>
												</div>
											
											</html:form>
										
									</div>
									</div>
																	
								</div>
							</div>
							<!--xxx panel end####################################################-->
						</div> <!-- inner content -->
					</div><!-- panel content -->
					<!--content end####################################################-->
					
					<%@ include file="login.jsp" %>
				<!--end of the main content-->
			</div>
			
			<div class="clear">&nbsp;</div>
			<%@ include file="footer.jsp" %>
			<!--end of the body-->
			</div>
		</div>
		<div id="cover" style="display:none;z-index:1;FILTER: alpha(opacity=70); POSITION: absolute;width:1280px;height:1024px;background-color:#000000 ;top:0px;left:0px" >
	</body>

</html>