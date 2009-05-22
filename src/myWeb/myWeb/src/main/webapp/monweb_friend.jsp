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
<%@ include file="checkSession.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<% 
	int number = ParamUtils.getIntParameter(request, "number", 10);
	int pg = ParamUtils.getIntParameter(request, "page", 1);
	Attribute count = new Attribute("name", new Integer(0));
	int total_pages = 0;
	Iterator iter = FriendService.listFriend(UserManager.getCurrentUser(), (pg-1)*number, number, count);
	total_pages = (int) Math.ceil(Double
			.parseDouble(count.getStringValue())
			/ number);

	request.setAttribute("userList", iter);
	request.setAttribute("page", page);
	request.setAttribute("total_pages", total_pages);
	request.setAttribute("count", count.getValue());
%>
<html xmlns="http://www.w3.org/1999/xhtml">

	<head>
		<meta http-equiv="content-type" content="text/html;charset=utf-8" />
		<meta name="generator" content="Adobe GoLive" />
		<title>monweb.com-用户好友</title>
		<link href="css/style.css" rel="stylesheet" type="text/css" media="all" />
	</head>

	<script type="text/javascript">
		function changePage(){
				var id = document.getElementById('id').value;
			    var page = document.getElementById('page').value;
				window.location.href="monweb_friend.jsp?page="+page;
			}
			
			function changePage2(){
				var id = document.getElementById('id').value;
			    var page = document.getElementById('page2').value;
				window.location.href="monweb_friend.jsp?page="+page;
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
					<%@ include file="search_form.jsp" %> 
					<div class="SR-title"><div>共有<span class="font03"><bean:write name="count"/></span>个好友</div></div>
					<!-- body of one piece of results||starts||-->
					<div class="page_list01">
								<%
									if(pg<=1){
								%>
								<img src="images/pre_arrow_grey.gif" align="absmiddle" />
								<%
									}else{
								%>
								<a href="monweb_friend.jsp?page=<%=pg-1%>"><img src="images/pre_arrow.gif" align="absmiddle" /></a>
								<%
									}
								%>
								&nbsp;<%=pg %>/<%=total_pages %>&nbsp;
								<%
									if(pg>=total_pages){
								%>
								<img src="images/next_arrow_grey.gif" align="absmiddle" />
								<%
									}else{
								%>
								<a href="monweb_friend.jsp?page=<%=pg+1%>"><img src="images/next_arrow.gif" align="absmiddle" /></a>
								<%
									}
								%>
								
								&nbsp;到<input type="text" size="2" class="text" />页&nbsp;<input type="button" value="GO" class="page_hop" />
					</div>
					<div class="content-panel">
						<div class="content-panel-content">
							<div id="bk_display" class="result_list">
									<ul>										
										<logic:iterate id="user" name="userList">
										<li>
											<div class="user_search_result">
												<div class="user_p_item"><img src="userPic?userName=<bean:write name='user' property='userName'/>" width=50 height=50/></div>
												<div class="user_t_item">
													<div><span class="font04"><bean:write name="user" property="userName" /></span></div>
													<div><a href="#"><img src="images/samsg_icon.gif" />&nbsp;发送邮件</a></div>
												</div>
											</div>
										</li>
										</logic:iterate>
									
										
									</ul>
								</div>
							</div>
							
						</div>
						<div class="page_list01">
								<%
									if(pg<=1){
								%>
								<img src="images/pre_arrow_grey.gif" align="absmiddle" />
								<%
									}else{
								%>
								<a href="monweb_friend.jsp?page=<%=pg-1%>"><img src="images/pre_arrow.gif" align="absmiddle" /></a>
								<%
									}
								%>
								&nbsp;<%=pg %>/<%=total_pages %>&nbsp;
								<%
									if(pg>=total_pages){
								%>
								<img src="images/next_arrow_grey.gif" align="absmiddle" />
								<%
									}else{
								%>
								<a href="monweb_friend.jsp?page=<%=pg+1%>"><img src="images/next_arrow.gif" align="absmiddle" /></a>
								<%
									}
								%>
								
								&nbsp;到<input type="text" size="2" class="text" />页&nbsp;<input type="button" value="GO" class="page_hop" />
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