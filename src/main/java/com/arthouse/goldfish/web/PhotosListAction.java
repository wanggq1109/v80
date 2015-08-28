package com.arthouse.goldfish.web;

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
import com.arthouse.common.domain.PhototTopic;
import com.arthouse.common.domain.PhtotoGraphyWroks;
import com.arthouse.common.dto.PhotoTopicDTO;
import com.arthouse.common.dto.PhotoTypeDTO;
import com.arthouse.common.dto.PhotoWorksDTO;
import com.arthouse.common.page.DateJsonValueProcessor;
import com.arthouse.common.page.Page;
import com.arthouse.common.util.Constant;
import com.arthouse.goldfish.service.IndexService;
import com.opensymphony.xwork2.Action;

public class PhotosListAction extends BaseAction {

	private Page page;

	public Integer pageIndex = 1;

	private String id;

	private IndexService indexService;

	Integer pageCountList = 0;

	Integer pageCount = 0;

	List<PhtotoGraphyWroks> slideList;

	List<PhtotoGraphyWroks> pageList = new ArrayList<PhtotoGraphyWroks>();

	List<PhototTopic> pageTopicList = new ArrayList<PhototTopic>();

	private PhototTopic topic;
	
	List<PhotoTopicDTO> dressListAllTopic = new ArrayList<PhotoTopicDTO>();
	
	List<PhtotoGraphyWroks> dressListInfo = new ArrayList<PhtotoGraphyWroks>();
	
	List<PhtotoGraphyWroks> photographyList = new ArrayList<PhtotoGraphyWroks>();
	
	// 服装赏析标签
	List<PhotoTypeDTO> dressTypeTagList = new ArrayList<PhotoTypeDTO>();

	String psDate;
	
	/**
	 * 礼服赏析详情页
	 * @return
	 * @throws Exception
	 */
	public String dressPhotsSecnd_init() throws Exception {
		
		request.setAttribute("code", Constant.DRESSSHARE);
		String id = request.getParameter("id");
		
		topic = indexService.getTopic(Long.parseLong(id));
		
		dressListInfo = indexService.getphotosByTopic(topic);
		

		return SUCCESS;
	}

	public String photoListbyTopicId() throws Exception {

		topic = indexService.getTopic(Long.parseLong(id));
		Date date = topic.getShotTime();
		DateFormat df = DateFormat.getDateInstance(DateFormat.FULL,
				Locale.CHINA);

		psDate = df.format(date);
		
		photographyList = indexService.getphotosByTopic(topic);
		
		
		return SUCCESS;
	}
	
	public String dressshowmenu() throws Exception{
		request.setAttribute("code", Constant.DRESSSHARE);
		Page page = new Page(19, pageIndex, true);
		indexService.getnewTopicandPhotosforPage(page,  Constant.DRESSSHARE);;
		int count = page.getTotalRecordCount();
		request.setAttribute("count", count);
		dressTypeTagList = indexService.getPhotoChildrenTypeByCode(Constant.DRESSSHARE);

		return SUCCESS;
	}

	public String photoListbyTopicIdweek() throws Exception {

		topic = indexService.getTopic(Long.parseLong(id));
		Date date = topic.getShotTime();
		DateFormat df = DateFormat.getDateInstance(DateFormat.FULL,
				Locale.CHINA);

		psDate = df.format(date);
		
		photographyList = indexService.getphotosByTopic(topic);

		return SUCCESS;
	}

