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
		<title>monweb.com-新用户注册</title>
		<link href="css/style.css" rel="stylesheet" type="text/css" media="all" />
	</head>
	
	<body>
	<script language="javascript" type="text/javascript">

	var http= getHTTPObject();
	var hasUserName=1;	//1表示用户名存在
	var hasPenName=1;	//1表示笔名存在
	var validCorrect=0;   //1表示不正确
	
	function preview(){  
			var x = document.getElementById("userPic");  
			if(!x || !x.value) return;  
			var patn = /\.jpg$|\.jpeg$|\.gif$/i;  
			if(patn.test(x.value)){    
				var y = document.getElementById("prePic");    
				y.src = 'file://localhost/' + x.value;    
				y.style.display="";	
			}else{    
				alert("您选择的似乎不是图像文件。");  
			}
		}
	
	
	
	function handleHttpResponse(){
		if(http.readyState ==4 ){
			if(http.status==200){
				
				
				hasUserName = http.responseText;
				if(hasUserName==1){
					document.getElementById("userNameError").style.display="";
					document.getElementById("userName").style.background="#FF0000";
					document.getElementById("userNameError").innerText = "对不起，用户名存在";
				}else{
					document.getElementById("userName").style.background="#FFFFFF";
					document.getElementById("userNameError").style.display="";
					document.getElementById("userNameError").innerText = "恭喜你，用户名不存在";
				}
			}
		
		}else{
			document.getElementById("userNameError").innerHTML = "<img src='img/ajax-loader.gif' />";
		}
		
			
	}
	
	function handleHttpResponse1(){
		if(http.readyState ==4 ){
		
			if(http.status == 200){
				
				hasPenName = http.responseText;
				
				if(hasPenName == 1){
					
					document.getElementById("penNameError").style.display="";
					document.getElementById("penName").style.background="#FF0000";
					document.getElementById("penNameError").innerText = "对不起，笔名存在";
				}else{
					document.getElementById("penName").style.background="#FFFFFF";
					document.getElementById("penNameError").style.display="";
					document.getElementById("penNameError").innerText = "恭喜你，笔名不存在";
				}
			}
			
		}
		
			
	}
	
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
		}
		
			
	}
	
	function chkUser(){
		var user = document.userRegForm.userName.value;
        var pattern = /^([.a-zA-Z0-9_-]{2,20})$/;
        if(!pattern.test(user)){
        	document.getElementById("userName").style.background="#FFFFFF";
            document.getElementById("userNameError").style.display="";
            document.getElementById("userNameError").innerText ="用户名必须由字母数字，下划线组成，5-10字符内";
            return;
        }
			
		var url="checkUser.jsp";
		var name= document.getElementById("userName").value;
		
		url += "?userName="+name;
		http.open("GET",url,true);
		http.onreadystatechange=handleHttpResponse;
		
		
		http.send(null);
	
		return;
	}
	
	function chkPenName(){
		var url="checkPenName.jsp";
		var name= document.getElementById("penName").value;
		url += "?penName="+name;
		http.open("GET",url,true);
		http.onreadystatechange=handleHttpResponse1;
		http.send(null);
		
		return;
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
	
	function copyName(){
		
		var name= document.getElementById("userName").value;
		document.getElementById("penName").value=name;
		chkPenName();
	}
	
	
	function getHTTPObject() 
	{
		if (window.ActiveXObject) 
			return  new ActiveXObject("Microsoft.XMLHTTP");
	  	else if (window.XMLHttpRequest) 
			return  new XMLHttpRequest();                

	}
		
	
	
	function chkpassword(){
		
		var m =document.userRegForm;
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
		
		var m =document.userRegForm;
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

	function checkEmail(){
		var email = document.userRegForm.email.value;
		var pattern = /^([.a-zA-Z0-9_-])+@([.a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
		if(!pattern.test(email)){
			document.getElementById("EmailError").style.display = "";
			document.getElementById("email").style.background= "#FF0000";
			document.getElementById("EmailError").innerText = "输入的email地址格式不对!";
		}
		else{
			document.getElementById("email").style.background= "#FFFFFF";
			document.getElementById("EmailError").style.display = "none";
		}
	}

	function checkMobile(){
		var mobile = document.userRegForm.mobile.value;
		
		if(isNaN(mobile)){
			document.getElementById("mobileError").style.display = "";
			document.getElementById("mobile").style.background= "#FF0000";
			document.getElementById("mobileError").innerText = "请输入正确的电话号码!";
		}
		else{
			document.getElementById("mobile").style.background= "#FFFFFF";
			document.getElementById("mobileError").style.display = "none";
		}
	}
	
	


	
	
	function checkfield(){
	{
		
 		var m=document.userRegForm; 
 		
 		
 		if(m.userName.value.length==0){
			alert("对不起,用户名必须为英文字母、数字或下划线，长度为5~20。");
		　 	m.userName.focus();
		　 	return false;
 		}
 		

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

		 if(m.penName.value.length==0)
		 {
		　 alert("对不起,笔名不能为空！！");
		　 m.penName.focus();
		　 return false; 
		 }
		 
		 if(validCorrect==1)
		 {
		　 alert("对不起,验证码不对！！");
		　 m.validCode.focus();
		　 return false; 
		 }
		
		var email = m.email.value;
		var count = email.indexOf("@");
		if(count == -1){
			alert("对不起，输入的email地址格式不对！");
			m.email.focus();
			return false ;
		}
		
		if(hasUserName == 1){
			alert("对不起，该用户已经有了");
			m.userName.focus();
			return false ;
		}
		
		if(hasPenName == 1){
			alert("对不起，该笔名已经c");
			m.penName.focus();
			return false ;
		}
		
		var mobile = document.userRegForm.mobile.value;
		if(isNaN(mobile)){
			alert("请输入正确的电话号码");
			m.mobile.focus();
			return false ;
		}

}
	}
</script>
<%
	List<IUser> arr = UMClient.getNewUser(8);
	List<MyWebRes> al = MyWebResService.instance().listMostRecentRes(9);
	request.setAttribute("newUser",arr);
	request.setAttribute("newRes",al);
%>
		<div id="mainwrap">
			<!--start of the body-->
			
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
							<h2>成为新的Monweb用户</h2><br />
							<span class="font01">"*"为必填项</span>
						</div>
						<html:form action="/userReg" method="POST" enctype="multipart/form-data">
							<div><span class="font01">*</span>用户名:<br />
								<html:text property="userName" maxlength="20" style="background:#FFFFFF" onblur="chkUser()" onchange="copyName()" onkeyup="copyName()" />
								<span id="userNameError" style="background-color:#FF9900;display:none;float:left;width:152px"></span>
								<%if (error_level ==0){%><span class="font01">该用户已经存在</span><%} %>
							</div>
								<span id="userNameError" style="background-color:#FF9900;display:none;float:left;width:152px"></span><br/>
								
							<div>
								<span class="font01">*</span>笔名:<br />
								<html:text property="penName" maxlength="20" style="background:#FFFFFF" onblur="chkPenName()"> </html:text>
								<%if (error_level ==1){%><span class="font01">该笔名已经存在</span><%} %>
							</div>
								<div id="penNameError" style="background-color:#FF9900;display:none;float:left;width:152px"></div>
								<br/>
							<div>
								<span class="font01">*</span>用户密码:<br />
								<html:password property="password" maxlength="20" size="22" style="background:#FFFFFF" onblur="chkpassword()"/>
								
							</div>
								<div id="passwordError" style="background-color:#FF9900;display:none;float:left;"></div>
								<br/>
							<div>
								<p><span class="font01">*</span>确认密码:<br />
								<html:password property="confirmPassword" maxlength="20" size="22" style="background:#FFFFFF" onblur="chkconfirmPassword()"/>
								
								<%if (error_level ==2){%><span class="font01">对不起，你输入的密码不匹配</span><%} %>
							</div>
								<div id="confirmPasswordStr" style="background-color:#FF9900;display:none;float:left;"></div>
								<br/>
							<div>
								<p><span class="font01">*</span>验证码:<br />
								<html:text property="validCode" maxlength="20" style="background:#FFFFFF" onblur="checkVaild()"/>
								<img width=60  height=20 src="ValidateCode" />
								<%if (error_level ==3){%><span class="font01">验证码不正确</span><%} %>
							</div>
								<div id="validCodeError" style="background-color:#FF9900;display:none;float:left;"></div>
								<br/>
							<div>
								<span class="font01">*</span>您的邮箱:<br />
								<html:text property="email" maxlength="40" style="background:#FFFFFF" onblur="checkEmail()" />
								<%if (error_level ==5){%><span class="font01">请输入正确的email地址</span><%} %>
							</div>
								<div id="EmailError" style="background-color:#FF9900;display:none;float:left;"></div>
								<br/>
							<div>
								<br/><span>&nbsp;&nbsp;</span>您的性别:<br />
								<span style="width:100px;"><html:radio property="sex" value="0">男</html:radio></span>
								<span style="width:100px;"><html:radio property="sex" value="1">女</html:radio></span>
							</div>
							<div>
								<span>&nbsp;&nbsp;</span>用户手机:<br />
								<html:text property="mobile" maxlength="20" style="background:#FFFFFF" onblur="checkMobile()" />
								<%if (error_level ==4){%><span class="font01">请输入正确的手机号码</span><%} %>
							</div>
								<div id="mobileError" style="background-color:#FF9900;display:none;float:left;"></div>
								<p>
							<div>
								<img name="prePic" id="prePic" width=80 height=80 style="display:none"/>
							</div>

							<div>
								<span>&nbsp;&nbsp;</span>选择用户头像:<br />
								<html:file property="userPic" onchange="preview()" />
								<%if (error_level ==6){%><span class="font01">对不起，图片文件大小不能超过64KB</span><%} %>
							</div>
							<br />
							<div align="center">
								<html:submit onclick="return checkfield()">确定</html:submit>
								<html:reset > 取消</html:reset>
							</div>
						</html:form>
					</div>
					<!--end of the main of left area-->
					</div>
				</div>
			
				<div id="right_side">
					<!--start of the main content of the right side-->
					
					<!--block no.1|starts|-->
					<div class="sidebar-panel">
						<div class="sidebar-panel-title"><img src="images/star.gif" />&nbsp;和更多的人分享</div>
						<div>
							 分享你使用体验,分享精彩,分享快乐!!!<br />
							 <div align="center"><img src="images/share.jpg" alt="分享精彩,分享快乐" /><br />&nbsp;</div>
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