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

import com.ngus.myweb.dataobject.BookMark;
import com.ngus.myweb.form.EditBookMarkForm;
import com.ngus.myweb.services.MyWebResService;

public final class EditBookMarkAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		ActionMessages errors = new ActionMessages();
		EditBookMarkForm fm = (EditBookMarkForm)form;
		String name = fm.getName();
		String url = fm.getUrl();
		String type = fm.getType();
		String id = fm.getId();
		String description = fm.getDescription();
		int shareLevel = fm.getShareLevel();
		BookMark bm = (BookMark)MyWebResService.instance().getInstanceByID(id);
		bm.setName(name);
		bm.setURL(url);
		bm.setRtype(type);
		bm.setDescription(description);
		bm.setTags(analyseTags((String)((EditBookMarkForm)form).getTags()));
		bm.setShareLevel(shareLevel);
				
		if(MyWebResService.instance().makePersistent(bm)==null){
			errors.add("", new ActionMessage("",bm));
			saveErrors(request,errors);
			return (new ActionForward(mapping.getInput()));
		}
		request.setAttribute("id", bm.getParentID());
		return (mapping.findForward("listbookmark"));
	}
	private List<String> analyseTags(String input){
		List<String> arr = new ArrayList<String>(5);
		boolean ignorespace = false;
		if(input==null){
			return arr;
		}
		String tmp = "";
		for(int i=0; i<input.length(); i++){
			char c = input.charAt(i);
			if(c=='"'){
				ignorespace = !ignorespace;
			}
			else if(Character.isWhitespace(c)){
				if(ignorespace==true){
					tmp	+= c;				
				}
				else{
					if(!tmp.trim().equalsIgnoreCase("")){
						arr.add(tmp);
					}
					tmp = "";
				}
			}
			else{
				tmp+=c;
			}
		}
		if(!tmp.trim().equalsIgnoreCase("")){
			arr.add(tmp);
		}
		return arr;
	}
}
