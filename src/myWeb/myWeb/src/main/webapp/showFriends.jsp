<%@ page language="java" contentType="text/html; charset=GB18030"
    pageEncoding="GB18030"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ page import="com.ngus.dataengine.DBConnection" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.Statement" %>
<%@ page import="java.sql.SQLException" %>
<%@ include file="checkSession.jsp" %>
<%
//setHeader sample
response.setHeader("Cache-Control","no-cache");
response.setHeader("Pragma","no-cache");
String penName = userPubInfo.getSPenName();
penName = penName.substring(0, penName.length()-1);
ResultSet rs1 = null;
ResultSet rs2 = null;
String friend = null;
int intPageSize = 10; //一页显示的记录数
int rowCount; //记录总数
int totalPage; //总页数
int currPage; //待显示页码
String strPage;
int i;
//取得待显示页码
strPage = request.getParameter("page");
if(strPage==null){//表明在QueryString中没有page这一个参数，此时显示第一页数据
	currPage = 1;
}else{//将字符串转换成整型
	currPage = Integer.parseInt(strPage);
	if(currPage<1) 
		currPage = 1;
}
String delFriend = request.getParameter("delPenName");

	
%>
<html:html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="css/basic.css" rel="stylesheet" type="text/css" media="all" />
</head>

<body>
<center>
<jsp:include page="headPage.jsp" />

<div class="boxwrap">
<table border="0" width="100%" style="border-bottom:1px solid #808080;">
	<tr>
		<td align="left"><img src="#" />&nbsp;<img src="#" />&nbsp;<img
			src="#" />&nbsp;<img src="#" />&nbsp;<img src="#" />&nbsp;<img
			src="#" /></td>
		<td align="right" valign="bottom"></td>
	</tr>
</table>
<div class="leftbox"><bean:parameter id="arg1" name="parentID" value="" />
<br />
<%
try {
	Connection con=DBConnection.getConnection();
	Statement st1 = con.createStatement();
	Statement st2 = con.createStatement();
	Statement st3 = con.createStatement();
	if(delFriend!=null)//删除我的一个好友，对方邀请我的或则我邀请对方的而成为的好友
		st3.executeUpdate("delete from friendlist where (user1='"+penName+"' and user2='"+delFriend+"') or (user2='"+penName+"' and user1='"+delFriend+"')");
	rs1 = st1.executeQuery("select user1,user2 from friendlist where status=1 and (user1='"+penName+"'or user2='"+penName+"')");
	rs1.last();
	rowCount = rs1.getRow();//获得当前行号，即总的纪录数
	totalPage = (rowCount+intPageSize-1) / intPageSize;//记算总页数
	rs1.first();
	if(currPage>totalPage) 	//调整待显示的页码
		currPage = totalPage;
	if(rowCount<=0){%>
		您暂时没有好友！
	<%}else{%>
		您现在的好友是：<br>
		<table width="100%" border="0" cellpadding="5" cellspacing="0" align="center">
				<tr>
					<th width="30%" valign="top" style="BORDER: #e6e8df 5px solid" bgcolor="#ffffff" align="left">笔名</th>
					<th width="30%" valign="top" style="BORDER: #e6e8df 5px solid" bgcolor="#ffffff" align="left">score</th>
					<th width="40%" valign="top" style="BORDER: #e6e8df 5px solid" bgcolor="#ffffff" align="left">最近登录时间</th>
					<th width="40%" valign="top" style="BORDER: #e6e8df 5px solid" bgcolor="#ffffff" align="left"></th>
				</tr>
				<%
				//将记录指针定位到待显示页的第一条记录上
				rs1.absolute((currPage-1) * intPageSize + 1);
				//显示数据
				i = 0;//当前要显示页的逻辑行号
				String friendsNewLogonTime = null;
				String friendsPenName = null;
				int friendsScore = 0;
				while(i<intPageSize && !rs1.isAfterLast()){				
					if(rs1.getString("user1").equalsIgnoreCase(penName))//get the friend's penName of the user
						friend = rs1.getString("user2");
					else
						friend = rs1.getString("user1");
					rs2 = st2.executeQuery("select penName,score,newLogonTime from userextension where penName='"+friend+"'");
					if(rs2.next()){
						friendsPenName = rs2.getString("penName");
						friendsScore = rs2.getInt("score");
						friendsNewLogonTime = rs2.getString("newLogonTime");	
					}		
					%>
					<tr>
						<td width="60%" valign="top" style="BORDER: #e6e8df 5px solid" bgcolor="#ffffff" align="left">		
							<%=friendsPenName%>
						</td>
						<td width="40%" valign="top" style="BORDER: #e6e8df 5px solid" bgcolor="#ffffff" align="left">		
							<%=friendsScore%>
						</td>
						<td width="40%" valign="top" style="BORDER: #e6e8df 5px solid" bgcolor="#ffffff" align="left">		
							<%=friendsNewLogonTime%>
						</td>
						<td width="40%" valign="top" style="BORDER: #e6e8df 5px solid" bgcolor="#ffffff" align="left">		
							<a href="showFriends.jsp?delPenName=<%=friendsPenName%>">删除</a>
						</td>
					</tr>
					<%i++;
					rs1.next();
				}%>
					<tr>
						<td>
							<form name="pageChange" id="pageChange"  method="get" action="showFriends.jsp" >
								第<%=currPage%>页 共<%=totalPage%>页 共<%=rowCount%>条
								<%if(currPage>1){%><a href="showFriends.jsp?page=<%=1%>">首页</a><%}%>
								<%if(currPage>1){%><a href="showFriends.jsp?page=<%=currPage-1%>">上一页</a><%}%>
								<%if(currPage<totalPage){%><a href="showFriends.jsp?page=<%=currPage+1%>">下一页</a><%}%>
								<%if(totalPage>1&&currPage<totalPage){%><a href="showFriends.jsp?page=<%=totalPage%>">尾页</a><%}%>
								跳到<input type="text" name="page" id="page" size="4" style="font-size:9px">页
								<input type="submit" name="submit" id="submit" size="4" value="GO" style="font-size:9px" >
							</form>
						</td>
					</tr>
				</table>
			<%}%>	
		<%}catch (SQLException e) {
		out.println(e);
	}	
