package com.mytooltest.imageloader.loader;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.mytooltest.R;
import com.mytooltest.util.BitmapUtil;

public class ImageLoaderActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String urlStr = "https://upload-images.jianshu.io/upload_images/944365-207a738cb165a2da.png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_loader);

        ImageView ivImg = findViewById(R.id.iv_img);

        Bitmap bitmap = BitmapUtil.getBitmapFromCacheOrStartToDownloadIt(urlStr);
        ivImg.setImageBitmap(bitmap);
    }


    @Override
    public void onClick(View view) {

    }
}
