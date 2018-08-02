package hr.manage.component.personal.service;

import hr.manage.component.personal.model.PersonalAll;
import hr.manage.component.personal.model.PersonalAllExport;
import hr.manage.component.personal.model.PersonalCondition;

import java.util.List;



public interface PersonalService {
	
	
	/**
     * 
    * @Title: checkPersonalByNameAndCard
    * @Description: 校验员工基本信息是否存在
    * @Param String name, String identityCard
    * @param @return    
    * @return int    
    * @throws
     */
	public int checkPersonalByNameAndCard(String name, String identityCard);
	
	
	/**
     * 
    * @Title: savePersonalAllListRecord
    * @Description: 保存员工基本信息列表
    * @Param List<PersonalAll> personAllList
    * @param @return    
    * @return int    
    * @throws
     */
	public int savePersonalAllListRecord(List<PersonalAll> personAllList);
	
	/**
     * 
    * @Title: getPersonalAllInfoById
    * @Description: 查询员工基本信息
    * @Param Integer personalInfoId
    * @param @return    
    * @return PersonalAll    
    * @throws
     */
	public PersonalAll getPersonalAllInfoById(Integer personalInfoId);
	
	/**
     * 
    * @Title: deletePersonalAllInfoById
    * @Description: 删除员工基本信息
    * @Param Integer personalInfoId
    * @param @return    
    * @return int    
    * @throws
     */
	public int deletePersonalAllInfoById(Integer personalInfoId);
	
	/**
     * 
    * @Title: listPersonalAllExport
    * @Description: 条件查询员工基本信息列表
    * @param @param PersonalCondition
    * @param @return    
    * @return List<listPersonalAllExport>    
    * @throws
     */
	public List<PersonalAllExport> listPersonalAllExport(PersonalCondition condition);
	
	/**
     * 
    * @Title: countPersonalAllExport
    * @Description: 条件查询员工基本信息列表个数
    * @param @param PersonalCondition
    * @param @return    
    * @return Long
    * @throws
     */
	public Long countPersonalAllExport(PersonalCondition condition);
}
