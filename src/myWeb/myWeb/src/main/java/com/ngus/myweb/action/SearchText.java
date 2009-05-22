package com.ngus.myweb.action;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;


import com.ngus.message.MessageObject;
import com.ngus.message.MessageEngine;
import com.ngus.myweb.form.SearchTextForm;

public final class SearchText extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		ActionMessages errors = new ActionMessages();
		String postId = (String)((SearchTextForm)form).getPostId();
		String text = (String)((SearchTextForm)form).getText();
		
		
		List<MessageObject> result = new ArrayList<MessageObject>();
		
		result = MessageEngine.instance().searchText(postId,text);
		int lenss = result.size();
		
		if(result.size() > 0 ){
			
			request.setAttribute("result" , result );
			
		}
		request.setAttribute("text" , text );
		request.setAttribute("lenss" , lenss);
		
		
		return (mapping.findForward("list"));
	}	
}
