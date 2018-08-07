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
* @see 合同相关信息相关API;
* @see 统一返回JSON串 code,message,data
* @author  lee
* @version 1.0
 */
@Path("contract")
public class ContractController {

	@Autowired
	Invocation inv;
	@Autowired
	ContractService contractService;
	@Autowired
	PersonalService personalService;
	@Autowired
	AdminService adminService;
	private final Log logger = LogFactory.getLog(ContractController.class);
	
	
	
	/**
     * 
    * Title: getList
    * Description: 根据条件获取员工合同信息列表
    * Url: contract/getList
    * @param String name, 姓名
    * @param String employeeNumber,员工编号
    * @param String startDate,合同结束时间查询的开始时间
    * @param String endDate,合同结束时间查询的截止时间
    * @param int pageIndex, 分页页数
    * @param int pageSize 	行数
    * @return String    
    * @throws
     */
	@AuthorityCheck(function = FunctionIds.FUNCTION_13)
	@NotCareLogin
	@Get("getList")
	@Post("getList")
	public String getPersonalAllList(@Param("name") String name,
			@Param("employeeNumber") String employeeNumber,
			@Param("startDate") String startDate,
			@Param("endDate") String endDate,
			@Param("pageIndex") int pageIndex, 
			@Param("pageSize") int pageSize) {
		ContractCondition condition = new ContractCondition();
//		if(StringUtils.isNotBlank(name)){
//			// 当查询条件有姓名时，只需要根据姓名查出该单，其他条件忽略
//			condition.setName(name);
//		} else {
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
			
//		}
		pageIndex = pageIndex < 0 ? 0 : pageIndex;
		pageSize = pageSize < 1 ? 1 : pageSize;
//		condition.setOrderby("createtime");
		condition.setOffset(pageIndex * pageSize);
		condition.setLimit(pageSize);
		Long count = 0L;
		List<ContractInfo> contractLists = new ArrayList<>();
		try {
			contractLists = contractService.listContractInfo(condition);
			count = contractService.countContractInfo(condition);
		} catch (Exception e) {
			logger.error("=====根据条件获取合同信息列表查询，调用service出错=====", e);
			return "@" + JSONResult.error(CodeMsg.SERVER_ERROR);
		}
		Long pageCount = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;
		Map<String, Object> dataMap = DataMapUtil.getDataMap("contractViewList", contractLists, count, pageCount);
		return "@" + JSONResult.success(dataMap);
	}
	
