<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="GB18030"%>
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
<% request.setAttribute("length", fd.size()); %>
<logic:greaterThan name="length" value="0">
<ul>
<logic:iterate id="folder" name="fd">
	<li id="<bean:write name="folder" property="ID"/>"><a href="#"><span class="test"><bean:write name="folder" property="name"/></span></a></li>
</logic:iterate>
</ul>
</logic:greaterThan>