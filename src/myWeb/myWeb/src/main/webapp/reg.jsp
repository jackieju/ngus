<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>  

<script language="javascript" type="text/javascript">

	var http= getHTTPObject();
	var hasUserName=1;	//1表示用户名存在
	var hasPenName=1;	//1表示笔名存在
	var validCorrect=0;   //1表示不正确
	
	
	
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
			else{
			alert("你所请求的页面发生异常，可能会影响你的浏览");
			alert(http.status);
			}
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
			else{
			alert("你所请求的页面发生异常，可能会影响你的浏览");
			alert(http.status);
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
			else{
			alert("你所请求的页面发生异常，可能会影响你的浏览");
			alert(http.status);
			}
		}
		
			
	}
	
	function chkUser(){
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
			alert("对不起，该笔名已经有了");
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
<html>
	<body topmargin="0">
		
		<html:form action="/userReg" method="POST" enctype="multipart/form-data">

			<table width="100%">

				<tr>
					<td align="center">
						<H1>
							用户注册
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
						用户名:
					</td>

					<td>

						<%--<input type="text" name="userName" maxlength="20" style="background:#FFFFFF" onBlur="chkUser()" onChange="copyName()" onkeyup="copyName()" value="" />--%>
						<html:text property="userName" maxlength="20" style="background:#FFFFFF" onblur="chkUser()" onchange="copyName()" onkeyup="copyName()" />
						<div id="userNameError" style="background-color:#FF9900;display:none"></div>

					</td>

				</tr>

				<tr>

					<td>
						<font color="red">*</font>
					</td>

					<td>
						笔名:
					</td>

					<td>

						
						<html:text property="penName" maxlength="100" style="background:#FFFFFF" onblur="chkPenName()"> </html:text>
						<div id="penNameError" style="background-color:#FF9900;display:none"></div>


					</td>

				</tr>

				<tr>

					<td>
						<font color="red">*</font>
					</td>

					<td>
						用户密码:
					</td>

					<td>
						
						<html:password property="password" maxlength="20" style="background:#FFFFFF" onblur="chkpassword()"/>
						<div id="passwordError" style="background-color:#FF9900;display:none"></div>

					</td>

				</tr>

				<tr>

					<td>
						<font color="red">*</font>
					</td>

					<td>
						确认密码:
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
						<input type="text" name="validCode" id="validCode" maxlength="20" style="background:#FFFFFF" onblur="checkVaild()"/>
						<img width=60  height=20 src="ValidateCode" >
						<div id="validCodeError" style="background-color:#FF9900;display:none"></div>

					</td>

				</tr>
				
				<tr>

					<td>
						<font color="red">*</font>
					</td>

					<td>
						用户邮箱:
					</td>

					<td>
						<html:text property="email" maxlength="40" style="background:#FFFFFF" onblur="checkEmail()" />
						<div id="EmailError" style="background-color:#FF9900;display:none"></div>

					</td>

				</tr>
				
				<tr>

					<td>
						
					</td>

					<td>
						用户性别:
					</td>

					<td>
						<span style="width:100px;"><html:radio property="sex" value="0">男</html:radio></span>
						<span style="width:100px;"><html:radio property="sex" value="1">女</html:radio></span>
					</td>

				</tr>
				
				<tr>

					<td>
						
					</td>

					<td>
						用户手机:
					</td>

					<td>
						<html:text property="mobile" maxlength="20" style="background:#FFFFFF" onblur="checkMobile()" />
						<div id="mobileError" style="background-color:#FF9900;display:none"></div>

					</td>

				</tr>
				
				<tr>

					<td>
						
					</td>

					<td>
						选择用户头像:
					</td>

					<td>
						<html:file property="userPic"  />
						

					</td>

				</tr>

			</table>



			<div align="center">

			
				

				<html:submit onclick="return checkfield()">确定</html:submit>
				<html:reset > 取消</html:reset>

			</div>



		</html:form>

	</body>

</html>
