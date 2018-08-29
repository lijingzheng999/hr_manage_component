package hr.manage.component.personal.service.impl;

import hr.manage.component.common.constants.DepartmentConstants;
import hr.manage.component.common.constants.UnitConstants;
import hr.manage.component.contract.dao.ContractInfoDAO;
import hr.manage.component.contract.model.ContractInfo;
import hr.manage.component.personal.dao.PersonalInfoDAO;
import hr.manage.component.personal.dao.PersonalSalaryInfoDAO;
import hr.manage.component.personal.dao.PersonalWorkInfoDAO;
import hr.manage.component.personal.model.PersonalAll;
import hr.manage.component.personal.model.PersonalAllExport;
import hr.manage.component.personal.model.PersonalCondition;
import hr.manage.component.personal.model.PersonalInfo;
import hr.manage.component.personal.model.PersonalSalaryInfo;
import hr.manage.component.personal.model.PersonalWorkInfo;
import hr.manage.component.personal.service.PersonalService;
import hr.manage.component.util.BigDecimalUtil;
import hr.manage.component.util.DateTimeUtil;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class PersonalServiceImpl implements PersonalService {

	private final Log logger = LogFactory.getLog(PersonalServiceImpl.class);
	@Autowired
	PersonalInfoDAO personalInfoDAO;
	@Autowired
	PersonalSalaryInfoDAO personalSalaryInfoDAO;
	@Autowired
	PersonalWorkInfoDAO personalWorkInfoDAO;
	@Autowired
	ContractInfoDAO contractInfoDAO;

	@Override
	public int checkPersonalByNameAndCard(String name, String identityCard) {
		return personalInfoDAO.checkPersonalByNameAndCard(name, identityCard);
	}
	@Override
	public PersonalInfo getPersonalByName(String name){
		return personalInfoDAO.getPersonalByName(name);
	}
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = { Exception.class })
	public synchronized int savePersonalAllListRecord(
			List<PersonalAll> personAllList) {
		int result = 0;
		for (PersonalAll curPerson : personAllList) {
			PersonalInfo person = curPerson.getPersonalInfo();
			PersonalWorkInfo work = curPerson.getPersonalWorkInfo();
			PersonalSalaryInfo salary = curPerson.getPersonalSalaryInfo();
			// 补全默认值
			// 生成员工编号 项目+部门+自增编号
			String unitCode = UnitConstants.seletCode(work.getExpatriateUnit());// 外派简称
			String departCode = DepartmentConstants.seletCode(work
					.getDepartment());// 部门简称
			int personNum = personalInfoDAO.countPersonalInfo();
			String strPersonNum = new DecimalFormat("0000")
					.format(personNum + 1);
			String employeeNumber = unitCode + departCode + strPersonNum;
			logger.info("savePersonalAllListRecord : 员工编号：" + employeeNumber);

			person.setEmployeeNumber(employeeNumber);
			person.setIsDel(1);
			person.setCreateTime(new Date());
			// 保存员工个人信息表,返回主键ID
			Integer personalInfoId = personalInfoDAO.save(person);
			work.setPersonalInfoId(personalInfoId);
			work.setIsDel(1);
			work.setCreateTime(new Date());
			Integer workId = personalWorkInfoDAO.save(work);
			salary.setPersonalInfoId(personalInfoId);
			salary.setIsDel(1);
			salary.setCreateTime(new Date());
			Integer salaryId = personalSalaryInfoDAO.save(salary);

			ContractInfo contractInfo = new ContractInfo();
			contractInfo.setPersonalInfoId(personalInfoId);
			contractInfo.setEmployeeNumber(employeeNumber);
			contractInfo.setName(person.getName());
			contractInfo.setContractNumber(employeeNumber + "01"); // 合同编号=员工编号+"01"
			contractInfo.setPosition(work.getPosition());
			contractInfo.setStartDate(work.getContractStartdate());
			contractInfo.setEndDate(work.getContractEnddate());
			contractInfo.setContractCount(1); // 导入默认为第一次；
			contractInfo.setMemo(person.getMemo());
			contractInfo.setIsDel(1);
			contractInfo.setCreateTime(new Date());
			contractInfoDAO.save(contractInfo);
			logger.info("savePersonalAll : 员工编号：" + employeeNumber
					+ person.getName());
		}
		result = 1;
		return result;
	}

	@Override
	public PersonalAll getPersonalAllInfoById(Integer personalInfoId) {
		PersonalAll personalAll = new PersonalAll();
		PersonalInfo personalInfo = personalInfoDAO
				.getPersonalInfoById(personalInfoId);
		PersonalWorkInfo personalWorkInfo = personalWorkInfoDAO
				.getPersonalWorkInfoById(personalInfoId);
		PersonalSalaryInfo personalSalaryInfo = personalSalaryInfoDAO
				.getPersonalSalaryInfoById(personalInfoId);
		personalAll.setPersonalInfo(personalInfo);
		personalAll.setPersonalWorkInfo(personalWorkInfo);
		personalAll.setPersonalSalaryInfo(personalSalaryInfo);
		return personalAll;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = { Exception.class })
	public int deletePersonalAllInfoById(Integer personalInfoId) {
		int result = 0;
		personalInfoDAO.deletePersonalInfoById(personalInfoId);
		personalWorkInfoDAO.deletePersonalWorkInfoById(personalInfoId);
		personalSalaryInfoDAO.deletePersonalSalaryInfoById(personalInfoId);
		contractInfoDAO.deleteContractInfoByPersonId(personalInfoId);
		result = 1;
		return result;
	}

	@Override
	public List<PersonalAllExport> listPersonalAllExport(
			PersonalCondition condition) {
		return personalInfoDAO.listPersonalAllExport(condition);
	}

	@Override
	public Long countPersonalAllExport(PersonalCondition condition) {
		return personalInfoDAO.countPersonalAllExport(condition);
	}

	// 修改本人-限制字段
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = { Exception.class })
	public int updatePersonalAllInfoBySelf(PersonalAll newPersonalAll) {
		int result = 0;
		PersonalAll PersonalAll = getPersonalAllInfoById(newPersonalAll
				.getPersonalInfo().getId());
		if (PersonalAll.getPersonalInfo() != null) {
			// 赋予新值--基本信息
			PersonalAll.getPersonalInfo().setPhone(
					newPersonalAll.getPersonalInfo().getPhone());
			PersonalAll.getPersonalInfo().setEmail(
					newPersonalAll.getPersonalInfo().getEmail());
			PersonalAll.getPersonalInfo().setIdentityCard(
					newPersonalAll.getPersonalInfo().getIdentityCard());

			// 取生日-年龄
			String strBirthDay = newPersonalAll.getPersonalInfo()
					.getIdentityCard().substring(6, 14);
			SimpleDateFormat sdtAge = new SimpleDateFormat("yyyyMMdd");
			java.util.Date birthDay = null;
			try {
				birthDay = sdtAge.parse(strBirthDay);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String curAge = String.valueOf(
					DateTimeUtil.yearDateDiff(birthDay, new Date())).trim();
			PersonalAll.getPersonalInfo().setAge(Integer.parseInt(curAge));

			// 判断性别
			String curSex = "";
			if (Integer.parseInt(newPersonalAll.getPersonalInfo()
					.getIdentityCard().substring(16).substring(0, 1)) % 2 == 0) {
				curSex = "女";
			} else {
				curSex = "男";
			}
			PersonalAll.getPersonalInfo().setSex(curSex);

			// String curBirthday =
			// newPersonalAll.getPersonalInfo().getIdentityCard().substring(10,
			// 12)+"月"+newPersonalAll.getPersonalInfo().getIdentityCard().substring(12,
			// 14)+"日";
			PersonalAll.getPersonalInfo().setBirthday(birthDay);

			PersonalAll.getPersonalInfo().setHomeProperty(
					newPersonalAll.getPersonalInfo().getHomeProperty());
			PersonalAll.getPersonalInfo().setNativePlace(
					newPersonalAll.getPersonalInfo().getNativePlace());
			PersonalAll.getPersonalInfo().setMarriage(
					newPersonalAll.getPersonalInfo().getMarriage());
			PersonalAll.getPersonalInfo().setNation(
					newPersonalAll.getPersonalInfo().getNation());
			PersonalAll.getPersonalInfo().setTitle(
					newPersonalAll.getPersonalInfo().getTitle());
			PersonalAll.getPersonalInfo().setEducation(
					newPersonalAll.getPersonalInfo().getEducation());
			PersonalAll.getPersonalInfo().setSchool(
					newPersonalAll.getPersonalInfo().getSchool());
			PersonalAll.getPersonalInfo().setMajor(
					newPersonalAll.getPersonalInfo().getMajor());
			PersonalAll.getPersonalInfo().setEnglish(
					newPersonalAll.getPersonalInfo().getEnglish());
			PersonalAll.getPersonalInfo().setGraduationTime(
					newPersonalAll.getPersonalInfo().getGraduationTime());
			String curWorkingLife = String.valueOf(
					DateTimeUtil.yearDateDiff(newPersonalAll.getPersonalInfo()
							.getGraduationTime(), new Date())
							+ "年").trim();
			PersonalAll.getPersonalInfo().setWorkingLife(curWorkingLife);

			PersonalAll.getPersonalInfo().setHomeAddress(
					newPersonalAll.getPersonalInfo().getHomeAddress());
			PersonalAll.getPersonalInfo().setContactAddress(
					newPersonalAll.getPersonalInfo().getContactAddress());
			PersonalAll.getPersonalInfo().setContact(
					newPersonalAll.getPersonalInfo().getContact());
			PersonalAll.getPersonalInfo().setContactPhone(
					newPersonalAll.getPersonalInfo().getContactPhone());
			PersonalAll.getPersonalInfo().setMemo(
					newPersonalAll.getPersonalInfo().getMemo());
			PersonalAll.getPersonalInfo().setUpdateTime(new Date());
			// 赋予新值--工作信息
			PersonalAll.getPersonalWorkInfo().setWorkingPlace(
					newPersonalAll.getPersonalWorkInfo().getWorkingPlace());
			PersonalAll.getPersonalWorkInfo().setPostType(
					newPersonalAll.getPersonalWorkInfo().getPostType());
			PersonalAll.getPersonalWorkInfo().setDepartment(
					newPersonalAll.getPersonalWorkInfo().getDepartment());
			PersonalAll.getPersonalWorkInfo().setCenter(
					newPersonalAll.getPersonalWorkInfo().getCenter());
			PersonalAll.getPersonalWorkInfo().setProject(
					newPersonalAll.getPersonalWorkInfo().getProject());
			PersonalAll.getPersonalWorkInfo()
					.setExpatriateManager(
							newPersonalAll.getPersonalWorkInfo()
									.getExpatriateManager());
			PersonalAll.getPersonalWorkInfo().setWorkingAddress(
					newPersonalAll.getPersonalWorkInfo().getWorkingAddress());
			PersonalAll.getPersonalWorkInfo().setUpdateTime(new Date());
			// 赋予新值--工资信息
			PersonalAll.getPersonalSalaryInfo().setBankCardNumber(
					newPersonalAll.getPersonalSalaryInfo().getBankCardNumber());
			PersonalAll.getPersonalSalaryInfo().setBankOpenPlace(
					newPersonalAll.getPersonalSalaryInfo().getBankOpenPlace());
			PersonalAll.getPersonalSalaryInfo().setUpdateTime(new Date());

			// 更新记录
			personalInfoDAO.update(PersonalAll.getPersonalInfo());
			personalWorkInfoDAO.update(PersonalAll.getPersonalWorkInfo());
			personalSalaryInfoDAO.update(PersonalAll.getPersonalSalaryInfo());
			result = 1;
		} else {
			logger.error("根据登陆ID未查到基本信息");
			return -1;
		}

		return result;
	}

	// 修改员工
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = { Exception.class })
	public int updatePersonalAllInfo(PersonalAll newPersonalAll) {
		int result = 0;
		PersonalAll PersonalAll = getPersonalAllInfoById(newPersonalAll
				.getPersonalInfo().getId());
		if (PersonalAll.getPersonalInfo() != null) {
			// 赋予新值--基本信息
			PersonalAll.getPersonalInfo().setName(
					newPersonalAll.getPersonalInfo().getName());
			PersonalAll.getPersonalInfo().setPhone(
					newPersonalAll.getPersonalInfo().getPhone());
			PersonalAll.getPersonalInfo().setEmail(
					newPersonalAll.getPersonalInfo().getEmail());
			PersonalAll.getPersonalInfo().setIdentityCard(
					newPersonalAll.getPersonalInfo().getIdentityCard());

			// 取生日-年龄
			String strBirthDay = newPersonalAll.getPersonalInfo()
					.getIdentityCard().substring(6, 14);
			SimpleDateFormat sdtAge = new SimpleDateFormat("yyyyMMdd");
			java.util.Date birthDay = null;
			try {
				birthDay = sdtAge.parse(strBirthDay);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String curAge = String.valueOf(
					DateTimeUtil.yearDateDiff(birthDay, new Date())).trim();
			PersonalAll.getPersonalInfo().setAge(Integer.parseInt(curAge));
			// 判断性别
			String curSex = "";
			if (Integer.parseInt(newPersonalAll.getPersonalInfo()
					.getIdentityCard().substring(16).substring(0, 1)) % 2 == 0) {
				curSex = "女";
			} else {
				curSex = "男";
			}
			PersonalAll.getPersonalInfo().setSex(curSex);
			// 出生年月
			// String curBirthday =
			// newPersonalAll.getPersonalInfo().getIdentityCard().substring(10,
			// 12)+"月"+newPersonalAll.getPersonalInfo().getIdentityCard().substring(12,
			// 14)+"日";
			PersonalAll.getPersonalInfo().setBirthday(birthDay);
			PersonalAll.getPersonalInfo().setHomeProperty(
					newPersonalAll.getPersonalInfo().getHomeProperty());
			PersonalAll.getPersonalInfo().setNativePlace(
					newPersonalAll.getPersonalInfo().getNativePlace());
			PersonalAll.getPersonalInfo().setMarriage(
					newPersonalAll.getPersonalInfo().getMarriage());
			PersonalAll.getPersonalInfo().setNation(
					newPersonalAll.getPersonalInfo().getNation());
			PersonalAll.getPersonalInfo().setTitle(
					newPersonalAll.getPersonalInfo().getTitle());
			PersonalAll.getPersonalInfo().setEducation(
					newPersonalAll.getPersonalInfo().getEducation());
			PersonalAll.getPersonalInfo().setSchool(
					newPersonalAll.getPersonalInfo().getSchool());
			PersonalAll.getPersonalInfo().setMajor(
					newPersonalAll.getPersonalInfo().getMajor());
			PersonalAll.getPersonalInfo().setEnglish(
					newPersonalAll.getPersonalInfo().getEnglish());
			PersonalAll.getPersonalInfo().setGraduationTime(
					newPersonalAll.getPersonalInfo().getGraduationTime());
			// 工作年限
			String curWorkingLife = String.valueOf(
					DateTimeUtil.yearDateDiff(newPersonalAll.getPersonalInfo()
							.getGraduationTime(), new Date())
							+ "年").trim();
			PersonalAll.getPersonalInfo().setWorkingLife(curWorkingLife);
			PersonalAll.getPersonalInfo().setHomeAddress(
					newPersonalAll.getPersonalInfo().getHomeAddress());
			PersonalAll.getPersonalInfo().setContactAddress(
					newPersonalAll.getPersonalInfo().getContactAddress());
			PersonalAll.getPersonalInfo().setContact(
					newPersonalAll.getPersonalInfo().getContact());
			PersonalAll.getPersonalInfo().setContactPhone(
					newPersonalAll.getPersonalInfo().getContactPhone());
			PersonalAll.getPersonalInfo().setMemo(
					newPersonalAll.getPersonalInfo().getMemo());
			PersonalAll.getPersonalInfo().setUpdateTime(new Date());

			// 赋予新值--工作信息-本人可修改值
			PersonalAll.getPersonalWorkInfo().setWorkingPlace(
					newPersonalAll.getPersonalWorkInfo().getWorkingPlace());
			PersonalAll.getPersonalWorkInfo().setPostType(
					newPersonalAll.getPersonalWorkInfo().getPostType());
			PersonalAll.getPersonalWorkInfo().setDepartment(
					newPersonalAll.getPersonalWorkInfo().getDepartment());
			PersonalAll.getPersonalWorkInfo().setCenter(
					newPersonalAll.getPersonalWorkInfo().getCenter());
			PersonalAll.getPersonalWorkInfo().setProject(
					newPersonalAll.getPersonalWorkInfo().getProject());
			PersonalAll.getPersonalWorkInfo()
					.setExpatriateManager(
							newPersonalAll.getPersonalWorkInfo()
									.getExpatriateManager());
			PersonalAll.getPersonalWorkInfo().setWorkingAddress(
					newPersonalAll.getPersonalWorkInfo().getWorkingAddress());
			PersonalAll.getPersonalWorkInfo().setUpdateTime(new Date());
			// 赋予新值--工作信息-人事也可修改值
			PersonalAll.getPersonalWorkInfo().setPosition(
					newPersonalAll.getPersonalWorkInfo().getPosition());
			PersonalAll.getPersonalWorkInfo().setLevel(
					newPersonalAll.getPersonalWorkInfo().getLevel());
			PersonalAll.getPersonalWorkInfo().setExpatriateUnit(
					newPersonalAll.getPersonalWorkInfo().getExpatriateUnit());
			PersonalAll.getPersonalWorkInfo().setRecruitChannel(
					newPersonalAll.getPersonalWorkInfo().getRecruitChannel());
			// PersonalAll.getPersonalWorkInfo().setContractCount(newPersonalAll.getPersonalWorkInfo().getContractCount());
			// PersonalAll.getPersonalWorkInfo().setContractStartdate(newPersonalAll.getPersonalWorkInfo().getContractStartdate());
			// PersonalAll.getPersonalWorkInfo().setContractEnddate(newPersonalAll.getPersonalWorkInfo().getContractEnddate());
			// PersonalAll.getPersonalWorkInfo().setContractRenewDate(newPersonalAll.getPersonalWorkInfo().getContractRenewDate());
			// PersonalAll.getPersonalWorkInfo().setContractRenewEnddate(newPersonalAll.getPersonalWorkInfo().getContractRenewEnddate());
			PersonalAll.getPersonalWorkInfo().setLeaveType(
					newPersonalAll.getPersonalWorkInfo().getLeaveType());
			PersonalAll.getPersonalWorkInfo().setLeaveStatus(
					newPersonalAll.getPersonalWorkInfo().getLeaveStatus());
			PersonalAll.getPersonalWorkInfo().setLeaveReason(
					newPersonalAll.getPersonalWorkInfo().getLeaveReason());
			PersonalAll.getPersonalWorkInfo().setLeaveWorkingTime(
					newPersonalAll.getPersonalWorkInfo().getLeaveWorkingTime());

			// 赋予新值--工资信息-本人可修改值
			PersonalAll.getPersonalSalaryInfo().setBankCardNumber(
					newPersonalAll.getPersonalSalaryInfo().getBankCardNumber());
			PersonalAll.getPersonalSalaryInfo().setBankOpenPlace(
					newPersonalAll.getPersonalSalaryInfo().getBankOpenPlace());
			PersonalAll.getPersonalSalaryInfo().setUpdateTime(new Date());
			// 赋予新值--工资信息-人事也可修改值
			PersonalAll.getPersonalSalaryInfo().setEntryTime(
					newPersonalAll.getPersonalSalaryInfo().getEntryTime());
			PersonalAll.getPersonalSalaryInfo().setArrivalTime(
					newPersonalAll.getPersonalSalaryInfo().getArrivalTime());
			PersonalAll.getPersonalSalaryInfo().setWorkerTime(
					newPersonalAll.getPersonalSalaryInfo().getWorkerTime());
			// 工龄
			double f1 = new BigDecimal(
					(float) DateTimeUtil.differentDaysByMillisecond(
							newPersonalAll.getPersonalSalaryInfo()
									.getArrivalTime(), new Date()) / 365)
					.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			PersonalAll.getPersonalSalaryInfo().setWorkingYears(
					BigDecimal.valueOf(f1));
			PersonalAll.getPersonalSalaryInfo().setInsuranceBeginDate(
					newPersonalAll.getPersonalSalaryInfo()
							.getInsuranceBeginDate());
			PersonalAll.getPersonalSalaryInfo().setInsuranceRealDate(
					newPersonalAll.getPersonalSalaryInfo()
							.getInsuranceRealDate());
			PersonalAll.getPersonalSalaryInfo().setInsurancePlace(
					newPersonalAll.getPersonalSalaryInfo().getInsurancePlace());
			PersonalAll.getPersonalSalaryInfo()
					.setProbationPeriod(
							newPersonalAll.getPersonalSalaryInfo()
									.getProbationPeriod());
			PersonalAll.getPersonalSalaryInfo().setProbationPeriodWelfare(
					newPersonalAll.getPersonalSalaryInfo()
							.getProbationPeriodWelfare());
			PersonalAll.getPersonalSalaryInfo().setWorkerWelfare(
					newPersonalAll.getPersonalSalaryInfo().getWorkerWelfare());
			PersonalAll.getPersonalSalaryInfo().setBasePay(
					newPersonalAll.getPersonalSalaryInfo().getBasePay());
			PersonalAll.getPersonalSalaryInfo().setMeritPay(
					newPersonalAll.getPersonalSalaryInfo().getMeritPay());
			PersonalAll.getPersonalSalaryInfo().setSubsidy(
					newPersonalAll.getPersonalSalaryInfo().getSubsidy());
			PersonalAll.getPersonalSalaryInfo().setWorkerPay(
					newPersonalAll.getPersonalSalaryInfo().getWorkerPay());
			PersonalAll.getPersonalSalaryInfo()
					.setProbationaryPay(
							newPersonalAll.getPersonalSalaryInfo()
									.getProbationaryPay());
			PersonalAll.getPersonalSalaryInfo()
					.setSettlementPrice(
							newPersonalAll.getPersonalSalaryInfo()
									.getSettlementPrice());

			// 更新记录
			personalInfoDAO.update(PersonalAll.getPersonalInfo());
			personalWorkInfoDAO.update(PersonalAll.getPersonalWorkInfo());
			personalSalaryInfoDAO.update(PersonalAll.getPersonalSalaryInfo());
			result = 1;
		} else {
			logger.error("根据员工ID未查到基本信息");
			return -1;
		}

		return result;
	}

	// 新增员工基本信息
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = { Exception.class })
	public synchronized int addPersonalAllInfo(PersonalAll personalAll) {
		int result = 0;
		PersonalInfo person = personalAll.getPersonalInfo();
		PersonalWorkInfo work = personalAll.getPersonalWorkInfo();
		PersonalSalaryInfo salary = personalAll.getPersonalSalaryInfo();
		// 补全默认值
		// 生成员工编号 项目+部门+自增编号
		String unitCode = UnitConstants.seletCode(work.getExpatriateUnit());// 外派简称
		String departCode = DepartmentConstants.seletCode(work.getDepartment());// 部门简称
		int personNum = personalInfoDAO.countPersonalInfo();
		String strPersonNum = new DecimalFormat("0000").format(personNum + 1);
		String employeeNumber = unitCode + departCode + strPersonNum;
		logger.info("savePersonalAllListRecord : 员工编号：" + employeeNumber);

		person.setEmployeeNumber(employeeNumber);
		person.setIsDel(1);
		person.setCreateTime(new Date());
		// 取生日-年龄
		String strBirthDay = person.getIdentityCard().substring(6, 14);
		SimpleDateFormat sdtAge = new SimpleDateFormat("yyyyMMdd");
		java.util.Date birthDay = null;
		try {
			birthDay = sdtAge.parse(strBirthDay);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String curAge = String.valueOf(
				DateTimeUtil.yearDateDiff(birthDay, new Date())).trim();
		person.setAge(Integer.parseInt(curAge));
		// 判断性别
		String curSex = "";
		if (Integer.parseInt(person.getIdentityCard().substring(16)
				.substring(0, 1)) % 2 == 0) {
			curSex = "女";
		} else {
			curSex = "男";
		}
		person.setSex(curSex);
		// 出生年月
		person.setBirthday(birthDay);
		// 工作年限
		String curWorkingLife = String.valueOf(
				DateTimeUtil.yearDateDiff(person.getGraduationTime(),
						new Date()) + "年").trim();
		person.setWorkingLife(curWorkingLife);

		// 保存员工个人信息表,返回主键ID
		Integer personalInfoId = personalInfoDAO.save(person);
		work.setPersonalInfoId(personalInfoId);
		work.setIsDel(1);
		work.setCreateTime(new Date());
		Integer workId = personalWorkInfoDAO.save(work);
		salary.setPersonalInfoId(personalInfoId);
		salary.setIsDel(1);
		salary.setCreateTime(new Date());
		// 工龄
		double f1 = new BigDecimal(
				(float) DateTimeUtil.differentDaysByMillisecond(
						salary.getArrivalTime(), new Date()) / 365).setScale(2,
				BigDecimal.ROUND_HALF_UP).doubleValue();
		salary.setWorkingYears(BigDecimal.valueOf(f1));
		// 转正工资=基本工资+绩效工资+补贴
		BigDecimal workerPay= BigDecimal.ZERO;
		workerPay=workerPay.add(salary.getBasePay());
		workerPay=workerPay.add( salary.getMeritPay());
		workerPay=workerPay.add(salary.getSubsidy());
		salary.setWorkerPay(workerPay);
		// 试用期工资=转正工资*80%
		BigDecimal probationaryPay= BigDecimal.ZERO;
		probationaryPay= workerPay.multiply(new BigDecimal("0.8"));
		probationaryPay=probationaryPay.setScale(2, BigDecimal.ROUND_HALF_UP);
		salary.setProbationaryPay(probationaryPay);

		Integer salaryId = personalSalaryInfoDAO.save(salary);

		ContractInfo contractInfo = new ContractInfo();
		contractInfo.setPersonalInfoId(personalInfoId);
		contractInfo.setEmployeeNumber(employeeNumber);
		contractInfo.setName(person.getName());
		contractInfo.setContractNumber(employeeNumber + "01"); // 合同编号=员工编号+"01"
		contractInfo.setPosition(work.getPosition());
		contractInfo.setStartDate(work.getContractStartdate());
		contractInfo.setEndDate(work.getContractEnddate());
		contractInfo.setContractCount(1); // 导入默认为第一次；
		contractInfo.setMemo(person.getMemo());
		contractInfo.setIsDel(1);
		contractInfo.setCreateTime(new Date());
		contractInfoDAO.save(contractInfo);
		logger.info("savePersonalAll : 员工编号：" + employeeNumber
				+ person.getName());

		return result;
	}

	@Override
	public int addLeaveInfo(PersonalWorkInfo work) {
		if (personalWorkInfoDAO.update(work)) {
			return 1;
		} else {
			return 0;
		}
	}
}
