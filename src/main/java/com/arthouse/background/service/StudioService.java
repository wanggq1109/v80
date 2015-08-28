package com.arthouse.background.service;

import java.util.List;

import com.arthouse.common.domain.EmployWorksTopic;
import com.arthouse.common.domain.Employee;
import com.arthouse.common.domain.EmployeePhotos;
import com.arthouse.common.domain.Recruiment;
import com.arthouse.common.domain.StudioInfo;
import com.arthouse.common.domain.StudioInfofw;
import com.arthouse.common.domain.StudioPhoto;
import com.arthouse.common.dto.EmployeePhotosDTO;
import com.arthouse.common.page.Page;

public interface StudioService {

	public void save(StudioInfo studio) throws Exception;

	public StudioInfo getStudioInfo() throws Exception;

	public void saveStudio_photo(StudioPhoto studioPhoto) throws Exception;

	public List<StudioPhoto> getStudioPhotoData(Page page) throws Exception;

	public List<StudioInfofw> getStudioInfoData(Page page) throws Exception;

	public List<Employee> getEmployeeInfoData(Page page) throws Exception;

	public void save(StudioInfofw fw) throws Exception;

	public void update(StudioInfofw fw) throws Exception;

	public StudioInfofw getstudioInfoFw(Long id) throws Exception;

	public void delete(String[] id) throws Exception;

	public Employee getEmployeeInfo(Long id) throws Exception;

	public void save_employee(Employee em) throws Exception;

	public void delete_employee(String[] id) throws Exception;

	public void update_employee(Employee em) throws Exception;

	public Recruiment getRecruimentInfo(Long id) throws Exception;

	public void save_recruiment(Recruiment rm) throws Exception;

	public void delete_recruiment(String[] id) throws Exception;

	public void update_recruiment(Recruiment em) throws Exception;

	public List<Recruiment> getRecruimentInfoData(Page page) throws Exception;
	
	public List<EmployWorksTopic> getPhotoTopicData(Page page) throws Exception;
	
	public EmployWorksTopic getPhotoTopicInfo(Long id) throws Exception;

	public void save_photoTopic(EmployWorksTopic t) throws Exception;

	public void delete_photoTopic(String[] id) throws Exception;

	public void update_photoTopic(EmployWorksTopic t) throws Exception;
	
	public void save_employeePhotos(EmployeePhotos em)throws Exception;
	
	public List<EmployeePhotos> getEmployeePhotosById(Long id)throws Exception;
	
	public List<EmployeePhotosDTO> getEmployeeTopicSelectById(Long id)throws Exception;

	public List<EmployeePhotosDTO> getEmployeeTopicSelectedById(Long id)throws Exception;
	
	public List<EmployWorksTopic> getTopicByEmployeeList(Long employeeId)throws Exception;
	
	public void deletePhotostduioPhoto(String[] id) throws Exception;
	
}
