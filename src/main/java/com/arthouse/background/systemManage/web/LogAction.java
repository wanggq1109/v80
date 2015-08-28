package com.arthouse.background.systemManage.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import com.arthouse.background.systemManage.service.LogService;
import com.arthouse.base.web.BaseAction;
import com.arthouse.common.domain.SystemLog;
import com.arthouse.common.page.DateJsonValueProcessor;
import com.arthouse.common.page.Page;

public class LogAction extends BaseAction{
	private LogService logService;
	private List<SystemLog> templateList = new ArrayList<SystemLog>();
	
	public String log_display()
	{
		return "manage";
	}
	
	public String log_getDefaltJsonList()throws Exception{
        //获取页面分页参数page和rows
        int currentPage = Integer.parseInt(request.getParameter("page"));//当前页
        int currentRows = Integer.parseInt(request.getParameter("rows"));//每页显示记录数
        Page page = new Page(15, currentPage, true);
        logService.getSystemLogList(request, page);
        response.setContentType("application/json; charset=utf-8"); //输出文本为application
        response.setHeader("Cache-Control", "no-cache"); //取消浏览器缓存

        //必须加上,防止前端从JSON中取出的数据成乱码
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        JsonConfig cfg = new JsonConfig();
        cfg.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));

        templateList = page.getRecord();

        
        JSONArray rows = JSONArray.fromObject(templateList, cfg);
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("total", page.getTotalRecordCount());
        jsonObject.put("rows", rows);
        out.print(jsonObject.toString());

        out.flush();
        out.close();
        return null;
    }
	
	//清空
	 public String log_del() throws Exception, IOException {
		 String ret="0";
	        try {
	        	logService.logdel(request);
	        } catch (Exception e) {
	        	ret=e.toString();
	            e.printStackTrace();
	        }
	        PrintWriter out = response.getWriter();
	        out.write(ret);
	        out.flush();
	        out.close();
	        return null;
	    }
	 
	public LogService getLogService() {
		return logService;
	}

	public void setLogService(LogService logService) {
		this.logService = logService;
	}

	public List<SystemLog> getTemplateList() {
		return templateList;
	}

	public void setTemplateList(List<SystemLog> templateList) {
		this.templateList = templateList;
	}
}
