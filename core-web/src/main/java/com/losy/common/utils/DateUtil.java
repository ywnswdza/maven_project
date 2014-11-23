package com.losy.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang.StringUtils;

/**
 * 日期工具类 2012-10-29
 * 
 * @author Administrator P
 * 
 */
public class DateUtil {

	/**
	 * 获取当天时间 【按传入参数格式返回】
	 */
	public static String getNowTime(String dateformat) {
		Date now = new Date();
		SimpleDateFormat dateFormat = null;
		String hehe = null;
		try {
			dateFormat = new SimpleDateFormat(dateformat);// 可以方便地修改日期格式
			hehe = dateFormat.format(now);
		} catch (Exception e) {
			dateFormat = new SimpleDateFormat("yyyy-MM-dd");// 可以方便地修改日期格式
			hehe = dateFormat.format(now);
		}
		return hehe;
	}

	/**
	 * 获取七天之前(以当前时间为参照)的时间 【按传入参数格式返回】
	 * 
	 * @return
	 */
	public static String getBefore7DaysTime(String dateformat) {
		Calendar cd = Calendar.getInstance();
		cd.add(Calendar.DATE, -7);
		return new SimpleDateFormat(dateformat).format(cd.getTime());
	}

	/**
	 * 获取指定时间指定日期 【按传入参数格式返回】
	 * 
	 * @return
	 */
	public static String getDaysTime(String dateformat, int day) {
		Calendar cd = Calendar.getInstance();
		cd.add(Calendar.DATE, day);
		return new SimpleDateFormat(dateformat).format(cd.getTime());
	}

	public static String getBeforeNDaysTime(int num, String date,
			String dateformat) {
		Calendar dayc1 = new GregorianCalendar();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		Date daystart;
		try {
			daystart = format.parse(date);
			dayc1.setTime(daystart);
		} catch (ParseException e) {

			e.printStackTrace();
		}
		dayc1.add(Calendar.DATE, -num);
		return new SimpleDateFormat(dateformat).format(dayc1.getTime());
	}

	/**
	 * 获取N天之前(以当前时间为参照)的时间
	 * 
	 * @param num
	 * @param dateformat
	 * @return
	 */
	public static String getBeforeNDaysTime(int num, String dateformat) {
		Calendar cd = Calendar.getInstance();
		cd.add(Calendar.DATE, -num);
		return new SimpleDateFormat(dateformat).format(cd.getTime());
	}

	/**
	 * 获取本月第一天的日期
	 * 
	 * @return
	 */
	public static String getFirstDayToCurMonth() {
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		String currentDate = format.format(date);
		StringBuffer sb = new StringBuffer(currentDate);
		sb.append("-01");
		return sb.toString();
	}
	
	
	/**
	 * 获取某月的第一天
	 * @param target,null表示当前月
	 * @return
	 */
	public static Date getFirstDayOfMonth(Date target){
		Calendar c = Calendar.getInstance();
		c.setTime(target != null ? target : new Date());
		c.set(Calendar.DAY_OF_MONTH,c.getActualMinimum(Calendar.DAY_OF_MONTH));
		return c.getTime();
	}
	
	/**
	 * 获取某月的最后一天
	 */
	public static Date getLastDayOfMonth(Date target){
		Calendar c = Calendar.getInstance();
		c.setTime(target != null ? target : new Date());
		c.set(Calendar.DAY_OF_MONTH,c.getActualMaximum(Calendar.DAY_OF_MONTH));
		return c.getTime();
	}
	

	/**
	 * 获取本月第一天的日期(给到秒)
	 * 
	 * @return
	 */
	public static String getFirstDayForTime() {
		String firTime = getFirstDayToCurMonth();
		StringBuffer str = new StringBuffer(firTime);
		str.append(" 00:00:00");
		return str.toString();
	}

	/**
	 * 获取昨天的日期
	 * 
	 * @return
	 */
	public static String getYesterDayDate() {
		Calendar cd = Calendar.getInstance();
		cd.add(Calendar.DATE, -1);
		return new SimpleDateFormat("yyyy-MM-dd").format(cd.getTime());
	}

	/**
	 * 获取昨天的日期(给到秒)
	 * 
	 * @return
	 */
	public static String getYesterDayTime() {
		String yesTime = getYesterDayDate();
		StringBuffer str = new StringBuffer(yesTime);
		str.append(" 23:59:59");
		return str.toString();
	}

