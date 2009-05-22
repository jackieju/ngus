<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ page import="com.ngus.myweb.services.MyWebResService"%>
<%@ page import="com.ngus.myweb.dataobject.MyWebRes"%>
<%@ page import="com.ngus.myweb.dataobject.BookMark"%>
<%@ page import="java.util.List"%>
<%@ page import="com.ngus.um.http.*" %>
<%@ page import="com.ngus.myweb.util.*"%>
<%@ page import = "java.util.*" %>
<%@ page import = "com.ns.log.Log" %>
<%
	//Log.trace("enter editbookmark.jsp");
	try	{
		List tags = MyWebResService.instance().listUserTag(UserManager.getCurrentUser().getSUserId());
		request.setAttribute("tags", tags);
	}
	catch(Exception e){
		Log.error(e);
	}
	
	BookMark bk = null;
	String tags = "";
	List<String> addedtags = null;
	try{
		String id = ParamUtils.getParameter(request, "id");
		MyWebRes res = MyWebResService.instance().getInstanceByID(id);
		if(res instanceof BookMark){
			bk = (BookMark)res;			
		}
		//Log.trace("name="+bk.getName());
		addedtags = res.getTags();
		Iterator<String> iter = addedtags.iterator();
		while(iter.hasNext()){
			String tmp = iter.next();
			if(tmp.indexOf(" ") > -1){
				tmp="\""+tmp+"\"";
			}
			tags+=tmp+" ";
		}
	}
	catch(Exception e){
		Log.error(e);
		out.print("<jsp:forward page=\"error.jsp\">");		
	}
	//Log.trace("finish editbookmark");
	request.setAttribute("bk", bk);
	//request.setAttribute("addedTags", iter);
%>
<div id="edit">
<form id="editbookmark">
  <input type="hidden" name="id" value="<bean:write name="bk" property="ID"/>"/>
  <table id="iadd" cellspacing="5" cellpadding="0" width="500" border="0">
    <tbody>
      <tr>
        <td width="35">标题</td>
        <td colspan="2"><input name="name" size="40" maxlength="255" value="<bean:write name="bk" property="name"/>" /></td>
      </tr>
      <tr>
        <td>网址</td>
        <td colspan="2"><input name="url" size="40" maxlength="255" value="<bean:write name="bk" property="URL"/>" /></td>
      </tr>
      <tr>
        <td>描述</td>
        <td colspan="2"><textarea name="description" cols="32" rows="5" type="text"><bean:write name="bk" property="description" /></textarea></td>
      </tr>
	  <tr>
	  	<td>类别</td>
		<td>
			<select name="type" style="width:150px;">
				<option value="webpage"
				<logic:equal name="bk" property="rtype" value="webpage">
					selected
				</logic:equal>
			>网页</option>
			<option value="rss"
				<logic:equal name="bk" property="rtype" value="rss">
					selected
				</logic:equal>
			>RSS</option>
			<option value="video"
				<logic:equal name="bk" property="rtype" value="video">
					selected
				</logic:equal>
			>视频</option>
			<option value="audio"
				<logic:equal name="bk" property="rtype" value="audio">
					selected
				</logic:equal>
			>音频</option>
			<option value="pic"
				<logic:equal name="bk" property="rtype" value="pic">
					selected
				</logic:equal>
			>图片</option>
			<option value="doc"
				<logic:equal name="bk" property="rtype" value="doc">
					selected
				</logic:equal>
			>文档</option>
			</select>
		</td>
	  </tr>
      <tr>
        <td>TAG</td>
        <td>
			<input id="tags" name="tags" size="40" maxlength="200" value="<%=tags%>" />
        	<br />
			<div id="bk_form_tags">			
			<logic:iterate id="tag" name="tags">
				<span onload="tag_mouseover(this);tag_mouseout(this);" onmouseover="tag_mouseover(this)" onmouseout="tag_mouseout(this)" onclick="tag_click(this)" 
				<%
					if(addedtags.contains(tag)){
				%>
				class="selected"
				<%
					}
				%>
				><bean:write name="tag"/></span>
			</logic:iterate>			
			</div>
		</td>
      </tr>
      <tr>
        <td>公开</td>
        <td colspan="2"><input name="shareLevel" type="checkbox" value="0" 
        <%
			if(bk.getShareLevel()==0){
				out.println("checked");
			}
        
        
		%>
		/></td>
      </tr>
      <tr>
        <td></td>
        <td colspan="2">
        	<input name="button" type="button" value="确定" onclick="edit();" />
        	<input name="button" type="button" value="取消" /></td>
      </tr>
    </tbody>
  </table>
</form>	
</div>