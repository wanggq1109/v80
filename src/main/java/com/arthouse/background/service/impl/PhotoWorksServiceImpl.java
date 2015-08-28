package com.arthouse.background.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;

import com.arthouse.background.service.PhotoWorksService;
import com.arthouse.common.dao.ICommonDao;
import com.arthouse.common.domain.EmployWorksTopic;
import com.arthouse.common.domain.EmployeePhotos;
import com.arthouse.common.domain.PhototTopic;
import com.arthouse.common.domain.PhtotoGraphyWroks;
import com.arthouse.common.page.Page;

/**
 * 摄影作品业务层
 * 
 * @author wgq
 * 
 */
public class PhotoWorksServiceImpl implements PhotoWorksService {

	private ICommonDao dao;

	public ICommonDao getDao() {
		return dao;
	}

	public void setDao(ICommonDao dao) {
		this.dao = dao;
	}

	/**
	 * 根据类型查询摄影作品
	 * 
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public List<PhtotoGraphyWroks> getPhotoWorksDataByType(Page page, Long id)
			throws Exception {
		List params = new ArrayList();
		StringBuilder hql = new StringBuilder();

		hql.append(" from PhtotoGraphyWroks  p   ").append(" where 1=1");
		// hql.append(" and p.photoType= ?");
		hql.append(" and p.phototdesc.id= ? order by id desc");
		params.add(id);

		return dao.query(hql.toString(), page, params.toArray());
	}

	public List<PhtotoGraphyWroks> getPhotoWorksDataByCode(Page page,
			String code) throws Exception {

		List params = new ArrayList();
		StringBuilder hql = new StringBuilder();

		hql.append(" from PhtotoGraphyWroks  p   ").append(" where 1=1");
		hql.append(" and p.photoType= ? order by p.id desc ");
		params.add(code);

		return dao.query(hql.toString(), page, params.toArray());

	}

	/**
	 * 存储摄影作品信息
	 * 
	 * @throws Exception
	 */
	public void savePhotoWorksInfo(PhtotoGraphyWroks photoWorks)
			throws Exception {

		dao.save(photoWorks);
	}

	/**
	 * 保存照片组描述
	 * 
	 * @throws Exception
	 */
	public void savePhotoGroupDesc(PhototTopic photodesc) throws Exception {

		dao.save(photodesc);

	}

	/**
	 * 更新照片组描述
	 * 
	 * @throws Exception
	 */
	public void UpdatePhotoGroupDesc(PhototTopic photodesc) throws Exception {

		dao.update(photodesc);

	}

	/**
	 * 获取摄影作品对象
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public PhtotoGraphyWroks getPhotoWroks(Long id) throws Exception {

		String sql = " from PhtotoGraphyWroks p where p.id ='" + id + "' ";
		PhtotoGraphyWroks photoworks = (PhtotoGraphyWroks) dao
				.getUniqueObjectByHql(sql);
		return photoworks;

	}

	/**
	 * 获取摄影作品描述
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public PhototTopic getPhotoWroksDesc(String code) throws Exception {

		String sql = " from PhototTopic p where p.photoTypeCode ='" + code
				+ "' ";
		PhototTopic photoworksdesc = (PhototTopic) dao
				.getUniqueObjectByHql(sql);
		return photoworksdesc;

	}

	/**
	 * 获取摄影作品对象
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public PhtotoGraphyWroks getPhotoWroks(String code) throws Exception {

		String sql = " from PhtotoGraphyWroks p where p.photoType ='" + code
				+ "' ";
		PhtotoGraphyWroks photowork = (PhtotoGraphyWroks) dao
				.getUniqueObjectByHql(sql);
		return photowork;

	}

	/**
	 * 获取摄影作品对象
	 * 
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public List<PhtotoGraphyWroks> getPhtotoGraphyWroksByCode(String code)
			throws Exception {

		String hql = " from PhtotoGraphyWroks p where p.photoType ='" + code
				+ "' ";
		List<PhtotoGraphyWroks> photoworks = dao.getListByHql(hql);
		return photoworks;

	}

	/**
	 * 更新
	 * 
	 * @param photoworks
	 * @throws Exception
	 */
	public void updatePhotoWorks(PhtotoGraphyWroks photoworks) throws Exception {

		dao.update(photoworks);

	}

	/**
	 * 文件重命名yyyyMMddHHMMss+4位随机数
	 */
	public String getPhotoRandom() {

		Random ran = new Random();
		int r = 0;
		m1: while (true) {
			int n = ran.nextInt(10000);
			r = n;
			int[] bs = new int[4];
			for (int i = 0; i < bs.length; i++) {
				bs[i] = n % 10;
				n /= 10;
			}
			Arrays.sort(bs);
			for (int i = 1; i < bs.length; i++) {
				if (bs[i - 1] == bs[i]) {
					continue m1;
				}
			}
			break;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String photoname = sdf.format(new Date());

		return photoname + String.valueOf(r);

	}

	/**
	 * 删除摄影作品
	 * 
	 * @throws Exception
	 */
	public void PhotoWorksDelete(String[] ids) throws Exception {

		String hql = "delete from  PhtotoGraphyWroks  WHERE id in (:ids)";

		dao.updTobatch(ids, hql);

	}

	/**
	 * 查询一组摄影作品
	 * 
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	public List<PhtotoGraphyWroks> getPhotoWorksList(String[] ids)
			throws Exception {

		Session session = dao.getHibernateSession();
		String sql = "select p.photoUrl,p.breviaryUrl "
				+ "from web_phtotographywroks p where p.id in(:id)";
		List<PhtotoGraphyWroks> list = session.createSQLQuery(sql)
				.setParameterList("id", ids).list();

		session.clear();
		session.close();
		return list;

	}

	public void save(Object obj) {
		try {
			dao.save(obj);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void update(Object obj) {
		try {
			dao.update(obj);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public PhototTopic getPhotoTopicInfo(Long id) throws Exception {

		String sql = " from PhototTopic p where p.id ='" + id + "' ";
		PhototTopic t = (PhototTopic) dao.getUniqueObjectByHql(sql);
		return t;

	}

	@SuppressWarnings("unchecked")
	public List<PhototTopic> getPhotoTopicData(Page page, String code)
			throws Exception {

		List params = new ArrayList();
		StringBuilder hql = new StringBuilder();
		hql.append(" from PhototTopic  t where 1=1 ");
		hql.append(" and t.photoTypeCode= ? order by t.time desc ");
		params.add(code);

		List<PhototTopic> list = dao.query(hql.toString(), page,
				params.toArray());

		return list;

	}
	
	public List<PhtotoGraphyWroks> getTopicPhotosById(Long id)throws Exception{
		
		String hql=" from PhtotoGraphyWroks p where p.phototdesc.id ='"+id+"' ";
		List<PhtotoGraphyWroks> photoworks = dao.getListByHql(hql);
        return photoworks;
	}
	
	/**
	 * 设置作品封面
	 * @param photoswork
	 */
	public void updatefacephotos(PhtotoGraphyWroks photoswork){
		
		PhototTopic topic =	photoswork.getPhototdesc();
		topic.setFaceurl(photoswork.getBreviaryUrl());
		try {
			dao.update(topic);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	

	public void delete(Object obj) {

		try {
			dao.delete(obj);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
