package hr.manage.component.salary.model;
import java.util.Date;

import net.paoding.rose.jade.plugin.sql.annotations.Column;
import net.paoding.rose.jade.plugin.sql.annotations.Table;

/**
 * salary_change
 * 
 * @author 
 * @version 1.0.0 2018-07-24
 */
@Table(value = "salary_change")
public class SalaryChange implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -5947481316976953272L;

    /** 表的主键 */
    @Column(pk=true,value = "id")
    private Integer id;

    /** 员工信息表ID */
    @Column(value = "personal_info_id")
    private Integer personalInfoId;
    
    /** 员工编号 项目+部门+自增编号 */
    @Column(value = "employee_number")
    private String employeeNumber;

    /** 姓名 */
    @Column(value = "name")
    private String name;

    /** 调薪类型 1:调级别；2:调薪;3:其他; */
    @Column(value = "type")
    private Integer type;

    /** 调薪幅度 */
    @Column(value = "change_range")
    private Integer changeRange;

    /** 调薪后工资 */
    @Column(value = "final_salary")
    private Integer finalSalary;

    /** 调薪原因 */
    @Column(value = "reason")
    private String reason;

    /** 备注 */
    @Column(value = "memo")
    private String memo;


    /** 创建人 */
    @Column(value = "create_user")
    private String createUser;

    /** 创建时间 */
    @Column(value = "create_time")
    private Date createTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPersonalInfoId() {
		return personalInfoId;
	}

	public void setPersonalInfoId(Integer personalInfoId) {
		this.personalInfoId = personalInfoId;
	}

	public String getEmployeeNumber() {
		return employeeNumber;
	}

	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getChangeRange() {
		return changeRange;
	}

	public void setChangeRange(Integer changeRange) {
		this.changeRange = changeRange;
	}

	public Integer getFinalSalary() {
		return finalSalary;
	}

	public void setFinalSalary(Integer finalSalary) {
		this.finalSalary = finalSalary;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

   
}