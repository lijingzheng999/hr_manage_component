package hr.manage.component.personal.service;

import hr.manage.component.personal.model.PersonalAll;

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
	

}
