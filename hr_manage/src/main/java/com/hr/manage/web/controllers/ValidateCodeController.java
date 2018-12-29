package com.hr.manage.web.controllers;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hr.manage.web.annotation.NotCareLogin;
import com.hr.manage.web.util.CreateValidateCode;


@Path("validate")
public class ValidateCodeController {
	
	private static final Log LOGGER = LogFactory.getLog(ValidateCodeController.class);
	
	/**
     * 
    * @Title: create
    * @Description: 生成验证码
    * @Url: validate/create
    * @Param   
    * @return     
    * @throws
     */
	@NotCareLogin
	@Get("create")
	@Post("create")
	public void create(Invocation inv) {
		//RandomValidateCode randomValidateCode = new RandomValidateCode();
		CreateValidateCode createValidateCode = new CreateValidateCode(108,30,4,20);
		try {
		//randomValidateCode.getRandcode(inv.getRequest(), inv.getResponse());// 输出图片方法
			createValidateCode.printValidateCode(inv.getRequest(), inv.getResponse());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(e);
		}
	}
}
