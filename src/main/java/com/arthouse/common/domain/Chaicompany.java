package com.arthouse.common.domain;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.arthouse.common.dao.Entity;

/**
 * 
 * 公司
 *
 */

@javax.persistence.Entity
@Table(name ="web_chaicompany")
public class Chaicompany implements Entity<Long>{
	private Long id;
	/**
	 * 公司编号
	 */
	private String no;
	/**
	 * 公司名称
	 */
	private String name;	
	/**
	 * 公司名称拼音
	 */
	private String pinyin;	
	/**
	 * 公司负责人
	 */
	private String chief;	
	/**
	 * 公司地址
	 */
	private String address;
	/**
	 * 电话
	 */
	private String telephone;
	/**
	 * 手机
	 */
	private String mobile;	
	/**
	 * 传真
	 */
	private String fax;
	/**
	 * 信息部门联系人
	 */
	private String infoLineman;	
	/**
	 * 信息部门电话
	 */
	private String infoTelephone;	
	
	private String status;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}
	@Column(name="chai_no")
	public String getNo() {
		return no;
	}
	@Column(name="chai_name")
	public String getName() {
		return name;
	}
	@Column(name="chai_pinyin")
	public String getPinyin() {
		return pinyin;
	}
	@Column(name="chai_chief")
	public String getChief() {
		return chief;
	}
	@Column(name="chai_address")
	public String getAddress() {
		return address;
	}
	@Column(name="chai_telephone")
	public String getTelephone() {
		return telephone;
	}
	@Column(name="chai_mobile")
	public String getMobile() {
		return mobile;
	}
	@Column(name="chai_fax")
	public String getFax() {
		return fax;
	}
	@Column(name="chai_info_lineman")
	public String getInfoLineman() {
		return infoLineman;
	}
	@Column(name="chai_info_telephone")
	public String getInfoTelephone() {
		return infoTelephone;
	}
	
	
	public void setId(Long id) {
		this.id = id;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}
	public void setChief(String chief) {
		this.chief = chief;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public void setInfoLineman(String infoLineman) {
		this.infoLineman = infoLineman;
	}
	public void setInfoTelephone(String infoTelephone) {
		this.infoTelephone = infoTelephone;
	}
	
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	 
	
	

}
