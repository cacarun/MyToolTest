package com.mytooltest.progress.view;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.mytooltest.R;

import java.lang.ref.WeakReference;


public class RoundCornerProgressDialog2 extends Dialog {

    private static final String TAG = "ShowTest";

    private Activity activity;

    private ImageView ivProgressBack;
    private RoundCornerImageView ivProgressFront;
    private ImageView ivProgressHead;

    private static final int PROGRESS_MAX = 100;
    private static final int MSG_PROGRESS_GO = 1;

    private static final long MILLIS_2000 = 2000;
    private static final long MILLIS_5000 = 5000;
    private static final long MILLIS_500 = 500;

    private Handler handler = new LoopHandler(this);

    private ValueAnimator valueAnimator;

    private static class LoopHandler extends Handler {

        WeakReference<RoundCornerProgressDialog2> wSwitcher;

        LoopHandler(RoundCornerProgressDialog2 switcher) {
            wSwitcher = new WeakReference<RoundCornerProgressDialog2>(switcher);
        }

        @Override
        public void handleMessage(Message msg) {
            RoundCornerProgressDialog2 switcher = wSwitcher.get();
            if (switcher == null) {
                return;
            }

            if (msg.what == MSG_PROGRESS_GO) {
                switcher.updatePercent(0, PROGRESS_MAX, MILLIS_5000);
            }
        }
    }

    private Runnable soonRunnable = new Runnable() {
        @Override
        public void run() {

            int percent = (int) valueAnimator.getAnimatedValue();

            Log.d(TAG, "runnableCode called... current percent: " + percent);

            // 取消当前进度条
            valueAnimator.cancel();
            if (percent < PROGRESS_MAX) {

                valueAnimator = null;
                // 快速完成剩余进度
                updatePercent(percent, PROGRESS_MAX, MILLIS_500);
            }

        }
    };

    public RoundCornerProgressDialog2(Activity activity) {
        super(activity, R.style.TranslucentFloatDialog);
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_round_corner_progress2);

        initView();
    }

    private void initView() {

        ivProgressBack = findViewById(R.id.iv_progress_back);
        ivProgressHead = findViewById(R.id.iv_progress_head);

        ivProgressFront = findViewById(R.id.iv_progress_front);
        ivProgressFront.setRadiusDp(20);

        setCanceledOnTouchOutside(false);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // 开始进度条
        handler.sendEmptyMessage(MSG_PROGRESS_GO);
        // 结束进度条
        handler.postDelayed(soonRunnable, MILLIS_2000);
    }

    /**
     * 快速结束进度条
     */
    public void finishProgress() {

        handler.post(soonRunnable);
    }

    private void updatePercent(int from, int to, long duration) {

        valueAnimator = ValueAnimator.ofInt(from, to);
        valueAnimator.setDuration(duration);
        valueAnimator.start();

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {

                int percent = (int) animator.getAnimatedValue();
                Log.d(TAG, "valueAnimator addUpdateListener percent: " + percent);

                float percentFloat = percent * 1.0f / PROGRESS_MAX;

                int headWidth = ivProgressHead.getWidth();
                int headHalfWidth = (int) Math.ceil(headWidth / 2f);

                int ivWidth = ivProgressBack.getWidth();
                int frontWidth = (int) (percentFloat * (ivWidth - headWidth)); // 指示头实际走过的长度
//                Log.d(TAG, "valueAnimator addUpdateListener ivWidth: " + ivWidth + " headWidth：" + headWidth + " frontWidth: " + frontWidth);

                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) ivProgressFront.getLayoutParams();
                lp.width = frontWidth + headHalfWidth; // 匹配指示头位置
                Log.d(TAG, "valueAnimator addUpdateListener lp.width: " + lp.width);
                ivProgressFront.setLayoutParams(lp);

                RelativeLayout.LayoutParams headParams = (RelativeLayout.LayoutParams) ivProgressHead.getLayoutParams();
                headParams.leftMargin = frontWidth;
                ivProgressHead.setLayoutParams(headParams);
//                ivProgressHead.requestLayout();
            }
        });

    }

}
