package com.arthouse.background.service;

import java.util.List;

import com.arthouse.common.domain.GroupItemsInfo;
import com.arthouse.common.domain.GroupOrder;
import com.arthouse.common.page.Page;

/**
 * 团购订单
 * 
 * @author wanggq
 *
 */
public interface OrderGroupService {
	
	/**
	 * 根据类型查询摄影作品
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public List<GroupOrder> getGroupOrderData(Page page,String name,String orderNo,String BeginTime,String  endTime)throws Exception;
	

	/**
	 * 查询团购主题信息
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public List<GroupItemsInfo> getGroupItemData(Page page)throws Exception;
	
	
	
	/**
	 * 根据id查询团购信息
	 * @param topic
	 * @return
	 * @throws Exception
	 */
	public GroupItemsInfo getGroupItem(Long id)
			throws Exception;
	
	
	public void save(Object obj);
	
	
	public void update(Object obj);
	
	
	public void delete(Object obj);

}
