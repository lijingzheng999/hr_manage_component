package hr.manage.component.recruit.model;

import java.math.BigDecimal;
import java.util.Date;

import net.paoding.rose.jade.plugin.sql.annotations.Column;
import net.paoding.rose.jade.plugin.sql.annotations.Table;
/**
 * resume_interview
 * 
 * @author 
 * @version 1.0.0 2018-08-21
 */
@Table(value = "resume_interview")
public class ResumeInterview implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -4239596244749550609L;

    /** 表的主键 */
    @Column(pk=true,value = "id")
    private Integer id;

    /** 简历表的主键 */
    @Column(value = "resume_id")
    private Integer resumeId;

    /** 姓名 */
    @Column(value = "name")
    private String name;

    /** 性别 */
    @Column(value = "sex")
    private String sex;

    /** 首日回访到岗情况（是否、时间） */
    @Column(value = "first_time")
    private String firstTime;

    /** 办理入职时间 */
    @Column(value = "entry_time")
    private String entryTime;

    /** 预计到岗时间 */
    @Column(value = "first_arrival_time")
    private String firstArrivalTime;

    /** 最终沟通时间 */
    @Column(value = "final_communicate_time")
    private String finalCommunicateTime;

    /** 最终沟通结果 */
    @Column(value = "final_communicate_result")
    private String finalCommunicateResult;

    /** 初步沟通时间 */
    @Column(value = "first_communicate_time")
    private String firstCommunicateTime;

    /** 初步沟通结果 */
    @Column(value = "first_communicate_result")
    private String firstCommunicateResult;

    /** 外派单位 */
    @Column(value = "expatriate_unit")
    private String expatriateUnit;

    /** 面试时间 */
    @Column(value = "interview_time")
    private String interviewTime;

    /** 驻场位置 */
    @Column(value = "location")
    private String location;

    /** 岗位名称(职位) */
    @Column(value = "position")
    private String position;

    /** 级别 */
    @Column(value = "level")
    private String level;

    /** 期望薪资 */
    @Column(value = "want_salary")
    private String wantSalary;

    /** 最终薪资 */
    @Column(value = "final_salary")
    private String finalSalary;

    /** 五险 */
    @Column(value = "insurance")
    private String insurance;

    /** 试用期 */
    @Column(value = "probation_period")
    private String probationPeriod;

    /** 试用期福利 */
    @Column(value = "probation_period_welfare")
    private String probationPeriodWelfare;

    /** 转正福利 */
    @Column(value = "worker_welfare")
    private String workerWelfare;

    /** 基本工资 */
    @Column(value = "base_pay")
    private BigDecimal basePay;

    /** 绩效工资 */
    @Column(value = "merit_pay")
    private BigDecimal meritPay;

    /** 补贴 */
    @Column(value = "subsidy")
    private BigDecimal subsidy;

    /** 试用期工资 */
    @Column(value = "probationary_pay")
    private BigDecimal probationaryPay;

    /** 联系电话 */
    @Column(value = "phone")
    private String phone;

    /** 邮箱 */
    @Column(value = "email")
    private String email;

    /** 招聘渠道 */
    @Column(value = "recruit_channel")
    private String recruitChannel;

    /** 项目经理 */
    @Column(value = "project_manager")
    private String projectManager;

    /** 备注 */
    @Column(value = "memo")
    private String memo;

    /** 入职状态：2：已入职，1：待入职；0：未入职（未入职来个原因） */
    @Column(value = "status")
    private Integer status;

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
     * 获取简历表的主键
     * 
     * @return 简历表的主键
     */
    public Integer getResumeId() {
        return this.resumeId;
    }

    /**
     * 设置简历表的主键
     * 
     * @param resumeId
     *          简历表的主键
     */
    public void setResumeId(Integer resumeId) {
        this.resumeId = resumeId;
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
     * @param value
     *          姓名
     */
    public void setName(String name) {
        this.name = name;
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
     * 获取首日回访到岗情况（是否、时间）
     * 
     * @return 首日回访到岗情况（是否、时间）
     */
    public String getFirstTime() {
        return this.firstTime;
    }

    /**
     * 设置首日回访到岗情况（是否、时间）
     * 
     * @param firstTime
     *          首日回访到岗情况（是否、时间）
     */
    public void setFirstTime(String firstTime) {
        this.firstTime = firstTime;
    }

    /**
     * 获取办理入职时间
     * 
     * @return 办理入职时间
     */
    public String getEntryTime() {
        return this.entryTime;
    }

    /**
     * 设置办理入职时间
     * 
     * @param entryTime
     *          办理入职时间
     */
    public void setEntryTime(String entryTime) {
        this.entryTime = entryTime;
    }

    /**
     * 获取预计到岗时间
     * 
     * @return 预计到岗时间
     */
    public String getFirstArrivalTime() {
        return this.firstArrivalTime;
    }

    /**
     * 设置预计到岗时间
     * 
     * @param firstArrivalTime
     *          预计到岗时间
     */
    public void setFirstArrivalTime(String firstArrivalTime) {
        this.firstArrivalTime = firstArrivalTime;
    }

    /**
     * 获取最终沟通时间
     * 
     * @return 最终沟通时间
     */
    public String getFinalCommunicateTime() {
        return this.finalCommunicateTime;
    }

    /**
     * 设置最终沟通时间
     * 
     * @param finalCommunicateTime
     *          最终沟通时间
     */
    public void setFinalCommunicateTime(String finalCommunicateTime) {
        this.finalCommunicateTime = finalCommunicateTime;
    }

    /**
     * 获取最终沟通结果
     * 
     * @return 最终沟通结果
     */
    public String getFinalCommunicateResult() {
        return this.finalCommunicateResult;
    }

    /**
     * 设置最终沟通结果
     * 
     * @param finalCommunicateResult
     *          最终沟通结果
     */
    public void setFinalCommunicateResult(String finalCommunicateResult) {
        this.finalCommunicateResult = finalCommunicateResult;
    }

    /**
     * 获取初步沟通时间
     * 
     * @return 初步沟通时间
     */
    public String getFirstCommunicateTime() {
        return this.firstCommunicateTime;
    }

    /**
     * 设置初步沟通时间
     * 
     * @param firstCommunicateTime
     *          初步沟通时间
     */
    public void setFirstCommunicateTime(String firstCommunicateTime) {
        this.firstCommunicateTime = firstCommunicateTime;
    }

    /**
     * 获取初步沟通结果
     * 
     * @return 初步沟通结果
     */
    public String getFirstCommunicateResult() {
        return this.firstCommunicateResult;
    }

    /**
     * 设置初步沟通结果
     * 
     * @param firstCommunicateResult
     *          初步沟通结果
     */
    public void setFirstCommunicateResult(String firstCommunicateResult) {
        this.firstCommunicateResult = firstCommunicateResult;
    }

    /**
     * 获取外派单位
     * 
     * @return 外派单位
     */
    public String getExpatriateUnit() {
        return this.expatriateUnit;
    }

    /**
     * 设置外派单位
     * 
     * @param expatriateUnit
     *          外派单位
     */
    public void setExpatriateUnit(String expatriateUnit) {
        this.expatriateUnit = expatriateUnit;
    }

    /**
     * 获取面试时间
     * 
     * @return 面试时间
     */
    public String getInterviewTime() {
        return this.interviewTime;
    }

    /**
     * 设置面试时间
     * 
     * @param interviewTime
     *          面试时间
     */
    public void setInterviewTime(String interviewTime) {
        this.interviewTime = interviewTime;
    }

    /**
     * 获取驻场位置
     * 
     * @return 驻场位置
     */
    public String getLocation() {
        return this.location;
    }

    /**
     * 设置驻场位置
     * 
     * @param location
     *          驻场位置
     */
    public void setLocation(String location) {
        this.location = location;
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
     * 获取级别
     * 
     * @return 级别
     */
    public String getLevel() {
        return this.level;
    }

    /**
     * 设置级别
     * 
     * @param level
     *          级别
     */
    public void setLevel(String level) {
        this.level = level;
    }

    /**
     * 获取期望薪资
     * 
     * @return 期望薪资
     */
    public String getWantSalary() {
        return this.wantSalary;
    }

    /**
     * 设置期望薪资
     * 
     * @param wantSalary
     *          期望薪资
     */
    public void setWantSalary(String wantSalary) {
        this.wantSalary = wantSalary;
    }

    /**
     * 获取最终薪资
     * 
     * @return 最终薪资
     */
    public String getFinalSalary() {
        return this.finalSalary;
    }

    /**
     * 设置最终薪资
     * 
     * @param finalSalary
     *          最终薪资
     */
    public void setFinalSalary(String finalSalary) {
        this.finalSalary = finalSalary;
    }

    /**
     * 获取五险
     * 
     * @return 五险
     */
    public String getInsurance() {
        return this.insurance;
    }

    /**
     * 设置五险
     * 
     * @param insurance
     *          五险
     */
    public void setInsurance(String insurance) {
        this.insurance = insurance;
    }

    /**
     * 获取试用期
     * 
     * @return 试用期
     */
    public String getProbationPeriod() {
        return this.probationPeriod;
    }

    /**
     * 设置试用期
     * 
     * @param probationPeriod
     *          试用期
     */
    public void setProbationPeriod(String probationPeriod) {
        this.probationPeriod = probationPeriod;
    }

    /**
     * 获取试用期福利
     * 
     * @return 试用期福利
     */
    public String getProbationPeriodWelfare() {
        return this.probationPeriodWelfare;
    }

    /**
     * 设置试用期福利
     * 
     * @param probationPeriodWelfare
     *          试用期福利
     */
    public void setProbationPeriodWelfare(String probationPeriodWelfare) {
        this.probationPeriodWelfare = probationPeriodWelfare;
    }

    /**
     * 获取转正福利
     * 
     * @return 转正福利
     */
    public String getWorkerWelfare() {
        return this.workerWelfare;
    }

    /**
     * 设置转正福利
     * 
     * @param workerWelfare
     *          转正福利
     */
    public void setWorkerWelfare(String workerWelfare) {
        this.workerWelfare = workerWelfare;
    }

    /**
     * 获取基本工资
     * 
     * @return 基本工资
     */
    public BigDecimal getBasePay() {
        return this.basePay;
    }

    /**
     * 设置基本工资
     * 
     * @param basePay
     *          基本工资
     */
    public void setBasePay(BigDecimal basePay) {
        this.basePay = basePay;
    }

    /**
     * 获取绩效工资
     * 
     * @return 绩效工资
     */
    public BigDecimal getMeritPay() {
        return this.meritPay;
    }

    /**
     * 设置绩效工资
     * 
     * @param meritPay
     *          绩效工资
     */
    public void setMeritPay(BigDecimal meritPay) {
        this.meritPay = meritPay;
    }

    /**
     * 获取补贴
     * 
     * @return 补贴
     */
    public BigDecimal getSubsidy() {
        return this.subsidy;
    }

    /**
     * 设置补贴
     * 
     * @param subsidy
     *          补贴
     */
    public void setSubsidy(BigDecimal subsidy) {
        this.subsidy = subsidy;
    }

    /**
     * 获取试用期工资
     * 
     * @return 试用期工资
     */
    public BigDecimal getProbationaryPay() {
        return this.probationaryPay;
    }

    /**
     * 设置试用期工资
     * 
     * @param probationaryPay
     *          试用期工资
     */
    public void setProbationaryPay(BigDecimal probationaryPay) {
        this.probationaryPay = probationaryPay;
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
     * 获取招聘渠道
     * 
     * @return 招聘渠道
     */
    public String getRecruitChannel() {
        return this.recruitChannel;
    }

    /**
     * 设置招聘渠道
     * 
     * @param recruitChannel
     *          招聘渠道
     */
    public void setRecruitChannel(String recruitChannel) {
        this.recruitChannel = recruitChannel;
    }

    /**
     * 获取项目经理
     * 
     * @return 项目经理
     */
    public String getProjectManager() {
        return this.projectManager;
    }

    /**
     * 设置项目经理
     * 
     * @param projectManager
     *          项目经理
     */
    public void setProjectManager(String projectManager) {
        this.projectManager = projectManager;
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
     * 获取入职状态：2：已入职，1：待入职；0：未入职（未入职来个原因）
     * 
     * @return 入职状态：2：已入职
     */
    public Integer getStatus() {
        return this.status;
    }

    /**
     * 设置入职状态：2：已入职，1：待入职；0：未入职（未入职来个原因）
     * 
     * @param status
     *          入职状态：2：已入职
     */
    public void setStatus(Integer status) {
        this.status = status;
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