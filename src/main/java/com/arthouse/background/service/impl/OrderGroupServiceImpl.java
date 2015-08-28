package com.arthouse.background.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.arthouse.background.service.OrderGroupService;
import com.arthouse.common.dao.ICommonDao;
import com.arthouse.common.domain.GroupItemsInfo;
import com.arthouse.common.domain.GroupOrder;
import com.arthouse.common.page.Page;

/**
 * 团购订单
 * @author wanggq
 *
 */
public class OrderGroupServiceImpl implements OrderGroupService {
	
	
	private ICommonDao dao;

	public ICommonDao getDao() {
		return dao;
	}

	public void setDao(ICommonDao dao) {
		this.dao = dao;
	}
	
	
	/**
	 * 根据类型查询摄影作品
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public List<GroupOrder> getGroupOrderData(Page page,String name,String orderNo,String BeginTime,String  endTime)throws Exception{
		 List params = new ArrayList();
		 StringBuilder hql= new StringBuilder();
		 hql.append(" from GroupOrder  o   ").append(" where 1=1");
		 if(StringUtils.isNotEmpty(name)){
	       	 hql.append(" and o.name= ?");
	         params.add(name);
	        
		 }
		 if(StringUtils.isNotEmpty(orderNo)){
	       	 hql.append(" and o.orderNo= ?");
	         params.add(orderNo);
	        
		 }
		  
		 if(StringUtils.isNotEmpty(BeginTime)&&StringUtils.isNotEmpty(endTime)){
			 
			 hql.append(" and o.orderTime >='"+BeginTime+"' and o.orderTime<='"+endTime+"'");
		 }
		
	   return dao.query(hql.toString(), page, params.toArray());
	}
	
	
	/**
	 * 查询团购主题信息
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public List<GroupItemsInfo> getGroupItemData(Page page)throws Exception{
		
		 List params = new ArrayList();
		 StringBuilder hql= new StringBuilder();
		 hql.append(" from GroupItemsInfo  t  order by t.id desc  ");
		  
		
	   return dao.query(hql.toString(), page, params.toArray());
		
	}
	
	/**
	 * 根据id查询团购信息
	 * @param topic
	 * @return
	 * @throws Exception
	 */
	public GroupItemsInfo getGroupItem(Long id)
			throws Exception {

		String hql = " from GroupItemsInfo p where p.id = '"
				+ id + "'  order by p.id desc";
		GroupItemsInfo groupItem = (GroupItemsInfo)dao.getUniqueObjectByHql(hql);
		return groupItem;

	}
	
	
	public void save(Object obj) {
		try {
			dao.save(obj);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void update(Object obj) {
		try {
			dao.update(obj);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void delete(Object obj) {

		try {
			dao.delete(obj);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


}
