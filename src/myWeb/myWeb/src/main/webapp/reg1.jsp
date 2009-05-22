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
		//int ret1 = UMClient.UF_CheckUserName(userName, userType, sugUserName1, sugUserName2);//-50005表示um连接不成功
		//int ret2 = UMClient.UF_CheckPenName(penName, sugPenName1, sugPenName2);//-50300表示有选择的penName已被注册
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
<TITLE> - 注册 </TITLE>
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
 <TD WIDTH=540 height="29" background="images/green_bg1.jpg" class="white"> &nbsp; <B>用户注册</B></TD> 
 </TR> 
 <TR> 
 <TD><TABLE WIDTH=540 CELLPADDING=0 CELLSPACING=0 BORDER=0> 
 	<input type=hidden name="userType" value="none">
	  <TR BGCOLOR="#F7EFD6"> 
	  <TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD> 
	  <TD VALIGN=bottom ALIGN=center COLSPAN=3><FONT COLOR="#333333"><br><B><FONT COLOR=RED>已经注册了？  </FONT><A STYLE="text-decoration: underline" HREF="log_in.jsp?via="><FONT size=3>点击此处</A> 登录。</FONT></TD> 
	  <TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD> 
	  </TR> 
 
	  <TR BGCOLOR="#F7EFD6"> 
	  <TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD> 
	  <TD VALIGN=Middle COLSPAN=3><FONT COLOR="#333333"><BR><B><FONT COLOR=BLUE>第一步：</FONT><FONT COLOR=BLUE>  建立用户名和笔名。</FONT></FONT><br><br></TD> 
	  <TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD> 
	  </TR> 
	  <TR BGCOLOR="#F7EFD6"> 
	  <TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD> 
 
	  <TD VALIGN=Middle WIDTH=135 NOWRAP align=right><FONT COLOR=#333333>请输入用户名：</FONT></TD> 
	  <TD WIDTH=189><FONT COLOR="#000000"><INPUT TYPE=TEXT NAME=userName maxlength="16" value="" onblur="syncpenname();"></FONT></TD> 
	  <td></td>
	   <TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD> 
	  </TR> 
	  <TR BGCOLOR="#F7EFD6"> 
	  <TD WIDTH=9 HEIGHT=4 NOWRAP></TD> 
	  <TD WIDTH=9 HEIGHT=4 NOWRAP></TD> 
	  <TD BGCOLOR=#FFF8DC><FONT COLOR=#333333> 合法的用户名应该以英文字母开头，由英文字母或数字、下划线组成，长度为3-16位。 </FONT></TD> 
	  </TR> 
	  
	  <TR BGCOLOR="#F7EFD6"> 
	  <TD WIDTH=9 HEIGHT=14 NOWRAP></TD> 
	  <TD WIDTH=9 HEIGHT=4 NOWRAP></TD> 
	  </TR> 
	  <TR BGCOLOR="#F7EFD6"> 
	  <TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD> 

	  <TD VALIGN=Middle WIDTH=135 NOWRAP align=right><FONT COLOR=#333333>请输入笔名：</FONT></TD>
	  <TD WIDTH=189><FONT COLOR="#000000"><INPUT TYPE=TEXT NAME=penName maxlength="16" value="" %>></FONT></TD> 
	  <td></td>
	  <TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD> 
	  </TR> 
	  <TR BGCOLOR="#F7EFD6"> 
	  <TD WIDTH=9 HEIGHT=4 NOWRAP></TD> 
	  <TD WIDTH=9 HEIGHT=4 NOWRAP></TD> 
	  <TD BGCOLOR=#FFF8DC><FONT COLOR=#333333> 合法的笔名可以包括中文，英文字符和数字。 </FONT></TD> 
	  
	  </TR> 
	  <TR BGCOLOR="#F7EFD6"> 
	  <TD WIDTH=9 HEIGHT=14 NOWRAP></TD> 
	  <TD WIDTH=9 HEIGHT=4 NOWRAP></TD> 
	  </TR> 
	  <TR BGCOLOR="#F7EFD6"> 
	  <TD WIDTH=9 HEIGHT=4 NOWRAP></TD> 
	  <TD WIDTH=9 HEIGHT=4 NOWRAP></TD> 
	  
	  <TD><b>为什么要设置笔名？</b> 为了更高的安全性，我们将用户名和笔名分别处理，用户名配合密码作为登录时的标识，而笔名仅作为网站上公开显示的一个代号。所以为了最高的安全性，建议您将笔名设置为不同于用户名。</TD> 
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
	  				<td><font color=red>您的用户名<%=userName %>已经被注册过了，请更换一个。</font></td>
	  				</tr>
	  				<TR BGCOLOR="#F7EFD6"> 
	  				<TD WIDTH=9 HEIGHT=4 NOWRAP></TD> 
	  				<TD WIDTH=9 HEIGHT=4 NOWRAP></TD> 
	  				<td><font color=red>您的笔名<%=penName %>已经被注册了，请更换一个。</font></td>
	  				</tr>
	  	  <% }else{ %>	
	  	  	 	  <TR BGCOLOR="#F7EFD6"> 
	  				<TD WIDTH=9 HEIGHT=4 NOWRAP></TD> 
	  				<TD WIDTH=9 HEIGHT=4 NOWRAP></TD> 
	  				<td><font color=red>您的用户名<%=userName %>已经被注册过了，请更换一个</font>。</td>
	  				</tr>
	  	  <%} %>
		<%}else {
	   			if(errocode2.equalsIgnoreCase("-50300")){ %>
					<TR BGCOLOR="#F7EFD6"> 
	  				<TD WIDTH=9 HEIGHT=4 NOWRAP></TD> 
	  				<TD WIDTH=9 HEIGHT=4 NOWRAP></TD> 
	  				<td><font color=red>您的笔名<%=penName %>已经被注册了，请更换一个。</font></td>
	  				</tr>
				<%}else 
					if(errocode1.equalsIgnoreCase("-50005") && errocode2.equalsIgnoreCase("-50005")){%>
						<TR BGCOLOR="#F7EFD6"> 
	  					<TD WIDTH=9 HEIGHT=4 NOWRAP></TD> 
	  					<TD WIDTH=9 HEIGHT=4 NOWRAP></TD> 
						<td><font color=red>um连接不成功！请稍候再试。</font></td>
						</tr>
					<%} %>
			<%} 
		}else
			if(userName!=null&&penName!=null&&(userName.length()==0||penName.length()==0)){%>
				
				<TR BGCOLOR="#F7EFD6"> 
					<TD WIDTH=9 HEIGHT=4 NOWRAP></TD> 
					<TD WIDTH=9 HEIGHT=4 NOWRAP></TD> 
					<td><font color=red>请输入完整的用户名和笔名!</font></td>
				</tr>
			<%} %>
	  <TR BGCOLOR="#F7EFD6"> 
	  <TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD> 
	  <TD ALIGN=right COLSPAN=3><FONT COLOR="#000000"><FONT FACE=Arial COLOR="#000000"><INPUT TYPE=SUBMIT NAME=continue VALUE="继续进入第二步"></FONT></FONT></TD> 
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
