package com.hr.manage.web.controllers;

import hr.manage.component.admin.model.Admin;
import hr.manage.component.admin.service.AdminService;
import hr.manage.component.recruit.model.RecruitInfo;
import hr.manage.component.recruit.model.ResumeCondition;
import hr.manage.component.recruit.model.ResumeInfo;
import hr.manage.component.recruit.model.ResumeInterview;
import hr.manage.component.recruit.model.ResumeInterviewCondition;
import hr.manage.component.recruit.service.ResumeService;

import com.hr.manage.web.constant.JSONResult;
import com.hr.manage.web.constant.CodeMsg;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;

import org.apache.commons.beanutils.locale.converters.DecimalLocaleConverter;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.internal.bind.BigIntegerTypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.hr.manage.web.annotation.AuthorityCheck;
import com.hr.manage.web.annotation.NotCareLogin;
import com.hr.manage.web.constant.FunctionIds;
import com.hr.manage.web.util.DataMapUtil;

/**
 * 
* @see 招聘相关信息相关API;
* @see 统一返回JSON串 code,message,data
* @author  lee
* @version 1.0
 */
@Path("resume")
public class ResumeController {

	@Autowired
	Invocation inv;
	@Autowired
	ResumeService resumeService;
	@Autowired
	AdminService adminService;
	private final Log logger = LogFactory.getLog(ResumeController.class);
	
	
	/**
     * 
    * Title: addRecruitInfo
    * Description: 新增招聘需求信息
    * Url: resume/addRecruitInfo
    * @param String recruitInfoJsonStr 招聘需求信息json串
    * @return String    
    * @throws
    * @see RecruitInfo
     */
	@AuthorityCheck(function = FunctionIds.FUNCTION_9)
	@NotCareLogin
	@Post("addRecruitInfo")
	@Get("addRecruitInfo")
	public String addRecruitInfo(
			@Param("recruitInfoJsonStr") String recruitInfoJsonStr) {
		if(StringUtils.isBlank(recruitInfoJsonStr)){
			logger.error("=====参数错误，不应为空=====");
			return "@" + JSONResult.error(CodeMsg.ERROR,"参数错误，不应为空！");
		}
		RecruitInfo recruitInfo = null;
		try {
			recruitInfo = JSONObject.parseObject(recruitInfoJsonStr, RecruitInfo.class);
		} catch (Exception e) {
			logger.error("=====新增招聘需求信息，解析参数出错=====", e);
			return "@" + JSONResult.error(CodeMsg.ERROR,"解析对象出错！");
		}
		Admin user = (Admin)inv.getRequest().getSession().getAttribute("user");
		
		// 进行新
		recruitInfo.setStatus(1); //默认进行中；
		recruitInfo.setIsDel(1);
		recruitInfo.setCreateUser(user.getRealname());
		recruitInfo.setCreateTime(new Date());
        int result = resumeService.addRecruitInfo(recruitInfo);
		if (result >0) {
			return "@" + JSONResult.success();
		} else {
			logger.error("=====新增招聘需求信息失败,数据库保存失败=====");
			return "@" + JSONResult.error(CodeMsg.ERROR, "新增招聘需求信息失败,数据库保存失败");
		}

	}
	
