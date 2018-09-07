package hr.manage.component.salary.service.impl;

import hr.manage.component.checkwork.dao.CheckWorkDetailDAO;
import hr.manage.component.checkwork.model.CheckWorkDetail;
import hr.manage.component.personal.dao.PersonalInfoDAO;
import hr.manage.component.personal.dao.PersonalSalaryInfoDAO;
import hr.manage.component.personal.model.PersonalAllExport;
import hr.manage.component.personal.model.PersonalCondition;
import hr.manage.component.personal.model.PersonalSalaryInfo;
import hr.manage.component.salary.dao.InsuranceDetailDAO;
import hr.manage.component.salary.dao.SalaryChangeDAO;
import hr.manage.component.salary.dao.SalaryDetailDAO;
import hr.manage.component.salary.model.InsuranceDetail;
import hr.manage.component.salary.model.SalaryChange;
import hr.manage.component.salary.model.SalaryChangeCondition;
import hr.manage.component.salary.model.SalaryDetail;
import hr.manage.component.salary.service.SalaryService;
import hr.manage.component.util.DateTimeUtil;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class SalaryServiceImpl implements SalaryService {

	private final Log logger = LogFactory.getLog(SalaryServiceImpl.class);
	@Autowired
	SalaryChangeDAO salaryChangeDAO;
	@Autowired
	PersonalSalaryInfoDAO personalSalaryInfoDAO;
	@Autowired
	SalaryDetailDAO salaryDetailDAO;
	@Autowired
	PersonalInfoDAO personalInfoDAO;
	@Autowired
	CheckWorkDetailDAO checkWorkDetailDAO;
	@Autowired
	InsuranceDetailDAO insuranceDetailDAO;

	@Override
	public List<SalaryChange> listSalaryChange(SalaryChangeCondition condition) {
		return salaryChangeDAO.listSalaryChange(condition);
	}

	@Override
	public Long countSalaryChange(SalaryChangeCondition condition) {
		return salaryChangeDAO.countSalaryChange(condition);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = { Exception.class })
	public int addSalaryChange(SalaryChange salaryChange) {
		int result = 0;
		// 保存工资调整信息
		result = salaryChangeDAO.save(salaryChange);
		if (result < 1) {
			result = -1;
		}
		// 同步员工基本信息
		PersonalSalaryInfo salary = personalSalaryInfoDAO
				.getPersonalSalaryInfoById(salaryChange.getPersonalInfoId());
		if (salary != null) {
			// 调级--修改基本工资
			BigDecimal oldBasePay = salary.getBasePay();
			BigDecimal oldMeritPay = salary.getMeritPay();
			if (salaryChange.getType() == 1) {
				// 新基本工资=原基本工资+调薪幅度
				BigDecimal basePay = BigDecimal.ZERO;
				basePay = basePay.add(oldBasePay);
				basePay = basePay.add(salaryChange.getChangeRange());
				salary.setBasePay(basePay);
				// 转正工资=原转正工资+调薪幅度
				BigDecimal workerPay = BigDecimal.ZERO;
				workerPay = workerPay.add(salaryChange.getChangeRange());
				workerPay = workerPay.add(salary.getWorkerPay());
				salary.setWorkerPay(workerPay);
			} else { // 调薪或其他--修改绩效工资
						// 新绩效工资=原绩效工资+调薪幅度
				BigDecimal meritPay = BigDecimal.ZERO;
				meritPay = meritPay.add(oldMeritPay);
				meritPay = meritPay.add(salaryChange.getChangeRange());
				salary.setMeritPay(meritPay);
				// 转正工资=原转正工资+调薪幅度
				BigDecimal workerPay = BigDecimal.ZERO;
				workerPay = workerPay.add(salaryChange.getChangeRange());
				workerPay = workerPay.add(salary.getWorkerPay());
				salary.setWorkerPay(workerPay);
			}
			salary.setUpdateTime(new Date());
			boolean curResult = personalSalaryInfoDAO.update(salary);
			if (curResult) {
				result = 1;
			} else {
				result = -3;
			}
		} else {
			result = -2;
		}
		return result;
	}

	@Override
	public int countSalaryDetailByTerm(String term) {
		return salaryDetailDAO.countSalaryDetailByTerm(term);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = { Exception.class })
	public int createSalaryDetailByTerm(String term) {
		int result = 0;
		int salaryCount = salaryDetailDAO.countSalaryDetailByTerm(term);
		if (salaryCount > 0) {
			// 工资表已经出过，不能重复出
			return -2;
		}
		// 构造工资出账周期
		SimpleDateFormat sdt = new SimpleDateFormat("yyyy-MM");
		Date startDate = null;
		try {
			startDate = sdt.parse(String.valueOf(term).trim());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar endDate = Calendar.getInstance();
		endDate.setTime(startDate);
		endDate.add(Calendar.MONTH, 1);
		// 工资表最终明细
		List<SalaryDetail> salaryDetails = new ArrayList<SalaryDetail>();
		PersonalCondition condition = new PersonalCondition();
		// 所有人员信息；包括在职和已离职；
		List<PersonalAllExport> personalAllExports = personalInfoDAO
				.listPersonalAllExport(condition);
		// 遍历所有人员,构造工资表
		for (PersonalAllExport personalAll : personalAllExports) {
			// 过滤已离职人员
			int leaveStatus = personalAll.getLeaveStatus();
			if (leaveStatus == 0) {
				// 如果离职时间在本月之前,过滤掉,不生成工资表
				if (personalAll.getLeaveWorkingTime().before(startDate)) {
					continue;
				}
			}
			SalaryDetail detail = new SalaryDetail();
			detail.setTerm(term);
			detail.setStartDate(startDate);
			detail.setEndDate(endDate.getTime());
			detail.setPersonalInfoId(personalAll.getId());
			detail.setName(personalAll.getName());
			detail.setEntryTime(personalAll.getEntryTime());
			detail.setExpatriateUnit(personalAll.getExpatriateUnit());
			Date workTime = personalAll.getWorkerTime();
			// 判断是否已转正;workTime<startDate 本月已转正
			if (workTime.before(startDate)) {
				detail.setProbationaryPay(BigDecimal.ZERO);
				detail.setBasePay(personalAll.getBasePay());
				detail.setMeritPay(personalAll.getMeritPay());
				detail.setOtherPay(BigDecimal.ZERO);
				detail.setTrafficSubsidy(new BigDecimal(100));
				detail.setComputerSubsidy(new BigDecimal(100));
				detail.setMealSubsidy(new BigDecimal(300));
				detail.setPhoneSubsidy(BigDecimal.ZERO);
				// 计算考勤扣款//////////////////////////////////
				BigDecimal attendanceDeduction = new BigDecimal(0);
				// 获取考勤信息
				CheckWorkDetail checkWork = checkWorkDetailDAO
						.getCheckWorkDetailByName(personalAll.getName(), term);
				// 全通或物联网考勤有数据
				if (checkWork != null) {
					// 所有费用=试用期工资+基本工资+绩效工资+交通补助+电脑补助+餐补
					attendanceDeduction = attendanceDeduction.add(detail
							.getProbationaryPay());
					attendanceDeduction = attendanceDeduction.add(detail
							.getBasePay());
					attendanceDeduction = attendanceDeduction.add(detail
							.getMeritPay());
					attendanceDeduction = attendanceDeduction.add(detail
							.getTrafficSubsidy());
					attendanceDeduction = attendanceDeduction.add(detail
							.getComputerSubsidy());
					attendanceDeduction = attendanceDeduction.add(detail
							.getMealSubsidy());
					// 所有费用/考勤天数
					attendanceDeduction = attendanceDeduction.divide(checkWork
							.getCheckWorkDays());
					// 乘以缺勤天数
					attendanceDeduction = attendanceDeduction
							.multiply(checkWork.getSettlementDays());
				} else {
					// 百度
				}
				detail.setAttendanceDeduction(attendanceDeduction);
				detail.setOtherDeduction(BigDecimal.ZERO);

			} else {
				// 判断是否在本月转正;仍有部分试用期或全是试用期
				// 本月未转正 workTime>endDate
				if (endDate.before(workTime)) {
					// 全是试用期
					detail.setProbationaryPay(personalAll.getProbationaryPay());
					detail.setBasePay(BigDecimal.ZERO);
					detail.setMeritPay(BigDecimal.ZERO);
					detail.setOtherPay(BigDecimal.ZERO);
					detail.setTrafficSubsidy(BigDecimal.ZERO);
					detail.setComputerSubsidy(BigDecimal.ZERO);
					detail.setMealSubsidy(BigDecimal.ZERO);
					detail.setPhoneSubsidy(BigDecimal.ZERO);
					// 计算考勤扣款//////////////////////////////////
					BigDecimal attendanceDeduction = new BigDecimal(0);
					// 获取考勤信息
					CheckWorkDetail checkWork = checkWorkDetailDAO
							.getCheckWorkDetailByName(personalAll.getName(),
									term);
					// 全通或物联网考勤有数据
					if (checkWork != null) {
						// 所有费用=试用期工资+基本工资+绩效工资+交通补助+电脑补助+餐补
						attendanceDeduction = attendanceDeduction.add(detail
								.getProbationaryPay());
						attendanceDeduction = attendanceDeduction.add(detail
								.getBasePay());
						attendanceDeduction = attendanceDeduction.add(detail
								.getMeritPay());
						attendanceDeduction = attendanceDeduction.add(detail
								.getTrafficSubsidy());
						attendanceDeduction = attendanceDeduction.add(detail
								.getComputerSubsidy());
						attendanceDeduction = attendanceDeduction.add(detail
								.getMealSubsidy());
						// 所有费用/考勤天数
						attendanceDeduction = attendanceDeduction
								.divide(checkWork.getCheckWorkDays());
						// 乘以缺勤天数
						attendanceDeduction = attendanceDeduction
								.multiply(checkWork.getSettlementDays());
					} else {
						// 百度
					}
					detail.setAttendanceDeduction(attendanceDeduction);
					detail.setOtherDeduction(BigDecimal.ZERO);

				} else {
					// 本月转正startDate<workTime<endDate 仍有部分试用期
					// 计算考勤扣款//////////////////////////////////
					BigDecimal attendanceDeduction = new BigDecimal(0);
					// 获取考勤信息
					CheckWorkDetail checkWork = checkWorkDetailDAO
							.getCheckWorkDetailByName(personalAll.getName(),
									term);
					// 全通或物联网考勤有数据
					if (checkWork != null) {
						// 计算试用期天数
						int dutyDays = DateTimeUtil.getDutyDays(startDate,
								workTime);
						// 计算转正天数
						BigDecimal workDays = checkWork.getCheckWorkDays()
								.subtract(new BigDecimal(dutyDays));
						// 获取试用期工资
						BigDecimal probationaryPay = personalAll
								.getProbationaryPay();

						// 除去本月考勤天数
						probationaryPay = probationaryPay.divide(checkWork
								.getCheckWorkDays());
						// 乘以本月试用期天数
						probationaryPay = probationaryPay
								.multiply(new BigDecimal(dutyDays));

						probationaryPay = probationaryPay.setScale(2);
						detail.setProbationaryPay(probationaryPay);

						// 转正工资
						BigDecimal workPay = personalAll.getWorkerPay();
						// 除去本月考勤天数
						workPay = workPay.divide(checkWork.getCheckWorkDays());
						// 乘以本月试用期天数
						workPay = workPay.multiply(workDays);
						workPay = workPay.setScale(2);
						detail.setBasePay(workPay);
						detail.setMeritPay(BigDecimal.ZERO);
						detail.setOtherPay(BigDecimal.ZERO);
						detail.setTrafficSubsidy(BigDecimal.ZERO);
						detail.setComputerSubsidy(BigDecimal.ZERO);
						detail.setMealSubsidy(BigDecimal.ZERO);
						detail.setPhoneSubsidy(BigDecimal.ZERO);
						// 所有费用=试用期工资+基本工资+绩效工资+交通补助+电脑补助+餐补
						attendanceDeduction = attendanceDeduction.add(detail
								.getProbationaryPay());
						attendanceDeduction = attendanceDeduction.add(detail
								.getBasePay());
						attendanceDeduction = attendanceDeduction.add(detail
								.getMeritPay());
						attendanceDeduction = attendanceDeduction.add(detail
								.getTrafficSubsidy());
						attendanceDeduction = attendanceDeduction.add(detail
								.getComputerSubsidy());
						attendanceDeduction = attendanceDeduction.add(detail
								.getMealSubsidy());
						// 所有费用/考勤天数
						attendanceDeduction = attendanceDeduction
								.divide(checkWork.getCheckWorkDays());
						// 乘以缺勤天数
						attendanceDeduction = attendanceDeduction
								.multiply(checkWork.getSettlementDays());
					} else {
						// 百度
					}
					detail.setAttendanceDeduction(attendanceDeduction);
					detail.setOtherDeduction(BigDecimal.ZERO);

				}
			}

			// 本月应发工资
			// 试用期工资+基本工资+绩效工资+其他工资+交通补助+电脑补助+餐补+话补-考勤扣款-其它扣款
			BigDecimal totalShouldPay = new BigDecimal(0);
			totalShouldPay = totalShouldPay.add(detail.getProbationaryPay());
			totalShouldPay = totalShouldPay.add(detail.getBasePay());
			totalShouldPay = totalShouldPay.add(detail.getMeritPay());
			totalShouldPay = totalShouldPay.add(detail.getOtherPay());
			totalShouldPay = totalShouldPay.add(detail.getTrafficSubsidy());
			totalShouldPay = totalShouldPay.add(detail.getComputerSubsidy());
			totalShouldPay = totalShouldPay.add(detail.getMealSubsidy());
			totalShouldPay = totalShouldPay.add(detail.getPhoneSubsidy());
			totalShouldPay = totalShouldPay.subtract(detail
					.getAttendanceDeduction());
			totalShouldPay = totalShouldPay
					.subtract(detail.getOtherDeduction());
			detail.setShouldPay(totalShouldPay);
			InsuranceDetail insurance = insuranceDetailDAO
					.getInsuranceDetailByName(personalAll.getName(), term);
			if (insurance != null) {
				detail.setEndowment(insurance.getEndowmentPayPersonal());
				detail.setMedical(insurance.getMedicalPayPersonal());
				detail.setUnemployment(insurance.getUnemploymentPayPersonal());
				detail.setAccumulationFund(insurance.getHousingPayPersonal());
				detail.setInsuranceDeduction(insurance
						.getInsurancePayPersonal());
			}
			// 报税工资=本月应发工资-个人社保及公积金扣款合计
			if (detail.getInsuranceDeduction() == null) {
				detail.setTaxPay(detail.getShouldPay());
			} else {
				detail.setTaxPay(detail.getShouldPay().multiply(
						detail.getInsuranceDeduction()));
			}
			// 应纳税所得额==IF(U-3500>0,U-3500,0) U为报税工资
			if (detail.getTaxPay().compareTo(new BigDecimal(3500)) > 0) {
				detail.setShouldTaxAmount(detail.getTaxPay().multiply(
						new BigDecimal(3500)));
			} else {
				detail.setShouldTaxAmount(BigDecimal.ZERO);
			}
			// 税率=IF(V>9000,"25%",IF(V>4500,"20%",IF(V>1500,"10%",IF(V>0,"3%",0))))
			// V为应纳税所得额
			if (detail.getShouldTaxAmount().compareTo(new BigDecimal(9000)) > 0) {
				detail.setTax(new BigDecimal(0.25));
			} else if (detail.getShouldTaxAmount().compareTo(
					new BigDecimal(4500)) > 0) {
				detail.setTax(new BigDecimal(0.20));
			} else if (detail.getShouldTaxAmount().compareTo(
					new BigDecimal(1500)) > 0) {
				detail.setTax(new BigDecimal(0.10));
			} else if (detail.getShouldTaxAmount().compareTo(new BigDecimal(0)) > 0) {
				detail.setTax(new BigDecimal(0.03));
			} else {
				detail.setTax(new BigDecimal(0.00));
			}
			// 速算扣除数
			// =IF(W="25%",1005,IF(W="20%",555,IF(W="10%",105,IF(W="3%",0,0))))
			// W为税率
			if (detail.getTax().compareTo(new BigDecimal(0.25)) == 0) {
				detail.setDeductNumber(new BigDecimal(1005));
			} else if (detail.getTax().compareTo(new BigDecimal(0.20)) == 0) {
				detail.setDeductNumber(new BigDecimal(555));
			} else if (detail.getTax().compareTo(new BigDecimal(0.10)) == 0) {
				detail.setDeductNumber(new BigDecimal(105));
			} else if (detail.getTax().compareTo(new BigDecimal(0.03)) == 0) {
				detail.setDeductNumber(new BigDecimal(0));
			} else {
				detail.setDeductNumber(new BigDecimal(0));
			}
			// 代扣代缴所得税=ROUND((V*W-X),2)
			// V应纳税所得额 W税率 X速算扣除数
			BigDecimal incomeTax = detail.getShouldTaxAmount()
					.multiply(detail.getTax())
					.subtract(detail.getDeductNumber()).setScale(2);
			detail.setIncomeTax(incomeTax);
			// 实发工资=U-Y U报税工资 Y代扣代缴所得税
			detail.setRealPay(detail.getTaxPay().subtract(incomeTax));
			detail.setBankPay(detail.getRealPay());
			detail.setIsDel(1);
			detail.setCreateTime(new Date());
			salaryDetails.add(detail);
		}
		if (salaryDetailDAO.save(salaryDetails)) {
			result = 1;
		} else {
			result = -1;
		}
		return result;
	}

	@Override
	public int countInsuranceDetailByTerm(String term) {
		return insuranceDetailDAO.countInsuranceDetailByTerm(term);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = { Exception.class })
	public int saveInsuranceDetailListRecord(
			List<InsuranceDetail> insuranceDetailList) {
		for (InsuranceDetail insurance : insuranceDetailList) {
			logger.info("saveInsuranceDetailListRecord : 员工姓名："
					+ insurance.getName() + " ---社保月份：" + insurance.getTerm());
			// 判断是否导入过本月社保
			InsuranceDetail curDetail = insuranceDetailDAO
					.getInsuranceDetailByName(insurance.getName(),
							insurance.getTerm());
			if (curDetail != null && curDetail.getUpdateTime() != null) {
				continue;
			}
			if (curDetail != null) {
				// 之前导入过,进行恢复处理
				// 删除导入的当月社保数据
				insuranceDetailDAO.deleteInsuranceDetailByName(
						insurance.getName(), insurance.getTerm());
			}
			// 养老单位
			insurance.setEndowmentPay(insurance.getEndowmentBase()
					.multiply(insurance.getEndowmentRate()).setScale(2));
			// 养老个人
			insurance
					.setEndowmentPayPersonal(insurance.getEndowmentBase()
							.multiply(insurance.getEndowmentRatePersonal())
							.setScale(2));
			// 失业单位
			insurance.setUnemploymentPay(insurance.getUnemploymentBase()
					.multiply(insurance.getUnemploymentRate()).setScale(2));
			// 失业个人
			insurance.setUnemploymentPayPersonal(insurance
					.getUnemploymentBase()
					.multiply(insurance.getUnemploymentRatePersonal())
					.setScale(2));
			// 工伤单位
			insurance.setWorkInjuryPay(insurance.getWorkInjuryBase()
					.multiply(insurance.getWorkInjuryRate()).setScale(2));
			// 医疗单位 北京的+3
			if(insurance.getInsurancePlace().contains("北京")){
				insurance.setMedicalPay(insurance.getMedicalBase()
						.multiply(insurance.getMedicalRate()).add(new BigDecimal(3)).setScale(2));
			}
			else{
				insurance.setMedicalPay(insurance.getMedicalBase()
						.multiply(insurance.getMedicalRate()).setScale(2));	
			}
			
			// 医疗个人
			insurance.setMedicalPayPersonal(insurance.getMedicalBase()
					.multiply(insurance.getMedicalRatePersonal()).setScale(2));
			// 生育单位
			insurance.setBirthPay(insurance.getBirthBase()
					.multiply(insurance.getBirthRate()).setScale(2));
			// 大病/残保单位
			insurance.setSickPay(insurance.getSickBase()
					.multiply(insurance.getSickRate()).setScale(2));
			// 大病个人
			insurance.setSickPayPersonal(insurance.getSickBase()
					.multiply(insurance.getSickRatePersonal()).setScale(2));
			// 社保单位合计
			BigDecimal socialSecurity = new BigDecimal(0);
			socialSecurity = socialSecurity.add(insurance.getEndowmentPay());
			socialSecurity = socialSecurity.add(insurance.getUnemploymentPay());
			socialSecurity = socialSecurity.add(insurance.getWorkInjuryPay());
			socialSecurity = socialSecurity.add(insurance.getMedicalPay());
			socialSecurity = socialSecurity.add(insurance.getBirthPay());
			socialSecurity = socialSecurity.add(insurance.getSickPay());
			insurance.setSocialSecurity(socialSecurity);
			// 社保个人合计
			BigDecimal socialSecurityPersonal = new BigDecimal(0);
			socialSecurityPersonal = socialSecurityPersonal.add(insurance
					.getEndowmentPayPersonal());
			socialSecurityPersonal = socialSecurityPersonal.add(insurance
					.getUnemploymentPayPersonal());
			socialSecurityPersonal = socialSecurityPersonal.add(insurance
					.getMedicalPayPersonal());
			socialSecurityPersonal = socialSecurityPersonal.add(insurance
					.getSickPayPersonal());
			insurance.setSocialSecurityPersonal(socialSecurityPersonal);
			// 社保小计 =社保单位合计+社保个人合计
			insurance.setSocialSecurityTotal(socialSecurity
					.add(socialSecurityPersonal));
			// 公积金单位
			insurance.setHousingPay(insurance.getHousingBase()
					.multiply(insurance.getHousingRate()).setScale(0));
			// 公积金个人
			insurance.setHousingPayPersonal(insurance.getHousingBase()
					.multiply(insurance.getHousingRatePersonal()).setScale(0));
			// 公积金小计
			insurance.setHousingPayTotal(insurance.getHousingPay().add(
					insurance.getHousingPayPersonal()));
			// 单位缴费合计=社保单位合计+公积金单位
			insurance.setInsurancePay(socialSecurity.add(insurance
					.getHousingPay()));
			// 个人缴费合计=社保个人合计+公积金个人
			insurance.setInsurancePayPersonal(socialSecurityPersonal
					.add(insurance.getHousingPayPersonal()));
			// 总合计=单位缴费合计+个人缴费合计+代理费
			BigDecimal insurancePayTotal = new BigDecimal(0);
			insurancePayTotal = insurancePayTotal.add(insurance
					.getInsurancePay());
			insurancePayTotal = insurancePayTotal.add(insurance
					.getInsurancePayPersonal());
			insurancePayTotal = insurancePayTotal.add(insurance.getAgencyPay());
			insurance.setInsurancePayTotal(insurancePayTotal);
			insuranceDetailDAO.save(insurance);

		}
		return 1;
	}
}
