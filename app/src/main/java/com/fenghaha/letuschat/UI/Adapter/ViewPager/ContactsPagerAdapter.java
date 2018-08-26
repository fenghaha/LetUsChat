package com.fenghaha.letuschat.UI.Adapter.ViewPager;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by FengHaHa on2018/8/24 0024 1:34
 */
public class ContactsPagerAdapter extends FragmentStatePagerAdapter {
    private Fragment[] mFragments;
    private String[] titles;

    public ContactsPagerAdapter(FragmentManager fm, String[]titles,Fragment[] mFragments) {
        super(fm);
        this.titles = titles;
        this.mFragments = mFragments;
    }

    @Override
    public Fragment getItem(int i) {
        return mFragments[i];
    }

    @Override
    public int getCount() {
        return mFragments.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
