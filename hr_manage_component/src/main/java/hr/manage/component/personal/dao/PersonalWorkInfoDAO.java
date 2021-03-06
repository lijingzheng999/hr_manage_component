package hr.manage.component.personal.dao;

import hr.manage.component.personal.model.PersonalWorkInfo;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.plugin.sql.GenericDAO;

@DAO(catalog="hr_manage")
public interface PersonalWorkInfoDAO  extends GenericDAO<PersonalWorkInfo,Integer>{
	
	public static final String TABLE = " personal_work_info ";
	
	public static final String COLUMNS = " id,personal_info_id,working_place,post_type,position,level,department,center,project,expatriate_manager,expatriate_unit,recruit_channel,working_address,contract_count,contract_startdate,contract_enddate,contract_renew_date,contract_renew_enddate,leave_status,leave_type,leave_reason,leave_working_time,is_del,update_time,create_time ";

	@SQL("SELECT  "
			+ COLUMNS
			+ " FROM "
			+ TABLE
			+ " WHERE personal_info_id= :1 and is_del=1 ")
	PersonalWorkInfo getPersonalWorkInfoById(Integer personalInfoId);

	@SQL(" update  "+ TABLE
			+ " set is_del=0 "
			+ " WHERE personal_info_id= :1 and is_del=1 ")
	int deletePersonalWorkInfoById(Integer personalInfoId);
}
