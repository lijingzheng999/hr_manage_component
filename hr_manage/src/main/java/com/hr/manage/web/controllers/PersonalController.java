package com.hr.manage.web.controllers;

import hr.manage.component.admin.model.Admin;
import hr.manage.component.admin.model.AdminRole;
import hr.manage.component.admin.service.AdminService;
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
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
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

/**
 * 
* @see 员工基本信息相关API;
* @see 统一返回JSON串 code,message,data
* @author  lee
* @version 1.0
 */
@Path("personal")
public class PersonalController {

	@Autowired
	Invocation inv;
	@Autowired
	PersonalService personalService;
	@Autowired
	AdminService adminService;
	private final Log logger = LogFactory.getLog(PersonalController.class);
	private static final String FILE_UPLOAD_PATH=ServiceConfigFactory.getValue("file.upload.path");
	
	/**
     * 
    *  Title:getPersonalAllInfoBySelfId
    *  Description:查询本人基本信息
    * Url: personal/getPersonalAllInfoBySelfId
    * @param   
    * @return String    
    * @throws
     */
	@AuthorityCheck(function = FunctionIds.FUNCTION_11)
	@NotCareLogin
	@Post("getPersonalAllInfoBySelfId")
	@Get("getPersonalAllInfoBySelfId")
	public String getPersonalAllInfoBySelfId(){
		Admin user = (Admin)inv.getRequest().getSession().getAttribute("user");
		//判断是否有基本信息
		if(user.getPersonalInfoId()>0){
			PersonalAll personalAll = personalService.getPersonalAllInfoById(user.getPersonalInfoId());
			if(personalAll.getPersonalInfo()!=null){
				return "@"+JSONResult.success(personalAll);
			}
			else{
				logger.error("根据登陆ID未查到基本信息");
				return "@"+JSONResult.error(CodeMsg.ERROR,"根据登陆ID未查到基本信息");
			}
		}
		else{
			logger.error("登陆ID没有基本信息");
			return "@"+JSONResult.error(CodeMsg.ERROR,"登陆ID没有基本信息");
		}
		
	}
	
	
	/**
     * 
    * Title:updatePersonalAllInfoBySelf
    * Description:修改本人基本信息
    * Url: personal/updatePersonalAllInfoBySelf
    * @param String personalAllJsonStr 基本信息Json串
    * @return String    
    * @throws
    * @see  PersonalAll
     */
	@AuthorityCheck(function = FunctionIds.FUNCTION_11)
	@NotCareLogin
	@Post("updatePersonalAllInfoBySelf")
	@Get("updatePersonalAllInfoBySelf")
	public String updatePersonalAllInfoBySelf(@Param("personalAll")String personalAllJsonStr ){
		if(StringUtils.isBlank(personalAllJsonStr)){
			logger.error("=====参数错误，不应为空=====");
			return "@" + JSONResult.error(CodeMsg.ERROR,"参数错误，不应为空！");
		}
		PersonalAll newPersonalAll = null;
		try {
			newPersonalAll = JSONObject.parseObject(personalAllJsonStr, PersonalAll.class);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("=====修改本人基本信息，解析参数出错=====", e);
			return "@" + JSONResult.error(CodeMsg.ERROR,"解析对象出错！");
		}
		int result =0;
		Admin user = (Admin)inv.getRequest().getSession().getAttribute("user");
		//判断是否有基本信息
		if(user.getPersonalInfoId()>0){
			//判断修改信息是否为本人的
			if(user.getPersonalInfoId().equals(newPersonalAll.getPersonalInfo().getId())){
				//进行修改
				try {
					result = personalService.updatePersonalAllInfoBySelf(newPersonalAll);
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("更新数据库异常"+e);
					return "@"+JSONResult.error(CodeMsg.ERROR,"更新数据库异常"+e);
				}
				if(result<0){
					logger.error("根据登陆ID未查到基本信息");
					return "@"+JSONResult.error(CodeMsg.ERROR,"根据登陆ID未查到基本信息");
				}
				if(result==0){
					logger.error("更新基本信息失败");
					return "@"+JSONResult.error(CodeMsg.ERROR,"更新基本信息失败");
				}
				return "@"+JSONResult.success();
				
			}
			else{
				logger.error("登陆ID与要修改的员工不一致");
				return "@"+JSONResult.error(CodeMsg.ERROR,"登陆ID与要修改的员工不一致");
			}
			
		}
		else{
			logger.error("登陆ID没有基本信息");
			return "@"+JSONResult.error(CodeMsg.ERROR,"登陆ID没有基本信息");
		}
		
	}
	
	
	/**
     * 
    * Title: addPersonalAllInfo
    * Description: 新增员工基本信息
    * Url: personal/addPersonalAllInfo
    * @param String personalAllJsonStr 员工基本信息json串
    * @return String    
    * @throws
    * @see PersonalAll
     */
	@AuthorityCheck(function = FunctionIds.FUNCTION_12)
	@NotCareLogin
	@Post("addPersonalAllInfo")
	@Get("addPersonalAllInfo")
	public String addPersonalAllInfo(
			@Param("personalAllJsonStr") String personalAllJsonStr) {
		if(StringUtils.isBlank(personalAllJsonStr)){
			logger.error("=====参数错误，不应为空=====");
			return "@" + JSONResult.error(CodeMsg.ERROR,"参数错误，不应为空！");
		}
		PersonalAll personalAll = null;
		try {
			personalAll = JSONObject.parseObject(personalAllJsonStr, PersonalAll.class);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("=====新增员工基本信息，解析参数出错=====", e);
			return "@" + JSONResult.error(CodeMsg.ERROR,"解析对象出错！");
		}
		//验证员工信息是否存在
        int checkNum = personalService.checkPersonalByNameAndCard(personalAll.getPersonalInfo().getName(), personalAll.getPersonalInfo().getIdentityCard());
        if(checkNum>0){
        	logger.error("====="+String.format("员工信息已存在,姓名:%s,身份证:%s", personalAll.getPersonalInfo().getName(), personalAll.getPersonalInfo().getIdentityCard())+"=====");
        	return "@"+JSONResult.error(CodeMsg.ERROR,String.format("员工信息已存在,姓名:%s,身份证:%s", personalAll.getPersonalInfo().getName(), personalAll.getPersonalInfo().getIdentityCard())); 
        }
		//进行新增
		int result =0;
		try {
			result = personalService.addPersonalAllInfo(personalAll);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("新增数据时数据库异常"+e);
			return "@"+JSONResult.error(CodeMsg.ERROR,"增数据时数据库异常"+e);
		}
		if(result<=0){
			logger.error("新增基本信息失败");
			return "@"+JSONResult.error(CodeMsg.ERROR,"新增基本信息失败");
		}
		return "@"+JSONResult.success();

	}
	
	
	
