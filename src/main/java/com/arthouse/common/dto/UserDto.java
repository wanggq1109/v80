package com.arthouse.common.dto;

/**
 * Created by IntelliJ IDEA.
 * User: Dragon
 * Date: 11-5-25
 * Time: 下午2:17
 * To change this template use File | Settings | File Templates.
 */
public class UserDto {

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
    
    private String userDeptid; 

    //用户的职位：比如主任、经理等。
    private String userDuty;

    //该用户所负责的子系统
    private String subSystem;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserDesc() {
        return userDesc;
    }

    public void setUserDesc(String userDesc) {
        this.userDesc = userDesc;
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

    public String getUserDept() {
        return userDept;
    }

    public void setUserDept(String userDept) {
        this.userDept = userDept;
    }

    public String getUserDuty() {
        return userDuty;
    }

    public void setUserDuty(String userDuty) {
        this.userDuty = userDuty;
    }

    public String getSubSystem() {
        return subSystem;
    }

    public void setSubSystem(String subSystem) {
        this.subSystem = subSystem;
    }

	public String getUserDeptid() {
		return userDeptid;
	}

	public void setUserDeptid(String userDeptid) {
		this.userDeptid = userDeptid;
	}
    
}
