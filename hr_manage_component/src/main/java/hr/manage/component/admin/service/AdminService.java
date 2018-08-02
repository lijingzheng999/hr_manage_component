package hr.manage.component.admin.service;

import hr.manage.component.admin.model.Admin;
import hr.manage.component.admin.model.AdminFunction;
import hr.manage.component.admin.model.AdminRole;

import java.util.List;



public interface AdminService {
	

	public Admin getUserById(int userid);
	

	public List<AdminRole> getAdminRoleByIds(List<Integer> roleids);   //登陆用
	

	public Admin getAdminByUsername(String username);

	public int updateUser(Admin user);
	
	
	
//////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Admin getUser(String username, String password);
	
	
	public int addUser(String username, String password, int roleid, String email, String realname, int status);
	
	public int delUser(int userid);
	
	public List<Admin> getUserList(int roleid, int page, int limit);
	
	public int getUserTotalNum(int roleid);
	
	public int checkUserName(String username);
	
	public List<AdminRole> getRoleList(int superAdminAvaiable);
	
	
	public int countUserSearchList(int userid, String username, int roleid);
	
	public List<Admin> getUserSearchList(int userid, String username, int roleid, int page, int limit);
	
	public int addRole(String rolename, String roledesc, int isEnable);
	
	public int checkRoleName(String rolename);
	
	public AdminRole getRole(int roleid);
	
	public int updateRole(AdminRole role);
	
	public int updateRoleModule(int roleid, String modules);
	
	public int deleteAdminRole(int roleid);
	
	public int updateRoleColumn(int roleid, String categorys);
	
	/*----------------------------用户管理-----------------------------------*/
	public List<Admin> getAdminList(int page,int pageSize); 
	
	public int getAdminTotalNum();
	
	public List<Admin> searchAdmin(String username,String realname,String mobilePhone,String email, String rolenames,int page, int pageSize);
	
	public int getSearchAdminCount(String username,String realname,String mobilePhone,String email,String rolenames);
	
	public int addAdmin(String username,String password1,String realname,String mobilePhone, String email);
	
	public void deleteAdmin(List<Integer> userids);
	
	public Admin getAdminById(int userid);
	
	
	public void  resetPassword(int userid,String password);
	
	public void modifyAdmin(int userid,String username,String realname,String email,String mobilePhone);
	
	/*----------------------------角色管理-----------------------------------*/
	public List<AdminRole> getAdminRoleList(int page,int pageSize);
	
	public int getAdminRoleCount();
	
	public int searchAdminRoleCount(String rolename,String description,Integer disabled);
	
	public List<AdminRole> searchAdminRole(String rolename,String description,Integer disabled,int page,int pageSize);
	
	public List<AdminRole> searchAdminRoleByUserids(String rolename,String description,Integer disabled,List<Integer> userRoleIds,int page,int pageSize);
	
	public int searchAdminRoleByUseridsCount(String rolename,String description,Integer disabled,List<Integer> loginUserRoleIds);
	
	public int addAdminRole(String rolename,String description,Integer disabled);
	
	public void deleteAdminRoleById(List<Integer> roleid);
	
	public void modifyAdminRole(int roleid,String rolename,String description,Integer disabled);
	
	public AdminRole getAdminRoleByName(String rolename);
	

	/*-------------------------------角色分配-------------------------------------------*/
	
	public List<AdminRole> getUsableRole(List<Integer> userRoleids);
	
	public List<AdminRole> getAllUsableRole();
	
	public void allocateRole(int userid,List<Integer> roleids);
	
	public void removeRole(List<Integer> userids);
	
	public List<Integer> getPrivilegeByRoleId(int roleid);
	
	public void setPrivilegeByRoleId(int roleid,List<Integer> functionIds);
    /*权限管理模块*/
	
	public List<AdminFunction> findAllFunctions();

	public List<AdminFunction> findAllFunctionsByPid(Integer pid);

	public long createPrivillege(AdminFunction function);

	public long modifyPrivillege(AdminFunction function);

	public AdminFunction getPrivillegeById(int id);

	public long delPrivillege(int id);

	public long assignFunction(int roleId, String ids);
	
	public List<String> getAuthorityUrl(List<Integer> functionIds);
	
	public List<AdminFunction> getFunctionsByIds(List<Integer>ids);
	
	public List<AdminFunction> getAllUsableFunction();
	
	public int resetRoleFunctionIds();
	
	public int resetAdminRoleIds();
	
	//根据岗位名称查询该岗位角色
	public List<Admin> getAdminsByRoleName(String roleName);
}
