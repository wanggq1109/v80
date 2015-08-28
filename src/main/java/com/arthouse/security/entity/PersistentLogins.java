package com.arthouse.security.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * 
 * 记住我"功能，采用持久化策略（将用户的登录信息存放在数据库表中）
 * 
 * @author barry.wang
 *
 */
@javax.persistence.Entity
@org.hibernate.annotations.Entity(dynamicUpdate = true)
@Table(name = "persistent_logins")
public class PersistentLogins implements java.io.Serializable{
	
	private String username;
	
	private String series;
	
	private String token;
	
	
	private Date lastUsed;


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	@Id
	@GeneratedValue(generator = "hibernate-uuid")
	@GenericGenerator(name = "hibernate-uuid", strategy = "uuid")
	public String getSeries() {
		return series;
	}


	public void setSeries(String series) {
		this.series = series;
	}


	public String getToken() {
		return token;
	}


	public void setToken(String token) {
		this.token = token;
	}

	@Column(name = "last_used")
	public Date getLastUsed() {
		return lastUsed;
	}


	public void setLastUsed(Date lastUsed) {
		this.lastUsed = lastUsed;
	}
	
	
	

}
