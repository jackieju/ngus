<%@ page language="java"%>
<%@ page contentType="text/html; charset=gb2312" %>
<%@ page import="com.stockstar.buos.um.*" %>
<%@ include file="CheckSession.jsp"%>
<%
//setHeader sample
response.setHeader("Cache-Control","no-cache");
response.setHeader("Pragma","no-cache");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META name="GENERATOR" content="IBM WebSphere Studio">
<TITLE>Usermanager Client on JSP Sample</TITLE>
<style>
	body,table,p,td,input,select{font-family:verdana;font-size:9pt;}
</style>
</HEAD>
<BODY>
<h3>UMClient Admin Functions-����Ա�ӿ�</h3>
<%


out.println("<li>Penname:" + ckPenName);
out.println("<li>UserId:" + ckUserId);
out.println("<li>UserType:" + ckUserType);
out.println("<li>Level:" + ckLevel);
//out.println("<li>Sex:" + ckSex);
//out.println("<li>Personal Service IP:" + ckPSIP);


int lRet = -1;
LogonTypeList logonTypeList = new LogonTypeList();
lRet = UMClient.AF_GetAllLogonTypeInfo(SSSESSIONID,ClientId,logonTypeList);
if (lRet!=0) out.println("Can not get LogonTypeList:" + lRet);
%>

<h4>����û���Ϣ</h4>
<li><a href="UM_AF_UserInit_FE.jsp">AF_UserInit</a> ����Աע��һ�����û�

<li>AF_ModInfo ����Ա�޸�����һ���û�����Ϣ��ֻ�б��������޸ģ�
<blockquote>
	<form name=f1 method=post action="UM_AF_ModInfo.jsp">
		Penname: <input type="text" name="PenName" value="" size=10>
		NewPenName: <input type="text" name="PenNameNew" value="" size=10>
		<input type="submit" value="�޸ı���">
	</form>
</blockquote>

<li>AF_ModUserLevel ����Ա�޸��û��ļ���
<blockquote>
	<form name=f1 method=post action="UM_AF_ModUserLevel.jsp">
		Penname: <input type="text" name="PenName" value="" size=10>
		UserLevel: <input type="text" size=1 name="UserLevel" value=""> (1-100)
		<input type="submit" value="�޸�Level">
	</form>
</blockquote>

<li>AF_ResetPasswd ����Ա�����û�������
<blockquote>
	<form name=f1 method=post action="UM_AF_ResetPasswd.jsp">
		Penname: <input type="text" name="PenName" value="" size=10>
		ResetType: <input type="radio" name="ResetType" value="1">�ַ����� <input type="radio" name="ResetType" value="2">��������
		<input type="submit" value="��������">
	</form>
</blockquote>

<li>AF_SetUserValid ����Ա�����û�����Ч��
<blockquote>
	<form name=f1 method=post action="UM_AF_SetUserValid.jsp">
		Penname: <input type="text" name="PenName" value="" size=10>
		Valid: <input type="radio" name="Valid" value="1">��Ч <input type="radio" name="Valid" value="0">��Ч
		<input type="submit" value="������Ч��">
	</form>
</blockquote>


<li>AF_GetUserSessionStatus  ����Ա�õ��û��ĻỰ״̬
<blockquote>
	<form name=f1 method=post action="UM_AF_GetUserSessionStatus.jsp">
		Penname: <input type="text" name="PenName" value="" size=10>
		<input type="submit" value="��ѯ�Ự״̬">
	</form>
</blockquote>

<li>AF_GetInfo ����Ա�õ��û���������Ϣ
<blockquote>
	<form name=f1 method=post action="UM_AF_GetInfo.jsp">
		Penname: <input type="text" name="PenName" value="" size=10>
		<input type="submit" value="����û���Ϣ">
	</form>
</blockquote>
<li>AF_GetPubInfo ����Ա�����û��ĵ�¼��Ϣ�õ��û�������Ϣ
<blockquote>
	<form name=f1 method=post action="UM_AF_GetPubInfo.jsp">

		LogonType(��¼����):
		<select name="LogonType">
			<%
			if (lRet == 0){
				LogonType logonType = new LogonType();
				for (int i=0; i<logonTypeList.Size(); i++){
					logonTypeList.Get(logonType,i);
					out.println("<option value=\"" + logonType.nLogonType + "\">" + logonType.nLogonType + "-" + logonType.sLogonTypeName + "</option>");
				}
			}
			%>
		</select>
		<br>
		LogonID(�����͵ĵ�¼��ʶ): <input type="text" name="LogonId" value=""> (��ѡ���һ�ֵ�¼����ʱ����¼����Ҫ���ӡ���¼���͡�);
		<input type="submit" value="����û�������Ϣ">
	</form>
</blockquote>
<li>AF_GetSecurityQuestion ����Ա�����û��ĵ�¼��Ϣ�õ��û��İ�ȫ����
<blockquote>
	<form name=f1 method=post action="UM_AF_GetSecurityQuestion.jsp">
		UserName: <input type="text" name="UserName" value="" size=10>
		UserType: <input type="text" name="UserType" value="none" size=6>
		<input type="submit" value="��ð�ȫ����">
	</form>
