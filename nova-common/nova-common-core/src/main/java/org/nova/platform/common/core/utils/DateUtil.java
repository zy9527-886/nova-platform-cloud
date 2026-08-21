package org.nova.platform.common.core.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @类功能说明：日期相关的工具
 * @公司名称：Noname
 * @作者：Panda
 * @创建时间：2018-12-19 下午10:20:47
 * @版本：V1.0
 */
public class DateUtil
{
    public static final String F1 = "yyyy-MM-dd HH:mm:ss";
    public static final String F2 = "yyyyMMddHHmmss";
    public static final String F3 = "yyyy-MM-dd";
    public static final String F4 = "yyyyMMdd";
    public static final String F5 = "yyMMdd";
    public static final String F6 = "yyyy-MM-dd HH:mm";
    public static final String F7 = "yyyy/MM/dd HH:mm:ss";
    public static final String F8 = "yyyy/MM/dd HH:mm";
    public static final String F9 = "yyyy/MM/dd";

    /**
     * @函数功能:将日期格式从yyyyMMddHHmmss->yyyy-MM-dd HH:mm:ss
     * @author zy 2018-12-19
     * @param yyyyMMddHHmmss
     * @return
     * @throws ParseException
     * @return String
     * @throws
     */
    public static String F2TOF1(String yyyyMMddHHmmss) throws ParseException
    {
        SimpleDateFormat sdf = new SimpleDateFormat(F2);
        Date date = sdf.parse(yyyyMMddHHmmss);
        sdf.applyPattern(F1);
        return sdf.format(date);
    }

    public static String DateTOF2(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat(F2);
        return sdf.format(date);
    }
    public static String DateTOF1(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat(F1);
        return sdf.format(date);
    }
    public static String DateTOF3(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat(F3);
        return sdf.format(date);
    }
    public static String DateTOF4(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat(F4);
        return sdf.format(date);
    }

    /**
     * @函数功能:将日期格式从yyyy-MM-dd HH:mm:ss->yyyyMMddHHmmss
     * @author zy 2018-12-19
     * @param yyyy_MM_dd_HH_mm_ss
     * @return
     * @throws ParseException
     * @return String
     * @throws
     */
    public static String F1TOF2(String yyyy_MM_dd_HH_mm_ss) throws ParseException
    {
        SimpleDateFormat sdf = new SimpleDateFormat(F1);
        Date date = sdf.parse(yyyy_MM_dd_HH_mm_ss);
        sdf.applyPattern(F2);
        return sdf.format(date);
    }

    /**
     * @函数功能:String转换成Date
     * @author zy 2018-12-19
     * @param date
     * @param pattern
     * @return
     * @throws ParseException
     * @return Date
     * @throws
     */
    public static Date StringToDate(String date, String pattern)
    {
    	try{
    		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        	return sdf.parse(date);
    	}catch(Exception e){
    		return null;
    	}
    }

