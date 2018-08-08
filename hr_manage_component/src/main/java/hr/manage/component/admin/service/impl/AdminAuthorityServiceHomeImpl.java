package hr.manage.component.admin.service.impl;

import hr.manage.component.admin.model.Admin;
import hr.manage.component.admin.model.AdminRole;
import hr.manage.component.admin.service.AdminAuthorityServiceHome;
import hr.manage.component.admin.service.AdminService;
import hr.manage.component.admin.util.AdminUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class AdminAuthorityServiceHomeImpl implements AdminAuthorityServiceHome{
	
	
	@Autowired
	private AdminService adminService;
	
	@Override
	public boolean haveAuthority(int adminId, int functionId) {
		Boolean boo = false;
		List<Integer> functionIds = getFunctionIds(adminId);
		if(functionIds!=null && functionIds.size()>0){
			boo=functionIds.contains(functionId);
		}
		return boo;
	}


	@Override
	public List<Integer> getFunctionIds(int adminId){
		Admin user = adminService.getUserById(adminId);
		List<Integer> functionIds = new ArrayList<Integer>(); 
		if(user==null){
			return  functionIds;
		}
		List<Integer> roleids = AdminUtil.getAdminRoleIds(user.getRoleids());
		List<AdminRole>	adminRoles = adminService.getAdminRoleByIds(roleids);
		if(adminRoles != null){
			for(AdminRole adminRole : adminRoles){
				functionIds.addAll(AdminUtil.getAdminRoleIds(adminRole.getFunctionIds()));
			}
			 	HashSet<Integer> set =   new  HashSet<Integer>(functionIds); 
			    functionIds.clear();
			    functionIds.addAll(set);
		}
		return functionIds;
	}

	
//	@Override
//	public boolean haveAuthorityUrl(int adminId, String url) {
//		Boolean boo = false;
//		List<Integer> functionIds = getFunctionIds(adminId);
//		if(functionIds != null && functionIds.size()>0){
//			List<String> urls = adminService.getAuthorityUrl(functionIds);
//			if(urls!=null && urls.size()>0){
//				boo = urls.contains(url);
//			}
//		}
//		return boo;
//	}
//	
//	@Override
//	public Map<String,Object> findAuthorityFunctionLessThanUser(int userid,String username,String realname, String mobilePhone,String email, String rolename,int page, int pageSize){
//		Map<String, Object> map = new HashMap<String,Object>();
//		List<Integer> userFunctionIds = getFunctionIds(userid);
//		List<Admin> admins = adminService.searchAdmin(username, realname, mobilePhone, email, rolename,PAGE, PAGESIZE_MAX);
//		List<Admin> showAdmins = new ArrayList<Admin>();
//		List<Admin> showAdminsByPage = new ArrayList<Admin>();
//		page = page<=0 ? 1 : page;
//		for(Admin admin : admins){
//			if(admin.getUserid()==userid){
//				continue;
//			}
//			List<Integer> adminFunctionIds = getFunctionIds(admin.getUserid());
//			if(AdminUtil.haveRoleid(userFunctionIds, adminFunctionIds)){
//				admin.setPassword("********");          //隐藏密码
//				showAdmins.add(admin);
//			}
//		}
//		int count = showAdmins.size();
//		int pageCount = count%pageSize == 0 ? count/pageSize : count/pageSize+1;
//		map.put("count",count);
//		map.put("pageCount", pageCount);
//		for(int i = (page-1)*pageSize; i<page*pageSize;i++){
//			if(i>showAdmins.size()-1){
//				break;
//			}
//			showAdminsByPage.add(showAdmins.get(i));
//		}
//		map.put("admins", showAdminsByPage);
//		return map;
//	} 
//	
//	@Override
//	public List<Admin> findAuthorityRoleLessThanUser(int userid,
//			String username, String realname, String mobilePhone, String email,
//			String rolename, int page, int pageSize) {
//		List<Admin> admins = adminService.searchAdmin(username, realname, mobilePhone, email, rolename,PAGE, PAGESIZE_MAX);
//		List<Admin> showAdmins = new ArrayList<Admin>();
//		List<Admin> showAdminsByPage = new ArrayList<Admin>();
//		page = page<=0 ? 1 : page;
//		String userRoleids = adminService.getAdminById(userid).getRoleids();
//		List<Integer> user = AdminUtil.getAdminRoleIds(userRoleids);
//		for(Admin admin : admins){
//			if(admin.getUserid()==userid){
//				continue;
//			}
//			if(AdminUtil.haveRoleid(user, admin.getRoleids())){
//				admin.setPassword("********"); 
//				showAdmins.add(admin); 
//			}
//		}
//		for(int i = (page-1)*pageSize; i<page*pageSize;i++){
//			if(i>showAdmins.size()-1){
//				break;
//			}
//			showAdminsByPage.add(showAdmins.get(i));
//		}
//		return showAdminsByPage;
//	}
//	
//	@Override
//	public Map<String,Object> findAdminRoleLessThanUser(int userid,
//			String rolename, String description, Integer disabled, int page,
//			int pageSize) {
//		Map<String, Object> map = new HashMap<String,Object>();
//		List<Integer> userFunctionIds = getFunctionIds(userid);
//		List<AdminRole> adminRoles =  adminService.searchAdminRole(rolename, description, disabled, PAGE, PAGESIZE_MAX);
//		List<AdminRole> showAdminRoles = new ArrayList<AdminRole>();
//		List<AdminRole> showAdminRolesByPage = new ArrayList<AdminRole>();
//		page = page<=0 ? 1 : page;
//		for(AdminRole adminRole : adminRoles){
//			if(AdminUtil.haveRoleid(userFunctionIds, adminRole.getFunctionIds())){
//				showAdminRoles.add(adminRole); 
//			}
//		}
//		int count = showAdminRoles.size();
//		int pageCount = count%pageSize == 0 ? count/pageSize : count/pageSize+1;
//		map.put("count",count);
//		map.put("pageCount", pageCount);
//		for(int i = (page-1)*pageSize; i<page*pageSize;i++){
//			if(i>showAdminRoles.size()-1){
//				break;
//			}
//			showAdminRolesByPage.add(showAdminRoles.get(i));
//		}
//		map.put("roles", showAdminRolesByPage);
//		return map;
//	}
//
//	@Override
//	public List<AdminRole> getAllUsableRoleLessThanUser(int userid) {
//		List<Integer> userFunctionIds = getFunctionIds(userid);
//		List<AdminRole> adminRoles = adminService.getAllUsableRole();
//		List<AdminRole> showAdminRoles = new ArrayList<AdminRole>();
//		for(AdminRole adminRole : adminRoles){
//			if(AdminUtil.haveRoleid(userFunctionIds, adminRole.getFunctionIds())){
//				showAdminRoles.add(adminRole);
//			}
//		}
//		return showAdminRoles;
//	}
//	
//	@Override
//	public List<Admin> getAdminsByRoleName(String roleName) {
//		
//		List<Admin> adminHaveRole = adminService.getAdminsByRoleName(roleName);
//		
//		return adminHaveRole;
//	}

}
