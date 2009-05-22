<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.ngus.um.*, com.ngus.um.dbobject.*" %>
<%@ page import="com.ngus.message.*" %>
<%@ include file="checkSession.jsp" %>
<% 
	
	int messageId = Integer.parseInt(request.getParameter("id"));
	
	MessageEngine.instance().updateReaded(messageId);
	MessageObject message = MessageEngine.instance().searchMsg(messageId);
	
	String type = request.getParameter("type"); 
	String userName= (String)session.getAttribute("username");
%>
	<div><span>消息内容：</sapn><%=message.getContent() %></div>
	<%if(type.equals("receive")){ %><a href="#" onclick="listPost('<%=userName %>',1)">回到收件箱</a><%} %>
	<%if(type.equals("post")){ %><a href="#" onclick="listReceive('<%=userName %>',1)">回到收件箱</a><%} %>