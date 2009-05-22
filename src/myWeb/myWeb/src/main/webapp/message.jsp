<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ page import="com.ngus.myweb.services.MyWebResService"%>
<%@ page import="com.ngus.myweb.dataobject.*"%>
<%@ page import="com.ngus.message.*"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ include file="checkSession.jsp" %>
<% 
	String userName= (String)session.getAttribute("username");
	int count = MessageEngine.instance().getNotReadedCount(userName);
	List<BookMark> bk = new ArrayList<BookMark>();
	List<Folder> fd = new ArrayList<Folder>();
	try{
		List<ObjectTree> ob = MyWebResService.instance().listAll(1);
		bk = new ArrayList<BookMark>();
		fd = new ArrayList<Folder>();
		for(ObjectTree tmp : ob){
			if(tmp.getNode() instanceof BookMark){
				bk.add((BookMark)tmp.getNode());
			}
			else{
				fd.add((Folder)tmp.getNode());
			}
		}		
	}
	catch(Exception e){
		e.printStackTrace();
	}

	request.setAttribute("fd",fd);
	request.setAttribute("bk",bk);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<link rel="stylesheet" type="text/css" title="default" href="css/lite_blue.css" />
		<link href="css/css.css" rel="stylesheet" type="text/css"/>
		<title>MyWeb - 首页</title>
		
		
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
		
		<div class="wrapper">
			
			<div class="inner-wrapper">
				<!--header start####################################################-->
				<div  class="header">
					<div class="inner-header">
						<div class="header-top">
							<div  class="header-top-description"></div>
							<div id="test" class="header-top-link"><a href="#">设为首页</a> | <a href="#">下载</a></div>
						</div>
						<div class="header-main">
							<div class="header-logo"><a href="#"><img src="img/logo.gif" alt="MyWeb Logo" /></a></div>
							<div class="header-search">
								<form method="post" accept-charset="utf-8" action="search">
								<div class="header-search-type"><input type="radio" name="radSearchType" checked="checked" class="radio" value="fulltext"/>全文  <input type="radio" name="radSearchType" class="radio" value="tag"/>Tag  <input type="radio" name="radSearchType" class="radio" value="user"/>好友</div>								
								<div class="header-search-field"><input type="text" name="key" class="header-search-text"/><input type="submit" value=" 搜 索 " class="botton" /></div>
								</form>
								<div class="header-search-hint">
								热门搜索 
								<a href=# />股票<a>
								<a href=# />NBA<a>
								<a href=# />美女<a>
								<a href=# />电影<a>
								<a href=# />视频<a>
								<a href=# />奥运<a>
								<a href=# />情人节<a>
								
								
								</div>
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
								<div class="menu-tab-selected">我的资源</div>
								<div class="menu-tab-unselected"><a href="#">下 载</a></div>
								<div class="menu-tab-unselected"><a href="#">帮 助</a></div>
					</div>
					<div class="panel_content">
						<div class="inner-content" >							
							<!--xxx panel start####################################################-->
							<div class="content-panel">
								<div class="content-panel-title">我的资源</div>
								<div class="content-panel-content">
									<div style="height: 310px">
									<div id="inbox" style="float: left">
										<ul id="tree">
											<div><img src="img/folder_folder.gif"/><a href="#" a target="_self"  onclick="listPost('<%=userName %>',1)"><span class="test">收件箱(<%=count %>)</span></a></div>
											<div><img src="img/folder_folder.gif"/><a href="#" a target="_self"  onclick="listReceive('<%=userName %>',1)"><span class="test">发件箱</span></a></div>
											<div><img src="img/folder_folder.gif"/><a href="#" a target="_self"  onclick="postMsg()"><span class="test">发送新邮件</span></a></div>
													
												
												
											
										</ul>
									</div>
									<div id="split_bar" class="split_bar" onmouseover=""></div>
									<div id="bk_display" style="float: left; border: 1px solid gray; width: 562px; height: 312px; overflow:auto">
										<%-- <ul>
											<logic:iterate id="bookmark" name="bk">
												<li>
													<div class="search-result">
														<div class="search-result-title"><bean:write name="bookmark" property="title"/></div>
														<div class="search-result-url"><a href="<bean:write name="bookmark" property="URL"/> target='_blank'"><bean:write name="bookmark" property="URL"/></a></div>
														<div class="search-result-description"><bean:write name="bookmark" property="description"/></div>
														<div class="search-result-tag">
															<logic:iterate id="tags" name="bookmark" property="tags">
																<a href="#"><bean:write name="tags"/></a> |
															</logic:iterate>
														</div>
														<div>
															<a href="#" onclick="editbookmark('<bean:write name="bookmark" property="ID" />');">编辑</a>
															<a href="#" onclick="deletebookmark('<bean:write name="bookmark" property="ID" />');">删除</a>
														</div>	
													</div>
												</li>
											</logic:iterate>
										</ul>--%>
									</div>
									</div>
																		
								</div>
							</div>
							<!--xxx panel start####################################################-->
						</div> <!-- inner content -->
					</div><!-- panel content -->
					<!--content end####################################################-->
					<!--sidebar start####################################################-->
					<div class="sidebar">
						<div class="inner-sidebar">
							<!--user panel start####################################################-->
							<%-- <div class="sidebar-panel">
								<div class="sidebar-panel-title">登录</div>
								<div class="sidebar-panel-content">
									<div class="user-login">
										<div class="user-login-input"><div>用户名</div> <input type="text" /></div>
										<div class="user-login-input"><div>密码</div> <input type="password" /> <a href="#">忘记密码</a></div>
										<div class="user-login-submit"><a href="reg.jsp">立即注册！</a>&nbsp;&nbsp;&nbsp;<input type="submit" value=" 登 录 " />
										</div>
									</div>
								</div>
							</div>--%>
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
	<div id="cover" style="display:none;z-index:1;FILTER: alpha(opacity=70); POSITION: absolute;width:1280px;height:1024px;background-color:#000000 ;top:0px;left:0px" >
	</body>
	
</html>