	/**
     * 
    * Title: getRecruitList
    * Description: 获取招聘需求列表
    * Url: resume/getRecruitList
    * @param String position, 岗位
    * @param String expatriateUnit,外派单位
    * @param Integer status,状态
    * @param int pageIndex, 分页页数
    * @param int pageSize 	行数
    * @return String    
    * @throws
     */
	@AuthorityCheck(function = FunctionIds.FUNCTION_9)
	@NotCareLogin
	@Get("getRecruitList")
	@Post("getRecruitList")
	public String getRecruitList(@Param("position") String position,
			@Param("expatriateUnit") String expatriateUnit,
			@Param("status") Integer status,
			@Param("pageIndex") int pageIndex, 
			@Param("pageSize") int pageSize) {
		ResumeCondition condition = new ResumeCondition();

			condition.setPosition(position);
			condition.setStatus(status);
			condition.setExpatriateUnit(expatriateUnit);
		
		pageIndex = pageIndex < 0 ? 0 : pageIndex;
		pageSize = pageSize < 1 ? 1 : pageSize;
//		condition.setOrderby("createtime");
		condition.setOffset(pageIndex * pageSize);
		condition.setLimit(pageSize);
		Long count = 0L;
		List<RecruitInfo> recruitLists = new ArrayList<>();
		try {
			recruitLists = resumeService.listRecruitInfo(condition);
			count = resumeService.countRecruitInfo(condition);
		} catch (Exception e) {
			logger.error("=====获取招聘需求列表查询，调用service出错=====", e);
			return "@" + JSONResult.error(CodeMsg.SERVER_ERROR);
		}
		Long pageCount = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;
		Map<String, Object> dataMap = DataMapUtil.getDataMap("recruitLists", recruitLists, count, pageCount);
		return "@" + JSONResult.success(dataMap);
	}
	
	
	/**
     * 
    * Title: deleteRecruitInfo
    * Description: 删除招聘需求信息
    * Url: resume/deleteRecruitInfo
    * @param Integer recruitInfoId
    * @return String    
    * @throws
    * @see RecruitInfo
     */
	@AuthorityCheck(function = FunctionIds.FUNCTION_9)
	@NotCareLogin
	@Post("deleteRecruitInfo")
	@Get("deleteRecruitInfo")
	public String deleteRecruitInfo(
			@Param("recruitInfoId") Integer recruitInfoId) {
	
        int result = resumeService.deleteRecruitInfo(recruitInfoId);
		if (result >0) {
			return "@" + JSONResult.success();
		} else {
			logger.error("=====删除招聘需求信息失败,数据库保存失败=====");
			return "@" + JSONResult.error(CodeMsg.ERROR, "删除招聘需求信息失败,数据库保存失败");
		}

	}
	
	/**
     * 
    * Title: updateRecruitInfo
    * Description: 修改招聘需求信息
    * Url: resume/updateRecruitInfo
    * @param String recruitInfoJsonStr  招聘需求信息json串
    * @return String    
    * @throws
    * @see RecruitInfo
     */
	@AuthorityCheck(function = FunctionIds.FUNCTION_9)
	@NotCareLogin
	@Post("updateRecruitInfo")
	@Get("updateRecruitInfo")
	public String updateRecruitInfo(
			@Param("recruitInfoJsonStr") String recruitInfoJsonStr) {
		if(StringUtils.isBlank(recruitInfoJsonStr)){
			logger.error("=====参数错误，不应为空=====");
			return "@" + JSONResult.error(CodeMsg.ERROR,"参数错误，不应为空！");
		}
		RecruitInfo recruitInfo = null;
		try {
			recruitInfo = JSONObject.parseObject(recruitInfoJsonStr, RecruitInfo.class);
		} catch (Exception e) {
			logger.error("=====修改招聘需求信息，解析参数出错=====", e);
			return "@" + JSONResult.error(CodeMsg.ERROR,"解析对象出错！");
		}
		recruitInfo.setUpdateTime(new Date());
		boolean result = resumeService.updateRecruitInfo(recruitInfo);
		if (result) {
			return "@" + JSONResult.success();
		} else {
			logger.error("=====修改招聘需求信息失败,数据库保存失败=====");
			return "@" + JSONResult.error(CodeMsg.ERROR, "修改招聘需求信息失败,数据库保存失败");
		}

	}
	
	/**
     * 
    * Title: updateRecruitStatusComplete
    * Description: 更改状态为已完成
    * Url: resume/updateRecruitStatusComplete
    * @param Integer recruitInfoId
    * @return String    
    * @throws
    * @see RecruitInfo
     */
	@AuthorityCheck(function = FunctionIds.FUNCTION_9)
	@NotCareLogin
	@Post("updateRecruitStatusComplete")
	@Get("updateRecruitStatusComplete")
	public String updateRecruitStatusComplete(
			@Param("recruitInfoId") Integer recruitInfoId) {
	
		RecruitInfo recruit = resumeService.getRecruitInfo(recruitInfoId);
		if (recruit !=null) {
			if(recruit.getStatus().equals(1)){
				resumeService.updateStatusComplete(recruitInfoId);
				return "@" + JSONResult.success();
			}else{
				logger.error("=====此招聘需求数据不是进行中状态=====");
				return "@" + JSONResult.error(CodeMsg.ERROR, "此招聘需求数据不是进行中状态");
			}
		} else {
			logger.error("===== 获取招聘需求数据失败,没有此ID数据=====");
			return "@" + JSONResult.error(CodeMsg.ERROR, "根据ID没有找到数据");
		}

	}
	
