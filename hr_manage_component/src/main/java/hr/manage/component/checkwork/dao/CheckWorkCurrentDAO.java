package hr.manage.component.checkwork.dao;

import hr.manage.component.checkwork.model.CheckWorkCurrent;
import hr.manage.component.checkwork.model.CheckWorkDetailCondition;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.plugin.sql.GenericDAO;

@DAO(catalog="hr_manage")
public interface CheckWorkCurrentDAO  extends GenericDAO<CheckWorkCurrent,Integer>{
	
	public static final String TABLE = " check_work_current ";
	
	public static final String COLUMNS = " id,term,personal_info_id,name,surplus_overtime_hours,annual_leave_days,surplus_annual_leave,sick_leave_days,compassionate_leave_days,is_del,update_time,create_time ";

	    
	@SQL("SELECT  " + COLUMNS + " FROM "+TABLE+" WHERE 1 = 1 " +
            "#if(:1.name != null  && :1.name !='') { and name = :1.name } " +
            " and is_del=1 " +
            " order by id " +
             "#if(:1.offset != null && :1.limit != null ){ limit :1.offset , :1.limit }")
    List<CheckWorkCurrent> listCheckWorkCurrent(CheckWorkDetailCondition condition);

    
    @SQL("SELECT  count(1) FROM "+TABLE+" WHERE 1 = 1 " +
    		 "#if(:1.name != null  && :1.name !='') { and name = :1.name } " +
	         " and is_del=1 ")
    Long countCheckWorkCurrent(CheckWorkDetailCondition condition);
    

    
    @SQL("SELECT  " + COLUMNS + " FROM "+TABLE+" WHERE 1 = 1 " +
            "#if(:1 != null  && :1 !='') { and name = :1 } " +
            "#if(:2 != null  && :2 !='') { and term = :2 } " +
            " and is_del=1 " )
    CheckWorkCurrent getCheckWorkCurrentByNameTerm(String name,String term);
    
    @SQL(" UPDATE " + TABLE +
    		 " SET id_del=0,update_time = now() " +
			 " WHERE name= :1.name  and is_del=1 " )
    int deleteCheckWorkCurrentByName(CheckWorkDetailCondition condition);
}
