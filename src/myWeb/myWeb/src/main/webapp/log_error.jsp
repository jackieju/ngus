<%@ page language="java" contentType="text/html; charset=GB18030"
    pageEncoding="GB18030"%>
<%int log_error_level = Integer.parseInt(request.getParameter("log_error_level"));%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GB18030">
		<title>log erro new</title>
	</head>
	<body>
		<%if (log_error_level == 0) {%>
			用户名不能为空！<a href="main.jsp">返回</a>登录
		<%} else if(log_error_level == 1){%>
			用户名密码错误！<a href="main.jsp">返回</a>
		<%} else if(log_error_level == 2){%>
			验证码不匹配！<a href="main.jsp">返回</a>
		<%} else if(log_error_level == 3){%>
			用户不存在!<a href="main.jsp">返回</a>
		<%} else if(log_error_level == 4){%>
			邮件发送失败！<a href="main.jsp">返回</a>
		<%} else if(log_error_level == 5){%>
			图片文件大小不能超过64KB！<a href="reg.jsp">返回</a>
		<%} %>
	</body>
</html>