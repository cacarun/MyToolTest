package com.mytooltest.dialog;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.mytooltest.R;
import com.mytooltest.dialog.base.SuperDialog;
import com.mytooltest.util.Feedback;
import com.mytooltest.view.RoundCornerImageView;


public class ProgressRunningDialog extends SuperDialog {

    private Activity activity;
    private Feedback feedback;

    private ImageView ivProgressBack;
    private RoundCornerImageView ivProgressFront;
    private ImageView ivProgressHead;

    private static final int PROGRESS_MAX = 100;

    private static final long PROGRESS_MILLIS_DEFAULT = 15000;
    private static final long PROGRESS_MILLIS_FINISH = 1000;

    private ValueAnimator valueAnimator;

    public ProgressRunningDialog(Activity activity, Feedback feedback) {
        super(activity, R.style.TranslucentFloatDialog);
        this.activity = activity;
        this.feedback = feedback;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_progress_running);

        initView();
    }

    private void initView() {

        ivProgressBack = findViewById(R.id.iv_progress_back);
        ivProgressHead = findViewById(R.id.iv_progress_head);

        ivProgressFront = findViewById(R.id.iv_progress_front);

        setCanceledOnTouchOutside(false);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // 开始进度条
        updatePercent(0, PROGRESS_MAX, PROGRESS_MILLIS_DEFAULT);
    }

    @Override
    public void onBackPressed() {

    }

    /**
     * 快速结束进度条
     */
    public void finishProgress(Feedback feedback) {

        this.feedback = feedback;

        if (valueAnimator == null) {
            return;
        }

        int percent = (int) valueAnimator.getAnimatedValue();

        // 取消当前进度条
        valueAnimator.cancel();
        if (percent < PROGRESS_MAX) {

            valueAnimator = null;
            // 快速完成剩余进度
            updatePercent(percent, PROGRESS_MAX, PROGRESS_MILLIS_FINISH);
        }
    }

    private void updatePercent(int from, int to, long duration) {

        valueAnimator = ValueAnimator.ofInt(from, to);
        valueAnimator.setDuration(duration);
        valueAnimator.start();

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {

                int percent = (int) animator.getAnimatedValue();
//                DTLog.d(TAG, "valueAnimator addUpdateListener percent: " + percent);

                float percentFloat = percent * 1.0f / PROGRESS_MAX;

                int headWidth = ivProgressHead.getWidth();
                int headHalfWidth = (int) Math.ceil(headWidth / 2f);

                int ivWidth = ivProgressBack.getWidth();
                int frontWidth = (int) (percentFloat * (ivWidth - headWidth)); // 指示头实际走过的长度

                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) ivProgressFront.getLayoutParams();
                lp.width = frontWidth + headHalfWidth; // 匹配指示头位置
//                DTLog.d(TAG, "valueAnimator addUpdateListener lp.width: " + lp.width);
                ivProgressFront.setLayoutParams(lp);

                RelativeLayout.LayoutParams headParams = (RelativeLayout.LayoutParams) ivProgressHead.getLayoutParams();
                headParams.leftMargin = frontWidth;
                ivProgressHead.setLayoutParams(headParams);

                if (percent == PROGRESS_MAX) {

                    // Fix: View not attached to window manager crash
                    try {
                        if (ProgressRunningDialog.this.isShowing()) {
                            ProgressRunningDialog.this.dismiss();
                        }
                    } catch (Exception e) {
                        // Handle or log or ignore
                        Log.e("ProgressDialog", "updatePercent finish exception = " + e.getMessage());
                    }

                    if (feedback != null) {
                        feedback.onFeedback(0);
                    }
                }
            }
        });

    }

}
