package com.fenghaha.letuschat.MVP.Presenter;


import com.avos.avoscloud.AVUser;
import com.fenghaha.letuschat.MVP.Contract.BaseContract;
import com.fenghaha.letuschat.MVP.Contract.LoginContract.LoginView;
import com.fenghaha.letuschat.MVP.Model.LoginModel;
import com.fenghaha.letuschat.Utils.MyTextUtil;

/**
 * Created by FengHaHa on2018/8/22 0022 14:33
 */
public class LoginPresenter extends BasePresenter<LoginView> {

    private LoginModel mModel;

    public LoginPresenter(LoginModel model) {
        this.mModel = model;
    }


    public void login(String usrName, String psw) {
        if (MyTextUtil.isEmpty(usrName, psw)) {
            mView.showToast("账号或者密码不合法！");
            return;
        }
        mView.showLoading();
        mModel.login(usrName, psw, new BaseContract.BaseCallBack<AVUser>() {
            @Override
            public void onSuccess(AVUser data) {
                mView.loginSuccess();
            }

            @Override
            public void onFailure(String msg) {
                mView.showToast(msg);
                mView.recovery();
            }
        });
    }

    public void signUp(String usrName, String psw, String psw2) {
        if (MyTextUtil.isEmpty(usrName, psw)) {
            mView.showToast("账号或者密码不合法！");
            return;
        }
        if (!MyTextUtil.isEqual(psw, psw2)) {
            mView.showToast("两次输入密码不一致！");
            return;
        }
        mModel.signUp(usrName, psw, new BaseContract.BaseCallBack<AVUser>() {
            @Override
            public void onComplete() {
                mView.signUpSuccess(usrName, psw);
            }
        });
    }

}
