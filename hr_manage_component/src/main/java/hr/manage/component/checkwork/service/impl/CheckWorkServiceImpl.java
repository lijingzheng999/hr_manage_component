package hr.manage.component.checkwork.service.impl;

import hr.manage.component.checkwork.dao.CheckWorkAnnualLeaveDAO;
import hr.manage.component.checkwork.dao.CheckWorkBaiduDAO;
import hr.manage.component.checkwork.dao.CheckWorkBaiduDetailDAO;
import hr.manage.component.checkwork.dao.CheckWorkCurrentDAO;
import hr.manage.component.checkwork.dao.CheckWorkCurrentLogDAO;
import hr.manage.component.checkwork.dao.CheckWorkDetailDAO;
import hr.manage.component.checkwork.model.CheckWorkAnnualLeave;
import hr.manage.component.checkwork.model.CheckWorkBaidu;
import hr.manage.component.checkwork.model.CheckWorkBaiduDetail;
import hr.manage.component.checkwork.model.CheckWorkCurrent;
import hr.manage.component.checkwork.model.CheckWorkCurrentLog;
import hr.manage.component.checkwork.model.CheckWorkDetail;
import hr.manage.component.checkwork.model.CheckWorkDetailCondition;
import hr.manage.component.checkwork.service.CheckWorkService;
import hr.manage.component.personal.dao.PersonalInfoDAO;
import hr.manage.component.personal.dao.PersonalSalaryInfoDAO;
import hr.manage.component.personal.model.PersonalInfo;
import hr.manage.component.personal.model.PersonalSalaryInfo;
import hr.manage.component.personal.model.PersonalWorkInfo;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.app.event.ReferenceInsertionEventHandler.referenceInsertExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Component
public class CheckWorkServiceImpl implements CheckWorkService {
	
	private final Log logger = LogFactory.getLog(CheckWorkServiceImpl.class);
	@Autowired
	CheckWorkDetailDAO checkWorkDetailDAO;
	@Autowired
	CheckWorkCurrentDAO checkWorkCurrentDAO;
	@Autowired
	CheckWorkCurrentLogDAO checkWorkCurrentLogDAO;
	@Autowired
	CheckWorkBaiduDAO checkWorkBaiduDAO;
	@Autowired
	CheckWorkBaiduDetailDAO checkWorkBaiduDetailDAO;
	@Autowired
	PersonalInfoDAO personalInfoDAO;
	@Autowired
	PersonalSalaryInfoDAO personalSalaryInfoDAO;
	@Autowired
	CheckWorkAnnualLeaveDAO checkWorkAnnualLeaveDAO;
	
	@Override
	public List<CheckWorkDetail> listCheckWorkDetail(CheckWorkDetailCondition condition){
		return checkWorkDetailDAO.listCheckWorkDetail(condition);
	}
	
	@Override
	public Long countCheckWorkDetail(CheckWorkDetailCondition condition){
		return checkWorkDetailDAO.countCheckWorkDetail(condition);
	}
	@Override
	public  CheckWorkDetail getCheckWorkDetailById(Integer detailId){
		return checkWorkDetailDAO.get(detailId);
	}
	
	@Override
	public  int updateCheckWorkDetail(CheckWorkDetail detail){
		int result =0;
		if(checkWorkDetailDAO.update(detail)){
			result =1;
		}
		return result;
	}
	
	@Override
	public  List<CheckWorkCurrent> listCheckWorkCurrent(CheckWorkDetailCondition condition){
		return checkWorkCurrentDAO.listCheckWorkCurrent(condition);
	}
	
	@Override
	public  Long countCheckWorkCurrent(CheckWorkDetailCondition condition){
		return checkWorkCurrentDAO.countCheckWorkCurrent(condition);
	}
	
	@Override
	public  CheckWorkCurrent getCheckWorkCurrentById(Integer currentId){
		return checkWorkCurrentDAO.get(currentId);
	}
	
	@Override
	public  int updateCheckWorkCurrent(CheckWorkCurrent current){
		int result =0;
		if(checkWorkCurrentDAO.update(current)){
			result =1;
		}
		return result;
	}
	
