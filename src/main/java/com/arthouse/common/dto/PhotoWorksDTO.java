package com.arthouse.common.dto;

import java.util.Date;

/**
 * 摄影作品DTO
 * 
 * @author wgq
 *
 */
public class PhotoWorksDTO {
	
	private String id;
	
	//照片类型
	private String photoType;
	
	//原图名称
	private String photoName;
	
	//缩略图名称
	private String breviaryName;
	
	//原图上传时间
	private Date uploadTime;
	
	//缩略图上传时间
	private Date breviaryUploadTime;
	
	//发布状态
	private String releaseStatus;
	
	//url
	private String photoUrl;
	
	//照片组主题
	private String phototitle;
	
	//壹周推荐状态
	private String weekrecommend;
	
	private String suolueurl;
	
	private String pageCount;
	
	
	

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public String getPhototitle() {
		return phototitle;
	}

	public void setPhototitle(String phototitle) {
		this.phototitle = phototitle;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPhotoType() {
		return photoType;
	}

	public void setPhotoType(String photoType) {
		this.photoType = photoType;
	}

	public String getPhotoName() {
		return photoName;
	}

	public void setPhotoName(String photoName) {
		this.photoName = photoName;
	}

	public String getBreviaryName() {
		return breviaryName;
	}

	public void setBreviaryName(String breviaryName) {
		this.breviaryName = breviaryName;
	}

	public Date getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	public Date getBreviaryUploadTime() {
		return breviaryUploadTime;
	}

	public void setBreviaryUploadTime(Date breviaryUploadTime) {
		this.breviaryUploadTime = breviaryUploadTime;
	}

	public String getReleaseStatus() {
		return releaseStatus;
	}

	public void setReleaseStatus(String releaseStatus) {
		this.releaseStatus = releaseStatus;
	}

	public String getWeekrecommend() {
		return weekrecommend;
	}

	public void setWeekrecommend(String weekrecommend) {
		this.weekrecommend = weekrecommend;
	}

	public String getSuolueurl() {
		return suolueurl;
	}

	public void setSuolueurl(String suolueurl) {
		this.suolueurl = suolueurl;
	}

	public String getPageCount() {
		return pageCount;
	}

	public void setPageCount(String pageCount) {
		this.pageCount = pageCount;
	}
	
	
	
}