	/**
     * 
    * Title: addContractInfo
    * Description: 新增合同基本信息
    * Url: contract/addContractInfo
    * @param String contractInfoJsonStr 合同信息json串
    * @return String    
    * @throws
    * @see ContractInfo
     */
	@AuthorityCheck(function = FunctionIds.FUNCTION_13)
	@NotCareLogin
	@Post("addContractInfo")
	@Get("addContractInfo")
	public String addContractInfo(
			@Param("contractInfoJsonStr") String contractInfoJsonStr) {
		if(StringUtils.isBlank(contractInfoJsonStr)){
			logger.error("=====参数错误，不应为空=====");
			return "@" + JSONResult.error(CodeMsg.ERROR,"参数错误，不应为空！");
		}
		ContractInfo contractInfo = null;
		try {
			contractInfo = JSONObject.parseObject(contractInfoJsonStr, ContractInfo.class);
		} catch (Exception e) {
			logger.error("=====新增合同基本信息，解析参数出错=====", e);
			return "@" + JSONResult.error(CodeMsg.ERROR,"解析对象出错！");
		}
		if(contractInfo.getContractCount()<2){
			logger.error("=====新增合同基本信息失败，合同签订次数不能小于2");
			return "@" + JSONResult.error(CodeMsg.ERROR,"新增合同基本信息失败，合同签订次数不能小于2");
		}
		if(contractInfo.getPersonalInfoId()==null || StringUtils.isBlank(contractInfo.getEmployeeNumber())){
			logger.error("=====新增合同基本信息失败，员工信息ID和员工编号不能为空");
			return "@" + JSONResult.error(CodeMsg.ERROR,"新增合同基本信息失败，员工信息ID和员工编号不能为空");
		}
		//验证修改的合同签订次数是否递增；
		int curContractCount  = contractService.countContractInfoByPersonalId(contractInfo.getPersonalInfoId());
		if(!contractInfo.getContractCount().equals(curContractCount+1)){
			logger.error("=====新增合同基本信息失败，合同签订次数不是递增的;curContractCount:"+curContractCount);
			return "@" + JSONResult.error(CodeMsg.ERROR,"新增合同基本信息失败，合同签订次数不是递增;curContractCount:"+curContractCount);
				
		}
		String strContractCount = new DecimalFormat("00").format(contractInfo.getContractCount());
		contractInfo.setContractNumber(contractInfo.getEmployeeNumber()+strContractCount);
		//新增需要判断参数；并且要更新基本信息
		int result = contractService.addContractInfo(contractInfo);
		if (result >0) {
			return "@" + JSONResult.success();
		} else {
			logger.error("=====数据库操作异常,新增失败=====result="+result);
			return "@" + JSONResult.error(CodeMsg.ERROR, "数据库操作异常,新增失败;result="+result);
		}

	}
	
	
	/**
     * 
    * Title: updateContractInfo
    * Description: 修改合同基本信息
    * Url: contract/updateContractInfo
    * @param String contractInfoJsonStr 合同信息json串
    * @return String    
    * @throws
    * @see ContractInfo
     */
	@AuthorityCheck(function = FunctionIds.FUNCTION_13)
	@NotCareLogin
	@Post("updateContractInfo")
	@Get("updateContractInfo")
	public String updateContractInfo(
			@Param("contractInfoJsonStr") String contractInfoJsonStr) {
		if(StringUtils.isBlank(contractInfoJsonStr)){
			logger.error("=====参数错误，不应为空=====");
			return "@" + JSONResult.error(CodeMsg.ERROR,"参数错误，不应为空！");
		}
		ContractInfo contractInfo = null;
		try {
			contractInfo = JSONObject.parseObject(contractInfoJsonStr, ContractInfo.class);
		} catch (Exception e) {
			logger.error("=====修改合同基本信息，解析参数出错=====", e);
			return "@" + JSONResult.error(CodeMsg.ERROR,"解析对象出错！");
		}
//		if(contractInfo.getContractCount()<2){
//			logger.error("=====修改合同基本信息失败，合同签订次数不能小于2");
//			return "@" + JSONResult.error(CodeMsg.ERROR,"新增合同基本信息失败，合同签订次数不能小于2");
//		}
		if(contractInfo.getPersonalInfoId()==null || StringUtils.isBlank(contractInfo.getEmployeeNumber())){
			logger.error("=====修改合同基本信息失败，员工信息ID和员工编号不能为空");
			return "@" + JSONResult.error(CodeMsg.ERROR,"新增合同基本信息失败，员工信息ID和员工编号不能为空");
		}
		//验证修改的合同ID是否是最新的一条合同；
		int maxContractCount  = contractService.getMaxContractCountById(contractInfo.getPersonalInfoId());
		if(!contractInfo.getId().equals(maxContractCount)){
			logger.error("=====修改合同基本信息失败，修改的合同ID不是此员工最新的合同");
			return "@" + JSONResult.error(CodeMsg.ERROR,"修改合同基本信息失败，修改的合同ID不是此员工最新的合同");
		
		}
		//验证修改的合同签订次数是否相等；
		int curContractCount  = contractService.countContractInfoByPersonalId(contractInfo.getPersonalInfoId());
		if(!contractInfo.getContractCount().equals(curContractCount)){
			logger.error("=====修改合同基本信息失败，修改了合同签订次数;curContractCount:"+curContractCount);
			return "@" + JSONResult.error(CodeMsg.ERROR,"修改合同基本信息失败，修改的合同签订次数;curContractCount:"+curContractCount);
		
		}
		
		String strContractCount = new DecimalFormat("00").format(contractInfo.getContractCount());
		contractInfo.setContractNumber(contractInfo.getEmployeeNumber()+strContractCount);
		
		// 进行修改
		int result  = contractService.updateContractInfo(contractInfo);
		if (result>0) {
			return "@" + JSONResult.success();
		} else {
			logger.error("=====修改合同信息数据库异常=====result="+result);
			return "@" + JSONResult.error(CodeMsg.ERROR, "修改合同信息数据库异常result="+result);
		}

	}
	
	
	/**
     * 
    * Title: deleteContractInfo
    * Description: 删除合同信息
    * Url: contract/contractInfoId
    * @param Integer contractInfoId  合同ID
    * @return String    
    * @throws
     */
	@AuthorityCheck(function = FunctionIds.FUNCTION_13)
	@NotCareLogin
	@Post("deleteContractInfo")
	@Get("deleteContractInfo")
	public String deleteContractInfo(
			@Param("contractInfoId")Integer contractInfoId) {

		ContractInfo contractInfo = contractService.getContractInfoById(contractInfoId);
		if (contractInfo != null) {
			//查找是否有员工信息，如果有禁止删除；
			PersonalAll personalAll = personalService.getPersonalAllInfoById(contractInfo.getPersonalInfoId());
			if(personalAll.getPersonalInfo()!=null){
				logger.error("=====要删除合同的员工基本信息仍存在,请先删除员工信息=====");
				return "@" + JSONResult.error(CodeMsg.ERROR, "要删除合同的员工基本信息仍存在,请先删除员工信息");
			}
			// 进行逻辑删除
			try {
				int result = contractService.deleteContractInfoById(contractInfoId);
				return "@" + JSONResult.success();
				
			} catch (Exception e) {
				logger.error("=====删除合同信息异常====="+e);
				return "@" + JSONResult.error(CodeMsg.ERROR, "删除合同信息异常,"+e.getMessage());
			}	
		} else {
			logger.error("=====根据合同ID未查到基本信息=====");
			return "@" + JSONResult.error(CodeMsg.ERROR, "根据合同ID未查到基本信息");
		}
	}
	
	
}
