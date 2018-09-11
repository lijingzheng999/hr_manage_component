package hr.manage.component.recruit.service;

import hr.manage.component.recruit.model.RecruitInfo;
import hr.manage.component.recruit.model.ResumeCondition;
import hr.manage.component.recruit.model.ResumeInfo;
import hr.manage.component.recruit.model.ResumeInterview;
import hr.manage.component.recruit.model.ResumeInterviewCondition;

import java.util.List;



public interface ResumeService {
	
	/**
     * 
    * @Title: addRecruitInfo
    * @Description: 保存招聘需求信息
    * @Param RecruitInfo recruitInfo
    * @return int    
    * @throws
     */
	public int addRecruitInfo(RecruitInfo recruitInfo);
	
	/**
     * 
    * @Title: listRecruitInfo
    * @Description: 获取招聘需求信息列表
    * @param @return    
    * @return List<listRecruitInfo>    
    * @throws
     */
	public List<RecruitInfo> listRecruitInfo(ResumeCondition condition);
	
	/**
     * 
    * @Title: countRecruitInfo
    * @Description: 获取招聘需求信息列表个数
    * @param @return    
    * @return Long 
    * @throws
     */
	public Long countRecruitInfo(ResumeCondition condition);
	/**
     * 
    * @Title: deleteRecruitInfo
    * @Description: 删除招聘需求信息
    * @Param Integer recruitInfoId
    * @return int    
    * @throws
     */
	public int deleteRecruitInfo(Integer recruitInfoId);
	/**
     * 
    * @Title: updateStatusComplete
    * @Description: 更改状态为已完成
    * @Param Integer recruitInfoId
    * @return int    
    * @throws
     */
	public int updateStatusComplete(Integer recruitInfoId);
	/**
     * 
    * @Title: updateRecruitInfo
    * @Description: 修改招聘需求信息
    * @Param RecruitInfo recruitInfo
    * @return boolean    
    * @throws
     */
	public boolean updateRecruitInfo(RecruitInfo recruitInfo);
	
	/**
     * 
    * @Title: getRecruitInfo
    * @Description: 通过ID获取招聘需求信息
    * @Param Integer recruitInfoId
    * @return RecruitInfo    
    * @throws
     */
	public RecruitInfo getRecruitInfo(Integer recruitInfoId);
	
	
	/**
     * 
    * @Title: addResumeInfo
    * @Description: 上传邀约人员、面试通过者和未通过者基本信息
    * @Param ResumeInfo resumeInfo
    * @return int    
    * @throws
     */
	public int addResumeInfo(ResumeInfo resumeInfo);
	
	/**
     * 
    * @Title: listResumeInfo
    * @Description: 条件简历基本信息列表
    * @param @param ResumeCondition
    * @param @return    
    * @return List<ResumeInfo>    
    * @throws
     */
	public List<ResumeInfo> listResumeInfo(ResumeCondition condition);
	
	/**
     * 
    * @Title: countResumeInfo
    * @Description: 条件查询简历基本信息列表个数
    * @param @param ResumeCondition
    * @param @return    
    * @return Long
    * @throws
     */
	public Long countResumeInfo(ResumeCondition condition);

	/**
     * 
    * @Title: deleteResumeInfo
    * @Description: 删除简历信息
    * @Param Integer resumeInfoId
    * @return int    
    * @throws
     */
	public int deleteResumeInfo(Integer resumeInfoId);

	/**
     * 
    * @Title: getResumeInfo
    * @Description: 根据ID获取简历信息
    * @Param Integer resumeInfoId
    * @return ResumeInfo    
    * @throws
     */
	public ResumeInfo getResumeInfo(Integer resumeInfoId);
	
	/**
     * 
    * @Title: updateResumeInfo
    * @Description: 修改简历信息
    * @Param ResumeInfo resumeInfo
    * @return boolean    
    * @throws
     */
	public boolean updateResumeInfo(ResumeInfo resumeInfo);
	
	/**
     * 
    * @Title: updateResumeInfoPass
    * @Description: 修改简历信息-面试通过；新增沟通表
    * @Param ResumeInfo resumeInfo
    * @return int    
    * @throws
     */
	public int updateResumeInfoPass(ResumeInfo resumeInfo);
	

	/**
     * 
    * @Title: addResumeInterview
    * @Description: 面试通过后;新增沟通表数据;
    * @Param ResumeInterview resumeInterview
    * @return int    
    * @throws
     */
	public int addResumeInterview(ResumeInterview resumeInterview);
	
	/**
     * 
    * @Title: listResumeInterview
    * @Description: 条件查询沟通表
    * @param @param ResumeInterviewCondition
    * @param @return    
    * @return List<ResumeInterview>    
    * @throws
     */
	public List<ResumeInterview> listResumeInterview(ResumeInterviewCondition condition);
	
	/**
     * 
    * @Title: countResumeInterview
    * @Description: 条件查询沟通表个数
    * @param @param ResumeInterviewCondition
    * @param @return    
    * @return Long
    * @throws
     */
	public Long countResumeInterview(ResumeInterviewCondition condition);

	/**
     * 
    * @Title: deleteResumeInterview
    * @Description: 删除沟通表信息
    * @Param Integer resumeInterviewId
    * @return int    
    * @throws
     */
	public int deleteResumeInterview(Integer resumeInterviewId);

	/**
     * 
    * @Title: getResumeInterview
    * @Description: 根据ID获取沟通表信息
    * @Param Integer resumeInterviewId
    * @return ResumeInterview    
    * @throws
     */
	public ResumeInterview getResumeInterview(Integer resumeInterviewId);
	
	/**
     * 
    * @Title: updateResumeInterview
    * @Description: 修改沟通表信息
    * @Param ResumeInterview resumeInterview
    * @return boolean    
    * @throws
     */
	public boolean updateResumeInterview(ResumeInterview resumeInterview);
	
	/**
     * 
    * @Title: updateResumeInterviewEntry
    * @Description: 修改简历信息-面试通过；新增沟通表
    * @Param ResumeInterview resumeInterview
    * @return int    
    * @throws
     */
	public int updateResumeInterviewEntry(ResumeInterview resumeInterview);
}
