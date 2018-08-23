package com.mytooltest.tab;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.mytooltest.R;

import java.util.List;

/**
 * Created by jarvis on 2018/8/2.
 */

public class DetailFragmentPagerAdapter extends FragmentPagerAdapter {
    private Context context;
    private List<Fragment> view;
    private List<String> titles;
    private LayoutInflater mInflater;

    public DetailFragmentPagerAdapter(Context context, FragmentManager fragmentManager, List<Fragment> views, List<String> titles) {
        super(fragmentManager);
        this.context = context;
        this.titles = titles;
        this.view = views;
    }

    @Override
    public Fragment getItem(int position) {
        // TODO Auto-generated method stub
        return view.get(position);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return view.size();
    }

    // 没有自定义view的话，这里是显示tab标题的名称，但是自定义之后就需要把这里消除掉
    // @Override
    // public CharSequence getPageTitle(int position) {
    //
    // return titles.get(position);
    // }

    public View getTabView0() {
        mInflater = LayoutInflater.from(context);
        View view = mInflater.inflate(R.layout.item_tab_left, null);
        TextView tv = (TextView) view.findViewById(R.id.tab_text_left);
        tv.setText(titles.get(0));
        return view;
    }

    public View getTabView1() {
        mInflater = LayoutInflater.from(context);
        View view = mInflater.inflate(R.layout.item_tab_right, null);
        TextView tv = (TextView) view.findViewById(R.id.tab_text_right);
        tv.setText(titles.get(1));
        return view;
    }
}
