<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/myWeb.tld" prefix="myWeb"%>
<%@ include file="checkSession.jsp" %>
<% 
	String type = request.getParameter("type");
	String number = request.getParameter("number");
	int pg;
	try{
		pg = Integer.parseInt(request.getParameter("page"));
	}
	catch(Exception e){
		pg = 1;
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html lang="zh" xml:lang="zh" xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html;charset=utf-8" />
<meta name="generator" content="Adobe GoLive" />
<title>02</title>
<link href="css/basic.css" rel="stylesheet" type="text/css" media="all" />
<script type="text/javascript">
	var djConfig = {isDebug: true, debugAtAllCosts: true };
</script>
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
<div class="leftbox"><br />
<table width="90%" border="0" align="center" bgcolor="#f2f2f9">
	<tr>
		<td>名称</td>
		<td>地址</td>
		<td>描述</td>
	</tr>
	<myWeb:listByType type="<%=type%>"
	page="<%=pg%>"
	number="<%=request.getParameter("number")%>"/>	
</table>
</div>

<div class="rightbox"><br />
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
		<td align="left">在这里发现和管理你的音乐，好友和饭团！</td></tr>
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
		<td align="left" class="gt" colspan="2"></td>
	</tr>
	<tr style="border-bottom:1px dotted #808080;">
		<td align="left"><img src="#" /> night prayer</td>
		<td align="left"><img src="#" /> 某某</td>
	</tr>
	<tr style="border-bottom:1px dotted #808080;">
		<td align="left"><img src="#" /> night prayer</td>
		<td align="left"><img src="#" /> 某某</td>
	</tr>
	<tr style="border-bottom:1px dotted #808080;">
		<td align="left"><img src="#" /> night prayer</td>
		<td align="left"><img src="#" /> 某某</td>
	</tr>
	<tr style="border-bottom:1px dotted #808080;">
		<td align="left"><img src="#" /> night prayer</td>
		<td align="left"><img src="#" /> 某某</td>
	</tr>
	<tr style="border-bottom:1px dotted #808080;">
		<td align="left"><img src="#" /> night prayer</td>
		<td align="left"><img src="#" /> 某某</td>
	</tr>
	<tr style="border-bottom:1px dotted #808080;">
		<td align="left"><img src="#" /> night prayer</td>
		<td align="left"><img src="#" /> 某某</td>
	</tr>
</table>
</div>
<br />
<br />
<div class="footer">
<p align="left">本次冥想花了0.0045秒</p>
<br />
<table border="0" cellspacing="0" width="100%">
	<tr>
		<td width="40%" align="left">
		关于我们&nbsp;&nbsp;|&nbsp;&nbsp;隐私政策&nbsp;&nbsp;|&nbsp;&nbsp;使用条款&nbsp;&nbsp;|&nbsp;&nbsp;新闻
		</td>
		<td width="40%"><img src="#" />&nbsp;<img src="#" />&nbsp;<img src="#" />&nbsp;<img
			src="#" />&nbsp;<img src="#" />&nbsp;<img src="#" />&nbsp;<img
			src="#" />&nbsp;<img src="#" /></td>
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

</html>
