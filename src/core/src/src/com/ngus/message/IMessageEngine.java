package com.ngus.message;

import java.sql.SQLException;
import java.util.List;

import com.ns.exception.NSException;



public interface IMessageEngine {
	 public List<MessageObject> listPostMsg(String postUserId,int start,int count) throws NSException, Exception; 
	 public List<MessageObject> listReceiveMsg(String receiveUserId,int start,int count) throws NSException, Exception; 
	 public MessageObject sendMsg(String postUserId,String receiveUserId,String title , String content) throws NSException, Exception;
     public MessageObject searchMsg (int messageId) throws Exception;
	 public MessageObject reply(int messageId,String content,String title)  throws Exception ; 
	 public MessageObject sendInvitation(String postUserId,String receiveUserId) throws NSException, Exception;
	 public List<MessageObject> searchText(String receiveId, String content) throws NSException, Exception; 
}
