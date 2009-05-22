<%@ page language="java"%>
<%@ page contentType="text/html; charset=gb2312" %>
<%@ page import="com.stockstar.buos.um.*" %>
<%@ include file="CheckSession.jsp"%>
<%
//setHeader sample
response.setHeader("Cache-Control","no-cache");
response.setHeader("Pragma","no-cache");

int lRet = -1;

Integer GroupId = new Integer(request.getParameter("GroupId"));

GroupInfoList groupInfoList = new GroupInfoList();
lRet = UMClient.AF_GetAllGroup(SSSESSIONID,ClientId,groupInfoList);

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META name="GENERATOR" content="IBM WebSphere Studio">
<TITLE>Usermanager Client on JSP Sample</TITLE>
<style>
	body,table,p,td{font-family:verdana;font-size:9pt;}
</style>
</HEAD>
<BODY>
<h3>UMClient.AF_ModGroup</h3>
<h4>Returns:</h4>
<%
if (lRet == 0){
	GroupInfo groupInfo = new GroupInfo();

	out.println("AF_ModGroup Successfully.");
	out.println("Group Number:" + groupInfoList.Size());

	for (int i=0; i<groupInfoList.Size(); i++){
		groupInfoList.Get(groupInfo,i);

		if (GroupId.intValue() == groupInfo.lGroupId){
		%>
			<form name=f1 method=post action="UM_AF_ModGroup.jsp">
			<input type=hidden name="GroupId" value="<%=groupInfo.lGroupId%>">
			½ÇÉ«Ãû³Æ£º<input type="text" name="GroupName" value="<%=groupInfo.sGroupName%>"><br>
			½ÇÉ«ÃèÊö£º<input type="text" name="GroupDescription" value="<%=groupInfo.sGroupDescription%>"><br>
			ÊÇ·ñÄ¬ÈÏ: <select name="IsDefault">
					<option value="0" <%if (groupInfo.nIsDefault==0) out.print("selected");%>>·ñ</option>
					<option value="1" <%if (groupInfo.nIsDefault==1) out.print("selected");%>>ÊÇ</option>
				  </select><br>
			<input type="hidden" name="action" value="modify">
			<input type=submit value="Modify Group">
			</form>
		<%
		}
	}
}else{
	out.println("AF_GetAllGroup Failed." + lRet);
}
%>

</BODY>
</HTML>
