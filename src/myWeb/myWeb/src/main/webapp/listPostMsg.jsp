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
<%@ page import ="com.ngus.myweb.searchkey.*" %>
<%@ include file="checkSession.jsp" %>
<%	
	String postId = userName;
	response.setHeader("Cache-Control","no-cache");
	response.setHeader("Pragma","no-cache");
	int totalNum = ParamUtils.getIntAttribute(request,"totalNum",0);
	int currPage = ParamUtils.getIntAttribute(request,"currPage",1);
	int totalPage = ParamUtils.getIntAttribute(request,"totalPage",1);
	
	
%>

<div id="message">
<% if (totalNum == 0){%>
		对不起，你的发件箱是空的。
<%} %>

				<logic:iterate id="element" name="msgList" indexId="index">
				
					<% MessageObject message = (MessageObject)element; %>
					
							
							<div ><span >主题:</span> <bean:write name="element" property="title" /></div>
							<div ><span >收信人:</span> <bean:write name="element" property="receiveUserId" /></div>
							
							<div ><a href="#" onclick="showMessage(<%=message.getMessageId() %>,'post')"> 查看</a>  <a href="DeleteMessage?userShow=post&messageId=<%=message.getMessageId() %>">删除</a></div>
							
							<div ><span >发送时间:</span> <bean:write name="element" property="createTime" /></div>
		
							
					
					<br/><br/>
				</logic:iterate>
				
				
				

</div>
<% if (totalNum != 0){%>
<span >
					<%if(currPage>1){ %><a onclick="listReceive('<%=userName %>',<%=currPage-1 %>)" href="#">上一页</a><%} %>
					<%if(currPage<totalPage){ %><a onclick="listReceive('<%=userName %>',<%=currPage+1 %>)" href="#">下一页</a><%} %>
					</span>
				当前第<%=currPage %>页， 总共<%=totalPage %>页
<% } %>