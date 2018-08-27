package com.fenghaha.letuschat.Utils;

import android.text.TextUtils;

import java.util.Random;

/**
 * Created by FengHaHa on2018/5/26 0026 0:46
 */
public class MyTextUtil {
    public static String newRandomAccount(){
        StringBuilder builder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            int k = random.nextInt(10);
            builder.append(k);
        }
        return builder.toString();
    }
    public static boolean isEqual(String s1, String s2) {
        return s1.equals(s2);
    }

    public static boolean isLegal(String text, int minLength, int maxLength) {
        //为空
        if (TextUtils.isEmpty(text) || isAllSpace(text)) {
            return false;
        } else if (text.length() >= minLength && text.length() <= maxLength) {
            return true;
        }
        return false;
    }

    public static boolean isEmpty(String... text) {
        for (String s :
                text) {
            if (TextUtils.isEmpty(s) || isAllSpace(s)) return true;
        }
        return false;
    }

    public static boolean isNull(String text) {
        return TextUtils.isEmpty(text) || isAllSpace(text) || "null".equals(text);
    }

    private static boolean isAllSpace(String text) {
        char temp[] = text.toCharArray();
        for (char aTemp : temp) {
            if (aTemp != ' ') return false;
        }
        return true;
    }
}
