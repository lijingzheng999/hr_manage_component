package hr.manage.component.salary.service;

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
	
	
	/**
     * 
    * @Title: countSalaryDetailByTerm
    * @Description: 通过账期查询工资表记录数
    * @param  countSalaryDetailByTerm
    * @return int  
    * @throws
     */
	public int countSalaryDetailByTerm(String term);

	/**
     * 
    * @Title: createSalaryDetailByTerm
    * @Description: 通过账期生成工资表明细
    * @param  createSalaryDetailByTerm
    * @return int  
    * @throws
     */
	public int createSalaryDetailByTerm(String term);

	/**
     * 
    * @Title: countInsuranceDetailByTerm
    * @Description: 通过账期查询保险记录数
    * @param  String term
    * @return int  
    * @throws
     */
	public int countInsuranceDetailByTerm(String term);

}
