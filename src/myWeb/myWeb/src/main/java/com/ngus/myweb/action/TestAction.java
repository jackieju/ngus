package com.ngus.myweb.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ns.exception.NSException;

public final class TestAction extends Action {
	
	static int num;
	/**
	 * @param args
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception, NSException {
				num++;
				
				request.setAttribute("num",num);
				return (mapping.findForward("success"));
		
	}
	

}
