package com.fenghaha.letuschat.UI.Activity;


import android.annotation.SuppressLint;
import android.os.Build;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.fenghaha.letuschat.Utils.ToastUtil;


/**
 * Created by FengHaHa on2018/8/22 0022 13:31
 */
@SuppressLint("Registered")
public abstract class BaseActivity extends AppCompatActivity {
    protected int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }



    protected <V extends View> V $(@IdRes int id) {
        return (V) findViewById(id);
    }

    abstract protected void initViews();

    protected void initMvp() {
    }
}
