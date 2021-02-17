package spingboot.express.utils;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description:
 * @Author: wanghu
 * @return:
 * @throws:
 */
public class DateUtils {

    public static long getUnixTime(String date) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
        DateTime dateTime = formatter.parseDateTime(date);
        long timestamp = dateTime.getMillis();
        return timestamp;
    }

    public static long getUnixTime2(String date) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyyMMdd");
        DateTime dateTime = formatter.parseDateTime(date);
        long timestamp = dateTime.getMillis();
        return timestamp;
    }

    public static String getMonthAndDay(Long iMillis) {
        DateTime dateTime = new DateTime(iMillis);
        return dateTime.toString(DateTimeFormat.forPattern("MMdd"));
    }

    public static String getYear(long iMillis) {
        DateTime dateTime = new DateTime(iMillis);
        return dateTime.toString(DateTimeFormat.forPattern("yyyy"));
    }

//
    public static String today() {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
        DateTime date = DateTime.now();
        return date.toString(formatter);
    }

    public static String nowTime() {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        DateTime date = DateTime.now();
        return date.toString(formatter);
    }

    public static String nowTime2() {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyyMMdd");
        DateTime date = DateTime.now();
        return date.toString(formatter);
    }

    public static String nowTime3() {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd-HH-mm-ss");
        DateTime date = DateTime.now();
        return date.toString(formatter);
    }

    //检验日期是否合法
    public static boolean validDateTime(String dateTime) {
        String format = "((19|20)[0-9]{2})-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])" +
                "([01][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]";
        Pattern pattern = Pattern.compile(format);
        Matcher matcher = pattern.matcher(dateTime);
        if (matcher.matches()) {
            pattern = Pattern.compile("(\\d{4})-(\\d+)-(\\d+).*");
            matcher = pattern.matcher(dateTime);
            if (matcher.matches()) {
                int y = Integer.valueOf(matcher.group(1));
                int m = Integer.valueOf(matcher.group(2));
                int d = Integer.valueOf(matcher.group(3));
                if (d > 28) {
                    Calendar c = Calendar.getInstance();
                    c.set(y,m-1,1);
                    int lastDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);
                    return (lastDay >= d);
                }
            }
            return true;
        }
        return false;
    }

    //获取上周周一日期
    public static String getLastWeekMonday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.WEEK_OF_YEAR,-1);
        cal.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(cal.getTime());
    }

    //获取上月第一天
    public static String getLastMonthFirstDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH,-1);
        cal.set(Calendar.DAY_OF_MONTH,1);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(cal.getTime());
    }

    //获取本月第一天
    public static String getThisMonthFirstDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH,1);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(cal.getTime());
    }

    //获取上周日
    public static String getLastWeekSunday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_WEEK,1);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(cal.getTime());
    }

    public static Date getDate(String str) throws ParseException {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sf.parse(str);
    }
}
