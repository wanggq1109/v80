package com.arthouse.security.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 权限资源中间表
 * Created by IntelliJ IDEA.
 * User: barry.wang
 * Date: 11-6-7
 * Time: 下午5:52
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(uniqueConstraints = {}, name = "sys_authorities_resources")
public class SysAuthoritiesResources implements Serializable {

    private long id;
    private SysAuthorities sysAuthorities;
    private SysResources sysResources;
    private Boolean enabled;

    public SysAuthoritiesResources() {
    }

    public SysAuthoritiesResources(long id) {
        this.id = id;
    }

    public SysAuthoritiesResources( SysAuthorities sysAuthorities,
                                   SysResources sysResources, Boolean enabled) {

        this.sysAuthorities = sysAuthorities;
        this.sysResources = sysResources;
        this.enabled = enabled;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "AUTHORITY_ID", nullable = false)
    public SysAuthorities getSysAuthorities() {
        return this.sysAuthorities;
    }

    public void setSysAuthorities(SysAuthorities sysAuthorities) {
        this.sysAuthorities = sysAuthorities;
    }

    @ManyToOne
    @JoinColumn(name = "RESOURCE_ID", nullable = false)
    public SysResources getSysResources() {
        return this.sysResources;
    }

    public void setSysResources(SysResources sysResources) {
        this.sysResources = sysResources;
    }

    public Boolean getEnabled() {
        return this.enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }


}
