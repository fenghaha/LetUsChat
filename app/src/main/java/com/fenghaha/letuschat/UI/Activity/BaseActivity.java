package com.fenghaha.letuschat.UI.Activity;


import android.annotation.SuppressLint;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.fenghaha.letuschat.Utils.ToastUtil;


/**
 * Created by FengHaHa on2018/8/22 0022 13:31
 */
@SuppressLint("Registered")
public abstract class BaseActivity extends AppCompatActivity {

    protected <V extends View> V $(@IdRes int id) {
        return (V) findViewById(id);
    }

    abstract protected void initViews();

    protected void initMvp() {
    }
}
