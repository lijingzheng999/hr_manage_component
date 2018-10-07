package hr.manage.component.checkwork.model;

import java.math.BigDecimal;
import java.util.Date;

import net.paoding.rose.jade.plugin.sql.annotations.Column;
import net.paoding.rose.jade.plugin.sql.annotations.Table;

/**
 * check_work_baidu
 * 
 * @author 
 * @version 1.0.0 2018-10-07
 */
@Table(value = "check_work_baidu")
public class CheckWorkBaidu implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 3059269972283686150L;

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

    /** 姓名 */
    @Column(value = "name")
    private String name;

    /** 应出勤小时数 */
    @Column(value = "attendance_hours")
    private BigDecimal attendanceHours;

    /** 实际出勤小时数 */
    @Column(value = "check_work_hours")
    private BigDecimal checkWorkHours;

    /** 超出小时数（实际出勤-应出勤=超出小时数） */
    @Column(value = "overstep_hours")
    private BigDecimal overstepHours;

    /** 超出小时折算全通给我司结算为天数 */
    @Column(value = "overstep_days")
    private BigDecimal overstepDays;

    /** 加班小时数 */
    @Column(value = "overtime_hours")
    private BigDecimal overtimeHours;

    /** 1倍核算天数 */
    @Column(value = "one_hours")
    private String oneHours;

    /** 1.5倍核算天数 */
    @Column(value = "one_point_five_hours")
    private Integer onePointFiveHours;

    /** 2倍核算天数 */
    @Column(value = "two_hours")
    private BigDecimal twoHours;

    /** 3倍核算天数 */
    @Column(value = "three_hours")
    private BigDecimal threeHours;

    /** 加班应发工资合计小时数 */
    @Column(value = "overtime_sum_hours")
    private BigDecimal overtimeSumHours;

    /** 折算全通给我司结算为天数 */
    @Column(value = "overtime_settle_days")
    private BigDecimal overtimeSettleDays;

    /** 全通加班结算天数合计 */
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
     * 获取应出勤小时数
     * 
     * @return 应出勤小时数
     */
    public BigDecimal getAttendanceHours() {
        return this.attendanceHours;
    }

    /**
     * 设置应出勤小时数
     * 
     * @param attendanceHours
     *          应出勤小时数
     */
    public void setAttendanceHours(BigDecimal attendanceHours) {
        this.attendanceHours = attendanceHours;
    }

    /**
     * 获取实际出勤小时数
     * 
     * @return 实际出勤小时数
     */
    public BigDecimal getCheckWorkHours() {
        return this.checkWorkHours;
    }

    /**
     * 设置实际出勤小时数
     * 
     * @param checkWorkHours
     *          实际出勤小时数
     */
    public void setCheckWorkHours(BigDecimal checkWorkHours) {
        this.checkWorkHours = checkWorkHours;
    }

    /**
     * 获取超出小时数（实际出勤-应出勤=超出小时数）
     * 
     * @return 超出小时数（实际出勤-应出勤=超出小时数）
     */
    public BigDecimal getOverstepHours() {
        return this.overstepHours;
    }

    /**
     * 设置超出小时数（实际出勤-应出勤=超出小时数）
     * 
     * @param overstepHours
     *          超出小时数（实际出勤-应出勤=超出小时数）
     */
    public void setOverstepHours(BigDecimal overstepHours) {
        this.overstepHours = overstepHours;
    }

    /**
     * 获取超出小时折算全通给我司结算为天数
     * 
     * @return 超出小时折算全通给我司结算为天数
     */
    public BigDecimal getOverstepDays() {
        return this.overstepDays;
    }

    /**
     * 设置超出小时折算全通给我司结算为天数
     * 
     * @param overstepDays
     *          超出小时折算全通给我司结算为天数
     */
    public void setOverstepDays(BigDecimal overstepDays) {
        this.overstepDays = overstepDays;
    }

    /**
     * 获取加班小时数
     * 
     * @return 加班小时数
     */
    public BigDecimal getOvertimeHours() {
        return this.overtimeHours;
    }

    /**
     * 设置加班小时数
     * 
     * @param overtimeHours
     *          加班小时数
     */
    public void setOvertimeHours(BigDecimal overtimeHours) {
        this.overtimeHours = overtimeHours;
    }

    /**
     * 获取1倍核算天数
     * 
     * @return 1倍核算天数
     */
    public String getOneHours() {
        return this.oneHours;
    }

    /**
     * 设置1倍核算天数
     * 
     * @param oneHours
     *          1倍核算天数
     */
    public void setOneHours(String oneHours) {
        this.oneHours = oneHours;
    }

    /**
     * 获取1.5倍核算天数
     * 
     * @return 1.5倍核算天数
     */
    public Integer getOnePointFiveHours() {
        return this.onePointFiveHours;
    }

    /**
     * 设置1.5倍核算天数
     * 
     * @param onePointFiveHours
     *          1.5倍核算天数
     */
    public void setOnePointFiveHours(Integer onePointFiveHours) {
        this.onePointFiveHours = onePointFiveHours;
    }

    /**
     * 获取2倍核算天数
     * 
     * @return 2倍核算天数
     */
    public BigDecimal getTwoHours() {
        return this.twoHours;
    }

    /**
     * 设置2倍核算天数
     * 
     * @param twoHours
     *          2倍核算天数
     */
    public void setTwoHours(BigDecimal twoHours) {
        this.twoHours = twoHours;
    }

    /**
     * 获取3倍核算天数
     * 
     * @return 3倍核算天数
     */
    public BigDecimal getThreeHours() {
        return this.threeHours;
    }

    /**
     * 设置3倍核算天数
     * 
     * @param threeHours
     *          3倍核算天数
     */
    public void setThreeHours(BigDecimal threeHours) {
        this.threeHours = threeHours;
    }

    /**
     * 获取加班应发工资合计小时数
     * 
     * @return 加班应发工资合计小时数
     */
    public BigDecimal getOvertimeSumHours() {
        return this.overtimeSumHours;
    }

    /**
     * 设置加班应发工资合计小时数
     * 
     * @param overtimeSumHours
     *          加班应发工资合计小时数
     */
    public void setOvertimeSumHours(BigDecimal overtimeSumHours) {
        this.overtimeSumHours = overtimeSumHours;
    }

    /**
     * 获取折算全通给我司结算为天数
     * 
     * @return 折算全通给我司结算为天数
     */
    public BigDecimal getOvertimeSettleDays() {
        return this.overtimeSettleDays;
    }

    /**
     * 设置折算全通给我司结算为天数
     * 
     * @param overtimeSettleDays
     *          折算全通给我司结算为天数
     */
    public void setOvertimeSettleDays(BigDecimal overtimeSettleDays) {
        this.overtimeSettleDays = overtimeSettleDays;
    }

    /**
     * 获取全通加班结算天数合计
     * 
     * @return 全通加班结算天数合计
     */
    public BigDecimal getSettlementDays() {
        return this.settlementDays;
    }

    /**
     * 设置全通加班结算天数合计
     * 
     * @param settlementDays
     *          全通加班结算天数合计
     */
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
}