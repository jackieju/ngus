<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ page import="com.ngus.myweb.services.MyWebResService"%>
<%@ page import="com.ngus.myweb.friend.*"%>
<%@ page import="com.ngus.um.http.*" %>
<%@ page import="com.ngus.um.*" %>
<%@ page import="com.ns.dataobject.Attribute"%>
<%@ page import="com.ngus.myweb.dataobject.*"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.*"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="com.ngus.message.*"%>
<%@ page import="com.ngus.myweb.searchkey.SearchKeyService" %>
<%@ page import="com.ngus.myweb.friend.FriendService" %>
<%@ page import="com.ngus.um.dbobject.*,com.ngus.um.*" %>
<%@ include file="checkSession.jsp" %>


<% 
	String userName= (String) session.getAttribute("username");
	if(userName != null){
	response.setHeader("Cache-Control","no-cache");
	response.setHeader("Pragma","no-cache");
	List<String> poptags = MyWebResService.instance().mostPopularTag(20);
	List<IUser> newUser = UMClient.getNewUser(10);
	List<BookMark> bk = new ArrayList<BookMark>();
	List<Folder> fd = new ArrayList<Folder>();
	Iterator<IUser> iter = null;
	try{		
		List<ObjectTree> ob = MyWebResService.instance().listAll(1);		
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
	try{
		iter = FriendService.listFriend(UserManager.getCurrentUser());
	}
	catch(Exception e){
		//Log.error(e);
	}
	//String userName = (String) session.getAttribute("userName");
	
	
	
	if(iter != null){
		request.setAttribute("friendList", iter);
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
		<title>monweb.com-main.jsp</title>
		<script type="text/javascript" src="javascript/prototype.js"></script>
		<script type="text/javascript" src="javascript/jsTree.js"></script>
		<script type="text/javascript" src="javascript/main.js"></script>
		<script type="text/javascript" src="javascript/menu.js"></script>
		<script type="text/javascript" src="javascript/page.js"></script>
		<script type="text/javascript" src="javascript/frame.js"></script>
		<link href="css/style.css" rel="stylesheet" type="text/css" media="all" />		
		<link href="css/css.css" rel="stylesheet" type="text/css" />
        <script type="text/javascript">
            function showContent(id){
                document.getElementById(id).style.display="block";
            }
            function hideContent(id){
                document.getElementById(id).style.display="none";
            }
        </script>
	</head>

	<body>
	<script type="text/javascript">
			document.body.onclick = function(ev){
				$("contextMenu").style.display = "none";
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
							<li class="my_initial_tab_on"><img src="images/icon_bookmark.gif" />&nbsp;书签</li>
							<li class="my_tab_off"><a href="viewrss.jsp"><img src="images/icon_rss.gif" />&nbsp;RSS</a></li>
							<li class="my_tab_off"><a href="#"><img src="images/icon_video.gif" />&nbsp;视频</a></li>
							<li class="my_tab_off"><a href="monweb_pic.jsp"><img src="images/icon_pic.gif" />&nbsp;图片</a></li>
							<li class="my_tab_off"><a href="download.jsp"><img src="images/icon_download.gif" />&nbsp;下载</a></li>
						</ul>
					</div>
					<div style="clear:both;"></div>
					<div style="border-top:1px solid #66CCFF;"></div>
					
				<%@ include file="search_form.jsp" %> 
					
					<!-- body of one piece of results||starts||-->
					<div class="content-panel">
						<div class="content-panel-content">
							<div style="height: 600px;">
								<div style="float: left">
									<ul id="tree">
										<li id="" open="true"><a href="#"><span class="test">目录</span></a>
											<ul>	
												<logic:present name="fd" scope="request">
													<logic:iterate id="folder" name="fd">
														<li id="<bean:write name="folder" property="ID"/>"><a href="#"><span class="test"><bean:write name="folder" property="name"/></span></a></li>
													</logic:iterate>
												</logic:present>
											</ul>
										</li>
									</ul>
								</div>
								<div style="background-color: transparent;" id="split_bar" class="split_bar" onmouseover=""></div>
<% int tmpNumber=0; %>
								<div id="bk_display" class="content_list">
									<ul>
										<logic:present name="bk" scope="request">
											<logic:iterate id="bookmark" name="bk">
												<li>
													<div class="search-result" onmouseover="showContent('content<%= tmpNumber %>');" onmouseout="hideContent('content<%= tmpNumber %>');">
														<div class="search-result-title"><bean:write name="bookmark" property="title"/></div>
														<div id="content<%=tmpNumber %>" style="display:none;">
<% tmpNumber++;%>
														<div class="search-result-url"><a href="<bean:write name="bookmark" property="URL"/>" target='_blank'><bean:write name="bookmark" property="URL"/></a></div>
														<div class="search-result-description"><bean:write name="bookmark" property="description"/></div>
														<div class="search-result-tag">
															TAG:
															<logic:iterate id="tag" name="bookmark" property="tags">
																<a href="search?type=tag&key=<bean:write name="tag"/>"><bean:write name="tag"/></a> |
															</logic:iterate>
														</div>
														<div>
															<a href="#" onclick="editbookmark('<bean:write name="bookmark" property="ID" />');"><img src="images/edit.gif" />编辑</a>
															<a href="#" onclick="deletebookmark('<bean:write name="bookmark" property="ID" />');"><img src="images/del.gif" align="absmiddle" />删除</a>
														</div>
                                        	           </div>
													</div>
												</li>
											</logic:iterate>
										</logic:present>
									</ul>
								</div>
							</div>
							<script type="text/javascript">	
								treeObj = new jsTree(document.getElementById("tree"));											
							</script>										
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
	<ul id="contextMenu" style="display: none;">
	<li onmouseover="javascript: this.className='menuHighLighted';" onmouseout="javascript: this.className=null" onclick="javascript: addTest();"><span class="imgbox"><img src="img/contextmenu/add_bookmark.gif"></span><span class="text">添加书签</span></li>
	<li onmouseover="javascript: this.className='menuHighLighted';" onmouseout="javascript: this.className=null" onclick="javascript: addFolderTest();"><span class="imgbox"><img src="img/contextmenu/add_folder.gif"></span><span class="text">添加目录</span></li>
	<li id="deleteNode" onmouseover="javascript: this.className='menuHighLighted';" onmouseout="javascript: this.className=null" onclick="javascript: deleteFolder($('contextMenu').getAttribute('folderid'));"><span class="text">删除</span></li>
	<li onmouseover="javascript: this.className='menuHighLighted';" onmouseout="javascript: this.className=null"><span class="text">刷新</span></li>
	</ul>
	</body>
	<%} %>
</html>