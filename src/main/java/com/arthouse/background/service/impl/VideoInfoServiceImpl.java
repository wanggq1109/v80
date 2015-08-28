package com.arthouse.background.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.arthouse.background.service.VideoInfoService;
import com.arthouse.common.dao.ICommonDao;
import com.arthouse.common.domain.News;
import com.arthouse.common.domain.VideoInfo;
import com.arthouse.common.page.Page;

/**
 * 微电影
 * @author wanggq
 *
 */
public class VideoInfoServiceImpl implements VideoInfoService {
	
	private ICommonDao dao;

	public ICommonDao getDao() {
		return dao;
	}

	public void setDao(ICommonDao dao) {
		this.dao = dao;
	}
	
	public VideoInfo getVideoInfoById(Long id)throws Exception{
		
		String sql=" from VideoInfo p where p.id ='"+id+"' ";
		VideoInfo videoinfo=(VideoInfo)dao.getUniqueObjectByHql(sql);
        return videoinfo;
	}
	
	/**
	 * 查询微电影
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public List<VideoInfo> getVideoData(Page page,String code)throws Exception{
		 List params = new ArrayList();
		 StringBuilder hql= new StringBuilder();
		 hql.append(" from VideoInfo  v  order by v.uploadTime desc");
	       	/* hql.append(" and p.photoType= ?");
	         params.add(code);*/
		
	   return dao.query(hql.toString(), page, params.toArray());
	}
	
	
	public void saveVideo(VideoInfo film)throws Exception{
		
		dao.save(film);
		
	}
	
	public void updateVideo(VideoInfo film)throws Exception{
		
		dao.update(film);
		
	}
	
	public void delete(String[] ids) throws Exception {

		String hql = "delete from  VideoInfo  WHERE id in (:ids)";

		dao.updTobatch(ids, hql);

	}

}
