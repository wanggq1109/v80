package com.arthouse.background.systemManage.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.arthouse.background.systemManage.service.RoleService;
import com.arthouse.common.dao.ICommonDao;
import com.arthouse.common.dto.AuthoritiesListDto;
import com.arthouse.common.page.Page;
import com.arthouse.security.entity.SysRoles;

public class RoleServiceImpl implements RoleService {

	private ICommonDao dao;
	public List<SysRoles> geSysRolesList(SysRoles sysRoles, Page page) {
        StringBuilder hql = new StringBuilder();
        hql.append("from SysRoles t ");
        List params = new ArrayList();
        hql.append(" where 1=1 ");

        if (sysRoles != null) {
            //Ar id = artMessage.getArtMessageType();
            //hql.append("and t.goodsType_id= ?");
            //params.add(artVideo.getGoodsType_id());
        }
        return dao.query(hql.toString(), page, params.toArray());
    }

	public SysRoles getSysRolesById(Long qid) throws Exception {
        Session session = dao.getHibernateSession();
        try{
           Query q = session.createQuery(" from SysRoles r where r.id = :id");
           q.setParameter("id", qid);
           SysRoles sysRoles = (SysRoles) q.uniqueResult();
           if (sysRoles == null) {
               throw new UsernameNotFoundException(qid+"不存在");
           }
           return sysRoles;
        }catch (Exception ex){
            throw ex;
        } finally {
       	    session.clear();
            session.close();
		}

    }
	
	public ICommonDao getDao() {
		return dao;
	}
	public void setDao(ICommonDao dao) {
		this.dao = dao;
	}

	public void delete(Object obj) throws Exception {
		dao.delete(obj);
		
	}

	public void save(Object obj) throws Exception {
		  dao.save(obj);
		
	}

	public void update(Object obj) throws Exception {
		 dao.update(obj);
		
	}

    public List<AuthoritiesListDto> getAuthoritiesRolesList(Long id) throws Exception
    {
        List<AuthoritiesListDto> lst=new ArrayList<AuthoritiesListDto>();
        Session session = dao.getHibernateSession();
        try
        {


            Query q1 = session.createSQLQuery("select b.id,b.authorityName,b.authorityDesc from sys_roles_authorities  a ,sys_authorities  b where a.AUTHORITY_ID=b.id and a.ROLE_ID="+id+"");

            if(q1.list().size()>0)
            {
                Iterator it= q1.list().iterator();
                while (it.hasNext())
                {
                    Object[] ob=(Object[])it.next();
                    AuthoritiesListDto dto=new AuthoritiesListDto();
                    dto.setId((BigInteger)ob[0]);
                    dto.setAuthorityName((String)ob[1]);
                    dto.setAuthorityDesc((String)ob[2]);
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

    public List<AuthoritiesListDto> getAuthoritiesRolesList2(Long id) throws Exception
    {
        Session session = dao.getHibernateSession();
        List<AuthoritiesListDto> lst=new ArrayList<AuthoritiesListDto>();
        try
        {


            Query q1 = session.createSQLQuery("SELECT c.id,c.authorityName,c.authorityDesc FROM sys_authorities c WHERE c.id NOT IN(select b.id from sys_roles_authorities  a ,sys_authorities  b where a.AUTHORITY_ID=b.id and a.ROLE_ID="+id+")");

            if(q1.list().size()>0)
            {
                Iterator it= q1.list().iterator();
                while (it.hasNext())
                {
                    Object[] ob=(Object[])it.next();
                    AuthoritiesListDto dto=new AuthoritiesListDto();
                    dto.setId((BigInteger)ob[0]);
                    dto.setAuthorityName((String)ob[1]);
                    dto.setAuthorityDesc((String)ob[2]);
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


    public void deleteRoleAuthorities(final String[] ids) throws Exception {

        String hql = "delete SysRolesAuthorities where ROLE_ID in (:ids)";
        dao.updTobatch(ids,hql);
    }

    public void deleterole(final String[] ids) throws Exception {

           String sql = "delete SysRolesAuthorities where ROLE_ID in (:ids)";
           dao.updTobatch(ids,sql);
           String shql = "delete SysUsersRoles where ROLE_ID in (:ids)";
           dao.updTobatch(ids,shql);
           String hql = "delete SysRoles where id in (:ids)";
           dao.updTobatch(ids,hql);

       }
	
}
