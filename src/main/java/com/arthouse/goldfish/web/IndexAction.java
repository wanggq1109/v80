package com.arthouse.goldfish.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.arthouse.background.service.PhotoTypeService;
import com.arthouse.base.web.BaseAction;
import com.arthouse.common.domain.GroupItemsInfo;
import com.arthouse.common.domain.News;
import com.arthouse.common.domain.PhotoType;
import com.arthouse.common.domain.PhtotoGraphyWroks;
import com.arthouse.common.domain.VideoInfo;
import com.arthouse.common.dto.CustomerDTO;
import com.arthouse.common.dto.GroupItemDTO;
import com.arthouse.common.dto.NewsDTO;
import com.arthouse.common.dto.PhotoTopicDTO;
import com.arthouse.common.dto.PhotoTypeDTO;
import com.arthouse.common.page.Page;
import com.arthouse.common.util.Constant;
import com.arthouse.goldfish.service.IndexService;

public class IndexAction extends BaseAction {

	/**
	 * @author Jerry 首页
	 */
	private static final long serialVersionUID = 1L;

	private IndexService indexService;

	private PhotoTypeService photoTyperService;

	private PhtotoGraphyWroks weekrecommend;

	private PhtotoGraphyWroks weekrecommendsub;

	// 最新
	private PhotoTopicDTO hotdressphotoTopic;

	// 传统
	private PhotoTopicDTO hotdressphotoTopicct;

	// 现代
	private PhotoTopicDTO hotdressphotoTopicxd;

	private VideoInfo newfilmInfo;

	private VideoInfo microfilmInfo;

	private VideoInfo huaxufilmInfo;

	List<VideoInfo> videoListnew = new ArrayList<VideoInfo>();

	List<GroupItemsInfo> groupListnew = new ArrayList<GroupItemsInfo>();

	List<VideoInfo> videoListMicroFilm = new ArrayList<VideoInfo>();

	List<VideoInfo> videoListHuaXu = new ArrayList<VideoInfo>();

	List<PhotoTopicDTO> photoListzuixin = new ArrayList<PhotoTopicDTO>();

	List<PhotoTopicDTO> photoDressListzuixin = new ArrayList<PhotoTopicDTO>();

	List<PhotoTopicDTO> photoDressListzuixin2 = new ArrayList<PhotoTopicDTO>();

	List<PhotoTopicDTO> photoListwaijing = new ArrayList<PhotoTopicDTO>();
	
	List<PhotoTopicDTO> hotspots1 = new ArrayList<PhotoTopicDTO>();
	List<PhotoTopicDTO> hotspots2 = new ArrayList<PhotoTopicDTO>();
	List<PhotoTopicDTO> hotspots3 = new ArrayList<PhotoTopicDTO>();
	List<PhotoTopicDTO> hotspots4 = new ArrayList<PhotoTopicDTO>();

	List<PhotoTopicDTO> photoListneijing = new ArrayList<PhotoTopicDTO>();

	List<PhotoTopicDTO> photoListgudian = new ArrayList<PhotoTopicDTO>();

	List<PhotoTopicDTO> photoListxiandai = new ArrayList<PhotoTopicDTO>();

	List<PhotoTopicDTO> photoListdressxiandai = new ArrayList<PhotoTopicDTO>();

	List<PhotoTopicDTO> photoListdressxiandai2 = new ArrayList<PhotoTopicDTO>();

	List<PhotoTopicDTO> photoListdresschuantong = new ArrayList<PhotoTopicDTO>();
	
	List<PhotoTopicDTO> photoListdresshanxi = new ArrayList<PhotoTopicDTO>();

	List<PhtotoGraphyWroks> photoListweekRecommend = new ArrayList<PhtotoGraphyWroks>();

	List<PhtotoGraphyWroks> photoListweekRecommendsub = new ArrayList<PhtotoGraphyWroks>();

	List<VideoInfo> videoListnewsub = new ArrayList<VideoInfo>();

	List<GroupItemDTO> groupList = new ArrayList<GroupItemDTO>();

	// 摄影作品标签类型
	List<PhotoTypeDTO> photoworksTypeTagList = new ArrayList<PhotoTypeDTO>();

	// 服装赏析
	List<PhotoTypeDTO> dressTypeTagList = new ArrayList<PhotoTypeDTO>();

	List<NewsDTO> newsList = new ArrayList<NewsDTO>();

	List<NewsDTO> newsList2 = new ArrayList<NewsDTO>();

