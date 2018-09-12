package hr.manage.component.salary.model;

import java.io.Serializable;
import java.util.Date;

import net.paoding.rose.jade.plugin.sql.annotations.Column;

/**
 * 
* @ClassName: ProfitDetailCondition
* @Description: 利润测算表查询条件对象
* @author 
* @date 
*
 */
public class ProfitDetailCondition implements Serializable {

    /**@Fields serialVersionUID : TODO*/
     
    private static final long serialVersionUID = -315122817335264041L;
    /**姓名 */
    private String name;
    /** 账期 */
    private String term;
   
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
