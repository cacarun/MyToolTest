package com.mytooltest.marquee.recycler;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;

import com.mytooltest.R;

import java.util.ArrayList;
import java.util.List;

public class AutoPollActivity extends AppCompatActivity {

    private AutoPollRecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upmarquee_auto_poll);
        initView();
    }
    private void initView() {
        mRecyclerView = (AutoPollRecyclerView) findViewById(R.id.recycleView);
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; ) {
            list.add(" Item: " + ++i);
        }
        AutoPollAdapter adapter = new AutoPollAdapter(list);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(adapter);

        // 方法二 添加
        DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator();
//        defaultItemAnimator.setAddDuration(2000);
        defaultItemAnimator.setRemoveDuration(2000);
        mRecyclerView.setItemAnimator(defaultItemAnimator);

        mRecyclerView.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(null != mRecyclerView){
            mRecyclerView.stop();
        }
    }

}
