<!-- User Register step2 -->
<%@ page language="java"%>
<%@ page contentType="text/html; charset=gb2312" %>

<%@ page import="com.ngus.um.*, com.ngus.um.dbobject.*" %>
<%@ page import="java.sql.Connection" %>

<%@ page import="java.sql.Statement"%>
<%@ page import="java.util.Date"%>
<%@ page import="com.ngus.dataengine.DBConnection"%>
<%@ page import="com.mysql.*"%>

<%
	//setHeader sample
	response.setHeader("Cache-Control","no-cache");
	response.setHeader("Pragma","no-cache");
	int lRet = -1;
	int nStep = 0;
	boolean bPwdErr = false;
	boolean bValidateErr = false;	
	String userName = null;
	
//	String userType = null;
	
	String penName = null;
	//String sSecurityAnswer = null;
	String pwd1 = request.getParameter("password");
	String pwd2 = request.getParameter("rePassword");

	//String checkSavePW = request.getParameter("checkSavePW");
	

	if (pwd1 == null ){
		userName = request.getParameter("userName");
		
		//userType = request.getParameter("userType");
		
		penName = request.getParameter("penName");
		session.putValue("userName", userName);
		session.putValue("penName", penName);
		//session.putValue("userType", userType);
		nStep = 1;		
	}
	else{
		nStep =  2;
		String validateCode1 = request.getParameter("validateCode").toString();
		String validateCode2 = session.getValue("validateCode").toString();
		if(!validateCode2.equals(validateCode1)){
			bValidateErr = true;
			//lRet= 1;//��֤�����벻��ȷ		
		}
		userName = session.getValue("userName").toString();
		penName = session.getValue("penName").toString();
		//userType = session.getValue("userType").toString();
		if (pwd1.compareTo(pwd2) != 0){
			bPwdErr = true;
			//lRet = 1;//�����������벻��
		}
		String sSex = request.getParameter("sex");
		//String sPwdHint = request.getParameter("passwordHint");
	//	if (sPwdHint == null)
		//	sPwdHint = new String("");
		//sSecurityAnswer = request.getParameter("questionAnswer");
		//Integer securityQuestionId = new Integer(request.getParameter("questionId"));
		
		//out.println(
		//"userName="+userName + 
		//", penName=" + penName + ", pwd=" + pwd1 + ", pePwd=" + pwd2 + ", userType=" + userType + ", sex=" + 
		//sSex + ", sSecurityAnswer=" + sSecurityAnswer + ", securityQuestionId=" + securityQuestionId + ", pwdHint=" + sPwdHint);
	
		if (!bPwdErr /*&& sSecurityAnswer != null && sSecurityAnswer.length()>0 */&& bValidateErr==false){		
			UserInfo userinfo = new UserInfo();
			userinfo.setPwd(pwd1);
			//StringObj userid = new StringObj();
			//Byte sex = new Byte(sSex);
			
			userinfo.setSex(Integer.parseInt(sSex));
			//out.println(request.getParameter("PenName"));
			userinfo.setNickName(penName);
			userinfo.setUserName(userName);
		//	userinfo.lSecurityQuestionId = securityQuestionId.intValue();
		//	userinfo.sSecurityAnswer = sSecurityAnswer;
			//out.println("userType:" + request.getParameter("UserType"));
		//	userinfo.sUserType = userType;
		//	userinfo.sPwdHint = sPwdHint;
	
	
	
			//lRet = UMClient.UF_UserInit(UserName,Pwd1, new String("111111"), userinfo,userid);
			new UMClient().register(userinfo);
			int userid = new UMClient().getUserInfoByUserName(userName).getId();
			lRet = 0;
			//add record in table userExtention
			try {
				
				Connection con =  con=DBConnection.getConnection();
				Statement st = con.createStatement();
				st.execute("insert into userextension(userid, score, newLogonTime, penName) values('"+userid+"',0, now(),'"+penName+"')");
			} catch (Exception e) {
				out.print(e);
				
			}
			//out.println("UF_UserInit return " + lRet);
		} // if (!bPwdEr && sSecurityAnswer != null)	
		if(lRet!=-1){
			if (lRet == 0)//lRet == 0
				response.sendRedirect("reg_ok.jsp?userName="+userName + "&penName=" + penName);
			else 
				response.sendRedirect("reg_err.jsp?ErrCode="+lRet);
		}		
	}	// if (Pwd1 == null)

