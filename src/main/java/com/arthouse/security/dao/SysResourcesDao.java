package com.arthouse.security.dao;

import com.arthouse.security.entity.SysResources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: barry.wang
 * Date: 11-6-8
 * Time: 下午1:26
 * To change this template use File | Settings | File Templates.
 */
public class SysResourcesDao extends HibernateDaoSupport{


     public static final Logger log = LoggerFactory
			.getLogger(SysResourcesDao.class);


    /*
	 * 持久化资源。
	 */
	public void persist( SysResources transientInstance ) {

		log.debug("持久化一个资源实例！");
		try {

			//transientInstance.setResourceId( Util.getPkId()+"" );

			getHibernateTemplate().persist( transientInstance );

			log.debug("持久化资源实例成功！");

		} catch (RuntimeException re) {
			log.error("持久化资源实例失败！", re);
			throw re;
		}
	}


	/*
	 * 保存或更新一个资源实例。
	 */
	public void attachDirty( SysResources instance ) {
		log.debug("更新一个资源实例");
		try {

			getHibernateTemplate().saveOrUpdate( instance );

			log.debug("更新资源实例成功！");

		} catch (RuntimeException re) {
			log.error("更新资源实例失败！", re);
			throw re;
		}
	}

	/*
	 * 根据资源id删除一个资源，非物理删除，而是一个逻辑删除，置enabled为false。
	 */
	public void delete( Long resourceId ) {
		log.debug("根据一个资源id删除一个用户，资源id为：" + resourceId );
		try {

			SysResources resource = findById(resourceId );

			resource.setEnabled( 0 );
//			getHibernateTemplate().saveOrUpdate( resource );

			log.debug("删除成功！");
		} catch (RuntimeException re) {

			log.error("删除失败！", re);
			throw re;
		}
	}


	/*
	 * 根据资源对象删除一个对象，非物理删除，而是一个逻辑删除，置enabled为false。
	 */
	public void delete( SysResources resource ) {
		log.debug("根据一个资源id删除一个用户，资源id为：" + resource.getId() );
		try {

//			SysResources resource = findById( resourceId );

			resource.setEnabled( 0 );
//			getHibernateTemplate().saveOrUpdate( resource );

			log.debug("删除成功！");
		} catch (RuntimeException re) {

			log.error("删除失败！", re);
			throw re;
		}
	}

	public SysResources findById(Long id) {
		log.debug("getting SysUsers instance with id: " + id);
		try {
			SysResources instance = (SysResources) getHibernateTemplate()
					.find("from SysResources where id='" + id +"'").get(0);
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


	/*
	 * 得到资源列表，参数列表在实例中包含着，包括资源号、资源名称、资源类型、资源地址。
	 */
    @SuppressWarnings("unchecked")
	public List<SysResources> findResourcesLst( SysResources resource ){

		List<SysResources> list = null ;
		String sql = "from SysResources where enabled=1 ";
		try{
			if(!"".equals( resource.getResourceName() )){
				sql += "and resourceName like '" + resource.getResourceName() +"' ";
			}

			if(!"".equals( resource.getResourceDesc() )){
				sql += "and resourceDesc like '" + resource.getResourceDesc() +"' ";
			}

			if(!"".equals( resource.getResourceType() )){
				sql += "and resourceType = '" + resource.getResourceType() +"' ";
			}

			if(!"".equals( resource.getResourceString() )){
				sql += "and resourceString like '%" + resource.getResourceString() + "%' ";
			}


			list = getHibernateTemplate().find( sql );


		}catch (RuntimeException re) {
			log.error("findByUserAccount() 错误！", re);
			throw re;
		}

		return list;

	}

}
