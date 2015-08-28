package com.arthouse.background.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.arthouse.background.service.CustomerService;
import com.arthouse.common.dao.ICommonDao;
import com.arthouse.common.domain.Customer;
import com.arthouse.common.domain.CustomerPhotos;
import com.arthouse.common.domain.PhtotoGraphyWroks;
import com.arthouse.common.page.Page;

public class CustomerServiceImpl implements CustomerService {
	
	private ICommonDao dao;

	public ICommonDao getDao() {
		return dao;
	}

	public void setDao(ICommonDao dao) {
		this.dao = dao;
	}
	
	
	/**
	 * 获取客人对象
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Customer getCustomer(Long id) throws Exception {

		String sql = " from Customer p where p.id ='" + id + "' ";
		Customer customer = (Customer) dao
				.getUniqueObjectByHql(sql);
		return customer;

	}
	
	public Customer getCustomerByMark() throws Exception {

		String sql = " from Customer p where p.mark = '1' ";
		Customer customer = (Customer) dao
				.getUniqueObjectByHql(sql);
		return customer;

	}
	
	
	
	public CustomerPhotos getCustomerPhotos(Long id) throws Exception {

		String sql = " from CustomerPhotos p where p.id ='" + id + "' ";
		CustomerPhotos customerphotos = (CustomerPhotos) dao
				.getUniqueObjectByHql(sql);
		return customerphotos;

	}
	
	
	
	/**
	 * 分页查询
	 * 
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public List<Customer> getCustomerByPage(Page page)
			throws Exception {
		List params = new ArrayList();
		StringBuilder hql = new StringBuilder();

		hql.append(" from Customer  p order by p.id desc ");

		return dao.query(hql.toString(), page, params.toArray());
	}
	
	
	public List<CustomerPhotos> getCustomerPhotosById(Long id)throws Exception{
		
		String hql=" from CustomerPhotos p where p.customer.id ='"+id+"' ";
		List<CustomerPhotos> customerphotos = dao.getListByHql(hql);
        return customerphotos;
	}
	
	
	public List<CustomerPhotos> getCustomerphotosDataById(Page page, Long id)
			throws Exception {
		List params = new ArrayList();
		StringBuilder hql = new StringBuilder();

		hql.append(" from CustomerPhotos p  ").append(" where 1=1");
		hql.append(" and p.customer.id= ? order by id desc");
		params.add(id);

		return dao.query(hql.toString(), page, params.toArray());
	}



	
	public void save(Object obj){
		
		try {
			dao.save(obj);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void update(Object obj){
		
		
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
	
	/**
	 * 设置客片组封面
	 * @param id
	 * @throws Exception
	 */
	public void updatefacePhotos(CustomerPhotos customerphotos)throws Exception{
		
		
		Customer customer = customerphotos.getCustomer();
		
		customer.setFace_url(customerphotos.getSl_url());
		
		dao.update(customer);
		
	}

}
