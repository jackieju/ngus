<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.ngus.um.*, com.ngus.um.dbobject.*" %>
<%@ page import="com.ngus.myweb.util.ParamUtils" %>
<% 
	response.setHeader("Cache-Control","no-cache");
	response.setHeader("Pragma","no-cache");
	String penName = ParamUtils.getParameter(request,"penName");
	
	
	int ret = new UMClient().checkDuplicate(null, penName, null);
	if(ret == -4){
		response.getWriter().write("1");
	}
	else{
		response.getWriter().write("0");
	}
	
%>
