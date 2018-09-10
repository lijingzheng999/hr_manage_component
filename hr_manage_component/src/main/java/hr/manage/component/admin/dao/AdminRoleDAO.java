package hr.manage.component.admin.dao;

import hr.manage.component.admin.model.AdminRole;

import java.util.List;

import net.paoding.rose.jade.annotation.ReturnGeneratedKeys;
import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.plugin.sql.GenericDAO;

@DAO(catalog="hr_manage")
public interface AdminRoleDAO  extends GenericDAO<AdminRole, Long>{
	
	final static String adminRole = "roleid,rolename,description,disabled,role_modules, role_columns";
	final static String ADMIN_ROLE ="roleid,rolename,description,disabled,functionIds,update_time,create_time";
	final static int USABLE  = 1;
	
	@SQL("select "+ADMIN_ROLE+" from admin_role where roleid in (:1) ")
	public List<AdminRole> getAdminRoleByIds(List<Integer> roleids);
	
	@SQL("select " + ADMIN_ROLE + " from admin_role where roleid = :1")
	public AdminRole getRole(int roleid);
	
//////////////////////////////////////////////////////////////////////////////////////////////
	
	
	@SQL("select " + adminRole + " from admin_role #if(:1 == 0){where roleid <> 1}")
	public List<AdminRole> getRoleList(int superAdminAvaiable);
	
	@SQL("insert into admin_role(rolename,description,disabled)" +
			"values(:1.rolename,:1.description,:1.disabled)")
	public int addRole(AdminRole adminRole);
	
	@SQL("select count(*) from admin_role where rolename = :1")
	public int checkRoleName(String rolename);
	
	@SQL("update admin_role set rolename=:1.rolename,description=:1.description,disabled=:1.disabled where roleid =:1.roleid")
	public int updateRole(AdminRole role);
	
	@SQL("update admin_role set role_modules = :1.roleModules where roleid =:1.roleid")
	public int updateRoleModule(AdminRole role);
	
	@SQL("delete from admin_role where roleid = :1")
	public int deleteAdminRole(int roleid);
	
	@SQL("update admin_role set role_columns = :1.roleColumns where roleid =:1.roleid")
	public int updateRoleColumn(AdminRole role);
	
	/*---------------------------adminRole(new) 角色管理----------------------------------------------*/
	@SQL(" select "+ADMIN_ROLE+" from admin_role order by create_time desc  #if(:2 > 0){limit :1,:2}")
	public List<AdminRole>getAdminRoleList(int index,int pageSize);
	
	@SQL(" select count(1) from admin_role ")
	public int getAdminRoleCount();
	
	@SQL("select count(1)  from admin_role where #if(:1){ rolename like '%##(:1)%' and } "
			+ "#if(:2){ description like '%##(:2)%' and } #if(:3!=null){ disabled =:3 and } 1=1 ")
	public int searchAdminRoleCount(String rolename,String description,Integer disabled);
	
	@SQL("select "+ADMIN_ROLE+" from admin_role where #if(:1){ rolename like '%##(:1)%' and } "
			+ "#if(:2){ description like '%##(:2)%' and } #if(:3!=null){ disabled =:3 and } 1=1 order by create_time desc #if(:5>0){limit :4,:5}")
	public List<AdminRole> searchAdminRole(String rolename,String description,Integer disabled,int index,int pageSize);
	
	@SQL("select "+ADMIN_ROLE+" from admin_role where #if(:1){ rolename like '%##(:1)%' and } "
			+ "#if(:2){ description like '%##(:2)%' and } #if(:3!=null){ disabled =:3 and } roleid in (:4)  #if(:6>0){limit :5,:6}")
	public List<AdminRole> searchAdminRoleByUserids(String rolename,String description,Integer disabled,List<Integer> loginUserRoleids,int index,int pageSize);
	
	@SQL("select count(*)  from admin_role where #if(:1){ rolename like '%##(:1)%' and } "
			+ "#if(:2){ description like '%##(:2)%' and } #if(:3!=null){ disabled =:3 and } roleid in (:4) ")
	public int searchAdminRoleByUseridsCount(String rolename,String description,Integer disabled,List<Integer> loginUserRoleids);
	
	@ReturnGeneratedKeys
	@SQL(" insert into admin_role ( rolename,description,listorder,disabled,role_modules, role_columns,create_time,update_time ) "
			+ "values (:1.rolename,:1.description,:1.listorder,:1.disabled,:1.roleModules,:1.roleColumns,NOW(),NOW() )")
	public int addAdminRole(AdminRole adminRole);
	
	@SQL(" delete from admin_role where roleid in (:1) ")
	public void deleteAdminRoleById(List<Integer> roleids);
	
	@SQL(" update admin_role set rolename=:1.rolename,description=:1.description,disabled=:1.disabled,update_time=NOW() where roleid=:1.roleid ")
	public void modifyAdminRole(AdminRole adminRole);
	
	@SQL(" select "+ADMIN_ROLE+" from admin_role where rolename=:1 ")
	public AdminRole getAdminRoleByName(String rolename);
	
	
	
	/*-----------------------------------角色分配----------------------------------------*/
	
	@SQL(" select "+ADMIN_ROLE+" from admin_role where disabled="+USABLE+" and roleid in (:1) ")
	public List<AdminRole> getUsableRole(List<Integer> userRoleids);
	
	@SQL(" select "+ADMIN_ROLE+" from admin_role where disabled="+USABLE  )
	public List<AdminRole> getAllUsableRole();
	
	@SQL("select "+ADMIN_ROLE+" from admin_role where roleid in (:1)")
	public List<AdminRole> getAdminRoleById(List<Integer> roleids);
	
	@SQL("select functionIds from admin_role where roleid =:1 ")
	public String getPrivilegeByRoleId(int roleid);
	
	@SQL("update admin_role set functionIds=:2 where roleid=:1")
	public void setPrivilegeByRoleId(int roleid,String functionIds);
	
	
	/*------------------权限控制，删除权限id时，查找出含有此functionId的角色---------------*/
	
	@SQL("select "+ADMIN_ROLE+" from admin_role where functionIds like '%,##(:1),%' ")
	public List<AdminRole> getAdminRolesByFunctionId(Integer functionId);
}
