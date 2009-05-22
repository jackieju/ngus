<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ page import="com.ngus.myweb.services.MyWebResService"%>
<%@ page import="com.ngus.myweb.dataobject.MyWebRes"%>
<%@ page import="com.ngus.myweb.dataobject.BookMark"%>
<%@ page import="com.ngus.myweb.util.*"%>
<%@ page import="com.ngus.comment.*"%>
<%@ page import="com.ngus.myweb.vote.*"%>
<%@ page import ="com.ns.exception.NSException" %>
<%@ page import="java.util.*"%>
<%@ page import="java.net.*"%>
<%@ include file="checkSessionNoLink.jsp" %>
<%! int currPage =1 ; %>
<%! int prePage =1 ; %>
<%! static long time ; %>
<%
	List<String> poptags = MyWebResService.instance().mostPopularTag(20);
	List<IUser> newUser = UMClient.getNewUser(10);
	if(!poptags.isEmpty())
		request.setAttribute("poptags",poptags);
	if(!newUser.isEmpty())
		request.setAttribute("newUser",newUser);
	
	
	String id = request.getParameter("id");
	String idUrl = id.replace("#","%23");
	int vote = 0;
	BookMark res = null;
	try {
		//String id = ParamUtils.getParameter(request, "id");
		res = (BookMark) MyWebResService.instance().getInstanceByID(id);	
		vote = VoteService.viewVote(res.getURL());
	} catch (Exception e) {
		e.printStackTrace();
		//response.sendRedirect("error.jsp");
		//TODO
		out.write("Error Loading BookMark");
		out.flush();
		return;
	}
	request.setAttribute("bookmark", res);
	
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
			
		}
		
	}
	//获得总评论数
	totalnum = CommentEngine.instance().getResTotalnum(id);
	
	
	//获得总页面数 
	totalpage = (int)Math.ceil((totalnum + pagesize-1) / pagesize); 	
	
	if (currPage>totalpage) currPage=totalpage; 
	
	int pagestart =(currPage-1)*pagesize; //定位每页开始的评论项
	
	int n=0;
	
	List<CommentObject> lcomObj = null; 
	try{
		lcomObj = CommentEngine.instance().listCommentsForRes( id ,pagestart,pagesize );
	}
 	catch (NSException e) {
 		e.printStackTrace();
	} catch (Exception e) {
		e.printStackTrace();
	}
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

	<head>
		<meta http-equiv="content-type" content="text/html;charset=utf-8" />
		<meta name="generator" content="Adobe GoLive" />
		<title>monweb.com-bookmark</title>
		<link href="css/style.css" rel="stylesheet" type="text/css" media="all" />
		<script type="text/javascript" src="javascript/prototype.js"></script>
		<script type="text/javascript" src="javascript/jsTree.js"></script>
		<script type="text/javascript" src="javascript/main.js"></script>
		<script type="text/javascript" src="javascript/menu.js"></script>
		<script type="text/javascript" src="javascript/page.js"></script>
		<script type="text/javascript" src="javascript/frame.js"></script>
		<script type="text/javascript">
	function delBookmark(id){
	var url = "delete.do";		
	var pars = "id="+id;

	var myAjax = new Ajax.Request(
		url, 
		{
			method: 'post',
			parameters: pars, 
			onLoading: loading.bind(this),
			onComplete: loadComplete.bind(this)
		});
	function loading(){
		//Element.update("bk_display", "<img src='img/loading.gif' width='13px'/> Loading....");
	}
	function loadComplete(originalRequest){
	alert("delete sueceded");
	}
}
			function vote(id){
				var url = "vote.do";
				var pars = "url="+id;
				var myAjax = new Ajax.Request(
					url, 
					{
						method: 'post',
						parameters: pars,
						onComplete: loadComplete.bind(this)
					});
				function loadComplete(originalRequest){
					//var tmp = ;
					Element.update("vote_number", originalRequest.responseText);
				}		
			}
			
			function changePage(){
				var id = document.getElementById('id').value;
			    var page = document.getElementById('page').value;
				window.location.href="bookmark.jsp?id="+id+"&page="+page;
			}
			
			function changePage2(){
				var id = document.getElementById('id').value;
			    var page = document.getElementById('page2').value;
				window.location.href="bookmark.jsp?id="+id+"&page="+page;
			}
		</script>
	</head>

	<body>
		<div id="mainwrap">
			<div>
			<!--start of the body-->
			<%@ include file="header.jsp" %>
			
			
			<br />
			<div id="content">
				<!--start of the main content-->
				<div id="main">
					<div id="inner_l_main">
					<!--start of the main of left area-->
						<%@ include file="search_form.jsp" %> 
						 <div class="content_main">
								<div class="inner_content_main">
									<div class="bookmark_detail">
										<div class="search-result">
											<div><span class="font07"><bean:write name="bookmark" property="name"/></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="font06">有20人收藏了这个书签</span></div>
											<div class="search-result-url"><a href="<bean:write name="bookmark" property="URL"/>"><bean:write name="bookmark" property="URL"/></a></div>													
											<div class="search-result-description"><bean:write name="bookmark" property="description"/></div>
											<div class="search-result-tag">
												<span class="font03">TAG:</span>
												<logic:iterate id="tags" name="bookmark" property="tags">
													<a href="#"><bean:write name="tags"/></a> |
												</logic:iterate>
											</div>													
										</div>	
										<div class="bookmark-operate">
											<div id="digg" onclick="vote('<%=URLEncoder.encode(res.getURL() ,"UTF-8") %>')"><a id="vote_number" href="#">有<%=vote%>人顶过了</a></div>
											
											<!--  alert("<%=res.getRO().getUser() %>, <%=session.getAttribute("userId") %>");-->
											<%				
											
											if (res.getRO().getUser().equalsIgnoreCase((String)(session.getAttribute("userId")))){ %>
											<a href="#" onclick="delBookmark('<%=res.getID() %>')" >删除</a>
											<%} %>
											<!-- <input type="image" src="images/digit.gif"  />-->
										</div>
									</div>									
								</div>
						  </div>							
							<div class="separateline"></div>
							<!--reviews starts here-->
							
							<div class="page_list">
								<%
									if(currPage<=1){
								%>
								<img src="images/pre_arrow_grey.gif" align="absmiddle" />
								<%
									}else{
								%>
								<a href="bookmark.jsp?id=<%=idUrl%>&page=<%=currPage-1%>"><img src="images/pre_arrow.gif" align="absmiddle" /></a>
								<%
									}
								%>
								&nbsp;<%=currPage %>/<%=totalpage %>&nbsp;
								<%
									if(currPage>=totalpage){
								%>
								<img src="images/next_arrow_grey.gif" align="absmiddle" />
								<%
									}else{
								%>
								<a href="bookmark.jsp?id=<%=idUrl%>&page=<%=currPage+1%>"><img src="images/next_arrow.gif" align="absmiddle" /></a>
								<%
									}
								%>
								<input type="hidden" id="id" value="<%=idUrl%>"/>
								&nbsp;到<input type="text" id="page" class="text" style="width:20px"/>页&nbsp;<input type="button" value="GO" class="page_hop" onclick="changePage()"/>
							</div>
							<div class="reviews_body">
							<%
								if(pagestart<0){
							%>
								<div class="rev_outline">
									<div class="rev_content">
										<div class="topic_content">
											目前暂无评论
										</div>
									</div>
								</div>
							<%
								}else{
									while(n<(totalnum - 10*(currPage-1) )&& n<=9 ){
										String resContent = lcomObj.get(n).getContent();
										String UTF8Content = new String(resContent.getBytes("ISO8859_1"),"UTF-8");
							%>
								<div class="rev_outline">
									<div class="user_pic"><img width=50 height=50 src="userPic?userName=<%=lcomObj.get(n).getUser()%>" alt="<%=lcomObj.get(n).getUser()%>" border="0" /></div>							
									<div class="rev_content">
										<a href="#"><%=lcomObj.get(n).getUser()%></a>&nbsp;&nbsp;<span class="font06">发表于<%=lcomObj.get(n).getCreateTime()%></span><br />
										<div class="topic_content">&nbsp;&nbsp;&nbsp;&nbsp;<%=UTF8Content%>
										</div>
									</div>
								</div>
							
						   		<!--a piece of review body-->
						   		<div class="clear"></div>
						   	<% 									
										n++;
						   			}
								}
								
							%>
						   </div>	
						   					
							<div class="page_list">
								<%
									if(currPage<=1){
								%>
								<img src="images/pre_arrow_grey.gif" align="absmiddle" />
								<%
									}else{
								%>
								<a href="bookmark.jsp?id=<%=idUrl%>&page=<%=currPage-1%>"><img src="images/pre_arrow.gif" align="absmiddle" /></a>
								<%
									}
								%>
								&nbsp;<%=currPage %>/<%=totalpage %>&nbsp;
								<%
									if(currPage>=totalpage){
								%>
								<img src="images/next_arrow_grey.gif" align="absmiddle" />
								<%
									}else{
								%>
								<a href="bookmark.jsp?id=<%=idUrl%>&page=<%=currPage+1%>"><img src="images/next_arrow.gif" align="absmiddle" /></a>
								<%
									}
								%>
								&nbsp;到<input id="page2" type="text" class="text" style="width:20px"/>页&nbsp;<input type="button" value="GO" class="page_hop" onclick="changePage2()"/>
							</div>
							<div class="input_review">
								<form name="addComment" method="get" action="bookmark.jsp">
									<textarea name="content1" rows="10" cols="65" display="true"></textarea>
									<br/>
									<input name="flag" value="<%= System.currentTimeMillis() %>" type="hidden" />
									<input name="id" value="<%=id %>" type="hidden" />
									<input type="submit"  name="submit" value="添加评论" />
								</form>
							</div>
					</div> 						
					<!--end of the main of left area-->
					</div>
				</div>
			
				<%@ include file="login.jsp" %>
				<!--end of the main content-->
			</div>
			
			<div class="clear">&nbsp;</div>
			<%@ include file="footer.jsp" %>
			<!--end of the body-->
			</div>
	</body>

</html>