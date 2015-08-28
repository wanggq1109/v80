package com.arthouse.common.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * 工作室服务
 * 
 * @author wanggq
 *
 */
@javax.persistence.Entity
@Table(name = "web_studioservice")
public class StudioInfofw {
	
	private Long id;
	
	private String title;
	
	private String content;

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	
	

}
