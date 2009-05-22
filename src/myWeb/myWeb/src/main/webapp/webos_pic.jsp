<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ page import="com.ngus.myweb.webservices.*"%>
<%@ page import="com.ngus.um.*" %>
<%@ include file="webos_checkSession.jsp" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<% 
	
	response.setHeader("Cache-Control","no-cache");
	response.setHeader("Pragma","no-cache");
	
	WebServices service = new WebServices();
	
	String[] picArray = service.listUserTag(sessionId);
	
	request.setAttribute("picArray",picArray);
	
%>
<html xmlns="http://www.w3.org/1999/xhtml">

	<head>
		<meta http-equiv="content-type" content="text/html;charset=utf-8" />
		<meta name="generator" content="Adobe GoLive" />
		<title>monweb.com-images</title>
		<link href="css/style.css" rel="stylesheet" type="text/css" media="all" />
        <link rel="stylesheet" type="text/css" href="css/magicbox.css"/>
        <script type="text/javascript" src="javascript/prototype.js"></script>
        <script type="text/javascript" src="javascript/effects.js"></script>
        <script type="text/javascript" src="javascript/utility.js"></script>
        <script type="text/javascript" src="javascript/magicbox.js"></script>
	</head>
	
	<script type="text/javascript">
			window.onload = function(){}
			function DrawImage(max_height, max_width, e){
				var image=new Image();
				image.src=e.src;
				if (e.height>e.width){	
					if (e.height > 90)
						e.height=90;
				}else if(e.width>90){
                	e.width=90;
                }
				e.alt=image.width+"pt"+image.height;
			} 
			
			var http= getHTTPObject();
			
			function response(){		
				if(http.readyState ==4 ){			
					if(http.status == 200){	
						document.getElementById("bk_display").innerHTML=http.responseText;
                		MagicBoxSystem.start();
					}
				}
				else{
					document.getElementById("bk_display").innerHTML="<img src='img/loading.gif'/>";
				}	
			}
			
			function WebOSListPic(tag,userId,currPage){
				var url="WebOSListPic?tag="+tag;				
				url += "&currPage=" +currPage;
				url += "&userId="+userId;
				url= encodeURI(url);
				
				http.open("POST",url,true);
				http.onreadystatechange=response;	
				http.send(null);

				return;
			}
			function getHTTPObject() 
			{
				if (window.ActiveXObject) 
					return  new ActiveXObject("Microsoft.XMLHTTP");
				else if (window.XMLHttpRequest) 
					return  new XMLHttpRequest(); 
			}
	</script>
	
	<body>
		<div style="text-align:center;">
			<div style="margin:0px auto auto;text-align:left;float:auto;padding:0px;width:690px;">
                <div class="content-panel" style="min-height:400px;">
					<div class="content-panel-content">
						<div id="directory" class="directory" style="height:100%;float:left;width:100px !important;width:150px;text-align:left;padding-left:5px;line-height:150%;  white-space: nowrap;overflow:auto;">
							<div class="show_tags" >
								<div>
									<img src="img/folder_folder.gif" /><a href="#" onclick="WebOSListPic('','<%=(String)request.getSession().getAttribute("userid") %>',1);"><span>&nbsp;所有</span></a>
								</div>		
								<logic:present name="picArray" scope="request">
									<logic:iterate id="element" name="picArray">
										<div>
											<img src="img/folder_folder.gif" /><a href="#" onclick="WebOSListPic('<bean:write name='element'/>','<%=(String)request.getSession().getAttribute("userid") %>',1);"><span>&nbsp;<bean:write name="element" /></span></a>
										</div>
									</logic:iterate>
								</logic:present>
							</div>
						</div>
						<div style="background-color: transparent;" id="split_bar" class="split_bar" onmouseover=""></div>
						<div class="content_list">
    					   <div id="bk_display" class="inner_content_list" align="left"></div>
								<!--end of the list-->
						</div>
					</div>
				</div>
					<!--end of the main of left area-->
					
			</div>
        </div>
	</body>

</html>