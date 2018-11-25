package com.hr.manage.web.controllers;

import hr.manage.component.admin.model.Admin;
import hr.manage.component.admin.service.AdminService;
import hr.manage.component.checkwork.model.CheckWorkDetail;
import hr.manage.component.checkwork.model.CheckWorkDetailCondition;
import hr.manage.component.checkwork.service.CheckWorkService;
import hr.manage.component.contract.model.ContractCondition;
import hr.manage.component.contract.model.ContractInfo;
import hr.manage.component.contract.service.ContractService;
import hr.manage.component.personal.model.PersonalAll;
import hr.manage.component.personal.model.PersonalAllExport;
import hr.manage.component.personal.model.PersonalCondition;
import hr.manage.component.personal.model.PersonalInfo;
import hr.manage.component.personal.model.PersonalSalaryInfo;
import hr.manage.component.personal.model.PersonalWorkInfo;
import hr.manage.component.personal.service.PersonalService;
import hr.manage.component.recruit.model.ResumeInfo;
import hr.manage.component.salary.model.InsuranceDetail;
import hr.manage.component.salary.model.InsuranceDetailCondition;
import hr.manage.component.salary.model.ProfitDetail;
import hr.manage.component.salary.model.ProfitDetailCondition;
import hr.manage.component.salary.model.SalaryChange;
import hr.manage.component.salary.model.SalaryChangeCondition;
import hr.manage.component.salary.model.SalaryDetail;
import hr.manage.component.salary.model.SalaryDetailCondition;
import hr.manage.component.salary.service.SalaryService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import com.hr.manage.config.ServiceConfigFactory;
import com.hr.manage.web.annotation.AuthorityCheck;
import com.hr.manage.web.annotation.NotCareLogin;
import com.hr.manage.web.constant.CodeMsg;
import com.hr.manage.web.constant.FunctionIds;
import com.hr.manage.web.constant.JSONResult;
import com.hr.manage.web.util.DataMapUtil;
import com.hr.manage.web.util.DateTimeUtil;
import com.hr.manage.web.util.ExportBeanExcel;
import com.hr.manage.web.util.MD5Util;
import com.hr.manage.web.util.StringToListUtil;
import com.hr.manage.web.util.StringUtil;

/**
 * 
* @see 工资调整相关信息相关API;
* @see 统一返回JSON串 code,message,data
* @author  lee
* @version 1.0
 */
@Path("salary")
public class SalaryController {

	@Autowired
	Invocation inv;
	@Autowired
	SalaryService salaryService;
	@Autowired
	PersonalService personalService;
	@Autowired
	CheckWorkService checkWorkService;
	
	private static final String FILE_UPLOAD_PATH=ServiceConfigFactory.getValue("file.upload.path");
	private final Log logger = LogFactory.getLog(SalaryController.class);
	
	
	
	/**
     * 
    * Title: getList
    * Description: 根据条件获取工资调整信息列表
    * Url: salary/getList
    * @param String name, 姓名
    * @param String employeeNumber,员工编号
    * @param String startDate,调整工资记录生成时间的开始时间
    * @param String endDate,调整工资记录生成时间的截止时间
    * @param int pageIndex, 分页页数
    * @param int pageSize 	行数
    * @return String    
    * @throws
     */
	@AuthorityCheck(function = FunctionIds.FUNCTION_19)
	@NotCareLogin
	@Get("getList")
	@Post("getList")
	public String getList(@Param("name") String name,
			@Param("employeeNumber") String employeeNumber,
			@Param("startDate") String startDate,
			@Param("endDate") String endDate,
			@Param("pageIndex") int pageIndex, 
			@Param("pageSize") int pageSize) {
		SalaryChangeCondition condition = new SalaryChangeCondition();
			condition.setName(name);
			condition.setEmployeeNumber(employeeNumber);
			SimpleDateFormat sdt=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//小写的mm表示的是分钟  
			Date curStartDate=null;
			Date curEndDate=null;
			try {
				if(StringUtils.isNotBlank(startDate)){
					curStartDate = sdt.parse(String.valueOf(startDate).trim());			
				}
				if(StringUtils.isNotBlank(endDate)){
					curEndDate = sdt.parse(String.valueOf(endDate).trim());
				}
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				logger.error("=====参数错误，日期格式不对，转换错误=====");
				return "@" + JSONResult.error(CodeMsg.ERROR,"参数错误，日期格式不对，转换错误！");
			}
			
			condition.setStartDate(curStartDate);
			condition.setEndDate(curEndDate);
			
		pageIndex = pageIndex < 0 ? 0 : pageIndex;
		pageSize = pageSize < 1 ? 1 : pageSize;
		condition.setOffset(pageIndex * pageSize);
		condition.setLimit(pageSize);
		Long count = 0L;
		List<SalaryChange> salaryLists = new ArrayList<>();
		try {
			salaryLists = salaryService.listSalaryChange(condition);
			count = salaryService.countSalaryChange(condition);
		} catch (Exception e) {
			logger.error("=====根据条件获取调薪列表查询，调用service出错=====", e);
			return "@" + JSONResult.error(CodeMsg.SERVER_ERROR);
		}
		Long pageCount = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;
		Map<String, Object> dataMap = DataMapUtil.getDataMap("salaryViewList", salaryLists, count, pageCount);
		return "@" + JSONResult.success(dataMap);
	}
	
