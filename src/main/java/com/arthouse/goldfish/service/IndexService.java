package com.arthouse.goldfish.service;

import java.util.List;

import com.arthouse.common.domain.Customer;
import com.arthouse.common.domain.CustomerPhotos;
import com.arthouse.common.domain.EmployWorksTopic;
import com.arthouse.common.domain.Employee;
import com.arthouse.common.domain.GroupItemsInfo;
import com.arthouse.common.domain.News;
import com.arthouse.common.domain.PhototTopic;
import com.arthouse.common.domain.PhtotoGraphyWroks;
import com.arthouse.common.domain.StudioInfo;
import com.arthouse.common.domain.VideoInfo;
import com.arthouse.common.dto.CustomerDTO;
import com.arthouse.common.dto.PhotoTopicDTO;
import com.arthouse.common.dto.PhotoTypeDTO;
import com.arthouse.common.page.Page;

public interface IndexService {

	public List<PhtotoGraphyWroks> getAllslides() throws Exception;

	public List<PhtotoGraphyWroks> getPhotoWorksByCode(String code)
			throws Exception;

	public List<PhotoTopicDTO> getnewTopicandPhotos(String photoTypeCode)
			throws Exception;

	public List<PhotoTopicDTO> getTopicByPhotosCode(String code)
			throws Exception;
	
	public List<PhotoTopicDTO> getTopicByPhotosCode2(String code)
			throws Exception;
	
	
	public List<PhotoTopicDTO> getTopicByPhotosCode3(String code)
			throws Exception;
	
	public List<PhotoTopicDTO> getTopicByPhotosCode4(String code)
			throws Exception;

	public List<PhtotoGraphyWroks> getPhotoWorksforWeekRecommend()
			throws Exception;
	
	public List<PhtotoGraphyWroks> getPhotoWorksforWeekRecommend2()
			throws Exception;

	public List<PhtotoGraphyWroks> getTopicPhotobyIdList(Page page, Long id)
			throws Exception;

	public List<PhototTopic> getnewTopicandPhotosforPage(Page page,
			String photoTypeCode) throws Exception;

	public List<PhotoTopicDTO> getTopicByPhotosCodePage(Page page, String code)
			throws Exception;

	public List<PhtotoGraphyWroks> getphotosByTopic(PhototTopic topic)
			throws Exception;

	public PhototTopic getTopic(Long id) throws Exception;
	
	
	public List<PhtotoGraphyWroks> getTopicPhotobyIdListforWeek(Page page, Long id)
			throws Exception;
	
	public StudioInfo getStudioObject()throws Exception;
	
	public Employee getEmployeeInfo(Long id) throws Exception;
	
	public List<EmployWorksTopic> getphotosByEmployeePhotoTopic(Long  id)
			throws Exception;
	
	public List<VideoInfo> getFilmListByType2()
			throws Exception;
	
	
	public List<CustomerPhotos> getCustomerphotosById(Customer customer)
			throws Exception;
	
	
	public List<CustomerPhotos> getCustomerPhotosbyIdList(Page page, Long id)
			throws Exception;
	
	public Customer getCustomerInfo(Long id) throws Exception;
	
	
	/**
	 * 根据时间获取顾客拍摄信息
	 * 
	 * @param page
	 * @param photoTypeCode
	 * @return
	 * @throws Exception
	 */
	public List<Customer> getCustomerInfoforPageByTime(Page page,
			String time) throws Exception ;
	
	/**
	 * 根据父节点获取二级子节点
	 * 
	 * @param code
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<PhotoTypeDTO> getPhotoChildrenTypeByCode2(String code)
			throws Exception;
	
	/**
	 * 根据父节点获取二级子节点
	 * 
	 * @param code
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<PhotoTypeDTO> getPhotoChildrenTypeByCode(String code)
			throws Exception;
	
	/**
	 * 首页显示客片20条最新记录
	 * @return
	 * @throws Exception
	 */
	public List<CustomerDTO> getCustomerInfoByNew() throws Exception;
	
	/**
	 * 查询微电影
	 * @param page
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public List<VideoInfo> getMicrofilmListPage(Page page,String type)
			throws Exception;
	
	
	/**
	 * 根据类型获取微电影信息
	 */
	public List<VideoInfo> getFilmListByType(String type)
			throws Exception;
	
	/**
	 * 获取微电影对象
	 * @return
	 * @throws Exception
	 */
	public VideoInfo getVideoInfo() throws Exception;
	
	
	/**
	 * 获取最新的微电影
	 */
	public List<VideoInfo> getFilmListByType()
			throws Exception;
	
	
	/**
	 * 获取最新团购
	 */
	public List<GroupItemsInfo> getTuanListByType()
			throws Exception;
	
	
	
	/**
	 * 根据类型获取微电影对象
	 * @return
	 * @throws Exception
	 */
	public VideoInfo getVideoInfo(String type) throws Exception;
	
	

	/**
	 * 获取首页新闻
	 */
	public List<News> getallnews()
			throws Exception;
	
	
	public News getNewsInfoObject(Long id) throws Exception;
	
	
	/**
	 * 查询分页新闻列表
	 * @param page
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public List<News> getNewsInfoListPage(Page page)
			throws Exception;
	
	

	/**
	 * 查询分页团购列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<GroupItemsInfo> getGroupitemInfoListPage(Page page)
			throws Exception;
	
	
	/**
	 * 获取团购对象
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public GroupItemsInfo getGroupItemObject(Long id) throws Exception;
	
	/**
	 * 根据类型获取作品主题或者服装主题
	 * @param page
	 * @param photoTypeCode
	 * @return
	 * @throws Exception
	 */
	public List<PhototTopic> getTopicandPhotosforPageByCode(Page page,
			String photoTypeCode) throws Exception;
	
	
}
