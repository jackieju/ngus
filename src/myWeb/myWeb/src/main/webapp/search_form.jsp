<%@ page language="java" %>
	<%
	String keys = (String)request.getAttribute("key");
	if (keys == null)
		keys = "";
	
	String byUser = request.getParameter("byUser");
	boolean bByUser = false;
    if ("true".equals(byUser) || "on".equals(byUser)) {
    	bByUser = true;
    }
    else if ("false".equals(byUser) || "off".equals(byUser)) {
    	bByUser = false;
    }
   
	%>
	<form class="search_form" method="post" action="search">
									<input type="text" class="text" name="key" value="<%=keys%>"/>
								    <input type="hidden" class="text" name="byUserId" value="<%=request.getAttribute("userid") %>"/>
									<select name="type">
										<option value="fulltext">全文搜索</option>
										<option value="tag">按标签</option>
										<option value="user">搜用户</option>
									</select>
									<input type="image" class="image" src="images/butn_search.gif" />
									<%if (bByUser){%>
										<input type="checkbox" checked name="byUser" >仅搜索我的收藏</input>
									<%}else{%>
										<input type="checkbox"  name="byUser" >仅搜索我的收藏</input>
									<%}%>
	</form>