	/**
     * 
    * Title: addSalaryChange
    * Description: 新增工资调整信息
    * Url: salary/addSalaryChange
    * @param String salaryChangeJsonStr 工资调整信息json串
    * @return String    
    * @throws
    * @see SalaryChange
     */
	@AuthorityCheck(function = FunctionIds.FUNCTION_20)
	@NotCareLogin
	@Post("addSalaryChange")
	@Get("addSalaryChange")
	public String addSalaryChange(
			@Param("salaryChangeJsonStr") String salaryChangeJsonStr) {
		if(StringUtils.isBlank(salaryChangeJsonStr)){
			logger.error("=====参数错误，不应为空=====");
			return "@" + JSONResult.error(CodeMsg.ERROR,"参数错误，不应为空！");
		}
		SalaryChange salaryChange = null;
		try {
			salaryChange = JSONObject.parseObject(salaryChangeJsonStr, SalaryChange.class);
		} catch (Exception e) {
			logger.error("=====新增工资调整信息失败，解析参数出错=====", e);
			return "@" + JSONResult.error(CodeMsg.ERROR,"解析对象出错！");
		}
		PersonalAll personalAll = personalService.getPersonalAllInfoById(salaryChange.getPersonalInfoId());
		if(personalAll.getPersonalSalaryInfo()==null){
			logger.error("=====新增工资调整信息失败，查不到此员工的工资信息");
			return "@" + JSONResult.error(CodeMsg.ERROR,"新增工资调整信息失败，查不到此员工的工资信息");
		}
		//判断调薪幅度加上原来的转正工资是否等于 调薪后工资
		BigDecimal newPay= BigDecimal.ZERO;
		newPay=newPay.add(salaryChange.getChangeRange());
		newPay=newPay.add(personalAll.getPersonalSalaryInfo().getWorkerPay());
		if(salaryChange.getFinalSalary().compareTo(newPay)!=0){
			logger.error("=====新增工资调整信息失败，调薪幅度加上原来的转正工资不等于填写的调薪后工资;原有转正工资为:"+personalAll.getPersonalSalaryInfo().getWorkerPay());
			return "@" + JSONResult.error(CodeMsg.ERROR,"新增工资调整信息失败，调薪幅度加上原来的转正工资不等于填写的调薪后工资;原有转正工资为:"+personalAll.getPersonalSalaryInfo().getWorkerPay());
	    }
		Admin user = (Admin)inv.getRequest().getSession().getAttribute("user");
		salaryChange.setEmployeeNumber(personalAll.getPersonalInfo().getEmployeeNumber());
		salaryChange.setCreateUser(user.getRealname());
		salaryChange.setCreateTime(new Date());
		//新增需要判断参数；并且要更新基本信息
		int result = salaryService.addSalaryChange(salaryChange);
		if (result >0) {
			return "@" + JSONResult.success();
		} else {
			logger.error("=====数据库操作异常,新增失败=====result="+result);
			return "@" + JSONResult.error(CodeMsg.ERROR, "数据库操作异常,新增失败;result="+result);
		}

	}
	
	
	/**
     * 
    * Title: getSalaryDetailList
    * Description: 通过条件查询工资表明细
    * Url: salary/getSalaryDetailList
    * @param String name, 姓名
    * @param String term,账期
    * @param String expatriateUnit,外派单位
    * @param int pageIndex, 分页页数
    * @param int pageSize 	行数
    * @return String    
    * @throws
     */
	@AuthorityCheck(function = FunctionIds.FUNCTION_19)
	@NotCareLogin
	@Get("getSalaryDetailList")
	@Post("getSalaryDetailList")
	public String getSalaryDetailList(@Param("name") String name,
			@Param("term") String term,
			@Param("expatriateUnit") String expatriateUnit,
			@Param("pageIndex") int pageIndex, 
			@Param("pageSize") int pageSize) {
		SalaryDetailCondition condition = new SalaryDetailCondition();
			condition.setName(name);
			condition.setTerm(term);
			condition.setExpatriateUnit(expatriateUnit);
			
		pageIndex = pageIndex < 0 ? 0 : pageIndex;
		pageSize = pageSize < 1 ? 1 : pageSize;
		condition.setOffset(pageIndex * pageSize);
		condition.setLimit(pageSize);
		Long count = 0L;
		List<SalaryDetail> salaryLists = new ArrayList<>();
		try {
			salaryLists = salaryService.listSalaryDetail(condition);
			count = salaryService.countSalaryDetail(condition);
		} catch (Exception e) {
			logger.error("=====根据条件获取工资表明细查询，调用service出错=====", e);
			return "@" + JSONResult.error(CodeMsg.SERVER_ERROR);
		}
		Long pageCount = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;
		Map<String, Object> dataMap = DataMapUtil.getDataMap("salaryViewList", salaryLists, count, pageCount);
		return "@" + JSONResult.success(dataMap);
	}
	
