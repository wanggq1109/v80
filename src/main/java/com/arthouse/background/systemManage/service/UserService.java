package com.arthouse.background.systemManage.service;


import java.util.List;

import com.arthouse.common.domain.Chaicompany;
import com.arthouse.common.dto.RoleListDto;
import com.arthouse.common.page.Page;
import com.arthouse.security.entity.SysRoles;
import com.arthouse.security.entity.SysUsers;

/**
 * Created by IntelliJ IDEA.
 * User: Dragon
 * Date: 11-5-10
 * Time: 下午5:16
 * To change this template use File | Settings | File Templates.
 */
public interface UserService {

    public void save(Object obj) throws Exception;

    public void update(Object obj) throws Exception;

    public void delete(Object obj) throws Exception;

    public List<SysUsers> geSysUsersList(SysUsers sysUsers, Page page);

    public SysUsers getSysUsersById(Long qid) throws Exception;

    public List<SysRoles> getSysRolesList() throws Exception;
    
    public String checkUsername(String userAccount)throws Exception;
    
    public boolean getUserNameCount(String name) throws Exception;

    public List<RoleListDto> getUserRolesList(Long id) throws Exception;
    public List<RoleListDto> getUserRolesList2(Long id) throws Exception;

    public void deleteUserRole(final String[] ids) throws Exception;

    public void updpwd(Long id,String pwd) throws Exception;

    public void deleteUser(final String[] ids) throws Exception;
    
    public List<Chaicompany> getChaicompanyData()throws Exception;
    
    public Chaicompany getChaicompanyById(Long qid) throws Exception;

}
