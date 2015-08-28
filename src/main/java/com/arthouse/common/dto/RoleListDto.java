package com.arthouse.common.dto;

import java.math.BigInteger;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 11-7-4
 * Time: 下午4:51
 * To change this template use File | Settings | File Templates.
 */
public class RoleListDto
{
    private String roleName;
	private BigInteger id;
	private String roleDesc;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}
    
}
