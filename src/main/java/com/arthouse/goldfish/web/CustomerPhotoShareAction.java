package com.arthouse.goldfish.web;

import org.apache.log4j.Logger;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.lang.StringUtils;

import com.arthouse.base.web.BaseAction;
import com.arthouse.common.domain.Customer;
import com.arthouse.common.domain.CustomerPhotos;
import com.arthouse.common.domain.PhototTopic;
import com.arthouse.common.domain.PhtotoGraphyWroks;
import com.arthouse.common.dto.CustomerDTO;
import com.arthouse.common.dto.CustomerPhotoDTO;
import com.arthouse.common.dto.PhotoTypeDTO;
import com.arthouse.common.page.DateJsonValueProcessor;
import com.arthouse.common.page.Page;
import com.arthouse.common.util.Constant;
import com.arthouse.goldfish.service.IndexService;
import com.opensymphony.xwork2.Action;

public class CustomerPhotoShareAction extends BaseAction {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(CustomerPhotoShareAction.class);
	
	private IndexService indexService;
	
	private Page page;

	public Integer pageIndex = 1;
	
	private String id;

	Integer pageCount = 0;
	
	List<Customer> pageTopicList = new ArrayList<Customer>();
	
	List<CustomerPhotos> pageList = new ArrayList<CustomerPhotos>();
	
	List<PhotoTypeDTO> hotspotsTagList = new ArrayList<PhotoTypeDTO>();
	
	List<CustomerPhotos> customerPhotosList = new ArrayList<CustomerPhotos>();
	
	List<PhtotoGraphyWroks> hotspotsList = new ArrayList<PhtotoGraphyWroks>();
	
	private PhototTopic topic;
	
	String psDate;
	
	private Customer customer;
	
	
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	/**
	 * 初始化客片列表
	 * @return
	 */
	public String customerPhotos_init(){
		
		
		
		return SUCCESS;
	}
	
	/**
	 * 初始化客片详情页
	 * @return
	 * @throws Exception 
	 * @throws NumberFormatException 
	 */
	public String showcase_content_init() throws NumberFormatException, Exception{
		
		String id = request.getParameter("id");
		request.setAttribute("id", id);
		customer = indexService.getCustomerInfo(Long.parseLong(id));
		Date date = customer.getShootTime();
		DateFormat df = DateFormat.getDateInstance(DateFormat.FULL,
				Locale.CHINA);

		psDate = df.format(date);
		
		customerPhotosList = indexService.getCustomerphotosById(customer);
		
		
		return SUCCESS;
	}
	
