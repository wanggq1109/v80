package com.arthouse.common.dto;

public class SysAuthorityDto {
	private Long id;
    private String authorityName;
    private String authorityDesc;
    private Boolean enabled;
    private Boolean issys;
    private String module;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
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
}
