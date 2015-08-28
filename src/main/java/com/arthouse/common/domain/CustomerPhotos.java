package com.arthouse.common.domain;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 客片分享
 * @author wgq
 *
 */
@javax.persistence.Entity
@Table(name = "web_customerPhotos")
public class CustomerPhotos {
	
	private Long id;
	
	//原图
	private String url;
	
	//缩略图
	private String  sl_url;
	
	//封面标帜
	private String mark;
	
	//上传时间
	private Date uploadTime;
	
	
	//描述
	private String remark;
	
	//顾客信息
	private Customer customer;

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public Date getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}


	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@ManyToOne
	@JoinColumn(name = "customer")
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	
	

}
