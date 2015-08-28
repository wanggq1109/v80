package com.arthouse.common.domain;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 工作室室介绍关联图片
 * @author wanggq
 *
 */

@javax.persistence.Entity
@Table(name = "web_studio_photo")
public class StudioPhoto {
	
	private Long id;
	
	private String picName;
	
	private String url;
	
	private Date uploadTime;
	
	private StudioInfo studioInfo;


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}
	
	@ManyToOne
	@JoinColumn(name = "STUDIOINFO_ID")
	public StudioInfo getStudioInfo() {
		return studioInfo;
	}


	public String getPicName() {
		return picName;
	}

	public String getUrl() {
		return url;
	}

	public Date getUploadTime() {
		return uploadTime;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setPicName(String picName) {
		this.picName = picName;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	
	public void setStudioInfo(StudioInfo studioInfo) {
		this.studioInfo = studioInfo;
	}
	
	

}
