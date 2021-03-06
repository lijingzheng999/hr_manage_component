package hr.manage.component.contract.dao;

import hr.manage.component.checkwork.model.CheckWorkDetail;
import hr.manage.component.contract.model.ContractCondition;
import hr.manage.component.contract.model.ContractInfo;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.plugin.sql.GenericDAO;

@DAO(catalog="hr_manage")
public interface ContractInfoDAO  extends GenericDAO<ContractInfo,Integer>{
	
	public static final String TABLE = " contract_info ";
	
	public static final String COLUMNS = " id,personal_info_id,employee_number,name,contract_number,position,start_date,end_date ,contract_count ,memo ,status,is_del ,update_time ,create_time  ";

	    
	    @SQL("SELECT  " + COLUMNS + " FROM "+TABLE+" WHERE 1 = 1 " +
	            "#if(:1.name != null  && :1.name !='') { and name = :1.name } " +
	            "#if(:1.employeeNumber != null  && :1.employeeNumber !='') { and employee_number = :1.employeeNumber } " +
	            "#if(:1.startDate != null  && :1.startDate !='') { and end_date >= :1.startDate } " +
	            "#if(:1.endDate != null && :1.endDate !='') { and end_date <= :1.endDate } " +
	            "#if(:1.status != null ) { and status = :1.status } " +
	            " and is_del=1 " +
	            " order by id " +
	             "#if(:1.offset != null && :1.limit != null ){ limit :1.offset , :1.limit }")
	    List<ContractInfo> listContractInfo(ContractCondition contractInfo);

	    
	    @SQL("SELECT  count(1) FROM "+TABLE+" WHERE 1 = 1 " +
	            "#if(:1.name != null  && :1.name !='') { and name = :1.name } " +
	            "#if(:1.employeeNumber != null  && :1.employeeNumber !='') { and employee_number = :1.employeeNumber } " +
	            "#if(:1.startDate != null  && :1.startDate !='') { and end_date >= :1.startDate } " +
	            "#if(:1.endDate != null && :1.endDate !='') { and end_date <= :1.endDate } " +
	            "#if(:1.status != null ) { and status = :1.status } " +
	            " and is_del=1 ")
	    Long countContractInfo(ContractCondition contractInfo);
	    
	    @SQL("SELECT  " + COLUMNS + " FROM "+TABLE+" WHERE 1 = 1 " +
	            "#if(:1 != null  && :1 >0) { and personal_info_id = :1 } " +
	            " and status=1 and is_del=1 " )
	    ContractInfo getCurContractInfoByPersonId(Integer personalId);
	    
	    @SQL(" update  "+ TABLE
				+ " set is_del=0,update_time = now() "
				+ " WHERE id= :1 and is_del=1 ")
		int deleteContractInfoById(Integer contractInfoId);
	    
	    @SQL(" update  "+ TABLE
				+ " set is_del=0,update_time = now() "
				+ " WHERE personal_info_id= :1 and is_del=1 ")
	    int deleteContractInfoByPersonId(Integer personInfoId);
	    
	    @SQL(" select max(id) from "+ TABLE
				+ " WHERE personal_info_id= :1 and is_del=1 ")
	    int getMaxContractCountById(Integer personalInfoId);
	    
	    @SQL(" select count(1) from "+ TABLE
				+ " WHERE personal_info_id= :1 and is_del=1 ")
	    int countContractInfoByPersonalId(Integer personalInfoId);
}
