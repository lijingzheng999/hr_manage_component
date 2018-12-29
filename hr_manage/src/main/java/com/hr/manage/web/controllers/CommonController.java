package com.hr.manage.web.controllers;

import hr.manage.component.admin.model.Admin;
import hr.manage.component.admin.service.AdminService;
import hr.manage.component.common.model.CommonType;
import hr.manage.component.common.model.SettingHoliday;
import hr.manage.component.common.model.UploadFile;
import hr.manage.component.common.model.UploadResult;
import hr.manage.component.common.service.CommonService;
import hr.manage.component.contract.model.ContractInfo;
import hr.manage.component.personal.model.PersonalAll;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;














import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hr.manage.config.ServiceConfigFactory;
import com.hr.manage.web.annotation.AuthorityCheck;
import com.hr.manage.web.annotation.NotCareLogin;
import com.hr.manage.web.constant.CodeMsg;
import com.hr.manage.web.constant.FunctionIds;
import com.hr.manage.web.constant.JSONResult;
import com.hr.manage.web.util.DataMapUtil;
import com.hr.manage.web.util.MD5Util;
import com.hr.manage.web.util.UploadUtil;

/**
 * 
* @see 通用相关API 配置码表附件相关
* @see 统一返回JSON串 code,message,data
* @author  lee
* @version 1.0
 */
@Path("common")
public class CommonController {
	private final Log logger = LogFactory.getLog(CommonController.class);
	
	@Autowired
	Invocation inv;
	@Autowired
	CommonService commonService;
	
	/**
     * 
    * Title: listCommonType
    * Description: 获取下拉框码表
    * Url: common/listCommonType
    * @param Integer type 类型,1:级别；2：所在部门；3：归属中心；4：外派单位； 
    * @return String    
    * @throws
     */
	@NotCareLogin
	@Post("listCommonType")
	@Get("listCommonType")
	public String listCommonType(@Param("type") Integer type){
		List<CommonType> listCommonType = commonService.listCommonType(type);
		return "@"+JSONResult.success(listCommonType);
	}
	
	/**
     * 
    * Title: deleteUploadFile
    * Description: 删除附件
    * Url: common/deleteUploadFile
    * @param Integer uploadFileId  附件ID
    * @return String    
    * @throws
     */
	@AuthorityCheck(function = FunctionIds.FUNCTION_11)
	@NotCareLogin
	@Post("deleteUploadFile")
	@Get("deleteUploadFile")
	public String deleteUploadFile(@Param("fileId") Integer uploadFileId){
		if(uploadFileId==null ){
			logger.error("=====uploadFileId参数错误，不应为空=====");
			return "@" + JSONResult.error(CodeMsg.ERROR,"uploadFileId参数错误，不应为空！");
		}
		int result = commonService.deleteUploadFile(uploadFileId);
		if(result>0){
			return "@"+JSONResult.success();
		}
		else{
			return "@" + JSONResult.error(CodeMsg.ERROR, "删除附件失败");
		}
	}
	
	
	/**
     * 
    * Title: saveUploadFile
    * Description: 上传附件
    * Url: common/saveUploadFile
    * @param Integer personalInfoId   员工ID
    * @param MultipartFile uploadFile 上传的文件
    * @param @return    
    * @return String    UploadResult的json
    * @see UploadResult
    * 
    * @throws
     */
	@AuthorityCheck(function = FunctionIds.FUNCTION_11)
	@NotCareLogin
	@Post("saveUploadFile")
	@Get("saveUploadFile")
	public String saveUploadFile(@Param("personalInfoId") Integer personalInfoId,@Param("uploadFile")MultipartFile uploadFile){
		if(personalInfoId==null ){
			logger.error("=====personalInfoId参数错误，不应为空=====");
			return "@" + JSONResult.error(CodeMsg.ERROR,"personalInfoId参数错误，不应为空！");
		}
		String resultJson;
		try {
			resultJson = UploadUtil.upload(uploadFile);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "@" + JSONResult.error(CodeMsg.ERROR,"保存附件文件异常！");
			
		}
		UploadResult uploadResult = JSONObject.parseObject(resultJson, UploadResult.class);
		if(!uploadResult.getInfo().equals("OK")){
			return "@" + JSONResult.error(CodeMsg.ERROR,"保存附件文件异常！");
		}
		UploadFile uploadfile = new UploadFile();
		uploadfile.setPersonalInfoId(personalInfoId);
		uploadfile.setType(1);
		uploadfile.setFileName(uploadResult.getName());
		uploadfile.setFileUrl(uploadResult.getFilePath());
		uploadfile.setFileType("");
		uploadfile.setComment(uploadResult.getDownloadUrl());
		uploadfile.setIsDel(1);
		int result = commonService.saveUploadFile(uploadfile);
		if(result>0){
			return "@"+JSONResult.success(resultJson);
		}
		else{
			return "@" + JSONResult.error(CodeMsg.ERROR, "保存数据库失败");
		}
	}
		
