<%@ page language="java" contentType="text/html; charset=GB18030"
    pageEncoding="GB18030"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page import="com.ngus.um.*, com.ngus.um.dbobject.*" %>
<%
//setHeader sample
	response.setHeader("Cache-Control","no-cache");
	response.setHeader("Pragma","no-cache");
	String userName = request.getParameter("userName");
	String penName = request.getParameter("penName");
	//String userType = request.getParameter("userType");
	String errocode1 = request.getParameter("errocode1");
	String errocode2 = request.getParameter("errocode2");
	if(userName!=null && penName!=null && userName.length()!=0 && penName.length()!=0 && errocode1 == null){
		//request.getSession().putValue("userName", userName);
		//request.getSession().putValue("penName", penName);
		//StringObj sugUserName1 = new StringObj();
		//StringObj sugUserName2 = new StringObj();
		//StringObj sugPenName1 = new StringObj();
		//StringObj sugPenName2 = new StringObj();
		//int ret1 = UMClient.UF_CheckUserName(userName, userType, sugUserName1, sugUserName2);//-50005��ʾum���Ӳ��ɹ�
		//int ret2 = UMClient.UF_CheckPenName(penName, sugPenName1, sugPenName2);//-50300��ʾ��ѡ���penName�ѱ�ע��
		//if (ret1 != 0 || ret2 != 0 )//ret1 != 0 && ret2 != 0 
		//	response.sendRedirect("reg1.jsp?errocode1="+ret1+"&errocode2="+ret2+"&userName="+userName+"&penName="+penName);
		//else
		//	response.sendRedirect("reg2.jsp?userName="+userName+"&penName="+penName+"&userType="+userType);
		
		// check username and nickname
		int ret = new UMClient().checkDuplicate(userName, penName, null);
		if (ret == 0)
			response.sendRedirect("reg2.jsp?userName="+userName+"&penName="+penName);
		else 
			response.sendRedirect("reg1.jsp?errocode="+ret+"&userName="+userName+"&penName="+penName);
	}
%>
<HTML>
<HEAD>
<TITLE> - ע�� </TITLE>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel="stylesheet" href="lib/style.css" type="text/css">
<script language="Javascript">
	function syncpenname(){
		f1.PenName.value = f1.UserName.value;
	}
</script>

</HEAD>
<BODY BGCOLOR=#FFFFFF LINK=#336666 TEXT=#000000 ALINK=#CC0000 VLINK=#666666>
<center>


