package com.jfinteck.dmq.core.utils;

import lombok.extern.slf4j.Slf4j;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@Slf4j
public class DateUtil {

    private static String datePattern = "yyyy-MM-dd";
    
    private static String datePattern_YYYYMMdd = "yyyyMMdd";

    private static String dateTimePattern = "yyyy-MM-dd HH:mm:ss";

    private static String timePattern = "HH:mm";
    
    private static String datePattern_HHmmss = "HHmmss";
    
    private final static DateFormat FORMAT_YYYYMMddHHmmss = new SimpleDateFormat("yyyyMMddHHmmss");
    
    private final static DateFormat FORMAT_YYYYMMddHHmmssSSS = new SimpleDateFormat("yyyyMMddHHmmssSS");
    
    private final static DateFormat FORMAT_YYYYMMdd = new SimpleDateFormat("yyyyMMdd");

    public final static DateFormat FORMAT_HHMMSS = new SimpleDateFormat("HHmmss");
    
    public final static DateFormat FORMAT_HH = new SimpleDateFormat("HH");
    
    public final static DateFormat FORMAT_YYYY_MM_DD_HHmmss = new SimpleDateFormat(dateTimePattern);

    /**
     * Return 缺省的日期格式 (yyyy/MM/dd)
     *
     * @return 在页面中显示的日期格式
     */
    public static String getDatePattern() {
        return datePattern;
    }

