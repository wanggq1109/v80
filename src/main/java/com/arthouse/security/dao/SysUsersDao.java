package com.arthouse.security.dao;

import com.arthouse.security.CustomUserDetails;
import com.arthouse.security.entity.SysAuthorities;
import com.arthouse.security.entity.SysRoles;
import com.arthouse.security.entity.SysUsers;
import com.arthouse.security.entity.SysUsersRoles;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.sql.SQLException;
import java.util.*;

/**
 *
 * Created by IntelliJ IDEA.
 * User: barry.wang
 * Date: 11-6-8
 * Time: 下午1:14
 * To change this template use File | Settings | File Templates.
 */
public class SysUsersDao extends HibernateDaoSupport {

    public static final Logger log = LoggerFactory
            .getLogger(SysUsersDao.class);


    // 对用户输入的密码进行MD5编码
    private PasswordEncoder passwordEncoder;

    // 通过SysrolesDao获得角色实例。
    private SysRolesDao sysRolesDao;

    @SuppressWarnings("null")
    public void persist(SysUsers transientInstance, Long[] rolesArr) {
        log.debug("持久化一个用户实例！");
        try {
            // 先将该用户对应的所有角色
            Set<SysUsersRoles> userRoles = null;

            // 角色实例
            SysRoles role = null;

            if(rolesArr!=null){
             userRoles =  new HashSet<SysUsersRoles>();
            for (Long roleId : rolesArr) {

                if (null != roleId && !"".equals(roleId)) {

                    role = sysRolesDao.findById(roleId);

                    SysUsersRoles userRole = new SysUsersRoles(transientInstance, role, true);

                    userRoles.add(userRole);
                }

            }
            }
            //transientInstance.setUserId( Util.getPkId()+"");

            // 密码通过盐值加密以备存储入数据库
            String newPassword = passwordEncoder.encodePassword(transientInstance.getUserPassword().trim(), transientInstance.getUserAccount().trim());

            transientInstance.setUserPassword(newPassword);

            if (null != userRoles)
                transientInstance.setSysUsersRoleses(userRoles);

            getHibernateTemplate().persist(transientInstance);

            log.debug("持久化实例成功！");
        } catch (RuntimeException re) {
            log.error("持久化用户实例失败！", re);
            throw re;
        }
    }
    
    
    public SysUsers getUser(Long userId){
    	
    	
    	SysUsers user = getHibernateTemplate().get(SysUsers.class, userId);
    	
    	return user;
    	
    }
    
    
    public SysUsers getUserByName(String userName){
    	
    	
    	 Session session = getHibernateTemplate().getSessionFactory().openSession();
    	 Query q = session.createQuery(" from SysUsers  u where u.userAccount = :name");
         q.setParameter("name", userName);
         SysUsers user = (SysUsers) q.uniqueResult();
         session.clear();
         session.close();
    	
    	return user;
    }

