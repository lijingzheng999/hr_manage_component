package hr.manage.component.personal.model;

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
public class PersonalCondition implements Serializable {

    /**@Fields serialVersionUID : TODO*/
     
    private static final long serialVersionUID = -905122817335164026L;
    /**姓名 */
    private String name;
    /**外派单位*/
    private String expatriateUnit;
    /**岗位 */
    private String postType;
    /**部门 */
    private String department;
    /**中心 */
    private String center;
    /**工作地点 */
    private String workingPlace;
   
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
	public String getPostType() {
		return postType;
	}
	public void setPostType(String postType) {
		this.postType = postType;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getCenter() {
		return center;
	}
	public void setCenter(String center) {
		this.center = center;
	}
	public String getWorkingPlace() {
		return workingPlace;
	}
	public void setWorkingPlace(String workingPlace) {
		this.workingPlace = workingPlace;
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
