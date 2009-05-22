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
	<div id="right_side">
	<!--start of the main content of the right side-->
		<% 
		
			
			if(request.getAttribute("username") == null){
				
		%>
					<!--block no.1|starts|-->
					<%@ include file="activeuser.jsp" %>
					<!--block no.1|ends|-->
					
		<%} else{%>
					<!--block no.1|starts|-->
					<%@ include file="userPanel.jsp" %>
					<!--block no.1|ends|-->
		<% } %>
					<!--block no.2|starts|-->
					<%@ include file="hottags.jsp" %>
					<!--block no.2|ends|-->
					
					<!--block no.3|starts|-->					
					<%@ include file="help_include.jsp" %>						
					<!--block no.3|ends|-->
					
					<!--end of the main content of the right side-->
</div>