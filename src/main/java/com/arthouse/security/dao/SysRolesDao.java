package com.arthouse.security.dao;

import com.arthouse.security.entity.SysAuthorities;
import com.arthouse.security.entity.SysRoles;
import com.arthouse.security.entity.SysRolesAuthorities;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.util.HashMap;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: barry.wang
 * Date: 11-6-8
 * Time: 下午1:20
 * To change this template use File | Settings | File Templates.
 */
public class SysRolesDao extends HibernateDaoSupport{

     public static final Logger log = LoggerFactory
			.getLogger(SysRolesDao.class);


    //权限dao
	private SysAuthoritiesDao sysAuthoritiesDao;

	//角色和权限的对应关系的dao
	private SysRolesAuthoritiesDao sysRolesAuthoritiesDao;

	//用户和角色对应关系的dao
	private SysUsersRolesDao sysUsersRolesDao;

	/*
	 * 将从页面返回的角色对象进行保存。
	 */
	public void persist(SysRoles transientInstance) {

		log.debug("角色 " + transientInstance.getRoleName() + " 持久化!");

		try {

			// 为写入的角色设置唯一id
			//transientInstance.setRoleId(Util.getPkId() + "");

			String roleName = transientInstance.getRoleName();

			// 当角色标识前缀不为"ROLE_"时，要添加该标识。
			if (roleName.startsWith("ROLE")) {

				if (!roleName.startsWith("ROLE_")) {
					roleName = roleName.substring(0, 4) + "_"
							+ roleName.substring(4);
					transientInstance.setRoleName(roleName);
				}

			} else {
				transientInstance.setRoleName("ROLE_" + roleName);
			}

			// 持久化
			getHibernateTemplate().persist(transientInstance);

			log.debug("角色 " + transientInstance.getRoleName() + " 持久化成功！");

		} catch (RuntimeException re) {

			log.error("角色 " + transientInstance.getRoleName() + " 持久化失败！", re);
			throw re;

		}
	}

	/*
	 * 对角色实例进行更新。
	 */
	public void attachDirty(SysRoles instance) {

		log.debug("更新角色 " + instance.getRoleName() + "!");

		try {

			getHibernateTemplate().saveOrUpdate(instance);

			log.debug("更新角色 " + instance.getRoleName() + "成功!");

		} catch (RuntimeException re) {

			log.error("更新角色 " + instance.getRoleName() + "失败!", re);
			throw re;

		}
	}

