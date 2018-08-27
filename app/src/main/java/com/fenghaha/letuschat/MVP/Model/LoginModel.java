package com.fenghaha.letuschat.MVP.Model;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.avos.avoscloud.SignUpCallback;
import com.fenghaha.letuschat.MVP.Contract.BaseContract;
import com.fenghaha.letuschat.Utils.MyApp;
import com.fenghaha.letuschat.Utils.ChatUtil;

/**
 * Created by FengHaHa on2018/8/22 0022 14:38
 */
public class LoginModel {

    public void login(String usrName, String psw, BaseContract.BaseCallBack<AVUser> callBack) {
        AVUser.logInInBackground(usrName, psw, new LogInCallback<AVUser>() {
            @Override
            public void done(AVUser avUser, AVException e) {
                if (e == null) {

                    ChatUtil.getContactsList(new BaseContract.BaseCallBack() {
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
}
