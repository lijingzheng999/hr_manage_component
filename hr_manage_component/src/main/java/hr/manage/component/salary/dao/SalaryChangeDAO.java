package hr.manage.component.salary.dao;

import hr.manage.component.salary.model.SalaryChange;
import hr.manage.component.salary.model.SalaryChangeCondition;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.plugin.sql.GenericDAO;

@DAO(catalog="hr_manage")
public interface SalaryChangeDAO  extends GenericDAO<SalaryChange,Integer>{
	
	public static final String TABLE = " salary_change ";
	
	public static final String COLUMNS = " id,personal_info_id,employee_number,name,type,change_range,final_salary,reason ,memo,create_user,create_time  ";

	    
	    @SQL("SELECT  " + COLUMNS + " FROM "+TABLE+" WHERE 1 = 1 " +
	            "#if(:1.name != null  && :1.name !='') { and name = :1.name } " +
	            "#if(:1.employeeNumber != null  && :1.employeeNumber !='') { and employee_number = :1.employeeNumber } " +
	            "#if(:1.type != null  && :1.type >0) { and type = :1.type } " +
	            "#if(:1.startDate != null) { and create_time >= :1.startDate } " +
	            "#if(:1.endDate != null) { and create_time <= :1.endDate } " +
	            " and is_del=1 " +
	            " order by id " +
	             "#if(:1.offset != null && :1.limit != null ){ limit :1.offset , :1.limit }")
	    List<SalaryChange> listSalaryChange(SalaryChangeCondition condition);

	    
	    @SQL("SELECT  count(1) FROM "+TABLE+" WHERE 1 = 1 " +
	    		"#if(:1.name != null  && :1.name !='') { and name = :1.name } " +
	            "#if(:1.employeeNumber != null  && :1.employeeNumber !='') { and employee_number = :1.employeeNumber } " +
	            "#if(:1.type != null  && :1.type >0) { and type = :1.type } " +
	            "#if(:1.startDate != null) { and create_time >= :1.startDate } " +
	            "#if(:1.endDate != null) { and create_time <= :1.endDate } " +
	            " and is_del=1 ")
	    Long countSalaryChange(SalaryChangeCondition condition);
	    
	
	    
	    @SQL(" select max(id) from "+ TABLE
				+ " WHERE personal_info_id= :1  ")
	    int getMaxContractCountById(Integer personalInfoId);
	    
	    @SQL(" select " + COLUMNS + " from "+ TABLE
				+ " WHERE personal_info_id= :1 ")
	    int countContractInfoByPersonalId(Integer personalInfoId);
}