<BR>
<!-- begin body -->
<FORM METHOD=POST ACTION="reg1.jsp" NAME="f1"><TABLE WIDTH=540 CELLPADDING=0 CELLSPACING=0 BORDER=0 bgcolor="#F7EFD6">
<TR>
 <TD WIDTH=540 height="29" background="images/green_bg1.jpg" class="white"> &nbsp; <B>�û�ע��</B></TD> 
 </TR> 
 <TR> 
 <TD><TABLE WIDTH=540 CELLPADDING=0 CELLSPACING=0 BORDER=0> 
 	<input type=hidden name="userType" value="none">
	  <TR BGCOLOR="#F7EFD6"> 
	  <TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD> 
	  <TD VALIGN=bottom ALIGN=center COLSPAN=3><FONT COLOR="#333333"><br><B><FONT COLOR=RED>�Ѿ�ע���ˣ�  </FONT><A STYLE="text-decoration: underline" HREF="log_in.jsp?via="><FONT size=3>����˴�</A> ��¼��</FONT></TD> 
	  <TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD> 
	  </TR> 
 
	  <TR BGCOLOR="#F7EFD6"> 
	  <TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD> 
	  <TD VALIGN=Middle COLSPAN=3><FONT COLOR="#333333"><BR><B><FONT COLOR=BLUE>��һ����</FONT><FONT COLOR=BLUE>  �����û����ͱ�����</FONT></FONT><br><br></TD> 
	  <TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD> 
	  </TR> 
	  <TR BGCOLOR="#F7EFD6"> 
	  <TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD> 
 
	  <TD VALIGN=Middle WIDTH=135 NOWRAP align=right><FONT COLOR=#333333>�������û�����</FONT></TD> 
	  <TD WIDTH=189><FONT COLOR="#000000"><INPUT TYPE=TEXT NAME=userName maxlength="16" value="" onblur="syncpenname();"></FONT></TD> 
	  <td></td>
	   <TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD> 
	  </TR> 
	  <TR BGCOLOR="#F7EFD6"> 
	  <TD WIDTH=9 HEIGHT=4 NOWRAP></TD> 
	  <TD WIDTH=9 HEIGHT=4 NOWRAP></TD> 
	  <TD BGCOLOR=#FFF8DC><FONT COLOR=#333333> �Ϸ����û���Ӧ����Ӣ����ĸ��ͷ����Ӣ����ĸ�����֡��»�����ɣ�����Ϊ3-16λ�� </FONT></TD> 
	  </TR> 
	  
	  <TR BGCOLOR="#F7EFD6"> 
	  <TD WIDTH=9 HEIGHT=14 NOWRAP></TD> 
	  <TD WIDTH=9 HEIGHT=4 NOWRAP></TD> 
	  </TR> 
	  <TR BGCOLOR="#F7EFD6"> 
	  <TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD> 

	  <TD VALIGN=Middle WIDTH=135 NOWRAP align=right><FONT COLOR=#333333>�����������</FONT></TD>
	  <TD WIDTH=189><FONT COLOR="#000000"><INPUT TYPE=TEXT NAME=penName maxlength="16" value="" %>></FONT></TD> 
	  <td></td>
	  <TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD> 
	  </TR> 
	  <TR BGCOLOR="#F7EFD6"> 
	  <TD WIDTH=9 HEIGHT=4 NOWRAP></TD> 
	  <TD WIDTH=9 HEIGHT=4 NOWRAP></TD> 
	  <TD BGCOLOR=#FFF8DC><FONT COLOR=#333333> �Ϸ��ı������԰������ģ�Ӣ���ַ������֡� </FONT></TD> 
	  
	  </TR> 
	  <TR BGCOLOR="#F7EFD6"> 
	  <TD WIDTH=9 HEIGHT=14 NOWRAP></TD> 
	  <TD WIDTH=9 HEIGHT=4 NOWRAP></TD> 
	  </TR> 
	  <TR BGCOLOR="#F7EFD6"> 
	  <TD WIDTH=9 HEIGHT=4 NOWRAP></TD> 
	  <TD WIDTH=9 HEIGHT=4 NOWRAP></TD> 
	  
	  <TD><b>ΪʲôҪ���ñ�����</b> Ϊ�˸��ߵİ�ȫ�ԣ����ǽ��û����ͱ����ֱ����û������������Ϊ��¼ʱ�ı�ʶ������������Ϊ��վ�Ϲ�����ʾ��һ�����š�����Ϊ����ߵİ�ȫ�ԣ�����������������Ϊ��ͬ���û�����</TD> 
	  </TR> 
	  	  <TR BGCOLOR="#F7EFD6"> 
	  <TD WIDTH=9 HEIGHT=14 NOWRAP></TD> 
	  <TD WIDTH=9 HEIGHT=4 NOWRAP></TD> 
	  </TR> 
	 
	  <%if(errocode1!=null){
		  if(errocode1.equalsIgnoreCase("-50300")){
			if(errocode2.equalsIgnoreCase("-50300")){%>
				  <TR BGCOLOR="#F7EFD6"> 
	  				<TD WIDTH=9 HEIGHT=4 NOWRAP></TD> 
	  				<TD WIDTH=9 HEIGHT=4 NOWRAP></TD> 
	  				<td><font color=red>�����û���<%=userName %>�Ѿ���ע����ˣ������һ����</font></td>
	  				</tr>
	  				<TR BGCOLOR="#F7EFD6"> 
	  				<TD WIDTH=9 HEIGHT=4 NOWRAP></TD> 
	  				<TD WIDTH=9 HEIGHT=4 NOWRAP></TD> 
	  				<td><font color=red>���ı���<%=penName %>�Ѿ���ע���ˣ������һ����</font></td>
	  				</tr>
	  	  <% }else{ %>	
	  	  	 	  <TR BGCOLOR="#F7EFD6"> 
	  				<TD WIDTH=9 HEIGHT=4 NOWRAP></TD> 
	  				<TD WIDTH=9 HEIGHT=4 NOWRAP></TD> 
	  				<td><font color=red>�����û���<%=userName %>�Ѿ���ע����ˣ������һ��</font>��</td>
	  				</tr>
	  	  <%} %>
		<%}else {
	   			if(errocode2.equalsIgnoreCase("-50300")){ %>
					<TR BGCOLOR="#F7EFD6"> 
	  				<TD WIDTH=9 HEIGHT=4 NOWRAP></TD> 
	  				<TD WIDTH=9 HEIGHT=4 NOWRAP></TD> 
	  				<td><font color=red>���ı���<%=penName %>�Ѿ���ע���ˣ������һ����</font></td>
	  				</tr>
				<%}else 
					if(errocode1.equalsIgnoreCase("-50005") && errocode2.equalsIgnoreCase("-50005")){%>
						<TR BGCOLOR="#F7EFD6"> 
	  					<TD WIDTH=9 HEIGHT=4 NOWRAP></TD> 
	  					<TD WIDTH=9 HEIGHT=4 NOWRAP></TD> 
						<td><font color=red>um���Ӳ��ɹ������Ժ����ԡ�</font></td>
						</tr>
					<%} %>
			<%} 
		}else
			if(userName!=null&&penName!=null&&(userName.length()==0||penName.length()==0)){%>
				
				<TR BGCOLOR="#F7EFD6"> 
					<TD WIDTH=9 HEIGHT=4 NOWRAP></TD> 
					<TD WIDTH=9 HEIGHT=4 NOWRAP></TD> 
					<td><font color=red>�������������û����ͱ���!</font></td>
				</tr>
			<%} %>
	  <TR BGCOLOR="#F7EFD6"> 
	  <TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD> 
	  <TD ALIGN=right COLSPAN=3><FONT COLOR="#000000"><FONT FACE=Arial COLOR="#000000"><INPUT TYPE=SUBMIT NAME=continue VALUE="��������ڶ���"></FONT></FONT></TD> 
	  <TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD> 
	  </TR> 
	  <TR BGCOLOR="#F7EFD6"> 
	  <TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD> 
	  <TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD> 
	  </TR> 
	  <TR BGCOLOR="#F7EFD6"> 
	  <TD WIDTH=9 HEIGHT=5 NOWRAP>&nbsp; </TD> 
	  <TD WIDTH=9 HEIGHT=5 NOWRAP>&nbsp; </TD> 
	  </TR> 
	  </TABLE></TD> 
	</TR> 
	<TR><TD HEIGHT=1 BGCOLOR="#000066"><IMG SRC="images/trans.gif" ALT="" WIDTH=1 HEIGHT=1 BORDER=0></TD></TR>
</TABLE>
<INPUT TYPE=HIDDEN NAME="agree" VALUE="I AGREE"><INPUT TYPE=HIDDEN NAME="via" VALUE=""> 
</FORM> 
<br> 
<!-- end body --> 
<BR> 


</center> 
</BODY> 
</HTML> 
