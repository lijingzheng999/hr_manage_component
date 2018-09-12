package hr.manage.component.salary.service;

import hr.manage.component.checkwork.model.CheckWorkDetail;
import hr.manage.component.salary.model.InsuranceDetail;
import hr.manage.component.salary.model.InsuranceDetailCondition;
import hr.manage.component.salary.model.ProfitDetail;
import hr.manage.component.salary.model.ProfitDetailCondition;
import hr.manage.component.salary.model.SalaryChange;
import hr.manage.component.salary.model.SalaryChangeCondition;
import hr.manage.component.salary.model.SalaryDetail;
import hr.manage.component.salary.model.SalaryDetailCondition;

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
    * @Title: listSalaryDetail
    * @Description: 通过条件查询工资表明细
    * @param  listSalaryDetail
    * @return List<SalaryDetail>  
    * @throws
     */
	public List<SalaryDetail> listSalaryDetail(SalaryDetailCondition condition);

	/**
     * 
    * @Title: countSalaryDetail
    * @Description: 通过条件查询工资表明细记录数
    * @param  Long
    * @return List<SalaryDetail>  
    * @throws
     */
	public Long countSalaryDetail(SalaryDetailCondition condition);
    
	/**
     * 
    * @Title: getSalaryDetailById
    * @Description: 通过ID获取工资表明细
    * @param  Long
    * @return SalaryDetail
    * @throws
     */
	public SalaryDetail getSalaryDetailById(Integer salaryDetailId);
	
	/**
     * 
    * @Title: updateSalaryDetail
    * @Description: 修改工资表明细
    * @param  SalaryDetail salaryDetail
    * @return int
    * @throws
     */
	public int updateSalaryDetail(SalaryDetail salaryDetail);
	
	/**
     * 
    * @Title: listInsuranceDetail
    * @Description: 通过条件查询社保表明细
    * @param  listInsuranceDetail
    * @return List<InsuranceDetail> 
    * @throws
     */
	public List<InsuranceDetail>  listInsuranceDetail(InsuranceDetailCondition condition);

	/**
     * 
    * @Title: countInsuranceDetail
    * @Description: 通过条件查询社保表明细记录数
    * @param  InsuranceDetailCondition condition
    * @return Long
    * @throws
     */
	public Long countInsuranceDetail(InsuranceDetailCondition condition);
 
	/**
     * 
    * @Title: getInsuranceDetailById
    * @Description: 通过ID查询社保表明细
    * @param  Integer insuranceDetailId
    * @return InsuranceDetail
    * @throws
     */
	public InsuranceDetail getInsuranceDetailById(Integer insuranceDetailId);
	
	/**
     * 
    * @Title: updateInsuranceDetail
    * @Description: 修改社保表明细
    * @param  InsuranceDetail insuranceDetail
    * @return int
    * @throws
     */
	public int  updateInsuranceDetail(InsuranceDetail insuranceDetail);
	
	/**
     * 
    * @Title: countInsuranceDetailByTerm
    * @Description: 通过账期查询保险记录数
    * @param  String term
    * @return int  
    * @throws
     */
	public int countInsuranceDetailByTerm(String term);

	/**
     * 
    * @Title: saveInsuranceDetailListRecord
    * @Description: 保存社保信息列表
    * @param  List<InsuranceDetail>
    * @return int
    * @throws
     */
	public  int saveInsuranceDetailListRecord( List<InsuranceDetail> insuranceDetailList);

	/**
     * 
    * @Title: listProfitDetail
    * @Description: 通过条件查询利润测算表明细
    * @param  ProfitDetailCondition condition
    * @return List<ProfitDetail>  
    * @throws
     */
	public List<ProfitDetail> listProfitDetail(ProfitDetailCondition condition);
	
	/**
     * 
    * @Title: countProfitDetail
    * @Description: 通过条件查询利润测算表记录数
    * @param  ProfitDetailCondition condition
    * @return Long  
    * @throws
     */
	public Long countProfitDetail(ProfitDetailCondition condition);

	
	
	/**
    * 
   * @Title: createProfitDetailByTerm
   * @Description: 通过账期生成利润表明细
   * @param  createProfitDetailByTerm
   * @return int  
   * @throws
    */
	public int createProfitDetailByTerm(String term);
}
