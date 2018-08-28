package hr.manage.component.checkwork.dao;

import hr.manage.component.checkwork.model.CheckWorkCurrentLog;
import hr.manage.component.checkwork.model.CheckWorkDetail;
import hr.manage.component.checkwork.model.CheckWorkDetailCondition;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.plugin.sql.GenericDAO;

@DAO(catalog="hr_manage")
public interface CheckWorkCurrentLogDAO  extends GenericDAO<CheckWorkCurrentLog,Integer>{
	
	public static final String TABLE = " check_work_current_log ";
	
	public static final String COLUMNS = " id,term,personal_info_id,name,surplus_overtime_hours,surplus_annual_leave,is_del,update_time,create_time ";

	    
	    @SQL("SELECT  " + COLUMNS + " FROM "+TABLE+" WHERE 1 = 1 " +
	            "#if(:1.term != null  && :1.term !='') { and term = :1.term } " +
	            "#if(:1.name != null  && :1.name !='') { and name = :1.name } " +
	            " and is_del=1 " +
	            " order by id " +
	             "#if(:1.offset != null && :1.limit != null ){ limit :1.offset , :1.limit }")
	    List<CheckWorkCurrentLog> listCheckWorkCurrentLog(CheckWorkDetailCondition condition);

	    
	    @SQL("SELECT  count(1) FROM "+TABLE+" WHERE 1 = 1 " +
	    		"#if(:1.term != null  && :1.term !='') { and term = :1.term } " +
	            "#if(:1.name != null  && :1.name !='') { and name = :1.name } " +
		         " and is_del=1 ")
	    Long countCheckWorkCurrentLog(CheckWorkDetailCondition condition);
	    
	
	    
	    @SQL("SELECT  " + COLUMNS + " FROM "+TABLE+" WHERE 1 = 1 " +
	    		"#if(:1.term != null  && :1.term !='') { and term = :1.term } " +
	            "#if(:1.name != null  && :1.name !='') { and name = :1.name } " +
	            " and is_del=1 " )
	    CheckWorkCurrentLog getCheckWorkCurrentByName(CheckWorkDetailCondition condition);
	    
	    @SQL(" UPDATE " + TABLE +
	    		 " SET id_del=0,update_time = now() " +
				 " WHERE name= :1.name and term = :1.term and is_del=1 " )
	    int deleteCheckWorkCurrentLogByName(CheckWorkDetailCondition condition);
}
