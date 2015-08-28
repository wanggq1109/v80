package com.arthouse.common.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

/**
 * 
 * 
 * @author wgq
 * 
 *         照片主题描述
 * 
 */
@javax.persistence.Entity
@Table(name = "web_photoTopic")
public class PhototTopic {

	private Long id;

	// 照片组主题
	private String title;

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

	// 新增主题时间
	private Date time;
	
	private String faceurl;

	// 摄影作品

	private Set<PhtotoGraphyWroks> photoworks = new HashSet<PhtotoGraphyWroks>();

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public String getShotAddress() {
		return shotAddress;
	}

	public Date getShotTime() {
		return shotTime;
	}

	public String getCameraman() {
		return cameraman;
	}

	public String getStylists() {
		return stylists;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setShotAddress(String shotAddress) {
		this.shotAddress = shotAddress;
	}

	public void setShotTime(Date shotTime) {
		this.shotTime = shotTime;
	}

	public void setCameraman(String cameraman) {
		this.cameraman = cameraman;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@OneToMany(mappedBy = "phototdesc", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
	@OrderBy(value = "id desc")
	public Set<PhtotoGraphyWroks> getPhotoworks() {
		return photoworks;
	}

	public void setPhotoworks(Set<PhtotoGraphyWroks> photoworks) {
		this.photoworks = photoworks;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getFaceurl() {
		return faceurl;
	}

	public void setFaceurl(String faceurl) {
		this.faceurl = faceurl;
	}
	
	

}