	List<CustomerDTO> customerphotosList = new ArrayList<CustomerDTO>();
	
	List<PhotoTypeDTO> hotspotsType = new ArrayList<PhotoTypeDTO>();

	/* 首页 */
	public String index_init() throws Exception {

		/**
		 * ################################作品赏析################################
		 */
		// 摄影作品最新主题
		photoListzuixin = indexService
				.getnewTopicandPhotos(Constant.PHOTOWORKS);
		// 作品赏析标签
		photoworksTypeTagList = indexService.getPhotoChildrenTypeByCode(Constant.PHOTOWORKS);
		
		//服装标签
		dressTypeTagList = indexService.getPhotoChildrenTypeByCode(Constant.DRESSSHARE);
		
				
		hotspotsType = indexService.getPhotoChildrenTypeByCode(Constant.HOTSPOPTSTYPE);
		
		if(hotspotsType.size()>0){
			
		String	jd1code = hotspotsType.get(0).getCode();
		hotspots1 = indexService.getTopicByPhotosCode(jd1code);
		
		String	jd2code = hotspotsType.get(1).getCode();
		hotspots2 = indexService.getTopicByPhotosCode(jd2code);
		
		
		String	jd3code = hotspotsType.get(2).getCode();
		hotspots3 = indexService.getTopicByPhotosCode(jd3code);
		
		
		String	jd4code = hotspotsType.get(3).getCode();
		hotspots4 = indexService.getTopicByPhotosCode(jd4code);
		
		}
		
		if (photoworksTypeTagList.size() > 0) {

			// 外景
			String waijing = photoworksTypeTagList.get(0).getCode();
			photoListwaijing = indexService.getTopicByPhotosCode(waijing);
			// 内景
			String neijing = photoworksTypeTagList.get(1).getCode();
			photoListneijing = indexService.getTopicByPhotosCode(neijing);

			// 古典
			String gudian = photoworksTypeTagList.get(2).getCode();

			photoListgudian = indexService.getTopicByPhotosCode(gudian);

			// 现代
			String xiandai = photoworksTypeTagList.get(3).getCode();

			photoListxiandai = indexService.getTopicByPhotosCode(xiandai);

			photoListweekRecommend = indexService
					.getPhotoWorksforWeekRecommend();
			if (photoListweekRecommend.size() > 0) {

				weekrecommend = photoListweekRecommend.get(0);
				photoListweekRecommend.remove(0);
			}

			/**
			 * ################################服装赏析#############################
			 * ###
			 */

			

			// 中国风
			String chuantong = dressTypeTagList.get(0).getCode();
			photoListdresschuantong = indexService
					.getTopicByPhotosCode(chuantong);
		

			

			// 欧美
			String xd = dressTypeTagList.get(1).getCode();
			photoListdressxiandai = indexService.getTopicByPhotosCode(xd);

			

			

			// 韩系
			String other = dressTypeTagList.get(2).getCode();
			photoListdresshanxi = indexService.getTopicByPhotosCode(other);

			

		
			/**
			 * ################################团购###############################
			 * #
			 */
			// 最新团购
			groupListnew = indexService.getTuanListByType();

			for (GroupItemsInfo groupitems : groupListnew) {

				GroupItemDTO dto = new GroupItemDTO();
				dto.setId(groupitems.getId());
				String title = groupitems.getTitle();
				if (title.length() >6) {

					title = title.substring(0, 6) + "...";
				}
				dto.setTitle(title);
				dto.setOld_price(groupitems.getOld_price());
				dto.setPrice(groupitems.getPrice());
				dto.setUrl(groupitems.getUrl());
				String descr = groupitems.getDescr();
				if (descr.length() >50) {

					descr = descr.substring(0, 50) + "...";
				}
				dto.setDescr(descr);
				dto.setContent(groupitems.getContent());

				groupList.add(dto);
			}

			/**
			 * ################################微电影##############################
			 * ##
			 */

			// 视频集合
			// 微电影类
			videoListMicroFilm = indexService.getFilmListByType("0");
			// 花絮类
			videoListHuaXu = indexService.getFilmListByType("1");
			// 最新类
			videoListnew = indexService.getFilmListByType();

			// 页面左上角微电影第一个视频
			// 最新类型
			if (videoListnew.size() > 0) {
				newfilmInfo = videoListnew.get(0);

				videoListnew.remove(0);
			}

			// 微电影类型
			if (videoListMicroFilm.size() > 0) {

				microfilmInfo = videoListMicroFilm.get(0);
				videoListMicroFilm.remove(0);
			}

			// 花絮类型
			if (videoListHuaXu.size() > 0) {

				huaxufilmInfo = videoListHuaXu.get(0);
				videoListHuaXu.remove(0);
			}

			/**
			 * ################################新闻信息#############################
			 * ###
			 * 
			 */
			List<News> newsinfoList = indexService.getallnews();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			for (News news : newsinfoList) {

				NewsDTO dto = new NewsDTO();
				dto.setId(news.getId());
				String title = news.getTitle();
				if (title.length() > 20) {

					title = title.substring(0, 20) + "...";
				}
				dto.setTitle(title);
				dto.setAuthor(news.getAuthor());
				dto.setContent(news.getContent());
				dto.setReleaseTime(sdf.format(news.getReleaseTime()));
				dto.setSource(news.getSource());

				newsList.add(dto);
			}

			customerphotosList = indexService.getCustomerInfoByNew();

		}

		return SUCCESS;
	}