    /*
      * 允许当前用户修改密码
      */
     public void changePassword(Long userId,String oldPassword, String
     newPassword) {
    	 Object obj = (UserDetails) SecurityContextHolder
     .getContext().getAuthentication().getPrincipal();
     String username = ((CustomUserDetails)obj).getUserAccount();
    
     String encodedOldPassword = passwordEncoder.encodePassword(oldPassword,
     username);
     String encodedNewPassword = passwordEncoder.encodePassword(newPassword,
     username);
    
     SysUsers user = getHibernateTemplate().get(SysUsers.class, userId);
     String  persistPwd = user.getUserPassword();
    
     if(encodedOldPassword.equals(persistPwd)){
    	 user.setUserPassword(encodedNewPassword);
    	 getHibernateTemplate().update(user);
     }else{
    	 
    	 log.debug("用户名或密码错误！");
    	 
     }
     
     
     }
    /*
      * 保存或更新一个用户实例，并且保存用户和角色之间的关联关系。
      */
    @SuppressWarnings("null")
    public void attachDirty(SysUsers instance, Long[] rolesArr,
                            String tempPassword) {
        log.debug("更新一个用户实例");
        try {

            // 先将该用户对应的所有角色
            Set<SysUsersRoles> userRoles = new HashSet<SysUsersRoles>();

            // 角色实例
            SysRoles role = new SysRoles();

            for (Long roleId : rolesArr) {

                if (null != roleId && !"".equals(roleId)) {

                    role = sysRolesDao.findById(roleId);

                    userRoles.add((SysUsersRoles) new SysUsersRoles(instance, role, true));
                }

            }


//			if( null == instance.getUserId() || "".equals( instance.getUserId()))
//				instance.setUserId( Util.getPkId()+"");

            //若密码已经更新
            if (tempPassword.equals(instance.getUserPassword())) {

                // 密码通过盐值加密以备存储入数据库
                String newPassword = passwordEncoder.encodePassword(instance
                        .getUserPassword().trim(), instance.getUserName()
                        .trim());

                instance.setUserPassword(newPassword);
            }

            if (null != userRoles)
                instance.setSysUsersRoleses(userRoles);

            getHibernateTemplate().saveOrUpdate(instance);

            log.debug("更新用户实例成功！");

        } catch (RuntimeException re) {
            log.error("更新用户实例失败！", re);
            throw re;
        }
    }

