package com.arthouse.common.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * 
 * 
 * @author wgq
 * 
 * 网站各上传照片类型
 * 
 */

@javax.persistence.Entity
@Table(name = "web_photoType", uniqueConstraints = { @UniqueConstraint(columnNames = { "code" }) })
public class PhotoType {

	private long id;
	// 类型编码
	private String code;
	// 类型名称
	private String name;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long getId() {
		return id;
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setName(String name) {
		this.name = name;
	}

}
