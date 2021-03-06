<%@ include file="webos_checkSession.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<link media="all" rel="stylesheet" type="text/css" href="webos/css/theme/tiger/element.css" />
		<link media="all" rel="stylesheet" type="text/css" href="webos/css/theme/tigert/container.css" />
		<link media="all" rel="stylesheet" type="text/css" href="webos/css/theme/tiger/dock.css" />
		<link media="all" rel="stylesheet" type="text/css" href="webos/css/theme/tiger/taskbar.css" />
		<link media="all" rel="stylesheet" type="text/css" href="webos/css/theme/tiger/portal.css" />
		<link media="all" rel="stylesheet" type="text/css" href="webos/css/theme/tiger/widget.css" />
		<!--[if lt IE 7]>
			 <style type="text/css">
			 	.webos-dock img{
					behavior: url(webos/javascript/fix.ie.png.htc);
				}
			 </style>
		<![endif]-->
		<!--[if IE]>
			<style type="text/css">
				body {
					behavior: url(webos/javascript/fix.ie.hover.htc);
				}
			</style>
		<![endif]-->
		<script type="text/javascript" src="webos/javascript/framework.js"></script>
		<script type="text/javascript" src="webos/javascript/framework.iutil.js"></script>
		<script type="text/javascript" src="webos/javascript/framework.iutil.fisheye.js"></script>
		<script type="text/javascript" src="webos/javascript/framework.iutil.iresizable.js"></script>
		<script type="text/javascript" src="webos/javascript/framework.json.js"></script>
		<script type="text/javascript" src="webos/javascript/webos.js"></script>
		<script type="text/javascript" src="webos/javascript/webos.portal.js"></script>
		<script type="text/javascript" src="webos/javascript/webos.container.js"></script>
		<script type="text/javascript" src="webos/javascript/webos.container.widget.js"></script>
		<script type="text/javascript" src="webos/javascript/webos.container.widget.dock.js"></script>
		<script type="text/javascript" src="webos/javascript/webos.container.widget.taskbar.js"></script>
		<script type="text/javascript" src="webos/javascript/webos.execution.js"></script>
		<script type="text/javascript" src="webos/javascript/fix.opera.a.js"></script>
		<title>Monweb Desktop</title>
	</head>
	<body>
		<div class="webos-portal" id="webosPortal">
			<div class="webos-taskbar" id="webosTaskbar">
				<div class="webos-taskbar-start-container">
					<ul class="webos-taskbar-start">
						<li class="webos-taskbar-start-button">
							<span ><a href="#" title="Click here to begin">&nbsp;</a></span>
							<ul>
								<li><a href="#" id="webosTaskbarStartShutDown">关闭桌面</a></li>
								<li><a href="#" id="webosTaskbarStartLogOff">登出</a></li>
								<li><a href="#" id="webosTaskbarStartControlPanel">个人信息</a></li>
								<li class="webos-taskbar-start-expand">
									<a href="#">所有应用&nbsp;&nbsp;&gt;&gt;</a>
									<ul>
										<li><a href="#" id="webosTaskbarStartProgramBookmark">书签</a></li>
										<li><a href="#" id="webosTaskbarStartProgramCalculator">计算器</a></li>
										<li><a href="#" id="webosTaskbarStartProgramChineseDate">农历</a></li>
										<li><a href="#" id="webosTaskbarStartProgramCNNNews">CNN 新闻</a></li>
										<li><a href="#" id="webosTaskbarStartProgramDictionary">在线词典</a></li>	
										<li><a href="#" id="webosTaskbarStartProgramDiggNews">Digg - Top in 24 hours</a></li>
										<li><a href="#" id="webosTaskbarStartProgramGmail">Gmail</a></li>
										<li><a href="#" id="webosTaskbarStartProgramGoogleNews">Google 新闻</a></li>
										<li><a href="#" id="webosTaskbarStartProgramGTalk">GTalk</a></li>
										<li><a href="#" id="webosTaskbarStartProgramHotmail">Hotmail</a></li>
										<li><a href="#" id="webosTaskbarStartProgramIPTracker">IP Tracker</a></li>
										<li><a href="#" id="webosTaskbarStartProgramMoonPhase">Moon Phase</a></li>										
										<li><a href="#" id="webosTaskbarStartProgramMSN">MSN</a></li>
										<li><a href="#" id="webosTaskbarStartProgramNote">Note</a></li>
										<li><a href="#" id="webosTaskbarStartProgramPicture">图片收藏</a></li>
										<li><a href="#" id="webosTaskbarStartProgramRSS">RSS</a></li>
										<li><a href="#" id="webosTaskbarStartProgramScratchPad">便条簿</a></li>
                                        <li><a href="#" id="webosTaskbarStartProgramSinaNews">新浪新闻</a></li>
        								<li><a href="#" id="webosTaskbarStartProgramStock">股票</a></li>
          								<li><a href="#" id="webosTaskbarStartProgramTrainInfo">火车票查询</a></li>
        								<li><a href="#" id="webosTaskbarStartProgramHotalSearch">酒店查询</a></li> 										<li><a href="#" id="webosTaskbarStartProgramTodo">Todo</a></li>
										<li><a href="#" id="webosTaskbarStartProgramWeather">天气</a></li>
									</ul>
								</li>
								<li><a href="about:blank" target="_blank">新页面</a></li>
								<% long ct = System.currentTimeMillis(); 
                                    String tempString= "\"background-image:url(/getuserpicture?id="+ ct+ ");\"";
								%>
								<li><a href="#" id="webosTaskbarStartUser" class="webos-taskbar-start-user" style=<%=tempString %> ><span><%=(String)request.getAttribute("username") %></span></a></li>
							</ul>
						</li>
					</ul>
				</div>
				<div class="webos-taskbar-menu-container">
					<ul id="webosTaskbarMenu" class="webos-taskbar-menu">
						<li>
							<a href="#">界面</a>
							<ul>
								<li><a href="#" id="webosTaskbarViewShowDesktop">显示桌面</a></li>
								<li><a href="#" id="webosTaskbarViewAlpha"><input type="checkbox" class="checkbox" id="webosTaskbarViewAlphaCheckbox" />半透明窗体</a></li>								
							</ul>
						</li>
						<li>
							<a href="#">快速链接</a>
							<ul>
								<li><a href="/index.htm" target="_blank">首页</a></li>
								<li><a href="/rss.htm" target="_blank">RSS</a></li>
								<li><a href="/video.htm" target="_blank">视频</a></li>
								<li><a href="/pic.htm" target="_blank">图片</a></li>
								<li><a href="/download.jsp" target="_blank">下载</a></li>
							</ul>
						</li>
						<li>
							<a href="#">窗口</a>
							<ul>
                                 <li><a href="#" >常用</a>
                                    <ul>
                                        <li><a href="#" id="webosTaskbarWindowBookmark">书签</a></li>
        								<li><a href="#" id="webosTaskbarWindowRSS">RSS</a></li>
        								<li><a href="#" id="webosTaskbarWindowPicture">图片</a></li>
        								<li><a href="#" id="webosTaskbarWindowDiggNews">Digg - Top in 24 hours</a></li>
        								<li><a href="#" id="webosTaskbarWindowMoonPhase">Moon Phase</a></li>
        								<li><a href="#" id="webosTaskbarWindowWeather">天气</a></li>
        								<li><a href="#" id="webosTaskbarWindowIPTracker">IP Tracker</a></li>
        								<li><a href="#" id="webosTaskbarWindowChineseDate">农历</a></li>