	@Override
	public  int deleteCheckWorkCurrent(Integer currentId){
		int result =0;
		result = checkWorkCurrentDAO.deleteCheckWorkCurrentById(currentId);
			
		return result;
	}
//	@Override
//	public CheckWorkDetail getCheckWorkDetailByName(CheckWorkDetailCondition condition){
//		return checkWorkDetailDAO.getCheckWorkDetailByName(condition);
//	}
//	
	
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = { Exception.class })
	public synchronized int saveCheckWorkDetailListRecord( List<CheckWorkDetail> checkWorkList){
		int result = 0;
		for (CheckWorkDetail checkWork : checkWorkList) {
			
			logger.info("saveCheckWorkDetailListRecord : 员工姓名：" + checkWork.getName() +" ---考勤月份："+checkWork.getTerm());
			//判断是否导入过本月考勤
			CheckWorkDetail curDetail = checkWorkDetailDAO.getCheckWorkDetailByName(checkWork.getName(),checkWork.getTerm());
			if(curDetail!=null && curDetail.getUpdateTime()!=null){
				continue;
			}
			if(curDetail!=null){
				//之前导入过,进行恢复处理
				//删除导入的当月考勤数据
				checkWorkDetailDAO.deleteCheckWorkDetailByName(checkWork.getName(),checkWork.getTerm());
			}
			//判断是否年假加班汇总表数据
			CheckWorkAnnualLeave annual = checkWorkAnnualLeaveDAO.getCheckWorkAnnualLeaveByName(checkWork.getName(),checkWork.getTerm());
			if(annual!=null){
				//有数据，根据term恢复
				String termMonth =checkWork.getTerm().substring(4);
				switch (termMonth) {
				case "01":
					annual.setAnnualLeaveJan(BigDecimal.ZERO);
					annual.setOvertimeJan(BigDecimal.ZERO);
					annual.setOffDutyShiftJan(BigDecimal.ZERO);
					annual.setLeaveJan(BigDecimal.ZERO);
				
					break;
				case "02":
					annual.setAnnualLeaveFeb(BigDecimal.ZERO);
					annual.setOvertimeFeb(BigDecimal.ZERO);
					annual.setOffDutyShiftFeb(BigDecimal.ZERO);
					annual.setLeaveFeb(BigDecimal.ZERO);
					break;
				case "03":
					annual.setAnnualLeaveMar(BigDecimal.ZERO);
					annual.setOvertimeMar(BigDecimal.ZERO);
					annual.setOffDutyShiftMar(BigDecimal.ZERO);
					annual.setLeaveMar(BigDecimal.ZERO);
					break;
				case "04":
					annual.setAnnualLeaveApr(BigDecimal.ZERO);
					annual.setOvertimeApr(BigDecimal.ZERO);
					annual.setOffDutyShiftApr(BigDecimal.ZERO);
					annual.setLeaveApr(BigDecimal.ZERO);
					break;
				case "05":
					annual.setAnnualLeaveMay(BigDecimal.ZERO);
					annual.setOvertimeMay(BigDecimal.ZERO);
					annual.setOffDutyShiftMay(BigDecimal.ZERO);
					annual.setLeaveMay(BigDecimal.ZERO);
					break;
				case "06":
					annual.setAnnualLeaveJun(BigDecimal.ZERO);
					annual.setOvertimeJun(BigDecimal.ZERO);
					annual.setOffDutyShiftJun(BigDecimal.ZERO);
					annual.setLeaveJun(BigDecimal.ZERO);
					break;
				case "07":
					annual.setAnnualLeaveJul(BigDecimal.ZERO);
					annual.setOvertimeJul(BigDecimal.ZERO);
					annual.setOffDutyShiftJul(BigDecimal.ZERO);
					annual.setLeaveJul(BigDecimal.ZERO);
					break;
				case "08":
					annual.setAnnualLeaveAug(BigDecimal.ZERO);
					annual.setOvertimeAug(BigDecimal.ZERO);
					annual.setOffDutyShiftAug(BigDecimal.ZERO);
					annual.setLeaveAug(BigDecimal.ZERO);
					break;
				case "09":
					annual.setAnnualLeaveSept(BigDecimal.ZERO);
					annual.setOvertimeSept(BigDecimal.ZERO);
					annual.setOffDutyShiftSept(BigDecimal.ZERO);
					annual.setLeaveSept(BigDecimal.ZERO);
					break;
				case "10":
					annual.setAnnualLeaveOct(BigDecimal.ZERO);
					annual.setOvertimeOct(BigDecimal.ZERO);
					annual.setOffDutyShiftOct(BigDecimal.ZERO);
					annual.setLeaveOct(BigDecimal.ZERO);
					break;
				case "11":
					annual.setAnnualLeaveNov(BigDecimal.ZERO);
					annual.setOvertimeNov(BigDecimal.ZERO);
					annual.setOffDutyShiftNov(BigDecimal.ZERO);
					annual.setLeaveNov(BigDecimal.ZERO);
					break;
				case "12":
					annual.setAnnualLeaveDec(BigDecimal.ZERO);
					annual.setOvertimeDec(BigDecimal.ZERO);
					annual.setOffDutyShiftDec(BigDecimal.ZERO);
					annual.setLeaveDec(BigDecimal.ZERO);
					break;
				default:
					break;
				}
				
				//总年假
				BigDecimal annualDays=BigDecimal.ZERO;
				annualDays = annualDays.add(annual.getAnnualLeaveJan());
				annualDays = annualDays.add(annual.getAnnualLeaveFeb());
				annualDays = annualDays.add(annual.getAnnualLeaveMar());
				annualDays = annualDays.add(annual.getAnnualLeaveApr());
				annualDays = annualDays.add(annual.getAnnualLeaveMay());
				annualDays = annualDays.add(annual.getAnnualLeaveJun());
				annualDays = annualDays.add(annual.getAnnualLeaveJul());
				annualDays = annualDays.add(annual.getAnnualLeaveAug());
				annualDays = annualDays.add(annual.getAnnualLeaveSept());
				annualDays = annualDays.add(annual.getAnnualLeaveOct());
				annualDays = annualDays.add(annual.getAnnualLeaveNov());
				annualDays = annualDays.add(annual.getAnnualLeaveDec());
				//剩余年假
				BigDecimal surplusAnnualDays=annual.getAnnualLeaveDays().subtract(annualDays);
				annual.setSurplusAnnualLeave(surplusAnnualDays);
				//总加班
				BigDecimal overtimeDays=BigDecimal.ZERO;
				overtimeDays = overtimeDays.add(annual.getOvertimeJan());
				overtimeDays = overtimeDays.add(annual.getOvertimeFeb());
				overtimeDays = overtimeDays.add(annual.getOvertimeMar());
				overtimeDays = overtimeDays.add(annual.getOvertimeApr());
				overtimeDays = overtimeDays.add(annual.getOvertimeMay());
				overtimeDays = overtimeDays.add(annual.getOvertimeJun());
				overtimeDays = overtimeDays.add(annual.getOvertimeJul());
				overtimeDays = overtimeDays.add(annual.getOvertimeAug());
				overtimeDays = overtimeDays.add(annual.getOvertimeSept());
				overtimeDays = overtimeDays.add(annual.getOvertimeOct());
				overtimeDays = overtimeDays.add(annual.getOvertimeNov());
				overtimeDays = overtimeDays.add(annual.getOvertimeDec());
				annual.setOvertimeCollect(overtimeDays);
				//总调休
				BigDecimal shiftDays=BigDecimal.ZERO;
				shiftDays = shiftDays.add(annual.getOffDutyShiftJan());
				shiftDays = shiftDays.add(annual.getOffDutyShiftFeb());
				shiftDays = shiftDays.add(annual.getOffDutyShiftMar());
				shiftDays = shiftDays.add(annual.getOffDutyShiftApr());
				shiftDays = shiftDays.add(annual.getOffDutyShiftMay());
				shiftDays = shiftDays.add(annual.getOffDutyShiftJun());
				shiftDays = shiftDays.add(annual.getOffDutyShiftJul());
				shiftDays = shiftDays.add(annual.getOffDutyShiftAug());
				shiftDays = shiftDays.add(annual.getOffDutyShiftSept());
				shiftDays = shiftDays.add(annual.getOffDutyShiftOct());
				shiftDays = shiftDays.add(annual.getOffDutyShiftNov());
				shiftDays = shiftDays.add(annual.getOffDutyShiftDec());
				annual.setOffDutyShiftCollect(shiftDays);
				//剩余加班天数
				BigDecimal surplusOvertime=overtimeDays.subtract(shiftDays);
				annual.setSurplusOvertimeDays(surplusOvertime);
				//请假天数
				BigDecimal leaveDays = BigDecimal.ZERO;
				leaveDays = leaveDays.add(annual.getLeaveJan());
				leaveDays = leaveDays.add(annual.getLeaveFeb());
				leaveDays = leaveDays.add(annual.getLeaveMar());
				leaveDays = leaveDays.add(annual.getLeaveApr());
				leaveDays = leaveDays.add(annual.getLeaveMay());
				leaveDays = leaveDays.add(annual.getLeaveJun());
				leaveDays = leaveDays.add(annual.getLeaveJul());
				leaveDays = leaveDays.add(annual.getLeaveAug());
				leaveDays = leaveDays.add(annual.getLeaveSept());
				leaveDays = leaveDays.add(annual.getLeaveOct());
				leaveDays = leaveDays.add(annual.getLeaveNov());
				leaveDays = leaveDays.add(annual.getLeaveDec());
				annual.setSurplusLeave(leaveDays);
				annual.setUpdateTime(new Date());
			}
			else{
				//本年度没有年假汇总表数据，进行初始化
			}
			///////////////摒弃Current表和Log表////////////////////////////////
//				//根据考勤并更记录恢复年假和加班数据
//				//获取最新一条变更记录
//				CheckWorkCurrentLog curLog = checkWorkCurrentLogDAO.getCheckWorkCurrentLogByName(checkWork.getName(),checkWork.getTerm());
//				if(curLog!=null){
//					CheckWorkCurrent curCurrent = checkWorkCurrentDAO.getCheckWorkCurrentByNameTerm(checkWork.getName(),checkWork.getTerm().substring(0, 4));
//					if(curCurrent!=null){
//						//恢复加班和年假天数
//						curCurrent.setSurplusOvertimeHours(curLog.getSurplusOvertimeHours());
//						curCurrent.setSurplusAnnualLeave(curLog.getSurplusAnnualLeave());
//						curCurrent.setUpdateTime(new Date());
//						checkWorkCurrentDAO.update(curCurrent);
//					}
//					//删除变更日志记录
//					checkWorkCurrentLogDAO.deleteCheckWorkCurrentLogByName(checkWork.getName(),checkWork.getTerm());
//					
//				}
//			}
//		    //判断考勤年度是否有当前剩余年假和加班小时数数据
//		    CheckWorkCurrent checkWorkCurrent = checkWorkCurrentDAO.getCheckWorkCurrentByNameTerm(checkWork.getName(),checkWork.getTerm().substring(0, 4));
//		    if(checkWorkCurrent == null){
//		    	//初始化当前剩余年假和加班小时数
//		    	//没有数据，已excel中的数据为准，不进行计算；
//			    CheckWorkCurrent current = new CheckWorkCurrent();
//			    current.setPersonalInfoId(checkWork.getPersonalInfo().getId());
//			    current.setName(checkWork.getName());
//			    current.setTerm(checkWork.getTerm().substring(0, 4));
//			    current.setSurplusOvertimeHours(checkWork.getSurplusOvertimeHours());
//			    current.setAnnualLeaveDays(checkWork.getAnnualLeaveDays());
//			    current.setSurplusAnnualLeave(checkWork.getSurplusAnnualLeave());
//			    current.setSickLeaveDays(checkWork.getSickLeaveDays());
//			    current.setCompassionateLeaveDays(checkWork.getCompassionateLeaveDays());
//			    current.setIsDel(1);
//			    current.setCreateTime(new Date());
//			    checkWorkCurrentDAO.save(current);
//			    
//			    //保存加班和年假数据变更记录;记录变更前的数据,以便回退;
//			    CheckWorkCurrentLog log = new CheckWorkCurrentLog();
//			    log.setPersonalInfoId(checkWork.getPersonalInfo().getId());
//			    log.setName(checkWork.getName());
//			    log.setTerm(checkWork.getTerm());
//			    //原有没有数据，默认0
//			    log.setSurplusOvertimeHours(0);
//			    log.setSurplusAnnualLeave(BigDecimal.ZERO);
//			    log.setIsDel(1);
//			    log.setCreateTime(new Date());
//			    checkWorkCurrentLogDAO.save(log);
//			    BigDecimal addOverTime =checkWork.getOvertimeDays().subtract(checkWork.getLeaveDays());
//				int r =addOverTime.compareTo(BigDecimal.ZERO);
//				//加班大于请假
//				if(r>=0){
//		    		 //记录到当月考勤扣款
//					checkWork.setSettlementDays(BigDecimal.ZERO);
//		    	}
//		    	else{
//		    		//请假大于加班,需要比较年假
//		    		BigDecimal settleDays=addOverTime.add(checkWork.getAnnualLeaveDays());
//		    		int sr =settleDays.compareTo(BigDecimal.ZERO);
//		    		if(sr>=0){
//		    			//年假可以抵扣缺勤
//						checkWork.setSettlementDays(BigDecimal.ZERO);
//		    		}
//		    		else{
//		    			//年假不能抵扣缺勤记录到考勤欠款
//		    			checkWork.setSettlementDays(settleDays.abs());
//		    		}
//		    		
//		    	}
//			   
//		    }
//		    else{
//		    	//当前有数据,进行变更增减
//		    	//保存加班和年假数据变更记录;记录变更前的数据,以便回退;
//		    	CheckWorkCurrentLog log = new CheckWorkCurrentLog();
//			    log.setPersonalInfoId(checkWork.getPersonalInfo().getId());
//			    log.setName(checkWork.getName());
//			    log.setTerm(checkWork.getTerm());
//			    //记录原有的加班小时数和年假天数,以便回退
//			    log.setSurplusOvertimeHours(checkWorkCurrent.getSurplusOvertimeHours());
//			    log.setSurplusAnnualLeave(checkWorkCurrent.getSurplusAnnualLeave());
//			    log.setIsDel(1);
//			    log.setCreateTime(new Date());
//			    checkWorkCurrentLogDAO.save(log);
//			    
//		    	checkWorkCurrent.setSickLeaveDays(checkWork.getSickLeaveDays());
//		    	checkWorkCurrent.setCompassionateLeaveDays(checkWork.getCompassionateLeaveDays());
//		    	checkWorkCurrent.setUpdateTime(new Date());
//		    	// 本月是否有新增加班小时数=加班天数-请假天数
//				BigDecimal addOverTime =checkWork.getOvertimeDays().subtract(checkWork.getLeaveDays());
//				int r =addOverTime.compareTo(BigDecimal.ZERO);
//		    	//加班大于请假
//				if(r>=0){
//					//调整后剩余加班小时数=新增加班天数*8+原有加班小时数
//					int newOverTime =addOverTime.multiply(new BigDecimal('8')).intValue()+checkWorkCurrent.getSurplusOvertimeHours();
//					checkWorkCurrent.setSurplusOvertimeHours(newOverTime);
//				}
//				else
//				{
//					//加班小于请假
//					//比较变更的缺勤天数*8与加班小时数，是否可以完全抵消
//					int subOverTime = checkWorkCurrent.getSurplusOvertimeHours() - addOverTime.multiply(new BigDecimal('8')).intValue();
//					if(subOverTime>=0){
//						checkWorkCurrent.setSurplusOvertimeHours(subOverTime);
//					}
//					else{
//						//加班不够抵扣，需要抵抗年假;加班清0,扣减年假
//						checkWorkCurrent.setSurplusOvertimeHours(0);
//						BigDecimal subLeaveDays=checkWorkCurrent.getSurplusAnnualLeave().subtract(new BigDecimal(subOverTime).divide(new BigDecimal(8)).setScale(1));
//						int lr = subLeaveDays.compareTo(BigDecimal.ZERO);
//						//剩余年假够抵扣
//						if(lr>=0){
//							checkWorkCurrent.setSurplusAnnualLeave(subLeaveDays);
//						}
//						else{
//							//lr=-1 不够抵扣,需记录到考勤扣款;剩余年假清0
//							checkWorkCurrent.setSurplusAnnualLeave(BigDecimal.ZERO);
//							//记录到当月考勤扣款
//							checkWork.setSettlementDays(subLeaveDays.abs());
//						}
//						
//					}
//				}
//				//保存变更后的当前加班小时数和剩余年假数
//		    	checkWorkCurrentDAO.update(checkWorkCurrent);
//		    } 
		    
		    //保存月考勤数据
		    checkWorkDetailDAO.save(checkWork);
		    
		}
		result = 1;
		return result;
	}
	
	@Override
	public  List<CheckWorkBaidu> listCheckWorkBaidu(CheckWorkDetailCondition condition){
		return checkWorkBaiduDAO.listCheckWorkBaidu(condition);
	}
	@Override
	public  Long countCheckWorkBaidu(CheckWorkDetailCondition condition){
		return checkWorkBaiduDAO.countCheckWorkBaidu(condition);
	}
	@Override
	public  CheckWorkBaidu getCheckWorkBaiduById(Integer baiduId){
		CheckWorkBaidu baidu = checkWorkBaiduDAO.get(baiduId);
		if(baidu!=null){
			List<CheckWorkBaiduDetail> baiduDetails = checkWorkBaiduDetailDAO.getCheckWorkBaiduDetailByBaiduId(baiduId);
			baidu.setBaiduDetails(baiduDetails);
		}
		return baidu;
	}
	@Override
	public  int updateCheckWorkBaidu(CheckWorkBaidu baidu){
		int result =0;
	    		//修改绩效部分-绩效本月应发
//    		baidu.setMeritPay(personalSalaryInfo.getMeritPay());
    		//暂估残保金（绩效本月应发*1.7%）
    		baidu.setResidualPay(baidu.getMeritPay().multiply(new BigDecimal("0.017")).setScale(2,BigDecimal.ROUND_HALF_UP));
    		//增值税及附加税=(绩效本月应发+暂估残保金)*6.72%
    		BigDecimal addedTax=baidu.getMeritPay().add(baidu.getResidualPay());
    		addedTax = addedTax.multiply(new BigDecimal("0.0672")).setScale(2,BigDecimal.ROUND_HALF_UP);
    		baidu.setAddedTax(addedTax);
    		//合计给我司结算金额=绩效本月应发+暂估残保金+增值税及附加税
    		BigDecimal settlementPay = baidu.getMeritPay().add(baidu.getResidualPay());
    		settlementPay=settlementPay.add(baidu.getAddedTax());
    		baidu.setSettlementPay(settlementPay);
    		//全通结算单价
//    		baidu.setSettlementPrice(personalSalaryInfo.getSettlementPrice());
    		//全通日结算单价=全通结算单价/应出勤天数
    		BigDecimal settlementPriceDay = baidu.getSettlementPrice();
    		settlementPriceDay=settlementPriceDay.divide(new BigDecimal(""+baidu.getAttendanceDays()),2,BigDecimal.ROUND_HALF_UP);
    		baidu.setSettlementPriceDay(settlementPriceDay);
    		//绩效奖励金额折算为天数=ROUND(合计给我司结算金额/全通日结算单价,2)
    		BigDecimal meritPayDays= baidu.getSettlementPay();
    		meritPayDays=meritPayDays.divide(baidu.getSettlementPriceDay(),2,BigDecimal.ROUND_HALF_UP);
    		baidu.setMeritPayDays(meritPayDays);
    		//记录上报全通考勤总天数=ROUND(应出勤天数+超出小时折算全通给我司结算为天数 +折算全通给我司结算为天数+绩效奖励金额折算为天数,2)
    		BigDecimal settlementFinalDays = new BigDecimal(""+baidu.getAttendanceDays());
    		settlementFinalDays = settlementFinalDays.add(baidu.getOverstepDays());
    		settlementFinalDays=settlementFinalDays.add(baidu.getOvertimeSettleDays());
    		settlementFinalDays=settlementFinalDays.add(meritPayDays).setScale(2,BigDecimal.ROUND_HALF_UP);
    		baidu.setSettlementFinalDays(settlementFinalDays);
    	
		if(checkWorkBaiduDAO.update(baidu)){
			
			result =1;
		}
		return result;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = { Exception.class })
	public  int saveCheckWorkBaiduListRecord( String term, Integer attendanceDays, List<CheckWorkBaidu> baiduList){
		int result = 0;
		for (CheckWorkBaidu baidu : baiduList) {
			logger.info("saveCheckWorkBaiduListRecord : 员工姓名：" + baidu.getName() +" ---考勤月份："+term);
			//判断是否导入过本月考勤
			CheckWorkBaidu curBaidu = checkWorkBaiduDAO.getCheckWorkBaiduByNameTerm(baidu.getName(),term);
			if(curBaidu!=null && curBaidu.getUpdateTime()!=null){
				continue;
			}
			if(curBaidu!=null){
				//之前导入过,进行恢复处理
				//删除导入的当月考勤数据
				checkWorkBaiduDAO.deleteCheckWorkBaiduByName(baidu.getName(),term);
				//删除导入的考勤日明细;
				checkWorkBaiduDetailDAO.deleteCheckWorkBaiduDetailById(curBaidu.getId());
			}
			PersonalSalaryInfo personalSalaryInfo=null;
			PersonalInfo personalInfo = personalInfoDAO
					.getPersonalByName(baidu.getName());
			if(personalInfo!=null){
				personalSalaryInfo = personalSalaryInfoDAO
						.getPersonalSalaryInfoById(personalInfo.getId());
			}
			
			//构造百度考勤	
			baidu.setTerm(term);
			baidu.setAttendanceDays(attendanceDays);
			SimpleDateFormat sdt=new SimpleDateFormat("yyyyMM");
			java.util.Date startDate=null;
			try {
				startDate = sdt.parse(String.valueOf(term).trim());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			baidu.setStartDate(startDate);
	        Calendar endDate = Calendar.getInstance();  
	        endDate.setTime(startDate);  
	        endDate.add(Calendar.MONTH, 1);  
	        baidu.setEndDate(endDate.getTime());
	        if(baidu.getBaiduDetails()!=null){
	        	//实际出勤小时数 
	        	BigDecimal checkWorkHours=BigDecimal.ZERO;
	        	//超出小时数（实际出勤-应出勤=超出小时数）
	        	BigDecimal overstepHours=BigDecimal.ZERO;
	        	//超出小时折算全通给我司结算为天数
	        	BigDecimal overstepDays=BigDecimal.ZERO;
	        	//加班小时数
	        	BigDecimal overtimeHours=BigDecimal.ZERO;
	        	//1倍核算天数
	        	BigDecimal oneHours=BigDecimal.ZERO;
	        	//1.5倍核算天数
	        	BigDecimal onePointFiveHours=BigDecimal.ZERO;
	        	//2倍核算天数
	        	BigDecimal twoHours=BigDecimal.ZERO;
	        	//3倍核算天数
	        	BigDecimal threeHours=BigDecimal.ZERO;
	        	//加班应发工资合计小时数
	        	BigDecimal overtimeSumHours=BigDecimal.ZERO;
	        	//折算全通给我司结算为天数
	        	BigDecimal overtimeSettleDays=BigDecimal.ZERO;
	        	//全通加班结算天数合计
	        	BigDecimal settlementDays=BigDecimal.ZERO;
	        	for (CheckWorkBaiduDetail detail : baidu.getBaiduDetails()) {
	        		/** 0:正常班；1：普通加班；2：周末加班；3：节假日正常班 （4倍） ；4：节假日加班(3倍)  ；5：年假算正常班 ；6：病假不算上班；7事假不算上班*/
	        		switch (detail.getWorkType()) {
					case 0:
						checkWorkHours=checkWorkHours.add(detail.getWorkHours());
						break;
					case 1:
						onePointFiveHours=onePointFiveHours.add(detail.getWorkHours());
						break;
					case 2:
						twoHours=twoHours.add(detail.getWorkHours());
						break;
					case 3:
						threeHours=threeHours.add(detail.getWorkHours());
						checkWorkHours=checkWorkHours.add(detail.getWorkHours());
						break;
					case 4:
						threeHours=threeHours.add(detail.getWorkHours());
						break;
					case 5:
						checkWorkHours=checkWorkHours.add(detail.getWorkHours());
						break;
					default:
						break;
					}
				}
	        	baidu.setCheckWorkHours(checkWorkHours);
	        	overstepHours=checkWorkHours.subtract(baidu.getAttendanceHours());
	        	baidu.setOverstepHours(overstepHours);
        		//=ROUND(超出小时数*1.5/应出勤*22,2)
	        	overstepDays=overstepDays.add(overstepHours);
	        	overstepDays=overstepDays.multiply(new BigDecimal("1.5"));
	        	overstepDays=overstepDays.divide(baidu.getAttendanceHours(),6,BigDecimal.ROUND_HALF_UP);
	        	overstepDays=overstepDays.multiply(new BigDecimal(""+attendanceDays));
	        	overstepDays=overstepDays.setScale(2, BigDecimal.ROUND_HALF_UP);
	        	baidu.setOverstepDays(overstepDays);
//	        	int r =overstepHours.compareTo(BigDecimal.ZERO);
//	        	if(r>=0){
//	        		baidu.setOverstepHours(overstepHours);
//	        		//=ROUND(超出小时数*1.5/应出勤*22,2)
//		        	overstepDays=overstepDays.add(overstepHours);
//		        	overstepDays=overstepDays.multiply(new BigDecimal("1.5"));
//		        	overstepDays=overstepDays.divide(baidu.getAttendanceHours(),6,BigDecimal.ROUND_HALF_UP);
//		        	overstepDays=overstepDays.multiply(new BigDecimal(""+attendanceDays));
//		        	overstepDays=overstepDays.setScale(2, BigDecimal.ROUND_HALF_UP);
//		        	baidu.setOverstepDays(overstepDays);
//	        	}
//	        	else{
//	        		baidu.setOverstepHours(BigDecimal.ZERO);
//	        		baidu.setOverstepDays(BigDecimal.ZERO);
//	        	}
	        	//加班小时数
	        	overtimeHours=overtimeHours.add(oneHours);
	        	overtimeHours=overtimeHours.add(onePointFiveHours);
	        	overtimeHours=overtimeHours.add(twoHours);
	        	overtimeHours=overtimeHours.add(threeHours);
	        	baidu.setOvertimeHours(overtimeHours);
	        	baidu.setOneHours(oneHours);
	        	baidu.setOnePointFiveHours(onePointFiveHours);
	        	baidu.setTwoHours(twoHours);
	        	baidu.setThreeHours(threeHours);
	        	//加班应发工资合计小时数=(oneHours*1)+(onePointFiveHours*1.5)+(twoHours*2)+(threeHours*3)
	        	overtimeSumHours=overtimeSumHours.add(oneHours.multiply(new BigDecimal("1")));
	        	overtimeSumHours=overtimeSumHours.add(onePointFiveHours.multiply(new BigDecimal("1.5")));
	        	overtimeSumHours=overtimeSumHours.add(twoHours.multiply(new BigDecimal("2")));
	        	overtimeSumHours=overtimeSumHours.add(threeHours.multiply(new BigDecimal("3")));
	        	baidu.setOvertimeSumHours(overtimeSumHours);
	        	//折算全通给我司结算为天数=ROUND(加班应发工资合计小时数/应出勤小时数*22,2)
	        	overtimeSettleDays=overtimeSettleDays.add(overtimeSumHours);
	        	overtimeSettleDays=overtimeSettleDays.divide(baidu.getAttendanceHours(),6,BigDecimal.ROUND_HALF_UP);
	        	overtimeSettleDays=overtimeSettleDays.multiply(new BigDecimal(""+attendanceDays));
	        	overtimeSettleDays=overtimeSettleDays.setScale(2, BigDecimal.ROUND_HALF_UP);
	        	baidu.setOvertimeSettleDays(overtimeSettleDays);
	        	//全通加班结算天数合计=超出小时折算全通给我司结算为天数+折算全通给我司结算为天数
	        	settlementDays=settlementDays.add(overstepDays);
	        	settlementDays=settlementDays.add(overtimeSettleDays);
	        	baidu.setSettlementDays(settlementDays);
	        	if(personalSalaryInfo!=null){
	        		//绩效本月应发
	        		baidu.setMeritPay(BigDecimal.ZERO);
	        		//暂估残保金（绩效本月应发*1.7%）
	        		baidu.setResidualPay(baidu.getMeritPay().multiply(new BigDecimal("0.017")).setScale(2,BigDecimal.ROUND_HALF_UP));
	        		//增值税及附加税=(绩效本月应发+暂估残保金)*6.72%
	        		BigDecimal addedTax=baidu.getMeritPay().add(baidu.getResidualPay());
	        		addedTax = addedTax.multiply(new BigDecimal("0.0672")).setScale(2,BigDecimal.ROUND_HALF_UP);
	        		baidu.setAddedTax(addedTax);
	        		//合计给我司结算金额=绩效本月应发+暂估残保金+增值税及附加税
	        		BigDecimal settlementPay = baidu.getMeritPay().add(baidu.getResidualPay());
	        		settlementPay=settlementPay.add(baidu.getAddedTax());
	        		baidu.setSettlementPay(settlementPay);
	        		//全通结算单价
	        		baidu.setSettlementPrice(personalSalaryInfo.getSettlementPrice());
	        		//全通日结算单价=全通结算单价/应出勤天数
	        		BigDecimal settlementPriceDay = baidu.getSettlementPrice();
	        		settlementPriceDay=settlementPriceDay.divide(new BigDecimal(""+attendanceDays),2,BigDecimal.ROUND_HALF_UP);
	        		baidu.setSettlementPriceDay(settlementPriceDay);
	        		//绩效奖励金额折算为天数=ROUND(合计给我司结算金额/全通日结算单价,2)
	        		BigDecimal meritPayDays= baidu.getSettlementPay();
	        		meritPayDays=meritPayDays.divide(baidu.getSettlementPriceDay(),2,BigDecimal.ROUND_HALF_UP);
	        		baidu.setMeritPayDays(meritPayDays);
	        		//记录上报全通考勤总天数=ROUND(应出勤天数+超出小时折算全通给我司结算为天数 +折算全通给我司结算为天数+绩效奖励金额折算为天数,2)
	        		BigDecimal settlementFinalDays = new BigDecimal(""+attendanceDays);
	        		settlementFinalDays = settlementFinalDays.add(overstepDays);
	        		settlementFinalDays=settlementFinalDays.add(overtimeSettleDays);
	        		settlementFinalDays=settlementFinalDays.add(meritPayDays).setScale(2,BigDecimal.ROUND_HALF_UP);
	        		baidu.setSettlementFinalDays(settlementFinalDays);
	        	}
	        	
	        }
	        baidu.setIsDel(1);
	        baidu.setCreateTime(new Date());
	        Integer baiduId= checkWorkBaiduDAO.save(baidu);
	        if(baiduId>0){
	        	//百度考勤入库成功,报错考勤日明细;
	        	for (CheckWorkBaiduDetail detail : baidu.getBaiduDetails()) {
	        		detail.setCheckWorkId(baiduId);
	        		detail.setIsDel(1);
	        		detail.setCreateTime(new Date());
//	        		checkWorkBaiduDetailDAO.save(detail);
	        	}
	        	checkWorkBaiduDetailDAO.save(baidu.getBaiduDetails());
	        }
		}
		result = 1;
		return result;
	}
}
