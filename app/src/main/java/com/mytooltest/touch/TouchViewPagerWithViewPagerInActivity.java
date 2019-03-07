package com.mytooltest.touch;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.mytooltest.R;
import com.mytooltest.base.BaseFragmentAdapter;
import com.mytooltest.touch.ui.ViewPagerFragment;

import java.util.ArrayList;

public class TouchViewPagerWithViewPagerInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch_viewpager_with_viewpager_in);
        initView();
        initData();
    }

    ViewPager mViewPager;
    TabLayout mTabLayout;
    public static final String TAG = "xujun";

    private final String[] mTitles = new String[]{
            "首页", "微博", "相册", "我的"
    };
    private ArrayList<Fragment> mFragments;


    private void initData() {
        mFragments = new ArrayList<>();
        for (int i = 0; i < mTitles.length; i++) {
            ViewPagerFragment listFragment = ViewPagerFragment.newInstance(mTitles[i]);
            mFragments.add(listFragment);

        }
        BaseFragmentAdapter baseViewPagerAdapter = new BaseFragmentAdapter(getSupportFragmentManager(), mFragments, mTitles);
        mViewPager.setAdapter(baseViewPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                ((ViewPagerFragment) mFragments.get(position)).onSelected();
                Log.i(TAG, "onPageSelected: position=" + position);
            }
        });
    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mTabLayout = (TabLayout) findViewById(R.id.tabs);
    }

}
