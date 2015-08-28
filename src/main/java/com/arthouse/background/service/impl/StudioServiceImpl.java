package com.arthouse.background.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.arthouse.background.service.StudioService;
import com.arthouse.common.dao.ICommonDao;
import com.arthouse.common.domain.EmployWorksTopic;
import com.arthouse.common.domain.Employee;
import com.arthouse.common.domain.EmployeePhotos;
import com.arthouse.common.domain.Recruiment;
import com.arthouse.common.domain.StudioInfo;
import com.arthouse.common.domain.StudioInfofw;
import com.arthouse.common.domain.StudioPhoto;
import com.arthouse.common.dto.EmployeePhotosDTO;
import com.arthouse.common.page.Page;

public class StudioServiceImpl implements StudioService {

	private ICommonDao dao;

	public ICommonDao getDao() {
		return dao;
	}

	public void setDao(ICommonDao dao) {
		this.dao = dao;
	}

	public void save(StudioInfo studio) throws Exception {

		dao.save(studio);

	}

	public void saveStudio_photo(StudioPhoto studioPhoto) throws Exception {

		dao.persist(studioPhoto);

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<StudioPhoto> getStudioPhotoData(Page page) throws Exception {

		List params = new ArrayList();
		StringBuilder hql = new StringBuilder();
		hql.append(" from StudioPhoto  s  order by s.uploadTime desc");
		List<StudioPhoto> list = dao.query(hql.toString(), page,
				params.toArray());

		return list;

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<StudioInfofw> getStudioInfoData(Page page) throws Exception {

		List params = new ArrayList();
		StringBuilder hql = new StringBuilder();
		hql.append(" from StudioInfofw  s  ");
		List<StudioInfofw> list = dao.query(hql.toString(), page,
				params.toArray());

		return list;

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Employee> getEmployeeInfoData(Page page) throws Exception {

		List params = new ArrayList();
		StringBuilder hql = new StringBuilder();
		hql.append(" from Employee  s  ");
		List<Employee> list = dao.query(hql.toString(), page, params.toArray());

		return list;

	}

	public void save(StudioInfofw fw) throws Exception {

		dao.save(fw);
	}

	public void save_employee(Employee em) throws Exception {

		dao.save(em);

	}

	public StudioInfo getStudioInfo() throws Exception {
		String hql = " from StudioInfo p  ";
		List list = dao.getListByHql(hql);
		if (list.size() > 0) {
			StudioInfo StudioInfo = (StudioInfo) list.get(0);
			return StudioInfo;

		} else {
			return null;
		}

	}

	public void update(StudioInfofw fw) throws Exception {

		dao.update(fw);
	}

	public StudioInfofw getstudioInfoFw(Long id) throws Exception {

		String sql = " from StudioInfofw p where p.id ='" + id + "' ";
		StudioInfofw fw = (StudioInfofw) dao.getUniqueObjectByHql(sql);
		return fw;

	}

	public void delete(String[] id) throws Exception {

		String hql = "delete from  StudioInfofw  WHERE id in (:ids)";

		dao.updTobatch(id, hql);

	}
	
	public void deletePhotostduioPhoto(String[] id) throws Exception {

		String hql = "delete from  StudioPhoto  WHERE id in (:ids)";

		dao.updTobatch(id, hql);

	}

	public Employee getEmployeeInfo(Long id) throws Exception {

		String sql = " from Employee p where p.id ='" + id + "' ";
		Employee em = (Employee) dao.getUniqueObjectByHql(sql);
		return em;

	}

	public void delete_employee(String[] id) throws Exception {

		String hql = "delete from  Employee  WHERE id in (:ids)";

		dao.updTobatch(id, hql);

	}

	public void update_employee(Employee em) throws Exception {

		dao.update(em);
	}

	public Recruiment getRecruimentInfo(Long id) throws Exception {
		String sql = " from Recruiment p where p.id ='" + id + "' ";
		Recruiment rm = (Recruiment) dao.getUniqueObjectByHql(sql);
		return rm;
	}

	public void save_recruiment(Recruiment rm) throws Exception {

		dao.save(rm);
	}

	public void delete_recruiment(String[] id) throws Exception {

		String hql = "delete from  Recruiment  WHERE id in (:ids)";

		dao.updTobatch(id, hql);
	}

	public void update_recruiment(Recruiment rm) throws Exception {

		dao.update(rm);
	}

	@SuppressWarnings("unchecked")
	public List<Recruiment> getRecruimentInfoData(Page page) throws Exception {

		List params = new ArrayList();
		StringBuilder hql = new StringBuilder();
		hql.append(" from Recruiment  s  ");
		List<Recruiment> list = dao.query(hql.toString(), page,
				params.toArray());

		return list;

	}
	
	@SuppressWarnings("unchecked")
	public List<EmployWorksTopic> getPhotoTopicData(Page page) throws Exception {

		List params = new ArrayList();
		StringBuilder hql = new StringBuilder();
		hql.append(" from EmployWorksTopic  t  ");
		List<EmployWorksTopic> list = dao.query(hql.toString(), page,
				params.toArray());

		return list;

	}
	
	public EmployWorksTopic getPhotoTopicInfo(Long id) throws Exception{
		
		String sql = " from EmployWorksTopic p where p.id ='" + id + "' ";
		EmployWorksTopic t = (EmployWorksTopic) dao.getUniqueObjectByHql(sql);
		return t;
		
	}

	public void save_photoTopic(EmployWorksTopic t) throws Exception{
		
		dao.save(t);
	}

	public void delete_photoTopic(String[] id) throws Exception{
		
		String hql = "delete from  EmployWorksTopic  WHERE id in (:ids)";

		dao.updTobatch(id, hql);
		
	}

	public void update_photoTopic(EmployWorksTopic t) throws Exception{
		
		dao.update(t);
		
	}
	
	public void save_employeePhotos(EmployeePhotos em)throws Exception{
		
		dao.save(em);
		
	}
	
	public List<EmployeePhotos> getEmployeePhotosById(Long id)throws Exception{
		
		String hql=" from EmployeePhotos p where p.employworkstopic.id ='"+id+"' ";
		List<EmployeePhotos> photoworks = dao.getListByHql(hql);
        return photoworks;
	}

	/**
	 * 显示备选主题
	 */
	public List<EmployeePhotosDTO> getEmployeeTopicSelectById(Long id)
			throws Exception {
		Session session = dao.getHibernateSession();
		List<EmployeePhotosDTO> list = new ArrayList<EmployeePhotosDTO>();
		try {

			Query q = session
					.createSQLQuery(" SELECT  t.id,t.name from web_employworkstopic t where t.status = '0' ");
			if (q.list().size() > 0) {
				Iterator it = q.list().iterator();
				while (it.hasNext()) {
					Object[] obj = (Object[]) it.next();
					EmployeePhotosDTO dto = new EmployeePhotosDTO();
					dto.setId((BigInteger) obj[0]);
					dto.setName((String) obj[1]);
					list.add(dto);
				}

			}
		} catch (Exception ex) {
			throw ex;
		} finally {
			session.clear();
			session.close();
		}

		return list;

	}
	
	/**
	 * 显示已选择主题
	 */
	public List<EmployeePhotosDTO> getEmployeeTopicSelectedById(Long id)throws Exception{
		
		Session session = dao.getHibernateSession();
		List<EmployeePhotosDTO> list = new ArrayList<EmployeePhotosDTO>();
		try {

			Query q = session
					.createSQLQuery(" SELECT  t.id,t.name from web_employworkstopic t where t.status = '1' and t.employee_id = '"+id+"'");
			if (q.list().size() > 0) {
				Iterator it = q.list().iterator();
				while (it.hasNext()) {
					Object[] obj = (Object[]) it.next();
					EmployeePhotosDTO dto = new EmployeePhotosDTO();
					dto.setId((BigInteger) obj[0]);
					dto.setName((String) obj[1]);
					list.add(dto);
				}

			}
		} catch (Exception ex) {
			throw ex;
		} finally {
			session.clear();
			session.close();
		}

		return list;

	}
	
	public List<EmployWorksTopic> getTopicByEmployeeList(Long employeeId)throws Exception{
		
		String hql=" from EmployWorksTopic p where p.employee.id ='"+employeeId+"' ";
		List<EmployWorksTopic> topiList = dao.getListByHql(hql);
        return topiList;
		
		
	}

}
