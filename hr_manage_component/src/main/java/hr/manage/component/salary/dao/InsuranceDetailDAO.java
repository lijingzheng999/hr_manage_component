package hr.manage.component.salary.dao;


import hr.manage.component.checkwork.model.CheckWorkDetail;
import hr.manage.component.salary.model.InsuranceDetail;
import hr.manage.component.salary.model.InsuranceDetailCondition;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.plugin.sql.GenericDAO;

@DAO(catalog="hr_manage")
public interface InsuranceDetailDAO  extends GenericDAO<InsuranceDetail,Integer>{
	
	public static final String TABLE = " insurance_detail ";
	
	public static final String COLUMNS = "  id,personal_info_id,term,start_date,end_date,name,insurance_begin_date,insurance_real_date,expatriate_unit,insurance_place,agency_company,agency_pay,endowment_base,endowment_rate,endowment_rate_personal,unemployment_base,unemployment_rate,unemployment_rate_personal,work_injury_base,work_injury_rate,medical_base,medical_rate,medical_rate_personal,birth_base,birth_rate,sick_base,sick_rate,sick_rate_personal,housing_base,housing_rate,housing_rate_personal,endowment_pay,endowment_pay_personal,unemployment_pay,unemployment_pay_personal,work_injury_pay,medical_pay,medical_pay_personal,birth_pay,sick_pay,sick_pay_personal,social_security,social_security_personal,social_security_total,housing_pay,housing_pay_personal,housing_pay_total,insurance_pay,insurance_pay_personal,insurance_pay_total,is_del,update_time,create_time  ";

	    
	    @SQL("SELECT  " + COLUMNS + " FROM "+TABLE+" WHERE 1 = 1 " +
	    		"#if(:1.name != null  && :1.name !='') { and name = :1.name } " +
	            "#if(:1.term != null  && :1.term !='') { and term = :1.term } " +
	            "#if(:1.expatriateUnit != null  && :1.expatriateUnit !='') { and expatriate_unit = :1.expatriateUnit } " +
	            " and is_del=1 " +
	            " order by id " +
	             "#if(:1.offset != null && :1.limit != null ){ limit :1.offset , :1.limit }")
	    List<InsuranceDetail> listInsuranceDetail(InsuranceDetailCondition condition);

	    
	    @SQL("SELECT  count(1) FROM "+TABLE+" WHERE 1 = 1 " +
	    		"#if(:1.name != null  && :1.name !='') { and name = :1.name } " +
	            "#if(:1.term != null  && :1.term !='') { and term = :1.term } " +
	            "#if(:1.expatriateUnit != null  && :1.expatriateUnit !='') { and expatriate_unit = :1.expatriateUnit } " +
	            " and is_del=1 ")
	    Long countInsuranceDetail(InsuranceDetailCondition condition);
	    
	
	    
	    
	    @SQL(" select count(1) from "+ TABLE
				+ " WHERE term= :1 and is_del=1 ")
	    int countInsuranceDetailByTerm(String term);
	    
	    @SQL("SELECT  " + COLUMNS + " FROM "+TABLE+" WHERE 1 = 1 " +
	            "#if(:1 != null  && :1 !='') { and name = :1 } " +
	            "#if(:2 != null  && :2 !='') { and term = :2 } " +
	            " and is_del=1 " )
	    InsuranceDetail getInsuranceDetailByName(String name,String term);
	    
	    @SQL(" UPDATE " + TABLE +
	    		 " SET id_del=0,update_time = now() " +
				 " WHERE name= :1 and term = :2 and is_del=1 " )
	    int deleteInsuranceDetailByName(String name,String term);
}
