package hr.manage.component.recruit.model;
import java.util.Date;

import net.paoding.rose.jade.plugin.sql.annotations.Column;
import net.paoding.rose.jade.plugin.sql.annotations.Table;

/**
 * resume_info
 * 
 * @author 
 * @version 1.0.0 2018-07-24
 */
@Table(value = "resume_info")
public class ResumeInfo implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -7997925148021166982L;

    /** 表的主键 */
    @Column(pk=true,value = "id")
    private Integer id;

    /** 姓名 */
    @Column(value = "name")
    private String name;

    /** 联系电话 */
    @Column(value = "phone")
    private String phone;

    /** 性别 */
    @Column(value = "sex")
    private String sex;

    /** 种类(邀约人员、面试通过者和未通过者) 暂留 */
    @Column(value = "type")
    private String type;

    /** 岗位名称(职位) */
    @Column(value = "position")
    private String position;

    /** 经验要求(工作年限,单位年) */
    @Column(value = "experience")
    private Integer experience;

    /** 邮箱 */
    @Column(value = "email")
    private String email;

    /** 毕业学校 */
    @Column(value = "school")
    private String school;

    /** 毕业专业 */
    @Column(value = "major")
    private String major;

    /** 学历 */
    @Column(value = "education")
    private String education;

    /** 出生日期 */
    @Column(value = "birthday")
    private String birthday;

    /** 邀约时间 */
    @Column(value = "invite_time")
    private Date inviteTime;

    /** 面试时间 */
    @Column(value = "interview_time")
    private Date interviewTime;

    /** 面试是否通过 1通过 0未通过 */
    @Column(value = "is_pass")
    private Integer isPass;

    /** 是否入职  1入职 0未入职 */
    @Column(value = "is_entry")
    private Integer isEntry;

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
     * 获取姓名
     * 
     * @return 姓名
     */
    public String getName() {
        return this.name;
    }

    /**
     * 设置姓名
     * 
     * @param name
     *          姓名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取联系电话
     * 
     * @return 联系电话
     */
    public String getPhone() {
        return this.phone;
    }

    /**
     * 设置联系电话
     * 
     * @param phone
     *          联系电话
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 获取性别
     * 
     * @return 性别
     */
    public String getSex() {
        return this.sex;
    }

    /**
     * 设置性别
     * 
     * @param sex
     *          性别
     */
    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     * 获取种类(邀约人员、面试通过者和未通过者) 暂留
     * 
     * @return 种类(邀约人员、面试通过者和未通过者) 暂留
     */
    public String getType() {
        return this.type;
    }

    /**
     * 设置种类(邀约人员、面试通过者和未通过者) 暂留
     * 
     * @param type
     *          种类(邀约人员、面试通过者和未通过者) 暂留
     */
    public void setType(String type) {
        this.type = type;
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
     * 获取毕业学校
     * 
     * @return 毕业学校
     */
    public String getSchool() {
        return this.school;
    }

    /**
     * 设置毕业学校
     * 
     * @param school
     *          毕业学校
     */
    public void setSchool(String school) {
        this.school = school;
    }

    /**
     * 获取毕业专业
     * 
     * @return 毕业专业
     */
    public String getMajor() {
        return this.major;
    }

    /**
     * 设置毕业专业
     * 
     * @param major
     *          毕业专业
     */
    public void setMajor(String major) {
        this.major = major;
    }

    /**
     * 获取学历
     * 
     * @return 学历
     */
    public String getEducation() {
        return this.education;
    }

    /**
     * 设置学历
     * 
     * @param education
     *          学历
     */
    public void setEducation(String education) {
        this.education = education;
    }

    /**
     * 获取出生日期
     * 
     * @return 出生日期
     */
    public String getBirthday() {
        return this.birthday;
    }

    /**
     * 设置出生日期
     * 
     * @param birthday
     *          出生日期
     */
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    /**
     * 获取邀约时间
     * 
     * @return 邀约时间
     */
    public Date getInviteTime() {
        return this.inviteTime;
    }

    /**
     * 设置邀约时间
     * 
     * @param inviteTime
     *          邀约时间
     */
    public void setInviteTime(Date inviteTime) {
        this.inviteTime = inviteTime;
    }

    /**
     * 获取面试时间
     * 
     * @return 面试时间
     */
    public Date getInterviewTime() {
        return this.interviewTime;
    }

    /**
     * 设置面试时间
     * 
     * @param interviewTime
     *          面试时间
     */
    public void setInterviewTime(Date interviewTime) {
        this.interviewTime = interviewTime;
    }

    /**
     * 获取面试是否通过 1通过 0未通过
     * 
     * @return 面试是否通过 1通过 0未通过
     */
    public Integer getIsPass() {
        return this.isPass;
    }

    /**
     * 设置面试是否通过 1通过 0未通过
     * 
     * @param isPass
     *          面试是否通过 1通过 0未通过
     */
    public void setIsPass(Integer isPass) {
        this.isPass = isPass;
    }

    /**
     * 获取是否入职  1入职 0未入职
     * 
     * @return 是否入职  1入职 0未入职
     */
    public Integer getIsEntry() {
        return this.isEntry;
    }

    /**
     * 设置是否入职  1入职 0未入职
     * 
     * @param isEntry
     *          是否入职  1入职 0未入职
     */
    public void setIsEntry(Integer isEntry) {
        this.isEntry = isEntry;
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