package hr.manage.component.checkwork.service.impl;

import hr.manage.component.checkwork.dao.CheckWorkBaiduDAO;
import hr.manage.component.checkwork.dao.CheckWorkBaiduDetailDAO;
import hr.manage.component.checkwork.dao.CheckWorkCurrentDAO;
import hr.manage.component.checkwork.dao.CheckWorkCurrentLogDAO;
import hr.manage.component.checkwork.dao.CheckWorkDetailDAO;
import hr.manage.component.checkwork.model.CheckWorkBaidu;
import hr.manage.component.checkwork.model.CheckWorkBaiduDetail;
import hr.manage.component.checkwork.model.CheckWorkCurrent;
import hr.manage.component.checkwork.model.CheckWorkCurrentLog;
import hr.manage.component.checkwork.model.CheckWorkDetail;
import hr.manage.component.checkwork.model.CheckWorkDetailCondition;
import hr.manage.component.checkwork.service.CheckWorkService;

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
				//根据考勤并更记录恢复年假和加班数据
				//获取最新一条变更记录
				CheckWorkCurrentLog curLog = checkWorkCurrentLogDAO.getCheckWorkCurrentLogByName(checkWork.getName(),checkWork.getTerm());
				if(curLog!=null){
					CheckWorkCurrent curCurrent = checkWorkCurrentDAO.getCheckWorkCurrentByNameTerm(checkWork.getName(),checkWork.getTerm().substring(0, 4));
					if(curCurrent!=null){
						//恢复加班和年假天数
						curCurrent.setSurplusOvertimeHours(curLog.getSurplusOvertimeHours());
						curCurrent.setSurplusAnnualLeave(curLog.getSurplusAnnualLeave());
						curCurrent.setUpdateTime(new Date());
						checkWorkCurrentDAO.update(curCurrent);
					}
					//删除变更日志记录
					checkWorkCurrentLogDAO.deleteCheckWorkCurrentLogByName(checkWork.getName(),checkWork.getTerm());
					
				}
			}
		    //判断考勤年度是否有当前剩余年假和加班小时数数据
		    CheckWorkCurrent checkWorkCurrent = checkWorkCurrentDAO.getCheckWorkCurrentByNameTerm(checkWork.getName(),checkWork.getTerm().substring(0, 4));
		    if(checkWorkCurrent == null){
		    	//初始化当前剩余年假和加班小时数
		    	//没有数据，已excel中的数据为准，不进行计算；
			    CheckWorkCurrent current = new CheckWorkCurrent();
			    current.setPersonalInfoId(checkWork.getPersonalInfo().getId());
			    current.setName(checkWork.getName());
			    current.setTerm(checkWork.getTerm().substring(0, 4));
			    current.setSurplusOvertimeHours(checkWork.getSurplusOvertimeHours());
			    current.setAnnualLeaveDays(checkWork.getAnnualLeaveDays());
			    current.setSurplusAnnualLeave(checkWork.getSurplusAnnualLeave());
			    current.setSickLeaveDays(checkWork.getSickLeaveDays());
			    current.setCompassionateLeaveDays(checkWork.getCompassionateLeaveDays());
			    current.setIsDel(1);
			    current.setCreateTime(new Date());
			    checkWorkCurrentDAO.save(current);
			    
			    //保存加班和年假数据变更记录;记录变更前的数据,以便回退;
			    CheckWorkCurrentLog log = new CheckWorkCurrentLog();
			    log.setPersonalInfoId(checkWork.getPersonalInfo().getId());
			    log.setName(checkWork.getName());
			    log.setTerm(checkWork.getTerm());
			    //原有没有数据，默认0
			    log.setSurplusOvertimeHours(0);
			    log.setSurplusAnnualLeave(BigDecimal.ZERO);
			    log.setIsDel(1);
			    log.setCreateTime(new Date());
			    checkWorkCurrentLogDAO.save(log);
			    BigDecimal addOverTime =checkWork.getOvertimeDays().subtract(checkWork.getLeaveDays());
				int r =addOverTime.compareTo(BigDecimal.ZERO);
				//加班大于请假
				if(r>=0){
		    		 //记录到当月考勤扣款
					checkWork.setSettlementDays(BigDecimal.ZERO);
		    	}
		    	else{
		    		//请假大于加班,需要比较年假
		    		BigDecimal settleDays=addOverTime.add(checkWork.getAnnualLeaveDays());
		    		int sr =settleDays.compareTo(BigDecimal.ZERO);
		    		if(sr>=0){
		    			//年假可以抵扣缺勤
						checkWork.setSettlementDays(BigDecimal.ZERO);
		    		}
		    		else{
		    			//年假不能抵扣缺勤记录到考勤欠款
		    			checkWork.setSettlementDays(settleDays.abs());
		    		}
		    		
		    	}
			   
		    }
		    else{
		    	//当前有数据,进行变更增减
		    	//保存加班和年假数据变更记录;记录变更前的数据,以便回退;
		    	CheckWorkCurrentLog log = new CheckWorkCurrentLog();
			    log.setPersonalInfoId(checkWork.getPersonalInfo().getId());
			    log.setName(checkWork.getName());
			    log.setTerm(checkWork.getTerm());
			    //记录原有的加班小时数和年假天数,以便回退
			    log.setSurplusOvertimeHours(checkWorkCurrent.getSurplusOvertimeHours());
			    log.setSurplusAnnualLeave(checkWorkCurrent.getSurplusAnnualLeave());
			    log.setIsDel(1);
			    log.setCreateTime(new Date());
			    checkWorkCurrentLogDAO.save(log);
			    
		    	checkWorkCurrent.setSickLeaveDays(checkWork.getSickLeaveDays());
		    	checkWorkCurrent.setCompassionateLeaveDays(checkWork.getCompassionateLeaveDays());
		    	checkWorkCurrent.setUpdateTime(new Date());
		    	// 本月是否有新增加班小时数=加班天数-请假天数
				BigDecimal addOverTime =checkWork.getOvertimeDays().subtract(checkWork.getLeaveDays());
				int r =addOverTime.compareTo(BigDecimal.ZERO);
		    	//加班大于请假
				if(r>=0){
					//调整后剩余加班小时数=新增加班天数*8+原有加班小时数
					int newOverTime =addOverTime.multiply(new BigDecimal('8')).intValue()+checkWorkCurrent.getSurplusOvertimeHours();
					checkWorkCurrent.setSurplusOvertimeHours(newOverTime);
				}
				else
				{
					//加班小于请假
					//比较变更的缺勤天数*8与加班小时数，是否可以完全抵消
					int subOverTime = checkWorkCurrent.getSurplusOvertimeHours() - addOverTime.multiply(new BigDecimal('8')).intValue();
					if(subOverTime>=0){
						checkWorkCurrent.setSurplusOvertimeHours(subOverTime);
					}
					else{
						//加班不够抵扣，需要抵抗年假;加班清0,扣减年假
						checkWorkCurrent.setSurplusOvertimeHours(0);
						BigDecimal subLeaveDays=checkWorkCurrent.getSurplusAnnualLeave().subtract(new BigDecimal(subOverTime).divide(new BigDecimal(8)).setScale(1));
						int lr = subLeaveDays.compareTo(BigDecimal.ZERO);
						//剩余年假够抵扣
						if(lr>=0){
							checkWorkCurrent.setSurplusAnnualLeave(subLeaveDays);
						}
						else{
							//lr=-1 不够抵扣,需记录到考勤扣款;剩余年假清0
							checkWorkCurrent.setSurplusAnnualLeave(BigDecimal.ZERO);
							//记录到当月考勤扣款
							checkWork.setSettlementDays(subLeaveDays.abs());
						}
						
					}
				}
				//保存变更后的当前加班小时数和剩余年假数
		    	checkWorkCurrentDAO.update(checkWorkCurrent);
		    } 
		    
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
		List<CheckWorkBaiduDetail> baiduDetails = checkWorkBaiduDetailDAO.getCheckWorkBaiduDetailByBaiduId(baiduId);
		baidu.setBaiduDetails(baiduDetails);
		return baidu;
	}
	@Override
	public  int updateCheckWorkBaidu(CheckWorkBaidu baidu){
		int result =0;
		if(checkWorkBaiduDAO.update(baidu)){
			result =1;
		}
		return result;
	}
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = { Exception.class })
	public  int saveCheckWorkBaiduListRecord( String term, List<CheckWorkBaidu> baiduList){
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
			//构造百度考勤	
			baidu.setTerm(term);
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
	        		/** 0:正常班；1：普通加班；2：周末加班；3：节假日加班  ；4：年假算正常班 ；5：病假算正常班；6病假不算上班*/
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
						checkWorkHours=checkWorkHours.add(detail.getWorkHours());
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
	        	int r =overstepHours.compareTo(BigDecimal.ZERO);
	        	if(r>=0){
	        		baidu.setOverstepHours(overstepHours);
	        		//=ROUND(超出小时数*1.5/应出勤*22,2)
		        	overstepDays=overstepDays.add(overstepHours);
		        	overstepDays=overstepDays.multiply(new BigDecimal("1.5"));
		        	overstepDays=overstepDays.divide(baidu.getAttendanceHours(),6,BigDecimal.ROUND_HALF_UP);
		        	overstepDays=overstepDays.multiply(new BigDecimal("22"));
		        	overstepDays=overstepDays.setScale(2, BigDecimal.ROUND_HALF_UP);
		        	baidu.setOverstepDays(overstepDays);
	        	}
	        	else{
	        		baidu.setOverstepHours(BigDecimal.ZERO);
	        		baidu.setOverstepDays(BigDecimal.ZERO);
	        	}
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
	        	overtimeSettleDays=overtimeSettleDays.multiply(new BigDecimal("22"));
	        	overtimeSettleDays=overtimeSettleDays.setScale(2, BigDecimal.ROUND_HALF_UP);
	        	baidu.setOvertimeSettleDays(overtimeSettleDays);
	        	//全通加班结算天数合计=超出小时折算全通给我司结算为天数+折算全通给我司结算为天数
	        	settlementDays=settlementDays.add(overstepDays);
	        	settlementDays=settlementDays.add(overtimeSettleDays);
	        	baidu.setSettlementDays(settlementDays);
	        	
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
