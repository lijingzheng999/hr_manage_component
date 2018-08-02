package hr.manage.component.recruit.model;
import java.util.Date;

import net.paoding.rose.jade.plugin.sql.annotations.Column;
import net.paoding.rose.jade.plugin.sql.annotations.Table;

/**
 * recruit_info
 * 
 * @author 
 * @version 1.0.0 2018-07-24
 */
@Table(value = "recruit_info")
public class RecruitInfo implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -6886293987267276739L;

    /** 表的主键 */
    @Column(pk=true,value = "id")
    private Integer id;

    /** 岗位名称(职位) */
    @Column(value = "position")
    private String position;

    /** 工作地点 */
    @Column(value = "working_place")
    private String workingPlace;

    /** 经验要求(工作年限,单位年) */
    @Column(value = "experience")
    private Integer experience;

    /** 学历要求 */
    @Column(value = "education")
    private String education;

    /** 岗位职责 */
    @Column(value = "post_duty")
    private String postDuty;

    /** 创建人 */
    @Column(value = "create_user")
    private String createUser;

    /** 是否删除 1未删除 0已删除 */
    @Column(value = "is_del")
    private Integer isDel;

    /** 修改时间 */
    @Column(value = "update_time")
    private Date updateTime;

    /** 创建时间 */
    @Column(value = "create_time")
    private Date createTime;

    /**
     * 获取表的主键
     * 
     * @return 表的主键
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * 设置表的主键
     * 
     * @param id
     *          表的主键
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取岗位名称(职位)
     * 
     * @return 岗位名称(职位)
     */
    public String getPosition() {
        return this.position;
    }

    /**
     * 设置岗位名称(职位)
     * 
     * @param position
     *          岗位名称(职位)
     */
    public void setPosition(String position) {
        this.position = position;
    }

    /**
     * 获取工作地点
     * 
     * @return 工作地点
     */
    public String getWorkingPlace() {
        return this.workingPlace;
    }

    /**
     * 设置工作地点
     * 
     * @param workingPlace
     *          工作地点
     */
    public void setWorkingPlace(String workingPlace) {
        this.workingPlace = workingPlace;
    }

    /**
     * 获取经验要求(工作年限,单位年)
     * 
     * @return 经验要求(工作年限
     */
    public Integer getExperience() {
        return this.experience;
    }

    /**
     * 设置经验要求(工作年限,单位年)
     * 
     * @param experience
     *          经验要求(工作年限
     */
    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    /**
     * 获取学历要求
     * 
     * @return 学历要求
     */
    public String getEducation() {
        return this.education;
    }

    /**
     * 设置学历要求
     * 
     * @param education
     *          学历要求
     */
    public void setEducation(String education) {
        this.education = education;
    }

    /**
     * 获取岗位职责
     * 
     * @return 岗位职责
     */
    public String getPostDuty() {
        return this.postDuty;
    }

    /**
     * 设置岗位职责
     * 
     * @param postDuty
     *          岗位职责
     */
    public void setPostDuty(String postDuty) {
        this.postDuty = postDuty;
    }

    /**
     * 获取创建人
     * 
     * @return 创建人
     */
    public String getCreateUser() {
        return this.createUser;
    }

    /**
     * 设置创建人
     * 
     * @param createUser
     *          创建人
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * 获取是否删除 1未删除 0已删除
     * 
     * @return 是否删除 1未删除 0已删除
     */
    public Integer getIsDel() {
        return this.isDel;
    }

    /**
     * 设置是否删除 1未删除 0已删除
     * 
     * @param isDel
     *          是否删除 1未删除 0已删除
     */
    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
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
     * 获取创建时间
     * 
     * @return 创建时间
     */
    public Date getCreateTime() {
        return this.createTime;
    }

    /**
     * 设置创建时间
     * 
     * @param createTime
     *          创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}