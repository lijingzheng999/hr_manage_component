package hr.manage.component.recruit.dao;

import hr.manage.component.recruit.model.RecruitInfo;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.plugin.sql.GenericDAO;

@DAO(catalog="hr_manage")
public interface RecruitInfoDAO  extends GenericDAO<RecruitInfo,Integer>{
	
	public static final String TABLE = " recruit_info ";
	
	public static final String COLUMNS = " id,expatriate_unit,center,city,work_place,pepole_need,position,status,post_duty,create_user ,is_del ,update_time ,create_time  ";

	    
	    @SQL("SELECT  " + COLUMNS + " FROM "+TABLE+" WHERE 1 = 1 " +
	    		" and is_del=1 " +
	            " order by id desc")
	    List<RecruitInfo> listRecruitInfo(RecruitInfo recruitInfo);
	   
	    @SQL(" UPDATE "+TABLE+
	    		" set is_del=0 ,update_time = now() " +
	            " WHERE id= :1 and is_del=1 ")
	    int deleteRecruitInfo(Integer recruitInfoId);
	    
	    @SQL(" UPDATE "+TABLE+
	    		" set status=0 ,update_time = now()  " +
	            " WHERE id= :1 and is_del=1 ")
	    int updateStatusComplete(Integer recruitInfoId);
}
