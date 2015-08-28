package com.arthouse.background.service;

import java.util.List;

import com.arthouse.common.domain.VideoInfo;
import com.arthouse.common.page.Page;

/**
 * 微电影
 * @author wanggq
 *
 */
public interface VideoInfoService {
	

	/**
	 * 查询微电影
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public List<VideoInfo> getVideoData(Page page,String code)throws Exception;
	
	public void saveVideo(VideoInfo film)throws Exception;
	
	public VideoInfo getVideoInfoById(Long id)throws Exception;
	
	public void updateVideo(VideoInfo film)throws Exception;
	
	public void delete(String[] ids) throws Exception;
	

}
