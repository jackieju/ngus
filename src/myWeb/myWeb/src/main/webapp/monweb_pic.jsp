<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ page import="com.ngus.myweb.services.*"%>
<%@ page import="com.ngus.myweb.webservices.*"%>
<%@ page import="com.ngus.myweb.friend.*"%>
<%@ page import="com.ngus.um.http.*" %>
<%@ page import="com.ngus.um.*" %>
<%@ page import="com.ngus.myweb.dataobject.*"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Iterator"%>
<%@ include file="checkSession.jsp" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<% 
	
	response.setHeader("Cache-Control","no-cache");
	response.setHeader("Pragma","no-cache");
	
	WebServices service = new WebServices();
	String sessionId= (String)session.getAttribute("sessionid");
	String[] picArray = service.listUserTag(sessionId);
	
	request.setAttribute("picArray",picArray);
	
%>
<html xmlns="http://www.w3.org/1999/xhtml">

	<head>
		<meta http-equiv="content-type" content="text/html;charset=utf-8" />
		<meta name="generator" content="Adobe GoLive" />
		<title>monweb.com-images</title>
		<link href="css/style.css" rel="stylesheet" type="text/css" media="all" />

	
	<script type="text/javascript">
			window.onload = function(){}
			function DrawImage(max_height, max_width, e){
				var image=new Image();
				image.src=e.src;
				if (e.height>e.width){	
					if (e.height > 90)
						e.height=90;
				}else if(e.width>90){
                	e.width=90;
                }
				e.alt=image.width+"pt"+image.height;
			} 
			
			var http= getHTTPObject();
			
			function response(){		
				if(http.readyState ==4 ){			
					if(http.status == 200){	
						document.getElementById("bk_display").innerHTML=http.responseText;		
					}
				}
				else{
					document.getElementById("bk_display").innerHTML="<img src='img/loading.gif'/>";
				}	
			}
			
			function listPic(tag,userId,currPage){
				var url="ListPic?tag="+tag;				
				url += "&currPage=" +currPage;
				url += "&userId="+userId;
				url= encodeURI(url);
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
	</script>
	
	</head>
	<body>
		
		<div id="mainwrap">
			<div>
			<!--start of the body-->
			<div id="header">
				<!--start of the head area-->
				<div class="logo"><div><a href="#" class="linklogo"></a></div></div>
				<div class="head-register"><a href="index.htm">首页</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href="download.jsp">下载</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href="monweb_QA.jsp">帮助</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href="logout.jsp">注销</a></div>	
				<!--end of the head area-->
			</div>
			
			
			<br />
			<div id="content">
				<!--start of the main content-->
				<div id="main">
					<div id="inner_l_main">
					<!--start of the main of left area-->
					<div>
						<ul class="nav">
							<li class="my_initial_tab_off"><a href="main.jsp"><img src="images/icon_bookmark.gif" />&nbsp;书签</a></li>
							<li class="my_tab_off"><a href="viewrss.jsp"><img src="images/icon_rss.gif" />&nbsp;RSS</a></li>
							<li class="my_tab_off"><a href="#"><img src="images/icon_video.gif" />&nbsp;视频</a></li>
							<li class="my_tab_on"><img src="images/icon_pic.gif" />&nbsp;图片</li>
							<li class="my_tab_off"><a href="download.jsp"><img src="images/icon_download.gif" />&nbsp;下载</a></li>
						</ul>
					</div>
					<div style="clear:both;"></div>
					<div style="border-top:1px solid #66CCFF;"></div>
					
					<form class="search_form" method="post" action="search">
									<input type="text" class="text" name="key" />
									<select type="name">
										<option value="fulltext">全文搜索</option>
										<option value="tag">搜标签</option>
										<option value="user">搜用户</option>
									</select>
									<input type="image" class="image" src="images/butn_search.gif" />
							</form>
					
					<!-- body of one piece of results||starts||-->
					<div class="content-panel">
						<div class="content-panel-content">
							<div >
								<div id="directory" class="directory" style="height:100%;float:left;width:100px !important;width:150px;text-align:left;padding-left:5px;line-height:150%;  white-space: nowrap;overflow:auto;">
									<div class="show_tags" >
									
										<div>
										<img src="img/folder_folder.gif" /><a href="#" onclick="listPic('', '<%=request.getSession().getAttribute("userid") %>',1)"><span>&nbsp;所有</span></a>
										</div>		
										<logic:present name="picArray" scope="request">
											<logic:iterate id="element" name="picArray">
												<div>
													<img src="img/folder_folder.gif" /><a href="#" onclick="listPic('<bean:write name='element'/>', '<%=request.getSession().getAttribute("userid") %>',1)"><span>&nbsp;<bean:write name="element" /></span></a>
												</div>
											</logic:iterate>
										</logic:present>
									</div>
								</div>
								<div style="background-color: transparent;" id="split_bar" class="split_bar" onmouseover=""></div>
								<div class="content_list">
									<div id="bk_display" class="inner_content_list" align="left">
									
									
									</div>
									<!--end of the list-->
								</div>
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
