package com.arthouse.common.dao.impl;


import java.io.Serializable;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.util.Assert;

import com.arthouse.common.dao.ICommonDao;
import com.arthouse.common.page.Page;
import com.arthouse.security.entity.SysUsers;

/**
 * Created by IntelliJ IDEA.
 * User: barry.wang
 * Date: 11-4-22
 * Time: 下午4:58
 * To change this template use File | Settings | File Templates.
 * <p/>
 * 系统dao(crud)操作常用方法
 */

public class CommonDao extends HibernateDaoSupport implements ICommonDao {



    // 删除order by字句使用的正则表达式
    private static Pattern removeOrderByPattern = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*", Pattern.CASE_INSENSITIVE);


    /**
     * 创建或保存Obj
     *
     * @param obj
     * @throws Exception
     */
    public void saveOrUpdate(Object obj) throws Exception {

        this.getHibernateTemplate().saveOrUpdate(obj);


    }

    /**
     * 创建Obj
     *
     * @param obj
     * @throws Exception
     */
    public void save(Object obj) throws Exception {

        this.getHibernateTemplate().save(obj);


    }

    public void persist(Object obj)throws Exception{


        //this.getHibernateTemplate().persist(obj);
        this.getHibernateTemplate().merge(obj);


    }


    /**
     * 更新obj
     *
     * @throws Exception 更新时出错则抛出
     */
    public void update(Object obj) throws Exception {

        getHibernateTemplate().update(obj);

    }

    /**
     * 删除obj
     *
     * @throws Exception 删除时出错则抛出
     */
    public void delete(Object obj) throws Exception {

        getHibernateTemplate().delete(obj);


    }

    public void deleteById(Class po, int id) throws Exception {
        Object obj = getHibernateTemplate().get(po, new Integer(id));
        delete(obj);
    }


    /**
     * 根据主键ID删除obj
     *
     * @throws Exception 删除时出错则抛出
     */
    public void deleteById(Class po, Serializable id) throws Exception {

        Object obj = getHibernateTemplate().get(po, id);
        delete(obj);

    }


    /**
     * 清空某po所有的数据 强烈建议尽量不要使用此方法，一般来说不要物理删除数据，置状态比较合适
     *
     * @param po
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public void deleteAll(Class po) throws Exception {
        final String poName = po.getName();
        getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {
                String hql;
                hql = "delete from" + poName;
                session.createQuery(hql).executeUpdate();
                return null;
            }
        });


    }

    /**
     * 根据Hql删除
     *
     * @param hql
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public void deleteByHql(String hql) throws Exception {

        final String _hql = hql;
        getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {
                int amount = session.createQuery("delete " + _hql)
                        .executeUpdate();

                return new Integer(amount);
            }
        });

    }

    /**
     * 根据Hql删除
     *
     * @param hql
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public void deleteByHql(String hql, Map params) throws Exception {
        final Map _params = params;
        final String _hql = hql;

        getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {

                Query query = session.createQuery(_hql);
                setQueryParameters(_params, query);
                int amount = query.executeUpdate();

                return new Integer(amount);
            }
        });

    }

    /**
     * 根据为long型的主键ID获取对应的Object
     *
     * @param obj
     * @param id
     * @return Object
     * @throws Exception
     */
    public Object getById(Object obj, long id) throws Exception {


        return getHibernateTemplate().get(obj.getClass(), id);
    }


    public Object getById(Class po, int id) throws Exception {
        return getById(po, new Integer(id));
    }


    public Object getById(Object obj, int id) throws Exception {
        return getById(obj.getClass(), new Integer(id));
    }

    /**
     * 根据主键ID获取对应的Object
     *
     * @param po
     * @param id
     * @return Object
     * @throws Exception
     */
    public Object getById(Class po, Serializable id)
            throws Exception {

        return getHibernateTemplate().get(po, id);

    }

    /**
     * 根据需执行的hql返回List <br>
     * 建议对于数据量小或一次进行频繁查询的使用
     *
     * @param hql
     * @return java.util.List
     * @throws Exception
     */
    public List getListByHql(String hql) throws Exception {

        return this.getHibernateTemplate().find(hql);

    }

