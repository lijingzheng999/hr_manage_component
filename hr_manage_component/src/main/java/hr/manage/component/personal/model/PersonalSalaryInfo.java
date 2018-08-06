package hr.manage.component.personal.model;
import java.math.BigDecimal;
import java.util.Date;

import net.paoding.rose.jade.plugin.sql.annotations.Column;
import net.paoding.rose.jade.plugin.sql.annotations.Table;

/**
 * personal_salary_info
 * 
 * @author 
 * @version 1.0.0 2018-07-24
 */

@Table(value = "personal_salary_info")
public class PersonalSalaryInfo implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 642054784201451770L;

    /** 表的主键 */
    @Column(pk=true,value = "id")
    private Integer id;

    /** 员工信息表ID */
    @Column(value = "personal_info_id")
    private Integer personalInfoId;

    /** 入职时间 */
    @Column(value = "entry_time")
    private Date entryTime;

    /** 到岗时间 */
    @Column(value = "arrival_time")
    private Date arrivalTime;

    /** 转正时间 */
    @Column(value = "worker_time")
    private Date workerTime;

    /** 工龄 */
    @Column(value = "working_years")
    private BigDecimal workingYears;

    /** 缴纳社保起始月份 */
    @Column(value = "insurance_begin_date")
    private String insuranceBeginDate;

    /** 社保缴纳地点 */
    @Column(value = "insurance_place")
    private String insurancePlace;

    /** 招商银行卡号 -员工可修改*/
    @Column(value = "bank_card_number")
    private String bankCardNumber;

    /** 开户行 -员工可修改*/
    @Column(value = "bank_open_place")
    private String bankOpenPlace;

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
    private Integer basePay;

    /** 绩效工资 */
    @Column(value = "merit_pay")
    private Integer meritPay;

    /** 补贴 */
    @Column(value = "subsidy")
    private Integer subsidy;

    /** 转正工资 */
    @Column(value = "worker_pay")
    private Integer workerPay;

    /** 试用期工资 */
    @Column(value = "probationary_pay")
    private Integer probationaryPay;

    /** 结算价 */
    @Column(value = "settlement_price")
    private BigDecimal settlementPrice;

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
     * 获取入职时间
     * 
     * @return 入职时间
     */
    public Date getEntryTime() {
        return this.entryTime;
    }

    /**
     * 设置入职时间
     * 
     * @param entryTime
     *          入职时间
     */
    public void setEntryTime(Date entryTime) {
        this.entryTime = entryTime;
    }

    /**
     * 获取到岗时间
     * 
     * @return 到岗时间
     */
    public Date getArrivalTime() {
        return this.arrivalTime;
    }

    /**
     * 设置到岗时间
     * 
     * @param arrivalTime
     *          到岗时间
     */
    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    /**
     * 获取转正时间
     * 
     * @return 转正时间
     */
    public Date getWorkerTime() {
        return this.workerTime;
    }

    /**
     * 设置转正时间
     * 
     * @param workerTime
     *          转正时间
     */
    public void setWorkerTime(Date workerTime) {
        this.workerTime = workerTime;
    }

    /**
     * 获取工龄
     * 
     * @return 工龄
     */
    public BigDecimal getWorkingYears() {
        return this.workingYears;
    }

    /**
     * 设置工龄
     * 
     * @param workingYears
     *          工龄
     */
    public void setWorkingYears(BigDecimal workingYears) {
        this.workingYears = workingYears;
    }

    /**
     * 获取缴纳社保起始月份
     * 
     * @return 缴纳社保起始月份
     */
    public String getInsuranceBeginDate() {
        return this.insuranceBeginDate;
    }

    /**
     * 设置缴纳社保起始月份
     * 
     * @param insuranceBeginDate
     *          缴纳社保起始月份
     */
    public void setInsuranceBeginDate(String insuranceBeginDate) {
        this.insuranceBeginDate = insuranceBeginDate;
    }

    /**
     * 获取社保缴纳地点
     * 
     * @return 社保缴纳地点
     */
    public String getInsurancePlace() {
        return this.insurancePlace;
    }

    /**
     * 设置社保缴纳地点
     * 
     * @param insurancePlace
     *          社保缴纳地点
     */
    public void setInsurancePlace(String insurancePlace) {
        this.insurancePlace = insurancePlace;
    }

    /**
     * 获取招商银行卡号
     * 
     * @return 招商银行卡号
     */
    public String getBankCardNumber() {
        return this.bankCardNumber;
    }

    /**
     * 设置招商银行卡号
     * 
     * @param bankCardNumber
     *          招商银行卡号
     */
    public void setBankCardNumber(String bankCardNumber) {
        this.bankCardNumber = bankCardNumber;
    }

    /**
     * 获取开户行
     * 
     * @return 开户行
     */
    public String getBankOpenPlace() {
        return this.bankOpenPlace;
    }

    /**
     * 设置开户行
     * 
     * @param bankOpenPlace
     *          开户行
     */
    public void setBankOpenPlace(String bankOpenPlace) {
        this.bankOpenPlace = bankOpenPlace;
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
    public Integer getBasePay() {
        return this.basePay;
    }

    /**
     * 设置基本工资
     * 
     * @param basePay
     *          基本工资
     */
    public void setBasePay(Integer basePay) {
        this.basePay = basePay;
    }

    /**
     * 获取绩效工资
     * 
     * @return 绩效工资
     */
    public Integer getMeritPay() {
        return this.meritPay;
    }

    /**
     * 设置绩效工资
     * 
     * @param meritPay
     *          绩效工资
     */
    public void setMeritPay(Integer meritPay) {
        this.meritPay = meritPay;
    }

    /**
     * 获取补贴
     * 
     * @return 补贴
     */
    public Integer getSubsidy() {
        return this.subsidy;
    }

    /**
     * 设置补贴
     * 
     * @param subsidy
     *          补贴
     */
    public void setSubsidy(Integer subsidy) {
        this.subsidy = subsidy;
    }

    /**
     * 获取转正工资
     * 
     * @return 转正工资
     */
    public Integer getWorkerPay() {
        return this.workerPay;
    }

    /**
     * 设置转正工资
     * 
     * @param workerPay
     *          转正工资
     */
    public void setWorkerPay(Integer workerPay) {
        this.workerPay = workerPay;
    }

    /**
     * 获取试用期工资
     * 
     * @return 试用期工资
     */
    public Integer getProbationaryPay() {
        return this.probationaryPay;
    }

    /**
     * 设置试用期工资
     * 
     * @param probationaryPay
     *          试用期工资
     */
    public void setProbationaryPay(Integer probationaryPay) {
        this.probationaryPay = probationaryPay;
    }

    /**
     * 获取结算价
     * 
     * @return 结算价
     */
    public BigDecimal getSettlementPrice() {
        return this.settlementPrice;
    }

    /**
     * 设置结算价
     * 
     * @param settlementPrice
     *          结算价
     */
    public void setSettlementPrice(BigDecimal settlementPrice) {
        this.settlementPrice = settlementPrice;
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