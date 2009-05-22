<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>  
<div id="add">
<form id="addfolder">
<bean:parameter id="arg1" name="parentId" value="" />
<input type="hidden" name="parentID" id="parentID" value="<bean:write name="arg1" />" />
标题<input type="text" name="name" size="16" maxlength="16" />	<br />
描述<input type="textarea" name="description" rows="5" cols="40"/><br />
<input type="radio" name="shareLevel" value="0" />公开
<input type="radio" name="shareLevel" value="1" />保密
<br/>
<input type="button" value="添加" onclick="addfolder();" />
</form>
</div>