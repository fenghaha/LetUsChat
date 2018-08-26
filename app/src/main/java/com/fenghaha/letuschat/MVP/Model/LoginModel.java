package com.fenghaha.letuschat.MVP.Model;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.avos.avoscloud.SignUpCallback;
import com.fenghaha.letuschat.MVP.Contract.BaseContract;
import com.fenghaha.letuschat.Utils.MyApp;
import com.fenghaha.letuschat.Utils.UserUtil;

/**
 * Created by FengHaHa on2018/8/22 0022 14:38
 */
public class LoginModel {

    public void login(String usrName, String psw, BaseContract.BaseCallBack<AVUser> callBack) {
        AVUser.logInInBackground(usrName, psw, new LogInCallback<AVUser>() {
            @Override
            public void done(AVUser avUser, AVException e) {
                if (e == null) {

                    UserUtil.getContactsList(new BaseContract.BaseCallBack() {
                        @Override
                        public void onComplete() {
                            callBack.onSuccess(avUser);
                        }
                    });
                    MyApp.openIMClient();
                } else callBack.onFailure(e.getMessage());
            }
        });
    }

    public void signUp(String usrName, String psw, BaseContract.BaseCallBack<AVUser> callBack) {
        AVUser avUser = new AVUser();
        avUser.setUsername(usrName);
        avUser.setPassword(psw);
        avUser.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    // 注册成功，把用户对象赋值给当前用户 AVUser.getCurrentUser()
                    callBack.onComplete();
                } else {
                    // 失败的原因可能有多种，常见的是用户名已经存在。
                    callBack.onFailure(e.getMessage());
                }
            }
        });
    }
}
