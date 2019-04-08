package com.mytooltest.imageloader.worker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mytooltest.R;


public class ImageWorkerActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_worker);

    }


    @Override
    public void onClick(View view) {

        int id = view.getId();

    }
}
