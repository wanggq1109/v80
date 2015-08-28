package com.arthouse.security;

import java.util.Set;

import org.springframework.security.core.userdetails.UserDetails;

import com.arthouse.security.entity.SysUsersRoles;

/**
 * Created by IntelliJ IDEA.
 * User: barry.wang
 * Date: 11-6-4
 * Time: 下午2:43
 * To change this template use File | Settings | File Templates.
 */
public interface CustomUserDetails extends UserDetails {

    //用户id
    public Long getId();

    //用户账户
    public String getUserAccount();

    //用户名
    public String getUserName();

    //用户密码
    public String getUserPassword();

    //用户描述或简介
    public String getUserDesc();

    //用户是否能用
    public boolean getEnabled();

    //是否超级用户
    public Boolean getIssys();

    //所属的单位
    public String getUserDept();

    //用户职位
    public String getUserDuty();

    //用户分管的子系统
    public String getSubSystem();

    //用户相对应的角色集
    public Set<SysUsersRoles> getSysUsersRoleses();

}
