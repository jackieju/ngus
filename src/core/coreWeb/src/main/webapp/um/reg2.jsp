<!-- User Register step2 -->
<%@ page language="java"%>
<%@ page contentType="text/html; charset=gb2312" %>

<%@ page import="com.stockstar.buos.um.*" %>

<%
//setHeader sample
response.setHeader("Cache-Control","no-cache");
response.setHeader("Pragma","no-cache");
int lRet;
int nStep = 0;
boolean bPwdErr = false;

String UserName = request.getParameter("UserName");
String PenName = request.getParameter("PenName");
String UserType = request.getParameter("UserType");
String sSecurityAnswer = null;
String Pwd1 = request.getParameter("Password");
String Pwd2 = request.getParameter("RePassword");
//String checkSavePW = request.getParameter("checkSavePW");
if (Pwd1 == null)
{
	nStep = 1;
	
}
else
{
nStep =  2;
if (Pwd1.compareTo(Pwd2) != 0)
	bPwdErr = true;


String sSex = request.getParameter("Sex");
String sPwdHint = request.getParameter("PasswordHint");
if (sPwdHint == null)
	sPwdHint = new String("");
sSecurityAnswer = request.getParameter("QuestionAnswer");
Integer SecurityQuestionId = new Integer(request.getParameter("QuestionId"));

out.println(
"UserName="+UserName + 
", PenName=" + PenName + ", Pwd=" + Pwd1 + ", RePwd=" + Pwd2 + ", UserType=" + UserType + ", Sex=" + 
sSex + ", sSecurityAnswer=" + sSecurityAnswer + ", SecurityQuestionId=" + SecurityQuestionId + ", PwdHint=" + sPwdHint);

if (!bPwdErr && sSecurityAnswer != null && sSecurityAnswer.length()>0)
{



UserInfo userinfo = new UserInfo();
StringObj userid = new StringObj();
Byte sex = new Byte(sSex);

userinfo.btSex = sex.byteValue();
//out.println(request.getParameter("PenName"));
userinfo.sPenName = PenName;
userinfo.lSecurityQuestionId = SecurityQuestionId.intValue();
userinfo.sSecurityAnswer = sSecurityAnswer;
//out.println("userType:" + request.getParameter("UserType"));
userinfo.sUserType = UserType;
userinfo.sPwdHint = sPwdHint;



lRet = UMClient.UF_UserInit(UserName,Pwd1, new String("111111"), userinfo,userid);
out.println("UF_UserInit return " + lRet);

if (lRet == 0)
{
	response.sendRedirect("reg_ok.jsp?UserName="+UserName + "&PenName=" + PenName);
}
else
{
	response.sendRedirect("reg_err.jsp?ErrCode="+lRet + "&UserName="+UserName + "&PenName=" + PenName);
}

} // if (!bPwdEr && sSecurityAnswer != null)

}	// if (Pwd1 == null)



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
<FORM METHOD=POST ACTION="reg2.jsp" NAME="_RG_Step2"><TABLE WIDTH=540 CELLPADDING=0 CELLSPACING=0 BORDER=0 bgcolor="#F7EFD6">
<TR>
 <TD WIDTH=540 height="29" background="images/green_bg1.jpg" class="white"> &nbsp; <B>用户注册</B></TD> 
 </TR> 
 <TR>
 <TD><TABLE WIDTH=540 CELLPADDING=0 CELLSPACING=0 BORDER=0>

	  <TR BGCOLOR="#F7EFD6">
	  <TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD>
	  <TD VALIGN=Middle COLSPAN=3><FONT  COLOR="#333333"><br><B>
	  <%if (nStep == 1) {%> 
	  <FONT COLOR=BLUE SIZE=3>第二步：</FONT>
	  <FONT COLOR=BLUE> 输入密码并建立密码提示。</FONT>
	  <%}else{ %>
	  <FONT COLOR=RED>
	  <%
	  if (bPwdErr)
	  	{
	  %>
	  请您填写有效的密码
	  <%}
	  if (sSecurityAnswer == null || sSecurityAnswer.length()==0)
	  {
	  %>
	  请正确填写您的安全问题答案
	  <%}%></FONT>
	  <%}%></FONT></TD>
	  <TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD>
	 </TR>

	
	<TR BGCOLOR="#F7EFD6">
	  <TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD>


	  <TD VALIGN=Middle WIDTH=135 NOWRAP><FONT  COLOR=#333333>创建密码：</FONT></TD>
	  <TD WIDTH=189><FONT COLOR="#000000"><INPUT SIZE=20 TYPE=PASSWORD NAME=Password maxlength="20" value="<%if (nStep == 2) out.println(Pwd1); %>"></FONT></TD>
	  <TD BGCOLOR=#FFF8DC><FONT  COLOR=#333333> <I>密码为6-12位字母或数字。</I> </FONT></TD>
	  <TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD>
	  </TR>
	  <TR BGCOLOR="#F7EFD6">
	  <TD WIDTH=9 HEIGHT=4 NOWRAP></TD>
	  <TD WIDTH=9 HEIGHT=4 NOWRAP></TD>
	  </TR>
	  <TR BGCOLOR="#F7EFD6">
	  <TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD>

	  <TD VALIGN=Middle WIDTH=135 NOWRAP><FONT  COLOR=#333333>密码确认：</FONT></TD>
	  <TD WIDTH=189><FONT COLOR="#000000"><INPUT SIZE=20 TYPE=PASSWORD NAME=RePassword maxlength="20" value="<%if (nStep == 2) out.println(Pwd2); %>"></FONT></TD>
	  <TD BGCOLOR=#F7EFD6><FONT  COLOR=#333333> <I></I> </FONT></TD>
	  <TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD>
	  </TR>

	  <TR BGCOLOR="#F7EFD6">
	  <TD WIDTH=9 HEIGHT=4 NOWRAP></TD>
	  <TD WIDTH=9 HEIGHT=4 NOWRAP></TD>
	  </TR>
	  
	  <TR BGCOLOR="#F7EFD6">
	  <TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD>
	  <TD VALIGN=Middle WIDTH=135 NOWRAP><FONT  COLOR=>安全问题：</FONT></TD>
	  <TD WIDTH=189><FONT COLOR="#000000"><select name="QuestionId">
								
											<option value="1">身份证号码</option>
									
										  </select></FONT></TD>
	  <TD></TD>
	  <TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD>
	  </TR>
	  <TR BGCOLOR="#F7EFD6">
	  <TD WIDTH=9 HEIGHT=4 NOWRAP></TD>
	  <TD WIDTH=9 HEIGHT=4 NOWRAP></TD>
	  </TR>
	  <TR BGCOLOR="#F7EFD6">
	  <TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD>

	  <TD VALIGN=Middle WIDTH=135 NOWRAP><FONT  COLOR=#333333>安全问题答案：</FONT></TD>
	  <TD WIDTH=189><FONT COLOR="#000000"><INPUT SIZE=20 TYPE=TEXT NAME=QuestionAnswer maxlength="20" value=""></FONT></TD>
	  <TD BGCOLOR=#FFF8DC><FONT  COLOR=#333333> <I>当您忘记密码时，可以通过正确的回答安全问题重置密码。</I> </FONT></TD>
	  <TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD>
	  </TR>
	  <TR BGCOLOR="#F7EFD6">
	  <TD WIDTH=9 HEIGHT=6 NOWRAP></TD>
	  <TD WIDTH=9 HEIGHT=6 NOWRAP></TD>
	  </TR>
	  <TR BGCOLOR="#F7EFD6">
	  <TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD>
	  <TD VALIGN=Middle ALIGN=center COLSPAN=3><FONT  COLOR="#333333"><hr></FONT></TD>
	  <TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD>
	  </TR>
	  <TR BGCOLOR="#F7EFD6">
	  <TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD>
	  <TD VALIGN=Middle WIDTH=0 COLSPAN=3><FONT  COLOR="#333333"><b>可选：</b></FONT></TD>
	  <TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD>
	  </TR><!--
	  <TR BGCOLOR="#F7EFD6">
	  <TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD>
	  <TD VALIGN=Middle WIDTH=135 NOWRAP><FONT  COLOR="#333333">密码提示：</FONT></TD>
	  <TD WIDTH=189><FONT COLOR="#000000"><INPUT SIZE=20 TYPE=TEXT NAME=PasswordHint maxlength="200" value=""></FONT></TD>
	  <TD BGCOLOR="#FFF8DC"><FONT  COLOR="#333333"> <I>提示帮助您记起密码。例如：密码为“mimi”，密码提示为“我的小猫”。  </I> </FONT></TD>
	  <TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD>
	  </TR>-->
	  <TR BGCOLOR="#F7EFD6">
	  <TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD>
	  <TD VALIGN=Middle WIDTH=135 NOWRAP><FONT  COLOR="#333333">性别：</FONT></TD>
	  <TD WIDTH=189><FONT COLOR="#000000"><select name="Sex">
											<option value="1">男</option>
											<option value="0">女</option>
											
										  </select></TD>
	  <TD></TD>
	  <TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD>
	  </TR>
	  <TR BGCOLOR="#F7EFD6">
	  <TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD>

	  <TD VALIGN=Middle WIDTH=135 NOWRAP><FONT  COLOR="#333333">手机号码：</FONT></TD>
	  <TD WIDTH=189><FONT COLOR="#000000"><INPUT SIZE=20 TYPE=TEXT NAME=mobileid maxlength="20" value=""></FONT></TD>
	  <TD BGCOLOR="#FFF8DC"><FONT  COLOR="#333333"> <I>您的手机号被确认，即成为VIP用户。   </I> </FONT></TD>
	  <TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD>
	  </TR>
	  <TR BGCOLOR="#F7EFD6">
	  <TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD>
	  <TD VALIGN=Middle ALIGN=center COLSPAN=3><FONT FACE="Arial"  COLOR="#333333"><hr></FONT></TD>
	  <TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD>
	  </TR>
	  <!--
	  <TR BGCOLOR="#F7EFD6">
	  <TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD>
	  <TD VALIGN=Middle WIDTH=0 COLSPAN=3><FONT  COLOR="#333333"><INPUT CHECKED TYPE=CHECKBOX NAME=checkSavePW VALUE=Y>&nbsp; 让此浏览器记住我的用户名和密码，以免每次都要登录。</FONT></TD>
	  <TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD>
	  </TR>
	  -->
	  <TR BGCOLOR="#F7EFD6">
	  <TD WIDTH=9 HEIGHT=6 NOWRAP></TD>
	  <TD WIDTH=9 HEIGHT=6 NOWRAP></TD>
	  </TR>
	  <TR BGCOLOR="#F7EFD6">
	  <TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD>
	  <TD ALIGN=right COLSPAN=3><FONT  COLOR="#000000"><FONT  FACE=Arial COLOR="#000000"><INPUT TYPE=SUBMIT NAME=continue VALUE="继续进入"></FONT></FONT></TD>
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
<INPUT TYPE=HIDDEN NAME="UserName" VALUE=<%=UserName%>>
<INPUT TYPE=HIDDEN NAME="PenName" VALUE=<%=PenName%>>
<input type=hidden name="UserType" value="<%=UserType%>">
</FORM>
<br>
<!-- end body -->
<BR>



</center>
</BODY>
</HTML>
