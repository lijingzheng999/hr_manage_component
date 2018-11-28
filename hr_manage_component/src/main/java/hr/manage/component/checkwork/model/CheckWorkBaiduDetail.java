package hr.manage.component.checkwork.model;
import java.math.BigDecimal;
import java.util.Date;

import net.paoding.rose.jade.plugin.sql.annotations.Column;
import net.paoding.rose.jade.plugin.sql.annotations.Table;
/**
 * check_work_baidu_detail
 * 
 * @author 
 * @version 1.0.0 2018-10-07
 */

@Table(value = "check_work_baidu_detail")
public class CheckWorkBaiduDetail implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -4428779267592524519L;

    /** 表的主键 */
    @Column(pk=true,value = "id")
    private Integer id;

    /** 考勤表的主键 */
    @Column(value = "check_work_id")
    private Integer checkWorkId;

    /** 0:白班；1：晚班 */
    @Column(value = "type")
    private Integer type;

    /** 0:正常班；1：普通加班；2：周末加班；3：节假日加班 （4倍） ；4：节假日正常班(3倍)  ；5：年假算正常班 ；6：病假不算上班；7事假不算上班*/
    @Column(value = "work_type")
    private Integer workType;

    /** 考勤日（几号1-31） */
    @Column(value = "current_day")
    private Integer currentDay;

    /** 工作小时数 */
    @Column(value = "work_hours")
    private BigDecimal workHours;

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
     * 获取考勤表的主键
     * 
     * @return 考勤表的主键
     */
    public Integer getCheckWorkId() {
        return this.checkWorkId;
    }

    /**
     * 设置考勤表的主键
     * 
     * @param checkWorkId
     *          考勤表的主键
     */
    public void setCheckWorkId(Integer checkWorkId) {
        this.checkWorkId = checkWorkId;
    }

    /**
     * 获取0:白班；1：晚班
     * 
     * @return 0:白班；1
     */
    public Integer getType() {
        return this.type;
    }

    /**
     * 设置0:白班；1：晚班
     * 
     * @param type
     *          0:白班；1
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取0:正常班；1：普通加班；2：周末加班；3：节假日加班
     * 
     * @return 0:正常班；1
     */
    public Integer getWorkType() {
        return this.workType;
    }

    /**
     * 设置0:正常班；1：普通加班；2：周末加班；3：节假日加班
     * 
     * @param workType
     *          0:正常班；1
     */
    public void setWorkType(Integer workType) {
        this.workType = workType;
    }

    /**
     * 获取考勤日（几号1-31）
     * 
     * @return 考勤日（几号1-31）
     */
    public Integer getCurrentDay() {
        return this.currentDay;
    }

    /**
     * 设置考勤日（几号1-31）
     * 
     * @param currentDay
     *          考勤日（几号1-31）
     */
    public void setCurrentDay(Integer currentDay) {
        this.currentDay = currentDay;
    }

    /**
     * 获取工作小时数
     * 
     * @return 工作小时数
     */
    public BigDecimal getWorkHours() {
        return this.workHours;
    }

    /**
     * 设置工作小时数
     * 
     * @param workHours
     *          工作小时数
     */
    public void setWorkHours(BigDecimal workHours) {
        this.workHours = workHours;
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