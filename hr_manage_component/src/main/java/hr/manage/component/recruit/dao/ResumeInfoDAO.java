package hr.manage.component.recruit.dao;

import hr.manage.component.recruit.model.ResumeCondition;
import hr.manage.component.recruit.model.ResumeInfo;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.plugin.sql.GenericDAO;

@DAO(catalog="hr_manage")
public interface ResumeInfoDAO  extends GenericDAO<ResumeInfo,Integer>{
	
	public static final String TABLE = " resume_info ";
	
	public static final String COLUMNS = " id,name,phone,age,sex,type,position,experience ,email ,school ,major ,education ,birthday,invite_time,interview_time,is_pass,is_entry,is_del,update_time,create_time ";

	    
	    @SQL("SELECT  " + COLUMNS + " FROM "+TABLE+" WHERE 1 = 1 " +
	            "#if(:1.position != null  && :1.position !='') { and position = :1.position } " +
	            "#if(:1.age != null  && :1.age >0) { and age <= :1.age } " +
	            "#if(:1.experience != null  && :1.experience >0) { and experience >= :1.experience } " +
	    		" and is_del = 1 " +
	            " order by id ")
	    List<ResumeInfo> listResumeInfo(ResumeCondition condition);
	 
	    
	    @SQL("SELECT  count(1) FROM "+TABLE+" WHERE 1 = 1 " +
	    		"#if(:1.position != null  && :1.position !='') { and position = :1.position } " +
	            "#if(:1.age != null  && :1.age >0) { and age <= :1.age } " +
	            "#if(:1.experience != null  && :1.experience >0) { and experience >= :1.experience } " +
	    		" and is_del=1 ")
	    Long countResumeInfo(ResumeCondition condition);
	    
	    @SQL(" UPDATE "+TABLE+
	    		" set is_del=0 " +
	            " WHERE id= :1 and is_del=1 ")
	    int deleteResumeInfo(Integer resumeInfoId);
}
