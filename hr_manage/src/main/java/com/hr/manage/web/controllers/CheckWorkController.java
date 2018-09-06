package com.hr.manage.web.controllers;

import hr.manage.component.admin.model.Admin;
import hr.manage.component.admin.service.AdminService;
import hr.manage.component.checkwork.model.CheckWorkCurrent;
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
* @see 考勤相关信息相关API;
* @see 统一返回JSON串 code,message,data
* @author  lee
* @version 1.0
 */
@Path("checkwork")
public class CheckWorkController {

	@Autowired
	Invocation inv;
	@Autowired
	CheckWorkService checkWorkService;
	@Autowired
	PersonalService personalService;
	private final Log logger = LogFactory.getLog(CheckWorkController.class);
	private static final String FILE_UPLOAD_PATH=ServiceConfigFactory.getValue("file.upload.path");
	
	
	
	/**
     * 
    * Title: getQtWlwList
    * Description: 根据条件获取全通物联网信息列表
    * Url: checkwork/getQtWlwList
    * @param String name, 姓名
    * @param String term,考勤月份
    * @param String expatriateUnit,外派单位
    * @param int pageIndex, 分页页数
    * @param int pageSize 	行数
    * @return String    
    * @throws
     */
	@AuthorityCheck(function = FunctionIds.FUNCTION_20)
	@NotCareLogin
	@Post("getQtWlwList")
	public String getQtWlwList(@Param("name") String name,
			@Param("term")  String term,
			@Param("expatriateUnit")  String expatriateUnit,
			@Param("pageIndex") int pageIndex, 
			@Param("pageSize") int pageSize) {
		CheckWorkDetailCondition condition = new CheckWorkDetailCondition();
			condition.setName(name);
			condition.setTerm(term);
			condition.setExpatriateUnit(expatriateUnit);
			
		pageIndex = pageIndex < 0 ? 0 : pageIndex;
		pageSize = pageSize < 1 ? 1 : pageSize;
		condition.setOffset(pageIndex * pageSize);
		condition.setLimit(pageSize);
		Long count = 0L;
		List<CheckWorkDetail> checkWorkDetailLists = new ArrayList<>();
		try {
			checkWorkDetailLists = checkWorkService.listCheckWorkDetail(condition);
			count = checkWorkService.countCheckWorkDetail(condition);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("=====根据条件获取全通物联网信息列表查询，调用service出错=====", e);
			return "@" + JSONResult.error(CodeMsg.SERVER_ERROR);
		}
		Long pageCount = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;
		Map<String, Object> dataMap = DataMapUtil.getDataMap("checkWorkDetailLists", checkWorkDetailLists, count, pageCount);
		return "@" + JSONResult.success(dataMap);
	}
	
	

	/**
     * 
    * Title: getCheckWorkDetailById
    * Description: 通过ID获取当月考勤信息
    * Url: checkwork/getCheckWorkDetailById
    * @param Integer detailId  考勤表ID
    * @return String    
    * @throws
     */
	@AuthorityCheck(function = FunctionIds.FUNCTION_13)
	@NotCareLogin
	@Post("getCheckWorkDetailById")
	@Get("getCheckWorkDetailById")
	public String getCheckWorkDetailById(
			@Param("detailId")Integer detailId) {
		
		CheckWorkDetail detailInfo= checkWorkService.getCheckWorkDetailById(detailId);
		if (detailInfo != null) {
			return "@" + JSONResult.success(detailInfo);			
		} else {
			logger.error("=====根据ID未查到考勤信息=====");
			return "@" + JSONResult.error(CodeMsg.ERROR, "根据ID未查到考勤信息");
		}
	}

