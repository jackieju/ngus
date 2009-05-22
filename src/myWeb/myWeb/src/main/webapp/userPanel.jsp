<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.ngus.myweb.userextension.UserExtensionService"%>
<%@ page import="java.util.List"%>
<%@ page import="com.ngus.um.http.*" %>
<%@ page import="com.ngus.um.*" %>
<%@ page import="com.ns.dataobject.Attribute"%>
<%@ page import="java.util.*"%>
<%@ page import="com.ngus.myweb.services.MyWebResService" %>
<%@ page import="com.ngus.myweb.friend.*"%>
		<%
					Attribute count1 = new Attribute("name", new Integer(0));
								
					Iterator iter1 = FriendService.listFriend(UserManager.getCurrentUser(), 0, 8, count1);
								
					request.setAttribute("userList", iter1);
								
	 %>			
		
				
					<!--user information starts-->
					<div class="sidebar-panel">
						<div class="sidebar-panel-title"></div>
						<div class="sidebar-panel-content">
							<div>
								<div class="user_id_pic"><img width=100  height=100 src="userPic?userName=<%=request.getAttribute("username")%>" /></div>
								<div class="user_id_content">
									<div><strong><%=(String)session.getAttribute("username") %></strong></div>
									<a href="monweb_personalResource.jsp">查看修改个人信息</a><br />
									<a href="monweb_changePassword.jsp">修改密码</a><br />
									<a href="monweb_message.jsp">我的信箱</a><br />
									<a href="logout.jsp">[退出]</a>
								</div>
								<div class="clear"></div>
							</div>	
						</div>						
					</div>
					<!--user information ends-->
					
					<!--block no.1|starts|-->
					<div class="sidebar-panel">
						<div class="sidebar-panel-title"><img src="images/star.gif" />&nbsp;我的好友</div>
						<div class="sidebar-panel-content">
						  	<logic:iterate id="element" name="userList" indexId="index">
						  		<div class="user">
						  			<logic:equal name="index" value="3">
						  				<div style="clear:left;"></div><!--每四个用户需要clear一次-->
						  			</logic:equal>
									<div class="user_pic"><a border=0 href="monweb_viewinfo.jsp?user=<bean:write name="element" property="userName" />" ><img width=50 height=50 src="userPic?userName=<bean:write name='element' property='userName'/>" /></a></div><!--程序控制 用户头像的宽高度应控制在50*50的大小, 以下均相同-->
									<div class="user_name"><a border=0 href="monweb_viewinfo.jsp?user=<bean:write name="element" property="userName" />" ><bean:write name="element" property="userName"/></a></div>
								</div>
						  	</logic:iterate>
							
							
							
							<div class="clear"></div>
							<div><a href="monweb_friend.jsp">查看全部&gt;&gt;</a></div>
							<div><form action="search?type=user" method="post"><input type="text" class="text" size="10" name="name"/><input type="submit" value="添加好友"/></form></div>						
						</div>						
					</div>
					<!--block no.1|ends|-->
					
					
					