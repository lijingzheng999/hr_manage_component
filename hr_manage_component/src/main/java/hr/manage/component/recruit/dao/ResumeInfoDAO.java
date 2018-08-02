package hr.manage.component.recruit.dao;

import hr.manage.component.recruit.model.ResumeInfo;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.plugin.sql.GenericDAO;

@DAO(catalog="hr_manage")
public interface ResumeInfoDAO  extends GenericDAO<ResumeInfo,Integer>{
	
	public static final String TABLE = " resume_info ";
	
	public static final String COLUMNS = " id,name,phone,sex,type,position,experience ,email ,school ,major ,education ,birthday,invite_time,interview_time,is_pass,is_entry,is_del,update_time,create_time ";

	    
	    @SQL("SELECT  " + COLUMNS + " FROM "+TABLE+" WHERE 1 = 1 " +
	            " order by id ")
	    List<ResumeInfo> listResumeInfo(ResumeInfo resumeInfo);

}
