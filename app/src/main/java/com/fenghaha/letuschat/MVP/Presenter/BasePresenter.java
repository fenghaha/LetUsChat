package com.fenghaha.letuschat.MVP.Presenter;

import com.fenghaha.letuschat.MVP.Contract.BaseContract;

/**
 * Created by FengHaHa on2018/8/22 0022 14:41
 */
public class BasePresenter<V extends BaseContract.BaseView> {
    protected V mView;

    public void attachView(V mView) {
        this.mView = mView;
    }

    public void detachView() {
        this.mView = null;
    }

    public boolean isViewAttached() {
        return mView != null;
    }

    public V getView() {
        return mView;
    }
}