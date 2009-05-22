<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ page import="com.ngus.myweb.services.MyWebResService"%>
<%@ page import="com.ngus.myweb.dataobject.*"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<% 
	List<BookMark> bk = new ArrayList<BookMark>();
	List<Folder> fd = new ArrayList<Folder>();
	String id = request.getParameter("id");
	if(request.getAttribute("id")!=null){
		id = request.getAttribute("id").toString();
	}
	try{
		List<ObjectTree> ob;
		if(id==null || id.trim().equals("")){
			ob = MyWebResService.instance().listAll(1);
		}
		else{
			ob = MyWebResService.instance().getTree(id,2).getChildren();
		}
		for(ObjectTree tmp : ob){
			if(tmp.getNode() instanceof BookMark){
				bk.add((BookMark)tmp.getNode());
			}
			else{
				fd.add((Folder)tmp.getNode());
			}
		}
		request.setAttribute("fd",fd);
		request.setAttribute("bk",bk);
	}
	catch(Exception e){
		e.printStackTrace();
	}
%>
<% request.setAttribute("length", bk.size()); %>
<% int tempIdNumber= 1;%>
<logic:greaterThan name="length" value="0">
<ul>
<logic:iterate id="bookmark" name="bk">
	<li>
		<div class="search-result" onmouseover="showContent('content<%= tempIdNumber %>');" onmouseout="hideContent('content<%= tempIdNumber %>');">
			<div class="search-result-title" ><bean:write name="bookmark" property="title"/></div>
            <div id="content<%= tempIdNumber %>" style="display:none;">
            <% tempIdNumber++; %>
			<div class="search-result-url"><a href="<bean:write name="bookmark" property="URL"/>" target='_blank'><bean:write name="bookmark" property="URL"/></a></div>
			<div class="search-result-description"><bean:write name="bookmark" property="description"/></div>
			<div class="search-result-tag">
			TAG:
			<logic:iterate id="tag" name="bookmark" property="tags">
				<a href="search?type=tag&key=<bean:write name="tag"/>"><bean:write name="tag"/></a> |
			</logic:iterate>			
			</div>			
			<div>
				<a href="#" onclick="editbookmark('<bean:write name="bookmark" property="ID" />');"><img src="images/edit.gif" />编辑</a>
				<a href="#" onclick="deletebookmark('<bean:write name="bookmark" property="ID" />');"><img src="images/del.gif" align="absmiddle" />删除</a>
			</div>
            </div>
		</div>
	</li>
</logic:iterate>
</ul>
</logic:greaterThan>