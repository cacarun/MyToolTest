package com.mytooltest.touch;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ScrollView;
import android.widget.TextView;

import com.mytooltest.R;
import com.mytooltest.base.BaseFragmentAdapter;
import com.mytooltest.touch.ui.ImageFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * ViewPager 本身是处理了滚动事件的冲突,
 * 它在横向滑动时会调用 requestDisallowInterceptTouchEvent() 方法使父控件不拦截当前的 Touch 事件序列
 *
 * 这里只是演示 ScrollView 嵌套 ViewPager 内部拦截法
 */
public class TouchScrollWithViewPagerInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch_scroll_with_viewpager_in);

        initView();
        initListener();
        initData();
    }

    ViewPager mViewPager;
    TextView mTextView;

    private List<Fragment> mFragments;

    ScrollView mNoHorizontalScrollView;
    private boolean first = true;
    private BaseFragmentAdapter mBaseFragmentAdapter;

    private void scroll() {
        mNoHorizontalScrollView.scrollTo(0, 0);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && first) {
            first = false;
            scroll();
        }
    }

    private void initListener() {
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                mTextView.setText(String.format("%d/8", position + 1));
            }
        });

    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mTextView = (TextView) findViewById(R.id.tv_page);
        mNoHorizontalScrollView = (ScrollView) findViewById(R.id.NoHorizontalScrollView);
    }

    private void initData() {
        mFragments = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            ImageFragment imageFragment = ImageFragment.newInstance(R.drawable.huoying);
            mFragments.add(imageFragment);
        }
        mBaseFragmentAdapter = new BaseFragmentAdapter(getSupportFragmentManager(), mFragments);
        mViewPager.setAdapter(mBaseFragmentAdapter);

    }

}
