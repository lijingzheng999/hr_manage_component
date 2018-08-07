package hr.manage.component.personal.model;

import java.math.BigDecimal;
import java.util.Date;
public class PersonalAllExport implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -7685182439356101440L;
    /////////////////////基本信息表////////
    /** 员工表的主键 */
    private Integer id;

    /** 员工编号 项目+部门+自增编号 */
    private String employeeNumber;

    /** 姓名 */
    private String name;

    /** 联系电话 */
    private String phone;

    /** 邮箱 */
    private String email;

    /** 身份证 */
    private String identityCard;

    /** 性别 */
    private String sex;

    /** 年龄 */
    private Integer age;

    /** 出生日期 */
    private String birthday;

    /** 户口性质 */
    private String homeProperty;

    /** 籍贯 */
    private String nativePlace;

    /** 婚姻状况 */
    private String marriage;

    /** 民族 */
    private String nation;

    /** 职称 */
    private String title;

    /** 学历 */
    private String education;

    /** 毕业学校 */
    private String school;

    /** 毕业专业 */
    private String major;

    /** 英语 */
    private String english;

    /** 毕业时间 */
    private Date graduationTime;

    /** 工作年限 */
    private String workingLife;

    /** 联系地址 */
    private String contactAddress;

    /** 户籍地址 */
    private String homeAddress;

    /** 紧急联系人 */
    private String contact;

    /** 紧急联系人电话 */
    private String contactPhone;

    /** 备注 */
    private String memo;


    /** 创建时间 */
    private Date createTime;

///////////////////工作表///////////////
    /** 工作地点 */
    private String workingPlace;

    /** 岗位类别 */
    private String postType;

    /** 职位 */
    private String position;

    /** 级别 */
    private String level;

    /** 部门 */
    private String department;

    /** 中心 */
    private String center;

    /** 项目 */
    private String project;

    /** 全通负责人 */
    private String expatriateManager;

    /** 外派单位 */
    private String expatriateUnit;

    /** 招聘渠道 */
    private String recruitChannel;

    /** 工作地址 */
    private String workingAddress;

    /** 合同签署次数 */
    private Integer contractCount;


    /** 合同生效日期 */
    private Date contractStartdate;

    /** 合同失效日期 */
    private Date contractEnddate;

    /** 续签合同日期 */
    private Date contractRenewDate;

    /** 续签合同失效日期 */
    private Date contractRenewEnddate;

    /** 是否撤离 */
    private String isLeave;

    /** 是否离职 */
    private String leaveWorking;

    /** 离职日期 */
    private Date leaveWorkingTime;
    
