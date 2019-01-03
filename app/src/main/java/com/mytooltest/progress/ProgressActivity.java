package com.mytooltest.progress;

import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.mytooltest.R;
import com.mytooltest.imageloader.ImageLoaderWrapper;
import com.mytooltest.progress.view.CircleProgress;
import com.mytooltest.progress.view.ProgressXfermodeView;
import com.mytooltest.progress.view.ProgressXfermodeView2;
import com.mytooltest.progress.view.RoundCornerProgressDialog;
import com.mytooltest.progress.view.RoundCornerProgressDialog2;
import com.mytooltest.progress.view.SaleProgressView;
import com.mytooltest.progress.view.SportProgressView;
import com.mytooltest.util.DeviceUtil;
import com.mytooltest.view.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class ProgressActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView ivRoundImageLoader;
    private ImageView ivRoundGlide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);


        SportProgressView sportview = findViewById(R.id.sportview);
        sportview.setProgress(80);

        CircleProgress circleProgress = findViewById(R.id.myCircleProgress);
        circleProgress.setProgress(100);


        // 条纹进度条 01 https://www.jianshu.com/p/f7e151c2cb57
        // 放置2张图片，一张作为背景（底，bottom），一张作为进度条图片，（cover）进度改变时，改变上面图片的宽度。
        findViewById(R.id.pop_dialog_btn).setOnClickListener(this);
        // 条形进度条 + 指示头
        findViewById(R.id.pop_dialog_btn2).setOnClickListener(this);


        // 条纹进度条 02 - 仿淘宝淘抢购进度条 https://www.jianshu.com/p/18240ea99f6e
        final SaleProgressView saleProgressView = findViewById(R.id.spv);
        SeekBar seekBar = findViewById(R.id.seek);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                saleProgressView.setTotalAndCurrentCount(100, i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        // loading 动画 drawable + src
        ImageView ivLoading = findViewById(R.id.iv_loading);

        Animation animIn = AnimationUtils.loadAnimation(this, R.anim.anim_progress_bar_loading);
        ivLoading.startAnimation(animIn);

        // 或者
//        RotateAnimation rotateAnim = new RotateAnimation(0, 359,
//                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//        rotateAnim.setInterpolator(new LinearInterpolator());
//        rotateAnim.setRepeatCount(Animation.INFINITE);
//        rotateAnim.setDuration(1000);
//        ivLoading.setAnimation(rotateAnim);
//        ivLoading.startAnimation(rotateAnim);
////        ivLoading.clearAnimation();



        // loading 动画 drawable + animation-list
        ImageView ivLoadingCall = findViewById(R.id.iv_loading_call);
//        ivLoadingCall.setBackgroundResource(R.drawable.drawable_anim_calling);
        AnimationDrawable animationDrawable = (AnimationDrawable) ivLoadingCall.getBackground();
        animationDrawable.start();

        // SRC 利用 ==》 源图像在运算时，只是在源图像所在区域与对应区域的目标图像做运算。所以目标图像与源图像不相交的地方是不会参与运算的
        ProgressXfermodeView progressXfermodeView = findViewById(R.id.progress_xfermode_view);
        progressXfermodeView.setProgress(50);

        ProgressXfermodeView2 progressXfermodeView2 = findViewById(R.id.view_progress_loading);
        progressXfermodeView2.setProgress(100);


        // ImageLoader 加载圆角图片
//        String uriPath = "https://upload-images.jianshu.io/upload_images/5750764-f894fa2a8896a29f.jpg";
        String uriPath = "drawable://" + R.drawable.layer2;
        ivRoundImageLoader = findViewById(R.id.iv_round_image_loader);
        ImageLoaderWrapper.getImageLoader().displayImage(uriPath, ivRoundImageLoader, getDisplayImageOptions(), new ImageLoadingListener() {

            @Override
            public void onLoadingStarted(String s, View view) {
            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {
                ivRoundImageLoader.setImageResource(R.drawable.empty);
            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                Log.d("ProgressActivity", "onLoadingComplete = " +  ivRoundImageLoader.getHeight());
            }

            @Override
            public void onLoadingCancelled(String s, View view) {

            }
        });

        // Glide 加载圆角图片
        int radius = DeviceUtil.dip2px(this, 8);
        ivRoundGlide = findViewById(R.id.iv_round_glide);
        Glide.with(this).load(R.drawable.layer2)
                .placeholder(R.drawable.empty)
                .bitmapTransform(new CenterCrop(this),
                        new RoundedCornersTransformation(this, radius, 0, RoundedCornersTransformation.CornerType.TOP))
                .into(ivRoundGlide);

    }

    private DisplayImageOptions getDisplayImageOptions() {

        return new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.empty)
                .showImageForEmptyUri(R.drawable.empty)
                .showImageOnFail(R.drawable.empty)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .displayer(new RoundedBitmapDisplayer(DeviceUtil.dip2px(this, 5), RoundedBitmapDisplayer.CORNER_TOP_LEFT)).build();
//                .displayer(new RoundedCenterCropBitmapDisplayer(DeviceUtil.dip2px(this, 5), RoundedBitmapDisplayer.CORNER_TOP_LEFT, 1)).build();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.pop_dialog_btn:

                // 动态改变 width
                popRoundProgressDialog();
                break;
            case R.id.pop_dialog_btn2:

                // 动态改变 width
                RoundCornerProgressDialog2 dialog = new RoundCornerProgressDialog2(this);
                dialog.show();
                break;
        }
    }


    private RoundCornerProgressDialog mRoundCornerProgressDialog;

    private void popRoundProgressDialog() {
        if (null == mRoundCornerProgressDialog) {
            mRoundCornerProgressDialog = new RoundCornerProgressDialog();
        }
        mRoundCornerProgressDialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTranslucentOrigin);
        mRoundCornerProgressDialog.show(getSupportFragmentManager(), RoundCornerProgressDialog.F_TAG);
    }

}
