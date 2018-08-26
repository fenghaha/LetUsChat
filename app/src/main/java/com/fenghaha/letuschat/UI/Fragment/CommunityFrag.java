package com.fenghaha.letuschat.UI.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.fenghaha.letuschat.R;

/**
 * Created by FengHaHa on2018/8/24 0024 0:40
 */
public class CommunityFrag extends BaseFragment {
    RelativeLayout mTitleLayout;
    public CommunityFrag() {
    }

    @SuppressLint("ValidFragment")
    public CommunityFrag(RelativeLayout mTitleLayout) {
        this.mTitleLayout = mTitleLayout;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return mRootView;
    }

    @Override
    public void initViews() {

    }

    @Override
    public int getContentViewId() {
        return R.layout.frg_home_community;
    }

}
