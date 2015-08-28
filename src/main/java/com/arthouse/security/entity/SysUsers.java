package com.arthouse.security.entity;

import com.arthouse.security.CustomUserDetails;
import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

/**
 * 获取用户信息
 * Created by IntelliJ IDEA.
 * User: barry.wang
 * Date: 11-5-10
 * Time: 下午4:03
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(uniqueConstraints = {}, name = "sys_users")
public class SysUsers implements CustomUserDetails, Serializable {


    public Long id;

    //用户账号 与 用户id相同，具有唯一性。
    private String userAccount;

    //中文用户名。
    private String userName;

    //密码原文 ＋ 用户名作为盐值 的字串经过Md5加密后形成的密文。
    private String userPassword;

    //用户备注
    private String userDesc;

    //是否能用。
    private Boolean enabled;

    //是否是超级用户。
    private Boolean issys;

    //用户所在的单位。
    private String userDept;

    //用户的职位：比如主任、经理等。
    private String userDuty;

    //该用户所负责的子系统
    private String subSystem;

    //一个用户具有多个角色。
    private Set<SysUsersRoles> sysUsersRoleses = new HashSet<SysUsersRoles>();


    //实现了UserDetails之后的相关变量
    private String password;
    private String username;
    private Set<GrantedAuthority> authorities;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;


    public SysUsers() {

    }

    public SysUsers(Long userId, String userAccount, String userName,
                    String userPassword, String userDesc, Boolean enabled,
                    Boolean issys, String userDuty, String userDept, String subSystem, Set<SysUsersRoles> sysUsersRoleses, boolean accountNonExpired,
                    boolean credentialsNonExpired, boolean accountNonLocked, Collection<GrantedAuthority> authorities) {

       /* if (((userAccount == null) || "".equals(userAccount)) || (userPassword == null)) {
            throw new IllegalArgumentException("Cannot pass null or empty values to constructor");
        }*/

        this.id = userId;
        this.userAccount = userAccount;
        this.userName = userName;
        this.userPassword = userPassword;
        this.userDesc = userDesc;
        this.issys = issys;
        this.userDuty = userDuty;
        this.userDept = userDept;
        this.subSystem = subSystem;
        this.sysUsersRoleses = sysUsersRoleses;
        this.username = userAccount;
        this.password = userPassword;
        this.enabled = enabled;
        this.accountNonExpired = accountNonExpired;
        this.credentialsNonExpired = credentialsNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.authorities = Collections.unmodifiableSet(sortAuthorities(authorities));
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserAccount() {
        return this.userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }


    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return this.userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserDesc() {
        return this.userDesc;
    }

    public void setUserDesc(String userDesc) {
        this.userDesc = userDesc;
    }

    public boolean getEnabled() {
        return this.enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getIssys() {
        return this.issys;
    }

    public void setIssys(Boolean issys) {
        this.issys = issys;
    }

    public String getUserDept() {
        return this.userDept;
    }

    public void setUserDept(String userDept) {
        this.userDept = userDept;
    }

    public String getUserDuty() {
        return this.userDuty;
    }

    public void setUserDuty(String userDuty) {
        this.userDuty = userDuty;
    }

    public String getSubSystem() {
        return this.subSystem;
    }

    public void setSubSystem(String subSystem) {
        this.subSystem = subSystem;
    }

    @OneToMany(mappedBy = "pubUsers", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @OrderBy(value = "id ASC")
    public Set<SysUsersRoles> getSysUsersRoleses() {
        return sysUsersRoleses;
    }


    public void setSysUsersRoleses(Set<SysUsersRoles> sysUsersRoleses) {
        this.sysUsersRoleses = sysUsersRoleses;
    }

    //~ Methods ========================================================================================================

    public boolean equals(Object rhs) {
        if (!(rhs instanceof SysUsers) || (rhs == null)) {
            return false;
        }

        SysUsers user = (SysUsers) rhs;

        //具有的权限。
        if (!authorities.equals(user.authorities)) {
            return false;
        }

        // 通过Spring Security构建一个用户时，用户名和密码不能为空。
        return (this.getPassword().equals(user.getPassword()) && this.getUsername().equals(user.getUsername())
                && (this.isAccountNonExpired() == user.isAccountNonExpired())
                && (this.isAccountNonLocked() == user.isAccountNonLocked())
                && (this.isCredentialsNonExpired() == user.isCredentialsNonExpired())
                && (this.isEnabled() == user.isEnabled()));
    }

     @Transient
    public Collection<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<GrantedAuthority> authorities) {
        this.authorities = (Set<GrantedAuthority>) authorities;
    }

    @Transient
    public String getPassword() {
        return password;
    }

    @Transient
    public String getUsername() {
        return username;
    }


    public int hashCode() {
        int code = 9792;

        //若该用户不是登录人员，则可以允许没有authorities。
        if (null != getUsername() && null != getAuthorities()) {
            for (GrantedAuthority authority : getAuthorities()) {

                code = code * (authority.hashCode() % 7);
            }
        }

        if (this.getPassword() != null) {
            code = code * (this.getPassword().hashCode() % 7);
        }

        if (this.getUsername() != null) {
            code = code * (this.getUsername().hashCode() % 7);
        }

        if (this.isAccountNonExpired()) {
            code = code * -2;
        }

        if (this.isAccountNonLocked()) {
            code = code * -3;
        }

        if (this.isCredentialsNonExpired()) {
            code = code * -5;
        }

        if (this.isEnabled()) {
            code = code * -7;
        }

        return code;
    }

    @Transient
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Transient
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Transient
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public boolean isEnabled() {
        return enabled;
    }

    private static SortedSet<GrantedAuthority> sortAuthorities(Collection<GrantedAuthority> authorities) {
        Assert.notNull(authorities, "Cannot pass a null GrantedAuthority collection");
        // Ensure array iteration order is predictable (as per UserDetails.getAuthorities() contract and SEC-717)
        SortedSet<GrantedAuthority> sortedAuthorities =
                new TreeSet<GrantedAuthority>(new AuthorityComparator());

        for (GrantedAuthority grantedAuthority : authorities) {
            Assert.notNull(grantedAuthority, "GrantedAuthority list cannot contain any null elements");
            sortedAuthorities.add(grantedAuthority);
        }

        return sortedAuthorities;
    }

    private static class AuthorityComparator implements Comparator<GrantedAuthority>, Serializable {
        public int compare(GrantedAuthority g1, GrantedAuthority g2) {
            // Neither should ever be null as each entry is checked before adding it to the set.
            // If the authority is null, it is a custom authority and should precede others.
            if (g2.getAuthority() == null) {
                return -1;
            }

            if (g1.getAuthority() == null) {
                return 1;
            }

            return g1.getAuthority().compareTo(g2.getAuthority());
        }
    }


    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString()).append(": ");
        sb.append("Username: ").append(this.username).append("; ");
        sb.append("Password: [PROTECTED]; ");
        sb.append("UserAccount: ").append(this.userAccount).append("; ");
        sb.append("UserDept: ").append(this.userDept).append("; ");
        sb.append("UserDuty: ").append(this.userDuty).append("; ");
        sb.append("UserDesc: ").append(this.userDesc).append("; ");
        sb.append("UserSubSystem: ").append(this.subSystem).append("; ");
        sb.append("UserIsSys: ").append(this.issys).append("; ");
        sb.append("Enabled: ").append(this.enabled).append("; ");
        sb.append("AccountNonExpired: ").append(this.accountNonExpired).append("; ");
        sb.append("credentialsNonExpired: ").append(this.credentialsNonExpired).append("; ");
        sb.append("AccountNonLocked: ").append(this.accountNonLocked).append("; ");

        if (null != authorities && !authorities.isEmpty()) {
            sb.append("Granted Authorities: ");

            boolean first = true;
            for (GrantedAuthority auth : authorities) {
                if (!first) {
                    sb.append(",");
                }
                first = false;

                sb.append(auth);
            }
        } else {
            sb.append("Not granted any authorities");
        }

        return sb.toString();
    }

    public static void main(String[] args) {


    }


}
