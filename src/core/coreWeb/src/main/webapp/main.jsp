<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!--
<%@ page import = "com.ngus.web.*,com.ngus.um.http.*" %>

-->
<html>
<head>
	<link rel="stylesheet" type="text/css" media="print" href="combined-printable.css">
<link type="text/css" rel="StyleSheet" media="all" href="combined.css" />
<link type="text/css" rel="StyleSheet" media="all" href="global.css" />
<meta http-equiv="content-type" content="text/html;charset=UTF-8" />
	<link rel="stylesheet" href="style.css" type="text/css" />
	<title>
		GNUS tester page
	</title>
	<script type="text/javascript">
	function hit(table) { 
		if (table.style.visibility == "visible")
			{ table.style.visibility = "hidden"; }
		else{ table.style.visibility = "visible"; }
		
		hit2(table); 
	} 
	function hit2(t) { 
	if (t.style.display != "none") 
		t.style.display = "none"; 
	else{ 
		all = t.childNodes;
		for( i=0;i<all.length;i++) 
		{ 
		if (all[i].tt==1) 
			if (all[i].style.display!="none") 
				all[i].style.display="none"; 
		} 
		t.style.display=""; 
		} 
	} 
		</script>
</head>
<body bgcolor=#eeeeee text=#000000>
<h1>
	GNUS tester 
</h1>
<%@ page import="com.ngus.um.dbobject.*,com.ngus.um.*" %>



User: 		<%
response.setHeader("Cache-Control","no-cache");
response.setHeader("Pragma","no-cache");

if (!new UMClient().checkHttpSession(request))
	out.println("check session failed");
else
{

User u = (User)request.getSession().getAttribute("user");
String userName= (String)request.getSession().getAttribute("username");

if (userName != null){
out.println(userName);
%><br>

NickName: 	<%

out.println(userName);%><br>


Sex: 		<%//out.println(u.getSex());
}
}

%><br>



<a href="um/log_in.jsp">Login</a>&nbsp<A STYLE="text-decoration: underline" href="um/LogOff.jsp">log off</a>
<hr>
<table 
	width="100%" 
	name="allInterafces">


<tr>
	<td 
		bgcolor="#8866ff" 
		COLSPAN="3" 
		onclick="hit(document.getElementById('jena'))">
		<B>
			Data Engine
		</B>
	</td>
</tr>
<tr><td><table id = "jena" name = "jena" Border="1" width="100%" style="visibility:hidden;display:none" >

<tr>
<td>
<font size=3 color=#550099>Create  model:</font>
</td>
<td>
<form action = "jena" method="post">			
Model Name:	<input name="modelName" value=""/>
	<input name="request" value="createModel" type= "hidden"/>			
	<input type="submit" value="submit">
</form>
</td>
</tr>

<tr>
<td>

<font size=3 color=#550099>Create  resource:</font>
</td>
<td>
<form action = "jena" method="post">			
Model Name:	<input name="modelName" value=""/><br>
	Resource Id:    <input name="resourceId" value=""/><br>
	<input name="request" value="createResource" type= "hidden"/>
	Value<br>
	<textarea name="content" rows="10" cols="80"></textarea><br>
	storageType: <input name="storageType" value="jcr"/><br>
	storagePath: <input name="storagePath" value="/tree/Clipboard"/><br>
	share level(number): <input name="shareLevel" value="0"/><br>
	title(number): <input name="title" value="haha"/><br>
			Property: <input name="property" value="Subject"/>value:<input name="value"/><br>
			Property: <input name="property"/>value:<input name="value"/><br>
			Property: <input name="property"/>value:<input name="value"/><br>
			Property: <input name="property"/>value:<input name="value"/><br>
			Property: <input name="property"/>value:<input name="value"/><br>
			Property: <input name="property"/>value:<input name="value"/><br>			
	<input type="submit" value="submit">
</form>
</td>
</tr>

<!--  create resource 2 -->
<tr>
<td>

<font size=3 color=#550099>Create  resource by ModelServlet:</font>
</td>
<td>
<form action = "/ModelServlet" method="post">			
	<input name="request" value="createResource2" type= "hidden"/>
	<input name="req" value="addRes" type= "hidden"/>
	xml<br>
	<textarea name="resobj" rows="10" cols="80" >
