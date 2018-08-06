package hr.manage.component.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DateTimeUtil {
	
	/**
     * 通过时间秒毫秒数判断两个时间的间隔
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDaysByMillisecond(Date date1,Date date2)
    {
        int days = (int) ((date2.getTime() - date1.getTime()) / (1000*3600*24));
        return days;
    }
	
    
    //计算两个日期相差年数
    public static int yearDateDiff(Date startDate,Date endDate){
       Calendar calBegin = Calendar.getInstance(); //获取日历实例
       Calendar calEnd = Calendar.getInstance();
       calBegin.setTime(startDate); //字符串按照指定格式转化为日期
       calEnd.setTime(endDate);
       return calEnd.get(Calendar.YEAR) - calBegin.get(Calendar.YEAR);
    }
    
    
}