///////////////////工资表///////////////
    
    /** 入职时间 */
    private Date entryTime;

    /** 到岗时间 */
    private Date arrivalTime;

    /** 转正时间 */
    private Date workerTime;

    /** 工龄 */
    private BigDecimal workingYears;

    /** 缴纳社保起始月份 */
    private String insuranceBeginDate;

    /** 社保缴纳地点 */
    private String insurancePlace;

    /** 招商银行卡号 */
    private String bankCardNumber;

    /** 开户行 */
    private String bankOpenPlace;

    /** 试用期 */
    private String probationPeriod;

    /** 试用期福利 */
    private String probationPeriodWelfare;

    /** 转正福利 */
    private String workerWelfare;

    /** 基本工资 */
    private Integer basePay;

    /** 绩效工资 */
    private Integer meritPay;

    /** 补贴 */
    private Integer subsidy;

    /** 转正工资 */
    private Integer workerPay;

    /** 试用期工资 */
    private Integer probationaryPay;

    /** 结算价 */
    private BigDecimal settlementPrice;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmployeeNumber() {
		return employeeNumber;
	}

	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIdentityCard() {
		return identityCard;
	}

	public void setIdentityCard(String identityCard) {
		this.identityCard = identityCard;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getHomeProperty() {
		return homeProperty;
	}

	public void setHomeProperty(String homeProperty) {
		this.homeProperty = homeProperty;
	}

	public String getNativePlace() {
		return nativePlace;
	}

	public void setNativePlace(String nativePlace) {
		this.nativePlace = nativePlace;
	}

	public String getMarriage() {
		return marriage;
	}

	public void setMarriage(String marriage) {
		this.marriage = marriage;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getEnglish() {
		return english;
	}

	public void setEnglish(String english) {
		this.english = english;
	}

	public Date getGraduationTime() {
		return graduationTime;
	}

	public void setGraduationTime(Date graduationTime) {
		this.graduationTime = graduationTime;
	}

	public String getWorkingLife() {
		return workingLife;
	}

	public void setWorkingLife(String workingLife) {
		this.workingLife = workingLife;
	}

	public String getContactAddress() {
		return contactAddress;
	}

	public void setContactAddress(String contactAddress) {
		this.contactAddress = contactAddress;
	}

	public String getHomeAddress() {
		return homeAddress;
	}

	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getWorkingPlace() {
		return workingPlace;
	}

	public void setWorkingPlace(String workingPlace) {
		this.workingPlace = workingPlace;
	}

	public String getPostType() {
		return postType;
	}

	public void setPostType(String postType) {
		this.postType = postType;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getCenter() {
		return center;
	}

	public void setCenter(String center) {
		this.center = center;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getExpatriateManager() {
		return expatriateManager;
	}

	public void setExpatriateManager(String expatriateManager) {
		this.expatriateManager = expatriateManager;
	}

	public String getExpatriateUnit() {
		return expatriateUnit;
	}

	public void setExpatriateUnit(String expatriateUnit) {
		this.expatriateUnit = expatriateUnit;
	}

	public String getRecruitChannel() {
		return recruitChannel;
	}

	public void setRecruitChannel(String recruitChannel) {
		this.recruitChannel = recruitChannel;
	}

	public String getWorkingAddress() {
		return workingAddress;
	}

	public void setWorkingAddress(String workingAddress) {
		this.workingAddress = workingAddress;
	}

	public Integer getContractCount() {
		return contractCount;
	}

	public void setContractCount(Integer contractCount) {
		this.contractCount = contractCount;
	}


	public Date getContractStartdate() {
		return contractStartdate;
	}

	public void setContractStartdate(Date contractStartdate) {
		this.contractStartdate = contractStartdate;
	}

	public Date getContractEnddate() {
		return contractEnddate;
	}

	public void setContractEnddate(Date contractEnddate) {
		this.contractEnddate = contractEnddate;
	}

	public Date getContractRenewDate() {
		return contractRenewDate;
	}

	public void setContractRenewDate(Date contractRenewDate) {
		this.contractRenewDate = contractRenewDate;
	}

	public Date getContractRenewEnddate() {
		return contractRenewEnddate;
	}

	public void setContractRenewEnddate(Date contractRenewEnddate) {
		this.contractRenewEnddate = contractRenewEnddate;
	}

	public String getIsLeave() {
		return isLeave;
	}

	public void setIsLeave(String isLeave) {
		this.isLeave = isLeave;
	}

	public String getLeaveWorking() {
		return leaveWorking;
	}

	public void setLeaveWorking(String leaveWorking) {
		this.leaveWorking = leaveWorking;
	}

	public Date getLeaveWorkingTime() {
		return leaveWorkingTime;
	}

	public void setLeaveWorkingTime(Date leaveWorkingTime) {
		this.leaveWorkingTime = leaveWorkingTime;
	}

	public Date getEntryTime() {
		return entryTime;
	}

	public void setEntryTime(Date entryTime) {
		this.entryTime = entryTime;
	}

	public Date getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(Date arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public Date getWorkerTime() {
		return workerTime;
	}

	public void setWorkerTime(Date workerTime) {
		this.workerTime = workerTime;
	}

	public BigDecimal getWorkingYears() {
		return workingYears;
	}

	public void setWorkingYears(BigDecimal workingYears) {
		this.workingYears = workingYears;
	}

	public String getInsuranceBeginDate() {
		return insuranceBeginDate;
	}

	public void setInsuranceBeginDate(String insuranceBeginDate) {
		this.insuranceBeginDate = insuranceBeginDate;
	}

	public String getInsurancePlace() {
		return insurancePlace;
	}

	public void setInsurancePlace(String insurancePlace) {
		this.insurancePlace = insurancePlace;
	}

	public String getBankCardNumber() {
		return bankCardNumber;
	}

	public void setBankCardNumber(String bankCardNumber) {
		this.bankCardNumber = bankCardNumber;
	}

	public String getBankOpenPlace() {
		return bankOpenPlace;
	}

	public void setBankOpenPlace(String bankOpenPlace) {
		this.bankOpenPlace = bankOpenPlace;
	}

	public String getProbationPeriod() {
		return probationPeriod;
	}

	public void setProbationPeriod(String probationPeriod) {
		this.probationPeriod = probationPeriod;
	}

	public String getProbationPeriodWelfare() {
		return probationPeriodWelfare;
	}

	public void setProbationPeriodWelfare(String probationPeriodWelfare) {
		this.probationPeriodWelfare = probationPeriodWelfare;
	}

	public String getWorkerWelfare() {
		return workerWelfare;
	}

	public void setWorkerWelfare(String workerWelfare) {
		this.workerWelfare = workerWelfare;
	}

	public Integer getBasePay() {
		return basePay;
	}

	public void setBasePay(Integer basePay) {
		this.basePay = basePay;
	}

	public Integer getMeritPay() {
		return meritPay;
	}

	public void setMeritPay(Integer meritPay) {
		this.meritPay = meritPay;
	}

	public Integer getSubsidy() {
		return subsidy;
	}

	public void setSubsidy(Integer subsidy) {
		this.subsidy = subsidy;
	}

	public Integer getWorkerPay() {
		return workerPay;
	}

	public void setWorkerPay(Integer workerPay) {
		this.workerPay = workerPay;
	}

	public Integer getProbationaryPay() {
		return probationaryPay;
	}

	public void setProbationaryPay(Integer probationaryPay) {
		this.probationaryPay = probationaryPay;
	}

	public BigDecimal getSettlementPrice() {
		return settlementPrice;
	}

	public void setSettlementPrice(BigDecimal settlementPrice) {
		this.settlementPrice = settlementPrice;
	}

}