    /**
     * 根据日期格式，返回日期按datePattern格式转换后的字符串
     *
     * @param aDate
     *            日期对象
     * @return 格式化后的日期的页面显示字符串
     */
    public static final String getDate(Date aDate) {
        SimpleDateFormat df = null;
        String returnValue = "";

        if (aDate != null) {
            df = new SimpleDateFormat(datePattern);
            returnValue = df.format(aDate);
        }

        return (returnValue);
    }
    
    
    /**
     * 减日期时间转为毫秒
     * @return
     */
    public static Date dateConvert(String dateTime){
         Date date=null;
        try {
            date = FORMAT_YYYYMMddHHmmss.parse(dateTime);
            System.out.println(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
    
    /**
     * 减日期时间转为毫秒
     * @return
     */
    public static Date dateConvert_YYYYMMdd(String dateTime){
         Date date=null;
        try {
            date = FORMAT_YYYYMMdd.parse(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
    
    /**
     * 减日期时间转为毫秒
     * @return
     */
    public static long dateConvertToms(String dateTime){
        return dateConvert(dateTime).getTime();
    }

    /**
     * 根据日期格式，返回日期按datePattern格式转换后的字符串
     *
     * @param aDate
     *            日期对象
     * @return 格式化后的日期的页面显示字符串
     */
    public static final String getDateTime(Date aDate) {
        SimpleDateFormat df = null;
        String returnValue = "";

        if (aDate != null) {
            df = new SimpleDateFormat(dateTimePattern);
            returnValue = df.format(aDate);
        }

        return (returnValue);
    }

    /**
     * 按照日期格式，将字符串解析为日期对象
     *
     * @param aMask
     *            输入字符串的格式
     * @param strDate
     *            一个按aMask格式排列的日期的字符串描述
     * @return Date 对象
     * @see SimpleDateFormat
     */
    public static final Date convertStringToDate(String aMask, String strDate){
        SimpleDateFormat df = null;
        Date date = null;
        df = new SimpleDateFormat(aMask);

        if (log.isDebugEnabled()) {
            log.debug("converting '" + strDate + "' to date with mask '"
                    + aMask + "'");
        }

        try {
            date = df.parse(strDate);
        } catch (ParseException pe) {
            log.error("ParseException: " + pe);
            //throw new ParseException(pe.getMessage(), pe.getErrorOffset());
        }

        return (date);
    }

    /**
     * This method returns the current date time in the format: yyyy/MM/dd HH:MM
     * a
     *
     * @param theTime
     *            the current time
     * @return the current date/time
     */
    public static String getTimeNow(Date theTime) {
        return getDateTime(timePattern, theTime);
    }

    /**
     * This method returns the current date in the format: yyyy-MM-dd
     *
     * @return the current date
     * @throws ParseException
     */
    public static Calendar getToday() throws ParseException {
        Date today = new Date();
        SimpleDateFormat df = new SimpleDateFormat(datePattern);

        // This seems like quite a hack (date -> string -> date),
        // but it works ;-)
        String todayAsString = df.format(today);
        Calendar cal = new GregorianCalendar();
        cal.setTime(convertStringToDate(todayAsString));

        return cal;
    }

    /**
     * This method generates a string representation of a date's date/time in
     * the format you specify on input
     *
     * @param aMask
     *            the date pattern the string is in
     * @param aDate
     *            a date object
     * @return a formatted string representation of the date
     *
     * @see SimpleDateFormat
     */
    public static final String getDateTime(String aMask, Date aDate) {
        SimpleDateFormat df = null;
        String returnValue = "";

        if (aDate == null) {
            log.error("aDate is null!");
        } else {
            df = new SimpleDateFormat(aMask);
            returnValue = df.format(aDate);
        }

        return (returnValue);
    }

    /**
     * 根据日期格式，返回日期按datePattern格式转换后的字符串
     *
     * @param aDate
     * @return
     */
    public static final String convertDateToString(Date aDate) {
        return getDateTime(datePattern, aDate);
    }
    
    /**
     * 根据日期格式，返回日期按dateTimePattern格式转换后的字符串
     *
     * @param aDate
     * @return
     */
    public static final String convertDateTimeToString(Date aDate) {
        return getDateTime(dateTimePattern, aDate);
    }

    /**
     * 按照日期格式，将字符串解析为日期对象
     *
     * @param strDate
     *            (格式 yyyyMMdd)
     * @return
     *
     * @throws ParseException
     */
    public static Date convertYYYYMMDDToDate(String strDate){
        Date aDate = null;
        if (log.isDebugEnabled()) {
            log.debug("converting date with pattern: " +    datePattern_YYYYMMdd);
        }
        aDate = convertStringToDate(    datePattern_YYYYMMdd, strDate);
        return aDate;
    }
    
    
    
    /**
     * 按照日期格式，将字符串解析为日期对象
     *
     * @param strDate
     *            (格式 yyyy-MM-dd)
     * @return
     *
     * @throws ParseException
     */
    public static Date convertStringToDate(String strDate){
        Date aDate = null;

        if (log.isDebugEnabled()) {
            log.debug("converting date with pattern: " + datePattern);
        }

        aDate = convertStringToDate(datePattern, strDate);

        return aDate;
    }

    /**
     * 按照日期格式，将字符串解析为日期对象
     *
     * @param strDate
     *            (格式 yyyy-MM-dd HH:mm:ss)
     * @return
     *
     * @throws ParseException
     */
    public static Date convertTimeStringToDate(String strDate){
        Date aDate = null;

        if (log.isDebugEnabled()) {
            log.debug("converting date with pattern: " + dateTimePattern);
        }

        aDate = convertStringToDate(dateTimePattern, strDate);

        return aDate;
    }

    /**
     * 时间相加
     *
     * @param date
     * @param day
     * @return
     */
    public static Date dateAdd(Date date, int day) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, day);
        return calendar.getTime();
    }

    /**
     * 获取两个日期之间的天数
     *
     * @param date1
     * @param date2
     * @return
     */
    public static long dateDiffer(Date date1, Date date2) {
        return (date1.getTime() - date2.getTime()) / (1000 * 3600 * 24);
    }
    /**
     * 得到两个日期之间的天数差，包括开始和结束日期(如：beginCalender=2007-10-01，endCalendar=2007-10-20
     * 结果为：20)
     * 
     * @param beginCalender
     *            开始日期(小的)
     * @param endCalendar
     *            结束日期(大的)
     * @return
     */
    public static Long getDifferenceDays(Date beginDay, Date endDay) {
        Calendar beginCalender = Calendar.getInstance();
        beginCalender.setTime(beginDay);

        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(endDay);

        Long days = (endCalendar.getTimeInMillis() - beginCalender
                .getTimeInMillis())
                / (24 * 60 * 60 * 1000);
        days = days + 1;
        return days;
    }
    /**
     * 得到两个日期之间的天数差，包括开始和结束日期(如：beginCalender=2007-10-01，endCalendar=2007-10-20
     * 结果为：20)
     * 
     * @param beginCalender
     *            开始日期(小的)
     * @param endCalendar
     *            结束日期(大的)
     * @return
     */
    public static Long getDifferenceDaysByStringDate(String beginDate, String endDate) {
        SimpleDateFormat sdf = new SimpleDateFormat(datePattern_YYYYMMdd);
        Date beginDay = null;
        Date endDay = null;
        try {
            beginDay = sdf.parse(beginDate);
            endDay = sdf.parse(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar beginCalender = Calendar.getInstance();
        beginCalender.setTime(beginDay);

        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(endDay);

        Long days = (endCalendar.getTimeInMillis() - beginCalender
                .getTimeInMillis())
                / (24 * 60 * 60 * 1000);
//      days = days + 1;
        return days;
    }
    public static Date getFirstDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();
    }
    
    public static Date getLastDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal.getTime();
    }
    /**
     * 返回本月最后一天
     * @param date
     * @return
     */
    public static String getLastDayStr(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH,0);
        DateFormat format = new SimpleDateFormat(datePattern_YYYYMMdd);
        return format.format(cal.getTime());
    }
    /**
     * 返回日期String格式  yyyyMMdd
     * @param date
     * @return
     */
    public static String getDateStr(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        DateFormat format = new SimpleDateFormat(datePattern_YYYYMMdd);
        return format.format(cal.getTime());
    }
    
    /**
     * 返回日期String格式  自定义格式
     * @param date
     * @return
     */
    public static String getDateStrByFormat(Date date,String format) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        DateFormat formatObj = new SimpleDateFormat(format);
        return formatObj.format(cal.getTime());
    }
    
    /**
     * 返回本月第一天
     * @param date  日期
     * @param month  0 当月  1下月  -1  上月  以此类推 
     * @return
     */
    public static String getFirstDateStr(int month, Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH,1);
        cal.set(Calendar.MONTH, month);
        DateFormat format = new SimpleDateFormat(datePattern_YYYYMMdd);
        return format.format(cal.getTime());
    }
    
    /**
     * 得到当前时间,格式为yyyyMMddHHmmss
     *
     * @return
     */
    public static String getyyyyMMddHHmmssCurDate() {
        return FORMAT_YYYYMMddHHmmss.format(new Date());
    }
    /**
     * 得到当前时间,格式为yyyyMMddHHmmssSS
     *
     * @return
     */
    public static String getyyyyMMddHHmmssSSCurDate() {
        return FORMAT_YYYYMMddHHmmssSSS.format(new Date());
    }
    /**
     * date转换成calendar，只精确到yyyyMMdd
     * @throws ParseException 
     */
    public static Calendar getyyyyMMDDCalendar(Date date) throws ParseException{
            SimpleDateFormat dataFormat = new SimpleDateFormat(datePattern_YYYYMMdd);
            dataFormat.parse(dataFormat.format(date));
            return dataFormat.getCalendar();
    }
    /**
     * 获取日期毫秒数(精确到天)
     * @throws ParseException 
     */
    public static long getyyyyMMDDTimeInMillis(Date date) throws ParseException{
            return getyyyyMMDDCalendar(date).getTimeInMillis();
    }
    /**
     * 获取日期的HHmmss
     * @param date
     * @return
     */
    public static long getFormatHHmmss(Date date){
        return Long.valueOf(FORMAT_HHMMSS.format(date));
    }
    /**
     * 获取日期的HH
     * @param date
     * @return
     */
    public static long getFormatHH(Date date){
        return Long.valueOf(FORMAT_HH.format(date));
    }
    /**
     * 时间是否在<code>days</code>内
     * 
     * @param old
     * @param days
     * @return
     */
    public static boolean isDaysInterval(Date old, int days) {
        Calendar now = Calendar.getInstance();
        return (now.getTimeInMillis() - old.getTime()) <= (1000L * 3600 * 24 * days);
    }

    /**
     * 得到当前日期后的N天的日期
     * 
     * @param days
     *            天数
     * @return
     */
    public static Date getBackDaysOfNow(int days) {
        Calendar now = Calendar.getInstance();
        now.setTimeInMillis(now.getTimeInMillis() + 1000L * 3600 * 24 * days);
        return now.getTime();
    }

    public static Date getBackDaysOfDay(Date date,int days) {
        Calendar day = Calendar.getInstance();
        day.setTime(date);
        day.setTimeInMillis(day.getTimeInMillis() + 1000L * 3600 * 24 * days);
        return day.getTime();
    }

    public static Date getBeginOfDay(Date day) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(day);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            return calendar.getTime();
    }
    /**
     * 时间差比较
     * @param date 原时间点
     * @param diffSecond 时间差(秒)
     * @return
     */
    public static boolean dateTimeDiffCompare(Date date,int diffSecond){
        Date cur = new Date();
        long s = diffSecond*1000;
        long diff = date.getTime()+s;
        long curTime = cur.getTime();
        if(curTime>diff){
            return false;
        }else{
            return true;
        }
    }
    
