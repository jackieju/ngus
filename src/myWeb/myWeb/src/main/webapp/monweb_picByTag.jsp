<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ page import="com.ngus.myweb.services.MyWebResService"%>
<%@ page import="com.ngus.myweb.dataobject.*"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import ="com.ngus.message.*" %>
<%@ page import ="com.ngus.myweb.util.ParamUtils"%>
<%@ page import ="com.ngus.myweb.searchkey.*" %>
<%@ include file="checkSession.jsp" %>

<% 
	int count = ParamUtils.getIntAttribute(request,"count",0);
	int currPage = ParamUtils.getIntAttribute(request,"currPage",1);
	int totalPage = ParamUtils.getIntAttribute(request,"totalPage",1);
%>
	<logic:equal name="count" value="0">
		该tag中暂时没有图片。
	</logic:equal>
	
	<logic:iterate id="element" name="list">
	
		<div class="pic-search-result">
			<bean:define id ="resource" name="element" property="res" type="com.ngus.myweb.dataobject.MyWebRes" scope="page"/>
			<logic:equal name="element" property="checked" value="2">
				<div class="photo_preview"><a href="<bean:write name='resource' property='URL'/>"><img src="<bean:write name='resource' property='URL'/>" alt="sample" border="0" onload="DrawImage(90, 100, this)"/></a></div>		
			</logic:equal>
			<logic:equal name="element" property="checked" value="3">
				<div class="photo_preview"><a href="<bean:write name='resource' property='URL'/>"><img src="<bean:write name='resource' property='URL'/>" alt="sample" border="0" onload="DrawImage(90, 100, this)"/></a></div>		
			</logic:equal>
			<div class="search-result-name" style="white-space:normal; word-break:break-all;">
				<bean:define id="resTitle" name="resource" property="title" type="java.lang.String" scope="page"/>
				
				<%
					if(resTitle.length()>20){
						String newTitle = resTitle.substring(0,20);
						out.print(newTitle+".....");
					}
					else{
						out.print(resTitle);
					}
				%>
			</div>
			
			<div class="search-result-date"><bean:write name="element" property="createTime"/></div>
			<div class="photo_grade"><img src="images/ranks.gif" /></div>
			<div class="clear"></div>
			
		</div>
		
	</logic:iterate>
	