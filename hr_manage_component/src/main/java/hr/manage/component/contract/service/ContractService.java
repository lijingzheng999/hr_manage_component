package hr.manage.component.contract.service;


import hr.manage.component.contract.model.ContractCondition;
import hr.manage.component.contract.model.ContractInfo;
import hr.manage.component.personal.model.PersonalAll;

import java.util.List;



public interface ContractService {
	
	
	/**
     * 
    * @Title: getContractInfoById
    * @Description: 查询合同信息
    * @Param Integer contractInfoId
    * @return ContractInfo    
    * @throws
     */
	public ContractInfo getContractInfoById(Integer contractInfoId);
	
	/**
     * 
    * @Title: deleteContractInfoById
    * @Description: 删除员工基本信息
    * @Param Integer contractInfoId
    * @param @return    
    * @return int    
    * @throws
     */
	public int deleteContractInfoById(Integer contractInfoId);
	
	/**
     * 
    * @Title: listContractInfo
    * @Description: 条件查询员工合同信息列表
    * @param  ContractCondition
    * @return List<ContractInfo>    
    * @throws
     */
	public List<ContractInfo> listContractInfo(ContractCondition condition);
	
	
	/**
     * 
    * @Title: countContractInfo
    * @Description: 条件查询合同信息列表个数
    * @param  ContractCondition
    * @return Long
    * @throws
     */
	public Long countContractInfo(ContractCondition condition);
}