	public List<NewsDTO> getNewsList() {
		return newsList;
	}

	public void setNewsList(List<NewsDTO> newsList) {
		this.newsList = newsList;
	}

	/* 礼服欣赏 */
	public String canonicals_list() throws Exception {
		return SUCCESS;
	}

	/* 顾客评价 */
	public String estimate() throws Exception {
		return SUCCESS;
	}

	/* 客片 */
	public String showcase() throws Exception {
		return SUCCESS;
	}

	/* 团购活动 */
	public String group_list() throws Exception {

		Page page = new Page(15, pageIndex, true);
		indexService.getGroupitemInfoListPage(page);
		int count = page.getTotalRecordCount();
		request.setAttribute("count", count);

		return SUCCESS;
	}

	/* 团购活动详情 */
	public String group_content() throws Exception {

		photoListweekRecommendsub = indexService
				.getPhotoWorksforWeekRecommend2();

		List<News> newsinfoList = indexService.getallnews();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for (News news : newsinfoList) {

			NewsDTO dto = new NewsDTO();
			dto.setId(news.getId());
			String title = news.getTitle();
			if (title.length() > 20) {

				title = title.substring(0, 20) + "...";
			}
			dto.setTitle(title);
			dto.setAuthor(news.getAuthor());
			dto.setContent(news.getContent());
			dto.setReleaseTime(sdf.format(news.getReleaseTime()));
			dto.setSource(news.getSource());

			newsList2.add(dto);
		}

		videoListnewsub = indexService.getFilmListByType2();

		String id = request.getParameter("id");
		GroupItemsInfo groupitem = indexService.getGroupItemObject(Long
				.parseLong(id));
		request.setAttribute("groupitem", groupitem);

		return SUCCESS;
	}

	/* 微电影 */
	public String microfilm() throws Exception {
		return SUCCESS;
	}

	/* 摄影作品 */
	public String photography_list() throws Exception {
		request.setAttribute("code", "01");
		Page page = new Page(11, pageIndex, true);
		indexService.getnewTopicandPhotosforPage(page, "01");
		int count = page.getTotalRecordCount();
		request.setAttribute("count", count);
		photoworksTypeTagList = indexService.getPhotoChildrenTypeByCode(Constant.PHOTOWORKS);
		return SUCCESS;
	}

	/* 联系我们 */
	public String studio_contact() throws Exception {
		return SUCCESS;
	}

	/* 80视觉招聘 */
	public String studio_recruit() throws Exception {
		return SUCCESS;
	}

	/* 80视觉服务 */
	public String studio_service() throws Exception {
		return SUCCESS;
	}

	/* 80视觉团队 */
	public String studio_team() throws Exception {
		return SUCCESS;
	}

	/* 工作室简介 */
	public String studio() throws Exception {

		return SUCCESS;
	}

	/* 新闻 */
	public String news_list() throws Exception {
		return SUCCESS;
	}

	/* 80视觉团队 */
	public String studio_team_show() throws Exception {
		return SUCCESS;
	}

	public IndexService getIndexService() {
		return indexService;
	}

	public void setIndexService(IndexService indexService) {
		this.indexService = indexService;
	}

	public List<PhotoTopicDTO> getPhotoListxiandai() {
		return photoListxiandai;
	}

	public void setPhotoListxiandai(List<PhotoTopicDTO> photoListxiandai) {
		this.photoListxiandai = photoListxiandai;
	}

	public List<PhotoTypeDTO> getPhotoworksTypeTagList() {
		return photoworksTypeTagList;
	}

