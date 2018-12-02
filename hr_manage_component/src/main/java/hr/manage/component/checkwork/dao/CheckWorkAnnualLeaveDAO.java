package hr.manage.component.checkwork.dao;

import hr.manage.component.checkwork.model.CheckWorkAnnualLeave;
import hr.manage.component.checkwork.model.CheckWorkDetail;
import hr.manage.component.checkwork.model.CheckWorkDetailCondition;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.plugin.sql.GenericDAO;

@DAO(catalog="hr_manage")
public interface CheckWorkAnnualLeaveDAO  extends GenericDAO<CheckWorkAnnualLeave,Integer>{
	
	public static final String TABLE = " check_work_annual_leave ";
	
	public static final String COLUMNS = " id,name,entry_time,start_date,end_date,annual_leave_days,annual_leave_jan,annual_leave_feb,annual_leave_mar,annual_leave_apr,annual_leave_may,annual_leave_jun,annual_leave_jul,annual_leave_aug,annual_leave_sept,annual_leave_oct,annual_leave_nov,annual_leave_dec,surplus_annual_leave,overtime_jan,overtime_feb,overtime_mar,overtime_apr,overtime_may,overtime_jun,overtime_jul,overtime_aug,overtime_sept,overtime_oct,overtime_nov,overtime_dec,overtime_collect,off_duty_shift_jan,off_duty_shift_feb,off_duty_shift_mar,off_duty_shift_apr,off_duty_shift_may,off_duty_shift_jun,off_duty_shift_jul,off_duty_shift_aug,off_duty_shift_sept,off_duty_shift_oct,off_duty_shift_nov,off_duty_shift_dec,off_duty_shift_collect,surplus_overtime_hours,leave_jan,leave_feb,leave_mar,leave_apr,leave_may,leave_jun,leave_jul,leave_aug,leave_sept,leave_oct,leave_nov,leave_dec,surplus_leave,is_del,update_time,create_time ";

	    
	    @SQL("SELECT  " + COLUMNS + " FROM "+TABLE+" WHERE 1 = 1 " +
	            "#if(:1.name != null  && :1.name !='') { and name = :1.name } " +
	            " and is_del=1 " +
	            " order by id " +
	             "#if(:1.offset != null && :1.limit != null ){ limit :1.offset , :1.limit }")
	    List<CheckWorkAnnualLeave> listCheckWorkAnnualLeave(CheckWorkDetailCondition condition);

	    
	    @SQL("SELECT  count(1) FROM "+TABLE+" WHERE 1 = 1 " +
	    		 "#if(:1.name != null  && :1.name !='') { and name = :1.name } " +
		         " and is_del=1 ")
	    Long countCheckWorkAnnualLeave(CheckWorkDetailCondition condition);
	    
	
	    
	    @SQL("SELECT  " + COLUMNS + " FROM "+TABLE+" WHERE 1 = 1 " +
	            "#if(:1 != null  && :1 !='') { and name = :1 } " +
	            " and is_del=1 " )
	    CheckWorkAnnualLeave getCheckWorkAnnualLeaveByName(String name);
	    
	    @SQL(" UPDATE " + TABLE +
	    		 " SET is_del=0,update_time = now() " +
				 " WHERE name= :1 and is_del=1 " )
	    int deleteCheckWorkAnnualLeaveByName(String name);
}
