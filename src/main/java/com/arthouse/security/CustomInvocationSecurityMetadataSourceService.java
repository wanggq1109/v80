package com.arthouse.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.AntUrlPathMatcher;
import org.springframework.security.web.util.UrlMatcher;

import com.arthouse.security.dao.SysAuthoritiesResourcesDao;


/**
 * Created by IntelliJ IDEA.
 * User: barry.wang
 * Date: 11-6-8
 * Time: 下午3:33
 * To change this template use File | Settings | File Templates.

 *
 * 最核心的地方，就是提供某个资源对应的权限定义，即getAttributes方法返回的结果。 此类在初始化时，应该取到所有资源及其对应角色的定义。
 *
 */

public class CustomInvocationSecurityMetadataSourceService implements
        FilterInvocationSecurityMetadataSource  {



        private SysAuthoritiesResourcesDao pubAuthoritiesResourcesDao;

        private UrlMatcher urlMatcher = new AntUrlPathMatcher();

        private static Map<String, Collection<ConfigAttribute>> resourceMap = null;

        public CustomInvocationSecurityMetadataSourceService()throws Exception{
            loadResourceDefine();
        }

       @SuppressWarnings("unchecked")
        private void loadResourceDefine() throws Exception {
          /*  ApplicationContext context = new ClassPathXmlApplicationContext(
                    "classpath:applicationContext.xml");

            SessionFactory sessionFactory = (SessionFactory) context
                    .getBean("sessionFactory");*/

          ApplicationContext  ac = new ClassPathXmlApplicationContext("spring.common.xml");
          SessionFactory sessionFactory =(SessionFactory)ac.getBean("sessionFactory");
          Session session = sessionFactory.openSession();
         


            try {
				String username = "";
				String sql = "";
				// 在Web服务器启动时，提取系统中的所有权限。
				sql = "select authorityName from sys_authorities";
				List<String> query = session.createSQLQuery(sql).list();
				/*
				 * 应当是资源为key， 权限为value。 资源通常为url， 权限就是那些以ROLE_为前缀的角色。 一个资源可以由多个权限来访问。
				 */
				resourceMap = new HashMap<String, Collection<ConfigAttribute>>();
				for (String auth : query) {
					ConfigAttribute ca = new SecurityConfig(auth);

					List<String> query1 = session
							.createSQLQuery(
									"select b.resourceString "
											+ "from sys_authorities_resources a, sys_resources b, "
											+ "sys_authorities c where a.RESOURCE_ID = b.id "
											+ "and a.AUTHORITY_ID=c.id and c.authorityName='"
											+ auth + "'").list();

					for (String res : query1) {
						String url = res;

						/*
						 * 判断资源文件和权限的对应关系，如果已经存在相关的资源url，则要通过该url为key提取出权限集合，将权限增加到权限集合中。
						 */
						if (resourceMap.containsKey(url)) {

							Collection<ConfigAttribute> value = resourceMap
									.get(url);
							value.add(ca);
							resourceMap.put(url, value);
						} else {
							Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();
							atts.add(ca);
							resourceMap.put(url, atts);
						}

					}

				}
			} catch (Exception e) {
				session.clear();
	            session.close();
	            e.printStackTrace();
				// TODO: handle exception
			}finally{
				
				session.clear();
	            session.close();
	            
	          
	            
			}
			
        }

        public Collection<ConfigAttribute> getAllConfigAttributes() {

            return null;
        }

        // 根据URL，找到相关的权限配置。
        public Collection<ConfigAttribute> getAttributes(Object object)
                throws IllegalArgumentException {

            // object 是一个URL，被用户请求的url。
            String url = ((FilterInvocation) object).getRequestUrl();

            int firstQuestionMarkIndex = url.indexOf("?");

            if (firstQuestionMarkIndex != -1) {
                url = url.substring(0, firstQuestionMarkIndex);
            }

            Iterator<String> ite = resourceMap.keySet().iterator();

            while (ite.hasNext()) {
                String resURL = ite.next();

                if (urlMatcher.pathMatchesUrl(url, resURL)) {

                    return resourceMap.get(resURL);
                }
            }

            return null;
        }

        public boolean supports(Class<?> arg0) {

            return true;
        }

    public SysAuthoritiesResourcesDao getPubAuthoritiesResourcesDao() {
        return pubAuthoritiesResourcesDao;
    }

    public void setPubAuthoritiesResourcesDao(SysAuthoritiesResourcesDao pubAuthoritiesResourcesDao) {
        this.pubAuthoritiesResourcesDao = pubAuthoritiesResourcesDao;
    }



}
