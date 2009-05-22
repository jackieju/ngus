<%@ page language="java" %>
<%@ page import="com.ngus.um.dbobject.*,com.ngus.um.*" %>
<%
	
	response.setHeader("Cache-Control","no-cache");
	response.setHeader("Pragma","no-cache");
	
	UMSession ums = null;
	String sessionId = null;
	String userName= null;
	Cookie[] cookies = request.getCookies();
	if(cookies!=null){
		Cookie cookie;
		for(int i=0; i<cookies.length; i++) {
			cookie = cookies[i];
			if (cookie.getName().compareTo("sessionId")==0){
				sessionId = cookie.getValue();
				
			}
		}
		
		if (sessionId!=null){
			try{
				ums = new UMClient().checkSession(sessionId);
				userName = ums.getUser().getUserName();
				request.getSession().setAttribute("username",userName);
				request.getSession().setAttribute("userid",ums.getUser().getSUserId());
				request.setAttribute("username",userName);
			}catch(Exception e){
				response.sendRedirect("webos_no_session.html");
			}
		}else{
			response.sendRedirect("webos_no_session.html");
		}
	}
	else{
		response.sendRedirect("webos_no_session.html");
	}
%>
