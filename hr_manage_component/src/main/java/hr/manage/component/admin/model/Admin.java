package hr.manage.component.admin.model;

import java.io.Serializable;
import java.util.Date;

import net.paoding.rose.jade.plugin.sql.annotations.Column;
import net.paoding.rose.jade.plugin.sql.annotations.Table;
@Table("admin")
public class Admin implements Serializable{

    /** 版本号 */
    private static final long serialVersionUID = -2818664694050874839L;

    /** 表的主键 */
    @Column(pk=true,value = "userid")
    private Integer userid;

    /** 用户名 */
    @Column(value = "username")
    private String username;

    /** 密码 */
    @Column(value = "password")
    private String password;

    /** 员工信息表ID */
    @Column(value = "personal_info_id")
    private Integer personalInfoId;

    /** 角色id列表 */
    @Column(value = "roleids")
    private String roleids;

    /** 角色名称 */
    @Column(value = "rolenames")
    private String rolenames;

    /** 上次登录IP */
    @Column(value = "lastloginip")
    private String lastloginip;

    /** 上次登录时间 */
    @Column(value = "lastlogintime")
    private Date lastlogintime;

    /** 邮箱 */
    @Column(value = "email")
    private String email;

    /** 姓名 */
    @Column(value = "realname")
    private String realname;

    /** 状态 1:可用 0：已禁用 */
    @Column(value = "status")
    private Integer status;

    /** 电话 */
    @Column(value = "mobile_phone")
    private String mobilePhone;

    /** 修改时间 */
    @Column(value = "update_time")
    private Date updateTime;

    /** 生成时间 */
    @Column(value = "create_time")
    private Date createTime;


	private AdminRole adminRole;
    /**
     * 获取表的主键
     * 
     * @return 表的主键
     */
    public Integer getUserid() {
        return this.userid;
    }

    public AdminRole getAdminRole() {
		return adminRole;
	}

	public void setAdminRole(AdminRole adminRole) {
		this.adminRole = adminRole;
	}

	/**
     * 设置表的主键
     * 
     * @param userid
     *          表的主键
     */
    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    /**
     * 获取用户名
     * 
     * @return 用户名
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * 设置用户名
     * 
     * @param username
     *          用户名
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 获取密码
     * 
     * @return 密码
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * 设置密码
     * 
     * @param password
     *          密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取员工信息表ID
     * 
     * @return 员工信息表ID
     */
    public Integer getPersonalInfoId() {
        return this.personalInfoId;
    }

    /**
     * 设置员工信息表ID
     * 
     * @param personalInfoId
     *          员工信息表ID
     */
    public void setPersonalInfoId(Integer personalInfoId) {
        this.personalInfoId = personalInfoId;
    }
    
    /**
     * 获取角色id列表
     * 
     * @return 角色id列表
     */
    public String getRoleids() {
        return this.roleids;
    }

    /**
     * 设置角色id列表
     * 
     * @param roleids
     *          角色id列表
     */
    public void setRoleids(String roleids) {
        this.roleids = roleids;
    }

    /**
     * 获取角色名称
     * 
     * @return 角色名称
     */
    public String getRolenames() {
        return this.rolenames;
    }

    /**
     * 设置角色名称
     * 
     * @param rolenames
     *          角色名称
     */
    public void setRolenames(String rolenames) {
        this.rolenames = rolenames;
    }

    /**
     * 获取上次登录IP
     * 
     * @return 上次登录IP
     */
    public String getLastloginip() {
        return this.lastloginip;
    }

    /**
     * 设置上次登录IP
     * 
     * @param lastloginip
     *          上次登录IP
     */
    public void setLastloginip(String lastloginip) {
        this.lastloginip = lastloginip;
    }

    /**
     * 获取上次登录时间
     * 
     * @return 上次登录时间
     */
    public Date getLastlogintime() {
        return this.lastlogintime;
    }

    /**
     * 设置上次登录时间
     * 
     * @param lastlogintime
     *          上次登录时间
     */
    public void setLastlogintime(Date lastlogintime) {
        this.lastlogintime = lastlogintime;
    }

    /**
     * 获取邮箱
     * 
     * @return 邮箱
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * 设置邮箱
     * 
     * @param email
     *          邮箱
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 获取姓名
     * 
     * @return 姓名
     */
    public String getRealname() {
        return this.realname;
    }

    /**
     * 设置姓名
     * 
     * @param realname
     *          姓名
     */
    public void setRealname(String realname) {
        this.realname = realname;
    }

    /**
     * 获取状态 1:可用 0：已禁用
     * 
     * @return 状态 1:可用 0
     */
    public Integer getStatus() {
        return this.status;
    }

    /**
     * 设置状态 1:可用 0：已禁用
     * 
     * @param status
     *          状态 1:可用 0
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取电话
     * 
     * @return 电话
     */
    public String getMobilePhone() {
        return this.mobilePhone;
    }

    /**
     * 设置电话
     * 
     * @param mobilePhone
     *          电话
     */
    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    /**
     * 获取修改时间
     * 
     * @return 修改时间
     */
    public Date getUpdateTime() {
        return this.updateTime;
    }

    /**
     * 设置修改时间
     * 
     * @param updateTime
     *          修改时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取生成时间
     * 
     * @return 生成时间
     */
    public Date getCreateTime() {
        return this.createTime;
    }

    /**
     * 设置生成时间
     * 
     * @param createTime
     *          生成时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}