	/**
     * 
    * Title: exportSalaryDetailList
    * Description: 通过条件导出工资表明细
    * Url: salary/exportSalaryDetailList
    * @param String name, 姓名
    * @param String term,账期
    * @param String expatriateUnit,外派单位
    * @return String    
    * @throws
     */
	@AuthorityCheck(function = FunctionIds.FUNCTION_19)
	@NotCareLogin
	@Get("exportSalaryDetailList")
	@Post("exportSalaryDetailList")
	public String exportSalaryDetailList(@Param("name") String name,
			@Param("term") String term,
			@Param("expatriateUnit") String expatriateUnit) {
		SalaryDetailCondition condition = new SalaryDetailCondition();
			condition.setName(name);
			condition.setTerm(term);
			condition.setExpatriateUnit(expatriateUnit);
		List<SalaryDetail> salaryLists = new ArrayList<>();
		try {
			salaryLists = salaryService.listSalaryDetail(condition);
		} catch (Exception e) {
			logger.error("=====根据条件导出工资表明细查询，调用service出错=====", e);
			return "@" + JSONResult.error(CodeMsg.SERVER_ERROR);
		}
		//根据查询条件和字段导出到excel
		Admin admin = (Admin)inv.getRequest().getSession().getAttribute("user");
		String dateStr = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		String fileName = "SalaryListExport-" + dateStr + ".xlsx";
		HttpServletResponse response = inv.getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");//xlsx
//		response.setContentType("application/vnd.ms-excel");//xls
		String heads = "";
		String columns = "";
		List<String> listHeads = StringToListUtil.getStringList(heads, ",");
		List<String> listColumns = StringToListUtil.getStringList(columns, ",");
		OutputStream out = null;
		try {
			out = response.getOutputStream();
			ExportBeanExcel<SalaryDetail> exportBeanExcelUtil = new ExportBeanExcel();
			exportBeanExcelUtil.exportExcel("工资表列表",listHeads,listColumns,salaryLists,out);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(admin.getRealname() + " 操作导出工资表列表文件出错", e);
			e.printStackTrace();
			return "@" + JSONResult.error(CodeMsg.SERVER_ERROR);
		} finally {
			try {
				out.flush();
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;	
}

	/**
     * 
    * Title: getSalaryDetailById
    * Description: 通过ID获取工资表明细
    * Url: salary/getSalaryDetailById
    * @param Integer salaryDetailId  工资表ID
    * @return String   
    * @see SalaryDetail 
    * @throws
     */
	@AuthorityCheck(function = FunctionIds.FUNCTION_19)
	@NotCareLogin
	@Post("getSalaryDetailById")
	@Get("getSalaryDetailById")
	public String getSalaryDetailById(
			@Param("salaryDetailId")Integer salaryDetailId) {

		SalaryDetail salaryDetail = salaryService.getSalaryDetailById(salaryDetailId);
		if (salaryDetail != null) {
			return "@" + JSONResult.success(salaryDetail);			
		} else {
			logger.error("=====根据ID未查到工资表信息=====");
			return "@" + JSONResult.error(CodeMsg.ERROR, "根据ID未查到工资表信息");
		}
	}
	
	/**
     * 
    * Title: updateSalaryDetail
    * Description: 修改工资表信息
    * Url: salary/updateSalaryDetail
    * @param String salaryDetailJsonStr 工资表json串
    * @return String    
    * @throws
    * @see SalaryDetail
     */
	@AuthorityCheck(function = FunctionIds.FUNCTION_10)
	@NotCareLogin
	@Post("updateSalaryDetail")
	@Get("updateSalaryDetail")
	public String updateSalaryDetail(
			@Param("salaryDetailJsonStr") String salaryDetailJsonStr) {
		if(StringUtils.isBlank(salaryDetailJsonStr)){
			logger.error("=====参数错误，不应为空=====");
			return "@" + JSONResult.error(CodeMsg.ERROR,"参数错误，不应为空！");
		}
		SalaryDetail salaryDetail = null;
		try {
			salaryDetail = JSONObject.parseObject(salaryDetailJsonStr, SalaryDetail.class);
		} catch (Exception e) {
			logger.error("=====修改工资表信息，解析参数出错=====", e);
			return "@" + JSONResult.error(CodeMsg.ERROR,"解析对象出错！");
		}
		
		
		int result = salaryService.updateSalaryDetail(salaryDetail);
		if (result>0) {
			return "@" + JSONResult.success();
		} else if(result==-2){
			logger.error("=====没有此工资表信息=====");
			return "@" + JSONResult.error(CodeMsg.ERROR, "修改失败,没有此工资表信息");
		} else{
			logger.error("=====修改工资表信息失败,数据库保存失败=====");
			return "@" + JSONResult.error(CodeMsg.ERROR, "修改工资表信息失败,数据库保存失败");
		}

	}
	
	/**
     * 
    * Title: createSalaryDetail
    * Description: 按月新增工资明细信息
    * Url: salary/createSalaryDetail
    * @param String term 工资账期201808
    * @return String    
    * @throws
    * @see SalaryDetail
     */
	@AuthorityCheck(function = FunctionIds.FUNCTION_19)
	@NotCareLogin
	@Post("createSalaryDetail")
	@Get("createSalaryDetail")
	public String createSalaryDetail(
			@Param("term") String term) {
		if(StringUtils.isBlank(term)){
			logger.error("=====参数错误，不应为空=====");
			return "@" + JSONResult.error(CodeMsg.ERROR,"参数错误，不应为空！");
		}
		int salaryCount = salaryService.countSalaryDetailByTerm(term);
		if(salaryCount>0){
			logger.error("=====所选工资账期出工资表失败,工资表已经出过,不能重复出");
			return "@" + JSONResult.error(CodeMsg.ERROR,"所选工资账期出工资表失败,工资表已经出过,不能重复出");
		}	
		CheckWorkDetailCondition condition = new CheckWorkDetailCondition();
		condition.setTerm(term);
		Long checkworkCount = checkWorkService.countCheckWorkDetail(condition);
		if(checkworkCount<=0){
			logger.error("=====所选工资账期出工资表失败,本月没有考勤记录,请上传考勤信息");
			return "@" + JSONResult.error(CodeMsg.ERROR,"所选工资账期出工资表失败,本月没有考勤记录,请上传考勤信息");
		}	
		int insuranceDetailCount = salaryService.countInsuranceDetailByTerm(term);
		if(insuranceDetailCount<=0){
			logger.error("=====所选工资账期出工资表失败,本月没有社保记录,请上传社保信息");
			return "@" + JSONResult.error(CodeMsg.ERROR,"所选工资账期出工资表失败,本月没有社保记录,请上传社保信息");
		}	
		//新增需要判断参数；并且要更新基本信息
		int result = salaryService.createSalaryDetailByTerm(term);
		if (result >0) {
			return "@" + JSONResult.success();
		} else {
			logger.error("=====数据库操作异常,所选账期出工资表失败=====result="+result);
			return "@" + JSONResult.error(CodeMsg.ERROR, "数据库操作异常,所选账期出工资表失败;result="+result);
		}

	}
	
	/**
     * 
    * Title: getInsuranceDetailList
    * Description: 通过条件查询社保表明细
    * Url: salary/getInsuranceDetailList
    * @param String name, 姓名
    * @param String term,账期
    * @param String expatriateUnit,外派单位
    * @param int pageIndex, 分页页数
    * @param int pageSize 	行数
    * @return String    
    * @throws
     */
	@AuthorityCheck(function = FunctionIds.FUNCTION_19)
	@NotCareLogin
	@Get("getInsuranceDetailList")
	@Post("getInsuranceDetailList")
	public String getInsuranceDetailList(@Param("name") String name,
			@Param("term") String term,
			@Param("expatriateUnit") String expatriateUnit,
			@Param("pageIndex") int pageIndex, 
			@Param("pageSize") int pageSize) {
		InsuranceDetailCondition condition = new InsuranceDetailCondition();
			condition.setName(name);
			condition.setTerm(term);
			condition.setExpatriateUnit(expatriateUnit);
			
		pageIndex = pageIndex < 0 ? 0 : pageIndex;
		pageSize = pageSize < 1 ? 1 : pageSize;
		condition.setOffset(pageIndex * pageSize);
		condition.setLimit(pageSize);
		Long count = 0L;
		List<InsuranceDetail> salaryLists = new ArrayList<>();
		try {
			salaryLists = salaryService.listInsuranceDetail(condition);
			count = salaryService.countInsuranceDetail(condition);
		} catch (Exception e) {
			logger.error("=====通过条件查询社保表明细查询，调用service出错=====", e);
			return "@" + JSONResult.error(CodeMsg.SERVER_ERROR);
		}
		Long pageCount = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;
		Map<String, Object> dataMap = DataMapUtil.getDataMap("salaryViewList", salaryLists, count, pageCount);
		return "@" + JSONResult.success(dataMap);
	}
	
	/**
     * 
    * Title: getInsuranceDetailById
    * Description: 通过ID查询社保表明细
    * Url: salary/getInsuranceDetailById
    * @param Integer insuranceDetailId  社保表ID
    * @return String   
    * @see InsuranceDetail 
    * @throws
     */
	@AuthorityCheck(function = FunctionIds.FUNCTION_19)
	@NotCareLogin
	@Post("getInsuranceDetailById")
	@Get("getInsuranceDetailById")
	public String getInsuranceDetailById(
			@Param("insuranceDetailId")Integer insuranceDetailId) {

		InsuranceDetail insuranceDetail = salaryService.getInsuranceDetailById(insuranceDetailId);
		if (insuranceDetail != null) {
			return "@" + JSONResult.success(insuranceDetail);			
		} else {
			logger.error("=====根据ID未查到社保表信息=====");
			return "@" + JSONResult.error(CodeMsg.ERROR, "根据ID未查到社保表信息");
		}
	}
	
	/**
     * 
    * Title: updateInsuranceDetail
    * Description: 修改社保表信息
    * Url: salary/updateInsuranceDetail
    * @param String insuranceDetailJsonStr 社保表json串
    * @return String    
    * @throws
    * @see InsuranceDetail
     */
	@AuthorityCheck(function = FunctionIds.FUNCTION_10)
	@NotCareLogin
	@Post("updateInsuranceDetail")
	@Get("updateInsuranceDetail")
	public String updateInsuranceDetail(
			@Param("insuranceDetailJsonStr") String insuranceDetailJsonStr) {
		if(StringUtils.isBlank(insuranceDetailJsonStr)){
			logger.error("=====参数错误，不应为空=====");
			return "@" + JSONResult.error(CodeMsg.ERROR,"参数错误，不应为空！");
		}
		InsuranceDetail insuranceDetail = null;
		try {
			insuranceDetail = JSONObject.parseObject(insuranceDetailJsonStr, InsuranceDetail.class);
		} catch (Exception e) {
			logger.error("=====修改社保表信息，解析参数出错=====", e);
			return "@" + JSONResult.error(CodeMsg.ERROR,"解析对象出错！");
		}
		
		
		int result = salaryService.updateInsuranceDetail(insuranceDetail);
		if (result>0) {
			return "@" + JSONResult.success();
		} else if(result==-2){
			logger.error("=====没有此社保表信息=====");
			return "@" + JSONResult.error(CodeMsg.ERROR, "修改失败,没有此社保表信息");
		} else{
			logger.error("=====修改社保表信息失败,数据库保存失败=====");
			return "@" + JSONResult.error(CodeMsg.ERROR, "修改社保表信息失败,数据库保存失败");
		}

	}
	
	/**
     * 
    * Title:importInsuranceExcel
    * Description:导入社保信息excel
    * Url: salary/importInsuranceExcel
    * @param   String term,社保月份
    * @param  MultipartFile filedata,  excel文件
    * @return String    
    * @throws
     */
	@AuthorityCheck(function = FunctionIds.FUNCTION_19)
	@NotCareLogin
	@Post("importInsuranceExcel")
	public String importInsuranceExcel(@Param("term")  String term,@Param("filedata")MultipartFile filedata){
		boolean result = true;
		int insuranceDetailCount = salaryService.countInsuranceDetailByTerm(term);
		if(insuranceDetailCount>0){
			logger.error("=====上传过社保信息失败,本月已经上传过社保信息");
			return "@" + JSONResult.error(CodeMsg.ERROR,"上传过社保信息失败,本月已经上传过社保信息");
		}
		CheckWorkDetailCondition condition = new CheckWorkDetailCondition();
		condition.setTerm(term);
		Long checkworkCount= checkWorkService.countCheckWorkDetail(condition);
		if(checkworkCount<=0){
			logger.error("=====上传过社保信息失败,本月没上传考勤信息");
			return "@" + JSONResult.error(CodeMsg.ERROR,"上传过社保信息失败,本月没上传考勤信息");
		}
		Admin user = (Admin)inv.getRequest().getSession().getAttribute("user");
		long fileNumber = 0;
		try {
			/**
			 * 1.上传文件
			 */
			String filePathInServer = "";
			String separator = File.separator;
			String filepath = FILE_UPLOAD_PATH;
			java.util.Date dt = new java.util.Date();
			SimpleDateFormat fmt = new SimpleDateFormat("yyMMddHHmmssSSSS");
			String type = filedata
					.getOriginalFilename()
					.substring(
							filedata.getOriginalFilename().lastIndexOf(".") + 1)
					.toLowerCase();// 获得文件扩展名
	
			String originalname = filedata.getOriginalFilename().substring(0,
					filedata.getOriginalFilename().lastIndexOf("."));// 获得原文件名称
			String filename = originalname+"_"+ fmt.format(dt) + "." + type;// 文件原名+时间+.+文件扩展名
			File filepaths = new File(filepath);
			if (!filepaths.exists()) {
				filepaths.mkdirs();
			}
			File orgFile = new File(filepath + separator
					+ filename);
			filedata.transferTo(orgFile);// 上传文件
			filePathInServer = orgFile.getPath();
			logger.info("adminUser : "+user.getUsername()+" upload 导入社保信息excel  file wait for whether to save ");
		
			/**
			 * 2. 解析文件xls
			 */
			boolean isE2007 = false;    //判断是否是excel2007格式  
			 if(filename.endsWith("xlsx"))  
		            isE2007 = true;  
			File myfile = new File(filePathInServer);
			InputStream is = new FileInputStream(myfile);
			 Workbook wb  = null;  
	            //根据文件格式(2003或者2007)来初始化  
	            if(isE2007)  
	                wb = new XSSFWorkbook(is);  
	            else  
	                wb = new HSSFWorkbook(is); 
	            Sheet sheet= wb.getSheetAt(0);     //获得第一个表单   
	            if(sheet.getLastRowNum()>5000){
					return "@"+JSONResult.error(CodeMsg.ERROR,"数量不能超过500"); 
				}
	            Iterator<Row> rows = sheet.rowIterator(); //获得第一个表单的迭代器  
	            List<InsuranceDetail> insuranceDetailList = new ArrayList<InsuranceDetail>();
	            while (rows.hasNext()) {  
	                Row row = rows.next();  //获得行数据  
	                if(row.getRowNum()<1||row==null)
	                	continue;
	                Iterator<Cell> cells = row.cellIterator();    //获得第一行的迭代器
	                InsuranceDetail detail= new InsuranceDetail();
	                while (cells.hasNext()) {  
	                    Cell cell = cells.next();  
	                    String cellValue = "";
	                    switch (cell.getCellType()) {   //根据cell中的类型来输出数据  
	                    case HSSFCell.CELL_TYPE_NUMERIC:  
	                    	if (DateUtil.isCellDateFormatted(cell)) {
								Date d = cell.getDateCellValue(); // 对日期处理
								DateFormat formater = new SimpleDateFormat(
										"yyyy-MM-dd HH:mm:ss");
								cellValue = formater.format(d);
							} else {// 其余按照数字处理
									// 防止科学计数法
								DecimalFormat df = new DecimalFormat("0.0000");
								double acno = cell.getNumericCellValue();
								String acnoStr = df.format(acno);
								if (acnoStr.indexOf(".") > 0) {
									acnoStr = acnoStr.replaceAll("0+?$", "");// 去掉多余的0
									cellValue = acnoStr.replaceAll("[.]$", "");// 如最后一位是.则去掉
								}
							}  
	                        break;  
	                    case HSSFCell.CELL_TYPE_STRING:  
	                    	cellValue = cell.getRichStringCellValue().getString(); 
	                        break;  
	                    case HSSFCell.CELL_TYPE_BOOLEAN:  
	                    	cellValue = String.valueOf(cell.getBooleanCellValue());  
	                        break;  
	                    case HSSFCell.CELL_TYPE_BLANK:  
	                    	cellValue = "";
	                        break;  
	                    case HSSFCell.CELL_TYPE_ERROR:  
	                    	cellValue = "";
	                        break;  
	                    case HSSFCell.CELL_TYPE_FORMULA:  
	                    	cellValue = cell.getCellFormula() + "";
	                        break;  
	                    default:  
	                    	cellValue = "";
	                        break;  
	                    } 
	                    if(cell.getColumnIndex()==0&&cellValue.equals("")){
	                    	break;
	                    }
	                    /**
						 * 处理文件中定义的属性
						 */
						String transforValue="";
						switch (cell.getColumnIndex()) {
							case 0:// 序号
								break;
							case 1:// 姓名
								transforValue = String.valueOf(cellValue).trim();
								detail.setName(transforValue);
								break;
							case 2:// 起缴时间
								transforValue = String.valueOf(cellValue).trim();
								detail.setInsuranceBeginDate(transforValue);
								break;
							case 3:// 实缴月份
								transforValue = String.valueOf(cellValue).trim();
								detail.setInsuranceRealDate(transforValue);
								break;
							case 4:// 外派单位
								transforValue = String.valueOf(cellValue).trim();
								detail.setExpatriateUnit(transforValue);
								break;
							case 5:// 缴费地点
								transforValue = String.valueOf(cellValue).trim();
								detail.setInsurancePlace(transforValue);
								break;
							case 6:// 代理公司
								transforValue = String.valueOf(cellValue).trim();
								detail.setAgencyCompany(transforValue);
								break;
							case 7:// 代理费
								transforValue = String.valueOf(cellValue).trim();
								if(!transforValue.equals("")&&!transforValue.equals("年结")){
									detail.setAgencyPay(BigDecimal.valueOf(Double.parseDouble(transforValue)));
								}
								else{
								  detail.setAgencyPay(BigDecimal.ZERO);
								}
								break;
							case 8:// 养老基数
								transforValue = String.valueOf(cellValue).trim();
								detail.setEndowmentBase(BigDecimal.valueOf(Double.parseDouble(transforValue)));
								break;
							case 9:// 养老单位比例
								transforValue = String.valueOf(cellValue).trim();
								detail.setEndowmentRate(BigDecimal.valueOf(Double.parseDouble(transforValue)).setScale(4,BigDecimal.ROUND_HALF_UP));
								break;
							case 10:// 养老个人比例
								transforValue = String.valueOf(cellValue).trim();
								detail.setEndowmentRatePersonal(BigDecimal.valueOf(Double.parseDouble(transforValue)).setScale(4,BigDecimal.ROUND_HALF_UP));
								break;
							case 11:// 失业基数 
								transforValue = String.valueOf(cellValue).trim();
								detail.setUnemploymentBase(BigDecimal.valueOf(Double.parseDouble(transforValue)));
								break;
							case 12://失业单位比例 
								transforValue = String.valueOf(cellValue).trim();
								detail.setUnemploymentRate(BigDecimal.valueOf(Double.parseDouble(transforValue)).setScale(4,BigDecimal.ROUND_HALF_UP));
								break;
							case 13:// 失业个人比例
								transforValue = String.valueOf(cellValue).trim();
								detail.setUnemploymentRatePersonal(BigDecimal.valueOf(Double.parseDouble(transforValue)).setScale(4,BigDecimal.ROUND_HALF_UP));
								break;
							case 14:// 工伤基数
								transforValue = String.valueOf(cellValue).trim();
								detail.setWorkInjuryBase(BigDecimal.valueOf(Double.parseDouble(transforValue)));
								break;
							case 15:// 工伤单位比例
								transforValue = String.valueOf(cellValue).trim();
								detail.setWorkInjuryRate(BigDecimal.valueOf(Double.parseDouble(transforValue)).setScale(4,BigDecimal.ROUND_HALF_UP));
								break;
							case 16:// 医疗基数
								transforValue = String.valueOf(cellValue).trim();
								detail.setMedicalBase(BigDecimal.valueOf(Double.parseDouble(transforValue)));
								break;
							case 17:// 医疗单位比例
								transforValue = String.valueOf(cellValue).trim();
								detail.setMedicalRate(BigDecimal.valueOf(Double.parseDouble(transforValue)).setScale(4,BigDecimal.ROUND_HALF_UP));
								break;
							case 18:// 医疗个人比例
								transforValue = String.valueOf(cellValue).trim();
								detail.setMedicalRatePersonal(BigDecimal.valueOf(Double.parseDouble(transforValue)).setScale(4,BigDecimal.ROUND_HALF_UP));
								break;
							case 19:// 生育基数
								transforValue = String.valueOf(cellValue).trim();
								detail.setBirthBase(BigDecimal.valueOf(Double.parseDouble(transforValue)));
								break;
							case 20:// 生育单位比例
								transforValue = String.valueOf(cellValue).trim();
								detail.setBirthRate(BigDecimal.valueOf(Double.parseDouble(transforValue)).setScale(4,BigDecimal.ROUND_HALF_UP));
								break;
							case 21:// 大病/残保基数
								transforValue = String.valueOf(cellValue).trim();
								detail.setSickBase(BigDecimal.valueOf(Double.parseDouble(transforValue)));
								break;
							case 22:// 大病/残保单位比例
								transforValue = String.valueOf(cellValue).trim();
								detail.setSickRate(BigDecimal.valueOf(Double.parseDouble(transforValue)).setScale(4,BigDecimal.ROUND_HALF_UP));
								break;
							case 23:// 大病/残保个人比例
								transforValue = String.valueOf(cellValue).trim();
								detail.setSickRatePersonal(BigDecimal.valueOf(Double.parseDouble(transforValue)).setScale(4,BigDecimal.ROUND_HALF_UP));
								break;
							case 24:// 住房公积金基数
								transforValue = String.valueOf(cellValue).trim();
								detail.setHousingBase(BigDecimal.valueOf(Double.parseDouble(transforValue)));
								break;
							case 25:// 住房公积金单位比例
								transforValue = String.valueOf(cellValue).trim();
								detail.setHousingRate(BigDecimal.valueOf(Double.parseDouble(transforValue)));
								break;
							case 26:// 住房公积金个人比例
								transforValue = String.valueOf(cellValue).trim();
								detail.setHousingRatePersonal(BigDecimal.valueOf(Double.parseDouble(transforValue)).setScale(4,BigDecimal.ROUND_HALF_UP));
								break;
						}
	                }  
	                //验证员工信息是否存在
	                PersonalInfo person = personalService.getPersonalByName(detail.getName());
	                if(person==null){
	                	logger.error("====="+String.format("员工信息不存在,姓名:%s", detail.getName())+"=====");
	                	return "@"+JSONResult.error(CodeMsg.ERROR,String.format("员工信息不存在,姓名:%s", detail.getName())); 
	                }
	                detail.setPersonalInfoId(person.getId());
	                detail.setTerm(term.trim());
	                SimpleDateFormat sdt=new SimpleDateFormat("yyyyMM");
					java.util.Date startDate=sdt.parse(String.valueOf(term).trim());
					detail.setStartDate(startDate);
			        Calendar endDate = Calendar.getInstance();  
			        endDate.setTime(startDate);  
			        endDate.add(Calendar.MONTH, 1);  
			        detail.setEndDate(endDate.getTime());
	                detail.setIsDel(1);
	                detail.setCreateTime(new Date());
	                insuranceDetailList.add(detail);              
			}
	        //批量入库
	        try {
	        	salaryService.saveInsuranceDetailListRecord(insuranceDetailList);
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
				logger.error("保存数据库异常,已回滚", e);
				return "@"+JSONResult.error(CodeMsg.ERROR,"保存数据库异常,已回滚"+ e.getMessage());  
			}
	        
		}catch(Exception e1){
			e1.printStackTrace();
			logger.error("upload 导入社保信息 throws Exception", e1);
			return "@"+JSONResult.error(CodeMsg.ERROR,"导入社保信息失败，请稍后重试"+e1);  
		}
		if(result){
			logger.info("adminUser : "+user.getUsername()+"upload 导入社保信息 ; fileNumber : OT"+fileNumber);
			return "@"+JSONResult.success("导入成功！文件编号为OT"+fileNumber);
		}else{
			logger.error("=====导入失败！请重新导入=====");
			return "@"+JSONResult.error(CodeMsg.ERROR,"导入失败！请重新导入");  
		}
		
	}
	
	/**
     * 
    * Title: exportProfitDetailList
    * Description: 通过条件导出利润测算表明细
    * Url: salary/exportProfitDetailList
    * @param String name, 姓名
    * @param String term,账期
    * @return String    
    * @see ProfitDetail
    * @throws
     */
	@AuthorityCheck(function = FunctionIds.FUNCTION_19)
	@NotCareLogin
	@Get("exportProfitDetailList")
	@Post("exportProfitDetailList")
	public String exportProfitDetailList(@Param("name") String name,
			@Param("term") String term) {
		ProfitDetailCondition condition = new ProfitDetailCondition();
			condition.setName(name);
			condition.setTerm(term);
			
		
		List<ProfitDetail> profitLists = new ArrayList<>();
		try {
			profitLists = salaryService.listProfitDetail(condition);
		} catch (Exception e) {
			logger.error("=====通过条件查询利润测算表明细查询，调用service出错=====", e);
			return "@" + JSONResult.error(CodeMsg.SERVER_ERROR);
		}
		//根据查询条件和字段导出到excel
				Admin admin = (Admin)inv.getRequest().getSession().getAttribute("user");
				String dateStr = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
				String fileName = "ProfitListExport-" + dateStr + ".xlsx";
				HttpServletResponse response = inv.getResponse();
				response.setCharacterEncoding("UTF-8");
				response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
				response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");//xlsx
//				response.setContentType("application/vnd.ms-excel");//xls
				String heads = "";
				String columns = "";
				List<String> listHeads = StringToListUtil.getStringList(heads, ",");
				List<String> listColumns = StringToListUtil.getStringList(columns, ",");
				OutputStream out = null;
				try {
					out = response.getOutputStream();
					ExportBeanExcel<ProfitDetail> exportBeanExcelUtil = new ExportBeanExcel();
					exportBeanExcelUtil.exportExcel("利润测算表列表",listHeads,listColumns,profitLists,out);
				} catch (IOException e) {
					e.printStackTrace();
					logger.error(admin.getRealname() + " 操作导出利润测算表列表文件出错", e);
					e.printStackTrace();
					return "@" + JSONResult.error(CodeMsg.SERVER_ERROR);
				} finally {
					try {
						out.flush();
						out.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				return null;	
	}
	
	
	/**
     * 
    * Title: getProfitDetailList
    * Description: 通过条件查询利润测算表明细
    * Url: salary/getProfitDetailList
    * @param String name, 姓名
    * @param String term,账期
    * @param String expatriateUnit,外派单位
    * @param int pageIndex, 分页页数
    * @param int pageSize 	行数
    * @return String    
    * @see ProfitDetail
    * @throws
     */
	@AuthorityCheck(function = FunctionIds.FUNCTION_19)
	@NotCareLogin
	@Get("getProfitDetailList")
	@Post("getProfitDetailList")
	public String getProfitDetailList(@Param("name") String name,
			@Param("term") String term,
			@Param("pageIndex") int pageIndex, 
			@Param("pageSize") int pageSize) {
		ProfitDetailCondition condition = new ProfitDetailCondition();
			condition.setName(name);
			condition.setTerm(term);
			
		pageIndex = pageIndex < 0 ? 0 : pageIndex;
		pageSize = pageSize < 1 ? 1 : pageSize;
		condition.setOffset(pageIndex * pageSize);
		condition.setLimit(pageSize);
		Long count = 0L;
		List<ProfitDetail> profitLists = new ArrayList<>();
		try {
			profitLists = salaryService.listProfitDetail(condition);
			count = salaryService.countProfitDetail(condition);
		} catch (Exception e) {
			logger.error("=====通过条件查询利润测算表明细查询，调用service出错=====", e);
			return "@" + JSONResult.error(CodeMsg.SERVER_ERROR);
		}
		Long pageCount = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;
		Map<String, Object> dataMap = DataMapUtil.getDataMap("profitViewList", profitLists, count, pageCount);
		return "@" + JSONResult.success(dataMap);
	}
	
	/**
     * 
    * Title: createProfitDetail
    * Description: 按月新增利润测算表明细信息
    * Url: salary/createProfitDetail
    * @param String term 工资账期201808
    * @return String    
    * @throws
    * @see ProfitDetail
     */
	@AuthorityCheck(function = FunctionIds.FUNCTION_19)
	@NotCareLogin
	@Post("createProfitDetail")
	@Get("createProfitDetail")
	public String createProfitDetail(
			@Param("term") String term) {
		if(StringUtils.isBlank(term)){
			logger.error("=====参数错误，不应为空=====");
			return "@" + JSONResult.error(CodeMsg.ERROR,"参数错误，不应为空！");
		}
		int salaryCount = salaryService.countSalaryDetailByTerm(term);
		if(salaryCount<=0){
			logger.error("=====所选月份出利润测算表失败,此月份工资表还未出过,不能出利润测算");
			return "@" + JSONResult.error(CodeMsg.ERROR,"所选月份出利润测算表失败,此月份工资表还未出过,不能出利润测算");
		}	
		//新增需要判断参数；
		int result = salaryService.createProfitDetailByTerm(term);
		if (result >0) {
			return "@" + JSONResult.success();
		} else {
			logger.error("=====数据库操作异常,所选账期出利润测算表失败=====result="+result);
			return "@" + JSONResult.error(CodeMsg.ERROR, "数据库操作异常,所选账期出利润测算表失败;result="+result);
		}

	}
	
	
}