<?xml version="1.0" encoding="utf-8"?>
<ro shareLevel="0" title="todo" tags="1,2,3" type="1" storageType="1" storagePath="/tree/todo" resourceType="doc">
周会
<list name="rdo" >
<rdo modelName="todo" done="true" />
</list>
<list name="ro" >
<ro aa="true" />
</list>
</ro>
</textarea><br>
	<input type="submit" value="submit">
</form>
</td>
</tr>

<!--  delete resource 2 -->
<tr>
<td>

<font size=3 color=#550099>delete  resource by ModelServlet:</font>
</td>
<td>
<form action = "/ModelServlet" method="post">			
	<input name="request" value="delRes" type= "hidden"/>
	<input name="resId" value="text://ngus/jcr/tree/todo#9768" />
	<input type="submit" value="submit">
</form>
</td>
</tr>

<tr>
<td>
<font size=3 color=#550099>Get model:</font>
</td>
<td>
<form action = "jena" method="post">			
Model Name:	<input name="modelName" value=""/>
	<input name="request" value="getModel" type= "hidden"/>
			
		
			<input type="submit" value="submit">
</form>
</td>
</tr>

<tr>
<td>
<font size=3 color=#550099>update ResourceDes</font>
</td>
<td>
<form action = "jena" method="post">			
Model Name:	<input name="modelName" value=""/>
Resource Id:    <input name="resourceId" value=""/><br>
	
	Property: <input name="property" value="Subject"/>value:<input name="value"/><br>
	Property: <input name="property"/>value:<input name="value"/><br>
	Property: <input name="property"/>value:<input name="value"/><br>
	Property: <input name="property"/>value:<input name="value"/><br>
	Property: <input name="property"/>value:<input name="value"/><br>
	Property: <input name="property"/>value:<input name="value"/><br>			
	<input name="request" value="updateResourceDes" type= "hidden"/>
	<input type="submit" value="submit">
</form>
</td>
</tr>

<tr>
<td>
<font size=3 color=#550099>update resource value</font>
</td><td><form action = "jena" method="post">			

	Resource Id:    <input name="resourceId" value=""/><br>
	Value<br>
	<textarea name="content" rows="10" cols="80"></textarea><br>
		
	<input name="request" value="updateResourceValue" type= "hidden"/>
	<input type="submit" value="submit">
</form>
</td>
</tr>

<tr>
<td>
<font size=3 color=#550099>update resource share level</font>
</td><td><form action = "jena" method="post">			

	Resource Id:    <input name="resourceId" value=""/><br>
	share level(number): <input name="shareLevel" value="0"/><br>
		
	<input name="request" value="updateResShareLevel" type= "hidden"/>
	<input type="submit" value="submit">
</form>
</td>
</tr>


<tr>
<td>
<font size=3 color=#550099>get Resource Des</font>
</td><td><form action = "jena" method="post">			
Model Name:	<input name="modelName" value=""/>
Resource Id:    <input name="resourceId" value=""/>
	<input name="request" value="getResourceDes" type= "hidden"/>			
	<input type="submit" value="submit">
</form>
</td>
</tr>



<tr>
<td>
<font size=3 color=#550099>getResourceProperty</font>
</td><td><form action = "jena" method="post">			
Model Name:	<input name="modelName" value=""/>
Resource Id:    <input name="resourceId" value=""/>
Property Name:    <input name="propertyName" value=""/>
		<input name="request" value="getResourceProperty" type= "hidden"/>			
	<input type="submit" value="submit">
</form>
</td>
</tr>

<tr>
<td>
<font size=3 color=#550099>getResourceObjById</font>
</td><td><form action = "jena" method="post">			

Resource Id:    <input name="resourceId" value=""/>
		<input name="request" value="getResourceObjById" type= "hidden"/>			
	<input type="submit" value="submit">
</form>
</td>
</tr>


<tr>
<td>
<font size=3 color=#550099>getResourceTree</font>
</td><td><form action = "jena" method="post">			

Resource Id:    <input name="resourceId" value=""/>
		<input name="request" value="getResourceTree" type= "hidden"/>			
	<input type="submit" value="test">