    /**
     * @函数功能:Date转换成String
     * @author zy 2018-12-19
     * @param date
     * @param pattern
     * @return
     * @return String
     * @throws
     */
    public static String DateToString(Date date, String pattern)
    {
        if (date == null)
        {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern(pattern);
        return sdf.format(date);
    }

    /**
     * @函数功能:时间的前或后偏移offset天的Date值
     * @author zy 2018-12-19
     * @param date
     * @param offset
     * @return
     * @return Date
     * @throws
     */
    public static Date offsetDay(Date date, int offset)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_YEAR, (calendar.get(Calendar.DAY_OF_YEAR) + offset));
        return calendar.getTime();
    }

    /**
     * @函数功能:时间的前或后偏移offset小时的Date值
     * @author zy 2018-12-19
     * @param date
     * @param offset
     * @return
     * @return Date
     * @throws
     */
    public static Date offsetHour(Date date, int offset)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, (calendar.get(Calendar.HOUR_OF_DAY) + offset));
        return calendar.getTime();
    }

    /**
     * @函数功能:时间的前或后偏移offset分钟的Date值
     * @author zy 2018-12-19
     * @param date
     * @param offset
     * @return
     * @return Date
     * @throws
     */
    public static Date offsetMinute(Date date, int offset)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MINUTE, (calendar.get(Calendar.MINUTE) + offset));
        return calendar.getTime();
    }

    /**
     * @函数功能:时间的前或后偏移offset秒的Date值
     * @author zy 2018-12-19
     * @param date
     * @param offset
     * @return
     * @return Date
     * @throws
     */
    public static Date offsetSecond(Date date, int offset)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.SECOND, (calendar.get(Calendar.SECOND) + offset));
        return calendar.getTime();
    }

    /**
     * @函数功能:取时间Date日期的00:00:00
     * @author zy 2018-12-19
     * @param date
     * @return
     * @return Date
     * @throws
     */
    public static Date getDayBegin(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * @函数功能:取时间Date日期的23:59:59
     * @author zy 2018-12-19
     * @param date
     * @return
     * @return Date
     * @throws
     */
    public static Date getDayEnd(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }

    /**
     * @函数功能：取Calendar日历的第一天
     * @作者：Miner
     * @创建时间：2018-12-19 下午02:09:10
     * @param date
     * @return
     */
    public static Date getFirstDateOfWeek(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int index = calendar.get(Calendar.DAY_OF_WEEK);
        if (index == 1)
        {
            index = 8;
        }
        calendar.add(Calendar.DATE, -(index - 2));
        return calendar.getTime();
    }

    /**
     * @函数功能：取Calendar日历的最后一天
     * @作者：Miner
     * @创建时间：2018-12-19 下午02:09:21
     * @param date
     * @return
     */
    public static Date getLastDateOfWeek(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int index = calendar.get(Calendar.DAY_OF_WEEK);
        if (index == 1)
        {
            index = 8;
        }
        calendar.add(Calendar.DATE, -(index - 2));
        calendar.add(Calendar.DATE, 6);
        return calendar.getTime();
    }

    /**
     * @函数功能:两时间想减得出差距的多少年，多少月，多少日，多少小时，多少分（不弄了）
     * @author zy 2018-12-19
     * @param minuend
     * @param subtrahend
     * @return void
     */
    public static void subtract(Date minuend, Date subtrahend)
    {
        Calendar calendarMinuend = Calendar.getInstance();
        calendarMinuend.setTime(minuend);
        Calendar calendarSubtrahend = Calendar.getInstance();
        calendarSubtrahend.setTime(subtrahend);
    }

    /**
     * @Description:获取
     * @return Date
     */
    public static Date getDayStartTime(){
    	Calendar currentDate = new GregorianCalendar();
    	currentDate.set(Calendar.HOUR_OF_DAY, 0);
    	currentDate.set(Calendar.MINUTE, 0);
    	currentDate.set(Calendar.SECOND, 0);
    	return (Date)currentDate.getTime().clone();
    }
    public static Date getWeekStartTime(){
    	Calendar currentDate = new GregorianCalendar();
    	currentDate.setFirstDayOfWeek(Calendar.MONDAY);
    	currentDate.set(Calendar.HOUR_OF_DAY, 0);
    	currentDate.set(Calendar.MINUTE, 0);
    	currentDate.set(Calendar.SECOND, 0);
    	currentDate.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
    	return (Date)currentDate.getTime().clone();
    }
    public static Date getMonthStartTime(){
    	Calendar currentDate = new GregorianCalendar();
    	currentDate.set(Calendar.HOUR_OF_DAY, 0);
    	currentDate.set(Calendar.MINUTE, 0);
    	currentDate.set(Calendar.SECOND, 0);
    	currentDate.set(Calendar.DAY_OF_MONTH, currentDate.getActualMinimum(Calendar.DAY_OF_MONTH));
    	return (Date)currentDate.getTime().clone();
    }
}
