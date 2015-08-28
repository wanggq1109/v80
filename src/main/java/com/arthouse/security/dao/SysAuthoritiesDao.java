package com.arthouse.security.dao;

import java.util.HashMap;
import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.arthouse.security.entity.SysAuthorities;
import com.arthouse.security.entity.SysAuthoritiesResources;
import com.arthouse.security.entity.SysResources;

/**
 * Created by IntelliJ IDEA.
 * User: barry.wang
 * Date: 11-6-8
 * Time: 下午1:23
 * To change this template use File | Settings | File Templates.
 */
public class SysAuthoritiesDao extends HibernateDaoSupport {
    public static final Logger log = LoggerFactory
            .getLogger(SysAuthoritiesDao.class);

    //资源dao
    private SysResourcesDao sysResourcesDao;

    //角色和权限的对应关系的dao
    private SysRolesAuthoritiesDao sysRolesAuthoritiesDao;

    //权限和资源对应关系的dao
    private SysAuthoritiesResourcesDao sysAuthoritiesResourcesDao;

    /*
    * 将从页面返回的权限对象进行保存。
    */
    public void persist(SysAuthorities transientInstance) {

        log.debug("权限 " + transientInstance.getAuthorityDesc() + " 持久化!");

        try {

            // 为写入的权限设置唯一id
            // transientInstance.setAuthorityId(Util.getPkId() + "");

            String authorityName = transientInstance.getAuthorityName();

            // 当权限标识前缀不为"AUTH_"时，要添加该标识。
            if (authorityName.startsWith("AUTH")) {

                if (!authorityName.startsWith("AUTH_")) {
                    authorityName = authorityName.substring(0, 4) + "_"
                            + authorityName.substring(4);
                    transientInstance.setAuthorityName(authorityName);
                }

            } else {
                transientInstance.setAuthorityName("AUTH_" + authorityName);
            }

            // 持久化
            getHibernateTemplate().persist(transientInstance);

            log.debug("权限 " + transientInstance.getAuthorityDesc() + " 持久化成功！");

        } catch (RuntimeException re) {

            log.error("权限 " + transientInstance.getAuthorityDesc() + " 持久化失败！", re);
            throw re;

        }
    }

    /*
    * 对权限实例进行更新。
    */
    public void attachDirty(SysAuthorities instance) {

        log.debug("更新权限" + instance.getAuthorityName() + "!");

        try {

            getHibernateTemplate().saveOrUpdate(instance);

            log.debug("更新权限 " + instance.getAuthorityName() + "成功!");

        } catch (RuntimeException re) {

            log.error("更新权限 " + instance.getAuthorityName() + "失败!", re);
            throw re;

        }
    }

