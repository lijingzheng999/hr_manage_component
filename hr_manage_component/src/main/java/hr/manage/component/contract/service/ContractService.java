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
    * @Title: getCurContractInfoByPersonId
    * @Description: 查询合同信息
    * @Param Integer personalId
    * @return ContractInfo    
    * @throws
     */
	public ContractInfo getCurContractInfoByPersonId(Integer personalId);
	
	/**
     * 
    * @Title: deleteContractInfoById
    * @Description: 删除合同信息
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
	

	/**
     * 
    * @Title: addContractInfo
    * @Description: 新增合同信息
    * @Param ContractInfo contractInfo
    * @return int    
    * @throws
     */
	public int addContractInfo(ContractInfo contractInfo);
	

	/**
     * 
    * @Title: updateContractInfo
    * @Description: 修改合同信息
    * @Param ContractInfo contractInfo
    * @return int    
    * @throws
     */
	public int updateContractInfo(ContractInfo contractInfo);
	

	/**
     * 
    * @Title: getMaxContractCountById
    * @Description: 根据员工ID查找最新一条的合同；
    * @Param Integer personalInfoId
    * @return int    
    * @throws
     */
	public int getMaxContractCountById(Integer personalInfoId);
	/**
     * 
    * @Title: countContractInfoByPersonalId
    * @Description: 根据员工ID查找有多少条合同；
    * @Param Integer personalInfoId
    * @return int    
    * @throws
     */
	public int countContractInfoByPersonalId(Integer personalInfoId);
}
