package com.ngus.myweb.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ngus.myweb.form.VoteForm;
import com.ngus.myweb.vote.VoteService;

/**
 * 
 * @author Administrator
 *
 */
public final class VoteAction extends Action {
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		String url = (String)((VoteForm)form).getUrl();
		
		if(VoteService.vote(url)){			
			return (new ActionForward(mapping.getInput()));
		}
		request.setAttribute("url", url);
		return mapping.findForward("listView");
	}
}