%>
</div>

<div class="rightbox"> <br/>	
<table bgcolor="#eaeaea" border="0" cellpadding="5px" cellspacing="0"
	width="100%" >
	<tr>
		<td style="font-size:14px;" align="left"><strong><a href="showFriends.jsp">我的好友</a></strong></td>
	</tr>
	<tr>
		<td style="font-size:14px;" align="left"><strong><a href="listRecMsg.jsp">收到的消息</a></strong></td>
	</tr>
</table>
<table bgcolor="#eaeaea" border="0" cellpadding="5px" cellspacing="0"
	width="100%">
	<tr>
		<td style="font-size:14px;" align="left"><strong>最近更新</strong></td>
	</tr>
	<tr>
		<td align="left">
		<ul type="disc">
			<li align="left">九龙塘开始公测</li>
			<li align="left">九龙塘增加对RSS的支持</li>
			<li align="left">九龙塘目前已支持Firefox浏览器，如果碰到问题请到<a href="#">firefox问题与建议</a></li>
		</ul>
		</td>
	</tr>
</table>
<br />
<table width="100%" border="0" cellpadding="5px" bgcolor="#f2f2f9">
	<tr>
		<td class="rt" align="left">我要分享</td>
	</tr>
	<tr>
		<td align="left">独乐乐不如众乐乐，分享自己喜欢的收藏！</td>
	</tr>
	<tr>
		<td class="rt" align="left">进入我的九龙塘</td>
	</tr>
	<tr>
		<td align="left">在这里发现和管理你的音乐，好友和饭团！</td>
	</tr>
	<tr>
		<td class="rt" align="left">浏览收藏</td>
	</tr>
	<tr>
		<td align="left">察看分享你的收藏和最新评论</td>
	</tr>
	<tr>
		<td class="rt" align="left">浏览评论</td>
	</tr>
	<tr>
		<td align="left">参加你感兴趣的讨论，查看最新话题</td>
	</tr>
	<tr>
		<td align="left"><img src="#" />了解更多〉〉</td>
	</tr>
</table>
<br />
<table border="0" cellpadding="5px" cellspacing="0" width="100%"
	bgcolor="#f2f9f2">
	<tr>
		<td align="left" class="gt" colspan="2">本周最热评论</td>
	</tr>
	<tr style="border-bottom:1px dotted #808080;">
		<td align="left"><img src="#" /> night prayer</td>
		<td align="left"><img src="#" /> 某某人</td>
	</tr>
	<tr style="border-bottom:1px dotted #808080;">
		<td align="left"><img src="#" /> night prayer</td>
		<td align="left"><img src="#" /> 某某人</td>
	</tr>
	<tr style="border-bottom:1px dotted #808080;">
		<td align="left"><img src="#" /> night prayer</td>
		<td align="left"><img src="#" /> 某某人</td>
	</tr>
	<tr style="border-bottom:1px dotted #808080;">
		<td align="left"><img src="#" /> night prayer</td>
		<td align="left"><img src="#" /> 某某人</td>
	</tr>
	<tr style="border-bottom:1px dotted #808080;">
		<td align="left"><img src="#" /> night prayer</td>
		<td align="left"><img src="#" /> 某某人</td>
	</tr>
	<tr style="border-bottom:1px dotted #808080;">
		<td align="left"><img src="#" /> night prayer</td>
		<td align="left"><img src="#" /> 某某人</td>
	</tr>
</table>
</div>
<br>
<br>
<div class="footer">
<p align="left">本次冥想花了0.0045秒</p>
<br />
<table border="0" cellspacing="0" width="100%">
	<tr>
		<td width="40%" align="left">
		关于我们&nbsp;&nbsp;|&nbsp;&nbsp;隐私政策&nbsp;&nbsp;|&nbsp;&nbsp;使用条款&nbsp;&nbsp;|&nbsp;&nbsp;新闻
		</td>
		<td width="40%"><img src="#" />&nbsp;<img src="#" />&nbsp;<img
			src="#" />&nbsp;<img src="#" />&nbsp;<img src="#" />&nbsp;<img
			src="#" />&nbsp;<img src="#" />&nbsp;<img src="#" /></td>
		<td rowspan="2"><img src="#" /></td>
	</tr>
	<tr>
		<td colspan="2">copyrigth&copy; all rights reserved</td>
	</tr>
</table>
</div>
</div>
</center>
</body>
</html:html>