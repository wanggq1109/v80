package com.arthouse.common.domain;


import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * 
 * 团购信息
 * 
 * @author wanggq
 *
 */
@javax.persistence.Entity
@Table(name = "web_GroupPurchase")
public class GroupItemsInfo {
	
	private Long id;
	
	//标题
	private String title;
	//当前价格
	private Double price;
	//原始价格
	private Double old_price;
	//信息描述
	private String descr;
	//缩略图路径
	private String url;
	//文本编辑器内容（ckeditor）
	private String content;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}
	public String getTitle() {
		return title;
	}
	public Double getPrice() {
		return price;
	}
	public Double getOld_price() {
		return old_price;
	}
	public String getDescr() {
		return descr;
	}
	public String getUrl() {
		return url;
	}
	
	@Lob 
    @Basic(fetch = FetchType.LAZY) 
    @Column(name="Content", columnDefinition="LongText", nullable=true)
	public String getContent() {
		return content;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public void setOld_price(Double old_price) {
		this.old_price = old_price;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public void setUrl(String url) {
		this.url = url;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	
	

}
