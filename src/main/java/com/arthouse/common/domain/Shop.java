package com.arthouse.common.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@javax.persistence.Entity
@Table(name = "business_info")
public class Shop {
	
	private int id;
	
	private String hairId;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getHairId() {
		return hairId;
	}

	public void setHairId(String hairId) {
		this.hairId = hairId;
	}
	
	

}
