package com.fenghaha.letuschat.UI.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.avos.avoscloud.AVUser;
import com.fenghaha.letuschat.MVP.Contract.BaseContract;
import com.fenghaha.letuschat.R;
import com.fenghaha.letuschat.Utils.MyApp;
import com.fenghaha.letuschat.Utils.ToastUtil;
import com.fenghaha.letuschat.Utils.ChatUtil;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash_ativity);
        if (AVUser.getCurrentUser() == null) {
            LoginActivity.actionStart(SplashActivity.this,"0","0");
            finish();
        } else {
            ChatUtil.init(this);
//            MyApp.openIMClient();
//            ChatUtil.getContactsList(new BaseContract.BaseCallBack() {
//                @Override
//                public void onFailure(String msg) {
//                    ToastUtil.makeToast(msg);
//                    LoginActivity.actionStart(SplashActivity.this);
//                    finish();
//                }
//                @Override
//                public void onComplete() {
//                    MainActivity.actionStart(SplashActivity.this);
//                    finish();
//                }
//            });

        }

    }
}
