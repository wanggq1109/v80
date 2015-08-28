package com.arthouse.goldfish.web;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.arthouse.base.web.BaseAction;
import com.arthouse.common.domain.News;
import com.arthouse.common.domain.PhtotoGraphyWroks;
import com.arthouse.common.domain.VideoInfo;
import com.arthouse.common.dto.MicroFilmDTO;
import com.arthouse.common.dto.NewsDTO;
import com.arthouse.common.page.Page;
import com.arthouse.goldfish.service.IndexService;
import com.opensymphony.xwork2.Action;

/**
 * 前台新闻展示
 * 
 * @author wanggq
 * 
 */
public class NewsInfoAction extends BaseAction {

	private IndexService indexService;
	private Page page;
	News newsinfo = null;
	List<News> pageList = new ArrayList<News>();
	
	List<NewsDTO> newsList = new ArrayList<NewsDTO>();
	
	List<PhtotoGraphyWroks> photoListweekRecommend = new ArrayList<PhtotoGraphyWroks>();
	
	private PhtotoGraphyWroks weekrecommend;
	
	List<VideoInfo> videoListnew = new ArrayList<VideoInfo>();

	/**
	 * 初始化新闻列表
	 * 
	 * @return
	 */
	public String newsinfoList_show() {

		return SUCCESS;
	}

	public String show_news() {

		String id = request.getParameter("id");
		try {
			newsinfo = indexService.getNewsInfoObject(Long.parseLong(id));
			
			List<News> newsinfoList = indexService.getallnews();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			for(News news : newsinfoList){
				
				NewsDTO dto = new NewsDTO();
				dto.setId(news.getId());
				String title = news.getTitle();
				if(title.length()>20){
					
					title = title.substring(0, 20)+"...";
				}
				dto.setTitle(title);
				dto.setAuthor(news.getAuthor());
				dto.setContent(news.getContent());
				dto.setReleaseTime(sdf.format(news.getReleaseTime()));
				dto.setSource(news.getSource());
				
				newsList.add(dto);
			}
			
			photoListweekRecommend = indexService.getPhotoWorksforWeekRecommend2();
			if (photoListweekRecommend.size() > 0) {

				weekrecommend = photoListweekRecommend.get(0);
			}
			
			videoListnew = indexService.getFilmListByType2();

			

		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return SUCCESS;
	}

	/**
	 * 新闻列表分页
	 * 
	 * @return
	 * @throws Exception
	 */
	public String ajaxnewsInfo_page() throws Exception {

		page = new Page(14, pageIndex, true);
		indexService.getNewsInfoListPage(page);
		pageList = page.getRecord();
		response.setContentType("application/json; charset=utf-8"); // 输出文本为application
		response.setHeader("Cache-Control", "no-cache"); // 取消浏览器缓存
		// 必须加上,防止前端从JSON中取出的数据成乱码
		response.setCharacterEncoding("UTF-8");

		PrintWriter out = response.getWriter();

		List<NewsDTO> dataList = new ArrayList<NewsDTO>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for (News news : pageList) {
			NewsDTO dto = new NewsDTO();
			dto.setId(news.getId());
			String title = news.getTitle();
			if (title.length() > 50) {

				title = title.substring(0, 50) + "...";
			}
			dto.setTitle(title);
			String descr = news.getDescr();
			if (StringUtils.isNotEmpty(descr)&&descr.length() > 50) {

				descr = descr.substring(0, 50) + "...";
			}
			
			dto.setDescr(descr);
			dto.setContent(news.getContent());
			dto.setAuthor(news.getAuthor());
			dto.setReleaseTime(sdf.format(news.getReleaseTime()));
			dto.setSource(news.getSource());
			dto.setUrl(news.getUrl());

			dataList.add(dto);
		}

		JSONArray rows = JSONArray.fromObject(dataList);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("pageCount", page.getPageCount());
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

	public News getNewsinfo() {
		return newsinfo;
	}

	public void setNewsinfo(News newsinfo) {
		this.newsinfo = newsinfo;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public List<News> getPageList() {
		return pageList;
	}

	public void setPageList(List<News> pageList) {
		this.pageList = pageList;
	}

	public List<NewsDTO> getNewsList() {
		return newsList;
	}

	public void setNewsList(List<NewsDTO> newsList) {
		this.newsList = newsList;
	}

	public List<PhtotoGraphyWroks> getPhotoListweekRecommend() {
		return photoListweekRecommend;
	}

	public void setPhotoListweekRecommend(
			List<PhtotoGraphyWroks> photoListweekRecommend) {
		this.photoListweekRecommend = photoListweekRecommend;
	}

	public PhtotoGraphyWroks getWeekrecommend() {
		return weekrecommend;
	}

	public void setWeekrecommend(PhtotoGraphyWroks weekrecommend) {
		this.weekrecommend = weekrecommend;
	}

	public List<VideoInfo> getVideoListnew() {
		return videoListnew;
	}

	public void setVideoListnew(List<VideoInfo> videoListnew) {
		this.videoListnew = videoListnew;
	}
	
	
	
}
