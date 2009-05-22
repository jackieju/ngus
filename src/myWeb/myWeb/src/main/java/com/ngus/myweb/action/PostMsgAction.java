package com.ngus.myweb.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.ngus.myweb.form.PostMsgForm;
import com.ngus.message.*;
import com.ngus.um.UMClient;

public class PostMsgAction extends Action{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionMessages errors = new ActionMessages();
		String title = (String)((PostMsgForm)form).getTitle();
		String content = (String)((PostMsgForm)form).getContent();
		String postId = (String)((PostMsgForm)form).getPostId();
		String receiveId = (String)((PostMsgForm)form).getReceiveId();
		
		if(new UMClient().checkDuplicate(receiveId, null, null)!=-3){
			int log_error_level;
			log_error_level =3 ;
			ActionForward fail = mapping.findForward("fail") ;
			fail = new ActionForward(fail.getPath()+"?log_error_level="+log_error_level);
			return fail;
		}else{
		
			MessageEngine.instance().sendMsg(postId , receiveId , title ,content);
			return (mapping.findForward("list"));
		}
	}
	
}
