package hr.manage.component.personal.dao;

import hr.manage.component.personal.model.PersonalAllExport;
import hr.manage.component.personal.model.PersonalCondition;
import hr.manage.component.personal.model.PersonalInfo;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.plugin.sql.GenericDAO;

@DAO(catalog = "hr_manage")
public interface PersonalInfoDAO extends GenericDAO<PersonalInfo, Integer> {

	public static final String TABLE = " personal_info ";

	public static final String COLUMNS = "   id,employee_number,name,phone,email,identity_card,sex,age,birthday,home_property,native_place,marriage,nation,title,education,school,major,english,graduation_time,working_life,contact_address,home_address,contact,contact_phone,memo,is_del,update_time,create_time  ";

	public static final String COLUMNS_INFO = " a.id,a.employee_number,a.name,a.phone,a.email,a.identity_card,a.sex,a.age,a.birthday,a.home_property,a.native_place,a.marriage,a.nation,a.title,a.education,a.school,a.major,a.english,a.graduation_time,a.working_life,a.contact_address,a.home_address,a.contact,a.contact_phone,a.memo,a.create_time   ";

	public static final String COLUMNS_WORK = " b.working_place,b.post_type,b.position,b.level,b.department,b.center,b.project,b.expatriate_manager,b.expatriate_unit,b.recruit_channel,b.working_address,b.contract_count,b.contract_startdate,b.contract_enddate,b.contract_renew_date,b.contract_renew_enddate,b.leave_status,b.leave_type,b.leave_reason,b.leave_working_time ";

	public static final String COLUMNS_SALARY= " c.entry_time,c.arrival_time,c.worker_time,c.working_years,c.insurance_begin_date,c.insurance_real_date,c.insurance_place,c.bank_card_number,c.bank_open_place,c.probation_period,c.probation_period_welfare,c.worker_welfare,c.base_pay,c.merit_pay,c.subsidy,c.worker_pay,c.probationary_pay,c.settlement_price ";

	
	@SQL("SELECT  "
			+ COLUMNS
			+ " FROM "
			+ TABLE
			+ " WHERE id= :1 and is_del=1 ")
	PersonalInfo getPersonalInfoById(Integer personalInfoId);
	
	@SQL(" update  "+ TABLE
			+ " set is_del=0 "
			+ " WHERE id= :1 and is_del=1 ")
	int deletePersonalInfoById(Integer personalInfoId);
	
	@SQL(" select count(1)  FROM " + TABLE
			+ "  where name = :1 and identity_card=:2 and is_del=1 ")
	int checkPersonalByNameAndCard(String name, String identityCard);

	@SQL(" select "+ COLUMNS +  " FROM " + TABLE
			+ "  where name = :1  and is_del=1 ")
	PersonalInfo getPersonalByName(String name);

	
	@SQL(" select count(1)  FROM " + TABLE)
	int countPersonalInfo();

