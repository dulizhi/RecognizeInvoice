package com.yhw.common.utils;

import com.yhw.common.constant.GlobalConstants;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.lang.management.ManagementFactory;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类
 *
 * @author ruoyi
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
    public static String YYYY = "yyyy";

    public static String YYYY_MM = "yyyy-MM";

    public static String YYYY_MM_DD = "yyyy-MM-dd";

    public static String YYYYMMDD = "yyyyMMdd";

    public static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    private static String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
            "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};

    /**
     * 获取当前Date型日期
     *
     * @return Date() 当前日期
     */
    public static Date getNowDate() {
        return new Date();
    }

    /**
     * 获取当前日期, 默认格式为yyyy-MM-dd
     *
     * @return String
     */
    public static String getDate() {
        return dateTimeNow(YYYY_MM_DD);
    }

    public static final String getTime() {
        return dateTimeNow(YYYY_MM_DD_HH_MM_SS);
    }

    public static final String dateTimeNow() {
        return dateTimeNow(YYYYMMDDHHMMSS);
    }

    public static final String dateTimeNow(final String format) {
        return parseDateToStr(format, new Date());
    }

    public static final String dateTime(final Date date) {
        return parseDateToStr(YYYY_MM_DD, date);
    }

    public static final String parseDateToStr(final String format, final Date date) {
        return new SimpleDateFormat(format).format(date);
    }

    public static final Date dateTime(final String format, final String ts) {
        try {
            return new SimpleDateFormat(format).parse(ts);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 日期路径 即年/月/日 如2018/08/08
     */
    public static final String datePath() {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyy/MM/dd");
    }

    /**
     * 日期路径 即年/月/日 如20180808
     */
    public static final String dateTime() {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyyMMdd");
    }

    /**
     * 日期型字符串转化为日期 格式
     */
    public static Date parseDate(Object str) {
        if (str == null) {
            return null;
        }
        try {
            return parseDate(str.toString(), parsePatterns);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 获取服务器启动时间
     */
    public static Date getServerStartDate() {
        long time = ManagementFactory.getRuntimeMXBean().getStartTime();
        return new Date(time);
    }

    /**
     * 计算两个时间差
     */
    public static String getDatePoor(Date endDate, Date nowDate) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        return day + "天" + hour + "小时" + min + "分钟";
    }

    /**
     * 计算两个时间相差天数
     */
    public static long getDayDiff(Date endDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        return (endDate.getTime() - calendar.getTime().getTime())/(1000 * 24 * 3600);
    }

    /**
     * @author hu.yuhao
     * 判断日期是否连续
     * @param endDate 截止日期
     * @param nowDate 开始日期
     * @return boolean true：连续，false：不连续
     */
    public static boolean isRunningDay(Date endDate, Date nowDate) {
        if (endDate == null || nowDate == null) {
            return false;
        }
        try {
            Date formatDate = getFormatDate(endDate);
            Date formatDate1 = getFormatDate(nowDate);
            long nd = 1000 * 24 * 60 * 60;
            // 获得两个时间的毫秒时间差异
            long diff = formatDate.getTime() - formatDate1.getTime();
            // 计算差多少天
            long day = diff / nd;
            if (day <= 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * @author hu.yuhao
     * 计算时间相差小时数
     */
    public static long getHourDiff(Date endData, Date nowData) {
        if (endData == null || nowData == null) {
            return 0;
        }
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endData.getTime() - nowData.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        if (day > 0) {
            return day * 24 + hour;
        } else {
            return hour;
        }
    }

    /**
     * @author hu.yuhao
     * 将long类型转为date
     */
    public static Date longToDate(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        return calendar.getTime();
    }

    /**
     * @author hu.yuhao
     * 将date对象转为只有年月日的date对象
     */
    public static Date getFormatDate(Date date) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(YYYY_MM_DD);
            String format = simpleDateFormat.format(date);
            return simpleDateFormat.parse(format);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * @author hu.yuhao
     * 获取当前只包含年月日的date对象
     */
    public static Date getNowFormatDate() throws ParseException {
        Date nowDate = getNowDate();
        return getFormatDate(nowDate);
    }

    /**
     * @param n 获取相距n天的日期
     * @author zou.chen
     */
    public static Date getNeedDate(int n) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, n * GlobalConstants.DAY_HOURS);
        return calendar.getTime();
    }
    /**
     * 检查是否是合法日期
     */
    public static String fomatDate(String date) {
        date=date.replace("/","");
        date=date.replace("-","");
        SimpleDateFormat sd=new SimpleDateFormat("yyyyMMdd");
        try {
            sd.setLenient(false);
            sd.parse(date);
        }
        catch (Exception e) {
            return null;
        }
        return date;
    }
}
