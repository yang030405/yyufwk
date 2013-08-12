package com.yyu.fwk.util;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DateUtil {

	protected static Log logger = LogFactory.getLog(DateUtil.class);

	public static String getYesterday() {
		return getDate(-1);
	}

	public static String getToday() {
		return getDate(0);
	}

	public static String getDate(int days) {
		Calendar currentTime = Calendar.getInstance();
		currentTime.add(Calendar.DATE, days);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(currentTime.getTime());
	}

	public static Date getDate(Date date, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, days);
		return cal.getTime();
	}

	public static Date addSecond(Date date, int seconds) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.SECOND, seconds);
		return cal.getTime();
	}

	public static String getIntegralHourStr(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		Calendar ca = Calendar.getInstance();
		ca.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE), cal.get(Calendar.HOUR_OF_DAY), 0, 0);
		return DateUtil.getDateTime(ca.getTime());
	}

	public static Date getDate(String dateStr) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return sdf.parse(dateStr);
		}
		catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getDate(Date date) {
		return formatDate(date, "yyyy-MM-dd");
	}

	public static String getDateTime(Date date) {
		return formatDate(date, "yyyy-MM-dd HH:mm:ss");
	}
	
	public static String formatDate(Date date, String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	// 把20101124转换为2010-11-24
	public static String convertDate(String str) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		return sdf1.format(sdf.parse(str));
	}

	// 把2010-11-24转换为20101124
	public static String convertToDate(String str) throws ParseException {
		if (str == null)
			return null;
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return sdf.format(sdf1.parse(str));
	}

	public static List<String> getAllDates(String startDate, String endDate) {
		try {
			List<String> list = new ArrayList<String>();
			Date sDate = null;
			Date eDate = null;
			if (BeanUtil.isBlank(startDate)) {
				sDate = new Date();
			}
			else {
				sDate = DateUtils.parseDate(startDate, new String[] { "yyyy-MM-dd" });
			}
			if (BeanUtil.isBlank(endDate)) {
				eDate = new Date();
			}
			else {
				eDate = DateUtils.parseDate(endDate, new String[] { "yyyy-MM-dd" });
			}
			
			if(sDate.after(eDate)) return list;
			
			while (!DateUtils.isSameDay(sDate, eDate)) {
				list.add(DateFormatUtils.format(sDate, "yyyy-MM-dd"));
				sDate = DateUtils.addDays(sDate, 1);
			}

			list.add(endDate);

			return list;
		}
		catch (ParseException e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * 将短时间格式字符串转换为时间 yyyy-MM-dd
	 * 
	 * @param strDate
	 * @return
	 */
	public static Date strToDate(String strDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}

	/**
	 * 将长时间格式时间转换为字符串 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param dateDate
	 * @return
	 */
	public static String dateToStrLong(java.util.Date dateDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(dateDate);
		return dateString;
	}

	/**
	 * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param dateDate
	 * @return
	 */
	public static Date strLongToDate(String strDate) throws ParseException{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = formatter.parse(strDate);
		return date;
	}
	
	/**
	 * 将短时间格式时间转换为字符串 yyyy-MM-dd
	 * 
	 * @param dateDate
	 * @param k
	 * @return
	 */
	public static String dateToStr(java.util.Date dateDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(dateDate);
		return dateString;
	}

	public static String parseDate(String strDate) {
		// 将2011/4/2转化为2011-04-02的格式
		strDate = strDate.replaceAll("(\\d{4})\\/(\\d{1})\\/", "$1-0$2-").replaceAll("(\\d{4}\\-\\d{2})\\/(\\d{1}$)", "$1-0$2");
		// 将2011-4-2转化为2011-04-02的格式
		strDate = strDate.replaceAll("(\\d{4})\\-(\\d{1})\\-", "$1-0$2-").replaceAll("(\\d{4}\\-\\d{2})\\-(\\d{1}$)", "$1-0$2");
		return strDate;
	}

	/* 把带汉字的2009年8月7日转化为2009-8-7 */
	public static String dateReplace(String startDate) {
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		String year = String.valueOf(gc.get(Calendar.YEAR));
		startDate = startDate.replaceAll("日", "");
		startDate = startDate.replaceAll("月", "-");
		startDate = startDate.replaceAll("年", "-");
		if (startDate.split("-").length < 3) {
			startDate = year + "-" + startDate;
		}
		return startDate;
	}

	// 得到一个时间延后或前移几天的时间,nowdate为时间,delay为前移或后延的天数
	public static String getNextDay(String nowdate, String delay) {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String mdate = "";
			Date d = strToDate(nowdate);
			long myTime = (d.getTime() / 1000) + Integer.parseInt(delay) * 24 * 60 * 60;
			d.setTime(myTime * 1000);
			mdate = format.format(d);
			return mdate;
		}
		catch (Exception e) {
			return "";
		}
	}

	// 得到二个日期间的间隔天数
	public static String getTwoDay(String sj1, String sj2) {
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		long day = 0;
		try {
			java.util.Date date = myFormatter.parse(sj1);
			java.util.Date mydate = myFormatter.parse(sj2);
			day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
		}
		catch (Exception e) {
			return "";
		}
		return day + "";
	}

	/*
	 * 取传入日期的上一月份第一天
	 */
	public static Date getPreviousMonthFirstDay(Date day){
		Calendar firstDayOfMonth = Calendar.getInstance();
		firstDayOfMonth.setTime(day);
		
		firstDayOfMonth.add(Calendar.MONTH, -1);
		firstDayOfMonth.set(Calendar.DAY_OF_MONTH,1);
		firstDayOfMonth.set(Calendar.HOUR_OF_DAY, 0);
		firstDayOfMonth.set(Calendar.MINUTE, 0);
		firstDayOfMonth.set(Calendar.SECOND, 0);
		
		return firstDayOfMonth.getTime();
	}

	public static Date getThisMonthLastDay(Date day){
		Calendar lastDayOfMonth = Calendar.getInstance();
		lastDayOfMonth.setTime(day);
		//取下一个月的第一天往回减一天
		lastDayOfMonth.add(Calendar.MONTH,1);
		lastDayOfMonth.set(Calendar.DAY_OF_MONTH, 1);
		lastDayOfMonth.add(Calendar.DAY_OF_YEAR, -1);
		
		return lastDayOfMonth.getTime();
	}

	public static List<Date> getMonthFirstDays(Date startDate, Date endDate){
		if(endDate != null && !startDate.before(endDate)){
			throw new RuntimeException("startDate must before endDate.");
		}
		List<Date> monthFirstDayList = new ArrayList<Date>();
		Date previousMonthFirstDay = getPreviousMonthFirstDay(endDate);
		
		for(;!previousMonthFirstDay.before(startDate); previousMonthFirstDay = getPreviousMonthFirstDay(previousMonthFirstDay)){
			monthFirstDayList.add(previousMonthFirstDay);
		}
		
		return monthFirstDayList;
	}

	/*
	 * 取传入日期的上一个星期一
	 */
	public static Date getPreviousMonday(Date day){
		Calendar previousMonday = Calendar.getInstance();
		previousMonday.setTime(day);
		previousMonday.set(Calendar.HOUR_OF_DAY, 0);
		previousMonday.set(Calendar.MINUTE, 0);
		previousMonday.set(Calendar.SECOND, 0);
		previousMonday.add(Calendar.DATE, -1);
		previousMonday.add(Calendar.WEEK_OF_MONTH, -1);
		previousMonday.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return previousMonday.getTime();
	}

	/*
	 * 输入日期所在周的下个周的周一及所有在当前日期所在周的上个周的周一和中间的所有周一。
	 * eg:
	 * Date date = new Date(2012 - 1900, 11 - 1, 6); // 2012-11-06
	 * System.out.println(getMondays(date));
	 * [Mon Nov 19 00:00:00 CST 2012, Mon Nov 12 00:00:00 CST 2012]
	 */
	public static List<Date> getMondays(Date startDate, Date endDate){
		if(endDate != null && !startDate.before(endDate)){
			throw new RuntimeException("startDate must before endDate.");
		}
		List<Date> mondayList = new ArrayList<Date>();
		Date previousMonday = getPreviousMonday(endDate);
		
		for(;!previousMonday.before(startDate); previousMonday = getPreviousMonday(previousMonday)){
			mondayList.add(previousMonday);
		}
		
		return mondayList;
	}
}
