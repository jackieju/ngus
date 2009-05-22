
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/myWeb.tld" prefix="myWeb"%>
<%@ page import="com.ngus.myweb.services.MyWebResService"%>
<%@ page import="com.ngus.myweb.dataobject.MyWebRes"%>
<%@ page import="com.ngus.myweb.dataobject.BookMark"%>
<%@ page import="java.util.*"%>
<%@ page import="com.ngus.comment.*"%>
<%@ page import="java.io.*" %>
<%@ page import ="com.ns.exception.NSException" %>

<%! int currPage =1 ; %>
<%! int prePage =1 ; %>
<%! static long time ; %>
<% 
	//=========================添加评论===================
	
			
   			String userName = (String)session.getAttribute("userName");
   			String soldtime = request.getParameter("flag");
   			long oldtime=0;
   			if(soldtime!=null&&soldtime!=""){
   				 oldtime = Long.parseLong(soldtime);
   			}
   			if(time < oldtime){
   				time = oldtime;
   			}
   			else{
   				time++;
   			}
   			  
   			
   			
   			
			String id = request.getParameter("id");
			
			//response.getWriter().println(id.length());
			//response.getWriter().println(id);
			String name = null;
			String url = null;
			String type = null;
			String description = null;
			String tags = null;
			String des1=null;
			String des=null;
			try {
				BookMark res = (BookMark) MyWebResService.instance()
						.getInstanceByID(id);
				
				name = res.getName();
				url = res.getURL();
				type = res.getRtype();
				description = res.getDescription();
				des1=description.replace("\n","<br>");
				des=des1.replace(" ","&nbsp");
				tags = "";
				Iterator iter = res.getTags().iterator();
				while(iter.hasNext()){
					tags+=iter.next()+" ";
				}				
			} catch (Exception e) {

			}
			
			String  oldcontent = request.getParameter("content1" );
			if (oldcontent!=null&&oldcontent!=""&&time==oldtime){
				try {
					String content1 = oldcontent.replace("\n","<br>");
					String content = content1.replace(" ","&nbsp");
					//response.getWriter().println("id="+id);
					CommentEngine.instance().addComment(content,id,userName);
				} catch (NSException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				} 
			}
			//=============================处理comment list的分页=========================
			
			
			int pagesize=10; //分页单位
			
			int totalpage; //总页数
			
			int totalnum; //总评论数
			
			String strPage;
			
			strPage=request.getParameter("page"); 
			
			//获得当前页位置
			if(strPage==null||strPage==""){
				currPage=1;
			}
			else{
				try{
					currPage=Integer.parseInt(strPage);
					
					if ( currPage < 1)
						currPage=1;
						
					prePage = currPage;
				}catch(NumberFormatException e){
					currPage = prePage;
					%>
					<script language=javascript>   
						alert('请输入数字,谢谢!!'); 
					</script>
					<%
				}
				
			}
			//获得总评论数
			totalnum = CommentEngine.instance().getResTotalnum(id);
			
			
			//获得总页面数 
			totalpage = (int)Math.ceil((totalnum + pagesize-1) / pagesize); 	
			System.out.println("totalpage="+totalpage);
			if (currPage>totalpage) currPage=totalpage; 
			
			int pagestart =(currPage-1)*pagesize; //定位每页开始的评论项
			
			int i=0;
			
			System.out.println("i="+i);
			System.out.println("pagestart="+pagestart);
			System.out.println("currPage="+currPage);
			
			List<CommentObject> lcomObj = null; 
			try{
				
				lcomObj = CommentEngine.instance().listCommentsForRes( id ,pagestart ,pagesize );
			}
		 	catch (NSException e) {
		 		e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
			
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
<div class="leftbox">
<bean:parameter id="arg1" name="parentID" value="" />
<br />

<table width="100%" border="0" cellpadding="5" cellspacing="0"
	align="center">
	<tr>
		<td width="100%" valign="top" style="BORDER: #e6e8df 5px solid"
			bgcolor="#ffffff" align="left"><code>详细信息</code> <br />
		<input type="hidden" name="id" id="id"
			value="<bean:write name="arg1" />" /> <code>标题: <%=name%></code> <br />
		<br />
		<code>地址: <a href="<%=url%>"><%=url%></a></code> <br />
		<br />
		<code>类型: <%=type%></code> <br />
		<br />
		<code>描述: </code>
		<%=des%> <br />
		<br />
		<code>Tags: </code>
		<%=tags%> <br />
		</td>
	</tr>
</table>

<table width="100%" border="0" cellpadding="5" cellspacing="0"
	align="center">
	<span style="font-size:15px; font-weight:700"> 评论:</span>
	<%
	if(pagestart<0){
		%>
		<tr>
		<td>
		 目前暂无评论
		 </td></tr>
		
		
	<% 
	}else{
		%>
	<%
	while(i<(totalnum - 10*(currPage-1) )&& i<=9 ){
		String resContent = lcomObj.get(i).getContent();
		String content2 = new String(resContent.getBytes("ISO8859_1"),"UTF-8");
	%>
	
	<tr>
		<td width="100%" valign="top" style="BORDER: #e6e8df 5px solid"
			bgcolor="#ffffff" align="left">
		<code >姓名:<%=lcomObj.get(i).getUser()%></code> <br />
		<br />
		<code>内容:<br><%=content2%></code> <br />
		<br />
		<code>发表时间:<%=lcomObj.get(i).getCreateTime()%></code> <br />
		</td>
	</tr>
	<% 
	i++;}
		
	} 
	if(i>0){
		%>
	
	<tr>
	<td>
	<form name="pageChange" id="pageChange"  method="get" action="displaybookmark.jsp" >
		第<%=currPage%>页 共<%=totalpage%>页 共<%=totalnum%>条
		<%String id1 = id.replace("#","%23"); %>
		<%if(currPage>1){%><a href="displaybookmark.jsp?id=<%=id1%>&page=<%=1%>">首页</a><%}%>
		<%if(currPage>1){%><a href="displaybookmark.jsp?id=<%=id1%>&page=<%=currPage-1%>">上一页</a><%}%>
		<%if(currPage<totalpage){%><a href="displaybookmark.jsp?id=<%=id1%>&page=<%=currPage+1%>">下一页</a><%}%>
		<%if(totalpage>1&&currPage<totalpage){%><a href="displaybookmark.jsp?id=<%=id1%>&page=<%=totalpage%>">尾页</a><%}%>
		跳到<input type="text" name="page" id="page" size="4" style="font-size:9px">页
		<input name="id" value="<%=id%>" type="hidden" /> 
		<input type="submit" name="submit" id="submit" size="4" value="GO" style="font-size:9px" >
	</form>
	</td>
	</tr>
	<% }%>

	<tr>
	<td>
	<form name="addComment" method="get" action="displaybookmark.jsp" >
		<textarea name="content1" rows="10" cols="65" display="true" ></textarea>
		<br />
		<input name="flag" value="<%= System.currentTimeMillis() %>" type="hidden" />
		<input name="id" value="<%=id %>" type="hidden" />
		<input type="submit"  name="submit" value="添加评论" >
	</form> 
	</td>
	</tr>
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