</form>
</td>
</tr>
<tr>
<td>
<font size=3 color=#550099>delete Resource Description</font>
</td><td><form action = "jena" method="post">			
Model Name:	<input name="modelName" value=""/>
Resource Id:    <input name="resourceId" value=""/>
		<input name="request" value="deleteResource" type= "hidden"/>			
	<input type="submit" value="submit">
</form>
</td>
</tr>

<!--  select -->
<!-- 
<tr>
<td>
select by :
</td><td><form action = "jena" method="post">			
	<input name="request" value="select" type= "hidden"/><br>
			Property: <input name="property" value="Subject"/>value:<input name="value"/><br>
			Property: <input name="property"/>value:<input name="value"/><br>
			Property: <input name="property"/>value:<input name="value"/><br>
			Property: <input name="property"/>value:<input name="value"/><br>
			Property: <input name="property"/>value:<input name="value"/><br>
			Property: <input name="property"/>value:<input name="value"/><br>
			
			<input type="submit" value="submit">
</form>
</td>
</tr>
-->

<!--  delete all model -->
<tr>
<td>
</td><td><form action = "jena" method="post">			
	<input name="request" value="deleteAllModel" type= "hidden"/>
	Model Name:	<input name="modelName" value=""/>
			<input type="submit" value="delete all models">
</form>
</td>
</tr>

<!-- show xml -->
<tr>
<td>
<font size=3 color=#550099>display xml of model</font>
</td><td><form action = "jena" method="post">			
Model Name:	<input name="modelName" value=""/>
		<input name="request" value="showModelXML" type= "hidden"/>			
	<input type="submit" value="submit">
</form>
</td>
</tr>
<!-- select -->
<tr>
<td>
<font size=3 color=#550099>select</font>
</td><td><form action = "jena" method="post">			
App Name:	<input name="modelName" value="myWeb_addTime"/>
where claus:	<input name="where" value="createTime>'2006-06-16 20:21:10.198'"/>
order by:	<input name="orderby" value="createTime"/>
Ascent:<input name="asc" value="true"/>
Start from:	<input name="start" value="0"/>
Record number:	<input name="number" value="10"/>
		<input name="request" value="select" type= "hidden"/>			
	<input type="submit" value="submit">
</form>
</td>
</tr>

<!-- test chinese -->
<tr>
<td>
<font size=3 color=#550099>select</font>
</td><td><form action = "jena" method="post">			
Text:	<input name="text" value="中文"/>
		<input name="request" value="testChinese" type= "hidden"/>			
	<input type="submit" value="submit">
</form>
</td>
</tr>
<!-- getResourceByType -->
<tr>
<td>
<font size=3 color=#550099>getResourceByType</font>
</td><td><form action = "jena" method="post">
		<input name="request" value="getResourceByType" type= "hidden"/>
		type:	<select name="type" value="doc">
				<option>doc</option>
				<option>pic</option>
				<option>webpage</option>
				<option>rss</option>
				<option>audio</option>
				<option>vedio</option>
				</select>
		user:	<input name="user" value="guest"/>			
	<input type="submit" value="submit">
</form>
</td>
</tr>

</table>
</td></tr>
<!-- end of data Engine -->





<!-- JCR Operation -->
<tr>
	<td 
		bgcolor="#8866ff" 
		COLSPAN="3" 
		onclick="hit(document.getElementById('jcr'))">
		<B>
			JCR Repository
		</B>
	</td>
</tr>
<tr><td><table id = "jcr" name = "jcr" Border="1" width="100%" style="visibility:hidden;display:none;" >

<tr>
<td>
<font size=3 color=#550099>lock jcr node</font>
</td><td><form action = "jena" method="post">		
workspace:	<input name="workspace" value=""/>	
path:	<input name="path" value=""/>
session scope:    <input type= "checkbox" name="force" />
recursive:    <input type="checkBox"  name="recursive"/>
session scope:    <input type= "checkbox" name="sessionScoped" />
	<input name="request" value="lockNode" type= "hidden"/>			
	<input type="submit" value="submit">
</form>
</td>
</tr>

<tr>
<td>
<font size=3 color=#550099>unlock jcr node</font>
</td><td><form action = "jena" method="post">		
workspace:	<input name="workspace" value=""/>	
path:	<input name="path" value=""/>
	<input name="request" value="unlockNode" type= "hidden"/>			
	<input type="submit" value="submit">
</form>
</td>
</tr>

