<%@ page language="java" contentType="text/html; charset=GB18030"
    pageEncoding="GB18030"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page import="com.ngus.message.MessageEngine" %>
<%@ page import="com.ngus.message.MessageObject" %>
<%@ page import="com.ngus.dataengine.DBConnection" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.Statement" %>
<%@ page import="java.util.LinkedList" %>
<%@ page import="java.util.List" %>
<%@ page import="java.sql.ResultSet" %>
<%@ include file="checkSession.jsp" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<html:html>
<%

Connection con = DBConnection.getConnection();
Statement st = con.createStatement();
ResultSet rs = null;
int rowCount; //��¼����
int totalPage; //��ҳ��
int currPage = 0; //����ʾҳ��
int intPageSize = 10; //һҳ��ʾ�ļ�¼��
String strPage;
//ȡ�ô���ʾҳ��
strPage = request.getParameter("page");
if(strPage==null){//������QueryString��û��page��һ����������ʱ��ʾ��һҳ����
	currPage = 1;
}else{//���ַ���ת��������
	currPage = Integer.parseInt(strPage);
	if(currPage<1) 
		currPage = 1;
}
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="css/basic.css" rel="stylesheet" type="text/css" media="all" />
</head>

<body>
<center>
<jsp:include page="headPage.jsp" />

<div class="boxwrap">
<table border="0" width="100%" style="border-bottom:1px solid #808080;">
	<tr>
		<td align="left"><img src="#" />&nbsp;<img src="#" />&nbsp;<img
			src="#" />&nbsp;<img src="#" />&nbsp;<img src="#" />&nbsp;<img
			src="#" /></td>
		<td align="right" valign="bottom"></td>
	</tr>
</table>
<div class="leftbox"><bean:parameter id="arg1" name="parentID" value="" />
<br />
<%
String penName = userPubInfo.getSPenName();
penName = penName.substring(0, penName.length()-1);
String userId = userPubInfo.getSUserId();
userId = userId.substring(0, userId.length()-1);

String acc = request.getParameter("acc");
String penNamePoster = request.getParameter("penNamePoster");

if(acc!=null){
	if(acc.equalsIgnoreCase("1")){
		st.executeUpdate("update friendlist set status=1 where user1='"+penNamePoster+"' and user2='"+penName+"'");
		//ȷ�Ϻ�������ݿ������������Է��ļ�¼����δͨ����֤��ɾ���������û�о���Ч��
		st.executeUpdate("delete from friendlist where user1='"+penName+"' and user2='"+penNamePoster+"' and status=0 ");
	}
		if(acc.equalsIgnoreCase("0"))
		st.executeUpdate("delete from friendlist where user1='"+penNamePoster+"' and user2='"+penName+"'");
		
}
List<MessageObject> li = MessageEngine.instance().listReceiveMsg(userId);
LinkedList<MessageObject> msg = new LinkedList();
int i = 0;
while(i<li.size()){
	
	if(li.get(i).title.equalsIgnoreCase("����")){
		//��ʾ��Щ��û�еõ��ظ�������
		UserPubInfo userPubInfo1 = new UserPubInfo();
		UMClient.UF_GetPubInfoByUserId(li.get(i).getPostUserId(), userPubInfo1);
		String tPenName = userPubInfo.getSPenName();
		tPenName = tPenName.substring(0, tPenName.length()-1);
		rs = st.executeQuery("select * from friendlist where user1='"+tPenName+"' and user2='"+penName+"' and status=0");
		if(rs.next())
			msg.add(li.get(i));
	}
		
	i++;
}
rowCount = msg.size();//��õ�ǰ�кţ����ܵļ�¼��
totalPage = (rowCount+intPageSize-1) / intPageSize;//������ҳ��
if(currPage>totalPage) 	//��������ʾ��ҳ��
	currPage = totalPage;
if(rowCount<=0){%>
	����ʱû����Ϣ!
<%}else{%>
	�����û��������Ϊ���ѣ�<br>
	<table width="100%" border="0" cellpadding="5" cellspacing="0" align="center">
		<tr>
			<th width="60%" valign="top" style="BORDER: #e6e8df 5px solid" bgcolor="#ffffff" align="left">����</th>
			<th width="40%" valign="top" style="BORDER: #e6e8df 5px solid" bgcolor="#ffffff" align="left"></th>
			<th width="60%" valign="top" style="BORDER: #e6e8df 5px solid" bgcolor="#ffffff" align="left"></th>
		</tr>
		<%
		//����¼ָ�붨λ������ʾҳ�ĵ�һ����¼��
		int recNum = ((currPage-1) * intPageSize + 1);
		//��ʾ����
		i = 0;//��ǰҪ��ʾҳ���߼��к�
		String newLogonTime;
		UserPubInfo userPubInfo1 = new UserPubInfo();
		while(i<intPageSize && recNum<=rowCount){
			//UMClient.UF_GetPubInfoByUserId(msg.get(recNum).postUserId, userPubInfo1);
			//penNamePoster = userPubInfo1.getSPenName();
			//penNamePoster = penNamePoster.substring(0, penNamePoster.length()-1);	
			penNamePoster = "gfgef";
			%>
			<tr>
				<td width="60%" valign="top" style="BORDER: #e6e8df 5px solid" bgcolor="#ffffff" align="left">		
					<%=penNamePoster%>
				</td>
				<td width="40%" valign="top" style="BORDER: #e6e8df 5px solid" bgcolor="#ffffff" align="left">		
					<a href="listRecMsg.jsp?acc=1&penNamePoster=<%=penNamePoster%>">��������</a>
				</td>
				<td width="40%" valign="top" style="BORDER: #e6e8df 5px solid" bgcolor="#ffffff" align="left">		
					<a href="listRecMsg.jsp?acc=0&penNamePoster=<%=penNamePoster%>">�ܾ�</a>
				</td>
			</tr>
			<%i++;
			recNum++;
		}%>
		<tr>
			<td>
				<form name="pageChange" id="pageChange"  method="get" action="searchUser.jsp" >
					��<%=currPage%>ҳ ��<%=totalPage%>ҳ ��<%=rowCount%>��
					<%if(currPage>1){%><a href="searchUser.jsp?page=<%=1%>">��ҳ</a><%}%>
					<%if(currPage>1){%><a href="searchUser.jsp?page=<%=currPage-1%>">��һҳ</a><%}%>
					<%if(currPage<totalPage){%><a href="searchUser.jsp?page=<%=currPage+1%>">��һҳ</a><%}%>
					<%if(totalPage>1&&currPage<totalPage){%><a href="searchUser.jsp?page=<%=totalPage%>>">βҳ</a><%}%>
					����<input type="text" name="page" id="page" size="4" style="font-size:9px">ҳ
					<input type="submit" name="submit" id="submit" size="4" value="GO" style="font-size:9px" >
				</form>
			</td>
		</tr>
	</table>

<%} %>
</div>