	public String ajaxPhotosdata() throws NumberFormatException, Exception {

		page = new Page(20, pageIndex, true);
		indexService.getTopicPhotobyIdList(page, Long.parseLong(id));
		pageList = page.getRecord();
		response.setContentType("application/json; charset=utf-8"); // 输出文本为application
		response.setHeader("Cache-Control", "no-cache"); // 取消浏览器缓存
		// 必须加上,防止前端从JSON中取出的数据成乱码
		response.setCharacterEncoding("UTF-8");
		List<PhotoWorksDTO> dataList = new ArrayList<PhotoWorksDTO>();
		for (PhtotoGraphyWroks photo : pageList) {
			PhotoWorksDTO dto = new PhotoWorksDTO();
			dto.setSuolueurl(photo.getBreviaryUrl());
			dto.setPhotoUrl(photo.getPhotoUrl());
			dto.setPhototitle(photo.getPhototdesc().getTitle());
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

	public String ajaxPhotosdataforweek() throws NumberFormatException,
			Exception {

		page = new Page(16, pageIndex, true);
		indexService.getTopicPhotobyIdListforWeek(page, Long.parseLong(id));
		pageList = page.getRecord();
		response.setContentType("application/json; charset=utf-8"); // 输出文本为application
		response.setHeader("Cache-Control", "no-cache"); // 取消浏览器缓存
		// 必须加上,防止前端从JSON中取出的数据成乱码
		response.setCharacterEncoding("UTF-8");
		List<PhotoWorksDTO> dataList = new ArrayList<PhotoWorksDTO>();
		for (PhtotoGraphyWroks photo : pageList) {
			PhotoWorksDTO dto = new PhotoWorksDTO();
			dto.setSuolueurl(photo.getBreviaryUrl());
			dto.setPhotoUrl(photo.getPhotoUrl());
			dto.setPhototitle(photo.getPhototdesc().getTitle());
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

	/**
	 * 显示摄影作品所有主题
	 * 
	 * @return
	 * @throws Exception
	 */
	public String ajaxPhotoTopicAllShow() throws Exception {
		String code = request.getParameter("code");
		page = new Page(25, pageIndex, true);
		indexService.getnewTopicandPhotosforPage(page, code);
		response.setContentType("application/json; charset=utf-8"); // 输出文本为application
		response.setHeader("Cache-Control", "no-cache"); // 取消浏览器缓存
		// 必须加上,防止前端从JSON中取出的数据成乱码
		response.setCharacterEncoding("UTF-8");
		JsonConfig cfg = new JsonConfig();
		cfg.registerJsonValueProcessor(java.util.Date.class,
				new DateJsonValueProcessor("yyyy-MM-dd"));
		pageTopicList = page.getRecord();
		List<PhotoTopicDTO> dataList = new ArrayList<PhotoTopicDTO>();
		for (PhototTopic topic : pageTopicList) {
			PhotoTopicDTO dto = new PhotoTopicDTO();
			dto.setId(topic.getId());
			dto.setName(topic.getTitle());
			dto.setPhotTopicDesc(topic.getPhotTopicDesc());
			dto.setShotTime(topic.getShotTime());
			dto.setStylists(topic.getStylists());
			dto.setCameraman(topic.getCameraman());
			dto.setShotAddress(topic.getShotAddress());
			List<PhtotoGraphyWroks> photoworksList = indexService
					.getphotosByTopic(topic);
			dto.setPhotosize(String.valueOf(photoworksList.size()));
			String faceurl = topic.getFaceurl();
			if(StringUtils.isNotBlank(faceurl)){
				dto.setUrl(faceurl);
			}else{
				for (Iterator it = photoworksList.iterator(); it.hasNext();) {
					PhtotoGraphyWroks photo = (PhtotoGraphyWroks) it.next();
					String photourl = photo.getBreviaryUrl();
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
	 * 根据类型获取作品主题或者服装主题
	 * 
	 * @return
	 * @throws Exception
	 */
	public String ajaxPhotoTopicShowbyCode() throws Exception {
		String code = request.getParameter("code");
		page = new Page(25, pageIndex, true);
		indexService.getTopicandPhotosforPageByCode(page, code);
		response.setContentType("application/json; charset=utf-8"); // 输出文本为application
		response.setHeader("Cache-Control", "no-cache"); // 取消浏览器缓存
		// 必须加上,防止前端从JSON中取出的数据成乱码
		response.setCharacterEncoding("UTF-8");
		JsonConfig cfg = new JsonConfig();
		cfg.registerJsonValueProcessor(java.util.Date.class,
				new DateJsonValueProcessor("yyyy-MM-dd"));
		pageTopicList = page.getRecord();
		List<PhotoTopicDTO> dataList = new ArrayList<PhotoTopicDTO>();
		for (PhototTopic topic : pageTopicList) {
			PhotoTopicDTO dto = new PhotoTopicDTO();
			dto.setId(topic.getId());
			dto.setName(topic.getTitle());
			dto.setPhotTopicDesc(topic.getPhotTopicDesc());
			dto.setShotTime(topic.getShotTime());
			dto.setStylists(topic.getStylists());
			dto.setCameraman(topic.getCameraman());
			dto.setShotAddress(topic.getShotAddress());
			List<PhtotoGraphyWroks> photoworksList = indexService
					.getphotosByTopic(topic);
			dto.setPhotosize(String.valueOf(photoworksList.size()));
			String faceurl = topic.getFaceurl();
			if(StringUtils.isNotBlank(faceurl)){
				dto.setUrl(faceurl);
			}else{
				for (Iterator it = photoworksList.iterator(); it.hasNext();) {
					PhtotoGraphyWroks photo = (PhtotoGraphyWroks) it.next();
					String photourl = photo.getBreviaryUrl();
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

	public IndexService getIndexService() {
		return indexService;
	}

	public void setIndexService(IndexService indexService) {
		this.indexService = indexService;
	}

	public List<PhtotoGraphyWroks> getSlideList() {
		return slideList;
	}

	public void setSlideList(List<PhtotoGraphyWroks> slideList) {
		this.slideList = slideList;
	}

	public Integer getPageCountList() {
		return pageCountList;
	}

	public void setPageCountList(Integer pageCountList) {
		this.pageCountList = pageCountList;
	}

	public Integer getPageCount() {
		return pageCount;
	}

	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public List<PhotoTopicDTO> getDressListAllTopic() {
		return dressListAllTopic;
	}

	public void setDressListAllTopic(List<PhotoTopicDTO> dressListAllTopic) {
		this.dressListAllTopic = dressListAllTopic;
	}

	public List<PhtotoGraphyWroks> getDressListInfo() {
		return dressListInfo;
	}

	public void setDressListInfo(List<PhtotoGraphyWroks> dressListInfo) {
		this.dressListInfo = dressListInfo;
	}

	public List<PhtotoGraphyWroks> getPhotographyList() {
		return photographyList;
	}

	public void setPhotographyList(List<PhtotoGraphyWroks> photographyList) {
		this.photographyList = photographyList;
	}

	public List<PhotoTypeDTO> getDressTypeTagList() {
		return dressTypeTagList;
	}

	public void setDressTypeTagList(List<PhotoTypeDTO> dressTypeTagList) {
		this.dressTypeTagList = dressTypeTagList;
	}
	
	
	

	
}
