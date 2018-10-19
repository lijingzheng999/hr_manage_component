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
	
	public static final String COLUMNS = " id,name,invite_time,interview_time,position,sex,birthday,experience,phone,email ,graduation_time,school ,major ,education ,status,memo,is_del,update_time,create_time ";

	    
	    @SQL("SELECT  " + COLUMNS + " FROM "+TABLE+" WHERE 1 = 1 " +
	    		"#if(:1.status != null ) { and status = :1.status } " +
	            "#if(:1.position != null  && :1.position !='') { and position = :1.position } " +
	            "#if(:1.startExperience != null  && :1.startExperience >0) { and experience >= :1.startExperience } " +
	            "#if(:1.endExperience != null  && :1.endExperience >0) { and experience <= :1.endExperience } " +
	            "#if(:1.startAge != null ) { and birthday >= :1.startAge } " +
	            "#if(:1.endAge != null ) { and birthday <= :1.endAge } " +
	            " and is_del = 1 " +
	            " order by id " +
	             "#if(:1.offset != null && :1.limit != null ){ limit :1.offset , :1.limit }")
	    List<ResumeInfo> listResumeInfo(ResumeCondition condition);
	 
	    
	    @SQL("SELECT  count(1) FROM "+TABLE+" WHERE 1 = 1 " +
	    		"#if(:1.status != null ) { and status = :1.status } " +
	            "#if(:1.position != null  && :1.position !='') { and position = :1.position } " +
	            "#if(:1.startExperience != null  && :1.startExperience >0) { and experience >= :1.startExperience } " +
	            "#if(:1.endExperience != null  && :1.endExperience >0) { and experience <= :1.endExperience } " +
	            "#if(:1.startAge != null ) { and birthday >= :1.startAge } " +
	            "#if(:1.endAge != null ) { and birthday <= :1.endAge } " +
	            " and is_del=1 ")
	    Long countResumeInfo(ResumeCondition condition);
	    
	    @SQL(" UPDATE "+TABLE+
	    		" set is_del=0 ,update_time = now()" +
	            " WHERE id= :1 and is_del=1 ")
	    int deleteResumeInfo(Integer resumeInfoId);
}
