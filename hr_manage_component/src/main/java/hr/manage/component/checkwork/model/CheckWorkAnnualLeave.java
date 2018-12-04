package hr.manage.component.checkwork.model;

import java.math.BigDecimal;
import java.util.Date;

import net.paoding.rose.jade.plugin.sql.annotations.Column;
import net.paoding.rose.jade.plugin.sql.annotations.Table;

/**
 * check_work_annual_leave
 * 
 * @version 1.0.0 2018-12-02
 */
@Table(value = "check_work_annual_leave")
public class CheckWorkAnnualLeave implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -3928243926252187354L;

    /** 表的主键 */
    @Column(pk=true,value = "id")
    private BigDecimal id;

    /** 姓名 */
    @Column(value = "name")
    private String name;
    
    /** 年度 */
    @Column(value = "term")
    private String term;
    
    /** 入职时间 */
    @Column(value = "entry_time")
    private Date entryTime;

    /** 可休年假起始日期 */
    @Column(value = "start_date")
    private Date startDate;

    /** 可休年假截止时间 */
    @Column(value = "end_date")
    private Date endDate;

    /** 当年应休年假天数 */
    @Column(value = "annual_leave_days")
    private BigDecimal annualLeaveDays;

    /** 1月实际休年假天数 */
    @Column(value = "annual_leave_jan")
    private BigDecimal annualLeaveJan;

    /** 2月实际休年假天数 */
    @Column(value = "annual_leave_feb")
    private BigDecimal annualLeaveFeb;

    /** 3月实际休年假天数 */
    @Column(value = "annual_leave_mar")
    private BigDecimal annualLeaveMar;

    /** 4月实际休年假天数 */
    @Column(value = "annual_leave_apr")
    private BigDecimal annualLeaveApr;

    /** 5月实际休年假天数 */
    @Column(value = "annual_leave_may")
    private BigDecimal annualLeaveMay;

    /** 6月实际休年假天数 */
    @Column(value = "annual_leave_jun")
    private BigDecimal annualLeaveJun;

    /** 7月实际休年假天数 */
    @Column(value = "annual_leave_jul")
    private BigDecimal annualLeaveJul;

    /** 8月实际休年假天数 */
    @Column(value = "annual_leave_aug")
    private BigDecimal annualLeaveAug;

    /** 9月实际休年假天数 */
    @Column(value = "annual_leave_sept")
    private BigDecimal annualLeaveSept;

    /** 10月实际休年假天数 */
    @Column(value = "annual_leave_oct")
    private BigDecimal annualLeaveOct;

    /** 11月实际休年假天数 */
    @Column(value = "annual_leave_nov")
    private BigDecimal annualLeaveNov;

    /** 12月实际休年假天数 */
    @Column(value = "annual_leave_dec")
    private BigDecimal annualLeaveDec;

    /** 当年剩余年休天数 */
    @Column(value = "surplus_annual_leave")
    private BigDecimal surplusAnnualLeave;

    /** 1月加班明细 */
    @Column(value = "overtime_jan")
    private BigDecimal overtimeJan;

    /** 2月加班明细 */
    @Column(value = "overtime_feb")
    private BigDecimal overtimeFeb;

    /** 3月加班明细 */
    @Column(value = "overtime_mar")
    private BigDecimal overtimeMar;

    /** 4月加班明细 */
    @Column(value = "overtime_apr")
    private BigDecimal overtimeApr;

    /** 5月加班明细 */
    @Column(value = "overtime_may")
    private BigDecimal overtimeMay;

    /** 6月加班明细 */
    @Column(value = "overtime_jun")
    private BigDecimal overtimeJun;

    /** 7月加班明细 */
    @Column(value = "overtime_jul")
    private BigDecimal overtimeJul;

    /** 8月加班明细 */
    @Column(value = "overtime_aug")
    private BigDecimal overtimeAug;

    /** 9月加班明细 */
    @Column(value = "overtime_sept")
    private BigDecimal overtimeSept;

    /** 10月加班明细 */
    @Column(value = "overtime_oct")
    private BigDecimal overtimeOct;

    /** 11月加班明细 */
    @Column(value = "overtime_nov")
    private BigDecimal overtimeNov;

    /** 12月加班明细 */
    @Column(value = "overtime_dec")
    private BigDecimal overtimeDec;

    /** 加班汇总 */
    @Column(value = "overtime_collect")
    private BigDecimal overtimeCollect;

    /** 1月调休明细 */
    @Column(value = "off_duty_shift_jan")
    private BigDecimal offDutyShiftJan;

    /** 2月调休明细 */
    @Column(value = "off_duty_shift_feb")
    private BigDecimal offDutyShiftFeb;

    /** 3月调休明细 */
    @Column(value = "off_duty_shift_mar")
    private BigDecimal offDutyShiftMar;

    /** 4月调休明细 */
    @Column(value = "off_duty_shift_apr")
    private BigDecimal offDutyShiftApr;

    /** 5月调休明细 */
    @Column(value = "off_duty_shift_may")
    private BigDecimal offDutyShiftMay;

    /** 6月调休明细 */
    @Column(value = "off_duty_shift_jun")
    private BigDecimal offDutyShiftJun;

    /** 7月调休明细 */
    @Column(value = "off_duty_shift_jul")
    private BigDecimal offDutyShiftJul;

    /** 8月调休明细 */
    @Column(value = "off_duty_shift_aug")
    private BigDecimal offDutyShiftAug;

    /** 9月调休明细 */
    @Column(value = "off_duty_shift_sept")
    private BigDecimal offDutyShiftSept;

    /** 10月调休明细 */
    @Column(value = "off_duty_shift_oct")
    private BigDecimal offDutyShiftOct;

    /** 11月调休明细 */
    @Column(value = "off_duty_shift_nov")
    private BigDecimal offDutyShiftNov;

    /** 12月调休明细 */
    @Column(value = "off_duty_shift_dec")
    private BigDecimal offDutyShiftDec;

    /** 调休汇总*/
    @Column(value = "off_duty_shift_collect")
    private BigDecimal offDutyShiftCollect;

    /** 剩余加班天数 */
    @Column(value = "surplus_overtime_days")
    private BigDecimal surplusOvertimeDays;

    /** 1月请假天数 */
    @Column(value = "leave_jan")
    private BigDecimal leaveJan;

    /** 2月请假天数 */
    @Column(value = "leave_feb")
    private BigDecimal leaveFeb;

    /** 3月请假天数 */
    @Column(value = "leave_mar")
    private BigDecimal leaveMar;

    /** 4月请假天数 */
    @Column(value = "leave_apr")
    private BigDecimal leaveApr;

    /** 5月请假天数 */
    @Column(value = "leave_may")
    private BigDecimal leaveMay;

    /** 6月请假天数 */
    @Column(value = "leave_jun")
    private BigDecimal leaveJun;

    /** 7月请假天数 */
    @Column(value = "leave_jul")
    private BigDecimal leaveJul;

    /** 8月请假天数 */
    @Column(value = "leave_aug")
    private BigDecimal leaveAug;

    /** 9月请假天数 */
    @Column(value = "leave_sept")
    private BigDecimal leaveSept;

    /** 10月请假天数 */
    @Column(value = "leave_oct")
    private BigDecimal leaveOct;

    /** 11月请假天数 */
    @Column(value = "leave_nov")
    private BigDecimal leaveNov;

    /** 12月请假天数 */
    @Column(value = "leave_dec")
    private BigDecimal leaveDec;

    /** 请假合计 */
    @Column(value = "surplus_leave")
    private BigDecimal surplusLeave;

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
    public BigDecimal getId() {
        return this.id;
    }

    /**
     * 设置表的主键
     * 
     * @param id
     *          表的主键
     */
    public void setId(BigDecimal id) {
        this.id = id;
    }

    /**
     * 获取姓名
     * 
     * @return 姓名
     */
    public String getname() {
        return this.name;
    }
    

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	/**
     * 设置姓名
     * 
     * @param value
     *          姓名
     */
    public void setname(String name) {
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
     * 获取可休年假起始日期
     * 
     * @return 可休年假起始日期
     */
    public Date getStartDate() {
        return this.startDate;
    }

    /**
     * 设置可休年假起始日期
     * 
     * @param startDate
     *          可休年假起始日期
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * 获取可休年假截止时间
     * 
     * @return 可休年假截止时间
     */
    public Date getEndDate() {
        return this.endDate;
    }

    /**
     * 设置可休年假截止时间
     * 
     * @param endDate
     *          可休年假截止时间
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * 获取当年应休年假天数
     * 
     * @return 当年应休年假天数
     */
    public BigDecimal getAnnualLeaveDays() {
        return this.annualLeaveDays;
    }

    /**
     * 设置当年应休年假天数
     * 
     * @param annualLeaveDays
     *          当年应休年假天数
     */
    public void setAnnualLeaveDays(BigDecimal annualLeaveDays) {
        this.annualLeaveDays = annualLeaveDays;
    }

    /**
     * 获取1月实际休年假天数
     * 
     * @return 1月实际休年假天数
     */
    public BigDecimal getAnnualLeaveJan() {
        return this.annualLeaveJan;
    }

    /**
     * 设置1月实际休年假天数
     * 
     * @param annualLeaveJan
     *          1月实际休年假天数
     */
    public void setAnnualLeaveJan(BigDecimal annualLeaveJan) {
        this.annualLeaveJan = annualLeaveJan;
    }

    /**
     * 获取2月实际休年假天数
     * 
     * @return 2月实际休年假天数
     */
    public BigDecimal getAnnualLeaveFeb() {
        return this.annualLeaveFeb;
    }

    /**
     * 设置2月实际休年假天数
     * 
     * @param annualLeaveFeb
     *          2月实际休年假天数
     */
    public void setAnnualLeaveFeb(BigDecimal annualLeaveFeb) {
        this.annualLeaveFeb = annualLeaveFeb;
    }

    /**
     * 获取3月实际休年假天数
     * 
     * @return 3月实际休年假天数
     */
    public BigDecimal getAnnualLeaveMar() {
        return this.annualLeaveMar;
    }

    /**
     * 设置3月实际休年假天数
     * 
     * @param annualLeaveMar
     *          3月实际休年假天数
     */
    public void setAnnualLeaveMar(BigDecimal annualLeaveMar) {
        this.annualLeaveMar = annualLeaveMar;
    }

    /**
     * 获取4月实际休年假天数
     * 
     * @return 4月实际休年假天数
     */
    public BigDecimal getAnnualLeaveApr() {
        return this.annualLeaveApr;
    }

    /**
     * 设置4月实际休年假天数
     * 
     * @param annualLeaveApr
     *          4月实际休年假天数
     */
    public void setAnnualLeaveApr(BigDecimal annualLeaveApr) {
        this.annualLeaveApr = annualLeaveApr;
    }

    /**
     * 获取5月实际休年假天数
     * 
     * @return 5月实际休年假天数
     */
    public BigDecimal getAnnualLeaveMay() {
        return this.annualLeaveMay;
    }

    /**
     * 设置5月实际休年假天数
     * 
     * @param annualLeaveMay
     *          5月实际休年假天数
     */
    public void setAnnualLeaveMay(BigDecimal annualLeaveMay) {
        this.annualLeaveMay = annualLeaveMay;
    }

    /**
     * 获取6月实际休年假天数
     * 
     * @return 6月实际休年假天数
     */
    public BigDecimal getAnnualLeaveJun() {
        return this.annualLeaveJun;
    }

    /**
     * 设置6月实际休年假天数
     * 
     * @param annualLeaveJun
     *          6月实际休年假天数
     */
    public void setAnnualLeaveJun(BigDecimal annualLeaveJun) {
        this.annualLeaveJun = annualLeaveJun;
    }

    /**
     * 获取7月实际休年假天数
     * 
     * @return 7月实际休年假天数
     */
    public BigDecimal getAnnualLeaveJul() {
        return this.annualLeaveJul;
    }

    /**
     * 设置7月实际休年假天数
     * 
     * @param annualLeaveJul
     *          7月实际休年假天数
     */
    public void setAnnualLeaveJul(BigDecimal annualLeaveJul) {
        this.annualLeaveJul = annualLeaveJul;
    }

    /**
     * 获取8月实际休年假天数
     * 
     * @return 8月实际休年假天数
     */
    public BigDecimal getAnnualLeaveAug() {
        return this.annualLeaveAug;
    }

    /**
     * 设置8月实际休年假天数
     * 
     * @param annualLeaveAug
     *          8月实际休年假天数
     */
    public void setAnnualLeaveAug(BigDecimal annualLeaveAug) {
        this.annualLeaveAug = annualLeaveAug;
    }

    /**
     * 获取9月实际休年假天数
     * 
     * @return 9月实际休年假天数
     */
    public BigDecimal getAnnualLeaveSept() {
        return this.annualLeaveSept;
    }

    /**
     * 设置9月实际休年假天数
     * 
     * @param annualLeaveSept
     *          9月实际休年假天数
     */
    public void setAnnualLeaveSept(BigDecimal annualLeaveSept) {
        this.annualLeaveSept = annualLeaveSept;
    }

    /**
     * 获取10月实际休年假天数
     * 
     * @return 10月实际休年假天数
     */
    public BigDecimal getAnnualLeaveOct() {
        return this.annualLeaveOct;
    }

    /**
     * 设置10月实际休年假天数
     * 
     * @param annualLeaveOct
     *          10月实际休年假天数
     */
    public void setAnnualLeaveOct(BigDecimal annualLeaveOct) {
        this.annualLeaveOct = annualLeaveOct;
    }

    /**
     * 获取11月实际休年假天数
     * 
     * @return 11月实际休年假天数
     */
    public BigDecimal getAnnualLeaveNov() {
        return this.annualLeaveNov;
    }

    /**
     * 设置11月实际休年假天数
     * 
     * @param annualLeaveNov
     *          11月实际休年假天数
     */
    public void setAnnualLeaveNov(BigDecimal annualLeaveNov) {
        this.annualLeaveNov = annualLeaveNov;
    }

    /**
     * 获取12月实际休年假天数
     * 
     * @return 12月实际休年假天数
     */
    public BigDecimal getAnnualLeaveDec() {
        return this.annualLeaveDec;
    }

    /**
     * 设置12月实际休年假天数
     * 
     * @param annualLeaveDec
     *          12月实际休年假天数
     */
    public void setAnnualLeaveDec(BigDecimal annualLeaveDec) {
        this.annualLeaveDec = annualLeaveDec;
    }

    /**
     * 获取当年剩余年休天数
     * 
     * @return 当年剩余年休天数
     */
    public BigDecimal getSurplusAnnualLeave() {
        return this.surplusAnnualLeave;
    }

    /**
     * 设置当年剩余年休天数
     * 
     * @param surplusAnnualLeave
     *          当年剩余年休天数
     */
    public void setSurplusAnnualLeave(BigDecimal surplusAnnualLeave) {
        this.surplusAnnualLeave = surplusAnnualLeave;
    }

    /**
     * 获取1月加班明细
     * 
     * @return 1月加班明细
     */
    public BigDecimal getOvertimeJan() {
        return this.overtimeJan;
    }

    /**
     * 设置1月加班明细
     * 
     * @param overtimeJan
     *          1月加班明细
     */
    public void setOvertimeJan(BigDecimal overtimeJan) {
        this.overtimeJan = overtimeJan;
    }

    /**
     * 获取2月加班明细
     * 
     * @return 2月加班明细
     */
    public BigDecimal getOvertimeFeb() {
        return this.overtimeFeb;
    }

    /**
     * 设置2月加班明细
     * 
     * @param overtimeFeb
     *          2月加班明细
     */
    public void setOvertimeFeb(BigDecimal overtimeFeb) {
        this.overtimeFeb = overtimeFeb;
    }

    /**
     * 获取3月加班明细
     * 
     * @return 3月加班明细
     */
    public BigDecimal getOvertimeMar() {
        return this.overtimeMar;
    }

    /**
     * 设置3月加班明细
     * 
     * @param overtimeMar
     *          3月加班明细
     */
    public void setOvertimeMar(BigDecimal overtimeMar) {
        this.overtimeMar = overtimeMar;
    }

    /**
     * 获取4月加班明细
     * 
     * @return 4月加班明细
     */
    public BigDecimal getOvertimeApr() {
        return this.overtimeApr;
    }

    /**
     * 设置4月加班明细
     * 
     * @param overtimeApr
     *          4月加班明细
     */
    public void setOvertimeApr(BigDecimal overtimeApr) {
        this.overtimeApr = overtimeApr;
    }

    /**
     * 获取5月加班明细
     * 
     * @return 5月加班明细
     */
    public BigDecimal getOvertimeMay() {
        return this.overtimeMay;
    }

    /**
     * 设置5月加班明细
     * 
     * @param overtimeMay
     *          5月加班明细
     */
    public void setOvertimeMay(BigDecimal overtimeMay) {
        this.overtimeMay = overtimeMay;
    }

    /**
     * 获取6月加班明细
     * 
     * @return 6月加班明细
     */
    public BigDecimal getOvertimeJun() {
        return this.overtimeJun;
    }

    /**
     * 设置6月加班明细
     * 
     * @param overtimeJun
     *          6月加班明细
     */
    public void setOvertimeJun(BigDecimal overtimeJun) {
        this.overtimeJun = overtimeJun;
    }

    /**
     * 获取7月加班明细
     * 
     * @return 7月加班明细
     */
    public BigDecimal getOvertimeJul() {
        return this.overtimeJul;
    }

    /**
     * 设置7月加班明细
     * 
     * @param overtimeJul
     *          7月加班明细
     */
    public void setOvertimeJul(BigDecimal overtimeJul) {
        this.overtimeJul = overtimeJul;
    }

    /**
     * 获取8月加班明细
     * 
     * @return 8月加班明细
     */
    public BigDecimal getOvertimeAug() {
        return this.overtimeAug;
    }

    /**
     * 设置8月加班明细
     * 
     * @param overtimeAug
     *          8月加班明细
     */
    public void setOvertimeAug(BigDecimal overtimeAug) {
        this.overtimeAug = overtimeAug;
    }

    /**
     * 获取9月加班明细
     * 
     * @return 9月加班明细
     */
    public BigDecimal getOvertimeSept() {
        return this.overtimeSept;
    }

    /**
     * 设置9月加班明细
     * 
     * @param overtimeSept
     *          9月加班明细
     */
    public void setOvertimeSept(BigDecimal overtimeSept) {
        this.overtimeSept = overtimeSept;
    }

    /**
     * 获取10月加班明细
     * 
     * @return 10月加班明细
     */
    public BigDecimal getOvertimeOct() {
        return this.overtimeOct;
    }

    /**
     * 设置10月加班明细
     * 
     * @param overtimeOct
     *          10月加班明细
     */
    public void setOvertimeOct(BigDecimal overtimeOct) {
        this.overtimeOct = overtimeOct;
    }

    /**
     * 获取11月加班明细
     * 
     * @return 11月加班明细
     */
    public BigDecimal getOvertimeNov() {
        return this.overtimeNov;
    }

    /**
     * 设置11月加班明细
     * 
     * @param overtimeNov
     *          11月加班明细
     */
    public void setOvertimeNov(BigDecimal overtimeNov) {
        this.overtimeNov = overtimeNov;
    }

    /**
     * 获取12月加班明细
     * 
     * @return 12月加班明细
     */
    public BigDecimal getOvertimeDec() {
        return this.overtimeDec;
    }

    /**
     * 设置12月加班明细
     * 
     * @param overtimeDec
     *          12月加班明细
     */
    public void setOvertimeDec(BigDecimal overtimeDec) {
        this.overtimeDec = overtimeDec;
    }

    /**
     * 获取加班汇总(h)
     * 
     * @return 加班汇总(h)
     */
    public BigDecimal getOvertimeCollect() {
        return this.overtimeCollect;
    }

    /**
     * 设置加班汇总(h)
     * 
     * @param overtimeCollect
     *          加班汇总(h)
     */
    public void setOvertimeCollect(BigDecimal overtimeCollect) {
        this.overtimeCollect = overtimeCollect;
    }

    /**
     * 获取1月调休明细
     * 
     * @return 1月调休明细
     */
    public BigDecimal getOffDutyShiftJan() {
        return this.offDutyShiftJan;
    }

    /**
     * 设置1月调休明细
     * 
     * @param offDutyShiftJan
     *          1月调休明细
     */
    public void setOffDutyShiftJan(BigDecimal offDutyShiftJan) {
        this.offDutyShiftJan = offDutyShiftJan;
    }

    /**
     * 获取2月调休明细
     * 
     * @return 2月调休明细
     */
    public BigDecimal getOffDutyShiftFeb() {
        return this.offDutyShiftFeb;
    }

    /**
     * 设置2月调休明细
     * 
     * @param offDutyShiftFeb
     *          2月调休明细
     */
    public void setOffDutyShiftFeb(BigDecimal offDutyShiftFeb) {
        this.offDutyShiftFeb = offDutyShiftFeb;
    }

    /**
     * 获取3月调休明细
     * 
     * @return 3月调休明细
     */
    public BigDecimal getOffDutyShiftMar() {
        return this.offDutyShiftMar;
    }

    /**
     * 设置3月调休明细
     * 
     * @param offDutyShiftMar
     *          3月调休明细
     */
    public void setOffDutyShiftMar(BigDecimal offDutyShiftMar) {
        this.offDutyShiftMar = offDutyShiftMar;
    }

    /**
     * 获取4月调休明细
     * 
     * @return 4月调休明细
     */
    public BigDecimal getOffDutyShiftApr() {
        return this.offDutyShiftApr;
    }

    /**
     * 设置4月调休明细
     * 
     * @param offDutyShiftApr
     *          4月调休明细
     */
    public void setOffDutyShiftApr(BigDecimal offDutyShiftApr) {
        this.offDutyShiftApr = offDutyShiftApr;
    }

    /**
     * 获取5月调休明细
     * 
     * @return 5月调休明细
     */
    public BigDecimal getOffDutyShiftMay() {
        return this.offDutyShiftMay;
    }

    /**
     * 设置5月调休明细
     * 
     * @param offDutyShiftMay
     *          5月调休明细
     */
    public void setOffDutyShiftMay(BigDecimal offDutyShiftMay) {
        this.offDutyShiftMay = offDutyShiftMay;
    }

    /**
     * 获取6月调休明细
     * 
     * @return 6月调休明细
     */
    public BigDecimal getOffDutyShiftJun() {
        return this.offDutyShiftJun;
    }

    /**
     * 设置6月调休明细
     * 
     * @param offDutyShiftJun
     *          6月调休明细
     */
    public void setOffDutyShiftJun(BigDecimal offDutyShiftJun) {
        this.offDutyShiftJun = offDutyShiftJun;
    }

    /**
     * 获取7月调休明细
     * 
     * @return 7月调休明细
     */
    public BigDecimal getOffDutyShiftJul() {
        return this.offDutyShiftJul;
    }

    /**
     * 设置7月调休明细
     * 
     * @param offDutyShiftJul
     *          7月调休明细
     */
    public void setOffDutyShiftJul(BigDecimal offDutyShiftJul) {
        this.offDutyShiftJul = offDutyShiftJul;
    }

    /**
     * 获取8月调休明细
     * 
     * @return 8月调休明细
     */
    public BigDecimal getOffDutyShiftAug() {
        return this.offDutyShiftAug;
    }

    /**
     * 设置8月调休明细
     * 
     * @param offDutyShiftAug
     *          8月调休明细
     */
    public void setOffDutyShiftAug(BigDecimal offDutyShiftAug) {
        this.offDutyShiftAug = offDutyShiftAug;
    }

    /**
     * 获取9月调休明细
     * 
     * @return 9月调休明细
     */
    public BigDecimal getOffDutyShiftSept() {
        return this.offDutyShiftSept;
    }

    /**
     * 设置9月调休明细
     * 
     * @param offDutyShiftSept
     *          9月调休明细
     */
    public void setOffDutyShiftSept(BigDecimal offDutyShiftSept) {
        this.offDutyShiftSept = offDutyShiftSept;
    }

    /**
     * 获取10月调休明细
     * 
     * @return 10月调休明细
     */
    public BigDecimal getOffDutyShiftOct() {
        return this.offDutyShiftOct;
    }

    /**
     * 设置10月调休明细
     * 
     * @param offDutyShiftOct
     *          10月调休明细
     */
    public void setOffDutyShiftOct(BigDecimal offDutyShiftOct) {
        this.offDutyShiftOct = offDutyShiftOct;
    }

    /**
     * 获取11月调休明细
     * 
     * @return 11月调休明细
     */
    public BigDecimal getOffDutyShiftNov() {
        return this.offDutyShiftNov;
    }

    /**
     * 设置11月调休明细
     * 
     * @param offDutyShiftNov
     *          11月调休明细
     */
    public void setOffDutyShiftNov(BigDecimal offDutyShiftNov) {
        this.offDutyShiftNov = offDutyShiftNov;
    }

    /**
     * 获取12月调休明细
     * 
     * @return 12月调休明细
     */
    public BigDecimal getOffDutyShiftDec() {
        return this.offDutyShiftDec;
    }

    /**
     * 设置12月调休明细
     * 
     * @param offDutyShiftDec
     *          12月调休明细
     */
    public void setOffDutyShiftDec(BigDecimal offDutyShiftDec) {
        this.offDutyShiftDec = offDutyShiftDec;
    }

    /**
     * 获取调休汇总(h)
     * 
     * @return 调休汇总(h)
     */
    public BigDecimal getOffDutyShiftCollect() {
        return this.offDutyShiftCollect;
    }

    /**
     * 设置调休汇总(h)
     * 
     * @param offDutyShiftCollect
     *          调休汇总(h)
     */
    public void setOffDutyShiftCollect(BigDecimal offDutyShiftCollect) {
        this.offDutyShiftCollect = offDutyShiftCollect;
    }

    /**
     * 获取剩余加班天数
     * 
     * @return 剩余加班天数
     */
    public BigDecimal getSurplusOvertimeDays() {
        return this.surplusOvertimeDays;
    }

    /**
     * 设置剩余加班天数
     * 
     * @param surplusOvertimeHours
     *          剩余加班天数
     */
    public void setSurplusOvertimeDays(BigDecimal surplusOvertimeDays) {
        this.surplusOvertimeDays = surplusOvertimeDays;
    }

    /**
     * 获取1月请假天数
     * 
     * @return 1月请假天数
     */
    public BigDecimal getLeaveJan() {
        return this.leaveJan;
    }

    /**
     * 设置1月请假天数
     * 
     * @param leaveJan
     *          1月请假天数
     */
    public void setLeaveJan(BigDecimal leaveJan) {
        this.leaveJan = leaveJan;
    }

    /**
     * 获取2月请假天数
     * 
     * @return 2月请假天数
     */
    public BigDecimal getLeaveFeb() {
        return this.leaveFeb;
    }

    /**
     * 设置2月请假天数
     * 
     * @param leaveFeb
     *          2月请假天数
     */
    public void setLeaveFeb(BigDecimal leaveFeb) {
        this.leaveFeb = leaveFeb;
    }

    /**
     * 获取3月请假天数
     * 
     * @return 3月请假天数
     */
    public BigDecimal getLeaveMar() {
        return this.leaveMar;
    }

    /**
     * 设置3月请假天数
     * 
     * @param leaveMar
     *          3月请假天数
     */
    public void setLeaveMar(BigDecimal leaveMar) {
        this.leaveMar = leaveMar;
    }

    /**
     * 获取4月请假天数
     * 
     * @return 4月请假天数
     */
    public BigDecimal getLeaveApr() {
        return this.leaveApr;
    }

    /**
     * 设置4月请假天数
     * 
     * @param leaveApr
     *          4月请假天数
     */
    public void setLeaveApr(BigDecimal leaveApr) {
        this.leaveApr = leaveApr;
    }

    /**
     * 获取5月请假天数
     * 
     * @return 5月请假天数
     */
    public BigDecimal getLeaveMay() {
        return this.leaveMay;
    }

    /**
     * 设置5月请假天数
     * 
     * @param leaveMay
     *          5月请假天数
     */
    public void setLeaveMay(BigDecimal leaveMay) {
        this.leaveMay = leaveMay;
    }

    /**
     * 获取6月请假天数
     * 
     * @return 6月请假天数
     */
    public BigDecimal getLeaveJun() {
        return this.leaveJun;
    }

    /**
     * 设置6月请假天数
     * 
     * @param leaveJun
     *          6月请假天数
     */
    public void setLeaveJun(BigDecimal leaveJun) {
        this.leaveJun = leaveJun;
    }

    /**
     * 获取7月请假天数
     * 
     * @return 7月请假天数
     */
    public BigDecimal getLeaveJul() {
        return this.leaveJul;
    }

    /**
     * 设置7月请假天数
     * 
     * @param leaveJul
     *          7月请假天数
     */
    public void setLeaveJul(BigDecimal leaveJul) {
        this.leaveJul = leaveJul;
    }

    /**
     * 获取8月请假天数
     * 
     * @return 8月请假天数
     */
    public BigDecimal getLeaveAug() {
        return this.leaveAug;
    }

    /**
     * 设置8月请假天数
     * 
     * @param leaveAug
     *          8月请假天数
     */
    public void setLeaveAug(BigDecimal leaveAug) {
        this.leaveAug = leaveAug;
    }

    /**
     * 获取9月请假天数
     * 
     * @return 9月请假天数
     */
    public BigDecimal getLeaveSept() {
        return this.leaveSept;
    }

    /**
     * 设置9月请假天数
     * 
     * @param leaveSept
     *          9月请假天数
     */
    public void setLeaveSept(BigDecimal leaveSept) {
        this.leaveSept = leaveSept;
    }

    /**
     * 获取10月请假天数
     * 
     * @return 10月请假天数
     */
    public BigDecimal getLeaveOct() {
        return this.leaveOct;
    }

    /**
     * 设置10月请假天数
     * 
     * @param leaveOct
     *          10月请假天数
     */
    public void setLeaveOct(BigDecimal leaveOct) {
        this.leaveOct = leaveOct;
    }

    /**
     * 获取11月请假天数
     * 
     * @return 11月请假天数
     */
    public BigDecimal getLeaveNov() {
        return this.leaveNov;
    }

    /**
     * 设置11月请假天数
     * 
     * @param leaveNov
     *          11月请假天数
     */
    public void setLeaveNov(BigDecimal leaveNov) {
        this.leaveNov = leaveNov;
    }

    /**
     * 获取12月请假天数
     * 
     * @return 12月请假天数
     */
    public BigDecimal getLeaveDec() {
        return this.leaveDec;
    }

    /**
     * 设置12月请假天数
     * 
     * @param leaveDec
     *          12月请假天数
     */
    public void setLeaveDec(BigDecimal leaveDec) {
        this.leaveDec = leaveDec;
    }

    /**
     * 获取请假合计
     * 
     * @return 请假合计
     */
    public BigDecimal getSurplusLeave() {
        return this.surplusLeave;
    }

    /**
     * 设置请假合计
     * 
     * @param surplusLeave
     *          请假合计
     */
    public void setSurplusLeave(BigDecimal surplusLeave) {
        this.surplusLeave = surplusLeave;
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