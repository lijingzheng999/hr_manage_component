package hr.manage.component.checkwork.service;


import hr.manage.component.checkwork.model.CheckWorkDetail;
import hr.manage.component.checkwork.model.CheckWorkDetailCondition;

import java.util.List;



public interface CheckWorkService {
	

	/**
     * 
    * @Title: listCheckWorkDetail
    * @Description: 条件查询全通物联网考勤信息列表
    * @param  CheckWorkDetailCondition
    * @return List<CheckWorkDetail>
    * @throws
     */
	public  List<CheckWorkDetail> listCheckWorkDetail(CheckWorkDetailCondition condition);
	
	
	/**
     * 
    * @Title: countCheckWorkDetail
    * @Description: 条件查询全通物联网考勤信息列表个数
    * @param  CheckWorkDetailCondition
    * @return Long
    * @throws
     */
	public  Long countCheckWorkDetail(CheckWorkDetailCondition condition);
	
	/**
     * 
    * @Title: getCheckWorkDetailByName
    * @Description: 条件查询全通物联网考勤信息
    * @param  CheckWorkDetailCondition
    * @return CheckWorkDetail
    * @throws
     */
	public  CheckWorkDetail getCheckWorkDetailByName(CheckWorkDetailCondition condition);
	
	/**
     * 
    * @Title: saveCheckWorkDetailListRecord
    * @Description: 保存全通物联网考勤信息列表
    * @param  List<CheckWorkDetail>
    * @return int
    * @throws
     */
	public  int saveCheckWorkDetailListRecord( List<CheckWorkDetail> checkWorkList);
}