	/**
	 * 初始化热门景点列表
	 * @return
	 */
	public String hotspotsshow_init(){
		
		try {
			hotspotsTagList = indexService.getPhotoChildrenTypeByCode2(Constant.HOTSPOPTSTYPE);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	
	/**
	 * 热门景点详情页
	 * @return
	 * @throws Exception 
	 * @throws NumberFormatException 
	 */
	public String hotspot_content() throws NumberFormatException, Exception{
		
		String id = request.getParameter("id");
		request.setAttribute("id", id);
		topic = indexService.getTopic(Long.parseLong(id));
		Date date = topic.getShotTime();
		DateFormat df = DateFormat.getDateInstance(DateFormat.FULL,
				Locale.CHINA);

		psDate = df.format(date);
		
		hotspotsList = indexService.getphotosByTopic(topic);
		
		return SUCCESS;
	}
	

	
	

	/**
	 * 根据时间显示客片列表信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String ajaxCustomerPhotoShowbyMonth() throws Exception {
		String time = request.getParameter("ym");
		page = new Page(20, pageIndex, true);
		indexService.getCustomerInfoforPageByTime(page, time);
		response.setContentType("application/json; charset=utf-8"); // 输出文本为application
		response.setHeader("Cache-Control", "no-cache"); // 取消浏览器缓存
		// 必须加上,防止前端从JSON中取出的数据成乱码
		response.setCharacterEncoding("UTF-8");
		JsonConfig cfg = new JsonConfig();
		cfg.registerJsonValueProcessor(java.util.Date.class,
				new DateJsonValueProcessor("yyyy-MM-dd"));
		pageTopicList = page.getRecord();
		List<CustomerDTO> dataList = new ArrayList<CustomerDTO>();
		 SimpleDateFormat sdf = new SimpleDateFormat("dd-MMMM-yyyy",  
	                Locale.ENGLISH);  
		for (Customer customer : pageTopicList) {
			CustomerDTO dto = new CustomerDTO();
			dto.setId(customer.getId());
			Date shootTime = customer.getShootTime();
			String engstr = sdf.format(shootTime);
			int index = engstr.indexOf("-", 0);
			String str1= engstr.substring(0, index);
			dto.setcDate(str1);
			String temp = engstr.substring(index+1);
			int index2 = temp.indexOf("-", 0);
			String str2 = temp.substring(0, index2);
			dto.setcMonth(str2.substring(0, 3));
			dto.setCustomerName(customer.getCustomerName());
			dto.setRemark(customer.getRemark());
			
			List<CustomerPhotos> photoworksList = indexService
					.getCustomerphotosById(customer);
			dto.setPhotoSize(String.valueOf(photoworksList.size()));
			String face_url =customer.getFace_url();
			if(StringUtils.isNotEmpty(face_url)){
				dto.setUrl(face_url);
			}else{
				for (Iterator it = photoworksList.iterator(); it.hasNext();) {
					CustomerPhotos photo = (CustomerPhotos) it.next();
					String photourl = photo.getSl_url();
					if (StringUtils.isNotEmpty(photourl)) {
						dto.setUrl(photourl);
						break;
					}
				}
			}
		
			dataList.add(dto);
		}

		pageCount = page.getPageCount();
		PrintWriter out = response.getWriter();

		JSONArray rows = JSONArray.fromObject(dataList, cfg);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("pageCount", pageCount);
		jsonObject.put("rows", rows);
		out.print(jsonObject.toString());
		out.flush();
		out.close();

		return Action.SUCCESS;
	}
	
	/**
	 * 显示当前用户下的所有照片
	 * @return
	 * @throws NumberFormatException
	 * @throws Exception
	 */
	public String ajaxcustomerPhotosdata() throws NumberFormatException, Exception {

		page = new Page(25, pageIndex, true);
		indexService.getCustomerPhotosbyIdList(page, Long.parseLong(id));
		pageList = page.getRecord();
		response.setContentType("application/json; charset=utf-8"); // 输出文本为application
		response.setHeader("Cache-Control", "no-cache"); // 取消浏览器缓存
		// 必须加上,防止前端从JSON中取出的数据成乱码
		response.setCharacterEncoding("UTF-8");
		List<CustomerPhotoDTO> dataList = new ArrayList<CustomerPhotoDTO>();
		for (CustomerPhotos photo : pageList) {
			CustomerPhotoDTO dto = new CustomerPhotoDTO();
			dto.setId(photo.getId());
			dto.setSl_url(photo.getSl_url());
			dto.setUploadTime(photo.getUploadTime());
			dto.setUrl(photo.getUrl());
			dataList.add(dto);
		}
		pageCount = page.getPageCount();
		PrintWriter out = response.getWriter();

		JSONArray rows = JSONArray.fromObject(dataList);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("pageCount", pageCount);
		jsonObject.put("rows", rows);
		out.print(jsonObject.toString());
		out.flush();
		out.close();

		return Action.SUCCESS;
	}



	public IndexService getIndexService() {
		return indexService;
	}



	public void setIndexService(IndexService indexService) {
		this.indexService = indexService;
	}



	public Page getPage() {
		return page;
	}



	public void setPage(Page page) {
		this.page = page;
	}



	public Integer getPageIndex() {
		return pageIndex;
	}



	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}



	public Integer getPageCount() {
		return pageCount;
	}



	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}

	public List<Customer> getPageTopicList() {
		return pageTopicList;
	}

	public void setPageTopicList(List<Customer> pageTopicList) {
		this.pageTopicList = pageTopicList;
	}

	public List<CustomerPhotos> getPageList() {
		return pageList;
	}

	public void setPageList(List<CustomerPhotos> pageList) {
		this.pageList = pageList;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public List<PhotoTypeDTO> getHotspotsTagList() {
		return hotspotsTagList;
	}


	public void setHotspotsTagList(List<PhotoTypeDTO> hotspotsTabList) {
		this.hotspotsTagList = hotspotsTagList;
	}

	public PhototTopic getTopic() {
		return topic;
	}

	public void setTopic(PhototTopic topic) {
		this.topic = topic;
	}

	public String getPsDate() {
		return psDate;
	}

	public void setPsDate(String psDate) {
		this.psDate = psDate;
	}

	public List<CustomerPhotos> getCustomerPhotosList() {
		return customerPhotosList;
	}

	public void setCustomerPhotosList(List<CustomerPhotos> customerPhotosList) {
		this.customerPhotosList = customerPhotosList;
	}

	public List<PhtotoGraphyWroks> getHotspotsList() {
		return hotspotsList;
	}

	public void setHotspotsList(List<PhtotoGraphyWroks> hotspotsList) {
		this.hotspotsList = hotspotsList;
	}
	
	
	
}
