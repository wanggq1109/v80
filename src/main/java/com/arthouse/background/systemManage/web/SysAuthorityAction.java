package com.arthouse.background.systemManage.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import com.arthouse.background.systemManage.service.SysAuthorityService;
import com.arthouse.background.systemManage.service.UserDetailsService;
import com.arthouse.base.web.BaseAction;
import com.arthouse.common.dto.ResourcesListDto;
import com.arthouse.common.dto.SysAuthorityDto;
import com.arthouse.common.page.Page;
import com.arthouse.security.entity.SysAuthorities;
import com.arthouse.security.entity.SysAuthoritiesResources;
import com.arthouse.security.entity.SysResources;


/*
 * 权限管理
 */
public class SysAuthorityAction extends BaseAction{
	private SysAuthorityService sysAuthorityService;
	private List<SysAuthorities> list=new ArrayList<SysAuthorities>();
	private SysAuthorities sysAuthority;
	
	private Long id;
    private String authorityName;
    private String authorityDesc;
    private Boolean enabled;
    private Boolean issys;
    private String module;
    private List<ResourcesListDto> Resourceslist=new ArrayList<ResourcesListDto>();
    private List<ResourcesListDto> Resourceslist2=new ArrayList<ResourcesListDto>();
    
    private UserDetailsService userDetailsService;

    String msg="";
	
