package com.arthouse.security.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: barry.wang
 * Date: 11-6-7
 * Time: 下午5:53
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(uniqueConstraints = {}, name = "sys_roles_authorities")
public class SysRolesAuthorities implements Serializable {


    private long id;

    private SysAuthorities sysAuthorities;

    private SysRoles sysRoles;

    private Boolean enabled;

    public SysRolesAuthorities() {
    }

    public SysRolesAuthorities(long id) {
        this.id = id;
    }

    public SysRolesAuthorities( SysAuthorities sysAuthorities,
                               SysRoles sysRoles, Boolean enabled) {

        this.sysAuthorities = sysAuthorities;
        this.sysRoles = sysRoles;
        this.enabled = enabled;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    @ManyToOne
    @JoinColumn(name = "AUTHORITY_ID")
    public SysAuthorities getSysAuthorities() {
        return sysAuthorities;
    }

    public void setSysAuthorities(SysAuthorities sysAuthorities) {
        this.sysAuthorities = sysAuthorities;
    }

    @ManyToOne
    @JoinColumn(name = "ROLE_ID")
    public SysRoles getSysRoles() {
        return sysRoles;
    }

    public void setSysRoles(SysRoles sysRoles) {
        this.sysRoles = sysRoles;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
