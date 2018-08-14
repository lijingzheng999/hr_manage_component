package hr.manage.component.salary.dao;

import hr.manage.component.salary.model.SalaryChange;
import hr.manage.component.salary.model.SalaryChangeCondition;
import hr.manage.component.salary.model.SalaryDetail;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.plugin.sql.GenericDAO;

@DAO(catalog="hr_manage")
public interface SalaryDetailDAO  extends GenericDAO<SalaryDetail,Integer>{
	
	public static final String TABLE = " salary_detail ";
	
	public static final String COLUMNS = "  id,personal_info_id,term,start_date,end_date,name,entry_time,expatriate_unit,probationary_pay,base_pay,merit_pay,other_pay,traffic_subsidy,computer_subsidy,meal_subsidy,phone_subsidy,attendance_deduction,other_deduction,should_pay,endowment,medical,unemployment,accumulation_fund,insurance_deduction,invoice,tax_pay,should_tax_amount,tax,deduct_number,income_tax,real_pay,bank_pay,cash,is_del,update_time,create_time  ";

	    
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
