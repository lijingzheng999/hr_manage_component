package hr.manage.component.recruit.dao;

import hr.manage.component.recruit.model.ResumeCondition;
import hr.manage.component.recruit.model.ResumeInfo;
import hr.manage.component.recruit.model.ResumeInterview;
import hr.manage.component.recruit.model.ResumeInterviewCondition;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.plugin.sql.GenericDAO;

@DAO(catalog="hr_manage")
public interface ResumeInterviewDAO  extends GenericDAO<ResumeInterview,Integer>{
	
	public static final String TABLE = " resume_interview ";
	
	public static final String COLUMNS = " id,resume_id,name,sex,first_time,entry_time,first_arrival_time,final_communicate_time,final_communicate_result,first_communicate_time,first_communicate_result,expatriate_unit,interview_time,location,position,level,want_salary,final_salary,insurance,probation_period,probation_period_welfare,worker_welfare,base_pay,merit_pay,subsidy,probationary_pay,phone,email,recruit_channel,project_manager,memo,status,is_del,update_time,create_time ";

	    
	    @SQL("SELECT  " + COLUMNS + " FROM "+TABLE+" WHERE 1 = 1 " +
	            "#if(:1.name != null  && :1.name !='') { and name = :1.name } " +
	            "#if(:1.expatriateUnit != null  && :1.expatriateUnit !='') { and expatriate_unit = :1.expatriateUnit } " +
	            "#if(:1.position != null  && :1.position !='') { and position = :1.position } " +
	            "#if(:1.status != null ) { and status = :1.status } " +
	            " and is_del = 1 " +
	            " order by id " +
	             "#if(:1.offset != null && :1.limit != null ){ limit :1.offset , :1.limit }")
	    List<ResumeInterview> listResumeInterview(ResumeInterviewCondition condition);
	 
	    
	    @SQL("SELECT  count(1) FROM "+TABLE+" WHERE 1 = 1 " +
	    		"#if(:1.name != null  && :1.name !='') { and name = :1.name } " +
	            "#if(:1.expatriateUnit != null  && :1.expatriateUnit !='') { and expatriate_unit = :1.expatriateUnit } " +
	            "#if(:1.position != null  && :1.position !='') { and position = :1.position } " +
	            "#if(:1.status != null ) { and status = :1.status } " +
	            " and is_del=1 ")
	    Long countResumeInterview(ResumeInterviewCondition condition);
	    
	    @SQL(" UPDATE "+TABLE+
	    		" set is_del=0 ,update_time = now()" +
	            " WHERE id= :1 and is_del=1 ")
	    int deleteResumeInterview(Integer resumeInterviewId);
}
