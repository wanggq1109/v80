package com.arthouse.security.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 用户角色中间表
 * Created by IntelliJ IDEA.
 * User: barry.wang
 * Date: 11-6-7
 * Time: 下午5:52
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(uniqueConstraints = {}, name = "sys_users_roles")
public class SysUsersRoles implements Serializable {

    private long id;


    private SysUsers pubUsers;


    private SysRoles pubRoles;

    private Boolean enabled;

    public SysUsersRoles() {
    }

    public SysUsersRoles(long id) {
        this.id = id;
    }

    public SysUsersRoles( SysUsers pubUsers, SysRoles pubRoles,
                         Boolean enabled) {

        this.pubUsers = pubUsers;
        this.pubRoles = pubRoles;
        this.enabled = enabled;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
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
    @JoinColumn(name = "USER_ID")
    public SysUsers getPubUsers() {
        return pubUsers;
    }

    public void setPubUsers(SysUsers pubUsers) {
        this.pubUsers = pubUsers;
    }

    @ManyToOne
    @JoinColumn(name = "ROLE_ID")
    public SysRoles getPubRoles() {
        return pubRoles;
    }

    public void setPubRoles(SysRoles pubRoles) {
        this.pubRoles = pubRoles;
    }
}
