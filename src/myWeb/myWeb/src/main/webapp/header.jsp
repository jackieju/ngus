<div id="header">
				<!--start of the head area-->
				<div class="logo"><div><a href="#" class="linklogo"></a></div></div>
				<%if(session.getAttribute("userid") ==null){ %>
				<div class="head-register"><a href="main.jsp">首页</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href="monweb_QA.jsp">帮助</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href="download.jsp">下载</a>&nbsp;|&nbsp;<a href="monweb_reg.jsp">注册</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href="monweb_login.jsp">登录</a></div>	
				<%}else{ %>
				<div class="head-register"><a href="index.htm">首页</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href="monweb_QA.jsp">帮助</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href="download.jsp">下载</a>&nbsp;|&nbsp;<a href="logout.jsp">[退出]</a></div>	
				<%} %>
				<!--end of the head area-->
</div>
