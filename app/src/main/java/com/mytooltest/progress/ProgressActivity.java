package com.mytooltest.progress;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.mytooltest.R;
import com.mytooltest.progress.view.CircleProgress;
import com.mytooltest.progress.view.RoundCornerProgressDialog;
import com.mytooltest.progress.view.SaleProgressView;
import com.mytooltest.progress.view.SportProgressView;

public class ProgressActivity extends AppCompatActivity implements View.OnClickListener {


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

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pop_dialog_btn:
                popRoundProgressDialog();
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
