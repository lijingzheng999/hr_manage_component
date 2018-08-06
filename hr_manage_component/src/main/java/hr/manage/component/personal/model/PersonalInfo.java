package hr.manage.component.personal.model;

import java.util.Date;

import net.paoding.rose.jade.plugin.sql.annotations.Column;
import net.paoding.rose.jade.plugin.sql.annotations.Table;
/**
 * personal_info
 * 
 * @author 
 * @version 1.0.0 2018-07-24
 */
@Table(value = "personal_info")
public class PersonalInfo implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -7685182439356101440L;

    /** 表的主键 */
    @Column(pk=true,value = "id")
    private Integer id;

    /** 员工编号 项目+部门+自增编号 */
    @Column(value = "employee_number")
    private String employeeNumber;

    /** 姓名 */
    @Column(value = "name")
    private String name;

    /** 联系电话-员工可修改 */
    @Column(value = "phone")
    private String phone;

    /** 邮箱 -员工可修改 */
    @Column(value = "email")
    private String email;

    /** 身份证-员工可修改  */
    @Column(value = "identity_card")
    private String identityCard;

    /** 性别 */
    @Column(value = "sex")
    private String sex;

    /** 年龄 */
    @Column(value = "age")
    private Integer age;

    /** 出生日期 */
    @Column(value = "birthday")
    private String birthday;

    /** 户口性质-员工可修改  */
    @Column(value = "home_property")
    private String homeProperty;

    /** 籍贯-员工可修改  */
    @Column(value = "native_place")
    private String nativePlace;

    /** 婚姻状况 -员工可修改 */
    @Column(value = "marriage")
    private String marriage;

    /** 民族 -员工可修改 */
    @Column(value = "nation")
    private String nation;

    /** 职称 -员工可修改 */
    @Column(value = "title")
    private String title;

    /** 学历 -员工可修改 */
    @Column(value = "education")
    private String education;

    /** 毕业学校-员工可修改  */
    @Column(value = "school")
    private String school;

    /** 毕业专业 -员工可修改 */
    @Column(value = "major")
    private String major;

    /** 英语 -员工可修改 */
    @Column(value = "english")
    private String english;

    /** 毕业时间 -员工可修改 */
    @Column(value = "graduation_time")
    private Date graduationTime;

    /** 工作年限 */
    @Column(value = "working_life")
    private String workingLife;

    /** 联系地址 -员工可修改 */
    @Column(value = "contact_address")
    private String contactAddress;

    /** 户籍地址 -员工可修改 */
    @Column(value = "home_address")
    private String homeAddress;

    /** 紧急联系人 -员工可修改 */
    @Column(value = "contact")
    private String contact;

    /** 紧急联系人电话 -员工可修改 */
    @Column(value = "contact_phone")
    private String contactPhone;

    /** 备注 -员工可修改 */
    @Column(value = "memo")
    private String memo;

    /** 是否删除 1未删除 0已删除 */
    @Column(value = "is_del")
    private Integer isDel;

    /** 编辑时间 */
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
     * 获取员工编号 项目+部门+自增编号
     * 
     * @return 员工编号 项目+部门+自增编号
     */
    public String getEmployeeNumber() {
        return this.employeeNumber;
    }

    /**
     * 设置员工编号 项目+部门+自增编号
     * 
     * @param employeeNumber
     *          员工编号 项目+部门+自增编号
     */
    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
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
     * 获取身份证
     * 
     * @return 身份证
     */
    public String getIdentityCard() {
        return this.identityCard;
    }

    /**
     * 设置身份证
     * 
     * @param identityCard
     *          身份证
     */
    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
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
     * 获取年龄
     * 
     * @return 年龄
     */
    public Integer getAge() {
        return this.age;
    }

    /**
     * 设置年龄
     * 
     * @param age
     *          年龄
     */
    public void setAge(Integer age) {
        this.age = age;
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
     * 获取户口性质
     * 
     * @return 户口性质
     */
    public String getHomeProperty() {
        return this.homeProperty;
    }

    /**
     * 设置户口性质
     * 
     * @param homeProperty
     *          户口性质
     */
    public void setHomeProperty(String homeProperty) {
        this.homeProperty = homeProperty;
    }

    /**
     * 获取籍贯
     * 
     * @return 籍贯
     */
    public String getNativePlace() {
        return this.nativePlace;
    }

    /**
     * 设置籍贯
     * 
     * @param nativePlace
     *          籍贯
     */
    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace;
    }

    /**
     * 获取婚姻状况
     * 
     * @return 婚姻状况
     */
    public String getMarriage() {
        return this.marriage;
    }

    /**
     * 设置婚姻状况
     * 
     * @param marriage
     *          婚姻状况
     */
    public void setMarriage(String marriage) {
        this.marriage = marriage;
    }

    /**
     * 获取民族
     * 
     * @return 民族
     */
    public String getNation() {
        return this.nation;
    }

    /**
     * 设置民族
     * 
     * @param nation
     *          民族
     */
    public void setNation(String nation) {
        this.nation = nation;
    }

    /**
     * 获取职称
     * 
     * @return 职称
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * 设置职称
     * 
     * @param title
     *          职称
     */
    public void setTitle(String title) {
        this.title = title;
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
     * 获取英语
     * 
     * @return 英语
     */
    public String getEnglish() {
        return this.english;
    }

    /**
     * 设置英语
     * 
     * @param english
     *          英语
     */
    public void setEnglish(String english) {
        this.english = english;
    }

    /**
     * 获取毕业时间
     * 
     * @return 毕业时间
     */
    public Date getGraduationTime() {
        return this.graduationTime;
    }

    /**
     * 设置毕业时间
     * 
     * @param graduationTime
     *          毕业时间
     */
    public void setGraduationTime(Date graduationTime) {
        this.graduationTime = graduationTime;
    }

    /**
     * 获取工作年限
     * 
     * @return 工作年限
     */
    public String getWorkingLife() {
        return this.workingLife;
    }

    /**
     * 设置工作年限
     * 
     * @param workingLife
     *          工作年限
     */
    public void setWorkingLife(String workingLife) {
        this.workingLife = workingLife;
    }

    /**
     * 获取联系地址
     * 
     * @return 联系地址
     */
    public String getContactAddress() {
        return this.contactAddress;
    }

    /**
     * 设置联系地址
     * 
     * @param contactAddress
     *          联系地址
     */
    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
    }

    /**
     * 获取户籍地址
     * 
     * @return 户籍地址
     */
    public String getHomeAddress() {
        return this.homeAddress;
    }

    /**
     * 设置户籍地址
     * 
     * @param homeAddress
     *          户籍地址
     */
    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    /**
     * 获取紧急联系人
     * 
     * @return 紧急联系人
     */
    public String getContact() {
        return this.contact;
    }

    /**
     * 设置紧急联系人
     * 
     * @param contact
     *          紧急联系人
     */
    public void setContact(String contact) {
        this.contact = contact;
    }

    /**
     * 获取紧急联系人电话
     * 
     * @return 紧急联系人电话
     */
    public String getContactPhone() {
        return this.contactPhone;
    }

    /**
     * 设置紧急联系人电话
     * 
     * @param contactPhone
     *          紧急联系人电话
     */
    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    /**
     * 获取备注
     * 
     * @return 备注
     */
    public String getMemo() {
        return this.memo;
    }

    /**
     * 设置备注
     * 
     * @param memo
     *          备注
     */
    public void setMemo(String memo) {
        this.memo = memo;
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
     * 获取编辑时间
     * 
     * @return 编辑时间
     */
    public Date getUpdateTime() {
        return this.updateTime;
    }

    /**
     * 设置编辑时间
     * 
     * @param updateTime
     *          编辑时间
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