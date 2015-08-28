package com.s2sh.page.web;

import com.arthouse.common.page.Page;

@SuppressWarnings("serial")
public class TemplateAction extends TemplateBaseAction {

	public String page() throws Exception {
		page = new Page(pageSize, pageIndex, true);
		templateManager.getTemplateList(null, page);
		System.out.println("svn ≤‚ ‘");
		return SUCCESS;
		
	}

	public String ajaxpage() throws Exception {
		page = new Page(pageSize, pageIndex, true);
		templateManager.getTemplateList(null, page);
		return SUCCESS;
	}

	public String ajax() throws Exception {
		return SUCCESS;
	}
}
