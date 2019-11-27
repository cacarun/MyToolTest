package com.mytooltest.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;


public class BaseFragmentAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragments;
    private String[] mTitles;

    public BaseFragmentAdapter(FragmentManager fm, List<Fragment> fragments) {
        this(fm, fragments, null);
    }

    public BaseFragmentAdapter(FragmentManager fm, List<Fragment> fragments, String[] mTitles) {
        super(fm);
        this.mFragments = fragments;
        this.mTitles = mTitles;
    }

    @Override
    public Fragment getItem(int position) {
        if (isEmpty()) {
            return null;
        }
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        if (isEmpty()) {
            return 0;
        }
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }

    public boolean isEmpty() {
        return mFragments == null || mFragments.size() == 0;

    }
}
