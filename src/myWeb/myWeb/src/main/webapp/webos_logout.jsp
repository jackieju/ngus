<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="webos_checkSession.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	
	new UMClient().logoff(sessionId);
		session.invalidate();
	response.sendRedirect("webos_signin.html");
%>