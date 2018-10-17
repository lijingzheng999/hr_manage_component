package hr.manage.component.checkwork.dao;

import hr.manage.component.checkwork.model.CheckWorkBaiduDetail;
import hr.manage.component.checkwork.model.CheckWorkDetailCondition;

import java.math.BigDecimal;
import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.plugin.sql.GenericDAO;

@DAO(catalog="hr_manage")
public interface CheckWorkBaiduDetailDAO  extends GenericDAO<CheckWorkBaiduDetail,Integer>{
	
	public static final String TABLE = " check_work_baidu_detail ";
	
	public static final String COLUMNS = " id,check_work_id,type,work_type,current_day,work_hours,is_del,update_time,create_time ";

	    
	@SQL("SELECT  " + COLUMNS + " FROM "+TABLE+" WHERE 1 = 1 " +
            "#if(:1.name != null  && :1.name !='') { and name = :1.name } " +
	        "#if(:1.term != null  && :1.term !='') { and term = :1.term } " +
            " and is_del=1 " +
            " order by id " +
             "#if(:1.offset != null && :1.limit != null ){ limit :1.offset , :1.limit }")
    List<CheckWorkBaiduDetail> listCheckWorkBaidu(CheckWorkDetailCondition condition);

    
    @SQL("SELECT  count(1) FROM "+TABLE+" WHERE 1 = 1 " +
    		 "#if(:1.name != null  && :1.name !='') { and name = :1.name } " +
	         "#if(:1.term != null  && :1.term !='') { and term = :1.term } " +
	         " and is_del=1 ")
    Long countCheckWorkBaiduDetail(CheckWorkDetailCondition condition);
    

    @SQL("SELECT  " + COLUMNS + " FROM "+TABLE+" WHERE 1 = 1 " +
            "#if(:1 != null  && :1 >0) { and check_work_id = :1 } " +
            " and is_del=1 " )
    List<CheckWorkBaiduDetail> getCheckWorkBaiduDetailByBaiduId(Integer baiduId);
   
    
    @SQL("SELECT  " + COLUMNS + " FROM "+TABLE+" WHERE 1 = 1 " +
            "#if(:1 != null  && :1 !='') { and name = :1 } " +
            "#if(:2 != null  && :2 !='') { and term = :2 } " +
            " and is_del=1 " )
    List<CheckWorkBaiduDetail> getCheckWorkBaiduDetailByNameTerm(String name,String term);
    
    @SQL("SELECT  SUM(work_hours) FROM "+TABLE+" WHERE 1 = 1 " +
            "#if(:1 != null  && :1 >0) { and check_work_id = :1 } " +
            "#if(:2 != null  && :2 >0) { and current_day < :2 } " +
            " work_type in(0,4,5) and is_del=1 " )
    BigDecimal getSumHoursByDay(Integer baiduId,Integer workDay);
    
    
    @SQL(" UPDATE " + TABLE +
    		 " SET is_del=0,update_time = now() " +
			 " WHERE name= :1.name  and is_del=1 " )
    int deleteCheckWorkBaiduDetailByName(CheckWorkDetailCondition condition);
    
    @SQL(" UPDATE " + TABLE +
   		 " SET is_del=0,update_time = now() " +
			 " WHERE check_work_id= :1  and is_del=1 " )
   int deleteCheckWorkBaiduDetailById(Integer currentId);
}
