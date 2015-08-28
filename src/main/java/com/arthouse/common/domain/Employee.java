package com.arthouse.common.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 
 * 员工信息
 * 
 * @author wanggq
 *
 */
@javax.persistence.Entity
@Table(name = "web_employeeInfo")
public class Employee {
	
	private Long id;
	
	private String name;
	
	private String position;
	
	private String intro;
	
	private String experience;
	
	private String photoUrl;
	
	private StudioInfo studioInfo;


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getPosition() {
		return position;
	}

	public String getIntro() {
		return intro;
	}

	public String getExperience() {
		return experience;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public void setExperience(String experience) {
		this.experience = experience;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	@ManyToOne
	@JoinColumn(name = "STUDIOINFO_ID")
	public StudioInfo getStudioInfo() {
		return studioInfo;
	}

	public void setStudioInfo(StudioInfo studioInfo) {
		this.studioInfo = studioInfo;
	}
	
	
	

}
