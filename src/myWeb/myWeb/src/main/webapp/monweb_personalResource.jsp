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
<%@ page import="com.ngus.myweb.services.MyWebResService" %>
<%@ include file="checkSession.jsp" %>
<% 
	String userName= (String)session.getAttribute("username");
	UMSession ums= (UMSession)session.getAttribute("ums");

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

	<head>
		<meta http-equiv="content-type" content="text/html;charset=utf-8" />
		<meta name="generator" content="Adobe GoLive" />
		<title>monweb.com-个人网络收藏夹</title>
		<link href="css/style.css" rel="stylesheet" type="text/css" media="all" />
		<script type="text/javascript">
			function preview(){  
				var x = document.getElementsByName("userPic")[0];  
				if(!x || !x.value) return;  
				var patn = /\.jpg$|\.jpeg$|\.gif$/i;  
				if(patn.test(x.value)){    
					var y = document.getElementById("prePic");    
					y.src = 'file://localhost/' + x.value;    
					
				}else{    
					alert("您选择的似乎不是图像文件。");  
				}
			}
		</script>
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
						<ul class="nav">
							<li class="tab_off"><a href="main.jsp"><img src="images/icon_bookmark.gif" />&nbsp;书签</a></li>
							<li class="tab_off"><a href="viewrss.jsp"><img src="images/icon_rss.gif" />&nbsp;RSS</a></li>
							<li class="tab_off"><a href="#"><img src="images/icon_video.gif" />&nbsp;视频</a></li>
							<li class="tab_off"><a href="monweb_pic.jsp"><img src="images/icon_pic.gif" />&nbsp;图片</a></li>
							<li class="tab_off"><a href="download.jsp"><img src="images/icon_download.gif" />&nbsp;下载</a></li>
						</ul>
						<div style="clear:both;"></div>
						<div style="border-top:1px solid #66CCFF;"></div>
					
						<div>
							<div class="inner_startbox">
								<form class="search_form">
										<input type="text" class="text" />
										<select name="type">
											<option value="1">全文搜索</option>
											<option value="2">搜标签</option>
										</select>
										<input type="image" class="image" src="images/butn_search.gif" />
								</form>
							</div>
						</div>
						<div class="content-panel">
							<div class="content-panel-content">
								<div class="user_info" >
									<div style="background:#E6F1F3;color:#006699;"><h2><%=userName %></h2></div>
									<%
											if(userName!=null){
												UMSession tempUms = null;
												tempUms = ums;//new UMClient().checkSession(sessionId);
												UMClient tempclient = new UMClient();
												int id ;
												User tep =null;
											
												id =tempUms.getUser().getUserId();
											
												tep = tempclient.getUserByUserId(id);
											
									%>
									<html:form action="/changeInfo" method="POST" enctype="multipart/form-data">
									<div>
											<img name="prePic" id="prePic" width=100 height=100 src="userPic?userName=<%=userName%>" />
											<br />
											<html:file property="userPic" onchange="preview()" /><logic:present name="perror"> 注意，上传图片文件大小不能超过64KB </logic:present> 
									</div>
									 <div>性别：<%if(tempUms.getUser().getSex()==0){ %>男<%}else{ %> 女<%} %></div>
										<div>电子邮箱：
										<% 	String email=null;
											if(tempUms.getUser().getEmail().length()!=0){ 
												email= tep.getEmail();
											}else{  
												email="没有存放个人邮箱地址";} 
										%></div>
										<div>
											
											<input type="text" name="email" value="<%=email %>" class="text"/> <logic:present name="eerror"> <span class="font01">请输入正确的email地址</span></logic:present> 
										</div>
										<div>手机号码：<% 	String mobile=null;
											if(tempUms.getUser().getMobile().length()!=0){ 
												mobile= tep.getMobile();
											}else{  
												mobile="没有存放个人电话号码";} 
										%></div>
										<div id="mobile" style="FILTER: alpha(opacity=0);">
											<input type="text" name="mobile" value="<%=mobile %>" class="text"/><logic:present name="merror"><span class="font01">请输入正确的mobile号码</span></logic:present>
										</div>
										<div>最近登入时间：<%String loginTime = tempUms.getUser().getLastLogonTime().toString() ;
															int len = loginTime.length(); 
														out.println(loginTime.substring(0,len-1));	
										%></div>
										<div class="separateline"></div>
										<input type=hidden name="id" value=<%=ums.getUser().getSUserId() %> />
										<input type=hidden name="userName" value=<%=ums.getUser().getUserName() %> />
										<div align="center"><html:submit>提交修改</html:submit><html:reset>重新设定</html:reset></div>
									</html:form>
									<%} %>
								</div>
							</div>
						</div>
					<!--end of the main of left area-->
					</div>
				</div>
			
				<div id="right_side">
					<!--start of the main content of the right side-->
				
					<%@ include file="login.jsp" %>
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