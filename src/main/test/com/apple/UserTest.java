package com.apple;


import java.util.HashSet;
import java.util.Set;

import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.arthouse.security.UserDetailsService;
import com.arthouse.security.entity.SysAuthorities;
import com.arthouse.security.entity.SysAuthoritiesResources;
import com.arthouse.security.entity.SysResources;
import com.arthouse.security.entity.SysRoles;
import com.arthouse.security.entity.SysRolesAuthorities;
import com.arthouse.security.entity.SysUsers;
import com.arthouse.security.entity.SysUsersRoles;

//第一次请执行main方法生成表
public class UserTest {

    static ApplicationContext ac;

   // static MessageTypeService messageTypeService;

    static UserDetailsService userDetailsService;

    

    @BeforeClass
    public static void beforeClass() {

        try {
			ac = new ClassPathXmlApplicationContext("spring.*.xml");
			userDetailsService = (UserDetailsService) ac
					.getBean("UserDetailService");
			System.out.println(userDetailsService + "---------执行成功!");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
    }

    @AfterClass
    public static void afterClass() {

    }

    @Test
    public void testSchemaExport() {
        new SchemaExport(new AnnotationConfiguration().configure()).create(
                false, true);
    }


    @Test
    public void tetSaveSysUser() throws Exception {

        //中间表
        Set<SysUsersRoles> sysUsersRoleses = new HashSet<SysUsersRoles>();
        Set<SysRolesAuthorities> sysRolesAuthoritieses = new HashSet<SysRolesAuthorities>();
        Set<SysAuthoritiesResources> sysAuthoritiesResourceses = new HashSet<SysAuthoritiesResources>();

        //资源
        SysResources sysResources = new SysResources();
        sysResources.setResourceName("index");
        sysResources.setResourceDesc("登陆后欢迎页面");
        sysResources.setResourceType("action");
        sysResources.setResourceString("/main.action");
        sysResources.setEnabled(1);
        sysResources.setIssys(1);
        sysResources.setModule("01");


        //权限
        SysAuthorities sysAuthorities = new SysAuthorities();
        sysAuthorities.setAuthorityName("ROLE_AD");
        sysAuthorities.setAuthorityDesc("登录后欢迎界面");
        sysAuthorities.setEnabled(true);
        sysAuthorities.setIssys(true);
        sysAuthorities.setModule("01");

        //角色
        SysRoles role  = new SysRoles();
        role.setRoleName("ROLE_ADMIN");
        role.setRoleDesc("管理本系统的单位用户和设置");
        role.setEnabled(true);
        role.setIssys(true);
        role.setModule("01");


        //用户
        SysUsers user = new SysUsers();
        user.setUserAccount("admin");
        user.setUserName("系统管理员");
        user.setUserPassword("1");
        user.setUserDesc("超级系统管理员");
        user.setEnabled(true);
        user.setIssys(true);
        user.setUserDept("0");
        user.setSubSystem("01");


        SysUsersRoles userRole = new SysUsersRoles(user, role, true);
        sysUsersRoleses.add(userRole);
        user.setSysUsersRoleses(sysUsersRoleses);


        SysRolesAuthorities sysRolesAuthorities = new SysRolesAuthorities(sysAuthorities,role,true);
        sysRolesAuthoritieses.add(sysRolesAuthorities);
        role.setSysRolesAuthorities(sysRolesAuthoritieses);

        SysAuthoritiesResources sysAuthoritiesResources = new SysAuthoritiesResources(sysAuthorities,sysResources,true);
        sysAuthoritiesResourceses.add(sysAuthoritiesResources);
        sysResources.setPubAuthoritiesResourceses(sysAuthoritiesResourceses);
       
        userDetailsService.persistAuth(sysAuthorities);
        userDetailsService.persistResource(sysResources);
        userDetailsService.persistRole(role);
        userDetailsService.persist(user);




    }







  /*  @Test
    public void testSaveUserDetail() throws Exception {
        *//**
         *  角色
         *//*
        Role role = new Role();
        role.setName("ROLE_ADMIN");
        role.setDescription("管理员");
        role.setAuthed(0);

        Role role2 = new Role();
        role2.setName("ROLE_USER");
        role2.setDescription("普通用户");
        role2.setAuthed(0);

        *//**
         * 资源
         *//*
        Resource rs  = new Resource();
        rs.setResourceName("列出用户");
        rs.setResourceUrl("/user_list*");
        
        Resource rs2  = new Resource();
        rs2.setResourceName("添加用户");
        rs2.setResourceUrl("*//*adduser*");

        Resource rs3  = new Resource();
        rs3.setResourceName("更改用户状态");
        rs3.setResourceUrl("*//*alterState*");

        Resource rs4  = new Resource();
        rs4.setResourceName("删除");
        rs4.setResourceUrl("*//*delete*");

        *//**
         * 用户
         *//*
        User user = new User();
        user.setName("admin");
        user.setPassword("admin");
        user.setDisabled(1);
        user.setRemark("管理员");


        *//**
         * 录入用户1信息
         *//*
        Set<Resource> resources1= new HashSet<Resource>();
        resources1.add(rs);
        role.setResources(resources1);
        Set<Role> roles1 = new HashSet<Role>();
        roles1.add(role);
        user.setRole(roles1);
        userDetailsService.persist(user);


        *//**
         * 录入用户2信息
         *//*
        User user2 = new User();
        user2.setName("zhangsan");
        user2.setPassword("zhangsan");
        user2.setDisabled(1);
        user2.setRemark("普通用户");
        Set<Resource> resources2= new HashSet<Resource>();
        resources2.add(rs2);
        role2.setResources(resources2);
        Set<Role> roles2 = new HashSet<Role>();
        roles2.add(role2);
        user2.setRole(roles2);
        userDetailsService.persist(user2);


        *//**
         * 录入用户3信息
         *//*
        User user3 = new User();
        user3.setName("lisi");
        user3.setPassword("lisi");
        user3.setDisabled(1);
        user3.setRemark("普通用户");
        Set<Resource> resources3= new HashSet<Resource>();
        resources3.add(rs3);
        role2.setResources(resources3);
        Set<Role> roles3 = new HashSet<Role>();
        roles3.add(role2);
        user3.setRole(roles3);
        userDetailsService.persist(user3);

        *//**
         * 录入用户4信息
         *//*
        User user4 = new User();
        user4.setName("wangwu");
        user4.setPassword("wangwu");
        user4.setDisabled(1);
        user4.setRemark("普通用户");
        Set<Resource> resources4= new HashSet<Resource>();
        resources4.add(rs4);
        role2.setResources(resources4);
        Set<Role> roles4 = new HashSet<Role>();
        roles4.add(role2);
        user4.setRole(roles4);
        userDetailsService.persist(user4);



    }

    @Test
    public void getUserDetailsData() throws Exception {

        String userName = "wangwu";

        User user =(User) userDetailsService.getUserInfo(userName);
       // System.out.println("*******************"+user.getUsername());

        for(Iterator it = user.getRole().iterator(); it.hasNext();){

            Role role = (Role)it.next();
              System.out.println("------"+role.getName());
            
        }


    }


    @Test
    public void getRolesByUserNameTest() throws Exception {
        String userName = "wangwu";
        List roleList = userDetailsService.getRoleByUserName(userName);
        for( Iterator it=roleList.iterator(); it.hasNext();){
            Object[] obj=(Object[])it.next();
            Long id = (Long)obj[0];
            System.out.println("权限Id-----------------"+id);
            String name  = (String)obj[1];
            System.out.println("权限名称----------------"+name);
        }
    }

    @Test
     public void testfindAllResource() throws Exception {

         List<Resource> list = userDetailsService.findAllResource();
         for(Resource resource:list){

             System.out.println("resourceName------------"+resource.getResourceName());
              System.out.println("url------------"+resource.getResourceUrl());



         }


     }*/



    public static void main(String[] args) {
        beforeClass();

    }
}
