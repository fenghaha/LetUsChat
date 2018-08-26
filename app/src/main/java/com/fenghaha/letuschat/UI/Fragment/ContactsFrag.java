package com.fenghaha.letuschat.UI.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fenghaha.letuschat.R;
import com.fenghaha.letuschat.UI.Adapter.ViewPager.ContactsPagerAdapter;

import butterknife.BindView;

/**
 * Created by FengHaHa on2018/8/24 0024 0:40
 */
public class ContactsFrag extends BaseFragment {

    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    RelativeLayout mTitleLayout;

    public ContactsFrag() {
    }

    @SuppressLint("ValidFragment")
    public ContactsFrag(RelativeLayout mTitleLayout) {
        this.mTitleLayout = mTitleLayout;
    }

    @Override
    public int getContentViewId() {
        return R.layout.frg_home_contacts;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return mRootView;
    }

    @Override
    public void initViews() {

        String[] titles = new String[]{"好友", "群聊"};
        Fragment[] fragments = new Fragment[2];
        fragments[0] = new ContactsItemFrag();
        fragments[1] = new ContactsItemFrag();
        ContactsPagerAdapter adapter = new ContactsPagerAdapter(getActivity().getSupportFragmentManager(), titles, fragments);
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