<tr>
<td>
<font size=3 color=#550099>save jcr session</font>
</td><td><form action = "jena" method="post">		
workspace:	<input name="workspace" value=""/>	
	<input name="request" value="saveSession" type= "hidden"/>			
	<input type="submit" value="submit">
</form>
</td>
</tr>



<!-- list JCR xml -->
<tr>
<td>
<font size=3 color=#550099>display xml of JCR repository</font>
</td><td><form action = "jena" method="post">			
Worspace Name:	<input name="modelName" value="tree"/>
App Name:	<input name="resourceId" value="clipboard"/>
		<input name="request" value="showJCRXML" type= "hidden"/>			
	<input type="submit" value="submit">
</form>
</td>
</tr>



<!-- search JCR -->
<tr>
<td>
<font size=3 color=#550099>search JCR</font>
</td><td><form action = "jena" method="post">			
App Name:	<input name="modelName" value=""/>
Query String:	<input name="queryString" value=""/>
Start from:	<input name="start" value="0"/>
Record number:	<input name="number" value="10"/>
		<input name="request" value="searchJCR" type= "hidden"/>			
	<input type="submit" value="submit">
</form>
</td>
</tr>

<!-- clean JCR -->
<tr>
<td>
<font size=3 color=#550099>Clean JCR Repository</font>
</td><td><form action = "jena" method="post">			
Workspace:	<input name="workspace" value=""/>
<input name="request" value="cleanJCR" type= "hidden"/>			
	<input type="submit" value="submit">
</form>
</td>
</tr>

</table>
</td></tr>
<!-- end of jcr -->

<!--  Administration -->
<tr>
	<td 
		bgcolor="#8866ff" 
		COLSPAN="3" 
		onclick="hit(document.getElementById('mc'))">
		<B>
			Administration
		</B>
	</td>
</tr>
<tr><td><table id = "mc" name = "mc" Border="1" width="100%" style="visibility:hidden;display:none;" >



<!-- show memcached status -->
<tr>
<td>
<font size=3 color=#550099>Get memcached status</font>
</td><td><form action = "jena" method="post">			

		<input name="request" value="getMemCachedStatus" type= "hidden"/>			
	<input type="submit" value="submit">
</form>
</td>
</tr>


<!-- set general Log  -->
<tr>
<td>
<font size=3 color=#550099>set general Log </font>
</td><td><form action = "jena" method="post">
	<input name="request" value="setLog" type= "hidden"/>
	file name<input name="file" value=""/>			
	log<input name="log" type= "checkbox" value=""/>
	warning<input name="warning" type= "checkbox" value=""/>			
	event<input name="event" type= "checkbox" value=""/>
	trace<input name="trace" type= "checkbox" value=""/>		
	trace level<input name="traceLevel" value=""/>						
								
	<input type="submit" value="submit">
</form>
</td>
</tr>

<!-- set general Log  -->
<tr>
<td>
<font size=3 color=#550099>set category Log </font>
</td><td><form action = "jena" method="post">
	<input name="request" value="setLogCat" type= "hidden"/>
	category<input name="category" value=""/>	
	enable category<input name="enable" type= "checkbox" value=""/>		
	log<input name="log" type= "checkbox" value=""/>
	warning<input name="warning" type= "checkbox" value=""/>			
	event<input name="event" type= "checkbox" value=""/>
	trace<input name="trace" type= "checkbox" value=""/>		
	trace level<input name="traceLevel" type= "checkbox" value=""/>						
								
	<input type="submit" value="submit">
</form>
</td>
</tr>


</table>
</td></tr>
<!-- end of Administration -->






<!--  message engine -->
<tr>
	<td 
		bgcolor="#8866ff" 
		COLSPAN="3" 
		onclick="hit(document.getElementById('me'))">
		<B>
			Message Engine
		</B>
	</td>
</tr>
<tr><td><table id = "me" name = "me" Border="1" width="100%" style="visibility:hidden;display:none;" >

<tr>
<td>
<font size=3 color=#550099>send Message</font>
</td><td><form action = "jena" method="post">		
<input name="request" value="sendMessage" type= "hidden"/>	
PostUserId:	<input name="postUserId" />
ReceiveUserId:	<input name="receiveUserId" />
Title:	<input name="title" />
content:<input name="content" />
<input type="submit" value="submit">
</form>
</td>
</tr>


