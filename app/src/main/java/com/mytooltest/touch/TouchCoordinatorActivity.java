package com.mytooltest.touch;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.mytooltest.R;
import com.mytooltest.base.BaseFragmentAdapter;
import com.mytooltest.base.RecyclerUtils;
import com.mytooltest.touch.ui.ImageFragment;
import com.mytooltest.touch.ui.RecyclerItemAdapter;

import java.util.ArrayList;
import java.util.List;


public class TouchCoordinatorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch_coordinator);

        initView();
        initListener();
        initData();
    }

    ViewPager mViewPager;
    TextView mTextView;
    RecyclerView mRecyclerView;

    private List<Fragment> mFragments;
    private ArrayList<String> mList;
    private RecyclerItemAdapter mItemAdapter;

    private BaseFragmentAdapter mBaseFragmentAdapter;


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
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
    }

    private void initData() {
        mList = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            String s = String.format("我是第%d个测试Item", i);
            mList.add(s);
        }
        mItemAdapter = new RecyclerItemAdapter(this, mList);
        RecyclerUtils.init(mRecyclerView);
        mRecyclerView.setAdapter(mItemAdapter);

        mFragments = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            ImageFragment imageFragment = ImageFragment.newInstance(R.drawable.huoying);
            mFragments.add(imageFragment);
        }

        mBaseFragmentAdapter = new BaseFragmentAdapter(getSupportFragmentManager(), mFragments);
        mViewPager.setAdapter(mBaseFragmentAdapter);

    }


}
