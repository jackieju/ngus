package com.ngus.myweb.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ngus.myweb.dataobject.BookMark;
import com.ngus.myweb.form.AddBookMarkForm;
import com.ngus.myweb.services.MyWebResService;
import com.ngus.myweb.util.TagUtils;

/**
 * 
 * 
 * @author colakings
 *
 */
public final class AddBookMarkAction extends Action {
	
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//Get the bookmark information
		AddBookMarkForm fm = (AddBookMarkForm)form;
		String name = fm.getName();
		String url =  fm.getUrl();
		String type = fm.getType();
		String parentID = fm.getParentID();
		String description = fm.getDescription();
		List<String> arr = TagUtils.tagAnalyse(fm.getTags());
		int shareLevel = fm.getShareLevel();
		//make bookmark
		BookMark bm = new BookMark(name, url, type, parentID, description, arr, shareLevel);

		//make bookmark persistent
		if (MyWebResService.instance().makePersistent(bm) == null) {
			return (new ActionForward(mapping.getInput()));
		}
		
		//set attribute and forword to next page
		request.setAttribute("id", parentID);
		return (mapping.findForward("listbookmark"));
	}	
}
