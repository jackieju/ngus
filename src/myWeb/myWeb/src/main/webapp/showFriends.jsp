<%@ page language="java" contentType="text/html; charset=GB18030"
    pageEncoding="GB18030"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ page import="com.ngus.dataengine.DBConnection" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.Statement" %>
<%@ page import="java.sql.SQLException" %>
<%@ include file="checkSession.jsp" %>
<%
//setHeader sample
response.setHeader("Cache-Control","no-cache");
response.setHeader("Pragma","no-cache");
String penName = userPubInfo.getSPenName();
penName = penName.substring(0, penName.length()-1);
ResultSet rs1 = null;
ResultSet rs2 = null;
String friend = null;
int intPageSize = 10; //һҳ��ʾ�ļ�¼��
int rowCount; //��¼����
int totalPage; //��ҳ��
int currPage; //����ʾҳ��
String strPage;
int i;
//ȡ�ô���ʾҳ��
strPage = request.getParameter("page");
if(strPage==null){//������QueryString��û��page��һ����������ʱ��ʾ��һҳ����
	currPage = 1;
}else{//���ַ���ת��������
	currPage = Integer.parseInt(strPage);
	if(currPage<1) 
		currPage = 1;
}
String delFriend = request.getParameter("delPenName");

	
%>
<html:html>

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
try {
	Connection con=DBConnection.getConnection();
	Statement st1 = con.createStatement();
	Statement st2 = con.createStatement();
	Statement st3 = con.createStatement();
	if(delFriend!=null)//ɾ���ҵ�һ�����ѣ��Է������ҵĻ���������Է��Ķ���Ϊ�ĺ���
		st3.executeUpdate("delete from friendlist where (user1='"+penName+"' and user2='"+delFriend+"') or (user2='"+penName+"' and user1='"+delFriend+"')");
	rs1 = st1.executeQuery("select user1,user2 from friendlist where status=1 and (user1='"+penName+"'or user2='"+penName+"')");
	rs1.last();
	rowCount = rs1.getRow();//��õ�ǰ�кţ����ܵļ�¼��
	totalPage = (rowCount+intPageSize-1) / intPageSize;//������ҳ��
	rs1.first();
	if(currPage>totalPage) 	//��������ʾ��ҳ��
		currPage = totalPage;
	if(rowCount<=0){%>
		����ʱû�к��ѣ�
	<%}else{%>
		�����ڵĺ����ǣ�<br>
		<table width="100%" border="0" cellpadding="5" cellspacing="0" align="center">
				<tr>
					<th width="30%" valign="top" style="BORDER: #e6e8df 5px solid" bgcolor="#ffffff" align="left">����</th>
					<th width="30%" valign="top" style="BORDER: #e6e8df 5px solid" bgcolor="#ffffff" align="left">score</th>
					<th width="40%" valign="top" style="BORDER: #e6e8df 5px solid" bgcolor="#ffffff" align="left">�����¼ʱ��</th>
					<th width="40%" valign="top" style="BORDER: #e6e8df 5px solid" bgcolor="#ffffff" align="left"></th>
				</tr>
				<%
				//����¼ָ�붨λ������ʾҳ�ĵ�һ����¼��
				rs1.absolute((currPage-1) * intPageSize + 1);
				//��ʾ����
				i = 0;//��ǰҪ��ʾҳ���߼��к�
				String friendsNewLogonTime = null;
				String friendsPenName = null;
				int friendsScore = 0;
				while(i<intPageSize && !rs1.isAfterLast()){				
					if(rs1.getString("user1").equalsIgnoreCase(penName))//get the friend's penName of the user
						friend = rs1.getString("user2");
					else
						friend = rs1.getString("user1");
					rs2 = st2.executeQuery("select penName,score,newLogonTime from userextension where penName='"+friend+"'");
					if(rs2.next()){
						friendsPenName = rs2.getString("penName");
						friendsScore = rs2.getInt("score");
						friendsNewLogonTime = rs2.getString("newLogonTime");	
					}		
					%>
					<tr>
						<td width="60%" valign="top" style="BORDER: #e6e8df 5px solid" bgcolor="#ffffff" align="left">		
							<%=friendsPenName%>
						</td>
						<td width="40%" valign="top" style="BORDER: #e6e8df 5px solid" bgcolor="#ffffff" align="left">		
							<%=friendsScore%>
						</td>
						<td width="40%" valign="top" style="BORDER: #e6e8df 5px solid" bgcolor="#ffffff" align="left">		
							<%=friendsNewLogonTime%>
						</td>
						<td width="40%" valign="top" style="BORDER: #e6e8df 5px solid" bgcolor="#ffffff" align="left">		
							<a href="showFriends.jsp?delPenName=<%=friendsPenName%>">ɾ��</a>
						</td>
					</tr>
					<%i++;
					rs1.next();
				}%>
					<tr>
						<td>
							<form name="pageChange" id="pageChange"  method="get" action="showFriends.jsp" >
								��<%=currPage%>ҳ ��<%=totalPage%>ҳ ��<%=rowCount%>��
								<%if(currPage>1){%><a href="showFriends.jsp?page=<%=1%>">��ҳ</a><%}%>
								<%if(currPage>1){%><a href="showFriends.jsp?page=<%=currPage-1%>">��һҳ</a><%}%>
								<%if(currPage<totalPage){%><a href="showFriends.jsp?page=<%=currPage+1%>">��һҳ</a><%}%>
								<%if(totalPage>1&&currPage<totalPage){%><a href="showFriends.jsp?page=<%=totalPage%>">βҳ</a><%}%>
								����<input type="text" name="page" id="page" size="4" style="font-size:9px">ҳ
								<input type="submit" name="submit" id="submit" size="4" value="GO" style="font-size:9px" >
							</form>
						</td>
					</tr>
				</table>
			<%}%>	
		<%}catch (SQLException e) {
		out.println(e);
	}	
%>
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