	public String sysAuthority_Manage(){
		/**
    	 * 日志
    	 */
    	try {
			userDetailsService.writeLog(getUsername(),"权限管理","查询权限信息");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "manage";
	}
	
	
	public String sysAuthority_List() throws Exception{
		 //获取页面分页参数page和rows
		int currentPage=Integer.parseInt(request.getParameter("page"));//当前页
		int currentRows=Integer.parseInt(request.getParameter("rows"));//每页记录数
		Page page=new Page(15,currentPage,true);
		sysAuthorityService.getSysAuthoritiesList(null, page);
		response.setContentType("application/json;charset=utf-8");//输出文本为application
		response.setHeader("Cache-Control", "no-cache");//取消浏览器缓存
		  //必须加上,防止前端从JSON中取出的数据成乱码
		response.setCharacterEncoding("utf-8");
		
		 list=page.getRecord();
		 List<SysAuthorityDto> artsysAuthorityDtolist=new ArrayList<SysAuthorityDto>();
		 for(Iterator iterator=list.iterator();iterator.hasNext();){
			 SysAuthorities s=(SysAuthorities)iterator.next();
			 SysAuthorityDto artsysAuthorityDto=new SysAuthorityDto();
			 artsysAuthorityDto.setAuthorityDesc(s.getAuthorityDesc());
			 artsysAuthorityDto.setAuthorityName(s.getAuthorityName());
			 artsysAuthorityDto.setEnabled(s.getEnabled());
			 artsysAuthorityDto.setId(s.getId());
			 artsysAuthorityDto.setIssys(s.getIssys());
			 artsysAuthorityDto.setModule(s.getModule());
			 artsysAuthorityDtolist.add(artsysAuthorityDto);
		 }
		 PrintWriter out = response.getWriter();
		 JsonConfig jsf=new JsonConfig();
		 JSONArray json=JSONArray.fromObject(artsysAuthorityDtolist,jsf);
		 JSONObject obj=new JSONObject();
		 obj.put("total", page.getTotalRecordCount());
		 obj.put("rows", json);
		 out.print(obj.toString());
		 out.flush();
		 out.close();
		return null;
	}
	
	public String sysAuthority_Edit(){
		
		return "edit";
	}
	
	public String sysAuthority_QueryById(){
		String id=request.getParameter("id");
		try {
			sysAuthority=sysAuthorityService.getSysAuthoritiesById(Long.parseLong(id));
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "edit";
	}
	
	public String sysAuthority_Upt() throws IOException {
		 msg="1000";
		SysAuthorities s=new SysAuthorities();
		s.setId(new Long(id));
		s.setAuthorityDesc(authorityDesc);
		s.setAuthorityName(authorityName);
		s.setEnabled(true);
		s.setIssys(issys);
		s.setModule("1");
		try {
			sysAuthorityService.update(s);
			/**
			 * 日志
			 */
			userDetailsService.writeLog(getUsername(),"权限管理","修改权限信息");
			
		} catch (Exception e) {
			 msg = "修改失败"+e.getMessage();
		}
         response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();

        out.write(msg);
        out.flush();
        out.close();
		return "edit";
	}
	
	public String sysAuthority_Add() throws IOException {
		 msg="0000";
		SysAuthorities s=new SysAuthorities();
		s.setAuthorityDesc(authorityDesc);
		s.setAuthorityName(authorityName);
		s.setEnabled(true);
		s.setIssys(issys);
		s.setModule("1");
		try {
			sysAuthorityService.save(s);
			/**
			 * 日志
			 */
			userDetailsService.writeLog(getUsername(),"权限管理","新增权限信息");
		} catch (Exception e) {
			 msg = "新增失败"+e.getMessage();
		}
         response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();

        out.write(msg);
        out.flush();
        out.close();
		return "edit";
	}
	
	/**
	 * 删除
	 * @return
	 * @throws Exception
	 */
	public String sysAuthority_Del() throws Exception{
		String ids=request.getParameter("id");
        String[] authies=ids.split(",");
        String errorMessage="0000";
		try {
			sysAuthorityService.deleteAuthorities(authies);
			/**
			 * 日志
			 */
			userDetailsService.writeLog(getUsername(),"权限管理","删除权限信息");
		} catch (Exception e) {
			 errorMessage = "删除失败"+e.getMessage();
		}
		response.setCharacterEncoding("utf-8");
		PrintWriter out=response.getWriter();
		out.print(errorMessage);
		out.flush();
		out.close();
		return null;
	}


    public String getresourceslist() throws IOException {
        String errorMessage = "";
        String id=request.getParameter("id");
        try
        {
            Resourceslist=sysAuthorityService.getResourcesList(Long.valueOf(id));
            for(int i=0;i<Resourceslist.size();i++)
            {
                  errorMessage += Resourceslist.get(i).getResourceDesc()+","+ Resourceslist.get(i).getId()+",";
            }

        }catch (Exception ex){
            errorMessage = "2";
        }
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        out.write(errorMessage);
        out.flush();
        out.close();
        return null;
    }

    public String getresourceslist2() throws IOException {
        String errorMessage = "";
        String id=request.getParameter("id");
        try
        {
            Resourceslist2=sysAuthorityService.getResourcesList2(Long.valueOf(id));

            for(int i=0;i<Resourceslist2.size();i++)
            {
                  errorMessage += Resourceslist2.get(i).getResourceDesc()+","+ Resourceslist2.get(i).getId()+",";
            }

        }catch (Exception ex){
            errorMessage = "2";
        }
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        out.write(errorMessage);
        out.flush();
        out.close();
        return null;
    }

    public String authorities_resources_edit() throws IOException {
        String resources=request.getParameter("resources");
        String[] resource=resources.split(",");
        String ids=request.getParameter("id");
        String[] id=ids.split(",");
        String errorMessage="1";
        SysAuthoritiesResources sar=new SysAuthoritiesResources();
        SysAuthorities sa=new SysAuthorities();
        SysResources sr=new SysResources();
        try{
            sysAuthorityService.deleteAuthoritiesResources(id);
            if(!resources.equals(""))
            {
                for(int i=0;i<resource.length;i++)
                {
                    sar.setEnabled(true);
                    sr.setId(Long.valueOf(resource[i]));
                    sa.setId(Long.valueOf(ids));
                    sar.setSysAuthorities(sa);
                    sar.setSysResources(sr);
                    sysAuthorityService.save(sar);

                }
            }
            
            /**
			 * 日志
			 */
			userDetailsService.writeLog(getUsername(),"权限管理","权限资源设置");
        }catch (Exception ex){
            errorMessage = "2";
        }
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        out.write(errorMessage);
        out.flush();
        out.close();
        return  null;
    }
	
	public SysAuthorityService getSysAuthorityService() {
		return sysAuthorityService;
	}
	public void setSysAuthorityService(SysAuthorityService sysAuthorityService) {
		this.sysAuthorityService = sysAuthorityService;
	}
	public List<SysAuthorities> getList() {
		return list;
	}
	public void setList(List<SysAuthorities> list) {
		this.list = list;
	}
	public SysAuthorities getSysAuthority() {
		return sysAuthority;
	}
	public void setSysAuthority(SysAuthorities sysAuthority) {
		this.sysAuthority = sysAuthority;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getAuthorityName() {
		return authorityName;
	}


	public void setAuthorityName(String authorityName) {
		this.authorityName = authorityName;
	}


	public String getAuthorityDesc() {
		return authorityDesc;
	}


	public void setAuthorityDesc(String authorityDesc) {
		this.authorityDesc = authorityDesc;
	}


	public Boolean getEnabled() {
		return enabled;
	}


	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}


	public Boolean getIssys() {
		return issys;
	}


	public void setIssys(Boolean issys) {
		this.issys = issys;
	}


	public String getModule() {
		return module;
	}


	public void setModule(String module) {
		this.module = module;
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
