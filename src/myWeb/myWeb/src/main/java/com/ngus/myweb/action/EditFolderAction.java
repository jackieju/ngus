package com.ngus.myweb.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.ngus.myweb.dataobject.Folder;
import com.ngus.myweb.form.EditFolderForm;
import com.ngus.myweb.services.MyWebResService;

public final class EditFolderAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		ActionMessages errors = new ActionMessages();
		EditFolderForm fm = (EditFolderForm)form;
		String name = fm.getName();		
		String id = fm.getId();
		String description = fm.getDescription();
		int shareLevel = fm.getShareLevel();
		Folder fd = (Folder)MyWebResService.instance().getInstanceByID(id);		
		fd.setName(name);
		fd.setDescription(description);
		fd.setShareLevel(shareLevel);
		if(MyWebResService.instance().makePersistent(fd)==null){
			errors.add("", new ActionMessage("",fd));
			saveErrors(request,errors);
			return (new ActionForward(mapping.getInput()));
		}
		
		return (mapping.findForward("list"));
	}
}
