package com.arthouse.background.systemManage.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.arthouse.background.systemManage.service.SysAuthorityService;
import com.arthouse.common.dao.ICommonDao;
import com.arthouse.common.dto.ResourcesListDto;
import com.arthouse.common.page.Page;
import com.arthouse.security.entity.SysAuthorities;


public class SysAuthorityServiceImpl implements SysAuthorityService {
	private ICommonDao dao;

	public void delete(Object obj) throws Exception {
		dao.delete(obj);
		
	}


	public SysAuthorities getSysAuthoritiesById(Long qid) throws Exception {
		Session session=dao.getHibernateSession();
		try {
			Query query=session.createQuery("from SysAuthorities s where s.id=:id");
			query.setParameter("id", qid);
			SysAuthorities sysAuthorities=(SysAuthorities)query.uniqueResult();
			if(sysAuthorities==null){
				throw new UsernameNotFoundException(qid+"不存在");
			}
			
			return sysAuthorities;
		} catch (Exception e) {
			throw e;
		} finally {
       	    session.clear();
            session.close();
		}
	}


	public List<SysAuthorities> getSysAuthoritiesList(
			SysAuthorities sysAuthorities, Page page) {
		StringBuilder builder=new StringBuilder();
		builder.append("from SysAuthorities s");
		builder.append(" where 1=1");
		List param=new ArrayList();
		return dao.query(builder.toString(), page, param.toArray());
	}


	public void save(Object obj) throws Exception {
		dao.save(obj);
		
	}


	public void update(Object obj) throws Exception {
		dao.update(obj);
		
	}
	
	

	public ICommonDao getDao() {
		return dao;
	}

	public void setDao(ICommonDao dao) {
		this.dao = dao;
	}

    public List<ResourcesListDto> getResourcesList(Long id) throws Exception
    {
        List<ResourcesListDto> lst=new ArrayList<ResourcesListDto>();
        Session session = dao.getHibernateSession();
        try
        {


            Query q1 = session.createSQLQuery("select b.id,b.resourceDesc from sys_authorities_resources  a ,sys_resources  b where a.RESOURCE_ID=b.id and a.AUTHORITY_ID="+id+"");

            if(q1.list().size()>0)
            {
                Iterator it= q1.list().iterator();
                while (it.hasNext())
                {
                    Object[] ob=(Object[])it.next();
                    ResourcesListDto dto=new ResourcesListDto();
                    dto.setId((BigInteger)ob[0]);
                    dto.setResourceDesc((String)ob[1]);
                    lst.add(dto);
                }


            }
        }
        catch (Exception ex){
              throw ex;
          } finally {
         	    session.clear();
                session.close();
    		}

        return lst;
    }

    public List<ResourcesListDto> getResourcesList2(Long id) throws Exception
    {
        Session session = dao.getHibernateSession();
        List<ResourcesListDto> lst=new ArrayList<ResourcesListDto>();
        try
        {


            Query q1 = session.createSQLQuery("SELECT c.id,c.resourceDesc FROM sys_resources c WHERE c.id NOT IN(select b.id from sys_authorities_resources  a ,sys_resources  b where a.RESOURCE_ID=b.id and a.AUTHORITY_ID="+id+")");

            if(q1.list().size()>0)
            {
                Iterator it= q1.list().iterator();
                while (it.hasNext())
                {
                    Object[] ob=(Object[])it.next();
                    ResourcesListDto dto=new ResourcesListDto();
                    dto.setId((BigInteger)ob[0]);
                    dto.setResourceDesc((String)ob[1]);
                    lst.add(dto);
                }


            }
        }
        catch (Exception ex){
              throw ex;
          } finally {
         	    session.clear();
                session.close();
    		}

        return lst;
    }

    public void deleteAuthoritiesResources(final String[] ids) throws Exception {

        String hql = "delete SysAuthoritiesResources where AUTHORITY_ID in (:ids)";
        dao.updTobatch(ids,hql);
    }

    public void deleteAuthorities(final String[] ids) throws Exception {

           String sql = "delete SysAuthoritiesResources where AUTHORITY_ID in (:ids)";
           dao.updTobatch(ids,sql);
           String shql = "delete SysRolesAuthorities where AUTHORITY_ID in (:ids)";
           dao.updTobatch(ids,shql);
           String hql = "delete SysAuthorities where id in (:ids)";
           dao.updTobatch(ids,hql);

       }

}
