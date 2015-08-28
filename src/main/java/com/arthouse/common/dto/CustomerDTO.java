package com.arthouse.common.dto;

import java.util.Date;

public class CustomerDTO {
	
	
	private Long id;
	
	//创建时间
	private Date createTime;
	
	//新人名称
	private String customerName;
	
	//拍摄日期
	private Date shootTime;
	
	//描述
	private String remark;
	
	//封面
	private String url;
	
	//照片组数量
	private String photoSize;
	
	private String cDate;
	
	private String cMonth;

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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPhotoSize() {
		return photoSize;
	}

	public void setPhotoSize(String photoSize) {
		this.photoSize = photoSize;
	}

	public String getcDate() {
		return cDate;
	}

	public void setcDate(String cDate) {
		this.cDate = cDate;
	}

	public String getcMonth() {
		return cMonth;
	}

	public void setcMonth(String cMonth) {
		this.cMonth = cMonth;
	}
	
	
	
}
