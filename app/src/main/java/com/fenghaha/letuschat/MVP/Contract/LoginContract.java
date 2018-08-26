package com.fenghaha.letuschat.MVP.Contract;

import com.avos.avoscloud.AVUser;

/**
 * Created by FengHaHa on2018/8/22 0022 14:31
 */
public class LoginContract extends BaseContract {
    public interface LoginView extends BaseView {
        void showLoading();

        void loginSuccess();

        void signUpSuccess(String usn, String psw);

        void loginMode();

        void signUpMode();
        void  recovery();
    }
}