	/**
     * 
    * Title:importExcel
    * Description:导入员工基本信息excel
    * Url: personal/importExcel
    * @param  MultipartFile filedata  excel文件
    * @return String    
    * @throws
     */
	@AuthorityCheck(function = FunctionIds.FUNCTION_12)
	@NotCareLogin
	@Post("importExcel")
	@Get("importExcel")
	public String importExcel(@Param("filedata")MultipartFile filedata){
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
			logger.info("adminUser : "+user.getUsername()+" upload timeOutRecord file wait for whether to save ");
		
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
	            List<PersonalAll> personAllList = new ArrayList<PersonalAll>();
	            while (rows.hasNext()) {  
	                Row row = rows.next();  //获得行数据  
	                if(row.getRowNum()<1||row==null)
	                	continue;
	                Iterator<Cell> cells = row.cellIterator();    //获得第一行的迭代器
	                PersonalInfo person= new PersonalInfo();
	                PersonalWorkInfo work= new PersonalWorkInfo();
	                PersonalSalaryInfo salary =new PersonalSalaryInfo();
	                PersonalAll personalAll = new PersonalAll();
	                while (cells.hasNext()) {  
	                    Cell cell = cells.next();  
	                    String cellValue = "";
	                    switch (cell.getCellType()) {   //根据cell中的类型来输出数据  
	                    case HSSFCell.CELL_TYPE_NUMERIC:  
	                    	int formatNo = cell.getCellStyle().getDataFormat();
	                    	int formatNo2 = HSSFDataFormat.getBuiltinFormat("yyyy-MM-dd");
	                    	if(cell.getCellStyle().getDataFormat()==178){
	                    		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	                    		double value = cell.getNumericCellValue();
	                    		Date date = org.apache.poi.ss.usermodel.DateUtil
	                    		.getJavaDate(value);
	                    		cellValue = sdf.format(date); 
	                    	}
	                    	else if (DateUtil.isCellDateFormatted(cell)) {
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
							case 1:// 姓名
								transforValue = String.valueOf(cellValue).trim();
								if(StringUtils.isBlank(transforValue)){
									logger.error("=====导入失败！姓名不能为空=====");
									return "@"+JSONResult.error(CodeMsg.ERROR,"导入失败！姓名不能为空");  
									
								}
								person.setName(transforValue);
								
								break;
							case 2:// 工作地點
								transforValue = String.valueOf(cellValue).trim();
								work.setWorkingPlace(transforValue);
								break;
							case 3:// 岗位类别
								transforValue = String.valueOf(cellValue).trim();
								work.setPostType(transforValue);
								break;
							case 4:// 职位
								transforValue = String.valueOf(cellValue).trim();
								work.setPosition(transforValue);
								break;
							case 5:// 級別
								transforValue = String.valueOf(cellValue).trim();
								work.setLevel(transforValue);
								break;
							case 6:// 部门
								transforValue = String.valueOf(cellValue).trim();
								work.setDepartment(transforValue);
								break;
							case 7:// 中心
								transforValue = String.valueOf(cellValue).trim();
								work.setCenter(transforValue);
								break;
							case 8:// 项目
								transforValue = String.valueOf(cellValue).trim();
								work.setProject(transforValue);
								break;
							case 9:// 全通负责人
								transforValue = String.valueOf(cellValue).trim();
								work.setExpatriateManager(transforValue);
								break;
							case 10:// 联系电话
								transforValue = String.valueOf(cellValue).trim();
								person.setPhone(transforValue);
								break;
							case 11:// 外派单位 
								transforValue = String.valueOf(cellValue).trim();
								work.setExpatriateUnit(transforValue);
								break;
							case 12://招聘渠道
								transforValue = String.valueOf(cellValue).trim();
								work.setRecruitChannel(transforValue);
								break;
							case 13:// 入职时间 
								transforValue = String.valueOf(cellValue).trim();
								if(StringUtils.isBlank(transforValue)){
									logger.error("=====导入失败！入职时间 不能为空=====");
									return "@"+JSONResult.error(CodeMsg.ERROR,"导入失败！入职时间不能为空");  
								}
									SimpleDateFormat sdtentry=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//小写的mm表示的是分钟  
									java.util.Date entryrdate=sdtentry.parse(String.valueOf(cellValue).trim());
									salary.setEntryTime(entryrdate);
								break;
							case 14:// 到岗时间
								transforValue = String.valueOf(cellValue).trim();
								if(StringUtils.isBlank(transforValue)){
									logger.error("=====导入失败！到岗时间不能为空=====");
									return "@"+JSONResult.error(CodeMsg.ERROR,"导入失败！到岗时间不能为空");  
								}
									SimpleDateFormat sdtarray=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//小写的mm表示的是分钟  
									java.util.Date arraydate=sdtarray.parse(String.valueOf(cellValue).trim());
									salary.setArrivalTime(arraydate);
									//工齡=(TODAY()-到崗時間)/365
									double f1 = new BigDecimal((float)DateTimeUtil.differentDaysByMillisecond(arraydate,new Date())/365).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
									
									salary.setWorkingYears(BigDecimal.valueOf(f1));
						
								break;
							case 15:// 转正时间
								transforValue = String.valueOf(cellValue).trim();
								if(StringUtils.isBlank(transforValue)){
									logger.error("=====导入失败！转正时间不能为空=====");
									return "@"+JSONResult.error(CodeMsg.ERROR,"导入失败！转正时间不能为空");  
								}
									SimpleDateFormat sdtworker=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//小写的mm表示的是分钟  
									java.util.Date workerdate=sdtworker.parse(String.valueOf(cellValue).trim());
									salary.setWorkerTime(workerdate);
								break;
							case 16:// 工齡=(TODAY()-到崗時間)/365
//								double f1 = new BigDecimal((float)DateTimeUtil.differentDaysByMillisecond(salary.getArrivalTime(),new Date())/365).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
//								
//								salary.setWorkingYears(BigDecimal.valueOf(f1));
								break;
							case 17:// 缴纳社保起始月份
								transforValue = String.valueOf(cellValue).trim();
								salary.setInsuranceBeginDate(transforValue);
//								SimpleDateFormat sdtInsuranceBeginDate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//小写的mm表示的是分钟  
//								java.util.Date insuranceBeginDate=sdtInsuranceBeginDate.parse(String.valueOf(cellValue).trim());
//								salary.setInsuranceBeginDate(sdtInsuranceBeginDate.format(insuranceBeginDate));
								break;
							case 18:// 社保缴纳地点
								transforValue = String.valueOf(cellValue).trim();
								salary.setInsurancePlace(transforValue);
								break;
							case 19:// 合同签署次数
								transforValue = String.valueOf(cellValue).trim();
								if(StringUtils.isBlank(transforValue)){
									transforValue="1";
								}
								work.setContractCount(Integer.parseInt(transforValue));
								break;
							case 20:// 合同签订日期
								transforValue = String.valueOf(cellValue).trim();
								if(StringUtils.isBlank(transforValue)){
									logger.error("=====导入失败！合同签订日期不能为空=====");
									return "@"+JSONResult.error(CodeMsg.ERROR,"导入失败！合同签订日期不能为空");  
								}
									SimpleDateFormat sdtContractDate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//小写的mm表示的是分钟  
									java.util.Date contractDate=sdtContractDate.parse(String.valueOf(cellValue).trim());
									work.setContractStartdate(contractDate);
								
								break;
							case 21:// 合同失效日期
								transforValue = String.valueOf(cellValue).trim();
								if(StringUtils.isBlank(transforValue)){
									logger.error("=====导入失败！合同失效日期不能为空=====");
									return "@"+JSONResult.error(CodeMsg.ERROR,"导入失败！合同失效日期不能为空");  
								}
									SimpleDateFormat sdtContractEnddate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//小写的mm表示的是分钟  
									java.util.Date contractEnddate=sdtContractEnddate.parse(String.valueOf(cellValue).trim());
									work.setContractEnddate(contractEnddate);
								
								
								break;
							case 22:// 续签合同日期
								transforValue = String.valueOf(cellValue).trim();
								if(StringUtils.isNotBlank(transforValue)){
									SimpleDateFormat sdtContractRenewDate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//小写的mm表示的是分钟  
									java.util.Date contractRenewDate=sdtContractRenewDate.parse(String.valueOf(cellValue).trim());
									work.setContractRenewDate(contractRenewDate);
								}
								
								break;
							case 23:// 续签合同失效日期
								transforValue = String.valueOf(cellValue).trim();
								if(StringUtils.isNotBlank(transforValue)){
									SimpleDateFormat sdtContractRenewEnddate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//小写的mm表示的是分钟  
									java.util.Date contractRenewEnddate=sdtContractRenewEnddate.parse(String.valueOf(cellValue).trim());
									work.setContractRenewEnddate(contractRenewEnddate);
								}
								
								break;
							case 24:// 身份证411024199101118518
								transforValue = String.valueOf(cellValue).trim();
								if(StringUtils.isBlank(transforValue)){
									logger.error("=====导入失败！身份证不能为空=====");
									return "@"+JSONResult.error(CodeMsg.ERROR,"导入失败！身份证不能为空");  
								}
								person.setIdentityCard(transforValue);
								// 年龄=YEAR(TODAY())-MID(身份证,7,4)
								String strBirthDay=transforValue.substring(6, 14);
								SimpleDateFormat sdtAge=new SimpleDateFormat("yyyyMMdd");
								java.util.Date birthDay=sdtAge.parse(strBirthDay);
								String curAge = String.valueOf(DateTimeUtil.yearDateDiff(birthDay,new Date())).trim();
								person.setAge(Integer.parseInt(curAge));
								// 性别=IF(MOD(MID(身份证,15,3),2),"男","女")
								String curSexString="";
								if (Integer.parseInt(transforValue.substring(16).substring(0, 1)) % 2 == 0) {// 判断性别
									curSexString = "女";
								} else {
									curSexString = "男";
								}
								person.setSex(curSexString);
								// 出生日期=MID(身份证,11,2)&"月"&MID(身份证,13,2)&"日"
								person.setBirthday(birthDay);
								break;
							case 25:// 年龄=YEAR(TODAY())-MID(身份证,7,4)
								//取生日
//								String strBirthDay=person.getIdentityCard().substring(6, 14);
//								SimpleDateFormat sdtAge=new SimpleDateFormat("yyyyMMdd");
//								java.util.Date birthDay=sdtAge.parse(strBirthDay);
//								transforValue = String.valueOf(DateTimeUtil.yearDateDiff(birthDay,new Date())).trim();
//								person.setAge(Integer.parseInt(transforValue));
								break;
							case 26:// 性别=IF(MOD(MID(身份证,15,3),2),"男","女")
//								if (Integer.parseInt(person.getIdentityCard().substring(16).substring(0, 1)) % 2 == 0) {// 判断性别
//									transforValue = "女";
//								} else {
//									transforValue = "男";
//								}
//								person.setSex(transforValue);
								break;
							case 27:// 出生日期=MID(身份证,11,2)&"月"&MID(身份证,13,2)&"日"
//								transforValue = person.getIdentityCard().substring(10, 12)+"月"+person.getIdentityCard().substring(12, 14)+"日";
//								person.setBirthday(transforValue);
								break;
							case 28:// 户口性质
								transforValue = String.valueOf(cellValue).trim();
								person.setHomeProperty(transforValue);
								break;
							case 29:// 籍贯
								transforValue = String.valueOf(cellValue).trim();
								person.setNativePlace(transforValue);
								break;
							case 30:// 婚姻状况
								transforValue = String.valueOf(cellValue).trim();
								person.setMarriage(transforValue);
								break;
							case 31:// 民族 
								transforValue = String.valueOf(cellValue).trim();
								person.setNation(transforValue);
								break;
							case 32://  职称
								transforValue = String.valueOf(cellValue).trim();
								person.setTitle(transforValue);
								break;
							case 33://学历
								transforValue = String.valueOf(cellValue).trim();
								person.setEducation(transforValue);
								break;
							case 34:// 毕业学校 
								transforValue = String.valueOf(cellValue).trim();
								person.setSchool(transforValue);
								break;
							case 35://毕业专业
								transforValue = String.valueOf(cellValue).trim();
								person.setMajor(transforValue);
								break;
							case 36:// 英语
								transforValue = String.valueOf(cellValue).trim();
								person.setEnglish(transforValue);
								break;
							case 37:// 毕业时间
								transforValue = String.valueOf(cellValue).trim();
								if(StringUtils.isNotBlank(transforValue)){
									SimpleDateFormat sdtGraduationTime=new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟  
									Date graduationTime=sdtGraduationTime.parse(cellValue.toString());
									person.setGraduationTime(graduationTime);
									// 工作年限=DATEDIF(毕业时间,TODAY(),"Y")&"年"
									String curWorkingLife = String.valueOf(DateTimeUtil.yearDateDiff(graduationTime,new Date())+"年").trim();
									person.setWorkingLife(curWorkingLife);
								}
								
								break;
							case 38:// 工作年限=DATEDIF(毕业时间,TODAY(),"Y")&"年"
//								transforValue = String.valueOf(DateTimeUtil.yearDateDiff(person.getGraduationTime(),new Date())+"年").trim();
//								person.setWorkingLife(transforValue);
								break;
							case 39:// 联系地址
								transforValue = String.valueOf(cellValue).trim();
								person.setContactAddress(transforValue);
								break;
							case 40:// 户籍地址
								transforValue = String.valueOf(cellValue).trim();
								person.setHomeAddress(transforValue);
								break;
							case 41:// 紧急联系人
								transforValue = String.valueOf(cellValue).trim();
								person.setContact(transforValue);
								break;
							case 42:// 紧急联系人电话
								transforValue = String.valueOf(cellValue).trim();
								person.setContactPhone(transforValue);
								break;
							case 43:// 招商银行卡号
								transforValue = String.valueOf(cellValue).trim();
								salary.setBankCardNumber(transforValue);
								break;
							case 44:// 开户行
								transforValue = String.valueOf(cellValue).trim();
								salary.setBankOpenPlace(transforValue);
								break;
							case 45://工作地址
								transforValue = String.valueOf(cellValue).trim();
								work.setWorkingAddress(transforValue);
								break;
							case 46:// 试用期
								transforValue = String.valueOf(cellValue).trim();
								salary.setProbationPeriod(transforValue);
								break;
							case 47:// 试用期福利
								transforValue = String.valueOf(cellValue).trim();
								salary.setProbationPeriodWelfare(transforValue);
								break;
							case 48:// 转正福利
								transforValue = String.valueOf(cellValue).trim();
								salary.setWorkerWelfare(transforValue);
								break;
							case 49:// 基本工资
								transforValue = String.valueOf(cellValue).trim();
								if(StringUtils.isBlank(transforValue)){
									transforValue="0";
								}
								salary.setBasePay(BigDecimal.valueOf(Double.parseDouble(transforValue)));
								break;
							case 50:// 绩效工资
								transforValue = String.valueOf(cellValue).trim();
								if(StringUtils.isBlank(transforValue)){
									transforValue="0";
								}
								salary.setMeritPay(BigDecimal.valueOf(Double.parseDouble(transforValue)));
								break;
							case 51:// 补贴
								transforValue = String.valueOf(cellValue).trim();
								if(StringUtils.isBlank(transforValue)){
									transforValue="0";
								}
								salary.setSubsidy(BigDecimal.valueOf(Double.parseDouble(transforValue)));
								break;
							case 52:// 转正工资=基本工资+绩效工资+补贴
//								transforValue = String.valueOf(cellValue).trim();
								BigDecimal workerPay= BigDecimal.ZERO;
								workerPay=workerPay.add(salary.getBasePay());
								workerPay=workerPay.add( salary.getMeritPay());
								workerPay=workerPay.add(salary.getSubsidy());
								salary.setWorkerPay(workerPay);
//								salary.setWorkerPay(salary.getBasePay()+salary.getMeritPay()+salary.getSubsidy());
								break;
							case 53:// 试用期工资=转正工资*80%
//								transforValue = String.valueOf(cellValue).trim();
								BigDecimal probationaryPay= BigDecimal.ZERO;
								probationaryPay= salary.getWorkerPay().multiply(new BigDecimal("0.8"));
								probationaryPay=probationaryPay.setScale(2, BigDecimal.ROUND_HALF_UP);
								salary.setProbationaryPay(probationaryPay);
//								salary.setProbationaryPay((int)Math.round(salary.getWorkerPay()*0.8));
								break;
							case 54:// 是否撤离
								transforValue = String.valueOf(cellValue).trim();
								if(transforValue.equals("是")){
									work.setLeaveStatus(0); //离职
								}
								else{
									work.setLeaveStatus(1); //在职
								}
								break;
							case 55:// 离职日期
								transforValue = String.valueOf(cellValue).trim();
								if(StringUtils.isNotBlank(transforValue)){
									SimpleDateFormat sdtLeaveWorkingTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//小写的mm表示的是分钟  
									java.util.Date leaveWorkingTime=sdtLeaveWorkingTime.parse(String.valueOf(cellValue).trim());
									work.setLeaveWorkingTime(leaveWorkingTime);
								}
								
								break;
							case 56:// 结算价
								transforValue = String.valueOf(cellValue).trim();
								if(StringUtils.isBlank(transforValue)){
									logger.error("=====导入失败！结算价不能为空=====");
									return "@"+JSONResult.error(CodeMsg.ERROR,"导入失败！结算价不能为空");  
									
								}
								salary.setSettlementPrice(BigDecimal.valueOf(Double.parseDouble(transforValue)));
							
								break;
							case 57:// 邮箱
								transforValue = String.valueOf(cellValue).trim();
								person.setEmail(transforValue);	
								break;
							case 58:// 社保实缴月份
								transforValue = String.valueOf(cellValue).trim();
								salary.setInsuranceRealDate(transforValue);
								break;
						}
	                }  
	                //验证员工信息是否存在
	                int checkNum = personalService.checkPersonalByNameAndCard(person.getName(), person.getIdentityCard());
	                if(checkNum>0){
	                	logger.error("====="+String.format("员工信息已存在,姓名:%s,身份证:%s", person.getName(), person.getIdentityCard())+"=====");
	                	return "@"+JSONResult.error(CodeMsg.ERROR,String.format("员工信息已存在,姓名:%s,身份证:%s", person.getName(), person.getIdentityCard())); 
	                }
	                personalAll.setPersonalInfo(person);
	                personalAll.setPersonalWorkInfo(work);
	                personalAll.setPersonalSalaryInfo(salary);
	                if(!StringUtils.isBlank(person.getName())){
		                personAllList.add(personalAll);
	                }              
			}
	        //批量入库
	        try {
	        	personalService.savePersonalAllListRecord(personAllList);
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
				logger.error("保存数据库异常,已回滚", e);
				return "@"+JSONResult.error(CodeMsg.ERROR,"保存数据库异常,已回滚"+ e.getMessage());  
			}
	        
		}catch(Exception e1){
			e1.printStackTrace();
			logger.error("upload personalInfo throws Exception", e1);
			return "@"+JSONResult.error(CodeMsg.ERROR,"上传员工基本信息文件失败，请稍后重试"+e1);  
		}
		if(result){
			logger.info("adminUser : "+user.getUsername()+"upload timeOutFile ; fileNumber : OT"+fileNumber);
			return "@"+JSONResult.success("导入成功！文件编号为OT"+fileNumber);
		}else{
			logger.error("=====导入失败！请重新导入=====");
			return "@"+JSONResult.error(CodeMsg.ERROR,"导入失败！请重新导入");  
		}
		
	}
	
