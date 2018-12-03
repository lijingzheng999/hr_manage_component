package hr.manage.component.checkwork.model;
import hr.manage.component.personal.model.PersonalInfo;

import java.math.BigDecimal;
import java.util.Date;

import net.paoding.rose.jade.plugin.sql.annotations.Column;
import net.paoding.rose.jade.plugin.sql.annotations.Table;
/**
 * check_work_detail
 * @description: 全通物联网考勤查询条件对象
 * @author 
 * @version 1.0.0 2018-08-27
 */
@Table(value = "check_work_detail")
public class CheckWorkDetail implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -2939499911405688824L;

    /** 表的主键 */
    @Column(pk=true,value = "id")
    private Integer id;

    /** 考勤月份 */
    @Column(value = "term")
    private String term;

    /** 考勤开始时间 */
    @Column(value = "start_date")
    private Date startDate;

    /** 考勤结束时间 */
    @Column(value = "end_date")
    private Date endDate;

    /** 合作厂家 */
    @Column(value = "manufacturer")
    private String manufacturer;

    /** 姓名 */
    @Column(value = "name")
    private String name;

    /** 外派单位 */
    @Column(value = "expatriate_unit")
    private String expatriateUnit;

    /** 入职时间 */
    @Column(value = "entry_time")
    private Date entryTime;

    /** 出勤天数 */
    @Column(value = "attendance_days")
    private BigDecimal attendanceDays;

    /** 考勤天数 */
    @Column(value = "check_work_days")
    private BigDecimal checkWorkDays;

    /** 加班天数 */
    @Column(value = "overtime_days")
    private BigDecimal overtimeDays;

    /** 请假天数 */
    @Column(value = "leave_days")
    private BigDecimal leaveDays;

    /** 负责人 */
    @Column(value = "manager")
    private String manager;

    /** 本月调休天数 */
    @Column(value = "cur_off_duty_shift_days")
    private BigDecimal curOffDutyShiftDays;
    
    /** 本月事假天数 */
    @Column(value = "cur_compassionate_days")
    private BigDecimal curCompassionateDays;
    
    /** 本月年假天数 */
    @Column(value = "cur_annual_days")
    private BigDecimal curAnnualDays;
    
    /** 婚假 */
    @Column(value = "cur_marital_days")
    private BigDecimal curMaritalDays;
    
    
    /** 备注 */
    @Column(value = "memo")
    private String memo;

    /** 上一年剩余加班天数 */
    @Column(value = "surplus_overtime_days")
    private BigDecimal surplusOvertimeDays;

