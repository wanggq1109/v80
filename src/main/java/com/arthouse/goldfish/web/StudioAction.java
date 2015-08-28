package com.arthouse.goldfish.web;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.arthouse.base.web.BaseAction;
import com.arthouse.common.domain.EmployWorksTopic;
import com.arthouse.common.domain.Employee;
import com.arthouse.common.domain.EmployeePhotos;
import com.arthouse.common.domain.StudioInfo;
import com.arthouse.common.domain.StudioPhoto;
import com.arthouse.goldfish.service.IndexService;

public class StudioAction extends BaseAction {

	private IndexService indexService;
	
	private StudioInfo studio;
	
	private String image ="";
	
	Set<Employee>  collectionEmployeePhoto;
	
	String topicImage = "";
	
	Employee employee;
	
	List<EmployWorksTopic> employeePhotoTopicList = new ArrayList<EmployWorksTopic>();

	

	public String studio() throws Exception {
		
		studio = indexService.getStudioObject();
		if(studio!=null){
			
			Set<StudioPhoto> collectionPhoto = studio.getStudioPhotoCollection();
			for (Iterator it = collectionPhoto.iterator(); it.hasNext();) {
				StudioPhoto photo = (StudioPhoto) it.next();
				image = photo.getUrl();
				if(StringUtils.isNotEmpty(image)){
					break;
				}
			}
		}
		
		return SUCCESS;
	}
	
	public String showallEmployee()throws Exception{
		studio = indexService.getStudioObject();
	    collectionEmployeePhoto	= studio.getEmployeePhotoCollection();
		
		return SUCCESS;
	}
	
	
	public String teamshow() throws NumberFormatException, Exception{
		
		String id = request.getParameter("id");
		employee = indexService.getEmployeeInfo(Long.parseLong(id));
		
		employeePhotoTopicList = indexService.getphotosByEmployeePhotoTopic(Long.parseLong(id));
		
		for(EmployWorksTopic topic :employeePhotoTopicList){
			
			Set<EmployeePhotos>  employeephotoscollection = topic.getEmployeephotoscollection();
			for (Iterator it = employeephotoscollection.iterator(); it.hasNext();) {
				
				EmployeePhotos photos = (EmployeePhotos) it.next();
				topicImage = photos.getSuolue_url();
				if(!topicImage.equals("")){
					break;
					
				}
				
			}
			
			
		}
		
		
		return SUCCESS;
	}

	public IndexService getIndexService() {
		return indexService;
	}

	public void setIndexService(IndexService indexService) {
		this.indexService = indexService;
	}

	public StudioInfo getStudio() {
		return studio;
	}

	public void setStudio(StudioInfo studio) {
		this.studio = studio;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Set<Employee> getCollectionEmployeePhoto() {
		return collectionEmployeePhoto;
	}

	public void setCollectionEmployeePhoto(Set<Employee> collectionEmployeePhoto) {
		this.collectionEmployeePhoto = collectionEmployeePhoto;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public List<EmployWorksTopic> getEmployeePhotoTopicList() {
		return employeePhotoTopicList;
	}

	public void setEmployeePhotoTopicList(
			List<EmployWorksTopic> employeePhotoTopicList) {
		this.employeePhotoTopicList = employeePhotoTopicList;
	}

	public String getTopicImage() {
		return topicImage;
	}

	public void setTopicImage(String topicImage) {
		this.topicImage = topicImage;
	}
	
	
	
}
