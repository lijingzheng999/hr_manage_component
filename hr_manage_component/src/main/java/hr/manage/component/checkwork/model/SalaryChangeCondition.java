package hr.manage.component.checkwork.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 
* @ClassName: SalaryChangeCondition
* @Description: 工资变更查询条件对象
* @author 
* @date 
*
 */
public class SalaryChangeCondition implements Serializable {

    /**@Fields serialVersionUID : TODO*/
     
    private static final long serialVersionUID = -315122817335264041L;
    /**姓名 */
    private String name;
    /**员工编号*/
    private String employeeNumber;
    /**调薪类型*/
    private Integer type;
    /**调整工资记录生成时间的开始时间 */
    private Date startDate;
    /**调整工资记录生成时间的开始时间 */
    private Date endDate;
  
   
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
	public String getEmployeeNumber() {
		return employeeNumber;
	}
	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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
