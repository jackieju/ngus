<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

	<head>
		<meta http-equiv="content-type" content="text/html;charset=utf-8" />
		<meta name="description" content="在线收藏永不丢失 智能分类为网站，图片，视频， RSS 用最便捷的方式分享您的收藏，以最快的速度发现流行"/>
		<meta name="keywords" content="在线收藏 智能 分类 tag 标签 图片 视频 RSS 分享 流行"/>
		<title>monweb.cn - 在线智能网络收藏夹 最方便的客户端软件 最智能的分类 发现分享 永不丢失 </title>
		<link href="css/style.css" rel="stylesheet" type="text/css" media="all" />
		<script type="text/javascript" src="javascript/prototype.js"></script>
		<script type="text/javascript">
			function vote(id, index){
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
					Element.update("vote_number"+index, originalRequest.responseText+"个人顶过了");
				}		
			}
						
		</script>
	</head>

	<body>
		<div id="mainwrap">
			<div>
			<!--start of the body-->
			<div id="header">
				<!--start of the head area-->
				<div class="logo"><div><a href="#" class="linklogo"></a></div></div>
				<div class="head-register"><a href="webos_desktop.jsp">Monweb Desktop</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href="download.jsp">下载</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href="monweb_QA.jsp">帮助</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href="monweb_reg.jsp">注册</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href="monweb_login.jsp">登录 </a></div>	
				<!--end of the head area-->
			</div>
			
			
			<br />
			<div id="content">
				<!--start of the main content-->
				<div id="main">
					<div id="inner_l_main">
					<!--start of the main of left area-->
					<ul class="nav">
						<li class=<#if type = "webpage">"initial_tab_on"<#else>"tab_off"</#if>><a href="index.htm"><img src="images/icon_bookmark.gif" />&nbsp;书签</a></li>
						<li class=<#if type = "rss">"initial_tab_on"<#else>"tab_off"</#if>><a href="rss.htm"><img src="images/icon_rss.gif" />&nbsp;RSS</a></li>
						<li class=<#if type = "video">"initial_tab_on"<#else>"tab_off"</#if>><a href="video.htm"><img src="images/icon_video.gif" />&nbsp;视频</a></li>
						<li class=<#if type = "pic">"initial_tab_on"<#else>"tab_off"</#if>><a href="pic.htm"><img src="images/icon_pic.gif" />&nbsp;图片</a></li>
						<li class="tab_off"><a href="download.jsp"><img src="images/icon_download.gif" />&nbsp;下载</a></li>
					</ul>
					<div style="clear:both;"></div>
					<div style="border-top:1px solid #66CCFF;"></div>
					
					<div class="startbox">
						<div class="inner_startbox">
							<table cellspacing="2px;">
								<tr>
									<td class="introduction">
										<div class="intro_title"><img src="images/glass.gif" />&nbsp;在线收藏</div>
										<div style="padding: 0 5px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;您的收藏将永不丢失，您可以从世界任何一个角落的任何一体计算机访问您的收藏。</div>
									</td>
									<td class="introduction">
										<div class="intro_title"><img src="images/hand.gif" />&nbsp;智能分类</div>
										<div style="padding: 0 5px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;您的收藏将被自动划分为网站，图像，视频， RSS等类型供您用更适合的方式浏览。</div>
									</td>
									<td class="introduction">
										<div class="intro_title"><img src="images/share.gif" />&nbsp;分享发现</div>
										<div style="padding: 0 5px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;无论何时何地，您可以用最便捷的方式共享您的收藏，以最快的速度发现流行。</div>
									</td>
								</tr>
							</table>
							<form class="search_form" method="post" action="search">
									<input type="text" class="text" name="key" />
									<select name="type">
										<option value="fulltext">全文搜索</option>
										<option value="tag">搜标签</option>
										<option value="user">搜用户</option>
									</select>
									<input type="image" class="image" src="images/butn_search.gif" />
							</form>
						</div>
					</div>
					
					<!-- body of one piece of results||starts||-->
					<#list hot_list as hot_bookmark>
					<div class="result_display">
						<div class="pic_display">
							<a href="${hot_bookmark.url}"><img src="${hot_bookmark.snapshot}" width="130" /></a><!--程序控制，图片宽度不超过130px;-->
						</div>
						<div class="content_display">
							<span class="content_title"><a href="${hot_bookmark.url}">${hot_bookmark.name}</a></span>
							<br />
							by&nbsp;<span class="user"><a href="#">${hot_bookmark.userName}</a></span>&nbsp;添加于${hot_bookmark.createTime?string('yyyy年MM月dd日')}<br />
							<div class="content_description">
							${hot_bookmark.description}
							</div>
							<span class="tag"><img src="images/tag.gif" />&nbsp;tags</span>:
							<span>
								<#assign tags = hot_bookmark.tags>
								<#list tags as tag>
								<a href="search?type=tag&key=${tag}">${tag}</a><#if tag_has_next>&nbsp;|&nbsp</#if>
								</#list>
							</span>
							<div class="content_reviews"><img src="images/add.gif" />搜藏次数：${hot_bookmark.time}次 <a id="vote_number${hot_bookmark_index}" href="javascript:void(0)"><span id="digg" onclick="vote(escape('${hot_bookmark.url}'), ${hot_bookmark_index})">顶一下</span></a></div>							
							<a href='addbookmark.jsp?name=${hot_bookmark.name}&url=${hot_bookmark.url}' target='_blank'>添加</a>
						</div>
						<div class="clear"></div>
						<div class="patchbox"></div>
					</div>
					</#list>
					<div style="text-align:right"><a href="ListPopByType?type=${type}">更多..</a></div>
					<!-- body of one piece of results||ends||-->								
										
					<!-- body of one piece of results||starts||-->
					<!--
					<div class="result_display">
						<div class="pic_display">
							<a href="#"><img src="images/bookmark_sample01.gif" /></a><!--程序控制，图片宽度不超过130px;-->
					<!--</div>
						<div class="content_display">
							<span class="content_title"><a href="#">KINEDA.com </a></span>
							<br />
							&nbsp;by&nbsp;<span class="user"><a href="#">原子少女猫</a></span>&nbsp;添加于2007-01-10<br />
							<div class="content_description">时尚中心-最丰富最IN的时尚网站，服装搭配潮流尽显都市魅力，丰富图库，世界名牌资讯明星服饰展示搭配技巧.</div>
							<span class="tag"><img src="images/tag.gif" />&nbsp;tags</span>:
							<span>								
								<a href="#">时尚</a>&nbsp;|&nbsp;
								<a href="#">服饰</a>&nbsp;|&nbsp;
								<a href="#">化妆品</a><br />
							</span>
							<div class="content_reviews"><img src="images/add.gif" />搜藏次数：27次 &nbsp;|&nbsp;<img src="images/talk.gif" ?>评论：7</div>
						</div>
						<div class="clear"></div>
						<div class="patchbox"></div>
					</div>
					<!-- body of one piece of results||ends||-->
					
					<!-- hot pics |starts|-->
					
					<!-- hot pics |ends|-->					
					
					<!--end of the main of left area-->
					</div>
				</div>
			
				<div id="right_side">
					<!--start of the main content of the right side-->
					
					<!--block no.1|starts|-->
					<div class="sidebar-panel">
						<div class="sidebar-panel-title"><img src="images/star.gif" />&nbsp;最近活动用户</div>
						<div class="sidebar-panel-content">
							<#list hot_user_list as hot_user>
							<div class="user">
								<div class="user_pic"><a border=0 href="monweb_viewinfo.jsp?user=${hot_user.userName}"><img width="50" height="50" src="userPic?userName=${hot_user.userName}" /></a></div><!--程序控制 用户头像的宽高度应控制在50*50的大小, 以下均相同-->
								<div class="user_name"><a border=0 href="monweb_viewinfo.jsp?user=${hot_user.userName}">${hot_user.userName}</a></div>								
							</div>
							<#if hot_user_index%4 = 3 || !hot_user_has_next>
								<div style="clear:left;"></div><!--每四个用户需要clear一次-->
							</#if>
							</#list>
							<!--<div class="user">
								<div class="user_pic"><a href="#"><img src="images/user_sample02.gif" /></a></div>
								<div class="user_name"><a href="#">SASUKE</a></div>
							</div>
							<div class="user">
								<div class="user_pic"><a href="#"><img src="images/user_sample03.gif" /></a></div>
								<div class="user_name"><a href="#">飞行人生</a></div>
							</div>
							<div class="user">
								<div class="user_pic"><a href="#"><img src="images/user_sample04.gif" /></a></div>
								<div class="user_name"><a href="#">非理性存在</a></div>
							</div>
							<div style="clear:left;"></div>--><!--每四个用户需要clear一次-->
							<!--<div class="user">
								<div class="user_pic"><a href="#"><img src="images/user_sample05.gif" /></a></div>
								<div class="user_name"><a href="#">深眠的法老</a></div>
							</div>
							<div class="user">
								<div class="user_pic"><a href="#"><img src="images/user_sample06.gif" /></a></div>
								<div class="user_name"><a href="#">TIAK</a></div>
							</div>
							<div class="user">
								<div class="user_pic"><a href="#"><img src="images/user_sample07.gif" /></a></div>
								<div class="user_name"><a href="#">MINI猪</a></div>
							</div>

							<div class="clear"></div>						
							-->
						</div>						
					</div>
					<!--block no.1|ends|-->
					
					
					<!--block no.2|starts|-->
					<div class="sidebar-panel">
						<div class="sidebar-panel-title">热门标签&nbsp;|&nbsp;hot tags </div>
						<div class="sidebar-panel-content">
							<div class="show_tags"><!--程序统计访问数, 改变style中的font-weight 和font-size的值-->
								<#list hot_tags as tag>
									<a href="search?type=tag&key=${tag}"  style="">${tag}</a>&nbsp; 
								</#list>
								<!--
								 <a href="#" style="" >午夜</a>&nbsp;  
								 <a href="#" style="" >城市</a>&nbsp;  
								 <a href="#" style="" >旅行</a>&nbsp;  
								 <a href="#" style="" >江浙</a>&nbsp; 
								 <a href="#" style="" >苏州</a>&nbsp; 
								 <a href="#" style="" >本帮菜</a>&nbsp; 
								 <a href="#" style="" >咖啡</a>&nbsp;
								 <a href="#" style="" >啤酒</a>&nbsp;
								 <a href="#" style="" >西餐</a>&nbsp;  
								 <a href="#" style="" >日本菜</a>&nbsp;  
								 <a href="#" style="" >心情</a>&nbsp; 
								 <a href="#" style="" >放松</a>&nbsp;  
								 <a href="#" style="" >同学的婚礼</a>&nbsp;
								-->
							</div>
						</div>
					</div>
					<!--block no.2|ends|-->
					
					<!--block no.3|starts|-->
					<div class="sidebar-panel">
						<div class="sidebar-panel-title"><img src="images/star.gif" />&nbsp;Monweb 使用帮助</div>
						<div class="sidebar-panel-content">
							<div class="help">
								<ul>
									<li><a href="monweb_QA.jsp#1">如何在线分享我喜欢的网络收藏？</a></li>
									<li><a href="monweb_QA.jsp#2">如何搜索其他人的收藏?</a></li>
									<li><a href="download.jsp">如何使用monweb的客户端插件?</a></li>
								</ul>
								<div align="right"><a href="monweb_QA.jsp">更多>></a>&nbsp;&nbsp;</div>
							</div>
						</div>
					</div>
					<!--block no.3|ends|-->
					
					
					<!--block no.2|ends|-->
					<!--end of the main content of the right side-->
				</div>		
				<!--end of the main content-->
			</div>
			
			<div class="clear">&nbsp;</div>
			created on ${pageCreateTime} consume ${pageCostTime}
			<div id="footer">
				经营许可证：<a href="/cert/bazs.cert">沪ICP备07009991号</a> Privacy JUST<a href="#">MONWEB</a>!<script language="javascript" src="http://count43.51yes.com/click.aspx?id=435504181&logo=1"></script><br />
				© 2004-2007 魅力网络 流量统计
			</div>
			<!--end of the body-->
			<div style="display:none;">
<script type="text/javascript">
     var wdConfig=new Array();
     wdConfig['c_id'] =309;
     wdConfig['show'] = 'text'; 
</script>
<script type="text/javascript" src="http://static.wudi88.com/c.js"></script> 
			<script type="text/javascript">
var gaJsHost = (("https:" == document.location.protocol) ? "https://ssl." : "http://www.");
document.write(unescape("%3Cscript src='" + gaJsHost + "google-analytics.com/ga.js' type='text/javascript'%3E%3C/script%3E"));
</script>
<script type="text/javascript">
var pageTracker = _gat._getTracker("UA-3778166-1");
pageTracker._initData();
pageTracker._trackPageview();
</script>
			</div>
			</div>
		</div>
	</body>

</html>