package com.arthouse.base.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.arthouse.background.service.PhotoTypeService;
import com.arthouse.common.dao.ICommonDao;
import com.arthouse.common.domain.PhotoType;
import com.arthouse.common.page.Page;
import com.arthouse.security.CustomUserDetails;
import com.arthouse.security.UserDetailsService;
import com.arthouse.security.dao.SysUsersDao;
import com.arthouse.security.entity.SysUsers;
import com.arthouse.security.entity.SysUsersRoles;
import com.s2sh.page.model.Template;

/**
 * 
 * @author wanggq
 *
 */
public class MainAction extends BaseAction {

	public static final Logger logger = LoggerFactory
			.getLogger(MainAction.class);

	private List<Template> templateList = new ArrayList<Template>();

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	// 对用户输入的密码进行MD5编码
	private PasswordEncoder passwordEncoder;

	private SysUsersDao sysUsersDao;

	private UserDetailsService userDetailsService;
	
	private PhotoTypeService photoTyperService;

	private String message;

	private ICommonDao arthouseDao;

	private Page page;

	private  com.arthouse.background.systemManage.service.UserDetailsService loguserDetailsService;

	public ICommonDao getarthouseDao() {
		return arthouseDao;
	}

	public void setarthouseDao(ICommonDao arthouseDao) {
		this.arthouseDao = arthouseDao;
	}

	public PasswordEncoder getPasswordEncoder() {
		return passwordEncoder;
	}

	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	private int total = 0;

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	/**
	 * 显示主框架页面
	 * 
	 * @return
	 */
	public String main() throws Exception {

		String username = getUsername();
		SysUsers user = sysUsersDao.getUserByName(username);
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();

		String authName = "";
		List<String> authList = new ArrayList<String>();

		for (SysUsersRoles ga : user.getSysUsersRoleses()) {
			authName = ga.getPubRoles().getRoleName();
			System.out.println("我的角色--------------"
					+ ga.getPubRoles().getRoleName());
			authList.add(authName);
		}

		request.setAttribute("authName", authName);
		List menuList = arthouseDao.getResourceByAuth(authList);

		// 系统管理
		if (menuList.contains("/user_ManageInit.action")
				&& menuList.contains("/sysAuthority_Manage.action")
				&& menuList.contains("/role_ManageInit.action")
				&& menuList.contains("/resources_Manage.action")) {
			request.setAttribute("user_ManageInit", "user_ManageInit");// 用户管理
			request.setAttribute("sysAuthority_Manage", "sysAuthority_Manage");// 权限管理
			request.setAttribute("role_ManageInit", "role_ManageInit");// 角色管理
			request.setAttribute("resources_Manage", "resources_Manage");// 资源管理
		}
		
		List<PhotoType> photoTypeList = photoTyperService.getParentPhotoType();
		if(photoTypeList.size()>0&&photoTypeList.size()==4){
			
			String photoworkType = photoTypeList.get(0).getCode();
			String dressType = photoTypeList.get(1).getCode();
			String slideType = photoTypeList.get(2).getCode();
			String studioType = photoTypeList.get(3).getCode();
			String employeephotoType = photoTypeList.get(4).getCode();
			String hotspots = photoTypeList.get(5).getCode();
			request.setAttribute("photoworkType", photoworkType);
			request.setAttribute("dressType", dressType);
			request.setAttribute("slideType", slideType);
			request.setAttribute("studioType", studioType);
			request.setAttribute("employeephotoType", employeephotoType);
			request.setAttribute("hotspots", hotspots);
			
		}else{
			request.setAttribute("photoworkType", "01");
			request.setAttribute("dressType", "02");
			request.setAttribute("slideType", "05");
			request.setAttribute("studioType", "07");
			request.setAttribute("employeephotoType", "08");
			request.setAttribute("hotspots", "09");
		}
		

		request.setAttribute("userName", user.getUserName());
		request.setAttribute("date", getCurrDate());
		loguserDetailsService.writeLog(getUsername(), "登录", "登录主页面");
		return SUCCESS;
	}

	/**
	 * 登录初始化
	 * 
	 * @return
	 */
	public String login() {

		return SUCCESS;
	}

	// 修改密码
	public String changePassword() throws IOException {
		Long userId = null;

		Object obj = SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		if (obj instanceof CustomUserDetails) {
			userId = ((CustomUserDetails) obj).getId();

		}
		String oldPassword = request.getParameter("pwd");
		String newPassword = request.getParameter("newpwd");

		String username = ((CustomUserDetails) obj).getUserAccount();

		String encodedOldPassword = passwordEncoder.encodePassword(oldPassword,
				username);
		String persistPwd = sysUsersDao.getUser(userId).getUserPassword();
		String errorMessage;

		if (encodedOldPassword.equals(persistPwd)) {

			userDetailsService.changePwd(userId, oldPassword, newPassword);

			errorMessage = "1";
		} else {

			errorMessage = "0";

		}
		PrintWriter out = response.getWriter();
		out.write(errorMessage);
		out.flush();
		out.close();

		return null;

	}

	/**
	 * 注销
	 * 
	 * @return
	 */
	public String cancellation() {

		session.clear();

		return "success";
	}

	/**
	 * 示例演示1
	 * 
	 * @return
	 */
	public String demoDefalt() {
		return "success";
	}

	/**
	 * 新增弹出tabs
	 * 
	 * @return
	 */
	public String demoAddTabs() {
		return "success";
	}

	/**
	 * 新增弹出tabs
	 * 
	 * @return
	 */
	public String demoAdd1() {
		return "success";
	}

	public String demoAddUser() throws Exception, IOException {

		return SUCCESS;
	}

	/**
	 * 新增弹出tabs
	 * 
	 * @return
	 */
	public String itemSelector() {
		return "success";
	}

	/**
	 * 示例演示2
	 * 
	 * @return
	 */
	public String demoEditTable() {
		return "success";
	}

	public String edit() {
		return "edit";
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public UserDetailsService getUserDetailsService() {
		return userDetailsService;
	}

	public void setUserDetailsService(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	public SysUsersDao getSysUsersDao() {
		return sysUsersDao;
	}

	public void setSysUsersDao(SysUsersDao sysUsersDao) {
		this.sysUsersDao = sysUsersDao;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public com.arthouse.background.systemManage.service.UserDetailsService getLoguserDetailsService() {
		return loguserDetailsService;
	}

	public void setLoguserDetailsService(
			com.arthouse.background.systemManage.service.UserDetailsService loguserDetailsService) {
		this.loguserDetailsService = loguserDetailsService;
	}

	public PhotoTypeService getPhotoTyperService() {
		return photoTyperService;
	}

	public void setPhotoTyperService(PhotoTypeService photoTyperService) {
		this.photoTyperService = photoTyperService;
	}

	
	
}
