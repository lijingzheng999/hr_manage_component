package com.hr.manage.web.controllers;

import hr.manage.component.admin.model.Admin;
import hr.manage.component.admin.service.AdminService;
import hr.manage.component.checkwork.model.CheckWorkBaidu;
import hr.manage.component.checkwork.model.CheckWorkBaiduDetail;
import hr.manage.component.checkwork.model.CheckWorkCurrent;
import hr.manage.component.checkwork.model.CheckWorkDetail;
import hr.manage.component.checkwork.model.CheckWorkDetailCondition;
import hr.manage.component.checkwork.service.CheckWorkService;
import hr.manage.component.common.model.SettingHoliday;
import hr.manage.component.common.service.CommonService;
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
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTColor;
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
	@Autowired
	SalaryService salaryService;
	@Autowired
	CommonService commonService;
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
	@AuthorityCheck(function = FunctionIds.FUNCTION_14)
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
	@AuthorityCheck(function = FunctionIds.FUNCTION_14)
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
	@AuthorityCheck(function = FunctionIds.FUNCTION_14)
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
	@AuthorityCheck(function = FunctionIds.FUNCTION_14)
	@NotCareLogin
	@Post("importQtWlwExcel")
	public String importQtWlwExcel(@Param("term")  String term,@Param("filedata")MultipartFile filedata){
		boolean result = true;
		if(StringUtils.isBlank(term)){
			logger.error("=====导入全通物联网考勤信息excel失败,账期不能为空");
			return "@" + JSONResult.error(CodeMsg.ERROR,"导入全通物联网考勤信息excel,账期不能为空");
		}
		int insuranceDetailCount = salaryService.countInsuranceDetailByTerm(term);
		if(insuranceDetailCount>0){
			logger.error("=====导入全通物联网考勤信息excel失败,本月已经上传过社保信息");
			return "@" + JSONResult.error(CodeMsg.ERROR,"导入全通物联网考勤信息excel,本月已经上传过社保信息");
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
								if(!transforValue.equals("")){
								  detail.setSurplusOvertimeHours(Integer.parseInt(transforValue));
								}
								else{
								  detail.setSurplusOvertimeHours(0);
								}
								break;
							case 12://可休年假天数 
								transforValue = String.valueOf(cellValue).trim();
								if(!transforValue.equals("")){
								  detail.setAnnualLeaveDays(BigDecimal.valueOf(Double.parseDouble(transforValue)));
								}
								else{
								  detail.setAnnualLeaveDays(BigDecimal.ZERO);
								}
								break;
							case 13:// 剩余年休天数
								transforValue = String.valueOf(cellValue).trim();
								if(!transforValue.equals("")){
								  detail.setSurplusAnnualLeave(BigDecimal.valueOf(Double.parseDouble(transforValue)));
								}
								else{
								  detail.setSurplusAnnualLeave(BigDecimal.ZERO);
								}
								break;
							case 14:// 累计长期病假天数
								transforValue = String.valueOf(cellValue).trim();
								if(!transforValue.equals("")){
									  detail.setSickLeaveDays(BigDecimal.valueOf(Double.parseDouble(transforValue)));
								}
								else{
								  detail.setSickLeaveDays(BigDecimal.ZERO);
								}
								break;
							case 15:// 累计长期事假天数
								transforValue = String.valueOf(cellValue).trim();
								if(!transforValue.equals("")){
									  detail.setCompassionateLeaveDays(BigDecimal.valueOf(Double.parseDouble(transforValue)));
								}
								else{
								  detail.setCompassionateLeaveDays(BigDecimal.ZERO);
								}
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
	                SimpleDateFormat sdt=new SimpleDateFormat("yyyyMM");
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
    * Title:importBaiduExcel
    * Description:导入百度考勤信息excel
    * Url: checkwork/importBaiduExcel
    * @param   String term,考勤月份
    * @param   Integer attendanceDays,应出勤天数
    * @param  MultipartFile filedata,  excel文件
    * @return String    
    * @throws
     */
	@AuthorityCheck(function = FunctionIds.FUNCTION_14)
	@NotCareLogin
	@Post("importBaiduExcel")
	public String importBaiduExcel(@Param("term")  String term,@Param("attendanceDays")  Integer attendanceDays,@Param("filedata")MultipartFile filedata){
		boolean result = true;
		if(StringUtils.isBlank(term)){
			logger.error("=====导入百度考勤信息excel失败,账期不能为空");
			return "@" + JSONResult.error(CodeMsg.ERROR,"导入全通物联网考勤信息excel,账期不能为空");
		}
		if(attendanceDays==null||attendanceDays<=0){
			logger.error("=====导入百度考勤信息excel失败,应出勤天数不能为空");
			return "@" + JSONResult.error(CodeMsg.ERROR,"导入全通物联网考勤信息excel,应出勤天数不能为空");
		}
//		int insuranceDetailCount = salaryService.countInsuranceDetailByTerm(term);
//		if(insuranceDetailCount>0){
//			logger.error("=====导入百度考勤信息excel失败,本月已经上传过社保信息");
//			return "@" + JSONResult.error(CodeMsg.ERROR,"导入百度考勤信息excel,本月已经上传过社保信息");
//		}
		Admin user = (Admin)inv.getRequest().getSession().getAttribute("user");
		SimpleDateFormat sdt=new SimpleDateFormat("yyyyMM");
		java.util.Date startDate=null;
		try {
			startDate = sdt.parse(String.valueOf(term).trim());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        Calendar endDate = Calendar.getInstance();  
        endDate.setTime(startDate);  
        endDate.add(Calendar.MONTH, 1);  
        //获取节假日列表
        List<SettingHoliday> listSettingHolidays = commonService.listSettingHoliday(null,startDate,endDate.getTime());
        Map<Integer,Integer> mapHolidays = new HashMap<Integer,Integer>();
        for (SettingHoliday settingHoliday : listSettingHolidays) {
        	mapHolidays.put(settingHoliday.getCurDate().getDate(), settingHoliday.getType());
		}
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
			logger.info("adminUser : "+user.getUsername()+" upload 导入百度考勤信息excel  file wait for whether to save ");
		
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
	            Sheet sheet = wb.getSheetAt(0);
	   		 Iterator<Row> rows = sheet.rowIterator(); //获得第一个表单的迭代器
	   		 List<CheckWorkBaidu> baiduList = new ArrayList<CheckWorkBaidu>();
	   		 CheckWorkBaidu baidu = null;
	   		 List<CheckWorkBaiduDetail> baiduDetails =null;
	   		 CheckWorkBaiduDetail detail = null;
	   		 while (rows.hasNext()) {  
	                Row row = rows.next();  //获得行数据  
	                if(row.getRowNum()<5||ExportBeanExcel.isRowEmpty(row))
	                	continue;
	                Iterator<Cell> cells = row.cellIterator();    //获得第一行的迭代器
	                
	                
	                if(row.getRowNum()%2==1){
//	               	 if(baiduDetails.size()>0){
//	               		 baidu.setBaiduDetails(baiduDetails);
//	               		 baiduList.add(baidu);
//	               	 }
	               	 baiduDetails = new ArrayList<CheckWorkBaiduDetail>();
	               	 baidu = new CheckWorkBaidu();
	                }
	                
	                Integer curType = -1;         
	                while (cells.hasNext()) {  
	               	 Cell cell = cells.next();  
	               	 String cellValue = "";
	               	 Integer holiType=0;
	               	 detail = new CheckWorkBaiduDetail();
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
	   	                    	continue;
	   	                    }
	   	                    /**
	   						 * 处理文件中定义的属性
	   						 */
	   						String transforValue="";
//	   						String [] colorStrings=new String [2];
	   						String colorStrings="";
	   						switch (cell.getColumnIndex()) {
	   							case 0:// 序号
	   								break;
	   							case 1:// 姓名
	   								transforValue = String.valueOf(cellValue).trim();
	   								if(StringUtils.isBlank(transforValue)){
	   									continue;
	   								}
	   								baidu.setName(transforValue);
	   								break;
	   							case 2:// 日期 白班or夜班；偶数白班；奇数夜班
	   								transforValue = String.valueOf(cellValue).trim();
	   								if(transforValue.equals("白班")){
	   									curType = 0;  
	   								}
	   								else{
	   									curType = 1;  
	   								}
	   								break;
	   							case 3:// 1号
	   								
	   								transforValue = String.valueOf(cellValue).trim();
	   								if(StringUtils.isBlank(transforValue)){
	   									continue;
	   								}
	   								if(mapHolidays.containsKey(1)){
	   									holiType=mapHolidays.get(1);
	   								}
	   								//0为背景色  1:为字体色
	   								colorStrings = ExportBeanExcel.getFontColors(wb,cell);
	   								if(colorStrings!=null){
	   									System.out.print( "  1 "+colorStrings+" "+transforValue);
	   								}
	   								detail.setCurrentDay(1);
	   								detail=ExportBeanExcel.getDetail(detail, colorStrings, transforValue,curType,holiType,startDate);
	   								baiduDetails.add(detail); 
	   								System.out.print( detail.getCurrentDay()+"  "+detail.getType()+ " "+detail.getWorkType()+" "+detail.getWorkHours());
	   								//detail.setWorkHours(BigDecimal.valueOf(Double.parseDouble(transforValue)));
	   								break;
	   							case 4:// 2号
	   								transforValue = String.valueOf(cellValue).trim();
	   								if(StringUtils.isBlank(transforValue)){
	   									continue;
	   								}
	   								//0为背景色  1:为字体色
	   								colorStrings = ExportBeanExcel.getFontColors(wb,cell);
	   								if(colorStrings!=null){
	   									System.out.print(  "  2 "+colorStrings+" "+transforValue);
	   								}
	   								detail.setCurrentDay(2);
	   								detail=ExportBeanExcel.getDetail(detail, colorStrings, transforValue,curType,holiType,startDate);
	   								baiduDetails.add(detail); 
	   								System.out.print( detail.getCurrentDay()+"  "+detail.getType()+ " "+detail.getWorkType()+" "+detail.getWorkHours());
	   								
	   								break;
	   							case 5:// 3号
	   								transforValue = String.valueOf(cellValue).trim();
	   								if(StringUtils.isBlank(transforValue)){
	   									continue;
	   								}
	   								//0为背景色  1:为字体色
	   								colorStrings = ExportBeanExcel.getFontColors(wb,cell);
	   								if(colorStrings!=null){
	   									System.out.print(  "  3 "+colorStrings+" "+transforValue);
	   								}
	   								detail.setCurrentDay(3);
	   								detail=ExportBeanExcel.getDetail(detail, colorStrings, transforValue,curType,holiType,startDate);
	   								baiduDetails.add(detail); 
	   								System.out.print( detail.getCurrentDay()+"  "+detail.getType()+ " "+detail.getWorkType()+" "+detail.getWorkHours());
	   								
	   								break;
	   							case 6:// 4号
	   								transforValue = String.valueOf(cellValue).trim();
	   								if(StringUtils.isBlank(transforValue)){
	   									continue;
	   								}
	   								//0为背景色  1:为字体色
	   								colorStrings = ExportBeanExcel.getFontColors(wb,cell);
	   								if(colorStrings!=null){
	   									System.out.print(  "  4 "+colorStrings+" "+transforValue);
	   								}
	   								detail.setCurrentDay(4);
	   								detail=ExportBeanExcel.getDetail(detail, colorStrings, transforValue,curType,holiType,startDate);
	   								baiduDetails.add(detail); 
	   								System.out.print( detail.getCurrentDay()+"  "+detail.getType()+ " "+detail.getWorkType()+" "+detail.getWorkHours());
	   								
	   								break;
	   							case 7:// 5号
	   								transforValue = String.valueOf(cellValue).trim();
	   								if(StringUtils.isBlank(transforValue)){
	   									continue;
	   								}
	   								//0为背景色  1:为字体色
	   								colorStrings = ExportBeanExcel.getFontColors(wb,cell);
	   								if(colorStrings!=null){
	   									System.out.print(  "  5 "+colorStrings+" "+transforValue);
	   								}
	   								detail.setCurrentDay(5);
	   								detail=ExportBeanExcel.getDetail(detail, colorStrings, transforValue,curType,holiType,startDate);
	   								baiduDetails.add(detail); 
	   								System.out.print( detail.getCurrentDay()+"  "+detail.getType()+ " "+detail.getWorkType()+" "+detail.getWorkHours());
	   								break;
	   							case 8:// 6号
	   								transforValue = String.valueOf(cellValue).trim();
	   								if(StringUtils.isBlank(transforValue)){
	   									continue;
	   								}
	   								//0为背景色  1:为字体色
	   								colorStrings = ExportBeanExcel.getFontColors(wb,cell);
	   								if(colorStrings!=null){
	   									System.out.print(  "  6 "+colorStrings+" "+transforValue);
	   								}
	   								detail.setCurrentDay(6);
	   								detail=ExportBeanExcel.getDetail(detail, colorStrings, transforValue,curType,holiType,startDate);
	   								baiduDetails.add(detail); 
	   								System.out.print( detail.getCurrentDay()+"  "+detail.getType()+ " "+detail.getWorkType()+" "+detail.getWorkHours());
	   								break;
	   							case 9:// 7号
	   								transforValue = String.valueOf(cellValue).trim();
	   								if(StringUtils.isBlank(transforValue)){
	   									continue;
	   								}
	   								//0为背景色  1:为字体色
	   								colorStrings = ExportBeanExcel.getFontColors(wb,cell);
	   								if(colorStrings!=null){
	   									System.out.print(  "  7 "+colorStrings+" "+transforValue);
	   								}
	   								detail.setCurrentDay(7);
	   								detail=ExportBeanExcel.getDetail(detail, colorStrings, transforValue,curType,holiType,startDate);
	   								baiduDetails.add(detail); 
	   								System.out.print( detail.getCurrentDay()+"  "+detail.getType()+ " "+detail.getWorkType()+" "+detail.getWorkHours());
	   								break;
	   							case 10:// 8号
	   								transforValue = String.valueOf(cellValue).trim();
	   								if(StringUtils.isBlank(transforValue)){
	   									continue;
	   								}
	   								//0为背景色  1:为字体色
	   								colorStrings = ExportBeanExcel.getFontColors(wb,cell);
	   								if(colorStrings!=null){
	   									System.out.print(  "  8 "+colorStrings+" "+transforValue);
	   								}
	   								detail.setCurrentDay(8);
	   								detail=ExportBeanExcel.getDetail(detail, colorStrings, transforValue,curType,holiType,startDate);
	   								baiduDetails.add(detail); 
	   								System.out.print( detail.getCurrentDay()+"  "+detail.getType()+ " "+detail.getWorkType()+" "+detail.getWorkHours());
	   								break;
	   							case 11:// 9号
	   								transforValue = String.valueOf(cellValue).trim();
	   								if(StringUtils.isBlank(transforValue)){
	   									continue;
	   								}
	   								//0为背景色  1:为字体色
	   								colorStrings = ExportBeanExcel.getFontColors(wb,cell);
	   								if(colorStrings!=null){
	   									System.out.print(  "  9 "+colorStrings+" "+transforValue);
	   								}
	   								detail.setCurrentDay(9);
	   								detail=ExportBeanExcel.getDetail(detail, colorStrings, transforValue,curType,holiType,startDate);
	   								baiduDetails.add(detail); 
	   								System.out.print( detail.getCurrentDay()+"  "+detail.getType()+ " "+detail.getWorkType()+" "+detail.getWorkHours());
	   								break;
	   							case 12:// 10号
	   								transforValue = String.valueOf(cellValue).trim();
	   								if(StringUtils.isBlank(transforValue)){
	   									continue;
	   								}
	   								//0为背景色  1:为字体色
	   								colorStrings = ExportBeanExcel.getFontColors(wb,cell);
	   								if(colorStrings!=null){
	   									System.out.print(  "  10 "+colorStrings+" "+transforValue);
	   								}
	   								detail.setCurrentDay(10);
	   								detail=ExportBeanExcel.getDetail(detail, colorStrings, transforValue,curType,holiType,startDate);
	   								baiduDetails.add(detail); 
	   								System.out.print( detail.getCurrentDay()+"  "+detail.getType()+ " "+detail.getWorkType()+" "+detail.getWorkHours());
	   								break;
	   							case 13:// 11号
	   								transforValue = String.valueOf(cellValue).trim();
	   								if(StringUtils.isBlank(transforValue)){
	   									continue;
	   								}
	   								//0为背景色  1:为字体色
	   								colorStrings = ExportBeanExcel.getFontColors(wb,cell);
	   								if(colorStrings!=null){
	   									System.out.print( "  11 "+colorStrings+" "+transforValue);
	   								}
	   								detail.setCurrentDay(11);
	   								detail=ExportBeanExcel.getDetail(detail, colorStrings, transforValue,curType,holiType,startDate);
	   								baiduDetails.add(detail); 
	   								System.out.print( detail.getCurrentDay()+"  "+detail.getType()+ " "+detail.getWorkType()+" "+detail.getWorkHours());
	   								break;
	   							case 14:// 12号
	   								transforValue = String.valueOf(cellValue).trim();
	   								if(StringUtils.isBlank(transforValue)){
	   									continue;
	   								}
	   								//0为背景色  1:为字体色
	   								colorStrings = ExportBeanExcel.getFontColors(wb,cell);
	   								if(colorStrings!=null){
	   									System.out.print(  "  12 "+colorStrings+" "+transforValue);
	   								}
	   								detail.setCurrentDay(12);
	   								detail=ExportBeanExcel.getDetail(detail, colorStrings, transforValue,curType,holiType,startDate);
	   								baiduDetails.add(detail); 
	   								System.out.print( detail.getCurrentDay()+"  "+detail.getType()+ " "+detail.getWorkType()+" "+detail.getWorkHours());
	   								break;
	   							case 15:// 13号
	   								transforValue = String.valueOf(cellValue).trim();
	   								if(StringUtils.isBlank(transforValue)){
	   									continue;
	   								}
	   								//0为背景色  1:为字体色
	   								colorStrings = ExportBeanExcel.getFontColors(wb,cell);
	   								if(colorStrings!=null){
	   									System.out.print(  "  13 "+colorStrings+" "+transforValue);
	   								}
	   								detail.setCurrentDay(13);
	   								detail=ExportBeanExcel.getDetail(detail, colorStrings, transforValue,curType,holiType,startDate);
	   								baiduDetails.add(detail); 
	   								System.out.print( detail.getCurrentDay()+"  "+detail.getType()+ " "+detail.getWorkType()+" "+detail.getWorkHours());
	   								break;
	   							case 16:// 14号
	   								transforValue = String.valueOf(cellValue).trim();
	   								if(StringUtils.isBlank(transforValue)){
	   									continue;
	   								}
	   								//0为背景色  1:为字体色
	   								colorStrings = ExportBeanExcel.getFontColors(wb,cell);
	   								if(colorStrings!=null){
	   									System.out.print(  "  14 "+colorStrings+" "+transforValue);
	   								}
	   								detail.setCurrentDay(14);
	   								detail=ExportBeanExcel.getDetail(detail, colorStrings, transforValue,curType,holiType,startDate);
	   								baiduDetails.add(detail); 
	   								System.out.print( detail.getCurrentDay()+"  "+detail.getType()+ " "+detail.getWorkType()+" "+detail.getWorkHours());
	   								break;
	   							case 17:// 15号
	   								transforValue = String.valueOf(cellValue).trim();
	   								if(StringUtils.isBlank(transforValue)){
	   									continue;
	   								}
	   								//0为背景色  1:为字体色
	   								colorStrings = ExportBeanExcel.getFontColors(wb,cell);
	   								if(colorStrings!=null){
	   									System.out.print(  "  15 "+colorStrings+" "+transforValue);
	   								}
	   								detail.setCurrentDay(15);
	   								detail=ExportBeanExcel.getDetail(detail, colorStrings, transforValue,curType,holiType,startDate);
	   								baiduDetails.add(detail); 
	   								System.out.print( detail.getCurrentDay()+"  "+detail.getType()+ " "+detail.getWorkType()+" "+detail.getWorkHours());
	   								break;
	   							case 18:// 16号
	   								transforValue = String.valueOf(cellValue).trim();
	   								if(StringUtils.isBlank(transforValue)){
	   									continue;
	   								}
	   								//0为背景色  1:为字体色
	   								colorStrings = ExportBeanExcel.getFontColors(wb,cell);
	   								if(colorStrings!=null){
	   									System.out.print(  "  16 "+colorStrings+" "+transforValue);
	   								}
	   								detail.setCurrentDay(16);
	   								detail=ExportBeanExcel.getDetail(detail, colorStrings, transforValue,curType,holiType,startDate);
	   								baiduDetails.add(detail); 
	   								System.out.print( detail.getCurrentDay()+"  "+detail.getType()+ " "+detail.getWorkType()+" "+detail.getWorkHours());
	   								break;
	   							case 19:// 17号
	   								transforValue = String.valueOf(cellValue).trim();
	   								if(StringUtils.isBlank(transforValue)){
	   									continue;
	   								}
	   								//0为背景色  1:为字体色
	   								colorStrings = ExportBeanExcel.getFontColors(wb,cell);
	   								if(colorStrings!=null){
	   									System.out.print(  "  17 "+colorStrings+" "+transforValue);
	   								}
	   								detail.setCurrentDay(17);
	   								detail=ExportBeanExcel.getDetail(detail, colorStrings, transforValue,curType,holiType,startDate);
	   								baiduDetails.add(detail); 
	   								System.out.print( detail.getCurrentDay()+"  "+detail.getType()+ " "+detail.getWorkType()+" "+detail.getWorkHours());
	   								break;
	   							case 20:// 18号
	   								transforValue = String.valueOf(cellValue).trim();
	   								if(StringUtils.isBlank(transforValue)){
	   									continue;
	   								}
	   								//0为背景色  1:为字体色
	   								colorStrings = ExportBeanExcel.getFontColors(wb,cell);
	   								if(colorStrings!=null){
	   									System.out.print(  "  18 "+colorStrings+" "+transforValue);
	   								}
	   								detail.setCurrentDay(18);
	   								detail=ExportBeanExcel.getDetail(detail, colorStrings, transforValue,curType,holiType,startDate);
	   								baiduDetails.add(detail); 
	   								System.out.print( detail.getCurrentDay()+"  "+detail.getType()+ " "+detail.getWorkType()+" "+detail.getWorkHours());
	   								break;
	   							case 21:// 19号
	   								transforValue = String.valueOf(cellValue).trim();
	   								if(StringUtils.isBlank(transforValue)){
	   									continue;
	   								}
	   								//0为背景色  1:为字体色
	   								colorStrings = ExportBeanExcel.getFontColors(wb,cell);
	   								if(colorStrings!=null){
	   									System.out.print(  "  19 "+colorStrings+" "+transforValue);
	   								}
	   								detail.setCurrentDay(19);
	   								detail=ExportBeanExcel.getDetail(detail, colorStrings, transforValue,curType,holiType,startDate);
	   								baiduDetails.add(detail); 
	   								System.out.print( detail.getCurrentDay()+"  "+detail.getType()+ " "+detail.getWorkType()+" "+detail.getWorkHours());
	   								break;
	   							case 22:// 20号
	   								transforValue = String.valueOf(cellValue).trim();
	   								if(StringUtils.isBlank(transforValue)){
	   									continue;
	   								}
	   								//0为背景色  1:为字体色
	   								colorStrings = ExportBeanExcel.getFontColors(wb,cell);
	   								if(colorStrings!=null){
	   									System.out.print(  "  20 "+colorStrings+" "+transforValue);
	   								}
	   								detail.setCurrentDay(20);
	   								detail=ExportBeanExcel.getDetail(detail, colorStrings, transforValue,curType,holiType,startDate);
	   								baiduDetails.add(detail); 
	   								System.out.print( detail.getCurrentDay()+"  "+detail.getType()+ " "+detail.getWorkType()+" "+detail.getWorkHours());
	   								break;
	   							case 23:// 21号
	   								transforValue = String.valueOf(cellValue).trim();
	   								if(StringUtils.isBlank(transforValue)){
	   									continue;
	   								}
	   								//0为背景色  1:为字体色
	   								colorStrings = ExportBeanExcel.getFontColors(wb,cell);
	   								if(colorStrings!=null){
	   									System.out.print(  "  21 "+colorStrings+" "+transforValue);
	   								}
	   								detail.setCurrentDay(21);
	   								detail=ExportBeanExcel.getDetail(detail, colorStrings, transforValue,curType,holiType,startDate);
	   								baiduDetails.add(detail); 
	   								System.out.print( detail.getCurrentDay()+"  "+detail.getType()+ " "+detail.getWorkType()+" "+detail.getWorkHours());
	   								break;
	   							case 24:// 22号
	   								transforValue = String.valueOf(cellValue).trim();
	   								if(StringUtils.isBlank(transforValue)){
	   									continue;
	   								}
	   								//0为背景色  1:为字体色
	   								colorStrings = ExportBeanExcel.getFontColors(wb,cell);
	   								if(colorStrings!=null){
	   									System.out.print(  "  22 "+colorStrings+" "+transforValue);
	   								}
	   								detail.setCurrentDay(22);
	   								detail=ExportBeanExcel.getDetail(detail, colorStrings, transforValue,curType,holiType,startDate);
	   								baiduDetails.add(detail); 
	   								System.out.print( detail.getCurrentDay()+"  "+detail.getType()+ " "+detail.getWorkType()+" "+detail.getWorkHours());
	   								break;
	   							case 25:// 23号
	   								transforValue = String.valueOf(cellValue).trim();
	   								if(StringUtils.isBlank(transforValue)){
	   									continue;
	   								}
	   								//0为背景色  1:为字体色
	   								colorStrings = ExportBeanExcel.getFontColors(wb,cell);
	   								if(colorStrings!=null){
	   									System.out.print(  "  23 "+colorStrings+" "+transforValue);
	   								}
	   								detail.setCurrentDay(23);
	   								detail=ExportBeanExcel.getDetail(detail, colorStrings, transforValue,curType,holiType,startDate);
	   								baiduDetails.add(detail); 
	   								System.out.print( detail.getCurrentDay()+"  "+detail.getType()+ " "+detail.getWorkType()+" "+detail.getWorkHours());
	   								break;
	   							case 26:// 24号
	   								transforValue = String.valueOf(cellValue).trim();
	   								if(StringUtils.isBlank(transforValue)){
	   									continue;
	   								}
	   								//0为背景色  1:为字体色
	   								colorStrings = ExportBeanExcel.getFontColors(wb,cell);
	   								if(colorStrings!=null){
	   									System.out.print(  "  24 "+colorStrings+" "+transforValue);
	   								}
	   								detail.setCurrentDay(24);
	   								detail=ExportBeanExcel.getDetail(detail, colorStrings, transforValue,curType,holiType,startDate);
	   								baiduDetails.add(detail); 
	   								System.out.print( detail.getCurrentDay()+"  "+detail.getType()+ " "+detail.getWorkType()+" "+detail.getWorkHours());
	   								break;
	   							case 27:// 25号
	   								transforValue = String.valueOf(cellValue).trim();
	   								if(StringUtils.isBlank(transforValue)){
	   									continue;
	   								}
	   								//0为背景色  1:为字体色
	   								colorStrings = ExportBeanExcel.getFontColors(wb,cell);
	   								if(colorStrings!=null){
	   									System.out.print(  "  25 "+colorStrings+" "+transforValue);
	   								}
	   								detail.setCurrentDay(25);
	   								detail=ExportBeanExcel.getDetail(detail, colorStrings, transforValue,curType,holiType,startDate);
	   								baiduDetails.add(detail); 
	   								System.out.print( detail.getCurrentDay()+"  "+detail.getType()+ " "+detail.getWorkType()+" "+detail.getWorkHours());
	   								break;
	   							case 28:// 26号
	   								transforValue = String.valueOf(cellValue).trim();
	   								if(StringUtils.isBlank(transforValue)){
	   									continue;
	   								}
	   								//0为背景色  1:为字体色
	   								colorStrings = ExportBeanExcel.getFontColors(wb,cell);
	   								if(colorStrings!=null){
	   									System.out.print(  "  26 "+colorStrings+" "+transforValue);
	   								}
	   								detail.setCurrentDay(26);
	   								detail=ExportBeanExcel.getDetail(detail, colorStrings, transforValue,curType,holiType,startDate);
	   								baiduDetails.add(detail); 
	   								System.out.print( detail.getCurrentDay()+"  "+detail.getType()+ " "+detail.getWorkType()+" "+detail.getWorkHours());
	   								break;
	   							case 29:// 27号
	   								transforValue = String.valueOf(cellValue).trim();
	   								if(StringUtils.isBlank(transforValue)){
	   									continue;
	   								}
	   								//0为背景色  1:为字体色
	   								colorStrings = ExportBeanExcel.getFontColors(wb,cell);
	   								if(colorStrings!=null){
	   									System.out.print(  "  27 "+colorStrings+" "+transforValue);
	   								}
	   								detail.setCurrentDay(27);
	   								detail=ExportBeanExcel.getDetail(detail, colorStrings, transforValue,curType,holiType,startDate);
	   								baiduDetails.add(detail); 
	   								System.out.print( detail.getCurrentDay()+"  "+detail.getType()+ " "+detail.getWorkType()+" "+detail.getWorkHours());
	   								break;
	   							case 30:// 28号
	   								transforValue = String.valueOf(cellValue).trim();
	   								if(StringUtils.isBlank(transforValue)){
	   									continue;
	   								}
	   								//0为背景色  1:为字体色
	   								colorStrings = ExportBeanExcel.getFontColors(wb,cell);
	   								if(colorStrings!=null){
	   									System.out.print(  "  28 "+colorStrings+" "+transforValue);
	   								}
	   								detail.setCurrentDay(28);
	   								detail=ExportBeanExcel.getDetail(detail, colorStrings, transforValue,curType,holiType,startDate);
	   								baiduDetails.add(detail); 
	   								System.out.print( detail.getCurrentDay()+"  "+detail.getType()+ " "+detail.getWorkType()+" "+detail.getWorkHours());
	   								break;
	   							case 31:// 29号
	   								transforValue = String.valueOf(cellValue).trim();
	   								if(StringUtils.isBlank(transforValue)){
	   									continue;
	   								}
	   								//0为背景色  1:为字体色
	   								colorStrings = ExportBeanExcel.getFontColors(wb,cell);
	   								if(colorStrings!=null){
	   									System.out.print(  "  29 "+colorStrings+" "+transforValue);
	   								}
	   								detail.setCurrentDay(29);
	   								detail=ExportBeanExcel.getDetail(detail, colorStrings, transforValue,curType,holiType,startDate);
	   								baiduDetails.add(detail); 
	   								System.out.print( detail.getCurrentDay()+"  "+detail.getType()+ " "+detail.getWorkType()+" "+detail.getWorkHours());
	   								break;
	   							case 32:// 30号
	   								transforValue = String.valueOf(cellValue).trim();
	   								if(StringUtils.isBlank(transforValue)){
	   									continue;
	   								}
	   								//0为背景色  1:为字体色
	   								colorStrings = ExportBeanExcel.getFontColors(wb,cell);
	   								if(colorStrings!=null){
	   									System.out.print(  "  30 "+colorStrings+" "+transforValue);
	   								}
	   								detail.setCurrentDay(30);
	   								detail=ExportBeanExcel.getDetail(detail, colorStrings, transforValue,curType,holiType,startDate);
	   								baiduDetails.add(detail); 
	   								System.out.print( detail.getCurrentDay()+"  "+detail.getType()+ " "+detail.getWorkType()+" "+detail.getWorkHours());
	   								break;
	   							case 33:// 31号
	   								transforValue = String.valueOf(cellValue).trim();
	   								if(StringUtils.isBlank(transforValue)){
	   									continue;
	   								}
	   								//0为背景色  1:为字体色
	   								colorStrings = ExportBeanExcel.getFontColors(wb,cell);
	   								if(colorStrings!=null){
	   									System.out.print(  "  31 "+colorStrings+" "+transforValue);
	   								}
	   								detail.setCurrentDay(31);
	   								detail=ExportBeanExcel.getDetail(detail, colorStrings, transforValue,curType,holiType,startDate);
	   								baiduDetails.add(detail); 
	   								System.out.print( detail.getCurrentDay()+"  "+detail.getType()+ " "+detail.getWorkType()+" "+detail.getWorkHours());
	   								break;
	   							case 34:// 应出勤小时数
	   								transforValue = String.valueOf(cellValue).trim();
	   								if(StringUtils.isBlank(transforValue)){
	   									continue;
	   								}
	   								baidu.setAttendanceHours(BigDecimal.valueOf(Double.parseDouble(transforValue)));
	   								break;
	   						}
	   						
	                      }  
	                
	            	  System.out.println();
	            	  if(row.getRowNum()%2==0){
	               	 if(baiduDetails.size()>0){
	               		 baidu.setBaiduDetails(baiduDetails);
	               		 baiduList.add(baidu);
	               	 }
	               	 
	               	
	                 }
	   			}
	        //批量入库
	        try {
	        	checkWorkService.saveCheckWorkBaiduListRecord(term,attendanceDays,baiduList);
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
				logger.error("保存数据库异常,已回滚", e);
				return "@"+JSONResult.error(CodeMsg.ERROR,"保存数据库异常,已回滚"+ e.getMessage());  
			}
	        
		}catch(Exception e1){
			e1.printStackTrace();
			logger.error("upload 导入百度考勤信息 throws Exception", e1);
			return "@"+JSONResult.error(CodeMsg.ERROR,"导入百度考勤信息失败，请稍后重试"+e1);  
		}
		if(result){
			logger.info("adminUser : "+user.getUsername()+"upload 导入百度考勤信息 ; fileNumber : OT"+fileNumber);
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
	@AuthorityCheck(function = FunctionIds.FUNCTION_14)
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
	@AuthorityCheck(function = FunctionIds.FUNCTION_14)
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
	@AuthorityCheck(function = FunctionIds.FUNCTION_14)
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
	
	
	

	
	/**
     * 
    * Title: getBaiduList
    * Description: 根据条件获取全通物联网信息列表
    * Url: checkwork/getBaiduList
    * @param String name, 姓名
    * @param String term,考勤月份
    * @param String expatriateUnit,外派单位
    * @param int pageIndex, 分页页数
    * @param int pageSize 	行数
    * @return String    
    * @throws
     */
	@AuthorityCheck(function = FunctionIds.FUNCTION_14)
	@NotCareLogin
	@Post("getBaiduList")
	public String getBaiduList(@Param("name") String name,
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
		List<CheckWorkBaidu> checkWorkBaiduLists = new ArrayList<>();
		try {
			checkWorkBaiduLists = checkWorkService.listCheckWorkBaidu(condition);
			count = checkWorkService.countCheckWorkBaidu(condition);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("=====根据条件获取百度考勤信息列表查询，调用service出错=====", e);
			return "@" + JSONResult.error(CodeMsg.SERVER_ERROR);
		}
		Long pageCount = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;
		Map<String, Object> dataMap = DataMapUtil.getDataMap("checkWorkBaiduLists", checkWorkBaiduLists, count, pageCount);
		return "@" + JSONResult.success(dataMap);
	}
	
	

	/**
     * 
    * Title: getCheckWorkBaiduById
    * Description: 通过ID获取当月百度考勤信息
    * Url: checkwork/getCheckWorkBaiduById
    * @param Integer baiduId  百度考勤表ID
    * @return String    
    * @throws
     */
	@AuthorityCheck(function = FunctionIds.FUNCTION_14)
	@NotCareLogin
	@Post("getCheckWorkBaiduById")
	@Get("getCheckWorkBaiduById")
	public String getCheckWorkBaiduById(
			@Param("baiduId")Integer baiduId) {
		
		CheckWorkBaidu baiduInfo= checkWorkService.getCheckWorkBaiduById(baiduId);
		if (baiduInfo != null) {
			return "@" + JSONResult.success(baiduInfo);			
		} else {
			logger.error("=====根据ID未查到百度考勤信息=====");
			return "@" + JSONResult.error(CodeMsg.ERROR, "根据ID未查到百度考勤信息");
		}
	}

	/**
     * 
    * Title: updateCheckWorkBaidu
    * Description: 修改百度考勤信息
    * Url: checkwork/updateCheckWorkBaidu
    * @param String detailInfoJsonStr 百度考勤信息json串
    * @return String    
    * @throws
    * @see CheckWorkBaidu
     */
	@AuthorityCheck(function = FunctionIds.FUNCTION_14)
	@NotCareLogin
	@Post("updateCheckWorkBaidu")
	@Get("updateCheckWorkBaidu")
	public String updateCheckWorkBaidu(
			@Param("baiduInfoJsonStr") String baiduInfoJsonStr) {
		if(StringUtils.isBlank(baiduInfoJsonStr)){
			logger.error("=====参数错误，不应为空=====");
			return "@" + JSONResult.error(CodeMsg.ERROR,"参数错误，不应为空！");
		}
		CheckWorkBaidu baiduInfo = null;
		try {
			baiduInfo = JSONObject.parseObject(baiduInfoJsonStr, CheckWorkBaidu.class);
		} catch (Exception e) {
			logger.error("=====修改百度考勤信息，解析参数出错=====", e);
			return "@" + JSONResult.error(CodeMsg.ERROR,"解析对象出错！");
		}
		CheckWorkBaidu baidu= checkWorkService.getCheckWorkBaiduById(baiduInfo.getId());
		if (baidu == null) {
			logger.error("=====根据ID未查到百度考勤信息=====");
			return "@" + JSONResult.error(CodeMsg.ERROR, "根据ID未查到百度考勤信息");
		} 
//		current.setSurplusOvertimeHours(currentInfo.getSurplusOvertimeHours());
//		current.setAnnualLeaveDays(currentInfo.getAnnualLeaveDays());
//		current.setSurplusAnnualLeave(currentInfo.getSurplusAnnualLeave());
//		current.setSickLeaveDays(currentInfo.getSickLeaveDays());
//		current.setCompassionateLeaveDays(currentInfo.getCompassionateLeaveDays());
//		detail.setSettlementDays(detailInfo.getSettlementDays());
		baiduInfo.setUpdateTime(new Date());
		// 进行修改
		int result  = checkWorkService.updateCheckWorkBaidu(baiduInfo);
		if (result>0) {
			return "@" + JSONResult.success();
		} else {
			logger.error("=====修改百度考勤信息数据库异常=====result="+result);
			return "@" + JSONResult.error(CodeMsg.ERROR, "修改百度勤信信息数据库异常result="+result);
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
