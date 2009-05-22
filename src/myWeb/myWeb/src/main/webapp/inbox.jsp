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
<%@ include file="checkSession.jsp" %>
<head>
<script language="javascript">
	
	
</script>
</head>


<%	
	response.setHeader("Refresh","1");
	response.setHeader("Cache-Control","no-cache");
	response.setHeader("Pragma","no-cache");
	int newcount = MessageEngine.instance().getNotReadedCount(userName);
	System.out.println(newcount);
%>
<ul id="tree">
		<div><img src="img/folder_folder.gif"/><a href="#" a target="_self"  onclick="listPost('<%=userName %>',1)"><span class="test">收件箱(<%=newcount %>)</span></a></div>
		<div><img src="img/folder_folder.gif"/><a href="#" a target="_self"  onclick="listReceive('<%=userName %>',1)"><span class="test">发件箱</span></a></div>
		
		
		<div><img src="img/folder_folder.gif"/><a href="#" a target="_self"  onclick="postMsg()"><span class="test">发送新邮件</span></a></div>
		
		
</ul>