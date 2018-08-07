package hr.manage.component.contract.dao;

import hr.manage.component.contract.model.ContractCondition;
import hr.manage.component.contract.model.ContractInfo;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.plugin.sql.GenericDAO;

@DAO(catalog="hr_manage")
public interface ContractInfoDAO  extends GenericDAO<ContractInfo,Integer>{
	
	public static final String TABLE = " contract_info ";
	
	public static final String COLUMNS = " id,personal_info_id,employee_number,name,contract_number,position,start_date,end_date ,contract_count ,memo ,is_del ,update_time ,create_time  ";

	    
	    @SQL("SELECT  " + COLUMNS + " FROM "+TABLE+" WHERE 1 = 1 " +
	            "#if(:1.name != null  && :1.name !='') { and name = :1.name } " +
	            "#if(:1.employeeNumber != null  && :1.employeeNumber !='') { and employee_number = :1.employeeNumber } " +
	            "#if(:1.startDate != null) { and start_date = :1.startDate } " +
	            "#if(:1.endDate != null) { and end_date = :1.endDate } " +
	            " and is_del=1 " +
	            " order by id ")
	    List<ContractInfo> listContractInfo(ContractCondition contractInfo);

	    
	    @SQL("SELECT  count(1) FROM "+TABLE+" WHERE 1 = 1 " +
	            "#if(:1.name != null  && :1.name !='') { and name = :1.name } " +
	            "#if(:1.employeeNumber != null  && :1.employeeNumber !='') { and employee_number = :1.employeeNumber } " +
	            "#if(:1.startDate != null) { and start_date = :1.startDate } " +
	            "#if(:1.endDate != null) { and end_date = :1.endDate } " +
	            " and is_del=1 ")
	    Long countContractInfo(ContractCondition contractInfo);
	    
	    @SQL(" update  "+ TABLE
				+ " set is_del=0 "
				+ " WHERE id= :1 and is_del=1 ")
		int deleteContractInfoById(Integer contractInfoId);
	    
	    @SQL(" update  "+ TABLE
				+ " set is_del=0 "
				+ " WHERE personal_info_id= :1 and is_del=1 ")
	    int deleteContractInfoByPersonId(Integer personInfoId);
	    
	    @SQL(" select max(id) from "+ TABLE
				+ " WHERE personal_info_id= :1 and is_del=1 ")
	    int getMaxContractCountById(Integer personalInfoId);
	    
	    @SQL(" select " + COLUMNS + " from "+ TABLE
				+ " WHERE personal_info_id= :1 and is_del=1 ")
	    int countContractInfoByPersonalId(Integer personalInfoId);
}
