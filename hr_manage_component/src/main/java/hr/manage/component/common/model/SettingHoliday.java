package hr.manage.component.common.model;

import java.util.Date;

/**
 * common_type
 * 
 * @author 
 * @version 1.0.0 2018-07-24
 */


import net.paoding.rose.jade.plugin.sql.annotations.Column;
import net.paoding.rose.jade.plugin.sql.annotations.Table;
@Table("setting_holiday")
public class SettingHoliday implements java.io.Serializable {
    /** 版本号 */
	private static final long serialVersionUID = -7980810640567856537L;

	/** 表的主键 */
    @Column(pk=true,value = "id")
    private Integer id;

    /** 日期类型 1标记为普通工作日 2标记为周末 3标记为节假日 */
    @Column(value = "type")
    private Integer type;

    /** 当前日期 */
    @Column(value = "cur_date")
    private Date curDate;

    /** 是否删除 1未删除 0已删除 */
    @Column(value = "is_del")
    private Integer isDel;

    /** 修改时间 */
    @Column(value = "update_time")
    private Date updateTime;

    
    /** 创建时间 */
    @Column(value = "create_time")
    private Date createTime;


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Integer getType() {
		return type;
	}


	public void setType(Integer type) {
		this.type = type;
	}


	public Date getCurDate() {
		return curDate;
	}


	public void setCurDate(Date curDate) {
		this.curDate = curDate;
	}


	public Integer getIsDel() {
		return isDel;
	}


	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}


	public Date getUpdateTime() {
		return updateTime;
	}


	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}


	public Date getCreateTime() {
		return createTime;
	}


	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

    
}