	/**
     * 
    * Title: getRecruitInfo
    * Description: 获取招聘需求数据
    * Url: resume/getRecruitInfo
    * @param Integer recruitInfoId
    * @return String    
    * @throws
    * @see RecruitInfo
     */
	@AuthorityCheck(function = FunctionIds.FUNCTION_9)
	@NotCareLogin
	@Post("getRecruitInfo")
	@Get("getRecruitInfo")
	public String getRecruitInfo(
			@Param("recruitInfoId") Integer recruitInfoId) {
	
		RecruitInfo recruit = resumeService.getRecruitInfo(recruitInfoId);
		if (recruit !=null) {
			return "@" + JSONResult.success(recruit);
		} else {
			logger.error("===== 获取招聘需求数据失败,没有此ID数据=====");
			return "@" + JSONResult.error(CodeMsg.ERROR, "根据ID没有找到数据");
		}

	}
	
	/**
     * 
    * Title: addResumeInfo
    * Description: 新增简历信息
    * Url: resume/addResumeInfo
    * @param String resumeInfoJsonStr 招聘简历信息json串
    * @return String    
    * @throws
    * @see ResumeInfo
     */
	@AuthorityCheck(function = FunctionIds.FUNCTION_10)
	@NotCareLogin
	@Post("addResumeInfo")
	@Get("addResumeInfo")
	public String addResumeInfo(
			@Param("resumeInfoJsonStr") String resumeInfoJsonStr) {
		if(StringUtils.isBlank(resumeInfoJsonStr)){
			logger.error("=====参数错误，不应为空=====");
			return "@" + JSONResult.error(CodeMsg.ERROR,"参数错误，不应为空！");
		}
		ResumeInfo resumeInfo = null;
		try {
			resumeInfo = JSONObject.parseObject(resumeInfoJsonStr, ResumeInfo.class);
		} catch (Exception e) {
			logger.error("=====新增简历信息，解析参数出错=====", e);
			return "@" + JSONResult.error(CodeMsg.ERROR,"解析对象出错！");
		}
		
		// 进行新
		resumeInfo.setIsDel(1);
		resumeInfo.setCreateTime(new Date());
        int result = resumeService.addResumeInfo(resumeInfo);
		if (result >0) {
			return "@" + JSONResult.success();
		} else {
			logger.error("=====新增简历信息失败,数据库保存失败=====");
			return "@" + JSONResult.error(CodeMsg.ERROR, "新增简历信息失败,数据库保存失败");
		}

	}
	
	/**
     * 
    * Title: getResumeList
    * Description: 根据条件筛选简历信息列表
    * Url: resume/getResumeList
    * @param String position, 岗位
    * @param String age,年龄
    * @param String experience,工作年限
    * @param int pageIndex, 分页页数
    * @param int pageSize 	行数
    * @return String    
    * @throws
     */
	@AuthorityCheck(function = FunctionIds.FUNCTION_10)
	@NotCareLogin
	@Get("getResumeList")
	@Post("getResumeList")
	public String getResumeList(@Param("position") String position,
			@Param("age") Integer age,
			@Param("experience") Integer experience,
			@Param("pageIndex") int pageIndex, 
			@Param("pageSize") int pageSize) {
		ResumeCondition condition = new ResumeCondition();

			condition.setPosition(position);
			condition.setExperience(experience);
			
		pageIndex = pageIndex < 0 ? 0 : pageIndex;
		pageSize = pageSize < 1 ? 1 : pageSize;
//		condition.setOrderby("createtime");
		condition.setOffset(pageIndex * pageSize);
		condition.setLimit(pageSize);
		Long count = 0L;
		List<ResumeInfo> resumeLists = new ArrayList<>();
		try {
			resumeLists = resumeService.listResumeInfo(condition);
			count = resumeService.countResumeInfo(condition);
		} catch (Exception e) {
			logger.error("=====根据条件筛选简历信息列表查询，调用service出错=====", e);
			return "@" + JSONResult.error(CodeMsg.SERVER_ERROR);
		}
		Long pageCount = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;
		Map<String, Object> dataMap = DataMapUtil.getDataMap("resumeViewList", resumeLists, count, pageCount);
		return "@" + JSONResult.success(dataMap);
	}
	
