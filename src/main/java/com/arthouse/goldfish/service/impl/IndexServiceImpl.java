package com.arthouse.goldfish.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;

import com.arthouse.common.dao.ICommonDao;
import com.arthouse.common.domain.Customer;
import com.arthouse.common.domain.CustomerPhotos;
import com.arthouse.common.domain.EmployWorksTopic;
import com.arthouse.common.domain.Employee;
import com.arthouse.common.domain.GroupItemsInfo;
import com.arthouse.common.domain.News;
import com.arthouse.common.domain.PhotoType;
import com.arthouse.common.domain.PhototTopic;
import com.arthouse.common.domain.PhtotoGraphyWroks;
import com.arthouse.common.domain.StudioInfo;
import com.arthouse.common.domain.VideoInfo;
import com.arthouse.common.dto.CustomerDTO;
import com.arthouse.common.dto.PhotoTopicDTO;
import com.arthouse.common.dto.PhotoTypeDTO;
import com.arthouse.common.page.Page;
import com.arthouse.goldfish.service.IndexService;

;

public class IndexServiceImpl implements IndexService {

	private ICommonDao dao;

	public ICommonDao getDao() {
		return dao;
	}

	public void setDao(ICommonDao dao) {
		this.dao = dao;
	}

	public List<PhtotoGraphyWroks> getAllslides() throws Exception {

		String hql = " from PhtotoGraphyWroks p where p.photoType = '05' and p.releaseStatus = '1' ";
		List<PhtotoGraphyWroks> photoworks = dao.getListByHql(hql);
		return photoworks;
	}

	/**
	 * 一周推荐
	 */
	public List<PhtotoGraphyWroks> getPhotoWorksforWeekRecommend()
			throws Exception {

		List<PhtotoGraphyWroks> photoworks;
		Session session = dao.getHibernateSession();
		try {
			Query q = session
					.createQuery(" from PhtotoGraphyWroks p where   p.weekWorkRecommend = '1' order by uploadTime desc ");
			q.setFirstResult(0);
			q.setMaxResults(7);
			photoworks = (List<PhtotoGraphyWroks>) q.list();
		} catch (Exception ex) {
			throw ex;
		} finally {
			session.clear();
			session.close();
		}

		return photoworks;

	}

	/**
	 * 一周推荐
	 */
	public List<PhtotoGraphyWroks> getPhotoWorksforWeekRecommend2()
			throws Exception {

		List<PhtotoGraphyWroks> photoworks;
		Session session = dao.getHibernateSession();
		try {
			Query q = session
					.createQuery(" from PhtotoGraphyWroks p where   p.weekWorkRecommend = '1' order by uploadTime desc ");
			q.setFirstResult(0);
			q.setMaxResults(3);
			photoworks = (List<PhtotoGraphyWroks>) q.list();
		} catch (Exception ex) {
			throw ex;
		} finally {
			session.clear();
			session.close();
		}

		return photoworks;

	}

