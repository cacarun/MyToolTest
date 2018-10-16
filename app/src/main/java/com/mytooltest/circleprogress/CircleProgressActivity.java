package com.mytooltest.circleprogress;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mytooltest.R;

public class CircleProgressActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_progress);


        SportProgressView sportview = (SportProgressView) findViewById(R.id.sportview);
        sportview.setProgress(80);

        CircleProgress circleProgress = findViewById(R.id.myCircleProgress);
        circleProgress.setProgress(100);

    }


}
