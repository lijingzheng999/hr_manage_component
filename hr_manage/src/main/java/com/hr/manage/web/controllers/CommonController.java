package com.hr.manage.web.controllers;

import hr.manage.component.admin.model.Admin;
import hr.manage.component.admin.service.AdminService;
import hr.manage.component.common.model.CommonType;
import hr.manage.component.common.model.UploadFile;
import hr.manage.component.common.model.UploadResult;
import hr.manage.component.common.service.CommonService;
import hr.manage.component.personal.model.PersonalAll;

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
	
}
