package com.arthouse.base.web;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.security.core.context.SecurityContextHolder;

import com.arthouse.security.CustomUserDetails;
import com.opensymphony.xwork2.ActionSupport;
import com.s2sh.page.service.TemplateManager;
/**
 * @author dzm
 *
 * @version 
 */
public class BaseAction  extends ActionSupport implements SessionAware, ServletRequestAware, ServletResponseAware{

	protected Map session;

    protected HttpServletRequest request;

    protected HttpServletResponse response;

    protected TemplateManager templateManager;

    // 每页记录数
	public Integer pageSize = 10;

	// 当前页数
	public Integer pageIndex = 1;

    /** 通过spring security获取当前用户名 */
    String userName ="";


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

    public TemplateManager getTemplateManager() {
        return templateManager;
    }

    public void setTemplateManager(TemplateManager templateManager) {
        this.templateManager = templateManager;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    /*************************************************spring security 方法***************************************************************************/

    @SuppressWarnings("unchecked")
    public String getUsername() {
		//通过spring security得到登录名称
		Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if (obj instanceof CustomUserDetails) {
			userName = ((CustomUserDetails)obj).getUsername();
		} else {
			userName = obj.toString();
		}
		return userName;
	}
    
    public  String getCurrDate(){
    	
    	Date date = new Date();
    	DateFormat df = DateFormat.getDateInstance(DateFormat.FULL, Locale.CHINA);
    	
    	String currDate = df.format(date);
    	
    	return currDate;
    	
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
}

