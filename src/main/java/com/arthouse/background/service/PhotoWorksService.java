package com.arthouse.background.service;

import java.util.List;

import com.arthouse.common.domain.PhototTopic;
import com.arthouse.common.domain.PhtotoGraphyWroks;
import com.arthouse.common.page.Page;

/**
 * 
 * 摄影作品
 * @author wgq
 *
 */
public interface PhotoWorksService {
	
	/**
	 * 根据类型查询摄影作品
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public List<PhtotoGraphyWroks> getPhotoWorksDataByType(Page page,Long id)throws Exception;
	
	
	public List<PhtotoGraphyWroks> getPhotoWorksDataByCode(Page page,String code)throws Exception;
	
	/**
	 * 存储摄影作品信息
	 */
	public void savePhotoWorksInfo(PhtotoGraphyWroks photoWorks)throws Exception;
	
	/**
	 * 文件重命名
	 * @return
	 */
	public String getPhotoRandom();
	
	/**
	 * 保存照片组描述
	 * @throws Exception
	 */
	public void savePhotoGroupDesc(PhototTopic photodesc)throws Exception;
	
	
	/**
	 * 更新照片组描述
	 * @throws Exception
	 */
	public void UpdatePhotoGroupDesc(PhototTopic photodesc)throws Exception;
	
	/**
	 * 获取摄影作品对象
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public PhtotoGraphyWroks getPhotoWroks(Long id)throws Exception;
	
	/**
	 * 更新
	 * @param photoworks
	 * @throws Exception
	 */
	public void updatePhotoWorks(PhtotoGraphyWroks photoworks)throws Exception;
	
	/**
	 * 删除摄影作品
	 * @throws Exception
	 */
	public void PhotoWorksDelete(String[] ids)throws Exception;
	
	
	/**
	 * 查询一组摄影作品
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	public List<PhtotoGraphyWroks> getPhotoWorksList(String[] ids)throws Exception;
	
	
	
	/**
	 * 获取摄影作品描述
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public PhototTopic getPhotoWroksDesc(String  code)throws Exception;
	
	
	/**
	 * 获取摄影作品对象
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public  List<PhtotoGraphyWroks> getPhtotoGraphyWroksByCode(String  code)throws Exception;
	
	
	public PhtotoGraphyWroks getPhotoWroks(String  code)throws Exception;
	
	
	
	public void save(Object obj);
	
	public void update(Object obj);
	
	
	public PhototTopic getPhotoTopicInfo(Long id) throws Exception;
	
	
	@SuppressWarnings("unchecked")
	public List<PhototTopic> getPhotoTopicData(Page page,String code) throws Exception;
	
	public void delete(Object obj);
	
	public List<PhtotoGraphyWroks> getTopicPhotosById(Long id)throws Exception;
	
	/**
	 * 设置作品封面
	 * @param photoswork
	 */
	public void updatefacephotos(PhtotoGraphyWroks photoswork);
}
