package com.mytooltest.imageloader.worker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.mytooltest.R;


public class ImageWorkerActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String urlStr = "https://upload-images.jianshu.io/upload_images/944365-207a738cb165a2da.png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_worker);

        ImageView ivImg = findViewById(R.id.iv_img);
        FacebookHeadImageFetcher.displayImage(urlStr, ivImg);
    }


    @Override
    public void onClick(View view) {

        int id = view.getId();

    }
}