	@SQL("SELECT  "
			+ COLUMNS
			+ " FROM "
			+ TABLE
			+ " WHERE 1 = 1 "
			+ "#if(:1.employee_number != null) { and employee_number = :1.employeeNumber } "
			+ " order by id ")
	PersonalInfo getPersonalInfoByModel(PersonalInfo personalInfo);

	
	@SQL(" SELECT " +COLUMNS_INFO +","+COLUMNS_WORK+","+COLUMNS_SALARY 
			+" FROM personal_info a "
            + "INNER JOIN personal_work_info b on a.id=b.personal_info_id "
            + "INNER JOIN personal_salary_info c on a.id= c.personal_info_id "
            + " WHERE 1 = 1 "
            + "#if(:1.name != null && :1.name !=''){ and a.name = :1.name } " 
            + "#if(:1.expatriateUnit != null && :1.expatriateUnit !='') { and b.expatriate_unit = :1.expatriateUnit } "
            + "#if(:1.postType != null && :1.postType !=''){ and b.post_type = :1.postType } " 
            + "#if(:1.position != null && :1.position !=''){ and b.position = :1.position } " 
            + "#if(:1.level != null && :1.level !=''){ and b.level = :1.level } " 
            + "#if(:1.department != null && :1.department !=''){ and b.department = :1.department } " 
            + "#if(:1.center != null && :1.center !=''){ and b.center = :1.center } " 
            + "#if(:1.workingPlace != null && :1.workingPlace !=''){ and b.working_place like concat('%',:1.workingPlace,'%') } " 
            + "#if(:1.leaveStatus != null && :1.leaveStatus >=0){ and b.leave_status = :1.leaveStatus } " 
            + "#if(:1.entryStartDate != null) { and c.entry_time >= :1.entryStartDate } " 
            + "#if(:1.entryEndDate != null) { and c.entry_time <= :1.entryEndDate } " 
            + "#if(:1.workerStartDate != null) { and c.worker_time >= :1.workerStartDate } " 
            + "#if(:1.workerEndDate != null) { and c.worker_time <= :1.workerEndDate } "
            + "#if(:1.leaveStartDate != null) { and b.leave_working_time >= :1.leaveStartDate } " 
            + "#if(:1.leaveEndDate != null) { and b.leave_working_time <= :1.leaveEndDate } "
            + "#if(:1.birthdayStartDate != null) { and DATE_FORMAT(a.birthday,'%m%d') >=  DATE_FORMAT(:1.birthdayStartDate,'%m%d') } " 
            + "#if(:1.birthdayEndDate != null) { and DATE_FORMAT(a.birthday,'%m%d') <= DATE_FORMAT(:1.birthdayEndDate,'%m%d')  } "
            + " and a.is_del=1  and b.is_del=1  and c.is_del=1 " 
            + " order by a.id " 
            + "#if(:1.offset != null && :1.limit != null ){ limit :1.offset , :1.limit }")
	List<PersonalAllExport> listPersonalAllExport(PersonalCondition condition);
	
	
	@SQL(" SELECT count(1) FROM personal_info a "
            + "INNER JOIN personal_work_info b on a.id=b.personal_info_id "
            + "INNER JOIN personal_salary_info c on a.id= c.personal_info_id "
            + " WHERE 1 = 1 "
            + "#if(:1.name != null && :1.name !=''){ and a.name = :1.name } " 
            + "#if(:1.expatriateUnit != null && :1.expatriateUnit !='') { and b.expatriate_unit = :1.expatriateUnit } "
            + "#if(:1.postType != null && :1.postType !=''){ and b.post_type = :1.postType } " 
            + "#if(:1.position != null && :1.position !=''){ and b.position = :1.position } " 
            + "#if(:1.level != null && :1.level !=''){ and b.level = :1.level } " 
            + "#if(:1.department != null && :1.department !=''){ and b.department = :1.department } " 
            + "#if(:1.center != null && :1.center !=''){ and b.center = :1.center } " 
            + "#if(:1.workingPlace != null && :1.workingPlace !=''){ and b.working_place like concat('%',:1.workingPlace,'%') } " 
            + "#if(:1.leaveStatus != null && :1.leaveStatus >=0){ and b.leave_status = :1.leaveStatus } " 
            + "#if(:1.entryStartDate != null) { and c.entry_time >= :1.entryStartDate } " 
            + "#if(:1.entryEndDate != null) { and c.entry_time <= :1.entryEndDate } " 
            + "#if(:1.workerStartDate != null) { and c.worker_time >= :1.workerStartDate } " 
            + "#if(:1.workerEndDate != null) { and c.worker_time <= :1.workerEndDate } "
            + "#if(:1.leaveStartDate != null) { and b.leave_working_time >= :1.leaveStartDate } " 
            + "#if(:1.leaveEndDate != null) { and b.leave_working_time <= :1.leaveEndDate } "
            + "#if(:1.birthdayStartDate != null) { and DATE_FORMAT(a.birthday,'%m%d') >=  DATE_FORMAT(:1.birthdayStartDate,'%m%d') } " 
            + "#if(:1.birthdayEndDate != null) { and DATE_FORMAT(a.birthday,'%m%d') <= DATE_FORMAT(:1.birthdayEndDate,'%m%d')  } "
            + " and a.is_del=1  and b.is_del=1  and c.is_del=1 " )
            Long countPersonalAllExport(PersonalCondition condition);

}
