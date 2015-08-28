package com.s2sh.page.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;

import com.arthouse.common.page.Page;
import com.opensymphony.xwork2.ActionSupport;
import com.s2sh.page.service.TemplateManager;

@SuppressWarnings( { "serial", "unchecked" })
public class TemplateBaseAction extends ActionSupport implements SessionAware, ServletRequestAware, ServletResponseAware {

	// Session
	protected Map session;

	// Request
	protected HttpServletRequest request;

	// Response
	protected HttpServletResponse response;

	// Page
	protected Page page;

	// 每页记录数
	protected Integer pageSize = 10;

	// 当前页数
	protected Integer pageIndex = 1;

	// Ajax分页
	protected String pageAjaxName;

	// TemplateManager
	protected TemplateManager templateManager;

	public void setSession(Map session) {
		this.session = session;
	}

	public Map getSession() {
		return this.session;
	}

	public HttpSession getHttpSession() {
		return request.getSession(false);
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletRequest getServletRequest() {
		return this.request;
	}

	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	public HttpServletResponse getServletResponse() {
		return this.response;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	public TemplateManager getTemplateManager() {
		return templateManager;
	}

	public void setTemplateManager(TemplateManager templateManager) {
		this.templateManager = templateManager;
	}

	public String getPageAjaxName() {
		return pageAjaxName;
	}

	public void setPageAjaxName(String pageAjaxName) {
		this.pageAjaxName = pageAjaxName;
	}
}
