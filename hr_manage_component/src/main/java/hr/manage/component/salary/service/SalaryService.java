package hr.manage.component.salary.service;


import hr.manage.component.contract.model.ContractCondition;
import hr.manage.component.contract.model.ContractInfo;
import hr.manage.component.personal.model.PersonalAll;
import hr.manage.component.salary.model.SalaryChange;
import hr.manage.component.salary.model.SalaryChangeCondition;

import java.util.List;



public interface SalaryService {
	

	/**
     * 
    * @Title: listSalaryChange
    * @Description: 条件查询调薪列表
    * @param  SalaryChangeCondition
    * @return List<SalaryChange>    
    * @throws
     */
	public List<SalaryChange> listSalaryChange(SalaryChangeCondition condition);
	
	

	/**
     * 
    * @Title: countSalaryChange
    * @Description: 条件查询调薪列表个数
    * @param  countSalaryChange
    * @return Long
    * @throws
     */
	public Long countSalaryChange(SalaryChangeCondition condition);
	
	/**
     * 
    * @Title: addSalaryChange
    * @Description: 新增工资调整
    * @Param SalaryChange salaryChange
    * @return int    
    * @throws
     */
	public int addSalaryChange(SalaryChange salaryChange);
	

}
