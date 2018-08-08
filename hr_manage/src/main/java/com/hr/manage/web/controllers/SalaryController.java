package com.hr.manage.web.controllers;

import hr.manage.component.admin.model.Admin;
import hr.manage.component.admin.service.AdminService;
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
import hr.manage.component.salary.model.SalaryChange;
import hr.manage.component.salary.model.SalaryChangeCondition;
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
	@AuthorityCheck(function = FunctionIds.FUNCTION_13)
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
	@AuthorityCheck(function = FunctionIds.FUNCTION_13)
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
		if(!salaryChange.getFinalSalary().equals(salaryChange.getChangeRange()+personalAll.getPersonalSalaryInfo().getWorkerPay())){
			logger.error("=====新增工资调整信息失败，调薪幅度加上原来的转正工资不等于填写的调薪后工资;原有转正工资为:"+personalAll.getPersonalSalaryInfo().getWorkerPay());
			return "@" + JSONResult.error(CodeMsg.ERROR,"新增工资调整信息失败，调薪幅度加上原来的转正工资不等于填写的调薪后工资;原有转正工资为:"+personalAll.getPersonalSalaryInfo().getWorkerPay());
	    }
		Admin user = (Admin)inv.getRequest().getSession().getAttribute("user");
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
	
	
	
}