	/**
     * 
    * Title: deleteResumeInfo
    * Description:  删除简历信息
    * Url: resume/deleteResumeInfo
    * @param  Integer resumeInfoId
    * @return String    
    * @throws
    * @see ResumeInfo
     */
	@AuthorityCheck(function = FunctionIds.FUNCTION_10)
	@NotCareLogin
	@Post("deleteResumeInfo")
	@Get("deleteResumeInfo")
	public String deleteResumeInfo(
			@Param("resumeInfoId") Integer resumeInfoId) {
		
        int result = resumeService.deleteResumeInfo(resumeInfoId);
		if (result >0) {
			return "@" + JSONResult.success();
		} else {
			logger.error("=====删除简历信息失败,数据库保存失败=====");
			return "@" + JSONResult.error(CodeMsg.ERROR, "删除简历信息失败,数据库保存失败");
		}

	}
	

	/**
     * 
    * Title: getResumeInfo
    * Description:  根据ID获取简历信息
    * Url: resume/getResumeInfo
    * @param  Integer resumeInfoId
    * @return String    
    * @throws
    * @see ResumeInfo
     */
	@AuthorityCheck(function = FunctionIds.FUNCTION_10)
	@NotCareLogin
	@Post("getResumeInfo")
	@Get("getResumeInfo")
	public String getResumeInfo(
			@Param("resumeInfoId") Integer resumeInfoId) {
		
		ResumeInfo resume = resumeService.getResumeInfo(resumeInfoId);
		if (resume !=null) {
			return "@" + JSONResult.success(resume);
		} else {
			logger.error("=====根据ID获取简历信息失败,没有此数据=====");
			return "@" + JSONResult.error(CodeMsg.ERROR, "根据ID获取简历信息失败,没有此数据");
		}

	}
	
	
	/**
     * 
    * Title: updateResumeInfo
    * Description: 修改简历信息
    * Url: resume/updateResumeInfo
    * @param String resumeInfoJsonStr 招聘简历信息json串
    * @return String    
    * @throws
    * @see ResumeInfo
     */
	@AuthorityCheck(function = FunctionIds.FUNCTION_10)
	@NotCareLogin
	@Post("updateResumeInfo")
	@Get("updateResumeInfo")
	public String updateResumeInfo(
			@Param("resumeInfoJsonStr") String resumeInfoJsonStr) {
		if(StringUtils.isBlank(resumeInfoJsonStr)){
			logger.error("=====参数错误，不应为空=====");
			return "@" + JSONResult.error(CodeMsg.ERROR,"参数错误，不应为空！");
		}
		ResumeInfo resumeInfo = null;
		try {
			resumeInfo = JSONObject.parseObject(resumeInfoJsonStr, ResumeInfo.class);
		} catch (Exception e) {
			logger.error("=====修改简历信息，解析参数出错=====", e);
			return "@" + JSONResult.error(CodeMsg.ERROR,"解析对象出错！");
		}
		
		resumeInfo.setUpdateTime(new Date());
		boolean result = resumeService.updateResumeInfo(resumeInfo);
		if (result) {
			return "@" + JSONResult.success();
		} else {
			logger.error("=====修改简历信息失败,数据库保存失败=====");
			return "@" + JSONResult.error(CodeMsg.ERROR, "修改简历信息失败,数据库保存失败");
		}

	}
	
