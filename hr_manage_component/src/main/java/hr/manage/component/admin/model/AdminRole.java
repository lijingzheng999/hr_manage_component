package hr.manage.component.admin.model;

import java.io.Serializable;
import java.util.Date;

import net.paoding.rose.jade.plugin.sql.annotations.Column;
import net.paoding.rose.jade.plugin.sql.annotations.Table;
@Table("admin_role")
public class AdminRole implements Serializable{
	/** 版本号 */
    private static final long serialVersionUID = 6738192116428082676L;

    /** 表的主键 */
    @Column(value = "roleid")
    private Integer roleid;

    /** 角色名称 */
    @Column(value = "rolename")
    private String rolename;

    /** 描述 */
    @Column(value = "description")
    private String description;

    /** 0:不可分配，1:可分配 */
    @Column(value = "disabled")
    private Integer disabled;

    /** 权限列表 */
    @Column(value = "functionIds")
    private String functionIds;

    /** 修改时间 */
    @Column(value = "update_time")
    private Date updateTime;

    /** 生成时间 */
    @Column(value = "create_time")
    private Date createTime;

    /**
     * 获取表的主键
     * 
     * @return 表的主键
     */
    public Integer getRoleid() {
        return this.roleid;
    }

    /**
     * 设置表的主键
     * 
     * @param roleid
     *          表的主键
     */
    public void setRoleid(Integer roleid) {
        this.roleid = roleid;
    }

    /**
     * 获取角色名称
     * 
     * @return 角色名称
     */
    public String getRolename() {
        return this.rolename;
    }

    /**
     * 设置角色名称
     * 
     * @param rolename
     *          角色名称
     */
    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    /**
     * 获取描述
     * 
     * @return 描述
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * 设置描述
     * 
     * @param description
     *          描述
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 获取0:不可分配，1:可分配
     * 
     * @return 0:不可分配
     */
    public Integer getDisabled() {
        return this.disabled;
    }

    /**
     * 设置0:不可分配，1:可分配
     * 
     * @param disabled
     *          0:不可分配
     */
    public void setDisabled(Integer disabled) {
        this.disabled = disabled;
    }

    /**
     * 获取权限列表
     * 
     * @return 权限列表
     */
    public String getFunctionIds() {
        return this.functionIds;
    }

    /**
     * 设置权限列表
     * 
     * @param functionIds
     *          权限列表
     */
    public void setFunctionIds(String functionIds) {
        this.functionIds = functionIds;
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