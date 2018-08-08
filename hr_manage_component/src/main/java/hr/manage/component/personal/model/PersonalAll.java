package hr.manage.component.personal.model;

/**
 * PersonalAll
 * 
 * @author 
 * @version 1.0.0 2018-07-24
 */
public class PersonalAll implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -7685182439356101441L;

    /** personal_info */
    private PersonalInfo personalInfo;
    /** personal_work_info */
    private PersonalWorkInfo personalWorkInfo;
    /** personal_salary_info */
    private PersonalSalaryInfo personalSalaryInfo;
	public PersonalInfo getPersonalInfo() {
		return personalInfo;
	}
	public void setPersonalInfo(PersonalInfo personalInfo) {
		this.personalInfo = personalInfo;
	}
	public PersonalWorkInfo getPersonalWorkInfo() {
		return personalWorkInfo;
	}
	public void setPersonalWorkInfo(PersonalWorkInfo personalWorkInfo) {
		this.personalWorkInfo = personalWorkInfo;
	}
	public PersonalSalaryInfo getPersonalSalaryInfo() {
		return personalSalaryInfo;
	}
	public void setPersonalSalaryInfo(PersonalSalaryInfo personalSalaryInfo) {
		this.personalSalaryInfo = personalSalaryInfo;
	}
    
    
}