    /**
     * 根据需执行的hql以及参数返回List <br>
     * 建议对于数据量小或一次进行频繁查询的使用
     *
     * @param hql
     * @param params
     * @return java.util.List
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public List getListByHql(String hql, Map params)
            throws Exception {

        final String _hql = hql;
        final Map _params = params;

        return (List) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {

                Query query = session.createQuery(_hql);
                setQueryParameters(_params, query);
                return query.list();
            }

        });

    }


    private void setQueryParameters(final Map params, Query query) {
        if (params != null) {
            Iterator it = params.keySet().iterator();
            String name = null;
            Object value = null;

            while (it.hasNext()) {
                name = (String) it.next();
                value = params.get(name);
                if (value instanceof Collection) {
                    query.setParameterList(name, (Collection) value);
                } else if (value instanceof Object[]) {
                    query.setParameterList(name, (Object[]) value);
                } else {
                    query.setParameter(name, value);
                }
            }
        }
    }

    /**
     * 根据需执行的hql返回唯一对象 <br>
     * 建议对于数据量小或一次进行频繁查询的使用
     *
     * @param hql
     * @return Object
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public Object getUniqueObjectByHql(String hql) throws Exception {

        final String _hql = hql;
        return getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {

                return session.createQuery(_hql).uniqueResult();
            }
        });


    }

    /**
     * 根据需执行的hql返回唯一对象 <br>
     * 建议对于数据量小或一次进行频繁查询的使用
     *
     * @param hql
     * @return Object
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public Object getUniqueObjectByHql(String hql, Map params)
            throws Exception {

        final String _hql = hql;
        final Map _params = params;

        return this.getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {

                Query query = session.createQuery(_hql);
                setQueryParameters(_params, query);
                Object obj = query.uniqueResult();
                return obj;
            }

        });



    }


    /**
     * 根据需调用的namedQuery查询数据库返回相应的List <br>
     * 建议使用NamedQuery替代hql <br>
     * 建议对于数据量小或一次进行频繁查询的使用
     *
     * @param namedQuery 参数
     * @param params     所需查询的参数的名以及值
     * @return java.util.List
     * @throws Exception
     */

