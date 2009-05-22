<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String validateCode1 = request.getParameter("validCode");
	String validateCode2 = (String)session.getAttribute("validateCode");
	if(!validateCode1.equals(validateCode2)){
		response.getWriter().write("1"); //1表示不正确
	}
	else{
		response.getWriter().write("0");
	}
	
%>
