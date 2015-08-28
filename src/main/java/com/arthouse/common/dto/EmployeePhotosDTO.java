package com.arthouse.common.dto;

import java.math.BigInteger;

public class EmployeePhotosDTO {
	
	private BigInteger id;
	
	private String name;
	
	private String reamrk;


	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getReamrk() {
		return reamrk;
	}

	public void setReamrk(String reamrk) {
		this.reamrk = reamrk;
	}
	
	

}
