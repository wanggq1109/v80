package com.arthouse.security;

import java.util.ArrayList;
import java.util.Collection;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.arthouse.security.dao.SysAuthoritiesResourcesDao;
import com.arthouse.security.dao.SysUsersDao;
import com.arthouse.security.entity.SysUsers;

/**
 * 
 * Created by IntelliJ IDEA.
 * User: barry.wang
 * Date: 11-6-8
 * Time: 下午3:23
 * To change this template use File | Settings | File Templates.
 */

public class CustomUserDetailsService implements UserDetailsService {


	private SysUsersDao sysUsersDao;


	private SysAuthoritiesResourcesDao sysAuthoritiesResourcesDao;


	private DataSource dataSource;
	
	private UserCache userCache;



	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException, DataAccessException {

		Collection<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();

		if( null == sysUsersDao ){
			sysUsersDao  = new SysUsersDao();
		}

		//得到用户的权限
		auths = sysUsersDao.loadUserAuthoritiesByName( username );

		//根据用户名取得一个SysUsers对象，以获取该用户的其他信息。
		SysUsers user = sysUsersDao.findByUserAccount( username );

		
        SysUsers sysUsers = new SysUsers( user.getId(), user.getUserAccount(), user.getUserName(),
				 user.getUserPassword(),user.getUserDesc(), true, false,
				 user.getUserDuty(), user.getUserDept(), user.getSubSystem(),
				 user.getSysUsersRoleses(), true, true, true, auths);

		return  sysUsers;
	}


    public SysUsersDao getSysUsersDao() {
        return sysUsersDao;
    }

    public void setSysUsersDao(SysUsersDao sysUsersDao) {
        this.sysUsersDao = sysUsersDao;
    }

    public SysAuthoritiesResourcesDao getSysAuthoritiesResourcesDao() {
        return sysAuthoritiesResourcesDao;
    }

    public void setSysAuthoritiesResourcesDao(SysAuthoritiesResourcesDao sysAuthoritiesResourcesDao) {
        this.sysAuthoritiesResourcesDao = sysAuthoritiesResourcesDao;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }


	public UserCache getUserCache() {
		return userCache;
	}


	public void setUserCache(UserCache userCache) {
		this.userCache = userCache;
	}
    
    
}
