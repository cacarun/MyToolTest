package com.mytooltest.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.mytooltest.R;

public class Test_Inflate_Activity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_inflate);

        LinearLayout container = findViewById(R.id.container);

        // 子视图宽高正常生效
//        LayoutInflater.from(this).inflate(R.layout.item_test_inflate, container, true);
        // 子视图宽高正常生效
//        LayoutInflater.from(this).inflate(R.layout.item_test_inflate, container);
        // 子视图宽高正常生效
//        View view = LayoutInflater.from(this).inflate(R.layout.item_test_inflate, container, false);
//        container.addView(view);

        // 子视图宽高失效，变为 width=match parent  height=wrap content
//        View view = LayoutInflater.from(this).inflate(R.layout.item_test_inflate, null);
//        container.addView(view);

        // 子视图宽高正常生效
//        View view = LayoutInflater.from(this).inflate(R.layout.item_test_inflate, null);
//        container.addView(view, 100, 100);

        // 子视图宽高正常生效
        View view = LayoutInflater.from(this).inflate(R.layout.item_test_inflate_with_container, null);
        container.addView(view);

    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

    }


}
