package hr.manage.component.common.dao;

import hr.manage.component.common.model.CommonType;
import hr.manage.component.common.model.SettingHoliday;

import java.util.Date;
import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.plugin.sql.GenericDAO;

@DAO(catalog="hr_manage")
public interface SettingHolidayDAO  extends GenericDAO<SettingHoliday,Integer>{
	
	public static final String TABLE = " setting_holiday ";
	
	public static final String COLUMNS = " id,type,cur_date,is_del,update_time,create_time ";

	    
	    @SQL("SELECT  " + COLUMNS + " FROM "+TABLE+" WHERE 1 = 1 " +
	            "#if(:1 != null) { and type = :1 } " +
	            "#if(:2 != null) { and cur_date >= :2 } " +
	            "#if(:3 != null) { and cur_date <= :3 } " +
	            " and is_del=1 " +
	            " order by id ")
	    List<SettingHoliday> listSettingHoliday(Integer type,Date startDate, Date endDate);

	    @SQL(" UPDATE "+TABLE +
		           " set is_del=0,update_time = now() " +
		    		" where id = :1 ")
		    int deleteSettingHoliday(Integer fileId);
}
