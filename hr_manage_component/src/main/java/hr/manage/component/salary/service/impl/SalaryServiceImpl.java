package hr.manage.component.salary.service.impl;

import hr.manage.component.checkwork.dao.CheckWorkDetailDAO;
import hr.manage.component.checkwork.model.CheckWorkDetail;
import hr.manage.component.personal.dao.PersonalInfoDAO;
import hr.manage.component.personal.dao.PersonalSalaryInfoDAO;
import hr.manage.component.personal.model.PersonalAllExport;
import hr.manage.component.personal.model.PersonalCondition;
import hr.manage.component.personal.model.PersonalSalaryInfo;
import hr.manage.component.salary.dao.SalaryChangeDAO;
import hr.manage.component.salary.dao.SalaryDetailDAO;
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
	
	@Override
	public List<SalaryChange> listSalaryChange(SalaryChangeCondition condition){
		return salaryChangeDAO.listSalaryChange(condition);
	}
	
	@Override
	public Long countSalaryChange(SalaryChangeCondition condition){
		return salaryChangeDAO.countSalaryChange(condition);
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW, rollbackFor={Exception.class})
	public int addSalaryChange(SalaryChange salaryChange){
		int result =0;
		//保存工资调整信息
		result = salaryChangeDAO.save(salaryChange);
		if(result<1){
			result = -1;
		}
		//同步员工基本信息
		PersonalSalaryInfo salary = personalSalaryInfoDAO.getPersonalSalaryInfoById(salaryChange.getPersonalInfoId());
		if(salary!=null){
			//调级--修改基本工资
			BigDecimal oldBasePay = salary.getBasePay();
			BigDecimal oldMeritPay = salary.getMeritPay();
			if(salaryChange.getType()==1){
				// 新基本工资=原基本工资+调薪幅度
				BigDecimal basePay= BigDecimal.ZERO;
				basePay=basePay.add(oldBasePay);
				basePay=basePay.add( salaryChange.getChangeRange());
				salary.setBasePay(basePay);
				// 转正工资=原转正工资+调薪幅度
				BigDecimal workerPay= BigDecimal.ZERO;
				workerPay=workerPay.add(salaryChange.getChangeRange());
				workerPay=workerPay.add(salary.getWorkerPay());
				salary.setWorkerPay(workerPay);
			}
			else{ //调薪或其他--修改绩效工资
				// 新绩效工资=原绩效工资+调薪幅度
				BigDecimal meritPay= BigDecimal.ZERO;
				meritPay=meritPay.add(oldMeritPay);
				meritPay=meritPay.add(salaryChange.getChangeRange());
				salary.setMeritPay(meritPay);
				// 转正工资=原转正工资+调薪幅度
				BigDecimal workerPay= BigDecimal.ZERO;
				workerPay=workerPay.add(salaryChange.getChangeRange());
				workerPay=workerPay.add(salary.getWorkerPay());
				salary.setWorkerPay(workerPay);
			}
			salary.setUpdateTime(new Date());
			boolean curResult = personalSalaryInfoDAO.update(salary);
			if(curResult){
				result = 1;
			}
			else{
				result = -3;
			}
		}
		else{
			result =-2;
		}
		return result;	
	}
	
	@Override
	public int countSalaryDetailByTerm(String term){
		return salaryDetailDAO.countSalaryDetailByTerm(term);
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW, rollbackFor={Exception.class})
	public int createSalaryDetailByTerm(String term){
		int result =0;
		int salaryCount = salaryDetailDAO.countSalaryDetailByTerm(term);
		if(salaryCount>0){
			//工资表已经出过，不能重复出
			return -2;
		}
		//构造工资出账周期
        SimpleDateFormat sdt=new SimpleDateFormat("yyyy-MM");
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
        //工资表最终明细
        List<SalaryDetail> salaryDetails= new ArrayList<SalaryDetail>();
        PersonalCondition condition = new PersonalCondition();
        //所有人员信息；包括在职和已离职；
    	List<PersonalAllExport> personalAllExports = personalInfoDAO.listPersonalAllExport(condition);
    	//遍历所有人员,构造工资表
    	for (PersonalAllExport personalAll : personalAllExports) {
    		//过滤已离职人员
    		int leaveStatus=personalAll.getLeaveStatus();
    		if(leaveStatus==0){
    			//如果离职时间在本月之前,过滤掉,不生成工资表
    			if(personalAll.getLeaveWorkingTime().before(startDate)){
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
    		Date workTime=personalAll.getWorkerTime();
    		//判断是否已转正;workTime<startDate  本月已转正
    		if(workTime.before(startDate)){
    			detail.setProbationaryPay(BigDecimal.ZERO);
    			detail.setBasePay(personalAll.getBasePay());
    			detail.setMeritPay(personalAll.getMeritPay());
    			detail.setOtherPay(BigDecimal.ZERO);
    			detail.setTrafficSubsidy(new BigDecimal(100));
    			detail.setComputerSubsidy(new BigDecimal(100));
    			detail.setMealSubsidy(new BigDecimal(300));
    			detail.setPhoneSubsidy(BigDecimal.ZERO);
    			//计算考勤扣款//////////////////////////////////
    			BigDecimal attendanceDeduction= new BigDecimal(0);
    			//获取考勤信息
				CheckWorkDetail checkWork=checkWorkDetailDAO.getCheckWorkDetailByName(personalAll.getName(), term);
				//全通或物联网考勤有数据
				if(checkWork!=null){
					//所有费用=试用期工资+基本工资+绩效工资+交通补助+电脑补助+餐补
					attendanceDeduction = attendanceDeduction.add(detail.getProbationaryPay());
					attendanceDeduction = attendanceDeduction.add(detail.getBasePay());
					attendanceDeduction = attendanceDeduction.add(detail.getMeritPay());
					attendanceDeduction = attendanceDeduction.add(detail.getTrafficSubsidy());
					attendanceDeduction = attendanceDeduction.add(detail.getComputerSubsidy());
					attendanceDeduction = attendanceDeduction.add(detail.getMealSubsidy());
					//所有费用/考勤天数
					attendanceDeduction=attendanceDeduction.divide(checkWork.getCheckWorkDays());
					//乘以缺勤天数
					attendanceDeduction=attendanceDeduction.multiply(checkWork.getSettlementDays());
				}
				else{
					//百度
				}
    			detail.setAttendanceDeduction(attendanceDeduction);
    			detail.setOtherDeduction(BigDecimal.ZERO);
    			
    		}
    		else{
    			//判断是否在本月转正;仍有部分试用期或全是试用期
    			//本月未转正 workTime>endDate
    			if(endDate.before(workTime)){
    				//全是试用期
    				detail.setProbationaryPay(personalAll.getProbationaryPay());
        			detail.setBasePay(BigDecimal.ZERO);
        			detail.setMeritPay(BigDecimal.ZERO);
        			detail.setOtherPay(BigDecimal.ZERO);
        			detail.setTrafficSubsidy(BigDecimal.ZERO);
        			detail.setComputerSubsidy(BigDecimal.ZERO);
        			detail.setMealSubsidy(BigDecimal.ZERO);
        			detail.setPhoneSubsidy(BigDecimal.ZERO);
        			//计算考勤扣款//////////////////////////////////
        			BigDecimal attendanceDeduction= new BigDecimal(0);
        			//获取考勤信息
    				CheckWorkDetail checkWork=checkWorkDetailDAO.getCheckWorkDetailByName(personalAll.getName(), term);
    				//全通或物联网考勤有数据
    				if(checkWork!=null){
    					//所有费用=试用期工资+基本工资+绩效工资+交通补助+电脑补助+餐补
    					attendanceDeduction = attendanceDeduction.add(detail.getProbationaryPay());
    					attendanceDeduction = attendanceDeduction.add(detail.getBasePay());
    					attendanceDeduction = attendanceDeduction.add(detail.getMeritPay());
    					attendanceDeduction = attendanceDeduction.add(detail.getTrafficSubsidy());
    					attendanceDeduction = attendanceDeduction.add(detail.getComputerSubsidy());
    					attendanceDeduction = attendanceDeduction.add(detail.getMealSubsidy());
    					//所有费用/考勤天数
    					attendanceDeduction=attendanceDeduction.divide(checkWork.getCheckWorkDays());
    					//乘以缺勤天数
    					attendanceDeduction=attendanceDeduction.multiply(checkWork.getSettlementDays());
    				}
    				else{
    					//百度
    				}
        			detail.setAttendanceDeduction(attendanceDeduction);
        			detail.setOtherDeduction(BigDecimal.ZERO);
        			
    			}
    			else{
    				//本月转正startDate<workTime<endDate 仍有部分试用期
    				//计算考勤扣款//////////////////////////////////
        			BigDecimal attendanceDeduction= new BigDecimal(0);
    				//获取考勤信息
    				CheckWorkDetail checkWork=checkWorkDetailDAO.getCheckWorkDetailByName(personalAll.getName(), term);
    				//全通或物联网考勤有数据
    				if(checkWork!=null){
    					//计算试用期天数
    					int dutyDays= DateTimeUtil.getDutyDays(startDate, workTime);
    					//计算转正天数
    					BigDecimal workDays= checkWork.getCheckWorkDays().subtract(new BigDecimal(dutyDays));
    					//获取试用期工资
    					BigDecimal probationaryPay = personalAll.getProbationaryPay();
    					
    					//除去本月考勤天数
    					probationaryPay = probationaryPay.divide(checkWork.getCheckWorkDays());
    					//乘以本月试用期天数
    					probationaryPay = probationaryPay.multiply(new BigDecimal(dutyDays));

    					probationaryPay = probationaryPay.setScale(2);
    					detail.setProbationaryPay(probationaryPay);
    					
    					//转正工资
    					BigDecimal workPay = personalAll.getWorkerPay();
    					//除去本月考勤天数
    					workPay = workPay.divide(checkWork.getCheckWorkDays());
    					//乘以本月试用期天数
    					workPay = workPay.multiply(workDays);
    					workPay = workPay.setScale(2);
            			detail.setBasePay(workPay);
            			detail.setMeritPay(BigDecimal.ZERO);
            			detail.setOtherPay(BigDecimal.ZERO);
            			detail.setTrafficSubsidy(BigDecimal.ZERO);
            			detail.setComputerSubsidy(BigDecimal.ZERO);
            			detail.setMealSubsidy(BigDecimal.ZERO);
            			detail.setPhoneSubsidy(BigDecimal.ZERO);
            			//所有费用=试用期工资+基本工资+绩效工资+交通补助+电脑补助+餐补
    					attendanceDeduction = attendanceDeduction.add(detail.getProbationaryPay());
    					attendanceDeduction = attendanceDeduction.add(detail.getBasePay());
    					attendanceDeduction = attendanceDeduction.add(detail.getMeritPay());
    					attendanceDeduction = attendanceDeduction.add(detail.getTrafficSubsidy());
    					attendanceDeduction = attendanceDeduction.add(detail.getComputerSubsidy());
    					attendanceDeduction = attendanceDeduction.add(detail.getMealSubsidy());
    					//所有费用/考勤天数
    					attendanceDeduction=attendanceDeduction.divide(checkWork.getCheckWorkDays());
    					//乘以缺勤天数
    					attendanceDeduction=attendanceDeduction.multiply(checkWork.getSettlementDays());
    				}
    				else{
    					//百度
    				}
    				detail.setAttendanceDeduction(attendanceDeduction);
        			detail.setOtherDeduction(BigDecimal.ZERO);
    				
    			}
    		}
    		
    		//本月应发工资
			//试用期工资+基本工资+绩效工资+其他工资+交通补助+电脑补助+餐补+话补-考勤扣款-其它扣款
			BigDecimal totalShouldPay= new BigDecimal(0);
			totalShouldPay = totalShouldPay.add(detail.getProbationaryPay());
			totalShouldPay = totalShouldPay.add(detail.getBasePay());
			totalShouldPay = totalShouldPay.add(detail.getMeritPay());
			totalShouldPay = totalShouldPay.add(detail.getOtherPay());
			totalShouldPay = totalShouldPay.add(detail.getTrafficSubsidy());
			totalShouldPay = totalShouldPay.add(detail.getComputerSubsidy());
			totalShouldPay = totalShouldPay.add(detail.getMealSubsidy());
			totalShouldPay = totalShouldPay.add(detail.getPhoneSubsidy());
			totalShouldPay = totalShouldPay.subtract(detail.getAttendanceDeduction());
			totalShouldPay = totalShouldPay.subtract(detail.getOtherDeduction());
			detail.setShouldPay(totalShouldPay);
			detail.setIsDel(1);
			detail.setCreateTime(new Date());
    		salaryDetails.add(detail);
		}
    	if(salaryDetailDAO.save(salaryDetails)){
    		result = 1;
    	}
    	else{
    		result = -1;
    	}
		return result;
	}
}
