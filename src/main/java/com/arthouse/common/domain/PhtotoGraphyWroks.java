package com.arthouse.common.domain;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 
 * 
 * 
 * @author wgq
 * 
 *         摄影作品/服饰赏析
 * 
 */
@javax.persistence.Entity
@Table(name = "web_phtotographywroks")
public class PhtotoGraphyWroks {

	private Long id;

	// 大图名称
	private String photoName;
	// 大图路径
	private String photoUrl;

	// 大图上传时间
	private Date uploadTime;

	// 缩略 图名称
	private String breviaryName;

	// 缩略图路径
	private String breviaryUrl;

	// 缩略图上传时间
	private Date breviaryUploadTime;

	// 上传图片类型
	private String photoType;
	
	//发布状态 0,未发布；1，已发布
	private String releaseStatus;
	
	//作品主题
	private PhototTopic phototdesc;
	
	//一周作品推荐  值为1被设置为推荐作品
	private String weekWorkRecommend;


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public String getPhotoName() {
		return photoName;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public Date getUploadTime() {
		return uploadTime;
	}

	public String getBreviaryName() {
		return breviaryName;
	}

	public String getBreviaryUrl() {
		return breviaryUrl;
	}

	public Date getBreviaryUploadTime() {
		return breviaryUploadTime;
	}

	public String getPhotoType() {
		return photoType;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setPhotoName(String photoName) {
		this.photoName = photoName;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	public void setBreviaryName(String breviaryName) {
		this.breviaryName = breviaryName;
	}

	public void setBreviaryUrl(String breviaryUrl) {
		this.breviaryUrl = breviaryUrl;
	}

	public void setBreviaryUploadTime(Date breviaryUploadTime) {
		this.breviaryUploadTime = breviaryUploadTime;
	}

	public void setPhotoType(String photoType) {
		this.photoType = photoType;
	}

	public String getReleaseStatus() {
		return releaseStatus;
	}

	public void setReleaseStatus(String releaseStatus) {
		this.releaseStatus = releaseStatus;
	}
	
	@ManyToOne
	@JoinColumn(name = "phototdesc")
	public PhototTopic getPhototdesc() {
		return phototdesc;
	}

	public void setPhototdesc(PhototTopic phototdesc) {
		this.phototdesc = phototdesc;
	}

	public String getWeekWorkRecommend() {
		return weekWorkRecommend;
	}

	public void setWeekWorkRecommend(String weekWorkRecommend) {
		this.weekWorkRecommend = weekWorkRecommend;
	}

	
	
	
}
