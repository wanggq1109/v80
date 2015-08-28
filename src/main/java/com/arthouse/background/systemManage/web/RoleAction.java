package com.arthouse.background.systemManage.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import com.arthouse.background.systemManage.service.RoleService;
import com.arthouse.background.systemManage.service.UserDetailsService;
import com.arthouse.base.web.BaseAction;
import com.arthouse.common.dto.AuthoritiesListDto;
import com.arthouse.common.dto.RoleDto;
import com.arthouse.common.page.Page;
import com.arthouse.security.entity.SysAuthorities;
import com.arthouse.security.entity.SysRoles;
import com.arthouse.security.entity.SysRolesAuthorities;


/*
 * 角色管理
 */
public class RoleAction extends BaseAction{
	
	private RoleService roleService; 
	
    private SysRoles role;
    
    private Long id;
    private String roleName;
    private String roleDesc;
    private Boolean enabled;
	private Boolean issys;
	//平台中的子系统
	private String module;

	private List<SysRoles> templateList = new ArrayList<SysRoles>();

    private List<AuthoritiesListDto> AuthoritiesList=new ArrayList<AuthoritiesListDto>();
    private List<AuthoritiesListDto> AuthoritiesList2=new ArrayList<AuthoritiesListDto>();
     
	String msg="";
	
	private UserDetailsService userDetailsService;

