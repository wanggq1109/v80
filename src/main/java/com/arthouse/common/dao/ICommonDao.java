package com.arthouse.common.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;

import com.arthouse.common.page.Page;
import com.arthouse.security.entity.SysUsers;
/**
 * Created by IntelliJ IDEA.
 * User: barry.wang
 * Date: 11-4-22
 * Time: 下午4:50
 * To change this template use File | Settings | File Templates.
 *
 * 系统dao(crud)操作常用方法
 */
public interface ICommonDao {
	

	/**
	 * 创建或保存Obj
	 * 
	 * @param obj
	 * @throws Exception
	 */
	public void saveOrUpdate(Object obj) throws Exception;
	
	/**
	 * 创建Obj
	 * 
	 * @param obj
	 * @throws Exception
	 */
	public  void save(Object obj) throws Exception;


    /**
     * 级联时数据的保存（当cascade = CascadeType.PRESIST时，保存数据必须用该方法）
     * @param obj
     * @throws Exception
     */
    public void persist(Object obj)throws Exception;
	

	/**
	 * 更新obj
	 * 
	 * @throws Exception
	 *             更新时出错则抛出
	 */
	public abstract void update(Object obj) throws Exception;

	/**
	 * 删除obj
	 * 
	 * @throws Exception
	 *             删除时出错则抛出
	 */
	public abstract void delete(Object obj) throws Exception;

	/**
	 * 根据主键ID删除obj
	 * 
	 * @throws Exception
	 *             删除时出错则抛出
	 */
	public void deleteById(Class po, Serializable id) throws Exception;

	/**
	 * 根据主键ID删除obj
	 * 
	 * @throws Exception
	 *             删除时出错则抛出
	 */
	public abstract void deleteById(Class po, int id) throws Exception;

	/**
	 * 清空某po所有的数据 强烈建议尽量不要使用此方法，一般来说不要物理删除数据，置状态比较合适
	 * 
	 * @param po
	 * @throws Exception
	 */
	public abstract void deleteAll(Class po) throws Exception;

	/**
	 * 根据Hql删除
	 * 
	 * @param hql
	 * @throws Exception
	 */
	public abstract void deleteByHql(String hql) throws Exception;

	/**
	 * 根据hql和parmas删除
	 * 
	 * @param hql
	 * @param params
	 * @throws Exception
	 */
	public abstract void deleteByHql(String hql, Map params)
			throws Exception;

	/**
	 * 根据为int型的主键ID获取对应的Object
	 * 
	 * @param obj
	 * @param id
	 * @return Object
	 * @throws Exception
	 */
	public abstract Object getById(Object obj, int id) throws Exception;

	/**
	 * 根据为int型的主键ID获取对应的Object
	 * 
	 * @param po
	 * @param id
	 * @return Object
	 * @throws Exception
	 */
	public abstract Object getById(Class po, int id) throws Exception;

	/**
	 * 根据主键ID获取对应的Object
	 * 
	 * @param id
	 * @return Object
	 * @throws Exception
	 */
	public abstract Object getById(Class po, Serializable id)
			throws Exception;

	/**
	 * 根据需执行的hql返回List <br>
	 * 建议对于数据量小或一次进行频繁查询的使用
	 * 
	 * @param hql
	 * @return java.util.List
	 * @throws Exception
	 */
	public abstract List getListByHql(String hql) throws Exception;

	/**
	 * 根据需执行的hql以及参数返回List <br>
	 * 建议对于数据量小或一次进行频繁查询的使用
	 * 
	 * @param hql
	 * @param params
	 * @return java.util.List
	 * @throws Exception
	 */
	public abstract List getListByHql(String hql, Map params)
			throws Exception;

	/**
	 * 根据需执行的hql返回唯一对象 <br>
	 * 建议对于数据量小或一次进行频繁查询的使用
	 * 
	 * @param hql
	 * @return Object
	 * @throws Exception
	 */
	public abstract Object getUniqueObjectByHql(String hql) throws Exception;

	/**
	 * 根据需执行的hql返回唯一对象 <br>
	 * 建议对于数据量小或一次进行频繁查询的使用
	 * 
	 * @param hql
	 * @return Object
	 * @throws Exception
	 */
	public abstract Object getUniqueObjectByHql(String hql, Map params)
			throws Exception;

    /**
	 * 通过查询语句查询
	 *
	 * @param ql
	 *            查询语句
	 * @param page
	 *            分页对象
	 * @param args
	 *            参数
	 * @return
	 */
	public List query(String ql, Page page, Object... args);

    public List query1(Query ql, Page page, Object... args) throws Exception;

	/**
	 * 计算记录数
	 *
	 * @param ql
	 *            查询语句
	 * @param args
	 *            查询条件参数
	 * @return
	 */
	public int count(String ql, Object... args);
	/**
	 * 根据需调用的namedQuery查询数据库返回相应的List <br>
	 * 建议使用NamedQuery替代hql <br>
	 * 建议对于数据量小或一次进行频繁查询的使用
	 * 
	 * @param namedQuery
	 *            参数
	 * @param params
	 *            所需查询的参数的名以及值
	 * @return java.util.List
	 * @throws Exception
	 */
	public abstract List getListByNamedQuery(String namedQuery, Map params)
			throws Exception;




	
	/**
	 * 获取查询的结果的总数
	 * 
	 * @param hql
	 * @return int
	 * @throws Exception
	 */
	//public abstract int getCount(String hql) throws Exception;

	/**
	 * 根据hql以及查询参数获取查询的结果的总数
	 * 
	 * @param hql
	 * @param
	 * @return int
	 * @throws Exception
	 */
	/*public int count(final String ql, final Object... args) throws Exception;
	*/

   public List getResourceByAuth(List<String> ListauthName) throws Exception ;
   
   /**
    * 根据登陆名称获取子菜单权限
    * @param authName
    * @return
    * @throws Exception
    */
   public List getSubMenuResourceBYAuth(String authName)throws Exception;

    /**
     * 批量删除数据
     * @param ids
     * @param hql
     * @throws Exception
     */
    public void deleTobatch(final String[] ids,String hql) throws Exception;


    public Session getHibernateSession()throws Exception;

    public void updTobatch(final String[] ids,String hql) throws Exception;
    public void updTobatch2(final String[] ids,String hql) throws Exception;
    
    public void updTobatch(String hql) throws Exception;
    
    public SysUsers getSysUsersByuserAccount(String userAccount) throws Exception;



}