<tr>
<td>
<font size=3 color=#550099>search Message</font>
</td><td><form action = "jena" method="post">		
<input name="request" value="searchMessage" type= "hidden"/>	
MessageId:	<input name="messageId" />

<input type="submit" value="submit">
</form>
</td>
</tr>

<tr>
<td>
<font size=3 color=#550099>List Post Message</font>
</td><td><form action = "jena" method="post">		
<input name="request" value="listPostMsg" type= "hidden"/>	
postUserId:	<input name="postUserId" />
<input type="submit" value="submit">
</form>
</td>
</tr>


<tr>
<td>
<font size=3 color=#550099>List Receive Message</font>
</td><td><form action = "jena" method="post">		
<input name="request" value="listReceiveMsg" type= "hidden"/>	
receiveUserId:	<input name="receiveUserId" />
<input type="submit" value="submit">
</form>
</td>
</tr>


<tr>
<td>
<font size=3 color=#550099>Reply Message</font>
</td><td><form action = "jena" method="post">		
<input name="request" value="replyMsg" type= "hidden"/>	
MessageId:	<input name="messageId" />
content:	<input name="content" />
title:	<input name="title" />
<input type="submit" value="submit">
</form>
</td>
</tr>


<tr>
<td>
<font size=3 color=#550099>Send Invitation</font>
</td><td><form action = "jena" method="post">		
<input name="request" value="sendInvitation" type= "hidden"/>	
postUserId:	<input name="postUserId" />
receiveUserId:	<input name="receiveUserId" />		
<input type="submit" value="submit">
</form>
</td>
</tr>


<tr>
<td>
<font size=3 color=#550099>Search Text</font>
</td><td><form action = "jena" method="post">		
<input name="request" value="searchText" type= "hidden"/>	
Text:	<input name="text" />
ReceiveId : <input name="receiveId" /><input type="submit" value="submit">
</form>
</td>
</tr>


</table>
</td></tr>
<!-- end of message engine -->



<!--  Clipboard -->
<tr>
	<td 
		bgcolor="#8866ff" 
		COLSPAN="3" 
		onclick="hit(document.getElementById('clipboard'))">
		<B>
			Clippboard (Text)
		</B>
	</td>
</tr>

<tr><td><table id = "clipboard" name = "clipboard" Border="1" width="100%" style="visibility:hidden;display:none;" >

<tr>
<td>
	<font size=3 color=#550099>add clipboard</font>
	Pease put your content here: (currently only text is supported)
	</td><td><form action = "clipboard" method="post">			
	
			<textarea name="content" rows="20" cols="80"></textarea><br>
			<input name="request" value="post" type= "hidden"/>
			category: <input name="category" value="todo"/><br>
			tag: <input name="tag" value="todo"/><br>
			tag: <input name="tag" value=""/><br>
			tag: <input name="tag" value=""/><br>
			
			Property: <input name="property" value="Subject"/>value:<input name="value"/><br>
			Property: <input name="property"/>value:<input name="value"/><br>
			Multi-Thread Testing<input type="checkBox" name="multiThread" /><br>
			thread number:<input name="threadNumber" value="10"/><br>
			time:<input name="time" value="100"/>MilliSeconds<br>
		
			<br>
		
			<input type="submit">
	</form>
</td>
</tr>

<tr>
<td>
	
	</td><td><form action = "clipboard" method="post">			
	
			<input name="request" value="listAll" type= "hidden"/>			
			<br>
		
			<input type="submit" value = "List all clipboard for default user">
</form>
</td>
</tr>	

<tr>
<td>
	
	</td><td><form action = "clipboard" method="post">			
			content:<textarea name="content" rows="20" cols="80"></textarea><br>
			<input name="request" value="addComment" type= "hidden"/>			
			<br>
			ResId:<input name="resId" /><br>
			Multi-Thread Testing<input type="checkBox" name="multiThread" /><br>
			thread number:<input name="threadNumber" value="10"/><br>
			time:<input name="time" value="100"/>MilliSeconds<br>
			<input type="submit" value = "Add Comment Test">
</form>
</td>
</tr>	

</table>
</td></tr>
<!-- end of clipboard -->

<!-- custom link tseting -->
</table>
<P>
</body>
</html>
