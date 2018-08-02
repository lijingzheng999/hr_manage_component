package hr.manage.component.admin.dao;

import hr.manage.component.admin.model.AdminFunction;
import hr.manage.component.admin.model.AdminRole;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.ReturnGeneratedKeys;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.plugin.sql.GenericDAO;

@DAO(catalog="hr_manage")
public interface AdminRolePrivDAO   extends GenericDAO<AdminFunction, Long>{

	final static String adminFunction = "id,name,description,isMenu,pid,isGroup,listOrder,level";
	final static String functionCols = "name,description,isMenu,pid,isGroup,listOrder,level";

	@SQL("select " + adminFunction + " from admin_function;")
	public List<AdminFunction> findAllFunctions();

	@SQL("select " + adminFunction + " from admin_function where pid = :1 ;")
	public List<AdminFunction> findAllFunctionsByPid(Integer pid);

	@ReturnGeneratedKeys
	@SQL("insert into admin_function ("+functionCols+") values(:1.name,:1.description, :1.isMenu, :1.url, :1.dependUrl, :1.pid, :1.isGroup, :1.listOrder, :1.level);")
	public long createPrivillege(AdminFunction function);

	@SQL("update admin_function set #if(:1.name !=null){name = :1.name,} " +
			"#if(:1.description!=null){description = :1.description,} #if(:1.isMenu!=-1){isMenu = :1.isMenu ,}  #if(:1.url !=null){url = :1.url, } #if(:1.dependUrl!=null){dependUrl = :1.dependUrl, }  #if(:1.pid!=-1){pid = :1.pid,} #if(:1.isGroup!=-1){isGroup = :1.isGroup,} #if(:1.listOrder !=null){listOrder = :1.listOrder,} " +
			"#if(:1.level !=null){level = :1.level} " +
			" where id = :1.id;")
	public long updatePrivillege(AdminFunction function);

	@SQL("select " + adminFunction + " from admin_function where id = :1 ;")
	public AdminFunction findFunctionById(Integer id);
	
	@SQL("update admin_function set isDel = 1 where id= :1;")
	public void delFunction(Integer id);

	@SQL("update admin_role set functionIds = :2 where roleid = :1;")
	public void assignFunction(int roleId, String ids);
	
	@SQL(" select url from admin_function where id in(:1);")
	public List<String> getFunctionUrl(List<Integer> functionIds);
	
	@SQL(" select "+adminFunction+" from admin_function where id in(:1);")
	public List<AdminFunction> getFunctionByIds(List<Integer> ids);
	
	@SQL("select " + adminFunction + " from admin_function ;")
	public List<AdminFunction> getAllUsableFunction();
	
	@SQL("select " + adminFunction + " from admin_function where isDel = 1;")
	public List<AdminFunction> getAllDisableFunctions();
}
