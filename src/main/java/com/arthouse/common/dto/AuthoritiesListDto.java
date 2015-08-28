package com.arthouse.common.dto;

import java.math.BigInteger;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 11-7-6
 * Time: 下午1:39
 * To change this template use File | Settings | File Templates.
 */
public class AuthoritiesListDto {
    private BigInteger id;
    private String authorityName;
    private String authorityDesc;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getAuthorityName() {
        return authorityName;
    }

    public void setAuthorityName(String authorityName) {
        this.authorityName = authorityName;
    }

	public String getAuthorityDesc() {
		return authorityDesc;
	}

	public void setAuthorityDesc(String authorityDesc) {
		this.authorityDesc = authorityDesc;
	}
    
}
