package hr.manage.component.checkwork.dao;

import hr.manage.component.checkwork.model.CheckWorkDetail;
import hr.manage.component.checkwork.model.CheckWorkDetailCondition;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.plugin.sql.GenericDAO;

@DAO(catalog="hr_manage")
public interface CheckWorkDetailDAO  extends GenericDAO<CheckWorkDetail,Integer>{
	
	public static final String TABLE = " check_work_detail ";
	
	public static final String COLUMNS = " id,term,start_date,end_date,manufacturer,name,expatriate_unit,entry_time,attendance_days,check_work_days,overtime_days,leave_days,manager,memo,surplus_overtime_hours,annual_leave_days,surplus_annual_leave,sick_leave_days,compassionate_leave_days,settlement_days,is_del,update_time,create_time  ";

	    
	    @SQL("SELECT  " + COLUMNS + " FROM "+TABLE+" WHERE 1 = 1 " +
	            "#if(:1.name != null  && :1.name !='') { and name = :1.name } " +
	            "#if(:1.term != null  && :1.term !='') { and term = :1.term } " +
	            "#if(:1.expatriateUnit != null  && :1.expatriateUnit !='') { and expatriateUnit = :1.expatriateUnit } " +
	            " and is_del=1 " +
	            " order by id " +
	             "#if(:1.offset != null && :1.limit != null ){ limit :1.offset , :1.limit }")
	    List<CheckWorkDetail> listCheckWorkDetail(CheckWorkDetailCondition condition);

	    
	    @SQL("SELECT  count(1) FROM "+TABLE+" WHERE 1 = 1 " +
	    		 "#if(:1.name != null  && :1.name !='') { and name = :1.name } " +
		         "#if(:1.term != null  && :1.term !='') { and term = :1.term } " +
		         "#if(:1.expatriateUnit != null  && :1.expatriateUnit !='') { and expatriateUnit = :1.expatriateUnit } " +
		         " and is_del=1 ")
	    Long countCheckWorkDetail(CheckWorkDetailCondition condition);
	    
	
	    
	    @SQL("SELECT  " + COLUMNS + " FROM "+TABLE+" WHERE 1 = 1 " +
	            "#if(:1 != null  && :1 !='') { and name = :1 } " +
	            "#if(:2 != null  && :2 !='') { and term = :2 } " +
	            " and is_del=1 " )
	    CheckWorkDetail getCheckWorkDetailByName(String name,String term);
	    
	    @SQL(" UPDATE " + TABLE +
	    		 " SET is_del=0,update_time = now() " +
				 " WHERE name= :1 and term = :2 and is_del=1 " )
	    int deleteCheckWorkDetailByName(String name,String term);
}