	/**
     * 
    * Title: getPersonalAllInfoById
    * Description:查询员工基本信息
    * Url: personal/getPersonalAllInfoById
    * @param Integer personalInfoId 员工基本信息ID
    * @return String    
    * @throws
     */
	@AuthorityCheck(function = FunctionIds.FUNCTION_12)
	@NotCareLogin
	@Post("getPersonalAllInfoById")
	@Get("getPersonalAllInfoById")
	public String getPersonalAllInfoById(@Param("personalInfoId")Integer personalInfoId){
		PersonalAll personalAll = personalService.getPersonalAllInfoById(personalInfoId);
		if(personalAll.getPersonalInfo()!=null){
			return "@"+JSONResult.success(personalAll);
		}
		else{
			logger.error("=====未查到员工数据=====");
			return "@"+JSONResult.error(CodeMsg.ERROR,"未查到员工数据");
		}
	}
	
	
	/**
     * 
    * Title: updatePersonalAllInfo
    * Description: 修改员工基本信息
    * Url: personal/updatePersonalAllInfo
    * @param String personalAllJsonStr 员工基本信息json串
    * @return String    
    * @throws
    * @see PersonalAll
     */
	@AuthorityCheck(function = FunctionIds.FUNCTION_12)
	@NotCareLogin
	@Post("updatePersonalAllInfo")
	@Get("updatePersonalAllInfo")
	public String updatePersonalAllInfo(
			@Param("personalAllJsonStr") String personalAllJsonStr) {
		logger.info(personalAllJsonStr);
		if(StringUtils.isBlank(personalAllJsonStr)){
			logger.error("=====参数错误，不应为空====="+personalAllJsonStr);
			return "@" + JSONResult.error(CodeMsg.ERROR,"参数错误，不应为空！");
		}
		PersonalAll newPersonalAll = null;
		try {
			newPersonalAll = JSONObject.parseObject(personalAllJsonStr, PersonalAll.class);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("=====修改员工基本信息，解析参数出错====="+personalAllJsonStr, e);
			return "@" + JSONResult.error(CodeMsg.ERROR,"解析对象出错！");
		}
		//进行修改
		int result =0;
		try {
			result = personalService.updatePersonalAllInfo(newPersonalAll);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("更新数据库异常"+e);
			return "@"+JSONResult.error(CodeMsg.ERROR,"更新数据库异常"+e);
		}
		if(result<0){
			logger.error("根据员工ID未查到基本信息");
			return "@"+JSONResult.error(CodeMsg.ERROR,"根据登陆ID未查到基本信息");
		}
		if(result==0){
			logger.error("更新基本信息失败");
			return "@"+JSONResult.error(CodeMsg.ERROR,"更新基本信息失败");
		}
		return "@"+JSONResult.success();

	}
	
	
	/**
     * 
    * Title: deletePersonalAllInfo
    * Description: 删除员工基本信息
    * Url: personal/deletePersonalAllInfo
    * @param Integer personalInfoId  员工ID
    * @return String    
    * @throws
     */
	@AuthorityCheck(function = FunctionIds.FUNCTION_12)
	@NotCareLogin
	@Post("deletePersonalAllInfo")
	@Get("deletePersonalAllInfo")
	public String deletePersonalAllInfo(
			@Param("personalInfoId")Integer personalInfoId) {

		PersonalAll personalAll = personalService
				.getPersonalAllInfoById(personalInfoId);
		if (personalAll.getPersonalInfo() != null) {
			// 进行逻辑删除
			try {
				int result = personalService.deletePersonalAllInfoById(personalInfoId);
				return "@" + JSONResult.success();
				
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("=====删除员工基本信息异常====="+e);
				return "@" + JSONResult.error(CodeMsg.ERROR, "删除员工基本信息异常,"+e.getMessage());
			}	
		} else {
			logger.error("=====根据员工ID未查到基本信息=====");
			return "@" + JSONResult.error(CodeMsg.ERROR, "根据员工ID未查到基本信息");
		}
	}
	
