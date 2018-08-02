//package com.hr.manage.web.controllers;
//
//import java.io.UnsupportedEncodingException;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import net.paoding.rose.web.Invocation;
//import net.paoding.rose.web.annotation.Param;
//import net.paoding.rose.web.annotation.Path;
//import net.paoding.rose.web.annotation.rest.Get;
//import net.paoding.rose.web.annotation.rest.Post;
//
//import org.apache.commons.lang.StringUtils;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//
//
//
//
//
//import com.google.gson.Gson;
//import com.hr.manage.web.annotation.AuthorityCheck;
//import com.hr.manage.web.constant.FunctionIds;
//
//@Path("authority")
//public class AuthorityController {
//	
//	private static final int PAGESIZE = 10;
//	private static final int ADMIN = 1;
//	private static final String MSG = "message";
//	private static final String SUC = "success";
//	private final Log logger = LogFactory.getLog(getClass());
//
//	@Autowired
//	SupplierHome supplierHome;
//
//	@Autowired
//	ProductHome productHome;
//
//	@Autowired
//	AdminService adminService;
//
//	@Autowired
//	AdminAuthorityServiceHome authorityServiceHome;
//	
//	@Autowired
//	SpFunctionService spService;
//	
//	@Autowired
//	Invocation inv;
//
//	// 权限管理M
//	@AuthorityCheck(function = FunctionIds.FUNCTION_1)
//	@Get("userlist")
//	@Post("userlist")
//	public String AdminAuthority(@Param("page") int page) {
//		return "a:/adminManage";
//	}
//
//	// 用户管理
//	@AuthorityCheck(function = FunctionIds.FUNCTION_2)
//	@Post("adminManage")
//	@Get("adminManage")
//	public String getAdminList(@Param("page") int page) {
//		logger.info("authority/adminManage start...");
//		logger.info("page=" + page);
//		try {
//			int userid = Integer.parseInt((String) inv.getRequest()
//					.getSession()
//					.getAttribute(Constants.LOGIN_USER_ID_TAG_FOR_AUTH));
//			List<Integer> functionIds = authorityServiceHome
//					.getFunctionIds(userid);
//			Map<String, Boolean> functionMap = new HashMap<String, Boolean>();
//			functionMap.put("function_" + FunctionIds.FUNCTION_9, AdminUtil
//					.haveSomeAuthority(functionIds, FunctionIds.FUNCTION_9));// 修改用户权限，按钮级，给前端js控制
//			inv.addModel("functionMap", new Gson().toJson(functionMap));
//		} catch (Exception e) {
//			logger.info("authority/adminManage error: \n" + e);
//		}
//		logger.info("authority/adminManage end...");
//		return "authority/adminManage";
//	}
//
//	@AuthorityCheck(function = 1)
//	@Post("searchAdmin")
////	@Get("searchAdmin")
//	public String searchAdmin(@Param("username") String username,
//			@Param("realname") String realname,
//			@Param("mobilePhone") String mobilePhone,
//			@Param("email") String email, @Param("rolename") String rolename,
//			@Param("page") int page) {
//		logger.info("authority/searchAdmin start...");
//		logger.info("username=" + username + "&realname=" + realname
//				+ "&mobilePhone=" + mobilePhone + "&email" + email
//				+ "&rolename=" + rolename + "&page=" + page);
//		Map<String, Object> map = new HashMap<String, Object>();
//		try {
//			int userid = Integer.parseInt((String) inv.getRequest()
//					.getSession()
//					.getAttribute(Constants.LOGIN_USER_ID_TAG_FOR_AUTH));
//			if (userid == ADMIN) {
//				List<Admin> admins = adminService.searchAdmin(username,
//						realname, mobilePhone, email, rolename, page, PAGESIZE);
//				map.put("admins", admins);
//			} else {
//				List<Integer> functionIds = authorityServiceHome
//						.getFunctionIds(userid);
//				if (!AdminUtil.haveSomeAuthority(functionIds,
//						FunctionIds.FUNCTION_2)
//						&& !AdminUtil.haveSomeAuthority(functionIds,
//								FunctionIds.FUNCTION_3)) {
//					return "@" + "你没有该权限！";
//				}
//				map = authorityServiceHome.findAuthorityFunctionLessThanUser(
//						userid, username, realname, mobilePhone, email,
//						rolename, page, PAGESIZE);
//			}
//		} catch (Exception e) {
//			logger.info("authority/searchAdmin error: \n" + e);
//		}
//		logger.info("authority/searchAdmin end... json:"
//				+ new Gson().toJson(map));
//		return "@" + new Gson().toJson(map);
//	}
//
//	@AuthorityCheck(function = 1)
//	@Post("searchAdminCount")
////	@Get("searchAdminCount")
//	public String getSearchAdminCount(@Param("username") String username,
//			@Param("realname") String realname,
//			@Param("mobilePhone") String mobilePhone,
//			@Param("email") String email, @Param("rolename") String rolename,
//			@Param("page") int page) {
//		logger.info("authority/searchAdminCount start...");
//		logger.info("username=" + username + "&realname=" + realname
//				+ "&mobilePhone=" + mobilePhone + "&email" + email
//				+ "&rolename=" + rolename + "&page=" + page);
//		Map<String, Object> map = new HashMap<String, Object>();
//		try {
//			int userid = Integer.parseInt((String) inv.getRequest()
//					.getSession()
//					.getAttribute(Constants.LOGIN_USER_ID_TAG_FOR_AUTH));
//			int count = 0;
//			if (userid == ADMIN) {
//				count = adminService.getSearchAdminCount(username, realname,
//						mobilePhone, email, rolename);
//				map.put("count", count);
//				int pageCount = count % PAGESIZE == 0 ? count / PAGESIZE
//						: count / PAGESIZE + 1;
//				map.put("pageCount", pageCount);
//			} else
//				map = authorityServiceHome.findAuthorityFunctionLessThanUser(
//						userid, username, realname, mobilePhone, email,
//						rolename, page, PAGESIZE);
//		} catch (Exception e) {
//			logger.info("authority/searchAdminCount error..." + e);
//		}
//		logger.info("authority/searchAdminCount end... json:"
//				+ new Gson().toJson(map));
//		return "@" + new Gson().toJson(map);
//	}
//
//	@AuthorityCheck(function = FunctionIds.FUNCTION_6)
//	@Post("addAdmin")
//	public String addAdmin(@Param("username") String username,
//			@Param("password1") String password1,
//			@Param("password2") String password2,
//			@Param("realname") String realname,
//			@Param("mobilePhone") String mobilePhone,
//			@Param("email") String email) {
//		String userid =(String) inv.getRequest().getSession().getAttribute(Constants.LOGIN_USER_ID_TAG_FOR_AUTH);
//		logger.info("authority/addAdmin start...");
//		logger.info("login_Id=" + userid + " add new user : username="+username+"&password1="+ password1
//				+ "&password2=" + password2 + "&realname=" + realname
//				+ "&mobilePhone=" + mobilePhone + "&email=" + email);
//		Map<String, Object> map = new HashMap<String, Object>();
//		try {
//			boolean flag = true;
//			if (StringUtils.isEmpty(username)) {
//				map.put(SUC, false);
//				map.put(MSG, "用户名不能为空");
//				flag = false;
//			}
//			if (StringUtils.isEmpty(password1)
//					|| StringUtils.isEmpty(password2)
//					|| !password1.equals(password2)) {
//				map.put(SUC, false);
//				map.put(MSG, "密码输入有误");
//				flag = false;
//			}
//			Admin admin = adminService.getAdminByUsername(username);
//			if (admin != null) {
//				map.put(SUC, false);
//				map.put(MSG, "用户名重复");
//				flag = false;
//			}
//			if (flag) {
//				adminService.addAdmin(username, password1, realname,
//						mobilePhone, email);
//				map.put(SUC, true);
//				map.put(MSG, "添加成功");
//			}
//		} catch (Exception e) {
//			logger.info("authority/addAdmin error:" + e);
//		}
//		logger.info("authority/addAdmin end... json:" + new Gson().toJson(map));
//		return "@" + new Gson().toJson(map);
//	}
//
//	@AuthorityCheck(function = FunctionIds.FUNCTION_7)
//	@Post("deleteAdmin")
//	public String deleteAdmin(@Param("userids") Integer userids[]) {
//		logger.info("authority/deleteAdmin start...");
//		logger.info("userids=" + userids);
//		try {
//			List<Integer> ids = Arrays.asList(userids);
//			String idsString = ids.toString().replace("[", ",")
//					.replace("]", ",");
//			ids = AdminUtil.removeAminRoleId(idsString, ADMIN);// 防止删除admin
//			adminService.deleteAdmin(ids);
//		} catch (Exception e) {
//			logger.info("authority/deleteAdmin error:" + e);
//		}
//		logger.info("authority/deleteAdmin end...");
//		return "a:/userlist";
//	}
//
//	@AuthorityCheck(function = FunctionIds.FUNCTION_1)
//	@Post("getAdmin")
//	public String getAdminById(@Param("userid") int userid) {
//		logger.info("authority/getAdmin start...");
//		logger.info("userid=" + userid);
//		Map<String, Object> map = new HashMap<String, Object>();
//		try {
//			Admin admin = adminService.getAdminById(userid);
//			String roleidsStr = admin.getRoleids();
//			List<Integer> roleids = AdminUtil.getAdminRoleIds(roleidsStr);
//			map.put("admin", admin);
//			map.put("roleids", roleids);
//		} catch (Exception e) {
//			logger.info("authority/getAdmin error:" + e);
//		}
//		logger.info("authority/getAdmin end... json:"+ new Gson().toJson(map));
//		return "@" + new Gson().toJson(map);
//	}
//
//	@AuthorityCheck(function = FunctionIds.FUNCTION_8)
//	@Post("resetPassword")
//	public String resetPassword(@Param("userids") int userid) {
//		logger.info("authority/resetPassword start...");
//		Admin loadAdmin = (Admin) inv.getRequest()
//				.getSession().getAttribute("user");
//		Map<String, Object> map = new HashMap<String, Object>();
//		try {
//			Admin admin = adminService.getAdminById(userid);
//			if (admin == null || userid == ADMIN) {
//				map.put(MSG, "未找到该ID用户");
//			} else {
//				//String password = RandomPasswordUtil.getRandomPasswrd(8);
//				String password = RandomPasswordUtil.getSpecialRandomPasswrd();
//				logger.info("loadAdmin("+loadAdmin.getUsername()+") resetPassword : userid=" + admin.getUsername() + "&password=" + password);
//				String passwordMD5 = MD5.sign(password,"","utf-8");
//				adminService.resetPassword(userid, passwordMD5);
//				map.put(MSG, "更改成功");
//				
//				//更改成功后，短信通知用户 
//				String mobile = admin.getMobilePhone();
//				 String content = "您的运营管理平台密码已重置为:"+password;
//			      if(StringUtils.isEmpty(mobile)){
//			    	  map.put(MSG, "该账户未绑定手机号码，请绑定手机后重置");
//			    	  return "@"+new Gson().toJson(map);
//			      }
//			      try {
//			    	  SmsUtil.sendSms("12", "1005", mobile, content,"1");
//			      } catch (Exception e) {
//					logger.error("重置密码，调用接口出错", e);
//					map.put(MSG, "密码短信发送失败，请稍后重试！");
//					return "@"+new Gson().toJson(map);
//			      }
//			}
//		} catch (Exception e) {
//			logger.info("authority/resetPassword error:" + e);
//		}
//		logger.info("authority/resetPassword end... json:" + new Gson().toJson(map));
//		return "@" + new Gson().toJson(map);
//	}
//
//	@AuthorityCheck(function = FunctionIds.FUNCTION_9)
//	@Post("modifyAdmin")
//	public String modifyAdmin(@Param("userid") int userid,
//			@Param("username") String username,
//			@Param("realname") String realname, @Param("email") String email,
//			@Param("mobilePhone") String mobilePhone) {
//		logger.info("authority/modifyAdmin start...");
//		logger.info("userid=" + userid + "&username=" + username + "&realname="
//				+ realname + "&email=" + email + "&mobilePhone=" + mobilePhone);
//		Map<String, Object> map = new HashMap<String, Object>();
//		try {
//			adminService.modifyAdmin(userid, username, realname, email,
//					mobilePhone);
//			map.put(MSG, "更新成功");
//		} catch (Exception e) {
//			logger.info("authority/modifyAdmin error:" + e);
//		}
//		logger.info("authority/modifyAdmin end... json:" + new Gson().toJson(map));
//		return "@" + new Gson().toJson(map);
//	}
//
//	// 角色管理
//	@AuthorityCheck(function = FunctionIds.FUNCTION_4)
//	@Post("roleManage")
//	@Get("roleManage")
//	public String roleManage(@Param("page") int page) {
//		logger.info("authority/roleManage start...");
//		logger.info("page=" + page);
//		try {
//			int userid = Integer.parseInt((String) inv.getRequest()
//					.getSession()
//					.getAttribute(Constants.LOGIN_USER_ID_TAG_FOR_AUTH));
//			List<Integer> functionIds = authorityServiceHome
//					.getFunctionIds(userid);
//			Map<String, Boolean> functionMap = new HashMap<String, Boolean>();
//			functionMap.put("function_" + FunctionIds.FUNCTION_15, AdminUtil
//					.haveSomeAuthority(functionIds, FunctionIds.FUNCTION_15));// 修改角色权限，按钮级，给前端js控制
//			inv.addModel("functionMap", new Gson().toJson(functionMap));
//		} catch (Exception e) {
//			logger.info("authority/roleManage error:" + e);
//		}
//		logger.info("authority/roleManage end...");
//		return "authority/roleManage";
//	}
//
//	@AuthorityCheck(function = FunctionIds.FUNCTION_4)
//	@Post("searchRole")
//	public String searchRole(@Param("rolename") String rolename,
//			@Param("description") String description,
//			@Param("disabled") Integer disabled, @Param("page") int page) {
//		logger.info("authority/searchRole start...");
//		logger.info("rolename=" + rolename + "&description=" + description
//				+ "&disabled=" + disabled + "&page=" + page);
//		Map<String, Object> map = new HashMap<String, Object>();
//		try {
//			int userid = Integer.parseInt((String) inv.getRequest()
//					.getSession()
//					.getAttribute(Constants.LOGIN_USER_ID_TAG_FOR_AUTH));
//			if (userid == ADMIN) {
//				List<AdminRole> roles = adminService.searchAdminRole(rolename,
//						description, disabled, page, PAGESIZE);
//				map.put("roles", roles);
//			} else
//				map = authorityServiceHome.findAdminRoleLessThanUser(userid,
//						rolename, description, disabled, page, PAGESIZE);
//		} catch (Exception e) {
//			logger.info("authority/searchRole error:" + e);
//		}
//		logger.info("authority/searchRole end... json:" + new Gson().toJson(map));
//		return "@" + new Gson().toJson(map);
//	}
//
//	@AuthorityCheck(function = FunctionIds.FUNCTION_1)
//	@Post("searchRoleCount")
//	public String searchRoleCount(@Param("rolename") String rolename,
//			@Param("description") String description,
//			@Param("disabled") Integer disabled, @Param("page") int page) {
//		logger.info("authority/searchRoleCount start...");
//		logger.info("rolename=" + rolename + "&description=" + description
//				+ "&disabled=" + disabled + "&page=" + page);
//		Map<String, Object> map = new HashMap<String, Object>();
//		try {
//			int userid = Integer.parseInt((String) inv.getRequest()
//					.getSession()
//					.getAttribute(Constants.LOGIN_USER_ID_TAG_FOR_AUTH));
//			int count = 0;
//			if (userid == ADMIN) {
//				count = adminService.searchAdminRoleCount(rolename,
//						description, disabled);
//				int pageCount = count % PAGESIZE == 0 ? count / PAGESIZE
//						: count / PAGESIZE + 1;
//				map.put("count", count);
//				map.put("pageCount", pageCount);
//			} else {
//				map = authorityServiceHome.findAdminRoleLessThanUser(userid,
//						rolename, description, disabled, page, PAGESIZE);
//			}
//		} catch (Exception e) {
//			logger.info("authority/searchRoleCount error:" + e);
//		}
//		logger.info("authority/searchRoleCount end... json:"+ new Gson().toJson(map));
//		return "@" + new Gson().toJson(map);
//	}
//
//	@AuthorityCheck(function = FunctionIds.FUNCTION_12)
//	@Post("addRole")
//	public String addAdminRole(@Param("rolename") String rolename,
//			@Param("description") String description,
//			@Param("disabled") Integer disabled) {
//		logger.info("authority/addRole start...");
//		logger.info("rolename=" + rolename + "&description=" + description
//				+ "&disabled=" + disabled);
//		// int userid = Integer.parseInt((String)
//		// inv.getRequest().getSession().getAttribute(Constants.LOGIN_USER_ID_TAG_FOR_AUTH));
//		Map<String, Object> map = new HashMap<String, Object>();
//		// String userRoleids = adminService.getAdminById(userid).getRoleids();
//		try {
//			if (rolename == null || StringUtils.isEmpty(rolename)
//					|| (disabled != 0 && disabled != 1)) {
//				map.put(MSG, "参数有误");
//				map.put(SUC, false);
//			} else {
//				adminService.addAdminRole(rolename, description, disabled);
//				// if(disabled==1){ //如果新添角色可用，给登录用户添加该角色，否则不添加
//				// userRoleids = userRoleids+addRoleId+",";
//				// List<Integer> roleids
//				// =AdminUtil.getAdminRoleIds(userRoleids);
//				// adminService.allocateRole(userid, roleids);
//				// }
//				map.put(MSG, "增加角色成功");
//				map.put(SUC, true);
//			}
//		} catch (Exception e) {
//			logger.info("authority/addRole error:" + e);
//		}
//		logger.info("authority/addRole end... json:" + new Gson().toJson(map));
//		return "@" + new Gson().toJson(map);
//	}
//
//	@AuthorityCheck(function = FunctionIds.FUNCTION_14)
//	@Post("deleteRole")
//	public String deleteAdminRole(@Param("roleids") Integer[] roleids) {
//		logger.info("authority/deleteRole start...");
//		logger.info("roleids=" + roleids);
//		Map<String, Object> map = new HashMap<String, Object>();
//		try {
//			List<Integer> ids = Arrays.asList(roleids);
//			adminService.deleteAdminRoleById(ids);
//			map.put(SUC, true);
//			map.put(MSG, "删除成功");
//		} catch (Exception e) {
//			map.put(SUC, false);
//			map.put(MSG, "删除失败");
//			logger.info("authority/deleteRole error:" + e);
//		}
//		logger.info("authority/deleteRole end... json:" + new Gson().toJson(map));
//		return "@" + new Gson().toJson(map);
//	}
//
//	@AuthorityCheck(function = FunctionIds.FUNCTION_15)
//	@Post("modifyRole")
//	public String modifyAdminRole(@Param("roleid") int roleid,
//			@Param("rolename") String rolename,
//			@Param("description") String description,
//			@Param("disabled") int disabled) {
//		logger.info("authority/modifyRole start...");
//		logger.info("roleid=" + roleid + "&rolename=" + rolename
//				+ "&description=" + description + "&disabled=" + disabled);
//		Map<String, Object> map = new HashMap<String, Object>();
//		try {
//			if (roleid == 0 || StringUtils.isEmpty(rolename)
//					|| (disabled != 0 && disabled != 1)) {
//				map.put(SUC, false);
//				map.put(MSG, "参数有误");
//			} else {
//				try {
//					adminService.modifyAdminRole(roleid, rolename, description,
//							disabled);
//				} catch (Exception e) {
//					map.put(SUC, false);
//					map.put(MSG, "修改失败");
//					return "@" + new Gson().toJson(map);
//				}
//				map.put(SUC, true);
//				map.put(MSG, "修改成功");
//			}
//		} catch (Exception e) {
//			logger.info("authority/modifyRole error:" + e);
//		}
//		logger.info("authority/modifyRole end:" + new Gson().toJson(map));
//		return "@" + new Gson().toJson(map);
//	}
//
//	@AuthorityCheck(function = FunctionIds.FUNCTION_1)
//	@Post("checkName")
//	public String checkRoleName(@Param("rolename") String rolename,
//			@Param("username") String username) {
//		logger.info("authority/checkName start...");
//		logger.info("rolename=" + rolename + "&username" + username);
//		Map<String, Object> map = new HashMap<String, Object>();
//		try {
//			if (StringUtils.isEmpty(username) && !StringUtils.isEmpty(rolename)) {
//				AdminRole role = adminService.getAdminRoleByName(rolename);
//				if (role != null) {
//					map.put(SUC, false);
//				} else {
//					map.put(SUC, true);
//				}
//			}
//			if (!StringUtils.isEmpty(username) && StringUtils.isEmpty(rolename)) {
//				Admin admin = adminService.getAdminByUsername(username);
//				if (admin != null) {
//					map.put(SUC, false);
//				} else {
//					map.put(SUC, true);
//				}
//			}
//		} catch (Exception e) {
//			logger.info("authority/checkName error:" + e);
//		}
//		logger.info("authority/checkName end... json:" + new Gson().toJson(map));
//		return "@" + new Gson().toJson(map);
//	}
//
//	// 角色分配
//	@AuthorityCheck(function = 3)
//    @Get("roleAssign")
//	@Post("roleAssign")
//	public String getRoleAssign(@Param("page") int page) {
//		
//		return "authority/roleAssign";
//	}
//
//	@AuthorityCheck(function = FunctionIds.FUNCTION_3)
//	@Post("searchRoleAssign")
//	public String searchRoleAssign(@Param("username") String username,
//			@Param("realname") String realname,
//			@Param("mobilePhone") String mobilePhone,
//			@Param("email") String email, @Param("rolenames") String rolenames,
//			@Param("pageIndex") int page) {
//		logger.info("authority/searchRoleAssign start...");
//		logger.info("username=" + username + "&realname=" + realname
//				+ "&mobilePhone=" + mobilePhone + "&email=" + email
//				+ "&rolenames=" + rolenames + "&pageIndex=" + page);
//		Map<String, Object> map = new HashMap<String, Object>();
//		try {
//			int userid = Integer.parseInt((String) inv.getRequest()
//					.getSession()
//					.getAttribute(Constants.LOGIN_USER_ID_TAG_FOR_AUTH));
//			if (userid == ADMIN) {
//				List<Admin> admins = adminService
//						.searchAdmin(username, realname, mobilePhone, email,
//								rolenames, page, PAGESIZE);
//				map.put("admins", admins);
//			} else {
//				map = authorityServiceHome.findAuthorityFunctionLessThanUser(
//						userid, username, realname, mobilePhone, email,
//						rolenames, page, PAGESIZE);
//			}
//		} catch (Exception e) {
//			logger.info("authority/searchRoleAssign error:" + e);
//		}
//		logger.info("authority/searchRoleAssign end... json:"
//				+ new Gson().toJson(map));
//		return "@" + new Gson().toJson(map);
//	}
//
//	@AuthorityCheck(function = FunctionIds.FUNCTION_3)
//	@Post("getUsableRole")
//	public String getUsableRole() {
//		logger.info("authority/getUsableRole start...");
//		Map<String, Object> map = new HashMap<String, Object>();
//		try {
//			int userid = Integer.parseInt((String) inv.getRequest()
//					.getSession()
//					.getAttribute(Constants.LOGIN_USER_ID_TAG_FOR_AUTH));
//			if (userid == ADMIN) {
//				List<AdminRole> adminRoles = adminService.getAllUsableRole();
//				map.put("roles", adminRoles);
//			} else {
//				List<AdminRole> adminRoles = authorityServiceHome
//						.getAllUsableRoleLessThanUser(userid);
//				map.put("roles", adminRoles);
//			}
//		} catch (Exception e) {
//			logger.info("authority/getUsableRole error:" + e);
//		}
//		logger.info("authority/getUsableRole end... json:" + new Gson().toJson(map));
//		return "@" + new Gson().toJson(map);
//	}
//
//	@AuthorityCheck(function = FunctionIds.FUNCTION_10)
//	@Post("allocateRole")
//	public String allocateRole(@Param("userid") int userid,
//			@Param("roleids") Integer[] roleides) {
//		logger.info("authority/allocateRole start...");
//		logger.info("userid=" + userid + "&roleids=" + roleides);
//		Map<String, Object> map = new HashMap<String, Object>();
//		try {
//			List<Integer> ids = Arrays.asList(roleides);
//			if (ids == null || ids.size() == 0) {
//				map.put(SUC, false);
//				map.put(MSG, "没选择要分配的角色");
//				return "@" + new Gson().toJson(map);
//			}
//			int loginUserId = Integer.parseInt((String) inv.getRequest()
//					.getSession()
//					.getAttribute(Constants.LOGIN_USER_ID_TAG_FOR_AUTH));
//			List<AdminRole> roles = new ArrayList<AdminRole>();
//			if (loginUserId == ADMIN) {
//				roles = adminService.getAllUsableRole();
//			} else {
//				roles = authorityServiceHome
//						.getAllUsableRoleLessThanUser(loginUserId);
//			}
//			List<Integer> usableRole = new ArrayList<Integer>();
//			for (AdminRole role : roles) {
//				usableRole.add(role.getRoleid());
//			}
//			if (usableRole == null || usableRole.size() == 0) {
//				map.put(SUC, false);
//				map.put(MSG, "没有可分配的角色");
//				return "@" + new Gson().toJson(map);
//			}
//			if (!usableRole.containsAll(ids)) {
//				map.put(SUC, false);
//				map.put(MSG, "有角色不在分配范围内");
//				return "@" + new Gson().toJson(map);
//			}
//			adminService.allocateRole(userid, ids);
//			map.put(SUC, true);
//			map.put(MSG, "角色成功分配！");
//		} catch (Exception e) {
//			logger.info("authority/allocateRole error:" + e);
//		}
//		logger.info("authority/allocateRole end... json:" + new Gson().toJson(map));
//		return "@" + new Gson().toJson(map);
//	}
//
//	@AuthorityCheck(function = FunctionIds.FUNCTION_14)
//	@Post("removeRole")
//	public String removeRole(@Param("userids") Integer[] userids) {
//		logger.info("authority/removeRole start...");
//		logger.info("userids=" + userids);
//		try {
//			List<Integer> ids = Arrays.asList(userids);
//			adminService.removeRole(ids);
//		} catch (Exception e) {
//			logger.info("authority/removeRole error:" + e);
//		}
//		logger.info("authority/removeRole end...");
//		return "a:/roleAssign";
//	}
//
//	// 权限管理
//	@AuthorityCheck(function = FunctionIds.FUNCTION_5)
//	@Get("functionManager")
//	public String toRoleManage(Invocation inv) {
//		// 查找父级权限下的子节点
//		// List<AdminFunction> functionList =
//		// adminService.findAllFunctionsByPid(0);
//		// inv.addModel("functionList",functionList);
//		return "authority/functionManager";
//	}
//
//	// 权限级联
//	@AuthorityCheck(function = FunctionIds.FUNCTION_5)
//	@Get("getChildFunctions")
//	public String getChildFunctions(Invocation inv, @Param("pid") int pid) {
//		logger.info("authority/getChildFunctions start...");
//		logger.info("pid=" + pid);
//		String childFunctions = null;
//		try {
//			// 查找父级权限下的子节点
//			List<AdminFunction> functionList = adminService
//					.findAllFunctionsByPid(pid);
//			Gson gson = new Gson();
//			childFunctions = gson.toJson(functionList);
//		} catch (Exception e) {
//			logger.info("authority/getChildFunctions error:" + e);
//		}
//		logger.info("authority/getChildFunctions end... json:" + childFunctions);
//		return "@" + childFunctions;
//	}
//
//	// 给角色分配权限
//	@AuthorityCheck(function = FunctionIds.FUNCTION_13)
////	@Get("assignFunction")
//	@Post("assignFunction")
//	public String functionAssign(@Param("roleId") int roleId,
//			@Param("functionIds") Integer[] functionIds) {
//		logger.info("authority/assignFunction start...");
//		logger.info("roleId="+roleId+"&functionIds="+functionIds);
//		Map<String, Object> map = new HashMap<String, Object>();
//		try {
//			List<Integer> ids = new ArrayList<Integer>();
//			if (functionIds != null && functionIds.length != 0) {
//				ids = Arrays.asList(functionIds);
//			}
//			adminService.setPrivilegeByRoleId(roleId, ids);
//			map.put(SUC, true);
//			map.put(MSG, "权限分配成功");
//		} catch (Exception e) {
//			map.put(SUC, true);
//			map.put(MSG, "权限分配失败");
//			logger.info("authority/assignFunction error"+e);
//		}
//		logger.info("authority/assignFunction end... json:"+ new Gson().toJson(map));
//		return "@" + new Gson().toJson(map);
//	}
//
//	/**
//	 * 创建权限
//	 * 
//	 * @param inv
//	 * @param function
//	 * @return
//	 */
//	@AuthorityCheck(function = FunctionIds.FUNCTION_5)
//	@Post("addPrivilege")
//	public String addPrivilege(Invocation inv, AdminFunction function) {
//		logger.info("authority/addPrivilege start...");
//		// 若创建顶级权限，需要判断pid
//		long rtn = adminService.createPrivillege(function);
//		logger.info("authority/addPrivilege end...");
//		return "r:" + inv.getRequest().getContextPath()
//				+ "/authority/functionManager";
//	}
//
//	/**
//	 * 更新权限
//	 * 
//	 * @param inv
//	 * @param function
//	 * @return
//	 */
//	@AuthorityCheck(function = FunctionIds.FUNCTION_5)
//	@Post("modPrivilege")
//	public String modPrivilege(Invocation inv, AdminFunction function) {
//		// 若创建顶级权限，需要判断pid
//		logger.info("authority/modPrivilege start...");
//		long rtn = adminService.modifyPrivillege(function);
//		logger.info("authority/modPrivilege end...");
//		return "r:" + inv.getRequest().getContextPath()
//				+ "/authority/functionManager";
//	}
//
//	/**
//	 * 删除权限
//	 * 
//	 * @param inv
//	 * @param function
//	 * @return
//	 */
//	@AuthorityCheck(function = FunctionIds.FUNCTION_5)
//	@Post("delPrivilege")
//	public String delPrivilege(Invocation inv, @Param("id") int id) {
//		logger.info("authority/delPrivilege start...");
//		// 删除先判断是否父级权限，并级联删除？
//		long rtn = adminService.delPrivillege(id);
//		logger.info("authority/delPrivilege end...");
//		return "r:" + inv.getRequest().getContextPath()
//				+ "/authority/functionManager";
//	}
//
//	/**
//	 * 查找权限
//	 * 
//	 * @param inv
//	 * @param id
//	 * @return
//	 */
//	@AuthorityCheck(function = FunctionIds.FUNCTION_5)
//	@Get("getPrivilege")
//	public String getPrivilege(Invocation inv, @Param("id") int id) {
//		logger.info("authority/getPrivilege start...");
//		logger.info("id="+id);
//		String fString=null;
//		try {
//			// 若创建顶级权限，需要判断pid
//			AdminFunction function = adminService.getPrivillegeById(id);
//			fString = new Gson().toJson(function);
//			System.out.println(fString);
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//		logger.info("authority/getPrivilege end... json:"+fString);
//		return "@" + fString;
//	}
//
//	@AuthorityCheck(function = FunctionIds.FUNCTION_4)
//	@Get("authPrivilege")
//	@Post("authPrivilege")
//	public String authPrivilege(@Param("id") int pid)
//			throws UnsupportedEncodingException {
//		logger.info("authority/authPrivilege start...");
//		logger.info("id="+pid);
//		String zns =null;
//		// 根据roleId获得所有可以分配的权限列表(根据当前用户的权限，可查出的权限记录)
//		// List<AdminFunction> functionList = adminService.findAllFunctions();
//		List<Map<String, Object>> znodes;
//		try {
//			int loginUserId = Integer.parseInt((String) inv.getRequest()
//					.getSession()
//					.getAttribute(Constants.LOGIN_USER_ID_TAG_FOR_AUTH));
//			List<AdminFunction> functionList = new ArrayList<AdminFunction>();
//			if (loginUserId == ADMIN) { // 登录用户为admin时
//				functionList = adminService.getAllUsableFunction();
//			} else {
//				List<Integer> functionIds = authorityServiceHome
//						.getFunctionIds(loginUserId);
//				functionList = adminService.getFunctionsByIds(functionIds);
//			}
//			znodes = new ArrayList<Map<String, Object>>();
//			for (AdminFunction func : functionList) {
//				// {pId:"0", id:"1",name:"用户组_001", isParent:true, open:false},
//				Map<String, Object> map = new HashMap<String, Object>();
//				map.put("id", func.getId().toString());
//				map.put("pId", func.getPid());
//				map.put("isParent", func.getIsGroup() == 0 ? false : true);
//				map.put("name", func.getName());
//				if (func.getIsGroup() == 1) {
//					map.put("open", false);
//				}
//				znodes.add(map);
//			}
//			// 查询该角色已有的权限，在返回的json串中标记√
//			// List<AdminFunction> functions =
//			// adminService.findFunctionsByRoleId(roleId);
//			zns = new Gson().toJson(znodes);
//		} catch (Exception e) {
//			logger.info("authority/authPrivilege error:"+e);
//		}
//		logger.info("authority/authPrivilege end... json:"+zns);
//		return "@" + zns;
//	}
//
//	@AuthorityCheck(function = FunctionIds.FUNCTION_4)
//	@Post("getPrivilegeByRoleId")
//	public String getPrivilegeByRoleId(@Param("roleid") int roleid) {
//		logger.info("authority/getPrivilegeByRoleId start...");
//		logger.info("roleid="+roleid);
//		Map<String, Object> map= new HashMap<String, Object>();
//		try {
//			List<Integer> functionIds = adminService
//					.getPrivilegeByRoleId(roleid);
//			map.put("rolePrivilege", functionIds);
//		} catch (Exception e) {
//			logger.info("authority/getPrivilegeByRoleId error:"+e);
//		}
//		logger.info("authority/getPrivilegeByRoleId end... json:"+ new Gson().toJson(map));
//		return "@" + new Gson().toJson(map);
//	}
//	
//	@Post("sp_functionManager")
//	@Get("sp_functionManager")
//	public String spFunctionManageIndex(){
//		return "authority/sp_functionManager";
//	}
//	
//	
//	@AuthorityCheck(function=FunctionIds.FUNCTION_5)
//	@Post("add_sp_function")
//	public String addSPFunction(Invocation inv,Function spFunction){
//		long rtn = spService.addSpFunction(spFunction);
//		logger.info("authority/addPrivilege end...");
//		return "r:" + inv.getRequest().getContextPath()
//				+ "/authority/sp_functionManager";
//	}
//	
//	@Post("getSpFunction")
//	@Get("getSpFunction")
//	public String getSpFunction(@Param("id")int id){
//		Function function = spService.getSpFunction(id);
//		String fString = new Gson().toJson(function);
//		return "@"+fString;
//	}
//	
//	@AuthorityCheck(function=FunctionIds.FUNCTION_5)
//	@Post("resetRoleFunctionId")
//	@Get("resetRoleFunctionId")
//	public String resetRoleId(){
//		int result = adminService.resetRoleFunctionIds();
//		if(result > 0) return "@ok";
//		else return "@fail";
//	}
//	
//	@AuthorityCheck(function=FunctionIds.FUNCTION_5)
//	@Post("resetAdminRoleIds")
//	@Get("resetAdminRoleIds")
//	public String resetAdminRoleIds(){
//		int result = adminService.resetAdminRoleIds();
//		if(result > 0) return "@ok";
//		else return "@fail";
//	}
//	
//	public String getChildSpFunctions(@Param("parentId")int parentId){
//		List<Function> functionList = spService.getChildSpFunction(parentId);
//		Gson gson = new Gson();
//		return "@"+gson.toJson(functionList);
//	}
//}
