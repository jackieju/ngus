<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>

<%@ include file="checkSession.jsp" %>


				<% String postId = (String)session.getAttribute("username"); %>
				
			
				<html:form action="/postMsg.do">

					<html:hidden property="postId" value="<%=postId %>" />
					<table>
					<tr>
						<td>
							发送信息
						</td>
					</tr>
					<tr>
						<td >
							发送对象:</td>
						<td>					
							<html:text property="receiveId" size="20" styleClass="text"/>
						</td>
					</tr>
					<tr>
						<td >
							标题:</td>
						<td>					
							<html:text property="title" size="20" styleClass="text"/>
						</td>
					</tr>
					<tr>
						<td >
							内容:</td>
						<td>					
							<html:textarea property="content" rows="14" cols="55" style="overflow-y:hidden"/>
						</td>
					</tr>
					</table>
					
					<html:submit value="提交"/>
			
				</html:form>