	/**
	 * 返回指定日期的前一天日期
	 * 
	 * @param dateParam
	 * @return
	 */
	public static Date getYesterByDate(Date dateParam) {
		Calendar c = Calendar.getInstance();
		c.setTime(dateParam);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day - 1);
		return c.getTime();
	}

	/**
	 * 返回指定日期的前一天日期
	 * 
	 * @param dateParam
	 * @return
	 */
	public static String getYesterByDate(String dateParam) {
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = getYesterByDate(sd.parse(dateParam));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new SimpleDateFormat("yyyy-MM-dd").format(date);
	}

	/**
	 * 返回指定日期的前N天日期
	 * 
	 * @param dateParam
	 * @return
	 */
	public static String getDateBefore(String dateParam, int num) {
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			// date = getYesterByDate(sd.parse(dateParam));

			Calendar c = Calendar.getInstance();
			c.setTime(sd.parse(dateParam));
			int day = c.get(Calendar.DATE);
			c.set(Calendar.DATE, day - (num - 1));
			date = c.getTime();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new SimpleDateFormat("yyyy-MM-dd").format(date);
	}

	/**
	 * 返回时间戳
	 * 
	 * @param Format
	 *            字符串格式yyyy-MM-dd 年月日
	 * @return
	 */
	public static int getTime(String user_time) {
		String re_time = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date d;
		try {
			d = sdf.parse(user_time);
			long l = d.getTime();
			String str = String.valueOf(l);
			re_time = str.substring(0, 10);
		} catch (ParseException e) {

			e.printStackTrace();
		}
		return Integer.parseInt(re_time);
	}

	/**
	 * 获取当前日期一个月前的日期
	 * 
	 * @param date:
	 *            yyyy-MM-dd 指定日期
	 * @return
	 * @throws ParseException
	 * @throws ParseException
	 */
	public static String getLastMonthDay(String date) {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Calendar cal = Calendar.getInstance();
			if (null == date || "".equals(date.trim())) // 如果传入的字符串为空值,默认为当天日期
				date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			// 设置cal的值
			cal.setTime(format.parse(date));
			int d = Integer.parseInt(date.substring(date.lastIndexOf("-") + 1,
					date.length()));

			cal.add(Calendar.MONTH, -1); // 上个月
			cal.set(Calendar.DATE, d); // 当天

			return new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}

	}
	/**
	 * 获取当前月份
	 * @return
	 */
	public static String getCurrentMonth() {
		return getMonth(getNowTime("yyyy-MM-dd"));
	}
	/**
	 * 获取上个月的月份
	 * @return
	 */
	public static String getLastMonth(String date){
		try{
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
			Calendar cal = Calendar.getInstance();
			cal.setTime(format.parse(date));
			cal.set(Calendar.MONTH, cal.get(Calendar.MONTH)-1);
			return format.format(cal.getTime());
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 返回时间的月份格式 
	 * @param time : yyyy-MM-dd
	 * @return
	 */
	public static String getMonth(String time){
		if(null==time || "".equals(time.trim()) || time.length()<7){
			throw new IllegalArgumentException("time is null");
		}
		return time.substring(0, 7);
	}
	
	public static void main(String[] args) throws ParseException {
//		System.out.println(getFirstDayofLastMonth("2014-06-30"));
//		System.out.println(Double.parseDouble("123.012"));
		System.out.println(getLastMonthLastDay("2014-07-15"));
	}

	public static String dataStrConver(String sourceTime, String oldPatten,String newPatten) {
		if(StringUtils.isBlank(sourceTime)) return new SimpleDateFormat(newPatten).format(new Date());;
		if(StringUtils.isBlank(oldPatten)) oldPatten = "yyyy-MM-dd";
		if(StringUtils.isBlank(newPatten)) newPatten = "yyyyMMdd";
		try {
			return new SimpleDateFormat(newPatten).format(new SimpleDateFormat(oldPatten).parse(sourceTime));
		} catch (ParseException e) {
			//return new SimpleDateFormat(newPatten).format(new Date());
			return sourceTime;
		}
	}

	public static Date getDateFromStr(String strTime, String format) {
		if(StringUtils.isBlank(format)) format = "yyyy-MM-dd";
		try {
			return new SimpleDateFormat(format).parse(strTime);
		} catch (ParseException e) {
			return new Date();
		}
	}

	public static long monthDiffer(Date stTime,Date endTime) {
		long monthday = 0;
		Calendar st = Calendar.getInstance();
		Calendar et = Calendar.getInstance();
		st.setTime(stTime);
		et.setTime(endTime);
        int sYear = st.get(Calendar.YEAR);
        int sMonth = st.get(Calendar.MONTH);
        int sDay = st.get(Calendar.DATE);

        int eYear = et.get(Calendar.YEAR);
        int eMonth = et.get(Calendar.MONTH);
        int eDay = et.get(Calendar.DATE);

        monthday = ((eYear - sYear) * 12 + (eMonth - sMonth));
        if (sDay < eDay) {
            monthday = monthday + 1;
        }
        return monthday;
	}
	
	/**
	 * 获取当前时间
	 * @param type 格式,例如：yyyyMMddHHmmss，不传参数默认为：yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String getDateTimeStr(String... type) {
		String ss = "yyyy-MM-dd HH:mm:ss";
		if(type.length > 0) ss = type[0];
		return new SimpleDateFormat(ss).format(new Date());
	}
	/**
	 * 获取指定日期的上个月的第一天
	 * @param date
	 * @return
	 */
	public static String getFirstDayofLastMonth(String date){
		/** check arg, if date is null,date=current **/
		date = StrUtil.isNullOrEmpty(date)?DateUtil.getNowTime("yyyy-MM-dd"):date;
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date d = null;
		try {
			d = format.parse(date);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(d);
			int m = calendar.get(Calendar.MONTH);
			calendar.set(Calendar.MONTH, m-1);
			calendar.set(Calendar.DAY_OF_MONTH,1);
			return format.format((calendar.getTime()));
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 获取上个月的第一天
	 * @return
	 */
	public static String getFirstDayOfLastMonth(){
		return getFirstDayofLastMonth(DateUtil.getNowTime("yyyy-MM-dd"));
	}
	/**
	 * 获取指定日期上个月的最后一天的日期
	 * @return
	 */
	public static String getLastMonthLastDay(String date){
		date = StrUtil.isNullOrEmpty(date)?DateUtil.getNowTime("yyyy-MM-dd"):date;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date d = null;
		try {
			d = df.parse(date);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(d);
			int m = calendar.get(Calendar.MONTH);
			// 设置日期为上个月一天
			calendar.set(Calendar.DATE, 1);
			// 设置日期为上个月最后一天
			calendar.add(Calendar.DATE, -1);
			// 设置月份减1
			calendar.set(Calendar.MONTH, m-1);
			
			return df.format(calendar.getTime());
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 获取当前日期上个月最后一天的日期
	 * @return
	 */
	public static String getLastMonthLastDay(){
		return getLastMonthLastDay(DateUtil.getNowTime("yyyy-MM-dd"));
	}
}
