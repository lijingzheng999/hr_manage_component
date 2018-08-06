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
    /** 岗位名称(职位) */
    private String position;
    /** 经验要求(工作年限,单位年) */
    private Integer experience;
    /** 年龄 */
    private Integer age;
  
   
    /**分页开始位置*/
    private Integer offset;
    /**每次取的条数*/
    private Integer limit;
    /**排序字段*/
    private String orderby;
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public Integer getExperience() {
		return experience;
	}
	public void setExperience(Integer experience) {
		this.experience = experience;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
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
