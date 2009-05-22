<!-- User Register step1 -->
<%@ page language="java"%>
<%@ page contentType="text/html; charset=gb2312" %>

<%@ page import="com.stockstar.buos.um.*, com.ns.log.*" %>

<%
//setHeader sample
//response.setHeader("Cache-Control","no-cache");
//response.setHeader("Pragma","no-cache");



//Get submited elements

int lRet=0;
boolean bLogon = false;

String UserName = request.getParameter("UserName");
//String UserType = request.getParameter("UserType");
String Password = request.getParameter("Password");	
String checkSavePW = request.getParameter("checkSavePW");
Log.trace("Username="+UserName);

if (UserName == null || UserName.length()==0)
{

%>

<HTML>
<HEAD>
<TITLE> 登录 </TITLE>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel="stylesheet" href="lib/newstyle.css" type="text/css">
</HEAD>
<BODY BGCOLOR=#FFFFFF LINK=#336666 TEXT=#000000 ALINK=#CC0000 VLINK=#666666>
<CENTER>

<BR>
<!-- begin body -->
<FORM METHOD=get ACTION="log_in.jsp" NAME="_RG_Step1"><TABLE WIDTH=540 CELLPADDING=0 CELLSPACING=0 BORDER=0 bgcolor="#F7EFD6">
<TR>
 <TD WIDTH=540 height="29" background="images/green_bg1.jpg" class="white" width=100%> &nbsp; <B>用户登录</B> 
	</TD>
 </TR>
 <TR>
 <TD><TABLE WIDTH=540 CELLPADDING=0 CELLSPACING=0 BORDER=0>

	  <TR BGCOLOR="#F7EFD6">
	  <TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD>
	  <TD VALIGN=Middle COLSPAN=3><br><FONT  COLOR="#333333"><B> 欢迎您！请输入您的用户名及密码。  </B></FONT></TD>
	  <TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD>
	  </TR>
	  <TR BGCOLOR="#F7EFD6">
	  <TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD>

	  <TD VALIGN=Middle WIDTH=81 NOWRAP align=right><FONT  COLOR=#333333>您的用户名：</FONT></TD>
	  <TD WIDTH=216><FONT COLOR="#000000"><INPUT SIZE=20 TYPE=TEXT NAME="UserName" maxlength="16" value=""></FONT></TD>
	  <TD BGCOLOR=#F7EFD6><FONT COLOR=#333333> <b></b>　 </FONT></TD>
	  <TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD>
	  </TR>
	  <TR BGCOLOR="#F7EFD6">
	  <TD WIDTH=9 HEIGHT=4 NOWRAP></TD>
	  <TD WIDTH=9 HEIGHT=4 NOWRAP></TD>
	  </TR>
	  <TR BGCOLOR="#F7EFD6">
	  <TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD>

	  <TD VALIGN=Middle WIDTH=81 NOWRAP align=right><FONT COLOR=#333333>密码：</FONT></TD>
	  <TD WIDTH=216><FONT COLOR="#000000"><INPUT SIZE=20 TYPE=password NAME="Password" maxlength="20" value=""></FONT></TD>
	  <TD BGCOLOR=#F7EFD6><FONT  COLOR=#333333> <b></b>　 </FONT></TD>
	  <TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD>
	  </TR>
	  <TR BGCOLOR="#F7EFD6">
	  <TD WIDTH=9 HEIGHT=4 NOWRAP></TD>
	  <TD WIDTH=9 HEIGHT=4 NOWRAP></TD>
	  </TR>
	  <TR BGCOLOR="#F7EFD6">
	  <TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD>
	  <TD VALIGN=Middle WIDTH=0 COLSPAN=3><FONT  COLOR="#333333"><INPUT CHECKED TYPE=CHECKBOX NAME=checkSavePW VALUE=Y>&nbsp;  让此浏览器记住我的用户名和密码，以免每次都要登录。</FONT></TD>
	  <TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD>
	  </TR>
	  <TR BGCOLOR="#F7EFD6">
	  <TD WIDTH=9 HEIGHT=6 NOWRAP></TD>
	  <TD WIDTH=9 HEIGHT=6 NOWRAP></TD>
	  </TR>
	  <TR BGCOLOR="#F7EFD6">
	  <TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD>
	  <TD ALIGN=right COLSPAN=3>
	  
	  
	  <FONT  COLOR="#000000">
	  	  <FONT  FACE=Arial COLOR="#000000"><INPUT TYPE=SUBMIT NAME=continue VALUE=" 登　录 "></FONT></FONT></TD>
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
	  <TR BGCOLOR="#F7EFD6">
	  <TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD>
	  <TD VALIGN=Middle COLSPAN=3><B><FONT  COLOR="#333333">密码忘记了？
        </FONT> </B><FONT  COLOR="#333333"><A STYLE="text-decoration: underline" HREF="ResetPwd.htm">点此处</A> 重置密码。</FONT></TD>
	  <TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD>
	  </TR>
	  <TR BGCOLOR="#F7EFD6">
	  <TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD>
	  <TD VALIGN=Middle COLSPAN=3><B><FONT  COLOR="#333333">想换个密码？
        </FONT> </B><FONT  COLOR="#333333"><A STYLE="text-decoration: underline" HREF="change_pwd.jsp">点此处</A> 修改密码（请先登录）。</FONT></TD>
	  <TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD>
	  </TR>
	  <TR BGCOLOR="#F7EFD6">
	  <TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD>
	  <TD VALIGN=Middle COLSPAN=3><B><FONT  COLOR="#333333">还没有注册？ </FONT> </B><FONT  COLOR="#333333"> <A STYLE="text-decoration: underline" HREF="reg1.htm">点此处</A> 快捷注册。</FONT></TD>
	  <TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD>
	  </TR>
	  </TABLE></TD>
	</TR>
	<TR><TD HEIGHT=1 BGCOLOR="#000066"><IMG SRC="../images/trans.gif" ALT="" WIDTH=1 HEIGHT=1 BORDER=0></TD></TR>
</TABLE>
<INPUT TYPE=HIDDEN NAME="via" VALUE=>
</FORM>
<br>
<!-- end body -->
<BR>



</center>
</BODY>
</HTML>


<%
}
else 	// if (UserName == null || UserName.length()==0)
{
	
String ClientId = request.getRemoteAddr();	



String LogonStatus = "";	//Return all the message from the logon process


if (UserName == null) UserName = "";
if (Password == null) Password = "";


	//out.println("UserManager Logon:");

		//String ClientId = request.getRemoteAddr();		
		//String UserType = request.getParameter("UserType");
		String UserType = "none";
		String Id =  UserName + UserType;
	
		byte logt, livet;
		logt = 1;	//Logon Type
		livet = 1;
		if (checkSavePW!=null){
			if (checkSavePW.compareTo("Y")==0){
				livet = 2;	//LiveType	1=Temporary, 2=Permanant
			}
		}
		//out.println("livetype:" + livet);
		//out.print("Begin to commit logon operation.");
		
		UserLogonOutput output = new UserLogonOutput(); 
		UserPubInfo userinfo = new UserPubInfo();
		
		
		//out.println("UserName:" + Id);
		//out.println("password:" + Password);
		Log.trace("logt="+logt + ", livet=" + livet+", id = " + Id + ", pwd="+Password + ", client="+ClientId);
		lRet = UMClient.UF_Logon(logt, livet, Id, Password, ClientId, output);
		if (lRet != 0)
		{
			bLogon = false;
			LogonStatus = LogonStatus + "UserLogOn error with code:" + lRet;			
		}
		else
		{
		bLogon = true;
			Log.trace("session id length="+ output.sEncryptedSessionId.length());
			//add Cookie
			Cookie userCookie = new Cookie("SSSESSIONID", output.sEncryptedSessionId);
			if (livet == 2){
				userCookie.setMaxAge(60*60*24*365); // 1 year
			}
			//userCookie.setDomain(".stockstar.com");	//一般需要确定一个域名，这里进行测试，所以先取消域名限制。
			userCookie.setPath("/");
			response.addCookie(userCookie);
			
			LogonStatus = "Logon Succesfully, Cookie Wrote to client. cookie=" + userCookie.getValue();
			
		}
		out.println(LogonStatus);
		Log.trace(LogonStatus);
}	// if (UserName == null || UserName.length()==0)
if (bLogon)
	response.sendRedirect("default.jsp");
%>