<div class="rightbox"> <br/>	
<table bgcolor="#eaeaea" border="0" cellpadding="5px" cellspacing="0"
	width="100%" >
	<tr>
		<td style="font-size:14px;" align="left"><strong><a href="showFriends.jsp">�ҵĺ���</a></strong></td>
	</tr>
	<tr>
		<td style="font-size:14px;" align="left"><strong><a href="listRecMsg.jsp">�յ�����Ϣ</a></strong></td>
	</tr>
</table>
<table bgcolor="#eaeaea" border="0" cellpadding="5px" cellspacing="0"
	width="100%">
	<tr>
		<td style="font-size:14px;" align="left"><strong>�������</strong></td>
	</tr>
	<tr>
		<td align="left">
		<ul type="disc">
			<li align="left">��������ʼ����</li>
			<li align="left">���������Ӷ�RSS��֧��</li>
			<li align="left">������Ŀǰ��֧��Firefox�������������������뵽<a href="#">firefox�����뽨��</a></li>
		</ul>
		</td>
	</tr>
</table>
<br />
<table width="100%" border="0" cellpadding="5px" bgcolor="#f2f2f9">
	<tr>
		<td class="rt" align="left">��Ҫ����</td>
	</tr>
	<tr>
		<td align="left">�����ֲ��������֣������Լ�ϲ�����ղأ�</td>
	</tr>
	<tr>
		<td class="rt" align="left">�����ҵľ�����</td>
	</tr>
	<tr>
		<td align="left">�����﷢�ֺ͹���������֣����Ѻͷ��ţ�</td>
	</tr>
	<tr>
		<td class="rt" align="left">����ղ�</td>
	</tr>
	<tr>
		<td align="left">�쿴��������ղغ���������</td>
	</tr>
	<tr>
		<td class="rt" align="left">�������</td>
	</tr>
	<tr>
		<td align="left">�μ������Ȥ�����ۣ��鿴���»���</td>
	</tr>
	<tr>
		<td align="left"><img src="#" />�˽���ࡵ��</td>
	</tr>
</table>
<br />
<table border="0" cellpadding="5px" cellspacing="0" width="100%"
	bgcolor="#f2f9f2">
	<tr>
		<td align="left" class="gt" colspan="2">������������</td>
	</tr>
	<tr style="border-bottom:1px dotted #808080;">
		<td align="left"><img src="#" /> night prayer</td>
		<td align="left"><img src="#" /> ĳĳ��</td>
	</tr>
	<tr style="border-bottom:1px dotted #808080;">
		<td align="left"><img src="#" /> night prayer</td>
		<td align="left"><img src="#" /> ĳĳ��</td>
	</tr>
	<tr style="border-bottom:1px dotted #808080;">
		<td align="left"><img src="#" /> night prayer</td>
		<td align="left"><img src="#" /> ĳĳ��</td>
	</tr>
	<tr style="border-bottom:1px dotted #808080;">
		<td align="left"><img src="#" /> night prayer</td>
		<td align="left"><img src="#" /> ĳĳ��</td>
	</tr>
	<tr style="border-bottom:1px dotted #808080;">
		<td align="left"><img src="#" /> night prayer</td>
		<td align="left"><img src="#" /> ĳĳ��</td>
	</tr>
	<tr style="border-bottom:1px dotted #808080;">
		<td align="left"><img src="#" /> night prayer</td>
		<td align="left"><img src="#" /> ĳĳ��</td>
	</tr>
</table>
</div>
<br>
<br>
<div class="footer">
<p align="left">����ڤ�뻨��0.0045��</p>
<br />
<table border="0" cellspacing="0" width="100%">
	<tr>
		<td width="40%" align="left">
		��������&nbsp;&nbsp;|&nbsp;&nbsp;��˽����&nbsp;&nbsp;|&nbsp;&nbsp;ʹ������&nbsp;&nbsp;|&nbsp;&nbsp;����
		</td>
		<td width="40%"><img src="#" />&nbsp;<img src="#" />&nbsp;<img
			src="#" />&nbsp;<img src="#" />&nbsp;<img src="#" />&nbsp;<img
			src="#" />&nbsp;<img src="#" />&nbsp;<img src="#" /></td>
		<td rowspan="2"><img src="#" /></td>
	</tr>
	<tr>
		<td colspan="2">copyrigth&copy; all rights reserved</td>
	</tr>
</table>
</div>
</div>
</center>
</body>
</html:html>