    public void attachClean(SysUsers instance) {
        log.debug("attaching clean SysUsers instance");
        try {
            getHibernateTemplate().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void delete(SysUsers persistentInstance) {
        log.debug("deleting SysUsers instance");
        try {
            getHibernateTemplate().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    /*
      * 根据用户id删除一个用户，非物理删除，而是一个逻辑删除，置enabled为false。
      */
    public void delete(Long userId) {
        log.debug("根据一个用户id删除一个用户，角色id为：" + userId);
        try {

            SysUsers user = findById(userId);
            user.setEnabled(false);
            getHibernateTemplate().saveOrUpdate(user);

            log.debug("删除成功！");
        } catch (RuntimeException re) {

            log.error("删除失败！", re);
            throw re;
        }
    }

    public SysUsers merge(SysUsers detachedInstance) {
        log.debug("merging SysUsers instance");
        try {
            SysUsers result = (SysUsers) getHibernateTemplate().merge(
                    detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public SysUsers findById(Long id) {
        log.debug("getting SysUsers instance with id: " + id);
        try {
            SysUsers instance = (SysUsers) getHibernateTemplate().get(
                    "com.artmuseum.security.entity.SysUsers", id);
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
     * 根据用户账号返回SysUsers实例对象。
     *
     * @param userAccount 用户账号，比如admin等。
     * @return SysUsers实例对象。
     * @author  2011-4-8 下午01:53:05
     */
    public SysUsers findByUserAccount(String userAccount) {
        log.debug("根据UserAccount查找SysUsers实例对象: " + userAccount);
        
        
        try {
        	 Session session = getHibernateTemplate().getSessionFactory().openSession();
        	 Query q = session.createQuery(" from SysUsers  u where u.userAccount = :userAccount");
             q.setParameter("userAccount", userAccount);
        	
           /* SysUsers instance = (SysUsers) getHibernateTemplate().find(
                    "from SysUsers where userAccount='" + userAccount + "'")
                    .get(0);*/
             SysUsers instance = (SysUsers) q.uniqueResult();
            if (instance == null) {
                log.debug("没有相匹配的SysUsers实例对象！");
                instance = new SysUsers();
            } else {
                log.debug("相匹配的SysUsers实例对象被找到！");
            }
            session.clear();
            session.close();
            return instance;
        } catch (RuntimeException re) {
            log.error("findByUserAccount() 错误！", re);
            throw re;
        }
    }

    /*
      * 得到用户列表，参数列表（查询条件）分别为用户所在单位、用户账号、用户名称、 用户所在的子系统、跟用户相关联的角色。
      */
    public List<SysUsers> findUserLst(String qryUnit, String qryUserAccount,
                                      String qryUsername, String qryModule, String qryRole) {

        List<SysUsers> list = null;

        String sql = "select users from SysUsers users where users.enabled = 1 ";

        //查询与用户相关联的角色列表。
        if (null != qryRole && !"".equals(qryRole)) {

            sql = "select users from SysUsers users, SysUsersRoles usersRoles "
                    + "where users.id = usersRoles.sysUsers.id and users.enabled = 1 ";
        }

        try {

            if (null != qryUnit && !"".equals(qryUnit)) {
                sql += "and users.userDept='" + qryUnit + "' ";
            }

            if (null != qryUserAccount && !"".equals(qryUserAccount)) {
                sql += "and users.userAccount='" + qryUserAccount + "' ";
            }

            if (null != qryUsername && !"".equals(qryUsername)) {
                sql += "and users.userName = '" + qryUsername + "' ";
            }

            if (null != qryModule && !"".equals(qryModule) && !"00".equals(qryModule)) {
                sql += "and users.subSystem='" + qryModule + "' ";
            }

            if (null != qryRole && !"".equals(qryRole) && !"00".equals(qryRole)) {
                sql += "and usersRoles.sysRoles.id ='" + qryRole + "'";
            }

            list = getHibernateTemplate().find(sql);

            return list;

        } catch (RuntimeException re) {
            log.error("findUserLst() 错误！", re);
            throw re;
        }

    }


    /*
      * 通过系统模块得到角色列表。
      */
    public HashMap getRolesMap(String xtmk) {

        HashMap rolesMap = new HashMap();

        try {
            String hql = "from SysRoles where module='" + xtmk + "'";

            List<SysRoles> list = getHibernateTemplate().find(hql);

            for (SysRoles role : list) {
                rolesMap.put(role.getId(), role.getRoleDesc());
            }
        } catch (RuntimeException re) {
            log.error("提取角色Map失败！", re);
            throw re;
        }

        return rolesMap;
    }

    /**
     * @param username
     * @param
     * @return
     */
    public List<GrantedAuthority> loadUserAuthoritiesByName(String username) {

        try {

            List<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();

            List<String> query1 = loadUserAuthorities(username);

            for (String roleName : query1) {
                GrantedAuthorityImpl authority = new GrantedAuthorityImpl(
                        roleName);
                auths.add(authority);
            }

            return auths;

        } catch (RuntimeException re) {
            log.error("find by authorities by username failed.", re);
            throw re;
        }
    }

    /**
     * @param username
     * @param
     * @return
     * @author  2011-3-30 下午03:51:48
     */
    @SuppressWarnings("unchecked")
    public List<String> loadUserAuthorities(final String username) {

        try {

            return this.getHibernateTemplate().executeFind(
                    new HibernateCallback() {
                        public List<SysAuthorities> doInHibernate(
                                Session session) throws HibernateException,
                                SQLException {
                            Query query = session
                                    .createSQLQuery("select a.authorityName "
                                            + "from sys_authorities a, sys_roles_authorities b, sys_users_roles c, sys_users d "
                                            + "where   a.id = b.AUTHORITY_ID and b.ROLE_ID = c.ROLE_ID  "
                                            + "and c.USER_ID = d.id and  d.userAccount = '"
                                            + username + "'");

                            return query.list();
                        }
                    });

        } catch (RuntimeException re) {
            log.error("find by authorities by username failed.", re);
            throw re;
        }
    }

    // 注入密码MD5编码
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

    // 注入sysRolesDao
    public void setSysRolesDao(SysRolesDao sysRolesDao) {
        this.sysRolesDao = sysRolesDao;
    }

    public SysRolesDao getSysRolesDao() {
        return sysRolesDao;
    }
}