	/**
     * 
    * Title: updateCheckWorkDetail
    * Description: 修改考勤信息
    * Url: checkwork/updateCheckWorkDetail
    * @param String detailInfoJsonStr 考勤信息json串
    * @return String    
    * @throws
    * @see CheckWorkDetail
     */
	@AuthorityCheck(function = FunctionIds.FUNCTION_13)
	@NotCareLogin
	@Post("updateCheckWorkDetail")
	@Get("updateCheckWorkDetail")
	public String updateCheckWorkDetail(
			@Param("detailInfoJsonStr") String detailInfoJsonStr) {
		if(StringUtils.isBlank(detailInfoJsonStr)){
			logger.error("=====参数错误，不应为空=====");
			return "@" + JSONResult.error(CodeMsg.ERROR,"参数错误，不应为空！");
		}
		CheckWorkDetail detailInfo = null;
		try {
			detailInfo = JSONObject.parseObject(detailInfoJsonStr, CheckWorkDetail.class);
		} catch (Exception e) {
			logger.error("=====修改考勤信息，解析参数出错=====", e);
			return "@" + JSONResult.error(CodeMsg.ERROR,"解析对象出错！");
		}
		CheckWorkDetail detail= checkWorkService.getCheckWorkDetailById(detailInfo.getId());
		if (detail == null) {
			logger.error("=====根据ID未查到考勤信息=====");
			return "@" + JSONResult.error(CodeMsg.ERROR, "根据ID未查到考勤信息");
		} 
//		current.setSurplusOvertimeHours(currentInfo.getSurplusOvertimeHours());
//		current.setAnnualLeaveDays(currentInfo.getAnnualLeaveDays());
//		current.setSurplusAnnualLeave(currentInfo.getSurplusAnnualLeave());
//		current.setSickLeaveDays(currentInfo.getSickLeaveDays());
//		current.setCompassionateLeaveDays(currentInfo.getCompassionateLeaveDays());
		detail.setSettlementDays(detailInfo.getSettlementDays());
		detail.setUpdateTime(new Date());
		// 进行修改
		int result  = checkWorkService.updateCheckWorkDetail(detail);
		if (result>0) {
			return "@" + JSONResult.success();
		} else {
			logger.error("=====修改考勤信息数据库异常=====result="+result);
			return "@" + JSONResult.error(CodeMsg.ERROR, "修改勤信信息数据库异常result="+result);
		}
	}
	
	
	/**
     * 
    * Title:importQtWlwExcel
    * Description:导入全通物联网考勤信息excel
    * Url: checkwork/importQtWlwExcel
    * @param   String term,考勤月份
    * @param  MultipartFile filedata,  excel文件
    * @return String    
    * @throws
     */
	@AuthorityCheck(function = FunctionIds.FUNCTION_12)
	@NotCareLogin
	@Post("importQtWlwExcel")
	public String importQtWlwExcel(@Param("term")  String term,@Param("filedata")MultipartFile filedata){
		boolean result = true;
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
			logger.info("adminUser : "+user.getUsername()+" upload 导入全通物联网考勤信息excel  file wait for whether to save ");
		
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
	            List<CheckWorkDetail> checkWorkDetailList = new ArrayList<CheckWorkDetail>();
	            while (rows.hasNext()) {  
	                Row row = rows.next();  //获得行数据  
	                if(row.getRowNum()<4||row==null)
	                	continue;
	                Iterator<Cell> cells = row.cellIterator();    //获得第一行的迭代器
	                CheckWorkDetail detail= new CheckWorkDetail();
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
								DecimalFormat df = new DecimalFormat("0.000");
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
							case 1:// 合作厂家
								transforValue = String.valueOf(cellValue).trim();
								detail.setManufacturer(transforValue);
								break;
							case 2:// 姓名
								transforValue = String.valueOf(cellValue).trim();
								detail.setName(transforValue);
								break;
							case 3:// 外派单位
								transforValue = String.valueOf(cellValue).trim();
								detail.setExpatriateUnit(transforValue);
								break;
							case 4:// 入职时间 
								transforValue = String.valueOf(cellValue).trim();
								if(!transforValue.equals("")){
									SimpleDateFormat sdt=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//小写的mm表示的是分钟  
									java.util.Date entryrdate=sdt.parse(String.valueOf(cellValue).trim());
									detail.setEntryTime(entryrdate);
								}
								break;
							case 5:// 出勤天数
								transforValue = String.valueOf(cellValue).trim();
								detail.setAttendanceDays(BigDecimal.valueOf(Double.parseDouble(transforValue)));
								break;
							case 6:// 考勤天数
								transforValue = String.valueOf(cellValue).trim();
								detail.setCheckWorkDays(BigDecimal.valueOf(Double.parseDouble(transforValue)));
								break;
							case 7:// 加班天数
								transforValue = String.valueOf(cellValue).trim();
								detail.setOvertimeDays(BigDecimal.valueOf(Double.parseDouble(transforValue)));
								break;
							case 8:// 请假天数
								transforValue = String.valueOf(cellValue).trim();
								detail.setLeaveDays(BigDecimal.valueOf(Double.parseDouble(transforValue)));
								break;
							case 9:// 负责人
								transforValue = String.valueOf(cellValue).trim();
								detail.setManager(transforValue);
								break;
							case 10:// 备注
								transforValue = String.valueOf(cellValue).trim();
								detail.setMemo(transforValue);
								break;
							case 11:// 剩余加班小时数 
								transforValue = String.valueOf(cellValue).trim();
								detail.setSurplusOvertimeHours(Integer.parseInt(transforValue));
								break;
							case 12://可休年假天数 
								transforValue = String.valueOf(cellValue).trim();
								detail.setAnnualLeaveDays(BigDecimal.valueOf(Double.parseDouble(transforValue)));
								break;
							case 13:// 剩余年休天数
								transforValue = String.valueOf(cellValue).trim();
								detail.setSurplusAnnualLeave(BigDecimal.valueOf(Double.parseDouble(transforValue)));
								break;
							case 14:// 累计长期病假天数
								transforValue = String.valueOf(cellValue).trim();
								detail.setSickLeaveDays(BigDecimal.valueOf(Double.parseDouble(transforValue)));
								break;
							case 15:// 累计长期事假天数
								transforValue = String.valueOf(cellValue).trim();
								detail.setCompassionateLeaveDays(BigDecimal.valueOf(Double.parseDouble(transforValue)));
								break;
						}
	                }  
	                //验证员工信息是否存在
	                PersonalInfo person = personalService.getPersonalByName(detail.getName());
	                if(person==null){
	                	logger.error("====="+String.format("员工信息不存在,姓名:%s", detail.getName())+"=====");
	                	return "@"+JSONResult.error(CodeMsg.ERROR,String.format("员工信息不存在,姓名:%s", detail.getName())); 
	                }
//	                //验证该员工是否有加班和年假表数据,没有的话需要初始化
//	                CheckWorkCurrent checkWorkCurrent = checkWorkService.getCheckWorkCurrentByName(detail.getName());
//	                if(checkWorkCurrent == null){
//	                	logger.error("====="+String.format("员工加班及年休假信息不存在,请进行初始化,姓名:%s", detail.getName())+"=====");
//	                	return "@"+JSONResult.error(CodeMsg.ERROR,String.format("员工加班及年休假信息不存在,请进行初始化,姓名:%s", detail.getName())); 
//	                }
//	                detail.setCheckWorkCurrent(checkWorkCurrent);
	                detail.setPersonalInfo(person);
	                detail.setTerm(term.trim());
	                SimpleDateFormat sdt=new SimpleDateFormat("yyyy-MM");
					java.util.Date startDate=sdt.parse(String.valueOf(term).trim());
					detail.setStartDate(startDate);
			        Calendar endDate = Calendar.getInstance();  
			        endDate.setTime(startDate);  
			        endDate.add(Calendar.MONTH, 1);  
			        detail.setEndDate(endDate.getTime());
			        detail.setSettlementDays(BigDecimal.ZERO);
	                detail.setIsDel(1);
	                detail.setCreateTime(new Date());
	                checkWorkDetailList.add(detail);              
			}
	        //批量入库
	        try {
	        	checkWorkService.saveCheckWorkDetailListRecord(checkWorkDetailList);
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
				logger.error("保存数据库异常,已回滚", e);
				return "@"+JSONResult.error(CodeMsg.ERROR,"保存数据库异常,已回滚"+ e.getMessage());  
			}
	        
		}catch(Exception e1){
			e1.printStackTrace();
			logger.error("upload 导入全通物联网考勤信息 throws Exception", e1);
			return "@"+JSONResult.error(CodeMsg.ERROR,"导入全通物联网考勤信息失败，请稍后重试"+e1);  
		}
		if(result){
			logger.info("adminUser : "+user.getUsername()+"upload 导入全通物联网考勤信息 ; fileNumber : OT"+fileNumber);
			return "@"+JSONResult.success("导入成功！文件编号为OT"+fileNumber);
		}else{
			logger.error("=====导入失败！请重新导入=====");
			return "@"+JSONResult.error(CodeMsg.ERROR,"导入失败！请重新导入");  
		}
		
	}
	
	

	/**
     * 
    * Title: getCurrentList
    * Description: 条件查询全通物联网人员加班及年假信息列表
    * Url: checkwork/getCurrentList
    * @param String name, 姓名
    * @param String term, 年度
    * @param int pageIndex, 分页页数
    * @param int pageSize 	行数
    * @return String    
    * @throws
     */
	@AuthorityCheck(function = FunctionIds.FUNCTION_20)
	@NotCareLogin
	@Post("getCurrentList")
	public String getCurrentList(@Param("name") String name,
			@Param("term")  String term,
			@Param("pageIndex") int pageIndex, 
			@Param("pageSize") int pageSize) {
		CheckWorkDetailCondition condition = new CheckWorkDetailCondition();
			condition.setName(name);
			condition.setTerm(term);
			
		pageIndex = pageIndex < 0 ? 0 : pageIndex;
		pageSize = pageSize < 1 ? 1 : pageSize;
		condition.setOffset(pageIndex * pageSize);
		condition.setLimit(pageSize);
		Long count = 0L;
		List<CheckWorkCurrent> checkWorkCurrentLists = new ArrayList<>();
		try {
			checkWorkCurrentLists = checkWorkService.listCheckWorkCurrent(condition);
			count = checkWorkService.countCheckWorkCurrent(condition);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("=====根据条件查询全通物联网人员加班及年假信息列表查询，调用service出错=====", e);
			return "@" + JSONResult.error(CodeMsg.SERVER_ERROR);
		}
		Long pageCount = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;
		Map<String, Object> dataMap = DataMapUtil.getDataMap("checkWorkCurrentLists", checkWorkCurrentLists, count, pageCount);
		return "@" + JSONResult.success(dataMap);
	}
	

	/**
     * 
    * Title: getCheckWorkCurrentById
    * Description: 通过ID获取年假及加班当前信息
    * Url: checkwork/getCheckWorkCurrentById
    * @param Integer currentId  年假及加班当前信息ID
    * @return String    
    * @throws
     */
	@AuthorityCheck(function = FunctionIds.FUNCTION_13)
	@NotCareLogin
	@Post("getCheckWorkCurrentById")
	@Get("getCheckWorkCurrentById")
	public String getCheckWorkCurrentById(
			@Param("currentId")Integer currentId) {
		
		CheckWorkCurrent currentInfo= checkWorkService.getCheckWorkCurrentById(currentId);
		if (currentInfo != null) {
			return "@" + JSONResult.success(currentInfo);			
		} else {
			logger.error("=====根据ID未查到基本信息=====");
			return "@" + JSONResult.error(CodeMsg.ERROR, "根据ID未查到基本信息");
		}
	}
	

	
	/**
     * 
    * Title: updateCheckWorkCurrent
    * Description: 修改年假及加班当前信息
    * Url: checkwork/updateCheckWorkCurrent
    * @param String currentInfoJsonStr 年假及加班当前信息json串
    * @return String    
    * @throws
    * @see CheckWorkCurrent
     */
	@AuthorityCheck(function = FunctionIds.FUNCTION_13)
	@NotCareLogin
	@Post("updateCheckWorkCurrent")
	@Get("updateCheckWorkCurrent")
	public String updateCheckWorkCurrent(
			@Param("currentInfoJsonStr") String currentInfoJsonStr) {
		if(StringUtils.isBlank(currentInfoJsonStr)){
			logger.error("=====参数错误，不应为空=====");
			return "@" + JSONResult.error(CodeMsg.ERROR,"参数错误，不应为空！");
		}
		CheckWorkCurrent currentInfo = null;
		try {
			currentInfo = JSONObject.parseObject(currentInfoJsonStr, CheckWorkCurrent.class);
		} catch (Exception e) {
			logger.error("=====修改年假及加班当前信息，解析参数出错=====", e);
			return "@" + JSONResult.error(CodeMsg.ERROR,"解析对象出错！");
		}
		CheckWorkCurrent current= checkWorkService.getCheckWorkCurrentById(currentInfo.getId());
		if (current == null) {
			logger.error("=====根据ID未查到基本信息=====");
			return "@" + JSONResult.error(CodeMsg.ERROR, "根据ID未查到基本信息");
		} 
		current.setSurplusOvertimeHours(currentInfo.getSurplusOvertimeHours());
		current.setAnnualLeaveDays(currentInfo.getAnnualLeaveDays());
		current.setSurplusAnnualLeave(currentInfo.getSurplusAnnualLeave());
		current.setSickLeaveDays(currentInfo.getSickLeaveDays());
		current.setCompassionateLeaveDays(currentInfo.getCompassionateLeaveDays());
		current.setUpdateTime(new Date());
		// 进行修改
		int result  = checkWorkService.updateCheckWorkCurrent(current);
		if (result>0) {
			return "@" + JSONResult.success();
		} else {
			logger.error("=====修改年假及加班当前信息数据库异常=====result="+result);
			return "@" + JSONResult.error(CodeMsg.ERROR, "修改年假及加班当前信息数据库异常result="+result);
		}
	}
	
