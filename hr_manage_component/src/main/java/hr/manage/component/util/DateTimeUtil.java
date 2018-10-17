package hr.manage.component.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTimeUtil {

	/**
	 * 通过时间秒毫秒数判断两个时间的间隔
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int differentDaysByMillisecond(Date date1, Date date2) {
		int days = (int) ((date2.getTime() - date1.getTime()) / (1000 * 3600 * 24));
		return days;
	}

	// 计算两个日期相差年数
	public static int yearDateDiff(Date startDate, Date endDate) {
		Calendar calBegin = Calendar.getInstance(); // 获取日历实例
		Calendar calEnd = Calendar.getInstance();
		calBegin.setTime(startDate); // 字符串按照指定格式转化为日期
		calEnd.setTime(endDate);
		return calEnd.get(Calendar.YEAR) - calBegin.get(Calendar.YEAR);
	}

	//获取两个时间中的工作日天数
	public static int getDutyDays(Date startDate, Date endDate) {
//		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//		Date startDate = null;
//		Date endDate = null;
//
//		try {
//			startDate = df.parse(strStartDate);
//			endDate = df.parse(strEndDate);
//		} catch (ParseException e) {
//			System.out.println("非法的日期格式,无法进行转换");
//			e.printStackTrace();
//		}
		int result = 0;
		while (startDate.compareTo(endDate) <= 0) {
			if (startDate.getDay() != 6 && startDate.getDay() != 0)
				result++;
			startDate.setDate(startDate.getDate() + 1);
		}

		return result;
	}
	
	//获取两个时间中的工作日天数
	public static int getDay(Date curDate) {
	  Calendar cal = Calendar.getInstance(); 
	  cal.setTime(curDate); 
	  int day=cal.get(Calendar.DATE);//获取日 
	  return day;
	} 
	
}
