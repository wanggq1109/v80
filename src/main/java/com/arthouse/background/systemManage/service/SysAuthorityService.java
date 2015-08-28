package com.arthouse.background.systemManage.service;

import java.util.List;

import com.arthouse.common.dto.ResourcesListDto;
import com.arthouse.common.page.Page;
import com.arthouse.security.entity.SysAuthorities;


public interface SysAuthorityService {
    public List<SysAuthorities> getSysAuthoritiesList(SysAuthorities sysAuthorities, Page page);
	 
	 public SysAuthorities getSysAuthoritiesById(Long qid) throws Exception;
	 
	 public void save(Object obj) throws Exception;

	 public void update(Object obj) throws Exception;

	 public void delete(Object obj) throws Exception;

    public List<ResourcesListDto> getResourcesList(Long id) throws Exception;

    public List<ResourcesListDto> getResourcesList2(Long id) throws Exception;

    public void deleteAuthoritiesResources(final String[] ids) throws Exception;

    public void deleteAuthorities(final String[] ids) throws Exception;

}
