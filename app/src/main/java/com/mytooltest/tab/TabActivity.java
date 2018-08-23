package com.mytooltest.tab;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.mytooltest.R;

import java.util.ArrayList;
import java.util.List;

public class TabActivity extends AppCompatActivity {

    private List<Fragment> datas;// 数据源
    private List<String> titles;
    private TabLayout tab;
    private CusViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        initDatas();
        initView();

    }
    //添加两个tab的名称，和两个子fragment。
    private void initDatas() {
        datas = new ArrayList<Fragment>();
        titles = new ArrayList<String>();
        datas.add(FragmentA.newInstance(1));
        datas.add(FragmentB.newInstance(1));
        titles.add("邀请任务");
        titles.add("活跃任务");

    }
    private void initView() {

        viewPager = (CusViewPager) findViewById(R.id.timeline_viewpager);
        viewPager.setPagingEnabled(false);

        tab = (TabLayout) findViewById(R.id.timeline_tablayout);
        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab));
        DetailFragmentPagerAdapter mAdapter = new DetailFragmentPagerAdapter(this,
                getSupportFragmentManager(), datas, titles);
        viewPager.setAdapter(mAdapter);
        tab.setupWithViewPager(viewPager);

        //这里就是将两个自定义tab添加到tablayout中
        TabLayout.Tab tabs = tab.getTabAt(0);
        tabs.setCustomView(mAdapter.getTabView0());
        tabs = tab.getTabAt(1);
        tabs.setCustomView(mAdapter.getTabView1());

    }


}