	/**
     * 
    * title: addAdminByPInfoId
    * description: 分配员工账号；默认密码
    * url: personal/addAdminByid
    * @param   Integer personalInfoId 员工ID
    * @return String    
    * @throws
     */
	@AuthorityCheck(function = FunctionIds.FUNCTION_12)
	@NotCareLogin
	@Post("addAdminByPInfoId")
	@Get("addAdminByPInfoId")
	public String addAdminByPInfoId(
			@Param("personalInfoId")Integer personalInfoId,
			@Param("roleId")Integer roleId,
			@Param("roleName")String roleName) {
		//校验权限ID是否存在

		AdminRole role = adminService.getRole(roleId);
		if(role==null){
			logger.error("=====没有此权限=====");
			return "@" + JSONResult.error(CodeMsg.ERROR, "没有此权限");
		}
		PersonalAll personalAll = personalService
				.getPersonalAllInfoById(personalInfoId);
		if (personalAll.getPersonalInfo() != null) {
			// 进行分配账号
			try {
				Admin admin = adminService.getAdminByInfoId(personalInfoId);
				if(admin!=null){
					logger.error("=====此员工已分配账号=====");
					return "@" + JSONResult.error(CodeMsg.ERROR, "此员工已分配账号");
				}
				else{
					Admin curAdmin = new Admin();
					curAdmin.setUsername(personalAll.getPersonalInfo().getPhone());
					//默认密码手机号+出生月日
					curAdmin.setPassword(MD5Util.GetMD5Code(personalAll.getPersonalInfo().getPhone()+personalAll.getPersonalInfo().getIdentityCard().substring(10, 14)));
					curAdmin.setPersonalInfoId(personalInfoId);
					//默认权限
					curAdmin.setRoleids(","+roleId+",");
					if(StringUtils.isBlank(roleName)){
						curAdmin.setRolenames(role.getRolename());
					}
					else{
						curAdmin.setRolenames(roleName);
					}
					curAdmin.setEmail(personalAll.getPersonalInfo().getEmail());
					curAdmin.setRealname(personalAll.getPersonalInfo().getName());
					curAdmin.setStatus(1);
					curAdmin.setMobilePhone(personalAll.getPersonalInfo().getPhone());
					curAdmin.setCreateTime(new Date());
					int result = (int) adminService.addUser(curAdmin);
					if(result>0){
						return "@" + JSONResult.success();
					}
					else{
						logger.error("=====入库异常,分配失败=====");
						return "@" + JSONResult.error(CodeMsg.ERROR, "入库异常,分配失败");
					}
				}
				
				
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("=====分配员工账号异常====="+e);
				return "@" + JSONResult.error(CodeMsg.ERROR, "分配员工账号异常,"+e);
			}	
		} else {
			logger.error("=====根据员工ID未查到基本信息=====");
			return "@" + JSONResult.error(CodeMsg.ERROR, "根据员工ID未查到基本信息");
		}
	}
	
