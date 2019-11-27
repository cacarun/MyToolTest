package com.mytooltest.imageloader;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mytooltest.R;
import com.mytooltest.imageloader.loader.ImageLoaderActivity;
import com.mytooltest.imageloader.worker.ImageWorkerActivity;

public class ImageLoaderTestActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_loader_test);

        findViewById(R.id.btn_image_loader).setOnClickListener(this);
        findViewById(R.id.btn_image_worker).setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {

        int id = view.getId();
        if (id == R.id.btn_image_loader) {

            startActivity(new Intent(this, ImageLoaderActivity.class));
        } else if (id == R.id.btn_image_worker) {

            startActivity(new Intent(this, ImageWorkerActivity.class));
        }
    }
}
