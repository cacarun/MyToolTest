package com.mytooltest.test;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mytooltest.R;
import com.mytooltest.util.DeviceUtil;

public class TestActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout llBottom;

    private boolean isBottomShow = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);


//        int value1 = 1;
//        int value2 = 1 << 1;
//        int value3 = 1 << 2;
//        int value4 = 1 << 3;
//        int valueAll = value1 | value2 | value3 | value4; // 15
//        Log.d("TestActivity", "value1=" + value1 + " value2=" + value2 + " value3=" + value3 + " value4=" + value4
//            + " valueAll=" + valueAll);
//        int result1 = value1 ^ valueAll;
//        Log.d("TestActivity", "result1=" + result1);



        Button btnShow = findViewById(R.id.btn_show);
        btnShow.setOnClickListener(this);

        llBottom = findViewById(R.id.ll_bottom);
        llBottom.setVisibility(View.GONE);


        DisplayMetrics displayMetrics = DeviceUtil.getScreenPix(this);
        int screenWidthPixels = displayMetrics.widthPixels;
        int screenHeightPixels = displayMetrics.heightPixels;
        Log.d("CommonEvent", "screen, screenWidthPixels=" + screenWidthPixels + " screenHeightPixels=" + screenHeightPixels);

        TextView tvTitle = findViewById(R.id.tv_title);
        tvTitle.post(new Runnable() {
            @Override
            public void run() {
                Log.d("CommonEvent", "tvTitle.getHeight=" + tvTitle.getHeight() + " tvTitle.getWidth=" + tvTitle.getWidth());

//                Rect rectangle= new Rect();
//                getWindow().getDecorView().getWindowVisibleDisplayFrame(rectangle);
//                //高度为rectangle.top-0仍为rectangle.top
//                Log.e("CommonEvent", "状态栏-方法4:" + rectangle.top);

                DisplayMetrics dm = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(dm);
                Log.e("CommonEvent", "屏幕高:" + dm.heightPixels);

                //应用区域
                Rect outRect1 = new Rect();
                getWindow().getDecorView().getWindowVisibleDisplayFrame(outRect1);
                Log.e("CommonEvent", "应用区顶部" + outRect1.top);
                Log.e("CommonEvent", "应用区宽" + outRect1.width());
                Log.e("CommonEvent", "应用区高" + outRect1.height());
            }
        });

//        Rect rect = new Rect();
//        getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
//        findViewById(R.id.sv_container).getWindowVisibleDisplayFrame(rect);
//        Log.e("CommonEvent", "CommonEvent, screen, left=" + rect.left + " top=" + rect.top + " right=" + rect.right + " bottom=" + rect.bottom);

    }

//    @Override
//    public void onWindowFocusChanged(boolean hasFocus) {
//        super.onWindowFocusChanged(hasFocus);
//
//        Rect rectangle= new Rect();
//        getWindow().getDecorView().getWindowVisibleDisplayFrame(rectangle);
//        //高度为rectangle.top-0仍为rectangle.top
//        Log.e("CommonEvent", "状态栏-方法3:" + rectangle.top);
//    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        if (id == R.id.btn_show) {

            isBottomShow = !isBottomShow;
            llBottom.setVisibility(isBottomShow ? View.VISIBLE : View.GONE);
        }
    }


}
