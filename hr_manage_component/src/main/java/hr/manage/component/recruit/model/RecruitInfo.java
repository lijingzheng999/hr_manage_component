package hr.manage.component.recruit.model;
import java.util.Date;

import net.paoding.rose.jade.plugin.sql.annotations.Column;
import net.paoding.rose.jade.plugin.sql.annotations.Table;

/**
 * recruit_info
 * 
 * @author 
 * @version 1.0.0 2018-07-24
 */
@Table(value = "recruit_info")
public class RecruitInfo implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -6886293987267276739L;

    /** 表的主键 */
    @Column(pk=true,value = "id")
    private Integer id;

    /** 外派单位 */
    @Column(value = "expatriate_unit")
    private String expatriateUnit;
    
    /** 中心 */
    @Column(value = "center")
    private String center;
    
    /** 地市 */
    @Column(value = "city")
    private String city;
    
    /** 所在职场 */
    @Column(value = "work_place")
    private String workPlace;
    
    /** 人员缺口 */
    @Column(value = "pepole_need")
    private Integer pepoleNeed;
    
    /** 岗位名称(职位) */
    @Column(value = "position")
    private String position;

    /** 状态; 0:已完成；1:进行中 */
    @Column(value = "status")
    private Integer status;

    /** 岗位职责 */
    @Column(value = "post_duty")
    private String postDuty;

    /** 创建人 */
    @Column(value = "create_user")
    private String createUser;

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

	public String getExpatriateUnit() {
		return expatriateUnit;
	}

	public void setExpatriateUnit(String expatriateUnit) {
		this.expatriateUnit = expatriateUnit;
	}

	public String getCenter() {
		return center;
	}

	public void setCenter(String center) {
		this.center = center;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getWorkPlace() {
		return workPlace;
	}

	public void setWorkPlace(String workPlace) {
		this.workPlace = workPlace;
	}

	public Integer getPepoleNeed() {
		return pepoleNeed;
	}

	public void setPepoleNeed(Integer pepoleNeed) {
		this.pepoleNeed = pepoleNeed;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getPostDuty() {
		return postDuty;
	}

	public void setPostDuty(String postDuty) {
		this.postDuty = postDuty;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
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