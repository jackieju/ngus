<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ page import="com.ngus.myweb.services.MyWebResService"%>
<%@ page import="com.ngus.um.http.*" %>
<%@ page import="java.util.List"%>
<%@ include file="checkSession.jsp" %>
<%
	String name = request.getParameter("name");
	String url = request.getParameter("url");	
	try	{
		List tags = MyWebResService.instance().listUserTag(UserManager.getCurrentUser().getSUserId());
		request.setAttribute("tags", tags);
	}
	catch(Exception e){
		e.printStackTrace();
	}
	if(name!=null){
		request.setAttribute("name", name);
	}
	if(url!=null){
		request.setAttribute("url", url);
	}
%>
<link href="css/style.css" rel="stylesheet" type="text/css" media="all" />
<script type="text/javascript" src="javascript/prototype.js"></script>
<script type="text/javascript" src="javascript/main.js"></script>		
<link href="css/css.css" rel="stylesheet" type="text/css">
<div id="add">	
<form id="addbookmark">
  <bean:parameter id="arg1" name="parentId" value="" />
  <input type="hidden" name="parentID" value="<bean:write name="arg1" />"/>
  <table id="iadd" cellspacing="5" cellpadding="0" width="500" border="0">
    <tbody>
      <tr>
        <td width="35">标题</td>
        <td colspan="2"><input name="name" size="40" maxlength="255" class="text"
        <logic:present name="name" scope="request">
        	value="<bean:write name="name" />"
        </logic:present>
        /></td>
      </tr>
      <tr>
        <td>网址</td>
        <td colspan="2"><input name="url" size="40" maxlength="255" class="text"
        <logic:present name="url" scope="request">
        	value="<bean:write name="url" />"
        </logic:present>
        /></td>
      </tr>
      <tr>
        <td>描述</td>
        <td colspan="2"><textarea name="description" cols="32" rows="5" type="text"></textarea></td>
      </tr>
	  <tr>
	  	<td>类别</td>
		<td>
			<select name="type" style="width:150px;">
				<option value="webpage">网页</option>
				<option value="rss">rss</option>
				<option value="video">视频</option>
				<option value="audio">音频</option>
				<option value="pic">图片</option>
				<option value="doc">文档</option>
			</select>
		</td>
	  </tr>
      <tr>
        <td>TAG</td>
        <td>
			<input id="tags" name="tags" size="40" maxlength="200" class="text"/>
        	<br />
			<div id="bk_form_tags">
			<logic:iterate id="tag" name="tags">
				<span onmouseover="tag_mouseover(this)" onmouseout="tag_mouseout(this)" onclick="tag_click(this)"><bean:write name="tag"/></span>
			</logic:iterate>
			</div>
		</td>
      </tr>
      <tr>
        <td>公开</td>
        <td colspan="2"><input name="shareLevel" type="checkbox" value="0" checked /></td>
      </tr>
      <tr>
        <td></td>
        <td colspan="2">
        	<input name="button" type="button" value="添加" onclick="addbookmark();" />
        	<input name="button" type="button" value="取消" /></td>
      </tr>
    </tbody>
  </table>
</form>
	<!-- <form id="addbookmark">
		<bean:parameter id="arg1" name="parentId" value="" />
		<input type="hidden" name="parentID" value="<bean:write name="arg1" />"/>
		name: <input type="text" name="name"/><br/>
		url : <input type="text" name="url"/><br/>
		description: <input type="textarea" name="description"/><br/>
		type : 
		<select name="type" style="width:150px;">
			<option value="webpage">webpage</option>
			<option value="rss">rss</option>
			<option value="video">video</option>
			<option value="audio">audio</option>
			<option value="pic">pic</option>
			<option value="doc">doc</option>
		</select>
		<br/>
		<input type="radio" name="shareLevel" value="0" />公开
		<input type="radio" name="shareLevel" value="1" />保密
		<br/>
		tags : <input id="tags" type="text" name="tags"/><br/>
		Your Tags:
		<div id="bk_form_tags">
			<logic:iterate id="tag" name="tags">
				<span onmouseover="tag_mouseover(this)" onmouseout="tag_mouseout(this)" onclick="tag_click(this)"><bean:write name="tag"/></span>
			</logic:iterate>					
		</div>
		<input type="button" value="submit" onclick="addbookmark();">
	</form>-->
</div>