	public void setPhotoworksTypeTagList(List<PhotoTypeDTO> photoworksTypeTagList) {
		this.photoworksTypeTagList = photoworksTypeTagList;
	}

	public PhotoTypeService getPhotoTyperService() {
		return photoTyperService;
	}

	public void setPhotoTyperService(PhotoTypeService photoTyperService) {
		this.photoTyperService = photoTyperService;
	}

	public List<PhotoTopicDTO> getPhotoListzuixin() {
		return photoListzuixin;
	}

	public void setPhotoListzuixin(List<PhotoTopicDTO> photoListzuixin) {
		this.photoListzuixin = photoListzuixin;
	}

	public List<PhotoTopicDTO> getPhotoListwaijing() {
		return photoListwaijing;
	}

	public void setPhotoListwaijing(List<PhotoTopicDTO> photoListwaijing) {
		this.photoListwaijing = photoListwaijing;
	}

	public List<PhotoTopicDTO> getPhotoListneijing() {
		return photoListneijing;
	}

	public void setPhotoListneijing(List<PhotoTopicDTO> photoListneijing) {
		this.photoListneijing = photoListneijing;
	}

	public List<PhotoTopicDTO> getPhotoListgudian() {
		return photoListgudian;
	}

	public void setPhotoListgudian(List<PhotoTopicDTO> photoListgudian) {
		this.photoListgudian = photoListgudian;
	}

	public PhtotoGraphyWroks getWeekrecommend() {
		return weekrecommend;
	}

	public void setWeekrecommend(PhtotoGraphyWroks weekrecommend) {
		this.weekrecommend = weekrecommend;
	}

	public List<PhtotoGraphyWroks> getPhotoListweekRecommend() {
		return photoListweekRecommend;
	}

	public void setPhotoListweekRecommend(
			List<PhtotoGraphyWroks> photoListweekRecommend) {
		this.photoListweekRecommend = photoListweekRecommend;
	}

	public List<PhotoTopicDTO> getPhotoListdressxiandai() {
		return photoListdressxiandai;
	}

	public void setPhotoListdressxiandai(
			List<PhotoTopicDTO> photoListdressxiandai) {
		this.photoListdressxiandai = photoListdressxiandai;
	}

	public List<PhotoTopicDTO> getPhotoListdresschuantong() {
		return photoListdresschuantong;
	}

	public void setPhotoListdresschuantong(
			List<PhotoTopicDTO> photoListdresschuantong) {
		this.photoListdresschuantong = photoListdresschuantong;
	}

	public List<PhotoTypeDTO> getDressTypeTagList() {
		return dressTypeTagList;
	}

	public void setDressTypeTagList(List<PhotoTypeDTO> dressTypeTagList) {
		this.dressTypeTagList = dressTypeTagList;
	}

	public List<PhotoTopicDTO> getPhotoDressListzuixin() {
		return photoDressListzuixin;
	}

	public void setPhotoDressListzuixin(List<PhotoTopicDTO> photoDressListzuixin) {
		this.photoDressListzuixin = photoDressListzuixin;
	}

	public PhotoTopicDTO getHotdressphotoTopic() {
		return hotdressphotoTopic;
	}

	public void setHotdressphotoTopic(PhotoTopicDTO hotdressphotoTopic) {
		this.hotdressphotoTopic = hotdressphotoTopic;
	}

	public VideoInfo getNewfilmInfo() {
		return newfilmInfo;
	}

	public void setNewfilmInfo(VideoInfo newfilmInfo) {
		this.newfilmInfo = newfilmInfo;
	}

	public VideoInfo getMicrofilmInfo() {
		return microfilmInfo;
	}

	public void setMicrofilmInfo(VideoInfo microfilmInfo) {
		this.microfilmInfo = microfilmInfo;
	}

	public VideoInfo getHuaxufilmInfo() {
		return huaxufilmInfo;
	}

	public void setHuaxufilmInfo(VideoInfo huaxufilmInfo) {
		this.huaxufilmInfo = huaxufilmInfo;
	}

	public List<VideoInfo> getVideoListnew() {
		return videoListnew;
	}

	public void setVideoListnew(List<VideoInfo> videoListnew) {
		this.videoListnew = videoListnew;
	}

	public List<VideoInfo> getVideoListMicroFilm() {
		return videoListMicroFilm;
	}

	public void setVideoListMicroFilm(List<VideoInfo> videoListMicroFilm) {
		this.videoListMicroFilm = videoListMicroFilm;
	}

