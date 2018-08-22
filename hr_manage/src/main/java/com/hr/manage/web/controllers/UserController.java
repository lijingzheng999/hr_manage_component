package com.hr.manage.web.controllers;

import hr.manage.component.admin.model.Admin;
import hr.manage.component.admin.service.AdminService;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.hr.manage.web.annotation.AuthorityCheck;
import com.hr.manage.web.constant.CodeMsg;
import com.hr.manage.web.constant.FunctionIds;
import com.hr.manage.web.constant.JSONResult;
import com.hr.manage.web.util.MD5Util;

@Path("")
public class UserController {

	@Autowired
	Invocation inv;
	@Autowired
	AdminService adminService;
	
	
	/**
     * 
    * @Title: editAdminUser
    * @Description: 员工修改密码
    * @Url: user/editpwd
    * @Param("userid") int userid, @Param("password") String password,@Param("repassword") String repassword,@Param("confirmpwd") String confirmpwd
    * @param @return    
    * @return String    
    * @throws
     */
	@AuthorityCheck(function = FunctionIds.FUNCTION_8)
	@Post("user/editpwd")
	@Get("user/editpwd")
	public String editAdminUser(@Param("userid") int userid, @Param("password") String password,@Param("repassword") String repassword,
			                    @Param("confirmpwd") String confirmpwd){
		Admin user = adminService.getUserById(userid);
		if(!StringUtils.isEmpty(password)){
			if(!MD5Util.GetMD5Code(password).equals(user.getPassword())){
				return "@"+JSONResult.error(CodeMsg.ERROR,"原密码不正确,请重新输入");
			}
		}
		else{
			return "@"+JSONResult.error(CodeMsg.ERROR,"请重新输入,密码不能为空");
		}
		
		
		if(!StringUtils.isEmpty(repassword)&&!StringUtils.isEmpty(confirmpwd)){
			if(!confirmpwd.equals(repassword)){
				return "@"+JSONResult.error(CodeMsg.ERROR,"两次输入的密码不正确");
			}
			user.setPassword(MD5Util.GetMD5Code(repassword));
		}
		else{
			return "@"+JSONResult.error(CodeMsg.ERROR,"请重新输入,密码不能为空");
		}
		adminService.updateUser(user);
		
		return "@"+JSONResult.success();
	}
	
	
	@Get("user/getRoles")
	public String getRoles(){
		Admin user = (Admin) inv.getRequest().getSession().getAttribute("user");
		if (user != null) {
			return "@"+JSONResult.success(user);
		}
		else{
			return "@"+JSONResult.error(CodeMsg.ERROR,"没有登录");
		}
	}
//	
//	@Get("user/admin/add")
//	public String addAdmin(){
//		String errorMSG = inv.getFlash().get("errorMSG");
//		if(!StringUtils.isEmpty(errorMSG)){
//			inv.addModel("errorMSG", errorMSG);
//		}
//		return "user/admin-add";
//	}
//	
//	@Get("user/admin/edit/{uid:[0-9]+}")
//	public String editAdmin(@Param("uid") int userid){
//		Admin user = adminService.getUserById(userid);
//		inv.addModel("user", user);
//		
//		String errorMSG = inv.getFlash().get("errorMSG");
//		if(!StringUtils.isEmpty(errorMSG)){
//			inv.addModel("errorMSG", errorMSG);
//		}
//		return "user/admin-edit";
//	}
//	
//	@Get("user/admin/delete/{uid:[0-9]+}")
//	public String delAdmin(@Param("uid") int userid){
//		//@TODO 查看用户权限
//		adminService.delUser(userid);
//		return "r:"+inv.getRequest().getContextPath()+"/user/admin/manage";
//	}
//	
//	@Post("user/admin/add")
//	public String addAdminUser(@Param("username") String username, @Param("password") String password,@Param("repassword") String repassword,
//							   @Param("email") String email,@Param("realname") String realname,@Param("roleid") int roleid){
//		if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)){
//			inv.addFlash("errorMSG", "必须输入用户名和密码。");
//			return "r:"+inv.getRequest().getContextPath()+"/user/admin/add";
//		}
//		
//		if(adminService.checkUserName(username) > 0){
//			inv.addFlash("errorMSG", "用户名已存在。");
//			return "r:"+inv.getRequest().getContextPath()+"/user/admin/add";
//		}
//		
//		if(!password.equals(repassword)){
//			inv.addFlash("errorMSG", "两次输入的密码不正确。");
//			return "r:"+inv.getRequest().getContextPath()+"/user/admin/add";
//		}
//		
//		if(roleid <= 0){
//			inv.addFlash("errorMSG", "用户权限组选择不正确。");
//			return "r:"+inv.getRequest().getContextPath()+"/user/admin/add";
//		}
//		
//		username = AntiSpamUtils.safeText(username);
//		email = AntiSpamUtils.safeText(email);
//		realname = AntiSpamUtils.safeText(realname);
//		adminService.addUser(username, password, roleid, email, realname, 1);
//		
//		return "r:"+inv.getRequest().getContextPath()+"/user/admin/manage";
//	}
//	
//	@Get("/user/manage")
//	public String userList(@Param("page") int page){
//		page = (page <= 0 ? 1 : page);
//		List<Admin> userList = adminService.getUserList(0, page, PAGE_SIZE);
//		int userTotalNum = adminService.getUserTotalNum(0);
//		int totalPage = (int)Math.ceil(userTotalNum *1.0 / PAGE_SIZE);
//		inv.addModel("userList", userList);
//		inv.addModel("page", page);
//		inv.addModel("totalPage", totalPage);
//		
//		List<AdminRole> roleList = adminService.getRoleList(0);
//		inv.addModel("roleList", roleList);
//		
//		return "user/user-manage";
//	}
//	
//	@Post("user/searchlist")
//	public String userSearchList(@Param("userid") int userid,@Param("username") String username,
//								  @Param("roleid") int roleid,@Param("page") int page){
//		Map<String, Object> search = new HashMap<String, Object>();
//		search.put("userid", (userid > 0 ? userid : null));
//		search.put("username", username);
//		search.put("roleid", roleid);
//		
//		page = (page <= 0 ? 1 : page);
//		
//		if(userid <= 0 && StringUtils.isEmpty(username) && roleid <= 0){
//			return "r:"+inv.getRequest().getContextPath()+"/user/manage?page="+page;
//		}
//		
//		inv.addModel("search", search);
//		
//		int totalNum = adminService.countUserSearchList(userid, username, roleid);
//		int totalPage = (int)Math.ceil(totalNum *1.0 / PAGE_SIZE);
//		List<Admin> userList = adminService.getUserSearchList(userid, username, roleid, page, PAGE_SIZE);
//		
//		inv.addModel("userList", userList);
//		inv.addModel("page", page);
//		inv.addModel("totalPage", totalPage);
//		
//		List<AdminRole> roleList = adminService.getRoleList(0);
//		inv.addModel("roleList", roleList);
//		
//		return "user/user-manage";
//	}
//	
//	@Get("/user/add")
//	public String showUserAdd(){
//		String errorMSG = inv.getFlash().get("errorMSG");
//		if(!StringUtils.isEmpty(errorMSG)){
//			inv.addModel("errorMSG", errorMSG);
//		}
//		
//		List<AdminRole> roleList = adminService.getRoleList(0);
//		inv.addModel("roleList", roleList);
//		return "user/user-add";
//	}
//	
//	@Post("user/add")
//	public String addUser(@Param("username") String username, @Param("password") String password,@Param("repassword") String repassword,
//							   @Param("email") String email,@Param("realname") String realname,@Param("roleid") int roleid){
//		List<AdminRole> roleList = adminService.getRoleList(0);
//		inv.addModel("roleList", roleList);
//		inv.addModel("username",username);
//		inv.addModel("password",password);
//		inv.addModel("repassword",repassword);
//		inv.addModel("email",email);
//		inv.addModel("realname",realname);
////		inv.addModel("roleid",roleid);
//		if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)){
//			inv.addModel("errorMSG", "必须输入用户名和密码。");
//			return "user/user-add";
//		}
//		
//		if(adminService.checkUserName(username) > 0){
//			inv.addModel("errorMSG", "用户名已存在。");
//			return "user/user-add";
//		}
//		
//		if(!password.equals(repassword)){
//			inv.addModel("errorMSG", "两次输入的密码不正确。");
//			return "user/user-add";
//		}
//		
//		if(roleid <= 0 ){
//			inv.addModel("errorMSG", "请选择该用户的权限组。");
//			return "user/user-add";
//		}
//		
//		username = AntiSpamUtils.safeText(username);
//		email = AntiSpamUtils.safeText(email);
//		realname = AntiSpamUtils.safeText(realname);
//		adminService.addUser(username, password, roleid, email, realname, 1);
//		
//		return "r:"+inv.getRequest().getContextPath()+"/user/manage";
//	}
//	
//	@Get("/user/edit/{uid:[0-9]+}")
//	public String editUser(@Param("uid") int userid){
//		Admin user = adminService.getUserById(userid);
//		inv.addModel("user", user);
//		List<AdminRole> roleList = adminService.getRoleList(0);
//		inv.addModel("roleList", roleList);
//		
//		String errorMSG = inv.getFlash().get("errorMSG");
//		if(!StringUtils.isEmpty(errorMSG)){
//			inv.addModel("errorMSG", errorMSG);
//		}
//		
//		return "user/user-edit";
//	}
//	
//	@Get("user/delete/{uid:[0-9]+}")
//	public String delUser(@Param("uid") int userid){
//		//@TODO 查看用户权限
//		adminService.delUser(userid);
//		return "r:"+inv.getRequest().getContextPath()+"/user/manage";
//	}
//	
//	@Post("user/edit")
//	public String editUserInfo(@Param("userid") int userid,@Param("username") String username, @Param("password") String password,@Param("repassword") String repassword,
//			   @Param("email") String email,@Param("realname") String realname,@Param("roleid") int roleid){
//		Admin user = adminService.getUserById(userid);
//		
//		if(StringUtils.isEmpty(username)){
//			inv.addFlash("errorMSG", "必须输入用户名。");
//			return "r:"+inv.getRequest().getContextPath()+"/user/edit/"+userid;
//		}
//		
//		if(!user.getUsername().equals(username)){
//			if(adminService.checkUserName(username) > 0){
//				inv.addFlash("errorMSG", "用户名已存在。");
//				return "r:"+inv.getRequest().getContextPath()+"/user/edit/"+userid;
//			}
//			user.setUsername(AntiSpamUtils.safeText(username));
//		}
//		
//		if(!StringUtils.isEmpty(password)){
//			if(!password.equals(repassword)){
//				inv.addFlash("errorMSG", "两次输入的密码不正确。");
//				return "r:"+inv.getRequest().getContextPath()+"/user/edit/"+userid;
//			}
//			user.setPassword(DigestUtils.md5Hex(password));
//		}
//		
//		user.setEmail(AntiSpamUtils.safeText(email));
//		user.setRealname(AntiSpamUtils.safeText(realname));
//		user.setRoleid(roleid);
//		adminService.updateUser(user);
//		
//		return "r:"+inv.getRequest().getContextPath()+"/user/manage";
//	}
//	
//	@Get("user/adminrole/manage")
//	public String roleManage(){
//		//TODO 获取登录用户信息
//		Admin user = adminService.getUserById(UserUtils.getUserId(inv));
//		if (user == null){
//			return "/";
//		}
//		List<AdminRole> roleList = adminService.getRoleList(user.getRoleid());
//		inv.addModel("roleList", roleList);
//		return "user/role-manage";
//	}
//	
//	@Get("user/access/setting")
//	public String accessSetting(@Param("roleid") int roleid){
//		inv.addModel("roleid", roleid);
//		AdminRole role = adminService.getRole(roleid);
//		if (role != null){
//			String modules = role.getRoleModules();
//			if (StringUtils.isNotEmpty(modules)){
//				Gson gson = new Gson();
//				Map<String, String> map = gson.fromJson(modules, new TypeToken<Map<String, String>>(){}.getType());
//				inv.addModel("moduleMap", map);
//			}
//		}
//		
//		return "user/access-setting";
//	}
//	
//	@Get("user/role/add")
//	public String  showRoleAdd(){
//		String errorMSG = inv.getFlash().get("errorMSG");
//		if(!StringUtils.isEmpty(errorMSG)){
//			inv.addModel("errorMSG", errorMSG);
//		}
//		return "user/role-add";
//	}
//	
//	@Get("/user/role/edit/{id:[0-9]+}")
//	public String editRole(@Param("id") int roleid){
//		String errorMSG = inv.getFlash().get("errorMSG");
//		if(!StringUtils.isEmpty(errorMSG)){
//			inv.addModel("errorMSG", errorMSG);
//		}
//		AdminRole role = adminService.getRole(roleid);
//		inv.addModel("role", role);
//		return "user/role-edit";
//	}
//	
//	@Post("user/role/add")
//	public String addRole(@Param("rolename") String rolename, @Param("roleDesc") String roleDesc,@Param("isEnable") int isEnable){
//		if(StringUtils.isEmpty(rolename)){
//			inv.addFlash("errorMSG", "必须输入角色名称。");
//			return "r:"+inv.getRequest().getContextPath()+"/user/role/add";
//		}
//		if (StringUtils.isEmpty(roleDesc)){
//			inv.addFlash("errorMSG", "必须输入角色描述");
//			return "r:"+inv.getRequest().getContextPath()+"/user/role/add";
//		}
//		if (adminService.checkRoleName(rolename) > 0){
//			inv.addFlash("errorMSG", "角色名称已存在。");
//			return "r:"+inv.getRequest().getContextPath()+"/user/role/add";
//		}
//		adminService.addRole(rolename, roleDesc, isEnable);
//		return "r:"+inv.getRequest().getContextPath()+"/user/adminrole/manage";
//	}
//	
//	
//	@Post("user/role/edit")
//	public String editRoleInfo(@Param("rolename") String rolename, @Param("roleDesc") String roleDesc,@Param("isEnable") int isEnable, @Param("roleid") int roleid){
//		AdminRole role = adminService.getRole(roleid);
//		if(StringUtils.isEmpty(rolename)){
//			inv.addFlash("errorMSG", "必须输入角色名称。");
//			return "r:"+inv.getRequest().getContextPath()+"/user/role/edit/"+roleid;
//		}
//		if(StringUtils.isEmpty(roleDesc)){
//			inv.addFlash("errorMSG", "必须输入角色描述。");
//			return "r:"+inv.getRequest().getContextPath()+"/user/role/edit/"+roleid;
//		}
//		if(!role.getRolename().equals(rolename)){
//			if(adminService.checkRoleName(rolename) > 0){
//				inv.addFlash("errorMSG", "角色名称已存在。");
//				return "r:"+inv.getRequest().getContextPath()+"/user/role/edit/"+roleid;
//			}
//			role.setRolename(AntiSpamUtils.safeText(rolename));
//		}
//		role.setRoleid(roleid);
//		role.setDisabled(isEnable);
//		adminService.updateRole(role);
//		return "r:"+inv.getRequest().getContextPath()+"/user/adminrole/manage";
//	}
//	
//	@Post("/user/role/editRoleModule")
//	public String editRoleModule(@Param("modules") String modules, @Param("roleid") int roleid){
//		Map<String, String> map = new HashMap<String, String>();
//		adminService.updateRoleModule(roleid, modules);
//		map.put("result", "success");
//		return AjaxUtils.printJson(map, inv);
//	}
//	
//	@Get("user/role/remove/{id:[0-9]+}")
//	public String remove(@Param("id") int roleid){
//		adminService.deleteAdminRole(roleid);
//		return "r:"+inv.getRequest().getContextPath()+"/user/adminrole/manage";
//	}
//	
//	@Post("/user/role/editRoleColumn")
//	public String editRoleColumn(@Param("categorys") String categorys, @Param("roleid") int roleid){
//		Map<String, String> map = new HashMap<String, String>();
//		adminService.updateRoleColumn(roleid, categorys);
//		map.put("result", "success");
//		return AjaxUtils.printJson(map, inv);
//	}
//	
//	@Get("user/enable/{uid:[0-9]+}")
//	public String enableUser(@Param("uid") int userid, @Param("page") int page){
//		Admin user = adminService.getUserById(userid);
//		if(user != null){
//			user.setStatus(1);
//			adminService.updateUser(user);
//		}
//		return "r:"+inv.getRequest().getContextPath()+"/user/manage?page="+page;
//	}
//	
//	@Get("user/disable/{uid:[0-9]+}")
//	public String disableUser(@Param("uid") int userid, @Param("page") int page){
//		Admin user = adminService.getUserById(userid);
//		if(user != null){
//			user.setStatus(0);
//			adminService.updateUser(user);
//		}
//		return "r:"+inv.getRequest().getContextPath()+"/user/manage?page="+page;
//	}
	
}
