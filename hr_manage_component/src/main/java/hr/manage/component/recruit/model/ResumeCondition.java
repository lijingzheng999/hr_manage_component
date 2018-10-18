package hr.manage.component.recruit.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import net.paoding.rose.jade.plugin.sql.annotations.Column;
/**
 * 
* @ClassName: PersonalCondition
* @Description: 员工查询条件对象
* @author 
* @date 
*
 */
public class ResumeCondition implements Serializable {

    /**@Fields serialVersionUID : TODO*/
     
    private static final long serialVersionUID = -905122817335164026L;
    /** 姓名 */
    private String name;
    /** 岗位名称(职位) */
    private String position;
    /** 面试状态 1:进行中;0:未通过;2:面试通过 */
    private Integer status;
    /** 外派单位 -需求表用*/
    private String expatriateUnit;
    /** 起始年龄*/
    private Date startAge;
    /** 截至年龄*/
    private Date endAge;
    /** 起始  经验要求(工作年限,单位年) */
    private Integer startExperience;
    /** 截至 经验要求(工作年限,单位年) */
    private Integer endExperience;
   
    /**分页开始位置*/
    private Integer offset;
    /**每次取的条数*/
    private Integer limit;
    /**排序字段*/
    private String orderby;
    
    
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	
	public Date getStartAge() {
		return startAge;
	}
	public void setStartAge(Date startAge) {
		this.startAge = startAge;
	}
	public Date getEndAge() {
		return endAge;
	}
	public void setEndAge(Date endAge) {
		this.endAge = endAge;
	}
	public Integer getStartExperience() {
		return startExperience;
	}
	public void setStartExperience(Integer startExperience) {
		this.startExperience = startExperience;
	}
	public Integer getEndExperience() {
		return endExperience;
	}
	public void setEndExperience(Integer endExperience) {
		this.endExperience = endExperience;
	}
	public String getExpatriateUnit() {
		return expatriateUnit;
	}
	public void setExpatriateUnit(String expatriateUnit) {
		this.expatriateUnit = expatriateUnit;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getOffset() {
		return offset;
	}
	public void setOffset(Integer offset) {
		this.offset = offset;
	}
	public Integer getLimit() {
		return limit;
	}
	public void setLimit(Integer limit) {
		this.limit = limit;
	}
	public String getOrderby() {
		return orderby;
	}
	public void setOrderby(String orderby) {
		this.orderby = orderby;
	}
	
    
    
}
