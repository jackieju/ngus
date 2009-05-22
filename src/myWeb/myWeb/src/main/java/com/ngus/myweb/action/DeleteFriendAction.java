package com.ngus.myweb.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ngus.myweb.form.DeleteFriendForm;
import com.ngus.myweb.friend.FriendService;
import com.ngus.um.UMClient;
import com.ngus.um.User;
import com.ns.log.Log;

public final class DeleteFriendAction extends Action{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int userId = ((DeleteFriendForm)form).getUserId();
		int friendId = ((DeleteFriendForm)form).getFriendId();
		String friendName = ((DeleteFriendForm)form).getFriendName();
		User user = null;
		User friend = null;
		try{
			UMClient um = new UMClient();
			user = um.getUserByUserId(userId);
			if (friendId != 0){
				friend = um.getUserByUserId(friendId);
			}
			else if(friendName!=null){
				friend = um.getUserByUserName(friendName);
			}
			else{
				//TODO error			
			}			
		}
		catch(Exception e){
			Log.error(e);
			return (mapping.findForward("error"));
		}		
		FriendService.deleteFriend(user, friend);
		return (mapping.findForward("listfriend"));
	}
}
