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
 * 客人信息
 * @author wgq
 *
 */
@javax.persistence.Entity
@Table(name = "web_customer")
public class Customer {
	
	
	private Long id;
	
	//创建时间
	private Date createTime;
	
	//新人名称
	private String customerName;
	
	//拍摄日期
	private Date shootTime;
	
	//缩略图
	private String url;
	
	//封面
	private String face_url;
	
	//描述
	private String remark;
	
	//客人作品信息
	private Set<CustomerPhotos> photoworks = new HashSet<CustomerPhotos>();
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Date getShootTime() {
		return shootTime;
	}

	public void setShootTime(Date shootTime) {
		this.shootTime = shootTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@OneToMany(mappedBy = "customer", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
	@OrderBy(value = "id desc")
	public Set<CustomerPhotos> getPhotoworks() {
		return photoworks;
	}

	public void setPhotoworks(Set<CustomerPhotos> photoworks) {
		this.photoworks = photoworks;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getFace_url() {
		return face_url;
	}

	public void setFace_url(String face_url) {
		this.face_url = face_url;
	}
	
	
	


}
