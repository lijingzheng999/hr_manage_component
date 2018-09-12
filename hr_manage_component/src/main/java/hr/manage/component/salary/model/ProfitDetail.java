package hr.manage.component.salary.model;
import java.math.BigDecimal;
import java.util.Date;

import net.paoding.rose.jade.plugin.sql.annotations.Column;
import net.paoding.rose.jade.plugin.sql.annotations.Table;
/**
 * profit_detail
 * 
 * @author 
 * @version 1.0.0 2018-09-12
 */
@Table(value = "profit_detail")
public class ProfitDetail implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 5389254880879497174L;

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

    /** 外派单位 */
    @Column(value = "expatriate_unit")
    private String expatriateUnit;

    /** 部门 */
    @Column(value = "department")
    private String department;

    /** 工作地点 */
    @Column(value = "working_place")
    private String workingPlace;

    /** 职位 */
    @Column(value = "position")
    private String position;

    /** 级别 */
    @Column(value = "level")
    private String level;

    /** 试用期工资 */
    @Column(value = "probationary_pay")
    private BigDecimal probationaryPay;

    /** 基本工资 */
    @Column(value = "base_pay")
    private BigDecimal basePay;

    /** 绩效工资 */
    @Column(value = "merit_pay")
    private BigDecimal meritPay;

    /** 交通补助 */
    @Column(value = "traffic_subsidy")
    private BigDecimal trafficSubsidy;

    /** 电脑补助 */
    @Column(value = "computer_subsidy")
    private BigDecimal computerSubsidy;

    /** 餐补 */
    @Column(value = "meal_subsidy")
    private BigDecimal mealSubsidy;

    /** 其他工资 */
    @Column(value = "other_pay")
    private BigDecimal otherPay;

    /** 试用期社保 */
    @Column(value = "probationary_insurance")
    private BigDecimal probationaryInsurance;

    /** 转正后薪资 */
    @Column(value = "worker_pay")
    private BigDecimal workerPay;

    /** 转正后社保 */
    @Column(value = "social_security")
    private BigDecimal socialSecurity;

    /** 公积金 */
    @Column(value = "housing_pay")
    private BigDecimal housingPay;

    /** 全通结算价 */
    @Column(value = "settlement_price")
    private BigDecimal settlementPrice;

    /** 结算天数 */
    @Column(value = "settlement_days")
    private BigDecimal settlementDays;

    /** 结算日单价 */
    @Column(value = "settlement_day_price")
    private BigDecimal settlementDayPrice;

    /** 试用期工会经费 */
    @Column(value = "probationary_union_pay")
    private BigDecimal probationaryUnionPay;

    /** 试用期残疾人就业保障金 */
    @Column(value = "probationary_disabled_pay")
    private BigDecimal probationaryDisabledPay;

    /** 试用期增值税及附加税 */
    @Column(value = "probationary_tax_pay")
    private BigDecimal probationaryTaxPay;

    /** 转正后工会经费 */
    @Column(value = "union_pay")
    private BigDecimal unionPay;

    /** 转正后残疾人就业保障金 */
    @Column(value = "disabled_pay")
    private BigDecimal disabledPay;

    /** 转正后增值税及附加税 */
    @Column(value = "tax_pay")
    private BigDecimal taxPay;

    /** 试用期利润 */
    @Column(value = "probationary_profit")
    private BigDecimal probationaryProfit;

    /** 试用期利润率 */
    @Column(value = "probationary_profit_rate")
    private BigDecimal probationaryProfitRate;

    /** 转正后利润 */
    @Column(value = "profit")
    private BigDecimal profit;

    /** 转正后利润率 */
    @Column(value = "profit_rate")
    private BigDecimal profitRate;

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
     * 获取部门
     * 
     * @return 部门
     */
    public String getDepartment() {
        return this.department;
    }

    /**
     * 设置部门
     * 
     * @param department
     *          部门
     */
    public void setDepartment(String department) {
        this.department = department;
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
     * 获取职位
     * 
     * @return 职位
     */
    public String getPosition() {
        return this.position;
    }

    /**
     * 设置职位
     * 
     * @param position
     *          职位
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
     * 获取试用期社保
     * 
     * @return 试用期社保
     */
    public BigDecimal getProbationaryInsurance() {
        return this.probationaryInsurance;
    }

    /**
     * 设置试用期社保
     * 
     * @param probationaryInsurance
     *          试用期社保
     */
    public void setProbationaryInsurance(BigDecimal probationaryInsurance) {
        this.probationaryInsurance = probationaryInsurance;
    }

    /**
     * 获取转正后薪资
     * 
     * @return 转正后薪资
     */
    public BigDecimal getWorkerPay() {
        return this.workerPay;
    }

    /**
     * 设置转正后薪资
     * 
     * @param workerPay
     *          转正后薪资
     */
    public void setWorkerPay(BigDecimal workerPay) {
        this.workerPay = workerPay;
    }

    /**
     * 获取转正后社保
     * 
     * @return 转正后社保
     */
    public BigDecimal getSocialSecurity() {
        return this.socialSecurity;
    }

    /**
     * 设置转正后社保
     * 
     * @param socialSecurity
     *          转正后社保
     */
    public void setSocialSecurity(BigDecimal socialSecurity) {
        this.socialSecurity = socialSecurity;
    }

    /**
     * 获取公积金
     * 
     * @return 公积金
     */
    public BigDecimal getHousingPay() {
        return this.housingPay;
    }

    /**
     * 设置公积金
     * 
     * @param housingPay
     *          公积金
     */
    public void setHousingPay(BigDecimal housingPay) {
        this.housingPay = housingPay;
    }

    /**
     * 获取全通结算价
     * 
     * @return 全通结算价
     */
    public BigDecimal getSettlementPrice() {
        return this.settlementPrice;
    }

    /**
     * 设置全通结算价
     * 
     * @param settlementPrice
     *          全通结算价
     */
    public void setSettlementPrice(BigDecimal settlementPrice) {
        this.settlementPrice = settlementPrice;
    }

    /**
     * 获取结算天数
     * 
     * @return 结算天数
     */
    public BigDecimal getSettlementDays() {
        return this.settlementDays;
    }

    /**
     * 设置结算天数
     * 
     * @param settlementDays
     *          结算天数
     */
    public void setSettlementDays(BigDecimal settlementDays) {
        this.settlementDays = settlementDays;
    }

    /**
     * 获取结算日单价
     * 
     * @return 结算日单价
     */
    public BigDecimal getSettlementDayPrice() {
        return this.settlementDayPrice;
    }

    /**
     * 设置结算日单价
     * 
     * @param settlementDayPrice
     *          结算日单价
     */
    public void setSettlementDayPrice(BigDecimal settlementDayPrice) {
        this.settlementDayPrice = settlementDayPrice;
    }

    /**
     * 获取试用期工会经费
     * 
     * @return 试用期工会经费
     */
    public BigDecimal getProbationaryUnionPay() {
        return this.probationaryUnionPay;
    }

    /**
     * 设置试用期工会经费
     * 
     * @param probationaryUnionPay
     *          试用期工会经费
     */
    public void setProbationaryUnionPay(BigDecimal probationaryUnionPay) {
        this.probationaryUnionPay = probationaryUnionPay;
    }

    /**
     * 获取试用期残疾人就业保障金
     * 
     * @return 试用期残疾人就业保障金
     */
    public BigDecimal getProbationaryDisabledPay() {
        return this.probationaryDisabledPay;
    }

    /**
     * 设置试用期残疾人就业保障金
     * 
     * @param probationaryDisabledPay
     *          试用期残疾人就业保障金
     */
    public void setProbationaryDisabledPay(BigDecimal probationaryDisabledPay) {
        this.probationaryDisabledPay = probationaryDisabledPay;
    }

    /**
     * 获取试用期增值税及附加税
     * 
     * @return 试用期增值税及附加税
     */
    public BigDecimal getProbationaryTaxPay() {
        return this.probationaryTaxPay;
    }

    /**
     * 设置试用期增值税及附加税
     * 
     * @param probationaryTaxPay
     *          试用期增值税及附加税
     */
    public void setProbationaryTaxPay(BigDecimal probationaryTaxPay) {
        this.probationaryTaxPay = probationaryTaxPay;
    }

    /**
     * 获取转正后工会经费
     * 
     * @return 转正后工会经费
     */
    public BigDecimal getUnionPay() {
        return this.unionPay;
    }

    /**
     * 设置转正后工会经费
     * 
     * @param unionPay
     *          转正后工会经费
     */
    public void setUnionPay(BigDecimal unionPay) {
        this.unionPay = unionPay;
    }

    /**
     * 获取转正后残疾人就业保障金
     * 
     * @return 转正后残疾人就业保障金
     */
    public BigDecimal getDisabledPay() {
        return this.disabledPay;
    }

    /**
     * 设置转正后残疾人就业保障金
     * 
     * @param disabledPay
     *          转正后残疾人就业保障金
     */
    public void setDisabledPay(BigDecimal disabledPay) {
        this.disabledPay = disabledPay;
    }

    /**
     * 获取转正后增值税及附加税
     * 
     * @return 转正后增值税及附加税
     */
    public BigDecimal getTaxPay() {
        return this.taxPay;
    }

    /**
     * 设置转正后增值税及附加税
     * 
     * @param taxPay
     *          转正后增值税及附加税
     */
    public void setTaxPay(BigDecimal taxPay) {
        this.taxPay = taxPay;
    }

    /**
     * 获取试用期利润
     * 
     * @return 试用期利润
     */
    public BigDecimal getProbationaryProfit() {
        return this.probationaryProfit;
    }

    /**
     * 设置试用期利润
     * 
     * @param probationaryProfit
     *          试用期利润
     */
    public void setProbationaryProfit(BigDecimal probationaryProfit) {
        this.probationaryProfit = probationaryProfit;
    }

    /**
     * 获取试用期利润率
     * 
     * @return 试用期利润率
     */
    public BigDecimal getProbationaryProfitRate() {
        return this.probationaryProfitRate;
    }

    /**
     * 设置试用期利润率
     * 
     * @param probationaryProfitRate
     *          试用期利润率
     */
    public void setProbationaryProfitRate(BigDecimal probationaryProfitRate) {
        this.probationaryProfitRate = probationaryProfitRate;
    }

    /**
     * 获取转正后利润
     * 
     * @return 转正后利润
     */
    public BigDecimal getProfit() {
        return this.profit;
    }

    /**
     * 设置转正后利润
     * 
     * @param profit
     *          转正后利润
     */
    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }

    /**
     * 获取转正后利润率
     * 
     * @return 转正后利润率
     */
    public BigDecimal getProfitRate() {
        return this.profitRate;
    }

    /**
     * 设置转正后利润率
     * 
     * @param profitRate
     *          转正后利润率
     */
    public void setProfitRate(BigDecimal profitRate) {
        this.profitRate = profitRate;
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