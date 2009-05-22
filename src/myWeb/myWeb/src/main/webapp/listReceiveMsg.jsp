<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ page import="com.ngus.myweb.services.MyWebResService"%>
<%@ page import="com.ngus.myweb.dataobject.*"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import ="com.ngus.message.*" %>
<%@ page import ="com.ngus.myweb.util.ParamUtils"%>
<%@ include file="checkSession.jsp" %>


<head>

</head>
<body>

<%	
	int newcount = MessageEngine.instance().getNotReadedCount(userName);
	String receiveId = userName;
	response.setHeader("Cache-Control","no-cache");
	response.setHeader("Pragma","no-cache");
	int totalNum = ParamUtils.getIntAttribute(request,"totalNum",0);
	int currPage = ParamUtils.getIntAttribute(request,"currPage",1);
	int totalPage = ParamUtils.getIntAttribute(request,"totalPage",1);
	
%>
<div id="message" >
	<% if (totalNum == 0){%>
		对不起，你的收件箱是空的。
	<%} %>


	
				<logic:iterate id="element" name="msgList" indexId="index">
				
				<%
					MessageObject message = (MessageObject)element;
					int id = message.getMessageId();
					String title = message.getTitle();
					
				%>
					<div class="search-result" >
							<div ><span >主题:</span><bean:write name="element" property="title" /><%if (!message.isReaded()){ %> <font color=red>未读</font> <%} %></div>
							<div ><span >发信人:</span><bean:write name="element" property="postUserId" /></div> 
						
							  <div ><a href="#" onclick="showMessage(<%=message.getMessageId() %>,'receive')"> 查看</a>  <a href="DeleteMessage?userShow=receive&messageId=<%=message.getMessageId() %>">删除</a> <a href="#" onclick="show(<%=message.getMessageId() %>,'<%=message.getTitle() %>');">回复</a> </div>
							<div ><span >接受时间:</span> <bean:write name="element" property="createTime" /></div>
						     
						    <div id="replyMessage" name="replyMessage" style=" padding:10px;display:none;Z-INDEX: 100;FILTER: alpha(opacity=0); POSITION: absolute; top:210px; left:450px; background-color:#EEF3F6 ;width:310px ; height:200px;border-style:solid;border-width:0pt; border-color:#EEF3F6;">
						     	<form action="/myWeb-web/reply.do" name="replyForm" method="GET">
						 			<div><span >回复消息:</span> <a href="#" onclick="hide()"><img src="img/close.jpg" style="border:0"/></a>
									</div>
									<br/>
									<span>回信标题:</span>
									<input type="text" name="title" id="title" size="42" />
									<br />
									<span>回信内容:</span>
									<br />
									&nbsp;<textarea name="content" rows="14" cols="38" style="overflow-y:hidden"></textarea>
									<br />
									<input type="hidden" name="id" id="messageId">
									
									<div align="right" style="padding:1px"><a href="#" onclick="document.getElementById('replyForm').reset();"><img src="img/cancel.jpg" style="border:0"/></a> 
									<a href="#" onclick="document.getElementById('replyForm').submit();"> <img src="img/send.jpg" style="border:0"/> </a> </div>
								</form>
							</div>
							
							<input type="hidden" name="messageId" id="messageId+<%=message.getMessageId()%>" value="<%=message.getMessageId()%>"/>
							
					</div>
					
					</logic:iterate>
					

</div>
<% if (totalNum != 0){%>
<span >
					<%if(currPage>1){ %><a onclick="listPost('<%=userName %>',<%=currPage-1 %>)" href="#">上一页</a><%} %>
					<%if(currPage<totalPage){ %><a onclick="listPost('<%=userName %>',<%=currPage+1 %>)" href="#">下一页</a><%} %>
					</span>
					<span align="right">当前第<%=currPage %>页， 总共<%=totalPage %>页</span>
<% } %>