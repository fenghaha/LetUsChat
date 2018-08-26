package com.fenghaha.letuschat.UI.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by FengHaHa on2018/8/22 0022 17:43
 */
public abstract class BaseFragment extends Fragment {
    public abstract int getContentViewId();
    public abstract void initViews();

    protected void initAllMembersView(Bundle savedInstanceState) {}

    protected Context mContext;
    protected View mRootView;
    Unbinder unbinder;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(getContentViewId(), container, false);
        unbinder = ButterKnife.bind(this, mRootView);
        this.mContext = getActivity();
        initAllMembersView(savedInstanceState);
        initViews();
        initMvp();
        return mRootView;
    }

    protected void initMvp(){}
    protected boolean isAttachedContext() {
        return getActivity() != null;
    }

    /**
     * 检查activity连接情况
     */
    public void checkActivityAttached() {
        if (getActivity() == null) {
            throw new ActivityNotAttachedException();
        }
    }

    public static class ActivityNotAttachedException extends RuntimeException {
        public ActivityNotAttachedException() {
            super("Fragment has disconnected from Activity ! - -.");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}