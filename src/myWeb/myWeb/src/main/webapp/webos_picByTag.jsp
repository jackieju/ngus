<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ page import ="com.ngus.myweb.util.ParamUtils"%>
<%@ include file="checkSession.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
   </head>

    <body>
	<logic:equal name="count" value="0">
		该tag中暂时没有图片。
	</logic:equal>
		<div class="magicbox">	
	<logic:iterate id="element" name="list">
			<bean:define id ="resource" name="element" property="res" type="com.ngus.myweb.dataobject.MyWebRes" scope="page"/>
			<logic:equal name="element" property="checked" value="2">
				<span src="<bean:write name='resource' property='URL'/>" />		
			</logic:equal>
			<logic:equal name="element" property="checked" value="3">
				<span src="<bean:write name='resource' property='URL'/>" />		
			</logic:equal>
	</logic:iterate>
		</div>    
    </body>
</html>