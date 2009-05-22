<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

	<head>
		<meta http-equiv="content-type" content="text/html;charset=utf-8" />
		<meta name="description" content="在线收藏永不丢失 智能分类为网站，图片，视频， RSS 用最便捷的方式分享您的收藏，以最快的速度发现流行"/>
		<meta name="keywords" content="在线收藏 智能 分类 tag 标签 图片 视频 RSS 分享 流行"/>
		<title>monweb.cn - 在线智能网络收藏夹 最方便的客户端软件 最智能的分类 发现分享 永不丢失 </title>
		<link href="css/style.css" rel="stylesheet" type="text/css" media="all" />
		<script type="text/javascript" src="javascript/prototype.js"></script>
		<link rel="stylesheet" href="css/todo.css" type="text/css" />
	</head>

	<body>
		<div id="mainwrap">
			<div>
			<!--start of the body-->
			
			
			
			<br />
			<div id="content">
				<!--start of the main content-->
				<div id="main">
					<div id="inner_l_main">
					<!--start of the main of left area-->
				
					<div style="clear:both;"></div>
					<div style="border-top:1px solid #66CCFF;"></div>
					
				
					<!-- body of one piece of results||starts||-->
					
					<#list res_list as res>
					<div class="result_display">
						<form action="ModelUpdateServlet" method="post">
	<div class="todo_list">
		<div class="todo_line" onclick="alert('not implemented')" onmouseover="this.style.background='#eeeeee'" onmouseleave="this.style.background=''" nowrap>
			<div class="todo_content" >
			${res.getValue()}
			</div>
			
			<div class="todo_status">
				<div class="todo_check">
				<input type="checkbox" checked />
				</div>
				
				
				
				<div class="todo_create_time">
${res.createTime}
				</div>
				
				<div class="todo_done_time">
${res.updateTime}
				</div>
			</div>
		</div>		
	</div>
					
						
							
						
						</form>
						</div>
						
						<div class="clear"></div>
						<div class="patchbox"></div>
					</div>
					</#list>
					
					<!-- body of one piece of results||ends||-->								
										
			
					<!--end of the main of left area-->
					</div>
				</div>
			
				
				<!--end of the main content-->
			</div>
			
			<div class="clear">&nbsp;</div>
			
			<div id="footer">
				 JUST<a href="http://www.monweb.cn">MONWEB</a>!<script language="javascript" src="http://count43.51yes.com/click.aspx?id=435504181&logo=1"></script><br />
				
			</div>
			<!--end of the body-->
			</div>
		</div>
	</body>

</html>