	/**
     * 
    * Title: updateResumeNotPass
    * Description: 面试是否通过; status=2通过；status=0未通过
    * Url: resume/updateResumeNotPass
    * @param Integer resumeInfoId
    * @return String    
    * @throws
    * @see ResumeInfo
     */
	@AuthorityCheck(function = FunctionIds.FUNCTION_10)
	@NotCareLogin
	@Post("updateResumeNotPass")
	@Get("updateResumeNotPass")
	public String updateResumeNotPass(
			@Param("resumeInfoId") Integer resumeInfoId) {
		
		ResumeInfo resume = resumeService.getResumeInfo(resumeInfoId);
		if (resume !=null) {
			if(resume.getStatus()!=1){
				logger.error("=====简历状态不是进行中=====");
				return "@" + JSONResult.error(CodeMsg.ERROR, "变更状态失败,简历状态不是进行中");
			}
			resume.setUpdateTime(new Date());
			resume.setStatus(0);//status=2通过；status=0未通过
			boolean result = resumeService.updateResumeInfo(resume);
			if (result) {
				return "@" + JSONResult.success();
			} else {
				logger.error("=====修改简历信息失败,数据库保存失败=====");
				return "@" + JSONResult.error(CodeMsg.ERROR, "修改简历信息失败,数据库保存失败");
			}
		} else {
			logger.error("=====根据ID获取简历信息失败,没有此数据=====");
			return "@" + JSONResult.error(CodeMsg.ERROR, "根据ID获取简历信息失败,没有此数据");
		}
	}
	
	/**
     * 
    * Title: updateResumePass
    * Description: 面试是否通过; status=2通过
    * Url: resume/updateResumePass
    * @param Integer resumeInfoId
    * @return String    
    * @throws
    * @see ResumeInfo
     */
	@AuthorityCheck(function = FunctionIds.FUNCTION_10)
	@NotCareLogin
	@Post("updateResumePass")
	@Get("updateResumePass")
	public String updateResumePass(
			@Param("resumeInfoId") Integer resumeInfoId) {
		
		ResumeInfo resume = resumeService.getResumeInfo(resumeInfoId);
		if (resume !=null) {
			if(resume.getStatus()!=1){
				logger.error("=====简历状态不是进行中=====");
				return "@" + JSONResult.error(CodeMsg.ERROR, "变更状态失败,简历状态不是进行中");
			}
			resume.setUpdateTime(new Date());
			resume.setStatus(2);//status=2通过；status=0未通过
			int result = resumeService.updateResumeInfoPass(resume);
			if (result>0) {
				return "@" + JSONResult.success();
			} else {
				logger.error("=====面试通过失败,数据库保存失败=====");
				return "@" + JSONResult.error(CodeMsg.ERROR, "面试通过失败,数据库保存失败");
			}
		} else {
			logger.error("=====根据ID获取简历信息失败,没有此数据=====");
			return "@" + JSONResult.error(CodeMsg.ERROR, "根据ID获取简历信息失败,没有此数据");
		}
	}
	

	/**
     * 
    * Title: getResumeInterview
    * Description: 获取沟通表数据
    * Url: resume/getResumeInterview
    * @param Integer resumeInterviewId
    * @return String    
    * @throws
    * @see ResumeInterview
     */
	@AuthorityCheck(function = FunctionIds.FUNCTION_10)
	@NotCareLogin
	@Post("getResumeInterview")
	@Get("getResumeInterview")
	public String getResumeInterview(
			@Param("resumeInterviewId") Integer resumeInterviewId) {
	
		ResumeInterview interview = resumeService.getResumeInterview(resumeInterviewId);
		if (interview !=null) {
			return "@" + JSONResult.success(interview);
		} else {
			logger.error("===== 获取沟通表数据失败,没有此ID数据=====");
			return "@" + JSONResult.error(CodeMsg.ERROR, "根据ID没有找到数据");
		}

	}
	
