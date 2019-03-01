package com.mytooltest.thread;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.mytooltest.R;


public class ThreadTestActivity extends AppCompatActivity implements View.OnClickListener, ThreadTestManager.LoadingListener {

    private static final String TAG = "ThreadTestActivity";

    private Button btnLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_test);


        btnLoading = findViewById(R.id.btn_loading);
        btnLoading.setOnClickListener(this);

        ThreadTestManager.getInstance().setLoadingListener(this);
    }


    @Override
    public void onClick(View v) {

        int id = v.getId();
        if (id == R.id.btn_loading) {

            btnLoading.setText("Start Loading...");
            ThreadTestManager.getInstance().loadingData();
        }
    }


    @Override
    public void onLoadingListener() {

        Log.d(TAG, "loading finish!!!");
        btnLoading.setText("Click me");
    }
}
