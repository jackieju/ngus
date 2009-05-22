package com.ngus.comment;

import java.util.List;

public interface ICommentEngine {
	
	public static final String SELECT_COMMID = "select id from test";
	public static final String SELECT_RESID = "select resId from test";
	public static final String SELECT_USER = "select user from test";
	public static final String SELECT_TIME = "select createTime from test";
	
	public CommentObject getCommentById(long comId) throws Exception;
	public List listCommentsForRes(String resId , int position,int count) throws Exception;
	public List listCommentsForUser(String users , int position,int count) throws Exception;
	public void addComment(String content,String resId,String userId) throws Exception;
	public int getResTotalnum(String value) throws Exception;
	public int getUserTotalnum(String value) throws Exception;
	
}
