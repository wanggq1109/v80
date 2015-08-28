package com.arthouse.background.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;

import com.arthouse.background.service.PhotoTypeService;
import com.arthouse.common.dao.ICommonDao;
import com.arthouse.common.domain.PhotoType;
import com.arthouse.common.dto.PhotoTypeDTO;

public class PhotoTypeServiceImpl implements PhotoTypeService {

	private ICommonDao dao;

	public ICommonDao getDao() {
		return dao;
	}

	public void setDao(ICommonDao dao) {
		this.dao = dao;
	}

	public void savePhotoType(String code, String name) throws Exception {

		PhotoType phototype = new PhotoType();
		phototype.setCode(code);
		phototype.setName(name);
		dao.save(phototype);

	}

	/**
	 * 
	 * 判断当前类型是否已经存在
	 */
	public boolean isExistPhotoType(String code) throws Exception {

		Session session = dao.getHibernateSession();
		try {
			Query q = session.createQuery(" from PhotoType p where p.code = :code");
					
			q.setParameter("code", code);

			List list = q.list();
			if (list.size() > 0) {

				return true;
			} else {

				return false;
			}

		} catch (Exception ex) {
			throw ex;
		} finally {
			session.clear();
			session.close();
		}

	}
	/**
	 * 
	 * 判断当前类型是否作品有关联
	 */
	public boolean isExistPhtotoGraphyWroks(String code) throws Exception {

		Session session = dao.getHibernateSession();
		try {
			Query q = session.createQuery(" from PhtotoGraphyWroks p where p.photoType = :type");
					
			q.setParameter("type", code);

			List list = q.list();
			if (list.size() > 0) {

				return true;
			} else {

				return false;
			}

		} catch (Exception ex) {
			throw ex;
		} finally {
			session.clear();
			session.close();
		}

	}
	
	public void updatePhotoType(PhotoType photoType)throws Exception{
		
		dao.update(photoType);
		
	}
	
	
	public void deletePhotoType(PhotoType photoType)throws Exception{
		
		dao.delete(photoType);
		
		
	}
	
	public PhotoType getPhotoType(String code) throws Exception {

		Session session = dao.getHibernateSession();
		try {
			Query q = session
					.createQuery(" from PhotoType p where p.code = :code");
			q.setParameter("code", code);
			PhotoType phototype = (PhotoType) q.uniqueResult();

			return phototype;
		} catch (Exception ex) {
			throw ex;
		} finally {
			session.clear();
			session.close();
		}

	}
	

	/**
	 * 获取所有的摄影作品父类型
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PhotoType> getParentPhotoType() throws Exception {

		Session session = dao.getHibernateSession();

		Query q = session
				.createQuery(" from PhotoType  p where Length(p.code)=2 ");

		List<PhotoType> photTypeList = (List<PhotoType>) q.list();

		session.clear();
		session.close();
		return photTypeList;

	}
	
	/**
	 * 获取所有的摄影作品父类型
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PhotoType> getParentPhotoType4Lever(String code) throws Exception {

		StringBuilder hql = new StringBuilder();
		hql.append("from PhotoType p ").append("where 1=1");

		if (StringUtils.isNotEmpty(code)) {
			hql.append(" and p.code like '" + code + "%'");// 模糊查询
		}
		
		hql.append(" and Length(p.code)=4");// 模糊查询
		
		List<PhotoType> photTypeList = dao.getListByHql(hql.toString());
		
		return photTypeList;

	}

	/**
	 * 根据父节点code获取所有子节点
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PhotoTypeDTO> getAllChildrenNode(String code) throws Exception {

		StringBuilder hql = new StringBuilder();
		hql.append("from PhotoType p ").append("where 1=1");

		if (StringUtils.isNotEmpty(code)) {
			hql.append(" and p.code like '" + code + "%'");// 模糊查询
		}
		List<PhotoType> childrenNodeList = (List<PhotoType>) dao
				.getListByHql(hql.toString());

		List<PhotoTypeDTO> list = new ArrayList<PhotoTypeDTO>();

		for (PhotoType phtotype : childrenNodeList) {
			PhotoTypeDTO dto = new PhotoTypeDTO();
			dto.setCode(phtotype.getCode());
			dto.setName(phtotype.getName());
			dto.setId(String.valueOf(phtotype.getId()));
			list.add(dto);
		}

		return list;
	}
	
	/**
	 * 根据父节点code获取本身以为其他子节点
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PhotoTypeDTO> getOtherChildrenNode(String code) throws Exception {

		StringBuilder hql = new StringBuilder();
		hql.append("from PhotoType p ").append("where p.code!= '"+code+"'");

		if (StringUtils.isNotEmpty(code)) {
			hql.append(" and p.code like '" + code + "%'");// 模糊查询
		}
		List<PhotoType> childrenNodeList = (List<PhotoType>) dao
				.getListByHql(hql.toString());

		List<PhotoTypeDTO> list = new ArrayList<PhotoTypeDTO>();

		for (PhotoType phtotype : childrenNodeList) {
			PhotoTypeDTO dto = new PhotoTypeDTO();
			dto.setText(phtotype.getName());
			dto.setId(phtotype.getCode());
			list.add(dto);
		}

		return list;
	}
	

	/**
	 * 根据父节点获取二级子节点
	 * 
	 * @param code
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<PhotoTypeDTO> getPhotoChildrenTypeByCode(String code)
			throws Exception {

		StringBuilder hql = new StringBuilder();
		hql.append("from PhotoType p ").append("where 1=1");

		if (StringUtils.isNotEmpty(code)) {
			hql.append(" and p.code like '" + code + "%'");// 模糊查询
		}

		hql.append(" and length(p.code)=4");// 模糊查询

		List<PhotoType> childrenList = (List<PhotoType>) dao.getListByHql(hql
				.toString());

		List list = new ArrayList<PhotoTypeDTO>();
		for (PhotoType phtotype : childrenList) {
			PhotoTypeDTO dto = new PhotoTypeDTO();
			dto.setId(phtotype.getCode());
			Set children = this.getPhotoChildrenTypeByCode3(phtotype.getCode());
			dto.setText(phtotype.getName());
			dto.setChildren(children);
			list.add(dto);
		}

		return list;

	}

	/**
	 * 根据父节点获取三级级子节点
	 * 
	 * @param code
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Set<PhotoTypeDTO> getPhotoChildrenTypeByCode3(String code)
			throws Exception {

		StringBuilder hql = new StringBuilder();
		hql.append("from PhotoType p ").append("where 1=1");

		if (StringUtils.isNotEmpty(code)) {
			hql.append(" and p.code like '" + code + "%'");// 模糊查询
		}

		hql.append(" and length(p.code)=6");// 模糊查询

		List<PhotoType> childrenList = (List<PhotoType>) dao.getListByHql(hql
				.toString());

		Set<PhotoTypeDTO> set = new HashSet<PhotoTypeDTO>();

		for (PhotoType phtotype : childrenList) {
			PhotoTypeDTO dto = new PhotoTypeDTO();
			dto.setId(phtotype.getCode());
			dto.setText(phtotype.getName());
			set.add(dto);
		}

		return set;

	}

}
