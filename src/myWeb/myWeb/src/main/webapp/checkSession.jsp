<%@ page language="java" %>
<%@ page import="com.ngus.um.dbobject.*,com.ngus.um.*" %>
<%
	
	response.setHeader("Cache-Control","no-cache");
	response.setHeader("Pragma","no-cache");
	
	if (!new UMClient().checkHttpSession(request))
		response.sendRedirect("monweb_login.jsp");		
%>