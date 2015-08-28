package com.arthouse.common.dto;

import java.util.Date;

public class CustomerPhotoDTO {
	
	private Long id;
	
	//原图
	private String url;
	
	//缩略图
	private String  sl_url;
	
	
	//上传时间
	private Date uploadTime;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public String getSl_url() {
		return sl_url;
	}


	public void setSl_url(String sl_url) {
		this.sl_url = sl_url;
	}


	public Date getUploadTime() {
		return uploadTime;
	}


	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}
	
	

}
