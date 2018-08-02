package hr.manage.component.common.dao;

import hr.manage.component.common.model.CommonType;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.plugin.sql.GenericDAO;

@DAO(catalog="hr_manage")
public interface CommonTypeDAO  extends GenericDAO<CommonType,Integer>{
	
	public static final String TABLE = " common_type ";
	
	public static final String COLUMNS = " id,type,content,memo,is_del,create_time ";

	    
	    @SQL("SELECT  " + COLUMNS + " FROM "+TABLE+" WHERE 1 = 1 " +
	            "#if(:1.type != null) { and type = :1.type } " +
	            " order by id ")
	    List<CommonType> listTradeInfo(CommonType commonType);

}
