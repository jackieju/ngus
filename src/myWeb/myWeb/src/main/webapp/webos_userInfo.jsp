<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>

<%@ page import="com.ngus.myweb.friend.*"%>
<%@ page import="com.ngus.um.http.*"%>
<%@ page import="com.ngus.um.*"%>
<%@ page import="java.util.*"%>
<%@ page import="com.ns.dataobject.Attribute"%>
<%@ page import="com.ngus.myweb.services.MyWebResService" %>
<%@ include file="webos_checkSession.jsp" %>
<% 

	Attribute count = new Attribute("name", new Integer(0));
	
	Iterator iter = FriendService.listFriend(UserManager.getCurrentUser(), 0, 8, count);
	
	List<String> al = MyWebResService.instance().mostPopularTag(14);
	
	request.setAttribute("userList", iter);
	
	request.setAttribute("keyList",al);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html xmlns="http://www.w3.org/1999/xhtml">

	<head>
		<meta http-equiv="content-type" content="text/html;charset=utf-8" />
		<meta name="generator" content="Adobe GoLive" />
        <style>
.input-button{ width:60px; height:18px; border: 1px solid #CCCCCC; color:#3333FF; }
.input-textarea{
    width:200px;
    margin:0px;
    height:18px;
}
.file-uploader{
    width:200px;
    margin: 10px 0px 10px;
    height:18px;
}   
h2,h3{
    margin:5px 0 5px 10px;
    padding:0;
}
body{
    text-align:center;
}
.border-div{
    margin:0px auto 0px;
    border:1px #CECECE solid;
    height:400px;
    width:250px;    
    text-align:left;
}
.webos-personal-resource{
    margin:10px;
}
.webos-personal-resource-id{
    background:#E6F1F3;
    color: #006699;
    float:none;
}
.webos-personal-resource-pic{
    float:none;
    height:100px;
}
.webos-personal-resource-userinfo{
    font-size:12px;
    color:#999999;
    font-family:Verdana,Arial,Helvetica,sans-serif;
    line-height:25px;
}
    a:hover{
    color:#3333FF;
    text-decoration:underline;
}         
.data-div{
    height:25px;
}
        </style>

		<title>用户资料</title>
	</head>

	<script type="text/javascript">
		function preview(){  
			var x = document.getElementById('userPic');  
			if(!x || !x.value) return;  
			var patn = /\.jpg$|\.jpeg$|\.gif$/i;  
			if(patn.test(x.value)){    
				var y = document.getElementById('prePic');    
				y.src = 'file://localhost/' + x.value;    
				
			}else{    
				alert("您选择的似乎不是图像文件。");  
			}
		}
		
		function show(){
				
				document.getElementById('mobile').style.display="";
				
				
				document.getElementById('mobile').filters.alpha.opacity+=10;
				if (document.getElementById('mobile').filters.alpha.opacity<=90){
					setTimeout("show("+id+",'"+title+"')",5);
				}
				
			}
		
		function showModify(){
			document.getElementById('info_change').style.display="";
			document.getElementById('info_display').style.display="none";
		}
		function showData(){
			document.getElementById('info_change').style.display="none";
			document.getElementById('info_display').style.display="";
            document.getElementById('email').value="";
            document.getElementById('mobile').value="";
		}
	</script>
	
	<body>
        <%
		if(userName!=null){
		UMSession tempUms = null;
		tempUms = new UMClient().checkSession(sessionId);
		UMClient tempclient = new UMClient();
		int id ;
		User tep =null;
		id =tempUms.getUser().getUserId();
		tep = tempclient.getUserByUserId(id);
		%>
        <div id="info_change" class="border-div"style="display:none;">
          <div class="webos-personal-resource">
            <div class="webos-personal-resource-id">
                <h2>ID:<%=userName %></h2>
            </div>
            <form action="/weboschangepic" method="post" enctype="multipart/form-data">
            <div class="webos-personal-resource-pic" >
                <img width="100" height="100" id="prePic" src="userPic?userName=<%=userName%>"/>
            </div>
            <div>
                <input class="file-uploader" type="file" id="userPic" name="userPic" onchange="preview();" />
            </div>
            <div>
                <input class="input-button" type="submit" value="改变头像" />
            </div>
            </form>
            <div class="webos-personal-resource-userinfo">
                <form  action="/weboschangeinfo" method="post" >
	                <div class="data-div">性别：
                        <%if(tempUms.getUser().getSex()==0){ %>
                        <input type="radio" name="sex" value="0" checked/>男
                        <input type="radio" name="sex" value="1" />女
                        <%}else{ %> <input type="radio" name="sex" value="0" />男
                        <input type="radio" name="sex" value="1" checked/>女
                        <%} %>
	                </div><% 	String email=null;
											if(tempUms.getUser().getEmail().length()!=0){ 
												email= tep.getEmail();
											}else{  
												email="没有存放个人邮箱地址";} 
										%>
					<div >电子邮箱：<%=email %>
                    </div>
					<div class="data-div">
						<input type="text" id="email" name="email" value="" class="input-textarea" /> 
					</div><% 	String mobile=null;
											if(tempUms.getUser().getMobile().length()!=0){ 
												mobile= tep.getMobile();
											}else{  
												mobile="没有存放个人电话号码";} 
										%>
					<div>手机号码：<%=mobile %>
                    </div>
					<div class="data-div">
						<input type="text" id="mobile" name="mobile" value="" class="input-textarea"/>
					</div>
					<div align="center">
						<input type="submit" value="提交修改" class="input-button"/>
						<input type="reset" value="重新设定" class="input-button" />
						<input type="button" value="放弃修改" class="input-button" onClick="showData();"/>
					</div>
						<input type=hidden name="id" value="<%=ums.getUser().getSUserId() %>" />
						<input type=hidden name="userName" value="<%=ums.getUser().getUserName() %>" />                                        
				</form>
			</div>
		  </div>
        </div>
        <div id="info_display" class="border-div" >
          <div class="webos-personal-resource">
            <div class="webos-personal-resource-id">
                <h2>ID:<%=userName %></h2>
            </div>
            <div class="webos-personal-resource-pic">
                <img width="100" height="100" src="userPic?userName=<%=userName%>"/>
            </div>
            <div class="webos-personal-resource-userinfo">
                <div>性别：<%if(tempUms.getUser().getSex()==0){ %>男<%}else{ %> 女<%} %></div>
                <div>邮箱地址：<%if(tempUms.getUser().getEmail().length()!=0){ %><%=tep.getEmail() %><%}else{ %>没有存放个人邮箱<%} %></div>
                <div>手机号码：<%if(tempUms.getUser().getMobile().length()!=0){ %><%=tep.getMobile() %><%}else{ %>没有存放个人手机号<%} %></div>
                <div>最近登入时间：<%String loginTime = tempUms.getUser().getLastLogonTime().toString() ;
															int len = loginTime.length(); 
														out.println(loginTime.substring(0,len-4));	
										%></div><br />
                <div><input id="submit_info" type="button" onClick="showModify();" value="修改信息" class="input-button" /></div>
            </div>
          </div>
        </div>
        <% } %>
	</body>

</html>