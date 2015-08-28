package com.arthouse.common.domain;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 团购订单
 * @author wanggq
 *
 */
@javax.persistence.Entity
@Table(name = "web_groupOrder")
public class GroupOrder {
	
	private Long id;
	/**
	 * 客户真实姓名
	 */
	private String name;
	
	/**
	 * 手机号码
	 */
	private String phoneNo;
	
	/**
	 * 通讯qq号码
	 */
	private String qq;
	
	/**
	 * 备注
	 */
	private String remark;
	
	/**
	 * 订单提交时间
	 */
	private Date orderTime;
	
	/**
	 * 订单号
	 */
	private String orderNo;
	
	
	/**
	 * 所选套餐名称
	 */
	private String itemsName;
	
	/**
	 * 套餐价格
	 */
	private Double  price;

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public String getQq() {
		return qq;
	}

	public String getRemark() {
		return remark;
	}

	public Date getOrderTime() {
		return orderTime;
	}


	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public String getItemsName() {
		return itemsName;
	}

	public void setItemsName(String itemsName) {
		this.itemsName = itemsName;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	
	
	
	

}