    @SuppressWarnings("unchecked")
    public List getListByNamedQuery(String namedQuery, Map params)
            throws Exception {

        final String _namedQuery = namedQuery;
        final Map _params = params;

        return (List) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {

                Query query = session.getNamedQuery(_namedQuery);
                setQueryParameters(_params, query);
                return query.list();
            }

        });


    }


    /**
     * 通过查询语句查询
     *
     * @param ql   查询语句
     * @param page 分页对象
     * @param args 参数
     * @return
     */
    @SuppressWarnings("unchecked")
    public List query(final String ql, final Page page, final Object... args) {
        return (List) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = session.createQuery(ql);
                for (int i = 0; i < args.length; i++) {
                    query.setParameter(i, args[i]);

                }
                query = (Query) CommonDao.this.analyse(query, page, CommonDao.this, args);
                page.setRecord(query.list());
                return page.getRecord();
            }

        });
    }

    @SuppressWarnings("unchecked")
    public List query1(final Query ql, final Page page, final Object... args) throws Exception{
        return (List) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {

                Query query = ql;
                try{

                  CommonDao.this.analyse1(query, page, CommonDao.this, args);
                }catch (Exception e){
                    e.printStackTrace();
                }
                page.setRecord(ql.list());
                return page.getRecord();
            }

        });
    }

    /**
     * @param query 查询语句
     * @param page  page对象
     * @param dao   dao实体
     * @param args  条件参数
     * @return
     */
    public Object analyse(Object query, Page page, ICommonDao dao, Object... args) {
        if (page.isCountTotalPage()) {
            page.setTotalRecordCount(dao.count(((Query) query).getQueryString(), args));
        }
        return this.analyse(query, page);
    }
    public Object analyse1(Object query, Page page, ICommonDao dao, Object... args) throws  Exception{
        if (page.isCountTotalPage()) {

             Query qq = (Query) query;
             List list = qq.list();
            page.setTotalRecordCount(list.size());
        }
        return this.analyse(query, page);
    }


    /**
     * @param q    查询语句
     * @param page page对象
     * @return
     */
    public Object analyse(Object q, Page page) {
        if (page.getPageSize() > 0) {
            if (q instanceof Query) {
                Query query = (Query) q;
                query.setFirstResult((page.getPageIndex() - 1) * page.getPageSize());
                query.setMaxResults(page.isCountTotalPage() ? page.getPageSize() : page.getPageSize() + 1);
            } else if (q instanceof Criteria) {
                Criteria c = (Criteria) q;
                c.setFirstResult((page.getPageIndex() - 1) * page.getPageSize());
                c.setMaxResults(page.isCountTotalPage() ? page.getPageSize() : page.getPageSize() + 1);
            }
        }
        return q;
    }

    /**
     * 计算记录数
     *
     * @param ql   查询语句
     * @param args 查询条件参数
     * @return
     */
    @SuppressWarnings("unchecked")
    public int count(final String ql, final Object... args) {
        final String countQueryString = " select count (*) " + this.removeSelect(this.removeOrderBy(ql));
        return ((Number) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = session.createQuery(ql);
                for (int i = 0; i < args.length; i++) {
                    query.setParameter(i, args[i]);
                }

                List countlist = getHibernateTemplate().find(countQueryString, args);
                Long totalCount = 0l;
                if (countlist != null && countlist.size() >= 1)
                    totalCount = (Long) countlist.get(0);
                return totalCount;
            }
        })).intValue();
    }

    /**
     * 去除ql语句中的select子句
     *
     * @param hql 查询语句
     * @return
     */
    private String removeSelect(String hql) {
        Assert.hasText(hql);
        int beginPos = hql.toLowerCase().indexOf("from");
        Assert.isTrue(beginPos != -1, " hql : " + hql + " must has a keyword 'from'");
        return hql.substring(beginPos);
    }


    /**
     * 删除ql语句中的order by字句
     *
     * @param ql 查询语句
     * @return
     */
    private String removeOrderBy(String ql) {
        if (ql != null && !"".equals(ql)) {
            Matcher m = removeOrderByPattern.matcher(ql);
            StringBuffer sb = new StringBuffer();
            while (m.find()) {
                m.appendReplacement(sb, "");
            }
            m.appendTail(sb);
            return sb.toString();
        }
        return "";
    }

    /**
     * 批量删除数据
     * @param ids
     * @param hql
     * @throws Exception
     */
    public void deleTobatch(final String[] ids,String hql) throws Exception {
        final String queryString = hql;
        final Integer id [] = new Integer[ids.length];
        for(int i=0;i<id.length;i++){
            id[i]=Integer.parseInt(ids[i]);
        }
        getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = session.createQuery(queryString);
                query.setParameterList("ids",id);
                return query.executeUpdate();
            }
        });
    }


    /**
     * 根据当前登录用户的权限获取其菜单集合
     *
     * @param authName
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public List getResourceByAuth(List<String> ListauthName) throws Exception {

      Session session = this.getHibernateSession();
    /*  List<String> query = session.createSQLQuery("select b.resourceString "
                                        + "from sys_authorities_resources a, sys_resources b, "
                                        + "sys_authorities c where a.RESOURCE_ID = b.id "
                                        + "and a.AUTHORITY_ID=c.id and b.resourceString!= '/main.action'  and c.authorityName='"
                                        + authName + "'").list();
     */
      String[] authrArray = new String[ListauthName.size()];
      for(int i=0;i<authrArray.length;i++){
    	  authrArray[i] = ListauthName.get(i);
      }
      
      String sql =  "select b.resourceString "
        + "from sys_authorities_resources a, sys_resources b,sys_roles_authorities d,"
        + "sys_authorities c, sys_roles e where e.roleName in(:roleName) and a.RESOURCE_ID = b.id "+" and d.AUTHORITY_ID=c.id "+" and e.id = d.ROLE_ID"
        + " and a.AUTHORITY_ID=c.id and b.resourceString!= '/main.action' ";
      
      List<String> query = session.createSQLQuery(sql).setParameterList("roleName", authrArray).list();
      session.clear();
      session.close();
      return  query;

    }

    /**
     * 根据登陆名称获取子菜单权限
     * @param authName
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public List getSubMenuResourceBYAuth(String authName)throws Exception{
    	
    	 Session session = this.getHibernateSession();
         List<String> query = session.createSQLQuery("select b.resourceString "
                                           + "from sys_authorities_resources a, sys_resources b, "
                                           + "sys_authorities c where a.RESOURCE_ID = b.id "
                                           + "and a.AUTHORITY_ID=c.id  and c.authorityName='"
                                           + authName + "'").list();
        
         session.clear();
         session.close();
         return  query;

    	
    }


    /**
     * 根据hql以及查询参数获取查询的结果的总数
     *
     * @return int
     * @throws Exception
     */

    public Session getHibernateSession() throws Exception {


        return getHibernateTemplate().getSessionFactory().openSession();
    }

    public void flush() throws Exception {
        Session session = getHibernateSession();
        session.flush();

    }

    //批量更新(删除，恢复)
    public void updTobatch(final String[] ids,String hql) throws Exception {
        final String queryString = hql;
        //final String id [] = new String[ids.length];
        final Long id[]=new Long[ids.length];
        for(int i=0;i<ids.length;i++){
            id[i]=Long.valueOf(ids[i]);
        }
        getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = session.createQuery(queryString);
                query.setParameterList("ids",id);
                return query.executeUpdate();
            }
        });
    }

    public void updTobatch2(final String[] ids,String hql) throws Exception {
        final String queryString = hql;
        //final String id [] = new String[ids.length];
        final String id[]=new String[ids.length];
        for(int i=0;i<ids.length;i++){
            id[i]=String.valueOf(ids[i]);
        }
        getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = session.createQuery(queryString);
                query.setParameterList("ids",id);
                return query.executeUpdate();
            }
        });
    }

    public void updTobatch(String hql) throws Exception {
        final String queryString = hql;
        getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = session.createQuery(queryString);
                return query.executeUpdate();
            }
        });
    }
    
    public SysUsers getSysUsersByuserAccount(String userAccount) throws Exception{
    	
    	String sql=" from SysUsers u where u.userAccount ='"+userAccount+"' and u.enabled=1";
    	SysUsers sysUsers=(SysUsers)getUniqueObjectByHql(sql);
    	
        return sysUsers;

    }

}
