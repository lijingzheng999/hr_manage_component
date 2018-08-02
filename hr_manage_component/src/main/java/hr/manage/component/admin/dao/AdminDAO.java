package hr.manage.component.admin.dao;

import hr.manage.component.admin.model.Admin;

import java.util.List;

import net.paoding.rose.jade.annotation.ReturnGeneratedKeys;
import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.plugin.sql.GenericDAO;

@DAO(catalog="hr_manage")
public interface AdminDAO  extends GenericDAO<Admin, Long>{

	final static String admin = "userid,username,password,personal_info_id,roleids,rolenames,lastloginip,lastlogintime,email,realname,status,mobile_phone,update_time,create_time";

	@SQL("select "+admin+" from admin where username=:1")
	public Admin getAdminByUsername(String username);
	
	@SQL("select " + admin + " from admin where userid = :1")
	public Admin getUserById(int userid);
	

	@SQL("select "+admin+" from admin where personal_info_id=:1")
	public Admin getAdminByInfoId(Integer personalInfoId);
	
	
	@SQL("update admin set username=:1.username,password=:1.password,roleids=:1.roleids,lastloginip=:1.lastloginip," +
			 "lastlogintime=NOW(),email=:1.email,realname=:1.realname,status=:1.status,mobile_phone=:1.mobilePhone,update_time=:1.updateTime where userid=:1.userid")
		public int updateUser(Admin user);
			
	@ReturnGeneratedKeys
	@SQL("insert into admin ( username,password,personal_info_id,roleids,rolenames,email,realname,status,mobile_phone,create_time ) "
			+ "values (:1.username,:1.password,:1.personalInfoId,:1.roleids,:1.rolenames,:1.email,:1.realname,:1.status,:1.mobilePhone,NOW() )")
	public int  addAdmin(Admin admin);
	
	
	///////////////////////////////////////////////////////////////////////////////////
	@SQL("select " + admin + " from admin where username = :1 and password = :2 and status=1")
	public Admin getUser(String username, String password);
	
	
	
	@SQL("insert into admin(username,password,personal_info_id,email,realname,status)" +
	 	  "values (:1.username,:1.password,:1.personal_info_id,:1.email,:1.realname,:1.status)")
	public int addUser(Admin admin);
	
	@SQL("update admin set status=0 where userid = :1")
	public int delUser(int userid);
	
	@SQL("select " + admin + " from admin #if(:1 > 0){where personal_info_id = :1} #if(:1 == 0){where personal_info_id <> 1} order by lastlogintime desc #if(:3 > 0){limit :2,:3}")
	public List<Admin> getUserList(int personalInfoId, int index, int limit);
	
	@SQL("select count(*) from admin #if(:1 > 0){where personal_info_id = :1} #if(:1 == 0){where personal_info_id <> 1}")
	public int countUserList(int personalInfoId);
	
	@SQL("select count(*) from admin where username = :1")
	public int checkUserName(String username);
	
	
	@SQL("select count(*) from admin where #if(:1>0){userid = :1 and } #if(:2>0){username like :3 and } #if(:4>0){personal_info_id=:4 and } userid > 0")
	public int countUserSearchList(int userid, int searchUserName, String username, int personalInfoId);
	
	@SQL("select " + admin + " from admin where #if(:1>0){userid = :1 and } #if(:2>0){username like :3 and } #if(:4>0){personal_info_id=:4 and } userid > 0 order by lastlogintime desc #if(:6 > 0){limit :5,:6}")
	public List<Admin> getUserSearchList(int userid, int searchUserName, String username, int personalInfoId, int index, int limit);

/*----------------------------------------------admin（new）DAO-----------------------------------------------------------*/
	@SQL("select "+admin+" from admin where status=1 order by lastlogintime desc #if(:2>0){limit :1,:2}")
	public List<Admin> getAdminList(int index,int pageSize);
	
	@SQL("select count(1) from admin where status=1")
	public int countAdminList();
	
	@SQL("select "+admin+" from admin where #if(:1){username like '%##(:1)%' and } #if(:2){realname like '%##(:2)%' and } "
			+ "#if(:3){mobile_phone like '%##(:3)%'  and } #if(:4){email like '%##(:4)%'  and } #if(:5){rolenames like '%##(:5)%'  and } status=1 order by create_time desc #if(:7>0){limit :6,:7}")
	public List<Admin> searchAdmin(String username, String realname,String mobilePhone, String email,String rolenames, int page,int pageSize);
	
	@SQL("select count(*) from admin where #if(:1){username like '%##(:1)%' and } #if(:2){realname like '%##(:2)%' and } "
			+ "#if(:3){mobile_phone like '%##(:3)%'  and } #if(:4){email like '%##(:4)%' and } #if(:5){rolenames like '%##(:5)%' and } status=1")
	public int getSearchAdminCount(String username,String realname,String mobilePhone,String email,String rolenames);
	
	
	@SQL("update  admin set status=0 where userid in (:1) ")
	public void deleteAdmin(List<Integer> userids);
	
	@SQL("select " + admin + " from admin where userid = :1")
	public Admin getAdminById(int userid);
	
	
	@SQL("update admin set password=:2 where userid=:1")
	public void resetPassword(int userid,String password);
	
//	@SQL("update admin set username=:1.username,realname=:1.realname,email=:1.email,mobile_phone=:1.mobilePhone where userid=:1.userid")
	@SQL("update admin set #if(:1.username){username=:1.username, } #if(:1.realname){ realname=:1.realname, } #if(:1.email){ email=:1.email, } #if(:1.mobilePhone){mobile_phone=:1.mobilePhone,} userid=:1.userid where userid=:1.userid ")
	public void modifyAdmin(Admin admin);
	
	/*-----------------------------角色分配------------------------------------*/
	@SQL("update admin set rolenames=:2,roleids=:3 where userid=:1")
	public void setAdminRoleName(int userid,String rolename,String roleidStr);
	
	@SQL("update admin set roleids='',rolenames='' where userid in (:1) ")
	public void removeRole(List<Integer> userids);
	
	@SQL("select "+admin+" from admin where roleids like '%,##(:1),%' ")
	public List<Admin> getAdminByRoleIds(int roleid);

}
