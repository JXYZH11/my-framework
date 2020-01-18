package com.jxyzh11.myframework.toolkit;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * DateUtil
 *
 * @author JXYZH11
 * @version 1.0
 * @description TODO
 * @date 2020/1/17 14:28
 */
@Slf4j
public class DateUtil {

    public static final String DEFAULT_TIME_000000 = " 00:00:00";
    public static final String DEFAULT_TIME_235959 = " 23:59:59";

    public static final int SECONDS_IN_DAY = 60 * 60 * 24;
    public static final long MILLIS_IN_DAY = 1000L * SECONDS_IN_DAY;

    public static final String yyyyMMdd_DATA_PATTERN = "yyyyMMdd";
    public static final String DEFAULT_DATA_PATTERN = "yyyy-MM-dd";
    public static final String DEFAULT_TIME_PATTERN = "HH:mm:ss";
    public static final String DEFAULT_DATATIME_PATTERN = DEFAULT_DATA_PATTERN + " " + DEFAULT_TIME_PATTERN;
    private static DateTimeFormatter FORMATTER = DateTimeFormat.forPattern(DEFAULT_DATATIME_PATTERN);


    /**
     * 判断两个date类型的日期,是否同一天
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSameDay(final Date date1, final Date date2) {
        if (date1 == null || date2 == null) {
            return false;
        }
        long ms1 = date1.getTime();
        long ms2 = date2.getTime();
        final long interval = ms1 - ms2;
        return interval < MILLIS_IN_DAY
                && interval > -1L * MILLIS_IN_DAY
                && toDay(ms1) == toDay(ms2);
    }

    /**
     *
     *
     * @param
     * @return
     * @author JXYZH11
     * @version 1.0
     * @description TODO
     * @date 2020/1/17 15:35
     */
    private static long toDay(long millis) {
        return (millis + TimeZone.getDefault().getOffset(millis)) / MILLIS_IN_DAY;
    }

    public static String format(Date date, String pattern) {
        if (date == null) {
            return null;
        }
        if (pattern == null) {
            pattern = DEFAULT_DATATIME_PATTERN;
        }
        return DateFormatUtils.format(date, pattern);
    }

    public static String format(Date date) {
        if (date == null) {
            return null;
        }
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(FORMATTER);
    }

    /**
     * 判断日期相差天数 date2-date1
     *
     * @param date1 date1
     * @param date2 date2
     * @return 返回结果
     */
    public static Integer getIntervalDays(String date1, String date2) {
        return getIntervalDays(parseDate(date1), parseDate(date2));
    }

