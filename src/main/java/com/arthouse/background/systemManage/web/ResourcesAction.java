package com.arthouse.background.systemManage.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import com.arthouse.background.systemManage.service.ResourceService;
import com.arthouse.background.systemManage.service.UserDetailsService;
import com.arthouse.base.web.BaseAction;
import com.arthouse.common.dto.ResourcesDto;
import com.arthouse.common.page.Page;
import com.arthouse.security.entity.SysResources;


/**
 * 资源管理
 * @author Administrator
 *
 */
public class ResourcesAction extends BaseAction{
	private ResourceService resourceService;
	private List<SysResources> list=new ArrayList<SysResources>();
	
	private Long id;
    private String resourceName;
	private String resourceDesc;
	private String resourceType;
	private String resourceString;
	private Boolean priority;

	//是否可用，0为不可用，1为可用。
	private Integer enabled;
	//是否是超级。0为不超级，1为超级。
	private Integer issys;
	private String module;
	
	private SysResources sysResources;
	
	private UserDetailsService userDetailsService;
	
	public String resources_Manage(){
		
		/**
    	 * 日志
    	 */
    	try {
			userDetailsService.writeLog(getUsername(),"资源管理","查询资源信息");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "manage";
	}
	
	String msg="";
	
	public String resources_getDefaltJsonList() throws Exception{
		 //获取页面分页参数page和rows
		int currentPage=Integer.parseInt(request.getParameter("page"));//当前页
		int currentRows=Integer.parseInt(request.getParameter("rows"));//每页记录数
		Page page=new Page(15,currentPage,true);
		resourceService.getSysResourcesList(null, page);
		
		response.setContentType("application/json;charset=utf-8");//输出文本为application
		response.setHeader("Cache-Control", "no-cache");//取消浏览器缓存
		
		  //必须加上,防止前端从JSON中取出的数据成乱码
		response.setCharacterEncoding("utf-8");
		
		 PrintWriter out = response.getWriter();
		 JsonConfig jsf=new JsonConfig();
		 
		 list=page.getRecord();//本页记录
		 
		 List<ResourcesDto> resourcelist=new ArrayList<ResourcesDto>();
		 for(Iterator i=list.iterator();i.hasNext();){
			 ResourcesDto artResourcesDto=new ResourcesDto();
			 SysResources resource=(SysResources)i.next();
			 artResourcesDto.setId(resource.getId());
			 artResourcesDto.setIssys(resource.getIssys());
			 artResourcesDto.setEnabled(resource.getEnabled());
			 artResourcesDto.setModule(resource.getModule());
			 artResourcesDto.setPriority(resource.getPriority());
			 artResourcesDto.setResourceDesc(resource.getResourceDesc());
			 artResourcesDto.setResourceName(resource.getResourceName());
			 artResourcesDto.setResourceString(resource.getResourceString());
			 artResourcesDto.setResourceType(resource.getResourceType());
			 resourcelist.add(artResourcesDto);
		 }
		 JSONArray rows=JSONArray.fromObject(resourcelist, jsf);
		 JSONObject obj=new JSONObject();
		 obj.put("total", page.getTotalRecordCount());
		 obj.put("rows", rows);
		 out.print(obj.toString());
		 out.flush();
		 out.close();
		return null;
	}
	
	
	public String resources_Edit(){
		
		return "edit";
	}
	
	public String resources_QueryById(){
		String id=request.getParameter("id");
		try {
			sysResources=resourceService.getSysResourcesById(Long.valueOf(id));
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "edit";
	}
	
	public String resources_Upt() throws IOException {
		 msg="1000";
		SysResources sysResources=new SysResources();
		sysResources.setEnabled(1);
		sysResources.setId(new Long(id));
		sysResources.setIssys(issys);
		sysResources.setModule("1");
		sysResources.setPriority(priority);
		sysResources.setResourceDesc(resourceDesc);
		sysResources.setResourceName(resourceName);
		sysResources.setResourceString(resourceString);
		sysResources.setResourceType(resourceType);
		try {
			resourceService.update(sysResources);
			
			/**
			 * 日志
			 */
			userDetailsService.writeLog(getUsername(),"资源管理","修改资源信息");
			
		} catch (Exception e) {
			 msg = "修改失败"+e.getMessage();
		}
		response.setCharacterEncoding("utf-8");
		PrintWriter out=response.getWriter();
		out.print(msg);
		out.flush();
		out.close();
		return "edit";
	}
	
	
	public String resources_Add() throws IOException {
		 msg="0000";
		SysResources sysResources=new SysResources();
		sysResources.setEnabled(1);
		sysResources.setIssys(issys);
		sysResources.setModule("1");
		sysResources.setPriority(priority);
		sysResources.setResourceDesc(resourceDesc);
		sysResources.setResourceName(resourceName);
		sysResources.setResourceString(resourceString);
		sysResources.setResourceType(resourceType);
		try {
			resourceService.save(sysResources);
			/**
			 * 日志
			 */
			userDetailsService.writeLog(getUsername(),"资源管理","新增资源信息");
		} catch (Exception e) {
			 msg = "新增失败"+e.getMessage();
		}
		response.setCharacterEncoding("utf-8");
		PrintWriter out=response.getWriter();
		out.print(msg);
		out.flush();
		out.close();
		return "edit";
	}
	
	public String resources_Del() throws Exception{
		String ids=request.getParameter("id");
        String[] resourceid=ids.split(",");
	    String errorMessage = "0000";

		try {
			resourceService.deleteresource(resourceid);
			/**
			 * 日志
			 */
			userDetailsService.writeLog(getUsername(),"资源管理","删除资源信息");
		} catch (Exception e) {
			 errorMessage = "删除失败"+e.getMessage();
		}
		response.setCharacterEncoding("utf-8");
		PrintWriter out=response.getWriter();
		out.print(errorMessage);
		out.flush();
		out.close();
		return "manage";
	}
	

	public ResourceService getResourceService() {
		return resourceService;
	}

	public void setResourceService(ResourceService resourceService) {
		this.resourceService = resourceService;
	}

	public List<SysResources> getList() {
		return list;
	}

	public void setList(List<SysResources> list) {
		this.list = list;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getResourceDesc() {
		return resourceDesc;
	}

	public void setResourceDesc(String resourceDesc) {
		this.resourceDesc = resourceDesc;
	}

	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	public String getResourceString() {
		return resourceString;
	}

	public void setResourceString(String resourceString) {
		this.resourceString = resourceString;
	}

	public Boolean getPriority() {
		return priority;
	}

	public void setPriority(Boolean priority) {
		this.priority = priority;
	}

	public Integer getEnabled() {
		return enabled;
	}

	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}

	public Integer getIssys() {
		return issys;
	}

	public void setIssys(Integer issys) {
		this.issys = issys;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}
	public SysResources getSysResources() {
		return sysResources;
	}

	public void setSysResources(SysResources sysResources) {
		this.sysResources = sysResources;
	}


	public String getMsg() {
		return msg;
	}


	public void setMsg(String msg) {
		this.msg = msg;
	}


	public UserDetailsService getUserDetailsService() {
		return userDetailsService;
	}


	public void setUserDetailsService(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}
	

}
