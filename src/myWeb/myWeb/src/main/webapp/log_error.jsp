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
			�û�������Ϊ�գ�<a href="main.jsp">����</a>��¼
		<%} else if(log_error_level == 1){%>
			�û����������<a href="main.jsp">����</a>
		<%} else if(log_error_level == 2){%>
			��֤�벻ƥ�䣡<a href="main.jsp">����</a>
		<%} else if(log_error_level == 3){%>
			�û�������!<a href="main.jsp">����</a>
		<%} else if(log_error_level == 4){%>
			�ʼ�����ʧ�ܣ�<a href="main.jsp">����</a>
		<%} else if(log_error_level == 5){%>
			ͼƬ�ļ���С���ܳ���64KB��<a href="reg.jsp">����</a>
		<%} %>
	</body>
</html>