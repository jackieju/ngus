<!-- Change Password -->
<%@ page language="java"%>
<%@ page contentType="text/html; charset=gb2312" %>

<%@ page import="com.stockstar.buos.um.*" %>

<%
//setHeader sample
response.setHeader("Cache-Control","no-cache");
response.setHeader("Pragma","no-cache");

String UserName = request.getParameter("UserName");
String Password = request.getParameter("Password");
String NewPwd = request.getParameter("NewPwd");
String NewPwdConf = request.getParameter("NewPwdConf");
String UserType = "none";


if (UserName == null || NewPwd.length()<6 || NewPwd.compareTo(NewPwdConf) != 0)  // no input or input has error
{

//out.println("UserName="+UserName);
%>

<HTML>
<HEAD>
<TITLE> �����޸� </TITLE>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel="stylesheet" href="lib/newstyle.css" type="text/css">
</HEAD>
<BODY BGCOLOR=#FFFFFF LINK=#336666 TEXT=#000000 ALINK=#CC0000 VLINK=#666666>
<CENTER>


<BR>
<!-- begin body -->

<FORM METHOD=POST ACTION="change_pwd.jsp">


<TABLE WIDTH=540 CELLPADDING=0 CELLSPACING=0 BORDER=0 bgcolor="#F7EFD6">
<TR>
 <TD WIDTH=540 height="29" background="images/green_bg1.jpg" class="white" width=100%> &nbsp; <B>�����޸�</B> 
	</TD>
 </TR>
 <TR>
 <TD>
	<p>
	
	<TABLE WIDTH=540 CELLPADDING=0 CELLSPACING=0 BORDER=0>
	
<%if (NewPwd != null && NewPwd.compareTo(NewPwdConf) != 0){%>

<TR BGCOLOR="#F7EFD6">
		<TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD>
		<TD VALIGN=Middle COLSPAN=3><FONT COLOR="#333333"><B><FONT COLOR=RED><IMG SRC="../images/aware.gif"> ��ȷ�����������롣</FONT></B></FONT></TD>
		<TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD>
		</TR>
<%}%>

		<TR BGCOLOR="#F7EFD6">
		<TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD>
		<TD VALIGN=Middle COLSPAN=3><FONT COLOR="#333333"><br><B>ֻҪԸ�⣬��������ʱ�޸��������롣</B><br><br></FONT></TD>
		<TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD>
		</TR>
		
		<!--
		<TR BGCOLOR="#F7EFD6">
		<TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD>
		<TD VALIGN=Middle WIDTH=81 NOWRAP><FONT COLOR="#333333">������</FONT></TD>
		<TD WIDTH=270><FONT COLOR="#000000"> ��<a href="log_in.asp?via=L2xvZ2luL2NoYW5nZV9wYXNzLmFzcA==">���µ�¼</a>��</TD>
		<TD BGCOLOR="#F7EFD6"><FONT COLOR="#333333"></FONT></TD>
		<TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD></TR>
		-->
		
		<TR BGCOLOR="#F7EFD6">
		<TD WIDTH=9 HEIGHT=4 NOWRAP>&nbsp; </TD>
		<TD WIDTH=9 HEIGHT=4 NOWRAP>&nbsp; </TD></TR>


		<TR BGCOLOR="#F7EFD6">
		<TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD>

		<TD VALIGN=Middle WIDTH=81 NOWRAP><FONT COLOR=#333333>�û���:</FONT></TD>
		<TD WIDTH=270><FONT COLOR="#000000"><INPUT SIZE=20 TYPE=text NAME=UserName maxlength="15" value=""></FONT></TD>
		<TD BGCOLOR=#F7EFD6><FONT COLOR=#333333>   </FONT></TD>
		<TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD>
		</TR>

		<TR BGCOLOR="#F7EFD6">
		<TD WIDTH=9 HEIGHT=4 NOWRAP>&nbsp; </TD>
		<TD WIDTH=9 HEIGHT=4 NOWRAP>&nbsp; </TD>
		</TR>

		<TR BGCOLOR="#F7EFD6">
		<TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD>

		<TD VALIGN=Middle WIDTH=81 NOWRAP><FONT COLOR=#333333>ԭ����:</FONT></TD>
		<TD WIDTH=270><FONT COLOR="#000000"><INPUT SIZE=20 TYPE=password NAME=Password maxlength="20" value=""></FONT></TD>
		<TD BGCOLOR=#F7EFD6><FONT COLOR=#333333>   </FONT></TD>
		<TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD>
		</TR>
		
		<TR BGCOLOR="#F7EFD6">
		<TD WIDTH=9 HEIGHT=4 NOWRAP>&nbsp; </TD>
		<TD WIDTH=9 HEIGHT=4 NOWRAP>&nbsp; </TD>
		</TR>

		<TR BGCOLOR="#F7EFD6">
		<TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD>



		<TD VALIGN=Middle WIDTH=81 NOWRAP><FONT COLOR=#333333>������</FONT></TD>
		<TD WIDTH=270><FONT COLOR="#000000"><INPUT SIZE=20 TYPE=password NAME=NewPwd maxlength="15" value="<%		if (NewPwd!=null)
		out.print(NewPwd);
		%>"></FONT></TD>
		<TD BGCOLOR=#F7EFD6><FONT COLOR=#333333>   </FONT>
<%if (NewPwd!=null && NewPwd.length()< 6) {%>
		<FONT COLOR=red>   �����볤������6λ! </FONT>
<%}%>	
	</TD>
		<TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD>
	


		<TR BGCOLOR="#F7EFD6">
		<TD WIDTH=9 HEIGHT=4 NOWRAP>&nbsp; </TD>
		<TD WIDTH=9 HEIGHT=4 NOWRAP>&nbsp; </TD>
		</TR>
	
	
		<TR BGCOLOR="#F7EFD6">
		<TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD>
		<TD VALIGN=Middle WIDTH=81 NOWRAP><FONT COLOR=#333333>������ȷ��</FONT></TD>
		<TD WIDTH=270><FONT COLOR="#000000"><INPUT SIZE=20 TYPE=password NAME=NewPwdConf maxlength="15" value="<%
		if (NewPwdConf!=null )
		out.print(NewPwdConf);
		%>">
		</FONT></TD>
		<TD BGCOLOR=#F7EFD6><FONT COLOR=#333333>   </FONT>
<%if (NewPwd!=null && NewPwd.compareTo(NewPwdConf) != 0) {%>
		<FONT COLOR=red>   ��ȷ������������! </FONT>
<%}%>
		</TD>
		<TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD>
		</TR>
		

		<TR BGCOLOR="#F7EFD6">
		<TD WIDTH=9 HEIGHT=5 NOWRAP>&nbsp; </TD>
		<TD WIDTH=9 HEIGHT=5 NOWRAP>&nbsp; </TD>
		</TR>

		<TR BGCOLOR="#F7EFD6">
		<TD WIDTH=9 HEIGHT=4 NOWRAP>&nbsp; </TD>
		<TD WIDTH=9 HEIGHT=4 NOWRAP>&nbsp; </TD>
		</TR>
		<TR BGCOLOR="#F7EFD6">
		<TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD>
		<TD ALIGN=right COLSPAN=3><FONT COLOR="#000000">

		<INPUT TYPE=BUTTON VALUE="����" onclick="window.navigate('default.asp')"> &nbsp; 
		<INPUT TYPE=SUBMIT NAME=continue VALUE="�޸��ҵ�����">

		</FONT></TD>
		<TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD></TR>
		<TR BGCOLOR="#F7EFD6">
		<TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD>
		<TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD></TR>
		</TABLE></TD>
	</TR>
	<TR><TD HEIGHT=1 BGCOLOR="#000066"><IMG SRC="../images/trans.gif" ALT="" WIDTH=1 HEIGHT=1 BORDER=0></TD></TR>
</TABLE>
</FORM>



<br>
<!-- end body -->
<BR>



</center>
</BODY>
</HTML>

<%}else{%>

<%

int lRet;
String SSSESSIONID = "";

String ClientId = request.getRemoteAddr();


Cookie[] cookies = request.getCookies();
Cookie cookie;
for(int i=0; i<cookies.length; i++) {
cookie = cookies[i];
if (cookie.getName().compareTo("SSSESSIONID")==0){
	SSSESSIONID = cookie.getValue();
	}
}

//out.println(SSSESSIONID);
if (SSSESSIONID.length()!=0){
	//out.println("Begin to checksession.");
	lRet = UMClient.UF_ChangePasswd(SSSESSIONID,ClientId,NewPwd,NewPwdConf);
	if (lRet == 0)
	{
		out.println("Change password succeeded.");
	}else
	{
		out.println("Change password failed, UsrMgr return " + lRet);
	}
	
}else{
	out.println("<font color=red>NO SESSIONID FOUND, CHECK SESSION CANCELLED.</font>");
}

}
%>
