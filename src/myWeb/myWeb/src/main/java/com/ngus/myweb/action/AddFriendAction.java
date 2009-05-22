package com.ngus.myweb.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ngus.message.MessageEngine;
import com.ngus.message.MessageObject;
import com.ngus.myweb.form.AddFriendForm;
import com.ngus.myweb.friend.FriendService;
import com.ngus.um.UMClient;
import com.ngus.um.User;
import com.ns.log.Log;

public final class AddFriendAction extends Action{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		
		
	//	int userId = ((AddFriendForm)form).getUserId();
		int userId = Integer.parseInt((String)request.getSession().getAttribute("userid"));
		int friendId = ((AddFriendForm)form).getFriendId();
		String friendName = ((AddFriendForm)form).getFriendName();
		
		if(friendName.equals("")||friendName.equals(null)){
			int log_error_level =3 ;
			ActionForward fail = mapping.findForward("fail") ;
			fail = new ActionForward(fail.getPath()+"?log_error_level="+log_error_level);
			return fail;
		}
		
		User user = null;
		User friend = null;
		
		
		if(new UMClient().checkDuplicate(friendName, null, null)!=-3){
			int log_error_level =3 ;
			ActionForward fail = mapping.findForward("fail") ;
			fail = new ActionForward(fail.getPath()+"?log_error_level="+log_error_level);
			return fail;
			
		}else{
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
					//user not found
					return (mapping.findForward("error"));
				}
				
			}
			catch(Exception e){
				Log.error(e);
				//return (mapping.findForward("error"));
			}		
			FriendService.addFriend(user, friend);
			MessageObject msg = MessageEngine.instance().sendInvitation(user.getUserName(), friend.getUserName());
			if(msg==null){
				Log.error("Cannot send invitation Error");
			}
			return (mapping.findForward("listfriend"));
		}
	}
}
