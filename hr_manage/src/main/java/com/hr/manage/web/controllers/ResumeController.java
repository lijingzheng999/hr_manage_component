package com.hr.manage.web.controllers;

import hr.manage.component.admin.model.Admin;
import hr.manage.component.admin.service.AdminService;
import hr.manage.component.recruit.model.RecruitInfo;
import hr.manage.component.recruit.model.ResumeCondition;
import hr.manage.component.recruit.model.ResumeInfo;
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
    * @return String    
    * @throws
     */
	@AuthorityCheck(function = FunctionIds.FUNCTION_9)
	@NotCareLogin
	@Get("getRecruitList")
	@Post("getRecruitList")
	public String getRecruitList() {
//		ResumeCondition condition = new ResumeCondition();
//
//			condition.setPosition(position);
//			condition.setAge(age);
//			condition.setExperience(experience);
//		
//		pageIndex = pageIndex < 0 ? 0 : pageIndex;
//		pageSize = pageSize < 1 ? 1 : pageSize;
////		condition.setOrderby("createtime");
//		condition.setOffset(pageIndex * pageSize);
//		condition.setLimit(pageSize);
//		Long count = 0L;
		List<RecruitInfo> recruitLists = new ArrayList<>();
		try {
			recruitLists = resumeService.listRecruitInfo();
		} catch (Exception e) {
			logger.error("=====获取招聘需求列表查询，调用service出错=====", e);
			return "@" + JSONResult.error(CodeMsg.SERVER_ERROR);
		}
//		Long pageCount = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;
//		Map<String, Object> dataMap = DataMapUtil.getDataMap("resumeViewList", resumeLists, count, pageCount);
		return "@" + JSONResult.success(recruitLists);
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
        int result = resumeService.updateRecruitInfo(recruitInfo);
		if (result >0) {
			return "@" + JSONResult.success();
		} else {
			logger.error("=====修改招聘需求信息失败,数据库保存失败=====");
			return "@" + JSONResult.error(CodeMsg.ERROR, "修改招聘需求信息失败,数据库保存失败");
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
//		if(StringUtils.isNotBlank(name)){
//			// 当查询条件有姓名时，只需要根据姓名查出该单，其他条件忽略
//			condition.setName(name);
//		} else {
			condition.setPosition(position);
			condition.setAge(age);
			condition.setExperience(experience);
			
//		}
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
        int result = resumeService.updateResumeInfo(resumeInfo);
		if (result >0) {
			return "@" + JSONResult.success();
		} else {
			logger.error("=====修改简历信息失败,数据库保存失败=====");
			return "@" + JSONResult.error(CodeMsg.ERROR, "修改简历信息失败,数据库保存失败");
		}

	}
	
}
