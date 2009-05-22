<%@ page language="java"%>
<%@ page contentType="text/html; charset=gb2312" %>

<%@ page import="com.stockstar.buos.um.*" %>
<%
	long lRet;
	String SSSESSIONID = "";
	String ClientId = request.getRemoteAddr();		

	
	Cookie[] cookies = request.getCookies();
	Cookie cookie;
	for(int i=0; i<cookies.length; i++) {
	cookie = cookies[i];
	if (cookie.getName().compareTo("SSSESSIONID")==0){
		SSSESSIONID = cookie.getValue();
		}
	}

	//out.println(SSSESSIONID);
	if (SSSESSIONID.length()!=0){
		//out.println("Begin to logoff.");

		
		lRet = UMClient.UF_Logoff(SSSESSIONID,ClientId);
		//out.println("UMClient.UF_Logoff:" + lRet);
		if (lRet != 0)
		{
			out.println("UserLogoff error with code:" + lRet);
		}
		else
		{
			//delete Cookie
			Cookie userCookie = new Cookie("SSSESSIONID","");
			userCookie.setMaxAge(-1); // delete
			userCookie.setDomain(".stockstar.com");
			userCookie.setPath("/");
			response.addCookie(userCookie);

			out.println("Logoff successfully! Cookie Deleted");
		}
	}else{
		out.println("No Session Cookie Detected. Logoff Operation Cancelled.");
	}
%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META name="GENERATOR" content="IBM WebSphere Studio">
<TITLE>UM_Logoff Sample</TITLE>
<style>
	body,table,p,td{font-family:verdana;font-size:9pt;}
</style>
</HEAD>
<BODY>
<h3>UF_Logoff</h3>
<a href="log_in.jsp">·µ»ØµÇÂ¼Ò³Ãæ</a>
</BODY>
</HTML>
