package com.mytooltest.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mytooltest.R;

public class TestActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        findViewById(R.id.btn_test_rect).setOnClickListener(this);
        findViewById(R.id.btn_test_inflate).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        if (id == R.id.btn_test_rect) {
            startActivity(new Intent(this, Test_Rect_Activity.class));
        } else if (id == R.id.btn_test_inflate) {
            startActivity(new Intent(this, Test_Inflate_Activity.class));
        }

    }


}
