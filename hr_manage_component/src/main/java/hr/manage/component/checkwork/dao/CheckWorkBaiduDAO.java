package hr.manage.component.checkwork.dao;

import hr.manage.component.checkwork.model.CheckWorkBaidu;
import hr.manage.component.checkwork.model.CheckWorkCurrent;
import hr.manage.component.checkwork.model.CheckWorkDetailCondition;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.plugin.sql.GenericDAO;

@DAO(catalog="hr_manage")
public interface CheckWorkBaiduDAO  extends GenericDAO<CheckWorkBaidu,Integer>{
	
	public static final String TABLE = " check_work_baidu ";
	
	public static final String COLUMNS = " id,term,start_date,end_date,name,attendance_hours,check_work_hours,overstep_hours,overstep_days,overtime_hours,one_hours,one_point_five_hours,two_hours,three_hours,overtime_sum_hours,overtime_settle_days,settlement_days,is_del,update_time,create_time ";

	    
	@SQL("SELECT  " + COLUMNS + " FROM "+TABLE+" WHERE 1 = 1 " +
            "#if(:1.name != null  && :1.name !='') { and name = :1.name } " +
	        "#if(:1.term != null  && :1.term !='') { and term = :1.term } " +
            " and is_del=1 " +
            " order by id " +
             "#if(:1.offset != null && :1.limit != null ){ limit :1.offset , :1.limit }")
    List<CheckWorkBaidu> listCheckWorkBaidu(CheckWorkDetailCondition condition);

    
    @SQL("SELECT  count(1) FROM "+TABLE+" WHERE 1 = 1 " +
    		 "#if(:1.name != null  && :1.name !='') { and name = :1.name } " +
	         "#if(:1.term != null  && :1.term !='') { and term = :1.term } " +
	         " and is_del=1 ")
    Long countCheckWorkBaidu(CheckWorkDetailCondition condition);
    

    
    @SQL("SELECT  " + COLUMNS + " FROM "+TABLE+" WHERE 1 = 1 " +
            "#if(:1 != null  && :1 !='') { and name = :1 } " +
            "#if(:2 != null  && :2 !='') { and term = :2 } " +
            " and is_del=1 " )
    CheckWorkBaidu getCheckWorkBaiduByNameTerm(String name,String term);
    
    @SQL(" UPDATE " + TABLE +
    		 " SET is_del=0,update_time = now() " +
			 " WHERE name= :1.name  and is_del=1 " )
    int deleteCheckWorkBaiduByName(CheckWorkDetailCondition condition);
    
    @SQL(" UPDATE " + TABLE +
   		 " SET is_del=0,update_time = now() " +
			 " WHERE id= :1  and is_del=1 " )
   int deleteCheckWorkBaiduById(Integer currentId);
}
