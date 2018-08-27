package hr.manage.component.checkwork.model;

import java.io.Serializable;
/**
 * 
* @ClassName: CheckWorkDetailCondition
* @Description: 全通物联网考勤查询条件对象
* @author 
* @date 
*
 */
public class CheckWorkDetailCondition implements Serializable {

    /**@Fields serialVersionUID : TODO*/
     
    private static final long serialVersionUID = -315122817335264041L;
    /**姓名 */
    private String name;
    /** 考勤月份 */
    private String term;
    /** 外派单位 */
    private String expatriateUnit;
  
   
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
	public String getTerm() {
		return term;
	}
	public void setTerm(String term) {
		this.term = term;
	}
	public String getExpatriateUnit() {
		return expatriateUnit;
	}
	public void setExpatriateUnit(String expatriateUnit) {
		this.expatriateUnit = expatriateUnit;
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
