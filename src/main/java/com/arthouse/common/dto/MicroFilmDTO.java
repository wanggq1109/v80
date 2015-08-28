package com.arthouse.common.dto;

import java.util.Date;

public class MicroFilmDTO {
	
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
	private String shootingDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public Date getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	public String getStatus() {
		return status;
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

	public String getShootingDate() {
		return shootingDate;
	}

	public void setShootingDate(String shootingDate) {
		this.shootingDate = shootingDate;
	}
	
	
	

}
