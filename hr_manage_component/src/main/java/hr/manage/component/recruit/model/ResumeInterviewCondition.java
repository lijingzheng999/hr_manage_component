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
public class ResumeInterviewCondition implements Serializable {

    /**@Fields serialVersionUID : TODO*/
     
    private static final long serialVersionUID = -905122817335164026L;
    /** 姓名 */
    private String name;
    /**外派单位*/
    private String expatriateUnit;
    /** 岗位名称(职位) */
    private String position;
    /** 入职状态：2：已入职，1：待入职；0：未入职（未入职来个原因） */
    private Integer status;
  
   
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
	
	public String getExpatriateUnit() {
		return expatriateUnit;
	}
	public void setExpatriateUnit(String expatriateUnit) {
		this.expatriateUnit = expatriateUnit;
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
