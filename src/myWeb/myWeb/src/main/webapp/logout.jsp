<%@ page language="java" contentType="text/html; charset=GB18030"
    pageEncoding="GB18030"%>
<%@ page import="com.ngus.um.*, com.ngus.um.dbobject.*,com.ns.log.Log" %>
<%@ include file="checkSession.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	String sessionId=(String) session.getAttribute("sessionid");
	
	new UMClient().logoff(sessionId);
	session.invalidate();
	response.sendRedirect("");
%>