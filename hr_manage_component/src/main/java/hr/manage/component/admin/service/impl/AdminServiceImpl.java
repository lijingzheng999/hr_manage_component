package hr.manage.component.admin.service.impl;

import hr.manage.component.admin.dao.AdminDAO;
import hr.manage.component.admin.dao.AdminRoleDAO;
import hr.manage.component.admin.dao.AdminRolePrivDAO;
import hr.manage.component.admin.model.Admin;
import hr.manage.component.admin.model.AdminFunction;
import hr.manage.component.admin.model.AdminRole;
import hr.manage.component.admin.service.AdminService;
import hr.manage.component.admin.util.AdminUtil;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;




@Component("AdminServiceImpl")
public class AdminServiceImpl implements AdminService {
	
	private static final int DISABLED = 0;
//	private static final int USABLE = 1;
	
	@Autowired
	AdminDAO adminDao;
	@Autowired
	AdminRoleDAO adminRoleDao;
	@Autowired
	AdminRolePrivDAO adminRolePrivDao;
	
	

	@Override
	public Admin getUserById(int userid) {
		return adminDao.getUserById(userid);
	}
	
	@Override
	public Admin getAdminByInfoId(Integer personalInfoId){
		return adminDao.getAdminByInfoId(personalInfoId);
	}

	@Override
	public List<AdminRole> getAdminRoleByIds(List<Integer> roleids) {
		if(roleids==null){
			roleids = new ArrayList<Integer>();
		}
		return adminRoleDao.getAdminRoleByIds(roleids);
	}
	
	

	@Override
	public Admin getAdminByUsername(String username) {
		return  adminDao.getAdminByUsername(username);
	}
	
	

	@Override
	public int updateUser(Admin user) {
		return adminDao.updateUser(user);
	}
	
	
	@Override
	public long addUser(Admin admin) {
		return adminDao.save(admin);
	}


	@Override
	public AdminRole getRole(int roleid) {
		return adminRoleDao.getRole(roleid);
	}
	////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public Admin getUser(String username, String password) {
		//password = DigestUtils.md5Hex(password);
		return adminDao.getUser(username, password);
	}

	
	@Override
	public int delUser(int userid) {
		return adminDao.delUser(userid);
	}

	@Override
	public List<Admin> getUserList(int roleid, int page, int limit) {
		page = (page <= 0 ? 1 : page);
		int index = (page - 1) * limit;
		
		List<Admin> userList = adminDao.getUserList(roleid, index, limit);
		for(Admin user : userList){
			user.setAdminRole(adminRoleDao.getRole(user.getPersonalInfoId()));
		}
		return userList;
	}

	@Override
	public int getUserTotalNum(int roleid) {
		return adminDao.countUserList(roleid);
	}

	@Override
	public int checkUserName(String username) {
		return adminDao.checkUserName(username);
	}

	@Override
	public List<AdminRole> getRoleList(int superAdminAvaiable) {
		return adminRoleDao.getRoleList(superAdminAvaiable);
	}



	@Override
	public int countUserSearchList(int userid, String username, int roleid) {
		int searchUserName = (StringUtils.isEmpty(username)?0:1);
		username = "%" + username + "%";
		return adminDao.countUserSearchList(userid, searchUserName, username, roleid);
	}

	@Override
	public List<Admin> getUserSearchList(int userid, String username,
			int roleid, int page, int limit) {
		int searchUserName = (StringUtils.isEmpty(username) ? 0 : 1);
		int index = (page - 1) * limit;
		username = "%" + username + "%";
		List<Admin> userList = adminDao.getUserSearchList(userid, searchUserName, username, roleid, index, limit);
		for (Admin user : userList) {
			user.setAdminRole(adminRoleDao.getRole(user.getPersonalInfoId()));
		}
		return userList;
	}

	@Override
	public int addRole(String rolename, String roledesc, int isEnable) {
		AdminRole role = new AdminRole();
		role.setRolename(rolename);
		role.setDescription(roledesc);
		role.setDisabled(isEnable);
		return adminRoleDao.addRole(role);
	}

	@Override
	public int checkRoleName(String rolename) {
		return adminRoleDao.checkRoleName(rolename);
	}


	@Override
	public int updateRole(AdminRole role) {
		return adminRoleDao.updateRole(role);
	}

	@Override
	public int updateRoleModule(int roleid, String modules) {
		AdminRole role = new AdminRole();
		role.setRoleid(roleid);
		return adminRoleDao.updateRoleModule(role);
	}

	@Override
	public int deleteAdminRole(int roleid) {
		return adminRoleDao.deleteAdminRole(roleid);
	}

	@Override
	public int updateRoleColumn(int roleid, String categorys) {
		AdminRole role = new AdminRole();
		role.setRoleid(roleid);
		return adminRoleDao.updateRoleColumn(role);
	}
