package com.arthouse.background.systemManage.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.arthouse.background.systemManage.service.ResourceService;
import com.arthouse.common.dao.ICommonDao;
import com.arthouse.common.page.Page;
import com.arthouse.security.entity.SysResources;


public class ResourceServiceImpl implements ResourceService {
	 private ICommonDao dao;

	public ICommonDao getDao() {
		return dao;
	}

	public void setDao(ICommonDao dao) {
		this.dao = dao;
	}


	public void delete(Object obj) throws Exception {
		dao.delete(obj);
		
	}


	public List<SysResources> getSysResourcesList(SysResources sysResources,
			Page page) {
		StringBuilder  builder=new StringBuilder();
		builder.append("from SysResources r");
		List param=new ArrayList();
		builder.append(" where 1=1");
		return dao.query(builder.toString(), page, param.toArray());
	}


	public SysResources getSysResourcesById(Long qid) throws Exception {
		Session session=dao.getHibernateSession();
		try {
			Query query=session.createQuery(" from SysResources s where s.id=:id");
			query.setParameter("id", qid);
			SysResources sysResources=(SysResources)query.uniqueResult();
			if(sysResources==null){
				throw new UsernameNotFoundException(qid+"不存在");
			}
			return sysResources;
		} catch (Exception e) {
			throw e;
		} finally {
       	    session.clear();
            session.close();
		}
	}


	public void save(Object obj) throws Exception {
		dao.save(obj);
		
	}

	public void update(Object obj) throws Exception {
		dao.update(obj);
		
	}

    public void deleteresource(final String[] ids) throws Exception {

           String sql = "delete SysAuthoritiesResources where RESOURCE_ID in (:ids)";
           dao.updTobatch(ids,sql);

           String hql = "delete SysResources where id in (:ids)";
           dao.updTobatch(ids,hql);

       }
}
