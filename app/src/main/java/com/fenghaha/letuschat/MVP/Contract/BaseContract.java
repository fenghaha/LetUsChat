package com.fenghaha.letuschat.MVP.Contract;

import android.content.Context;

import com.fenghaha.letuschat.Utils.ToastUtil;

/**
 * Created by FengHaHa on2018/8/20 0020 18:24
 */
public class BaseContract {
    public interface BaseView {
        default void showToast(String msg) {
            ToastUtil.makeToast(msg);
        }

        Context getContext();
    }

    public abstract static class BaseCallBack<T> {
        public void onSuccess(T data) {
        }

        public void onComplete() {
        }

        public  void onFailure(String msg){
            ToastUtil.makeToast(msg);
        }

        public void onError(Throwable e) {
            e.printStackTrace();
        }
    }

}