	/**
     * 
    * Title: getList
    * Description: 根据条件获取员工信息列表
    * Url: personal/getList
    * @param String name, 姓名
    * @param String expatriateUnit,外派单位
    * @param String postType,岗位
    * @param String position,职位
    * @param String level, 级别
    * @param String department,部门
    * @param String center,中心
    * @param String workingPlace,工作地点 
    * @param Integer leaveStatus,离职状态：0：已离职；1：在职  
    * @param String entryStartDate,入职时间查询的开始时间
    * @param String entryEndDate,入职时间查询的截止时间	
    * @param String workerStartDate,转正时间查询的开始时间
    * @param String workerEndDate,转正时间查询的截止时间	
    * @param String leaveStartDate,离职时间查询的开始时间
    * @param String leaveEndDate,离职时间查询的截止时间	
    * @param String birthdayStartDate,生日查询的开始时间
    * @param String birthdayEndDate,生日查询的截止时间	
    * @param int pageIndex, 分页页数
    * @param int pageSize 	行数
    * @return String    
    * @throws
     */
	@AuthorityCheck(function = FunctionIds.FUNCTION_12)
	@NotCareLogin
	@Get("getList")
	@Post("getList")
	public String getPersonalAllList(@Param("name") String name,
			@Param("expatriateUnit") String expatriateUnit,
			@Param("postType") String postType,
			@Param("position") String position,
			@Param("level") String level,
			@Param("department") String department,
			@Param("center") String center,
			@Param("workingPlace") String workingPlace,
			@Param("leaveStatus") Integer leaveStatus, 
			@Param("entryStartDate")String  entryStartDate,
			@Param("entryEndDate")String  entryEndDate,
			@Param("workerStartDate")String  workerStartDate,
			@Param("workerEndDate")String  workerEndDate,
			@Param("leaveStartDate")String  leaveStartDate,
			@Param("leaveEndDate")String  leaveEndDate,
			@Param("birthdayStartDate")String  birthdayStartDate,
			@Param("birthdayEndDate")String  birthdayEndDate,
			@Param("pageIndex") int pageIndex, 
			@Param("pageSize") int pageSize) {
		PersonalCondition condition = new PersonalCondition();
			condition.setName(name);
			condition.setExpatriateUnit(expatriateUnit);
			condition.setPostType(postType);
			condition.setPosition(position);
			condition.setLevel(level);
			condition.setDepartment(department);
			condition.setCenter(center);
			condition.setWorkingPlace(workingPlace);
			condition.setLeaveStatus(leaveStatus);
			SimpleDateFormat sdt=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//小写的mm表示的是分钟  
			Date curEntryStartDate=null;
			Date curEntryEndDate=null;
			Date curWorkerStartDate=null;
			Date curWorkerEndDate=null;
			Date curLeaveStartDate=null;
			Date curLeaveEndDate=null;
			Date curBirthdayStartDate=null;
			Date curBirthdayEndDate=null;
			try {
				if(StringUtils.isNotBlank(entryStartDate)){
					curEntryStartDate = sdt.parse(String.valueOf(entryStartDate).trim());			
				}
				if(StringUtils.isNotBlank(entryEndDate)){
					curEntryEndDate = sdt.parse(String.valueOf(entryEndDate).trim());
				}
				if(StringUtils.isNotBlank(workerStartDate)){
					curWorkerStartDate = sdt.parse(String.valueOf(workerStartDate).trim());			
				}
				if(StringUtils.isNotBlank(workerEndDate)){
					curWorkerEndDate = sdt.parse(String.valueOf(workerEndDate).trim());
				}
				if(StringUtils.isNotBlank(leaveStartDate)){
					curLeaveStartDate = sdt.parse(String.valueOf(leaveStartDate).trim());			
				}
				if(StringUtils.isNotBlank(leaveEndDate)){
					curLeaveEndDate = sdt.parse(String.valueOf(leaveEndDate).trim());
				}
				if(StringUtils.isNotBlank(birthdayStartDate)){
					curBirthdayStartDate = sdt.parse(String.valueOf(birthdayStartDate).trim());			
				}
				if(StringUtils.isNotBlank(birthdayEndDate)){
					curBirthdayEndDate = sdt.parse(String.valueOf(birthdayEndDate).trim());
				}
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				logger.error("=====参数错误，日期格式不对，转换错误=====");
				return "@" + JSONResult.error(CodeMsg.ERROR,"参数错误，日期格式不对，转换错误！");
			}
			condition.setEntryStartDate(curEntryStartDate);
			condition.setEntryEndDate(curEntryEndDate);
			condition.setWorkerStartDate(curWorkerStartDate);
			condition.setWorkerEndDate(curWorkerEndDate);
			condition.setLeaveStartDate(curLeaveStartDate);
			condition.setLeaveEndDate(curLeaveEndDate);
			condition.setBirthdayStartDate(curBirthdayStartDate);
			condition.setBirthdayEndDate(curBirthdayEndDate);
			
		pageIndex = pageIndex < 0 ? 0 : pageIndex;
		pageSize = pageSize < 1 ? 1 : pageSize;
//		condition.setOrderby("id");
		condition.setOffset(pageIndex * pageSize);
		condition.setLimit(pageSize);
		Long count = 0L;
		List<PersonalAllExport> personalLists = new ArrayList<>();
		try {
			personalLists = personalService.listPersonalAllExport(condition);
			count = personalService.countPersonalAllExport(condition);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("=====根据条件获取员工信息列表查询，调用service出错=====", e);
			return "@" + JSONResult.error(CodeMsg.SERVER_ERROR);
		}
		Long pageCount = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;
		Map<String, Object> dataMap = DataMapUtil.getDataMap("personalViewList", personalLists, count, pageCount);
		return "@" + JSONResult.success(dataMap);
	}
	
