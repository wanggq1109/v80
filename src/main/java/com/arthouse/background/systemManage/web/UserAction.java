package com.arthouse.background.systemManage.web;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.springframework.security.authentication.encoding.PasswordEncoder;

import com.arthouse.background.systemManage.service.UserDetailsService;
import com.arthouse.background.systemManage.service.UserService;
import com.arthouse.base.web.BaseAction;
import com.arthouse.common.domain.Chaicompany;
import com.arthouse.common.dto.RoleListDto;
import com.arthouse.common.dto.UserDto;
import com.arthouse.common.page.Page;
import com.arthouse.security.entity.SysRoles;
import com.arthouse.security.entity.SysUsers;
import com.arthouse.security.entity.SysUsersRoles;

/**
 * Created by IntelliJ IDEA.
 * User: dragon
 * Date: 11-5-18
 * Time: 下午2:07
 * To change this template use File | Settings | File Templates.
 */
public class UserAction extends BaseAction{

    private UserService userService;
     
    private UserDetailsService userDetailsService;

    public UserDetailsService getUserDetailsService() {
		return userDetailsService;
	}

	public void setUserDetailsService(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	private UserDto j_user;

    private SysUsers k_user;

    public Long id;

    //用户账号 与 用户id相同，具有唯一性。
    private String userAccount;

    //中文用户名。
    private String userName;

    //密码原文 ＋ 用户名作为盐值 的字串经过Md5加密后形成的密文。
    private String userPassword;

    //用户备注
    private String userDesc;

    //是否能用。
    private Boolean enabled;

    //是否是超级用户。
    private Boolean issys;

    //用户所在的单位。
    private String userDept;

    //用户的职位：比如主任、经理等。
    private String userDuty;

    //该用户所负责的子系统
    private String subSystem;


    private List<SysUsers> templateList = new ArrayList<SysUsers>();

    // 对用户输入的密码进行MD5编码
    private PasswordEncoder passwordEncoder;


    private List<SysRoles> roleList=new ArrayList<SysRoles>();
    private List<RoleListDto> userlist=new ArrayList<RoleListDto>();
     private List<RoleListDto> userlist2=new ArrayList<RoleListDto>();

    String msg = "";
    public String getMsg() {
		return msg;
	}

    private List<RoleListDto> dtolist=new ArrayList<RoleListDto>();
    /**

	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**

     * 进入用户管理页面
     * @return
     */
    public String User_ManageInit()throws Exception{

    	/**
    	 * 日志
    	 */
    	userDetailsService.writeLog(getUsername(),"用户管理","查询用户信息");

        return "manage";

    }

    /**
     * 进入用户管理页面
     * @return
     */
    public String User_Edit(){

        return "edit";

    }

    /**
     * 跟据用户id查询用户信息
     * @return
     */
    public String User_QueryById(){

        String id = request.getParameter("id");
        try{
            k_user = userService.getSysUsersById(Long.valueOf(id));
        }catch (Exception ex){
             ex.printStackTrace();
        }

        return "edit";
    }

    /**
     * 所有用户信息显示
     * @return
     * @throws Exception
     */
     public String User_getDefaltJsonList() throws Exception {
        //获取页面分页参数page和rows
        int currentPage = Integer.parseInt(request.getParameter("page"));//当前页
        int currentRows = Integer.parseInt(request.getParameter("rows"));//每页显示记录数
        Page page = new Page(15, currentPage, true);

        userService.geSysUsersList(null, page);

        response.setContentType("application/json; charset=utf-8"); //输出文本为application
        response.setHeader("Cache-Control", "no-cache"); //取消浏览器缓存

        //必须加上,防止前端从JSON中取出的数据成乱码
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        JsonConfig cfg = new JsonConfig();

        templateList = page.getRecord();

         List<UserDto> userList = new ArrayList<UserDto>();

        for( Iterator it=templateList.iterator(); it.hasNext();){

            SysUsers users = (SysUsers) it.next();
            UserDto userDto = new UserDto();
            userDto.setId(users.getId());
            userDto.setEnabled(users.getEnabled());
            userDto.setIssys(users.getIssys());
            userDto.setSubSystem(users.getSubSystem());
            userDto.setUserAccount(users.getUserAccount());
            userDto.setUserDept(users.getUserDept());
            userDto.setUserDesc(users.getUserDesc());
            userDto.setUserDuty(users.getUserDuty());
            userDto.setUserName(users.getUserName());
            //公司
            if(users.getUserDept()!=null && !"".equals(users.getUserDept())){
            	if("0".equals(users.getUserDept())){
            		 userDto.setUserDept("本公司");//公司名称
            		 userDto.setUserDeptid("0");//公司id
            	}else{
            		if(users.getUserDept()!=null){
            		 Chaicompany chai=userService.getChaicompanyById(Long.valueOf(users.getUserDept()));
                	 if(chai!=null){
                		 userDto.setUserDept(chai.getName());//公司名称
                		 userDto.setUserDeptid(users.getUserDept());//公司id
                	 }
            		}
            	}
            }
            
            userList.add(userDto);

        }

        JSONArray rows = JSONArray.fromObject(userList, cfg);
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("total", page.getTotalRecordCount());
        jsonObject.put("rows", rows);
        out.print(jsonObject.toString());
        System.out.println("-------" + jsonObject);

        out.flush();
        out.close();
        return null;
    }
     
     
     /**
 	 * 公司
 	 * @return
 	 * @throws Exception
 	 */
 	public String User_getJsonList()throws Exception{
 	      response.setContentType("application/json; charset=utf-8"); //输出文本为application
 	      response.setHeader("Cache-Control", "no-cache"); //取消浏览器缓存
 	        //必须加上,防止前端从JSON中取出的数据成乱码
 	    //  response.setCharacterEncoding("UTF-8");
 	      PrintWriter out = response.getWriter();
 	        try {
 	             List<Chaicompany> list  = userService.getChaicompanyData();
 	            String retTree="";
 	            String json="";
 	            if(list.size()>0){
 	            	 JSONObject jsonObj = new JSONObject();
 	            	 jsonObj.put("id", "0");
 	            	 jsonObj.put("text","本公司");
// 	            	 jsonObj.put("attributes","");
 	            	 retTree=jsonObj.toString()+",";
 	                for(int i=0;i<list.size();i++){
 	                    
 	                    JSONObject jsonObject = new JSONObject();

 	                    jsonObject.put("id",  list.get(i).getId());
 	                    jsonObject.put("text", list.get(i).getName());
// 	                    jsonObject.put("attributes", list.get(i).getNo());
 	                     retTree +=jsonObject.toString()+",";
 	                   }
 	                }
 	            else
 	            {
 	            	retTree = "{\"id\":\"\",\"text\":\"无\",\"attributes\":\"\"},";
 	            }
 	            if(!"".equals(retTree)){
 	            	 json ="["+ retTree.substring(0,retTree.length()-1)+"]";
 	            }
 	            out.print(json);
 	        } catch (Exception e) {
 	            e.printStackTrace();
 	        }
 	        out.flush();
 	        out.close();
 	        return null;
 	  }

    /**
     * 新增
     * @return
     */
	public String User_Add() throws Exception{

         msg = "0000";
         response.setCharacterEncoding("utf-8");
         PrintWriter out = response.getWriter();

        //验证用户信息
        String checkMsg = userService.checkUsername(userAccount);
        if(!checkMsg.equals("")){
            msg = checkMsg;
            out.write(msg);
            out.flush();
            out.close();
            return "edit";
        }
        SysUsers u=new SysUsers();
        u.setEnabled(true);
       // u.setIssys(issys);
        u.setSubSystem(subSystem);
        u.setUserAccount(userAccount);
        u.setUserDept(userDept);
        u.setUserDesc(userDesc);
        u.setUserName(userName);
        //用户密码加密
        String encodedNewPassword = passwordEncoder.encodePassword(userPassword,userAccount);
        u.setUserPassword(encodedNewPassword);
//        userDetailsService.changePwd(u.getId(), userPassword, userPassword);
        try{
             userService.save(u);
             
         	/**
         	 * 日志
         	 */
         	userDetailsService.writeLog(getUsername(),"用户管理","新增用户信息");

        	

        }catch (Exception ex){

            ex.printStackTrace();

            msg = "新增失败"+ex.getMessage();
        }
       

        out.write(msg);
        out.flush();
        out.close();
        return "edit";

    }

    /**
     * 修改
     * @return
     * @throws Exception
     */
    public String User_Upt() throws Exception{

        msg = "1000";

        String userid=request.getParameter("id");
        //用户密码加密
        String encodedNewPassword = passwordEncoder.encodePassword(userPassword,userAccount);
        try{

             userService.updpwd(Long.valueOf(userid),encodedNewPassword);
             
         	/**
          	 * 日志
          	 */
          	userDetailsService.writeLog(getUsername(),"用户管理","修改用户密码");
             
        }catch (Exception ex){
            ex.printStackTrace();
            msg = "修改失败"+ex.getMessage();
        }
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();

        out.write(msg);
        out.flush();
        out.close();
        return "edit";
    }
    
    public String User_UptInfo() throws Exception{
        msg = "1000";
        String userid=request.getParameter("id");
        
        String userName_edit=request.getParameter("userName_edit");
        String userDesc_edit=request.getParameter("userDesc_edit");
        String userDept_edit=request.getParameter("userDept_edit");

        SysUsers s=userService.getSysUsersById(Long.valueOf(userid));
        s.setUserName(userName_edit);
        s.setUserDesc(userDesc_edit);
        s.setUserDept(userDept_edit);
      
        try{
        	userService.update(s);
        	  /**
             * 日志管理
             */
        	userDetailsService.writeLog(getUsername(),"用户管理","修改用户信息");
       	

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
    public String User_Del() throws Exception{

        String ids = request.getParameter("id");
        String[] useid=ids.split(",");
        String errorMessage = "0000";

        try{

            userService.deleteUser(useid);
            
            /**
             * 日志管理
             */
            userDetailsService.writeLog(getUsername(),"用户管理","删除用户");
        	

        }   catch (Exception ex){
            errorMessage = "删除失败"+ex.getMessage();
        }

        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();

        out.write(errorMessage);
        out.flush();
        out.close();

        return  SUCCESS;
    }


    public String role_list() throws IOException {
        String errorMessage = "";
        String id=request.getParameter("id");
        try
        {
            userlist2=userService.getUserRolesList2(Long.valueOf(id));
            userlist=userService.getUserRolesList(Long.valueOf(id));
            for(int i=0;i<userlist.size();i++)
            {
                //  errorMessage += userlist.get(i).getRoleName()+","+ userlist.get(i).getId()+",";
                  errorMessage += userlist.get(i).getRoleDesc()+","+ userlist.get(i).getId()+",";
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

    public String role_list2() throws IOException {
        String errorMessage = "";
        String id=request.getParameter("id");
        try
        {
            userlist2=userService.getUserRolesList2(Long.valueOf(id));

            for(int i=0;i<userlist2.size();i++)
            {
//                  errorMessage += userlist2.get(i).getRoleName()+","+ userlist2.get(i).getId()+",";
            	 errorMessage += userlist2.get(i).getRoleDesc()+","+ userlist2.get(i).getId()+",";
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

    public String user_role_edit() throws IOException {
        String roles=request.getParameter("role");
        String[] role=roles.split(",");
        String ids=request.getParameter("id");
        String[] id=ids.split(",");
        String errorMessage="1";
        SysUsersRoles sur=new SysUsersRoles();
        SysRoles sr=new SysRoles();
        SysUsers su=new SysUsers();
        try{
            userService.deleteUserRole(id);
            if(!roles.equals(""))
            {
                for(int i=0;i<role.length;i++)
                {
                    sur.setEnabled(true);
                    sr.setId(Long.valueOf(role[i]));
                    su.setId(Long.valueOf(ids));
                    sur.setPubRoles(sr);
                    sur.setPubUsers(su);
                    userService.save(sur);

                }
            }
        }catch (Exception ex){
            errorMessage = "2";
        }
        
        /**
         * 日志管理
         */
        try {
			userDetailsService.writeLog(getUsername(),"用户管理","用户角色设置修改");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        out.write(errorMessage);
        out.flush();
        out.close();
        return  null;
    }

    public String saveUser(){

        return  null;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public List<SysUsers> getTemplateList() {
        return templateList;
    }

    public void setTemplateList(List<SysUsers> templateList) {
        this.templateList = templateList;
    }

    public UserDto getJ_user() {
        return j_user;
    }

    public void setJ_user(UserDto j_user) {
        this.j_user = j_user;
    }

    public SysUsers getK_user() {
        return k_user;
    }

    public void setK_user(SysUsers k_user) {
        this.k_user = k_user;
    }


    public List<SysRoles> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<SysRoles> roleList) {
        this.roleList = roleList;
    }


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserDesc() {
		return userDesc;
	}

	public void setUserDesc(String userDesc) {
		this.userDesc = userDesc;
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

	public String getUserDept() {
		return userDept;
	}

	public void setUserDept(String userDept) {
		this.userDept = userDept;
	}

	public String getUserDuty() {
		return userDuty;
	}

	public void setUserDuty(String userDuty) {
		this.userDuty = userDuty;
	}

	public String getSubSystem() {
		return subSystem;
	}

	public void setSubSystem(String subSystem) {
		this.subSystem = subSystem;
	}

	public PasswordEncoder getPasswordEncoder() {
		return passwordEncoder;
	}

	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}


}