	/**
     * 
    * Title: updateResumeInterview
    * Description: 修改沟通表信息
    * Url: resume/updateResumeInterview
    * @param String resumeInterviewJsonStr 沟通表信息json串
    * @return String    
    * @throws
    * @see ResumeInterview
     */
	@AuthorityCheck(function = FunctionIds.FUNCTION_10)
	@NotCareLogin
	@Post("updateResumeInterview")
	@Get("updateResumeInterview")
	public String updateResumeInterview(
			@Param("resumeInterviewJsonStr") String resumeInterviewJsonStr) {
		if(StringUtils.isBlank(resumeInterviewJsonStr)){
			logger.error("=====参数错误，不应为空=====");
			return "@" + JSONResult.error(CodeMsg.ERROR,"参数错误，不应为空！");
		}
		ResumeInterview resumeInterview = null;
		try {
			resumeInterview = JSONObject.parseObject(resumeInterviewJsonStr, ResumeInterview.class);
		} catch (Exception e) {
			logger.error("=====修改简历信息，解析参数出错=====", e);
			return "@" + JSONResult.error(CodeMsg.ERROR,"解析对象出错！");
		}
		
		resumeInterview.setUpdateTime(new Date());
		boolean result = resumeService.updateResumeInterview(resumeInterview);
		if (result) {
			return "@" + JSONResult.success();
		} else {
			logger.error("=====修改沟通表信息失败,数据库保存失败=====");
			return "@" + JSONResult.error(CodeMsg.ERROR, "修改沟通表信息失败,数据库保存失败");
		}
	}
	
	/**
     * 
    * Title: updateInterviewNotEntry
    * Description: 变更不入职
    * Url: resume/updateInterviewNotEntry
    * @param Integer resumeInterviewId
    * @param String reson
    * @return String    
    * @throws
    * @see ResumeInterview
     */
	@AuthorityCheck(function = FunctionIds.FUNCTION_10)
	@NotCareLogin
	@Post("updateInterviewNotEntry")
	@Get("updateInterviewNotEntry")
	public String updateInterviewNotEntry(
			@Param("resumeInterviewId") Integer resumeInterviewId,@Param("reson") String reson) {
		
		ResumeInterview interview = resumeService.getResumeInterview(resumeInterviewId);
		if (interview !=null) {
			if(interview.getStatus()!=1){
				logger.error("=====沟通表状态不是待入职=====");
				return "@" + JSONResult.error(CodeMsg.ERROR, "变更状态失败,沟通表状态不是待入职");
			}
			interview.setUpdateTime(new Date());
			interview.setStatus(0);
			//status=0未入职
			interview.setMemo(reson);
			//未入职原因
			boolean result = resumeService.updateResumeInterview(interview);
			if (result) {
				return "@" + JSONResult.success();
			} else {
				logger.error("=====办理未入职失败,数据库保存失败=====");
				return "@" + JSONResult.error(CodeMsg.ERROR, "办理未入职,数据库保存失败");
			}
		} else {
			logger.error("=====根据ID获取沟通表信息失败,没有此数据=====");
			return "@" + JSONResult.error(CodeMsg.ERROR, "根据ID获取沟通表信息失败,没有此数据");
		}
	}
	
	/**
     * 
    * Title: updateInterviewEntry
    * Description: 办理入职
    * Url: resume/updateInterviewEntry
    * @param Integer resumeInterviewId
    * @param String reson
    * @return String    
    * @throws
    * @see ResumeInterview
     */
	@AuthorityCheck(function = FunctionIds.FUNCTION_10)
	@NotCareLogin
	@Post("updateInterviewEntry")
	@Get("updateInterviewEntry")
	public String updateInterviewEntry(
			@Param("resumeInterviewId") Integer resumeInterviewId,@Param("reson") String reson) {
		
		ResumeInterview interview = resumeService.getResumeInterview(resumeInterviewId);
		if (interview !=null) {
			if(interview.getStatus()!=1){
				logger.error("=====沟通表状态不是待入职=====");
				return "@" + JSONResult.error(CodeMsg.ERROR, "变更状态失败,沟通表状态不是待入职");
			}
			interview.setUpdateTime(new Date());
			//status=2 已入职
			interview.setStatus(2);
            //备注
			interview.setMemo(reson);
			int result = resumeService.updateResumeInterviewEntry(interview);
			if (result>0) {
				//获取简历信息
				ResumeInfo resume = resumeService.getResumeInfo(interview.getResumeId());
				Map<String, Object> dataMap = new HashMap<>();
				dataMap.put("resumeInterview", interview);
				if (resume !=null) {
					dataMap.put("resumeInfo", resume);
				}
				return "@" + JSONResult.success(dataMap);
			} else {
				logger.error("=====办理入职失败,数据库保存失败=====");
				return "@" + JSONResult.error(CodeMsg.ERROR, "办理入职失败,数据库保存失败");
			}
		} else {
			logger.error("=====根据ID获取沟通表信息失败,没有此数据=====");
			return "@" + JSONResult.error(CodeMsg.ERROR, "根据ID获取沟通表信息失败,没有此数据");
		}
	}
	
