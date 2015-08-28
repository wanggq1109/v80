package com.arthouse.common.dto;

import java.util.Date;

public class PhotoTopicDTO {

	private Long id;

	//主题
	private String name;

	// 拍摄地址
	private String shotAddress;

	// 拍摄时间
	private Date shotTime;

	// 摄影师
	private String cameraman;

	// 造型师
	private String stylists;

	// 照片组描述
	private String photTopicDesc;

	// 照片分类code
	private String photoTypeCode;
	
	//随机封面
	private String url;
	
	//设置封面照
	private String face_url;
	
	//当前主题有多少照片
	private String photosize;
	
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getShotAddress() {
		return shotAddress;
	}

	public void setShotAddress(String shotAddress) {
		this.shotAddress = shotAddress;
	}

	public Date getShotTime() {
		return shotTime;
	}

	public void setShotTime(Date shotTime) {
		this.shotTime = shotTime;
	}

	public String getCameraman() {
		return cameraman;
	}

	public void setCameraman(String cameraman) {
		this.cameraman = cameraman;
	}

	public String getStylists() {
		return stylists;
	}

	public void setStylists(String stylists) {
		this.stylists = stylists;
	}

	public String getPhotTopicDesc() {
		return photTopicDesc;
	}

	public void setPhotTopicDesc(String photTopicDesc) {
		this.photTopicDesc = photTopicDesc;
	}

	public String getPhotoTypeCode() {
		return photoTypeCode;
	}

	public void setPhotoTypeCode(String photoTypeCode) {
		this.photoTypeCode = photoTypeCode;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPhotosize() {
		return photosize;
	}

	public void setPhotosize(String photosize) {
		this.photosize = photosize;
	}

	public String getFace_url() {
		return face_url;
	}

	public void setFace_url(String face_url) {
		this.face_url = face_url;
	}

	
	
}
