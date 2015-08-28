/**
 * 
 */
package com.arthouse.common.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 * @author tyl
 *
 * @version 2011-12-26上午10:18:20
 * 
 * 日志表
 */
@javax.persistence.Entity
@Table(name ="systemlog")
public class SystemLog {
	private long id;
	
	private  String userAccount;//账号
	
	private String userName;//用户名
	
	private Date date;
	
	private String module_name;//模块名称
	
	private String operation;//操作

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long getId() {
		return id;
	}


	public Date getDate() {
		return date;
	}

	@Column(name="module_name")
	public String getModule_name() {
		return module_name;
	}

	@Column(name="operation")
	public String getOperation() {
		return operation;
	}

	public void setId(long id) {
		this.id = id;
	}


	public void setDate(Date date) {
		this.date = date;
	}

	public void setModule_name(String moduleName) {
		module_name = moduleName;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}


	public String getUserAccount() {
		return userAccount;
	}


	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	

}
