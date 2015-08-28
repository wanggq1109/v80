package com.arthouse.background.systemManage.service;

import java.util.List;

import com.arthouse.common.page.Page;
import com.arthouse.security.entity.SysResources;


public interface ResourceService {
     public List<SysResources> getSysResourcesList(SysResources sysResources, Page page);
	 
	 public SysResources getSysResourcesById(Long qid) throws Exception;
	 
	 public void save(Object obj) throws Exception;

	 public void update(Object obj) throws Exception;

	 public void delete(Object obj) throws Exception;

    public void deleteresource(final String[] ids) throws Exception;

}
