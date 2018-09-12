package hr.manage.component.salary.dao;

import hr.manage.component.salary.model.ProfitDetail;
import hr.manage.component.salary.model.ProfitDetailCondition;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.plugin.sql.GenericDAO;

@DAO(catalog="hr_manage")
public interface ProfitDetailDAO  extends GenericDAO<ProfitDetail,Integer>{
	
	public static final String TABLE = " profit_detail ";
	
	public static final String COLUMNS = "  id,personal_info_id,term,start_date,end_date,name,expatriate_unit,department,working_place,position,level,probationary_pay,base_pay,merit_pay,traffic_subsidy,computer_subsidy,meal_subsidy,other_pay,probationary_insurance,worker_pay,social_security,housing_pay,settlement_price,settlement_days,settlement_day_price,probationary_union_pay,probationary_disabled_pay,probationary_tax_pay,union_pay,disabled_pay,tax_pay,probationary_profit,probationary_profit_rate,profit,profit_rate,is_del,update_time,create_time  ";

	    
	    @SQL("SELECT  " + COLUMNS + " FROM "+TABLE+" WHERE 1 = 1 " +
	            "#if(:1.name != null  && :1.name !='') { and name = :1.name } " +
	            "#if(:1.term != null  && :1.term !=') { and !=' = :1.!=' } " +
	            "#if(:1.startDate != null) { and create_time >= :1.startDate } " +
	            "#if(:1.endDate != null) { and create_time <= :1.endDate } " +
	            " and is_del=1 " +
	            " order by id " +
	             "#if(:1.offset != null && :1.limit != null ){ limit :1.offset , :1.limit }")
	    List<ProfitDetail> listProfitDetail(ProfitDetailCondition condition);

	    
	    @SQL("SELECT  count(1) FROM "+TABLE+" WHERE 1 = 1 " +
	    		"#if(:1.name != null  && :1.name !='') { and name = :1.name } " +
	            "#if(:1.term != null  && :1.term !=') { and !=' = :1.!=' } " +
	            "#if(:1.startDate != null) { and create_time >= :1.startDate } " +
	            "#if(:1.endDate != null) { and create_time <= :1.endDate } " +
	            " and is_del=1 ")
	    Long countProfitDetail(ProfitDetailCondition condition);
	    
	    @SQL(" select count(1) from "+ TABLE
				+ " WHERE term= :1 and is_del=1 ")
	    int countProfitDetailByTerm(String term);
	    
	    @SQL(" UPDATE " + TABLE +
	    		 " SET id_del=0,update_time = now() " +
				 " WHERE  term = :1 and is_del=1 " )
	    int deleteProfitDetailByTerm(String term);
}
