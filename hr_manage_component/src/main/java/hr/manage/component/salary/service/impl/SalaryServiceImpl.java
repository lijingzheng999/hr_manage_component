package hr.manage.component.salary.service.impl;

import hr.manage.component.checkwork.dao.CheckWorkDetailDAO;
import hr.manage.component.checkwork.model.CheckWorkDetail;
import hr.manage.component.personal.dao.PersonalInfoDAO;
import hr.manage.component.personal.dao.PersonalSalaryInfoDAO;
import hr.manage.component.personal.model.PersonalAllExport;
import hr.manage.component.personal.model.PersonalCondition;
import hr.manage.component.personal.model.PersonalSalaryInfo;
import hr.manage.component.salary.dao.InsuranceDetailDAO;
import hr.manage.component.salary.dao.ProfitDetailDAO;
import hr.manage.component.salary.dao.SalaryChangeDAO;
import hr.manage.component.salary.dao.SalaryDetailDAO;
import hr.manage.component.salary.model.InsuranceDetail;
import hr.manage.component.salary.model.InsuranceDetailCondition;
import hr.manage.component.salary.model.ProfitDetail;
import hr.manage.component.salary.model.ProfitDetailCondition;
import hr.manage.component.salary.model.SalaryChange;
import hr.manage.component.salary.model.SalaryChangeCondition;
import hr.manage.component.salary.model.SalaryDetail;
import hr.manage.component.salary.model.SalaryDetailCondition;
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
	@Autowired
	ProfitDetailDAO profitDetailDAO;

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
		// int salaryCount = salaryDetailDAO.countSalaryDetailByTerm(term);
		// if (salaryCount > 0) {
		// // 工资表已经出过，不能重复出
		// return -2;
		// }
		// 构造工资出账周期
		SimpleDateFormat sdt = new SimpleDateFormat("yyyyMM");
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
				if (personalAll.getLeaveWorkingTime() != null
						&& personalAll.getLeaveWorkingTime().before(startDate)) {
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
			if (workTime != null && workTime.before(startDate)) {
				detail.setProbationaryPay(BigDecimal.ZERO);
				detail.setBasePay(personalAll.getBasePay());
				detail.setMeritPay(personalAll.getMeritPay());
				detail.setOtherPay(BigDecimal.ZERO);
				BigDecimal subsidy = personalAll.getSubsidy();
				// 补贴为500
				if (subsidy.compareTo(new BigDecimal(500)) == 0) {
					detail.setTrafficSubsidy(new BigDecimal(100));
					detail.setComputerSubsidy(new BigDecimal(100));
					detail.setMealSubsidy(new BigDecimal(300));
				} else if (subsidy.compareTo(new BigDecimal(400)) == 0) {
					detail.setTrafficSubsidy(new BigDecimal(100));
					detail.setComputerSubsidy(new BigDecimal(0));
					detail.setMealSubsidy(new BigDecimal(300));
				} else {
					detail.setTrafficSubsidy(new BigDecimal(100));
					detail.setComputerSubsidy(new BigDecimal(0));
					detail.setMealSubsidy(new BigDecimal(0));
				}

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
					attendanceDeduction = attendanceDeduction.divide(
							checkWork.getCheckWorkDays(), 2,
							BigDecimal.ROUND_HALF_UP);
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
				if (endDate != null && endDate.getTime().before(workTime)) {
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
						attendanceDeduction = attendanceDeduction.divide(
								checkWork.getCheckWorkDays(), 2,
								BigDecimal.ROUND_HALF_UP);
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
						probationaryPay = probationaryPay.divide(
								checkWork.getCheckWorkDays(), 2,
								BigDecimal.ROUND_HALF_UP);
						// 乘以本月试用期天数
						probationaryPay = probationaryPay
								.multiply(new BigDecimal(dutyDays));

						probationaryPay = probationaryPay.setScale(2,
								BigDecimal.ROUND_HALF_UP);
						detail.setProbationaryPay(probationaryPay);

						// 转正工资
						BigDecimal workPay = personalAll.getWorkerPay();
						// 除去本月考勤天数
						workPay = workPay.divide(checkWork.getCheckWorkDays(),
								2, BigDecimal.ROUND_HALF_UP);
						// 乘以本月试用期天数
						workPay = workPay.multiply(workDays);
						workPay = workPay.setScale(2, BigDecimal.ROUND_HALF_UP);
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
						attendanceDeduction = attendanceDeduction.divide(
								checkWork.getCheckWorkDays(), 2,
								BigDecimal.ROUND_HALF_UP);
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
				detail.setTaxPay(detail.getShouldPay().subtract(
						detail.getInsuranceDeduction()));
			}
			// 应纳税所得额==IF(U-3500>0,U-3500,0) U为报税工资
			if (detail.getTaxPay().compareTo(new BigDecimal(3500)) > 0) {
				detail.setShouldTaxAmount(detail.getTaxPay().subtract(
						new BigDecimal(3500)));
			} else {
				detail.setShouldTaxAmount(BigDecimal.ZERO);
			}
			// 税率=IF(V>9000,"25%",IF(V>4500,"20%",IF(V>1500,"10%",IF(V>0,"3%",0))))
			// V为应纳税所得额
			if (detail.getShouldTaxAmount().compareTo(new BigDecimal(9000)) > 0) {
				detail.setTax(new BigDecimal("0.25"));
			} else if (detail.getShouldTaxAmount().compareTo(
					new BigDecimal(4500)) > 0) {
				detail.setTax(new BigDecimal("0.20"));
			} else if (detail.getShouldTaxAmount().compareTo(
					new BigDecimal(1500)) > 0) {
				detail.setTax(new BigDecimal("0.10"));
			} else if (detail.getShouldTaxAmount().compareTo(new BigDecimal(0)) > 0) {
				detail.setTax(new BigDecimal("0.03"));
			} else {
				detail.setTax(new BigDecimal("0.00"));
			}
			// 速算扣除数
			// =IF(W="25%",1005,IF(W="20%",555,IF(W="10%",105,IF(W="3%",0,0))))
			// W为税率
			if (detail.getTax().compareTo(new BigDecimal("0.25")) == 0) {
				detail.setDeductNumber(new BigDecimal(1005));
			} else if (detail.getTax().compareTo(new BigDecimal("0.20")) == 0) {
				detail.setDeductNumber(new BigDecimal(555));
			} else if (detail.getTax().compareTo(new BigDecimal("0.10")) == 0) {
				detail.setDeductNumber(new BigDecimal(105));
			} else if (detail.getTax().compareTo(new BigDecimal("0.03")) == 0) {
				detail.setDeductNumber(new BigDecimal(0));
			} else {
				detail.setDeductNumber(new BigDecimal(0));
			}
			// 代扣代缴所得税=ROUND((V*W-X),2)
			// V应纳税所得额 W税率 X速算扣除数
			BigDecimal incomeTax = detail.getShouldTaxAmount()
					.multiply(detail.getTax())
					.subtract(detail.getDeductNumber())
					.setScale(2, BigDecimal.ROUND_HALF_UP);
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
	public List<SalaryDetail> listSalaryDetail(SalaryDetailCondition condition) {
		return salaryDetailDAO.listSalaryDetail(condition);
	}

	@Override
	public Long countSalaryDetail(SalaryDetailCondition condition) {
		return salaryDetailDAO.countSalaryDetail(condition);
	}

	@Override
	public SalaryDetail getSalaryDetailById(Integer salaryDetailId) {
		return salaryDetailDAO.get(salaryDetailId);
	}

	@Override
	public int updateSalaryDetail(SalaryDetail salary) {
		int result = 0;
		SalaryDetail detail = salaryDetailDAO.get(salary.getId());
		if (detail == null) {
			return -2;
		}
		// 更新数据;
		detail.setProbationaryPay(salary.getProbationaryPay());
		detail.setBasePay(salary.getBasePay());
		detail.setMeritPay(salary.getMeritPay());
		detail.setOtherPay(salary.getOtherPay());
		detail.setTrafficSubsidy(salary.getTrafficSubsidy());
		detail.setComputerSubsidy(salary.getComputerSubsidy());
		detail.setMealSubsidy(salary.getMealSubsidy());
		detail.setPhoneSubsidy(salary.getPhoneSubsidy());
		detail.setAttendanceDeduction(salary.getAttendanceDeduction());
		detail.setOtherDeduction(salary.getOtherDeduction());
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
		totalShouldPay = totalShouldPay.subtract(detail.getOtherDeduction());
		detail.setShouldPay(totalShouldPay);
		detail.setEndowment(salary.getEndowment());
		detail.setMedical(salary.getMedical());
		detail.setUnemployment(salary.getUnemployment());
		detail.setAccumulationFund(salary.getAccumulationFund());
		// 个人社保及公积金扣款合计
		// 个人养老+个人医疗+个人失业+个人公积金
		BigDecimal insuranceDeduction = new BigDecimal(0);
		insuranceDeduction = insuranceDeduction.add(detail.getEndowment());
		insuranceDeduction = insuranceDeduction.add(detail.getMedical());
		insuranceDeduction = insuranceDeduction.add(detail.getUnemployment());
		insuranceDeduction = insuranceDeduction.add(detail
				.getAccumulationFund());
		detail.setInsuranceDeduction(insuranceDeduction);
		// 报税工资
		detail.setTaxPay(detail.getShouldPay().subtract(
				detail.getInsuranceDeduction()));
		// 应纳税所得额==IF(U-3500>0,U-3500,0) U为报税工资
		if (detail.getTaxPay().compareTo(new BigDecimal(3500)) > 0) {
			detail.setShouldTaxAmount(detail.getTaxPay().subtract(
					new BigDecimal(3500)));
		} else {
			detail.setShouldTaxAmount(BigDecimal.ZERO);
		}
		// 税率=IF(V>9000,"25%",IF(V>4500,"20%",IF(V>1500,"10%",IF(V>0,"3%",0))))
		// V为应纳税所得额
		if (detail.getShouldTaxAmount().compareTo(new BigDecimal(9000)) > 0) {
			detail.setTax(new BigDecimal("0.25"));
		} else if (detail.getShouldTaxAmount().compareTo(new BigDecimal(4500)) > 0) {
			detail.setTax(new BigDecimal("0.20"));
		} else if (detail.getShouldTaxAmount().compareTo(new BigDecimal(1500)) > 0) {
			detail.setTax(new BigDecimal("0.10"));
		} else if (detail.getShouldTaxAmount().compareTo(new BigDecimal(0)) > 0) {
			detail.setTax(new BigDecimal("0.03"));
		} else {
			detail.setTax(new BigDecimal("0.00"));
		}
		// 速算扣除数
		// =IF(W="25%",1005,IF(W="20%",555,IF(W="10%",105,IF(W="3%",0,0))))
		// W为税率
		if (detail.getTax().compareTo(new BigDecimal("0.25")) == 0) {
			detail.setDeductNumber(new BigDecimal(1005));
		} else if (detail.getTax().compareTo(new BigDecimal("0.20")) == 0) {
			detail.setDeductNumber(new BigDecimal(555));
		} else if (detail.getTax().compareTo(new BigDecimal("0.10")) == 0) {
			detail.setDeductNumber(new BigDecimal(105));
		} else if (detail.getTax().compareTo(new BigDecimal("0.03")) == 0) {
			detail.setDeductNumber(new BigDecimal(0));
		} else {
			detail.setDeductNumber(new BigDecimal(0));
		}
		// 代扣代缴所得税=ROUND((V*W-X),2)
		// V应纳税所得额 W税率 X速算扣除数
		BigDecimal incomeTax = detail.getShouldTaxAmount()
				.multiply(detail.getTax()).subtract(detail.getDeductNumber())
				.setScale(2, BigDecimal.ROUND_HALF_UP);
		detail.setIncomeTax(incomeTax);
		// 实发工资=U-Y U报税工资 Y代扣代缴所得税
		detail.setRealPay(detail.getTaxPay().subtract(incomeTax));
		detail.setBankPay(detail.getRealPay());
		detail.setUpdateTime(new Date());
		if(salaryDetailDAO.update(detail)){
			result=1;
		}
		return result;
	}

	@Override
	public List<InsuranceDetail> listInsuranceDetail(
			InsuranceDetailCondition condition) {
		return insuranceDetailDAO.listInsuranceDetail(condition);
	}

	@Override
	public Long countInsuranceDetail(InsuranceDetailCondition condition) {
		return insuranceDetailDAO.countInsuranceDetail(condition);
	}

	@Override
	public InsuranceDetail getInsuranceDetailById(Integer insuranceDetailId) {
		return insuranceDetailDAO.get(insuranceDetailId);
	}

	@Override
	public int updateInsuranceDetail(InsuranceDetail detail) {
		int result = 0;
		InsuranceDetail insurance = insuranceDetailDAO.get(detail.getId());
		if (insurance == null) {
			return -2;
		}
		// 更新数据;
		insurance.setAgencyPay(detail.getAgencyPay());
		insurance.setEndowmentBase(detail.getEndowmentBase());
		insurance.setEndowmentRate(detail.getEndowmentRate());
		insurance.setEndowmentRatePersonal(detail.getEndowmentRatePersonal());
		insurance.setUnemploymentBase(detail.getUnemploymentBase());
		insurance.setUnemploymentRate(detail.getUnemploymentRate());
		insurance.setUnemploymentRatePersonal(detail
				.getUnemploymentRatePersonal());
		insurance.setWorkInjuryBase(detail.getWorkInjuryBase());
		insurance.setWorkInjuryRate(detail.getWorkInjuryRate());
		insurance.setMedicalBase(detail.getMedicalBase());
		insurance.setMedicalRate(detail.getMedicalRate());
		insurance.setMedicalRatePersonal(detail.getMedicalRatePersonal());
		insurance.setBirthBase(detail.getBirthBase());
		insurance.setBirthRate(detail.getBirthRate());
		insurance.setSickBase(detail.getSickBase());
		insurance.setSickRate(detail.getSickRate());
		insurance.setSickRatePersonal(detail.getSickRatePersonal());
		insurance.setHousingBase(detail.getHousingBase());
		insurance.setHousingRate(detail.getHousingRate());
		insurance.setHousingRatePersonal(detail.getHousingRatePersonal());
		// 养老单位
		insurance.setEndowmentPay(insurance.getEndowmentBase()
				.multiply(insurance.getEndowmentRate())
				.setScale(2, BigDecimal.ROUND_HALF_UP));
		// 养老个人
		insurance.setEndowmentPayPersonal(insurance.getEndowmentBase()
				.multiply(insurance.getEndowmentRatePersonal())
				.setScale(2, BigDecimal.ROUND_HALF_UP));
		// 失业单位
		insurance.setUnemploymentPay(insurance.getUnemploymentBase()
				.multiply(insurance.getUnemploymentRate())
				.setScale(2, BigDecimal.ROUND_HALF_UP));
		// 失业个人
		insurance.setUnemploymentPayPersonal(insurance.getUnemploymentBase()
				.multiply(insurance.getUnemploymentRatePersonal())
				.setScale(2, BigDecimal.ROUND_HALF_UP));
		// 工伤单位
		insurance.setWorkInjuryPay(insurance.getWorkInjuryBase()
				.multiply(insurance.getWorkInjuryRate())
				.setScale(2, BigDecimal.ROUND_HALF_UP));
		// 医疗单位
		insurance.setMedicalPay(insurance.getMedicalBase()
				.multiply(insurance.getMedicalRate())
				.setScale(2, BigDecimal.ROUND_HALF_UP));

		// 医疗个人 北京的+3
		if (insurance.getInsurancePlace().contains("北京")) {
			insurance.setMedicalPayPersonal(insurance.getMedicalBase()
					.multiply(insurance.getMedicalRatePersonal())
					.add(new BigDecimal(3))
					.setScale(2, BigDecimal.ROUND_HALF_UP));
		} else {
			insurance.setMedicalPayPersonal(insurance.getMedicalBase()
					.multiply(insurance.getMedicalRatePersonal())
					.setScale(2, BigDecimal.ROUND_HALF_UP));

		}

		// 生育单位
		insurance.setBirthPay(insurance.getBirthBase()
				.multiply(insurance.getBirthRate())
				.setScale(2, BigDecimal.ROUND_HALF_UP));
		// 大病/残保单位
		insurance.setSickPay(insurance.getSickBase()
				.multiply(insurance.getSickRate())
				.setScale(2, BigDecimal.ROUND_HALF_UP));
		// 大病个人
		insurance.setSickPayPersonal(insurance.getSickBase()
				.multiply(insurance.getSickRatePersonal())
				.setScale(2, BigDecimal.ROUND_HALF_UP));
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
				.multiply(insurance.getHousingRate())
				.setScale(0, BigDecimal.ROUND_HALF_UP));
		// 公积金个人
		insurance.setHousingPayPersonal(insurance.getHousingBase()
				.multiply(insurance.getHousingRatePersonal())
				.setScale(0, BigDecimal.ROUND_HALF_UP));
		// 公积金小计
		insurance.setHousingPayTotal(insurance.getHousingPay().add(
				insurance.getHousingPayPersonal()));
		// 单位缴费合计=社保单位合计+公积金单位
		insurance
				.setInsurancePay(socialSecurity.add(insurance.getHousingPay()));
		// 个人缴费合计=社保个人合计+公积金个人
		insurance.setInsurancePayPersonal(socialSecurityPersonal.add(insurance
				.getHousingPayPersonal()));
		// 总合计=单位缴费合计+个人缴费合计+代理费
		BigDecimal insurancePayTotal = new BigDecimal(0);
		insurancePayTotal = insurancePayTotal.add(insurance.getInsurancePay());
		insurancePayTotal = insurancePayTotal.add(insurance
				.getInsurancePayPersonal());
		insurancePayTotal = insurancePayTotal.add(insurance.getAgencyPay());
		insurance.setInsurancePayTotal(insurancePayTotal);
		if(insuranceDetailDAO.update(insurance)){
			result=1;
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
					.multiply(insurance.getEndowmentRate())
					.setScale(2, BigDecimal.ROUND_HALF_UP));
			// 养老个人
			insurance.setEndowmentPayPersonal(insurance.getEndowmentBase()
					.multiply(insurance.getEndowmentRatePersonal())
					.setScale(2, BigDecimal.ROUND_HALF_UP));
			// 失业单位
			insurance.setUnemploymentPay(insurance.getUnemploymentBase()
					.multiply(insurance.getUnemploymentRate())
					.setScale(2, BigDecimal.ROUND_HALF_UP));
			// 失业个人
			insurance.setUnemploymentPayPersonal(insurance
					.getUnemploymentBase()
					.multiply(insurance.getUnemploymentRatePersonal())
					.setScale(2, BigDecimal.ROUND_HALF_UP));
			// 工伤单位
			insurance.setWorkInjuryPay(insurance.getWorkInjuryBase()
					.multiply(insurance.getWorkInjuryRate())
					.setScale(2, BigDecimal.ROUND_HALF_UP));
			// 医疗单位
			insurance.setMedicalPay(insurance.getMedicalBase()
					.multiply(insurance.getMedicalRate())
					.setScale(2, BigDecimal.ROUND_HALF_UP));

			// 医疗个人 北京的+3
			if (insurance.getInsurancePlace().contains("北京")) {
				insurance.setMedicalPayPersonal(insurance.getMedicalBase()
						.multiply(insurance.getMedicalRatePersonal())
						.add(new BigDecimal(3))
						.setScale(2, BigDecimal.ROUND_HALF_UP));
			} else {
				insurance.setMedicalPayPersonal(insurance.getMedicalBase()
						.multiply(insurance.getMedicalRatePersonal())
						.setScale(2, BigDecimal.ROUND_HALF_UP));

			}

			// 生育单位
			insurance.setBirthPay(insurance.getBirthBase()
					.multiply(insurance.getBirthRate())
					.setScale(2, BigDecimal.ROUND_HALF_UP));
			// 大病/残保单位
			insurance.setSickPay(insurance.getSickBase()
					.multiply(insurance.getSickRate())
					.setScale(2, BigDecimal.ROUND_HALF_UP));
			// 大病个人
			insurance.setSickPayPersonal(insurance.getSickBase()
					.multiply(insurance.getSickRatePersonal())
					.setScale(2, BigDecimal.ROUND_HALF_UP));
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
					.multiply(insurance.getHousingRate())
					.setScale(0, BigDecimal.ROUND_HALF_UP));
			// 公积金个人
			insurance.setHousingPayPersonal(insurance.getHousingBase()
					.multiply(insurance.getHousingRatePersonal())
					.setScale(0, BigDecimal.ROUND_HALF_UP));
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

	@Override
	public List<ProfitDetail> listProfitDetail(ProfitDetailCondition condition) {
		return profitDetailDAO.listProfitDetail(condition);
	}

	@Override
	public Long countProfitDetail(ProfitDetailCondition condition) {
		return profitDetailDAO.countProfitDetail(condition);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = { Exception.class })
	public int createProfitDetailByTerm(String term) {
		int result = 0;
		int profitCount = profitDetailDAO.countProfitDetailByTerm(term);
		if (profitCount > 0) {
			// 利润表已经出过，手动删除
			profitDetailDAO.deleteProfitDetailByTerm(term);
		}
		// 构造利润表
		SimpleDateFormat sdt = new SimpleDateFormat("yyyyMM");
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
		// 利润表最终明细
		List<ProfitDetail> profitDetails = new ArrayList<ProfitDetail>();
		PersonalCondition condition = new PersonalCondition();
		condition.setLeaveStatus(1);
		// 在职人员
		List<PersonalAllExport> personalAllExports = personalInfoDAO
				.listPersonalAllExport(condition);
		// 遍历所有在职人员,构造利润表
		for (PersonalAllExport personalAll : personalAllExports) {
			// 社保表数据
			InsuranceDetail insurance = insuranceDetailDAO
					.getInsuranceDetailByName(personalAll.getName(), term);
			// 全通物联网考勤表数据
			CheckWorkDetail checkWork = checkWorkDetailDAO
					.getCheckWorkDetailByName(personalAll.getName(), term);
			if (checkWork == null) {
				// 全通没考勤取百度考勤
			}
			ProfitDetail detail = new ProfitDetail();
			// 构造信息
			detail.setTerm(term);
			detail.setStartDate(startDate);
			detail.setEndDate(endDate.getTime());
			detail.setPersonalInfoId(personalAll.getId());
			detail.setName(personalAll.getName());
			detail.setExpatriateUnit(personalAll.getExpatriateUnit());
			detail.setWorkingPlace(personalAll.getWorkingPlace());
			detail.setDepartment(personalAll.getDepartment());
			detail.setPosition(personalAll.getPosition());
			detail.setLevel(personalAll.getLevel());
			detail.setProbationaryPay(personalAll.getProbationaryPay());
			detail.setBasePay(personalAll.getBasePay());
			detail.setMeritPay(personalAll.getMeritPay());
			detail.setOtherPay(BigDecimal.ZERO);
			BigDecimal subsidy = personalAll.getSubsidy();
			// 补贴为500
			if (subsidy.compareTo(new BigDecimal(500)) == 0) {
				detail.setTrafficSubsidy(new BigDecimal(100));
				detail.setComputerSubsidy(new BigDecimal(100));
				detail.setMealSubsidy(new BigDecimal(300));
			} else if (subsidy.compareTo(new BigDecimal(400)) == 0) {
				detail.setTrafficSubsidy(new BigDecimal(100));
				detail.setComputerSubsidy(new BigDecimal(0));
				detail.setMealSubsidy(new BigDecimal(300));
			} else {
				detail.setTrafficSubsidy(new BigDecimal(100));
				detail.setComputerSubsidy(new BigDecimal(0));
				detail.setMealSubsidy(new BigDecimal(0));
			}
			// 试用期福利probationPeriodWelfare
			String probationWelfare = personalAll.getProbationPeriodWelfare();
			if (!probationWelfare.equals("无") && !probationWelfare.equals("")) {
				// 试用期有福利取社保表
				if (insurance != null) {
					detail.setProbationaryInsurance(insurance.getInsurancePay());
				} else {
					detail.setProbationaryInsurance(BigDecimal.ZERO);
				}
			} else {
				// 试用期没有福利
				detail.setProbationaryInsurance(BigDecimal.ZERO);
			}
			detail.setWorkerPay(personalAll.getWorkerPay());
			if (insurance != null) {
				detail.setSocialSecurity(insurance.getSocialSecurity());
				detail.setHousingPay(insurance.getHousingPay());
			} else {
				detail.setSocialSecurity(BigDecimal.ZERO);
				detail.setHousingPay(BigDecimal.ZERO);
			}
			detail.setSettlementPrice(personalAll.getSettlementPrice());
			// 结算天数从考勤表取
			if (checkWork != null) {
				detail.setSettlementDays(checkWork.getCheckWorkDays());
			} else {
				detail.setSettlementDays(new BigDecimal(22));
			}
			// 日单价=结算价/结算天数
			detail.setSettlementDayPrice(detail.getSettlementPrice().divide(
					detail.getSettlementDays(), 2, BigDecimal.ROUND_HALF_UP));
			detail.setProbationaryUnionPay(BigDecimal.ZERO);
			// 试用期残疾人就业保障金=试用期薪资*1.7%
			detail.setProbationaryDisabledPay(detail.getProbationaryPay()
					.multiply(new BigDecimal("0.017"))
					.setScale(2, BigDecimal.ROUND_HALF_UP));
			// 试用期增值税及附加税=ROUND(全通结算价/1.06*0.06*(1+12%),2)
			BigDecimal taxPay = new BigDecimal(0);
			taxPay = taxPay.add(personalAll.getSettlementPrice());
			taxPay = taxPay.divide(new BigDecimal("1.06"), 4,
					BigDecimal.ROUND_HALF_UP);
			taxPay = taxPay.multiply(new BigDecimal("0.06"));
			taxPay = taxPay.multiply(new BigDecimal("1.12")).setScale(2,
					BigDecimal.ROUND_HALF_UP);
			detail.setProbationaryTaxPay(taxPay);
			detail.setUnionPay(BigDecimal.ZERO);
			// 残疾人就业保障金=转正薪资*1.7%
			detail.setDisabledPay(detail.getWorkerPay()
					.multiply(new BigDecimal("0.017"))
					.setScale(2, BigDecimal.ROUND_HALF_UP));
			// 增值税及附加税=ROUND(全通结算价/1.06*0.06*(1+12%),2)
			detail.setTaxPay(taxPay);
			// 试用期利润 =R3-G3-N3-U3-V3-W3
			// 全通结算价-试用期薪资-试用期社保-试用期工会经费-试用期残疾人就业保障金-试用期增值税及附加税
			BigDecimal pProfit = new BigDecimal(0);
			pProfit = pProfit.add(detail.getSettlementPrice());
			pProfit = pProfit.subtract(detail.getProbationaryPay());
			pProfit = pProfit.subtract(detail.getProbationaryInsurance());
			pProfit = pProfit.subtract(detail.getProbationaryUnionPay());
			pProfit = pProfit.subtract(detail.getProbationaryDisabledPay());
			pProfit = pProfit.subtract(detail.getProbationaryTaxPay());
			detail.setProbationaryProfit(pProfit);
			// 试用期利润率 =试用期利润/全通结算价
			BigDecimal pProfitRate = new BigDecimal(0);
			pProfitRate = pProfit.divide(detail.getSettlementPrice(), 4,
					BigDecimal.ROUND_HALF_UP);
			detail.setProbationaryProfitRate(pProfitRate);
			// 转正后利润 =R3-O3-P3-Q3-X3-Y3-Z3
			// 全通结算价-转正后薪资-转正后社保-公积金-工会经费-残疾人就业保障金-增值税及附加税
			BigDecimal profit = new BigDecimal(0);
			profit = profit.add(detail.getSettlementPrice());
			profit = profit.subtract(detail.getWorkerPay());
			profit = profit.subtract(detail.getSocialSecurity());
			profit = profit.subtract(detail.getHousingPay());
			profit = profit.subtract(detail.getUnionPay());
			profit = profit.subtract(detail.getDisabledPay());
			profit = profit.subtract(detail.getTaxPay());
			detail.setProfit(profit);
			// 转正后利润率 =转正后利润/全通结算价
			BigDecimal profitRate = new BigDecimal(0);
			profitRate = profit.divide(detail.getSettlementPrice(), 4,
					BigDecimal.ROUND_HALF_UP);
			detail.setProfitRate(profitRate);
			detail.setIsDel(1);
			detail.setCreateTime(new Date());
			profitDetails.add(detail);
		}

		if (profitDetailDAO.save(profitDetails)) {
			result = 1;
		} else {
			result = -1;
		}
		return result;

	}
}
