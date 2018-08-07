package hr.manage.component.personal.dao;

import hr.manage.component.personal.model.PersonalSalaryInfo;
import hr.manage.component.personal.model.PersonalWorkInfo;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.plugin.sql.GenericDAO;

@DAO(catalog="hr_manage")
public interface PersonalSalaryInfoDAO  extends GenericDAO<PersonalSalaryInfo,Integer>{
	
	public static final String TABLE = " personal_salary_info ";
	
	public static final String COLUMNS = "  id,personal_info_id,entry_time,arrival_time,worker_time,working_years,insurance_begin_date,insurance_place,bank_card_number,bank_open_place,probation_period,probation_period_welfare,worker_welfare,base_pay,merit_pay,subsidy,worker_pay,probationary_pay,settlement_price,is_del,update_time,create_time ";

	@SQL("SELECT  "
			+ COLUMNS
			+ " FROM "
			+ TABLE
			+ " WHERE personal_info_id= :1 and is_del=1 ")
	PersonalSalaryInfo getPersonalSalaryInfoById(Integer personalInfoId);

	@SQL(" update  "+ TABLE
			+ " set is_del=0 "
			+ " WHERE personal_info_id= :1 and is_del=1 ")
	int deletePersonalSalaryInfoById(Integer personalInfoId);
}
