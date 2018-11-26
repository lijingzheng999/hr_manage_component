package hr.manage.component.common.service;

import hr.manage.component.common.model.CommonType;
import hr.manage.component.common.model.SettingHoliday;
import hr.manage.component.common.model.UploadFile;
import hr.manage.component.personal.model.PersonalAll;
import hr.manage.component.personal.model.PersonalAllExport;
import hr.manage.component.personal.model.PersonalCondition;

import java.util.Date;
import java.util.List;



public interface CommonService {
	
	
	/**
     * 
    * @Title: saveUploadFile
    * @Description: 保存上传附件
    * @Param UploadFile file
    * @param @return    
    * @return int    
    * @throws
     */
	public int saveUploadFile(UploadFile file);

	/**
     * 
    * @Title: deleteUploadFile
    * @Description: 逻辑删除附件
    * @Param Integer fileId
    * @param @return    
    * @return int    
    * @throws
     */
	public int deleteUploadFile(Integer fileId);
	
	/**
     * 
    * @Title: listUploadFile
    * @Description: 获取附件列表
    * @Param Integer personalInfoId,Integer type
    * @param @return    
    * @return List<UploadFile>    
    * @throws
     */
	public List<UploadFile> listUploadFile(Integer personalInfoId,Integer type);
	
	
	/**
     * 
    * @Title: listCommonType
    * @Description: 获取码表
    * @Param Integer type
    * @param @return    
    * @return List<CommonType>    
    * @throws
     */
	public List<CommonType> listCommonType(Integer type);
	
	/**
     * 
    * @Title: listSettingHoliday
    * @Description: 获取节假日日期设置
    * @Param nteger type,Date startDate, Date endDate
    * @param @return    
    * @return List<SettingHoliday>    
    * @throws
     */
	public List<SettingHoliday> listSettingHoliday(Integer type,Date startDate, Date endDate);
	
	/**
     * 
    * @Title: deleteSettingHoliday
    * @Description: 逻辑删除节假日日期设置
    * @Param Integer fileId
    * @param @return    
    * @return int    
    * @throws
     */
	public int deleteSettingHoliday(Integer holiDayId);
	

	/**
     * 
    * @Title: saveSettingHoliday
    * @Description: 保存上传附件
    * @Param SettingHoliday holiday
    * @param @return    
    * @return int    
    * @throws
     */
	public int saveSettingHoliday(SettingHoliday holiday);

}
