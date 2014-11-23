package com.losy.common.utils;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;



/**
 * 字符串的帮助类 <br/>
 * 提供静态方法，不可以实例化。
 * @author RSun
 * @date 2012-2-26下午03:58:06
 */
public  class StrUtil {

	/** 禁止实例化 **/
	private StrUtil() {
	}

	public static boolean verifyImsi(String imsi){
		return !isNullOrEmpty(imsi) && (imsi).matches("^[0-9]{1,15}$");
	}
	
	public static boolean verifyMobile(String mobile){
		return !isNullOrEmpty(mobile) && (mobile).matches("^1[0-9]{10}");
	}
	
	public static boolean verifyEmail(String email){
		return !isNullOrEmpty(email) && (email).matches(".*?@.*?");
	}
	
	/**
	 * 检查字符串是否为null或者为空串<br>
	 * 为空串或者为null返回true，否则返回false
	 */
	public static boolean isNullOrEmpty(String str) {
		boolean result = true;
		if (str != null && !"".equals(str.trim())) {
			result = false;
		}
		return result;
	}


	/** 转换为float数据类型的方法 **/
	public static float _Float(Object str) {
		float f = 0.0f;
		try {
			f = Float.parseFloat(str.toString());
			return f;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	/** 转换为int数据类型的方法 **/
	public static int _Integer(Object str) {
		int i = 0;
		try {
			i = Integer.parseInt(str.toString());
			return i;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	
	/**
	 * <h1>获取时间到日期</h1>
	 * 说明：如果参数为null，则返回当天日期 
	 * @param date
	 * @return 格式：2012-01-05
	 */
	public static String getDateByDay(Date date) {
		if(date == null)
			date = new Date();
		return new SimpleDateFormat("yyyy-MM-dd").format(date);
	}
	
	/**
	 * 字符串日期转换 
	 * @param dateStr 要转换的日期
	 * @param formatFrom 转前格式
	 * @param formatTo 转后格式
	 * @return
	 */
	public static String dateStrConvert(String dateStr,String formatFrom,String formatTo){
		if(StrUtil.isNullOrEmpty(dateStr))
			return "";
		DateFormat sdf= new SimpleDateFormat(formatFrom);
		Date date=null;
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new SimpleDateFormat(formatTo).format(date);
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
	 * 字符串转换到日期时间格式
	 * @param dateStr 需要转换的字符串
	 * @param formatStr 需要格式的目标字符串  举例 yyyy-MM-dd
	 * @return Date 返回转换后的时间
	 * @throws ParseException 转换异常
	 */
	public static Date strToDate(String dateStr, String formatStr){
		DateFormat sdf= new SimpleDateFormat(formatStr);
		Date date=null;
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	
	/**
	 * 比较两个时间的相差值（d1与d2）
	 * @param d1 时间一
	 * @param d2
	 * @param type 类型【d/h/m/s】
	 * @return d1 - d2
	 */
	public static long compareDate(Date d1, Date d2, char type) {
		long num = d1.getTime() - d2.getTime();
		num/=1000;
		if('m' == type){
			num/=60;
		}else if ('h' == type) {
			num/=3600;
		}else if ('d' == type) {
			num/=3600;
			num/=24;
		}
		return num;
	}
	
	/**
	 * 比较两个时间的相差值（d1与d2）
	 * @param d1 时间一
	 * @param d2
	 * @param type 类型【d/h/m/s】
	 * @return d1 - d2
	 */
	public static long compareDate(long d1, long d2, char type) {
		long num = d2 - d1;
		num/=1000;
		if('m' == type){
			num/=60;
		}else if ('h' == type) {
			num/=3600;
		}else if ('d' == type) {
			num/=3600;
			num/=24;
		}
		return num;
	}
	
	/**
	 * 比较两个时间的相差值（d1与d2）
	 * @param d1 时间一
	 * @param d2
	 * @param type 类型【d/h/m/s】
	 * @return d1 - d2
	 */
	public static double compareDateDouble(long d1, long d2, char type) {
		double num = (d2 - d1) / 1000;
		if('m' == type){
			num/=60;
		}else if ('h' == type) {
			num/=3600;
		}else if ('d' == type) {
			num/=3600;
			num/=24;
		}
		BigDecimal bd = new BigDecimal(num);
		num = bd.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
		return num;
	}
	
	/**
	 * 
	 * @param str1
	 * @param str2 空 则跟当前时间比较
	 * @return str1 < str2 <0 str1 > str2 >0 
	 */
	public static int compareDate(String str1, String str2) {
		Date d1 = getStrByDataTime(str1,null);
		Date d2 = getStrByDataTime(str2,null);
		return d1.compareTo(d2);
	}
	
	
	/**
	 * 根据参照时间获取相差天、小时数的新时间
	 * @param date		参照时间
	 * @param type		天或小时[d/h]
	 * @param num		差值，例如：2表示之后2天或小时，-2表示之前2天或小时
	 * @return
	 */
	public static Date getNextDay(Date date, char type, int num) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		switch (type) {
			case 'd':
				calendar.add(Calendar.DAY_OF_MONTH, num);
				break;
			case 'h':
				calendar.add(Calendar.HOUR_OF_DAY, num);
				break;
			default:
				break;
		}
		date = calendar.getTime();
		return date;
	}
	

	/** 获取当前时间戳  **/
	public static String getTimeStamp() {
		  String timeStamp = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
	      return timeStamp;
	}
	
	
	/** 获得本周第一天时间(yyyy-MM-dd) **/
	public static String getMonOfWeek(){
//		Calendar c = new GregorianCalendar();
//		c.setFirstDayOfWeek(Calendar.MONDAY);
//		c.setTime(new Date());
//		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek());
		
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_WEEK, c.getActualMinimum(Calendar.DAY_OF_WEEK));
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String str = sdf.format(c.getTime());
		return str;
	}
	
	
	/** 获得本周的最后一天(yyyy-MM-dd) **/
	public static String getSunOfWeek(){
//		Calendar c = new GregorianCalendar();
//		c.setFirstDayOfWeek(Calendar.MONDAY);
//		c.setTime(new Date());
//		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6);
		
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_WEEK, c.getActualMaximum(Calendar.DAY_OF_WEEK));
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String str = sdf.format(c.getTime());
		return str;
	}
	
	
	/** 获得当月的第一天(yyyy-MM-dd) **/
	public static String getFirstDayOfMonth(){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		String str=sdf.format(cal.getTime());
		return str;
	}
	
	
	/** 获得当月的最后一天(yyyy-MM-dd) **/
	public static String getLastDayOfMonth(){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        SimpleDateFormat sdfs=new SimpleDateFormat("yyyy-MM-dd");
        String str=sdfs.format(cal.getTime());
		return str;
	}
	
	
	/**  获取上一个月第一天（yyyy-MM-dd）**/
	public static String getFirstDayOfLastMonth(){
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH);
		cal.set(Calendar.MONTH, month-1);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		String str=sdf.format(cal.getTime());
		return str;
	}
	
	/**  获取上一个月最后一天（yyyy-MM-dd）**/
	public static String getLastDayOfLastMonth(){
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH);
		cal.set(Calendar.MONTH, month-1);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        SimpleDateFormat sdfs=new SimpleDateFormat("yyyy-MM-dd");
        String str=sdfs.format(cal.getTime());
		return str;
	}
	/**
	 * 格式化时间，时间转字符串
	 * @param date	null则为当前系统时间
	 * @param format 格式，null则默认为：'yyyy-MM-dd HH:mm:ss'
	 * @return 字符串格式的日期
	 */
	public static String getDateTimeByStr(Date date, String format) {
		if(date == null)
			date = new Date();
		if(format == null)
			format = "yyyy-MM-dd HH:mm:ss";
		return new SimpleDateFormat(format).format(date);
	}
	
	
	/**
	 * 格式化时间，字符串转时间
	 * @param dataStr	需要转换的字符串
	 * @param format 	格式，null则默认为：'yyyy-MM-dd HH:mm:ss'
	 * @return	转换的Date
	 */
	public static Date getStrByDataTime(String dataStr, String format) {
		if(dataStr == null || "".equals(dataStr))
			return new Date();
		if (format == null || "".equals(format))
			format = "yyyy-MM-dd HH:mm:ss";
		DateFormat sdf = new SimpleDateFormat(format);
		Date date = null;
		try {
			date = sdf.parse(dataStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}
	
	
	public static void main(String[] args) {
		System.out.println(getNum(9));
//		long nn = compareDate((new Date()), getNextDay(new Date(), 'd', -3), 'd');
//		System.out.println(nn);
//		System.out.println(new SimpleDateFormat("yyyy-MM-dd HH").format(getNextDay(new Date(), 'h', -2)));
//		Integer aa = 11;
//		System.out.println(getCleanInteger(aa));
//		System.out.println(getPrecision("100", "200454561656565668594919619256255.2"));
//		System.out.println(subStrmaxStr("1234567890", 20));
		
		/*System.out.println(isNum("023940324"));
		System.out.println(isNum("02394039-24"));
		System.out.println(isNum("0239403b24"));*/
		
//		StrUtil u = new StrUtil();
		//System.out.println(u.dateStrConvert("2013-01-30 02:03:04.3","yyyy-MM-dd HH:mm:ss","yyyyMMddHHmmss"));
//		System.out.println(compareDate("2013-06-13 15:15:15", null));
		
	}
	
	/**
	 * 输入一个长度,获得相应长度的随机数
	 * 长度不能超过9
	 * @param num
	 * @return
	 */
	public static Integer getNum(Integer num){
		String str = "";
		if(num != null && num != 0){
			if(num < 10){
				for(int i=0; i<num; i++){
					Random r = new Random();
					str += r.nextInt(10);
				}
			}else{
				str = "0";
			}
		}else{
			str = "0";
		}
		return Integer.parseInt(str.toString());
	}
	
	/**
	 * 限制字符串的最长长度，多余的截取
	 * @param str	源字符串
	 * @param maxLen 最长长度	
	 * @return
	 */
	public static String subStrmaxStr(String str, int maxLen){
		if(!StrUtil.isNullOrEmpty(str)){
			try {
				maxLen = (maxLen < 0) ? str.length() : maxLen;
				str = str.substring(0,str.length() >= maxLen ? maxLen : str.length());
			} catch (Exception e) {
				e.printStackTrace();
				str = "";
			}
		}else{
			str = "";
		}
		return str;
	}
	
	/**检查是否是数字 **/
	public static boolean isNum(String str){
		return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
	}
	
	
	 /** 保证将传入参数处理到一定是两位 **/
	public static String formatToTwoLength(Object o){
		String str = "";
		try {
			str = String.valueOf(o);
		} catch (Exception e) {
			e.printStackTrace();
		}
	    if(str.length() > 2)
	       return str.substring(0, 2);
	    if(str.length() < 2)
	       return "0"+str;
	    return str;
	}
	
	/**
	 * 检查字符串是否为null或者为"null"<br>
	 * 为null或者为"null",返回""，否则返回字符串 <br>
	 * 用于处理数据库查询数据
	 */
	public static String getCleanString(Object obj){
		if( obj == null ){
			return "";
		}else if(String.valueOf(obj).equals("null")){
			return "";
		}else{
			return String.valueOf(obj);
		}
	}
	

	/**
	 * 检查int数据是否为null<br>
	 * 为null,返回0，否则返回原值 <br>
	 * @param obj
	 * @return
	 */
	public static int getCleanInteger(Object obj){
		if( obj != null ){
			return _Integer(obj);
		}else{
			return 0;
		}
	}
	
	

	/**
	 * 检查float数据是否为null<br>
	 * 为null,返回0f，否则返回原值 <br>
	 * @param obj
	 * @param defaultValue	默认值，可以传入可以不传。传入，为null时返回本值
	 * @return
	 */
	public static float getCleanFloat(Object obj, float... defaultValue){
		if( obj != null ){
			return _Float(obj);
		}else{
			if(defaultValue.length != 1){
				return 0f;
			}else{
				return defaultValue[0];
			}
		}
	}
	
	
	/**
	 * 从number数值中随机抽取count个数字
	 * @param count	  要抽取的数值个数
	 * @param number 数字范围为 (0, number]
	 * @return
	 */
	public static int[] randomNumber(int count, int number) {
	    int k=count, n=number; 
		int[] numbers = new int[n];
		for (int i = 0; i < numbers.length; i++)
			numbers[i] = i + 1;
		int[] result = new int[k];
		for (int i = 0; i < result.length; i++){  
			int r = (int) (Math.random() * n);
			result[i] = numbers[r];
			numbers[r] = numbers[n - 1];
			n--;
		}
	      
//	    Arrays.sort(result);
//	    for (int r : result)
		return result;
		
	}
	
	/**
	 * 返回随机字符串
	 * @param toks 要随机操作的字符串
	 * @param len 长度
	 * @return
	 */
	public static String genRandoomString(String toks,int len)
	{
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<len;i++) sb.append(toks.charAt((int)(Math.random() * len)));
		return sb.toString();
	}
	
	/**
	 * 高精度乘法运算
	 * @param num	乘数
	 * @param num2	因子
	 * @return
	 */
	public static String getPrecision(String num, String num2){
		return new BigDecimal(num).multiply(new BigDecimal(num2)).toString();
	}


	/**
	 * 截取小数后面的0
	 * @param str
	 * @return
	 */
	public static String substringFloat(String str){
		String ss = str.substring(0, str.indexOf('.'));
		if(Double.parseDouble(ss) == Double.parseDouble(str)){
			return ss;
		}
		return str;
	}
	
	
	/** 数组转字符串  **/
	private static String arrayTurnStr(Object[] array, String split){
		if(null == array)
			return "";
		StringBuilder ss = new StringBuilder();
		for (int i = 0; i < array.length; i++) {
			if(i>0)
				ss.append(getCleanString(split));
			ss.append(getCleanString(array[i]));
		}
		return ss.toString();
	}
	
	/**
	 * 数组转字符串
	 * @param array 原数组
	 * @param split 分割符
	 * @return
	 */
	public static String arrayStrTurnStr(String[] array, String split){
		return arrayTurnStr(array, split);
	}
	
	/**
	 * 数组转字符串
	 * @param array 原数组
	 * @param split 分割符
	 * @return
	 */
	public static String arrayIntTurnStr(Integer[] array, String split){
		return arrayTurnStr(array, split);
	}
	
	public static  Date getDifferByDate(Date date,int differDay) {
		Calendar cd = Calendar.getInstance();
		cd.setTime(date);
		cd.add(Calendar.DAY_OF_YEAR, differDay);
		return cd.getTime();
	}

	/**
	 * 判断版本号
	 * @param version	v2.x.x
	 * @param ver	203
	 * @return	version>ver 返回ture；否则返回false
	 */
	public static boolean judgeVersion(String version, int ver){
		if(isNullOrEmpty(version))
			return false;
		String[] arr = version.split("\\.");
		int vers = StrUtil.getCleanInteger(arrayTurnStr(arr, ""));
		if(vers > ver){
			return true;
		}
		return false;
	}
	
	/**
	 * 替换字符，防止SQL注入
	 * @param str
	 * @return
	 */
	public final static String mysqlEscape(String str){
		str = StrUtil.getCleanString(str);
		str = str.replaceAll("'","''");
		str = str.replaceAll("\\\\","\\\\\\\\");
		str = str.replaceAll("%","\\%");
		str = str.replaceAll("_","\\_");
		return str;
	}
	
}
