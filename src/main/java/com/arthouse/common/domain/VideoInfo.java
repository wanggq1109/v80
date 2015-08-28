package com.arthouse.common.domain;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 微电影
 * @author wanggq
 *
 */
@javax.persistence.Entity
@Table(name = "web_videoInfo")
public class VideoInfo {
	
	private Long id ;
	
	/**
	 * 视频名称
	 */
	private String name;
	
	/**
	 * 视频内容简介
	 */
	private String intro;
	
	/**
	 * 视频上传时间
	 */
	private Date uploadTime;
	
	/**
	 * 发布状态
	 */
	private String status;
	
	/**
	 * 路径
	 */
	private String url;
	
	//视频缩略图
	private String suolueurl;
	
	//点击次数
	private int count;
	
	//视频类型 0普通微电影；1花絮；
	private String type;
	
	//拍摄时间
	private Date shootingDate;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getIntro() {
		return intro;
	}

	public Date getUploadTime() {
		return uploadTime;
	}

	public String getStatus() {
		return status;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSuolueurl() {
		return suolueurl;
	}

	public void setSuolueurl(String suolueurl) {
		this.suolueurl = suolueurl;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getShootingDate() {
		return shootingDate;
	}

	public void setShootingDate(Date shootingDate) {
		this.shootingDate = shootingDate;
	}
	 
	
	
	
}
