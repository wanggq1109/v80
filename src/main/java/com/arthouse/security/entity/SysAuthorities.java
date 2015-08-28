package com.arthouse.security.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * 权限
 * Created by IntelliJ IDEA.
 * User: barry.wang
 * Date: 11-6-7
 * Time: 下午5:52
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(uniqueConstraints = {}, name = "sys_authorities")
public class SysAuthorities implements Serializable {

    private Long id;
    private String authorityName;
    private String authorityDesc;
    private Boolean enabled;
    private Boolean issys;
    private String module;

    private Set<SysRolesAuthorities> pubRolesAuthoritieses = new HashSet<SysRolesAuthorities>();

    private Set<SysAuthoritiesResources> pubAuthoritiesResourceses = new HashSet<SysAuthoritiesResources>();


    public SysAuthorities() {
    }

    public SysAuthorities(Long id) {
        this.id = id;
    }

    public SysAuthorities(String authorityName,
                          String authorityDesc, Boolean enabled, Boolean issys, String module,
                          Set pubRolesAuthoritieses, Set pubAuthoritiesResourceses) {

        this.authorityName = authorityName;
        this.authorityDesc = authorityDesc;
        this.enabled = enabled;
        this.issys = issys;
        this.module = module;
        this.pubRolesAuthoritieses = pubRolesAuthoritieses;
        this.pubAuthoritiesResourceses = pubAuthoritiesResourceses;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @OneToMany(mappedBy = "sysAuthorities", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @OrderBy(value = "id ASC")
    public Set<SysRolesAuthorities> getPubRolesAuthoritieses() {
        return pubRolesAuthoritieses;
    }

    public void setPubRolesAuthoritieses(Set<SysRolesAuthorities> pubRolesAuthoritieses) {
        this.pubRolesAuthoritieses = pubRolesAuthoritieses;
    }

    @OneToMany(mappedBy = "sysAuthorities", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @OrderBy(value = "id ASC")
    public Set<SysAuthoritiesResources> getPubAuthoritiesResourceses() {
        return pubAuthoritiesResourceses;
    }

    public void setPubAuthoritiesResourceses(Set<SysAuthoritiesResources> pubAuthoritiesResourceses) {
        this.pubAuthoritiesResourceses = pubAuthoritiesResourceses;
    }
}
