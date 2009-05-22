package com.ngus.myweb.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.ngus.myweb.form.DeleteForm;
import com.ngus.myweb.services.MyWebResService;

public final class DeleteAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		ActionMessages errors = new ActionMessages();
		String id = ((DeleteForm)form).getId();
		String pid = MyWebResService.instance().getInstanceByID(id).getParentID();
		if(MyWebResService.instance().deleteByID(id)==false){
			errors.add("", new ActionMessage("",id));
			saveErrors(request,errors);
			return (new ActionForward(mapping.getInput()));
		}
		request.setAttribute("id", pid);
		return (mapping.findForward("listbookmark"));
	}
}
