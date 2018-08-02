package com.hr.manage.web.controllers;

import hr.manage.component.admin.model.Admin;
import hr.manage.component.admin.service.AdminService;
import hr.manage.component.personal.model.PersonalAll;
import hr.manage.component.personal.model.PersonalInfo;
import hr.manage.component.personal.model.PersonalSalaryInfo;
import hr.manage.component.personal.model.PersonalWorkInfo;
import hr.manage.component.personal.service.PersonalService;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
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

import com.google.gson.Gson;
import com.google.gson.internal.bind.BigIntegerTypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.hr.manage.web.annotation.AuthorityCheck;
import com.hr.manage.web.annotation.NotCareLogin;
import com.hr.manage.web.constant.CodeMsg;
import com.hr.manage.web.constant.FunctionIds;
import com.hr.manage.web.constant.JSONResult;
import com.hr.manage.web.util.DateTimeUtil;
import com.hr.manage.web.util.MD5Util;
import com.hr.manage.web.util.StringUtil;

@Path("")
public class PersonalController {

	@Autowired
	Invocation inv;
	@Autowired
	PersonalService personalService;
	
	private final Log logger = LogFactory.getLog(PersonalController.class);
	private static final String FILE_UPLOAD_URL="C:/data/uploadfile/";
	
	/**
     * 
    * @Title: importExcel
    * @Description: 导入员工基本信息excel
    * @Url: personal/importExcel
    * @Param @Param("filedata")MultipartFile filedata
    * @param @return    
    * @return String    
    * @throws
     */
	@AuthorityCheck(function = FunctionIds.FUNCTION_12)
	@NotCareLogin
	@Post("personal/importExcel")
	@Get("personal/importExcel")
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
			String filepath = FILE_UPLOAD_URL;
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
	                    	if (DateUtil.isCellDateFormatted(cell)) {
								Date d = cell.getDateCellValue(); // 对日期处理
								DateFormat formater = new SimpleDateFormat(
										"yyyy-MM-dd hh:mm:ss");
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
								if(!transforValue.equals("")){
									SimpleDateFormat sdtentry=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//小写的mm表示的是分钟  
									java.util.Date entryrdate=sdtentry.parse(String.valueOf(cellValue).trim());
									salary.setEntryTime(entryrdate);
								}
								
								break;
							case 14:// 到岗时间
								transforValue = String.valueOf(cellValue).trim();
								if(!transforValue.equals("")){
									SimpleDateFormat sdtarray=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//小写的mm表示的是分钟  
									java.util.Date arraydate=sdtarray.parse(String.valueOf(cellValue).trim());
									salary.setArrivalTime(arraydate);
								}
								
								break;
							case 15:// 转正时间
								transforValue = String.valueOf(cellValue).trim();
								if(!transforValue.equals("")){
									SimpleDateFormat sdtworker=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//小写的mm表示的是分钟  
									java.util.Date workerdate=sdtworker.parse(String.valueOf(cellValue).trim());
									salary.setWorkerTime(workerdate);
								}
								

								break;
							case 16:// 工齡=(TODAY()-到崗時間)/365
								double f1 = new BigDecimal((float)DateTimeUtil.differentDaysByMillisecond(salary.getArrivalTime(),new Date())/365).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
								
								salary.setWorkingYears(BigDecimal.valueOf(f1));
								break;
							case 17:// 缴纳社保起始月份
								transforValue = String.valueOf(cellValue).trim();
								salary.setInsuranceBeginDate(transforValue);
								break;
							case 18:// 社保缴纳地点
								transforValue = String.valueOf(cellValue).trim();
								salary.setInsurancePlace(transforValue);
								break;
							case 19:// 合同签署次数
								transforValue = String.valueOf(cellValue).trim();
								work.setContractNumber(Integer.parseInt(transforValue));
								break;
							case 20:// 合同签订日期
								transforValue = String.valueOf(cellValue).trim();
								if(!transforValue.equals("")){
									SimpleDateFormat sdtContractDate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//小写的mm表示的是分钟  
									java.util.Date contractDate=sdtContractDate.parse(String.valueOf(cellValue).trim());
									work.setContractDate(contractDate);
									work.setContractStartdate(contractDate);
								}
								
								break;
							case 21:// 合同失效日期
								transforValue = String.valueOf(cellValue).trim();
								if(!transforValue.equals("")){
									SimpleDateFormat sdtContractEnddate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//小写的mm表示的是分钟  
									java.util.Date contractEnddate=sdtContractEnddate.parse(String.valueOf(cellValue).trim());
									work.setContractEnddate(contractEnddate);
								}
								
								break;
							case 22:// 续签合同日期
								transforValue = String.valueOf(cellValue).trim();
								if(!transforValue.equals("")){
									SimpleDateFormat sdtContractRenewDate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//小写的mm表示的是分钟  
									java.util.Date contractRenewDate=sdtContractRenewDate.parse(String.valueOf(cellValue).trim());
									work.setContractRenewDate(contractRenewDate);
								}
								
								break;
							case 23:// 续签合同失效日期
								transforValue = String.valueOf(cellValue).trim();
								if(!transforValue.equals("")){
									SimpleDateFormat sdtContractRenewEnddate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//小写的mm表示的是分钟  
									java.util.Date contractRenewEnddate=sdtContractRenewEnddate.parse(String.valueOf(cellValue).trim());
									work.setContractRenewEnddate(contractRenewEnddate);
								}
								
								break;
							case 24:// 身份证411024199101118518
								transforValue = String.valueOf(cellValue).trim();
								person.setIdentityCard(transforValue);
								break;
							case 25:// 年龄=YEAR(TODAY())-MID(身份证,7,4)
								//取生日
								String strBirthDay=person.getIdentityCard().substring(6, 14);
								SimpleDateFormat sdtAge=new SimpleDateFormat("yyyyMMdd");
								java.util.Date birthDay=sdtAge.parse(strBirthDay);
								transforValue = String.valueOf(DateTimeUtil.yearDateDiff(birthDay,new Date())).trim();
								person.setAge(Integer.parseInt(transforValue));
								break;
							case 26:// 性别=IF(MOD(MID(身份证,15,3),2),"男","女")
								if (Integer.parseInt(person.getIdentityCard().substring(16).substring(0, 1)) % 2 == 0) {// 判断性别
									transforValue = "女";
								} else {
									transforValue = "男";
								}
								person.setSex(transforValue);
								break;
							case 27:// 出生日期=MID(身份证,11,2)&"月"&MID(身份证,13,2)&"日"
								transforValue = person.getIdentityCard().substring(10, 12)+"月"+person.getIdentityCard().substring(12, 14)+"日";
								person.setBirthday(transforValue);
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
								if(!transforValue.equals("")){
									SimpleDateFormat sdtGraduationTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//小写的mm表示的是分钟  
									Date graduationTime=sdtGraduationTime.parse(String.valueOf(cellValue).trim());
									person.setGraduationTime(graduationTime);
								}
								
								break;
							case 38:// 工作年限=DATEDIF(毕业时间,TODAY(),"Y")&"年"
								transforValue = String.valueOf(DateTimeUtil.yearDateDiff(person.getGraduationTime(),new Date())+"年").trim();
								person.setWorkingLife(transforValue);
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
							case 45://工作地点 
								transforValue = String.valueOf(cellValue).trim();
								work.setWorkingPlace(transforValue);
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
								salary.setBasePay(Integer.parseInt(transforValue));
								break;
							case 50:// 绩效工资
								transforValue = String.valueOf(cellValue).trim();
								salary.setMeritPay(Integer.parseInt(transforValue));
								break;
							case 51:// 补贴
								transforValue = String.valueOf(cellValue).trim();
								salary.setSubsidy(Integer.parseInt(transforValue));
								break;
							case 52:// 转正工资=基本工资+绩效工资+补贴
//								transforValue = String.valueOf(cellValue).trim();
								salary.setWorkerPay(salary.getBasePay()+salary.getMeritPay()+salary.getSubsidy());
								break;
							case 53:// 试用期工资=转正工资*80%
//								transforValue = String.valueOf(cellValue).trim();
								salary.setProbationaryPay((int)Math.round(salary.getWorkerPay()*0.8));
								break;
							case 54:// 是否撤离
								transforValue = String.valueOf(cellValue).trim();
								work.setIsLeave(transforValue);
								break;
							case 55:// 离职日期
								transforValue = String.valueOf(cellValue).trim();
								if(!transforValue.equals("")){
									SimpleDateFormat sdtLeaveWorkingTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//小写的mm表示的是分钟  
									java.util.Date leaveWorkingTime=sdtLeaveWorkingTime.parse(String.valueOf(cellValue).trim());
									work.setLeaveWorkingTime(leaveWorkingTime);
								}
								
								break;
							case 56:// 结算价
								transforValue = String.valueOf(cellValue).trim();
								if(!transforValue.equals("")){
									salary.setSettlementPrice(BigDecimal.valueOf(Double.parseDouble(transforValue)));
								}
								
								break;
							case 57:// 邮箱
								transforValue = String.valueOf(cellValue).trim();
								person.setEmail(transforValue);	
								break;
						}
	                }  
	                //验证员工信息是否存在
	                int checkNum = personalService.checkPersonalByNameAndCard(person.getName(), person.getIdentityCard());
	                if(checkNum>0){
	                	return "@"+JSONResult.error(CodeMsg.ERROR,String.format("员工信息已存在,姓名:%s,身份证:%s", person.getName(), person.getIdentityCard())); 
	                }
	                personalAll.setPersonalInfo(person);
	                personalAll.setPersonalWorkInfo(work);
	                personalAll.setPersonalSalaryInfo(salary);
	                personAllList.add(personalAll);              
			}
	        //批量入库
	        try {
	        	personalService.savePersonalAllListRecord(personAllList);
			} catch (Exception e) {
				// TODO: handle exception
				logger.error("保存数据库异常,已回滚", e);
				return "@"+JSONResult.error(CodeMsg.ERROR,"保存数据库异常,已回滚"+ e);  
			}
	        
		}catch(Exception e){
			logger.error("upload personalInfo throws Exception", e);
			return "@"+JSONResult.error(CodeMsg.ERROR,"上传员工基本信息文件失败，请稍后重试"+e);  
		}
		if(result){
			logger.info("adminUser : "+user.getUsername()+"upload timeOutFile ; fileNumber : OT"+fileNumber);
			return "@"+JSONResult.success("导入成功！文件编号为OT"+fileNumber);
		}else{
			return "@"+JSONResult.error(CodeMsg.ERROR,"导入失败！请重新导入");  
		}
		
	}
	
	
}
