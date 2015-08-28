package com.arthouse.common.dto;

public class RoleDto {
	private Long id;
	private String roleName;
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Boolean getIssys() {
		return issys;
	}

	public void setIssys(Boolean issys) {
		this.issys = issys;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	private String roleDesc;
	private Boolean enabled;
	private Boolean issys;

	//平台中的子系统
	private String module;

}
