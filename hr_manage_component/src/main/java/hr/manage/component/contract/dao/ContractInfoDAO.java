package hr.manage.component.contract.dao;

import hr.manage.component.contract.model.ContractInfo;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.plugin.sql.GenericDAO;

@DAO(catalog="hr_manage")
public interface ContractInfoDAO  extends GenericDAO<ContractInfo,Integer>{
	
	public static final String TABLE = " contract_info ";
	
	public static final String COLUMNS = " id,employee_number,name,contract_number,position,start_date,end_date ,contract_count ,memo ,is_del ,update_time ,create_time  ";

	    
	    @SQL("SELECT  " + COLUMNS + " FROM "+TABLE+" WHERE 1 = 1 " +
	            "#if(:1.employee_number != null) { and employee_number = :1.employeeNumber } " +
	            " order by id ")
	    List<ContractInfo> listTradeInfo(ContractInfo contractInfo);

}