//	/**
//     * 
//    * Title: deleteCheckWorkCurrent
//    * Description: 删除年假及加班当前信息
//    * Url: checkwork/deleteCheckWorkCurrent
//    * @param Integer currentId  年假及加班当前信息ID
//    * @return String    
//    * @throws
//     */
//	@AuthorityCheck(function = FunctionIds.FUNCTION_13)
//	@NotCareLogin
//	@Post("deleteCheckWorkCurrent")
//	@Get("deleteCheckWorkCurrent")
//	public String deleteCheckWorkCurrent(
//			@Param("currentId")Integer currentId) {
//
//		CheckWorkCurrent current= checkWorkService.getCheckWorkCurrentById(currentId);
//		if (current != null) {
//			// 进行逻辑删除
//			try {
//				int result = checkWorkService.deleteCheckWorkCurrent(currentId);
//				return "@" + JSONResult.success();
//				
//			} catch (Exception e) {
//				e.printStackTrace();
//				logger.error("=====删除年假及加班当前信息息异常====="+e);
//				return "@" + JSONResult.error(CodeMsg.ERROR, "删除年假及加班当前信息异常,"+e.getMessage());
//			}	
//		} 
//		else{
//			logger.error("=====根据ID未查到年假及加班当前信息=====");
//			return "@" + JSONResult.error(CodeMsg.ERROR, "根据ID未查到年假及加班当前信息");
//		}
//	}
//	
}
