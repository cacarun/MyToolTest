package com.mytooltest.banner;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.mytooltest.R;

import java.util.ArrayList;
import java.util.List;

public class BannerActivity extends AppCompatActivity {

    private static final String TAG = "BannerActivity";

//    private BannerView bannerView;
    private BannerViewMagic bannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);

        bannerView = findViewById(R.id.view_banner);

        // 图片 160 * 351 比例
        String url01 = "https://s3-us-west-1.amazonaws.com/dt-client-resource/talku/banner1%402x.png";
        String url02 = "https://s3-us-west-1.amazonaws.com/dt-client-resource/talku/banner2%402x.png";
        String url03 = "https://s3-us-west-1.amazonaws.com/dt-client-resource/talku/banner3%402x.png";
        List<String> dataList = new ArrayList<>();
        dataList.add(url01);
        dataList.add(url02);
        dataList.add(url03);
        bannerView.initData(dataList);

    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d("BannerView", "onResume");
        bannerView.startBanner();
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.d("BannerView", "onStop");
        bannerView.stopBanner();
    }
}
