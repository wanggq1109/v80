package com.arthouse.security.dao;

import com.arthouse.security.entity.SysAuthoritiesResources;
import org.hibernate.LockMode;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by IntelliJ IDEA.
 * User: barry.wang
 * Date: 11-6-8
 * Time: 下午1:27
 * To change this template use File | Settings | File Templates.
 */
public class SysAuthoritiesResourcesDao extends HibernateDaoSupport{


    public static final Logger log = LoggerFactory
               .getLogger(SysAuthoritiesResourcesDao.class);



	public void persist(SysAuthoritiesResources transientInstance) {
		log.debug("persisting SysAuthoritiesResources instance");
		try {
			getHibernateTemplate().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(SysAuthoritiesResources instance) {
		log.debug("attaching dirty SysAuthoritiesResources instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(SysAuthoritiesResources instance) {
		log.debug("attaching clean SysAuthoritiesResources instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(SysAuthoritiesResources persistentInstance) {
		log.debug("deleting SysAuthoritiesResources instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public SysAuthoritiesResources merge(
			SysAuthoritiesResources detachedInstance) {
		log.debug("merging SysAuthoritiesResources instance");
		try {
			SysAuthoritiesResources result = (SysAuthoritiesResources) getHibernateTemplate().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public SysAuthoritiesResources findById(long id) {
		log.debug("getting SysAuthoritiesResources instance with id: " + id);
		try {
			SysAuthoritiesResources instance = (SysAuthoritiesResources) getHibernateTemplate()
					.get("com.artmuseum.security.entity.SysAuthoritiesResources", id);
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

	/**
	 * 得到PubAuthoritiesResources的对象列表。
	 *@return
	 */
    @SuppressWarnings("unchecked")
	public List<SysAuthoritiesResources> getAll(){

		List<SysAuthoritiesResources> auths = new ArrayList<SysAuthoritiesResources>();

		try{

			auths = getHibernateTemplate()
				.find("from SysAuthoritiesResources");

			return auths;

		}catch( RuntimeException re ){
			log.error("find by authorities failed.", re);
			throw re;
		}

	}



	/**
	 * 根据权限id删除权限与资源之间旧有的关联关系。

	 *@param authorityId
	 */
	public void deleteOldAuthorityAndResourceRelative( Long authorityId ){

		try{

			getSession().createSQLQuery("delete sys_authorities_resources where AUTHORITY_ID='" + authorityId + "'");

			log.info("删除权限与资源之间的关联关系成功！");

		}catch(RuntimeException re){
			log.error("删除权限与资源之间的关联关系失败！");
			throw re;
		}

	}


	public List findByExample(SysAuthoritiesResources instance) {
		log.debug("finding SysAuthoritiesResources instance by example");
		try {
			List results = getSession().createCriteria(
					"com.artmuseum.security.entity.SysAuthoritiesResources").add(
					Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}




}
