package com.wykblog.blog.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;

import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateTimeUtil {
  private static Logger logger = LoggerFactory.getLogger(DateTimeUtil.class);
  
  public static final String PATTERN_DEFAULT = "yyyy-MM-dd HH:mm:ss";
  
  public static final String PATTERN_DAY = "yyyy-MM-dd";
  
  public static final String PATTERN_COMPACT = "yyyyMMdd";
  
  public static SimpleDateFormat formatDisplayDate = new SimpleDateFormat("M月d号");
  
  public static SimpleDateFormat formatDisplayTimeA = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
  
  public static String formatDate(Date date, String pattern) {
    if (date == null)
      throw new IllegalArgumentException("date is null"); 
    if (pattern == null)
      throw new IllegalArgumentException("pattern is null"); 
    SimpleDateFormat formatter = new SimpleDateFormat(pattern);
    return formatter.format(date);
  }
  
  public static String businessAppDisplayTime(Timestamp timestamp) {
    if (timestamp == null)
      return ""; 
    Date date = new Date(timestamp.getTime());
    Date date1 = getMorningDate(date);
    long time = date1.getTime();
    long now = System.currentTimeMillis();
    int eclapseSec = (int)((now - time) / 1000L);
    int days = eclapseSec / 86400;
    StringBuffer sb = new StringBuffer();
    if (days > 0) {
      if (days == 1) {
        sb.append("昨天" + parseDateTime(timestamp, "HH:mm"));
      } else if (days == 2) {
        sb.append("前天" + parseDateTime(timestamp, "HH:mm"));
      } else {
        sb.append(parseDateTime(timestamp, "yyyy/MM/dd"));
      } 
    } else {
      sb.append("今天" + parseDateTime(timestamp, "HH:mm"));
    } 
    return sb.toString();
  }
  
  public static String generateAppDisplayTime(Timestamp timestamp) {
    if (timestamp == null)
      return ""; 
    long time = timestamp.getTime();
    long now = System.currentTimeMillis();
    int eclapseSec = (int)((now - time) / 1000L);
    int days = eclapseSec / 86400;
    StringBuffer sb = new StringBuffer();
    if (days > 0) {
      if (days == 1) {
        sb.append("昨天");
      } else if (days == 2) {
        sb.append("前天");
      } else if (days >= 30) {
        int month = days / 30;
        if (month > 12) {
          sb.append(normalizeTime(time));
        } else {
          sb.append(month).append("个月前");
        } 
      } else if (days < 30) {
        sb.append(days).append("天前");
      } 
    } else {
      sb.append(parseDateTime(timestamp, "HH:mm"));
    } 
    return sb.toString();
  }
  
  public static Date getDaysAgo(int days) {
    return new Date(System.currentTimeMillis() - (days * 86400) * 1000L);
  }
  
  public static String normalizeTime(long time) {
    SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date date = new Date(time);
    return sm.format(date);
  }
  
  public static String normalizeTime(Timestamp timestamp) {
    if (timestamp == null)
      return ""; 
    Date date = new Date(timestamp.getTime());
    SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    return sm.format(date);
  }
  
  public static String parseDateTime(Date date, String format) {
    if (date == null)
      return ""; 
    SimpleDateFormat sm = new SimpleDateFormat(format);
    return sm.format(date);
  }
  
  public static Date getTodayDate() {
    Date date = new Date();
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    cal.set(10, 0);
    cal.set(12, 0);
    cal.set(13, 0);
    cal.set(14, 0);
    return cal.getTime();
  }
  
  public static Date getMorningDate(Date date) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    cal.set(10, 0);
    cal.set(12, 0);
    cal.set(13, 0);
    cal.set(14, 0);
    return cal.getTime();
  }
  
  public static Date getYesterdayDate() {
    Date date = new Date(System.currentTimeMillis() - 86400000L);
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    cal.set(10, 0);
    cal.set(12, 0);
    cal.set(13, 0);
    cal.set(14, 0);
    return cal.getTime();
  }
  
  public static String getDateByFormat(Date date, String format) {
    if (date == null)
      date = new Date(); 
    SimpleDateFormat formatDate = new SimpleDateFormat(format);
    return formatDate.format(date);
  }
  
  public static long getDateDiff(Date dateStart, Date dateStop) {
    if (dateStart != null && dateStop != null) {
      SimpleDateFormat formatDate = new SimpleDateFormat("yyyy");
      formatDate.format(dateStart);
      formatDate.format(dateStop);
      long diff = (dateStart.getTime() - dateStop.getTime()) / 1000L / 3600L / 24L / 365L;
      return diff;
    } 
    return 0L;
  }
  
  public static Date parseStringToDate(String date, String format) {
    if (date == null)
      return null; 
    SimpleDateFormat sm = new SimpleDateFormat(format);
    try {
      return sm.parse(date);
    } catch (ParseException e) {
      logger.error(e.getMessage(), e);
      return null;
    } 
  }
  
  public static Date parseStringToDate(String time) {
    if (StringUtil.isEmpty(time))
      return null; 
    int year = 0, month = 1, day = 1, hour = 0, min = 0, sec = 0;
    List<String> strList = new ArrayList<String>();
    StringBuffer nsb = new StringBuffer();
    int i;
    for (i = 0; i < time.length(); i++) {
      char c = time.charAt(i);
      if (Character.isDigit(c)) {
        nsb.append(c);
      } else if (nsb.length() > 0) {
        strList.add(nsb.toString());
        nsb = new StringBuffer();
      } 
    } 
    if (nsb.length() > 0)
      strList.add(nsb.toString()); 
    for (i = 0; i < strList.size(); i++) {
      int n = CommonUtil.getIntValue(strList.get(i));
      switch (i) {
        case 0:
          year = n;
          break;
        case 1:
          month = n;
          break;
        case 2:
          day = n;
          break;
        case 3:
          hour = n;
          break;
        case 4:
          min = n;
          break;
        case 5:
          sec = n;
          break;
      } 
    } 
    StringBuffer sb = new StringBuffer();
    sb.append(StringUtil.formatString("%04d", new Object[] { Integer.valueOf(year) })).append("-")
      .append(StringUtil.formatString("%02d", new Object[] { Integer.valueOf(month) })).append("-")
      .append(StringUtil.formatString("%02d", new Object[] { Integer.valueOf(day) })).append(" ")
      .append(StringUtil.formatString("%02d", new Object[] { Integer.valueOf(hour) })).append(":")
      .append(StringUtil.formatString("%02d", new Object[] { Integer.valueOf(min) })).append(":")
      .append(StringUtil.formatString("%02d", new Object[] { Integer.valueOf(sec) }));
    return parseStringToDate(sb.toString(), "yyyy-MM-dd HH:mm:ss");
  }
  
  public static long diffMonth(Date end) {
    Date begin = new Date();
    if (begin != null && end != null) {
      SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM");
      formatDate.format(begin);
      formatDate.format(end);
      long diff = (begin.getTime() - end.getTime()) / 1000L / 3600L / 24L;
      return diff;
    } 
    return 0L;
  }
  
  public static long diffMonth(Date begin, Date end) {
    if (begin != null && end != null) {
      Calendar beginCal = Calendar.getInstance();
      beginCal.setTime(begin);
      Calendar endCal = Calendar.getInstance();
      endCal.setTime(end);
      long month = ((endCal.get(1) - beginCal.get(1)) * 12);
      return month + (endCal.get(2) - beginCal.get(2));
    } 
    return 0L;
  }
  
  public static void main(String[] args) throws ParseException {
    LocalVariableTableParameterNameDiscoverer localVariableTableParameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
    Method[] methods = DateTimeUtil.class.getDeclaredMethods();
    byte b;
    int i;
    Method[] arrayOfMethod1;
    for (i = (arrayOfMethod1 = methods).length, b = 0; b < i; ) {
      Method method = arrayOfMethod1[b];
      String[] params = localVariableTableParameterNameDiscoverer.getParameterNames(method);
      byte b1;
      int j;
      String[] arrayOfString1;
      for (j = (arrayOfString1 = params).length, b1 = 0; b1 < j; ) {
        String param = arrayOfString1[b1];
        System.out.println(String.valueOf(method.getName()) + " " + param);
        b1++;
      } 
      b++;
    } 
  }
  
  public static Date getYear(String year) {
    SimpleDateFormat formatDate = new SimpleDateFormat("yyyy");
    try {
      return formatDate.parse(year);
    } catch (ParseException e) {
      return null;
    } 
  }
  
  public static Date ceiling(Date date) {
    if (date == null)
      return null; 
    SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd");
    String day = String.valueOf(sm.format(date)) + " 23:59:59";
    return parseStringToDate(day, "yyyy-MM-dd HH:mm:ss");
  }
  
  public static Date floor(Date date) {
    if (date == null)
      return null; 
    SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd");
    String day = String.valueOf(sm.format(date)) + " 00:00:00";
    return parseStringToDate(day, "yyyy-MM-dd HH:mm:ss");
  }
  
  public static Date maxDate(Date date) {
    if (date == null)
      return null; 
    SimpleDateFormat sm = new SimpleDateFormat("yyyy");
    String day = String.valueOf(sm.format(date)) + "-12-31 00:00:00";
    return parseStringToDate(day, "yyyy-MM-dd HH:mm:ss");
  }
  
  public static Timestamp toTimestamp(Date date) {
    if (date == null)
      return null; 
    return new Timestamp(date.getTime());
  }
  
  public static Date delta(Date date, int daltaDays) {
    if (date == null)
      return null; 
    return new Date(date.getTime() + (daltaDays * 86400) * 1000L);
  }
  
  public static Date toSqlDate(Date date) {
    if (date == null)
      return null; 
    return new Date(date.getTime());
  }
  
  public static Date firstDayInMonth() {
    Calendar cal = Calendar.getInstance();
    cal.set(5, 1);
    return cal.getTime();
  }
  
  public static Date lastDayInMonth() {
    Calendar cal = Calendar.getInstance();
    cal.set(5, cal.getActualMaximum(5));
    return cal.getTime();
  }
}