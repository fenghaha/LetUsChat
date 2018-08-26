package com.fenghaha.letuschat.Utils;

import android.widget.Toast;


/**
 * Created by Administrator on 2018/8/10 0010.
 */
public class ToastUtil {
    public static void makeToast(String content) {
        Toast.makeText(MyApp.getContext(), content, Toast.LENGTH_SHORT).show();
    }
}
