package com.arthouse.security.dao;

import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.arthouse.security.entity.SysRolesAuthorities;

/**
 *
 * Created by IntelliJ IDEA.
 * User: barry.wang
 * Date: 11-6-8
 * Time: 下午1:24
 * To change this template use File | Settings | File Templates.
 */
public class SysRolesAuthoritiesDao extends HibernateDaoSupport{

     public static final Logger log = LoggerFactory
			.getLogger(SysRolesAuthoritiesDao.class);


    /**
	 * 根据角色id删除角色与权限之间旧有的关联关系。

	 *@param roleId
	 */
	public void deleteOldRoleAndPermissionRelative( Long roleId ){

		try{

			getSession().createSQLQuery("delete sys_roles_authorities where ROLE_ID='" + roleId + "'");

			log.info("删除角色与权限之间的关联关系成功！");

		}catch(RuntimeException re){
			log.error("删除角色与权限之间的关联关系失败！");
			throw re;
		}

	}

    @SuppressWarnings("unchecked")
	public List<SysRolesAuthorities> getRolesAuthoritiesLst( long roleId ){

		try{

			List<SysRolesAuthorities> list =
				getHibernateTemplate().find("from SysRolesAuthorities where sysRoles.id='" + roleId +"'");

			return list;

		}catch( RuntimeException ex ){
			log.error("提取角色跟权限之间的关联关系的实例列表失败！");
			throw ex;
		}

	}

	public void persist(SysRolesAuthorities transientInstance) {
		log.debug("persisting SysRolesAuthorities instance");
		try {
			getHibernateTemplate().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(SysRolesAuthorities instance) {
		log.debug("attaching dirty SysRolesAuthorities instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(SysRolesAuthorities instance) {
		log.debug("attaching clean SysRolesAuthorities instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(SysRolesAuthorities persistentInstance) {
		log.debug("deleting SysRolesAuthorities instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public SysRolesAuthorities merge(SysRolesAuthorities detachedInstance) {
		log.debug("merging SysRolesAuthorities instance");
		try {
			SysRolesAuthorities result = (SysRolesAuthorities) getHibernateTemplate().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public SysRolesAuthorities findById(long id) {
		log.debug("getting SysRolesAuthorities instance with id: " + id);
		try {
			SysRolesAuthorities instance = (SysRolesAuthorities) getHibernateTemplate().get("com.artmuseum.security.entity.SysRolesAuthorities", id);
			if (instance == null) {
				log.debug("get successful, no instance found");
			} else {
				log.debug("get successful, instance found");
			}
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(SysRolesAuthorities instance) {
		log.debug("finding SysRolesAuthorities instance by example");
		Session session = getSession();
		try {
			List results = session.createCriteria(
					"com.artmuseum.security.entity.SysRolesAuthorities").add(Example.create(instance))
					.list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			session.clear();
			session.close();
			throw re;
		}finally{
			
			session.clear();
			session.close();
		}
	}

    
}
