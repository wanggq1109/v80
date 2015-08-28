package com.arthouse.background.service;

import java.util.List;
import java.util.Set;

import com.arthouse.common.domain.PhotoType;
import com.arthouse.common.dto.PhotoTypeDTO;

public interface PhotoTypeService {

	/**
	 * 获取所有的摄影作品父类型
	 * 
	 * @return
	 */
	public List getParentPhotoType() throws Exception;

	/**
	 * 根据父节点获取对应子节点
	 * 
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public List<PhotoTypeDTO> getPhotoChildrenTypeByCode(String code)
			throws Exception;

	/**
	 * 根据父节点获取三级级子节点
	 * 
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public Set<PhotoTypeDTO> getPhotoChildrenTypeByCode3(String code)
			throws Exception;

	/**
	 * 根据父节点code获取所有子节点
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<PhotoTypeDTO> getAllChildrenNode(String code) throws Exception;

	/**
	 * 
	 * 判断当前类型是否作品有关联
	 */
	public boolean isExistPhtotoGraphyWroks(String code) throws Exception;
	
	
	
	/**
	 * 根据父节点code获取本身以为其他子节点
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<PhotoTypeDTO> getOtherChildrenNode(String code) throws Exception;
	
	
	/**
	 * 获取所有的摄影作品父类型
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<PhotoType> getParentPhotoType4Lever(String code) throws Exception;

	public void savePhotoType(String code, String name) throws Exception;

	// 判断是否已经存在类型
	public boolean isExistPhotoType(String code) throws Exception;

	public PhotoType getPhotoType(String code) throws Exception;

	public void updatePhotoType(PhotoType photoType) throws Exception;

	public void deletePhotoType(PhotoType photoType) throws Exception;

}