	/**
     * 
    * Title: listUploadFile
    * Description: 获取附件列表
    * Url: common/listUploadFile
    * @param Integer personalInfoId 员工ID
    * @param Integer type 附件类型;空为查全部,1为员工基本信息的图片
    * @return List<UploadFile>    
    * @throws
     */
	@AuthorityCheck(function = FunctionIds.FUNCTION_11)
	@NotCareLogin
	@Post("listUploadFile")
	@Get("listUploadFile")
	public String listUploadFile(@Param("personalInfoId") Integer personalInfoId,@Param("type") Integer  type){
		List<UploadFile> listUploadFile = commonService.listUploadFile(personalInfoId,type);
		return "@"+JSONResult.success(listUploadFile);
	}
	
	/**
     * 
    * Title: listSettingHoliday
    * Description: 获取节假日设置列表
    * Url: common/listSettingHoliday
    * @param Integer type,Date startDate, Date endDate 
    * @param int pageIndex, 分页页数
    * @param int pageSize 	行数
    * @return List<SettingHoliday>    
    * @throws
     */
	@AuthorityCheck(function = FunctionIds.FUNCTION_14)
	@NotCareLogin
	@Post("listSettingHoliday")
	@Get("listSettingHoliday")
	public String listSettingHoliday(@Param("type") Integer type,@Param("startDate") String startDate,
			@Param("endDate") String endDate,@Param("pageIndex") int pageIndex, 
			@Param("pageSize") int pageSize){
		SimpleDateFormat sdt=new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟  
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
		pageIndex = pageIndex < 0 ? 0 : pageIndex;
		pageSize = pageSize < 1 ? 1 : pageSize;
		Integer curOffset =pageIndex * pageSize;
		Integer curLimit=pageSize;
		Long count = 0L;
		List<SettingHoliday> listSettingHoliday = new ArrayList<>();
		try {
			listSettingHoliday = commonService.listSettingHoliday(type,curStartDate,curEndDate,curOffset,curLimit);
			count = commonService.countSettingHoliday(type,curStartDate,curEndDate);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("=====根据条件获取节假日信息列表查询，调用service出错=====", e);
			return "@" + JSONResult.error(CodeMsg.SERVER_ERROR);
		}
		Long pageCount = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;
		Map<String, Object> dataMap = DataMapUtil.getDataMap("listSettingHoliday", listSettingHoliday, count, pageCount);
		return "@" + JSONResult.success(dataMap);
//		List<SettingHoliday> listSettingHoliday = commonService.listSettingHoliday(type,curStartDate,curEndDate);
//		return "@"+JSONResult.success(listSettingHoliday);
	}
	

	/**
     * 
    * Title: deleteSettingHoliday
    * Description: 删除节假日设置
    * Url: common/deleteSettingHoliday
    * @param Integer holiDayId  节假日设置ID
    * @return String    
    * @throws
     */
	@AuthorityCheck(function = FunctionIds.FUNCTION_14)
	@NotCareLogin
	@Post("deleteSettingHoliday")
	@Get("deleteSettingHoliday")
	public String deleteSettingHoliday(@Param("holiDayId") Integer holiDayId){
		if(holiDayId==null ){
			logger.error("=====holiDayId参数错误，不应为空=====");
			return "@" + JSONResult.error(CodeMsg.ERROR,"holiDayId参数错误，不应为空！");
		}
		int result = commonService.deleteSettingHoliday(holiDayId);
		if(result>0){
			return "@"+JSONResult.success();
		}
		else{
			return "@" + JSONResult.error(CodeMsg.ERROR, "删除节假日设置失败");
		}
	}
	
	/**
     * 
    * Title: saveSettingHoliday
    * Description: 上传附件
    * Url: common/saveSettingHoliday
    * @param Integer type  日期类型 1标记为普通工作日 2标记为周末 3标记为节假日
    * @param String curDate  具体日期
    * @param @return    
    * @return String    UploadResult的json
    * @see UploadResult
    * 
    * @throws
     */
	@AuthorityCheck(function = FunctionIds.FUNCTION_11)
	@NotCareLogin
	@Post("saveSettingHoliday")
	@Get("saveSettingHoliday")
	public String saveSettingHoliday(@Param("type") Integer type,@Param("curDate")String curDate){
		if(type==null ){
			logger.error("=====type参数错误，不应为空=====");
			return "@" + JSONResult.error(CodeMsg.ERROR,"type参数错误，不应为空！");
		}
		SimpleDateFormat sdt=new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟  
		Date curSetDate=null;
		try {
			if(StringUtils.isNotBlank(curDate)){
				curSetDate = sdt.parse(String.valueOf(curDate).trim());			
			}
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			logger.error("=====参数错误，日期格式不对，转换错误=====");
			return "@" + JSONResult.error(CodeMsg.ERROR,"参数错误，日期格式不对，转换错误！");
		}
		
		SettingHoliday holiday = new SettingHoliday();
		holiday.setType(type);
		holiday.setCurDate(curSetDate);
		holiday.setCreateTime(new Date());
		holiday.setIsDel(1);
		int result = commonService.saveSettingHoliday(holiday);
		if(result>0){
			return "@"+JSONResult.success();
		}
		else{
			return "@" + JSONResult.error(CodeMsg.ERROR, "保存数据库失败");
		}
	}
}