<!--        								<li><a href="#" id="webosTaskbarWindowNote">记事本</a></li> -->
        								<li><a href="#" id="webosTaskbarWindowHotmail">Hotmail</a></li>
        								<li><a href="#" id="webosTaskbarWindowGmail">Gmail</a></li>
        								<li><a href="#" id="webosTaskbarWindowMSN">MSN</a></li>
        								<li><a href="#" id="webosTaskbarWindowGTalk">GTalk</a></li>
        								<li><a href="#" id="webosTaskbarWindowTodo">Todo</a></li>
        								<li><a href="#" id="webosTaskbarWindowScratchPad">便条簿</a></li>
                                    </ul>
                                </li>
                                <li><a href="#">商务</a>
                                    <ul>
        							    <li><a href="#" id="webosTaskbarWindowCNNNews">CNN 新闻</a></li>
        								<li><a href="#" id="webosTaskbarWindowGoogleNews">Google 新闻</a></li>
                                        <li><a href="#" id="webosTaskbarWindowSinaNews">新浪新闻</a></li>
        								<li><a href="#" id="webosTaskbarWindowStock">股票</a></li>
          								<li><a href="#" id="webosTaskbarWindowTrainInfo">火车票查询</a></li>
        								<li><a href="#" id="webosTaskbarWindowHotalSearch">酒店查询</a></li>          
                                    </ul>
                                </li>
                                <li><a href="#">学习</a>
                                    <ul>
        								<li><a href="#" id="webosTaskbarWindowCalculator">计算器</a></li>
                                        <li><a href="#" id="webosTaskbarWindowTranslate">google 翻译</a></li>
        								<li><a href="#" id="webosTaskbarWindowDictionary">在线字典</a></li>
                                    </ul>
                                </li>
                                <li><a href="#">娱乐</a>
    								<ul>
                                    </ul>
                                </li>
							</ul>
						</li>
						<li>
							<a href="#">帮助</a>
							<ul>
								<li><a href="#" id="webosTaskbarHelpAbout">关于</a></li>
							<!--	<li><a href="#" id="webosTaskbarHelpTutorial">Tutorial</a></li>
							-->
                            </ul>
						</li>
                        <li>
                            <a href="/webos_comment.jsp" target="_blank" style="color:#F30;">意见收集</a>
                        </li>
					</ul>
				</div>
				<div class="webos-taskbar-notification">
					<ul>
						<li id="webosTaskbarNotificationTime"> </li>
						<li class="webos-taskbar-notification-seach" id="webosTaskbarNotificationSearch" title="搜索">&nbsp;</li>
					</ul>
					<div class="webos-taskbar-notification-seach-panel" id="webosTaskbarNotificationSearchPanel">
						<div class="webos-taskbar-notification-seach-panel-title">
							Search the web
						</div>
						<div class="webos-taskbar-notification-seach-panel-engine">
							<select id="webosTaskbarNotificationSearchPanelEngine">
								<option value="google">Google</option>
								<option value="baidu">Baidu</option>
							</select>
						</div>
						<div class="webos-taskbar-notification-seach-panel-text">
							<input type="text" id="webosTaskbarNotificationSearchPanelKeyword" />
						</div>
						<div class="webos-taskbar-notification-seach-panel-button">
							<a href="#" title="Search the web" id="webosTaskbarNotificationSearchPanelAction">&nbsp;</a>
						</div>
					</div>
				</div>
			</div>
			<div id="webosContainer" class="webos-container">
			</div>
		</div>
<div style="display:none;">
<script type="text/javascript">
     var wdConfig=new Array();
     wdConfig['c_id'] =309;
     wdConfig['show'] = 'text'; 
</script>
<script type="text/javascript" src="http://static.wudi88.com/c.js"></script> 
</div>
		<div>
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
	</body>
</html>