/*--------------------------------------------------------------------------------------------------*/
	@Override
	public List<Admin> getAdminList(int page,int pageSize) {
		page = (page <= 0 ? 1 : page);
		int index = (page - 1) * pageSize;
		List<Admin> adminList = adminDao.getAdminList(index, pageSize);
		return adminList;
	}

	@Override
	public int getAdminTotalNum() {
		return adminDao.countAdminList();
	}

	@Override
	public List<Admin> searchAdmin(String username, String realname,
			String mobilePhone, String email, String rolenames,int page, int pageSize) {
		page = (page <= 0 ? 1 : page);
		int index = (page - 1) * pageSize;
		List<Admin> admins = adminDao.searchAdmin(username, realname, mobilePhone, email,rolenames, index, pageSize);
		return admins;
	}

	@Override
	public int getSearchAdminCount(String username, String realname,
			String mobilePhone, String email,String rolenames) {
		return adminDao.getSearchAdminCount(username, realname, mobilePhone, email,rolenames);
	}

	@Override
	public int addAdmin(String username, String password, String realname,
			String mobilePhone, String email) {
		Admin admin = new Admin();
		admin.setUsername(username);
		//admin.setPassword(DigestUtils.md5Hex(password));
		admin.setPassword(password);
		admin.setRealname(realname);
		admin.setMobilePhone(mobilePhone);
		admin.setEmail(email);
		return adminDao.addAdmin(admin);
	}

	@Override
	public void deleteAdmin(List<Integer> userids) {
		adminDao.deleteAdmin(userids);
	}

	@Override
	public Admin getAdminById(int userid) {
		return adminDao.getAdminById(userid);
	}
	

	@Override
	public void resetPassword(int userid, String password) {
		//password = DigestUtils.md5Hex(password);
		adminDao.resetPassword(userid, password);
	}

	@Override
	public void modifyAdmin(int userid,String username, String realname, String email,
			String mobilePhone) {
		Admin admin = new Admin();
		admin.setUserid(userid);
		admin.setUsername(username);
		admin.setRealname(realname);
		admin.setEmail(email);
		admin.setMobilePhone(mobilePhone);
		adminDao.modifyAdmin(admin);
	}

	@Override
	public List<AdminRole> getAdminRoleList(int page, int pageSize) {
		page = (page <= 0 ? 1 : page);
		int index = (page - 1) * pageSize;
		List<AdminRole> roles = adminRoleDao.getAdminRoleList(index, pageSize);
		return roles;
	}

	@Override
	public int getAdminRoleCount() {
		return adminRoleDao.getAdminRoleCount();
	}

	@Override
	public int searchAdminRoleCount(String rolename,String description,Integer disabled) {
		return adminRoleDao.searchAdminRoleCount(rolename,description,disabled);
	}

	@Override
	public List<AdminRole> searchAdminRole(String rolename, String description,
			Integer disabled,int page,int pageSize) {
		page = (page <= 0 ? 1 : page);
		int index = (page - 1) * pageSize;
		return adminRoleDao.searchAdminRole(rolename, description, disabled,index,pageSize);
	}

	@Override
	public List<AdminRole> searchAdminRoleByUserids(String rolename,
			String description, Integer disabled, List<Integer> userRoleIds,
			int page, int pageSize) {
		page = (page <= 0 ? 1 : page);
		int index = (page - 1) * pageSize;
		return adminRoleDao.searchAdminRoleByUserids(rolename, description, disabled, userRoleIds, index, pageSize);
	}

	@Override
	public int searchAdminRoleByUseridsCount(String rolename,
			String description, Integer disabled, List<Integer> loginUserRoleIds) {
		return adminRoleDao.searchAdminRoleByUseridsCount(rolename, description, disabled, loginUserRoleIds);
	}
	
	@Override
	public int addAdminRole(String rolename, String description,
			Integer disabled) {
		AdminRole adminRole = new AdminRole();
		adminRole.setRolename(rolename);
		adminRole.setDescription(description);
		adminRole.setDisabled(disabled);
		return adminRoleDao.addAdminRole(adminRole);
	}

	@Override
	public void deleteAdminRoleById(List<Integer> roleids) {
		adminRoleDao.deleteAdminRoleById(roleids);
		for(Integer roleid  : roleids){
			List<Admin> admins = adminDao.getAdminByRoleIds(roleid);
			if(admins == null || admins.size() == 0) continue;
			for(Admin admin : admins){
				List<Integer> ids = AdminUtil.removeAminRoleId(admin.getRoleids(), roleid);
				allocateRole(admin.getUserid(), ids);
			}
		}
	}

	@Override
	public void modifyAdminRole(int roleid,String rolename, String description,
			Integer disabled) {
		AdminRole adminRole = new AdminRole();
		adminRole.setRoleid(roleid);
		adminRole.setRolename(rolename);
		adminRole.setDescription(description);
		adminRole.setDisabled(disabled);
		adminRoleDao.modifyAdminRole(adminRole);
		List<Admin> admins = adminDao.getAdminByRoleIds(roleid);
		if(admins != null && admins.size() != 0){
			if(disabled==DISABLED){
				for(Admin admin : admins){
					List<Integer> roleids = AdminUtil.removeAminRoleId(admin.getRoleids(), roleid);
					allocateRole(admin.getUserid(), roleids);
				}
			}else{
				for(Admin admin : admins){
					List<Integer> roleids = AdminUtil.getAdminRoleIds(admin.getRoleids());
					allocateRole(admin.getUserid(), roleids);
				}
			}
		}
	}

	@Override
	public AdminRole getAdminRoleByName(String rolename) {
		return adminRoleDao.getAdminRoleByName(rolename);
	}
	
	
	@Override
	public List<AdminRole> getUsableRole(List<Integer> userRoleids) {
		return adminRoleDao.getUsableRole(userRoleids);
	}

	@Override
	public List<AdminRole> getAllUsableRole() {
		return adminRoleDao.getAllUsableRole();
	}

	@Override
	public void allocateRole(int userid, List<Integer> roleids) {
		String rolename = "";
		if(roleids != null && roleids.size()!=0){
			rolename = getRoleName(roleids);
		}
		String roleidStr = roleids.toString().replace("[", ",").replace("]",",").replaceAll(" ", "");
		adminDao.setAdminRoleName(userid, rolename, roleidStr);
	}
	
	@Override
	public void removeRole(List<Integer> userids) {
		adminDao.removeRole(userids);
	}
	
	@Override
	public List<Integer> getPrivilegeByRoleId(int roleid) {
		String functionStr = adminRoleDao.getPrivilegeByRoleId(roleid);
		List<Integer> functionIds = AdminUtil.getAdminRoleIds(functionStr);
		return functionIds;
	}
	
	@Override
	public void setPrivilegeByRoleId(int roleid,List<Integer> functionIds){
		String functionIdsStr="";
		if(functionIds!=null && functionIds.size()!=0){
			functionIdsStr = functionIds.toString().replace("[", ",").replace("]",",").replace(" ", "");
		}
		adminRoleDao.setPrivilegeByRoleId(roleid, functionIdsStr);
	}
	
	public String getRoleName(List<Integer> roleids){
		List<AdminRole>roles =adminRoleDao.getAdminRoleById(roleids);
		StringBuffer sb = new StringBuffer();
		for(AdminRole role : roles){
			sb.append(role.getRolename()).append(",");
		}
		String roleName = sb.toString().trim();
		roleName = roleName.substring(0, roleName.length()-1);
		return roleName;
	}
	@Override
	public List<AdminFunction> findAllFunctions() {
		return adminRolePrivDao.findAllFunctions();
	}

	@Override
	public List<AdminFunction> findAllFunctionsByPid(Integer pid) {
		return adminRolePrivDao.findAllFunctionsByPid(pid);
	}

	@Override
	public long createPrivillege(AdminFunction function) {
		return adminRolePrivDao.createPrivillege(function);
	}

	@Override
	public long modifyPrivillege(AdminFunction function) {
		return adminRolePrivDao.updatePrivillege(function);
	}

	@Override
	public AdminFunction getPrivillegeById(int id) {
		return adminRolePrivDao.findFunctionById(id);
	}

	@Override
	public long delPrivillege(int id) {
		//逻辑删除当前权限，查找含有该权限的角色集合，遍历删除该权限id，将删除后的新权限重新赋值给角色
		adminRolePrivDao.delFunction(id);
		List<AdminRole> pidAdminRoles = adminRoleDao.getAdminRolesByFunctionId(id);
		if(pidAdminRoles!=null&&pidAdminRoles.size()>0){
			for(AdminRole pidAdminRole : pidAdminRoles){
				List<Integer> newFunctionIdsIntegers = AdminUtil.removeAminRoleId(pidAdminRole.getFunctionIds(),id);
				String functionIdsStr = "";
				if(newFunctionIdsIntegers!=null&&newFunctionIdsIntegers.size()>0){
					functionIdsStr = newFunctionIdsIntegers.toString().replace("[", ",").replace("]",",").replace(" ", "");
				}
				adminRoleDao.setPrivilegeByRoleId(pidAdminRole.getRoleid(), functionIdsStr);
			}
		}
		//逻辑删除其子权限，并将相应角色下的该id删除
		List<AdminFunction>  adminFunctions = adminRolePrivDao.findAllFunctionsByPid(id);
		if(adminFunctions!=null && adminFunctions.size()>0){
			for(AdminFunction adminFunction : adminFunctions){
				adminRolePrivDao.delFunction(adminFunction.getId());      //逻辑删除子权限
				List<AdminRole> childAdminRoles = adminRoleDao.getAdminRolesByFunctionId(adminFunction.getId());
				for(AdminRole childAdminRole : childAdminRoles){
					List<Integer> newFunctionIdsIntegers = AdminUtil.removeAminRoleId(childAdminRole.getFunctionIds(),adminFunction.getId());
					String functionIdsStr = "";
					if(newFunctionIdsIntegers!=null&&newFunctionIdsIntegers.size()>0){
						functionIdsStr = newFunctionIdsIntegers.toString().replace("[", ",").replace("]",",").replace(" ", "");
					}
					adminRoleDao.setPrivilegeByRoleId(childAdminRole.getRoleid(), functionIdsStr);
				}
			}
		}
		return 1;
	}

	@Override
	public long assignFunction(int roleId, String ids) {
		adminRolePrivDao.assignFunction(roleId, ids);
		return 1;
	}

	@Override
	public List<String> getAuthorityUrl(List<Integer> functionIds) {
		return adminRolePrivDao.getFunctionUrl(functionIds);
	}

	@Override
	public List<AdminFunction> getFunctionsByIds(List<Integer> ids) {
		return adminRolePrivDao.getFunctionByIds(ids);
	}

	@Override
	public List<AdminFunction> getAllUsableFunction() {
		return adminRolePrivDao.getAllUsableFunction();
	}

	@Override
	public List<Admin> getAdminsByRoleName(String roleName) {
		
		AdminRole adminRole = adminRoleDao.getAdminRoleByName(roleName);
		if(null!=adminRole){
			int roleID = adminRole.getRoleid();
			List<Admin> admins = adminDao.getAdminByRoleIds(roleID);
			return admins;
		}else{
			System.out.println("======查不到对应的角色=====");
			List<Admin> admins=new ArrayList<Admin>();
			return admins;
		}
		
	}

	@Override
	public int resetRoleFunctionIds() {
		try {
			List<AdminFunction> deletedFunctions = adminRolePrivDao.getAllDisableFunctions();
			if(deletedFunctions == null || deletedFunctions.size() == 0) return 1;
			for (AdminFunction deletedFunction : deletedFunctions) {
				String willDeleteId = deletedFunction.getId()+"";
				List<AdminRole> adminRoles = adminRoleDao.getAdminRolesByFunctionId(deletedFunction.getId());
				if(adminRoles == null || adminRoles.size() ==0 ) continue;
				for(AdminRole adminRole : adminRoles){
					String[] ids = adminRole.getFunctionIds().split(","+willDeleteId+",");
					StringBuilder sb = new StringBuilder();
					for (int i = 0; i < ids.length; i++) {
						sb.append(ids[i]).append(",");
					}
					String nowIds = sb.toString().replace(",,",",");
					adminRoleDao.setPrivilegeByRoleId(adminRole.getRoleid(), nowIds);
				}
			}
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	public int resetAdminRoleIds() {
		try {
			List<Admin> admins = adminDao.getAdminList(0, 0);
			if(admins == null || admins.size() == 0) return 1;
			for (Admin admin : admins) {
				String roleIdsStr = admin.getRoleids();
				List<Integer> roleIds = AdminUtil.getAdminRoleIds(roleIdsStr);
				if(roleIds == null || roleIds.size() == 0) continue;
				loop1 : for (Integer id : roleIds) {
					AdminRole adminRole = adminRoleDao.getRole(id);
					if(adminRole == null){
						String dealStr = roleIds.toString().replace("[", ",").replace("]",",").replace(" ", "");
						roleIds = AdminUtil.removeAminRoleId(dealStr, id);
						allocateRole(admin.getUserid(), roleIds);
						continue loop1;
					}
					if(adminRole.getDisabled() == 1){
						continue loop1;
					}else{
						String dealStr = roleIds.toString().replace("[", ",").replace("]",",").replace(" ", "");
						roleIds = AdminUtil.removeAminRoleId(dealStr, id);
						allocateRole(admin.getUserid(), roleIds);
						continue loop1;
					}
				}
			}
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	

}
