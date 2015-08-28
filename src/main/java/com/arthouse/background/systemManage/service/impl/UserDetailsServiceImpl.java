package com.arthouse.background.systemManage.service.impl;

import java.util.Date;

import org.hibernate.Query;
import org.hibernate.Session;

import com.arthouse.background.systemManage.service.UserDetailsService;
import com.arthouse.common.dao.ICommonDao;
import com.arthouse.common.domain.SystemLog;
import com.arthouse.security.dao.SysAuthoritiesDao;
import com.arthouse.security.dao.SysResourcesDao;
import com.arthouse.security.dao.SysRolesDao;
import com.arthouse.security.dao.SysUsersDao;
import com.arthouse.security.entity.SysAuthorities;
import com.arthouse.security.entity.SysResources;
import com.arthouse.security.entity.SysRoles;
import com.arthouse.security.entity.SysUsers;

/**
 * 
 * Created by IntelliJ IDEA.
 * User: barry.wang
 * Date: 11-5-13
 * Time: 上午9:44
 * To change this template use File | Settings | File Templates.
 */
public class UserDetailsServiceImpl  implements UserDetailsService{

    private ICommonDao dao;

    private SysUsersDao sysUsersDao;

    private SysRolesDao sysRolesDao;

    private SysAuthoritiesDao sysAuthoritiesDao;

    private SysResourcesDao sysResourcesDao;

    public SysResourcesDao getSysResourcesDao() {
        return sysResourcesDao;
    }

    public void setSysResourcesDao(SysResourcesDao sysResourcesDao) {
        this.sysResourcesDao = sysResourcesDao;
    }

    public SysAuthoritiesDao getSysAuthoritiesDao() {
        return sysAuthoritiesDao;
    }

    public void setSysAuthoritiesDao(SysAuthoritiesDao sysAuthoritiesDao) {
        this.sysAuthoritiesDao = sysAuthoritiesDao;
    }

    public SysRolesDao getSysRolesDao() {
        return sysRolesDao;
    }

    public void setSysRolesDao(SysRolesDao sysRolesDao) {
        this.sysRolesDao = sysRolesDao;
    }

    public SysUsersDao getSysUsersDao() {
        return sysUsersDao;
    }

    public void setSysUsersDao(SysUsersDao sysUsersDao) {
        this.sysUsersDao = sysUsersDao;
    }

    public ICommonDao getDao() {
        return dao;
    }

    public void setDao(ICommonDao dao) {
        this.dao = dao;
    }

    public void persistAuth(SysAuthorities authorities){


        sysAuthoritiesDao.persist(authorities);

    }

    public void persist(SysUsers user) throws Exception {

       sysUsersDao.persist(user,null);

    }

     public void persistRole(SysRoles role){

         sysRolesDao.persist(role);

     }

    public void persistResource(SysResources resources){
        

        sysResourcesDao.persist(resources);

    }
    
    
    public void changePwd(Long userId, String oldPassword, String newPassword){
    	
    	sysUsersDao.changePassword(userId, oldPassword, newPassword);
    	
    }
    
    /**
     * 写日志，
     * @param log
     * @throws Exception
     */
    public void  writeLog(String userAccount,String module_name,String operation) throws Exception{
    	
    	SystemLog log=new SystemLog();
    	
    	SysUsers user=getSysUsersByuserAccount(userAccount);
    	
		log.setUserAccount(user.getUserAccount());
		
		log.setUserName(user.getUserName());
		
        log.setModule_name(module_name);
        
        log.setOperation(operation);
        

        log.setDate(new Date());

        dao.save(log);
    }
    
    /**
     * 获取  登录用户
     */
    private SysUsers getSysUsersByuserAccount(String userAccount) throws Exception{
        Session session = dao.getHibernateSession();
        try{
           Query q = session.createQuery(" from SysUsers u where u.userAccount = :userAccount and u.enabled=1");
           q.setParameter("userAccount", userAccount);
           SysUsers sysUsers = (SysUsers) q.uniqueResult();
           if (sysUsers == null) {
//               throw new UsernameNotFoundException(userAccount+"不存在");
           }
           return sysUsers;
        }catch (Exception ex){
            throw ex;
        } finally {
       	    session.clear();
            session.close();
		}

    }

   
    /**
     * Hql 查询
     * 获得用户信息
     *
     * @param userName
     * @return
     * @throws Exception
     * @throws DataAccessException
     */

   /* public UserDetails loadUserByUserName(String userName)
            throws Exception, DataAccessException {
        Session session = dao.getHibernateSession();
        Query q = session.createQuery(" from User  u where u.name = :name");
        q.setParameter("name", userName);
        User user = (User) q.uniqueResult();
        if (user == null) {
            throw new UsernameNotFoundException("用户名" + userName + "不存在");
        }
        session.clear();
        session.close();
        return user;
    }


    *//**
     * Criteria 查询方式
     *
     * @param name
     * @return
     * @throws Exception
     *//*
    public UserDetails getUserInfo(String name) throws Exception {
        Session session = dao.getHibernateSession();
        Criteria c = session.createCriteria(User.class)
                .add(Restrictions.eq("name", name));
        User user = (User) c.uniqueResult();

        if (user == null) {
            throw new UsernameNotFoundException("用户名" + name + "不存在");
        }
        System.out.println("-------getUserInfo 执行---------");
        session.clear();
        session.close();
        return user;

    }

    *//**
     *
     * 获得当前用户的所有权限
     *
     * @param userName
     * @return
     *//*
    public List<Role> getRoleByUserName(String userName) throws Exception {

        Session session = dao.getHibernateSession();

        Query q = session.createQuery("select r.id,r.name,r.description from User u ,Role r, UserRole ur" +
                " where u.id=ur.comUR.userId and r.id=ur.comUR.roleId and u.name = :name");

        q.setParameter("name", userName);

        List<Role> roles = (List<Role>) q.list();

        session.clear();
        session.close();
        return roles;

    }

    *//**
     * 获取所有资源
     * @return
     * @throws Exception
     *//*
    public List<Resource> findAllResource() throws Exception {

         Session session = dao.getHibernateSession();

          Query q = session.createQuery("from Resource r");

          List<Resource> resourceList = q.list();

         session.clear();
         session.close();
         return  resourceList;
    }*/

}