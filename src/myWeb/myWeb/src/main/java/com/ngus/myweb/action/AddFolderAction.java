package com.ngus.myweb.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.ngus.myweb.dataobject.Folder;
import com.ngus.myweb.form.AddFolderForm;
import com.ngus.myweb.services.MyWebResService;

public final class AddFolderAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		ActionMessages errors = new ActionMessages();
		AddFolderForm fm = (AddFolderForm)form;
		String name = fm.getName();		
		String parentID = fm.getParentID();
		String description = fm.getDescription();
		List<String> arr = new ArrayList<String>();
		int shareLevel = fm.getShareLevel();
		Folder bm = new Folder(name, parentID, description, arr, shareLevel);
		
		if(MyWebResService.instance().makePersistent(bm)==null){
			errors.add("", new ActionMessage("",bm));
			saveErrors(request,errors);
			return (new ActionForward(mapping.getInput()));
		}
		
		return (mapping.findForward("list"));
	}
}
