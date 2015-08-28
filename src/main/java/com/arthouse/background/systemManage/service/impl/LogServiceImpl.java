package com.arthouse.background.systemManage.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Query;
import org.hibernate.Session;

import com.arthouse.background.systemManage.service.LogService;
import com.arthouse.common.dao.ICommonDao;
import com.arthouse.common.domain.SystemLog;
import com.arthouse.common.page.Page;

public class LogServiceImpl implements LogService{
	private ICommonDao dao;

	public ICommonDao getDao() {
		return dao;
	}

	public void setDao(ICommonDao dao) {
		this.dao = dao;
	}
	
	//显示
	public List<SystemLog> getSystemLogList(HttpServletRequest request, Page page) {
		String username=request.getParameter("name");
		String start_time=request.getParameter("start_time");
		String end_time=request.getParameter("end_time");
		String module_name=request.getParameter("module_name");
        StringBuilder hql = new StringBuilder();
        hql.append("from SystemLog d ");
        List params = new ArrayList();
        hql.append(" where 1=1 ");
        if(module_name!=""&&module_name!=null)
        {
            hql.append(" and d.module_name like '%"+module_name+"%'");
        }
        if(username!=""&&username!=null)
        {
            hql.append(" and d.userName like '%"+username+"%'");
        }
        if(start_time!=""&&end_time!=""&&start_time!=null&&end_time!=null)
        {
            hql.append(" and d.date between '"+start_time+"' and '"+end_time+"' ");
        }
        hql.append(" order by  d.date desc");
        return dao.query(hql.toString(), page, params.toArray());
    }
	
	//清空
	public void logdel(HttpServletRequest request) throws Exception {   	
		String start_time=request.getParameter("start_time_del");
		String end_time=request.getParameter("end_time_del");
		String sql="DELETE FROM systemlog  WHERE date between '"+start_time+"' and '"+end_time+"' ";
		Session session = dao.getHibernateSession();
    	try{
    		if(start_time!=""&&end_time!=""&&start_time!=null&&end_time!=null)
    		{
    			Query q = session.createSQLQuery(sql);
                q.executeUpdate();
    		}
            

        } catch (Exception ex){
            throw ex;

        } finally {
       	    session.clear();
            session.close();
		}
    	
    }
}
