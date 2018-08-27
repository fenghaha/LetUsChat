package com.fenghaha.letuschat.Utils;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by FengHaHa on2018/8/26 0026 0:06
 */
@SuppressLint("SimpleDateFormat")
public class DateUtil {
    public static String millisecsToDateString(long timestamp) {
        SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm");
        return format.format(new Date(timestamp));
    }
    public static String DateToString(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm");
        return format.format(date);
    }
    public static String getTime(String time) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now;
        Date record;
        try {
            now = new Date(System.currentTimeMillis());
            record = sdf.parse(time);
            long difference = (now.getTime() - record.getTime()) / 1000;  //毫秒转换到秒
            long day = difference / (24 * 60 * 60);
            long hour = difference / (60 * 60) - day * 24;
            long min = difference / 60 - day * 24 * 60 - hour * 60;
            if (day > 3) {
                //超过3天直接显示日期咯
                return time;
            } else if (day <= 3 && day > 0) {
                return day + "天前";
            } else if (hour > 0) {
                return (min > 0) ? hour + "小时" + min + "分钟前" : hour + "小时前";
            } else if (min > 0) {
                return min + "分钟前";
            } else {
                return "刚刚";
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
