package com.arthouse.common.domain;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;


@javax.persistence.Entity
@Table(name = "web_newsInfo")
public class News {
	
	private Long id;
	//标题
	private String title;
	//作者
	private String author;
	//新闻来下
	private String source;
	//内容
	private String content;
	//发布时间
	private Date releaseTime;
	//新闻列表缩略图
	private String url;
	
	private String descr;
	
	//发布状态 1：发布；0：未发布；
	private String status;
	
	

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getAuthor() {
		return author;
	}

	
	@Lob 
    @Basic(fetch = FetchType.LAZY) 
    @Column(name="Content", columnDefinition="LongText", nullable=true)
	public String getContent() {
		return content;
	}

	public Date getReleaseTime() {
		return releaseTime;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setAuthor(String author) {
		this.author = author;
	}


	public void setContent(String content) {
		this.content = content;
	}

	public void setReleaseTime(Date releaseTime) {
		this.releaseTime = releaseTime;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Lob 
    @Basic(fetch = FetchType.LAZY) 
    @Column(name="descr", columnDefinition="LongText", nullable=true)
	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
	

}
