package hr.manage.component.salary.model;
import java.math.BigDecimal;
import java.util.Date;

import net.paoding.rose.jade.plugin.sql.annotations.Column;
import net.paoding.rose.jade.plugin.sql.annotations.Table;

/**
 * insurance_detail
 * 
 * @author 
 * @version 1.0.0 2018-09-06
 */
@Table(value = "insurance_detail")
public class InsuranceDetail implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -7308770596431717010L;

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

    /** 缴纳社保起始月份 */
    @Column(value = "insurance_begin_date")
    private String insuranceBeginDate;

    /** 实际缴纳社保起始月份 */
    @Column(value = "insurance_real_date")
    private String insuranceRealDate;

    /** 外派单位 */
    @Column(value = "expatriate_unit")
    private String expatriateUnit;

    /** 社保缴纳地点 */
    @Column(value = "insurance_place")
    private String insurancePlace;

    /** 代理公司 */
    @Column(value = "agency_company")
    private String agencyCompany;

    /** 代理费 */
    @Column(value = "agency_pay")
    private BigDecimal agencyPay;

    /** 养老基数 */
    @Column(value = "endowment_base")
    private BigDecimal endowmentBase;

    /** 养老单位比例 */
    @Column(value = "endowment_rate")
    private BigDecimal endowmentRate;

    /** 养老个人比例 */
    @Column(value = "endowment_rate_personal")
    private BigDecimal endowmentRatePersonal;

    /** 失业基数 */
    @Column(value = "unemployment_base")
    private BigDecimal unemploymentBase;

    /** 失业单位比例 */
    @Column(value = "unemployment_rate")
    private BigDecimal unemploymentRate;

    /** 失业个人比例 */
    @Column(value = "unemployment_rate_personal")
    private BigDecimal unemploymentRatePersonal;

    /** 工伤基数 */
    @Column(value = "work_injury_base")
    private BigDecimal workInjuryBase;

    /** 工伤单位比例 */
    @Column(value = "work_injury_rate")
    private BigDecimal workInjuryRate;

    /** 医疗基数 */
    @Column(value = "medical_base")
    private BigDecimal medicalBase;

    /** 医疗单位比例 */
    @Column(value = "medical_rate")
    private BigDecimal medicalRate;

    /** 医疗个人比例 */
    @Column(value = "medical_rate_personal")
    private BigDecimal medicalRatePersonal;

    /** 生育基数 */
    @Column(value = "birth_base")
    private BigDecimal birthBase;

    /** 生育单位比例 */
    @Column(value = "birth_rate")
    private BigDecimal birthRate;

    /** 大病/残保基数 */
    @Column(value = "sick_base")
    private BigDecimal sickBase;

    /** 大病/残保单位比例 */
    @Column(value = "sick_rate")
    private BigDecimal sickRate;

    /** 大病/残保个人比例 */
    @Column(value = "sick_rate_personal")
    private BigDecimal sickRatePersonal;

    /** 住房公积金基数 */
    @Column(value = "housing_base")
    private BigDecimal housingBase;

    /** 住房公积金单位比例 */
    @Column(value = "housing_rate")
    private BigDecimal housingRate;

    /** 住房公积金个人比例 */
    @Column(value = "housing_rate_personal")
    private BigDecimal housingRatePersonal;

    /** 养老单位 */
    @Column(value = "endowment_pay")
    private BigDecimal endowmentPay;

    /** 养老个人 */
    @Column(value = "endowment_pay_personal")
    private BigDecimal endowmentPayPersonal;

    /** 失业单位 */
    @Column(value = "unemployment_pay")
    private BigDecimal unemploymentPay;

    /** 失业个人 */
    @Column(value = "unemployment_pay_personal")
    private BigDecimal unemploymentPayPersonal;

    /** 工伤单位 */
    @Column(value = "work_injury_pay")
    private BigDecimal workInjuryPay;

    /** 医疗单位例 */
    @Column(value = "medical_pay")
    private BigDecimal medicalPay;

    /** 医疗个人 */
    @Column(value = "medical_pay_personal")
    private BigDecimal medicalPayPersonal;

    /** 生育单位 */
    @Column(value = "birth_pay")
    private BigDecimal birthPay;

    /** 大病/残保单位 */
    @Column(value = "sick_pay")
    private BigDecimal sickPay;

    /** 大病/残保个人 */
    @Column(value = "sick_pay_personal")
    private BigDecimal sickPayPersonal;

    /** 社保单位合计 */
    @Column(value = "social_security")
    private BigDecimal socialSecurity;

    /** 社保个人合计 */
    @Column(value = "social_security_personal")
    private BigDecimal socialSecurityPersonal;

    /** 社保小计 */
    @Column(value = "social_security_total")
    private BigDecimal socialSecurityTotal;

    /** 住房公积金单位 */
    @Column(value = "housing_pay")
    private BigDecimal housingPay;

    /** 住房公积金个人 */
    @Column(value = "housing_pay_personal")
    private BigDecimal housingPayPersonal;

    /** 公积金小计缴费 */
    @Column(value = "housing_pay_total")
    private BigDecimal housingPayTotal;

    /** 单位缴费合计 */
    @Column(value = "insurance_pay")
    private BigDecimal insurancePay;

    /** 个人缴费合计 */
    @Column(value = "insurance_pay_personal")
    private BigDecimal insurancePayPersonal;

    /** 总合计 */
    @Column(value = "insurance_pay_total")
    private BigDecimal insurancePayTotal;

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
     * 获取实际缴纳社保起始月份
     * 
     * @return 实际缴纳社保起始月份
     */
    public String getInsuranceRealDate() {
        return this.insuranceRealDate;
    }

    /**
     * 设置实际缴纳社保起始月份
     * 
     * @param insuranceRealDate
     *          实际缴纳社保起始月份
     */
    public void setInsuranceRealDate(String insuranceRealDate) {
        this.insuranceRealDate = insuranceRealDate;
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
     * 获取代理公司
     * 
     * @return 代理公司
     */
    public String getAgencyCompany() {
        return this.agencyCompany;
    }

    /**
     * 设置代理公司
     * 
     * @param agencyCompany
     *          代理公司
     */
    public void setAgencyCompany(String agencyCompany) {
        this.agencyCompany = agencyCompany;
    }

    /**
     * 获取代理费
     * 
     * @return 代理费
     */
    public BigDecimal getAgencyPay() {
        return this.agencyPay;
    }

    /**
     * 设置代理费
     * 
     * @param agencyPay
     *          代理费
     */
    public void setAgencyPay(BigDecimal agencyPay) {
        this.agencyPay = agencyPay;
    }

    /**
     * 获取养老基数
     * 
     * @return 养老基数
     */
    public BigDecimal getEndowmentBase() {
        return this.endowmentBase;
    }

    /**
     * 设置养老基数
     * 
     * @param endowmentBase
     *          养老基数
     */
    public void setEndowmentBase(BigDecimal endowmentBase) {
        this.endowmentBase = endowmentBase;
    }

    /**
     * 获取养老单位比例
     * 
     * @return 养老单位比例
     */
    public BigDecimal getEndowmentRate() {
        return this.endowmentRate;
    }

    /**
     * 设置养老单位比例
     * 
     * @param endowmentRate
     *          养老单位比例
     */
    public void setEndowmentRate(BigDecimal endowmentRate) {
        this.endowmentRate = endowmentRate;
    }

    /**
     * 获取养老个人比例
     * 
     * @return 养老个人比例
     */
    public BigDecimal getEndowmentRatePersonal() {
        return this.endowmentRatePersonal;
    }

    /**
     * 设置养老个人比例
     * 
     * @param endowmentRatePersonal
     *          养老个人比例
     */
    public void setEndowmentRatePersonal(BigDecimal endowmentRatePersonal) {
        this.endowmentRatePersonal = endowmentRatePersonal;
    }

    /**
     * 获取失业基数
     * 
     * @return 失业基数
     */
    public BigDecimal getUnemploymentBase() {
        return this.unemploymentBase;
    }

    /**
     * 设置失业基数
     * 
     * @param unemploymentBase
     *          失业基数
     */
    public void setUnemploymentBase(BigDecimal unemploymentBase) {
        this.unemploymentBase = unemploymentBase;
    }

    /**
     * 获取失业单位比例
     * 
     * @return 失业单位比例
     */
    public BigDecimal getUnemploymentRate() {
        return this.unemploymentRate;
    }

    /**
     * 设置失业单位比例
     * 
     * @param unemploymentRate
     *          失业单位比例
     */
    public void setUnemploymentRate(BigDecimal unemploymentRate) {
        this.unemploymentRate = unemploymentRate;
    }

    /**
     * 获取失业个人比例
     * 
     * @return 失业个人比例
     */
    public BigDecimal getUnemploymentRatePersonal() {
        return this.unemploymentRatePersonal;
    }

    /**
     * 设置失业个人比例
     * 
     * @param unemploymentRatePersonal
     *          失业个人比例
     */
    public void setUnemploymentRatePersonal(BigDecimal unemploymentRatePersonal) {
        this.unemploymentRatePersonal = unemploymentRatePersonal;
    }

    /**
     * 获取工伤基数
     * 
     * @return 工伤基数
     */
    public BigDecimal getWorkInjuryBase() {
        return this.workInjuryBase;
    }

    /**
     * 设置工伤基数
     * 
     * @param workInjuryBase
     *          工伤基数
     */
    public void setWorkInjuryBase(BigDecimal workInjuryBase) {
        this.workInjuryBase = workInjuryBase;
    }

    /**
     * 获取工伤单位比例
     * 
     * @return 工伤单位比例
     */
    public BigDecimal getWorkInjuryRate() {
        return this.workInjuryRate;
    }

    /**
     * 设置工伤单位比例
     * 
     * @param workInjuryRate
     *          工伤单位比例
     */
    public void setWorkInjuryRate(BigDecimal workInjuryRate) {
        this.workInjuryRate = workInjuryRate;
    }

    /**
     * 获取医疗基数
     * 
     * @return 医疗基数
     */
    public BigDecimal getMedicalBase() {
        return this.medicalBase;
    }

    /**
     * 设置医疗基数
     * 
     * @param medicalBase
     *          医疗基数
     */
    public void setMedicalBase(BigDecimal medicalBase) {
        this.medicalBase = medicalBase;
    }

    /**
     * 获取医疗单位比例
     * 
     * @return 医疗单位比例
     */
    public BigDecimal getMedicalRate() {
        return this.medicalRate;
    }

    /**
     * 设置医疗单位比例
     * 
     * @param medicalRate
     *          医疗单位比例
     */
    public void setMedicalRate(BigDecimal medicalRate) {
        this.medicalRate = medicalRate;
    }

    /**
     * 获取医疗个人比例
     * 
     * @return 医疗个人比例
     */
    public BigDecimal getMedicalRatePersonal() {
        return this.medicalRatePersonal;
    }

    /**
     * 设置医疗个人比例
     * 
     * @param medicalRatePersonal
     *          医疗个人比例
     */
    public void setMedicalRatePersonal(BigDecimal medicalRatePersonal) {
        this.medicalRatePersonal = medicalRatePersonal;
    }

    /**
     * 获取生育基数
     * 
     * @return 生育基数
     */
    public BigDecimal getBirthBase() {
        return this.birthBase;
    }

    /**
     * 设置生育基数
     * 
     * @param birthBase
     *          生育基数
     */
    public void setBirthBase(BigDecimal birthBase) {
        this.birthBase = birthBase;
    }

    /**
     * 获取生育单位比例
     * 
     * @return 生育单位比例
     */
    public BigDecimal getBirthRate() {
        return this.birthRate;
    }

    /**
     * 设置生育单位比例
     * 
     * @param birthRate
     *          生育单位比例
     */
    public void setBirthRate(BigDecimal birthRate) {
        this.birthRate = birthRate;
    }

    /**
     * 获取大病/残保基数
     * 
     * @return 大病/残保基数
     */
    public BigDecimal getSickBase() {
        return this.sickBase;
    }

    /**
     * 设置大病/残保基数
     * 
     * @param sickBase
     *          大病/残保基数
     */
    public void setSickBase(BigDecimal sickBase) {
        this.sickBase = sickBase;
    }

    /**
     * 获取大病/残保单位比例
     * 
     * @return 大病/残保单位比例
     */
    public BigDecimal getSickRate() {
        return this.sickRate;
    }

    /**
     * 设置大病/残保单位比例
     * 
     * @param sickRate
     *          大病/残保单位比例
     */
    public void setSickRate(BigDecimal sickRate) {
        this.sickRate = sickRate;
    }

    /**
     * 获取大病/残保个人比例
     * 
     * @return 大病/残保个人比例
     */
    public BigDecimal getSickRatePersonal() {
        return this.sickRatePersonal;
    }

    /**
     * 设置大病/残保个人比例
     * 
     * @param sickRatePersonal
     *          大病/残保个人比例
     */
    public void setSickRatePersonal(BigDecimal sickRatePersonal) {
        this.sickRatePersonal = sickRatePersonal;
    }

    /**
     * 获取住房公积金基数
     * 
     * @return 住房公积金基数
     */
    public BigDecimal getHousingBase() {
        return this.housingBase;
    }

    /**
     * 设置住房公积金基数
     * 
     * @param housingBase
     *          住房公积金基数
     */
    public void setHousingBase(BigDecimal housingBase) {
        this.housingBase = housingBase;
    }

    /**
     * 获取住房公积金单位比例
     * 
     * @return 住房公积金单位比例
     */
    public BigDecimal getHousingRate() {
        return this.housingRate;
    }

    /**
     * 设置住房公积金单位比例
     * 
     * @param housingRate
     *          住房公积金单位比例
     */
    public void setHousingRate(BigDecimal housingRate) {
        this.housingRate = housingRate;
    }

    /**
     * 获取住房公积金个人比例
     * 
     * @return 住房公积金个人比例
     */
    public BigDecimal getHousingRatePersonal() {
        return this.housingRatePersonal;
    }

    /**
     * 设置住房公积金个人比例
     * 
     * @param housingRatePersonal
     *          住房公积金个人比例
     */
    public void setHousingRatePersonal(BigDecimal housingRatePersonal) {
        this.housingRatePersonal = housingRatePersonal;
    }

    /**
     * 获取养老单位
     * 
     * @return 养老单位
     */
    public BigDecimal getEndowmentPay() {
        return this.endowmentPay;
    }

    /**
     * 设置养老单位
     * 
     * @param endowmentPay
     *          养老单位
     */
    public void setEndowmentPay(BigDecimal endowmentPay) {
        this.endowmentPay = endowmentPay;
    }

    /**
     * 获取养老个人
     * 
     * @return 养老个人
     */
    public BigDecimal getEndowmentPayPersonal() {
        return this.endowmentPayPersonal;
    }

    /**
     * 设置养老个人
     * 
     * @param endowmentPayPersonal
     *          养老个人
     */
    public void setEndowmentPayPersonal(BigDecimal endowmentPayPersonal) {
        this.endowmentPayPersonal = endowmentPayPersonal;
    }

    /**
     * 获取失业单位
     * 
     * @return 失业单位
     */
    public BigDecimal getUnemploymentPay() {
        return this.unemploymentPay;
    }

    /**
     * 设置失业单位
     * 
     * @param unemploymentPay
     *          失业单位
     */
    public void setUnemploymentPay(BigDecimal unemploymentPay) {
        this.unemploymentPay = unemploymentPay;
    }

    /**
     * 获取失业个人
     * 
     * @return 失业个人
     */
    public BigDecimal getUnemploymentPayPersonal() {
        return this.unemploymentPayPersonal;
    }

    /**
     * 设置失业个人
     * 
     * @param unemploymentPayPersonal
     *          失业个人
     */
    public void setUnemploymentPayPersonal(BigDecimal unemploymentPayPersonal) {
        this.unemploymentPayPersonal = unemploymentPayPersonal;
    }

    /**
     * 获取工伤单位
     * 
     * @return 工伤单位
     */
    public BigDecimal getWorkInjuryPay() {
        return this.workInjuryPay;
    }

    /**
     * 设置工伤单位
     * 
     * @param workInjuryPay
     *          工伤单位
     */
    public void setWorkInjuryPay(BigDecimal workInjuryPay) {
        this.workInjuryPay = workInjuryPay;
    }

    /**
     * 获取医疗单位例
     * 
     * @return 医疗单位例
     */
    public BigDecimal getMedicalPay() {
        return this.medicalPay;
    }

    /**
     * 设置医疗单位例
     * 
     * @param medicalPay
     *          医疗单位例
     */
    public void setMedicalPay(BigDecimal medicalPay) {
        this.medicalPay = medicalPay;
    }

    /**
     * 获取医疗个人
     * 
     * @return 医疗个人
     */
    public BigDecimal getMedicalPayPersonal() {
        return this.medicalPayPersonal;
    }

    /**
     * 设置医疗个人
     * 
     * @param medicalPayPersonal
     *          医疗个人
     */
    public void setMedicalPayPersonal(BigDecimal medicalPayPersonal) {
        this.medicalPayPersonal = medicalPayPersonal;
    }

    /**
     * 获取生育单位
     * 
     * @return 生育单位
     */
    public BigDecimal getBirthPay() {
        return this.birthPay;
    }

    /**
     * 设置生育单位
     * 
     * @param birthPay
     *          生育单位
     */
    public void setBirthPay(BigDecimal birthPay) {
        this.birthPay = birthPay;
    }

    /**
     * 获取大病/残保单位
     * 
     * @return 大病/残保单位
     */
    public BigDecimal getSickPay() {
        return this.sickPay;
    }

    /**
     * 设置大病/残保单位
     * 
     * @param sickPay
     *          大病/残保单位
     */
    public void setSickPay(BigDecimal sickPay) {
        this.sickPay = sickPay;
    }

    /**
     * 获取大病/残保个人
     * 
     * @return 大病/残保个人
     */
    public BigDecimal getSickPayPersonal() {
        return this.sickPayPersonal;
    }

    /**
     * 设置大病/残保个人
     * 
     * @param sickPayPersonal
     *          大病/残保个人
     */
    public void setSickPayPersonal(BigDecimal sickPayPersonal) {
        this.sickPayPersonal = sickPayPersonal;
    }

    /**
     * 获取社保单位合计
     * 
     * @return 社保单位合计
     */
    public BigDecimal getSocialSecurity() {
        return this.socialSecurity;
    }

    /**
     * 设置社保单位合计
     * 
     * @param socialSecurity
     *          社保单位合计
     */
    public void setSocialSecurity(BigDecimal socialSecurity) {
        this.socialSecurity = socialSecurity;
    }

    /**
     * 获取社保个人合计
     * 
     * @return 社保个人合计
     */
    public BigDecimal getSocialSecurityPersonal() {
        return this.socialSecurityPersonal;
    }

    /**
     * 设置社保个人合计
     * 
     * @param socialSecurityPersonal
     *          社保个人合计
     */
    public void setSocialSecurityPersonal(BigDecimal socialSecurityPersonal) {
        this.socialSecurityPersonal = socialSecurityPersonal;
    }

    /**
     * 获取社保小计
     * 
     * @return 社保小计
     */
    public BigDecimal getSocialSecurityTotal() {
        return this.socialSecurityTotal;
    }

    /**
     * 设置社保小计
     * 
     * @param socialSecurityTotal
     *          社保小计
     */
    public void setSocialSecurityTotal(BigDecimal socialSecurityTotal) {
        this.socialSecurityTotal = socialSecurityTotal;
    }

    /**
     * 获取住房公积金单位
     * 
     * @return 住房公积金单位
     */
    public BigDecimal getHousingPay() {
        return this.housingPay;
    }

    /**
     * 设置住房公积金单位
     * 
     * @param housingPay
     *          住房公积金单位
     */
    public void setHousingPay(BigDecimal housingPay) {
        this.housingPay = housingPay;
    }

    /**
     * 获取住房公积金个人
     * 
     * @return 住房公积金个人
     */
    public BigDecimal getHousingPayPersonal() {
        return this.housingPayPersonal;
    }

    /**
     * 设置住房公积金个人
     * 
     * @param housingPayPersonal
     *          住房公积金个人
     */
    public void setHousingPayPersonal(BigDecimal housingPayPersonal) {
        this.housingPayPersonal = housingPayPersonal;
    }

    /**
     * 获取公积金小计缴费
     * 
     * @return 公积金小计缴费
     */
    public BigDecimal getHousingPayTotal() {
        return this.housingPayTotal;
    }

    /**
     * 设置公积金小计缴费
     * 
     * @param housingPayTotal
     *          公积金小计缴费
     */
    public void setHousingPayTotal(BigDecimal housingPayTotal) {
        this.housingPayTotal = housingPayTotal;
    }

    /**
     * 获取设置单位缴费合计
     * 
     * @return 设置单位缴费合计
     */
    public BigDecimal getInsurancePay() {
        return this.insurancePay;
    }

    /**
     * 设置单位缴费合计
     * 
     * @param insurancePay
     *          设置单位缴费合计
     */
    public void setInsurancePay(BigDecimal insurancePay) {
        this.insurancePay = insurancePay;
    }

    /**
     * 获取个人缴费合计
     * 
     * @return 个人缴费合计
     */
    public BigDecimal getInsurancePayPersonal() {
        return this.insurancePayPersonal;
    }

    /**
     * 设置个人缴费合计
     * 
     * @param insurancePayPersonal
     *          个人缴费合计
     */
    public void setInsurancePayPersonal(BigDecimal insurancePayPersonal) {
        this.insurancePayPersonal = insurancePayPersonal;
    }

    /**
     * 获取总合计
     * 
     * @return 总合计
     */
    public BigDecimal getInsurancePayTotal() {
        return this.insurancePayTotal;
    }

    /**
     * 设置总合计
     * 
     * @param insurancePayTotal
     *          总合计
     */
    public void setInsurancePayTotal(BigDecimal insurancePayTotal) {
        this.insurancePayTotal = insurancePayTotal;
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