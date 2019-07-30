package com.mytooltest.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.mytooltest.R;

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

    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        if (id == R.id.btn_show) {

            isBottomShow = !isBottomShow;
            llBottom.setVisibility(isBottomShow ? View.VISIBLE : View.GONE);
        }
    }


}
