package com.arthouse.security.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 *  角色
 * Created by IntelliJ IDEA.
 * User: barry.wang
 * Date: 11-6-7
 * Time: 下午5:40
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(uniqueConstraints = {}, name = "sys_roles")
public class SysRoles implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	private String roleName;
	private String roleDesc;
	private Boolean enabled;
	private Boolean issys;

	//平台中的子系统
	private String module;


    @OneToMany(mappedBy = "pubRoles",cascade = { CascadeType.PERSIST, CascadeType.MERGE },fetch = FetchType.LAZY)
    @OrderBy(value = "id ASC")
	private Set<SysUsersRoles> sysUsersRoles = new HashSet<SysUsersRoles>();


    @OneToMany(mappedBy = "sysRoles",cascade = { CascadeType.PERSIST, CascadeType.MERGE },fetch = FetchType.LAZY)
    @OrderBy(value = "id ASC")
    private Set<SysRolesAuthorities> sysRolesAuthorities = new HashSet<SysRolesAuthorities>();

	public SysRoles() {
	}

	public SysRoles(Long id) {
		this.id = id;
	}

	public SysRoles(Long id, String roleName, String roleDesc) {
		this.id = id;
		this.roleName = roleName;
		this.roleDesc = roleDesc;
	}

	public SysRoles(Long id, String roleName, String roleDesc,
			Boolean enabled, Boolean issys, String module) {
		this.id = id;
		this.roleName = roleName;
		this.roleDesc = roleDesc;
		this.enabled = enabled;
		this.issys = issys;
		this.module = module;
	}

	public SysRoles(Long id, String roleName, String roleDesc,
			Boolean enabled, Boolean issys, String module, Set<SysUsersRoles>sysUsersRoles,
			Set<SysRolesAuthorities> sysRolesAuthorities) {
		this.id= id;
		this.roleName = roleName;
		this.roleDesc = roleDesc;
		this.enabled = enabled;
		this.issys = issys;
		this.module = module;
		this.sysUsersRoles = sysUsersRoles;
		this.sysRolesAuthorities = sysRolesAuthorities;
	}


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleDesc() {
		return this.roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	public Boolean getEnabled() {
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


	public String getModule() {
		return this.module;
	}

	public void setModule(String module) {
		this.module = module;
	}

    @OneToMany(mappedBy = "sysRoles", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @OrderBy(value = "id ASC")
    public Set<SysRolesAuthorities> getSysRolesAuthorities() {
        return sysRolesAuthorities;
    }

    public void setSysRolesAuthorities(Set<SysRolesAuthorities> sysRolesAuthorities) {
        this.sysRolesAuthorities = sysRolesAuthorities;
    }

    @OneToMany(mappedBy = "pubRoles", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @OrderBy(value = "id ASC")
    public Set<SysUsersRoles> getSysUsersRoles() {
        return sysUsersRoles;
    }

    public void setSysUsersRoles(Set<SysUsersRoles> sysUsersRoles) {
        this.sysUsersRoles = sysUsersRoles;
    }

    /*
    * 若要把持久类的实例放入Set中(尤其表示多值关联时)，或重用脱管实例，
    * 就要重写该持久类的equals和hashCode。
    * @see java.lang.Object#equals(java.lang.Object)
    */
	public boolean equals( Object other ){

		if( this == other ) return true;

		if( !( other instanceof SysRoles ) ) return false;

		final SysRoles sysRoles = (SysRoles)other;

		if( !sysRoles.getRoleName().equals( getRoleName() ) ) return false;

		if( !sysRoles.getRoleDesc().equals( getRoleDesc())) return false;

		return true;

	}

	/*
	 * 重写hashCode()。
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode(){

		int result;

		result = getRoleName().hashCode();

		result = 29 * result + getRoleDesc().hashCode();

		return result;
	}

	public static void main(String[] args){

		SysRoles role1 = new SysRoles(1L,"lxb","ljh");
		SysRoles role2 = new SysRoles(1L,"lxb","ljh");

		/*
		 * 经过试验，当不重写equals和hashCode时显示为false;
		 * 重写时，显示为true。
		 * 这就是为什么重写equals和hashCode的原因，当你希望从hiberate中提取的对象实例中，
		 * 若是所有的字段的内容都相同时，就认为这两个对象实例是相同的，此时就需要重写equals和hashCode。
		 * 重写equals和hashCode意味着，混杂在不同上下文及Session中的两个实例对象有了确定的语义。
		 */
		System.out.println(role1.equals(role2));

		/*
		 * 经过试验，当不重写equals和hashCode时显示为false;
		 * 重写时，显示为true。
		 *
		 */
		System.out.println(role1.hashCode() == role2.hashCode());

	}

}