//    /** 可休年假天数 */
//    @Column(value = "annual_leave_days")
//    private BigDecimal annualLeaveDays;
//
//    /** 剩余年休天数 */
//    @Column(value = "surplus_annual_leave")
//    private BigDecimal surplusAnnualLeave;
//
//    /** 累计长期病假天数 */
//    @Column(value = "sick_leave_days")
//    private BigDecimal sickLeaveDays;
//
//    /** 累计长期事假天数 */
//    @Column(value = "compassionate_leave_days")
//    private BigDecimal compassionateLeaveDays;

    /** 当月考勤扣款天数 */
    @Column(value = "settlement_days")
    private BigDecimal settlementDays;

    
    /** 是否删除 1未删除 0已删除 */
    @Column(value = "is_del")
    private Integer isDel;

    /** 编辑时间 */
    @Column(value = "update_time")
    private Date updateTime;

    /** 创建时间 */
    @Column(value = "create_time")
    private Date createTime;

    
    

    private PersonalInfo personalInfo;

    
    
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
     * 获取考勤月份
     * 
     * @return 考勤月份
     */
    public String getTerm() {
        return this.term;
    }

    /**
     * 设置考勤月份
     * 
     * @param term
     *          考勤月份
     */
    public void setTerm(String term) {
        this.term = term;
    }

    /**
     * 获取考勤开始时间
     * 
     * @return 考勤开始时间
     */
    public Date getStartDate() {
        return this.startDate;
    }

    /**
     * 设置考勤开始时间
     * 
     * @param startDate
     *          考勤开始时间
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * 获取考勤结束时间
     * 
     * @return 考勤结束时间
     */
    public Date getEndDate() {
        return this.endDate;
    }

    /**
     * 设置考勤结束时间
     * 
     * @param endDate
     *          考勤结束时间
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * 获取合作厂家
     * 
     * @return 合作厂家
     */
    public String getManufacturer() {
        return this.manufacturer;
    }

    /**
     * 设置合作厂家
     * 
     * @param manufacturer
     *          合作厂家
     */
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
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
     * 获取出勤天数
     * 
     * @return 出勤天数
     */
    public BigDecimal getAttendanceDays() {
        return this.attendanceDays;
    }

    /**
     * 设置出勤天数
     * 
     * @param attendanceDays
     *          出勤天数
     */
    public void setAttendanceDays(BigDecimal attendanceDays) {
        this.attendanceDays = attendanceDays;
    }

    /**
     * 获取考勤天数
     * 
     * @return 考勤天数
     */
    public BigDecimal getCheckWorkDays() {
        return this.checkWorkDays;
    }

    /**
     * 设置考勤天数
     * 
     * @param checkWorkDays
     *          考勤天数
     */
    public void setCheckWorkDays(BigDecimal checkWorkDays) {
        this.checkWorkDays = checkWorkDays;
    }

    /**
     * 获取加班天数
     * 
     * @return 加班天数
     */
    public BigDecimal getOvertimeDays() {
        return this.overtimeDays;
    }

    /**
     * 设置加班天数
     * 
     * @param overtimeDays
     *          加班天数
     */
    public void setOvertimeDays(BigDecimal overtimeDays) {
        this.overtimeDays = overtimeDays;
    }

    /**
     * 获取请假天数
     * 
     * @return 请假天数
     */
    public BigDecimal getLeaveDays() {
        return this.leaveDays;
    }

    /**
     * 设置请假天数
     * 
     * @param leaveDays
     *          请假天数
     */
    public void setLeaveDays(BigDecimal leaveDays) {
        this.leaveDays = leaveDays;
    }

    /**
     * 获取负责人
     * 
     * @return 负责人
     */
    public String getManager() {
        return this.manager;
    }

    /**
     * 设置负责人
     * 
     * @param manager
     *          负责人
     */
    public void setManager(String manager) {
        this.manager = manager;
    }

    
    public BigDecimal getCurOffDutyShiftDays() {
		return curOffDutyShiftDays;
	}

	public void setCurOffDutyShiftDays(BigDecimal curOffDutyShiftDays) {
		this.curOffDutyShiftDays = curOffDutyShiftDays;
	}

	public BigDecimal getCurCompassionateDays() {
		return curCompassionateDays;
	}

	public void setCurCompassionateDays(BigDecimal curCompassionateDays) {
		this.curCompassionateDays = curCompassionateDays;
	}

	public BigDecimal getCurAnnualDays() {
		return curAnnualDays;
	}

	public void setCurAnnualDays(BigDecimal curAnnualDays) {
		this.curAnnualDays = curAnnualDays;
	}

	public BigDecimal getCurMaritalDays() {
		return curMaritalDays;
	}

	public void setCurMaritalDays(BigDecimal curMaritalDays) {
		this.curMaritalDays = curMaritalDays;
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
     * 获取剩余加班天数
     * 
     * @return 剩余加班天数
     */
    public BigDecimal getSurplusOvertimeDays() {
        return this.surplusOvertimeDays;
    }

    /**
     * 设置剩余加班小时数
     * 
     * @param surplusOvertimeHours
     *          剩余加班小时数
     */
    public void setSurplusOvertimeDays(BigDecimal surplusOvertimeDays) {
        this.surplusOvertimeDays = surplusOvertimeDays;
    }

//    /**
//     * 获取可休年假天数
//     * 
//     * @return 可休年假天数
//     */
//    public BigDecimal getAnnualLeaveDays() {
//        return this.annualLeaveDays;
//    }
//
//    /**
//     * 设置可休年假天数
//     * 
//     * @param annualLeaveDays
//     *          可休年假天数
//     */
//    public void setAnnualLeaveDays(BigDecimal annualLeaveDays) {
//        this.annualLeaveDays = annualLeaveDays;
//    }
//
//    /**
//     * 获取剩余年休天数
//     * 
//     * @return 剩余年休天数
//     */
//    public BigDecimal getSurplusAnnualLeave() {
//        return this.surplusAnnualLeave;
//    }
//
//    /**
//     * 设置剩余年休天数
//     * 
//     * @param surplusAnnualLeave
//     *          剩余年休天数
//     */
//    public void setSurplusAnnualLeave(BigDecimal surplusAnnualLeave) {
//        this.surplusAnnualLeave = surplusAnnualLeave;
//    }
//
//    /**
//     * 获取累计长期病假天数
//     * 
//     * @return 累计长期病假天数
//     */
//    public BigDecimal getSickLeaveDays() {
//        return this.sickLeaveDays;
//    }
//
//    /**
//     * 设置累计长期病假天数
//     * 
//     * @param sickLeaveDays
//     *          累计长期病假天数
//     */
//    public void setSickLeaveDays(BigDecimal sickLeaveDays) {
//        this.sickLeaveDays = sickLeaveDays;
//    }
//
//    /**
//     * 获取累计长期事假天数
//     * 
//     * @return 累计长期事假天数
//     */
//    public BigDecimal getCompassionateLeaveDays() {
//        return this.compassionateLeaveDays;
//    }
//
//    /**
//     * 设置累计长期事假天数
//     * 
//     * @param compassionateLeaveDays
//     *          累计长期事假天数
//     */
//    public void setCompassionateLeaveDays(BigDecimal compassionateLeaveDays) {
//        this.compassionateLeaveDays = compassionateLeaveDays;
//    }


    public BigDecimal getSettlementDays() {
		return settlementDays;
	}

	public void setSettlementDays(BigDecimal settlementDays) {
		this.settlementDays = settlementDays;
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

	public PersonalInfo getPersonalInfo() {
		return personalInfo;
	}

	public void setPersonalInfo(PersonalInfo personalInfo) {
		this.personalInfo = personalInfo;
	}

	
    
    
}