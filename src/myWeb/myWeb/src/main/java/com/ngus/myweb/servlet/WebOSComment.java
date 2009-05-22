package com.ngus.myweb.servlet;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ngus.myweb.util.DbUtils;
import com.ngus.myweb.util.ParamUtils;
import com.ns.db.DB;
import com.ns.util.DateTime;
import com.ns.log.Log;
/**
 * Servlet implementation class for Servlet: Search
 * 
 */
public class WebOSComment extends javax.servlet.http.HttpServlet implements
		javax.servlet.Servlet {

	public WebOSComment() {
		super();
	}

	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request,
	 *      HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request,
	 *      HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		PrintWriter out= response.getWriter();
		String req= request.getParameter("req");
		
		if(req == null){
			out.print("0");
		}else if(req.equals("submit")){
			String name= "Guest";
			if(request.getSession().getAttribute("username") != null)
				name= (String)request.getSession().getAttribute("username");
			
			String comment= request.getParameter("comment");
			if(submitComment(name, comment))
				out.print("1");
			else
				out.print("0");
		}else if(req.equals("getcommets")){
			//int currentpage= ParamUtils.getIntParameter(request, "currentpage", 1);
			//out.print(getCommentList(currentpage, 10));
			int page= ParamUtils.getIntParameter(request, "page", 0);
			int pageSize= ParamUtils.getIntParameter(request, "pageSize", 0);
			
			if(page != 0){
				out.print(getComments(page, pageSize));
				//out.print(getLastTen());
			}else{
				
			}
		}else if(req.equals("gettotlanumber")){
			out.print(getTotalNumber());
		}
	}
	
	private boolean submitComment(String name, String comment){
		Connection con= null;
		PreparedStatement ps= null;
		boolean flag= true;
		try{
			con = DB.getConnection();			
			ps = con.prepareStatement("insert into webos_comment values(null,?,?,?)");
			ps.setString(1, name);
			ps.setString(2, comment);
			ps.setTimestamp(3, DateTime.currentTimestamp());
			ps.execute();			
		}
		catch(Exception e){
			flag=false;
		}
		finally{
			DbUtils.closeConnection(con);
			DbUtils.closePreparedStatement(ps);
			
		}
		return flag;
	}

	private int getTotalNumber(){
		Connection con= null;
		PreparedStatement ps= null;
		int num=0;
		try{
			con = DB.getConnection();			
			ps = con.prepareStatement("select * from webos_comment");
			ResultSet rs = ps.executeQuery();
			if(rs != null){
			while(rs.next()){
				num++;
			}
			}
		}
		catch(Exception e){
			Log.trace("comment db search");
			Log.error(e);
		}
		finally{
			DbUtils.closeConnection(con);
			DbUtils.closePreparedStatement(ps);
			
		}	
		
		return num;
	}
		
	private String getComments(int page, int pageSize){
		Connection con =null;
		PreparedStatement ps= null;
		String comments="[";
		
		try{
			con= DB.getConnection();
			int temp= (page-1)*pageSize;
			ps= con.prepareStatement("select * from webos_comment order by id limit "+temp+ ","+ pageSize);
			ResultSet rs= ps.executeQuery();
			String comment= "";
			
			if(rs!= null){
				rs.next();
				comment="{\"name\":\""+rs.getString("userName")
						+"\",\"comment\":\""+rs.getString("comment")
						+"\",\"date\":\""+ rs.getTimestamp("createtime")
						+"\"}";
				comments+= comment;
				while(rs.next()){
					comment=",{\"name\":\""+rs.getString("userName")
							+"\",\"comment\":\""+rs.getString("comment")
							+"\",\"date\":\""+ rs.getTimestamp("createtime")
							+"\"}";
					comments+=comment;
				}
				comments+= "]";			
			}
			
		}catch(Exception e){
			comments= "[]";
		}
		finally{
			DbUtils.closeConnection(con);
			DbUtils.closePreparedStatement(ps);
		}
		
		return comments;
	}
	
	private String getLastTen(){
		int total= getTotalNumber();
		Connection con= null;
		PreparedStatement ps= null;
		String comments="[";		
		try{
			con = DB.getConnection();			
			ps = con.prepareStatement("select * from webos_comment"); //order by id limit 0,30");
			ResultSet rs = ps.executeQuery();
			String comment="";	
			
			if(rs != null){
				int i= total- 10;	
				while(i > 0){
					rs.next();
					i--;
				}
				rs.next();
				comment="{\"name\":\""+rs.getString("userName")
						+"\",\"comment\":\""+rs.getString("comment")
						+"\",\"date\":\""+ rs.getTimestamp("createtime")
						+"\"}";
				comments+= comment;
				while(rs.next()){
					comment=",{\"name\":\""+rs.getString("userName")
							+"\",\"comment\":\""+rs.getString("comment")
							+"\",\"date\":\""+ rs.getTimestamp("createtime")
							+"\"}";
					comments+=comment;
				}
				comments+= "]";
			}
		}
		catch(Exception e){
			comments= "[]";
		}
		finally{
			DbUtils.closeConnection(con);
			DbUtils.closePreparedStatement(ps);
		}
		return comments;
	}
	
	private String getCommentList(int currentPage, int pageSize){
		int total= getTotalNumber();
		Connection con= null;
		PreparedStatement ps= null;
		String comments="[";
		try{
			con = DB.getConnection();			
			ps = con.prepareStatement("select * from webos_comment");
			ResultSet rs = ps.executeQuery();
			String comment="";
			
			
			if(total< ((currentPage-1)*pageSize+1) ){
				comments="[]";
			}else if(total> currentPage*pageSize){
				if(rs != null){
					int i=(currentPage-1)*pageSize;
					while(i != 0){
						rs.next();
						i--;
					}
					rs.next();
					comment="{\"name\":\""+rs.getString("userName")
							+"\",\"comment\":\""+rs.getString("comment")
							+"\",\"date\":\""+ rs.getTimestamp("createtime")
							+"\"}";
					comments+= comment;
					i= pageSize-1;
					while(i!= 0 && rs.next()){
						comment=",{\"name\":\""+rs.getString("userName")
								+"\",\"comment\":\""+rs.getString("comment")
								+"\",\"date\":\""+ rs.getTimestamp("createtime")
								+"\"}";
						comments+=comment;						
					}
					comments+= "]";
					i--;
				}
			}else{
				if(rs != null){
					int i=(currentPage-1)*pageSize;
					while(i != 0){
						rs.next();
						i--;
					}
					rs.next();
					comment="{\"name\":\""+rs.getString("userName")
							+"\",\"comment\":\""+rs.getString("comment")
							+"\",\"date\":\""+ rs.getTimestamp("createtime")
							+"\"}";
					comments+= comment;
					while(rs.next()){
						comment=",{\"name\":\""+rs.getString("userName")
								+"\",\"comment\":\""+rs.getString("comment")
								+"\",\"date\":\""+ rs.getTimestamp("createtime")
								+"\"}";
						comments+=comment;
					}
					comments+= "]";
				}
			}
		}
		catch(Exception e){
			comments= "[]";
		}
		finally{
			DbUtils.closeConnection(con);
			DbUtils.closePreparedStatement(ps);
			
		}		
		return comments;
	}
	
}