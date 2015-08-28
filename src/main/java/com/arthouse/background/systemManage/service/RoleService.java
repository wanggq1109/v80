package com.arthouse.background.systemManage.service;

import java.util.List;

import com.arthouse.common.dto.AuthoritiesListDto;
import com.arthouse.common.page.Page;
import com.arthouse.security.entity.SysRoles;

public interface RoleService {
	
	 public List<SysRoles> geSysRolesList(SysRoles sysRoles, Page page);
	 
	 public SysRoles getSysRolesById(Long qid) throws Exception;
	 
	 public void save(Object obj) throws Exception;

	 public void update(Object obj) throws Exception;

	 public void delete(Object obj) throws Exception;

     public List<AuthoritiesListDto> getAuthoritiesRolesList(Long id) throws Exception;

     public List<AuthoritiesListDto> getAuthoritiesRolesList2(Long id) throws Exception;

    public void deleteRoleAuthorities(final String[] ids) throws Exception;

    public void deleterole(final String[] ids) throws Exception;

}
