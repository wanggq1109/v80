package com.arthouse.background.service;

import java.util.List;

import com.arthouse.common.domain.News;
import com.arthouse.common.page.Page;

public interface NewsInfoService {
	
	
	public void newsInfo_save(News news);
	
	
	/**
	 * 查询新闻
	 * @return
	 * @throws Exception
	 */
	public List<News> getnewsInfo(Page page)throws Exception;
	
	
	public void delete(String[] id)throws Exception;
	
	

	/**
	 * 获取新闻对象
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public News getnewInfo(Long id)throws Exception;
	
	
	
	public void update(News newsinfo)throws Exception;

}
