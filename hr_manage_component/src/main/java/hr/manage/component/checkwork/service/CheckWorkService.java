package hr.manage.component.checkwork.service;


import hr.manage.component.checkwork.model.CheckWorkBaidu;
import hr.manage.component.checkwork.model.CheckWorkCurrent;
import hr.manage.component.checkwork.model.CheckWorkDetail;
import hr.manage.component.checkwork.model.CheckWorkDetailCondition;
import hr.manage.component.checkwork.model.CheckWorkAnnualLeave;

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
    * @Title: getCheckWorkDetailById
    * @Description: 根据ID查询全通物联网人员考勤信息
    * @param  Integer detailId
    * @return CheckWorkDetail
    * @throws
     */
	public  CheckWorkDetail getCheckWorkDetailById(Integer detailId);
	
	/**
     * 
    * @Title: updateCheckWorkDetail
    * @Description: 删除全通物联网人员考勤信息
    * @param  CheckWorkDetail detail
    * @return int
    * @throws
     */
	public  int updateCheckWorkDetail(CheckWorkDetail detail);
	
	/**
     * 
    * @Title: CheckWorkAnnualLeave
    * @Description: 条件查询全通物联网人员加班及年假信息列表
    * @param  CheckWorkDetailCondition
    * @return List<CheckWorkAnnualLeave>
    * @throws
     */
	public  List<CheckWorkAnnualLeave> listCheckWorkAnnualLeave(CheckWorkDetailCondition condition);
	
	
	/**
     * 
    * @Title: countCheckWorkAnnualLeave
    * @Description: 条件查询全通物联网人员加班及年假信息列表个数
    * @param  CheckWorkDetailCondition
    * @return Long
    * @throws
     */
	public  Long countCheckWorkAnnualLeave(CheckWorkDetailCondition condition);

	
	/**
     * 
    * @Title: getCheckWorkAnnualLeaveById
    * @Description: 根据ID查询全通物联网人员加班及年假信息
    * @param  Integer annualId
    * @return CheckWorkAnnualLeave
    * @throws
     */
	public  CheckWorkAnnualLeave getCheckWorkAnnualLeaveById(Integer annualId);
	
	/**
     * 
    * @Title: updateCheckWorkAnnualLeave
    * @Description: 修改全通物联网人员加班及年假信息
    * @param  CheckWorkAnnualLeave current
    * @return int
    * @throws
     */
	public  int updateCheckWorkAnnualLeave(CheckWorkAnnualLeave annual);
	
	
	
//	/**
//     * 
//    * @Title: listCheckWorkCurrent
//    * @Description: 条件查询全通物联网人员加班及年假信息列表
//    * @param  CheckWorkDetailCondition
//    * @return List<CheckWorkCurrent>
//    * @throws
//     */
//	public  List<CheckWorkCurrent> listCheckWorkCurrent(CheckWorkDetailCondition condition);
//	
//	
//	/**
//     * 
//    * @Title: countCheckWorkCurrent
//    * @Description: 条件查询全通物联网人员加班及年假信息列表个数
//    * @param  CheckWorkDetailCondition
//    * @return Long
//    * @throws
//     */
//	public  Long countCheckWorkCurrent(CheckWorkDetailCondition condition);
//
//	
//	/**
//     * 
//    * @Title: getCheckWorkCurrentById
//    * @Description: 根据ID查询全通物联网人员加班及年假信息
//    * @param  Integer currentId
//    * @return CheckWorkCurrent
//    * @throws
//     */
//	public  CheckWorkCurrent getCheckWorkCurrentById(Integer currentId);
//	
//	/**
//     * 
//    * @Title: updateCheckWorkCurrent
//    * @Description: 修改全通物联网人员加班及年假信息
//    * @param  CheckWorkCurrent current
//    * @return int
//    * @throws
//     */
//	public  int updateCheckWorkCurrent(CheckWorkCurrent current);
//	
//	/**
//     * 
//    * @Title: deleteCheckWorkCurrent
//    * @Description: 删除全通物联网人员加班及年假信息
//    * @param  Integer currentId
//    * @return int
//    * @throws
//     */
//	public  int deleteCheckWorkCurrent(Integer currentId);
//	/**
//     * 
//    * @Title: getCheckWorkDetailByName
//    * @Description: 根据姓名查询全通物联网考勤信息
//    * @param  CheckWorkDetailCondition
//    * @return CheckWorkDetail
//    * @throws
//     */
//	public  CheckWorkDetail getCheckWorkDetailByName(CheckWorkDetailCondition condition);
//	
//	/**
//     * 
//    * @Title: getCheckWorkCurrentByName
//    * @Description: 根据姓名查询全通物联网人员当前剩余年假和加班小时数
//    * @param  CheckWorkDetailCondition
//    * @return CheckWorkCurrent
//    * @throws
//     */
//	public  CheckWorkCurrent getCheckWorkCurrentByName(String name);
//	
	/**
     * 
    * @Title: saveCheckWorkDetailListRecord
    * @Description: 保存全通物联网考勤信息列表
    * @param  List<CheckWorkDetail>
    * @return int
    * @throws
     */
	public  int saveCheckWorkDetailListRecord( List<CheckWorkDetail> checkWorkList);
	
	

	/**
     * 
    * @Title: listCheckWorkBaidu
    * @Description: 条件查询百度考勤信息列表
    * @param  CheckWorkDetailCondition
    * @return List<CheckWorkBaidu>
    * @throws
     */
	public  List<CheckWorkBaidu> listCheckWorkBaidu(CheckWorkDetailCondition condition);
	
	
	/**
     * 
    * @Title: countCheckWorkBaidu
    * @Description: 条件查询百度考勤信息列表个数
    * @param  CheckWorkDetailCondition
    * @return Long
    * @throws
     */
	public  Long countCheckWorkBaidu(CheckWorkDetailCondition condition);

	/**
     * 
    * @Title: getCheckWorkBaiduById
    * @Description: 根据ID查询百度人员考勤信息
    * @param  Integer baiduId
    * @return CheckWorkBaidu
    * @throws
     */
	public  CheckWorkBaidu getCheckWorkBaiduById(Integer baiduId);
	
	/**
     * 
    * @Title: updateCheckWorkBaidu
    * @Description: 删除全通物联网人员考勤信息
    * @param  CheckWorkBaidu baidu
    * @return int
    * @throws
     */
	public  int updateCheckWorkBaidu(CheckWorkBaidu baidu);
	

	/**
     * 
    * @Title: saveCheckWorkBaiduListRecord
    * @Description: 保存百度考勤信息列表
    * @param   String term
    * @param  List<CheckWorkBaidu> baiduList
    * @return int
    * @throws
     */
	public  int saveCheckWorkBaiduListRecord( String term, Integer attendanceDays, List<CheckWorkBaidu> baiduList);
	
}
