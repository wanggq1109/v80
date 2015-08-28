package com.arthouse.common.domain;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 员工作品列表信息
 * 
 * @author wanggq
 *
 */

@javax.persistence.Entity
@Table(name = "web_employphotos")
public class EmployeePhotos {
	
	private Long id;
	
	private String name;
	
	private String url;
	
	private String suolue_url;
	
	private Date uploadTime;
	
	private EmployWorksTopic employworkstopic;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getUrl() {
		return url;
	}

	public String getSuolue_url() {
		return suolue_url;
	}

	public Date getUploadTime() {
		return uploadTime;
	}
	
	@ManyToOne
	@JoinColumn(name = "employworkstopic_id")
	public EmployWorksTopic getEmployworkstopic() {
		return employworkstopic;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setSuolue_url(String suolue_url) {
		this.suolue_url = suolue_url;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	

	public void setEmployworkstopic(EmployWorksTopic employworkstopic) {
		this.employworkstopic = employworkstopic;
	}
	
	
	
}