    public void attachClean(SysAuthorities instance) {
        log.debug("attaching clean SysAuthorities instance");
        try {
            getHibernateTemplate().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }


    /**
     * 根据权限id删除该权限，角色和权限，权限和资源的关系。
     *
     * @param authorityId
     */
    public void delete(Long authorityId) {
        log.debug("根据权限id删除该权限！权限id为 ： " + authorityId);
        try {

            Long id = authorityId;
            SysAuthorities authority = findById(id);

            getHibernateTemplate().delete(authority);

            log.debug("权限id" + authorityId + "删除成功！");
        } catch (RuntimeException re) {
            log.error("权限id" + authorityId + "删除失败！", re);
            throw re;
        }
    }


    /**
     * 通过id得以权限的实例。
     *
     * @param id
     * @return
     */
    public SysAuthorities findById(Long id) {
        log.debug("通过id得到权限类的实例: " + id);
        try {

            SysAuthorities instance = (SysAuthorities) getHibernateTemplate().find("from SysAuthorities where id='" + id + "'").get(0);

            if (instance == null) {
                log.debug("该权限类不存在！");
            } else {
                log.debug("成功找到该权限类的实例！");
            }
            return instance;
        } catch (RuntimeException re) {
            log.error("查找权限类实例失败！", re);
            throw re;
        }
    }

    /**
     * 根据平台中的子系统返回权限列表。
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<SysAuthorities> findAuthoritiesLst(String xtmk) {
        try {
            List<SysAuthorities> results = getHibernateTemplate().find(
                    "from SysAuthorities where module='" + xtmk + "'");

            return results;

        } catch (RuntimeException re) {

            throw re;
        }
    }

    /**
     * 根据权限id，得到以资源id为键，"true"为值的Map。
     *
     * @param authorityId
     * @return
     */
    @SuppressWarnings("unchecked")
    public HashMap getSelectedResourcesMap(String authorityId) {
    	Session session = getSession();
        try {
            List<String> results = session.createSQLQuery("select a.id " +
                    "from sys_resources a, sys_authorities_resources b " +
                    "where a.id = b.RESOURCE_ID and b.AUTHORITY_ID='" +
                    authorityId + "' and a.enabled=1").list();

            HashMap<String, String> hashMap = new HashMap<String, String>(0);

            for (String resourcesId : results) {
                hashMap.put(resourcesId, "true");
            }

            return hashMap;
        } catch (RuntimeException re) {

            throw re;
        }finally{
        	
        	session.clear();
        	session.close();
        	
        }
    }

    /*
    * 保存权限和资源之间一对多的关系。
    */
    public boolean saveAuthorityAndReSource(Long authorityId, Long[] resourceskey) {

        SysAuthorities sysAuthorities = findById(authorityId);
        SysResources sysResources = null;
        SysAuthoritiesResources sysAuthoritiesResources;

        //在重新设置之前先删除之前所拥有的全部对应关系
        sysAuthoritiesResourcesDao.deleteOldAuthorityAndResourceRelative(authorityId);

        try {
            /*
            * 将用户在前台通过checkbox选中的所有权限id提取出来。
            * 在进行保存之前，首先要通过id提取该权限的实例，
            * 为角色权限关联表sysRolesAuthorities的setSysAuthorities()提供数据。
            * 最后通过hibernate的save()方法保存新建的sysRolesAuthorities实例。
            */
            for (Long resId : resourceskey) {

                sysResources = sysResourcesDao.findById(resId);

                /*
                * 生成一个新的实例很重要，否则会报
                * identifier of an instance of xxx
                * was altered from xxx to xxx"的异常。
                */
                sysAuthoritiesResources = new SysAuthoritiesResources();
                // sysAuthoritiesResources.setId( Util.getPkId() );
                sysAuthoritiesResources.setSysResources(sysResources);
                sysAuthoritiesResources.setSysAuthorities(sysAuthorities);
                sysAuthoritiesResources.setEnabled(true);

                getHibernateTemplate().save(sysAuthoritiesResources);

            }

        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }

        return true;
    }


    /**
     * 得到资源列表。
     */
    @SuppressWarnings("unchecked")
    public List<SysResources> getResourcesList() {
        try {

            List<SysResources> results = getHibernateTemplate().find(
                    "from SysResources where enabled=1");

            return results;

        } catch (RuntimeException re) {

            throw re;
        }
    }

    //注入
    public void setSysResourcesDao(SysResourcesDao sysResourcesDao) {
        this.sysResourcesDao = sysResourcesDao;
    }

    public SysResourcesDao getSysResourcesDao() {
        return sysResourcesDao;
    }

    public void setSysRolesAuthoritiesDao(SysRolesAuthoritiesDao sysRolesAuthoritiesDao) {
        this.sysRolesAuthoritiesDao = sysRolesAuthoritiesDao;
    }

    public SysRolesAuthoritiesDao getSysRolesAuthoritiesDao() {
        return sysRolesAuthoritiesDao;
    }

    public SysAuthoritiesResourcesDao getSysAuthoritiesResourcesDao() {
        return sysAuthoritiesResourcesDao;
    }

    public void setSysAuthoritiesResourcesDao(SysAuthoritiesResourcesDao sysAuthoritiesResourcesDao) {
        this.sysAuthoritiesResourcesDao = sysAuthoritiesResourcesDao;
    }


}
