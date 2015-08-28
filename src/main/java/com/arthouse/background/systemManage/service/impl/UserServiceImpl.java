package com.arthouse.background.systemManage.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.arthouse.background.systemManage.service.UserService;
import com.arthouse.common.dao.ICommonDao;
import com.arthouse.common.domain.Chaicompany;
import com.arthouse.common.dto.RoleListDto;
import com.arthouse.common.page.Page;
import com.arthouse.security.entity.SysRoles;
import com.arthouse.security.entity.SysUsers;

/**
 * Created by IntelliJ IDEA.
 * User: barry.wang
 * Date: 11-5-4
 * Time: 下午5:16
 * To change this template use File | Settings | File Templates.
 */
public class UserServiceImpl implements UserService {

    private ICommonDao dao;

    public ICommonDao getDao() {
        return dao;
    }


    public void setDao(ICommonDao dao) {
        this.dao = dao;
    }


     public void save(Object obj)throws Exception{

        dao.save(obj);

     }

     public void update(Object obj)throws Exception{

        dao.update(obj);

     }

     public void delete(Object obj)throws Exception{

        dao.delete(obj);

     }

    public List<SysUsers> geSysUsersList(SysUsers sysUsers, Page page) {
        StringBuilder hql = new StringBuilder();
        hql.append("from SysUsers t ");
        List params = new ArrayList();
        hql.append(" where 1=1 ");

        if (sysUsers != null) {
            //Ar id = artMessage.getArtMessageType();
            //hql.append("and t.goodsType_id= ?");
            //params.add(artVideo.getGoodsType_id());
        }
        return dao.query(hql.toString(), page, params.toArray());
    }

     public SysUsers getSysUsersById(Long qid) throws Exception{
         Session session = dao.getHibernateSession();
         try{
            Query q = session.createQuery(" from SysUsers u where u.id = :id");
            q.setParameter("id", qid);
            SysUsers sysUsers = (SysUsers) q.uniqueResult();
            if (sysUsers == null) {
                throw new UsernameNotFoundException(qid+"不存在");
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
      * 判断该用户名是否新增过
      * @param name
      * @return
      * @throws Exception
      */
     public boolean getUserNameCount(String name) throws Exception{
    	 Session session = dao.getHibernateSession();
         try{
             
              Query q1 = session.createQuery("select count(*) from SysUsers r where r.userAccount=:name");
              q1.setParameter("name", name);
              List list= q1.list();
              int count = Integer.valueOf(list.get(0).toString());
              if(count>0){
                  return false;
              }else{
                  return true;
              }
          } catch (Exception ex){
              throw ex;
          } finally {
         	    session.clear();
                session.close();
    		}



     }
     
     /**
      * 验证用户新增 、 保存  信息
      * @param sysusers
      * @return
      * @throws Exception
      */
     public String checkUsername(String userAccount) throws Exception{

         boolean retCheck = true;

         //判断该用户名是否注册
         retCheck = getUserNameCount(userAccount.trim());
         if(retCheck == false){
             return "0002";
         }
         return "";
     }

    public List<SysRoles> getSysRolesList() throws Exception {
        StringBuilder hql = new StringBuilder();
        hql.append("from SysRoles t ");
        return dao.getListByHql(hql.toString());
    }

    public List<RoleListDto> getUserRolesList(Long id) throws Exception
    {
        List<RoleListDto> lst=new ArrayList<RoleListDto>();
        Session session = dao.getHibernateSession();
        try
        {


            Query q1 = session.createSQLQuery("select b.id,b.roleName,b.roleDesc from sys_users_roles  a ,sys_roles  b where a.ROLE_ID=b.id and a.USER_ID="+id+"");

            if(q1.list().size()>0)
            {
                Iterator it= q1.list().iterator();
                while (it.hasNext())
                {
                    Object[] ob=(Object[])it.next();
                    RoleListDto dto=new RoleListDto();
                    dto.setId((BigInteger)ob[0]);
                    dto.setRoleName((String)ob[1]);
                    dto.setRoleDesc((String)ob[2]);
                    lst.add(dto);
                }


            }
        }
        catch (Exception ex){
              throw ex;
          } finally {
         	    session.clear();
                session.close();
    		}

        return lst;
    }

    public List<RoleListDto> getUserRolesList2(Long id) throws Exception
    {
        Session session = dao.getHibernateSession();
        List<RoleListDto> lst=new ArrayList<RoleListDto>();
        try
        {


            Query q1 = session.createSQLQuery("SELECT c.id,c.roleName,c.roleDesc FROM sys_roles c WHERE c.id NOT IN(select b.id from sys_users_roles  a , sys_roles b where a.ROLE_ID=b.id and a.USER_ID="+id+")");

            if(q1.list().size()>0)
            {
                Iterator it= q1.list().iterator();
                while (it.hasNext())
                {
                    Object[] ob=(Object[])it.next();
                    RoleListDto dto=new RoleListDto();
                    dto.setId((BigInteger)ob[0]);
                    dto.setRoleName((String)ob[1]);
                    dto.setRoleDesc((String)ob[2]);
                    lst.add(dto);
                }


            }
        }
        catch (Exception ex){
              throw ex;
          } finally {
         	    session.clear();
                session.close();
    		}

        return lst;
    }

    public void deleteUserRole(final String[] ids) throws Exception {

        String hql = "delete SysUsersRoles where USER_ID in (:ids)";
        dao.updTobatch(ids,hql);
    }

    public void updpwd(Long id,String pwd) throws Exception {
        Session session = dao.getHibernateSession();
        try
        {


            Query q1 = session.createSQLQuery("UPDATE sys_users set userPassword='"+pwd+"' WHERE id="+id+" ");
            q1.executeUpdate();

        }
        catch (Exception ex){
              throw ex;
          } finally {
         	    session.clear();
                session.close();
    		}

    }

    public void deleteUser(final String[] ids) throws Exception {

           String sql = "delete SysUsersRoles where USER_ID in (:ids)";
           dao.updTobatch(ids,sql);
           String hql = "delete SysUsers where id in (:ids)";
           dao.updTobatch(ids,hql);
       }
    
    /**
     * 查询商品所有连锁公司
     * @return
     * @throws Exception
     */
     public List<Chaicompany> getChaicompanyData()throws Exception{
         try{
            StringBuilder hql = new StringBuilder();
            hql.append("from Chaicompany t where t.status='1'");
            return  dao.getListByHql(hql.toString());
         }catch (Exception ex){
             throw ex;
         }
     }
     
     public Chaicompany getChaicompanyById(Long qid) throws Exception {
         Session session = dao.getHibernateSession();
         try{
            Query q = session.createQuery(" from Chaicompany u where u.id = :id");
            q.setParameter("id", qid);
            Chaicompany chaicompany = (Chaicompany) q.uniqueResult();
            if (chaicompany == null) {
                throw new UsernameNotFoundException(qid+"不存在");
            }
            return chaicompany;
         }catch (Exception ex){
             throw ex;
         } finally {
        	    session.clear();
                session.close();
    		}

     }

}
