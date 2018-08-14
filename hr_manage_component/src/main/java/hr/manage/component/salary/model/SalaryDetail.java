package hr.manage.component.salary.model;

import java.math.BigDecimal;
import java.util.Date;

import net.paoding.rose.jade.plugin.sql.annotations.Column;
import net.paoding.rose.jade.plugin.sql.annotations.Table;

/**
 * salary_detail
 * 
 * @author 
 * @version 1.0.0 2018-08-14
 */
@Table(value = "salary_detail")
public class SalaryDetail implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -8308575140305303948L;

    /** 表的主键 */
    @Column(pk=true,value = "id")
    private Integer id;

    /** 员工信息表ID */
    @Column(value = "personal_info_id")
    private Integer personalInfoId;

    /** 账期 */
    @Column(value = "term")
    private String term;

    /** 结算开始时间 */
    @Column(value = "start_date")
    private Date startDate;

    /** 结算结束时间 */
    @Column(value = "end_date")
    private Date endDate;

    /** 姓名 */
    @Column(value = "name")
    private String name;

    /** 入职时间 */
    @Column(value = "entry_time")
    private Date entryTime;

    /** 外派单位 */
    @Column(value = "expatriate_unit")
    private String expatriateUnit;

    /** 试用期工资 */
    @Column(value = "probationary_pay")
    private BigDecimal probationaryPay;

    /** 基本工资 */
    @Column(value = "base_pay")
    private BigDecimal basePay;

    /** 绩效工资 */
    @Column(value = "merit_pay")
    private BigDecimal meritPay;

    /** 其他工资 */
    @Column(value = "other_pay")
    private BigDecimal otherPay;

    /** 交通补助 */
    @Column(value = "traffic_subsidy")
    private BigDecimal trafficSubsidy;

    /** 电脑补助 */
    @Column(value = "computer_subsidy")
    private BigDecimal computerSubsidy;

    /** 餐补 */
    @Column(value = "meal_subsidy")
    private BigDecimal mealSubsidy;

    /** 话补 */
    @Column(value = "phone_subsidy")
    private BigDecimal phoneSubsidy;

    /** 考勤扣款 */
    @Column(value = "attendance_deduction")
    private BigDecimal attendanceDeduction;

    /** 其它扣款 */
    @Column(value = "other_deduction")
    private BigDecimal otherDeduction;

    /** 本月应发工资 */
    @Column(value = "should_pay")
    private BigDecimal shouldPay;

    /** 养老个人合计 */
    @Column(value = "endowment")
    private BigDecimal endowment;

    /** 医疗个人合计 */
    @Column(value = "medical")
    private BigDecimal medical;

    /** 失业个人合计 */
    @Column(value = "unemployment")
    private BigDecimal unemployment;

    /** 公积金合计 */
    @Column(value = "accumulation_fund")
    private BigDecimal accumulationFund;

    /** 个人社保及公积金扣款合计 */
    @Column(value = "insurance_deduction")
    private BigDecimal insuranceDeduction;

    /** 个人需冲票 */
    @Column(value = "invoice")
    private BigDecimal invoice;

    /** 报税工资 */
    @Column(value = "tax_pay")
    private BigDecimal taxPay;

    /** 应纳税所得额 */
    @Column(value = "should_tax_amount")
    private BigDecimal shouldTaxAmount;

    /** 税率 */
    @Column(value = "tax")
    private BigDecimal tax;

    /** 速算扣除数 */
    @Column(value = "deduct_number")
    private BigDecimal deductNumber;

    /** 代扣代缴所得税 */
    @Column(value = "income_tax")
    private BigDecimal incomeTax;

    /** 实发工资 */
    @Column(value = "real_pay")
    private BigDecimal realPay;

    /** 招行代发 */
    @Column(value = "bank_pay")
    private BigDecimal bankPay;

    /** 现金 */
    @Column(value = "cash")
    private BigDecimal cash;

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
     * 获取账期
     * 
     * @return 账期
     */
    public String getTerm() {
        return this.term;
    }

    /**
     * 设置账期
     * 
     * @param term
     *          账期
     */
    public void setTerm(String term) {
        this.term = term;
    }

    /**
     * 获取结算开始时间
     * 
     * @return 结算开始时间
     */
    public Date getStartDate() {
        return this.startDate;
    }

    /**
     * 设置结算开始时间
     * 
     * @param startDate
     *          结算开始时间
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * 获取结算结束时间
     * 
     * @return 结算结束时间
     */
    public Date getEndDate() {
        return this.endDate;
    }

    /**
     * 设置结算结束时间
     * 
     * @param endDate
     *          结算结束时间
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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
     * 获取其他工资
     * 
     * @return 其他工资
     */
    public BigDecimal getOtherPay() {
        return this.otherPay;
    }

    /**
     * 设置其他工资
     * 
     * @param otherPay
     *          其他工资
     */
    public void setOtherPay(BigDecimal otherPay) {
        this.otherPay = otherPay;
    }

    /**
     * 获取交通补助
     * 
     * @return 交通补助
     */
    public BigDecimal getTrafficSubsidy() {
        return this.trafficSubsidy;
    }

    /**
     * 设置交通补助
     * 
     * @param trafficSubsidy
     *          交通补助
     */
    public void setTrafficSubsidy(BigDecimal trafficSubsidy) {
        this.trafficSubsidy = trafficSubsidy;
    }

    /**
     * 获取电脑补助
     * 
     * @return 电脑补助
     */
    public BigDecimal getComputerSubsidy() {
        return this.computerSubsidy;
    }

    /**
     * 设置电脑补助
     * 
     * @param computerSubsidy
     *          电脑补助
     */
    public void setComputerSubsidy(BigDecimal computerSubsidy) {
        this.computerSubsidy = computerSubsidy;
    }

    /**
     * 获取餐补
     * 
     * @return 餐补
     */
    public BigDecimal getMealSubsidy() {
        return this.mealSubsidy;
    }

    /**
     * 设置餐补
     * 
     * @param mealSubsidy
     *          餐补
     */
    public void setMealSubsidy(BigDecimal mealSubsidy) {
        this.mealSubsidy = mealSubsidy;
    }

    /**
     * 获取话补
     * 
     * @return 话补
     */
    public BigDecimal getPhoneSubsidy() {
        return this.phoneSubsidy;
    }

    /**
     * 设置话补
     * 
     * @param phoneSubsidy
     *          话补
     */
    public void setPhoneSubsidy(BigDecimal phoneSubsidy) {
        this.phoneSubsidy = phoneSubsidy;
    }

    /**
     * 获取考勤扣款
     * 
     * @return 考勤扣款
     */
    public BigDecimal getAttendanceDeduction() {
        return this.attendanceDeduction;
    }

    /**
     * 设置考勤扣款
     * 
     * @param attendanceDeduction
     *          考勤扣款
     */
    public void setAttendanceDeduction(BigDecimal attendanceDeduction) {
        this.attendanceDeduction = attendanceDeduction;
    }

    /**
     * 获取其它扣款
     * 
     * @return 其它扣款
     */
    public BigDecimal getOtherDeduction() {
        return this.otherDeduction;
    }

    /**
     * 设置其它扣款
     * 
     * @param otherDeduction
     *          其它扣款
     */
    public void setOtherDeduction(BigDecimal otherDeduction) {
        this.otherDeduction = otherDeduction;
    }

    /**
     * 获取本月应发工资
     * 
     * @return 本月应发工资
     */
    public BigDecimal getShouldPay() {
        return this.shouldPay;
    }

    /**
     * 设置本月应发工资
     * 
     * @param shouldPay
     *          本月应发工资
     */
    public void setShouldPay(BigDecimal shouldPay) {
        this.shouldPay = shouldPay;
    }

    /**
     * 获取养老个人合计
     * 
     * @return 养老个人合计
     */
    public BigDecimal getEndowment() {
        return this.endowment;
    }

    /**
     * 设置养老个人合计
     * 
     * @param endowment
     *          养老个人合计
     */
    public void setEndowment(BigDecimal endowment) {
        this.endowment = endowment;
    }

    /**
     * 获取医疗个人合计
     * 
     * @return 医疗个人合计
     */
    public BigDecimal getMedical() {
        return this.medical;
    }

    /**
     * 设置医疗个人合计
     * 
     * @param medical
     *          医疗个人合计
     */
    public void setMedical(BigDecimal medical) {
        this.medical = medical;
    }

    /**
     * 获取失业个人合计
     * 
     * @return 失业个人合计
     */
    public BigDecimal getUnemployment() {
        return this.unemployment;
    }

    /**
     * 设置失业个人合计
     * 
     * @param unemployment
     *          失业个人合计
     */
    public void setUnemployment(BigDecimal unemployment) {
        this.unemployment = unemployment;
    }

    /**
     * 获取公积金合计
     * 
     * @return 公积金合计
     */
    public BigDecimal getAccumulationFund() {
        return this.accumulationFund;
    }

    /**
     * 设置公积金合计
     * 
     * @param accumulationFund
     *          公积金合计
     */
    public void setAccumulationFund(BigDecimal accumulationFund) {
        this.accumulationFund = accumulationFund;
    }

    /**
     * 获取个人社保及公积金扣款合计
     * 
     * @return 个人社保及公积金扣款合计
     */
    public BigDecimal getInsuranceDeduction() {
        return this.insuranceDeduction;
    }

    /**
     * 设置个人社保及公积金扣款合计
     * 
     * @param insuranceDeduction
     *          个人社保及公积金扣款合计
     */
    public void setInsuranceDeduction(BigDecimal insuranceDeduction) {
        this.insuranceDeduction = insuranceDeduction;
    }

    /**
     * 获取个人需冲票
     * 
     * @return 个人需冲票
     */
    public BigDecimal getInvoice() {
        return this.invoice;
    }

    /**
     * 设置个人需冲票
     * 
     * @param invoice
     *          个人需冲票
     */
    public void setInvoice(BigDecimal invoice) {
        this.invoice = invoice;
    }

    /**
     * 获取报税工资
     * 
     * @return 报税工资
     */
    public BigDecimal getTaxPay() {
        return this.taxPay;
    }

    /**
     * 设置报税工资
     * 
     * @param taxPay
     *          报税工资
     */
    public void setTaxPay(BigDecimal taxPay) {
        this.taxPay = taxPay;
    }

    /**
     * 获取应纳税所得额
     * 
     * @return 应纳税所得额
     */
    public BigDecimal getShouldTaxAmount() {
        return this.shouldTaxAmount;
    }

    /**
     * 设置应纳税所得额
     * 
     * @param shouldTaxAmount
     *          应纳税所得额
     */
    public void setShouldTaxAmount(BigDecimal shouldTaxAmount) {
        this.shouldTaxAmount = shouldTaxAmount;
    }

    /**
     * 获取税率
     * 
     * @return 税率
     */
    public BigDecimal getTax() {
        return this.tax;
    }

    /**
     * 设置税率
     * 
     * @param tax
     *          税率
     */
    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    /**
     * 获取速算扣除数
     * 
     * @return 速算扣除数
     */
    public BigDecimal getDeductNumber() {
        return this.deductNumber;
    }

    /**
     * 设置速算扣除数
     * 
     * @param deductNumber
     *          速算扣除数
     */
    public void setDeductNumber(BigDecimal deductNumber) {
        this.deductNumber = deductNumber;
    }

    /**
     * 获取代扣代缴所得税
     * 
     * @return 代扣代缴所得税
     */
    public BigDecimal getIncomeTax() {
        return this.incomeTax;
    }

    /**
     * 设置代扣代缴所得税
     * 
     * @param incomeTax
     *          代扣代缴所得税
     */
    public void setIncomeTax(BigDecimal incomeTax) {
        this.incomeTax = incomeTax;
    }

    /**
     * 获取实发工资
     * 
     * @return 实发工资
     */
    public BigDecimal getRealPay() {
        return this.realPay;
    }

    /**
     * 设置实发工资
     * 
     * @param realPay
     *          实发工资
     */
    public void setRealPay(BigDecimal realPay) {
        this.realPay = realPay;
    }

    /**
     * 获取招行代发
     * 
     * @return 招行代发
     */
    public BigDecimal getBankPay() {
        return this.bankPay;
    }

    /**
     * 设置招行代发
     * 
     * @param bankPay
     *          招行代发
     */
    public void setBankPay(BigDecimal bankPay) {
        this.bankPay = bankPay;
    }

    /**
     * 获取现金
     * 
     * @return 现金
     */
    public BigDecimal getCash() {
        return this.cash;
    }

    /**
     * 设置现金
     * 
     * @param cash
     *          现金
     */
    public void setCash(BigDecimal cash) {
        this.cash = cash;
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