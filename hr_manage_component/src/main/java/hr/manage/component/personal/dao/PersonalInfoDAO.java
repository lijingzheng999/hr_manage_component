package hr.manage.component.personal.dao;

import hr.manage.component.personal.model.PersonalInfo;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.plugin.sql.GenericDAO;

@DAO(catalog="hr_manage")
public interface PersonalInfoDAO  extends GenericDAO<PersonalInfo,Integer>{
	
	public static final String TABLE = " personal_info ";
	
	public static final String COLUMNS = "   id,employee_number,name,phone,email,identity_card,sex,age,birthday,home_property,native_place,marriage,nation,title,education,school,major,english,graduation_time,working_life,contact_address,home_address,contact,contact_phone,memo,is_del,update_time,create_time  ";

	 @SQL(" select count(1)  FROM "+TABLE+"  where name = :1 and identity_card=:2 and is_del=1 ")
	 int checkPersonalByNameAndCard(String name, String identityCard);
	 
	 @SQL(" select count(1)  FROM "+TABLE )
	 int countPersonalInfo();
	 
	    @SQL("SELECT  " + COLUMNS + " FROM "+TABLE+" WHERE 1 = 1 " +
	            "#if(:1.employee_number != null) { and employee_number = :1.employeeNumber } " +
	            " order by id ")
	    PersonalInfo getPersonalInfoById(PersonalInfo personalInfo);

}