	/**
     * 
    * Title: exportPersonalAll
    *  根据条件导出员工信息列表
    * Url: personal/exportPersonalAll
    * @param String heads,导出字段中文名集合;逗号间隔;例如(姓名,年龄,生日)
    * @param String columns,导出字段名集合;逗号间隔;例如(name,age,birthday)
    * @param String name, 姓名
    * @param String expatriateUnit,外派单位
    * @param String postType,岗位
    * @param String department,部门
    * @param String center,中心
    * @param String workingPlace,工作地点   
    * @return String    
    * @throws
     */
	@AuthorityCheck(function = FunctionIds.FUNCTION_12)
	@NotCareLogin
	@Get("export")
	@Post("export")
	public String exportPersonalAll(@Param("heads") String heads,
			@Param("columns") String columns,
			@Param("name") String name,
			@Param("expatriateUnit") String expatriateUnit,
			@Param("postType") String postType,
			@Param("department") String department,
			@Param("center") String center,
			@Param("workingPlace") String workingPlace) {
		PersonalCondition condition = new PersonalCondition();
//		if(StringUtils.isNotBlank(name)){
//			// 当查询条件有姓名时，只需要根据姓名查出该单，其他条件忽略
//			condition.setName(name);
//		} else {
			condition.setName(name);
			condition.setExpatriateUnit(expatriateUnit);
			condition.setPostType(postType);
			condition.setDepartment(department);
			condition.setCenter(center);
			condition.setWorkingPlace(workingPlace);
			
//		}
//		condition.setOrderby("name");
		Long count = 0L;
		List<PersonalAllExport> personalLists = new ArrayList<>();
		try {
			personalLists = personalService.listPersonalAllExport(condition);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("=====根据条件获取员工信息列表查询，调用service出错=====", e);
			return "@" + JSONResult.error(CodeMsg.SERVER_ERROR);
		}
		//根据查询条件和字段导出到excel
		Admin admin = (Admin)inv.getRequest().getSession().getAttribute("user");
		String dateStr = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		String fileName = "PersonalAllExport-" + dateStr + ".xlsx";
		HttpServletResponse response = inv.getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");//xlsx
//		response.setContentType("application/vnd.ms-excel");//xls
		List<String> listHeads = StringToListUtil.getStringList(heads, ",");
		List<String> listColumns = StringToListUtil.getStringList(columns, ",");
		OutputStream out = null;
		try {
			out = response.getOutputStream();
			ExportBeanExcel<PersonalAllExport> exportBeanExcelUtil = new ExportBeanExcel();
			exportBeanExcelUtil.exportExcel("员工信息列表",listHeads,listColumns,personalLists,out);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(admin.getRealname() + " 操作导出员工列表文件出错", e);
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
//		return "@" + JSONResult.success();
	}
	

	/**
     * 
    * title: addLeaveInfo
    * description: 办理离职
    * url: personal/addLeaveInfo
    * @param  Integer personalInfoId,
	* @param  Integer leaveType,
	* @param  String leaveReason,
	* @param  String leaveWorkingTime
    * @return String    
    * @throws
     */
	@AuthorityCheck(function = FunctionIds.FUNCTION_12)
	@NotCareLogin
	@Post("addLeaveInfo")
	@Get("addLeaveInfo")
	public String addLeaveInfo(
			@Param("personalInfoId")Integer personalInfoId,
			@Param("leaveType")Integer leaveType,
			@Param("leaveReason")String leaveReason,
			@Param("leaveWorkingTime")String leaveWorkingTime) {
		SimpleDateFormat sdt=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//小写的mm表示的是分钟  
		Date curLeaveTime=null;
		try {
			if(StringUtils.isNotBlank(leaveWorkingTime)){
				curLeaveTime = sdt.parse(String.valueOf(leaveWorkingTime).trim());			
			}
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			logger.error("=====参数错误，日期格式不对，转换错误=====");
			return "@" + JSONResult.error(CodeMsg.ERROR,"参数错误，日期格式不对，转换错误！");
		}
		// 验证员工信息是否存在
		PersonalAll personalAll = personalService
				.getPersonalAllInfoById(personalInfoId);
		if (personalAll.getPersonalWorkInfo() != null) {
			// 验证员工离职状态是否为在职
			try {
				if(!personalAll.getPersonalWorkInfo().getLeaveStatus().equals(1)){
					logger.error("=====此员工已不是在职状态=====");
					return "@" + JSONResult.error(CodeMsg.ERROR, "此员工已不是在职状态");
				}
				else{
					PersonalWorkInfo work = new PersonalWorkInfo();
					work= personalAll.getPersonalWorkInfo();
					work.setLeaveStatus(0); //离职状态
                    work.setLeaveType(leaveType);
                    work.setLeaveReason(leaveReason);
                    work.setLeaveWorkingTime(curLeaveTime);
                    work.setUpdateTime(new Date());
					int result = (int) personalService.addLeaveInfo(work);
					if(result>0){
						return "@" + JSONResult.success();
					}
					else{
						logger.error("=====入库异常,办理离职失败=====");
						return "@" + JSONResult.error(CodeMsg.ERROR, "入库异常,办理离职失败");
					}
				}
				
				
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("=====办理离职异常====="+e);
				return "@" + JSONResult.error(CodeMsg.ERROR, "办理离职异常,"+e);
			}	
		} else {
			logger.error("=====根据员工ID未查到基本信息=====");
			return "@" + JSONResult.error(CodeMsg.ERROR, "根据员工ID未查到基本信息");
		}
	}
	
}
