<!-- User Register step1 failed-->
<%@ page language="java"%>
<%@ page contentType="text/html; charset=gb2312" %>

<%@ page import="com.stockstar.buos.um.*" %>

<%
//int type = (new Integer(request.getParameter("type"))).intValue();
String SugUserName1 = request.getParameter("SugUserName1");
String SugUserName2 = request.getParameter("SugUserName2");
String SugPenName1 = request.getParameter("SugPenName1");
String SugPenName2 = request.getParameter("SugPenName2");
String UserName = request.getParameter("UserName");
int ret1 = (new Integer(request.getParameter("ret1"))).intValue();
int ret2 = (new Integer(request.getParameter("ret2"))).intValue();
out.println("SugUserName1="+SugUserName1+", SugUserName2=" + SugUserName2 + ", SugPenName1=" + SugPenName1 + ", SugPenName2="
+ SugPenName2);

%>
<HTML>
<HEAD>
<TITLE> 注册 </TITLE>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel="stylesheet" href="lib/style.css" type="text/css">
</HEAD>
<BODY BGCOLOR=#FFFFFF LINK=#336666 TEXT=#000000 ALINK=#CC0000 VLINK=#666666>
<CENTER>


<BR>
<!-- begin body -->
<FORM METHOD=POST ACTION="reg2.jsp" NAME="_RG_Step1_2">
<TABLE WIDTH=540 CELLPADDING=0 CELLSPACING=0 BORDER=0 bgcolor="#F7EFD6">
<TR>
 <TD WIDTH=540 height="29" background="images/green_bg1.jpg" class="white"> &nbsp; <B>重新选择用户名</B></TD> 
 </TR> 
 <TR>
 <TD><TABLE WIDTH=540 CELLPADDING=0 CELLSPACING=0 BORDER=0>
 <%if (ret1 == ErrorCodeMap.UMLIB_ERR_SUGGEST_NAME) {%>
		<TR BGCOLOR="#F7EFD6">
		<TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD>
		<TD VALIGN=Middle COLSPAN=3><FONT SIZE=2 COLOR="#333333"><IMG SRC="images/aware.gif"><B><FONT COLOR=red><font color=blue>您选择的用户名 <I><%=UserName%></I> 已被他人使用。</font><br><font color=blue>您选择的笔名 <I><%=UserName%></I> 已被他人使用。</font></FONT><br><br></FONT></TD>
		<TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD>
		</TR>

		<TR BGCOLOR="#F7EFD6">
		<TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD>
		<TD VALIGN=Middle COLSPAN=3><FONT SIZE=2 COLOR="#333333"><B>请从下列未被使用的用户名中选择一个或重新输入您的用户名。</B><br></TD>
		<TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD>
		</TR>

		<TR BGCOLOR="#F7EFD6">
		<TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD>
		<TD ALIGN=center><FONT SIZE=2 COLOR="#000000"><FONT SIZE=2 COLOR="#000000"><INPUT CHECKED TYPE=RADIO NAME=UserName VALUE=<%=SugUserName1%>></FONT></FONT></TD>
		<TD WIDTH=270 BGCOLOR="#F7EFD6"><FONT SIZE=2 COLOR="#333333"><%=SugUserName1%></FONT></TD>
		<TD VALIGN=Middle><FONT SIZE=1 COLOR="#333333">&nbsp; </FONT></TD>
		<TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD>
		</TR>
		<TR BGCOLOR="#F7EFD6">
		<TD WIDTH=9 HEIGHT=4 NOWRAP>&nbsp; </TD>
		<TD WIDTH=9 HEIGHT=4 NOWRAP>&nbsp; </TD>
		</TR>

		<TR BGCOLOR="#F7EFD6">
		<TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD>
		<TD ALIGN=center><FONT SIZE=2 COLOR="#000000"><FONT SIZE=2 COLOR="#000000"><INPUT TYPE=RADIO NAME=UserName VALUE=<%=SugUserName2%>></FONT></FONT></TD>
		<TD WIDTH=270 BGCOLOR="#F7EFD6"><FONT SIZE=2 COLOR="#333333"><%=SugUserName2%></FONT></TD>
		<TD VALIGN=Middle><FONT SIZE=1 COLOR="#333333">&nbsp; </FONT></TD>
		<TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD>
		</TR>
		<TR BGCOLOR="#F7EFD6">
		<TD WIDTH=9 HEIGHT=4 NOWRAP>&nbsp; </TD>
		<TD WIDTH=9 HEIGHT=4 NOWRAP>&nbsp; </TD>
		</TR>

		<TR BGCOLOR="#F7EFD6">
		<TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD>
		<TD ALIGN=center><FONT SIZE=2 COLOR="#000000"><FONT SIZE=2 COLOR="#000000"><INPUT  TYPE=RADIO NAME=UserName VALUE="Input"></FONT></FONT></TD>
		<TD WIDTH=270 BGCOLOR="#F7EFD6"><FONT SIZE=2 COLOR="#333333">输入您的用户名：&nbsp; &nbsp; <br><INPUT TYPE=TEXT NAME=UserName SIZE=20 VALUE=></FONT></TD>
		<TD BGCOLOR="#FFF8DC"><FONT SIZE=1 COLOR="#000000"><FONT SIZE=2> 合法的用户名应该以英文字母开头，由英文字母或数字、下划线组成，用户名3-16位。  </FONT></FONT></TD>
		<TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD>
		</TR>

		<TR BGCOLOR="#F7EFD6">
		<TD WIDTH=9 HEIGHT=5 NOWRAP>&nbsp; </TD>
		<TD WIDTH=9 HEIGHT=5 NOWRAP>&nbsp; </TD>
		</TR>
<%}else out.println("UserMgr error: " + ret1);%>
 <%if (ret2 == ErrorCodeMap.UMLIB_ERR_SUGGEST_NAME) {%>
		<TR BGCOLOR="#F7EFD6">
		<TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD>
		<TD VALIGN=Middle COLSPAN=3><FONT SIZE=2 COLOR="#333333"><B>请从下列未被使用的笔名中选择一个或重新输入您的笔名。</B><br></FONT></TD>
		<TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD>
		</TR>

		<TR BGCOLOR="#F7EFD6">
		<TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD>
		<TD ALIGN=center><FONT SIZE=2 COLOR="#000000"><FONT SIZE=2 COLOR="#000000"><INPUT TYPE=RADIO CHECKED NAME=PenName VALUE=<%=SugPenName1%>></FONT></FONT></TD>
		<TD WIDTH=270 BGCOLOR="#F7EFD6"><FONT SIZE=2 COLOR="#333333"><%=SugPenName1%></FONT></TD>
		<TD VALIGN=Middle><FONT SIZE=1 COLOR="#333333">&nbsp; </FONT></TD>
		<TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD>
		</TR>
		
		<TR BGCOLOR="#F7EFD6">
		<TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD>
		<TD ALIGN=center><FONT SIZE=2 COLOR="#000000"><FONT SIZE=2 COLOR="#000000"><INPUT TYPE=RADIO NAME=PenName VALUE=<%=SugPenName2%>></FONT></FONT></TD>
		<TD WIDTH=270 BGCOLOR="#F7EFD6"><FONT SIZE=2 COLOR="#333333"><%=SugPenName2%></FONT></TD>
		<TD VALIGN=Middle><FONT SIZE=1 COLOR="#333333">&nbsp; </FONT></TD>
		<TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD>
		</TR>
				
		<TR BGCOLOR="#F7EFD6">
		<TD WIDTH=9 HEIGHT=4 NOWRAP>&nbsp; </TD>
		<TD WIDTH=9 HEIGHT=4 NOWRAP>&nbsp; </TD>
		</TR>

		<TR BGCOLOR="#F7EFD6">
		<TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD>
		<TD ALIGN=center><FONT SIZE=2 COLOR="#000000"><FONT SIZE=2 COLOR="#000000"><INPUT  TYPE=RADIO NAME=PenName VALUE="Input"></FONT></FONT></TD>
		<TD WIDTH=270 BGCOLOR="#F7EFD6"><FONT SIZE=2 COLOR="#333333">输入您的笔名：&nbsp; &nbsp; <br><INPUT TYPE=TEXT NAME=PenName SIZE=20 VALUE=></FONT></TD>
		<TD BGCOLOR="#FFF8DC"><FONT SIZE=1 COLOR="#000000"><FONT SIZE=2> 合法的笔名应该以英文字母开头，由英文字母或数字、下划线组成，用户名3-16位。  </FONT></FONT></TD>
		<TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD>
		</TR>
 
 <%}else out.println("UserMgr error: " + ret1);%>
 
		<TR BGCOLOR="#F7EFD6">
		<TD WIDTH=9 HEIGHT=5 NOWRAP>&nbsp; </TD>
		<TD WIDTH=9 HEIGHT=5 NOWRAP>&nbsp; </TD>
		</TR>
		<TR BGCOLOR="#F7EFD6"><TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD>
		<TD VALIGN=Middle COLSPAN=3><FONT SIZE=2 COLOR="#333333"><br>用户名 "<%=UserName%>" 就代表您吗？  如果您已经注册过，请<A STYLE="text-decoration: underline" HREF="log_in.jsp?via=">点击此处</A>登录 。<br></FONT></TD> 
		<TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD> 
		</TR> 
		<TR BGCOLOR="#F7EFD6"> 
		<TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp;  </TD> 
		<TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp;  </TD> 
		</TR> 
		<TR BGCOLOR="#F7EFD6"> 
		<TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD> 
		<TD ALIGN=right COLSPAN=3><FONT SIZE=2 COLOR="#000000"><FONT SIZE=2 COLOR="#000000"><INPUT TYPE=SUBMIT NAME=submit VALUE="继 续"></FONT></FONT></TD> 
		<TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD> 
		</TR> 
		<TR BGCOLOR="#F7EFD6"> 
		<TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD> 
		<TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD> 
		</TR> 
		</TABLE></TD> 
	</TR> 
	<TR><TD HEIGHT=1 BGCOLOR="#000066"><IMG SRC="images/trans.gif" ALT="" WIDTH=1 HEIGHT=1 BORDER=0></TD></TR>
</TABLE>
<!--
<INPUT TYPE=HIDDEN NAME="UserName" VALUE=<%//=UserName%>> 
<INPUT TYPE=HIDDEN NAME="PenName" VALUE=<%//=PenName%>> 
<INPUT TYPE=HIDDEN NAME="via" VALUE=aHR0cDovL215LnN0b2Nrc3Rhci5jb20vc2NyaXB0cy9teXN0b2Nrc3Rhci5kbGw/bG9naW4=> 
-->
</FORM> 
<br> 
<!-- end body --> 
<BR> 

 

 
</center> 
</BODY> 
</HTML> 
<%out.clear();out.println("cleared");%>