package com.hr.manage.web.controllers;

import hr.manage.component.admin.model.Admin;
import hr.manage.component.admin.service.AdminAuthorityServiceHome;
import hr.manage.component.admin.service.AdminService;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.hr.manage.web.annotation.AuthorityCheck;
import com.hr.manage.web.annotation.NotCareLogin;
import com.hr.manage.web.constant.CodeMsg;
import com.hr.manage.web.constant.Constants;
import com.hr.manage.web.constant.FunctionIds;
import com.hr.manage.web.constant.JSONResult;
import com.hr.manage.web.util.MD5Util;

/**
 * 
* @see 员工登陆相关API;
* @see 统一返回JSON串 code,message,data
* @author  lee
* @version 1.0
 */
@Path("")
public class HomeController {

	private final Log logger = LogFactory.getLog(getClass());
//	private static final String KEY = ServiceConfigFactory.getValue("checkKey");
	
	@Autowired
	Invocation inv;
	
	@Autowired
	AdminService adminService;

	@Autowired
	private AdminAuthorityServiceHome authorityServiceHome;
	
	
	@NotCareLogin
	@Get("")
	public String none(){
		return "@runing";
	}

	/**
     * 
    * Url: login
    * 登陆
    * @param String username 用户名
    * @param String password 密码
    * @param String captcha 验证码
    * @return String    
    * @throws
     */
	@NotCareLogin
	@Post("login")
	@Get("login")
	public String login(@Param("username") String username, 
			@Param("password") String password,
			@Param("captcha") String captcha){
		HttpSession userSession = inv.getRequest().getSession();
//		if(userSession.getAttribute("validate_code")==null){
//          return "@"+JSONResult.error(CodeMsg.ERROR,"请等待验证码加载完成！");
//		}
//		String code = userSession.getAttribute("validate_code").toString();
//		if( "".equals(captcha)  || captcha==null ){
//	        return "@"+JSONResult.error(CodeMsg.ERROR,"请输入验证码！");
//			
//		}
//		//测试时if条件置为false
//		if( !code.equalsIgnoreCase(captcha)){
//		    return "@"+JSONResult.error(CodeMsg.ERROR,"验证码错误");
//		}
		Admin user = adminService.getAdminByUsername(username);
		if (user == null){
			return "@"+JSONResult.error(CodeMsg.ERROR,"用户不存在");
		} 
		if(user.getStatus() == 0){
			return "@"+JSONResult.error(CodeMsg.ERROR,"该用户已注销");
		}
		
		String md5Password = user.getPassword();
		if(!md5Password.equals(MD5Util.GetMD5Code(password))){
			return "@"+JSONResult.error(CodeMsg.ERROR,"密码错误");
		}
		userSession.setAttribute("user", user);
		userSession.setAttribute(Constants.LOGIN_USER_ID_TAG_FOR_AUTH, String.valueOf(user.getUserid()));
//		userSession.setAttribute("loginUserId", admin.getUserid());
		userSession.setAttribute("lastloginip", user.getLastloginip());
		userSession.setAttribute("lastlogintime", user.getLastlogintime());
		user.setLastloginip(this.getIpAddr(inv.getRequest()));
		user.setLastlogintime(new Date());
		adminService.updateUser(user);
		logger.info(" operater  do : user "+user.getUsername()+"  login  hr-manage " );
		//获取权限列表
		List<Integer> functionList= authorityServiceHome.getFunctionIds(user.getUserid());
		return "@"+JSONResult.success(functionList);
	}
	
	// 权限测试1
		@AuthorityCheck(function = FunctionIds.FUNCTION_22)
		@Get("testauthority")
		public String AdminAuthority() {
			return "@hi,you have authority";
		}
		
		// 权限测试2
			@AuthorityCheck(function = 50)
			@Get("testauthority2")
			public String AdminAuthority2() {
				return "@hi,you have  authority";
			}
			
//	
//	private String loginPageSelect(Invocation inv){
//		Admin user = (Admin) inv.getRequest().getSession().getAttribute("user");
//		if(user != null){
//			return "r:" + inv.getRequest().getContextPath() + "/index";
//		}else{
//			return "login";
//		}
//	}
			@NotCareLogin
			@Get("testimport")
		    public String testimport() {
		    	return "batch-add";
		    }
			@NotCareLogin
			@Get("testimportkaoqin")
		    public String testimportkaoqin() {
		    	return "batch-add-kaoqin";
		    }
			@NotCareLogin
			@Get("testimportshebao")
		    public String testimportshebao() {
		    	return "batch-add-shebao";
		    }
			@NotCareLogin
			@Get("testupload")
		    public String testupload() {
		    	return "upload-image";
		    }
	@Get("index")
    public String index() {
    	return "index";
    }
	
	@Get("empty")
    public String empty() {
    	return "empty";
    }
	
	@Get("logout")
    public String logout() {
		inv.getRequest().getSession().invalidate();
    	return "r:"+inv.getRequest().getContextPath()+"/login";
    }
	

	
	//取得用户IP
	public String getIpAddr(HttpServletRequest request) {  
		 String ip = request.getHeader("x-forwarded-for");  
		 if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
		  ip = request.getHeader("Proxy-Client-IP");  
		 }  
		 if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
		  ip = request.getHeader("WL-Proxy-Client-IP");  
		 }  
		 if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
		  ip = request.getRemoteAddr();  
		 }  
		 if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
		  ip = request.getHeader("http_client_ip");  
		 }  
		 if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
		  ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
		 }  
		 // 如果是多级代理，那么取第一个ip为客户ip  
		 if (ip != null && ip.indexOf(",") != -1) {  
		  ip = ip.substring(ip.lastIndexOf(",") + 1, ip.length()).trim();  
		 }  
		 return ip;  
		} 
	
	
}