    /**
     * 获取当前日期
     * @param aMask
     * @return
     */
    public static final String getCurrDateYYYYMMdd() {
        String returnValue = "";
        SimpleDateFormat df  = new SimpleDateFormat(datePattern_YYYYMMdd);
        returnValue = df.format(new Date());
        return (returnValue);
    }
    
    /**
     * 获取昨日日期
     * @param aMask
     * @return
     */
    public static final String getYesterDateYYYYMMdd(String dateTime) {
        Date date = dateConvert_YYYYMMdd(dateTime);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        String returnValue = "";
        SimpleDateFormat df  = new SimpleDateFormat(datePattern_YYYYMMdd);
        returnValue = df.format(calendar.getTime());
        return returnValue;
    }
    
    /**
     * 获取昨日日期
     * @param aMask
     * @return
     */
    public static final String getYesterDateYYYYMMdd() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        String returnValue = "";
        SimpleDateFormat df  = new SimpleDateFormat(datePattern_YYYYMMdd);
        returnValue = df.format(calendar.getTime());
        return returnValue;
    }
    
    
    /**
     * 获取当前时间
     * @param aMask
     * @return
     */
    public static final String getCurrTimeHHmmss() {
        String returnValue = "";
        SimpleDateFormat df  = new SimpleDateFormat(datePattern_HHmmss);
        returnValue = df.format(new Date());
        return (returnValue);
    }
    
    /**
     * 获取当前时间
     * @param aMask
     * @return
     */
    public static final String getCurrTimeHHmmss(Date date) {
        String returnValue = "";
        SimpleDateFormat df  = new SimpleDateFormat(datePattern_HHmmss);
        returnValue = df.format(date);
        return (returnValue);
    }
    
    
    /**
     * 计算放款时间
     * @param dateTime
     * @return
     */
    public static final Date calculateInsteadPayDate(Date nowDate,String dateTime){
        String[] timeArray = dateTime.split(":");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(nowDate);
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]));
        calendar.set(Calendar.SECOND, Integer.parseInt(timeArray[2]));
        Date returnDate = nowDate;
        if(nowDate.getTime() > calendar.getTime().getTime()){
            calendar.add(Calendar.DAY_OF_MONTH, 1);  //加 一天 
            returnDate = calendar.getTime();
        }
        return returnDate;
    }
    
    /**
     * 计算任小贷还款时间
     * @param nowDate
     * @param days
     * @param dateTime
     * @return
     */
    public static final String calculateRepaymentDate(Date nowDate,int days){
//      String[] timeArray = dateTime.split(":");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(nowDate);
//      calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]));
//      calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]));
//      calendar.set(Calendar.SECOND, Integer.parseInt(timeArray[2]));
//      if(nowDate.getTime() > calendar.getTime().getTime()){                   /* 放款日切时间点去除 */
//          days = days + 1; //加 一天 
//      }
        calendar.add(Calendar.DAY_OF_MONTH, days);  
        return getDateStr(calendar.getTime());
    }
    
    /**
     * 任分期计算还回还款时间
     * @param nowDate
     * @param days
     * @param dateTime
     * @return
     */
    public static final String rfqcalculateRepaymentDate(Date nowDate,int months,String dateTime){
        int days =0;
        String[] timeArray = dateTime.split(":");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(nowDate);
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]));
        calendar.set(Calendar.SECOND, Integer.parseInt(timeArray[2]));
        calendar.add(Calendar.MONTH, months);  
        if(nowDate.getTime() > calendar.getTime().getTime()){
            days = 1; //加 一天 
        }
        calendar.add(Calendar.DAY_OF_MONTH, days);  
        return getDateStr(calendar.getTime());
    }
    
    
    /**
     * 返回明天的日期
     * @return
     */
    public static final Date getTomorrowDate(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }
    
    /**
     * 返回明日日期
     * @return
     */
    public static final String getTomorrowDate_YYYYMMDD(){
        Date firstDay = getTomorrowDate();
        SimpleDateFormat df  = new SimpleDateFormat(datePattern_YYYYMMdd);
        return df.format(firstDay);
    }
    
    /**
     * 
     * @param date
     * @return
     */
    public static final String getDateStr(String date){
        return date.substring(0, 4) + "年" + date.substring(4,6) + "月" + date.substring(6);
    }
    /**
     * 后去现在时间前多少秒时间
     * @param BeforeSecond
     * @return String
     */
    public static final String getNowDateByBeforeSecond(int BeforeSecond){
        Calendar c = new GregorianCalendar();
        Date date = new Date();
        c.setTime(date);
        c.add(Calendar.SECOND,BeforeSecond);
        date=c.getTime();
        return FORMAT_YYYY_MM_DD_HHmmss.format(date);
    }
    /**
     * 后去现在时间前多少秒时间
     * @param BeforeSecond
     * @return String
     */
    public static final Date getNowDateByBeforeSecond1(int BeforeSecond){
        Calendar c = new GregorianCalendar();
        Date date = new Date();
        c.setTime(date);
        c.add(Calendar.SECOND,BeforeSecond);
        return c.getTime();
    }
    /**
     * 在日期上增加数个整月
     */
    public static String addMonth(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, n);
        return FORMAT_YYYYMMdd.format(cal.getTime());
    }
    
    public static Date setHHmmss(int hour,int minute,int second,Date curDate){
        Calendar startCal = Calendar.getInstance();
        startCal.setTime(curDate);
        startCal.set(Calendar.HOUR_OF_DAY, hour);
        startCal.set(Calendar.MINUTE, minute);
        startCal.set(Calendar.SECOND, second);
        return startCal.getTime();
    }

    /**
     * 返回日期String格式  yyyyMMdd
     * @param date
     * @return
     */
    public static String getHHmmss(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        DateFormat format = new SimpleDateFormat(datePattern_HHmmss);
        return format.format(cal.getTime());
    }
    
}
