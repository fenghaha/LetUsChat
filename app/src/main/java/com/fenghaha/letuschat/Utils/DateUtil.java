package com.fenghaha.letuschat.Utils;

import android.annotation.SuppressLint;

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
}
