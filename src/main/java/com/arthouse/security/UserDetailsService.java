package com.arthouse.security;

import com.arthouse.security.entity.SysAuthorities;
import com.arthouse.security.entity.SysResources;
import com.arthouse.security.entity.SysRoles;
import com.arthouse.security.entity.SysUsers;

/**
 * Created by IntelliJ IDEA.
 * User: barry.wang
 * Date: 11-10-21
 * Time: 下午3:01
 * To change this template use File | Settings | File Templates.
 */
public interface UserDetailsService {

    public void persist(SysUsers user) throws Exception;

    public void persistRole(SysRoles role);

    public void persistAuth(SysAuthorities authorities);

    public void persistResource(SysResources resources);

    public void changePwd(Long userId, String oldPassword, String newPassword);
}
