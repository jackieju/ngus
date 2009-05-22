<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ page import="com.ngus.myweb.services.MyWebResService"%>
<%@ page import="com.ngus.myweb.dataobject.*"%>
<%@ page import="com.ngus.message.*"%>
<%@ page import="com.ngus.myweb.util.*"%>
<%@ page import="com.ngus.myweb.dataobject.*"%>
<%@ page import="com.ngus.myweb.friend.*"%>
<%@ page import="com.ngus.um.http.*"%>
<%@ page import="java.util.*"%>
<%@ page import="com.ns.dataobject.Attribute"%>
<%@ page import="java.net.*"%>
<%@ page import="com.ngus.myweb.services.MyWebResService" %>
<%@ include file="checkSession.jsp" %>


<% 
	String userName= (String)session.getAttribute("username");
	response.setHeader("Cache-Control","no-cache");
	response.setHeader("Pragma","no-cache");
	if(userName!=null){
		int count = MessageEngine.instance().getNotReadedCount(userName);

		
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

	<head>
		<meta http-equiv="content-type" content="text/html;charset=utf-8" />
		<meta name="generator" content="Adobe GoLive" />
		<title>monweb.com-我的消息</title>
		<script type="text/javascript" src="javascript/prototype.js"></script>
		<script type="text/javascript" src="javascript/jsTree.js"></script>
		<script type="text/javascript" src="javascript/main.js"></script>
		<script type="text/javascript" src="javascript/menu.js"></script>
		<script type="text/javascript" src="javascript/page.js"></script>
		<script type="text/javascript" src="javascript/frame.js"></script>
		<link href="css/style.css" rel="stylesheet" type="text/css" media="all" />		
		<link href="css/css.css" rel="stylesheet" type="text/css">
	</head>

	<body>
		<script type="text/javascript">
			var http= getHTTPObject();
	
			function response(){
				
				if(http.readyState ==4 ){
					
					if(http.status == 200){	
							
						document.getElementById("bk_display").innerHTML=http.responseText;			
						load1();	
					}
					
				}
				else{
					document.getElementById("bk_display").innerHTML="<img src='img/loading.gif'/>";
					
				}	
					
			}
			
			function postMsg(){
				
				var url="postMsg.jsp"		
				http.open("POST",url,true);
				http.onreadystatechange=response;
				http.send(null);
				return;
			}
	
			function listPost(userName,currPage){
				var url="ListMessage?type=receive";
				url+="&userName="+userName;
				url+="&currPage="+currPage;	
				http.open("POST",url,true);
				http.onreadystatechange=response;	
				http.send(null);
				return;
			}
			
			function listReceive(userName,currPage){
				
				var url="ListMessage?type=post";
				url+="&userName="+userName;
				url+="&currPage="+currPage;	
							
				http.open("POST",url,true);
				http.onreadystatechange=response;	
				http.send(null);
				return;
			}
			
			function response2(){
				
				if(http.readyState ==4 ){
					
					if(http.status == 200){	
						
						document.getElementById("inbox").innerHTML=http.responseText;	
							
					}
					
				}
				else{
						
				}	
					
			}
			
			function load1(){
				
				var url="inbox.jsp";
				http.open("POST",url,true);
				http.onreadystatechange=response2;	
				http.send(null);
				return;
			}
			
			function showMessage(id,type){
				
				
				var messageId = id;
				var url="messageContent.jsp"
				url += "?id="+messageId;
				url += "&type="+type;			
				http.open("POST",url,true);
				http.onreadystatechange=response;	
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
			
			
			function show(id,title){
				
				document.getElementById('cover').style.display="";
				document.getElementById('replyMessage').style.display="";
				document.getElementById('id').value=id;
				document.getElementById('title').value="Re:"+title;
				document.getElementById('replyMessage').filters.alpha.opacity+=10;
				if (document.getElementById('replyMessage').filters.alpha.opacity<=90){
					setTimeout("show("+id+",'"+title+"')",5);
				}
				
			}
			
			function hide(){
				document.getElementById('cover').style.display="none";
				document.getElementById('replyMessage').filters.alpha.opacity-=10;
				if (document.getElementById('replyMessage').filters.alpha.opacity>=1){ 
					
					setTimeout("hide()",5);
				}else{
					document.getElementById('replyMessage').style.display="none";
				}
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
									<div id="inbox" style="float: left">
										<ul id="tree">
											
												<div><img src="img/folder_folder.gif"/><a href="#" a target="_self"  onclick="listPost('<%=userName %>',1)"><span class="test">收件箱(<%=count %>)</span></a></div>
												<div><img src="img/folder_folder.gif"/><a href="#" a target="_self"  onclick="listReceive('<%=userName %>',1)"><span class="test">发件箱</span></a></div>
												<div><img src="img/folder_folder.gif"/><a href="#" a target="_self"  onclick="postMsg()"><span class="test">发送新消息</span></a></div>
											
										</ul>
									</div>
									
									<div style="background-color: transparent;" id="split_bar" class="split_bar" onmouseover=""></div>
									<div id="bk_display" style="border: 1px solid #CECECE; overflow: auto; float: left; width: 517px; height: 600px;">
										
									</div>
									</div>
																	
								</div>
							</div>
							<!--xxx panel end####################################################-->
						</div> <!-- inner content -->
					</div><!-- panel content -->
					<!--content end####################################################-->
					
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
		<div id="cover" style="display:none;z-index:1;FILTER: alpha(opacity=70); POSITION: absolute;width:1280px;height:1024px;background-color:#000000 ;top:0px;left:0px" >
	</body>
	<%} %>
</html>