	public List<VideoInfo> getVideoListHuaXu() {
		return videoListHuaXu;
	}

	public void setVideoListHuaXu(List<VideoInfo> videoListHuaXu) {
		this.videoListHuaXu = videoListHuaXu;
	}

	public PhotoTopicDTO getHotdressphotoTopicct() {
		return hotdressphotoTopicct;
	}

	public void setHotdressphotoTopicct(PhotoTopicDTO hotdressphotoTopicct) {
		this.hotdressphotoTopicct = hotdressphotoTopicct;
	}

	public PhotoTopicDTO getHotdressphotoTopicxd() {
		return hotdressphotoTopicxd;
	}

	public void setHotdressphotoTopicxd(PhotoTopicDTO hotdressphotoTopicxd) {
		this.hotdressphotoTopicxd = hotdressphotoTopicxd;
	}

	public List<GroupItemsInfo> getGroupListnew() {
		return groupListnew;
	}

	public void setGroupListnew(List<GroupItemsInfo> groupListnew) {
		this.groupListnew = groupListnew;
	}

	public List<PhtotoGraphyWroks> getPhotoListweekRecommendsub() {
		return photoListweekRecommendsub;
	}

	public void setPhotoListweekRecommendsub(
			List<PhtotoGraphyWroks> photoListweekRecommendsub) {
		this.photoListweekRecommendsub = photoListweekRecommendsub;
	}

	public List<VideoInfo> getVideoListnewsub() {
		return videoListnewsub;
	}

	public void setVideoListnewsub(List<VideoInfo> videoListnewsub) {
		this.videoListnewsub = videoListnewsub;
	}

	public PhtotoGraphyWroks getWeekrecommendsub() {
		return weekrecommendsub;
	}

	public void setWeekrecommendsub(PhtotoGraphyWroks weekrecommendsub) {
		this.weekrecommendsub = weekrecommendsub;
	}

	public List<NewsDTO> getNewsList2() {
		return newsList2;
	}

	public void setNewsList2(List<NewsDTO> newsList2) {
		this.newsList2 = newsList2;
	}

	public List<GroupItemDTO> getGroupList() {
		return groupList;
	}

	public void setGroupList(List<GroupItemDTO> groupList) {
		this.groupList = groupList;
	}

	public List<PhotoTopicDTO> getPhotoDressListzuixin2() {
		return photoDressListzuixin2;
	}

	public void setPhotoDressListzuixin2(
			List<PhotoTopicDTO> photoDressListzuixin2) {
		this.photoDressListzuixin2 = photoDressListzuixin2;
	}

	public List<PhotoTopicDTO> getPhotoListdressxiandai2() {
		return photoListdressxiandai2;
	}

	public void setPhotoListdressxiandai2(
			List<PhotoTopicDTO> photoListdressxiandai2) {
		this.photoListdressxiandai2 = photoListdressxiandai2;
	}


	public List<CustomerDTO> getCustomerphotosList() {
		return customerphotosList;
	}

	public void setCustomerphotosList(List<CustomerDTO> customerphotosList) {
		this.customerphotosList = customerphotosList;
	}

	public List<PhotoTypeDTO> getHotspotsType() {
		return hotspotsType;
	}

	public void setHotspotsType(List<PhotoTypeDTO> hotspotsType) {
		this.hotspotsType = hotspotsType;
	}

	public List<PhotoTopicDTO> getHotspots1() {
		return hotspots1;
	}

	public void setHotspots1(List<PhotoTopicDTO> hotspots1) {
		this.hotspots1 = hotspots1;
	}

	public List<PhotoTopicDTO> getHotspots2() {
		return hotspots2;
	}

	public void setHotspots2(List<PhotoTopicDTO> hotspots2) {
		this.hotspots2 = hotspots2;
	}

	public List<PhotoTopicDTO> getHotspots3() {
		return hotspots3;
	}

	public void setHotspots3(List<PhotoTopicDTO> hotspots3) {
		this.hotspots3 = hotspots3;
	}

	public List<PhotoTopicDTO> getHotspots4() {
		return hotspots4;
	}

	public void setHotspots4(List<PhotoTopicDTO> hotspots4) {
		this.hotspots4 = hotspots4;
	}

	public List<PhotoTopicDTO> getPhotoListdresshanxi() {
		return photoListdresshanxi;
	}

	public void setPhotoListdresshanxi(List<PhotoTopicDTO> photoListdresshanxi) {
		this.photoListdresshanxi = photoListdresshanxi;
	}
	
	

}
