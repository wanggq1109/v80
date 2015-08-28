package com.arthouse.background.service;

import java.util.List;

import com.arthouse.common.domain.Customer;
import com.arthouse.common.domain.CustomerPhotos;
import com.arthouse.common.page.Page;

public interface CustomerService {
	
	public void save(Object obj);
	
	public void delete(Object obj);
	
	public void update(Object obj);
	
	/**
	 * 获取客人对象
	 * 
	 * @param id
	 * @return
	 * 
	 * @throws Exception
	 */
	public Customer getCustomer(Long id) throws Exception;
	
	
	public CustomerPhotos getCustomerPhotos(Long id) throws Exception;
	
	
	
	public List<CustomerPhotos> getCustomerphotosDataById(Page page, Long id)
			throws Exception ;
	
	/**
	 * 分页查询
	 * 
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public List<Customer> getCustomerByPage(Page page)
			throws Exception;	
	
	
	public List<CustomerPhotos> getCustomerPhotosById(Long id)throws Exception;
	
	
	/**
	 * 设置客片组封面
	 * @param id
	 * @throws Exception
	 */
	public void updatefacePhotos(CustomerPhotos customerphotos)throws Exception;

}