	/*
	 * 将传入的对象置为Transent状态。
	 */
	public void attachClean(SysRoles instance) {
		log.debug("attaching clean SysRoles instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(SysRoles persistentInstance) {
		log.debug("deleting SysRoles instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	/**
	 * 根据角色id删除该角色。
	 *
	 *@param roleId
	 */
	public void delete(Long roleId) {
		log.debug("根据角色id删除该角色！角色id为 ： " + roleId);
		try {
            Long id = roleId;
			SysRoles role = findById( id );

			getHibernateTemplate().delete( role );

			log.debug("角色id" + roleId + "删除成功！");
		} catch (RuntimeException re) {
			log.error("角色id" + roleId + "删除失败！", re);
			throw re;
		}
	}

	/*
	 * 将传入的detached(托管)状态的对象的属性复制到持久化对象中，并返回该持久化对象。 如果该session中没有关联的持久化对象，加载一个。
	 * 如果传入对象未保存，保存一个副本并作为持久对象返回，传入对象依然保持detached状态。
	 */

	public SysRoles merge(SysRoles detachedInstance) {
		log.debug("merging SysRoles instance");
		try {
			SysRoles result = (SysRoles) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	/*
	 * 通过id得到一个角色实例。
	 */
	public SysRoles findById( Long id) {

		log.debug("得到一个角色实例通过下面的id: " + id);

		try {

			SysRoles instance = (SysRoles)getHibernateTemplate().find(
					"from SysRoles where id= '" + id + "'").get(0);

			if (instance == null) {
				log.debug("查找的角色实例不存在！");
			} else {
				log.debug("成功查找到该实例！");
			}

			return instance;

		} catch (RuntimeException re) {
			log.error("查找失败!", re);
			throw re;
		}
	}

	/*
	 * Hibernate工具生成的代码。
	 */
	public List findByExample(SysRoles instance) {
		log.debug("finding SysRoles instance by example");
		try {
			List results = ((Criteria) getHibernateTemplate().find(
					"com.artmuseum.security.entity.SysRoles")).list();


			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	/*
	 * 试验采用本地main()方法运行返回的list结果是什么？
	 * 经过验证发现，实际上这个功能返回的列表中仅有一条数据，即SysRoles的实例。
	 * 通过list.get(0)将该实例取出，然后转型到SysRoles，进行getRoleName()等操作即可。
	 * 并非此前我想象的直接将各个字段的内容提出来形成一个列表。 11/4/16 15:09
	 */
	public List findByExampleByLocal(SysRoles instance, SessionFactory sessionFactory ) {
		log.debug("finding SysRoles instance by example");
		Session session = sessionFactory.openSession();
		try {

			List results = session.createCriteria(
			"com.artmuseum.security.entity.SysRoles").add(
			Example.create(instance)).list();

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

	/*
	 * 试验使用本地main()方法将实例的字段提取成Map。
	 */
	public List findByExampleByLocalForMap( Session session ) {
		log.debug("finding SysRoles instance by example");
		try {

			List results = session
		     .createSQLQuery("select id,roleName from sys_roles")
		     .setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();


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
	 * 根据平台中的子系统返回角色列表。
	 *
	 *@return
	 */
	public List<SysRoles> findRolesLst(String xtmk) {
		try {

			String hql = "";

			//若xtmk为空，则返回所有角色列表。
			if( null == xtmk ){
				hql = "from SysRoles";
			}else{
				hql = "from SysRoles where module='" + xtmk + "'";
			}

			List<SysRoles> results = getHibernateTemplate().find(hql);

			return results;

		} catch (RuntimeException re) {

			throw re;
		}
	}

	/**
	 * 得到权限列表。
	 */
    @SuppressWarnings("unchecked")
	public List<SysAuthorities> getPermissionList() {
		try {

			List<SysAuthorities> results = getHibernateTemplate().find(
					"from SysAuthorities");

			return results;

		} catch (RuntimeException re) {

			throw re;
		}
	}

	/**
	 * 根据角色id，得到以权限id为键，"true"为值的Map。
	 *
	 *@param roleId
	 *@return
	 */
    @SuppressWarnings("unchecked")
	public HashMap getSelectedPermissionMap(Long roleId) {
    	
    	Session session = getSession();
    	
		try {

			List<String> results = session.createSQLQuery("select a.authority_id " +
					"from sys_authorities a, sys_roles_authorities b " +
					"where a.id = b.AUTHORITY_ID and b.ROLE_ID='"+ roleId +"'").list();

			HashMap<String,String> hashMap = new HashMap<String,String>(0);

			for (String authorityId : results) {
				hashMap.put( authorityId, "true" );
			}

			return hashMap;
		} catch (RuntimeException re) {
			
			session.clear();
			session.close();
			throw re;
		}finally{
			
			session.clear();
			session.close();
		}
	}

	/*
	 * 保存角色和权限之间一对多的关系。
	 */
	public boolean savePermissionAndRole(Long roleId, Long[] permisskey) {

		SysRoles sysRoles = findById(roleId);
		SysAuthorities sysAuthorities = null;
		SysRolesAuthorities sysRolesAuthorities;

		//在重新设置之前先删除之前所拥有的全部对应关系
		sysRolesAuthoritiesDao.deleteOldRoleAndPermissionRelative( roleId );

		try {
			/*
			 * 将用户在前台通过checkbox选中的所有权限id提取出来。
			 * 在进行保存之前，首先要通过id提取该权限的实例，
			 * 为角色权限关联表sysRolesAuthorities的setSysAuthorities()提供数据。
			 * 最后通过hibernate的save()方法保存新建的sysRolesAuthorities实例。
			 */
			for (Long perId : permisskey) {

				sysAuthorities = sysAuthoritiesDao.findById(perId);

				/*
				 * 生成一个新的实例很重要，否则会报
				 * identifier of an instance of xxx
				 * was altered from xxx to xxx"的异常。
				 */
				sysRolesAuthorities = new SysRolesAuthorities();
				//sysRolesAuthorities.setId(Util.getPkId());
				sysRolesAuthorities.setSysRoles(sysRoles);
				sysRolesAuthorities.setSysAuthorities(sysAuthorities);
				sysRolesAuthorities.setEnabled(true);

				getHibernateTemplate().save(sysRolesAuthorities);

			}

		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}

		return true;
	}


	//注入
	public void setSysAuthoritiesDao( SysAuthoritiesDao sysAuthoritiesDao){
		this.sysAuthoritiesDao = sysAuthoritiesDao;
	}

	public SysAuthoritiesDao getSysAuthoritiesDao(){
		return sysAuthoritiesDao;
	}

	public void setSysRolesAuthoritiesDao( SysRolesAuthoritiesDao sysRolesAuthoritiesDao){
		this.sysRolesAuthoritiesDao = sysRolesAuthoritiesDao;
	}

	public SysRolesAuthoritiesDao getSysRolesAuthoritiesDao(){
		return sysRolesAuthoritiesDao;
	}

	public SysUsersRolesDao getSysUsersRolesDao(){
		return sysUsersRolesDao;
	}

	public void setSysUsersRolesDao( SysUsersRolesDao sysUsersRolesDao){
		this.sysUsersRolesDao = sysUsersRolesDao;
	}


	//本地测试入口。
	public static void main(String[] args) {
		
		Session session = null;

		try {
			ApplicationContext context = new ClassPathXmlApplicationContext(
					"applicationContext.xml");
			SessionFactory sessionFactory = (SessionFactory) context
					.getBean("sessionFactory");
			 session = sessionFactory.openSession();
		} catch (Exception e) {
			// TODO: handle exception
			session.clear();
			session.close();
		}finally{
			
			session.clear();
			session.close();
		}


	}

    

}
