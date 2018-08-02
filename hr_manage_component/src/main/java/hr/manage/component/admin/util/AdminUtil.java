package hr.manage.component.admin.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AdminUtil {
	/**
	 * 将admin中的角色roleids转化为List<Integer>(权限适用)	
	 * @param idsStr
	 * @return
	 */
	public static List<Integer> getAdminRoleIds(String idsStr){
		List<Integer> intIds = new ArrayList<Integer>();
		if(idsStr==null || idsStr.trim().length()==0){
			return intIds;
		}
		String stringIds = idsStr.trim().substring(1,idsStr.trim().lastIndexOf(","));
		if(stringIds.length()==0){
			return intIds;
		}
		String[] roleids = stringIds.split(",");
		for(String roleid : roleids){
			if(roleid.contains("null")){
				continue;
			}
			intIds.add(Integer.parseInt(roleid.trim()));
		}
		return intIds;
	}
	
	/**
	 * 删除admin中roleids字符串中某id,并转化为List<Integer> (权限适用)	
	 * @param idsStr   roleids字符串
	 * @param roleid	要删除的id  
	 * @return
	 */
	public static List<Integer> removeAminRoleId(String idsStr,int roleid){
		List<Integer> idsIntegers = getAdminRoleIds(idsStr);
		for(int i=0 ; i<idsIntegers.size() ; i++){
			if(idsIntegers.get(i)==roleid){
				idsIntegers.remove(i);
			}
		}
		return idsIntegers;
	}
	
	/**
	 * 判断某用户是否含有某角色（或权限）（权限适用）
	 * @param user
	 * @param admin
	 * @return
	 */
	public static boolean haveRoleid(List<Integer> user,List<Integer> admin){
		if(user==null || user.size()==0 || user.size()<admin.size()){
			return false;
		}
		if (user!=null && user.size()>0 && (admin==null || admin.size()==0)){
			return true;
		}
		Collections.sort(user);
		Collections.sort(admin);
		if(user.containsAll(admin)){
			return true;
		}
		if(user.size()==admin.size()){
			boolean boo = true;
			for(int i=0;i<user.size();i++){
				if(!user.get(i).equals(admin.get(i))){
					boo = false;
					break;
				}
			}
			return boo;
		}
		return false;
	}
	
	/**
	 *  判断某用户是否含有某角色（或权限）（权限适用）
	 * @param user
	 * @param adminRoleids
	 * @return
	 */
	public static boolean haveRoleid(List<Integer> user,String adminRoleids){
		List<Integer> admin = getAdminRoleIds(adminRoleids);
		return haveRoleid(user, admin);
	}
	
	public static boolean haveSomeAuthority(List<Integer> functionIds,Integer functionId){
		if(functionIds != null && functionIds.size()>0){
			return functionIds.contains(functionId);
		}
		return false;
	}
}
