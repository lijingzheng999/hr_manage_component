package hr.manage.component.admin.service;


import java.util.List;
import org.springframework.stereotype.Component;

@Component
public interface AdminAuthorityServiceHome {
	public boolean haveAuthority(int adminId,int functionId);
	//根据登陆用户id查找该用户权限
	public List<Integer> getFunctionIds(int userid);
		
//	public boolean haveAuthorityUrl(int adminId,String url);
//	//查找权限小于loginUser的用户
//	public Map<String,Object> findAuthorityFunctionLessThanUser(int userid,String username,String realname, String mobilePhone,String email, String rolename,int page, int pageSize);
//	//查找角色小于loginUser的用户
//	public List<Admin> findAuthorityRoleLessThanUser(int  userid,String username,String realname, String mobilePhone,String email, String rolename,int page, int pageSize);
//	//查找权限小于loginUser的角色
//	public Map<String,Object> findAdminRoleLessThanUser(int userid,String rolename, String description,Integer disabled,int page,int pageSize);
//	//查找权限小于loginUser的可用角色
//	public List<AdminRole> getAllUsableRoleLessThanUser(int userid);
//	
//	//查找权限符合接收短信的角色
//	List<Admin> getAdminsByRoleName(String roleName);
}
