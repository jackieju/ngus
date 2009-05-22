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

	String targetUser = request.getParameter("user");

	Attribute count = new Attribute("name", new Integer(0));
	
	Iterator iter = FriendService.listFriend(UserManager.getCurrentUser(), 0, 8, count);
	

	
	request.setAttribute("userList", iter);
	
	
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html xmlns="http://www.w3.org/1999/xhtml">

	<head>
		<meta http-equiv="content-type" content="text/html;charset=utf-8" />
		<meta name="generator" content="Adobe GoLive" />
		<title>monweb.com-个人网络收藏夹</title>
		<link href="css/style.css" rel="stylesheet" type="text/css" media="all" />
	</head>

	<script type="text/javascript">
		function preview(){  
			var x = document.getElementById("userPic");  
			if(!x || !x.value) return;  
			var patn = /\.jpg$|\.jpeg$|\.gif$/i;  
			if(patn.test(x.value)){    
				var y = document.getElementById("prePic");    
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
	</script>
	
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
							<li class="initial_tab_on"><img src="images/icon_bookmark.gif" />&nbsp;书签</li>
							<li class="tab_off"><a href="#"><img src="images/icon_rss.gif" />&nbsp;RSS</a></li>
							<li class="tab_off"><a href="#"><img src="images/icon_video.gif" />&nbsp;视频</a></li>
							<li class="tab_off"><a href="#"><img src="images/icon_pic.gif" />&nbsp;图片</a></li>
							<li class="tab_off"><a href="#"><img src="images/icon_download.gif" />&nbsp;下载</a></li>
						</ul>
						<div style="clear:both;"></div>
						<div style="border-top:1px solid #66CCFF;"></div>
					
						<div>
							<div class="inner_startbox">
								<%@ include file="search_form.jsp" %> 
							</div>
						</div>
						<div class="content-panel">
							<div class="content-panel-content">
								<div class="user_info" >
									<%
											if(targetUser!=null&&targetUser.length()>0){
												UMSession tempUms = null;
											//	UserInfo ui = new UMClient().getUserInfoByUserName(targetUser);
												User u = new UMClient().getUserByUserName(targetUser);
												//UMClient tempclient = new UMClient();
												int id ;
											//	User tep =null;
											
											//	id =tempUms.getUser().getUserId();
											
											//	tep = tempclient.getUserByUserId(id);
											
									%>
									
									
										<div style="background:#E6F1F3;color:#006699;"><h2><%=u.getUserName() %></h2></div>
											<div>
											<img name="prePic" id="prePic" width=100 height=100 src="userPic?userName=<%=u.getUserName()%>" />
											<div><a href="#"><img src="images/samsg_icon.gif" />&nbsp;发送邮件</a></div>
											<div><a href="addFriend.do?friendName=<%=u.getUserName()%>"><img src="images/add.gif" />&nbsp;加入我的好友列表</a></div>
									
											<br />
											
										</div>
										<div>性别：<%if(u.getSex()==0){ %>男<%}else{ %> 女<%} %></div>
										<div>邮箱地址：<%if(u.getEmail().length()!=0){ %><%=u.getEmail() %><%}else{ %>没有存放个人邮箱<%} %></div>
										<div>手机号码：<%if(u.getMobile().length()!=0){ %><%=u.getMobile() %><%}else{ %>没有存放个人手机号<%} %></div>
										<div>最近登入时间：<%String loginTime = u.getLastLogonTime().toString() ;
															int len = loginTime.length(); 
														out.println(loginTime.substring(0,len-4));	
										%></div>
										<div class="separateline"></div>
										
									<%} %>
								</div>
							</div>
						</div>
					<!--end of the main of left area-->
					</div>
				</div>
			
				<%@ include file="login.jsp" %>
				<!--end of the main content-->
			</div>
			
			<div class="clear">&nbsp;</div>
			<%@ include file="footer.jsp" %>
			<!--end of the body-->
			</div>
		</div>
	</body>

</html>