%>
	
<HTML>
<HEAD>
<TITLE> ע�� </TITLE>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel="stylesheet" href="lib/style.css" type="text/css">
</HEAD>
<BODY BGCOLOR=#FFFFFF LINK=#336666 TEXT=#000000 ALINK=#CC0000 VLINK=#666666>
<CENTER>


<BR>
<!-- begin body -->
<FORM METHOD=POST ACTION="reg2.jsp" NAME="_RG_Step2"><TABLE WIDTH=540 CELLPADDING=0 CELLSPACING=0 BORDER=0 bgcolor="#F7EFD6">
<TR>
 <TD WIDTH=540 height="29" background="images/green_bg1.jpg" class="white"> &nbsp; <B>�û�ע��</B></TD> 
 </TR> 
 <TR>
 <TD><TABLE WIDTH=540 CELLPADDING=0 CELLSPACING=0 BORDER=0>

	  <TR BGCOLOR="#F7EFD6">
	  <TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD>
	  <TD VALIGN=Middle COLSPAN=3><FONT  COLOR="#333333"><br><B>
	  <%if (nStep == 1) {%> 
	  <FONT COLOR=BLUE SIZE=3>�ڶ�����</FONT>
	  <FONT COLOR=BLUE> �������벢����������ʾ��</FONT>
	  <%}else{ %>
	  <FONT COLOR=RED>
	  <%
	  if (bPwdErr)
	  	{
	  %>
	  ������д��Ч������
	  <%}}%>
	  
	
	  </FONT></TD>
	  <TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD>
	 </TR>

	
	<TR BGCOLOR="#F7EFD6">
	  <TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD>


	  <TD VALIGN=Middle WIDTH=135 NOWRAP><FONT  COLOR=#333333>�������룺</FONT></TD>
	  <TD WIDTH=189><FONT COLOR="#000000"><INPUT SIZE=20 TYPE=PASSWORD NAME=password maxlength="20" value="<%if (nStep == 2) out.println(pwd1); %>"></FONT></TD>
	  <TD BGCOLOR=#FFF8DC><FONT  COLOR=#333333> <I>����Ϊ6-12λ��ĸ�����֡�</I> </FONT></TD>
	  <TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD>
	  </TR>
	  <TR BGCOLOR="#F7EFD6">
	  <TD WIDTH=9 HEIGHT=4 NOWRAP></TD>
	  <TD WIDTH=9 HEIGHT=4 NOWRAP></TD>
	  </TR>
	  <TR BGCOLOR="#F7EFD6">
	  <TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD>

	  <TD VALIGN=Middle WIDTH=135 NOWRAP><FONT  COLOR=#333333>����ȷ�ϣ�</FONT></TD>
	  <TD WIDTH=189><FONT COLOR="#000000"><INPUT SIZE=20 TYPE=PASSWORD NAME=rePassword maxlength="20" value="<%if (nStep == 2) out.println(pwd2); %>"></FONT></TD>
	  <TD BGCOLOR=#F7EFD6><FONT  COLOR=#333333> <I></I> </FONT></TD>
	  <TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD>
	  </TR>

	  <TR BGCOLOR="#F7EFD6">
	  <TD WIDTH=9 HEIGHT=4 NOWRAP></TD>
	  <TD WIDTH=9 HEIGHT=4 NOWRAP></TD>
	  </TR>
	  <!-- 
	  <TR BGCOLOR="#F7EFD6">
	  <TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD>
	  <TD VALIGN=Middle WIDTH=135 NOWRAP><FONT  COLOR=>��ȫ���⣺</FONT></TD>
	  <TD WIDTH=189><FONT COLOR="#000000"><select name="questionId">
								
											<option value="1">���֤����</option>
									
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

	  <TD VALIGN=Middle WIDTH=135 NOWRAP><FONT  COLOR=#333333>��ȫ����𰸣�</FONT></TD>
	  <TD WIDTH=189><FONT COLOR="#000000"><INPUT SIZE=20 TYPE=TEXT NAME=questionAnswer maxlength="20" value=""></FONT></TD>
	  <TD BGCOLOR=#FFF8DC><FONT  COLOR=#333333> <I>������������ʱ������ͨ����ȷ�Ļش�ȫ�����������롣</I> </FONT></TD>
	  </TR>
	  -->
	  <TR BGCOLOR="#F7EFD6">
	  <TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD>
	  <TD VALIGN=Middle WIDTH=135 NOWRAP><FONT  COLOR=#333333>������֤�룺</FONT></TD>
	  <TD WIDTH=189><FONT COLOR="#000000"><INPUT SIZE=20 TYPE=TEXT NAME=validateCode maxlength="20" value=""></FONT></TD>
	  <td><img width=60  height=20 src="validateCode.jsp" ></td>
	 
	  
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
	  <TD VALIGN=Middle WIDTH=0 COLSPAN=3><FONT  COLOR="#333333"><b>��ѡ��</b></FONT></TD>
	  <TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD>
	  </TR><!--
	  <TR BGCOLOR="#F7EFD6">
	  <TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD>
	  <TD VALIGN=Middle WIDTH=135 NOWRAP><FONT  COLOR="#333333">������ʾ��</FONT></TD>
	  <TD WIDTH=189><FONT COLOR="#000000"><INPUT SIZE=20 TYPE=TEXT NAME=PasswordHint maxlength="200" value=""></FONT></TD>
	  <TD BGCOLOR="#FFF8DC"><FONT  COLOR="#333333"> <I>��ʾ�������������롣���磺����Ϊ��mimi����������ʾΪ���ҵ�Сè����  </I> </FONT></TD>
	  <TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD>
	  </TR>-->
	  <TR BGCOLOR="#F7EFD6">
	  <TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD>
	  <TD VALIGN=Middle WIDTH=135 NOWRAP><FONT  COLOR="#333333">�Ա�</FONT></TD>
	  <TD WIDTH=189><FONT COLOR="#000000"><select name="sex">
											<option value="1">��</option>
											<option value="0">Ů</option>
											
										  </select></TD>
	  <TD></TD>
	  <TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD>
	  </TR>
	  <TR BGCOLOR="#F7EFD6">
	  <TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD>

	  <TD VALIGN=Middle WIDTH=135 NOWRAP><FONT  COLOR="#333333">�ֻ����룺</FONT></TD>
	  <TD WIDTH=189><FONT COLOR="#000000"><INPUT SIZE=20 TYPE=TEXT NAME=mobileid maxlength="20" value=""></FONT></TD>
	  <TD BGCOLOR="#FFF8DC"><FONT  COLOR="#333333"> <I>�����ֻ��ű�ȷ�ϣ�����ΪVIP�û���   </I> </FONT></TD>
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
	  <TD VALIGN=Middle WIDTH=0 COLSPAN=3><FONT  COLOR="#333333"><INPUT CHECKED TYPE=CHECKBOX NAME=checkSavePW VALUE=Y>&nbsp; �ô��������ס�ҵ��û��������룬����ÿ�ζ�Ҫ��¼��</FONT></TD>
	  <TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD>
	  </TR>
	  -->
	  <TR BGCOLOR="#F7EFD6">
	  <TD WIDTH=9 HEIGHT=6 NOWRAP></TD>
	  <TD WIDTH=9 HEIGHT=6 NOWRAP></TD>
	  </TR>
	  <TR BGCOLOR="#F7EFD6">
	  <TD WIDTH=9 HEIGHT=20 NOWRAP>&nbsp; </TD>
	  <TD ALIGN=right COLSPAN=3><FONT  COLOR="#000000"><FONT  FACE=Arial COLOR="#000000"><INPUT TYPE=SUBMIT NAME=continue VALUE="��������"></FONT></FONT></TD>
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
<INPUT TYPE=HIDDEN NAME="UserName" VALUE=<%=userName%>>
<INPUT TYPE=HIDDEN NAME="PenName" VALUE=<%=penName%>>

</FORM>
<br>
<!-- end body -->
<BR>



</center>
</BODY>
</HTML>
