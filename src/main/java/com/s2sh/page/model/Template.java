package com.s2sh.page.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@javax.persistence.Entity
@Table(name = "template")
public class Template  {

	private long id;

	private String name;

	private Date templatDate;

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "templat_date")
	public Date getTemplatDate() {
		return templatDate;
	}

	public void setTemplatDate(Date templatDate) {
		this.templatDate = templatDate;
	}
}