	/**
	 * 根据类型code查询摄影作品信息
	 */
	public List<PhtotoGraphyWroks> getPhotoWorksByCode(String code)
			throws Exception {

		List<PhtotoGraphyWroks> photoworks;
		Session session = dao.getHibernateSession();
		try {

			Query q = session
					.createQuery(" from PhtotoGraphyWroks p where  p.photoType like '"
							+ code + "%' ");
			q.setParameter("code", code);
			q.setFirstResult(0);
			q.setMaxResults(13);

			photoworks = (List<PhtotoGraphyWroks>) q.list();
		} catch (Exception ex) {
			throw ex;
		} finally {
			session.clear();
			session.close();
		}

		return photoworks;

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

		List<PhotoType> childrenList = new ArrayList<PhotoType>();
		List<PhotoTypeDTO> list = new ArrayList<PhotoTypeDTO>();
		Session session = dao.getHibernateSession();
		
		Query q = session
				.createQuery("from PhotoType p where  p.code like '"
							+ code + "%' and length(p.code)=4 ");
		
		q.setFirstResult(0);
		q.setMaxResults(4);

		try {
				childrenList= q.list();
			for (PhotoType phtotype : childrenList) {
				PhotoTypeDTO dto = new PhotoTypeDTO();
				dto.setId(String.valueOf(phtotype.getId()));
				dto.setCode(phtotype.getCode());
				dto.setText(phtotype.getName());
				list.add(dto);
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			session.clear();
			session.close();
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
	public List<PhotoTypeDTO> getPhotoChildrenTypeByCode2(String code)
			throws Exception {

		List<PhotoType> childrenList = new ArrayList<PhotoType>();
		List<PhotoTypeDTO> list = new ArrayList<PhotoTypeDTO>();
		Session session = dao.getHibernateSession();
		
		Query q = session
				.createQuery("from PhotoType p where  p.code like '"
							+ code + "%' and length(p.code)=4 ");
		
		try {
				childrenList= q.list();
			for (PhotoType phtotype : childrenList) {
				PhotoTypeDTO dto = new PhotoTypeDTO();
				dto.setId(phtotype.getCode());
				dto.setText(phtotype.getName());
				dto.setCode(phtotype.getCode());
				list.add(dto);
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			session.clear();
			session.close();
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
	

	public List<PhotoTopicDTO> getnewTopicandPhotos(String photoTypeCode)
			throws Exception {
		List<PhotoTopicDTO> topicDtoList = new ArrayList<PhotoTopicDTO>();
		Session session = dao.getHibernateSession();
		try {
			Query q = session
					.createQuery(" from PhototTopic p where 1=1 and  p.photoTypeCode like '"
							+ photoTypeCode + "%' order by p.time desc");
			q.setFirstResult(0);
			q.setMaxResults(12);

			List<PhototTopic> topicList = (List<PhototTopic>) q.list();
			for (PhototTopic topic : topicList) {
				PhotoTopicDTO dto = new PhotoTopicDTO();
				Set<PhtotoGraphyWroks> photoworksSet = topic.getPhotoworks();
				dto.setId(topic.getId());
				dto.setName(topic.getTitle());
				dto.setPhotTopicDesc(topic.getPhotTopicDesc());
				dto.setShotTime(topic.getShotTime());
				dto.setStylists(topic.getStylists());
				dto.setCameraman(topic.getCameraman());
				dto.setShotAddress(topic.getShotAddress());
				dto.setPhotosize(String.valueOf(photoworksSet.size()));
				String face_url = topic.getFaceurl();
				if(StringUtils.isNotEmpty(face_url)){
					dto.setUrl(face_url);
				}else{
					
					for (Iterator it = photoworksSet.iterator(); it.hasNext();) {
						PhtotoGraphyWroks photo = (PhtotoGraphyWroks) it.next();
						String photourl = photo.getBreviaryUrl();
						if (StringUtils.isNotEmpty(photourl)) {
							dto.setUrl(photourl);
							break;
						}

					}

				}
				
				topicDtoList.add(dto);

			}

		} catch (Exception ex) {
			throw ex;
		} finally {
			session.clear();
			session.close();
		}

		return topicDtoList;

	}

	public List<PhotoTopicDTO> getTopicByPhotosCode(String code)
			throws Exception {

		List<PhotoTopicDTO> topicDtoList = new ArrayList<PhotoTopicDTO>();
		Session session = dao.getHibernateSession();

		try {
			Query q = session
					.createQuery(" from PhototTopic p where   p.photoTypeCode = '"
							+ code + "' order by p.time desc");
			q.setFirstResult(0);
			q.setMaxResults(12);

			List<PhototTopic> topicList = (List<PhototTopic>) q.list();
			for (PhototTopic topic : topicList) {
				PhotoTopicDTO dto = new PhotoTopicDTO();
				Set<PhtotoGraphyWroks> photoworksSet = topic.getPhotoworks();
				dto.setId(topic.getId());
				dto.setName(topic.getTitle());
				dto.setPhotTopicDesc(topic.getPhotTopicDesc());
				dto.setShotTime(topic.getShotTime());
				dto.setStylists(topic.getStylists());
				dto.setCameraman(topic.getCameraman());
				dto.setShotAddress(topic.getShotAddress());
				dto.setPhotosize(String.valueOf(photoworksSet.size()));
				dto.setPhotoTypeCode(code);
				String face_url =topic.getFaceurl();
				if(StringUtils.isNotEmpty(face_url)){
					dto.setUrl(face_url);
				}else{
					for (Iterator it = photoworksSet.iterator(); it.hasNext();) {
						PhtotoGraphyWroks photo = (PhtotoGraphyWroks) it.next();
						String photourl = photo.getBreviaryUrl();
						if (StringUtils.isNotEmpty(photourl)) {
							dto.setUrl(photourl);
							break;
						}

					}

				}
				
				topicDtoList.add(dto);

			}

		} catch (Exception ex) {
			throw ex;
		} finally {
			session.clear();
			session.close();
		}

		return topicDtoList;

	}
	
	
	public List<PhotoTopicDTO> getTopicByPhotosCode2(String code)
			throws Exception {

		List<PhotoTopicDTO> topicDtoList = new ArrayList<PhotoTopicDTO>();
		Session session = dao.getHibernateSession();

		try {
			Query q = session
					.createQuery(" from PhototTopic p where   p.photoTypeCode = '"
							+ code + "' order by p.time desc");
			q.setFirstResult(0);
			q.setMaxResults(12);

			List<PhototTopic> topicList = (List<PhototTopic>) q.list();
			for (PhototTopic topic : topicList) {
				PhotoTopicDTO dto = new PhotoTopicDTO();
				Set<PhtotoGraphyWroks> photoworksSet = topic.getPhotoworks();
				dto.setId(topic.getId());
				dto.setName(topic.getTitle());
				dto.setPhotTopicDesc(topic.getPhotTopicDesc());
				dto.setShotTime(topic.getShotTime());
				dto.setStylists(topic.getStylists());
				dto.setCameraman(topic.getCameraman());
				dto.setShotAddress(topic.getShotAddress());
				dto.setPhotosize(String.valueOf(photoworksSet.size()));
				dto.setPhotoTypeCode(code);
				String face_url =topic.getFaceurl();
				if(StringUtils.isNotEmpty(face_url)){
					dto.setUrl(face_url);
				}else{
					for (Iterator it = photoworksSet.iterator(); it.hasNext();) {
						PhtotoGraphyWroks photo = (PhtotoGraphyWroks) it.next();
						String photourl = photo.getBreviaryUrl();
						if (StringUtils.isNotEmpty(photourl)) {
							dto.setUrl(photourl);
							break;
						}

					}

				}
				
				topicDtoList.add(dto);

			}

		} catch (Exception ex) {
			throw ex;
		} finally {
			session.clear();
			session.close();
		}

		return topicDtoList;

	}
	
	
	public List<PhotoTopicDTO> getTopicByPhotosCode3(String code)
			throws Exception {

		List<PhotoTopicDTO> topicDtoList = new ArrayList<PhotoTopicDTO>();
		Session session = dao.getHibernateSession();

		try {
			Query q = session
					.createQuery(" from PhototTopic p where   p.photoTypeCode = '"
							+ code + "' order by p.time desc");
			q.setFirstResult(0);
			q.setMaxResults(12);

			List<PhototTopic> topicList = (List<PhototTopic>) q.list();
			for (PhototTopic topic : topicList) {
				PhotoTopicDTO dto = new PhotoTopicDTO();
				Set<PhtotoGraphyWroks> photoworksSet = topic.getPhotoworks();
				dto.setId(topic.getId());
				dto.setName(topic.getTitle());
				dto.setPhotTopicDesc(topic.getPhotTopicDesc());
				dto.setShotTime(topic.getShotTime());
				dto.setStylists(topic.getStylists());
				dto.setCameraman(topic.getCameraman());
				dto.setShotAddress(topic.getShotAddress());
				dto.setPhotosize(String.valueOf(photoworksSet.size()));
				dto.setPhotoTypeCode(code);
				String face_url =topic.getFaceurl();
				if(StringUtils.isNotEmpty(face_url)){
					dto.setUrl(face_url);
				}else{
					for (Iterator it = photoworksSet.iterator(); it.hasNext();) {
						PhtotoGraphyWroks photo = (PhtotoGraphyWroks) it.next();
						String photourl = photo.getBreviaryUrl();
						if (StringUtils.isNotEmpty(photourl)) {
							dto.setUrl(photourl);
							break;
						}

					}

				}
				
				topicDtoList.add(dto);

			}

		} catch (Exception ex) {
			throw ex;
		} finally {
			session.clear();
			session.close();
		}

		return topicDtoList;

	}
	
	public List<PhotoTopicDTO> getTopicByPhotosCode4(String code)
			throws Exception {

		List<PhotoTopicDTO> topicDtoList = new ArrayList<PhotoTopicDTO>();
		Session session = dao.getHibernateSession();

		try {
			Query q = session
					.createQuery(" from PhototTopic p where   p.photoTypeCode = '"
							+ code + "' order by p.time desc");
			q.setFirstResult(0);
			q.setMaxResults(12);

			List<PhototTopic> topicList = (List<PhototTopic>) q.list();
			for (PhototTopic topic : topicList) {
				PhotoTopicDTO dto = new PhotoTopicDTO();
				Set<PhtotoGraphyWroks> photoworksSet = topic.getPhotoworks();
				dto.setId(topic.getId());
				dto.setName(topic.getTitle());
				dto.setPhotTopicDesc(topic.getPhotTopicDesc());
				dto.setShotTime(topic.getShotTime());
				dto.setStylists(topic.getStylists());
				dto.setCameraman(topic.getCameraman());
				dto.setShotAddress(topic.getShotAddress());
				dto.setPhotosize(String.valueOf(photoworksSet.size()));
				dto.setPhotoTypeCode(code);
				String face_url =topic.getFaceurl();
				if(StringUtils.isNotEmpty(face_url)){
					dto.setUrl(face_url);
				}else{
					for (Iterator it = photoworksSet.iterator(); it.hasNext();) {
						PhtotoGraphyWroks photo = (PhtotoGraphyWroks) it.next();
						String photourl = photo.getBreviaryUrl();
						if (StringUtils.isNotEmpty(photourl)) {
							dto.setUrl(photourl);
							break;
						}

					}

				}
				
				topicDtoList.add(dto);

			}

		} catch (Exception ex) {
			throw ex;
		} finally {
			session.clear();
			session.close();
		}

		return topicDtoList;

	}

	/**
	 * 首页显示客片20条最新记录
	 * @return
	 * @throws Exception
	 */
	public List<CustomerDTO> getCustomerInfoByNew() throws Exception {

		List<CustomerDTO> customerDtoList = new ArrayList<CustomerDTO>();
		Session session = dao.getHibernateSession();
		try {
			Query q = session
					.createQuery(" from Customer p  order by p.createTime desc");
			q.setFirstResult(0);
			q.setMaxResults(14);

			List<Customer> customerList = (List<Customer>) q.list();
			 SimpleDateFormat sdf = new SimpleDateFormat("dd-MMMM-yyyy",  
		                Locale.ENGLISH);  
			for (Customer ct : customerList) {
				CustomerDTO dto = new CustomerDTO();
				
				dto.setId(ct.getId());
				dto.setRemark(ct.getRemark());
				dto.setCustomerName(ct.getCustomerName());
				Date shootTime = ct.getShootTime();
				String engstr = sdf.format(shootTime);
				int index = engstr.indexOf("-", 0);
				String str1= engstr.substring(0, index);
				dto.setcDate(str1);
				String temp = engstr.substring(index+1);
				int index2 = temp.indexOf("-", 0);
				String str2 = temp.substring(0, index2);
				dto.setcMonth(str2.substring(0, 3));
				String face_url =ct.getFace_url();
				if(StringUtils.isNotEmpty(face_url)){
					dto.setUrl(face_url);
				}else{
					Set<CustomerPhotos> photoSet = ct.getPhotoworks();
					for (Iterator it = photoSet.iterator(); it.hasNext();) {
						CustomerPhotos photo = (CustomerPhotos) it.next();
						String photourl = photo.getSl_url();
						if (StringUtils.isNotEmpty(photourl)) {
							dto.setUrl(photourl);
							break;
						}
					}
					
				}
				

				customerDtoList.add(dto);
			}

		} catch (Exception ex) {
			throw ex;
		} finally {
			session.clear();
			session.close();
		}
		return customerDtoList;
	}
	
	
	public List<CustomerPhotos> getCustomerPhotosbyIdList(Page page, Long id)
			throws Exception {
		StringBuilder hql = new StringBuilder();
		hql.append("from CustomerPhotos t ");
		List params = new ArrayList();
		hql.append(" where t.customer.id=" + id);

		hql.append(" order by t.uploadTime desc ");
		return dao.query(hql.toString(), page, params.toArray());
	}
	

	public List<PhtotoGraphyWroks> getTopicPhotobyIdList(Page page, Long id)
			throws Exception {
		StringBuilder hql = new StringBuilder();
		hql.append("from PhtotoGraphyWroks t ");
		List params = new ArrayList();
		hql.append(" where t.phototdesc.id='" + id
				+ "' and t.releaseStatus='1'");

		hql.append(" order by t.uploadTime desc ");
		return dao.query(hql.toString(), page, params.toArray());
	}

	public List<PhtotoGraphyWroks> getTopicPhotobyIdListforWeek(Page page,
			Long id) throws Exception {
		StringBuilder hql = new StringBuilder();
		hql.append("from PhtotoGraphyWroks p where  p.weekWorkRecommend = '1'  ");

		hql.append(" and p.phototdesc.id='" + id + "' ");

		List params = new ArrayList();
		hql.append(" order by p.uploadTime desc  ");
		return dao.query(hql.toString(), page, params.toArray());
	}

	public List<PhtotoGraphyWroks> getphotosByTopic(PhototTopic topic)
			throws Exception {

		String hql = " from PhtotoGraphyWroks p where p.phototdesc.id = '"
				+ topic.getId() + "'  order by p.uploadTime desc";
		List<PhtotoGraphyWroks> photoworks = dao.getListByHql(hql);
		return photoworks;

	}
	
	public List<CustomerPhotos> getCustomerphotosById(Customer customer)
			throws Exception {

		String hql = " from CustomerPhotos p where p.customer.id = '"
				+ customer.getId() + "'  order by p.uploadTime desc";
		List<CustomerPhotos> photoworks = dao.getListByHql(hql);
		return photoworks;

	}

	public PhototTopic getTopic(Long id) throws Exception {

		String hql = " from PhototTopic p where p.id = '" + id + "'  ";
		PhototTopic topic = (PhototTopic) dao.getUniqueObjectByHql(hql);
		return topic;

	}
	
	
	public Customer getCustomerInfo(Long id) throws Exception {

		String hql = " from Customer p where p.id = '" + id + "'  ";
		Customer customer = (Customer) dao.getUniqueObjectByHql(hql);
		return customer;

	}

	public List<PhototTopic> getnewTopicandPhotosforPage(Page page,
			String photoTypeCode) throws Exception {
		List<PhotoTopicDTO> topicDtoList = new ArrayList<PhotoTopicDTO>();
		List params = new ArrayList();

		StringBuilder hql = new StringBuilder();
		hql.append("from PhototTopic p where 1=1 and  p.photoTypeCode like '"
				+ photoTypeCode + "%' order by p.time desc");

		return dao.query(hql.toString(), page, params.toArray());

	}

	/**
	 * 根据类型获取作品主题或者服装主题
	 * 
	 * @param page
	 * @param photoTypeCode
	 * @return
	 * @throws Exception
	 */
	public List<PhototTopic> getTopicandPhotosforPageByCode(Page page,
			String photoTypeCode) throws Exception {
		List<PhotoTopicDTO> topicDtoList = new ArrayList<PhotoTopicDTO>();
		List params = new ArrayList();

		StringBuilder hql = new StringBuilder();
		hql.append("from PhototTopic p where p.photoTypeCode = '"
				+ photoTypeCode + "' order by p.time desc");

		return dao.query(hql.toString(), page, params.toArray());

	}
	
	
	/**
	 * 根据时间获取顾客拍摄信息
	 * 
	 * @param page
	 * @param photoTypeCode
	 * @return
	 * @throws Exception
	 */
	public List<Customer> getCustomerInfoforPageByTime(Page page,
			String time) throws Exception {
		List params = new ArrayList();

		StringBuilder hql = new StringBuilder();
		hql.append("from Customer p where p.shootTime like '"
				+ time + "%' order by p.shootTime desc");

		return dao.query(hql.toString(), page, params.toArray());

	}
	
	
	
	

	@SuppressWarnings("unchecked")
	public List<PhotoTopicDTO> getTopicByPhotosCodePage(Page page, String code)
			throws Exception {

		List<PhotoTopicDTO> topicDtoList = new ArrayList<PhotoTopicDTO>();
		Session session = dao.getHibernateSession();
		StringBuilder hql = new StringBuilder();
		List params = new ArrayList();

		try {
			hql.append("from PhototTopic p where   p.photoTypeCode = '" + code
					+ "' order by p.time asc");
			List<PhototTopic> topicList = (List<PhototTopic>) (List<PhototTopic>) dao
					.query(hql.toString(), page, params.toArray());
			for (PhototTopic topic : topicList) {
				PhotoTopicDTO dto = new PhotoTopicDTO();
				Set<PhtotoGraphyWroks> photoworksSet = topic.getPhotoworks();
				dto.setId(topic.getId());
				dto.setName(topic.getTitle());
				dto.setPhotTopicDesc(topic.getPhotTopicDesc());
				dto.setShotTime(topic.getShotTime());
				dto.setStylists(topic.getStylists());
				dto.setCameraman(topic.getCameraman());
				dto.setShotAddress(topic.getShotAddress());
				for (Iterator it = photoworksSet.iterator(); it.hasNext();) {
					PhtotoGraphyWroks photo = (PhtotoGraphyWroks) it.next();
					String photourl = photo.getBreviaryUrl();
					if (StringUtils.isNotEmpty(photourl)) {
						dto.setUrl(photourl);
						break;
					}

				}

				topicDtoList.add(dto);

			}

		} catch (Exception ex) {
			throw ex;
		}

		return topicDtoList;

	}

	public StudioInfo getStudioObject() throws Exception {
		String hql = " from StudioInfo p  ";
		StudioInfo studio = (StudioInfo) dao.getUniqueObjectByHql(hql);
		return studio;

	}

	public Employee getEmployeeInfo(Long id) throws Exception {

		String sql = " from Employee p where p.id ='" + id + "' ";
		Employee em = (Employee) dao.getUniqueObjectByHql(sql);
		return em;

	}

	public List<EmployWorksTopic> getphotosByEmployeePhotoTopic(Long id)
			throws Exception {

		String hql = " from EmployWorksTopic p where p.employee.id = '" + id
				+ "'  order by p.id desc";
		List<EmployWorksTopic> photoworks = dao.getListByHql(hql);
		return photoworks;

	}

	/**
	 * 查询微电影
	 * 
	 * @param page
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public List<VideoInfo> getMicrofilmListPage(Page page, String type)
			throws Exception {
		StringBuilder hql = new StringBuilder();
		hql.append("from VideoInfo t ");
		List params = new ArrayList();
		hql.append(" where 1=1");
		if (StringUtils.isNotEmpty(type)) {

			hql.append(" and t.type= '" + type + "' ");
		}

		hql.append(" order by t.uploadTime desc ");
		return dao.query(hql.toString(), page, params.toArray());
	}

	/**
	 * 获取最新微电影对象
	 * 
	 * @return
	 * @throws Exception
	 */
	public VideoInfo getVideoInfo() throws Exception {

		String sql = " from VideoInfo p order by p.uploadTime desc ";
		VideoInfo film = (VideoInfo) dao.getUniqueObjectByHql(sql);
		return film;

	}

	/**
	 * 根据类型获取微电影对象
	 * 
	 * @return
	 * @throws Exception
	 */
	public VideoInfo getVideoInfo(String type) throws Exception {

		String sql = " from VideoInfo p where p.type = '" + type
				+ "' order by p.uploadTime desc ";
		VideoInfo film = (VideoInfo) dao.getUniqueObjectByHql(sql);
		return film;

	}

	/**
	 * 根据类型获取微电影信息
	 */
	public List<VideoInfo> getFilmListByType(String type) throws Exception {

		List<VideoInfo> filmList;
		Session session = dao.getHibernateSession();
		try {

			Query q = session.createQuery(" from VideoInfo p where  p.type = '"
					+ type + "' order by p.uploadTime desc ");
			q.setFirstResult(0);
			q.setMaxResults(5);

			filmList = (List<VideoInfo>) q.list();
		} catch (Exception ex) {
			throw ex;
		} finally {
			session.clear();
			session.close();
		}

		return filmList;

	}

	/**
	 * 获取最新的微电影
	 */
	public List<VideoInfo> getFilmListByType() throws Exception {

		List<VideoInfo> filmList;
		Session session = dao.getHibernateSession();
		try {

			Query q = session
					.createQuery(" from VideoInfo p  order by p.uploadTime desc ");
			q.setFirstResult(0);
			q.setMaxResults(5);

			filmList = (List<VideoInfo>) q.list();
		} catch (Exception ex) {
			throw ex;
		} finally {
			session.clear();
			session.close();
		}

		return filmList;

	}

	/**
	 * 获取最新团购
	 */
	public List<GroupItemsInfo> getTuanListByType() throws Exception {

		List<GroupItemsInfo> groupitem;
		Session session = dao.getHibernateSession();
		try {

			Query q = session
					.createQuery(" from GroupItemsInfo p  order by p.id desc ");
			q.setFirstResult(0);
			q.setMaxResults(4);

			groupitem = (List<GroupItemsInfo>) q.list();
		} catch (Exception ex) {
			throw ex;
		} finally {
			session.clear();
			session.close();
		}

		return groupitem;

	}

	/**
	 * 获取最新的微电影
	 */
	public List<VideoInfo> getFilmListByType2() throws Exception {

		List<VideoInfo> filmList;
		Session session = dao.getHibernateSession();
		try {

			Query q = session
					.createQuery(" from VideoInfo p  order by p.uploadTime desc ");
			q.setFirstResult(0);
			q.setMaxResults(2);

			filmList = (List<VideoInfo>) q.list();
		} catch (Exception ex) {
			throw ex;
		} finally {
			session.clear();
			session.close();
		}

		return filmList;

	}

	/**
	 * 获取首页新闻
	 */
	public List<News> getallnews() throws Exception {

		List<News> newsinfo;
		Session session = dao.getHibernateSession();
		try {

			Query q = session
					.createQuery(" from News n  order by n.releaseTime desc ");
			q.setFirstResult(0);
			q.setMaxResults(7);

			newsinfo = (List<News>) q.list();
		} catch (Exception ex) {
			throw ex;
		} finally {
			session.clear();
			session.close();
		}

		return newsinfo;
	}

	public News getNewsInfoObject(Long id) throws Exception {

		String sql = " from News p where p.id ='" + id + "' ";
		News news = (News) dao.getUniqueObjectByHql(sql);
		return news;
	}

	/**
	 * 查询分页新闻列表
	 * 
	 * @param page
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public List<News> getNewsInfoListPage(Page page) throws Exception {
		StringBuilder hql = new StringBuilder();
		hql.append("from News t where status = '1'");
		List params = new ArrayList();
		hql.append(" order by t.releaseTime desc ");
		return dao.query(hql.toString(), page, params.toArray());
	}

	/**
	 * 查询分页团购列表
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<GroupItemsInfo> getGroupitemInfoListPage(Page page)
			throws Exception {
		StringBuilder hql = new StringBuilder();
		hql.append("from GroupItemsInfo t ");
		List params = new ArrayList();
		hql.append(" order by t.id desc ");
		return dao.query(hql.toString(), page, params.toArray());

	}

	/**
	 * 获取团购对象
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public GroupItemsInfo getGroupItemObject(Long id) throws Exception {

		String sql = " from GroupItemsInfo p where p.id ='" + id + "' ";
		GroupItemsInfo groupitem = (GroupItemsInfo) dao
				.getUniqueObjectByHql(sql);
		return groupitem;
	}
	
	
	
	

}
