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
<%@ include file="webos_checkSession.jsp" %>
<% 
	response.setHeader("Cache-Control","no-cache");
	response.setHeader("Pragma","no-cache");
	List<String> poptags = MyWebResService.instance().mostPopularTag(20);
	List<IUser> newUser = UMClient.getNewUser(10);
	List<BookMark> bk = new ArrayList<BookMark>();
	List<Folder> fd = new ArrayList<Folder>();
	Iterator<IUser> iter = null;
	try{
		List<MyWebRes> ob = MyWebResService.instance().getByType("rss", 0, 10);
		for(MyWebRes tmp : ob){
			if(tmp instanceof BookMark){
				bk.add((BookMark)tmp);
			}
			else{
				fd.add((Folder)tmp);
			}
		}		
	}
	catch(Exception e){
		e.printStackTrace();
	}
	if(!fd.isEmpty())
		request.setAttribute("fd",fd);
	if(!bk.isEmpty())
		request.setAttribute("bk",bk);
	if(!poptags.isEmpty())
		request.setAttribute("poptags",poptags);
	if(!newUser.isEmpty())
		request.setAttribute("newUser",newUser);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

	<head>
		<meta http-equiv="content-type" content="text/html;charset=utf-8" />
		<meta name="generator" content="Adobe GoLive" />
		<title>monweb.com-RSS</title>
		<link href="css/style.css" rel="stylesheet" type="text/css" media="all" />
		<script type="text/javascript" src="javascript/prototype.js"></script>
		<script type="text/javascript" src="javascript/main.js"></script>
		<script type="text/javascript" src="javascript/page.js"></script>
		<script type="text/javascript" src="javascript/frame.js"></script>
		<script type="text/javascript">
			function loadrss(uri){				
				var url = "LoadRss";
				var pars = "url="+uri;
				var myAjax = new Ajax.Request(
					url, 
					{
						method: 'post',
						parameters: pars,
						onLoading: loading.bind(this),
						onComplete: loadComplete.bind(this)
					});
				function loading(){
					Element.update("bk_display", "<img src='img/loading.gif' width='13px'/> Loading....");
				}
				function loadComplete(originalRequest){
					//var tmp = originalRequest.responseText;
					Element.update("bk_display", originalRequest.responseText);
				}		
			}
		</script>
	</head>

	<body>
		<div id="webos_mainwrap">
			<div>
			<!--start of the body-->
            <!-- now empty here -->			
			
			<br />
			<div id="webos_content">
				<!--start of the main content-->
				<div id="main">
					<div id="inner_l_main">
					<!--start of the main of left area-->
					<div style="border-top:1px solid #66CCFF;"></div>
					
					<form class="search_form" method="post" action="webossearch">
						<input type="text" class="text" name="key" />
						<select name="type">
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
								<div class="directory_rss">
									<ul id = "tree">
										<logic:present name="bk" scope="request">
										<logic:iterate id="bookmark" name="bk">																						
											<li><a href="#" onclick="loadrss('<bean:write name="bookmark" property="URL" />')"><span class="test"><bean:write name="bookmark" property="name"/></span></a>
										</logic:iterate>
										</logic:present>										
									</ul>
								</div>
								<div style="background-color: transparent;" id="split_bar" class="split_bar" onmouseover=""></div>
								<div id="bk_display" class="content_list">								
								</div>
							</div>																
						</div>
					</div>
					<!--end of the main of left area-->
					</div>
				</div>
		
				<!--end of the main content-->
			</div>
			
			<!--end of the body-->
			</div>
		</div>
	</body>

</html>