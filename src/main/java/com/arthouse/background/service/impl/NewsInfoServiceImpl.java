package com.arthouse.background.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.arthouse.background.service.NewsInfoService;
import com.arthouse.common.dao.ICommonDao;
import com.arthouse.common.domain.News;
import com.arthouse.common.domain.PhtotoGraphyWroks;
import com.arthouse.common.domain.VideoInfo;
import com.arthouse.common.page.Page;

public class NewsInfoServiceImpl implements NewsInfoService {

	private ICommonDao dao;

	public ICommonDao getDao() {
		return dao;
	}

	public void setDao(ICommonDao dao) {
		this.dao = dao;
	}

	public void newsInfo_save(News news) {

		try {
			dao.save(news);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 查询新闻
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<News> getnewsInfo(Page page) throws Exception {
		List params = new ArrayList();
		StringBuilder hql = new StringBuilder();
		hql.append(" from News  n  order by n.releaseTime desc");

		return dao.query(hql.toString(), page, params.toArray());
	}

	public void delete(String[] id) throws Exception {

		String hql = "delete from  News  WHERE id in (:ids)";

		dao.updTobatch(id, hql);

	}
	
	
	/**
	 * 获取新闻对象
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public News getnewInfo(Long id)throws Exception{
		
		String sql=" from News p where p.id ='"+id+"' ";
		News newsinfo=(News)dao.getUniqueObjectByHql(sql);
        return newsinfo;
		
	}
	
	
	public void update(News newsinfo)throws Exception{
		
		dao.update(newsinfo);
		
	}
	

}
