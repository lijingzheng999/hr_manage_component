package hr.manage.component.checkwork.model;
import java.math.BigDecimal;
import java.util.Date;

import net.paoding.rose.jade.plugin.sql.annotations.Column;
import net.paoding.rose.jade.plugin.sql.annotations.Table;
/**
 * check_work_current_log
 * 
 * @author 
 * @version 1.0.0 2018-08-28
 */
@Table(value = "check_work_current_log")
public class CheckWorkCurrentLog implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -6576725642213606532L;

    /** 表的主键 */
    @Column(pk=true,value = "id")
    private Integer id;

    /** 考勤月份 */
    @Column(value = "term")
    private String term;

    /** 员工信息表ID */
    @Column(value = "personal_info_id")
    private Integer personalInfoId;

    /** 姓名 */
    @Column(value = "name")
    private String name;

    /** 变更加班小时数 */
    @Column(value = "surplus_overtime_hours")
    private Integer surplusOvertimeHours;

    /** 变更剩余年休天数 */
    @Column(value = "surplus_annual_leave")
    private BigDecimal surplusAnnualLeave;

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
     * 获取变更加班小时数
     * 
     * @return 变更加班小时数
     */
    public Integer getSurplusOvertimeHours() {
        return this.surplusOvertimeHours;
    }

    /**
     * 设置变更加班小时数
     * 
     * @param surplusOvertimeHours
     *          变更加班小时数
     */
    public void setSurplusOvertimeHours(Integer surplusOvertimeHours) {
        this.surplusOvertimeHours = surplusOvertimeHours;
    }

    /**
     * 获取变更剩余年休天数
     * 
     * @return 变更剩余年休天数
     */
    public BigDecimal getSurplusAnnualLeave() {
        return this.surplusAnnualLeave;
    }

    /**
     * 设置变更剩余年休天数
     * 
     * @param surplusAnnualLeave
     *          变更剩余年休天数
     */
    public void setSurplusAnnualLeave(BigDecimal surplusAnnualLeave) {
        this.surplusAnnualLeave = surplusAnnualLeave;
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