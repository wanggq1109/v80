package com.arthouse.security.dao;

import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.arthouse.security.entity.SysUsersRoles;

/**
 * Created by IntelliJ IDEA.
 * User: barry.wang
 * Date: 11-6-8
 * Time: 下午1:22
 * To change this template use File | Settings | File Templates.
 */
public class SysUsersRolesDao extends HibernateDaoSupport{

    public static final Logger log = LoggerFactory
			.getLogger(SysUsersRolesDao.class);


    public void persist(SysUsersRoles transientInstance) {
		log.debug("persisting SysUsersRoles instance");
		try {
			getHibernateTemplate().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(SysUsersRoles instance) {
		log.debug("attaching dirty SysUsersRoles instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(SysUsersRoles instance) {
		log.debug("attaching clean SysUsersRoles instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(SysUsersRoles persistentInstance) {
		log.debug("deleting SysUsersRoles instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public SysUsersRoles merge(SysUsersRoles detachedInstance) {
		log.debug("merging SysUsersRoles instance");
		try {
			SysUsersRoles result = (SysUsersRoles) getHibernateTemplate().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public SysUsersRoles findById(long id) {
		log.debug("getting SysUsersRoles instance with id: " + id);
		try {
			SysUsersRoles instance = (SysUsersRoles) getHibernateTemplate().get("com.artmuseum.security.entity.SysUsersRoles", id);
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

	public List findByExample(SysUsersRoles instance) {
		log.debug("finding SysUsersRoles instance by example");
		Session session  = getSession();
		try {
			List results = session.createCriteria(
					"avatar.base.security.entity.SysUsersRoles").add(Example.create(instance)).list();
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
	
	/**
	 * 根据角色id返回用户和角色的关联关系的实例列表。
	 *@param roleId
	 *@return
	 */
	public List<SysUsersRoles> getUsersRolesLst( String roleId ){

		try{

			List<SysUsersRoles> list =
				getHibernateTemplate().find("from SysUsersRoles where pubRoles.id='" + roleId +"'");

			return list;

		}catch( RuntimeException ex){

			log.error("提取用户跟角色的关联关系的实例列表失败！");
			throw ex;
		}

	}

}
