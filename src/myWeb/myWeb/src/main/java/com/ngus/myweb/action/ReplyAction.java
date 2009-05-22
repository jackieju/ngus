package com.ngus.myweb.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

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

public final class ReplyAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		String content = request.getParameter("content");
		
		String title = request.getParameter("title");
		
		int messageId = Integer.parseInt(request.getParameter("id"));
		
		MessageEngine.instance().reply(messageId,content,title);
		
		return (mapping.findForward("success"));
	}
}