	/**
     * 
    * Title: updateInterviewReEntry
    * Description: 再次办理入职
    * Url: resume/updateInterviewReEntry
    * @param Integer resumeInterviewId
    * @return String    
    * @throws
    * @see ResumeInterview
     */
	@AuthorityCheck(function = FunctionIds.FUNCTION_10)
	@NotCareLogin
	@Post("updateInterviewReEntry")
	@Get("updateInterviewReEntry")
	public String updateInterviewReEntry(
			@Param("resumeInterviewId") Integer resumeInterviewId) {
		
		ResumeInterview interview = resumeService.getResumeInterview(resumeInterviewId);
		if (interview !=null) {
				//获取简历信息
				ResumeInfo resume = resumeService.getResumeInfo(interview.getResumeId());
				Map<String, Object> dataMap = new HashMap<>();
				dataMap.put("resumeInterview", interview);
				if (resume !=null) {
					dataMap.put("resumeInfo", resume);
				}
				return "@" + JSONResult.success(dataMap);
			
		} else {
			logger.error("=====根据ID获取沟通表信息失败,没有此数据=====");
			return "@" + JSONResult.error(CodeMsg.ERROR, "根据ID获取沟通表信息失败,没有此数据");
		}
	}
	
	
	/**
     * 
    * Title: getInterViewList
    * Description: 根据条件筛选简历信息列表
    * Url: resume/getInterViewList
    * @param String name, 姓名
    * @param String position, 岗位
    * @param String expatriateUnit,外派单位
    * @param int status,入职状态：2：已入职，1：待入职；0：未入职（未入职来个原因） 
    * @param int pageIndex, 分页页数
    * @param int pageSize 	行数
    * @return String    
    * @throws
     */
	@AuthorityCheck(function = FunctionIds.FUNCTION_10)
	@NotCareLogin
	@Get("getInterViewList")
	@Post("getInterViewList")
	public String getInterViewList(@Param("name") String name,
			@Param("position") String position,
			@Param("expatriateUnit") String expatriateUnit,
			@Param("status") Integer status,
			@Param("pageIndex") int pageIndex, 
			@Param("pageSize") int pageSize) {
		ResumeInterviewCondition condition = new ResumeInterviewCondition();

			condition.setName(name);
			condition.setPosition(position);
			condition.setExpatriateUnit(expatriateUnit);
			condition.setStatus(status);
			
		pageIndex = pageIndex < 0 ? 0 : pageIndex;
		pageSize = pageSize < 1 ? 1 : pageSize;
//		condition.setOrderby("createtime");
		condition.setOffset(pageIndex * pageSize);
		condition.setLimit(pageSize);
		Long count = 0L;
		List<ResumeInterview> interviewLists = new ArrayList<>();
		try {
			interviewLists = resumeService.listResumeInterview(condition);
			count = resumeService.countResumeInterview(condition);
		} catch (Exception e) {
			logger.error("=====根据条件筛选沟通表信息列表查询，调用service出错=====", e);
			return "@" + JSONResult.error(CodeMsg.SERVER_ERROR);
		}
		Long pageCount = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;
		Map<String, Object> dataMap = DataMapUtil.getDataMap("interviewLists", interviewLists, count, pageCount);
		return "@" + JSONResult.success(dataMap);
	}
}