    /**
     * 判断日期相差天数 date2-date1
     *
     * @param date1 date1
     * @param date2 date2
     * @return 返回结果
     */
    public static Integer getIntervalDays(Date date1, Date date2) {
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATA_PATTERN);
        Calendar cal = Calendar.getInstance();
        try {
            date1 = sdf.parse(sdf.format(date1));
        } catch (ParseException e) {
        }
        cal.setTime(date1);
        long time1 = cal.getTimeInMillis();
        try {
            date2 = sdf.parse(sdf.format(date2));
        } catch (ParseException e) {
        }
        cal.setTime(date2);
        long time2 = cal.getTimeInMillis();
        long between_days = Math.abs((time2 - time1) / (SECONDS_IN_DAY));
        return Integer.parseInt(String.valueOf(between_days));
    }


    /**
     * 获取日期的0点
     */
    public static Date getZeroOClock(Date date1) {
        long lastTime = date1.getTime();
        String lastDate = new SimpleDateFormat(DEFAULT_DATA_PATTERN).format(lastTime);
        Date lastZero = new Date();
        try {
            lastZero = new SimpleDateFormat(DEFAULT_DATA_PATTERN).parse(lastDate);
        } catch (ParseException e) {
        }
        return lastZero;
    }


    public static String getFirstOfMonth(Date date) {
        return new SimpleDateFormat("yyyy-MM-01").format(date);
    }

    /**
     * 获取昨天的日期字符串
     *
     * @return 结果
     */
    public static String getYesterdayDateStr() {
        return getYesterdayDateStr(new Date());
    }

    public static String getYesterdayDateStr(Date date) {
        Date dBefore = getYesterday(date);
        //设置时间格式
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATA_PATTERN);
        //格式化前一天
        return sdf.format(dBefore);
    }

    /**
     * 获取当前日期的后一天
     *
     * @return 结果
     */
    public static Date getTomorrow() {
        return getTomorrow(new Date());
    }

    /**
     * 获取指定日期的后一天
     *
     * @return 结果
     */
    public static Date getTomorrow(Date date) {
        return getDate(date, 1);
    }

    /**
     * 获取当前日期的前一天
     *
     * @return 结果
     */
    public static Date getYesterday() {
        return getYesterday(new Date());
    }

    /**
     * 获取指定日期的前一天
     *
     * @param date 指定日期
     * @return 结果
     */
    public static Date getYesterday(Date date) {
        return getDate(date, -1);
    }

    /**
     * 获取指定日期的前/后 dateDiff 天
     *
     * @param date     指定日期
     * @param dateDiff dateDiff
     * @return 结果
     */
    public static Date getDate(Date date, int dateDiff) {
        //得到日历
        Calendar calendar = Calendar.getInstance();
        //把当前时间赋给日历
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, dateDiff);
        return calendar.getTime();
    }

    /**
     * 获取当前日期的年字符串:yyyy格式
     *
     * @return 结果
     */
    public static String getCurrentYear() {
        return getYear(new Date());
    }

    /**
     * 获取当前日期的年月日字符串:yyyy-MM-dd格式
     *
     * @return 结果
     */
    public static String getCurrentYMD() {
        return getYMD(new Date());
    }

    /**
     * 获取当前日期的小时字符串:HH格式
     *
     * @return 结果
     */
    public static String getCurrentHour() {
        return getHour(new Date());
    }

    /**
     * 获取指定日期的年字符串:yyyy格式
     *
     * @param date 日期
     * @return 结果
     */
    public static String getYear(Date date) {
        return getCurrent(date, "yyyy");
    }

    /**
     * 获取指定日期的月日字符串:MM-dd格式
     *
     * @param date 日期
     * @return 结果
     */
    public static String getMD(Date date) {
        return getCurrent(date, "MM-dd");
    }

    /**
     * 获取指定日期的年月日字符串:yyyy-MM-dd格式
     *
     * @param date 日期
     * @return 结果
     */
    public static String getYMD(Date date) {
        return getCurrent(date, DEFAULT_DATA_PATTERN);
    }

    /**
     * 获取指定日期的小时字符串:HH格式
     *
     * @param date 日期
     * @return 结果
     */
    public static String getHour(Date date) {
        return getCurrent(date, "HH");
    }

    /**
     * 获取指定日期的指定格式字符串
     *
     * @param date    日期
     * @param pattern 格式
     * @return 结果
     */
    public static String getCurrent(Date date, String pattern) {
        return new SimpleDateFormat(pattern).format(date);
    }

    /**
     * 获取指定日期的默认 yyyy-MM-dd HH:mm:ss 格式字符串
     *
     * @param date 日期
     * @return 结果
     */
    public static String getCurrent(Date date) {
        return getCurrent(date, DEFAULT_DATATIME_PATTERN);
    }

    /**
     * 获取当前日期的指定格式字符串
     *
     * @param pattern 格式
     * @return 结果
     */
    public static String getCurrent(String pattern) {
        return getCurrent(new Date(), pattern);
    }

    /**
     * 获取当前时间的字符串,格式为:yyyy-MM-dd HH:mm:ss
     *
     * @return 结果
     */
    public static String getCurrent() {
        return getCurrent(DEFAULT_DATATIME_PATTERN);
    }

    /**
     * 使用默认日期格式,将字符串转换为日期对象
     *
     * @param dateStr 日期字符串
     * @return 转换结果
     */
    public static Date parseDate(String dateStr) {
        return parseDate(dateStr, DEFAULT_DATA_PATTERN);
    }

    /**
     * 使用指定的pattern日期格式,将字符串转换为日期对象
     *
     * @param dateStr 日期字符串
     * @param pattern 指定的格式, 不指定将用默认格式
     * @return 转换结果
     */
    public static Date parseDate(String dateStr, String pattern) {
        if (null == dateStr || "".equals(dateStr.trim())) {
            return null;
        }
        if (null == pattern || "".equals(pattern.trim())) {
            pattern = DEFAULT_DATA_PATTERN;
        }
        Date date = null;
        try {
            date = new SimpleDateFormat(pattern).parse(dateStr);
        } catch (ParseException e) {
        }
        return date;
    }

    /**
     * 获取传入日期当天的剩余时间，按照秒计
     *
     * @param currentDate 日期
     * @return 描述
     */
    public static Integer getLeftSecondsOneDay(Date currentDate) {
        if (null == currentDate) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return (int) ((calendar.getTime().getTime() - currentDate.getTime()) / 1000);
    }

    /**
     * 两个日期相差毫秒数
     *
     * @param date1 日期
     * @param date2 日期
     * @return 秒数
     */
    public static Long getIntervalSeconds(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            return null;
        }
        return date1.getTime() - date2.getTime();
    }

    /**
     * 获取指定时间间隔minute分钟的时间
     * 为正表示指定时间之后，反之之前
     *
     * @param date    传入日期
     * @param seconds 分钟数
     * @return 日期
     */
    public static Date getDiffTimeBySecond(Date date, int seconds) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, seconds);
        return calendar.getTime();
    }

    /**
     * 获取指定时间间隔minute分钟的时间
     * 为正表示指定时间之后，反之之前
     *
     * @param date    传入日期
     * @param minutes 分钟数
     * @return 日期
     */
    public static Date getDiffTimeByMinute(Date date, int minutes) {
        if (null == date) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, minutes);
        return calendar.getTime();
    }

    /**
     * 获取指定时间间隔hour小时的时间
     * hour为正表示指定时间之后，反之之前
     *
     * @param date  传入日期
     * @param hours 小时数
     * @return 日期
     */
    public static Date getDiffTimeByHour(Date date, int hours) {
        if (null == date) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, hours);
        return calendar.getTime();
    }

    /**
     * 获取指定时间间隔day天的时间
     * day>0表示指定时间之后day天，反之之前
     *
     * @param date 传入日期
     * @param days 天数
     * @return 日期
     */
    public static Date getDiffTimeByDay(Date date, int days) {
        if (null == date) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, days);
        return calendar.getTime();
    }

    /**
     * 获取指定日期当天的零点
     *
     * @param date 指定日期
     * @return 零点日期
     */
    public static Date getBeginOfDay(Date date) {
        if (date == null) {
            return null;
        }
        return new DateTime(date)
                .withMillisOfDay(0)
                .toDate();
    }

    public static Date getEndOfPrevDay(Date date) {
        if (date == null) {
            return null;
        }
        return new DateTime(date)
                .withMillisOfDay(0)
                .minusSeconds(1)
                .toDate();
    }

    /**
     * 获取指定日期当天的结束时间点
     *
     * @param date 指定日期
     * @return 结束点日期
     */
    public static Date getEndOfDay(Date date) {
        if (date == null) {
            return null;
        }
        return new DateTime(date)
                .withMillisOfDay(0)
                .plusDays(1)
                .minusSeconds(1)
                .toDate();
    }

    /**
     * 根据字符串获取指定的时间
     * 格式：2018-08-22 12:32:34 --> 2018-08-22 12:00:00
     *
     * @param dateStr 字符串
     * @return 日期
     */
    public static Date getHourTime(String dateStr) {
        if (StringUtils.isEmpty(dateStr)) {
            return null;
        }
        int index = dateStr.indexOf(":");
        return parseDate(dateStr.substring(0, index) + ":00:00", DateUtil.DEFAULT_DATATIME_PATTERN);
    }

    /**
     * 根据date参数获取指定的时间
     * 格式：2018-08-22 12:32:34 --> 2018-08-22 12:00:00
     *
     * @param date 日期
     * @return 日期
     */
    public static Date getHourTime(Date date) {
        if (date == null) {
            return null;
        }
        String dateStr = DateUtil.format(date, DateUtil.DEFAULT_DATATIME_PATTERN);
        int index = dateStr.indexOf(":");
        String res = dateStr.substring(0, index) + ":00:00";
        return parseDate(res, DateUtil.DEFAULT_DATATIME_PATTERN);
    }

    /**
     * 获取指定日期相隔months个月份的日期，为正，日期往后累计，反之往前
     *
     * @param original 原始日期
     * @param months   相差的月份数
     * @return 间隔的日期
     */
    public static Date getSeparateDate(Date original, int months) {
        DateTime dateTime = new DateTime(original);
        DateTime result = dateTime.plusMonths(months);
        return result.toDate();
    }

    public static Date getIntervalDate(Date date, int days) {
        if (date == null) {
            return null;
        }
        DateTime dateTime = new DateTime(date);
        return dateTime.plusDays(days).toDate();
    }

    /**
     * 获取指定日期是周几
     *
     * @param date 指定日期
     * @author sunny
     * @date 2019/7/30
     */
    public static Week getWeek(Date date) {
        if (date == null) {
            return null;
        }
        DateTime dateTime = new DateTime(date);
        int week = dateTime.getDayOfWeek();
        return Week.find(week);
    }

    /**
     * 获取指定日期所在周的指定周几的日期(起始时间00:00:00)
     *
     * @param date 指定日期
     * @param week 周
     * @author sunny
     * @date 2019/7/30
     */
    public static Date getByWeek(Date date, Week week) {
        if (date == null) {
            return null;
        }
        if (week == null) {
            return null;
        }
        Week source = getWeek(date);
        int intervalDays = week.getVal() - source.getVal();
        return new DateTime(date)
                .withMillisOfDay(0)
                .plusDays(intervalDays)
                .toDate();
    }

    @Getter
    public enum Week {
        Monday(1),
        Tuesday(2),
        Wednesday(3),
        Thursday(4),
        Friday(5),
        Saturday(6),
        Sunday(7);

        Week(int val) {
            this.val = val;
        }

        private int val;

        static Week find(int val) {
            switch (val) {
                case 1:
                    return Monday;
                case 2:
                    return Tuesday;
                case 3:
                    return Wednesday;
                case 4:
                    return Thursday;
                case 5:
                    return Friday;
                case 6:
                    return Saturday;
                case 7:
                    return Sunday;
                default:
                    return null;
            }
        }
    }

    public static void main(String[] args) {
        String datStr = "2019-08-01 10:00:00";
        Date now = parseDate(datStr);
        Date begin = getBeginOfDay(now);
        Date end = getEndOfDay(now);
        log.debug(DateUtil.format(begin));
        log.debug(DateUtil.format(end));
        Week week = getWeek(now);
        log.debug(DateUtil.format(DateUtil.getEndOfPrevDay(now)));

        Date monday = getByWeek(now, Week.Monday);
        Date tuesday = getByWeek(now, Week.Tuesday);
        Date wednesday = getByWeek(now, Week.Wednesday);
        Date thursday = getByWeek(now, Week.Thursday);
        Date friday = getByWeek(now, Week.Friday);
        Date saturday = getByWeek(now, Week.Saturday);
        Date sunday = getByWeek(now, Week.Sunday);
        log.debug(Week.Monday + ":" + format(monday));
        log.debug(Week.Tuesday + ":" + format(tuesday));
        log.debug(Week.Wednesday + ":" + format(wednesday));
        log.debug(Week.Thursday + ":" + format(thursday));
        log.debug(Week.Friday + ":" + format(friday));
        log.debug(Week.Saturday + ":" + format(saturday));
        log.debug(Week.Sunday + ":" + format(sunday));
    }
}