	/**
     * 进入角色管理页面
     * @return
     */
    public String Role_ManageInit(){
//       System.out.println("-----------------");
    	
    	/**
    	 * 日志
    	 */
    	try {
			userDetailsService.writeLog(getUsername(),"角色管理","查询角色信息");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return "manage";

    }
    

    /**
     * 所有角色信息显示
     * @return
     * @throws Exception
     */
     public String Role_getDefaltJsonList() throws Exception {
        //获取页面分页参数page和rows
        int currentPage = Integer.parseInt(request.getParameter("page"));//当前页
        int currentRows = Integer.parseInt(request.getParameter("rows"));//每页显示记录数
        Page page = new Page(15, currentPage, true);

        roleService.geSysRolesList(null, page);

        response.setContentType("application/json; charset=utf-8"); //输出文本为application
        response.setHeader("Cache-Control", "no-cache"); //取消浏览器缓存

        //必须加上,防止前端从JSON中取出的数据成乱码
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        JsonConfig cfg = new JsonConfig();

        templateList = page.getRecord();

         List<RoleDto> roleList = new ArrayList<RoleDto>();

        for( Iterator it=templateList.iterator(); it.hasNext();){

            SysRoles roles = (SysRoles) it.next();
            RoleDto roleDto = new RoleDto();
            roleDto.setId(roles.getId());
            roleDto.setEnabled(roles.getEnabled());
            roleDto.setIssys(roles.getIssys());
            roleDto.setModule(roles.getModule());
            roleDto.setRoleDesc(roles.getRoleDesc());
            roleDto.setRoleName(roles.getRoleName());
            roleList.add(roleDto);
        }
        JSONArray rows = JSONArray.fromObject(roleList, cfg);
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("total", page.getTotalRecordCount());
        jsonObject.put("rows", rows);
        out.print(jsonObject.toString());
//        System.out.println("-------" + jsonObject);
        out.flush();
        out.close();
        return null;
    }
     
     /**
      * 进入角色管理页面
      * @return
      */
     public String Role_Edit(){

         return "edit";

     }
     
     /**
      * 跟据角色id查询角色信息
      * @return
      */
     public String Role_QueryById(){

         String id = request.getParameter("id");
         try{
             role = roleService.getSysRolesById(Long.valueOf(id));
         }catch (Exception ex){
              ex.printStackTrace();
         }

         return "edit";
     }
     
     /**
      * 新增
      * @return
      */
 	public String Role_Add() throws Exception{
//         System.out.println("----------((----------------");
//         System.out.println(roleName+"*************************");
         msg = "0000";
         SysRoles r=new SysRoles();
         r.setRoleName(roleName);
         r.setRoleDesc(roleDesc);
         r.setIssys(issys);
//         r.setId(new Long(id));
         try{
        	 roleService.save(r);
        	 
        	 /**
        	  * 日志
        	  */
        	userDetailsService.writeLog(getUsername(),"角色管理","新增角色信息");

         }catch (Exception ex){

             ex.printStackTrace();

             msg = "新增失败"+ex.getMessage();
         }
         response.setCharacterEncoding("utf-8");
		PrintWriter out=response.getWriter();
		out.print(msg);
		out.flush();
		out.close();
         return "edit";

     }

     /**
      * 修改
      * @return
      * @throws Exception
      */
     public String Role_Upt() throws Exception{
          msg = "1000";

         SysRoles r=new SysRoles();
         r.setId(new Long(id));
         r.setRoleName(roleName);
         r.setRoleDesc(roleDesc);
         r.setIssys(issys);
         r.setEnabled(true);
         r.setModule("1");
         try{
        	 roleService.update(r);
//        	 roleService.update(role);
        	 
        	 /**
        	  * 日志
        	  */
        	userDetailsService.writeLog(getUsername(),"角色管理","修改角色信息");
         }catch (Exception ex){
             msg = "修改失败"+ex.getMessage();
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
     public String Role_Del() throws Exception{

         String ids = request.getParameter("id");
         String[] roleid=ids.split(",");
         String errorMessage = "0000";
         try{
             roleService.deleterole(roleid);
             
             /**
        	  * 日志
        	  */
        	userDetailsService.writeLog(getUsername(),"角色管理","删除角色信息");
         }   catch (Exception ex){
             errorMessage = "删除失败"+ex.getMessage();
         }
         response.setCharacterEncoding("utf-8");
         PrintWriter out = response.getWriter();
         out.write(errorMessage);
         out.flush();
         out.close();
//         return  SUCCESS;
         return "manage";
     }

    public String getauthoritieslist() throws IOException {
        String errorMessage = "";
        String id=request.getParameter("id");
        try
        {
            AuthoritiesList=roleService.getAuthoritiesRolesList(Long.valueOf(id));
            for(int i=0;i<AuthoritiesList.size();i++)
            {
//                  errorMessage += AuthoritiesList.get(i).getAuthorityName()+","+ AuthoritiesList.get(i).getId()+",";
                  errorMessage += AuthoritiesList.get(i).getAuthorityDesc()+","+ AuthoritiesList.get(i).getId()+",";
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

    public String getauthoritieslist2() throws IOException {
        String errorMessage = "";
        String id=request.getParameter("id");
        try
        {
            AuthoritiesList2=roleService.getAuthoritiesRolesList2(Long.valueOf(id));

            for(int i=0;i<AuthoritiesList2.size();i++)
            {
//                  errorMessage += AuthoritiesList2.get(i).getAuthorityName()+","+ AuthoritiesList2.get(i).getId()+",";
                  errorMessage += AuthoritiesList2.get(i).getAuthorityDesc()+","+ AuthoritiesList2.get(i).getId()+",";
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

    public String role_authorities_edit() throws IOException {
        String authorities=request.getParameter("authorities");
        String[] authoritie=authorities.split(",");
        String ids=request.getParameter("id");
        String[] id=ids.split(",");
        String errorMessage="1";
        SysRolesAuthorities sua=new SysRolesAuthorities();
        SysRoles sr=new SysRoles();
        SysAuthorities sa=new SysAuthorities();
        try{
            roleService.deleteRoleAuthorities(id);
            if(!authorities.equals(""))
            {
                for(int i=0;i<authoritie.length;i++)
                {
                    sua.setEnabled(true);
                    sa.setId(Long.valueOf(authoritie[i]));
                    sr.setId(Long.valueOf(ids));
                    sua.setSysAuthorities(sa);
                    sua.setSysRoles(sr);
                    roleService.save(sua);

                }
            }
            
            /**
       	  * 日志
       	  */
       	userDetailsService.writeLog(getUsername(),"角色管理","为角色设置权限");
       	
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
    public RoleService getRoleService() {
 		return roleService;
 	}
 	public void setRoleService(RoleService roleService) {
 		this.roleService = roleService;
 	}
 	public List<SysRoles> getTemplateList() {
		return templateList;
	}
	public void setTemplateList(List<SysRoles> templateList) {
		this.templateList = templateList;
	}
	
    public SysRoles getRole() {
		return role;
	}
	public void setRole(SysRoles role) {
		this.role = role;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
