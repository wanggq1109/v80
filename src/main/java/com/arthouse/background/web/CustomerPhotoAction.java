package com.arthouse.background.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.lang.StringUtils;

import com.arthouse.background.service.CustomerService;
import com.arthouse.base.web.BaseAction;
import com.arthouse.common.domain.Customer;
import com.arthouse.common.domain.CustomerPhotos;
import com.arthouse.common.dto.CustomerDTO;
import com.arthouse.common.dto.CustomerPhotoDTO;
import com.arthouse.common.page.DateJsonValueProcessor;
import com.arthouse.common.page.Page;

/**
 * 
 * 客片分享
 * 
 * @author wgq
 *
 */
public class CustomerPhotoAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private CustomerService customerService;

	public CustomerService getCustomerService() {
		return customerService;
	}

	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}

	/**
	 * 页面初始化
	 * 
	 * @return
	 */
	public String customer_init() {

		return SUCCESS;
	}
	
	public String  customerphotos_init(){
		
		String id = request.getParameter("id");
		request.setAttribute("id", id);
		
		return SUCCESS;
	}

	/**
	 * 新增顾客信息
	 * 
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	public String customerInfo_add() throws IOException, ParseException {

		String errorMessage = "";
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

		String customerName = request.getParameter("customerName");
		String shootTime = request.getParameter("shootTime");
		String remark = request.getParameter("remark");
		
		Customer customer = new Customer();
		customer.setCustomerName(customerName);
		customer.setShootTime(sdf.parse(shootTime));
		customer.setRemark(remark);
		customer.setCreateTime(new Date());

		try {
			customerService.save(customer);

			errorMessage = "1";
		} catch (Exception e) {
			// TODO: handle exception
			errorMessage = e.getMessage();
		}

		PrintWriter out = response.getWriter();
		out.write(errorMessage);
		out.flush();
		out.close();
		return null;

	}
	
	/**
	 * 
	 * @return
	 * @throws Exception 
	 * @throws NumberFormatException 
	 */
	public String edit_facephots() throws NumberFormatException, Exception{
		
		String id = request.getParameter("id");
		String errorMessage = "";
		
		CustomerPhotos customerphotos =  customerService.getCustomerPhotos(Long.parseLong(id));
		
		try {
			customerService.updatefacePhotos(customerphotos);
			errorMessage = "001";
		} catch (Exception e) {
			// TODO: handle exception
			errorMessage = e.getMessage();
		}
		PrintWriter out = response.getWriter();
		out.write(errorMessage);
		out.flush();
		out.close();
		return null;
	}
	
	
	
	
	
	
	/**
	 * 获取上传作品信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getPhotoWorksDataList() throws Exception {

		int currentPage = Integer.parseInt(request.getParameter("page"));// 当前页
		int currentRows = Integer.parseInt(request.getParameter("rows"));// 每页显示记录数
		Page page = new Page(currentRows, currentPage, true);

		String id = request.getParameter("id");

		
		customerService.getCustomerphotosDataById(page, Long.parseLong(id));

		response.setContentType("application/json; charset=utf-8"); // 输出文本为application
		response.setHeader("Cache-Control", "no-cache"); // 取消浏览器缓存

		// 必须加上,防止前端从JSON中取出的数据成乱码
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		JsonConfig cfg = new JsonConfig();
		cfg.registerJsonValueProcessor(java.util.Date.class,
				new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
		List<CustomerPhotoDTO> photosList = new ArrayList<CustomerPhotoDTO>();
		List<CustomerPhotos> photodtoList = page.getRecord();
		for(CustomerPhotos photos : photodtoList){
			CustomerPhotoDTO dto = new CustomerPhotoDTO();
			dto.setId(photos.getId());
			dto.setSl_url(photos.getSl_url());
			dto.setUploadTime(photos.getUploadTime());
			photosList.add(dto);
		}

		JSONArray rows = JSONArray.fromObject(photosList, cfg);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("total", page.getTotalRecordCount());
		jsonObject.put("rows", rows);
		out.print(jsonObject.toString());

		out.flush();
		out.close();
		return null;
	}

	public String getCustomerInfo() throws Exception {

		int currentPage = Integer.parseInt(request.getParameter("page"));// 当前页
		int currentRows = Integer.parseInt(request.getParameter("rows"));// 每页显示记录数
		Page page = new Page(10, currentPage, true);

		customerService.getCustomerByPage(page);

		response.setContentType("application/json; charset=utf-8"); // 输出文本为application
		response.setHeader("Cache-Control", "no-cache"); // 取消浏览器缓存

		// 必须加上,防止前端从JSON中取出的数据成乱码
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		JsonConfig cfg = new JsonConfig();
		cfg.registerJsonValueProcessor(java.util.Date.class,
				new DateJsonValueProcessor("yyyy-MM-dd"));

		List<Customer> CustomerList = page.getRecord();
		List<CustomerDTO> ptList = new ArrayList<CustomerDTO>();

		for (Iterator it = CustomerList.iterator(); it.hasNext();) {
			Customer customer = (Customer) it.next();
			CustomerDTO dto = new CustomerDTO();
			dto.setCreateTime(customer.getCreateTime());
			dto.setCustomerName(customer.getCustomerName());
			dto.setId(customer.getId());
			dto.setRemark(customer.getRemark());
			dto.setShootTime(customer.getShootTime());
			ptList.add(dto);
		}

		JSONArray rows = JSONArray.fromObject(ptList, cfg);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("total", page.getTotalRecordCount());
		jsonObject.put("rows", rows);
		out.print(jsonObject.toString());
		System.out.println(jsonObject.toString());
		out.flush();
		out.close();
		return null;
	}
	
	/**
	 * 编辑作品主题信息
	 * 
	 * @return
	 * @throws NumberFormatException
	 * @throws Exception
	 */
	public String customerInfo_edit() throws NumberFormatException,
			Exception {

		String errorMessage = "";
		String id = request.getParameter("id");
		String customerName = request.getParameter("customerName");
		String shootTime = request.getParameter("shootTime");
		String remark = request.getParameter("remark");

		try {
			if (StringUtils.isNotEmpty(id)) {
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

				Customer customer =	customerService.getCustomer(Long.parseLong(id));
				customer.setCustomerName(customerName);
				customer.setShootTime(sdf.parse(shootTime));
				customer.setCreateTime(new Date());
				customer.setRemark(remark);
				
				customerService.update(customer);
			}

			errorMessage = "1";
		} catch (Exception e) {
			// TODO: handle exception
			errorMessage = e.getMessage();
		}

		PrintWriter out = response.getWriter();
		out.write(errorMessage);
		out.flush();
		out.close();
		return null;

	}
	
	
	/**
	 * 删除顾客信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String customer_delete() throws Exception {

		String id = request.getParameter("id");
		String[] ids = id.split(",");
		String errorMessage = "";

		try {

			for (int i = 0; i < ids.length; i++) {
				Customer customer = customerService.getCustomer(Long
						.parseLong(ids[i]));
				int photosize =	customerService.getCustomerPhotosById(customer.getId()).size();
				if (photosize > 0) {
					errorMessage = "5555";
				} else {
					customerService.delete(customer);
					errorMessage = "0000";
				}

			}

		} catch (Exception e) {

			errorMessage = e.getMessage();
		}
		PrintWriter out = response.getWriter();
		out.write(errorMessage);
		out.flush();
		out.close();
		return null;
	}
	
	
	public String delete_phots() throws NumberFormatException, Exception{
		
		String id = request.getParameter("id");
		String[] ids = id.split(",");
		String errorMessage = "";
		for (int i = 0; i < ids.length; i++) {
			CustomerPhotos customerphotos = customerService.getCustomerPhotos(Long
					.parseLong(ids[i]));
			
			customerService.delete(customerphotos);
			errorMessage = "0000";
		}
		
		PrintWriter out = response.getWriter();
		out.write(errorMessage);
		out.flush();
		out.close();
		return null;
		
	}

}