</blockquote>
<li>UM_AF_GetUserAuthorization  ����Աȡ���û���һ��Ȩ������
<blockquote>
	<form name=f1 method=post action="UM_AF_GetUserAuthorization.jsp">
		Penname: <input type="text" name="PenName" value="" size=10>
		<input type="submit" value="ȡ���û�Ȩ��">
	</form>
</blockquote>


<li><a href="UM_AF_GetOnlineNumber.jsp">AF_GetOnlineNumber</a> ����Ա�õ������û���

<h4>����Ա����</h4>
<li><a href="UM_AF_GetBasicRight.jsp">AF_GetBasicRight</a> ����Աȡ���Լ���Ȩ�޶����ϵ�Ȩ��״̬
<li><a href="UM_AF_GetBasicOperator.jsp">AF_GetBasicOperator</a> ����Աȡ�ÿ��Բ��������Ȩ�޶���Ĺ���Ա�б�(��������߹���Ա)


<h4>�û���¼���͹رպͿ�ͨ</h4>

<li><a href="UM_AF_GetAllLogonTypeInfo.jsp">AF_GetAllLogonTypeInfo</a> ����Ա������еĵ�¼�����б�</li>
<li><a href="UM_AF_GetAllLogonTypeInfo.jsp">AF_DelLogonType</a> ����Աɾ����¼����</li>
<li><a href="UM_AF_GetAllLogonTypeInfo.jsp">AF_AddLogonType</a> ����Ա���ӵ�¼����</li>

<li>AF_OpenLogonType ��ͨĳ���û��ĵ�¼����
<blockquote>
	<form name=f1 method=post action="UM_AF_OpenLogonType.jsp">
		Penname: <input type="text" name="PenName" value="">
		<!--UserId(18Ϊ���): <input type="text" name="UserId" value="">-->
		<br>
		LogonType(��¼���͵ı��):
		<select name="LogonType">
			<%
			if (lRet == 0){
				LogonType logonType = new LogonType();
				for (int i=0; i<logonTypeList.Size(); i++){
					logonTypeList.Get(logonType,i);
					out.println("<option value=\"" + logonType.nLogonType + "\">" + logonType.sLogonTypeName + "</option>");
				}
			}
			%>
		</select>
		<br>
		LogonTypeName(�õ�¼���͵ĵ�¼��): <input type="text" name="LogonID" value=""><br>
		<input type="submit" value="OpenLogonType">
	</form>
</blockquote>
<li>AF_CloseLogonType �ر�ĳ���û��ĵ�¼����
<blockquote>
	<form name=f1 method=post action="UM_AF_CloseLogonType.jsp">
		Penname: <input type="text" name="PenName" value="">
		<!--UserId(18Ϊ���): <input type="text" name="UserId" value="">-->
		<br>
		LogonType(��¼���͵ı��):
		<select name="LogonType">
			<%
			if (lRet == 0){
				LogonType logonType = new LogonType();
				for (int i=0; i<logonTypeList.Size(); i++){
					logonTypeList.Get(logonType,i);
					out.println("<option value=\"" + logonType.nLogonType + "\">" + logonType.nLogonType + "-" + logonType.sLogonTypeName + "</option>");
				}
			}
			%>
		</select>

		<br>
		LogonTypeName(�õ�¼���͵ĵ�¼��): <input type="text" name="LogonID" value=""><br>
		<input type="submit" value="CloseLogonType">
	</form>
</blockquote>

<h4>Ȩ�ޣ���ɫ�������</h4>
<li><a href="UM_AF_GetAllAuthorityObject.jsp">AF_GetAllAuthorityObject</a> ����Աȡ�����е�Ȩ���б�</li>
<li><a href="UM_AF_GetAllAuthorityObject.jsp">AF_DelAuthorityObject</a> ����Աɾ��Ȩ�޶���</li>
<li><a href="UM_AF_GetAllAuthorityObject.jsp">AF_AddAuthorityObject</a> ����Ա���Ȩ�޶���</li>

<br><br>
<li><a href="UM_AF_GetAllRole.jsp">AF_GetAllRole</a> ����Աȡ�����еĽ�ɫ�б�</li>
<li><a href="UM_AF_GetAllRole.jsp">AF_DelRole</a> ����Աɾ����ɫ����</li>
<li><a href="UM_AF_GetAllRole.jsp">AF_AddRole</a> ����Ա��ӽ�ɫ����</li>

<br><br>
<li><a href="UM_AF_GetAllGroup.jsp">AF_GetAllGroup</a> ����Աȡ�����еĽ�ɫ�б�</li>
<li><a href="UM_AF_GetAllGroup.jsp">AF_DelGroup</a> ����Աɾ����ɫ����</li>
<li><a href="UM_AF_GetAllGroup.jsp">AF_AddGroup</a> ����Ա��ӽ�ɫ����</li>

<h4><a href="default.jsp">������ͨ�ӿ�ҳ��</a></h4>
</BODY>
</HTML>
