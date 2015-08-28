package com.s2sh.page.service;

import java.util.List;

import com.arthouse.common.page.Page;
import com.s2sh.page.model.Template;

public interface TemplateManager  {
	
	public List<Template> getTemplateList(Template template, Page page);
	
	public void save(Object obj)throws Exception;

	public void update(Object obj)throws Exception;

	public void delete(Object obj)throws Exception;
	
	public List getAllshopInfo()throws Exception;
	
	
}
