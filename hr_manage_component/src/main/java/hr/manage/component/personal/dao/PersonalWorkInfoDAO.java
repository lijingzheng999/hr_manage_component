package hr.manage.component.personal.dao;

import hr.manage.component.personal.model.PersonalWorkInfo;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.plugin.sql.GenericDAO;

@DAO(catalog="hr_manage")
public interface PersonalWorkInfoDAO  extends GenericDAO<PersonalWorkInfo,Integer>{
	
	public static final String TABLE = " personal_work_info ";
	
	public static final String COLUMNS = " id,personal_info_id,working_place,post_type,position,level,department,center,project,expatriate_manager,expatriate_unit,recruit_channel,working_address,contract_number,contract_date,contract_startdate,contract_enddate,contract_renew_date,contract_renew_enddate,is_leave,leave_working,leave_working_time,is_del,update_time,create_time ";

	    
//	    @SQL("SELECT  " + COLUMNS + " FROM "+TABLE+" WHERE 1 = 1 " +
//	            "#if(:1.employee_number != null) { and employee_number = :1.employeeNumber } " +
//	            " order by id ")
//	    List<ContractInfo> listTradeInfo(ContractInfo contractInfo);

}
